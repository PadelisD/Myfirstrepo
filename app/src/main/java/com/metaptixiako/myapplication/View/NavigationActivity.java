package com.metaptixiako.myapplication.View;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.speech.RecognizerIntent;

import com.metaptixiako.myapplication.R;
import com.metaptixiako.myapplication.ViewModel.MainActivityViewModelListener;
import com.metaptixiako.myapplication.ViewModel.NavigationActivityViewModel;
import com.metaptixiako.myapplication.ViewModel.Utils.Command;
import com.metaptixiako.myapplication.ViewModel.NavigationKeyWords;
import com.metaptixiako.myapplication.ViewModel.NavigationKeyWordsListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import static com.metaptixiako.myapplication.ViewModel.Utils.Command.confirmationKeyWords;

public class NavigationActivity extends BaseActivity {
    private TextView but1, lv;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private final int REQ_CODE_SPEECH_CONFIRMATION = 150;
    private final int REQ_CODE_SPEECH_DESTINATION = 200;
    private TextToSpeech t1;
    private NavigationKeyWords nav;
    // String toSpeak = "Are you sure?";
    private final String destinationConstant = "declareDestination";
    private final String validateDestinationConstant = "validateDestination";
    private NavigationActivityViewModel mViewModel;

    private static Command.SupportedActions[] supportedKeyWords() {
        Command.SupportedActions[] actions = {Command.SupportedActions.goBack};
        return actions;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        say("start navigation. Where do you want to navigate?", destinationConstant);
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

    public void validateUsersRequest(String usersInput) {
        String toSpeak1 = "Are you sure you want to navigate to " + usersInput + "?";
        say(toSpeak1, validateDestinationConstant);
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

    @Override
    public void doneAction(final String utteranceID) {
        if (utteranceID.equals(destinationConstant)) {
            askSpeechInput(REQ_CODE_SPEECH_DESTINATION);
            nav = new NavigationKeyWords();
            nav.setlistener( new NavigationKeyWordsListener() {
                @Override
                public void successFound(Command.SupportedActions action) {

                }

                @Override
                public void failed() {
                }
            });
        } else if (utteranceID.equals(validateDestinationConstant)) {
            askSpeechInput(REQ_CODE_SPEECH_CONFIRMATION);
            nav = new NavigationKeyWords();
            nav.setlistener(new NavigationKeyWordsListener() {
                @Override
                public void successFound(Command.SupportedActions action) {
                    if (action == Command.SupportedActions.accept) {
                        // TODO: 16/12/2019
                    } else if (action == Command.SupportedActions.decline) {
                        //loop
                        say("Where do you want to navigate?", destinationConstant);
                    }
                }

                @Override
                public void failed() {
                }
            });
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
                nav.setlistener( new NavigationKeyWordsListener() {
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
                            say("Where do you want to navigate?", destinationConstant);
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
