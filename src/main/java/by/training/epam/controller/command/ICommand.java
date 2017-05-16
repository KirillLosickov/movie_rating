package by.training.epam.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Simple command operation.
 * @author Sergei Kalashynski
 * @version 1.0
 */
public interface ICommand {
    /**
     * Perform a command from http request.
     * @param request the operand to use for getting difference values.
     * @param response the operand to use for getting difference values.
     * @return path of jsp page
     */
    String execute(HttpServletRequest request, HttpServletResponse response);
}