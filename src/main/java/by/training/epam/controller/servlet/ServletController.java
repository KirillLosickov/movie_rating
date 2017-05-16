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
import java.io.PrintWriter;

/**
 * The class store objects with properties
 * <B> id </ b>, <b> userLogin </ b>, <b> idMovie </ b> and <b> value </ b>.
 * It implements interface {@see Serializable}
 *
 * @version 1.0
 * @autor Sergei Kalashynski
 */
@WebServlet(name = "Controller",
        urlPatterns = {"/login", ""},
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
        /* response.setContentType("text/html");
        response.getWriter().println("E R R O R");
        */

        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
        if (servletName == null) {
            servletName = "Unknown";
        }
        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "Unknown";
        }

        // Set response content type
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.write("<html><head><title>Exception/Error Details</title></head><body>");
        if (statusCode != 500) {
            out.write("<h3>Error Details</h3>");
            out.write("<strong>Status Code</strong>:" + statusCode + "<br>");
            out.write("<strong>Requested URI</strong>:" + requestUri);
        } else {
            out.write("<h3>Exception Details</h3>");
            out.write("<ul><li>ServletController Name:" + servletName + "</li>");
            out.write("<li>Exception Name:" + throwable.getClass().getName() + "</li>");
            out.write("<li>Requested URI:" + requestUri + "</li>");
            out.write("<li>Exception Message:" + throwable.getMessage() + "</li>");
            out.write("</ul>");
        }
        out.write("<br><br>");
        out.write("<a href=\"index.html\">Home Page</a>");
        out.write("</body></html>");
    }
}