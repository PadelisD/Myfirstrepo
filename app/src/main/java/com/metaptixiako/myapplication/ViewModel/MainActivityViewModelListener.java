package com.metaptixiako.myapplication.ViewModel;

import android.content.Intent;

public interface MainActivityViewModelListener {
    void say(final String message, final String utteranceID);
    void startActivityForClass(Class className);
//    void navigateToActivity(Class activity);
    void askSpeechInput(Intent intent, int code);
}
