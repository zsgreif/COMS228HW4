package edu.iastate.cs228.hw4;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class OrderedMapTest
{
  OrderedMap<String, Integer> map;
  OrderedMap<Integer, String> map2;
  
  @Before
  public void setup()
  {
    map = new OrderedMap<String, Integer>();
  }
  
  @Test
  public void testAddRemove()
  {
    map.put("A", new Integer(1));
    assertEquals(1, map.size());
    map.remove("A");
    assertEquals(0, map.size());
    map.put("B", new Integer(2));
    map.put("C", new Integer(3));
    map.put("D", new Integer(4));
    map.put("E", new Integer(5));
    assertEquals(4, map.size());
    map.remove("D");
    map.remove("B");
    assertEquals(new Integer(5), map.get("E"));
  }

  @Test
  public void testAddRemove2()
  {
    map2 = new OrderedMap<Integer, String>();
    map2.put(new Integer(8), "A");
    map2.put(new Integer(3), "B");
    map2.put(new Integer(1), "C");
    map2.put(new Integer(6), "D");
    map2.put(new Integer(4), "E");
    map2.put(new Integer(7), "F");
    map2.put(new Integer(10), "G");
    map2.put(new Integer(14), "H");
    map2.put(new Integer(13), "I");
    map2.remove(8);
    
    assertEquals("F", map2.get(new Integer(7)));
  }
}
