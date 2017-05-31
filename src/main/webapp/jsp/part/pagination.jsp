<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table>
    <tr>
        <c:if test="${currentPage != 1}">
            <td>
            <form action="controller" method="get">
                <input type="hidden" name="command" value="show_movies"/>
                <input type="hidden" name="pagination" value="${currentPage - 1}"/>
                <input type="hidden" name="page" value="user_page"/>
                <input type="submit" value="Prev" class="other_button"/>
            </form>
            </td>
        </c:if>
        <c:forEach begin="1" end="${noOfPages}" var="i">
            <c:choose>
                <c:when test="${currentPage eq i}">
                    <td id="current">${i}</td>
                </c:when>
                <c:otherwise>
                <td>
                    <form action="controller" method="get">
                        <input type="hidden" name="command" value="${command}"/>
                        <input type="hidden" name="pagination" value="${i}"/>
                        <input type="hidden" name="page" value="user_page"/>
                        <input type="submit" value="${i}" class="other_button"/>
                    </form>
                </td>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${currentPage lt noOfPages}">
            <td>
            <form action="controller" method="get">
                <input type="hidden" name="command" value="${command}"/>
                <input type="hidden" name="pagination" value="${currentPage + 1}"/>
                <input type="hidden" name="page" value="user_page"/>
                <input type="submit" value="Next" class="other_button"/>
            </form>
            </td>
        </c:if>
    </tr>
</table>