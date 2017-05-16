package by.training.epam.controller.command;

import by.training.epam.controller.command.impl.*;
import by.training.epam.controller.servlet.RequestParameter;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public final class CommandProvider {
    private static Logger logger = Logger.getLogger(CommandProvider.class);

    private final static CommandProvider instance = new CommandProvider();
    private Map<CommandName, ICommand> repository = new HashMap<>();

    private CommandProvider() {
        repository.put(CommandName.SIGN_IN, new SignIn());
        repository.put((CommandName.SIGN_UP), new SignUp());
        repository.put(CommandName.SHOW_MOVIES, new ShowMovies());
        repository.put(CommandName.CHANGE_LOCAL, new ChangeLocale());
        repository.put(CommandName.EXIT, new ExitFromAccount());
        repository.put(CommandName.REDIRECT, new Redirect());
        repository.put(CommandName.SHOW_MOVIE, new ShowMovie());
        repository.put(CommandName.ADD_MOVIE, new AddMovie());
        repository.put(CommandName.SHOW_PAGE_FOR_EDIT_MOVIE, new ShowPageForEditMovie());
        repository.put(CommandName.SHOW_PAGE_FOR_ADD_MOVIE, new ShowPageForAddMovie());
        repository.put(CommandName.REMOVE_MOVIE, new SetRemoveMovie());
        repository.put(CommandName.REGAIN_MOVIE, new SetUnRemovedMovie());
        repository.put(CommandName.SHOW_USERS, new ShowUsers());
        repository.put(CommandName.PERSONAL_USER, new ShowUser());
        repository.put(CommandName.SORT_FILMS_BY_DATE, new SortMoviesByDate());
        repository.put(CommandName.ADD_STATISTIC, new AddStatistic());
        repository.put(CommandName.SORT_FILMS_BY_RATING, new SortMoviesByRating());
        repository.put(CommandName.BLOCK_USER, new BlockUser());
        repository.put(CommandName.UNBLOCK_USER, new UnblockUser());
        repository.put(CommandName.REMOVE_COMMENT, new RemoveComment());
        repository.put(CommandName.WRONG_REQUEST, new WrongRequest());
        repository.put(CommandName.EDIT_MOVIE, new EditMovie());
        repository.put(CommandName.SET_ADMIN_RIGHT, new SetAdminRight());
        repository.put(CommandName.SET_USER_RIGHT, new SetUserRight());
        repository.put(CommandName.SHOW_MAIN_PAGE, new ShowMainPage());
        repository.put(CommandName.CHANGE_USER_RATING, new ChangeUserRating());
    }

    public static CommandProvider getInstance() {
        return instance;
    }

    public ICommand getCommand(HttpServletRequest request) {
        ICommand iCommand = repository.get(CommandName.WRONG_REQUEST);
        String command = request.getParameter(RequestParameter.COMMAND.getValue());
        try {
            CommandName commandName = CommandName.valueOf(command.toUpperCase().replace('-', '_'));
            iCommand = repository.get(commandName);
        } catch (IllegalArgumentException e) {
            request.setAttribute("wrongAction", e.getMessage());
            logger.error(((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())), e);
        }
        return iCommand;
    }
}
