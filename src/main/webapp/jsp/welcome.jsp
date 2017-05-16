<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set value="alert" var="alert"/>
<c:set var="command" value="${command}"/>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/welcome.css" type="text/css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" pageEncoding="utf-8">
    <title>film rating</title>
    <fmt:setLocale scope="session" value="${sessionScope.welcomeLocal}"/>
    <fmt:setLocale scope="request" value="${welcomeLocal}"/>
    <fmt:setBundle basename="by.training.epam.localization.front-end" scope="session" var="loc"/>
    <fmt:message bundle="${loc}" key="local.button.name.en" var="en_button"/>
    <fmt:message bundle="${loc}" key="local.button.name.ru" var="ru_button"/>
    <fmt:message bundle="${loc}" key="local.word.signIn" var="word_sign_in"/>
    <fmt:message bundle="${loc}" key="local.word.signUp" var="word_sign_up"/>
    <fmt:message bundle="${loc}" key="local.word.sign_in_on_website" var="sign_in_on_website"/>
    <fmt:message bundle="${loc}" key="local.word.lastname" var="lastname"/>
    <fmt:message bundle="${loc}" key="local.word.name" var="name"/>
    <fmt:message bundle="${loc}" key="local.word.pickUsername" var="pick_username"/>
    <fmt:message bundle="${loc}" key="local.word.createPassword" var="create_password"/>
    <fmt:message bundle="${loc}" key="local.word.sign_up_on_website" var="sign_up_on_website"/>
    <fmt:message bundle="${loc}" key="local.word.show-hide" var="show_hide"/>
    <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
    <script src="${pageContext.request.contextPath}/js/index.js"></script>
</head>
<body>
    <div class="banner">
        <div class="container-buttons">
            <form action="controller" method="post">
                <input class="lang_ru" type="hidden" name="command" value="change_local"/>
                <input class="lang_ru" type="hidden" name="local" value="ru"/>
                <input type="hidden" name="page" value="welcome_page"/>
                <input class="lang_ru" type="submit" value="${ru_button}"/>
            </form>
            <form action="controller" method="post">
                <input class="lang_en" type="hidden" name="command" value="change_local"/>
                <input class="lang_en" type="hidden" name="local" value="en"/>
                <input type="hidden" name="page" value="welcome_page"/>
                <input class="lang_en" type="submit" value="${en_button}"/>
            </form>
        </div>
        <input type="button" value="${show_hide}" id="show_form"/>
    </div>
    <div id="main">
        <input type="button" value="${word_sign_in}" id="butIn"/>
        <input type="button" value="${word_sign_up}" id="butUp"/>
        <div id="blockIn">
            <h1 style="color: white;" align="center">${word_sign_in}</h1><br/>
            <form class="autorization_form" action="controller" accept-charset="utf-8" method="post">
                <input type="hidden" name="command" value="sign_in"/>
                <input class="input_field" type="text" name="login" required placeholder="${pick_username}"/><br/>
                <input class="input_field" type="password" name="password" required placeholder="${create_password}"/><br/>
                <input class="input_field" type="submit"  value="${sign_in_on_website}"/>
            </form>
            <p class="error_message">${requestScope.information}</p>
        </div>
        <div id="blockUp">
            <h1 style="color: white;" align="center">${word_sign_up}</h1>
            <form class="regisration-form" action="controller" accept-charset="utf-8" method="post">
                <input type="hidden" name="command" value="sign_up"/>
                <input class="input_field" type="text" name="login" required placeholder="${pick_username}"/><br/>
                <input class="input_field" type="password" name="password" required placeholder="${create_password}"/><br/>
                <input class="input_field" type="email" name="email" required placeholder="email"/><br/>
                <input class="input_field" type="text" name="name" required placeholder="${name}"/><br/>
                <input class="input_field" type="text" name="lastname" required placeholder="${lastname}"/><br/>
                <input class="input_field" type="submit"  value="${sign_up_on_website}"/>
            </form>
        </div>
    </div>
</body>
</html>