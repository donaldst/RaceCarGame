package com.mycompany.a4;

/**
 * ICollection provides an interface for GameObjectCollection
 **/
public interface ICollection {
	public void add(GameObject newObject);
	public IIterator getIterator();
}
