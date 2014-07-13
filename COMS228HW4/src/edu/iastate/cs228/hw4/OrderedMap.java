package edu.iastate.cs228.hw4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * @author Zach Greif
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
    root = null;
    size = 0;
  }
  
  public void clear()
  {
    //Remove all accessibility to the keys.
    root = null;
    
    //Clear all the map values.
    map.clear();
    
    //reset size
    size = 0;
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
    if(orderingKey == null)
      return false;
    
    O key = (O) orderingKey;
    BSTNode<O> current = root;
    
    //Find the node with the key.
    while(current != null && current.data.compareTo(key) != 0)
    {
      if(key.compareTo(current.data) < 0)
      {
        current = current.leftChild;
      }
      else
      {
        current = current.rightChild;
      }
    }
    
    //orderingKey isn't in the tree.
    if(current == null)
    {
      return false;
    }
    
    //Find the successor.
    BSTNode<O> successor = successor(current);
    
    //The node is a leaf.
    if(successor == null)
    {
      //If the successor and parent are both null, it must be the root
      if(current.parent == null)
      {
        root = null;
      }
      //The node is the parent's left child
      else if(key.compareTo(current.parent.data) < 0)
      {
        current.parent.leftChild = null;
      }
      //The node is the parent's right child
      else if(key.compareTo(current.parent.data) > 0)
      {
        current.parent.rightChild = null;
      }
    }
    
    //The node has only one child.
    else if(successor.equals(current.rightChild) && current.leftChild == null
        || successor.equals(current.leftChild) && current.rightChild == null)
    {
      //Swap the data between the successor and current.
      O temp = current.data;
      current.data = successor.data;
      successor.data = temp;
      
      //Set current's children to the successor's children.
      current.leftChild = successor.leftChild;
      current.rightChild = successor.rightChild;
    }
    
    //The node has two children.
    else
    {
      //Swap the data between the successor and current.
      O temp = current.data;
      current.data = successor.data;
      successor.data = temp;
      
      //We only went right once when finding successor
      if(successor.parent.equals(current))
      {
        current.rightChild = successor.rightChild;
      }
      
      //We went right, then left at least once.
      else
      {
        successor.parent.leftChild = successor.rightChild;
      }
    }
    
    size--;
    map.remove(key);
    return true;
  }

  public Iterator<O> keyIterator()
  {
    return new KeyIterator();
  }

  public boolean containsOrderingKey(Object orderingKey)
  {
    //Map conveniently does this for us.
    return map.containsKey(orderingKey);
  }

  public boolean containsMapValue(Object mapValue)
  {
    //Map conveniently does this for us.
    return map.containsValue(mapValue);
  }

  public ArrayList<O> keysInAscendingOrder()
  {
    //ArrayList to store all the keys
    //Final so we can access it in the anonymous Visitor class
    final ArrayList<O> keyList = new ArrayList<O>();
    
    //Constructs a visitor that adds keys to the ArrayList
    Visitor<O> keyVisitor = new Visitor<O>(){
      public void visit(BSTNode<O> node)
      {
        keyList.add(node.data);
      }   
    };
    
    //Traverse through the tree with our new visitor
    inOrderTraversal(keyVisitor);
    
    //Return the filled ArrayList.
    return keyList;
  }

  public ArrayList<M> valuesInAscendingOrder()
  {
    //ArrayList to store all the values
    //Final so we can access it in the anonymous Visitor class
    final ArrayList<M> valueList = new ArrayList<M>();
    
    //Constructs a visitor that adds values to the ArrayList
    //Uses get from map to translate keys on the tree into values
    Visitor<O> valueVisitor = new Visitor<O>(){
      public void visit(BSTNode<O> node)
      {
        valueList.add(map.get(node.data));
      }
    };
    
    //Traverse through the tree with our new visitor
    inOrderTraversal(valueVisitor);
    
    //Return the filled ArrayList.
    return valueList;
  }

  public O ceiling(O orderingKey)
  {
    //Get the keys in order
    ArrayList<O> keys = keysInAscendingOrder();
    
    //Iterate through the keys
    for(int i = 0; i < keys.size(); i++)
    {
      //If we find a key that is larger, because they are in order, it must be the ceiling
      if(keys.get(i).compareTo(orderingKey) > 0)
      {
        return keys.get(i);
      }
    }
    
    //We didn't find anything larger
    return null;
  }

  public OrderedMapInterface<O, M> subMap(O fromKey, O toKey)
  {
    if(fromKey == null || toKey == null || fromKey.compareTo(toKey) > 0)
      return null;
      
    //Construct a new OrderedMap
    OrderedMap<O, M> newMap = new OrderedMap<O, M>();
    
    //Get all the keys
    ArrayList<O> keyList = this.keysInAscendingOrder();
    
    //Iterate through all the keys
    for(O key : keyList)
    {
      //If the key is in range
      if(key.compareTo(fromKey) >= 0 && key.compareTo(toKey) <= 0)
      {
        //We add the key and it's associated value to the new OrderedMap
        newMap.put(key, map.get(key));
      }
    }
    
    //Return the new OrderedMap
    return newMap;
  }
  
  public void inOrderTraversal(Visitor<O> visitor)
  {
    //Stack to store unvisited nodes
    Stack<BSTNode<O>> stack = new Stack<BSTNode<O>>();
    
    //Start at the root
    BSTNode<O> node = root;
    
    //Iterate until the node is null, and the stack is empty.
    while(!stack.isEmpty() || node != null)
    {
      //If the node is not null, we add it to the stack and move to its left child.
      if(node != null)
      {
        stack.push(node);
        node = node.leftChild;
      }
      
      //Otherwise we visit the node and move the the right child.
      else
      {
        node = stack.pop();
        visitor.visit(node);
        node = node.rightChild;
      }
    }
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
    //If both children are null, there is no successor.
    if(current == null || (current.leftChild == null && current.rightChild == null))
    {
      return null;
    }
    
    //If left is null and right is not, right is the successor.
    else if(current.leftChild == null && current.rightChild != null)
    {
      return current.rightChild;
    }
    
    //If right is null and left is not, left is the successor.
    else if(current.rightChild == null && current.leftChild != null)
    {
      return current.leftChild;
    }
    
    else
    {
      //Go right once, then left until we can't go left anymore.
      current = current.rightChild;
      while(current.leftChild != null)
      {
        current = current.leftChild;
      }
      return current;
    }
  }
  
  private class KeyIterator implements Iterator<O>
  {
    private Stack<BSTNode<O>> keys;
    private boolean removeAllowed;
    private O previousValue;
    
    public KeyIterator()
    {
      keys = new Stack<BSTNode<O>>();
      
      BSTNode<O> node = root;
      
      while(node != null)
      {
        keys.push(node);
        node = node.leftChild;
      }
      
    }
    
    public boolean hasNext()
    {
      return !keys.isEmpty();
    }
    
    public O next()
    {
      if(!hasNext())
        throw new NoSuchElementException();
      
      BSTNode<O> node = keys.pop();
      previousValue = node.data;
      if(node.rightChild != null)
      {
        node = node.rightChild;
        while(node != null)
        {
          keys.push(node);
          node = node.leftChild;
        }
      }
      removeAllowed = true;
      return previousValue;
    }
    
    public void remove()
    {
      if(removeAllowed)
      {
        //Will remove from the OrderedMap
        OrderedMap.this.remove(previousValue);
        removeAllowed = false;
      }
      else
      {
        throw new IllegalStateException();
      }
    }
  }
}
