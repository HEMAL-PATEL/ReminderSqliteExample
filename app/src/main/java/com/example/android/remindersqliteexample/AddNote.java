package com.example.android.remindersqliteexample;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import com.database.DatabseManage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.android.remindersqliteexample.R.id.event;

public class AddNote extends AppCompatActivity {

    public EditText editEvent, editDate , editTime;
    public int year , month , day , hour , minute;
    public Button done;
    public AlarmManager alarmManager;
    public String eventEntered;
    public String timeEntered;
    public String dateEntered;
    public String formateTime;
    public Calendar c1 , c2;
    private Long tsCurrent, tsSet;
    public String ts;
    public String toParse;
    public SimpleDateFormat format;
    public Date date;
    public RelativeLayout relativeLayout;
    public String timeToNotify;


    public DatabseManage databseManage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databseManage = new DatabseManage(getApplicationContext());

        editEvent = (EditText) findViewById(event);
        editTime = (EditText) findViewById(R.id.time);
        editDate = (EditText) findViewById(R.id.date);
        done = (Button) findViewById(R.id.button);

        editTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c1 = Calendar.getInstance();
                hour = c1.get(Calendar.HOUR_OF_DAY);
                minute = c1.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddNote.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                timeToNotify = hourOfDay+":"+minute;
                                formateTime = FormatTime(hourOfDay, minute);
                                editTime.setText(formateTime);
                            }
                        }, hour, minute, false);

                timePickerDialog.show();
            }

        });



        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c2 = Calendar.getInstance();
                year = c2.get(Calendar.YEAR);
                month = c2.get(Calendar.MONTH);
                day = c2.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddNote.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                editDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, year, month, day);

                datePickerDialog.show();

            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eventEntered = editEvent.getText().toString();
                timeEntered = editTime.getText().toString();
                dateEntered = editDate.getText().toString();

                databseManage.createDatabase(eventEntered , "" , timeEntered , dateEntered);

                tsCurrent = System.currentTimeMillis();
                ts = tsCurrent.toString();
                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                setOneTimeAlarm();

                finish();
            }
        });


    }



    public void setOneTimeAlarm(){
        Intent intent = new Intent(this , TimeAlarm.class);
        intent.putExtra("event", eventEntered);
        intent.putExtra("time", timeEntered);
        intent.putExtra("date", dateEntered);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        try {
            // Convert the set date and time to timestamp
            toParse = dateEntered + " " + timeToNotify;
            format = new SimpleDateFormat("d-M-yyyy hh:mm");
            date = format.parse(toParse);
            tsSet = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        alarmManager.set(AlarmManager.RTC_WAKEUP  , tsSet , pendingIntent);
    }



    public String FormatTime(int hour, int minute){

        String time;
        time = "";
        String formattedMinute;

        if(minute/10 == 0){
            formattedMinute = "0"+minute;
        }
        else{
            formattedMinute = ""+minute;
        }

        if(hour == 0){
            time = "12" + ":" + formattedMinute + " AM";
        }
        else if(hour < 12){
            time = hour + ":" + formattedMinute + " AM";
        }
        else if(hour == 12){
            time = "12" + ":" + formattedMinute + " PM";
        }
        else{
            int temp = hour - 12;
            time = temp + ":" + formattedMinute + " PM";
        }
        return time;
    }


}
