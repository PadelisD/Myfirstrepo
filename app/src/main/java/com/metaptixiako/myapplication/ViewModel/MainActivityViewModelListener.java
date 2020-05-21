package com.metaptixiako.myapplication.ViewModel;

import android.content.Intent;

public interface MainActivityViewModelListener {
    void say(final String message, final String utteranceID);
    void startActivityForClass(Class className);
    void validateUsersRequest(String usersInput);
    void askSpeechInput(Intent intent, int code);
}
