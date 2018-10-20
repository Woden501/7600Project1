package com.woden501.project1;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.woden501.project1.models.Message;

public class Main {
	public static void main(String[] args) {

		// Read the messages file into a string
		String messageFileString = "";
		try {
			URI messageFileUri = URI.create(args[0]);
			Path messageFilePath = Path.of(messageFileUri);
			messageFileString = Files.readString(messageFilePath, StandardCharsets.ISO_8859_1);
		} catch (Exception e) {
			System.out.println("Unable to parse SMS file. Exception: " + e.toString());
			System.out.println("Exiting..");
			System.exit(1);
		}

		// Create message object for each message in string
		String[] lines = messageFileString.split("\n");

		List<Message> messages = new ArrayList<>(lines.length);

		for (String line : lines) {
			int lastComma = line.lastIndexOf(",");
			Message message = new Message(line.substring(0, lastComma), line.substring(lastComma + 1));
			messages.add(message);
		}
		
		// Iterate through all of the messages extracting the four features we want
		for (Message message : messages) {
			// Count the number of characters in each message
			countCharacters(message);
			
			// Count the number of currency symbols in each message
			countCurrencySymbols(message);
			
			// Count the number of numeric strings
			countNumericStrings(message);
			
			// Find frequency of most used word
			findWordFrequency(message);
		}

		// Write counts to file in ARFF format
		// Start by generating string
		String arffOutput = "@RELATION spam\n";
		arffOutput += "@ATTRIBUTE charcount NUMERIC\n";
		arffOutput += "@ATTRIBUTE currencycount NUMERIC\n";
		arffOutput += "@ATTRIBUTE numericcount NUMERIC\n";
		arffOutput += "@ATTRIBUTE frequencycount NUMERIC\n";
		arffOutput += "@ATTRIBUTE class {ham, spam}\n";
		arffOutput += "\n@DATA\n";
		// Then call the overridden toString method on each Message to print out the appropriate
		// format for the ARFF file
		for (Message message : messages) {
			arffOutput += message.toString() + "\n";
		}
		
		// Write the ARFF string to disk
		try {
			Files.writeString(Path.of("project.arff"), arffOutput);
		} catch (IOException e) {
			System.out.println("Unable to write ARFF output to disk. Exception: " + e.getMessage());
			System.exit(1);
		}
		
		System.out.println("ARFF file produced...");
	}

	// Takes a Message object and counts the number of characters in its contents string
	private static void countCharacters(Message message) {
		message.setNumChars(message.getMessageContents().length());
	}

	// Takes a Message object and counts the number of currency symbols in its contents string
	private static void countCurrencySymbols(Message message) {
		int currencySymbolCount = 0;
		// Look at every character in the message, and check if it is a currency symbol
		for (int j = 0; j < message.getMessageContents().length(); j++) {
			if (Character.getType(message.getMessageContents().charAt(j)) == Character.CURRENCY_SYMBOL) {
				currencySymbolCount++;
			}
		}
		message.setNumCurrencySymbols(currencySymbolCount);
	}

	// Takes a Message object and counts the number of numeric strings in its contents string
	private static void countNumericStrings(Message message) {
		int numNumericStrings = 0;
		
		// Regex pattern that matches all numeric strings of length 1 or more
		Pattern p = Pattern.compile("-?\\d+");
		Matcher m = p.matcher(message.getMessageContents());
		
		// Repeatedly search for matches to the regex pattern, and each time one is found
		// we add one to our count of numeric strings.
		while (m.find()) {
			numNumericStrings++;
		}
		message.setNumNumericStrings(numNumericStrings);
	}

	// Takes a Message object and finds the count of the most frequently used word in its contents string
	private static void findWordFrequency(Message message) {
		// Remove non-whitespace and non-apostrophe puncutation
		String noPuncationString = message.getMessageContents().replaceAll("[^A-Za-z' ]+", "");
		
		// Break string into it's component words
		String[] words = noPuncationString.split(" ");
		
		// Iterate over every word in the message counting how many times it's been encountered
		// and comparing to the current highest count
		HashMap<String, Integer> wordCount = new HashMap<>();
		int highestCount = 0;
		for (String word : words) {
			Integer currentCount = wordCount.get(word);
			
			// If the word isn't in the hashmap yet then add it and set the count to 1
			if (currentCount == null) {
				currentCount = 1;
				wordCount.put(word, currentCount);
			}
			// The word is in the hashmap, so we just add 1 to the count
			else {
				currentCount++;
				wordCount.put(word, currentCount);
			}
			
			// Check to see if this new current count is greater than the highest count so far
			// If so set this as the new highest count
			if (currentCount > highestCount) {
				highestCount = currentCount;
			}
		}
		message.setFrequencyMostUsedWord(highestCount);
	}
}
