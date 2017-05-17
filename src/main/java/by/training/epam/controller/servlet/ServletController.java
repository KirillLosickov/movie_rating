package by.training.epam.controller.servlet;


import by.training.epam.controller.command.CommandProvider;
import by.training.epam.controller.command.ICloseDBCommand;
import by.training.epam.controller.command.ICommand;
import by.training.epam.controller.command.impl.CloseDBCommand;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * The class store objects with properties
 * <B> id </ b>, <b> userLogin </ b>, <b> idMovie </ b> and <b> value </ b>.
 * It implements interface {@see Serializable}
 *
 * @version 1.0
 * @autor Sergei Kalashynski
 */
@WebServlet(name = "Controller",
        urlPatterns = {"/controller"},
        initParams = {@WebInitParam(name = "init_log4j", value = "/WEB-INF/log4j.properties")})
@MultipartConfig
public class ServletController extends HttpServlet {
    private static final String LOG4J_PARAM = "init_log4j";
    private static final long serialVersionUID = 1L;

    private CommandProvider commandProvider = CommandProvider.getInstance();

    /**
     * Create new empty object
     */
    public ServletController() {
        super();
    }

    /**
     * Override init method of {@link HttpServlet#init()}
     * Initialize log4j configuration
     */
    @Override
    public void init() throws ServletException {
        String prefix = getServletContext().getRealPath("");
        String filename = getInitParameter(LOG4J_PARAM);
        if (filename == null) {
            BasicConfigurator.configure();
        } else {
            PropertyConfigurator.configure(prefix + File.separator + filename);
        }
    }

    /**
     * Override doGet method of {@link HttpServlet#doGet(HttpServletRequest, HttpServletResponse)}
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Override doPost method of {@link HttpServlet#doPost(HttpServletRequest, HttpServletResponse)}
     * It call the method {@link ServletController#processRequest(HttpServletRequest, HttpServletResponse)}
     *
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Private method
     * It method get command from request, execute request and forward to other page
     *
     * @throws ServletException
     * @throws IOException
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ICommand iCommand = commandProvider.getCommand(request);
        String page = iCommand.execute(request, response);
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        } else {
            errorMessageDirectlyFromResponse(request, response);
        }
    }

    /**
     * Override destroy method of {@link ServletController#destroy()}
     * It method close database connections.
     */
    @Override
    public void destroy() {
        ICloseDBCommand command = new CloseDBCommand();
        command.closeDB();
    }

    private void errorMessageDirectlyFromResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println(" UNKNOWN ERROR");
    }
}