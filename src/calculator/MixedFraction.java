package calculator;

/**
 * A Mixed fraction with a whole value and a proper fraction.
 * 
 */
public class MixedFraction extends Number
{
  public static final int BAR_STYLE = 0;
  public static final int SOLIDUS_STYLE = 1;
  public static final int SLASH_STYLE = 2;

  private int style;
  private final int whole;
  private final Fraction fraction;

  /**
   * Constructs a mixed fraction.
   * 
   * @param whole
   *          number outside of fraction
   * @param numerator
   *          top of fraction
   * @param denominator
   *          bottom of fraction
   * @throws IllegalArgumentException
   *           denominator equals zero
   */
  public MixedFraction(int whole, int numerator, int denominator)
  {
    this(whole, numerator, denominator, true);
  }

  /**
   * Constructs a mixed fraction.
   * 
   * @param whole
   *          number outside of fraction
   * @param numerator
   *          top of fraction
   * @param denominator
   *          bottom of fraction
   * @param simplify
   *          weather to simplify 
   * @throws IllegalArgumentException
   *           denominator equals zero
   */
  public MixedFraction(int whole, int numerator, int denominator, boolean simplify)
  {
    int denom = denominator;
    if (simplify)
    {
      if (denom <= 0)
      {
        denom = 1;
      }
      if (whole != 0 && numerator < 0)
      {
        throw new IllegalArgumentException();
      }

      int posNum, posDen, posWhole;

      int finalWhole;

      // Force positive values
      posNum = Math.abs(numerator);
      posDen = Math.abs(denom);
      posWhole = Math.abs(whole);

      // Calculate positive whole

      finalWhole = posNum / posDen + posWhole;

      // Make negative if whole or numerator was negative
      if (whole < 0 || numerator < 0)
      {
        if (finalWhole == 0)
        {
          posNum *= -1;
        }
        else
        {
          finalWhole *= -1;
        }
      }

      this.whole = finalWhole;

      this.fraction = new Fraction(posNum % posDen, posDen, true);

      style = 0;
    }
    else
    {
      this.whole = whole;
      this.fraction = new Fraction(numerator, denom, false);
    }
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
    return fraction.getDenominator();
  }

  /**
   * Gets numerator.
   * 
   * @return numerator
   */
  public int getNumerator()
  {
    return fraction.getNumerator();
  }

  /**
   * Gets style.
   * 
   * @return style
   */
  public int getStyle()
  {
    return style;
  }

  /**
   * Returns the fraction as a double.
   * 
   * @return double value
   */
  @Override
  public double getValue()
  {
    return (double) whole + fraction.getValue();
  }

  /**
   * Gets value outside of fraction.
   * 
   * @return whole
   */
  public int getWhole()
  {
    return whole;
  }

  /**
   * sets style.
   * 
   * @param change
   *          - style to change to.
   */
  public void setStyle(int change)
  {
    style = change;
  }

  @Override
  public String toString()
  {
    return toStringSlash();
  }

  /**
   * toString for slash style.
   * 
   * @param full
   *          whether there is a whole part of the fraction.
   * @return the string representation of the mixed fraction.
   */
  public String toString(boolean full)
  {
    if (full)
    {
      return String.format("%d %d/%d", getWhole(), getNumerator(), 
          getDenominator());
    }

    return toStringSlash();
  }

  /**
   * toString for html formatting.
   * 
   * @return the html formatted string of the mixed fraction.
   */
  public String toStringHTML()
  {
    return String.format(
        "<span class=\"whole\">%d </span><span class=\"top\">%d</span>/"
        + "<span class =\"bottom\">%d</span>",
        getWhole(), getNumerator(), getDenominator());
  }

  /**
   * converts object to a string in slash style.
   * 
   * @return String representation of the object.
   */
  public String toStringSlash()
  {
    if (getWhole() == 0)
    {
      if (getNumerator() != 0)
      {
        return String.format("%d/%d", getNumerator(), getDenominator());
      }
      else
      {
        return String.format("0");
      }
    }
    else
    {
      if (getNumerator() != 0)
      {
        return String.format("%d %d/%d", getWhole(), getNumerator(), getDenominator());
      }
      else
      {
        return String.format("%d", getWhole());
      }
    }
  }

}
