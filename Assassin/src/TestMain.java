//Johan's killer test client
//Tests the basic functionalities of AssassinManager

import java.util.*;

public class TestMain {
   public static void main(String[] args) {
      //
      /* the basicTest tests:
         -constructor
         -killRingContains
         -printKillRing
         -printGraveyard
      */
      basicTest();

      /* the medTest tests:
         -constructor
         -printKillRing
         -printGraveyard
         -isGameOver
         -winner
         -kill

          The method tests all of your methods but does not test edge cases.
          To test for edge cases, try modifying killer test
      */
      String[] elements = {"a", "b", "c", "d", "e"};
      medTest(elements);
      //Try different combinations of letters to test your code, eg:
      String[] elements2 = {"a", "x", "c"};
      medTest(elements2);
      aLittleEncouragement();


   }

   public static void basicTest() {
      System.out.println("----- Basic Test-----");
      ArrayList<String> names = new ArrayList<String>(); //Test for null, test for empty

      //Basic tests
      //Adding elements to an ArrayList
      String[] elements = {"a", "b", "c", "d", "e"};

      //Construcs a manager using added elements
      AssassinManager manager = setUpManager(elements);

      manager.printKillRing();
      System.out.println("Kill ring contains a: " + manager.killRingContains("a"));
      System.out.println("Kill ring contains b: " + manager.killRingContains("b"));
      System.out.println("Kill ring contains d: " + manager.killRingContains("d"));
      System.out.println("Kill ring contains D: " + manager.killRingContains("d"));
      System.out.println("Kill ring contains e: " + manager.killRingContains("e"));
      System.out.println("The following test should print: false");
      System.out.println("Kill ring contains o: " + manager.killRingContains("o"));
      System.out.println();

      manager.printKillRing();
      System.out.println("The following is an empty graveyard: ");
      manager.printGraveyard();
      System.out.println();
   }

   public static void medTest(String[] elements) {
      System.out.println("----- Medium Test-----");
      AssassinManager manager = setUpManager(elements);
      System.out.println();
      manager.printKillRing();
      //Loop this on every other element
      try {
         System.out.println("Is the game over? " + manager.isGameOver());
         System.out.println("Killing b!");
         manager.kill("b");
         System.out.println("Graveyard: ");
         manager.printGraveyard();
         System.out.println();
         System.out.println("Killing a! (testing front)");
         manager.kill("a");
         System.out.println("Graveyard: ");
         manager.printGraveyard();
         System.out.println();
         System.out.println("Winner (should return null): " + manager.winner());
         System.out.println("Is the game over? " + manager.isGameOver());
         System.out.println("Current Kill Ring");
         manager.printKillRing();
         System.out.println("Killing e! (testing end of list)");
         manager.kill("e");
         System.out.println("Graveyard: ");
         manager.printGraveyard();
         System.out.println();
         System.out.println("Current Kill Ring");
         manager.printKillRing();
         System.out.println("Killing c! (front of list)");
         manager.kill("c");
         System.out.println("Graveyard: ");
         manager.printGraveyard();

         System.out.println("Will throw IllegalStateException:");
         manager.kill("c");
      } catch (IllegalStateException e) {
         System.out.println("IllegalStateException Thrown");
      } catch (IllegalArgumentException e) {
         System.out.println("Illegal Argument Exception Thrown");
      }

      try {
         manager.kill("a");
         manager.kill("c");
      } catch (IllegalArgumentException e) {
         System.out.println("Illegal Argument Exception Thrown");
      } catch (IllegalStateException e) {
         System.out.println("IllegalStateException Thrown");
      }

      System.out.println("Winner (should return d or x): " + manager.winner());
      System.out.println("Should print \"GAME OVER\"");
      if (manager.isGameOver()) {
         System.out.println("              GAME OVER");
      }
   }

   //--AssassinManager constructor must be functional--
   //Sets up a manager using passed array of elements
   //Pre: Array of strings passed must not be null and contain
   // one or more elements
   //Post: returns an AssassinManager containing elements passed
   public static AssassinManager setUpManager(String[] elements) {
      ArrayList<String> names = new ArrayList<String>();
      for (int i = 0; i < elements.length; i++) {
         names.add(elements[i]);
      }
      return new AssassinManager(names);
   }

   public static void aLittleEncouragement() {
      System.out.println(" If you have made it to here, you are in good shape.\n " +
                               "check against the output comparison tool. Keep in mind \n" +
                               " that this program only tests the most frequent issues and is \n" +
                               " is not fully comprehensive. Read the spec and don't forget to \n" +
                               " throw a few exceptions. As Donald Knuth once said: \n" +
                               " \"A list is only as strong as its weakest link.\"");
   }
}