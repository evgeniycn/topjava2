package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

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
        List<UserMealWithExceed> filteredWithExceededStreams = getFilteredWithExceededStreams(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        for (UserMealWithExceed meal : filteredWithExceededStreams) {
            System.out.println(meal.toString());

        }
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExceed> result = new ArrayList<>();
        Map<LocalDate, Integer> thresholdMap = new HashMap<>();
        Map<LocalDate, List<UserMeal>> finalMap = new HashMap<>();

        //add check for a null array
        LocalDate date = mealList.get(0).getDateTime().toLocalDate();

        thresholdMap.put(date, 0);
        finalMap.put(date, new ArrayList<>());

        for (UserMeal meal : mealList) {

            date = meal.getDateTime().toLocalDate();

            if (!thresholdMap.containsKey(date)) {
                thresholdMap.put(date, meal.getCalories());
                if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                    List<UserMeal> tmpList = new ArrayList<>();
                    tmpList.add(meal);
                    finalMap.put(date, tmpList);
                }
            } else {
                Integer caloriesCount = thresholdMap.get(date);
                thresholdMap.put(date, caloriesCount + meal.getCalories());
                if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                    List<UserMeal> tmpList = finalMap.get(date);
                    tmpList.add(meal);
                }
            }
        }
        Iterator it = finalMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            date = (LocalDate) pair.getKey();
            List<UserMeal> tmpList = (List<UserMeal>) pair.getValue();
            Integer finalCaloriesCount = thresholdMap.get(date);
            for (UserMeal tmpMeal : tmpList) {
                result.add(new UserMealWithExceed(tmpMeal.getDateTime(), tmpMeal.getDescription(), tmpMeal.getCalories(), finalCaloriesCount > caloriesPerDay));
            }
        }
        return result;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededStreams(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        final LocalDate[] date = {mealList.get(0).getDateTime().toLocalDate()};
        final int[] caloriesCount = {mealList.get(0).getCalories()};
        Map<LocalDate, Integer> dates = new HashMap<>();
        dates.put(date[0], caloriesCount[0]);

        List<UserMeal> collect = mealList.stream()
                .sorted(Comparator.comparing(UserMeal::getDateTime))
                .filter(meal -> {
                    if (meal.getDateTime().toLocalDate().isEqual(date[0])) {
                        caloriesCount[0] = caloriesCount[0] + meal.getCalories();
                        dates.put(date[0], caloriesCount[0]);
                    } else {
                        date[0] = meal.getDateTime().toLocalDate();
                        caloriesCount[0] = meal.getCalories();
                        dates.put(date[0], caloriesCount[0]);
                    }
                    return TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime);
                })
                .collect(Collectors.toList());

        return collect
                .stream()
                .map(meal -> new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), caloriesPerDay < dates.get(meal.getDateTime().toLocalDate())))
                .collect(Collectors.toList());

    }
}
