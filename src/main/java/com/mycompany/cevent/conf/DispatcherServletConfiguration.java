package com.mycompany.cevent.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mycompany.cevent.rest.MessageResource;

@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = { MessageResource.class })
public class DispatcherServletConfiguration {

}
