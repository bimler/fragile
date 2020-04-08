package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import calculator.FareySequence;
import calculator.Fraction;

class FareySequenceTest
{

  @Test
  void testCreateSequenceWrongOrder()
  {
    FareySequence sequence = new FareySequence(0);
    
    assertTrue(sequence.get(0).compareTo(new Fraction(0, 1, false)) == 0);
  }
  
  @Test
  void testCreateSequenceGoodOrder()
  {
    FareySequence sequence = new FareySequence(10);
    
    assertTrue(sequence.get(1).compareTo(new Fraction(1, 10, false)) == 0);
  }

  @Test
  void testSize()
  {
    FareySequence sequence = new FareySequence(5);
    
    assertTrue(sequence.size() == 11);
  }
  
  @Test
  void testGetNext()
  {
    FareySequence sequence = new FareySequence(5);
    
    assertTrue(sequence.getNext(new Fraction(0, 1, false))
        .compareTo(new Fraction(1, 5, false)) == 0);
  }
  
  @Test
  void testGetNextOutOfBounds()
  {
    FareySequence sequence = new FareySequence(5);
    
    assertTrue(sequence.getNext(new Fraction(1, 1, false))
        .compareTo(new Fraction(0, 1, false)) == 0);
  }
  
  @Test
  void testInvalidSearch()
  {
    FareySequence sequence = new FareySequence(5);
    
    assertTrue(sequence.search(new Fraction(1, 6, false)) == -1);
  }
  
  @Test
  void testSearch()
  {
    FareySequence sequence = new FareySequence(5);
    
    assertTrue(sequence.search(new Fraction(1, 5, false)) == 1);
  }
}
