package com.lisi4ka.validation;

import com.lisi4ka.utils.PackagedCommand;

import java.io.Serializable;

public class PrintFieldAscendingStandardOfLivingValid implements Validation, Serializable {
    public PackagedCommand[] valid(String[] commandText){
        if (commandText.length == 1){
            return new PackagedCommand[]{new PackagedCommand(commandText[0], null)};
        }
        else {
            throw new IllegalArgumentException("Command \"print_field_ascending_standard_of_living\" takes no arguments!\n");
        }
    }
}
