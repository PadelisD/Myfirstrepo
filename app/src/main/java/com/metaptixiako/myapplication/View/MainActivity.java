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


public class MainActivity extends AppCompatActivity implements MainActivityViewModelListener {

    private TextToSpeech t1;
    private TextView voiceInput;
    private Button speakButton;
    private MainActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        speakButton = (Button) findViewById(R.id.btnSpeak);
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mViewModel.mListener = new WeakReference(this);
        speakButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mViewModel.askUser(mViewModel.REQ_CODE_SPEECH_INPUT);
            }
        });
    }

    // Receiving speech input

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mViewModel.hanldeResponse(requestCode, resultCode, data);
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
            mViewModel.askUser(mViewModel.REQ_CODE_SPEECH_CONFIRMATION);
        }
    };

    @Override
    public void say(final String message) {
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                    t1.setOnUtteranceProgressListener(mProgressListener);
                    t1.setSpeechRate(1.0f);
                    HashMap<String, String> myHashmap = new HashMap<String, String>();
                    myHashmap.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "some message");
                    t1.speak(message, TextToSpeech.QUEUE_FLUSH, myHashmap);
                }
            }
        });
    }

    @Override
    public void startNavigationActivity() {
        Intent intent = new Intent(this, Navigation.class);
        startActivityForResult(intent, 2);
    }

    @Override
    public void askSpeechInput(Intent intent, int code) {

        startActivityForResult(intent, code);
    }

    //endregion
}
