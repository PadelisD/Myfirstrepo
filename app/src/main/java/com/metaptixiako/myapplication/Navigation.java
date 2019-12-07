package com.metaptixiako.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.speech.RecognizerIntent;

import com.metaptixiako.myapplication.Utils.Command;
import com.metaptixiako.myapplication.io.NavigationKeyWords;
import com.metaptixiako.myapplication.io.NavigationKeyWordsListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import static com.metaptixiako.myapplication.Utils.Command.confirmationKeyWords;

public class Navigation extends AppCompatActivity {
    private TextView but1, lv;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private final int REQ_CODE_SPEECH_CONFIRMATION = 150;
    private TextToSpeech t1;
    private NavigationKeyWords nav;
    String toSpeak = "Are you sure?";

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

                    t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if (status != TextToSpeech.ERROR) {
                                t1.setLanguage(Locale.UK);
                                t1.setOnUtteranceProgressListener(mProgressListener);
                                t1.setSpeechRate(1.0f);
                                HashMap<String,String> myHashmap = new HashMap<String, String>();
                                myHashmap.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "some message");
                                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH,myHashmap);
                            }
                        }
                    });
                }
                if (action == Command.SupportedActions.accept) {
                    finish();
                }
            }



            @Override
            public void failed() {int x=0;
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
    private UtteranceProgressListener mProgressListener = new UtteranceProgressListener() {
        @Override
        public void onStart(String utteranceId) {
        } // Do nothing

        @Override
        public void onError(String utteranceId) {
        } // Do nothing.

        @Override
        public void onDone(String utteranceId) {
            askSpeechInput(REQ_CODE_SPEECH_CONFIRMATION);
        }
    };

}