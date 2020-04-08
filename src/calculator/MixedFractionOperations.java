package calculator;

import gui.DisplayField;

/**
 * A static class that performs operations on mixed fractions.
 * 
 */
public class MixedFractionOperations
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
  public static MixedFraction add(MixedFraction left, MixedFraction right)
  {
    MixedFraction result;

    int denom, leftNum, rightNum, resultNum;

    // Force common denominator
    denom = left.getDenominator() * right.getDenominator();

    // Determine initial value before conversion
    leftNum = Math.abs(left.getWhole()) * left.getDenominator() + Math.abs(left.getNumerator());
    if (left.getWhole() < 0)
    {
      leftNum *= -1;
    }
    rightNum = Math.abs(right.getWhole()) * Math.abs(right.getDenominator())
        + Math.abs(right.getNumerator());
    if (right.getWhole() < 0)
    {
      rightNum *= -1;
    }

    // Adjust for common denominator
    leftNum *= right.getDenominator();
    rightNum *= left.getDenominator();

    // Calculate resultNum
    resultNum = leftNum + rightNum;

    result = new MixedFraction(0, resultNum, denom);

    return result;
  }

  /**
   * Multiplies the whole by the denominator and adds it to the numerator to convert.
   * 
   * @param mixed
   *          a mixed fraction to convert
   * @return a proper fraction
   */
  public static Fraction convertToProperFraction(MixedFraction mixed)
  {
    int numerator = mixed.getNumerator() + (mixed.getWhole() * mixed.getDenominator());
    int denominator = mixed.getDenominator();

    return new Fraction(numerator, denominator, true);
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
  public static MixedFraction divide(MixedFraction left, MixedFraction right)
  {
    MixedFraction result;

    int denom, leftNum, rightNum, resultNum;

    // Determine improper numerator
    leftNum = Math.abs(left.getWhole()) * Math.abs(left.getDenominator())
        + Math.abs(left.getNumerator());
    rightNum = Math.abs(right.getWhole()) * Math.abs(right.getDenominator())
        + Math.abs(right.getNumerator());

    // Adjust for negative values
    if (left.getWhole() < 0)
    {
      leftNum *= -1;
    }
    if (right.getWhole() < 0)
    {
      rightNum *= -1;
    }

    // Calculate resultNum
    resultNum = leftNum * right.getDenominator();

    // Calculate denom
    denom = left.getDenominator() * rightNum;

    // Fix for negative values
    if (denom < 0)
    {
      denom *= -1;
    }

    if (leftNum < 0 && rightNum < 0)
    {
      resultNum = Math.abs(resultNum);
    }

    result = new MixedFraction(0, resultNum, denom);

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
  public static MixedFraction freshmanSum(MixedFraction left, MixedFraction right)
  {
    Fraction fractionResult;
    MixedFraction result;

    fractionResult = FractionOperations.freshmanSum(convertToProperFraction(left),
        convertToProperFraction(right));
    result = new MixedFraction(0, fractionResult.getNumerator(), fractionResult.getDenominator());

    return result;
  }

  /**
   * Calculates the result between two mixed fractions.
   * 
   * @param left - fraction a
   * @param right - fraction a
   * @return MexedFraction - the result
   */
  public static MixedFraction mediant(MixedFraction left, MixedFraction right)
  {
    return new MixedFraction(0,
        (left.getNumerator() + left.getWhole() * left.getDenominator()) + right.getNumerator()
            + right.getWhole() * right.getDenominator(),
        left.getDenominator() + right.getDenominator(), true);
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
  public static MixedFraction multiply(MixedFraction left, MixedFraction right)
  {
    MixedFraction result;

    int denom, leftNum, rightNum, resultNum;

    // Determine improper numerator
    leftNum = Math.abs(left.getWhole()) * Math.abs(left.getDenominator())
        + Math.abs(left.getNumerator());
    rightNum = Math.abs(right.getWhole()) * Math.abs(right.getDenominator())
        + Math.abs(right.getNumerator());

    // Adjust for negative values
    if (left.getWhole() < 0)
    {
      leftNum *= -1;
    }
    if (right.getWhole() < 0)
    {
      rightNum *= -1;
    }

    // Calculate resultNum
    resultNum = leftNum * rightNum;

    // Calculate denom
    denom = left.getDenominator() * right.getDenominator();

    result = new MixedFraction(0, resultNum, denom);

    return result;
  }

  /**
   * Raises a fraction to a power.
   * 
   * @param left
   *          the leftmost fraction
   * @param power
   *          the power which the left fraction is raised to.
   * @param historyField
   *          the field where history is displayed.
   * @return the result of the power operation.
   */
  public static MixedFraction power(MixedFraction left, int power, DisplayField historyField)
  {
    MixedFraction frac = new MixedFraction(1, 0, 1, false);
    MixedFraction factor = left;

    if (power >= 0)
    {
      for (int i = 0; i < power; i++)
      {
        frac = MixedFractionOperations.multiply(frac, factor);
      }
    }
    else
    {
      for (int i = 0; i > power; i--)
      {
        frac = MixedFractionOperations.divide(frac, factor);
      }
    }

    if (historyField != null)
    {
      historyField.addPowerCalculation(frac, factor, power);
    }
    return frac;
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
  public static MixedFraction subtract(MixedFraction left, MixedFraction right)
  {
    MixedFraction result;

    int denom, leftNum, rightNum, resultNum;

    // Force common denominator
    denom = left.getDenominator() * right.getDenominator();

    // Determine initial value before conversion
    leftNum = Math.abs(left.getWhole()) * left.getDenominator() + Math.abs(left.getNumerator());
    if (left.getWhole() < 0)
    {
      leftNum *= -1;
    }
    rightNum = Math.abs(right.getWhole()) * Math.abs(right.getDenominator())
        + Math.abs(right.getNumerator());
    if (right.getWhole() < 0)
    {
      rightNum *= -1;
    }

    // Adjust for common denominator
    leftNum *= right.getDenominator();
    rightNum *= left.getDenominator();

    // Calculate resultNum
    resultNum = leftNum - rightNum;

    result = new MixedFraction(0, resultNum, denom);

    return result;
  }
}
