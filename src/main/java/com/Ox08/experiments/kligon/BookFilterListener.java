package com.Ox08.experiments.kligon;
import jakarta.inject.Inject;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author <a href="mailto:alex3.145@gmail.com">Alex Chernyshev</a>
 */
@WebFilter(value="/*")
// and servlet context listener - to being used as initialization point,
// because we can't use @ApplicationScoped and @Observes
@WebListener
// .and servlet with exception handler - because I'm too lazy to create dedicated class.
@WebServlet(value="/handleException")
public class BookFilterListener extends HttpServlet implements Filter,
        ServletContextListener {
    @Inject
    private Logger log;
    @Inject
    private BookService bs;
    
    /**
     * These 2 methods are used for servlet, that works as endpoint for error handling.
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {
       /*
        * There should be proper implementation,
        * that extracts error details from context and show dump.
        * But this is not needed for demo project.
        */ 
        response.sendRedirect("%s/guestbook.xhtml"
                .formatted(request.getServletContext().getContextPath()));
    }
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {
        this.doPost(request, response);
    }
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void contextInitialized(ServletContextEvent sce) {
        try {
            // simple check for records count in database, 
            // also triggers model generation if db is empty
            if (bs.fetchRecordsCount() == 0) {
                //create test entity
                BookRecord r = new BookRecord();
                r.setAuthor("system@test.org");
                r.setMessage("Test message");
                r.setTitle("Test title");
                r = bs.save(r);
                log.log(Level.INFO, "automatically added default record: {0}", r.getId());
            }
        } catch (Exception e) {
            log.log(Level.WARNING, 
                    String.format("Exception on startup: %s", e.getMessage()), e);
        }
    }
    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc)
            throws IOException, ServletException {
        if (!(sr instanceof HttpServletRequest request)
                || !(sr1 instanceof HttpServletResponse response)) {
            fc.doFilter(sr, sr1);
            return; // should not happen
        }
        log.log(Level.INFO, "got request: {0}", request.getRequestURI());
        // somehow this is required for correct characters encoding in page output
        request.setCharacterEncoding("UTF-8");
        final String p = request.getRequestURI(),
                cp = request.getServletContext().getContextPath();
        String url = p;
        // strip context path from url
        if (p.startsWith(cp))
            url = p.substring(cp.length());

        if (url.equals("/"))
            response
                    .sendRedirect(cp + "/guestbook.xhtml");
         else
            fc.doFilter(sr, sr1);

    }
}
