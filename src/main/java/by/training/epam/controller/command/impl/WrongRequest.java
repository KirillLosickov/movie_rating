package by.training.epam.controller.command.impl;

import by.training.epam.controller.command.ICommand;
import by.training.epam.controller.servlet.JspPageName;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class {@code WrongRequest} is the class which responsible for reception parameters from {@link HttpServletRequest}
 * and generate attributes for it.
 *
 * @author Sergei Kalashynski
 * @version 1.0
 */
public class WrongRequest implements ICommand {
    /**
     * Override method {@link ICommand#execute(HttpServletRequest, HttpServletResponse)}
     * @return path of jsp page {@link JspPageName#ERROR_PAGE }
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return JspPageName.ERROR_PAGE.getPath();
    }
}
