package com.metaptixiako.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.metaptixiako.myapplication.io.NavigationKeyWords;
import com.metaptixiako.myapplication.io.NavigationKeyWordsListener;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView voiceInput;
    private Button speakButton;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private NavigationKeyWords nav;
    private static final String searchTerm = "navigation";
    private static final String searchTerm1 = "go to";
    private static final String searchTerm2 = "how to navigate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        speakButton = (Button) findViewById(R.id.btnSpeak);
        nav = new NavigationKeyWords();
        nav.setlistener(new NavigationKeyWordsListener() {
            @Override
            public void successFound() {
                startNavigation();
            }

            @Override
            public void failed() {

            }
        });
        speakButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                askSpeechInput();
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
                    nav.findKeyword(data);
                }
            }
        }
    }

    // Showing google speech input dialog

    //region Private Methods

    private void askSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 10000);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 10000);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startNavigation() {
        Intent intent = new Intent(this, Navigation.class);
        startActivityForResult(intent, 2);
    }

    //endregion
}
