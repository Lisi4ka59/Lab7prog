package com.lisi4ka.utils;

import com.lisi4ka.validation.Validation;

import java.io.Serializable;
import java.util.HashMap;

public class CommandMap extends HashMap<String, Validation> implements Serializable{
    public static final int byteBufferLimit = 2000;
    public CommandMap(){
    }
}
