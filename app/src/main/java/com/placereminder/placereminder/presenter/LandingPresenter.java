package com.placereminder.placereminder.presenter;

import com.placereminder.placereminder.db.AppDatabase;
import com.placereminder.placereminder.model.Reminderdb;
import com.placereminder.placereminder.ui.ILandingActivity;

import java.util.List;

public class LandingPresenter implements ILandingPresenter{

    private ILandingActivity view;
    private Reminderdb reminder;
    AppDatabase appDatabase;
    private List<Reminderdb> reminderdbArrayList;


    public LandingPresenter(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    public void setView(ILandingActivity view,AppDatabase db) {
        this.view = view;
        appDatabase = db;
    }

    @Override
    public void retriveData() {
        reminder = new Reminderdb();
        reminder.setPlaceName("Sample Data");
        reminder.setPlaceLocation("Sample Data");
        reminder.setDate("12/12/20");
        reminder.setTime("12.30 ap");
        reminder.setDescription("");
        appDatabase.reminderDao().addTradeHistory(reminder);
        reminderdbArrayList = appDatabase.reminderDao().getAll();
        view.retrieveReminderDataSet(reminderdbArrayList);

    }
}
