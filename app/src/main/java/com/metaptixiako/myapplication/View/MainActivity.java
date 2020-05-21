package com.metaptixiako.myapplication.View;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.metaptixiako.myapplication.R;
import com.metaptixiako.myapplication.ViewModel.MainActivityViewModel;

import java.lang.ref.WeakReference;


public class MainActivity extends BaseActivity {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mViewModel.hanldeResponse(requestCode, resultCode, data);
    }

    @Override
    public void doneAction (final String utteranceId) {
        mViewModel.askUser(mViewModel.REQ_CODE_SPEECH_CONFIRMATION);
    }

    public void doneTalking(final String utteranceID) {
    }
}