package by.training.epam.filters;

import javax.servlet.*;
import java.io.IOException;

public class CharsetFilter implements Filter {
    private String encoding;
    private ServletContext servletContext;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        servletContext.log("charset is set");
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig fConfig) {
        encoding = fConfig.getInitParameter("characterEncoding");
        servletContext=fConfig.getServletContext();
    }
    @Override
    public void destroy() {
    }
}
