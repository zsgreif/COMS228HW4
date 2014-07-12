package edu.iastate.cs228.hw4;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class OrderedMapTest
{
  OrderedMap<String, Integer> map;
  OrderedMap<Integer, String> map2;
  
  @Before
  public void setup1()
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
    map2.remove(3);
    map2.remove(1);
    map2.remove(6);
    map2.remove(4);
    map2.remove(10);
    map2.remove(14);
    map2.remove(13);
    
    assertEquals(0, map.size());
  }
  
  @Test
  public void testNullRemove()
  {
    
    assertEquals(false, map.remove("A"));
    map.put("A", new Integer(1));
    assertEquals(false, map.remove("B"));
  }
  
  @Test
  public void testRemoveOrder()
  {
    map.put("B", new Integer(1));
    map.put("A", new Integer(2));
    map.put("C", new Integer(3));
    map.remove("B");
    ArrayList<String> keys = map.keysInAscendingOrder();
    assertEquals("A", keys.get(0));
    assertEquals("C", keys.get(1));
  }
  
  @Test
  public void testIterator()
  {
    map.put("A", new Integer(1));
    Iterator<String> iter = map.keyIterator();
    
    assertEquals(true, iter.hasNext());
    assertEquals("A", iter.next());
    
    iter.remove();
    assertEquals(0, map.size());
  }
  
  OrderedMap<Integer, String> testMap;
EvenOddVisitor<String> testVisitor;
Iterator<Integer> testKeyIter;
 
@Before
public void setup() {
testMap = new OrderedMap<Integer, String>();
testVisitor = new EvenOddVisitor<String>();
}
 
// @Ignore
@Test
public void testInitialization() {
assertEquals(testMap.size(), 0);
assertFalse(testMap.containsMapValue("A"));
assertFalse(testMap.containsOrderingKey("B"));
}
 
// @Ignore
@Test
public void testPut1() {
testMap.put(1, "A");
assertEquals(testMap.size(), 1);
assertTrue(testMap.containsMapValue("A"));
assertTrue(testMap.containsOrderingKey(1));
assertFalse(testMap.containsOrderingKey(2));
treeContainsValue("A", testMap);
treeContainsKey(1, testMap);
}
 
// @Ignore
@Test
public void testPut2() {
putTenItemsInOrder(testMap);
assertEquals(testMap.size(), 10);
assertEquals(testMap.keysInAscendingOrder().size(), 10);
assertEquals(testMap.get(10), "J");
assertEquals(testMap.get(9), "I");
}
 
// @Ignore
@Test
public void testRemove1() {
putTenItemsInOrder(testMap);
assertEquals(testMap.get(8), "H");
// Remove H
testMap.remove(8);
assertEquals(testMap.size(), 9);
assertEquals(testMap.keysInAscendingOrder().size(), 9);
treeNotContainsKey(8, testMap);
treeNotContainsValue("H", testMap);
assertFalse(testMap.containsOrderingKey(8));
assertFalse(testMap.containsMapValue("H"));
}
 
// @Ignore
@Test
public void testRemove2() {
putTenItemsInReverseOrder(testMap);
assertEquals(testMap.get(8), "H");
// Remove H
testMap.remove(8);
treeNotContainsKey(8, testMap);
treeNotContainsValue("H", testMap);
assertEquals(testMap.size(), 9);
assertEquals(testMap.keysInAscendingOrder().size(), 9);
assertFalse(testMap.containsOrderingKey(8));
assertFalse(testMap.containsMapValue("H"));
}
 
// @Ignore
@Test
public void testRemove3() {
putItemsInRandomOrder(testMap, 10);
assertEquals(testMap.get(8), "H");
// Remove H
testMap.remove(8);
treeNotContainsKey(8, testMap);
treeNotContainsValue("H", testMap);
assertEquals(testMap.size(), 9);
assertEquals(testMap.keysInAscendingOrder().size(), 9);
assertFalse(testMap.containsOrderingKey(8));
assertFalse(testMap.containsMapValue("H"));
}
 
// @Ignore
@Test
public void testRemove4() {
putItemsInRandomOrder(testMap, 10);
assertFalse(testMap.remove(20));
assertEquals(testMap.size(), 10);
assertEquals(testMap.keysInAscendingOrder().size(), 10);
}
@Test
public void testSimpleRemove(){
testMap.put(3, "C");
testMap.put(6, "F");
testMap.put(1, "A");
testMap.remove(3);
assertEquals(testMap.size(), 2);
assertEquals(testMap.keysInAscendingOrder().size(), 2);
treeNotContainsKey(3, testMap);
treeContainsKey(1, testMap);
treeContainsKey(6, testMap);
}
@Test
public void testSimpleRemove2(){
testMap.put(3, "C");
testMap.put(1, "A");
testMap.remove(3);
assertEquals(testMap.size(), 1);
assertEquals(testMap.keysInAscendingOrder().size(), 1);
treeNotContainsKey(3, testMap);
treeContainsKey(1, testMap);
}
@Test
public void testSimpleRemove3(){
testMap.put(3, "C");
testMap.put(1, "A");
testMap.put(6, "F");
testMap.put(5, "E");
testMap.put(4, "D");
testMap.remove(3);
assertEquals(testMap.size(), 4);
assertEquals(testMap.keysInAscendingOrder().size(), 4);
treeNotContainsKey(3, testMap);
treeContainsKey(1, testMap);
treeContainsKey(4, testMap);
}
@Test
public void testSimpleRemove4(){
testMap.put(3, "C");
testMap.put(1, "A");
testMap.put(6, "F");
testMap.put(5, "E");
testMap.put(4, "D");
testMap.remove(4);
assertEquals(testMap.size(), 4);
assertEquals(testMap.keysInAscendingOrder().size(), 4);
treeNotContainsKey(4, testMap);
}
@Test
public void testSimpleRemoveTwice(){
testMap.put(3, "C");
testMap.put(1, "A");
testMap.put(6, "F");
testMap.put(5, "E");
testMap.put(4, "D");
testMap.remove(1);
testMap.remove(3);
assertEquals(testMap.size(), 3);
assertEquals(testMap.keysInAscendingOrder().size(), 3);
treeNotContainsKey(1, testMap);
treeNotContainsKey(3, testMap);
treeContainsKey(4, testMap);
}
 
// @Ignore
@Test
public void testRemovePut1() {
putTenItemsInOrder(testMap);
assertEquals(testMap.get(8), "H");
// Remove H
testMap.remove(8);
// Put H
testMap.put(11, "H");
assertFalse(testMap.containsOrderingKey(8));
assertTrue(testMap.containsOrderingKey(11));
assertTrue(testMap.containsMapValue("H"));
treeNotContainsKey(8, testMap);
assertEquals(testMap.size(), 10);
assertEquals(testMap.keysInAscendingOrder().size(), 10);
}
 
@Ignore
@Test
public void testRemovePut2() {
putTenItemsInReverseOrder(testMap);
assertEquals(testMap.get(8), "H");
// Remove H
testMap.remove(8);
// Put H
testMap.put(1, "H");
assertFalse(testMap.containsOrderingKey(8));
treeNotContainsKey(8, testMap);
assertTrue(testMap.containsOrderingKey(1));
assertTrue(testMap.containsMapValue("H"));
assertFalse(testMap.containsMapValue("A"));
treeNotContainsValue("A", testMap);
assertEquals(testMap.size(), 9);
assertEquals(testMap.keysInAscendingOrder().size(), 9);
}
 
@Ignore
@Test
public void testRemovePut3() {
putItemsInRandomOrder(testMap, 10);
assertEquals(testMap.get(8), "H");
// Remove H
testMap.remove(8);
// Put H
testMap.put(1, "H");
assertFalse(testMap.containsOrderingKey(8));
treeNotContainsKey(8, testMap);
assertTrue(testMap.containsOrderingKey(1));
assertTrue(testMap.containsMapValue("H"));
assertFalse(testMap.containsMapValue("A"));
treeNotContainsValue("A", testMap);
assertEquals(testMap.size(), 9);
assertEquals(testMap.keysInAscendingOrder().size(), 9);
}
 
// @Ignore
@Test
public void testIterator1() {
putTenItemsInReverseOrder(testMap);
testKeyIter = testMap.keyIterator();
assertEquals(testKeyIter.next().toString(), "" + 1);
assertEquals(testKeyIter.next().toString(), "" + 2);
}
 
// @Ignore
@Test
public void testIterator2() {
putItemsInRandomOrder(testMap, 10);
testKeyIter = testMap.keyIterator();
assertEquals(testKeyIter.next().toString(), "" + 1);
assertEquals(testKeyIter.next().toString(), "" + 2);
}
 
@Test(expected = NoSuchElementException.class)
public void testIteratorIsEmpty() {
putItemsInRandomOrder(testMap, 10);
testKeyIter = testMap.keyIterator();
while (testKeyIter.hasNext()) {
testKeyIter.next();
}
testKeyIter.next();
}
 
// @Ignore
@Test
public void testIteratorRemove1() {
putTenItemsInOrder(testMap);
testKeyIter = testMap.keyIterator();
assertEquals(testKeyIter.next().toString(), "" + 1);
testKeyIter.remove();
treeNotContainsKey(1, testMap);
treeNotContainsValue("A", testMap);
assertFalse(testMap.containsMapValue("A"));
assertFalse(testMap.containsOrderingKey("1"));
assertEquals(testMap.size(), 9);
assertEquals(testMap.keysInAscendingOrder().size(), 9);
}
 
// @Ignore
@Test
public void testIteratorRemove2() {
putItemsInRandomOrder(testMap, 10);
testKeyIter = testMap.keyIterator();
assertEquals(testKeyIter.next().toString(), "" + 1);
assertEquals(testKeyIter.next().toString(), "" + 2);
testKeyIter.remove();
treeNotContainsKey(2, testMap);
treeNotContainsValue("B", testMap);
assertEquals(testMap.size(), 9);
assertFalse(testMap.containsMapValue("B"));
assertFalse(testMap.containsOrderingKey(2));
assertEquals(testMap.keysInAscendingOrder().size(), 9);
}
 
// @Ignore
@Test
public void testIteratorRemove3() {
putTenItemsInOrder(testMap);
testKeyIter = testMap.keyIterator();
assertEquals(testKeyIter.next().toString(), "" + 1);
assertEquals(testKeyIter.next().toString(), "" + 2);
testKeyIter.remove();
treeNotContainsKey(2, testMap);
treeNotContainsValue("B", testMap);
assertEquals(testMap.size(), 9);
assertFalse(testMap.containsMapValue("B"));
assertFalse(testMap.containsOrderingKey(2));
assertEquals(testMap.keysInAscendingOrder().size(), 9);
}
 
// @Ignore
@Test
public void testKeysAscendingOrder() {
putTenItemsInReverseOrder(testMap);
ArrayList<Integer> keys = testMap.keysInAscendingOrder();
for (int i = 1; i <= 10; i++) {
assertEquals(keys.get(i - 1).toString(), "" + i);
}
}
 
// @Ignore
@Test
public void testKeysAscendingOrder2() {
putItemsInRandomOrder(testMap, 20);
ArrayList<Integer> keys = testMap.keysInAscendingOrder();
for (int i = 1; i <= 10; i++) {
assertEquals(keys.get(i - 1).toString(), "" + i);
}
}
 
// @Ignore
@Test
public void testValuesAscendingOrder() {
putTenItemsInReverseOrder(testMap);
ArrayList<String> values = testMap.valuesInAscendingOrder();
for (int i = 1; i <= 10; i++) {
assertEquals(values.get(i - 1), Character.toString((char) (i + 64)));
}
}
 
// @Ignore
@Test
public void testValuesAscendingOrder2() {
putItemsInRandomOrder(testMap, 20);
ArrayList<String> values = testMap.valuesInAscendingOrder();
for (int i = 1; i <= 10; i++) {
assertEquals(values.get(i - 1), Character.toString((char) (i + 64)));
}
}
 
// @Ignore
@Test
public void testCeiling() {
putItemsInRandomOrder(testMap, 10);
assertEquals(testMap.ceiling(-1).toString(), "" + 1);
assertEquals(testMap.ceiling(0).toString(), "" + 1);
assertEquals(testMap.ceiling(1).toString(), "" + 2);
assertNull(testMap.ceiling(10));
}
 
// @Ignore
@Test
public void testCeiling2() {
putItemsInRandomOrder(testMap, 10);
testMap.remove(6);
assertEquals(testMap.ceiling(5).toString(), "" + 7);
assertNull(testMap.ceiling(10));
}
 
// @Ignore
@Test
public void testInvalidSubMap() {
putItemsInRandomOrder(testMap, 10);
assertNull(testMap.subMap(10, -1));
assertNull(testMap.subMap(10, 1));
//assertNull(testMap.subMap(20, 30));
//assertNull(testMap.subMap(-5, -1));
//assertNull(testMap.subMap(11, 11));
}
 
@Test
public void testSubmap1() {
putItemsInRandomOrder(testMap, 10);
OrderedMap<Integer, String> submap = (OrderedMap<Integer, String>) testMap
.subMap(4, 7);
assertEquals(submap.size(), 4);
assertEquals(submap.keysInAscendingOrder().size(), 4);
assertTrue(submap.containsMapValue("F"));
assertTrue(submap.containsMapValue("G"));
assertTrue(submap.containsOrderingKey(4));
assertTrue(submap.containsOrderingKey(5));
treeNotContainsKey(1, submap);
treeContainsKey(4, submap);
treeContainsKey(7, submap);
treeNotContainsValue("A", submap);
treeContainsValue("D", submap);
treeContainsValue("G", submap);
assertEquals(submap.get(4), "D");
assertEquals(testMap.get(4), "D");
assertNull(submap.ceiling(7));
}
 
// @Ignore
@Test
public void testSubMap2() {
// putTenItemsInReverseOrder(testMap);
putItemsInRandomOrder(testMap, 10);
OrderedMap<Integer, String> submap = (OrderedMap<Integer, String>) testMap
.subMap(3, 9);
assertEquals(submap.size(), 7);
assertEquals(submap.keysInAscendingOrder().size(), 7);
assertEquals(submap.get(4), "D");
assertEquals(testMap.get(4), "D");
assertEquals(submap.ceiling(1).toString(), "" + 3);
assertEquals(submap.get(2), null);
treeNotContainsKey(1, submap);
treeNotContainsValue("A", submap);
treeContainsValue("C", submap);
treeContainsValue("I", submap);
treeContainsKey(3, submap);
treeContainsKey(9, submap);
}
 
@Test
public void testSubMapEdgeCase1() {
putItemsInRandomOrder(testMap, 10);
OrderedMap<Integer, String> submap = (OrderedMap<Integer, String>) testMap
.subMap(3, 3);
assertEquals(submap.size(), 1);
assertEquals(submap.keysInAscendingOrder().size(), 1);
assertEquals(submap.get(3), "C");
assertEquals(testMap.get(3), "C");
assertEquals(submap.ceiling(1).toString(), "" + 3);
assertNull(submap.ceiling(3));
assertNull(submap.ceiling(4));
assertEquals(submap.get(2), null);
treeNotContainsKey(1, submap);
treeNotContainsValue("A", submap);
treeContainsKey(3, submap);
treeContainsValue("C", submap);
}
 
@Test
public void testRemoveRoot() {
testMap.put(7, "G");
testMap.put(3, "C");
testMap.put(9, "I");
testMap.put(2, "B");
testMap.put(6, "F");
testMap.put(5, "E");
testMap.put(8, "H");
testMap.put(4, "D");
testMap.put(1, "A");
testMap.put(10, "J");
assertEquals(testMap.size(), 10);
assertEquals(testMap.keysInAscendingOrder().size(), 10);
testMap.remove(7);
treeNotContainsKey(7, testMap);
treeNotContainsValue("G", testMap);
treeContainsKey(8, testMap);
treeContainsValue("H", testMap);
assertEquals(testMap.size(), 9);
assertEquals(testMap.keysInAscendingOrder().size(), 9);
}
 
// @Ignore
@Test
public void testRemoveRoot2() {
putTenItemsInReverseOrder(testMap);
testMap.remove(10);
treeNotContainsKey(10, testMap);
treeNotContainsValue("J", testMap);
treeContainsKey(9, testMap);
treeContainsValue("I", testMap);
assertEquals(testMap.size(), 9);
assertEquals(testMap.keysInAscendingOrder().size(), 9);
assertFalse(testMap.containsOrderingKey(10));
assertFalse(testMap.containsMapValue("J"));
}
 
@Test
public void testRemoveRoot3() {
putTenItemsInOrder(testMap);
testMap.remove(1);
treeNotContainsKey(1, testMap);
treeNotContainsValue("A", testMap);
treeContainsKey(2, testMap);
treeContainsValue("B", testMap);
assertEquals(testMap.size(), 9);
assertEquals(testMap.keysInAscendingOrder().size(), 9);
assertFalse(testMap.containsOrderingKey(1));
assertFalse(testMap.containsMapValue("A"));
}
 
// @Ignore
@Test
public void testRemoveLeaf() {
putTenItemsInOrder(testMap);
// Remove the leaf
testMap.remove(10);
assertEquals(testMap.size(), 9);
treeNotContainsKey(10, testMap);
treeNotContainsValue("J", testMap);
assertEquals(testMap.size(), 9);
assertEquals(testMap.keysInAscendingOrder().size(), 9);
assertFalse(testMap.containsOrderingKey(10));
assertFalse(testMap.containsMapValue("J"));
}
 
// @Ignore
@Test
public void testRemoveLeaf2() {
putTenItemsInReverseOrder(testMap);
// Remove the leaf
testMap.remove(1);
assertEquals(testMap.size(), 9);
assertEquals(testMap.keysInAscendingOrder().size(), 9);
treeNotContainsKey(1, testMap);
treeNotContainsValue("A", testMap);
assertEquals(testMap.size(), 9);
assertFalse(testMap.containsOrderingKey(1));
assertFalse(testMap.containsMapValue("A"));
}
 
@Test
public void testRemoveLeaf3() {
testMap.put(7, "G");
testMap.put(3, "C");
testMap.put(9, "I");
testMap.put(2, "B");
testMap.put(6, "F");
testMap.put(5, "E");
testMap.put(8, "H");
testMap.put(4, "D");
testMap.put(1, "A");
testMap.put(10, "J");
assertEquals(testMap.size(), 10);
testMap.remove(4);
treeNotContainsKey(4, testMap);
treeNotContainsValue("D", testMap);
treeContainsKey(5, testMap);
treeContainsValue("E", testMap);
assertEquals(testMap.size(), 9);
assertEquals(testMap.keysInAscendingOrder().size(), 9);
}
 
@Test
public void testRemoveLeaf4() {
putItemsInRandomOrder(testMap, 10);
assertEquals(testMap.size(), 10);
testMap.remove(1);
treeNotContainsKey(1, testMap);
treeNotContainsValue("A", testMap);
assertEquals(testMap.size(), 9);
assertEquals(testMap.keysInAscendingOrder().size(), 9);
}
 
@Test
public void testRemoveLeaf5() {
putItemsInRandomOrder(testMap, 10);
assertEquals(testMap.size(), 10);
testMap.remove(10);
treeNotContainsKey(10, testMap);
treeNotContainsValue("J", testMap);
assertEquals(testMap.size(), 9);
assertEquals(testMap.keysInAscendingOrder().size(), 9);
}
 
@Test
public void testRemoveInternal1() {
testMap.put(7, "G");
testMap.put(3, "C");
testMap.put(9, "I");
testMap.put(2, "B");
testMap.put(6, "F");
testMap.put(5, "E");
testMap.put(8, "H");
testMap.put(4, "D");
testMap.put(1, "A");
testMap.put(10, "J");
assertEquals(testMap.size(), 10);
testMap.remove(3);
treeNotContainsKey(3, testMap);
treeNotContainsValue("C", testMap);
treeContainsKey(4, testMap);
treeContainsValue("D", testMap);
treeContainsKey(6, testMap);
treeContainsValue("F", testMap);
assertEquals(testMap.size(), 9);
assertEquals(testMap.keysInAscendingOrder().size(), 9);
}
 
// @Ignore
@Test
public void testIteratorRemove() {
putItemsInRandomOrder(testMap, 11);
assertEquals(testMap.size(), 11);
assertTrue(testMap.containsOrderingKey(3));
testKeyIter = testMap.keyIterator();
while (testKeyIter.hasNext()) {
if (testKeyIter.next().equals(3)) {
testKeyIter.remove();
}
}
assertFalse(testMap.containsOrderingKey(3));
assertFalse(testMap.containsMapValue("C"));
treeNotContainsKey(3, testMap);
treeNotContainsValue("C", testMap);
assertEquals(testMap.size(), 10);
assertEquals(testMap.keysInAscendingOrder().size(), 10);
}
 
// @Ignore
@Test(expected = IllegalStateException.class)
public void testIteratorRemoveTwice() {
putItemsInRandomOrder(testMap, 10);
testKeyIter = testMap.keyIterator();
assertTrue(testKeyIter.next().equals(1));
testKeyIter.remove();
treeNotContainsKey(1, testMap);
assertFalse(testMap.containsOrderingKey(1));
testKeyIter.remove();
}
 
@Test
public void testIteratorRemoveThenAdd() {
putItemsInRandomOrder(testMap, 11);
assertEquals(testMap.get(3), "C");
assertTrue(testMap.containsOrderingKey(3));
testKeyIter = testMap.keyIterator();
assertTrue(testKeyIter.next().equals(1));
assertTrue(testKeyIter.next().equals(2));
assertTrue(testKeyIter.next().equals(3));
testKeyIter.remove();
treeNotContainsKey(3, testMap);
testMap.put(3, "Z");
assertTrue(testMap.containsOrderingKey(3));
assertTrue(testMap.containsMapValue("Z"));
assertEquals(testMap.get(3), "Z");
treeContainsValue("Z", testMap);
treeContainsKey(3, testMap);
assertEquals(testMap.keysInAscendingOrder().size(), 11);
}
 
@Test
public void testClear() {
putItemsInRandomOrder(testMap, 20);
assertEquals(testMap.size(), 20);
testMap.clear();
assertEquals(testMap.size(), 0);
assertEquals(testMap.keysInAscendingOrder().size(), 0);
}
 
@Test
public void testClearThenAdd() {
putItemsInRandomOrder(testMap, 20);
testMap.clear();
putItemsInRandomOrder(testMap, 10);
assertEquals(testMap.size(), 10);
assertEquals(testMap.keysInAscendingOrder().size(), 10);
}
 
@Test(expected = NullPointerException.class)
public void testPutNull() {
putItemsInRandomOrder(testMap, 10);
testMap.put(null, "Z");
assertEquals(testMap.size(), 10);
assertEquals(testMap.keysInAscendingOrder().size(), 10);
}
 
@Test
public void testGetNull() {
putItemsInRandomOrder(testMap, 10);
assertNull(testMap.get(null));
}
 
@Test
public void testRemoveNull() {
putItemsInRandomOrder(testMap, 10);
assertFalse(testMap.remove(null));
}

@Ignore
@Test
public void testDuplicatedKey(){
testMap.put(7, "G");
testMap.put(3, "C");
testMap.put(9, "I");
testMap.put(2, "B");
testMap.put(6, "F");
testMap.put(5, "E");
testMap.put(8, "H");
testMap.put(4, "D");
testMap.put(1, "A");
testMap.put(10, "J");
testMap.put(3, "Z");
assertTrue(testMap.containsOrderingKey(3));
assertTrue(testMap.containsMapValue("Z"));
assertFalse(testMap.containsMapValue("C"));
assertEquals(testMap.get(3), "Z");
treeContainsKey(3, testMap);
treeContainsValue("Z", testMap);
assertEquals(testMap.size(), 10);
assertEquals(testMap.keysInAscendingOrder().size(),10);
}

@Test
public void testDuplicatedValue(){
testMap.put(7, "G");
testMap.put(3, "G");
testMap.put(9, "I");
testMap.put(2, "B");
testMap.put(6, "F");
testMap.put(5, "E");
testMap.put(8, "H");
testMap.put(4, "D");
testMap.put(1, "A");
testMap.put(10, "J");
assertTrue(testMap.containsOrderingKey(3));
assertFalse(testMap.containsMapValue("C"));
assertEquals(testMap.get(3), "G");
assertEquals(testMap.get(7), "G");
treeContainsKey(3, testMap);
treeContainsValue("G", testMap);
assertEquals(testMap.size(), 10);
assertEquals(testMap.keysInAscendingOrder().size(),10);
}
 
private void treeNotContainsKey(int key, OrderedMap<Integer, String> map) {
ArrayList<Integer> keys = map.keysInAscendingOrder();
for (Integer k : keys) {
assertFalse(k.equals(key));
}
}
 
private void treeContainsKey(int key, OrderedMap<Integer, String> map) {
ArrayList<Integer> keys = map.keysInAscendingOrder();
ArrayList<Integer> dummy = new ArrayList<Integer>();
for (Integer k : keys) {
if (k.equals(key)) {
dummy.add(k);
}
}
assertTrue(dummy != null && dummy.size() == 1);
}
 
private void treeNotContainsValue(String value,
OrderedMap<Integer, String> map) {
ArrayList<String> values = map.valuesInAscendingOrder();
for (String v : values) {
assertFalse(v.equals(value));
}
}
 
private void treeContainsValue(String value, OrderedMap<Integer, String> map) {
ArrayList<String> values = map.valuesInAscendingOrder();
ArrayList<String> dummy = new ArrayList<String>();
for (String v : values) {
if (v.equals(value)) {
dummy.add(v);
}
}
//We allow duplicated values
assertTrue(dummy != null && dummy.size() != 0);
}
 
private void putTenItemsInOrder(OrderedMap<Integer, String> map) {
// 1 is mapped to A, 2 is mapped to B, and so on...
for (int i = 1; i <= 10; i++) {
map.put(i, Character.toString((char) (i + 64)));
}
}
 
private void putTenItemsInReverseOrder(OrderedMap<Integer, String> map) {
// 1 is mapped to A, 2 is mapped to B, and so on...
for (int i = 10; i >= 1; i--) {
map.put(i, Character.toString((char) (i + 64)));
}
}
 
private void putItemsInRandomOrder(OrderedMap<Integer, String> map, int n) {
int count = 0;
while (count < n) {
Random ran = new Random();
int key = ran.nextInt(n) + 1;
if (!map.containsOrderingKey(key)) {
// 1 is mapped to A, 2 is mapped to B, and so on...
map.put(key, Character.toString((char) (key + 64)));
count++;
}
}
}
}
