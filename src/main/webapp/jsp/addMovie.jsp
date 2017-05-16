<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Movie</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/partial.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/banner.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/menu.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/addmovie.css" type="text/css"/>

    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" pageEncoding="utf-8" charset="utf-8">
    <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
    <script src="${pageContext.request.contextPath}/js/add_field.js"></script>
    <fmt:setLocale scope="session" value="${sessionScope.userLocal}"/>
    <fmt:setBundle basename="by.training.epam.localization.front-end" scope="session" var="loc"/>
    <fmt:message bundle="${loc}" key="local.word.name" var="name_word"/>
    <fmt:message bundle="${loc}" key="local.word.budget" var="budget_word"/>
    <fmt:message bundle="${loc}" key="local.word.profit" var="profit_word"/>
    <fmt:message bundle="${loc}" key="local.word.duration" var="duration_word"/>
    <fmt:message bundle="${loc}" key="local.word.averageMark" var="averageMark_word"/>
    <fmt:message bundle="${loc}" key="local.word.releaseDate" var="releaseDate_word"/>
    <fmt:message bundle="${loc}" key="local.word.restrictionAge" var="resticton_word"/>
    <fmt:message bundle="${loc}" key="local.word.type" var="type_word"/>
    <fmt:message bundle="${loc}" key="local.word.countries" var="countries_word"/>
    <fmt:message bundle="${loc}" key="local.word.description" var="description_word"/>
    <fmt:message bundle="${loc}" key="local.word.genres" var="genres_word"/>
    <fmt:message bundle="${loc}" key="local.word.participants" var="participants_word"/>
    <fmt:message bundle="${loc}" key="local.button.addFilm" var="addFilm_but"/>
    <fmt:message bundle="${loc}" key="local.word.do_not_match_pattern" var="do_not_match_pattern"/>
</head>
<body>
<%@include file="/jsp/part/banner.jsp"%>
<%@include file="/jsp/part/menu.jsp"%>
<section class="main">
    <form action="controller" method="post" id="addForm" enctype="multipart/form-data">
        <input type="hidden" name="command" value="add_Movie"/><br/>
        <h3>${name_word}</h3>
        <input class="addField" type="text" name="title" required pattern="{2,}"><br/>
        <h3>${budget_word}</h3>
        <input class="addField" type="text" name="budget" pattern="[0-9]+"/><br/>
        <h3>${profit_word}</h3>
        <input class="addField" type="text" name="profit" pattern="[0-9]+"/><br/>
        <h3>${duration_word}</h3>
        <input class="addField" type="text" name="duration" placeholder="HH:MM" pattern="^([0-1]\d|2[0-3])(:[0-5]\d){1}$"required/><span
            class="fieldFormat">${do_not_match_pattern}</span><br/>
        <h3>${resticton_word}</h3>
        <input class="addField" type="text" name="restriction_age" pattern="^[1-9]{1}$|^[1-9]{1}[0-9]{1}$|^100$" placeholder="1-100" required/><span
            class="fieldFormat">${do_not_match_pattern}</span><br/>
        <h3>${releaseDate_word} ошибка в pattern on jsp</h3>
        <input type="text" name="date_release" pattern="^\d{4}\-(0?[1-9]|1[012])\-(0?[1-9]|[12]\d|3[01])$" placeholder="2016-03-12" required class="addField"/><br/><br/>
        <input class="" type="file" name="poster" placeholder="poster" size="50"/><br/><br/>
        <hr/>
        <h3>${type_word}</h3>
        <select class="selectField inline_b" name="type" required>
            <c:forEach var="type" items="${movieTypes}">
                <option>${type}</option>
            </c:forEach>
        </select>
        <br/>
        <hr/>
        <div id="divcountry">
            <h3>${countries_word}</h3>
            <button type="button" onclick="add('country')">+</button>
            <button type="button" onclick="del('country')">-</button>
            <br/> <br/>
            <div id="countryS">
                <select class="selectField block_b" name="country" id="country0" required value="${country.id}">
                    <c:forEach var="country" items="${countries}">
                        <option value="${country.id}">${country.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <hr/>
        <div id="divperson">
            <h3>${participants_word}</h3>
            <button type="button" onclick="add('person')">+</button>
            <button type="button" onclick="del('person')">-</button>
            <br/><br/>
            <div id="personS">
                <div id="person0">
                    <select class="selectField inline_b" name="person" required>
                        <c:forEach var="person" items="${persons}">
                            <option value="${person.id}">${person.name} ${person.lastname}</option>
                        </c:forEach>
                    </select>
                    <select class="selectField inline_b" name="position" required>
                        <c:forEach var="position" items="${positions}">
                            <option value="${position.id}">${position.value}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
        <hr/>
        <div id="divgenre">
            <h2>${genres_word}</h2>
            <button type="button" onclick="add('genre')">+</button>
            <button type="button" onclick="del('genre')">-</button>
            <br/><br/>
            <div id="genreS">
                <select class="selectField block_b" name="genre" id="genre0" required>
                    <c:forEach var="genre" items="${genres}">
                        <option value="${genre.id}">${genre.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <h3>${description_word}</h3>
        <textarea rows="10" cols="100"
                  name="description" placeholder="${description_word}" required id="textarea"></textarea><br/>
        <input type="submit" value="${addFilm_but}" class="all-btns"/>
    </form>
</section>
</body>
</html>
