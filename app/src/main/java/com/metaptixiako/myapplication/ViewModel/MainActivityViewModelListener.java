package com.metaptixiako.myapplication.ViewModel;

import android.content.Intent;

public interface MainActivityViewModelListener {
    void say(String message, final String utteranceID);
    void startActivityForClass(Class className);
    void askSpeechInput(Intent intent, int code);
}
