package com.atpl.mmg.AandA.utils;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

@Component
public class IDGeneration {
	private static final AtomicInteger count= new AtomicInteger(0);
	private  final long LIMIT = 100000000L;
	private  long last = 0;
	
	private  long generateUniqueId() {
		// 12 digits.
		long id = System.currentTimeMillis() % LIMIT;
		if (id <= last) {
			id = (last + 1) % LIMIT;
		}
		return last = id;
	}

	public String generate(String ref ){
		Random random = new Random();
		String res = "MMGFR"+ref+(random.nextInt(1000) + 1);
		return res;
		
	}
	
	public String generate(String ref,String countryCode,String stateCode,String cityCode ){
		Random random = new Random();
		String res = "MMGFR"+ref+countryCode+stateCode+cityCode+(random.nextInt(1000) + 1);
		return res;
		
	}
	public  String RandomNumberDriver() {
	    // It will generate 6 digit random Number.
	    // from 0 to 999999
	    Random rnd = new Random();
	    int number = rnd.nextInt(99999999);

	    // this will convert any number sequence into 6 character.
	    return String.format("%06d", number);
	}
	
	public  String RandomNumberVehicle() {
	    // It will generate 6 digit random Number.
	    // from 0 to 999999
	    Random rnd = new Random();
	    int number = rnd.nextInt(99999999);

	    // this will convert any number sequence into 6 character.
	    return String.format("%06d", number);
	}
	
	public  String RandomNumberLabour() {
	    // It will generate 6 digit random Number.
	    // from 0 to 999999
	    Random rnd = new Random();
	    int number = rnd.nextInt(999999999);

	    // this will convert any number sequence into 6 character.
	    return String.format("%06d", number);
	}
	
	public  String RandomNumberFrequentCustomer() {
	    // It will generate 6 digit random Number.
	    // from 0 to 999999
	    Random rnd = new Random();
	    int number = rnd.nextInt(99999999);

	    // this will convert any number sequence into 6 character.
	    return String.format("%06d", number);
	}
	
	public  BigInteger RandomNumberUserSession() {
		  
		 Random rnd = new Random();
		    int number = rnd.nextInt(99999999);

	    // this will convert any number sequence into 6 character.
		 return BigInteger.valueOf(number);
	}

	public String generateRequestNumber(){
		return "SRNO"+ String.valueOf(generateUniqueId());
	}
	
	public  String generateRandomNumber() {
		return String.valueOf(generateUniqueId());
	}

}
