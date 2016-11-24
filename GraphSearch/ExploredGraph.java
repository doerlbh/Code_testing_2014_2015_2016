import java.util.*;

// Happy Thanksgiving to you!

/**
 * @author Baihan Lin  < sunnylin@uw.edu >
 * @version 	1.2
 * @since 		1.0 2016-11-12
 * 
 * Extra Credit Options Implemented:  A5E1, A5E2 (half done, but not finished)
 * 
 * Solution to Assignment 5 in CSE 373, Autumn 2016
 * University of Washington.
 * (Based on starter code v1.3. By Steve Tanimoto.)
 *
 * Java version 8 or higher is recommended.
 *
 */

// Here is the main application class:
public class ExploredGraph {
	Set<Vertex> Ve; // collection of explored vertices
	Set<Edge> Ee;   // collection of explored edges

	LinkedHashMap<String, Vertex> pMap; // predecessors of maps
	Set<Operator> pMove; // peg moves
	int costF; // cost function

	/**
	 * Constructor to ExploredGraph
	 * @param Ve stores collection of explored vertices.
	 * @param Ee stores collection of explored edges.
	 * @param pMap stores  predecessors of maps
	 * @param pMove stores peg moves
	 * @param costF stores the choice of cost function
	 */
	public ExploredGraph() {
		Ve = new LinkedHashSet<Vertex>();
		Ee = new LinkedHashSet<Edge>();
		pMap = new LinkedHashMap<String, Vertex>();
		pMove = new LinkedHashSet<Operator>();
		costF = 1; // by default we use edgeCost function 1
	}

	/**
	 * Testing space
	 * @param args
	 */
	public static void main(String[] args) {

		ExploredGraph eg = new ExploredGraph();

		// Basic Testing:
		// bfs search path for 6 disks, expect (2^6) - 1 = 63
		//		Vertex v0 = eg.new Vertex("[[6,5,4,3,2,1],[],[]]");
		//		Vertex v1 = eg.new Vertex("[[],[],[6,5,4,3,2,1]]");
		//		System.out.println(v0);

		// A5E1 Testing 1:
		// bfs search path for 6 disks, 4 pegs, expect 17
		//		Vertex v0 = eg.new Vertex("[[6,5,4,3,2,1],[],[],[]]");
		//		Vertex v1 = eg.new Vertex("[[],[],[],[6,5,4,3,2,1]]");
		//		System.out.println(v0);

		// A5E1 Testing 2:
		// bfs search for 4 disks, 5 pegs, expect 7
		Vertex v0 = eg.new Vertex("[[4,3,2,1],[],[],[],[]]");
		Vertex v1 = eg.new Vertex("[[],[],[],[],[4,3,2,1]]");
		System.out.println(v0);

		// find path		
		eg.initialize(v0);
		ArrayList<Vertex> path = eg.shortestPath(v0, v1);
		for (Vertex v : path) { System.out.println(v.toString()); }
		System.out.println("path length = " + (path.size() - 1));

		// test other functions
		eg.initialize(v0);
		eg.bfs(v0,v1);
		System.out.println(eg.nvertices());
		System.out.println(eg.nedges());
		System.out.println(eg.retrievePath(v1));

	}

	/**
	 * Public Method to set up an instance of this class
	 * @param v: the starting vertex v to insert into its set of vertices
	 */
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

	/**
	 * Private Method to support initialze and return peg number for A5E1
	 * @param v: a sample vertex 
	 * @return peg number
	 */
	private int getPegNumber(Vertex v) {
		return string2peg(v.toString());
	}

	/**
	 * Private Method to support getPegNumber and Vertex and return peg number for A5E1
	 * @param s: a string extracted from a vertex in toString method
	 * @return peg number
	 */
	int string2peg(String s) {
		int pCount = -1; // account for the first bracket in the front
		for (char c : s.toCharArray()) {
			if (c == '[') {
				pCount++;
			}
		}
		//		System.out.println(s + "---" + pCount);
		return pCount;
	}

	/**
	 * Public Method to obtain the number of vertices
	 * @return number of vertices
	 */
	public int nvertices() {return Ve.size();}

	/**
	 * Public Method to obtain the number of edges
	 * @return number of edges
	 */
	public int nedges() {return Ee.size();}

	/**
	 * Public Method set the choice of using which edge cost function.
	 * @param m: choice of cost function choice
	 */
	public void useEdgeCostFunction(int m) {
		costF = m;
	}
	
	/**
	 * Public Method to calculate the edge cost
	 * @param i, j: two pegs
	 * @param k: number on the disk
	 * note: another implicit parameter is the choice of cost function
	 */
	public int getEdgeCost(int i, int j, int k) {
		int edgeCost = 1;
		switch (costF) {
		case 1:	edgeCost = 1;
		break;
		case 2: edgeCost = k;
		break;
		case 3: edgeCost = k * k;
		break;
		case 4: edgeCost = (int)Math.pow(2, k);
		break;
		case 5: double p = (j == 1) ? 0.1 : 1;
		edgeCost = (int)(p * Math.pow(2, k));
		break;
		case 6: edgeCost = k*(k+1)*(k+2);
		break;	
		}	
		return edgeCost;
	}
	
	/**
	 * Public Method to do Iterative Depth-First Search from vi to vj
	 * @param vi, vj: two vertices
	 * @return nothing, but added all possible solution into Ve and Ee
	 */
	public void idfs(Vertex vi, Vertex vj) {
		pMap.clear();
		pMap.put(vi.toString(), null);
		Ee.add(new Edge(null, vi));

		Stack<Vertex> oVe = new Stack<Vertex>(); //open
		LinkedList<Vertex> cVe = new LinkedList<Vertex>(); //closed

		boolean isFound = false;
		int dist = 0;

		oVe.push(vi);
		Vertex curVe; // current Vertex
		while (!oVe.empty() || !isFound) {
			curVe = oVe.pop();
			curVe.dist = dist;
			dist++;
			for (Operator move : pMove) {
				Vertex nextVe = move.transition(curVe);
				if ( (!nextVe.equals(curVe))
						&& (oVe.search(nextVe) == -1)
						&& (!cVe.contains(nextVe))) {
					Ve.add(nextVe);
					Ee.add(new Edge(curVe, nextVe));
					pMap.put(nextVe.toString(), curVe);
					oVe.push(nextVe);
				}
				if (nextVe.equals(vj)) {
					isFound = true;
				}
			}
			cVe.add(curVe);
		}
	}

	/**
	 * Public Method to do Breadth-First Search from vi to vj
	 * @param vi, vj: two vertices
	 * @return nothing, but added all possible solution into Ve and Ee
	 */
	public void bfs(Vertex vi, Vertex vj) {
		pMap.clear();
		pMap.put(vi.toString(), null);
		Ee.add(new Edge(null, vi));

		Queue<Vertex> oVe = new LinkedList<Vertex>(); // open
		Queue<Vertex> cVe = new LinkedList<Vertex>(); // closed

		boolean isFound = false;
		int dist = 0;

		oVe.add(vi);
		Vertex curVe; // current Vertex
		while (!oVe.isEmpty() || !isFound) {
			curVe = oVe.remove();
			curVe.dist = dist;
			dist++;
			for (Operator move : pMove) {
				Vertex nextVe = move.transition(curVe);
				if ( (!nextVe.equals(curVe))
						&& (!oVe.contains(nextVe))
						&& (!cVe.contains(nextVe))) {
					Ve.add(nextVe);
					Ee.add(new Edge(curVe, nextVe));
					pMap.put(nextVe.toString(), curVe);
					oVe.add(nextVe);
				}
				if (nextVe.equals(vj)) {
					isFound = true;
				}
			}
			cVe.add(curVe);
		}
	} 

	/**
	 * Public Method to use path links established to return path
	 * @param vi: a vertex as target
	 * @return ArrayList of Vertex which contains states along the path
	 */
	public ArrayList<Vertex> retrievePath(Vertex vi) {
		Stack<Vertex> backPath = new Stack<Vertex>();	
		ArrayList<Vertex> path = new ArrayList<Vertex>();
		if (pMap.containsKey(vi.toString())) {
			Vertex curMap = vi;
			while (pMap.get(curMap.toString()) != null) {
				backPath.push(curMap);
				curMap = pMap.get(curMap.toString());
			}
			backPath.push(curMap);
			while (!backPath.isEmpty()) { path.add(backPath.pop()); }
		} else {
			throw new IllegalArgumentException("Vertex not in searched path");
		}
		return path;
	}

	/**
	 * Public Method to get the shortest from one vertex to another
	 * @param vi, vj: two vertices as two states
	 * @return the shortest path in an ArrayList of vertices as states
	 */
	public ArrayList<Vertex> shortestPath(Vertex vi, Vertex vj) {
		bfs(vi, vj);
		ArrayList<Vertex> path = retrievePath(vj);
		return path;
	} 

	/**
	 * Public Method to get all the explored vertices 
	 * @return collection of explored vertices
	 */
	public Set<Vertex> getVertices() {return Ve;} 

	/**
	 * Public Method to get all the explored edges 
	 * @return collection of explored edges
	 */
	public Set<Edge> getEdges() {return Ee;} 

	// Inner class vertex to store each state
	class Vertex {
		int dist; // distance from the starting point
		int nPegV; // number of peg for A5E1

		ArrayList<Stack<Integer>> pegs; // Each vertex will hold a Towers-of-Hanoi state.
		// There will be 3 pegs in the standard version, but more if you do extra credit option A5E1.

		// Constructor that takes a string such as "[[4,3,2,1],[],[]]":
		/**
		 * Constructor to ExploredGraph
		 * @param dist stores distance from the starting point
		 * @param nPegV stores number of peg for A5E1
		 * @param pegs stores a Towers-of-Hanoi state
		 */
		public Vertex(String vString) {
			nPegV = string2peg(vString);
			//			System.out.println(vString);
			String[] parts = vString.split("\\],\\[");
			//			System.out.println(parts.length);
			pegs = new ArrayList<Stack<Integer>>(nPegV);
			for (int i = 0; i < nPegV;i++) {
				pegs.add(new Stack<Integer>());
				try {
					parts[i]=parts[i].replaceAll("\\[","");
					parts[i]=parts[i].replaceAll("\\]","");
					List<String> al = new ArrayList<String>(Arrays.asList(parts[i].split(",")));
					Iterator<String> it = al.iterator();
					while (it.hasNext()) {
						String item = it.next();
						if (!item.equals("")) {
							pegs.get(i).push(Integer.parseInt(item));
						}
					}
				}
				catch(NumberFormatException nfe) { nfe.printStackTrace(); }
			}
		}

		/**
		 * Public Method to return a string from the vertex 
		 * @return a string with all the state elements
		 */
		public String toString() {
			String ans = "[";
			for (int i=0; i< nPegV; i++) {
				ans += pegs.get(i).toString().replace(" ", "");
				if (i<nPegV-1) { ans += ","; }
			}
			ans += "]";
			return ans;
		}

		/**
		 * Public Method to compare two objects as vertex
		 * @return a boolean: true if equal
		 */
		public boolean equals(Object v){
			if (v == this) { return true; }
			if (!(v instanceof Vertex)) { return false; }
			Vertex other = (Vertex) v;
			return (this.toString().equals(other.toString()));
		}
	}

	// Inner class vertex to store each edge between two vertices
	class Edge {

		private Vertex vi, vj;

		/**
		 * Constructor of Edge
		 * @param vi, vj: two vertices that form this edge
		 */
		public Edge(Vertex vi, Vertex vj) {
			this.vi = vi;
			this.vj = vj;
		}

		/**
		 * Public Method to return a string from the edge 
		 * @return a string with all the vertices as elements
		 */
		public String toString() {
			return "Edge from " + vi.toString() + " to " + vj.toString();
		}

		/**
		 * Public Method to return the first vertex 
		 * @return vi: the first vertex
		 */
		public Vertex getEndPoint1(){ return vi;}

		/**
		 * Public Method to return the second vertex 
		 * @return vi: the second vertex
		 */
		public Vertex getEndPoint2(){ return vj;}
	}

	// Inner Class to store operator
	class Operator {

		private int i, j;

		/**
		 * Constructor of Operator 
		 * @param  i, j: the two pegs as source and target
		 */
		public Operator(int i, int j) { 
			this.i = i;
			this.j = j;
		}

		/**
		 * Public Method to determine whether precondition of the operation is met
		 * @param  v: a vertex which represent the state
		 * @return true if precondition is met
		 */
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

		/**
		 * Public Method to transition from one state to another
		 * @param  v: a vertex which represent the current state
		 * @return a new state as vertex
		 */
		public Vertex transition(Vertex v) {
			if(precondition(v)){
				Vertex next = new Vertex(v.toString());
				next.pegs.get(j).push(next.pegs.get(i).pop());
				return next;
			} else {
				return v; 
			}
		}

		/**
		 * Public Method to describe the operator as a string
		 * @return a string with all operation information
		 */
		public String toString() {
			return "Move a disk from peg " + i + " to peg " + j;
		}
	}

}
