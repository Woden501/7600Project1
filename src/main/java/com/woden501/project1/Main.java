package com.woden501.project1;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
	public static void main(String[] args) {
		
		String messageFileString = "";
		
		try {
			URL messageFileUrl = ClassLoader.getSystemResource("english_big.txt");
			Path messageFilePath = Path.of(messageFileUrl.toURI());
			messageFileString = Files.readString(messageFilePath, StandardCharsets.ISO_8859_1);
		} catch (Exception e) {
			System.out.println("Unable to parse SMS file. Exception: " + e.toString());
			System.out.println("Exiting..");
		}
		
		System.out.println(messageFileString);
		
		// ToDo: Count the number of characters in each message
		
		// ToDo: Count the number of currency symbols in each message
		
		// ToDo: Count the number of numeric strings
		
		// ToDo: Find frequency of most used word
		
		// Write counts to file in ARFF format
	}
}
