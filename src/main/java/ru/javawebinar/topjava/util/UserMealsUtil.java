package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                //new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),//
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 29, 9, 0), "Завтрак", 1500),//
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 29, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 29, 20, 0), "Ужин", 510)
        );
        List<UserMealWithExceed> filteredWithExceeded = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        for (UserMealWithExceed meal : filteredWithExceeded) {
            System.out.println(meal.toString());

        }
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Collections.sort(mealList);
        //Map<LocalDate, ArrayList<UserMeal>> map = new HashMap<>();
        List<UserMealWithExceed> result = new ArrayList<>();
        int threshhold = 0;
        //add check for a null array
        LocalDate date = mealList.get(0).getDateTime().toLocalDate();
        ArrayList<UserMeal> tmpList = new ArrayList<>();
        for (UserMeal meal : mealList) {

            if (date.isEqual(meal.getDateTime().toLocalDate())) {
                tmpList.add(meal);
                threshhold += meal.getCalories();
            } else {
                boolean exceed = threshhold > caloriesPerDay;
                for (UserMeal tmpMeal : tmpList) {
                    if (TimeUtil.isBetween(tmpMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                        result.add(new UserMealWithExceed(tmpMeal.getDateTime(), tmpMeal.getDescription(), tmpMeal.getCalories(), exceed));
                    }
                }
                //map.put(date, tmpList);
                tmpList = new ArrayList<>();
                tmpList.add(meal);
                date = meal.getDateTime().toLocalDate();
                threshhold = meal.getCalories();
            }


        }
        boolean exceed = threshhold > caloriesPerDay;
        for (UserMeal tmpMeal : tmpList) {
            if (TimeUtil.isBetween(tmpMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                result.add(new UserMealWithExceed(tmpMeal.getDateTime(), tmpMeal.getDescription(), tmpMeal.getCalories(), exceed));
            }
        }
        //map.put(date, tmpList);
        return result;
    }
}
