package by.training.epam.controller.command.impl;

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
 * Class {@code BlockUser} is the class which responsible for reception parameters from {@link HttpServletRequest}
 * and generate attributes for it.
 *
 * @author Sergei Kalashynski
 * @version 1.0
 */
public class BlockUser implements ICommand {
    private static Logger logger = Logger.getLogger(BlockUser.class);
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    /**
     * Override method {@link ICommand#execute(HttpServletRequest, HttpServletResponse)}
     * Redirect parameters to {@link IUserService#blockUser(String)}
     * @return path of jsp page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\" try to block user");
        IUserService IUserService = serviceFactory.getUserServiceImpl();
        try {
            String idUser = request.getParameter(RequestParameter.ID_USER.getValue());
            IUserService.blockUser(idUser);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), "User is block");
            logger.debug(((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) +  " blocked user");
        } catch (ServiceException e) {
            logger.error(((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())), e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
        }
        return JspPageName.INDEX_PAGE.getPath();
    }
}
