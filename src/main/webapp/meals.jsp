<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 10/6/2019
  Time: 2:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>

<table>
    <thead>
    <tr>
        <th>id</th>
        <th>Date</th>
        <th>Name</th>
        <th>Calories</th>
    </tr>
    </thead>

    <tbody>
    <c:forEach items="${meals}" var="meal">

        <tr bgcolor=${meal.isExcess() ? "red" : "green"}>
            <td>${meal.getId()}</td>
            <td>${meal.getDateTime().toString().replace("T", " ")}</td>
            <td>${meal.getDescription()}</td>
            <td>${meal.getCalories()}</td>
        </tr>
    </c:forEach>
    </tbody>

    <form action="/action_page.php" method="get">
        First name: <input type="text" name="fname"><br>
        Last name: <input type="text" name="lname"><br>
        <input type="submit" value="Submit">
        <input type="submit" formmethod="post" value="Submit using POST">
    </form>


</table>

</body>
</html>
