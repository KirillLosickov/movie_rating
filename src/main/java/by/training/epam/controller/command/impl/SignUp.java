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

/**
 * Class {@code SignUp} is the class which responsible for reception parameters from {@link HttpServletRequest}
 * and generate attributes for it.
 *
 * @author Sergei Kalashynski
 * @version 1.0
 */
public class SignUp implements ICommand {
    private static Logger logger = Logger.getLogger(SignUp.class);
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    /**
     * Override method {@link ICommand#execute(HttpServletRequest, HttpServletResponse)}
     * Redirect parameters to {@link IUserService#signUp(String, String, String, String, String, String)}
     *
     * @return path of jsp page {@link JspPageName#ADMIN_USER_PAGE}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug(request.getHeader("User-Agent") + " try to sign up account");
        User user = null;
        try {
            //шифровать пароль
            String login = request.getParameter(RequestParameter.LOGIN.getValue());
            //шифровать логин
            String password = request.getParameter(RequestParameter.PASSWORD.getValue());

            String email = request.getParameter(RequestParameter.EMAIL.getValue());
            String name = request.getParameter(RequestParameter.NAME.getValue());
            String lastName = request.getParameter(RequestParameter.LASTNAME.getValue());
            IUserService userService = serviceFactory.getUserServiceImpl();
            user = userService.signUp(login, password, email, name, lastName, request.getLocale().toString());
            request.getSession(true).setAttribute(RequestParameter.LOGIN.getValue(), user.getLogin());
            request.getSession().setAttribute(RequestParameter.POSITION.getValue(), RequestParameter.USER.getValue());
            Cookie cookie = new Cookie(RequestParameter.LOGIN.getValue(), user.getLogin());
            response.addCookie(cookie);
            request.getSession().setAttribute(RequestParameter.USER_LOCAL.getValue(), user.getLocal());
            request.getSession().setAttribute(RequestParameter.PAGE.getValue(), JspPageName.ADMIN_USER_PAGE);
            logger.debug(request.getHeader("User-Agent") + " sign up account as \"" + user.getLogin() + "\"");
        } catch (ServiceException e) {
            logger.error(((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())), e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
        }
        return JspPageName.ADMIN_USER_PAGE.getPath();
    }
}
