package com.pedroedrasousa.wordpredictor;

import java.util.List;


/**
 * Stores a list of words and a list of next valid characters
 * for a given prefix.
 * 
 * @author Pedro Edra Sousa
 *  
 */
public class WordPrediction {
	
	private List<String>	mWords;
	private List<Character>	mNextValidChars;
	
	public WordPrediction(List<String> wordList, List<Character> charList) {
		assert(wordList != null && charList != null);
		mWords			= wordList;
		mNextValidChars	= charList;
	}
	
	public void setWords(List<String> wordList) {
		assert(wordList != null);
		mWords = wordList;
	}

	public void setNextValidChars(List<Character> charList) {
		assert(charList != null);
		mNextValidChars = charList;
	}

	public List<String> getWords() {
		return mWords;
	}
	
	public List<Character> getNextValidChars() {
		return mNextValidChars;
	}
}
