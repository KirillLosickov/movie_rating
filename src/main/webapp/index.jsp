<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Movie rating</title>
    <style>
        .descr { visibility: hidden; }
    </style>
</head>
<script>
    function doRedirect() {
        atTime = "1";
        toUrl = "url";
        setTimeout(document.getElementById("redirect").click(), atTime);
    }
</script>
<body onload="doRedirect();">
<form class="descr" action="controller" method="post">
    <input type-="hidden" name="information" value="${requestScope.information}"/>
    <input type="hidden" name="command" value="redirect"/>
    <input id="redirect" type="submit"/>
</form>
</body>
</html>