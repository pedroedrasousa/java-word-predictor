package com.pedroedrasousa.wordpredictor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;


/**
 * A trie data structure that can be searched by prefixes.
 * Strings are returned in no particular order.
 * Search is not case sensitive. Every string is converted to lower case.
 * 
 * @author Pedro Edra Sousa
 *
 */
public class WordTrieTree implements WordPredictor {
	
	private static class WordTrieNode {
		
		private HashMap<Character, WordTrieNode> mChildren;		// This node children nodes.
		private WordTrieNode	mParent;						// This node parent node.
		private char			mCharacter;						// The character this node represents.
		private boolean			mIsWord;						// Indicates whether this node represents the last character of a word or not.
		
		/**
		 * Class constructor.
		 */
		public WordTrieNode() {
			mChildren = new HashMap<Character, WordTrieNode>();
		}
		
		/**
		 * Constructor used to instantiate a node that will represent a given character.
		 * Only the root node will not represent any character.
		 * @param character The character this node represents
		 */
		public WordTrieNode(char character) {
			this();
			mCharacter = character;
		}
		
		/**
		* Adds a given word to this node.
		* Child nodes for each successive letter will be added.
		* Will be used recursively.
		* @param word The word to add
		*/
		protected void addWord(String word) {
			Character c = word.charAt(0);
			
			// Try to fetch the node representing this char
			WordTrieNode node = mChildren.get(c);
			
			// Is there no node representing this character? If so, create one.
			if (node == null) {
				node = new WordTrieNode(c);
				node.mParent = this;
				mChildren.put(c, node);
			}
		    
			// Are there letters remaining in the word?
			if (word.length() > 1) {
				node.addWord( word.substring(1) );	// Add them to the child node.
			}
			else {
				node.mIsWord = true;				// This node represents a full word.
			}
		}

		/**
		 * Adds to a given list the complete words traversing from this node downward.
		 * Will be used recursively.
		 * @param wordList List to add the words
		 */
		protected void getWords(List<String> wordList) {

			// If this node represents a word, add it to the list.
			if (mIsWord) {
				wordList.add( toString() );
			}
			
			// Add the words of every child node to the list.
			for (Entry<Character, WordTrieNode> entry : mChildren.entrySet()) {
				entry.getValue().getWords(wordList);
			}
		}
		
		/**
		 * Gets this node children nodes characters.
		 * @return A list of this node children characters.
		 */
		protected ArrayList<Character> getChildChars() {
			// Create a list to return.
			ArrayList<Character> charList = new ArrayList<Character>();
			
			// Add the character of every child node to the list.
			for (Entry<Character, WordTrieNode> entry : mChildren.entrySet()) {
				charList.add( entry.getKey() );
			}
			
			return charList;
		}

		/**
		 * Gets the complete string this node represents by reverse traversing the tree starting from this node.
		 * Will be used recursively.
		 */
		public String toString() {
			if (mParent == null) {
				return "";
			}
			else {
				return mParent.toString() + mCharacter;
			}
		}
		
		/**
		* Gets the child representing the given char.
		* @param c	The given char.
		* @return	A TrieNode representing the given char or null if it doesn't exist
		*/
		protected WordTrieNode getNode(char c) {
		   return mChildren.get(c);
		}
	}

	private WordTrieNode mRootNode;
	
	/**
	* Constructor
	*/
	public WordTrieTree() {
		mRootNode = new WordTrieNode();
	}
	
	/**
	* Adds a word to the trie.
	* @param word	The word to add.
	*/
	public void addWord(String word) {
		assert(word != null);
		mRootNode.addWord( word.toLowerCase() );
	}
	
	/**
	* Adds words from a given list to the trie.
	* @param wordList	The word list to add.
	*/
	public void addAllWords(List<String> wordList) {
		assert(wordList != null);
		
		for (String s : wordList)
			addWord(s);
	}
	
	/**
	* Adds words from a given string array to the trie.
	* @param wordArray	The word array to add.
	*/
	public void addAllWords(String[] wordArray) {
		assert(wordArray != null);
		
		for (int i = 0; i < wordArray.length; i++) {
			addWord( wordArray[i] );
		}
	}
	
	/**
	* Get the words in the trie with the given prefix and valid next characters.
	* 
	* @param prefix	The prefix to search
	* @return		A WordPrediction object containing a list with the words with the given prefix 
	* 				and a list containing valid next characters.
	*/
	public WordPrediction getPredictions(String prefix) {
		assert(prefix != null);
		
		// Create a local copy of the prefix in lower case.
		String lCasePrefix = prefix.toLowerCase();
		
		List<String>	wordList	= new ArrayList<String>();
		List<Character> charList	= new ArrayList<Character>();
		
		WordTrieNode lastNode = mRootNode;
		
		// Find the node which represents the last character of the prefix.
		for (int i = 0; i < lCasePrefix.length(); i++) {
			lastNode = lastNode.getNode( lCasePrefix.charAt(i) );

			// If no node matches, then no words exist.
			if (lastNode == null)
				return new WordPrediction(wordList, charList);	// Lists will be empty.
		}

		// Add the words by traversing the tree starting from this node.
		lastNode.getWords(wordList);
		charList = lastNode.getChildChars();
		
		return new WordPrediction(wordList, charList);
	}
}