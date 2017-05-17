<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale scope="session" value="${sessionScope.userLocal}"/>
<fmt:message bundle="${loc}" key="local.word.exception" var="exceptionLocal"/>
<fmt:message bundle="${loc}" key="local.word.exceptionMessage" var="exceptionMessageLocal"/>
<fmt:message bundle="${loc}" key="local.word.servletName" var="servletNameLocal"/>
<fmt:message bundle="${loc}" key="local.word.statusCode" var="statusCodeLocal"/>
<fmt:message bundle="${loc}" key="local.sentence.sorry_error" var="sentence"/>
<fmt:message bundle="${loc}" key="local.sentence.request_failed" var="sentenceRequestFailed"/>
<fmt:message bundle="${loc}" key="local.word.redirect" var="redirectWord"/>
<!DOCTYPE html>
<html>
<head>
    <title>Error Page</title>
</head>
<body>
<h1>${sentence}</h1>
<p>${information}</p>
<c:if test="${pageContext.errorData.requestURI ne null}">
    ${sentenceRequestFailed} ${pageContext.errorData.requestURI}<br/>
</c:if>
<c:if test="${pageContext.errorData.servletName ne null}">
    ${servletNameLocal}: ${pageContext.errorData.servletName}<br/>
</c:if>
<c:if test="${pageContext.errorData.statusCode ne null}">
    ${statusCodeLocal}: ${pageContext.errorData.statusCode}<br/>
</c:if>
<c:if test="${pageContext.exception ne null}">
    ${exceptionLocal}: ${pageContext.exception}<br/>
</c:if>
<c:if test="${pageContext.exception.message ne null}">
    ${exceptionMessageLocal}: ${pageContext.exception.message}<br/>
</c:if>
<form action="controller" method="post">
    <input type="hidden" name="command" value="redirect"/>
    <input type="submit" value="${redirectWord}"/>
</form>
</body>
</html>