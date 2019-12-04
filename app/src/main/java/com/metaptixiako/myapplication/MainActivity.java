package com.metaptixiako.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;

import com.metaptixiako.myapplication.io.NavigationKeyWords;
import com.metaptixiako.myapplication.io.NavigationKeyWordsListener;

import java.util.ArrayList;
import java.util.Locale;

import static com.metaptixiako.myapplication.Utils.Command.*;

public class MainActivity extends AppCompatActivity {
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private final int REQ_CODE_SPEECH_CONFIRMATION = 150;
    private NavigationKeyWords nav;
//    private TextToSpeech t1;
    String toSpeak = "Are you sure?";
    private TextView voiceInput;
    private Button speakButton;

    private static SupportedActions[] supportedKeyWords() {
        SupportedActions[] actions = {SupportedActions.navigate};
        return actions;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        speakButton = (Button) findViewById(R.id.btnSpeak);


        nav = new NavigationKeyWords();
        nav.setlistener(new NavigationKeyWordsListener() {
            @Override
            public void successFound(SupportedActions action) {
                if (action == SupportedActions.navigate) {
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
                    startNavigation();
                }
            }

            @Override
            public void failed() {
                int x = 0;
            }
        });
        speakButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                askSpeechInput(REQ_CODE_SPEECH_INPUT);
            }
        });
    }

    // Receiving speech input

    @Override
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

    // Showing google speech input dialog

    //region Private Methods

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

    private void startNavigation() {
        Intent intent = new Intent(this, Navigation.class);
        startActivityForResult(intent, 2);
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
    //endregion
}
