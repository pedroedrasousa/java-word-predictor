package com.pedroedrasousa.wordpredictor;

import java.util.List;


/**
 * Represents an object that can predict words from a given prefix
 * and a list of complete words.
 * 
 * @author Pedro Edra Sousa
 *
 */
public interface WordPredictor {
	public WordPrediction getPredictions(String prefix);
	public void addWord(String word);
	public void addAllWords(List<String> wordList);
	public void addAllWords(String[] wordList);
}
