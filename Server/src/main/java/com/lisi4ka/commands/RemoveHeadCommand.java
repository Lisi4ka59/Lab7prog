package com.lisi4ka.commands;

import com.lisi4ka.models.City;
import com.lisi4ka.utils.BdManager;

import java.sql.SQLException;
import java.util.List;

public class RemoveHeadCommand implements Command{
    private final List<City> collection;
    public RemoveHeadCommand(List<City> collection){
        this.collection = collection;
    }

    @Override
    public String execute(String args, String user) {
        BdManager bdManager = new BdManager();
        if (!collection.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            City city = collection.get(0);
            if (city.getUser().equals(user)) {
                int id =city.getId();
                try {
                    bdManager.bdRemove(id);
                } catch (SQLException e) {
                    e.printStackTrace();
                    return String.format("Can not delete first city %d, because of data base problem", id);
                }
                stringBuilder.append(city.toString()).append("\n");
                collection.remove(0);
                stringBuilder.append("First element of collection removed").append("\n");
                return stringBuilder.toString();
            } else {
                return "First element of collection is not belong to you!\n";
            }
        }
        else {
            return "Collection is empty\n";
        }
    }

}
