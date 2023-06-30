package com.lisi4ka.commands;

import com.lisi4ka.models.City;
import com.lisi4ka.utils.BdManager;

import java.sql.SQLException;
import java.util.List;


public class ClearCommand implements Command{
    private final List<City> collection;
    public ClearCommand(List<City> collection) {
        this.collection = collection;
    }
    @Override
    public String execute(String args, String user) {
        clearByUser(user);
        return "Collection cleaned!\n";
    }
    public void clearByUser(String user){
        BdManager bdManager = new BdManager();
        City city;
        int remove;
        int index = 0;
        do {
            remove = 0;
            for (int i = index; i < collection.size(); i++) {
                if (collection.get(i).getUser().equals(user)) {
                    city = collection.get(i);
                    try {
                        bdManager.bdRemove(city.getId());
                    }catch (SQLException ex){
                        System.out.printf("Error while removing city with ID: %d!\n", city.getId());
                        continue;
                    }
                    collection.remove(i);
                    remove++;
                    index = i;
                    break;
                }
            }
        }while (remove>0);
    }
}
