package calculator;

/**
 * A number that can condense to a single double value.
 * 
 */
public abstract class Number implements Comparable<Number>
{
  /**
   * Abstract method for returning a value of a Number.
   * 
   * @return double value of number.
   */
  public abstract double getValue();
}
