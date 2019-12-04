package com.metaptixiako.myapplication.io;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.speech.RecognizerIntent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;

import com.metaptixiako.myapplication.Utils.Command;

public class NavigationKeyWords {

    WeakReference<NavigationKeyWordsListener> mListener;


    public void findKeyword(Intent data, Command.SupportedActions[] keywords) {
        ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
        Command.SupportedActions action = Command.getSpokenAction(results);
        if (action == Command.SupportedActions.nan) {
            mListener.get().failed();
        } else if (!Arrays.asList(keywords).contains(action)) {
            mListener.get().failed();
        } else {
            mListener.get().successFound(action);
        }
    }

    public void setlistener(NavigationKeyWordsListener mListener) {
        this.mListener = new WeakReference<>(mListener);
    }
}

