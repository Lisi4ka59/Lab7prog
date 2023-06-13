package com.lisi4ka.commands;
import com.lisi4ka.models.City;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.lisi4ka.utils.BdConnect.conn;

public class RemoveFirstCommand implements Command{
    private final List<City> collection;
    public RemoveFirstCommand(List<City> collection){

        this.collection = collection;
    }
    @Override
    public String execute(String args, String user) {
        if (!collection.isEmpty()) {
            City city = collection.get(0);
            if (city.getUser().equals(user)) {
                int id = city.getId();
                try {
                    PreparedStatement statement = conn.prepareStatement("DELETE FROM city where id = ?");
                    statement.setInt(1, id);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return String.format("Can not delete first city %d, because of data base problem\n", id);
                }
                collection.remove(0);
                return "First element of collection removed\n";
            } else {
                return "First element of collection is not belong to you!\n";
            }

        }else {
            return "Collection is empty!\n";
        }
    }

}
