<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="banner">
    <form action="controller" method="post">
        <input type="hidden" name="command" value="show_main_page"/>
        <input class="banner-btn image-logo" type="submit" value=""/>
    </form>
    <form action="controller" method="post" accept-charset="UTF-8">
        <input type="hidden" name="command" value="exit"/>
        <input class="banner-btn quit" type="submit" value=""/>
    </form>
    <form action="controller" method="post">
        <input type="hidden" name="command" value="change_local"/>
        <input type="hidden" name="local" value="ru"/>
        <input type="hidden" name="page" value="admin_user_page"/>
        <input class="banner-btn lang_ru" type="submit" value=""/>
    </form>
    <form action="controller" method="post">
        <input type="hidden" name="command" value="change_local"/>
        <input type="hidden" name="local" value="en"/>
        <input type="hidden" name="page" value="admin_user_page"/>
        <input class="banner-btn lang_en" type="submit" value=""/>
    </form>
    <br/><br/><br/><br/><br/><br/>
</div>