package com.woden501.project1.models;

public class Message {
	private String messageContents;
	private String messageType;
	private int numChars;
	private int numCurrencySymbols;
	private int numNumericStrings;
	private int frequencyMostUsedWord;

	public Message(String messageContents, String messageType) {
		super();
		this.messageContents = messageContents;
		this.messageType = messageType;
	}

	public String getMessageContents() {
		return messageContents;
	}

	public void setMessageContents(String messageContents) {
		this.messageContents = messageContents;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public int getNumChars() {
		return numChars;
	}

	public void setNumChars(int numChars) {
		this.numChars = numChars;
	}

	public int getNumCurrencySymbols() {
		return numCurrencySymbols;
	}

	public void setNumCurrencySymbols(int numCurrencySymbols) {
		this.numCurrencySymbols = numCurrencySymbols;
	}

	public int getNumNumericStrings() {
		return numNumericStrings;
	}

	public void setNumNumericStrings(int numNumericStrings) {
		this.numNumericStrings = numNumericStrings;
	}

	public int getFrequencyMostUsedWord() {
		return frequencyMostUsedWord;
	}

	public void setFrequencyMostUsedWord(int frequencyMostUsedWord) {
		this.frequencyMostUsedWord = frequencyMostUsedWord;
	}
}
