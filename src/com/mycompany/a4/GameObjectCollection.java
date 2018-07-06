package com.mycompany.a4;

import java.util.ArrayList;

/**
 * 
 * @author Tyler
 * 
 * GameObjectCollection implements the interface ICollection and provides required
 * methods. 
 */

public class GameObjectCollection implements ICollection{
	
	private ArrayList<GameObject> collection;
	
	public GameObjectCollection(){
		collection = new ArrayList<GameObject>();
	}
	
	/**
	 * Adds object to collection. Defined from interface.
	 */
	public void add(GameObject newObject){
		collection.add(newObject);
	}
	
	/**
	 * Returns an iterator for the collection
	 */
	public IIterator getIterator() {
		return new GameObjectIterator();
	}

	/**
	 * 
	 * @author Tyler
	 * Inner class that defines an iterator for the collection. Implements IIterator
	 * interface.
	 */
	private class GameObjectIterator implements IIterator{
		private int current;
		
		public GameObjectIterator(){
			current = -1;
		}
		
		/**
		 * Returns true if there is another element in the collection, false if not.
		 */
		public boolean hasNext(){
			if (collection.size() <= 0)
				return false;
			else if (current == collection.size() - 1)
				return false;
			else
				return true;
		}
		
		/**
		 * Returns the next game object within the collection
		 */
		
		public GameObject next(){
			current++;
			return collection.get(current);
		}
	}
	
}