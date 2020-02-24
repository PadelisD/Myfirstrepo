package com.metaptixiako.myapplication.Service;

public class NavigateCommand implements BaseCommand {
    public String[] getActions() {
        return new String[]{"navigate", "go", "move to"};
    }
}
