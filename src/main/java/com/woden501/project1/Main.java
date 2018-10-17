package com.woden501.project1;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.woden501.project1.models.Message;

public class Main {
	public static void main(String[] args) {

		// Read the messages file into a string
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

		// Create message object for each message in string
		String[] lines = messageFileString.split("\n");

		List<Message> messages = new ArrayList<>(lines.length);

		for (String line : lines) {
			int lastComma = line.lastIndexOf(",");
			Message message = new Message(line.substring(0, lastComma), line.substring(lastComma + 1));
			messages.add(message);
		}

		int i = 1;
		for (Message message : messages) {
			System.out.println(i + ": " + message.getMessageContents() + ", " + message.getMessageType());
			i++;
		}

		// ToDo: Count the number of characters in each message
		for (Message message : messages) {
			message.setNumChars(message.getMessageContents().length());
		}

		i = 1;
		for (Message message : messages) {
			System.out.println(i + ": " + message.getNumChars() + ", " + message.getMessageType());
			i++;
		}

		// Count the number of currency symbols in each message
		for (Message message : messages) {
			int currencySymbolCount = 0;

			// Look at every character in the message, and check if it is a currency symbol
			for (int j = 0; j < message.getMessageContents().length(); j++) {
				if (Character.getType(message.getMessageContents().charAt(j)) == Character.CURRENCY_SYMBOL) {
					currencySymbolCount++;
				}
			}
			message.setNumCurrencySymbols(currencySymbolCount);
		}

		i = 1;
		for (Message message : messages) {
			System.out.println(i + ": " + message.getNumCurrencySymbols() + ", " + message.getMessageType());
			i++;
		}

		// ToDo: Count the number of numeric strings
		for (Message message : messages) {
			int numNumericStrings = 0;
			Pattern p = Pattern.compile("-?\\d+");
			Matcher m = p.matcher(message.getMessageContents());
			while (m.find()) {
				numNumericStrings++;
			}

			message.setNumNumericStrings(numNumericStrings);
		}

		i = 1;
		for (Message message : messages) {
			System.out.println(i + ": " + message.getNumNumericStrings() + ", " + message.getMessageType());
			i++;
		}

		// Find frequency of most used word
		for (Message message : messages) {
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
				
				if (currentCount == null) {
					currentCount = 1;
					wordCount.put(word, currentCount);
				}
				else {
					currentCount++;
					wordCount.put(word, currentCount);
				}
				
				if (currentCount > highestCount) {
					highestCount = currentCount;
				}
			}
			
			message.setFrequencyMostUsedWord(highestCount);
		}
		
		i = 1;
		for (Message message : messages) {
			System.out.println(i + ": " + message.getFrequencyMostUsedWord() + ", " + message.getMessageType());
			i++;
		}

		// Write counts to file in ARFF format
		String arffOutput = "";

//		// Finding all characters used
//		HashSet<Character> charactersUsed = new HashSet<>();
//		for (String line : lines) {
//			for (int j = 0; j < line.length(); j++) {
//				charactersUsed.add(line.charAt(j));
//			}
//		}
//		
//		for(Character character : charactersUsed) {
//			if (Character.getType(character) == Character.) {
//				System.out.println(character + ": true");
//			}
//		}
	}
}
