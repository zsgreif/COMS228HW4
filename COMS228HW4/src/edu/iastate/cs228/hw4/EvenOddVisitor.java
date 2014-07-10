package edu.iastate.cs228.hw4;

public class EvenOddVisitor<O extends Comparable<? super O>> implements Visitor<O>
{
  private int evenNums = 0;
  private int oddNums = 0;
  
  public void visit(BSTNode<O> node)
  {
    
  }
  
  public int getNumEvens()
  {
    return this.evenNums;
  }
  
  public int getNumOdds()
  {
    return this.oddNums;
  }
}
