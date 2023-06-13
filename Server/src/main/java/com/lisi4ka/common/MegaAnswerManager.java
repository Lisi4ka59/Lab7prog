package com.lisi4ka.common;

import com.lisi4ka.utils.AnswerKey;
import com.lisi4ka.utils.PackagedResponse;

import java.nio.channels.SocketChannel;
import java.util.concurrent.RecursiveAction;

import static com.lisi4ka.common.ServerApp.answerKeyQueue;

public class MegaAnswerManager extends RecursiveAction {
    static volatile int threads = 0;
    @Override
    protected void compute() {
        int prom = threads;
        threads = prom + 1;
        if (threads < 2) {
            MegaAnswerManager megaAnswerManager = new MegaAnswerManager();
            megaAnswerManager.fork();
            megaAnswerManager.fork();
        } else {
            try {
                while (true) {
                    synchronized (answerKeyQueue) {
                        if (!answerKeyQueue.isEmpty()) {
                            AnswerKey answerKey;
                            answerKey = answerKeyQueue.poll();
                            if (answerKey != null && answerKey.key != null && answerKey.answer != null) {
                                SocketChannel key = answerKey.key;
                                PackagedResponse answer = answerKey.answer;
                                AnswerManager answerManager = new AnswerManager(key, answer);
                                answerManager.run();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                //e.printStackTrace(System.out);
            }
        }
    }
}
