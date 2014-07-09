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
    return map.get(orderingKey);
  }
  
  public void put(O orderingKey, M mapValue)
  {
    //Add the key to the BST
    add(orderingKey);
    
    //Add the value to the map
    map.put(orderingKey,  mapValue);
    
    size++; //increment size
  }

  public int size()
  {
    return size;
  }

  public boolean remove(Object orderingKey)
  {
    O key = (O) orderingKey;
    BSTNode<O> current = root;
    
    boolean foundKey = false;
    while(!foundKey)
    {
      if(key.compareTo(current.data) < 0)
      {
        current = current.leftChild;
      }
      else if(key.compareTo(current.data) > 0)
      {
        current = current.rightChild;
      }
      else
      {
        foundKey = true;
      }
      
      if(current == null)
        return false;
    }
    
    BSTNode<O> successor = successor(current);
    
    
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

  /**
   * Adds a key into the Binary Search Tree.
   * @param orderingKey - The key to be added to the Binary Search Tree.
   * @throws IllegalArgumentException If orderingKey is already in the tree.
   */
  private void add(O orderingKey)
  {
    //We first check if root is null
    BSTNode<O> current = root;
    
    //If root is not null, we have to find the correct position
    while(current != null)
    {
      //Check if orderingKey is less than the current node's key
      if(orderingKey.compareTo(current.data) < 0)
      {
        //If the current node has no left child, we set it's left child to a new node with orderingKey
        if(current.leftChild == null)
        {
          current.leftChild = new BSTNode<O>(orderingKey, current, null, null);
          return;
        }
        //If the current node has a left child, we make that our current node.
        else
        {
          current = current.leftChild;
        }
      }
    //Check if orderingKey is greater than the current node's key
      else if(orderingKey.compareTo(current.data) > 0)
      {
      //If the current node has no right child, we set it's right child to a new node with orderingKey
        if(current.rightChild == null)
        {
          current.rightChild = new BSTNode<O>(orderingKey, current, null, null);
          return;
        }
      //If the current node has a right child, we make that our current node.
        else
        {
          current = current.rightChild;
        }
      }
      //Otherwise, (orderingKey is equal to the node's key) we throw an exception, because we don't allow duplicates
      else
      {
        throw new IllegalArgumentException();
      }
    }
    
    //If root is null, the correct position is at the root
    root = new BSTNode<O>(orderingKey);
  }
  
  /**
   * Determines the successor node of a given node.
   * @param current - The node for which we want to find a successor.
   * @return The node that succeeds the given node.
   */
  private BSTNode<O> successor(BSTNode<O> current)
  {
    //If there are no children, there is no successor.
    if(current.leftChild == null && current.rightChild == null)
      return null;
    
    //If left is not null and right is null, left is the successor.
    else if(current.leftChild != null && current.rightChild == null)
      return current.leftChild;
    
    //If right is not null and left is null, right is the successor.
    else if(current.rightChild == null && current.rightChild != null)
      return current.rightChild;
    
    //If there are two children, we return the node with the next greatest value
    else
    {
      BSTNode<O> successor = current.rightChild;
      while(successor != null)
      {
        if(successor.leftChild == null)
        {
          break;
        }
        else
        {
          successor = successor.leftChild;
        }
      }
      return successor;
    }
  }
}
