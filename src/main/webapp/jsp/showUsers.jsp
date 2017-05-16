<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="position" value="${sessionScope.position}"/>
<c:set var="admin" value="admin"/>
<c:set var="user" value="user"/>
<c:set var="show_movies" value="show_movies"/>
<c:set value="alert" var="alert"/>
<c:set var="command" value="${command}"/>
<!DOCTYPE html>
<html>
<head>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/partial.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/menu.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/banner.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/usersPage.css" type="text/css"/>

    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" pageEncoding="utf-8">
    <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
    <title>showing users</title>
    <fmt:setLocale scope="session" value="${sessionScope.userLocal}"/>
    <fmt:setBundle basename="by.training.epam.localization.front-end" scope="session" var="loc"/>
    <fmt:message bundle="${loc}" key="local.word.login" var="login_word"/>
    <fmt:message bundle="${loc}" key="local.word.password" var="password_word"/>
    <fmt:message bundle="${loc}" key="local.word.name" var="name_word"/>
    <fmt:message bundle="${loc}" key="local.word.lastname" var="lastname_word"/>
    <fmt:message bundle="${loc}" key="local.word.email" var="email_word"/>
    <fmt:message bundle="${loc}" key="local.word.isblock" var="isblock_word"/>
    <fmt:message bundle="${loc}" key="local.word.rating" var="rating_word"/>
</head>
<body>
<%@include file="/jsp/part/banner.jsp"%>
<%@include file="/jsp/part/menu.jsp"%>
<section class="main">
    <table id="table_users">
        <tr class="for_hover_tr">
            <th>${login_word}</th>
            <th>${password_word}</th>
            <th>${name_word}</th>
            <th>${lastname_word}</th>
            <th>${rating_word}</th>
            <th>${isblock_word}</th>
        </tr>
        <c:forEach var="user" items="${userList}">
            <tr class="for_hover_tr">
                <td>
                    <form action="controller" method="post" style="margin: 0px;">
                        <input type="hidden" name="command" value="personal_user">
                        <input type="hidden" name="login" value="${user.login}">
                        <input class="input_login" type="submit" value="${user.login}">
                    </form>
                </td>
                <td><c:out value="${user.password}"/></td>
                <td><c:out value="${user.name}"/></td>
                <td><c:out value="${user.lastName}"/></td>
                <td><c:out value="${user.rating}"/></td>
                <td><c:out value="${user.block}"/></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>
