package com.lisi4ka.validation;

import com.lisi4ka.utils.PackagedCommand;

import java.io.Serializable;

public class LogOut implements Validation, Serializable {
    @Override
    public PackagedCommand[] valid(String[] commandText) {
        return new PackagedCommand[]{new PackagedCommand("LogOut", "")};
    }
}
