/*
 * VITacademics
 * Copyright (C) 2015  Aneesh Neelam <neelam.aneesh@gmail.com>
 * Copyright (C) 2015  Saurabh Joshi <saurabhjoshi94@outlook.com>
 * Copyright (C) 2015  Gaurav Agerwala <gauravagerwala@gmail.com>
 * Copyright (C) 2015  Karthik Balakrishnan <karthikb351@gmail.com>
 * Copyright (C) 2015  Pulkit Juneja <pulkit.16296@gmail.com>
 * Copyright (C) 2015  Hemant Jain <hemanham@gmail.com>
 * Copyright (C) 2015  Darshan Mehta <darshanmehta17@gmail.com>
 *
 * This file is part of VITacademics.
 *
 * VITacademics is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VITacademics is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VITacademics.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.karthikb351.vitinfo2.customwidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.karthikb351.vitinfo2.R;


public class TimeLineView extends View {

    public static final int STATE_FINISHED = 0X01;
    public static final int STATE_CURRENT = 0X02;
    public static final int STATE_SCHEDULED = 0X03;

    public static final int TYPE_INITIAL = 0x04;
    public static final int TYPE_INNER = 0x05;
    public static final int TYPE_END = 0x06;

    private float PADDING_TOP = 12;
    private float CIRCLE_RADIUS = 6;
    private float BORDER_THICKNESS = 4;
    private float STROKE_WIDTH_RING = 3.33f;
    private float STROKE_WIDTH_BORDER = 10;
    private float STROKE_WIDTH_LINE = 2;

    private int widgetState;
    private int widgetType;

    private Paint paintRing;
    private Paint paintDot;
    private Paint paintBorder;
    private Paint paintLine;

    private float cx;
    private float cy;
    private float height;
    private float width;

    private float density;

    private RectF rectF;

    private Path mPath;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();
        cx = width / 2;
        cy = PADDING_TOP + CIRCLE_RADIUS;

        switch (widgetState){
            case STATE_CURRENT:
                rectF.set(cx - BORDER_THICKNESS, cy - BORDER_THICKNESS,
                        cx + BORDER_THICKNESS, cy + BORDER_THICKNESS);
                mPath.reset();
                for (int i = 0; i <= 360; i++)
                {
                    mPath.addArc(rectF, i, 1);
                }
                canvas.drawPath(mPath, paintBorder);
            case STATE_SCHEDULED:
                rectF.set(cx - CIRCLE_RADIUS, cy - CIRCLE_RADIUS,
                        cx + CIRCLE_RADIUS, cy + CIRCLE_RADIUS);
                mPath.reset();
                for (int i = 0; i <= 360; i += 1)
                {
                    mPath.addArc(rectF, i, 1);
                }
                canvas.drawPath(mPath, paintRing);
                break;
            case STATE_FINISHED:
                canvas.drawCircle(cx, cy, CIRCLE_RADIUS, paintDot);
                break;
        }

        switch (widgetType){
            case TYPE_INITIAL:
                canvas.drawLine(cx, cy + (11 * density), cx, height, paintLine);
                break;
            case TYPE_INNER:
                canvas.drawLine(cx, cy - (11 * density), cx, 0, paintLine);
                canvas.drawLine(cx, cy + (11 * density), cx, height, paintLine);
                break;
            case TYPE_END:
                canvas.drawLine(cx, cy - (11 * density), cx, 0, paintLine);
                break;
        }

    }

    private void initialize(){

        density = getContext().getResources().getDisplayMetrics().density;

        PADDING_TOP *= density;
        BORDER_THICKNESS += CIRCLE_RADIUS;
        BORDER_THICKNESS *= density;
        CIRCLE_RADIUS *= density;
        STROKE_WIDTH_RING *= density;
        STROKE_WIDTH_BORDER *= density;
        STROKE_WIDTH_LINE *= density;

        paintDot = new Paint();
        paintDot.setColor(ContextCompat.getColor(getContext(), R.color.text_secondary));
        paintDot.setFlags(Paint.ANTI_ALIAS_FLAG);

        paintRing = new Paint();
        paintRing.setColor(ContextCompat.getColor(getContext(), R.color.text_secondary));
        paintRing.setFlags(Paint.ANTI_ALIAS_FLAG);
        paintRing.setStrokeWidth(STROKE_WIDTH_RING);
        paintRing.setStyle(Paint.Style.FILL_AND_STROKE);

        paintBorder = new Paint();
        paintBorder.setColor(ContextCompat.getColor(getContext(), R.color.text_secondary));
        paintBorder.setAlpha(90);
        paintBorder.setFlags(Paint.ANTI_ALIAS_FLAG);
        paintBorder.setStrokeWidth(STROKE_WIDTH_BORDER);
        paintBorder.setStyle(Paint.Style.FILL_AND_STROKE);

        paintLine = new Paint();
        paintLine.setColor(ContextCompat.getColor(getContext(), R.color.text_secondary));
        paintLine.setAlpha(70);
        paintLine.setFlags(Paint.ANTI_ALIAS_FLAG);
        paintLine.setStrokeWidth(STROKE_WIDTH_LINE);
        paintLine.setStyle(Paint.Style.FILL_AND_STROKE);

        widgetState = STATE_SCHEDULED;
        widgetType = TYPE_INNER;

        rectF = new RectF();
        mPath = new Path();

    }

    public void setState(int state){
        switch (state){
            case STATE_CURRENT:
                widgetState = STATE_CURRENT;
                break;
            case STATE_SCHEDULED:
                widgetState = STATE_SCHEDULED;
                break;
            case STATE_FINISHED:
                widgetState = STATE_FINISHED;
                break;
            default:
                break;
        }
        invalidate();
    }

    public void setType(int type){
        switch (type){
            case TYPE_INITIAL:
                widgetType = TYPE_INITIAL;
                break;
            case TYPE_INNER:
                widgetType = TYPE_INNER;
                break;
            case TYPE_END:
                widgetType = TYPE_END;
                break;
            default:
                break;
        }
        invalidate();
    }

    public TimeLineView(Context context) {
        super(context);
        initialize();
    }

    public TimeLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }
}
