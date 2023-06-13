package com.lisi4ka.validation;

import com.lisi4ka.utils.PackagedCommand;

import java.io.Serializable;

public class AddIfMinValid implements Validation, Serializable {
    public PackagedCommand[] valid(String[] commandText) {
        if (commandText.length == 1) {
            AddValid addValid = new AddValid();
            String[] command = "add".split(" ");
            PackagedCommand args;
            try {
                args = addValid.valid(command)[0];
            } catch (Exception ex) {
                throw new IllegalArgumentException("Error while reading arguments for command \"add_if_min\"!\n");
            }
            return new PackagedCommand[]{new PackagedCommand(commandText[0], args.getCommandArguments())};
        } else if (commandText.length == 2) {
            AddValid addValid = new AddValid();
            String[] command = String.format("add %s", commandText[1]).split(" ");
            PackagedCommand args;
            try {
                args = addValid.valid(command)[0];
            } catch (Exception ex) {
                throw new IllegalArgumentException("Illegal arguments for command \"add_if_min\"!\n");
            }
            return new PackagedCommand[]{new PackagedCommand(commandText[0], args.getCommandArguments())};
        } else {
            throw new IllegalArgumentException("Illegal arguments for command \"add_if_min\"!\n");
        }
    }
}
