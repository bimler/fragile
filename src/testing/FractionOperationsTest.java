package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import calculator.Fraction;
import calculator.FractionOperations;

class FractionOperationsTest
{

  @Test
  void testAddDenominator()
  {
    Fraction a = new Fraction(5, 9, true);
    Fraction b = new Fraction(7, 12, true);
    Fraction expected = new Fraction(123, 108, true);

    assertEquals(FractionOperations.add(a, b).getDenominator(), expected.getDenominator());
  }

  @Test
  void testAddNumerator()
  {
    new FractionOperations();

    Fraction a = new Fraction(1, 4, true);
    Fraction b = new Fraction(3, 8, true);
    Fraction expected = new Fraction(5, 8, true);
    assertEquals(FractionOperations.add(a, b).getNumerator(), expected.getNumerator());

    a = new Fraction(4, 6, true);
    b = new Fraction(2, 5, true);
    expected = new Fraction(16, 15, true);
    assertEquals(FractionOperations.add(a, b).getNumerator(), expected.getNumerator());
  }

  @Test
  void testdivideByZero()
  {
    Fraction a = new Fraction(5, 9, true);
    Fraction b = new Fraction(0, 12, true);

    assertThrows(IllegalArgumentException.class, () -> {
      FractionOperations.divide(a, b);
    });
  }

  @Test
  void testdivideDenominator()
  {
    Fraction a = new Fraction(5, 9, true);
    Fraction b = new Fraction(7, 12, true);
    Fraction expected = new Fraction(60, 63, true);

    assertEquals(FractionOperations.divide(a, b).getDenominator(), expected.getDenominator());
  }

  @Test
  void testdivideNumerator()
  {
    Fraction a = new Fraction(1, 4, true);
    Fraction b = new Fraction(3, 8, true);
    Fraction expected = new Fraction(2, 3, true);

    assertEquals(FractionOperations.divide(a, b).getNumerator(), expected.getNumerator());
  }

  @Test
  void testFreshmanSum()
  {
    Fraction a = new Fraction(5, 9, true);
    Fraction b = new Fraction(7, 12, true);
    Fraction expected = FractionOperations.freshmanSum(a, b);

    assertEquals(expected.getNumerator(), 4);
    assertEquals(expected.getDenominator(), 7);
  }

  @Test
  void testGcd()
  {
    int multiple = FractionOperations.gcd(34, 51);
    assertEquals(multiple, 17);

    multiple = FractionOperations.gcd(4, 8);
    assertEquals(multiple, 4);

    multiple = FractionOperations.gcd(0, 8);
    assertEquals(multiple, 8);

    multiple = FractionOperations.gcd(4, 0);
    assertEquals(multiple, 4);
  }

  @Test
  void testGetSameDenom()
  {
    Fraction a = new Fraction(1, 4, true);
    Fraction b = new Fraction(1,5,true);
    Fraction expected = FractionOperations.getSameDenom(a,b)[0];
    
    assertEquals( expected.getDenominator(), 20);


  }

  @Test
  void testIntegerPower()
  {
    Fraction a = new Fraction(1, 4, true);
    Fraction expected = FractionOperations.integerPower(a, 1);

    assertEquals(expected.getNumerator(), 1);
    assertEquals(expected.getDenominator(), 4);
    
    expected = FractionOperations.integerPower(a, 2);

    assertEquals(expected.getNumerator(), 1);
    assertEquals(expected.getDenominator(), 16);
    
    expected = FractionOperations.integerPower(a, -1);

    assertEquals(expected.getNumerator(), 4);
    assertEquals(expected.getDenominator(), 1);
    
    expected = FractionOperations.integerPower(a, 0);

    assertEquals(expected.getNumerator(), 1);
    assertEquals(expected.getDenominator(), 1);
  }

  @Test
  void testMediant()
  {
    Fraction a = new Fraction(5, 9, true);
    Fraction b = new Fraction(7, 12, true);
    Fraction expected = FractionOperations.mediant(a, b);

    assertEquals(expected.getNumerator(), 4);
    assertEquals(expected.getDenominator(), 7);
  }

  @Test
  void testMultiplyDenominator()
  {
    Fraction a = new Fraction(5, 9, true);
    Fraction b = new Fraction(7, 12, true);
    Fraction expected = new Fraction(35, 108, true);

    assertEquals(FractionOperations.multiply(a, b).getDenominator(), expected.getDenominator());
  }

  @Test
  void testMultiplyNumerator()
  {
    Fraction a = new Fraction(1, 4, true);
    Fraction b = new Fraction(3, 8, true);
    Fraction expected = new Fraction(3, 32, true);

    assertEquals(FractionOperations.multiply(a, b).getNumerator(), expected.getNumerator());
  }

  @Test
  void testSubtractDenominator()
  {
    Fraction a = new Fraction(5, 7, true);
    Fraction b = new Fraction(5, 12, true);
    Fraction expected = new Fraction(25, 84, true);

    assertEquals(FractionOperations.subtract(a, b).getDenominator(), expected.getDenominator());
  }
  
  @Test
  void testSubtractNumerator()
  {
    Fraction a = new Fraction(3, 5, true);
    Fraction b = new Fraction(3, 9, true);
    Fraction expected = new Fraction(4, 15, true);

    assertEquals(FractionOperations.subtract(a, b).getNumerator(), expected.getNumerator());
  }
  
  @Test
  void testSubtractNumeratorNegative()
  {
    Fraction a = new Fraction(3, 5, true);
    Fraction b = new Fraction(6, 9, true);
    Fraction expected = new Fraction(-1, 15, true);

    assertEquals(FractionOperations.subtract(a, b).getNumerator(), expected.getNumerator());
  }
  

}
