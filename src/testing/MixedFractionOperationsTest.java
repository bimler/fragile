package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import calculator.Fraction;
import calculator.MixedFraction;
import calculator.MixedFractionOperations;
import gui.DisplayField;
import gui.FragileWindow;

class MixedFractionOperationsTest
{

  @Test
  void testAddDenominator()
  {
    MixedFraction a = new MixedFraction(6, 3, 8);
    MixedFraction b = new MixedFraction(8, 4, 9);
    MixedFraction expected = new MixedFraction(14, 59, 72);

    assertEquals(MixedFractionOperations.add(a, b).getDenominator(), expected.getDenominator());
  }

  @Test
  void testAddNumerator()
  {
    new MixedFractionOperations();
    MixedFraction a = new MixedFraction(1, 4, 5);
    MixedFraction b = new MixedFraction(3, 8, 12);
    MixedFraction expected = new MixedFraction(5, 7, 15);

    assertEquals(MixedFractionOperations.add(a, b).getNumerator(), expected.getNumerator());

    MixedFraction c = new MixedFraction(-1, 4, 5);
    MixedFraction d = new MixedFraction(-3, 8, 12);

    assertEquals(MixedFractionOperations.add(c, d).getNumerator(), expected.getNumerator());
  }

  @Test
  void testAddWhole()
  {
    MixedFraction a = new MixedFraction(3, 15, 16);
    MixedFraction b = new MixedFraction(2, 15, 16);
    MixedFraction expected = new MixedFraction(6, 14, 16);

    assertEquals(MixedFractionOperations.add(a, b).getWhole(), expected.getWhole());
  }

  @Test
  void testDivideDenominator()
  {
    MixedFraction a = new MixedFraction(6, 3, 8);
    MixedFraction b = new MixedFraction(8, 4, 9);
    MixedFraction expected = new MixedFraction(0, 459, 608);

    assertEquals(MixedFractionOperations.divide(a, b).getDenominator(), expected.getDenominator());
  }

  @Test
  void testDivideNumerator()
  {
    MixedFraction a = new MixedFraction(1, 4, 5);
    MixedFraction b = new MixedFraction(3, 8, 12);
    MixedFraction expected = new MixedFraction(0, 108, 220);

    assertEquals(MixedFractionOperations.divide(a, b).getNumerator(), expected.getNumerator());

    MixedFraction c = new MixedFraction(-1, 4, 5);
    MixedFraction d = new MixedFraction(-3, 8, 12);
    assertEquals(MixedFractionOperations.divide(c, d).getNumerator(), expected.getNumerator());

  }

  @Test
  void testDivideWhole()
  {
    MixedFraction a = new MixedFraction(3, 15, 16);
    MixedFraction b = new MixedFraction(2, 15, 16);
    MixedFraction expected = new MixedFraction(0, 1008, 752);

    assertEquals(MixedFractionOperations.divide(a, b).getWhole(), expected.getWhole());
  }

  @Test
  void testFreshmanSum()
  {
    MixedFraction a = new MixedFraction(3, 1, 4);
    MixedFraction b = new MixedFraction(2, 2, 3);
    MixedFraction expected = MixedFractionOperations.freshmanSum(a, b);

    assertEquals(expected.getWhole(), 3);
    assertEquals(expected.getNumerator(), 0);
    assertEquals(expected.getDenominator(), 1);
  }

  @Test
  void testPower()
  {
    MixedFraction a = new MixedFraction(3, 1, 4);
    MixedFraction expected = MixedFractionOperations.power(a, 2, null);

    assertEquals(expected.getWhole(), 10);
    assertEquals(expected.getNumerator(), 9);
    assertEquals(expected.getDenominator(), 16);
    
    a = new MixedFraction( 0,1,4);
    expected = MixedFractionOperations.power(a, -1, null);

    assertEquals(expected.getWhole(), 4 );
    assertEquals(expected.getNumerator(), 0);
    assertEquals(expected.getDenominator(), 1);
  }

  @Test
  void testMediant()
  {
    MixedFraction a = new MixedFraction(3, 1, 4);
    MixedFraction b = new MixedFraction(2, 2, 3);
    MixedFraction expected = MixedFractionOperations.mediant(a, b);

    assertEquals(expected.getWhole(), 3);
    assertEquals(expected.getNumerator(), 0);
    assertEquals(expected.getDenominator(), 1);
  }

  @Test
  void testMultiplyDenominator()
  {
    MixedFraction a = new MixedFraction(6, 3, 8);
    MixedFraction b = new MixedFraction(8, 4, 9);
    MixedFraction expected = new MixedFraction(53, 5, 6);

    assertEquals(MixedFractionOperations.multiply(a, b).getDenominator(),
        expected.getDenominator());
  }

  @Test
  void testMultiplyNumerator()
  {
    MixedFraction a = new MixedFraction(1, 4, 5);
    MixedFraction b = new MixedFraction(3, 8, 12);
    MixedFraction expected = new MixedFraction(6, 3, 5);

    assertEquals(MixedFractionOperations.multiply(a, b).getNumerator(), expected.getNumerator());

    MixedFraction c = new MixedFraction(-1, 4, 5);
    MixedFraction d = new MixedFraction(-3, 8, 12);

    assertEquals(MixedFractionOperations.multiply(c, d).getNumerator(), expected.getNumerator());
  }

  @Test
  void testMultiplyWhole()
  {
    MixedFraction a = new MixedFraction(3, 15, 16);
    MixedFraction b = new MixedFraction(2, 15, 16);
    MixedFraction expected = new MixedFraction(11, 145, 256);

    assertEquals(MixedFractionOperations.multiply(a, b).getWhole(), expected.getWhole());
  }

  @Test
  void testProperFractions()
  {
    MixedFraction a = new MixedFraction(3, 15, 16);
    Fraction expected = MixedFractionOperations.convertToProperFraction(a);

    assertEquals(expected.getNumerator(), 63);
    assertEquals(expected.getDenominator(), 16);
  }

  @Test
  void testSubtractDenominator()
  {
    MixedFraction a = new MixedFraction(6, 3, 8);
    MixedFraction b = new MixedFraction(8, 4, 9);
    MixedFraction expected = new MixedFraction(-3, 67, 72);

    assertEquals(MixedFractionOperations.subtract(a, b).getDenominator(),
        expected.getDenominator());
  }

  @Test
  void testSubtractNegativeResult()
  {
    MixedFraction a = new MixedFraction(0, 1, 2);
    MixedFraction b = new MixedFraction(1, 0, 1);

    MixedFraction result = MixedFractionOperations.subtract(a, b);

    assertEquals("-1/2", result.toString());
  }

  @Test
  void testSubtractNumerator()
  {
    MixedFraction a = new MixedFraction(1, 4, 5);
    MixedFraction b = new MixedFraction(3, 8, 12);
    MixedFraction expected = new MixedFraction(-1, 13, 15);

    assertEquals(MixedFractionOperations.subtract(a, b).getNumerator(), expected.getNumerator());

    MixedFraction c = new MixedFraction(-1, 4, 5);
    MixedFraction d = new MixedFraction(-3, 8, 12);
    assertEquals(MixedFractionOperations.subtract(c, d).getNumerator(), expected.getNumerator());

  }

  @Test
  void testSubtractWhole()
  {
    MixedFraction a = new MixedFraction(3, 15, 16);
    MixedFraction b = new MixedFraction(2, 15, 16);
    MixedFraction expected = new MixedFraction(1, 0, 16);

    assertEquals(MixedFractionOperations.subtract(a, b).getWhole(), expected.getWhole());
  }
  
  @Test
  void testToStringHTML()
  {
	  MixedFraction frac = new MixedFraction( 1,1,4,true);
	  assertEquals( frac.toStringHTML(), "<span class=\"whole\">1 </span><span class=\"top\">1</span>/<span class =\"bottom\">4</span>");
  }

}
