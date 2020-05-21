package com.metaptixiako.myapplication.View;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.metaptixiako.myapplication.R;
import com.metaptixiako.myapplication.ViewModel.MainActivityViewModel;
import com.metaptixiako.myapplication.ViewModel.MainActivityViewModelListener;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Locale;

public class BaseActivity extends AppCompatActivity implements MainActivityViewModelListener {

    private TextToSpeech t1;
    private TextView voiceInput;
//    private MainActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Showing google speech input dialog

    //region Private Methods
    private UtteranceProgressListener mProgressListener = new UtteranceProgressListener() {
        @Override
        public void onStart(String utteranceId) {
        } // Do nothing

        @Override
        public void onError(String utteranceId) {
        } // Do nothing.

        @Override
        public void onDone(String utteranceId) {
            doneAction(utteranceId);
        }

    };

    public void doneAction(final String utteranceId) {

    }

    @Override
    public void say(final String message, final String utteranceID) {
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                    t1.setOnUtteranceProgressListener(mProgressListener);
                    t1.setSpeechRate(1.0f);
                    HashMap<String, String> myHashmap = new HashMap<String, String>();
                    myHashmap.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceID);
                    t1.speak(message, TextToSpeech.QUEUE_FLUSH, myHashmap);
                }
            }
        });
    }

    public void startActivityForClass(Class className) {
        Intent intent = new Intent(this, className);
        startActivityForResult(intent, 0);
    }

    @Override
    public void askSpeechInput(Intent intent, int code) {
        startActivityForResult(intent, code);
    }

}