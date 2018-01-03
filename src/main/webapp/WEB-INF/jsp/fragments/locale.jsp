<%--
  Created by IntelliJ IDEA.
  User: Stas
  Date: 03.01.2018
  Time: 13:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<li class="dropdown">
    <a href="#" class="dropdown-toggle" data-toggle="dropdown">${pageContext.response.locale}<b class="caret"></b></a>
    <ul class="dropdown-menu">
        <li><a href="${requestScope['javax.servlet.forward.request_uri']}?language=en">English</a></li>
        <li><a href="${requestScope['javax.servlet.forward.request_uri']}?language=ru">Русский</a></li>
    </ul>
</li>

