package com.lisi4ka.commands;

import com.lisi4ka.models.*;
import com.lisi4ka.utils.BdManager;
import com.lisi4ka.utils.CityComparator;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static com.lisi4ka.utils.Checker.checkDate;
import static com.lisi4ka.utils.CityLinkedList.idRepeat;

public class AddCommand implements Command {
    private final List<City> collection;

    public AddCommand(List<City> collection) {
        this.collection = collection;
    }

    @Override
    public String execute(String args, String login) {
        City city = getCityArgs(args, login);

        collection.add(city);
        idRepeat += 1;
        collection.sort(new CityComparator());
        return "Congratulations! City added to collection\n";
    }

    public static City getCityArgs(String args, String login) {
        BdManager addManager = new BdManager();
        String[] cityArgs = args.split(",");
        String name = cityArgs[0];
        double x = Double.parseDouble(cityArgs[1]);
        float y = Float.parseFloat(cityArgs[2]);
        double area = Double.parseDouble(cityArgs[3]);
        long population = Long.parseLong(cityArgs[4]);
        int metersAboveSeaLevel = Integer.parseInt(cityArgs[5]);
        Climate climate = Climate.fromInt(Integer.parseInt(cityArgs[6]));
        Government government;
        if ("null".equals(cityArgs[7])) {
            government = null;
        } else {
            government = Government.fromInt(Integer.parseInt(cityArgs[7]));
        }
        StandardOfLiving standardOfLiving;
        if ("null".equals(cityArgs[8])) {
            standardOfLiving = null;
        } else {
            standardOfLiving = StandardOfLiving.fromInt(Integer.parseInt(cityArgs[8]));
        }
        long age;
        Human governor;
        if ("null".equals(cityArgs[9]) || "null".equals(cityArgs[10])) {
            governor = null;
        } else {
            age = Long.parseLong(cityArgs[9]);
            Date birthday = checkDate(cityArgs[10]);
            governor = new Human(age, birthday);
        }
        Coordinates coordinates = new Coordinates(x, y);
        City city = new City(1, name, coordinates, population, area, metersAboveSeaLevel, climate, government, standardOfLiving, governor, login);
        try {
            addManager.bdSetCity(city);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            addManager.bdGetCityUser(city);
        }catch (SQLException e){
            e.printStackTrace(System.out);
            throw  new RuntimeException();
        }
        return city;
    }
}