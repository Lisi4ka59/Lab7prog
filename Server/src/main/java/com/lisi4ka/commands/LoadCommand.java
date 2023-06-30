package com.lisi4ka.commands;

import com.lisi4ka.models.*;
import com.lisi4ka.utils.BdManager;

import java.util.List;

public class LoadCommand implements Command {
    private final List<City> collection;
    public LoadCommand(List<City> collection) {
        this.collection = collection;
    }

    private String load() {
        BdManager bdManager = new BdManager();
        try {

                bdManager.bdCreate();
                bdManager.bdLoad(collection);
            } catch (Exception e) {
                return "Error while connecting!";
            }

        return "Collection uploaded";
    }

    @Override
    public String execute() {
        return load();
    }
}
