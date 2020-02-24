package com.metaptixiako.myapplication.ViewModel;

import com.metaptixiako.myapplication.ViewModel.Utils.Command;

public interface NavigationKeyWordsListener {
    void successFound(Command.SupportedActions action);

    void failed();
}
