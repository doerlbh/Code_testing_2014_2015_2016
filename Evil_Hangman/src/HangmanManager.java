// Baihan Lin, 1360521, Section AP
// Prof: Helene Martin, TA: Autumn Johnson
// CSE 143, HW 5: Evil Hangman

// About: HangmanManger is a class with functions to process and maintain the
// words pool in the game so that parameters about current pool, guesses left
// and current pattern are all well monitored, and the word options of each letter
// are selected on the biggest occurrence to increase the difficulty and slow down
// the player.

import java.util.*;

public class HangmanManager {

	private Set<String> hManager;
	private SortedSet<Character> guessed;
	private Map<String, Set<String>> patternOptions; 
	private String currentPattern;
	private int guessesLeft;

	// Pre: given a list of words as dictionary, the length of the guessed word
	//      and the max time of wrong guesses. 
	//      length >= 1, max >= 0, throws an IllegalArgumentException otherwise
	// Post: construct a hangman manager to store words pool
	public HangmanManager(List<String> dictionary, int length, int max) {
		if (length < 1 || max < 0) {
			throw new IllegalArgumentException();
		}
		hManager = new TreeSet<String>();
		guessed = new TreeSet<Character>();
		guessesLeft = max;
		currentPattern = "";
		for (int i = 0; i < length; i++) {
			currentPattern += "- ";
		}
		patternOptions = new TreeMap<String, Set<String>>();
		for (String word : dictionary) {
			if (word.length() == length) {     // only store words in that length
				hManager.add(word);
			}
		}
	}

	// Post: return the current words in the pool
	public Set<String> words() {
		return hManager;
	}

	// Post: return the guess opportunities left in the game
	public int guessesLeft() {
		return guessesLeft;
	}

	// Post: return a sorted set of letters that have been guessed
	public SortedSet<Character> guesses() {
		return guessed;
	}

	// Pre: there has to be words left in the pool (i.e. not empty), 
	//      otherwise throws an IllegalArgumentException
	// Post: return the current pattern in the game
	public String pattern() {
		if (hManager.size() == 0) {
			throw new IllegalArgumentException();
		}
		return currentPattern;
	}

	// Pre: number of guesses left >= 1 and the list of words isn't empty,
	//      throw an IllegalStateException otherwise. 
	//      In this nonempty list, the letter guessed shouldn't repeat (guess twice),
	//      throw an IllegalArgumentException otherwise.
	// Post: given input of letter guessed, determine the best pattern (biggest frequency
	//       so as to slow down the player most), and return the occurrence of the letter
	//       in the selected pattern.
	public int record(char guess) {
		if (guessesLeft < 1 || hManager.size() == 0) {
			throw new IllegalStateException();
		} 
		if (guessed.contains(guess)) {
			throw new IllegalArgumentException();
		}
		guessed.add(guess);
		for (String word : hManager) {
			String wordPattern = "";
			for (int i = 0; i < word.length(); i++) {  // produce a pattern from a word
				char wordElement = word.charAt(i);
				if (wordElement == guess || wordElement == currentPattern.charAt(i * 2)) {      
					wordPattern = wordPattern + wordElement + " ";
				} else {
					wordPattern += "- ";
				}
			}
			if (!patternOptions.containsKey(wordPattern)) { 
				patternOptions.put(wordPattern, new TreeSet<String>());
			}
			patternOptions.get(wordPattern).add(word);
		}
		int max = 0;      // compare and determine the most frequent pattern
		for (String iteratorPattern : patternOptions.keySet()) {
			int optionNum = patternOptions.get(iteratorPattern).size();
			if (optionNum > max) {
				max = optionNum;
				currentPattern = iteratorPattern; 
			}
		}
		hManager = patternOptions.get(currentPattern);  // update hManager
		patternOptions.clear();                 // clear patternOptions for next guess
		int count = 0;           // count the occurrence of the guessed letter in pattern
		for (int i = 0; i < currentPattern.length(); i++) {
			if (currentPattern.charAt(i) == guess) {
				count++;
			}
		}
		if (count == 0) { 
			guessesLeft--;   // update guesses left in the game
		}
		return count;
	}

}