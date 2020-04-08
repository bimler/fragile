package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import calculator.MixedFraction;

class MixedFractionTest
{

  @Test
  void testDenominator()
  {
    MixedFraction fraction = new MixedFraction(5, 7, 8);
    assertEquals(fraction.getDenominator(), 8);
  }

  @Test
  void testEquals()
  {
    MixedFraction equals = new MixedFraction(4, 4, 6);
    MixedFraction other = new MixedFraction(4, 2, 3);

    assertEquals(equals.compareTo(other), 0);
  }
  
  @Test
  void testFrac()
  {
    
    MixedFraction fraction = new MixedFraction(5, 7, 8, false);
    assertEquals(fraction.getWhole(), 5);
    assertEquals(fraction.getNumerator(), 7);
    assertEquals(fraction.getDenominator(), 8);
  }
  
  @Test
  void testGreaterThan()
  {
    MixedFraction bigger = new MixedFraction(4, 5, 6);
    MixedFraction other = new MixedFraction(4, 2, 3);

    assertEquals(bigger.compareTo(other), 1);
  }

  @Test
  void testInvalidFrac()
  {
    MixedFraction fraction = new MixedFraction(5, 7, -8, true);
    
    assertEquals(fraction.getDenominator(), 1);
    
    assertThrows(IllegalArgumentException.class, () -> { 
      new MixedFraction(5, -4, 3, true); });
    
    MixedFraction weird1 = new MixedFraction(-5, 7, 8, true);
    MixedFraction weird2 = new MixedFraction(0, -7, 8, true);
    
    assertEquals(weird1.getNumerator(), 7);
    assertEquals(weird1.getDenominator(), 8);
    assertEquals(weird1.getWhole(), -5);
    
    assertEquals(weird2.getNumerator(), -7);
    assertEquals(weird2.getDenominator(), 8);
    assertEquals(weird2.getWhole(), 0);
    
  }

  @Test
  void testLessThan()
  {
    MixedFraction smaller = new MixedFraction(4, 5, 6);
    MixedFraction other = new MixedFraction(5, 7, 8);

    assertEquals(smaller.compareTo(other), -1);
  }

  @Test
  void testNumerator()
  {
    MixedFraction fraction = new MixedFraction(5, 7, 8);
    assertEquals(fraction.getNumerator(), 7);
  }

  @Test
  void testStyle()
  {
    
    MixedFraction fraction = new MixedFraction(5, 7, 8);
    fraction.setStyle(0);
    assertEquals(MixedFraction.BAR_STYLE, fraction.getStyle());
  }

  @Test
  void testtToStrings()
  {
    MixedFraction test1 = new MixedFraction(4, 5, 6);
    MixedFraction test2 = new MixedFraction(4, 0, 6);
    MixedFraction test3 = new MixedFraction(0, 5, 6);
    MixedFraction test4 = new MixedFraction(0, 0, 6);
    
    assertEquals("4 5/6", test1.toString(true));
    assertEquals("4 5/6", test1.toString(false));
    assertEquals("4 5/6", test1.toString());
    assertEquals("4", test2.toString());
    assertEquals("5/6", test3.toString());
    assertEquals("0", test4.toString());
  }

  @Test
  void testValue()
  {

    MixedFraction fraction = new MixedFraction(5, 7, 8);
    assertEquals(fraction.getValue(), 5.875);
  }
  
  @Test
  void testWhole()
  {
    MixedFraction fraction = new MixedFraction(5, 7, 8);
    assertEquals(fraction.getWhole(), 5);
  }

}
