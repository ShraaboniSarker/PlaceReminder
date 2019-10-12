package com.placereminder.placereminder.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.placereminder.placereminder.R;
import com.placereminder.placereminder.db.AppDatabase;
import com.placereminder.placereminder.db.PrefKey;
import com.placereminder.placereminder.db.PrefManager;
import com.placereminder.placereminder.model.Reminderdb;
import com.placereminder.placereminder.notification.MyNotificationPublisher;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddReminderActivity extends AppCompatActivity implements View.OnClickListener {


    EditText placeName;
    EditText placeLocation;
    EditText alarmTime;
    EditText placeDescription;
    Button btnSave;
    EditText alarmDate;
    Reminderdb reminder;
    AppDatabase appDatabase;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    Button btn_pick_location_from_map;
    int REQUEST_CODE = 1;
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    int PLACE_PICKER_REQUEST = 1;
    int alarmHour;
    int alarmMin;
    int year, month,day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        initView();
        initListner();
    }
    private void initView() {
        myCalendar = Calendar.getInstance();
        placeName = findViewById(R.id.et_place_name);
        placeLocation = findViewById(R.id.et_place_location);
        alarmTime = findViewById(R.id.et_time_picker);
        btnSave = findViewById(R.id.btn_save);
        placeDescription = findViewById(R.id.et_description);
        alarmDate = findViewById(R.id.et_date_picker);
        btn_pick_location_from_map = findViewById(R.id.btn_pick_location_from_map);
       // alarmDate.setMinDate(System.currentTimeMillis() - 1000);
        appDatabase = AppDatabase.getAppDatabase(this);
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
    }

    private void initListner() {
        placeLocation.setOnClickListener(this);
        alarmTime.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        alarmDate.setOnClickListener(this);
        btn_pick_location_from_map.setOnClickListener(this);
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        alarmDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_place_location:
//                startActivity(new Intent(this,PickPlaceActivity.class));
                break;
            case R.id.et_time_picker:
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddReminderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String am_pm;
                        if (selectedHour>12){
                            am_pm = "pm";
                        }else{
                            am_pm = "am";
                        }
                        alarmTime.setText( selectedHour + ":" + selectedMinute+" "+am_pm);
                        alarmHour = selectedHour;
                        alarmMin = selectedMinute;
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

                break;
            case R.id.btn_save:
                reminder = new Reminderdb();
                reminder.setPlaceName(placeName.getText().toString());
                reminder.setPlaceLocation(placeLocation.getText().toString());
                reminder.setDate(alarmDate.getText().toString());
                reminder.setTime(alarmTime.getText().toString());
                reminder.setDescription(placeDescription.getText().toString());
                appDatabase.reminderDao().addTradeHistory(reminder);
                String dateofAlarm =alarmDate.getText().toString();
                        String[] arrOfStr = dateofAlarm.split("/", 3);
                        year = Integer.parseInt(arrOfStr[2]);
                        month = Integer.parseInt(arrOfStr[1]);
                        day = Integer.parseInt(arrOfStr[0]);
                Calendar myAlarmDate = Calendar.getInstance();
                myAlarmDate.setTimeInMillis(System.currentTimeMillis());
                myAlarmDate.clear();
                myAlarmDate.set(year, month, day, alarmHour, alarmMin, 0);
                scheduleNotification(getNotification( placeName.getText().toString(),placeLocation.getText().toString()+
                        "/n"+placeDescription.getText().toString()) , myAlarmDate) ;
               // startActivity(new Intent(AddReminderActivity.this,PickPlaceActivity.class));
                Toast.makeText(this, "Successfully set the reminder!", Toast.LENGTH_SHORT).show();
//                PrefManager.getSharePref().saveABoolean(PrefKey.KEY_FIRST_TIME_USER,true);
               // PrefKey.KEY_FIRST_TIME_USER = true;
                onBackPressed();
                break;
            case R.id.et_date_picker:
                new DatePickerDialog(AddReminderActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.btn_pick_location_from_map:
               // startActivity(new Intent(AddReminderActivity.this,PickPlaceActivity.class));
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();  // for some unknown reason google map
                // is loading but got close with in 2 sec. worked a lot on that issue. myb due to google
                // api duplicate issue reason.
                Intent place_intent;
                try {
                    place_intent = builder.build(AddReminderActivity.this);
                    startActivityForResult(place_intent,REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    protected void onActivityResult(int requestCode,int resultCode, Intent data){

        if(requestCode == REQUEST_CODE){

            if(resultCode == RESULT_OK){
                Place place = PlacePicker.getPlace(data,this);
                String address = String.format("Place %s",place.getAddress());
                placeLocation.setText(address);
            }
        }

    }

    private void scheduleNotification (Notification notification , Calendar myAlarmDate) {
        Intent notificationIntent = new Intent( this, MyNotificationPublisher. class ) ;
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID , 1 ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION , notification) ;
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( this, 0 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.set(AlarmManager.RTC_WAKEUP , myAlarmDate.getTimeInMillis() , pendingIntent) ;
    }

    private Notification getNotification (String placeName, String placeLocation) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder( this, default_notification_channel_id ) ;
        builder.setContentTitle( placeName ) ;
        builder.setContentText(placeLocation) ;
        builder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
        builder.setAutoCancel( true ) ;
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
        return builder.build() ;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
