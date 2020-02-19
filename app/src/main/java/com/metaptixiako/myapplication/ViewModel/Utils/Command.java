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

    private static String[] navigationActionsArray = {"navigate", "go", "move to"};
    private static String[] acceptActionsArray = {"yes", "sure", "ok"};
    private static String[] declineActionsArray = {"no", "nope"};
    private static String[] goBackActionsArray = {"stop", "return", "back"};

    public static HashMap<SupportedActions, String[]> givenkeyWords = new HashMap<SupportedActions, String[]>();

    static {
        givenkeyWords.put(SupportedActions.navigate, navigationActionsArray);
        givenkeyWords.put(SupportedActions.accept, acceptActionsArray);
        givenkeyWords.put(SupportedActions.decline, declineActionsArray);
        givenkeyWords.put(SupportedActions.goBack, goBackActionsArray);
    }

    public static SupportedActions[] confirmationKeyWords() {
        SupportedActions[] actions = {SupportedActions.accept, SupportedActions.decline};
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