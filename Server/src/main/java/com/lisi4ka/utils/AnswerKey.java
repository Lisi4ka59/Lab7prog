package com.lisi4ka.utils;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class AnswerKey {
    public PackagedResponse answer;
    //public SelectionKey key;
    public SocketChannel key;
    public AnswerKey(PackagedResponse answer, SocketChannel key){
        this.answer = answer;
        this.key = key;
    }
}
