package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import calculator.Fraction;

class FractionTest
{

  @Test
  void testDenominator()
  {
    Fraction fraction = new Fraction(6, 8, true);
    assertEquals(fraction.getDenominator(), 4);

    Fraction negFraction = new Fraction(6, -8, true);
    assertEquals(4, negFraction.getDenominator());
  }

  @Test
  void testEquals()
  {
    Fraction equals = new Fraction(4, 6, true);
    Fraction other = new Fraction(2, 3, true);

    assertEquals(equals.compareTo(other), 0);
  }

  @Test
  void testGreaterThan()
  {
    Fraction bigger = new Fraction(5, 6, true);
    Fraction other = new Fraction(2, 3, true);

    assertEquals(bigger.compareTo(other), 1);
  }

  @Test
  void testLessThan()
  {
    Fraction smaller = new Fraction(5, 6, true);
    Fraction other = new Fraction(7, 8, true);

    assertEquals(smaller.compareTo(other), -1);
  }

  @Test
  void testNumerator()
  {
    Fraction fraction = new Fraction(6, 8, true);
    assertEquals(fraction.getNumerator(), 3);
  }

  @Test
  void testToString()
  {
    Fraction bigger = new Fraction(5, 6, true);

    assertEquals("5/6", bigger.toString());
  }

  @Test
  void testValue()
  {
    Fraction fraction = new Fraction(7, 8, true);
    assertEquals(fraction.getValue(), 0.875);
  }

  @Test
  void testZeroDenom()
  {
    assertThrows(IllegalArgumentException.class, () -> {
      new Fraction(7, 0, true);
    });
  }
  
  @Test
  void testToStringHTML()
  {
	  Fraction frac = new Fraction( 1,4,true);
	  assertEquals( frac.toStringHTML(), "<span class=\"top\">1</span>/<span class =\"bottom\">4</span>");
  }

}
