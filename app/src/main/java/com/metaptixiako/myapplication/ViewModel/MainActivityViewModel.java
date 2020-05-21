package com.metaptixiako.myapplication.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;

import com.metaptixiako.myapplication.View.NavigationActivity;
import com.metaptixiako.myapplication.ViewModel.Utils.Command;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static com.metaptixiako.myapplication.ViewModel.Utils.Command.confirmationKeyWords;

public class MainActivityViewModel extends AndroidViewModel {

    public WeakReference<MainActivityViewModelListener> mListener;
    private NavigationKeyWords nav;
    private String toSpeak = "Are you sure?";
    public final int REQ_CODE_SPEECH_INPUT = 100;
    public final int REQ_CODE_SPEECH_CONFIRMATION = 150;
    private static Command.SupportedActions[] supportedKeyWords() {
        Command.SupportedActions[] actions = {Command.SupportedActions.navigate};
        return actions;
    }

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void hanldeResponse(int requestCode, int resultCode, Intent data) {
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

    public void askUser(int code) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 10000);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 10000);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        try {
            mListener.get().askSpeechInput(intent, code);
            nav = new NavigationKeyWords();
            nav.setlistener(new NavigationKeyWordsListener() {
                @Override
                public void successFound(Command.SupportedActions action) {
                    if (action == Command.SupportedActions.navigate) {
                        mListener.get().say(toSpeak, "");
                    }
                    if (action == Command.SupportedActions.accept) {
                        mListener.get().startActivityForClass(NavigationActivity.class);
                    } else if (action == Command.SupportedActions.decline) {
                        //nothing for now
                    }
                }
                @Override
                public void failed() {
                    int x = 0;
                }
            });
        } catch (ActivityNotFoundException a) {

        }
    }
}
