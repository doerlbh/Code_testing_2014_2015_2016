// Baihan Lin, 1360521, Section AP
// Prof: Helene Martin, TA: Autumn Johnson
// CSE 143, HW 5: Evil Hangman

// About: 

import java.util.*;

public class HangmanManager3 {

	private Set<String> hManager;
	private SortedSet<Character> guessed;
	private Map<String, Set<String>> patternOptions;
	private String currentPattern;
	private int guessesLeft;
	private int length;                   // Redundant???

	public HangmanManager3(List<String> dictionary, int lengthOfWord, int max) {
		if (lengthOfWord < 1 || max < 0) {
			throw new IllegalArgumentException();
		}
		hManager = new TreeSet<String>();
		length = lengthOfWord;
		guessed = new TreeSet<Character>();
		guessesLeft = max;
		for (int i = 0; i < length; i++) {
			currentPattern += "-";
		}
		patternOptions = new TreeMap<String, Set<String>>();
		for (String word : dictionary) {
			hManager.add(word);
		}
	}

	public Set<String> words() {
		return hManager;
	}

	public int guessesLeft() {
		return guessesLeft;
	}

	public SortedSet<Character> guesses() {
		return guessed;
	}

	public String pattern() {
		if (hManager.size() == 0) {
			throw new IllegalArgumentException();
		}
		return currentPattern;
	}

	public int record(char guess) {
		if (guessesLeft < 1 || hManager.size() == 0) {
			throw new IllegalStateException();
		} 
		if (guessed.contains(guess)) {
			throw new IllegalArgumentException();
		}
		Set<Object> options = new TreeSet<Object>();
		options.add("-");
		options.add(guess);                                  // how to encapsulate it???
		generatePatterns(options, length, "", guess);
		int max = 0;
		for (String iteratorPattern : patternOptions.keySet()) {
			int optionNum = patternOptions.get(iteratorPattern).size();
			if (optionNum > max) {
				max = optionNum;
				currentPattern = iteratorPattern; 
			}
		}
		return max;
	}

	private void generatePatterns(Set<Object> options, int length, String soFar, char guess) {
		if (length == 0) {
			patternOptions.put(soFar, generateOptions(soFar, guess));
		} else {
			for (Object o : options) {
				soFar += o;
				generatePatterns(options, length - 1, soFar, guess);
				soFar.substring(0, soFar.length() - 1);
			}
		}
	}

	private Set<String> generateOptions(String givenPattern, char guess) {
		Set<String> options = new TreeSet<String>();
		for (String word : hManager) {
			if (word.length() == length) {
				String wordPattern = "";
				for (int i = 0; i < word.length(); i++) {
					if (word.charAt(i) == guess) {
						wordPattern += guess;
					} else {
						wordPattern += "-";
					}
				}
				if (wordPattern.equals(givenPattern)) {
					options.add(word);
				}
			}
		}
		return options;
	}

}
