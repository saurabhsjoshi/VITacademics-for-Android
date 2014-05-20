package com.karthikb351.vitinfo2.objects.TimeTableFiles;

import android.content.Context;
import android.text.format.DateUtils;

import com.karthikb351.vitinfo2.objects.DataHandler;
import com.karthikb351.vitinfo2.objects.Subject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class TimeTable{

    DataHandler dat;
    String[] slts_today;

    public TimeTable(Context context){
        dat = new DataHandler(context);
    }

    /* Get slots number for each day */
    private void set_day(int Day){
        switch (Day){
            case Calendar.MONDAY:
                slts_today = new String[] {"a1",  "f1", "c1",  "e1", "td1", "a2", "f2", "c2", "e2", "td2", "l1", "l2", "l3", "l4", "l5", "l6", "l31", "l32", "l33", "l34", "l35", "l36"};
                break;

            case Calendar.TUESDAY:
                slts_today = new String[] {"b1", "g1", "d1", "ta1", "tf1", "b2", "g2", "d2", "ta2", "tf2", "l7", "l8", "l9", "l10", "l11", "l12", "l37", "l38", "l39", "l40", "l41", "l42"};
                break;

            case Calendar.WEDNESDAY:
                slts_today = new String[] {"c1","f1","e1","tb1","tg1","c2","f2","e2","tb2","tg2","l13","l14","l15","l16","l17","l18","l43","l44","l45","l46","l47","l48"};
                break;

            case Calendar.THURSDAY:
                slts_today = new String[] {"d1","a1","f1","c1","te1","d2","a2","f2","c2","te2","l19","l20","l21","l22","l23","l24","l49","l50","l51","l52","l53","l54"};
                break;

            case Calendar.FRIDAY:
                slts_today = new String[] {"e1","b1","g1","d1","tc1","e2","b2","g2","d2","tc2","l25","l26","l27","l28 ","l29","l30","l55","l56","l57","l58","l59","l60"};
                break;
        }
    }

    //PARSE A FRIENDS TIMETABLE
    public String FriendVenue = "";
    public String FreindEndsIn = "";
    public boolean getFriendStatus(String ttJSON){
        Calendar calendar  = Calendar.getInstance();

        int Day = calendar.get(Calendar.DAY_OF_WEEK);

        if(Day == Calendar.SATURDAY || Day == Calendar.SUNDAY)
        {
            FriendVenue = "";
            FreindEndsIn = "";
            return false;
        }

        else{
            set_day(Day);
            try {
                JSONObject root = new JSONObject(ttJSON);

                JSONObject days = root.getJSONObject("timetable");

                SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
                JSONArray json_slts = days.getJSONArray(dayFormat.format(calendar.getTime()));
                String clsnbr, slt="";

                for(int i = 0; i < json_slts.length(); i++){
                    if(json_slts.getInt(i) != 0){
                        clsnbr = Integer.toString(json_slts.getInt(i));

                        //Get the subject from data handler
                        Subject sub = getFriendSubject(clsnbr, root.getJSONArray("subjects"));

                        //Get the slot for now
                        if(sub.slot.contains("+")){
                            int tmp;

                            //Check if two slots and break
                            String[] parts = sub.slot.split("\\+");
                            for(int j = 0; j < parts.length; j++){
                                //Check if lab and increment accordingly
                                if(parts[j].charAt(0) == 'L')
                                    tmp = i + 10;
                                else
                                {
                                    tmp = i;
                                    if (i > 4)
                                        tmp -= 1;
                                }
                                if(slts_today[tmp].trim().toUpperCase().equals(parts[j])){
                                    slt = parts[j];
                                    break;
                                }
                            }
                        }
                        //Only one slot wow
                        else
                            slt = sub.slot;

                        TTSlot temp = new TTSlot(slt, clsnbr);
                        temp.venue = getVenue(root.getJSONArray("subjects"), clsnbr);
                        temp.setTime(i);

                        if (calendar.compareTo(temp.frm_time) >= 0 && calendar.compareTo(temp.to_time) < 0){
                            FriendVenue = temp.venue;
                            FreindEndsIn = DateUtils.getRelativeTimeSpanString(temp.to_time.getTimeInMillis(), calendar.getTimeInMillis(), 0).toString();
                            return true;
                        }
                    }
                }

            }catch (Exception e){e.printStackTrace(); return false;}

            return false;
        }

    }

    private Subject getFriendSubject(String clsnbr, JSONArray root){
        Subject att = new Subject();
        try {
            JSONObject sub;
            String temp;

            for(int i = 0; i < root.length(); i++){
                sub = root.getJSONObject(i);
                temp = sub.getString("cnum");
                if(temp.equals(clsnbr)){
                    att.slot = sub.getString("slot");
                    break;
                }
            }
        }catch (Exception e){e.printStackTrace();}

        return att;
    }

    /* Returns list of TTSlots for given Calendar.Day! */
    public ArrayList<TTSlot> getTT(int Day){
        ArrayList<TTSlot> today = new ArrayList<TTSlot>();

        //If Sunday or Monday then show Monday
        if(Day == Calendar.SUNDAY || Day == Calendar.SATURDAY)
            Day = Calendar.MONDAY;

        set_day(Day);

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_WEEK, Day);

            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
            JSONObject root = new JSONObject(dat.getTimeTable());
            JSONArray subs = root.getJSONArray("subjects");
            root = root.getJSONObject("timetable");

            JSONArray json_slts = root.getJSONArray(dayFormat.format(calendar.getTime()));

            TTSlot temp;
            String clsnbr, slt="";

            for(int i = 0; i < json_slts.length(); i++){

                //Check if user has class now
                if(json_slts.getInt(i) != 0){
                    clsnbr = Integer.toString(json_slts.getInt(i));

                    //Get the subject from data handler
                    Subject sub = dat.getSubject(clsnbr);

                    //Get the slot for now
                    if(sub.slot.contains("+")){
                        int tmp;

                        //Check if two slots and break
                        String[] parts = sub.slot.split("\\+");
                        for(int j = 0; j < parts.length; j++){
                            //Check if lab and increment accordingly

                            if(parts[j].charAt(0) == 'L')
                                tmp = i + 10;
                            else
                            {
                                tmp = i;
                                if (i > 4)
                                    tmp -= 1;
                            }
                            if(slts_today[tmp].trim().toUpperCase().equals(parts[j])){
                                slt = parts[j];
                                break;
                            }
                        }
                    }
                    //Only one slot wow
                    else
                        slt = sub.slot;

                    temp = new TTSlot(slt, clsnbr);
                    temp.venue = getVenue(subs, clsnbr);

                    temp.setTime(i);
                    today.add(temp);
                }
            }
        }catch (Exception e){e.printStackTrace();}
        return today;
    }

    private String getVenue(JSONArray json, String clsnbr) throws Exception{
        for(int i = 0; i < json.length(); i++){
            JSONObject t = json.getJSONObject(i);
            if(t.getString("cnum").equals(clsnbr))
                return t.getString("venue");
        }
        throw new Exception("Couldn't find the Venue!");
    }
}