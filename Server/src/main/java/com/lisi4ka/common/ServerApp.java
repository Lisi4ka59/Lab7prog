package com.lisi4ka.common;

import com.lisi4ka.utils.*;
import com.lisi4ka.validation.*;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;


import static com.lisi4ka.utils.Serializer.serialize;
import static java.lang.Thread.sleep;

public class ServerApp {
    public static CityLinkedList cities = new CityLinkedList();

    private CommandMap createCommandMap() {
        CommandMap commandMap = new CommandMap();
        commandMap.put("add", new AddValid());
        commandMap.put("add_if_min", new AddIfMinValid());
        commandMap.put("clear", new ClearValid());
        commandMap.put("execute_script", new ExecuteScriptValid());
        commandMap.put("exit", new ExitValid());
        commandMap.put("help", new HelpValid());
        commandMap.put("info", new InfoValid());
        commandMap.put("print_descending", new PrintDescendingValid());
        commandMap.put("print_field_ascending_standard_of_living", new PrintFieldAscendingStandardOfLivingValid());
        commandMap.put("print_unique_standard_of_living", new PrintUniqueStandardOfLivingValid());
        commandMap.put("remove_by_id", new RemoveByIdValid());
        commandMap.put("remove_first", new RemoveFirstValid());
        commandMap.put("remove_head", new RemoveHeadValid());
        commandMap.put("show", new ShowValid());
        commandMap.put("update", new UpdateIdValid());
        commandMap.put("login", new LogIn());
        commandMap.put("logout", new LogOut());
        commandMap.put("register", new Register());
        return commandMap;
    }

    public static HashMap<String, String> users = new HashMap<>();
    public volatile static HashMap<String, Queue<String>> queueMap = new HashMap<>();
    ForkJoinPool forkJoinPool = new ForkJoinPool();
    ExecutorService executor = Executors.newFixedThreadPool(10);
    public static ExecutorService invokeExecutor = Executors.newCachedThreadPool();
    public static HashMap<String, String> logins = new HashMap<>();
    public volatile static Queue<AnswerKey> answerKeyQueue = new LinkedList<>();

    private void run() {
        try {
            System.out.println("Server started");

            Invoker invoker = new Invoker(cities);
            String firstAnswer = invoker.run("load", "nobody");
            InetAddress host = InetAddress.getByName("localhost");
            Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(host, 9857));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            SelectionKey key;
            Runnable basic = () ->
            {
                MegaAnswerManager megaAnswerManager = new MegaAnswerManager();
                //megaAnswerManager.fork();
                forkJoinPool.invoke(megaAnswerManager);
            };
            Thread thread = new Thread(basic);
            thread.start();
            while (true) {
                if (selector.select() <= 0)
                    continue;
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while (iterator.hasNext()) {
                    key = iterator.next();
                    assert key != null;
                    if (key.isValid() && key.isAcceptable()) {
                        SocketChannel sc = serverSocketChannel.accept();
                        sc.configureBlocking(false);
                        sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                        System.out.println("Connection Accepted: " + sc.getRemoteAddress());
                        //synchronized (queueMap){
                        queueMap.put(sc.getRemoteAddress().toString(), new LinkedList<>());
                        queueMap.get(sc.getRemoteAddress().toString()).add("CommandMap");
                    //}
                    }
                    if (key.isValid() && key.isReadable()) {
                        RequestManager requestManager = new RequestManager(key);
                        Thread thread1 = new Thread(requestManager);
                        executor.submit(thread1);
                        sleep(1);
                    }
                    if (key.isValid() && key.isWritable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        String answer;
                        PackagedResponse packagedResponse;
                            if (queueMap.get(socketChannel.getRemoteAddress().toString()) != null) {
                                if (!queueMap.get(socketChannel.getRemoteAddress().toString()).isEmpty()) {
                                    answer = queueMap.get(socketChannel.getRemoteAddress().toString()).poll();
                                    if ("CommandMap".equals(answer)) {
                                        packagedResponse = new PackagedResponse(createCommandMap(), String.format("Connection accepted!\n%s\nType \"login\" to sign in or \"register\" to sign up", firstAnswer));
                                    } else {
                                        packagedResponse = new PackagedResponse(answer, ResponseStatus.OK);
                                    }
                                } else {
                                    iterator.remove();
                                    continue;
                                }
                            } else {
                                iterator.remove();
                                continue;
                            }
                        synchronized (answerKeyQueue) {
                            AnswerKey answerKey = new AnswerKey(packagedResponse, socketChannel);
                            answerKeyQueue.add(answerKey);
                        }
                    }
                    iterator.remove();
                }

            }
        } catch (Exception ex) {
            //ex.printStackTrace(System.out);
            System.out.println("This port is already in use!");
        }
    }

    public static void serverRun() {
        ServerApp serverApp = new ServerApp();
        serverApp.run();
    }
}