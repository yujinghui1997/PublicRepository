package com.shixun.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * security 标签配置
 */
public class ClassPathTldsLoader {


    //配置Security标签库12345
    private  static  final  String SECURITY_TLD = "/static/tags/security.tld";

    private List<String> classPathTlds;

    public  ClassPathTldsLoader(String... classPathTlds){
        if (classPathTlds.length == 0){
            this.classPathTlds = Arrays.asList(SECURITY_TLD);
        } else {
            this.classPathTlds = Arrays.asList(classPathTlds);
        }
    }

    @Autowired
    private FreeMarkerConfigurer configurer;

    @PostConstruct
    public  void  loadClassPathTlds(){
        configurer.getTaglibFactory().setClasspathTlds(classPathTlds);
    }
    
    

}
