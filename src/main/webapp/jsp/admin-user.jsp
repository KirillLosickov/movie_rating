<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="position" value="${sessionScope.position}"/>
<c:set var="admin" value="admin"/>
<c:set var="user" value="user"/>
<c:set var="show_movies" value="show_movies"/>
<c:set var="sort_films_by_date" value="sort_films_by_date"/>
<c:set var="sort_films_by_rating" value="sort_films_by_rating"/>
<c:set value="alert" var="alert"/>
<c:set var="command" value="${command}"/>
<!DOCTYPE html>
<html>
<head>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/partial.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/banner.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/menu.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/movies.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagination.css" type="text/css"/>

    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" pageEncoding="utf-8">
    <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
    <fmt:setLocale scope="session" value="${sessionScope.userLocal}"/>
    <fmt:setBundle basename="by.training.epam.localization.front-end" scope="session" var="loc"/>
   <c:choose>
       <c:when test="${position eq(admin)}">
           <title>administrator</title>
       </c:when>
       <c:when test="${position eq(user)}">
           <title>user</title>
       </c:when>
       <c:otherwise>
           ERROR
       </c:otherwise>
   </c:choose>
</head>
<body>
<%@include file="/jsp/part/banner.jsp"%>
<%@include file="/jsp/part/menu.jsp"%>
<c:choose>
    <c:when test="${command eq(show_movies)||command eq(sort_films_by_date)|| command eq (sort_films_by_rating)}">
       <jsp:include page="/jsp/part/showMovies.jsp"/>
        <div class="pagination">
            <%@include file="/jsp/part/pagination.jsp" %>
        </div>
    </c:when>
    <c:otherwise>
        <h4><c:out value="${requestScope.information}"/></h4>
    </c:otherwise>
</c:choose>
</body>
</html>