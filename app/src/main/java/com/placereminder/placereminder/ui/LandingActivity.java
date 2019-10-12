package com.placereminder.placereminder.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.placereminder.placereminder.R;
import com.placereminder.placereminder.adapter.ReminderAdapter;
import com.placereminder.placereminder.db.AppDatabase;
import com.placereminder.placereminder.db.PrefKey;
import com.placereminder.placereminder.db.PrefManager;
import com.placereminder.placereminder.model.Reminderdb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LandingActivity extends AppCompatActivity{

    RelativeLayout container;
    RecyclerView reminderList;
    FloatingActionButton addReminder;
    private int PLACE_PICKER_REQUEST = 1;
    AppDatabase appDatabase;
    List<Reminderdb> reminderdbArrayList;
    private ReminderAdapter adapter;
    LinearLayoutManager layoutManager;
    private Reminderdb reminder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // ButterKnife.bind(this);
        initView();


        addReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 startActivity(new Intent(LandingActivity.this,AddReminderActivity.class));

            }
        });
        retrieveReminderDataSet();
    }

    private void retrieveReminderDataSet(){

            reminderdbArrayList = appDatabase.reminderDao().getAll();
            Collections.reverse(reminderdbArrayList);
            adapter = new ReminderAdapter(this, reminderdbArrayList);
            layoutManager = new LinearLayoutManager(this);
            reminderList.setLayoutManager(layoutManager);
            reminderList.setAdapter(adapter);

    }

    private void initView() {
        appDatabase = AppDatabase.getAppDatabase(this);
        reminderList = findViewById(R.id.rv_reminder_list);
        addReminder = findViewById(R.id.fab_add_reminder);
        reminder = new Reminderdb();
        reminder.setPlaceName("Sample Data");
        reminder.setPlaceLocation("Sample Data");
        reminder.setDate("12/12/20");
        reminder.setTime("12.30 ap");
        reminder.setDescription("");
        appDatabase.reminderDao().addTradeHistory(reminder);
    }

}
