package com.lisi4ka.common;

import com.lisi4ka.utils.PackagedResponse;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

import static com.lisi4ka.utils.Serializer.serialize;
import static java.lang.Thread.sleep;

public class AnswerManager implements Runnable {
    PackagedResponse answer;
    SocketChannel socketChannel;

    public AnswerManager(SocketChannel socketChannel, PackagedResponse answer){
        this.answer = answer;
        this.socketChannel = socketChannel;
    }
    @Override
    public void run() {
        try {
            sleep(10);
            socketChannel.write(ByteBuffer.wrap(serialize(answer)));
        } catch (Exception e) {
            //e.printStackTrace(System.out);
        }
    }
}
