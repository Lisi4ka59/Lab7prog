package com.lisi4ka.validation;

import com.lisi4ka.utils.PackagedCommand;
import com.lisi4ka.utils.PasswordConverter;

import java.io.Serializable;

import static com.lisi4ka.utils.Checker.inputStringNotNull;
import static com.lisi4ka.utils.PackagedCommand.staticLogin;
import static com.lisi4ka.utils.PackagedCommand.staticPassword;

public class Register implements Validation, Serializable {
    @Override
    public PackagedCommand[] valid(String[] commandText) {
            String login = inputStringNotNull("Enter login: ");
            String password1 = PasswordConverter.convert(inputStringNotNull("Enter password: "));
            String password2 = PasswordConverter.convert(inputStringNotNull("Repeat password: "));
            if (!password1.equals(password2)){
                throw new IllegalArgumentException("Passwords mismatch! Try again");
            }
            staticLogin = login;
            staticPassword = password1;
            return new PackagedCommand[]{new PackagedCommand("register", login + "@" + password1)};
    }
}
