package com.metaptixiako.myapplication.ViewModel;

import android.content.Intent;

public interface MainActivityViewModelListener {
    void say(String message);
    void startNavigationActivity();
//    void navigateToActivity(Class activity);
    void askSpeechInput(Intent intent, int code);
}
