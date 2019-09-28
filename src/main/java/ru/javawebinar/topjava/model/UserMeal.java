package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class UserMeal implements Comparable{
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    @Override
    public int compareTo(Object o) {
        if (this.dateTime.isAfter(((UserMeal)o).getDateTime())) {return 1;}
        if (this.dateTime.isBefore(((UserMeal)o).getDateTime())) {return -1;}
        else return 0;
    }
}
