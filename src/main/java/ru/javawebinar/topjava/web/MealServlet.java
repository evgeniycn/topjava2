package ru.javawebinar.topjava.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

public class MealServlet extends HttpServlet {

    private final static List<Meal> mealList = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),//
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510),
            new Meal(LocalDateTime.of(2015, Month.MAY, 29, 9, 0), "Завтрак", 1500),//
            new Meal(LocalDateTime.of(2015, Month.MAY, 29, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 29, 20, 0), "Ужин", 510)
    );

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<MealTo> mealTos = MealsUtil.getFiltered(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

        req.setAttribute("meals", mealTos);
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);

        //resp.sendRedirect("users.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String date = req.getParameter("date");
        LocalDateTime dateTime = LocalDateTime.parse(date);
        String name = req.getParameter("name");
        int calories = Integer.getInteger(req.getParameter("calories"));

        Meal meal = new Meal(dateTime, name, calories);
        mealList.add(meal);
        resp.sendRedirect("meals.jsp");

    }
}
