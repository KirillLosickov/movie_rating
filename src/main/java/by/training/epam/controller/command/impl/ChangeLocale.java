package by.training.epam.controller.command.impl;

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

/**
 * Class {@code ChangeLocale} is the class which responsible for change user's locale.
 *
 * @author Sergei Kalashynski
 * @version 1.0
 */
public class ChangeLocale implements ICommand {

    private static Logger logger = Logger.getLogger(ChangeLocale.class);
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    /**
     * Override method {@link ICommand#execute(HttpServletRequest, HttpServletResponse)}
     * It method has logic appointment for reading cookies and session.
     * @return path of jsp page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\" try to change locale");
        IUserService IUserService = serviceFactory.getUserServiceImpl();
        String local = request.getParameter(RequestParameter.LOCAL.getValue());
        String login = (String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue());
        JspPageName page = JspPageName.valueOf(((String) request.getParameter("page")).toUpperCase());
        Cookie cookies[] = request.getCookies();
        try {
            if (login != null) {
                IUserService.updateLocale(login, local);
                request.getSession().setAttribute(RequestParameter.USER_LOCAL.getValue(), local);
            }
            else { //сессия есть, но пользователь не входил в аакаунт, сесссия не соделжит никкаких данных
                //читаем куки
                boolean hasCookie = false;
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(RequestParameter.LOGIN.getValue())) {
                        hasCookie = true;
                        IUserService.updateLocale(cookie.getValue(), local);
                        request.getSession().setAttribute(RequestParameter.USER_LOCAL.getValue(), local);
                        break;
                    }
                }
                if (hasCookie == false) {
                    //или сообщить об ошибке, что закончилась сессия --
                    request.getSession().setAttribute(RequestParameter.WELCOME_LOCAL.getValue(), local);
                    Cookie cookieWelcomeLocal = new Cookie(RequestParameter.WELCOME_LOCAL.getValue(), local);
                    response.addCookie(cookieWelcomeLocal);
                    page = JspPageName.WELCOME_PAGE;
                }
            }
            logger.debug(((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + " changed locale");
        } catch (ServiceException e) {
            logger.error(((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())), e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
        }
        return page.getPath();
    }
}

