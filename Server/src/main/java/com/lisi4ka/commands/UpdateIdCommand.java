package com.lisi4ka.commands;

import com.lisi4ka.models.*;
import com.lisi4ka.utils.BdManager;
import com.lisi4ka.utils.CityComparator;

import java.util.Date;
import java.util.List;

import static com.lisi4ka.utils.Checker.checkDate;

public class UpdateIdCommand implements Command {
    public static String message = "";
    private final List<City> collection;

    public UpdateIdCommand(List<City> collection) {

        this.collection = collection;
    }

    private String updateArgs(String[] args, String user) {
        boolean update = false;
        BdManager updateBd = new BdManager();
        int id = Integer.parseInt(args[0]);
        for (City city : collection) {
            if (city.getId() == id) {
                if (!user.equals(city.getUser())) {
                    return String.format("City %d does not belong to you\n", id);
                }
                if (updateBd.isUpdate(id, "name", args[1])) {
                    city.setName(args[1]);
                }
                if (updateBd.isUpdate(id, "coordinate_x", Double.parseDouble(args[2]))) {
                    double x = Double.parseDouble(args[2]);
                    Coordinates coordinates = city.getCoordinates();
                    coordinates.setX(x);
                    city.setCoordinates(coordinates);
                }
                if (updateBd.isUpdate(id, "coordinate_y", Float.parseFloat(args[3]))) {
                    float y = Float.parseFloat(args[3]);
                    Coordinates coordinates = city.getCoordinates();
                    coordinates.setY(y);
                    city.setCoordinates(coordinates);
                }
                if (updateBd.isUpdate(id, "area", Double.parseDouble(args[4]))) {
                    city.setArea(Double.parseDouble(args[4]));
                }
                if (updateBd.isUpdate(id, "population", Long.parseLong(args[5]))) {
                    city.setPopulation(Long.parseLong(args[5]));
                }
                if (updateBd.isUpdate(id, "meters_above_sea_level", Integer.parseInt(args[6]))) {
                    city.setMetersAboveSeaLevel(Integer.parseInt(args[6]));
                }
                if (updateBd.isUpdate(id, "climate", Climate.fromInt(Integer.parseInt(args[7])).name())) {
                    city.setClimate(Climate.fromInt(Integer.parseInt(args[7])));
                }
                if ("null".equals(args[8])) {
                    if (updateBd.isUpdate(id, "government")) {
                        city.setGovernment(null);
                    }
                } else {
                    if (updateBd.isUpdate(id, "government", Government.fromInt(Integer.parseInt(args[8])).name())) {
                        city.setGovernment(Government.fromInt(Integer.parseInt(args[8])));
                    }
                }
                if ("null".equals(args[9])) {
                    if (updateBd.isUpdate(id, "standard_of_living")) {
                        city.setStandardOfLiving(null);
                    }
                } else {
                    if (updateBd.isUpdate(id, "standard_of_living", StandardOfLiving.fromInt(Integer.parseInt(args[9])).name())) {
                        city.setStandardOfLiving(StandardOfLiving.fromInt(Integer.parseInt(args[9])));
                    }
                }
                Date birthday;
                if ("null".equals(args[10]) || "null".equals(args[11])) {
                    if (updateBd.isUpdate(id, "governor_age") || updateBd.isUpdate(id, "governor_birthday")) {
                        city.setGovernor(null);
                    }
                } else {
                    if (updateBd.isUpdate(id, "governor_age", Long.parseLong(args[10])) || updateBd.isUpdate(id, "governor_birthday", new java.sql.Date(checkDate(args[11]).getTime()))) {
                        long age = Long.parseLong(args[10]);
                        birthday = checkDate(args[11]);
                        city.setGovernor(new Human(age, birthday));
                    }
                }
                update = true;
                break;
            }
        }
        if (update) {
            String msg = message;
            message = "";
            return String.format("City %d updated\n%s", id, msg);
        }
        else
            return String.format("City %d doesn't exist\n", id);
    }



    @Override
    public String execute(String args, String user) {
        String[] cityArgs = args.split(",");
        String answer = updateArgs(cityArgs, user);
        collection.sort(new CityComparator());
        return answer;
    }
}
