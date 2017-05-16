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
import java.util.List;

/**
 * Class {@code ShowUsers} is the class which responsible for reception parameters from {@link HttpServletRequest}
 * and generate attributes for it.
 *
 * @author Sergei Kalashynski
 * @version 1.0
 */
public class ShowUsers implements ICommand {
    private static Logger logger = Logger.getLogger(ShowUsers.class);
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    /**
     * Override method {@link ICommand#execute(HttpServletRequest, HttpServletResponse)}
     * Redirect parameters to {@link IUserService#getUserByLogin(String)}
     *
     * @return path of jsp page {@link JspPageName#USERS_PAGE}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.debug("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\" try to show users");
            List<User> userList = serviceFactory.getUserServiceImpl().getAllUsers();
            request.setAttribute(RequestParameter.USER_LIST.getValue(), userList);
            logger.debug("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\" showed users");
        } catch (ServiceException e) {
            logger.error("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\"", e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
        }
        return JspPageName.USERS_PAGE.getPath();
    }
}
