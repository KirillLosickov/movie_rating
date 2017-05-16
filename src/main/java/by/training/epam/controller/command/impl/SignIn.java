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

import static by.training.epam.controller.servlet.RequestParameter.LOGIN;

/**
 * Class {@code SignIn} is the class which responsible for reception parameters from {@link HttpServletRequest}
 * and generate attributes for it.
 *
 * @author Sergei Kalashynski
 * @version 1.0
 */
public class SignIn implements ICommand {
    private static Logger logger = Logger.getLogger(SignIn.class);
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    /**
     * Override method {@link ICommand#execute(HttpServletRequest, HttpServletResponse)}
     * It method has logic appointment for reading cookies and session.
     * @return path of jsp page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug(request.getHeader("User-Agent") + " try to sign in account");
        JspPageName jspPageName = JspPageName.WELCOME_PAGE;
        String login = request.getParameter(RequestParameter.LOGIN.getValue());
        String password = request.getParameter(RequestParameter.PASSWORD.getValue());

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            //наша БД не позволит найти пользователя с login = null и пароль = null,
            //но если мы пишем приложение, где не знаем как устроена БД, то нужно проверять
            request.setAttribute("errorData", "введите логин или пароль");
            return jspPageName.getPath();
        }
        try {
            IUserService iUserService = serviceFactory.getUserServiceImpl();
            User user = iUserService.signIn(login, password);
            if (!user.isBlock()) {
                if (user.getPosition() != null) {
                    Cookie cookieLogin = new Cookie(RequestParameter.LOGIN.getValue(), user.getLogin());
                    response.addCookie(cookieLogin);
                    request.getSession(true).setAttribute(RequestParameter.LOGIN.getValue(), user.getLogin());
                    if (user.getPosition().equals(RequestParameter.ADMIN.getValue())) {
                        request.getSession().setAttribute(RequestParameter.POSITION.getValue(), RequestParameter.ADMIN.getValue());
                    } else {
                        request.getSession().setAttribute(RequestParameter.POSITION.getValue(), RequestParameter.USER.getValue());
                    }
                    request.getSession().setAttribute(LOGIN.getValue(), user.getLogin());
                    request.getSession().setAttribute(RequestParameter.PAGE.getValue(), JspPageName.ADMIN_USER_PAGE);
                    jspPageName = JspPageName.ADMIN_USER_PAGE;
                    logger.debug(request.getHeader("User-Agent") + " sign in account as \"" + user.getLogin()+"\"");
                } else {
                    logger.info(request.getHeader("User-Agent") + " unsuccessfully sign in account ");
                    request.setAttribute(RequestParameter.INFORMATION.getValue(), "error of input login or password");
                }
            } else {
                logger.info(request.getHeader("User-Agent") + " unsuccessfully sign in account. Account is block");
                request.setAttribute(RequestParameter.INFORMATION.getValue(), "account is block");
            }
        } catch (ServiceException e) {
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
            logger.error(((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())), e);
        }
        return jspPageName.getPath();
    }
}
