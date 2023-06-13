package com.lisi4ka.validation;

import com.lisi4ka.models.Coordinates;
import com.lisi4ka.models.Human;
import com.lisi4ka.utils.PackagedCommand;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import static com.lisi4ka.utils.Checker.checkDate;
import static com.lisi4ka.utils.CityReader.*;

public class AddValid implements Validation, Serializable {
    public PackagedCommand[] valid(String[] commandText) {
        if (commandText.length == 1 || commandText.length == 2) {
            if (commandText.length == 1) {
                StringBuilder args = new StringBuilder();
                args.append(inputName());
                args.append(",");
                Coordinates coordinates = inputCoordinates();
                args.append(coordinates.getX());
                args.append(",");
                args.append(coordinates.getY());
                args.append(",");
                args.append(inputArea());
                args.append(",");
                args.append(inputPopulation());
                args.append(",");
                args.append(inputMetersAboveSeaLevel());
                args.append(",");
                args.append(inputClimate().ordinal() + 1);
                args.append(",");
                try {
                    args.append(inputGovernment().ordinal() + 1);
                } catch (NullPointerException ex) {
                    args.append("null");
                }
                args.append(",");
                try {
                    args.append(inputStandardOfLiving().ordinal() + 1);
                } catch (NullPointerException ex) {
                    args.append("null");
                }
                args.append(",");
                Human governor = inputGovernor();
                if (governor == null) {
                    args.append("null");
                    args.append(",");
                    args.append("null");
                } else {
                    args.append(governor.age());
                    args.append(",");
                    args.append(governor.getStringBirthday());
                }
                return new PackagedCommand[]{new PackagedCommand(commandText[0], args.toString())};
            } else {
                String[] str = commandText;
                commandText = commandText[1].split(",");
                boolean flag = true;
                StringBuilder args = new StringBuilder();
                args.append(commandText[0]);
                args.append(",");
                try {
                    if (Double.parseDouble(commandText[1]) > -25) {
                        args.append(Double.parseDouble(commandText[1]));
                    } else {
                        flag = false;
                    }
                    args.append(",");
                    args.append(Float.parseFloat(commandText[2]));
                    args.append(",");
                    if (Double.parseDouble(commandText[3]) > 0) {
                        args.append(Double.parseDouble(commandText[3]));
                    } else {
                        flag = false;
                    }
                    args.append(",");
                    if (Long.parseLong(commandText[4]) > 0) {
                        args.append(Long.parseLong(commandText[4]));
                    } else {
                        flag = false;
                    }
                    args.append(",");
                    if (Integer.parseInt(commandText[5]) > -432 && Integer.parseInt(commandText[5]) < 8849) {
                        args.append(Integer.parseInt(commandText[5]));
                    } else {
                        flag = false;
                    }
                    args.append(",");
                    if (Integer.parseInt(commandText[6]) > 0 && Integer.parseInt(commandText[6]) < 5) {
                        args.append(Integer.parseInt(commandText[6]));
                    } else {
                        flag = false;
                    }
                    args.append(",");
                    if ("null".equals(commandText[7])) {
                        args.append("null");
                    } else {
                        if (Integer.parseInt(commandText[7]) > 0 && Integer.parseInt(commandText[7]) < 5) {
                            args.append(Integer.parseInt(commandText[7]));
                        } else {
                            flag = false;
                        }
                    }
                    args.append(",");
                    if ("null".equals(commandText[8])) {
                        args.append("null");
                    } else {
                        if (Integer.parseInt(commandText[8]) > 0 && Integer.parseInt(commandText[8]) < 6) {
                            args.append(Integer.parseInt(commandText[8]));
                        } else {
                            flag = false;
                        }
                    }
                }catch (Exception ex){
                    throw new IllegalArgumentException("Illegal arguments for command \"add\"!\n");
                }
                args.append(",");
                if ("null".equals(commandText[9]) || "null".equals(commandText[10])) {
                    args.append("null");
                    args.append(",");
                    args.append("null");
                } else {
                    try {
                        if (Long.parseLong(commandText[9]) > 0) {
                            args.append(Long.parseLong(commandText[9]));
                        } else {
                            flag = false;
                        }
                    }catch (Exception ex){
                        throw new IllegalArgumentException("Illegal arguments for command \"add\"!\n");
                    }
                    args.append(",");
                    SimpleDateFormat DateFormat = new SimpleDateFormat("dd.MM.yyyy");
                    try {
                        args.append(DateFormat.format(checkDate(commandText[10])));
                    } catch (Exception ex) {
                        throw new IllegalArgumentException("Illegal arguments for command \"add\"!\n");
                    }
                }
                if (flag) {
                    return new PackagedCommand[]{new PackagedCommand(str[0], args.toString())};
                }
                throw new IllegalArgumentException("Illegal arguments for command \"add\"!\n");
            }
        } else {
            throw new IllegalArgumentException("Illegal arguments for command \"add\"!\n");
        }
    }
}
