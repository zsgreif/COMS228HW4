/**
 * 
 */
package edu.iastate.cs228.hw4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Zach
 *
 */
public class OrderedMap<O extends Comparable<? super O>, M> implements OrderedMapInterface<O, M>
{
  private Map<O, M> map;
  private BSTNode<O> root;
  private int size;
  
  public OrderedMap()
  {
    map = new HashMap<O, M>();
  }
  
  public void clear()
  {
    //Remove all accessibility to the keys.
    root = null;
    
    //Clear all the map values.
    map.clear();
  }

  public M get(Object orderingKey)
  {
    
    return null;
  }

  public void put(O orderingKey, M mapValue)
  {
    // TODO Auto-generated method stub

  }

  public int size()
  {
    // TODO Auto-generated method stub
    return 0;
  }

  public boolean remove(Object orderingKey)
  {
    // TODO Auto-generated method stub
    return false;
  }

  public Iterator<O> keyIterator()
  {
    // TODO Auto-generated method stub
    return null;
  }

  public boolean containsOrderingKey(Object orderingKey)
  {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean containsMapValue(Object mapValue)
  {
    // TODO Auto-generated method stub
    return false;
  }

  public ArrayList<O> keysInAscendingOrder()
  {
    // TODO Auto-generated method stub
    return null;
  }

  public ArrayList<M> valuesInAscendingOrder()
  {
    // TODO Auto-generated method stub
    return null;
  }

  public O ceiling(O orderingKey)
  {
    // TODO Auto-generated method stub
    return null;
  }

  public OrderedMapInterface<O, M> subMap(O fromKey, O toKey)
  {
    // TODO Auto-generated method stub
    return null;
  }

}
