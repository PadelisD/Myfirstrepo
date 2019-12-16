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
    private final int REQ_CODE_SPEECH_DESTINATION = 200;
    private TextToSpeech t1;
    private NavigationKeyWords nav;
    String toSpeak = "Are you sure?";
    private final String destinationConstant = "declareDestination";
    private final String validateDestinationConstant = "validateDestination";

    private static Command.SupportedActions[] supportedKeyWords() {
        Command.SupportedActions[] actions = {Command.SupportedActions.goBack};
        return actions;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        speakTextWithAction("start navigation. Where do you want to navigate?", destinationConstant);
    }

    private void speakTextWithAction(final String textToSpeak, final String utteranceID) {
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                    t1.setSpeechRate(1.0f);
                    t1.setOnUtteranceProgressListener(mProgressListener);
                    HashMap<String, String> myHashmap = new HashMap<String, String>();
                    myHashmap.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceID);
                    t1.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, myHashmap);
                }
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

    private void validateUsersRequest(String usersInput) {
        String toSpeak1 = "Are you sure you want to navigate to " + usersInput + "?";
        HashMap<String, String> myHashmap = new HashMap<String, String>();
        myHashmap.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, validateDestinationConstant);
        t1.speak(toSpeak1, TextToSpeech.QUEUE_FLUSH, myHashmap);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_DESTINATION: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //ask for data
                    validateUsersRequest(result.get(0));
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
            if (utteranceId.equals(destinationConstant)) {
                askSpeechInput(REQ_CODE_SPEECH_DESTINATION);
                nav = new NavigationKeyWords();
                nav.setlistener(new NavigationKeyWordsListener() {
                    @Override
                    public void successFound(Command.SupportedActions action) {

                    }

                    @Override
                    public void failed() {
                    }
                });
            } else if (utteranceId.equals(validateDestinationConstant)) {
                askSpeechInput(REQ_CODE_SPEECH_CONFIRMATION);
                nav = new NavigationKeyWords();
                nav.setlistener(new NavigationKeyWordsListener() {
                    @Override
                    public void successFound(Command.SupportedActions action) {
                        if (action == Command.SupportedActions.accept) {
                            // TODO: 16/12/2019
                        } else if (action == Command.SupportedActions.decline) {
                            //loop
                            speakTextWithAction("Where do you want to navigate?", destinationConstant);
                        }
                    }

                    @Override
                    public void failed() {
                    }
                });
            }
        }
    };
}
//endregion
