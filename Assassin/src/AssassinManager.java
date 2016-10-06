// Baihan Lin, 1360521, Section AP
// Prof: Helene Martin, TA: Autumn Johnson
// CSE 143, HW 3: Assassin

// About: AssassinManager is a class with functions to maintain and process
// the people in the game for clients to do killing, display and check the kill 
// ring and grave yard, and return the winner and call stop when GameOver.

import java.util.*;

public class AssassinManager {

	private AssassinNode killFront; //a reference to the front node of the kill ring
	private AssassinNode deadFront; //a reference to the front node of the graveyard

	// Constructor: To create a new AssassinManger based on input namelist.
	public AssassinManager(ArrayList<String> nameList) {
		killFront = null;
		deadFront = null;		
		AssassinNode current = killFront;
		for (String i : nameList) {
			if (killFront == null) {
				killFront = new AssassinNode(i);
				current = killFront;        // Seems redundancy, but really necessary
			} else {
				current.next = new AssassinNode(i);
				current = current.next;
			}
		}
	}

	// Pre: No exception
	// Post: print out names of people in kill ring and their relationship (who stalking who)
	public void printKillRing() {
		AssassinNode current = killFront;
		while (current != deadFront) {
			System.out.println("  " + current.name + " is stalking " + target(current).name);
			current = current.next;
		}
	}

	// Pre: do nothing if no one was killed yet
	// Post: print out the names of people in the graveyard and who killed them
	public void printGraveyard() {
		if (deadFront != null) {
			AssassinNode current = deadFront;
			while (true) {          // Please refer to "Note" below
				System.out.println("  " + current.name + " was killed by " + current.killer);
				if (current.next == null) {
					break;
				}
				current = current.next;
			}
		}
	}

	// Note: the reason I use several "Loop and a half" in this class is that it 
	// fits the Process All Items condition better. Also, no worry. runtime will
	// surely be less than O(N).

	// Pre: input a name. Ignore the cases. No exception
	// Post: return true if the name is still in kill ring. Otherwise false.
	public boolean killRingContains(String name) {
		AssassinNode current = killFront;
		while (current != deadFront) {
			if (current.name.equalsIgnoreCase(name)) {
				return true;            // Seems boolean Zen, but really necessary
			}
			current = current.next;
		}
		return false;
	}

	// Pre: input a name. Ignore the cases. No exception
	// Post: return true if the name is dead in graveyard. Otherwise false.
	public boolean graveyardContains(String name) {
		if (deadFront != null) {
			AssassinNode current = deadFront;
			while (true) {
				if (current.name.equalsIgnoreCase(name)) {
					return true;           // Seems boolean Zen, but really necessary
				} 
				if (current.next == null) {
					break;
				}
				current = current.next;
			}
		}
		return false;
	}

	// Pre: No exception
	// Post: return true if the game is over. Otherwise false.
	public boolean isGameOver() {
		return killFront.next == deadFront;
	}

	// Pre: No exception
	// Post: return the name of the winner if game is over. Otherwise return null
	public String winner() {
		if (this.isGameOver()) {
			return killFront.name; 
		}
		return null;
	}

	// Pre: Input a victim. Throw an IllegalStateException if game is over,
	//      Throw an IllegalArgumentException if input is null.
	// Post: The victim is removed out of kill ring and put into graveyard
	public void kill(String name) {
		if (this.isGameOver()) {
			throw new IllegalStateException();
		} else if (!this.killRingContains(name)) {
			throw new IllegalArgumentException();
		} else {
			AssassinNode current = killFront;
			while (current != deadFront) {
				if (current.name.equalsIgnoreCase(name)){
					current.killer = this.whoKill(current).name;
					this.remove(current);
					current.next = deadFront;
					whoKill(deadFront).next = current;
					deadFront = current;
					break;
				} 
				current = current.next;
			}
		}
	}

	// Helper method for printKillRing.
	// Pre: input a reference to a killer. Ignore the cases. No exception
	// Post: return the reference to the target that the killer is trying to kill
	private AssassinNode target(AssassinNode killer) {
		if (killer == whoKill(deadFront)) {
			return killFront;
		} 
		return killer.next;
	}

	// Helper method for kill
	// Pre: input a reference to the victim being killed. No exception
	// Post: remove the killed from the kill ring
	private void remove(AssassinNode killed) {
		if (killed == killFront) {
			killFront = killed.next;
		} else {
			whoKill(killed).next = killed.next;
		}
	}

	// Helper method for kill
	// Pre: input a reference to a person in the kill ring. No exception
	// Post: return the previous one, i.e. the killer that is stalking him/her.
	private AssassinNode whoKill(AssassinNode victim) {
		if (victim == killFront) {
			return whoKill(deadFront);
		} else {
			AssassinNode current = killFront;
			while (true) {
				if (current.next == victim) {
					return current; 
				}
				current = current.next;
			}
		}
	}

}
