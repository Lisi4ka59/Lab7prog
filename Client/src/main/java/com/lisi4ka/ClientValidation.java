package com.lisi4ka;

import com.lisi4ka.utils.PackagedCommand;
import com.lisi4ka.validation.*;

import java.util.NoSuchElementException;
import java.util.Scanner;

import static com.lisi4ka.ClientApp.commandMap;

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
                try {
                    if (commandMap.containsKey(commandText[0])) {
                        Validation valid = commandMap.get(commandText[0]);
                        return valid.valid(commandText);
                    } else{
                        System.out.print("Unknown command! Type \"help\" to open command list");
                    }
                } catch (IllegalArgumentException ex) {
                    System.out.print(ex.getMessage());
                }
            } catch (NoSuchElementException e) {
                System.out.println("Program termination");
                System.exit(0);
            }
        }
    }
}
