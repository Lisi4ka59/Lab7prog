package com.lisi4ka.utils;

import java.io.Serializable;

public class PackagedCommand implements Serializable{
        private final String commandName;
        private final String commandArguments;
        private String login;
        private String password;
    public static String staticLogin = "";
    public static String staticPassword = "";
        public PackagedCommand(String name, String args){
            commandName = name;
            commandArguments = args;
            login = staticLogin;
            password = staticPassword;
        }
        public String getCommandName(){
            return commandName;
        }
        public String getCommandArguments(){
            return commandArguments;
        }
        public String getLogin(){return  login;}
}
