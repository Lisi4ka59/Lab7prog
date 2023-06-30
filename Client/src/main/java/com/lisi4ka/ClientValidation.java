package com.lisi4ka;

import com.lisi4ka.utils.PackagedCommand;
import com.lisi4ka.validation.*;

import java.util.NoSuchElementException;
import java.util.Scanner;

import static com.lisi4ka.ClientApp.commandMap;
import static com.lisi4ka.ClientApp.loginFlag;

public class ClientValidation {
    public static PackagedCommand[] validation() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            try {
                String[] commandText = scanner.nextLine().trim().split(" ");
                if ("".equals(commandText[0])) {
                    continue;
                }
                PackagedCommand[] request = null;
                if ("login".equals(commandText[0]) && loginFlag){
                    System.out.println("First you need to log out!");
                    continue;
                }else if ("login".equals(commandText[0]) || loginFlag) {
                    try {
                        if (commandMap.containsKey(commandText[0])) {
                            Validation valid = commandMap.get(commandText[0]);
                            request = valid.valid(commandText);
                        } else {
                            System.out.println("Unknown command! Type \"help\" to open command list");
                        }
                    } catch (IllegalArgumentException ex) {
                        System.out.print(ex.getMessage());
                    }
                }else {
                    System.out.println("Type \"login\" to sign in or \"register\" to sign up");
                    continue;
                }
                if ("login".equals(commandText[0])){
                    loginFlag = true;
                } else if ("logout".equals(commandText[0])){
                    loginFlag = false;
                }
                if (request != null){
                    return request;
                }
            } catch (NoSuchElementException e) {
                System.out.println("Program termination");
                System.exit(0);
            }
        }
    }
}
