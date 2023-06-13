package com.lisi4ka.validation;

import com.lisi4ka.utils.PackagedCommand;

import java.io.Serializable;

import static com.lisi4ka.utils.Checker.inputLong;

public class UpdateIdValid implements Validation, Serializable {
    public PackagedCommand[] valid(String[] commandText) {
        if (commandText.length == 1) {
            long id = inputLong("Input city ID: ");
            return getPackagedCommand(commandText, id);
        } else if (commandText.length == 2) {
            long id;
            try {
                id = Long.parseLong(commandText[1]);
            } catch (Exception ex){
                throw new IllegalArgumentException("Illegal ID for command \"update\"!\n");
            }
            return getPackagedCommand(commandText, id);
        } else {
            throw new IllegalArgumentException("Illegal arguments for command \"update\"!\n");
        }
    }

    private PackagedCommand[] getPackagedCommand(String[] commandText, long id) {
        AddValid addValid = new AddValid();
        String[] command = "add".split(" ");
        PackagedCommand args;
        try {
            args = addValid.valid(command)[0];
        } catch (Exception ex) {
            throw new IllegalArgumentException("Error while reading arguments for command \"update\"!\n");
        }
        return new PackagedCommand[]{new PackagedCommand(commandText[0], String.format("%d,%s", id, args.getCommandArguments()))};
    }
}
