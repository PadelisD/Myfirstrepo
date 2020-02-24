package com.metaptixiako.myapplication.Service;

public class ProcessActions {
    private BaseCommand command;

    public ProcessActions(BaseCommand command){
        this.command = command;
    }

    public String[] getCommands() {
        return this.command.getActions();
    }
}


