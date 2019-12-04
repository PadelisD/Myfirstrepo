package com.metaptixiako.myapplication.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Command {

    public enum SupportedActions {
        navigate,
        accept,
        decline,
        goBack,
        nan
    }

    public static HashMap<SupportedActions, String[]> givenkeyWords = new HashMap<SupportedActions, String[]>();

    static {
        String[] navigationActionsArray = {"navigate", "go", "move to"};
        givenkeyWords.put(SupportedActions.navigate, navigationActionsArray);
        String[] acceptActionsArray = {"yes", "sure", "ok"};
        givenkeyWords.put(SupportedActions.accept, acceptActionsArray);
        String[] declineActionsArray = {"no", "nope"};
        givenkeyWords.put(SupportedActions.decline, declineActionsArray);
        String[] goBackActionsArray = {"stop", "return", "back"};
        givenkeyWords.put(SupportedActions.goBack, goBackActionsArray);
    }

    //
    public static SupportedActions[] confirmationKeyWords() {
        SupportedActions[] actions = {SupportedActions.accept};
        return actions;
    }

    public static SupportedActions getSpokenAction(ArrayList<String> spokenCommands) {
        for (String command : spokenCommands) {
            for (HashMap.Entry<SupportedActions, String[]> entry : givenkeyWords.entrySet()) {
                String[] supportedCommands = entry.getValue();
                if (Arrays.asList(supportedCommands).contains(command)) {
                    return entry.getKey();
                }
            }
        }
        return SupportedActions.nan;
    }
}