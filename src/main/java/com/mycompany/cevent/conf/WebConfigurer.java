package com.mycompany.cevent.conf;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.DispatcherServlet;

import com.mycompany.cevent.web.filter.CachingHttpHeadersFilter;
import com.mycompany.cevent.web.filter.gzip.GZipServletFilter;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
public class WebConfigurer implements ServletContextListener {

	private final Logger log = LoggerFactory.getLogger(WebConfigurer.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		log.info("Web application configuration");

		log.debug("Configuring Spring root application context");
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(ApplicationConfiguration.class);
		rootContext.refresh();

		servletContext.setAttribute(
				WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
				rootContext);

		EnumSet<DispatcherType> disps = EnumSet.of(DispatcherType.REQUEST,
				DispatcherType.FORWARD, DispatcherType.ASYNC);
		
        if (WebApplicationContextUtils
                .getRequiredWebApplicationContext(servletContext)
                .getBean(Environment.class)
                .acceptsProfiles(Constants.SPRING_PROFILE_OPENSHIFT)) {

            initCachingHttpHeadersFilter(servletContext, disps);
        }

		initSpring(servletContext, rootContext);
		initGzipFilter(servletContext, disps);

		log.debug("Web application fully configured");
	}
	
    /**
     * Initializes the GZip filter.
     */
    private void initGzipFilter(ServletContext servletContext, EnumSet<DispatcherType> disps) {
        log.debug("Registering GZip Filter");

        FilterRegistration.Dynamic compressingFilter = servletContext.addFilter("gzipFilter", new GZipServletFilter());
        Map<String, String> parameters = new HashMap<String, String>();

        compressingFilter.setInitParameters(parameters);

        compressingFilter.addMappingForUrlPatterns(disps, true, "*.css");
        compressingFilter.addMappingForUrlPatterns(disps, true, "*.json");
        compressingFilter.addMappingForUrlPatterns(disps, true, "*.html");
        compressingFilter.addMappingForUrlPatterns(disps, true, "*.js");
        compressingFilter.addMappingForUrlPatterns(disps, true, "/app/rest/*");

        compressingFilter.setAsyncSupported(true);
    }

    /**
     * Initializes the cachig HTTP Headers Filter.
     */
    private void initCachingHttpHeadersFilter(ServletContext servletContext,
                                              EnumSet<DispatcherType> disps) {

        log.debug("Registering Cachig HTTP Headers Filter");
        FilterRegistration.Dynamic cachingHttpHeadersFilter =
                servletContext.addFilter("cachingHttpHeadersFilter",
                        new CachingHttpHeadersFilter());

        cachingHttpHeadersFilter.addMappingForUrlPatterns(disps, true, "/resources/*");
        cachingHttpHeadersFilter.setAsyncSupported(true);
    }

	/**
	 * Initializes Spring and Spring MVC.
	 */
	private ServletRegistration.Dynamic initSpring(
			ServletContext servletContext,
			AnnotationConfigWebApplicationContext rootContext) {
		log.debug("Configuring Spring Web application context");
		AnnotationConfigWebApplicationContext dispatcherServletConfiguration = new AnnotationConfigWebApplicationContext();
		dispatcherServletConfiguration.setParent(rootContext);
		dispatcherServletConfiguration
				.register(DispatcherServletConfiguration.class);

		log.debug("Registering Spring MVC Servlet");
		ServletRegistration.Dynamic dispatcherServlet = servletContext
				.addServlet("dispatcher", new DispatcherServlet(
						dispatcherServletConfiguration));
		dispatcherServlet.addMapping("/app/*");
		dispatcherServlet.setLoadOnStartup(1);
		dispatcherServlet.setAsyncSupported(true);
		return dispatcherServlet;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		log.info("Destroying Web application");
		WebApplicationContext ac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(sce.getServletContext());
		AnnotationConfigWebApplicationContext gwac = (AnnotationConfigWebApplicationContext) ac;
		gwac.close();
		log.debug("Web application destroyed");
	}
}
