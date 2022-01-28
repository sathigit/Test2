package com.atpl.mmg.utils;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class IDGeneration {

	public String generate(String ref) {
		Random random = new Random();
		String res = "MMG" + ref + (random.nextInt(1000) + 1);
		return res;
	}

	public String RandomNumberVehicle() {
		// It will generate 6 digit random Number.
		// from 0 to 999999
		Random rnd = new Random();
		int number = rnd.nextInt(99999999);

		// this will convert any number sequence into 6 character.
		return String.format("%06d", number);
	}

	public String RandomNumberDriver() {
		// It will generate 6 digit random Number.
		// from 0 to 999999
		Random rnd = new Random();
		int number = rnd.nextInt(99999999);

		// this will convert any number sequence into 6 character.
		return String.format("%06d", number);
	}

	public String RandomNumberLabour() {
		// It will generate 6 digit random Number.
		// from 0 to 999999
		Random rnd = new Random();
		int number = rnd.nextInt(999999999);

		// this will convert any number sequence into 6 character.
		return String.format("%06d", number);
	}

	public String generate(String ref, String countryCode, String stateCode, String cityCode) {
		Random random = new Random();
		String res = "MMG" + ref + countryCode + stateCode + cityCode + (random.nextInt(1000) + 1);
		return res;

	}

	public String RandomNumberFranchise() {
		// It will generate 6 digit random Number.
		// from 0 to 999999
		Random rnd = new Random();
		int number = rnd.nextInt(999999);

		// this will convert any number sequence into 6 character.
		return String.format("%06d", number);
	}

	public String RandomNumberFleet() {
		// It will generate 6 digit random Number.
		// from 0 to 999999
		Random rnd = new Random();
		int number = rnd.nextInt(999999);

		// this will convert any number sequence into 6 character.
		return String.format("%06d", number);
	}

	public String RandomNumberWarehouse() {
		// It will generate 6 digit random Number.
		// from 0 to 999999
		Random rnd = new Random();
		int number = rnd.nextInt(999999);

		// this will convert any number sequence into 6 character.
		return String.format("%06d", number);
	}

	public String generateRequestNumber() {
		Random random = new Random();
		String res = "SRNO" + (random.nextInt(1000) + 1);
		return res;
	}

	public String RandomNumberEmployee() {
		// It will generate 6 digit random Number.
		// from 0 to 999999
		Random rnd = new Random();
		int number = rnd.nextInt(99999999);

		// this will convert any number sequence into 6 character.
		return String.format("%06d", number);
	}
	public String generateQuotationNumber() {
		Random random = new Random();
		String res = "MMGQUO" + (random.nextInt(1000000) + 1);
		return res;
	}
}
