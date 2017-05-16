package by.training.epam.controller.command.impl;

import by.training.epam.controller.command.ICommand;
import by.training.epam.controller.servlet.JspPageName;
import by.training.epam.controller.servlet.RequestParameter;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.training.epam.controller.servlet.RequestParameter.LOGIN;
import static by.training.epam.controller.servlet.RequestParameter.WELCOME_LOCAL;

/**
 * Class {@code ExitFromAccount} is the class which responsible for reception parameters from {@link HttpServletRequest}
 * and generate attributes for it.
 *
 * @author Sergei Kalashynski
 * @version 1.0
 */
public class ExitFromAccount implements ICommand {
    private static Logger logger = Logger.getLogger(ExitFromAccount.class);

    /**
     * Override method {@link ICommand#execute(HttpServletRequest, HttpServletResponse)}
     * It method has logic appointment for reading cookies and session.
     * @return path of jsp page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(LOGIN.getValue())) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                request.getSession().invalidate();
            }
            //Нужно для установления локали на welcomePage, там где нет сессии после выхода
            if (cookie.getName().equals(WELCOME_LOCAL.getValue())) {
                request.setAttribute(WELCOME_LOCAL.getValue(),cookie.getValue());
            }
        }
        logger.debug("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\" came out");
        return JspPageName.WELCOME_PAGE.getPath();
    }
}
