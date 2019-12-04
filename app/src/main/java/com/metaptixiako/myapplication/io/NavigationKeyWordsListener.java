package com.metaptixiako.myapplication.io;

import com.metaptixiako.myapplication.Utils.Command;

public interface NavigationKeyWordsListener {
    void successFound(Command.SupportedActions action);

    void failed();
}
