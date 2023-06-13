package com.lisi4ka.commands;

import com.lisi4ka.models.City;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.lisi4ka.utils.BdConnect.conn;


public class RemoveByIdCommand implements Command {
    private final List<City> collection;

    public RemoveByIdCommand(List<City> collection) {
        this.collection = collection;
    }

    private String remove(int id, String user) {
        boolean removed = false;
        for (City city : collection) {
            if (city.getId() == id) {
                if (city.getUser().equals(user)){
                    try {
                        PreparedStatement statement = conn.prepareStatement("DELETE FROM city where id = ?");
                        statement.setInt(1, id);
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return String.format("Can not remove city %d, because of data base problem", id);
                    }
                    removed = collection.remove(city);
                    break;
                }else {
                    return String.format("City with ID %d is not belong to you!\n", id);
                }
            }
        }
        if (removed)
            return String.format("City with ID %d removed!\n", id);
        else
            return String.format("City with ID %d does not exists!\n", id);
    }

    @Override
    public String execute(String stringId, String user) {
        int id = Integer.parseInt(stringId);
        return remove(id, user);
    }

}