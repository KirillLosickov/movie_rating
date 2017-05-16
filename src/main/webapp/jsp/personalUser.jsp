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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/banner.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/menu.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/personalPage.css" type="text/css"/>

    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" pageEncoding="utf-8">
    <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
    <title>personal page</title>
    <fmt:setLocale scope="session" value="${sessionScope.userLocal}"/>
    <fmt:setBundle basename="by.training.epam.localization.front-end" scope="session" var="loc"/>
    <fmt:message bundle="${loc}" key="local.word.login" var="login_word"/>
    <fmt:message bundle="${loc}" key="local.word.password" var="password_word"/>
    <fmt:message bundle="${loc}" key="local.word.name" var="name_word"/>
    <fmt:message bundle="${loc}" key="local.word.lastname" var="lastname_word"/>
    <fmt:message bundle="${loc}" key="local.word.rating" var="raiting_word"/>
    <fmt:message bundle="${loc}" key="local.button.make_as_admin" var="make_admin_but"/>
    <fmt:message bundle="${loc}" key="local.button.make_as_user" var="make_user_but"/>
    <fmt:message bundle="${loc}" key="local.button.block" var="block_but"/>
    <fmt:message bundle="${loc}" key="local.button.unblock" var="unblock_but"/>
    <fmt:message bundle="${loc}" key="local.question.deleted" var="question_deleted"/>
    <fmt:message bundle="${loc}" key="local.word.change_user_rating" var="change_user_rating"/>
</head>
<body>
<%@include file="/jsp/part/banner.jsp"%>
<%@include file="/jsp/part/menu.jsp"%>
<jsp:useBean id="person" class="by.training.epam.bean.User" scope="request"/>
<div class="main">

    <p class="infoAboutFilm"><b>${login_word}:</b> ${person.login}</p>
    <p class="infoAboutFilm"><b>${password_word}:</b> ${person.password}</p>
    <p class="infoAboutFilm"><b>${name_word}:</b> ${person.name}</p>
    <p class="infoAboutFilm"><b>${lastname_word}:</b> ${person.lastName}</p>
    <p class="infoAboutFilm"><b>Email:</b> ${person.email}</p>
    <p class="infoAboutFilm"><b>${raiting_word}:</b> ${person.rating}</p>
    <p class="infoAboutFilm"><b>${question_deleted}:</b> ${person.block}</p>

    <c:if test="${position eq (admin)}">
        <c:choose>
            <c:when test="${person.block eq true}">
                <form action="controller" method="post" >
                    <input type="hidden" name ="command" value="unblock_user"/>
                    <input type="hidden" name ="id_user" value="${person.id}"/>
                    <input class="all-btns" type="submit" value="${unblock_but}"/>
                </form>
            </c:when>
            <c:when test="${person.block eq (false)}">
                <form action="controller" method="post" >
                    <input type="hidden" name ="command" value="block_user"/>
                    <input type="hidden" name ="id_user" value="${person.id}"/>
                    <input class="all-btns" type="submit" value="${block_but}"/>
                </form>
            </c:when>
        </c:choose>
        <c:choose>
            <c:when test="${person.position eq user}">
                <form action="controller" method="post">
                    <input type="hidden" name="command" value="set_admin_right"/>
                    <input type="hidden" name="id_user" value="${person.id}"/>
                    <input class="all-btns" type="submit" value="${make_admin_but}"/>
                </form>
            </c:when>
            <c:when test="${person.position eq admin}">
                <form action="controller" method="post">
                    <input type="hidden" name="command" value="set_user_right"/>
                    <input type="hidden" name="id_user" value="${person.id}"/>
                    <input class="all-btns" type="submit" value="${make_user_but}"/>
                </form>
            </c:when>
        </c:choose>
        <hr>
        <form action="controller" method="post">
            <input type="hidden" name="command" value="change_user_rating"/>
            <input type="hidden" name="id_user" value="${person.id}"/>
            <input class="all-btns" type="submit" value="${change_user_rating}"/><br>
            <input type="text" name="rating" id="users_rating"/>
        </form>
    </c:if>
</div>
</body>
</html>
