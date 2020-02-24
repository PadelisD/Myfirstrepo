package com.metaptixiako.myapplication.Service;

public class DeclineCommand implements BaseCommand {
    public String[] getActions() {
        return new String[]{"no", "nope"};
    }
}
