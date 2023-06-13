package com.lisi4ka.validation;

import com.lisi4ka.utils.PackagedCommand;
import com.lisi4ka.utils.PasswordConverter;

import java.io.Serializable;

import static com.lisi4ka.utils.Checker.inputStringNotNull;
import static com.lisi4ka.utils.PackagedCommand.staticLogin;
import static com.lisi4ka.utils.PackagedCommand.staticPassword;

public class LogIn implements Validation, Serializable {
    public PackagedCommand[] valid(String[] commandText) {
        String login = inputStringNotNull("Enter login: ");
        String password = PasswordConverter.convert(inputStringNotNull("Enter password: "));
        staticLogin = login;
        staticPassword = password;
        return new PackagedCommand[]{new PackagedCommand("login", login + "@" + password)};
    }
}
