package com.lisi4ka.validation;

import com.lisi4ka.utils.PackagedCommand;

import java.io.Serializable;

public class ClearValid implements Validation, Serializable {
    public PackagedCommand[] valid(String[] commandText){
        if (commandText.length == 1){
            return new PackagedCommand[] {new PackagedCommand(commandText[0], null)};
        }
        else {
            throw new IllegalArgumentException("Command \"clear\" takes no arguments!\n");
        }
    }
}
