<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="position" value="${sessionScope.position}"/>
<c:set var="admin" value="admin"/>
<c:set var="user" value="user"/>
<!DOCTYPE html>
<html>
<head>
    <title>Movie</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/partial.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/banner.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/menu.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/movie.css" type="text/css"/>

    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" pageEncoding="utf-8" charset="utf-8">
    <title>film rating</title>
    <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
    <fmt:setLocale scope="session" value="${sessionScope.userLocal}"/>
    <fmt:setBundle basename="by.training.epam.localization.front-end" scope="session" var="loc"/>
    <fmt:message bundle="${loc}" key="local.word.averageMark" var="averageMark_word"/>
    <fmt:message bundle="${loc}" key="local.word.countries" var="countries_word"/>
    <fmt:message bundle="${loc}" key="local.word.genres" var="genres_word"/>
    <fmt:message bundle="${loc}" key="local.word.duration" var="duration_word"/>
    <fmt:message bundle="${loc}" key="local.word.budget" var="budget_word"/>
    <fmt:message bundle="${loc}" key="local.word.releaseDate" var="premiere_word"/>
    <fmt:message bundle="${loc}" key="local.word.restrictionAge" var="restrictionAge_word"/>
    <fmt:message bundle="${loc}" key="local.word.type" var="type_word"/>
    <fmt:message bundle="${loc}" key="local.word.profit" var="profit_word"/>
    <fmt:message bundle="${loc}" key="local.word.description" var="description_word"/>
    <fmt:message bundle="${loc}" key="local.word.proposition_for_estimate" var="proposition_word"/>
    <fmt:message bundle="${loc}" key="local.word.your_comment" var="your_comment_word"/>
    <fmt:message bundle="${loc}" key="local.button.remove" var="remove_button"/>
    <fmt:message bundle="${loc}" key="local.button.submit" var="submit_button"/>
</head>
<body>
<%@include file="/jsp/part/banner.jsp"%>
<%@include file="/jsp/part/menu.jsp"%>
<section class="main">
    <jsp:useBean id="movie" class="by.training.epam.bean.Movie" scope="request"/>
    <h1 align="center" id="filmNameMovies">${movie.title}</h1>
    <img src="${pageContext.request.contextPath}/images/films/${movie.imageName}" alt="Poster" id="img_film">
    <div>
        <p><span id="average_rating">${averageMark_word}: ${movie.averageMark}</span>
            <span id="rating_mark">${requestScope.averageMark}</span>
        </p>
        <p><span class="descrip_field">${countries_word} :</span>
            <c:forEach var="country" items="${movie.countries}">
                <jsp:useBean id="country" class="by.training.epam.bean.Country"></jsp:useBean>
                <span style="padding:0px 10px;"><c:out value="${country.name}"/></span>
            </c:forEach>
        </p>
        <p><span class="descrip_field">${genres_word} :</span>
            <c:forEach items="${movie.genres}" var="genre">
                <jsp:useBean id="genre" class="by.training.epam.bean.Genre"></jsp:useBean>
                <c:out value="${genre.name}"/>
            </c:forEach>
            <fmt:formatDate type="time" timeStyle="short" value="${movie.duration}" var="theFormattedDate"/>
        <p><span class="descrip_field">${duration_word} :</span>${theFormattedDate}</p>
        <p><span class="descrip_field">${profit_word} :</span> $${movie.profit}</p>
        <p><span class="descrip_field">${premiere_word} :</span> ${movie.releaseDate}</p>
        <p><span class="descrip_field">${restrictionAge_word} :</span> ${movie.restrictionAge}</p>
        <p><span class="descrip_field">${budget_word} :</span> $${movie.budget}</p>
        <p><span class="descrip_field">${type_word} :</span> ${movie.type}</p>
        <c:forEach var="position" items="${movie.members.keySet()}">
            <jsp:useBean id="position" class="by.training.epam.bean.Position"/>
            <p><b><c:out value="${position.value}"/>:</b>
                <c:forEach var="person" items="${movie.members.get(position)}">
                    <jsp:useBean id="person" class="by.training.epam.bean.Person"/>
                    <span style="padding:0px 10px;"><c:out value="${person.name}"/> <c:out
                            value="${person.lastname}"/></span>
                </c:forEach>
            </p>
        </c:forEach>
        <p><span class="descrip_field">${description_word} :</span></p>
        <p class="infoAboutFilm">${movie.description}</p>
    </div>
    <form action="controller" method="post" id="ratingForm">
        <hr/>
        <label for="rating">${proposition_word}:</label>
        <input type="hidden" name="command" value="add_statistic"/>
        <input type="hidden" name="id_movie" value="${movie.id}"/>
        <input type="text" name="mark" id="rating" pattern="^[1-9]?$|10" class="addField">
        <input type="hidden" name="login" value="${sessionScope.login}"/>
        <textarea name="comment" cols="80" rows="5" placeholder="${your_comment_word}"
                  id="commentField"></textarea><br>
        <input type="submit" class="all-btns" value="${submit_button}"/>
    </form>
</section>
<section id="sectionComments">
    <c:forEach var="comment" items="${comments}">
        <jsp:useBean id="comment" class="by.training.epam.bean.Comment"/>
        <div class="divComment">
            <p class="nameComment"><c:out value="${comment.userLogin}"/></p>
            <p><c:out value="${comment.value}"/></p>
            <c:if test="${position eq (admin)}">
                <form action="controller" method="post">
                    <input type="hidden" name="command" value="remove_comment"/>
                    <input type="hidden" name="id_comment" value="${comment.id}"/>
                    <input type="submit" value="${remove_button}" class="all-btns">
                </form>
            </c:if>
        </div>
    </c:forEach>
</section>
</body>
</html>