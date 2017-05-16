<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale scope="session" value="${sessionScope.userLocal}"/>
<fmt:setBundle basename="by.training.epam.localization.front-end" scope="session" var="loc"/>
<fmt:message bundle="${loc}" key="local.button.films" var="films_button"/>
<fmt:message bundle="${loc}" key="local.button.tickets" var="tickets_button"/>
<fmt:message bundle="${loc}" key="local.button.account" var="account_button"/>
<fmt:message bundle="${loc}" key="local.button.users" var="users_button"/>
<fmt:message bundle="${loc}" key="local.button.sortByDate" var="sort_by_date_button"/>
<fmt:message bundle="${loc}" key="local.button.sortByRaiting" var="sort_by_raiting_button"/>
<fmt:message bundle="${loc}" key="local.button.addFilm" var="add_film_button"/>
<c:set var="position" value="${sessionScope.position}"/>
<c:set var="admin" value="admin"/>
<c:set var="user" value="user"/>
<div class="menu">
    <form class="menu-horizontally" action="controller" method="post">
        <input type="hidden" name="command" value="show_movies"/>
        <input type="hidden" name="pagination" value="1"/>
        <input type="submit" value="${films_button}" class="input_class navigation"/>
    </form>
    <!--<div class="banner-btn lang_ru">
        <a href="${pageContext.request.contextPath}?command=show_movies&pagination=1">${films_button}</a>
    </div>-->
    <c:choose>
        <c:when test="${position eq (admin)}">
            <form class="menu-horizontally" action="controller" method="post">
                <input type="hidden" name="command" value="show_users"/>
                <input type="submit" value="${users_button}" class="input_class navigation"/>
            </form>
            <!--<div class="banner-btn lang_ru">
                <a href="${pageContext.request.contextPath}?command=show_users">${users_button}</a>
            </div>-->
        </c:when>
        <c:when test="${position eq (user)}">
            <form class="menu-horizontally" action="controller" method="post">
                <input type="hidden" name="command" value="personal_user">
                <input type="hidden" name="login" value="${sessionScope.login}">
                <input type="submit" value="${account_button}" class="input_class navigation">
            </form>
            <!--<div class="banner-btn lang_ru">
                <a href="${pageContext.request.contextPath}?command=personal_user&login=${sessionScope.login}">${account_button}</a>
            </div>-->
        </c:when>
    </c:choose>
    <c:if test="${position eq (admin)}">
        <form class="menu-horizontally" action="controller" method="post">
            <input type="hidden" name="command" value="show_page_for_add_movie"/>
            <input class="input_class navigation" type="submit" value="${add_film_button}"/>
        </form>
        <!--<div class="banner-btn lang_ru">
            <a href="${pageContext.request.contextPath}?command=show_page_for_add_movie">${add_film_button}</a>
        </div>-->
    </c:if>
    <c:if test="${movieList ne (null)}">
        <form class="menu-vertically" action="controller" method="post">
            <input type="hidden" name="command" value="sort_films_by_rating"/>
            <input type="hidden" name="pagination" value="1"/>
            <input type="submit" value="${sort_by_raiting_button}" class="input_class sort"/>
        </form>
        <form class="menu-vertically" action="controller" method="post">
            <input type="hidden" name="command" value="sort_films_by_date"/>
            <input type="hidden" name="pagination" value="1"/>
            <input type="submit" value="${sort_by_date_button}" class="input_class sort"/>
        </form>
        <!--<div class="banner-btn lang_ru">
            <a href="${pageContext.request.contextPath}?command=sort_films_by_rating&pagination=1">${sort_by_raiting_button}</a>
        </div>
        <div class="banner-btn lang_ru">
            <a href="${pageContext.request.contextPath}?command=sort_films_by_date&pagination=1">${sort_by_date_button}</a>
        </div>-->
    </c:if>
</div>
