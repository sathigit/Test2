package com.atpl.mmg.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

public class VersionInfo {

	@SuppressWarnings("resource")
	public static String getVersionInfo() {

		ApplicationContext appContext = new ClassPathXmlApplicationContext();
		Resource resource = appContext.getResource("classpath:readme.txt");
		
		StringBuilder sb = new StringBuilder();
		

		try {
			InputStream is = resource.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				sb.append(line + "<br>");
			}
			br.close();
			return sb.toString();

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		
	}

}
