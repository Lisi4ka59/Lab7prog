package com.lisi4ka.utils;

import java.io.Serializable;

public class PackagedResponse implements Serializable {
    private final String message;
    private CommandMap commandMap = null;

    public PackagedResponse(CommandMap commandMap, String message){
        this.message = message;
        this.status = ResponseStatus.Map;
        this.commandMap = commandMap;
    }
    public PackagedResponse(String message, ResponseStatus status){
        this.message = message;
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public CommandMap getCommandMap(){return commandMap;}
    public ResponseStatus status;
}

