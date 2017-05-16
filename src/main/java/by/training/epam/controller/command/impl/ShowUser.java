package by.training.epam.controller.command.impl;

import by.training.epam.bean.User;
import by.training.epam.controller.command.ICommand;
import by.training.epam.controller.servlet.JspPageName;
import by.training.epam.controller.servlet.RequestParameter;
import by.training.epam.service.IUserService;
import by.training.epam.service.exception.ServiceException;
import by.training.epam.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class {@code ShowUser} is the class which responsible for reception parameters from {@link HttpServletRequest}
 * and generate attributes for it.
 *
 * @author Sergei Kalashynski
 * @version 1.0
 */
public class ShowUser implements ICommand {
    private static Logger logger = Logger.getLogger(ShowUser.class);

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    /**
     * Override method {@link ICommand#execute(HttpServletRequest, HttpServletResponse)}
     * Redirect parameters to {@link IUserService#getUserByLogin(String)}
     *
     * @return path of jsp page {@link JspPageName#ADMIN_USER_PAGE}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\" try to show user");
        try {
            String loginUser = request.getParameter(RequestParameter.LOGIN.getValue());
            IUserService iUserService = serviceFactory.getUserServiceImpl();
            User user = iUserService.getUserByLogin(loginUser);
            request.setAttribute(RequestParameter.PERSON.getValue(), user);
            logger.debug("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\" showed user");
        } catch (ServiceException e) {
            logger.error("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\"", e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
        }
        return JspPageName.PERSONAL_USER_PAGE.getPath();
    }
}
