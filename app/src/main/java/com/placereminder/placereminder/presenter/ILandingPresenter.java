package com.placereminder.placereminder.presenter;

import com.placereminder.placereminder.db.AppDatabase;
import com.placereminder.placereminder.ui.ILandingActivity;

public interface ILandingPresenter {
    void setView(ILandingActivity view, AppDatabase db);
    void retriveData();
}
