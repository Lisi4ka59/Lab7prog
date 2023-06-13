package com.lisi4ka.models;

import java.text.SimpleDateFormat;

/**
 * @param age Значение поля должно быть больше 0
 */
public record Human(long age, java.util.Date birthday) {
    public String getStringBirthday() {
        return new SimpleDateFormat("dd.MM.yyyy").format(birthday());
    }

    @Override
    public String toString() {
        return String.format("Age = %d, birthday = %s", age(), getStringBirthday());
    }
}