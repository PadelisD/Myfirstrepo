package com.metaptixiako.myapplication.Service;

public class GoBackCommand implements BaseCommand {
    public String[] getActions() {
        return new String[]{"stop", "return", "back"};
    }
}
