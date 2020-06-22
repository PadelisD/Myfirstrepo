package com.metaptixiako.myapplication.View;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;

import com.metaptixiako.myapplication.R;
import com.metaptixiako.myapplication.ViewModel.NavigationActivityViewModel;
import com.metaptixiako.myapplication.ViewModel.Utils.Command;
import com.metaptixiako.myapplication.ViewModel.NavigationKeyWords;
import com.metaptixiako.myapplication.ViewModel.NavigationKeyWordsListener;

import java.lang.ref.WeakReference;

public class NavigationActivity extends BaseActivity {
    private TextView but1, lv;
    private TextToSpeech t1;
    private NavigationKeyWords nav;
    // String toSpeak = "Are you sure?";
    private final String destinationConstant = "declareDestination";
    private final String validateDestinationConstant = "validateDestination";


    private NavigationActivityViewModel nViewModel;

    private static Command.SupportedActions[] supportedKeyWords() {
        Command.SupportedActions[] actions = {Command.SupportedActions.goBack};
        return actions;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        nViewModel = ViewModelProviders.of(this).get(NavigationActivityViewModel.class);
        nViewModel.mListener = new WeakReference(this);
        say("start navigation. Where do you want to navigate?", destinationConstant);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        nViewModel.hanldeResponse(requestCode, resultCode, data);
    }

    @Override
    public void doneAction(final String utteranceID) {
        if (utteranceID.equals(destinationConstant)) {
            nViewModel.askUser(nViewModel.REQ_CODE_SPEECH_DESTINATION);
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
            nViewModel.askUser(nViewModel.REQ_CODE_SPEECH_CONFIRMATION);
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
}
//endregion
