package com.lisi4ka.common;

import static com.lisi4ka.common.ServerApp.cities;
import static com.lisi4ka.common.ServerApp.queueMap;
import static com.lisi4ka.utils.CommandMap.byteBufferLimit;

public class InvokerManager implements Runnable {
    String socketChannel;
    String commandText;
    String user;

    public InvokerManager(String socketChannel, String commandText, String user) {
        this.commandText = commandText;
        this.socketChannel = socketChannel;
        this.user = user;
    }

    @Override
    public void run() {
        Invoker invoker = new Invoker(cities);
        String answer = invoker.run(commandText, user);
        int packageCount = answer.length() / byteBufferLimit +
                ((answer.length() % byteBufferLimit == 0) ? 0 : 1);
        for (int i = 0; i < packageCount; i++) {
            String smallAnswer;
            if (answer.length() > byteBufferLimit) {
                smallAnswer = answer.substring(0, byteBufferLimit);
                answer = answer.substring(byteBufferLimit);
            } else {
                smallAnswer = answer;
            }
            synchronized (queueMap) {
                //System.out.println(smallAnswer);
                queueMap.get(socketChannel).add(smallAnswer);
            }
        }
    }
}