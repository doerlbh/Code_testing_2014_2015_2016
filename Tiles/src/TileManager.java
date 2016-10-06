// Baihan Lin, 1360521, Section AP
// Prof: Helene Martin, TA: Autumn Johnson
// CSE 143, HW 1: Tiles

// About: TileManager is the the logic part for a graphical program that 
// allows the user to click on rectangular tiles. It manages tiles by storing,
// maintaining, adding, deleting, and shuffling the tiles in the panel.

import java.util.*;
import java.awt.*;

// TileManager is a class to store and maintain a list of Tile objects.
public class TileManager {

	private int size;
	private ArrayList<Tile> list;

	// Constructor: To create a new tile manager object.
	public TileManager() {
		list = new ArrayList<Tile>();
		size = list.size();
	}

	// Goal: To add the given tile to the end of the tile manager's list of tiles
	// Pre: input a Tile object, no exceptions
	// Post: a given tile is added to the end of tile manager's list of tiles
	public void addTile(Tile rect) {
		list.add(rect);
		size++;
	}

	// Goal: To cause all tiles in the manager to appear on the screen using the given
	// graphical pen.
	// Pre: input a graphical pen object g
	// Post: all tiles appear on screen with given graphical pen drawing.
	public void drawAll(Graphics g) {
		for (int i = 0; i < size; i++) {
			list.get(i).draw(g);
		}
		size = list.size();
	}

	// Goal: To move the tile topmost on the coordinate to the top of the list (very surface)
	// Pre: given 0 < x < WIDTH, 0 < y < HEIGHT. No exception due to mouse clicking.
	// Post: the tile topmost on the coordinate goes to the top of the list
	public void raise(int x, int y) {
		int index = tileClicked(x, y);
		if (index >= 0) {
			list.add(list.get(index));
			list.remove(index);
		}
	}

	// Goal: To move the tile topmost on the coordinate to the bottom of the list (deepest)
	// Pre: given 0 < x < WIDTH, 0 < y < HEIGHT. No exception due to mouse clicking.
	// Post: the tile topmost on the coordinate goes to the bottom of the list
	public void lower(int x, int y) {
		int index = tileClicked(x, y);
		if (index >= 0) {
			for (int i = size; i > 0; i--) {
				list.add(i, list.get(i-1));
			}
			list.add(0, list.get(index + 1));
			list.remove(index + 1);
		}
	}

	// Goal: To remove the topmost tile (if existed) on the given coordinate
	// Pre: given 0 < x < WIDTH, 0 < y < HEIGHT. No exception due to mouse clicking
	// Post: removes the topmost tile (if existed) on the given coordinate (x,y)
	public void delete(int x, int y) {
		int index = tileClicked(x, y);
		if (index >= 0) {
			list.remove(index);
			size--; 
		}
	}

	// Goal: To remove all the tiles (if existed) on the given coordinate.
	// Pre: given 0 < x < WIDTH, 0 < y < HEIGHT. No exception due to mouse clicking.
	// Post: removes the topmost tile (if existed) on the given coordinate (x,y).
	public void deleteAll(int x, int y) {
		int index = tileClicked(x, y);
		while (index >= 0) {
			list.remove(index);
			size--;
			index = tileClicked(x, y);
		}
	}

	// Goal: To reorder tiles in different layers and different coordinates
	// Pre: input the width and height of the panel
	// Post: within the panel the tiles will be shuffled into different layers and places.
	public void shuffle(int width, int height) {
		Collections.shuffle(list);
		for (int i = 0; i < size; i++) {
			Random rand = new Random();
			list.get(i).setX(rand.nextInt(width - list.get(i).getWidth()));
			list.get(i).setX(rand.nextInt(width - list.get(i).getWidth()));
		}
	}

	// Helper Program for methods: raise, lower, delete, deleteAll
	// Goal: To determine which tile is clicked, or no tile is clicked
	// Pre: given x and y mouse clicked
	// Post: return the index of the clicked or -1
	private int tileClicked(int x, int y) {
		for (int i = size - 1; i > -1; i--) {
			if (list.get(i).getX() < x 
					&& list.get(i).getX() + list.get(i).getWidth() > x  
					&& list.get(i).getY() < y 
					&& list.get(i).getY() + list.get(i).getHeight() > y ) {
				return i;
			}
		}
		return -1;
	}
}