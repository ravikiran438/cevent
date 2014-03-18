package com.mycompany.cevent.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This filter is used in production, to put HTTP cache headers with an expiration time until versioning is implemented.
 * </p>
 */
public class CachingHttpHeadersFilter implements Filter {

    // Cache period is 4 hours (in ms)
    private final static long CACHE_PERIOD = 14400000L;

    // We consider the last modified date is the start up time of the server
    private final static long LAST_MODIFIED = System.currentTimeMillis();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Nothing to initialize
    }
    
    @Override
    public void destroy() {
        // Nothing to destroy
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        httpResponse.setHeader("Cache-Control", "max-age=14400000, public");
        httpResponse.setHeader("Pragma", "cache");

        // Setting Expires header, for proxy caching
        httpResponse.setDateHeader("Expires", CACHE_PERIOD + System.currentTimeMillis());

        // Setting the Last-Modified header, for browser caching
        httpResponse.setDateHeader("Last-Modified", LAST_MODIFIED);

        chain.doFilter(request, response);
    }
}
