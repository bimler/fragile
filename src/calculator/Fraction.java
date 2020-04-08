package calculator;

/**
 * A proper fraction with a numerator and denominator.
 * 
 */
public class Fraction extends Number
{
  private final int numerator;
  private final int denominator;

  /**
   * Constructs fraction.
   * 
   * @param numerator
   *          top of fraction
   * @param denominator
   *          bottom of fraction
   * @param simplify
   *          divide numerator and denominator by gcd
   * @throws IllegalArgumentException
   *           denominator equals zero
   */
  public Fraction(int numerator, int denominator, boolean simplify)
  {
    int denom = denominator;
    int num = numerator;
    if (simplify)// ie. 50/100 = 1/2
    {
      if (denom == 0)
      {
        throw new IllegalArgumentException();
      }
      else if (denom < 0)
      {
        denom *= -1;
      }

      int commonMultiple = Math.abs(FractionOperations.gcd(num, denominator));

      num /= commonMultiple;
      denom /= commonMultiple;
    }

    this.numerator = num;
    this.denominator = denom;
  }

  /**
   * Compares this value with another numbers value.
   * 
   * @return less than, equals, or greater than
   */
  @Override
  public int compareTo(Number o)
  {
    double thisValue = this.getValue();
    double otherValue = o.getValue();
    int result;

    if (thisValue < otherValue)
      result = -1;
    else if (thisValue > otherValue)
      result = 1;
    else
      result = 0;

    return result;
  }

  /**
   * Gets denominator.
   * 
   * @return denominator
   */
  public int getDenominator()
  {
    return this.denominator;
  }

  /**
   * Gets numerator.
   * 
   * @return numerator
   */
  public int getNumerator()
  {
    return this.numerator;
  }

  /**
   * Returns the fraction as a double.
   * 
   * @return double value
   */
  @Override
  public double getValue()
  {
    return (double) numerator / (double) denominator;
  }

  /**
   * returns string representation of fraction.
   * 
   * @return String - the representation
   */
  public String toString()
  {
    return String.format("%d/%d", numerator, denominator);
  }

  /**
   * HTML representation of fraction.
   * 
   * @return String - the representation.
   */
  public String toStringHTML()
  {
    return String.format("<span class=\"top\">%d</span>/<span class =\"bottom\">%d</span>",
        getNumerator(), getDenominator());
  }
}
