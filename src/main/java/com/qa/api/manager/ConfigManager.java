package com.qa.api.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
		
	private static Properties properties=new Properties();
	
	static {
		//mvn clean install -Denv=qa/uat/stage/dev/prod
		//mvn clean install -Denv=qa
		//mvn clean install-- if env is not given, then run test cases on QA env by default.
		//env --environment variable(system)
		
		String envName=System.getProperty("env","prod");
		System.out.println("Running the text on: "+envName);
		
		String filename= "config_"+envName+".properties";
		System.out.println(filename);
		
		InputStream input=ConfigManager.class.getClassLoader().getResourceAsStream(filename);
		if(input != null) {
			try {
				properties.load(input);
				System.out.println("properties=====>"+properties);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static String get(String key) {
		return properties.getProperty(key).trim();
	}

	public static void set(String key, String value) {
		properties.setProperty(key,value);
	}
}
