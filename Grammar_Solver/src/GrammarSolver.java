// Baihan Lin, 1360521, Section AP
// Prof: Helene Martin, TA: Autumn Johnson
// CSE 143, HW 4: Grammar Solver

// About: GrammarSolver is  a class with functions to process and generate
// grammatical elements based on the grammar rules given in a common format 
// called Backus-Naur Form (BNF).

import java.util.*;

public class GrammarSolver {

	private Map<String, String[]> grammarRules;

	// Pre: given grammar rules as a list of string
	//      throws an IllegalArgumentException if the list is null or empty
	// Post: construct a grammar solver with the rules given
	public GrammarSolver(List<String> rules) {
		checkNull(rules);
		grammarRules = new TreeMap<String, String[]>();
		for (String rule : rules) {
			String key = rule.split("::=")[0];
			if (grammarRules.containsKey(key)) {
				throw new IllegalArgumentException();
			} else {
				grammarRules.put(key, rule.split("::=")[1].split("[|]"));
			}
		}
	}

	// Pre: given a symbol to check
	//      throws an IllegalArgumentException if it is null or ""
	// Post: return true if the symbol is a terminal syntax.
	public boolean contains(String symbol) {
		checkNull(symbol);
		return grammarRules.containsKey(symbol);
	}

	// Pre: no exceptions
	// Post: return the whole set of terminal syntaxes.
	public Set<String> getSymbols() {
		return grammarRules.keySet();
	}

	// Pre: given a symbol to generate. 
	//      throws an IllegalArgumentException if it is null or ""
	// Post: generate and return the symbol based on the grammar rules.
	public String generate(String symbol) {
		checkNull(symbol);
		if (!contains(symbol)) {
			return symbol;
		} else {
			String result = "";
			Random rand = new Random();
			int index = rand.nextInt(grammarRules.get(symbol).length);
			String[] phraseParts = grammarRules.get(symbol)[index].trim().split("[ \t]+");
			for (String part : phraseParts) {
				result = result + generate(part).trim() + " ";
			}
			return result.trim();
		}
	}

	// The following two are Helper Programs to avoid redundancy for condition check
	// Goal: To determine whether the input is null or not

	// Pre: given a string as an input
	// Post: if it is null or "", throw an IllegalArgumentException
	private void checkNull(String str) {
		if (str == null || str.equals("")) {
			throw new IllegalArgumentException();
		}
	}

	// Pre: given a string list as an input
	// Post: if it is null or empty, throw an IllegalArgumentException
	private void checkNull(List<String> strList) {
		if (strList == null || strList.isEmpty()) {  
			throw new IllegalArgumentException();
		}
	}

}
