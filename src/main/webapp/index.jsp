<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Redirect</title>
</head>
<script>
    function doRedirect() {
        atTime = "1";
        toUrl = "url";
        setTimeout(document.getElementById("redirect").click(), atTime);
    }
</script>
<body onload="doRedirect();">
Loading...
<form class="myform" action="controller" method="post">
    <input type-="hidden" name="information" value="${requestScope.information}"/>
    <input type="hidden" name="command" value="redirect"/>
    <input id="redirect" style="visibility:hidden;" type="submit"/>
</form>
</body>
</html>