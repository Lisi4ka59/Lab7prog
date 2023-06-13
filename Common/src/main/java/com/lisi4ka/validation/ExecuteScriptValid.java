package com.lisi4ka.validation;

import com.lisi4ka.utils.PackagedCommand;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ExecuteScriptValid implements Validation, Serializable {
    public static final int addLines = 12;
    ArrayList<String> exeRecursion = new ArrayList<>();

    public PackagedCommand[] valid(String[] commandText) throws IllegalArgumentException {
        ArrayList<PackagedCommand> commandsToRun = new ArrayList<>();
        if (commandText.length == 2) {
            String fileName = commandText[1];
            if (exeRecursion.contains(fileName)) {
                exeRecursion.clear();
                throw new IllegalArgumentException(String.format("Can not execute script from file %s, because it can be very dangerous as it can cause recursion!\n", fileName));
            }
            exeRecursion.add(fileName);
            ArrayList<String> lines = new ArrayList<>();
            try {
                FileInputStream fStream = new FileInputStream(fileName);
                BufferedReader br = new BufferedReader(new InputStreamReader(fStream));
                String line;

                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (!"".equals(line)) {
                        lines.add(line);
                    }
                }
            } catch (SecurityException e) {
                exeRecursion.clear();
                throw new IllegalArgumentException(String.format("Do not have sufficient rights to execute file %s\n", fileName));
            } catch (IOException e) {
                exeRecursion.clear();
                throw new IllegalArgumentException(String.format("File \"%s\" does not exist\n", fileName));
            }
            for (int i = 0; i < lines.size(); i++) {
                try {
                    String[] line = lines.get(i).split(" ");
                    if (line.length >= 1 && "execute_script".equals(line[0])) {
                        commandsToRun.addAll(Arrays.stream(valid(line)).toList());
                    } else if (line.length == 1 && "add".equals(line[0])) {
                        try {
                            int j = i + addLines;
                            i++;
                            StringBuilder lineArgs = new StringBuilder();
                            for (; i < j; i++) {
                                lineArgs.append(lines.get(i));
                                if (i != j - 1) {
                                    lineArgs.append(",");
                                }
                            }
                            i--;
                            commandsToRun.add(new PackagedCommand(line[0], lineArgs.toString()));
                        } catch (Exception ex) {
                            System.out.printf("Illegal arguments for command %s!\n", line[0]);
                            System.out.println(ex.getMessage());
                            throw new IllegalArgumentException(ex.getMessage());
                        }
                    } else if (line.length == 2 && "update_id".equals(line[0])) {
                        try {
                            int j = i + addLines;
                            i++;
                            StringBuilder lineArgs = new StringBuilder().append(line[1]);
                            for (; i < j; i++) {
                                lineArgs.append(lines.get(i));
                                if (i != j - 1) {
                                    lineArgs.append(",");
                                }
                            }
                            i--;
                            commandsToRun.add(new PackagedCommand(line[0], lineArgs.toString()));
                        } catch (Exception ex) {
                            System.out.printf("Illegal arguments for command %s!\n", line[0]);
                            System.out.println(ex.getMessage());
                            throw new IllegalArgumentException(ex.getMessage());
                        }
                    } else if (line.length == 1) {
                        commandsToRun.add(new PackagedCommand(line[0], null));
                    } else if (line.length == 2) {
                        commandsToRun.add(new PackagedCommand(line[0], line[1]));
                    }
                } catch (IllegalArgumentException ex) {
                    exeRecursion.clear();
                    throw new IllegalArgumentException(ex.getMessage());
                }
            }
            exeRecursion.remove(fileName);
            return commandsToRun.toArray(PackagedCommand[]::new);
        } else
            throw new
                    IllegalArgumentException("Invalid arguments for command execute_script!\n");
    }
}
