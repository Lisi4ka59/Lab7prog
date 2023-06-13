package com.lisi4ka.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Base64;

public class Serializer {
    public static byte[] serialize(String answer) throws IOException {
        ByteArrayOutputStream stringOut = new ByteArrayOutputStream();
        ObjectOutputStream serializeObject = new ObjectOutputStream(stringOut);
        PackagedResponse packagedResponse = new PackagedResponse(answer, ResponseStatus.OK);
        serializeObject.writeObject(packagedResponse);
        String serializeCommand = Base64.getEncoder().encodeToString(stringOut.toByteArray());
        return serializeCommand.getBytes();
    }
    public static byte[] serialize(PackagedResponse packagedResponse) throws IOException {
        ByteArrayOutputStream stringOut = new ByteArrayOutputStream();
        ObjectOutputStream serializeObject = new ObjectOutputStream(stringOut);
        serializeObject.writeObject(packagedResponse);
        String serializeCommand = Base64.getEncoder().encodeToString(stringOut.toByteArray());
        return serializeCommand.getBytes();
    }
    public static byte[] serialize(PackagedCommand packagedCommand) throws IOException {
        ByteArrayOutputStream stringOut = new ByteArrayOutputStream();
        ObjectOutputStream serializeObject = new ObjectOutputStream(stringOut);
        serializeObject.writeObject(packagedCommand);
        String serializeCommand = Base64.getEncoder().encodeToString(stringOut.toByteArray());
        return serializeCommand.getBytes();
    }
}
