package com.example.prog.bloodshare.models;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Prog on 18/12/2016.
 */
public class Request {
    @SerializedName("id")
    public int request_id;
    @SerializedName("user_id")
    public int id;
    @SerializedName("num_donator")
    public int num_donator;
    @SerializedName("numofuser")
    public int numofuser;
    @SerializedName("bloodtype")
    public String bloodtype;
    @SerializedName("location")
    public String location;
    @SerializedName("reason")
    public String reason;
    @SerializedName("details")
    public String details;
    @SerializedName("user_name")
    public String user_name;
    @SerializedName("timestamp")
    private String timestamp;
    public String getTime() {
        if (timestamp == null) {
            return "now";
        } else {
            SimpleDateFormat serverFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

            Date date = null;
            try {
                date = serverFormat.parse(timestamp);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            TimeZone timeZone = TimeZone.getDefault();
            int rawOffset = timeZone.getRawOffset();
            long local = 0;
            if (date != null) {
                local = date.getTime() + rawOffset;
            }
            Calendar calendar = new GregorianCalendar();
            calendar.setTimeInMillis(local);
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/d" +"\n"+
                    "hh:mm a", Locale.ENGLISH);
            return format.format(calendar.getTime());
        }
    }
}
