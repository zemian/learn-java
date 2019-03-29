package com.zemian.hellojava.spring.jmx.option2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.export.assembler.InterfaceBasedMBeanInfoAssembler;

import java.util.HashMap;


/**
 * A main entry into Spring application and its Java Config settings.
 */
@Configuration
@ComponentScan
@PropertySource("classpath:/hellojava/app.properties")
@EnableMBeanExport
public class AppMain2 {
	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext spring = new AnnotationConfigApplicationContext(AppMain2.class);
		AppServer server = spring.getBean(AppServer.class);
		server.run(args);
	}

	/* This special bean needs to be static to resolve "${}" string in @Value injection. */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		PropertySourcesPlaceholderConfigurer a = new PropertySourcesPlaceholderConfigurer();
		return a;
	}

	@Autowired
	@Bean
	public MBeanExporter mBeanExporter(AppServer appServer) {
		InterfaceBasedMBeanInfoAssembler assembler = new InterfaceBasedMBeanInfoAssembler();
		assembler.setManagedInterfaces(AppServerMBean.class);

		HashMap<String, Object> beans = new HashMap<>();
		beans.put("com.zemian.hellojava:name=AppServer", appServer);

		MBeanExporter a = new MBeanExporter();
		a.setBeans(beans);
		a.setAssembler(assembler);
		return a;
	}
}
