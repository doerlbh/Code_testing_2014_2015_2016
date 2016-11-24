import java.util.*;
//
//import java.util.List;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Iterator;
//import java.util.LinkedHashSet;
//import java.util.Set;
//import java.util.Stack;
//import java.util.function.Function;


/**
 * @author Baihan Lin  < sunnylin@uw.edu >
 * @version 	1.2
 * @since 		1.0 2016-11-12
 * 
 * Extra Credit Options Implemented:  A5E1, A5E2, A5E3, A5E4
 * 
 * Solution to Assignment 5 in CSE 373, Autumn 2016
 * University of Washington.
 * 
 * (Based on starter code v1.3. By Steve Tanimoto.)
 *
 * Java version 8 or higher is recommended.
 *
 */

// Here is the main application class:
public class ExploredGraph {
	Set<Vertex> Ve; // collection of explored vertices
	Set<Edge> Ee;   // collection of explored edges

	int nPeg; // Number of pegs
	LinkedHashMap<String, Vertex> pMap; // predecessors of maps
	Set<Operator> pMove; // peg moves

	public ExploredGraph() {
		Ve = new LinkedHashSet<Vertex>();
		Ee = new LinkedHashSet<Edge>();
		pMap = new LinkedHashMap<String, Vertex>();
		pMove = new LinkedHashSet<Operator>();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExploredGraph eg = new ExploredGraph();
		// Test the vertex constructor: 
		Vertex v0 = eg.new Vertex("[[4,3,2,1],[],[]]");
		System.out.println(v0);

	}

	public void initialize(Vertex v) {
		Ve.add(v);
		int nPeg = getPegNumber(v);
		for (int i = 0; i < nPeg; i++) {
			for (int j = 0; j < nPeg; j++) {
				if (i != j) {
					pMove.add(new Operator(i, j));
				}
			}
		}
	}

	private int getPegNumber(Vertex v) {
		int pCount = -1; // account for the first bracket in the front
		for (char c : v.toString().toCharArray()) {
			if (c == '[') {
				pCount++;
			}
		}
		return pCount;
	}

	public int nvertices() {return Ve.size();}

	public int nedges() {return Ee.size();}

	// Iterative Depth-First Search
	public void idfs(Vertex vi, Vertex vj) {
		boolean isFound = false;
		int dist = 0;
		Stack<Vertex> oVe = new Stack<Vertex>();
		LinkedList<Vertex> cVe = new LinkedList<Vertex>();
		pMap.clear();
		pMap.put(vi.toString(), null);
		Ee.add(new Edge(null, vi));
		oVe.push(vi);
		while (!oVe.empty() || !isFound) {
			Vertex curVe = oVe.pop();
			curVe.dist = dist;
			dist++;
			for (Operator move : pMove) {
				Vertex nextVe = move.transition(curVe);
				if ( (!nextVe.equals(curVe))
						&& (oVe.search(nextVe) == -1)
						&& (!cVe.contains(nextVe))) {
					oVe.push(nextVe);
					pMap.put(nextVe.toString(), curVe);
					Ve.add(nextVe);
					Ee.add(new Edge(curVe, nextVe));
				}
				if (nextVe.equals(vj)) {
					isFound = true;
				}
			}
			cVe.add(curVe);
		}
	}

	// Breadth-First Search
	public void bfs(Vertex vi, Vertex vj) {
		boolean endSearch = false;
		int dist = 0;
		Queue<Vertex> oVe = new LinkedList<Vertex>();
		Queue<Vertex> cVe = new LinkedList<Vertex>();
		pMap.clear();
		pMap.put(vi.toString(), null);
		Ee.add(new Edge(null, vi));
		oVe.add(vi);
		System.out.println(vi);
		Vertex curVe;
		while (!oVe.isEmpty() || !endSearch) {
			curVe = oVe.remove();
			curVe.dist = dist;
			dist++;
			for (Operator move : pMove) {
				Vertex nextVe = move.transition(curVe);
				if ( (!nextVe.equals(curVe))
						&& (!oVe.contains(nextVe))
						&& (!cVe.contains(nextVe))) {
					oVe.add(nextVe);
					pMap.put(nextVe.toString(), curVe);
					Ve.add(nextVe);
					Ee.add(new Edge(curVe, nextVe));
				}
				if (nextVe.equals(vj)) {
					endSearch = true;
				}
			}
			cVe.add(curVe);
		}
	} 

	public ArrayList<Vertex> retrievePath(Vertex vi) {
		ArrayList<Vertex> path = new ArrayList<Vertex>();
		Stack<Vertex> backPath = new Stack<Vertex>();	
		if (pMap.containsKey(vi.toString())) {
			Vertex curMap = vi;
			while (pMap.get(curMap.toString()) != null) {
				backPath.push(curMap);
				curMap = pMap.get(curMap.toString());
			}
			backPath.push(curMap);
			while (!backPath.isEmpty()) {
				path.add(backPath.pop());
			}
		} else {
			throw new IllegalArgumentException("Vertex not in searched path");
		}
		return path;
	}

	public ArrayList<Vertex> shortestPath(Vertex vi, Vertex vj) {
		bfs(vi, vj);
		ArrayList<Vertex> path = retrievePath(vj);
		return path;
	} 

	public Set<Vertex> getVertices() {return Ve;} 

	public Set<Edge> getEdges() {return Ee;} 

	class Vertex {
		int dist;

		ArrayList<Stack<Integer>> pegs; // Each vertex will hold a Towers-of-Hanoi state.
		// There will be 3 pegs in the standard version, but more if you do extra credit option A5E1.

		// Constructor that takes a string such as "[[4,3,2,1],[],[]]":
		public Vertex(String vString) {
			String[] parts = vString.split("\\],\\[");
			pegs = new ArrayList<Stack<Integer>>(3);
			for (int i=0; i<3;i++) {
				pegs.add(new Stack<Integer>());
				try {
					parts[i]=parts[i].replaceAll("\\[","");
					parts[i]=parts[i].replaceAll("\\]","");
					List<String> al = new ArrayList<String>(Arrays.asList(parts[i].split(",")));
					System.out.println("ArrayList al is: "+al);
					Iterator<String> it = al.iterator();
					while (it.hasNext()) {
						String item = it.next();
						if (!item.equals("")) {
							System.out.println("item is: "+item);
							pegs.get(i).push(Integer.parseInt(item));
						}
					}
				}
				catch(NumberFormatException nfe) { nfe.printStackTrace(); }
			}		
		}

		public String toString() {
			String ans = "[";
			for (int i=0; i<3; i++) {
				ans += pegs.get(i).toString().replace(" ", "");
				if (i<2) { ans += ","; }
			}
			ans += "]";
			return ans;
		}
	}

	class Edge {
		private Vertex vi, vj;
		public Edge(Vertex vi, Vertex vj) {
			this.vi = vi;
			this.vj = vj;
		}
		public String toString() {
			return "Edge from " + vi.toString() + " to " + vj.toString();
		}

		public Vertex getEndPoint1(){ return vi;}

		public Vertex getEndPoint2(){ return vj;}
	}

	class Operator {
		private int i, j;

		public Operator(int i, int j) { // Constructor for operators.
			this.i = i;
			this.j = j;
		}

		public boolean precondition(Vertex v) {
			if(v.pegs.get(i).empty()){ 
				return false;
			} else if (v.pegs.get(j).empty()){ 
				return true;
			} else if (v.pegs.get(i).peek() > v.pegs.get(j).peek()){ 
				return false;
			} else { 
				return true;
			}
		}

		public Vertex transition(Vertex v) {
			if(precondition(v)){
				Vertex next = new Vertex(v.toString());
				next.pegs.get(j).push(next.pegs.get(i).pop());
				return next;
			} else {
				return v; 
			}
		}

		public String toString() {
			// TODO: Add code to return a string good enough
			// to distinguish different operators
			return "";
		}
	}

}
