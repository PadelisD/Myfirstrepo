package com.metaptixiako.myapplication.io;

import android.content.Intent;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class NavigationKeyWords {

    WeakReference<NavigationKeyWordsListener> mListener;

    enum Keywords {
        navigate, goTO
    }

   public void findKeyword(Intent data) {
        mListener.get().successFound();
        mListener.get().failed();
    }

    public void setlistener(NavigationKeyWordsListener mListener) {
        this.mListener = new WeakReference<>(mListener);
    }
}

