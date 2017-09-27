<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="f" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>Meals</title>
    <link rel="stylesheet" href="resources/css/style.css">

</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>

<form method="post">
    <dl>
        <dt>id</dt>
        <dd><input type="number" name="Id"></dd>
    </dl>
    <dl>
        <dt>DateTime</dt>
        <dd><input type="datetime-local" name="DateTime"></dd>
    </dl>
    <dl>
        <dt>Description</dt>
        <dd><input type="text" name="Description"></dd>
    </dl>
    <dl>
        <dt>Calories</dt>
        <dd><input type="number" name="Calories"></dd>
    </dl>

    <button type="submit">Submit</button>

</form>


<table border="1" cellpadding="8" cellspacing="0">
    <tr>
        <td>
            Date
        </td>
        <td>
            Description
        </td>
        <td>
            Calories
        </td>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealWithExceed"/>
        <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
            <td>${f:formatDateTime(meal.dateTime)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
            <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
        </tr>

    </c:forEach>
</table>

</body>
</html>