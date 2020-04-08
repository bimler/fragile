package calculator;

/**
 * A static class to perform operations for and on proper fractions.
 * 
 * @author Ethan Davis
 * @version 2/23/2019
 */
public class FractionOperations
{

  /**
   * Adds the right to the left. left + right.
   * 
   * @param left
   *          left side of the addition
   * @param right
   *          right side of the addition
   * @return result
   */
  public static Fraction add(Fraction left, Fraction right)
  {
    Fraction result;
    Fraction sameDenomLeft;
    Fraction sameDenomRight;
    int newNumer;
    int newDenom;

    sameDenomLeft = new Fraction(left.getNumerator() * right.getDenominator(),
        left.getDenominator() * right.getDenominator(), false);
    sameDenomRight = new Fraction(right.getNumerator() * left.getDenominator(),
        right.getDenominator() * left.getDenominator(), false);

    newNumer = sameDenomLeft.getNumerator() + sameDenomRight.getNumerator();
    newDenom = sameDenomLeft.getDenominator();
    result = new Fraction(newNumer, newDenom, true);

    return result;
  }

  /**
   * Divides the left by the right. left / right.
   * 
   * @param left
   *          left side of the division
   * @param right
   *          right side of the division
   * @return result
   * @throws IllegalArgumentException
   *           if dividing by zero
   */
  public static Fraction divide(Fraction left, Fraction right)
  {
    Fraction result;
    int newNumer;
    int newDenom;

    if (right.getNumerator() == 0)
    {
      throw new IllegalArgumentException();
    }

    newNumer = left.getNumerator() * right.getDenominator();
    newDenom = left.getDenominator() * right.getNumerator();
    result = new Fraction(newNumer, newDenom, true);

    return result;
  }

  /**
   * Calculates the mediant of two fractions.
   * 
   * a/b mediant c/d = (a+c)/(b+d) Often called the freshman sum.
   * 
   * @param left
   *          fraction a
   * @param right
   *          fraction b
   * @return mediant
   */
  public static Fraction freshmanSum(Fraction left, Fraction right)
  {
    Fraction result;

    result = new Fraction(left.getNumerator() + right.getNumerator(),
        left.getDenominator() + right.getDenominator(), true);

    return result;
  }

  /**
   * Recursively finds the Greatest common divisor.
   * 
   * @param numerator
   *          the top of the fraction
   * @param denominator
   *          the bottom of the fraction
   * @return greatest common denominator
   */
  public static int gcd(int numerator, int denominator)
  {
    int result;

    if (denominator == 0)
      result = numerator;
    else
      result = gcd(denominator, numerator % denominator);

    return result;
  }

  /**
   * Takes two fractions and changes them to have the same denominator.
   * 
   * @param left
   *          left fraction
   * @param right
   *          right fraction
   * @return left and right fraction
   */
  public static Fraction[] getSameDenom(Fraction left, Fraction right)
  {
    Fraction sameDenomLeft;
    Fraction sameDenomRight;
    Fraction[] results = new Fraction[2];

    sameDenomLeft = new Fraction(left.getNumerator() * right.getDenominator(),
        left.getDenominator() * right.getDenominator(), false);
    sameDenomRight = new Fraction(right.getNumerator() * left.getDenominator(),
        right.getDenominator() * left.getDenominator(), false);

    results[0] = sameDenomLeft;
    results[1] = sameDenomRight;

    return results;
  }

  /**
   * Multiplies fraction by itself power times.
   * 
   * @param fraction
   *          the fraction
   * @param power
   *          to power
   * @return fraction ^ power
   */
  public static Fraction integerPower(Fraction fraction, int power)
  {
    Fraction result;
    int numerator = fraction.getNumerator();
    int denominator = fraction.getDenominator();

    if (power < 0)
    {
      result = integerPower(new Fraction(denominator, numerator, true), Math.abs(power));
    }
    else if (power == 0)
    {
      result = new Fraction(1, 1, true);
    }
    else if (power == 1)
    {
      result = new Fraction(numerator, denominator, true);
    }
    else // power > 1
    {
      for (int i = 1; i < power; i++)
      {
        numerator = numerator * fraction.getNumerator();
        denominator = denominator * fraction.getDenominator();
      }

      result = new Fraction(numerator, denominator, true);
    }

    return result;
  }

  /**
   * Calculates the mediant of two fractions.
   * 
   * @param frac1 - a fraction
   * @param frac2 - another fraction
   * @return Fraction - the result
   */
  public static Fraction mediant(Fraction frac1, Fraction frac2)
  {
    return new Fraction(frac1.getNumerator() + frac2.getNumerator(),
        frac1.getDenominator() + frac2.getDenominator(), true);
  }

  /**
   * Multiplies the left by the right. left * right.
   * 
   * @param left
   *          left side of the multiplication
   * @param right
   *          right side of the multiplication
   * @return result
   */
  public static Fraction multiply(Fraction left, Fraction right)
  {
    Fraction result;
    int newNumer;
    int newDenom;

    newNumer = left.getNumerator() * right.getNumerator();
    newDenom = left.getDenominator() * right.getDenominator();
    result = new Fraction(newNumer, newDenom, true);

    return result;
  }

  /**
   * Subtracts the right from the left. left - right.
   * 
   * @param left
   *          left side of the subtraction
   * @param right
   *          right side of the subtraction
   * @return result
   */
  public static Fraction subtract(Fraction left, Fraction right)
  {
    Fraction result;
    Fraction sameDenomLeft;
    Fraction sameDenomRight;
    int newNumer;
    int newDenom;

    sameDenomLeft = new Fraction(left.getNumerator() * right.getDenominator(),
        left.getDenominator() * right.getDenominator(), false);
    sameDenomRight = new Fraction(right.getNumerator() * left.getDenominator(),
        right.getDenominator() * left.getDenominator(), false);

    newNumer = sameDenomLeft.getNumerator() - sameDenomRight.getNumerator();
    newDenom = sameDenomLeft.getDenominator();
    result = new Fraction(newNumer, newDenom, false);

    return result;
  }
}
