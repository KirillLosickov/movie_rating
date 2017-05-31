<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale scope="session" value="${sessionScope.userLocal}"/>
<fmt:setBundle basename="by.training.epam.localization.front-end" scope="session" var="loc"/>
<fmt:message bundle="${loc}" key="local.button.edit" var="edit_button"/>
<fmt:message bundle="${loc}" key="local.button.reestablish" var="reestablish_button"/>
<fmt:message bundle="${loc}" key="local.button.remove" var="remove_button"/>
<fmt:message bundle="${loc}" key="local.word.releaseDate" var="releaseDate_word"/>
<fmt:message bundle="${loc}" key="local.word.restrictionAge" var="restrictionAge_word"/>
<fmt:message bundle="${loc}" key="local.word.duration" var="duration_word"/>
<fmt:message bundle="${loc}" key="local.question.deleted" var="deleted_question"/>
<fmt:message bundle="${loc}" key="local.word.type" var="type_word"/>
<fmt:message bundle="${loc}" key="local.word.averageMark" var="averageMark_word"/>
<c:set var="position" value="${sessionScope.position}"/>
<c:set var="admin" value="admin"/>
<c:set var="user" value="user"/>
<c:forEach var="movie" items="${movieList}">
    <div class="main">
        <form action="controller" method="get">
            <input type="hidden" name="command" value="show_movie"/>
            <input type="hidden" name="id_movie" value="${movie.id}"/>
            <input id="film_name" type="submit" value="${movie.title}"/>
        </form>
        <c:if test="${movie.imageName ne null}">
            <form class="poster" action="controller" method="get">
                <input type="hidden" name="command" value="show_movie"/>
                <input type="hidden" name="id_movie" value="${movie.id}"/>
                <input id="film_img" type="image"
                       src="${pageContext.request.contextPath}/images/films/${movie.imageName}" alt="${movie.title}"/>
            </form>
        </c:if>
        <p><b>${releaseDate_word}: </b>${movie.releaseDate}</p>
        <p><b>${restrictionAge_word}:</b> ${movie.restrictionAge}+</p>
        <p><b>${type_word}: </b>${movie.type}</p>
        <fmt:formatDate type="time" timeStyle="short" value="${movie.duration}" var="theFormattedDate"/>
        <p><b>${duration_word} :</b>${theFormattedDate}</p>
        <p><b>${averageMark_word}: </b>${movie.averageMark}</p>
        <c:if test="${position eq (admin)}">
            <p><b>${deleted_question}: </b> <c:out value="${movie.deleted ? 'Yes' : 'No'}"/></p>
        </c:if>
        <p id="description"><c:out value="${movie.description}"/></p>
        <div id="button_under_film">
            <c:choose>
                <c:when test="${position eq(admin)}">
                    <form class="button" action="controller" method="get">
                        <input type="hidden" name="id_movie" value="${movie.id}"/>
                        <input type="hidden" name="command" value="show_page_for_edit_movie"/>
                        <input class="all-btns" type="submit" value="${edit_button}"/>
                    </form>
                    <c:choose>
                        <c:when test="${movie.deleted eq (true)}">
                            <form class="button" action="controller" method="post">
                                <input type="hidden" name="id_movie" value="${movie.id}"/>
                                <input type="hidden" name="command" value="regain_movie"/>
                                <input type="hidden" name="pagination" value="${currentPage}"/>
                                <input class="all-btns  " type="submit" value="${reestablish_button}"
                                       onclick="confirm('Вы уверены?')"/>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <form class="button" action="controller" method="post">
                                <input type="hidden" name="id_movie" value="${movie.id}"/>
                                <input type="hidden" name="command" value="remove_movie"/>
                                <input type="hidden" name="pagination" value="${currentPage}"/>
                                <input class="all-btns" type="submit" value="${remove_button}"
                                       onclick="confirm('Вы уверены?')"/>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:when test="${position eq(user)}">
                </c:when>
                <c:otherwise>
                    ERROR
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</c:forEach>
