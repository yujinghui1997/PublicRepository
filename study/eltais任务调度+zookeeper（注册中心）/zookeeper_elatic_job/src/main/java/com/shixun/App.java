package com.shixun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ComponentScan(basePackages = {"com.shixun"})
@ImportResource(locations = {"classpath:applicationContent.xml"})
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
		System.out.println("-----------启动成功----------");
	}

}
