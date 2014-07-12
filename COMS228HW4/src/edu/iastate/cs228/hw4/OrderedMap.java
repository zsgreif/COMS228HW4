/**
 * 
 */
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
    
    //Cast to a key.
    //Will throw an error if the value isn't the right type, but we would just throw an error anyway.
    O key = (O) orderingKey;
    
    //Start searching for the node at the root
    BSTNode<O> current = root;
    
    boolean foundKey = false;
    
    //Find the key using binary search
    while(!foundKey)
    {
      //If the node is null, we haven't found it.
      if(current == null)
        return false;
      
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
    }
    
    //Find the successor node.
    BSTNode<O> successor = successor(current);
    
    
    
    if(successor != null)
    {
      //Put the successors data in the removal spot.
      current.data = successor.data;    
      
      //Successor should never have more than one child, so we are free to call removeNode to do our dirty work
      removeNode(successor);
    }
    
    //if there is no successor, the node must be a leaf
    else
      removeNode(current);
    
    map.remove(key);
    size--;
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
      //If the node is null, we add it to the stack and move to its left child.
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
  
  /**
   * Removes a node from the BST assuming it has either one or zero children.
   * @param node - The node to be removed from the graph.
   * @throws IllegalArgumentException If node has two children.
   */
  private void removeNode(BSTNode<O> node)
  {
    //We only remove nodes with zero or one children
    if(node.leftChild != null && node.rightChild != null)
      throw new IllegalArgumentException();
    
    BSTNode<O> parent = node.parent;
    BSTNode<O> child = successor(node);
    
    //If the node has no parent, it must be the root.
    if(parent == null)
    {
      //We nullify the child's parent and set the root to the child
      if(child != null)
        child.parent = null;
      root = child;
      return;
    }
    
    //Changes the parent's child to the successor node.
    //Will work correctly even if there is no successor
    if(node.equals(parent.leftChild))
    {
      parent.leftChild = child;
      if(child != null)
      {
        child.parent = parent;
      }
    }
    else //node.equals(parent.rightChild)
    {
      parent.rightChild = child;
      if(child != null)
      {
        child.parent = parent;
      }
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
