package com.wy.service.registry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletContextInitializer;

import javax.annotation.ManagedBean;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@ManagedBean
@Slf4j
public class ExecutorListener implements ServletContextInitializer {

    @Value("${server.address}")
    private String serviceAddress;

    @Value("${server.port}")
    private String servicePort;

    @Value("${registry.service}")
    private String serviceName;

    @Autowired
    private ServiceRegistry serviceRegistry;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        log.info("onstartup");
        serviceRegistry.registe(serviceName,servicePort,serviceAddress+":"+servicePort);

//        ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
//        RequestMappingHandlerMapping mapping = applicationContext
    }


}
