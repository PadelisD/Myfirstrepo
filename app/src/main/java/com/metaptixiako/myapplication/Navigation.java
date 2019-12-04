package com.metaptixiako.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.speech.RecognizerIntent;

import com.metaptixiako.myapplication.Utils.Command;
import com.metaptixiako.myapplication.io.NavigationKeyWords;
import com.metaptixiako.myapplication.io.NavigationKeyWordsListener;

import java.util.ArrayList;
import java.util.Locale;

import static com.metaptixiako.myapplication.Utils.Command.confirmationKeyWords;

public class Navigation extends AppCompatActivity {
    private TextView but1, lv;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private final int REQ_CODE_SPEECH_CONFIRMATION = 150;
    private static final String searchTerm = "stop";
    private NavigationKeyWords nav;

    private static Command.SupportedActions[] supportedKeyWords() {
        Command.SupportedActions[] actions = {Command.SupportedActions.goBack};
        return actions;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        askSpeechInput(REQ_CODE_SPEECH_INPUT);
        nav = new NavigationKeyWords();
        nav.setlistener(new NavigationKeyWordsListener() {
            @Override
            public void successFound(Command.SupportedActions action) {
                if (action == Command.SupportedActions.goBack) {
//                    t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
//                        @Override
//                        public void onInit(int status) {
//                            if (status != TextToSpeech.ERROR) {
//                                t1.setLanguage(Locale.UK);
//                                t1.setOnUtteranceProgressListener(mProgressListener);
//                                t1.setSpeechRate(1.0f);
//                                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
//                            }
//                        }
//                    });
//                }
//                if (action == SupportedActions.accept) {
                    finish();
                }
            }

            @Override
            public void failed() {
                int x = 0;
            }
        });
    }

    private void askSpeechInput(int code) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 10000);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 10000);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        try {
            startActivityForResult(intent, code);
        } catch (ActivityNotFoundException a) {

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    nav.findKeyword(data, supportedKeyWords());
                }
                break;
            }
            case REQ_CODE_SPEECH_CONFIRMATION: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    nav.findKeyword(data, confirmationKeyWords());
                }
                break;
            }
        }
    }
}