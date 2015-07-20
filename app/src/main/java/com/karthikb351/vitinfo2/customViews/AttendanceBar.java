package com.karthikb351.vitinfo2.customViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.karthikb351.vitinfo2.R;

/**
 * Created by aashrai on 20/7/15.
 * Custom view for attendance percentage bar
 * This view assumes that you are setting the dimensions
 *
 * @attr ref R.styleable#AttendanceBar_innerRadiusRatio
 * @attr ref R.styleable#AttendanceBar_thicknessRatio
 * @attr ref R.styleable#AttendanceBar_ringColor
 * @attr ref R.styleable#AttendanceBar_percentage
 * @attr ref R.styleable#AttendanceBar_textColor
 * @attr ref R.styleable#AttendanceBar_fontSize
 */
public class AttendanceBar extends View {

    float innerRadiusRatio, thicknessRatio, fontSize;
    ColorStateList ringColor, textColor;
    float percentage, innerRectOffset;
    String percentageText;
    Path path;
    Paint paint;
    RectF rectF;

    public AttendanceBar(Context context) {
        super(context);
    }

    public AttendanceBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rectF = new RectF();
        path = new Path();
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AttendanceBar, 0, 0);

        try {
            innerRadiusRatio = typedArray.getFloat(R.styleable.AttendanceBar_innerRadiusRatio, 4.0f);
            thicknessRatio = typedArray.getFloat(R.styleable.AttendanceBar_thicknessRatio, 12.0f);
            percentage = typedArray.getFloat(R.styleable.AttendanceBar_percentage, 0);
            percentageText = Integer.toString((int) percentage);
            fontSize = typedArray.getDimensionPixelSize(R.styleable.AttendanceBar_fontSize, 0);
            ringColor = typedArray.getColorStateList(R.styleable.AttendanceBar_ringColor);
            textColor = typedArray.getColorStateList(R.styleable.AttendanceBar_textColor);
        } finally {
            typedArray.recycle();
        }

    }

    public AttendanceBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = Math.min(MeasureSpec.getSize(widthMeasureSpec)
                , MeasureSpec.getSize(heightMeasureSpec));
        //Making the view square
        setMeasuredDimension(size, size);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(ringColor.getDefaultColor());
        paint.setStrokeWidth(getWidth() / thicknessRatio);

        innerRectOffset = getWidth() - getWidth() / innerRadiusRatio - paint.getStrokeWidth();
        rectF.set(paint.getStrokeWidth(), paint.getStrokeWidth(), innerRectOffset, innerRectOffset);
        path.arcTo(rectF, 270, percentage / 100 * 360);
        canvas.drawPath(path, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(textColor.getDefaultColor());
        paint.setTextSize(fontSize);
        Rect rect = new Rect();
        paint.getTextBounds(percentageText, 0, percentageText.length(), rect);
        canvas.drawText(percentageText,
                rectF.centerX() - paint.measureText(percentageText) / 2,
                rectF.centerY() + rect.height() / 3,
                paint
        );
    }

    public void setRingColor(ColorStateList ringColor) {
        this.ringColor = ringColor;
        invalidate();
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
        this.percentageText = Integer.toString(percentage);
        invalidate();
    }

    @Override
    public void invalidate() {
        path = new Path();
        super.invalidate();
    }
}
