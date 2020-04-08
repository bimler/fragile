package utilities;

import calculator.Fraction;
import calculator.FractionOperations;
import calculator.MixedFraction;
import calculator.MixedFractionOperations;
import calculator.Operations;

/**
 * A representation of 1 line of calculations as shown in the history.
 * 
 */
public class Calculation
{
  private MixedFraction left;
  private MixedFraction right;
  private MixedFraction result;
  private Operations operation;

  /**
   * Create a new calculation.
   * 
   * @param left
   *          fraction a
   * @param right
   *          fraction b
   * @param result
   *          a op b = result
   * @param operation
   *          the operation
   */
  public Calculation(MixedFraction left, MixedFraction right, MixedFraction result,
      Operations operation)
  {
    this.left = left;
    this.right = right;
    this.operation = operation;
    this.result = result;
  }

  /**
   * Get left operand.
   * 
   * @return left operand
   */
  public MixedFraction getLeft()
  {
    return left;
  }

  /**
   * Get right operand.
   * 
   * @return right operand
   */
  public MixedFraction getRight()
  {
    return right;
  }

  /**
   * Get result.
   * 
   * @return result
   */
  public MixedFraction getResult()
  {
    return result;
  }

  /**
   * Get the operation.
   * 
   * @return operation
   */
  public Operations getOperation()
  {
    return operation;
  }

  /**
   * Return a string representation of this calculation.
   * 
   * a b/c op d e/f = result
   * 
   * @return the string representation
   */
  public String toString()
  {
    String opString = "";

    switch (operation)
    {
      case ADD:
        opString = "+";
        break;
      case SUBTRACT:
        opString = "-";
        break;
      case MULTIPLY:
        opString = "x";
        break;
      case DIVIDE:
        opString = "%";
        break;
      case POWER:
        opString = "^";
        break;
      case MEDIANT:
        opString = "Med.";
        break;
      default:
        break;
    }

    return left.toStringHTML() + " " + opString + " " + right.toStringHTML() + " = "
        + result.toStringHTML() + "<br>";
  }

  /**
   * Shows the steps of the calculation.
   * 
   * @return the string of steps.
   */
  public String showSteps()
  {
    String steps = "";
    if (operation != null)
    {
      switch (operation)
      {
        case ADD:
          steps = showAdd();
          break;
        case SUBTRACT:
          steps = showSubtract();
          break;
        case MULTIPLY:
          steps = showMultiply();
          break;
        case DIVIDE:
          steps = showDivide();
          break;
        case POWER:
          break;
        case MEDIANT:
          break;
        default:
          break;
      }
    }

    return steps;
  }

  /**
   * Shows the subtraction steps.
   * 
   * @return string of subtraction steps.
   */
  private String showSubtract()
  {
    // initial calculation
    String steps = left.toStringHTML() + " - " + right.toStringHTML() + "<br>";

    // convert fractions to improper/proper
    Fraction lef = MixedFractionOperations.convertToProperFraction(left);
    Fraction righ = MixedFractionOperations.convertToProperFraction(right);
    steps += lef.toStringHTML() + " - " + righ.toStringHTML() + "<br>";

    // get same denominator
    Fraction[] sameDenoms = FractionOperations.getSameDenom(lef, righ);
    steps += sameDenoms[0].toStringHTML() + " - " + sameDenoms[1].toStringHTML() + "<br>";

    // show numerator sub
    int resultNumerator = sameDenoms[0].getNumerator() - sameDenoms[1].getNumerator();
    Fraction improperResult = new Fraction(resultNumerator, sameDenoms[0].getDenominator(), false);
    steps += sameDenoms[0].getNumerator() + " - " + sameDenoms[1].getNumerator() + " = "
        + improperResult.getNumerator() + "<br>";

    // back in fraction form
    steps += improperResult.toStringHTML() + "<br>";

    // back in mixed form
    MixedFraction resul = new MixedFraction(0, improperResult.getNumerator(),
        improperResult.getDenominator());
    steps += resul.toStringHTML();

    return steps;
  }

  /**
   * Shows the adding steps.
   * 
   * @return string of adding steps.
   */
  private String showAdd()
  {
    // initial calculation
    String steps = left.toStringHTML() + " + " + right.toStringHTML() + "<br>";

    // convert fractions to improper/proper
    Fraction lef = MixedFractionOperations.convertToProperFraction(left);
    Fraction righ = MixedFractionOperations.convertToProperFraction(right);
    steps += lef.toStringHTML() + " + " + righ.toStringHTML() + "<br>";

    // get same denominator
    Fraction[] sameDenoms = FractionOperations.getSameDenom(lef, righ);
    steps += sameDenoms[0].toStringHTML() + " + " + sameDenoms[1].toStringHTML() + "<br>";

    // show numerator addition
    int resultNumerator = sameDenoms[0].getNumerator() + sameDenoms[1].getNumerator();
    Fraction improperResult = new Fraction(resultNumerator, sameDenoms[0].getDenominator(), false);
    steps += sameDenoms[0].getNumerator() + " + " + sameDenoms[1].getNumerator() + " = "
        + improperResult.getNumerator() + "<br>";

    // back in fraction form
    steps += improperResult.toStringHTML() + "<br>";

    // back in mixed form
    MixedFraction resul = new MixedFraction(0, improperResult.getNumerator(),
        improperResult.getDenominator());
    steps += resul.toStringHTML();

    return steps;
  }

  /**
   * Shows the multiply steps.
   * 
   * @return string of the multiply steps.
   */
  private String showMultiply()
  {
    // initial calculation
    String steps = left.toStringHTML() + " * " + right.toStringHTML() + "<br>";

    // convert fractions to improper/proper
    Fraction lef = MixedFractionOperations.convertToProperFraction(left);
    Fraction righ = MixedFractionOperations.convertToProperFraction(right);
    steps += lef.toStringHTML() + " * " + righ.toStringHTML() + "<br>";

    // show numerator multiplication
    int resultNumerator = lef.getNumerator() * righ.getNumerator();
    steps += lef.getNumerator() + " * " + righ.getNumerator() + " = " + resultNumerator + "<br>";

    // show denominator multiplication
    int resultDenom = lef.getDenominator() * righ.getDenominator();
    steps += lef.getDenominator() + " * " + righ.getDenominator() + " = " + resultDenom + "<br>";

    // back in fraction form
    Fraction improperResult = new Fraction(resultNumerator, resultDenom, false);
    steps += improperResult.toStringHTML() + "<br>";

    // back in mixed form
    MixedFraction resul = new MixedFraction(0, improperResult.getNumerator(),
        improperResult.getDenominator());
    steps += resul.toStringHTML();

    return steps;
  }

  /**
   * Shows the division steps.
   * 
   * @return string of the division steps.
   */
  private String showDivide()
  {
    // initial calculation
    String steps = left.toStringHTML() + " " + Operations.DIVIDE.toString() + " "
        + right.toStringHTML() + "<br>";

    // convert fractions to improper/proper
    Fraction lef = MixedFractionOperations.convertToProperFraction(left);
    Fraction rightt = MixedFractionOperations.convertToProperFraction(right);
    Fraction righ = new Fraction(rightt.getDenominator(), rightt.getNumerator(), false);
    steps += lef.toStringHTML() + " " + Operations.DIVIDE.toString() + " " + rightt.toStringHTML()
        + "<br>";

    // show as multiplication
    steps += lef.toStringHTML() + " * " + righ.toStringHTML() + "<br>";

    // show numerator multiplication
    int resultNumerator = lef.getNumerator() * righ.getNumerator();
    steps += lef.getNumerator() + " * " + righ.getNumerator() + " = " + resultNumerator + "<br>";

    // show denominator multiplication
    int resultDenom = lef.getDenominator() * righ.getDenominator();
    steps += lef.getDenominator() + " * " + righ.getDenominator() + " = " + resultDenom + "<br>";

    // back in fraction form
    Fraction improperResult = new Fraction(resultNumerator, resultDenom, false);
    steps += improperResult.toStringHTML() + "<br>";

    // back in mixed form
    MixedFraction resul = new MixedFraction(0, improperResult.getNumerator(),
        improperResult.getDenominator());
    steps += resul.toStringHTML();

    return steps;
  }
}
