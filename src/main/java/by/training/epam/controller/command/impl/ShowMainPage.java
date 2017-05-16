package by.training.epam.controller.command.impl;

import by.training.epam.controller.command.ICommand;
import by.training.epam.controller.servlet.JspPageName;
import by.training.epam.service.IUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This simple class {@code ShowMainPage} responsible for reception parameters from {@link HttpServletRequest}
 * and generate attributes for it.
 *
 * @author Sergei Kalashynski
 * @version 1.0
 */
public class ShowMainPage implements ICommand {

    /**
     * Override method {@link ICommand#execute(HttpServletRequest, HttpServletResponse)}
     * Redirect parameters to {@link IUserService#setUserRight(String)}
     *
     * @return path of jsp page {@link JspPageName#ADMIN_USER_PAGE}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return JspPageName.ADMIN_USER_PAGE.getPath();
    }
}