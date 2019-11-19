package com.metaptixiako.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.speech.RecognizerIntent;
import java.util.ArrayList;
import java.util.Locale;

public class Navigation extends AppCompatActivity {
    private TextView but1, lv;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private static final String searchTerm = "stop";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        askSpeechInput();
    }

    public void askSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 10000);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 10000);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean key = false;
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    key = result.contains(searchTerm);
//                    for (String str : result) {
//                        key = str.toLowerCase().contains(searchTerm.toLowerCase());
//                        if (key) {
//                            Log.w("myApp", "Found:" + str);
//                            break;
//                        }
//                    }
                    if (key) {
                        finish();
                    } else {
                        askSpeechInput();
                    }
                }
            }
        }
    }
}