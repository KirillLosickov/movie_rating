package by.training.epam.controller.command.impl;

import by.training.epam.bean.User;
import by.training.epam.controller.command.ICommand;
import by.training.epam.controller.servlet.JspPageName;
import by.training.epam.controller.servlet.RequestParameter;
import by.training.epam.service.IUserService;
import by.training.epam.service.exception.ServiceException;
import by.training.epam.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.training.epam.controller.servlet.RequestParameter.*;

/**
 * Class {@code Redirect} is the class which responsible for reception parameters from {@link HttpServletRequest}
 * and generate attributes for it.
 *
 * @author Sergei Kalashynski
 * @version 1.0
 */
public class Redirect implements ICommand {
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    private static Logger logger = Logger.getLogger(Redirect.class);

    /**
     * Override method {@link ICommand#execute(HttpServletRequest, HttpServletResponse)}

     * @return path of jsp page which depends on user's role.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        //нужно читать данные из сессии, возможно пользователь только что вышел из приложения
        //т.к. мы перенаправляем страницу, то сессия уже создалась, метод HttpSession.isNew() всегда будет возвражать false
        JspPageName jspPageName = null;
        String position = (String) request.getSession().getAttribute(RequestParameter.POSITION.getValue());
        if (position != null) {
            String param = request.getParameter(RequestParameter.INFORMATION.getValue());
            request.setAttribute(RequestParameter.INFORMATION.getValue(), param);
            jspPageName = JspPageName.ADMIN_USER_PAGE;
        } else {
            jspPageName = readCookies(request);
        }


        return jspPageName.getPath();
    }

    /**
     * Logic method which read cookie and respond pth of jsp page.
     * @return path of jsp page
     */
    private JspPageName readCookies(HttpServletRequest request) {
        JspPageName jspPageName = null;
        Cookie cookies[] = request.getCookies();
        boolean hasCookieLogin = false;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(LOGIN.getValue())) {
                hasCookieLogin = true;
                try {
                    IUserService iUserService = serviceFactory.getUserServiceImpl();
                    User user = iUserService.getUserByLogin(cookie.getValue());
                    request.getSession().setAttribute(LOGIN.getValue(), user.getLogin());
                    request.getSession().setAttribute(RequestParameter.USER_LOCAL.getValue(), user.getLocal());
                    if (RequestParameter.ADMIN.getValue().equals(user.getPosition())) {
                        request.getSession().setAttribute(RequestParameter.POSITION.getValue(), ADMIN.getValue()); //делаю
                    }
                    if (RequestParameter.USER.getValue().equals(user.getPosition())) {
                        request.getSession().setAttribute(RequestParameter.POSITION.getValue(), USER.getValue());  //д
                    }
                    jspPageName = JspPageName.ADMIN_USER_PAGE;
                } catch (ServiceException e) {
                    logger.error("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\"", e);
                    request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
                }
                break;
            }
        }
        if (hasCookieLogin == false) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(WELCOME_LOCAL.getValue())) {
                    request.getSession().setAttribute(WELCOME_LOCAL.getValue(), cookie.getName());
                    break;
                }
            }
            jspPageName = JspPageName.WELCOME_PAGE;
        }
        return jspPageName;
    }
}
