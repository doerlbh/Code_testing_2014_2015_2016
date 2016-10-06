// Baihan Lin, 1360521, Section AP
// Prof: Helene Martin, TA: Autumn Johnson
// CSE 143, HW 6: Anagram

// About: Anagrams is a class with functions to process and generate the anagrams
// of a given phrase from pool of words in a dictionary. The functions available
// for clients includes getting all the words possible with all letters of the 
// given phrase and printing out anagrams with or without maximum word limit.

import java.util.*;

public class Anagrams {

	private Set<String> allWords; 

	// Pre: given a list of words as dictionary, throws an IllegalArgumentException
	//      if the dictionary set is null.
	// Post: construct a anagrams (solver) to store words pool.
	public Anagrams(Set<String> dictionary) {
		checkNull(dictionary);
		allWords = new TreeSet<String>(dictionary);  
	}

	// Pre: given a phrase, throws an IllegalArgumentException if the string passed is null
	// Post: return a set of words that can be generated from all the letters of the phrase
	public Set<String> getWords(String phrase) {
		Set<String> wordOptions = new TreeSet<String>();
		checkNull(phrase);
		for (String word : allWords) {
			if (toLI(phrase).contains(word)) {
				wordOptions.add(word); 
			}
		}
		return wordOptions;
	}

	// Pre: given a phrase, throws an IllegalArgumentException if the string passed is null
	// Post: print all anagrams that can be formed using all letters of given phrase
	public void print(String phrase) {
		print (phrase, 0);
	}

	// Pre: given a phrase, throws an IllegalArgumentException if the string passed is null
	//      given a max limiting words in anagram, throws an IllegalArgumentException if max<0
	// Post: print all anagrams within max words formed using all letters of given phrase
	public void print(String phrase, int max) {
		Stack<String> soFar = new Stack<String>();
		LetterInventory phraseLI = toLI(phrase);
		print(phraseLI, max, soFar);
	}

	// Pre: given a phrase, throws an IllegalArgumentException if the string passed is null
	//      given a max limiting words in anagram, throws IllegalArgumentException if max < 0
	//      given a stack of words chosen in the process of generating anagrams
	// Post: print all anagrams within max words formed using all letters of given phrase
	private void print(LetterInventory phraseLI, int max, Stack<String> soFar) {
		checkNull(phraseLI);
		if (max < 0) {
			throw new IllegalArgumentException();
		} else if (max == 0 || max >= soFar.size()) {  // two meaningful conditions
			if (phraseLI.isEmpty()) {                  // Base Case
				System.out.println(soFar);
			} else {                                   // Recursive case
				for (String chosen : getWords(phraseLI.toString())) {
					if (phraseLI.contains(chosen)) {
						soFar.push(chosen);
						phraseLI.subtract(chosen);    
						print(phraseLI, max, soFar);  // recursive backtracking
						soFar.pop();                  
						phraseLI.add(chosen);         
					}
				}
			}
		}		
	}

	// Pre: given an object as an input
	// Post: if it is null, throw an IllegalArgumentException
	private void checkNull(Object o) {
		if (o == null) {
			throw new IllegalArgumentException();
		}
	}

	// Pre: given a String
	// Post: return a LetterInventory generated from String
	private LetterInventory toLI(String phrase) {
		LetterInventory letters = new LetterInventory(phrase);
		return letters;
	}

}
