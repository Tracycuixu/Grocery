package com.example.grocerystore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class SpringMVCCconfiguration implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("index01").setViewName("index01");
        registry.addViewController("common").setViewName("common");
        registry.addViewController("login").setViewName("login");
        registry.addViewController("testcheckbox").setViewName("/test/testcheckbox");
        registry.addViewController("selectoptiondropdownmenu").setViewName("/test/selectoptiondropdownmenu");
        registry.addViewController("listallcustomers").setViewName("/listallcustomers");
        registry.addViewController("listallproductions").setViewName("/listallproductions");
        registry.addViewController("listallinventories").setViewName("/listallinventories");
        registry.addViewController("danli").setViewName("addManyInventoryListForm");


    }

}
