package com.store.Util;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class MyConfig {
	
	/*
	 * @Value("${server.http.port}") private Integer http;
	 * 
	 * 
	 * 
	 * @Value("${server.port}") private Integer https;
	 * 
	 * 
	 * @Bean public Connector connector(){ Connector connector=new
	 * Connector("org.apache.coyote.http11.Http11NioProtocol");
	 * connector.setScheme("http"); connector.setPort(http);
	 * connector.setSecure(false); connector.setRedirectPort(https); return
	 * connector; }
	 * 
	 * @Bean public TomcatServletWebServerFactory
	 * tomcatServletWebServerFactory(Connector connector){
	 * TomcatServletWebServerFactory tomcat=new TomcatServletWebServerFactory(){
	 * 
	 * @Override protected void postProcessContext(Context context) {
	 * SecurityConstraint securityConstraint=new SecurityConstraint();
	 * securityConstraint.setUserConstraint("CONFIDENTIAL"); SecurityCollection
	 * collection=new SecurityCollection(); collection.addPattern("/*");
	 * securityConstraint.addCollection(collection);
	 * context.addConstraint(securityConstraint); } };
	 * tomcat.addAdditionalTomcatConnectors(connector); return tomcat; }
	 */
	
}
