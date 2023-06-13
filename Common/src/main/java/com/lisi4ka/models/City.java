package com.lisi4ka.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class City implements Comparable<City> {
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private double area; //Значение поля должно быть больше 0
    private Long population; //Значение поля должно быть больше 0, Поле не может быть null
    private int metersAboveSeaLevel;
    private Climate climate; //Поле не может быть null
    private Government government; //Поле может быть null
    private StandardOfLiving standardOfLiving; //Поле может быть null
    private Human governor; //Поле может быть null
    private final String user;
    public City (int id, String name, Coordinates coordinates, Long population, double area, int metersAboveSeaLevel,
                 Climate climate, Government government, StandardOfLiving standardOfLiving, Human governor, String user){
        this.id = id;
        creationDate = LocalDateTime.now();
        this.name = name;
        this.coordinates = coordinates;
        this.population = population;
        this.area = area;
        this.climate = climate;
        this.standardOfLiving = standardOfLiving;
        this.government = government;
        this.governor = governor;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.user = user;
    }

    public void setCreationDate(LocalDateTime creationDate){
        if (creationDate == null) {
            throw new NullPointerException("Field creationDate can not be null");
        }
        this.creationDate=creationDate;
    }
    public String getUser(){return user;}

    public String getCreationDate (){
        return creationDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
    }
    public Integer getId (){
        return id;
    }
    public void setId (Integer id){
        this.id = id;
    }

    public void setCoordinates (Coordinates coordinates){
        if (coordinates == null) {
            throw new NullPointerException("Field coordinates can not be null");
        }
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates(){
        return coordinates;
    }

    public void setPopulation (Long population){
        if (population == null ) {
            throw new NullPointerException("Field population can not be null or less than 0");
        }
        if (population <= 0){
            throw new IllegalArgumentException("Field population can not be less or equals than 0");
        }
        this.population=population;
    }

    public Long getPopulation (){
        return population;
    }

    public void setArea (double area){
        if (area <= 0) {
            throw new IllegalArgumentException("Field area can not be less or equals than 0");
        }
        this.area = area;
    }

    public double getArea (){
        return area;
    }
    public String getStringArea(){
        return String.format("%.2f\n", area);
    }

    public void setMetersAboveSeaLevel (int metersAboveSeaLevel){
        if (metersAboveSeaLevel < -432 || metersAboveSeaLevel > 8849) {
            throw new IllegalArgumentException("Field metersAboveSeaLevel can not be less than -432 or more than 8849!");
        }
        this.metersAboveSeaLevel = metersAboveSeaLevel;
    }

    public Integer getMetersAboveSeaLevel (){
        return metersAboveSeaLevel;
    }

    public void setClimate (Climate climate){
        if (climate == null) {
            throw new NullPointerException("Field climate can not be null");

        }

        this.climate = climate;
    }

    public Climate getClimate (){
        return climate;
    }

    public void setGovernment (Government government){
        this.government = government;
    }
    public Government getGovernment (){
        return government;
    }

    public void setStandardOfLiving (StandardOfLiving standardOfLiving){
        this.standardOfLiving = standardOfLiving;
    }

    public StandardOfLiving getStandardOfLiving (){
        return standardOfLiving;
    }

    public void setGovernor (Human governor){
        this.governor = governor;
    }

    public Human getGovernor (){
        return governor;
    }

    public void setName (String name){
        if (name == null) {
            throw new NullPointerException("Field name can not be null");
        }
        this.name = name;
    }

    public String getName (){
        return name;
    }
    @Override
    public String toString(){
        return String.format("Name: %s\nUser: %s\nID: %d\nCreation date: %s\nCoordinates: %sStandard of living: %s\nGovernment: %s\nClimate: %s\nArea: %sPopulation: %d\nMeters above sea level: %d\nGovernor %s", getName(), getUser(), getId(), getCreationDate(), getCoordinates(), getStandardOfLiving(), getGovernment(), getClimate(), getStringArea(), getPopulation(), getMetersAboveSeaLevel(), getGovernor());
    }
    @Override
    public int compareTo(City o) {
        if (o!=null)
            return  -name.compareToIgnoreCase(o.name);
        else
            return 1;
    }
}