package com.lisi4ka.common;

import com.lisi4ka.utils.BdManager;
import com.lisi4ka.utils.PackagedCommand;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Base64;

import static com.lisi4ka.common.ServerApp.*;

public class RequestManager implements Runnable {
    SelectionKey key;

    public RequestManager(SelectionKey key) {
        this.key = key;
    }

    @Override
    public void run() {
        try {
            SocketChannel sc = (SocketChannel) key.channel();
            if (!users.containsKey(sc.getRemoteAddress().toString())){
                users.put(sc.getRemoteAddress().toString(), "");
            }
            StringBuilder stringBuilder =new StringBuilder();
            ByteBuffer bb = ByteBuffer.allocate(8192);
            boolean flag = true;
            try {
                sc.read(bb);
            } catch (SocketException | EOFException ex) {
                sc.close();
                flag = false;
                System.out.print("Client close connection!\nServer will keep running\nTry running another client to re-establish connection\n");
            }
            if (flag) {
                String[] result = new String(bb.array()).trim().split("rO0");
                for (int i = 1; i < result.length; i++){
                    byte[] data = Base64.getDecoder().decode("rO0" + result[i]);
                    PackagedCommand packagedCommand = null;
                    try {
                        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
                        packagedCommand = (PackagedCommand) ois.readObject();
                        ois.close();
                    } catch (EOFException | ClassNotFoundException e) {
                        e.printStackTrace(System.out);
                    }
                    if (packagedCommand != null) {
                        if ("LogOut".equals(packagedCommand.getCommandName())) {
                            users.remove(sc.getRemoteAddress().toString());
                        } else if ("register".equals(packagedCommand.getCommandName())) {
                            if (!logins.containsKey(packagedCommand.getLogin())){
                                String[] logpswd = packagedCommand.getCommandArguments().split("@");
                                BdManager bdManager = new BdManager();
                                stringBuilder.append(bdManager.loginInsert(logpswd)/*loginInsert(logpswd)*/);
                            } else{
                                stringBuilder.append(String.format("User with login \"%s\" is already exist!", packagedCommand.getLogin()));
                            }
                        } else {
                            if ("".equals(users.get(sc.getRemoteAddress().toString()))) {
                                String[] logpswd = packagedCommand.getCommandArguments().split("@");
                                if (logins.containsKey(logpswd[0])) {
                                    if (logpswd[1].equals(logins.get(logpswd[0]))) {
                                        users.put(sc.getRemoteAddress().toString(), logpswd[0]);
                                    } else {
                                        stringBuilder.append(String.format("Password for login \"%s\" incorrect!", logpswd[0]));
                                    }
                                } else{
                                    stringBuilder.append(String.format("Login \"%s\" does not exist", logpswd[0]));
                                }
                            } else if (packagedCommand.getLogin().equals(users.get(sc.getRemoteAddress().toString()))) {
                                InvokerManager invokerManager;
                                if (packagedCommand.getCommandArguments() == null) {
                                    invokerManager = new InvokerManager(sc.getRemoteAddress().toString(), packagedCommand.getCommandName(), users.get(sc.getRemoteAddress().toString()));
                                } else {
                                    invokerManager = new InvokerManager(sc.getRemoteAddress().toString(), packagedCommand.getCommandName() + " " + packagedCommand.getCommandArguments(), users.get(sc.getRemoteAddress().toString()));
                                }
                                invokeExecutor.execute(invokerManager);
                            }else {
                                stringBuilder.append("\nType \"login\" to sign in or \"register\" to sign up");
                            }
                        }
                    }
                }



            }
            if (!"".equals(users.get(sc.getRemoteAddress().toString()))){
                stringBuilder.append(String.format("You are working as %s\n", users.get(sc.getRemoteAddress().toString())));
            }else {
                stringBuilder.append("\nType \"login\" to sign in or \"register\" to sign up");
            }
            synchronized (queueMap) {
                queueMap.get(sc.getRemoteAddress().toString()).add(stringBuilder.toString());
            }
        } catch (IOException e) {
            //e.printStackTrace(System.out);
        }
    }

//    private String loginInsert(String[] logpswd) {
//        try {
//            PreparedStatement statement = conn.prepareStatement("INSERT INTO account (login, password) values (?, ?)");
//            statement.setString(1, logpswd[0]);
//            statement.setString(2, logpswd[1]);
//            statement.executeUpdate();
//            logins.put(logpswd[0], logpswd[1]);
//            return String.format("You are registered with username \"%s\"\nTo sign in, type \"login\" and enter your login and password", logpswd[0]);
//        } catch (SQLException e) {
//            //e.printStackTrace();
//            return "You are not registered yet, because of error in data base!";
//        }
//    }
}
