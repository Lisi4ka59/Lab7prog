package com.lisi4ka.utils;

import com.lisi4ka.commands.UpdateIdCommand;
import com.lisi4ka.models.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import static com.lisi4ka.common.ServerApp.logins;
import static com.lisi4ka.utils.BdConnect.conn;

public class BdManager {
    public String loginInsert(String[] logpswd) {
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO account (login, password) values (?, ?)");
            statement.setString(1, logpswd[0]);
            statement.setString(2, logpswd[1]);
            statement.executeUpdate();
            logins.put(logpswd[0], logpswd[1]);
            return String.format("You are registered with username \"%s\"\nTo sign in, type \"login\" and enter your login and password", logpswd[0]);
        } catch (SQLException e) {
            //e.printStackTrace();
            return "You are not registered yet, because of error in data base!";
        }
    }
    public boolean isUpdate(int id, String cityField, String newField) {
        try {
            PreparedStatement statement = conn.prepareStatement("UPDATE city SET " + cityField + " = ? WHERE id = ?");
            statement.setString(1, newField);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            UpdateIdCommand.message += cityField + " was not updated!\n";
            return false;
        }
        return true;
    }

    public boolean isUpdate(int id, String cityField, long newField) {
        try {
            PreparedStatement statement = conn.prepareStatement("UPDATE city SET " + cityField + " = ? WHERE id = ?");
            statement.setLong(1, newField);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            UpdateIdCommand.message += cityField + " was not updated!\n";
            return false;
        }
        return true;
    }

    public boolean isUpdate(int id, String cityField, float newField) {
        try {
            PreparedStatement statement = conn.prepareStatement("UPDATE city SET " + cityField + " = ? WHERE id = ?");
            statement.setFloat(1, newField);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            UpdateIdCommand.message += cityField + " was not updated!\n";
            return false;
        }
        return true;
    }

    public boolean isUpdate(int id, String cityField, int newField) {
        try {
            PreparedStatement statement = conn.prepareStatement("UPDATE city SET " + cityField + " = ? WHERE id = ?");
            statement.setInt(1, newField);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            UpdateIdCommand.message += cityField + " was not updated!\n";
            return false;
        }
        return true;
    }

    public boolean isUpdate(int id, String cityField, Date newField) {
        try {
            PreparedStatement statement = conn.prepareStatement("UPDATE city SET " + cityField + " = ? WHERE id = ?");
            statement.setDate(1, new java.sql.Date(newField.getTime()));
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            UpdateIdCommand.message += cityField + " was not updated!\n";
            return false;
        }
        return true;
    }

    public boolean isUpdate(int id, String cityField, double newField) {
        try {
            PreparedStatement statement = conn.prepareStatement("UPDATE city SET " + cityField + " = ? WHERE id = ?");
            statement.setDouble(1, newField);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            UpdateIdCommand.message += cityField + " was not updated!\n";
            return false;
        }
        return true;
    }

    public boolean isUpdate(int id, String cityField) {
        try {
            PreparedStatement statement = conn.prepareStatement("UPDATE city SET " + cityField + " = ? WHERE id = ?");
            statement.setNull(1, 12);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            UpdateIdCommand.message += cityField + " was not updated!\n";
            return false;
        }
        return true;
    }
    public void bdSetCity(City city) throws SQLException {
        PreparedStatement stmt;
        stmt = conn.prepareStatement(" INSERT INTO city(name, coordinate_x, coordinate_y, area, population, meters_above_sea_level, climate, government, standard_of_living, governor_age, governor_birthday, user_id)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, (select id from account where login = ?))");
        stmt.setString(1, city.getName());
        stmt.setDouble(2, city.getCoordinates().getX());
        stmt.setFloat(3, city.getCoordinates().getY());
        stmt.setDouble(4, city.getArea());
        stmt.setLong(5, city.getPopulation());
        stmt.setInt(6, city.getMetersAboveSeaLevel());
        stmt.setString(7, city.getClimate().name());
        if (city.getGovernment() == null){
            stmt.setNull(8, 12);
        }else {
            stmt.setString(8, city.getGovernment().name());
        }
        if (city.getStandardOfLiving() == null){
            stmt.setNull(9, 12);
        }else {
            stmt.setString(9, city.getStandardOfLiving().name());
        }
        if (city.getGovernor() == null){
            stmt.setNull(10, 4);
            stmt.setNull(11, 91);
        }else{
            stmt.setLong(10, city.getGovernor().age());
            stmt.setDate(11, new java.sql.Date(city.getGovernor().birthday().getTime()));
        }
        stmt.setString(12, city.getUser());
        stmt.executeUpdate();
    }
    public void bdGetCityUser(City city) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT currval(pg_get_serial_sequence('city','id'))");
        while (rs.next()){
            city.setId(rs.getInt(1));
        }
    }
    public void bdLoad(List<City> collection) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select city.id, name, coordinate_x, coordinate_y, population, area, meters_above_sea_level, climate, government," +
                " standard_of_living, governor_age, governor_birthday, creation_city_date, login from city left join account on user_id = account.id");
        while (rs.next()) {
            City city = new City(
                    rs.getInt("id"),
                    rs.getString("name"),
                    new Coordinates(rs.getDouble("coordinate_x"), rs.getFloat("coordinate_y")),
                    rs.getLong("population"),
                    rs.getDouble("area"),
                    rs.getInt("meters_above_sea_level"),
                    Climate.valueOf(rs.getString("climate")),
                    (rs.getString("government") == null) ? null :
                            Government.valueOf(rs.getString("government")),
                    (rs.getString("standard_of_living") == null) ? null :
                            StandardOfLiving.valueOf(rs.getString("standard_of_living")),
                    (rs.getInt("governor_age") == 0)  ? null :
                            new Human(rs.getLong("governor_age"), rs.getDate("governor_birthday")),
                    rs.getString("login")
            );
            city.setCreationDate(rs.getTimestamp("creation_city_date").toLocalDateTime());
            collection.add(city);
            collection.sort(new CityComparator());
        }
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("select login, password from account");
        while (resultSet.next()) {
            String login = resultSet.getString("login");
            String password = resultSet.getString("password");
            logins.put(login, password);
        }
    }
    public void bdRemove(int id) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("DELETE FROM city where id = ?");
        statement.setInt(1, id);
        statement.executeUpdate();
    }
    public void bdCreate() throws SQLException {
        String createTableQuery = "CREATE TABLE  IF NOT EXISTS city (id Serial, name TEXT NOT NULL, coordinate_x double precision NOT NULL, coordinate_y real NOT NULL, area double precision NOT NULL, population BIGINT NOT NULL, meters_above_sea_level INT NOT NULL, climate text NOT NULL, government text, standard_of_living text, governor_age int, governor_birthday date, user_id INT, creation_city_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP);";
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(createTableQuery);
        String createAnotherTableQuery = "CREATE TABLE  IF NOT EXISTS account (id Serial, creation_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP, login text NOT NULL, password text NOT NULL);";
        Statement statement = conn.createStatement();
        statement.executeUpdate(createAnotherTableQuery);
        //System.out.println("Table created successfully!");
    }
}

