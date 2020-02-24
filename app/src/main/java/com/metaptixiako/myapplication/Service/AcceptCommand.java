package com.metaptixiako.myapplication.Service;

public class AcceptCommand implements BaseCommand {
//    @Override
    public String[] getActions() {
        return new String[]{"yes", "sure", "ok"};
    }
}
