package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;

public interface Storage {

    public MealTo createMeal (LocalDateTime dateTime, String name, int calories);

    public MealTo getMeal (int id);

    public void updateMeal (MealTo meal);

    public boolean deleteMeal (int id);

}
