package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;

public class InMemoryStorage implements Storage {
    @Override
    public MealTo createMeal(LocalDateTime dateTime, String name, int calories) {
        return null;
    }

    @Override
    public MealTo getMeal(int id) {
        return null;
    }

    @Override
    public void updateMeal(MealTo meal) {

    }

    @Override
    public boolean deleteMeal(int id) {
        return false;
    }
}
