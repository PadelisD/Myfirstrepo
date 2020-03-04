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


public class MainActivity extends BaseActivity { //implements MainActivityViewModelListener {

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
    public void doneAction () {
        mViewModel.askUser(mViewModel.REQ_CODE_SPEECH_CONFIRMATION);
    }

    @Override
    public void startActivityForClass() {
        Intent intent = new Intent(this, NavigationActivity.class);
        startActivityForResult(intent, 3);
    }
}
