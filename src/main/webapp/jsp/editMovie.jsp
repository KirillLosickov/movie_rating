<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Movie</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/partial.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/banner.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/menu.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/movie.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/addmovie.css" type="text/css"/>

    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script src="https://code.jquery.com/jquery-1.10.2.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/deleteField.js" type="text/javascript"></script>
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
    <fmt:message bundle="${loc}" key="local.word.poster" var="poster_word"/>
    <fmt:message bundle="${loc}" key="local.word.do_not_match_pattern" var="do_not_match_pattern"/>
</head>
<body>
<%@include file="/jsp/part/banner.jsp"%>
<%@include file="/jsp/part/menu.jsp"%>
<section class="main">
    <div id="film">
        <jsp:useBean id="movie" class="by.training.epam.bean.Movie" scope="request"/>
        <form action="controller" method="post" enctype="multipart/form-data">
            <input type="hidden" name="command" value="edit_movie"/>
            <input type="hidden" name="id_movie" value="${movie.id}"/>
            <div class="information_image">
                <img src="${pageContext.request.contextPath}/images/films/${movie.imageName}" alt="Постер"
                     id="img_film">
            </div>
            <div class="information">
                <div class="field">
                    <span>${name_word}:</span>
                    <input type="text" name="title" value="${movie.title}" required pattern="{2,}"
                           class="addField"/>
                    <br/>
                    <span class="fieldFormat">${do_not_match_pattern}</span>
                </div>
                <div class="field">
                    <span>${releaseDate_word}: </span>
                    <input type="text" name="date_release" value="${movie.releaseDate}"
                           placeholder="2016-03-12" required class="addField"/>
                    <br/>
                    <span class="fieldFormat">${do_not_match_pattern}</span>
                </div>
                <div class="field">
                    <span>${type_word}: </span>
                    <select class="selectField right" name="type">
                        <c:forEach var="type" items="${movieTypes}">
                            <c:choose>
                                <c:when test="${type eq movie.type}">
                                    <option selected>${type}</option>
                                </c:when>
                                <c:when test="${type ne movie.type}">
                                    <option>${type}</option>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </select>
                    <br/>
                    <span class="fieldFormat">${do_not_match_pattern}</span>
                </div>
                <div class="field">
                    <span>${resticton_word}: </span>
                    <input type="text" name="restriction_age" value="${movie.restrictionAge}"
                           placeholder="1-100" pattern="^[1-9]{1}$|^[1-9]{1}[0-9]{1}$|^100$" required class="addField"/>
                    <br/>
                    <span class="fieldFormat">${do_not_match_pattern}</span>
                </div>
                <div class="field">
                    <span>${poster_word}: </span>
                    <input type="file" name="poster"/>
                    <br/>
                    <span class="fieldFormat">${do_not_match_pattern}</span>
                </div>
                <div class="field">
                    <span>${duration_word}: </span>
                    <fmt:formatDate type="time" timeStyle="short" value="${movie.duration}" var="theFormattedDate"/>
                    <input type="text" name="duration" value="${theFormattedDate}" placeholder="HH:MM"
                           pattern="^([0-1]\d|2[0-3])(:[0-5]\d){1}$" required class="addField"/>
                    <br/>
                    <span class="fieldFormat">${do_not_match_pattern}</span>
                </div>
                <div class="field">
                    <span>${budget_word}: </span>
                    <input type="text" name="budget" value="${movie.budget}" pattern="[0-9]+" class="addField"/>
                    <br/>
                    <span class="fieldFormat">${do_not_match_pattern}</span>
                </div>
                <div class="field">
                    <span>${profit_word}: </span>
                    <input type="text" name="profit" value="${movie.profit}" pattern="[0-9]+" class="addField"/>
                    <br/>
                    <span class="fieldFormat">${do_not_match_pattern}</span>
                </div>
            </div>
            <hr/>
            <div class="field">
                <p>${description_word}: </p>
                <textarea rows="10" cols="100" name="description" placeholder="${description_word}"
                          required id="textarea">${movie.description}</textarea>
            </div>
            <br/>
            <div id="Divcountry">
                <p>${countries_word}: </p>
                <button type="button" onclick="addField('country')">+</button>
                <c:forEach var="country" items="${movie.countries}">
                    <jsp:useBean id="country" class="by.training.epam.bean.Country"/>
                    <div id="Selectcountry">
                        <p id='country${country.id}'>
                            <select class="selectField inline_b countcountry" name="country">
                                <c:forEach var="cou" items="${countries}">
                                    <jsp:useBean id="cou" class="by.training.epam.bean.Country"/>
                                    <c:choose>
                                        <c:when test="${country.name eq cou.name}">
                                            <option value="${cou.id}" selected>${cou.name}</option>
                                        </c:when>
                                        <c:when test="${country.name ne cou.name}">
                                            <option value="${cou.id}"> ${cou.name}</option>
                                        </c:when>
                                    </c:choose>
                                </c:forEach>
                            </select>
                            <button type="button" onclick="deleteField('country', '${country.id}')">-</button>
                        </p>
                    </div>
                </c:forEach>
            </div>
            <div id="Divgenre">
                <p>${genres_word}: </p>
                <button type="button" onclick="addField('genre')">+</button>
                <c:forEach items="${movie.genres}" var="genre">
                <jsp:useBean id="genre" class="by.training.epam.bean.Genre"></jsp:useBean>
                <div id="Selectgenre">
                    <p id='genre${genre.id}'>
                        <select class="selectField inline_b countgenre" name="genre">
                            <c:forEach var="gen" items="${genres}">
                            <jsp:useBean id="gen" class="by.training.epam.bean.Genre"/>
                            <div id="Selectgenre">
                                <c:choose>
                                <c:when test="${genre.name eq gen.name}">
                                <option value="${gen.id}" selected>${gen.name}</option>
                                </c:when>
                                <c:when test="${genre.name ne gen.name}">
                                <option value="${gen.id}"> ${gen.name}</option>
                                </c:when>
                                </c:choose>
                                </c:forEach>
                        </select>
                        <button type="button" onclick="deleteField('genre', '${genre.id}')">-</button>
                    </p>
                </div>
            </div>
            </c:forEach>
    </div>
    <div id="Divperson">
        <p>${participants_word}: </p>
        <button type="button" onclick="addField('person')">+</button>
        <c:forEach var="position" items="${movie.members.keySet()}">
            <jsp:useBean id="position" class="by.training.epam.bean.Position"/>
            <c:forEach var="person" items="${movie.members.get(position)}">
                <jsp:useBean id="person" class="by.training.epam.bean.Person"/>
                <div id="Selectperson">
                    <p id="person${person.id}" class="countperson">
                        <select class="selectField inline_b" name="position">
                            <option value="${position.id}"><c:out value="${position.value}"/></option>
                            <c:forEach var="pos" items="${requestScope.positions}">
                                <jsp:useBean id="pos" class="by.training.epam.bean.Position"/>
                                <option value="${pos.id}"><c:out value="${pos.value}"/></option>
                            </c:forEach>
                        </select>
                        <select class="selectField inline_b" name="person">
                            <option value="${person.id}"><c:out value="${person.name}"/><c:out
                                    value="${person.lastname}"/></option>
                            <c:forEach var="per" items="${requestScope.persons}">
                                <jsp:useBean id="per" class="by.training.epam.bean.Person"/>
                                <option value="${per.id}"><c:out value="${per.name}"/><c:out
                                        value="${per.lastname}"/></option>
                            </c:forEach>
                        </select>
                        <button type="button" onclick="deleteField('person', '${person.id}')">-</button>
                    </p>
                </div>
            </c:forEach>
        </c:forEach>
    </div>
    <input type="submit" value="Ok" class="all-btns"/>
    </form>
    </div>
</section>
</body>
</html>