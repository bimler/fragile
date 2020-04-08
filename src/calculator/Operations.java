package calculator;

/**
 * 
 * For adding operations to a string.
 *
 */
public enum Operations
{

  ADD("+"), SUBTRACT("-"), MULTIPLY("x"), DIVIDE("\u00f7"), MEDIANT("Med."), POWER("^");

  private String opString;

  /**
   * Set the opString to the argument provided.
   * 
   * @param opString
   *          new opString
   */
  private Operations(String opString)
  {
    this.opString = opString;
  }

  /**
   * toString method for operations.
   * 
   * @return opString String format of operation.
   */
  public String toString()
  {
    return opString;
  }
}
