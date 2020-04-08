package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import calculator.*;

/**
 * InputField object for returning data input from keyboard and JButtons.
 * 
 *
 */
public class InputField extends JTextPane implements KeyListener
{

  private static final long serialVersionUID = 1L;
  private MixedFraction fraction;
  private boolean displayWhole;
  private Operations operation;
  private int style;

  /**
   * Constructor, for empty fraction.
   * 
   * @param displayWhole
   *          boolean whether to display the whole part of the fraction.
   * @param style
   *          the set style.
   */
  public InputField(boolean displayWhole, int style)
  {
    this(null, displayWhole, style);
  }

  /**
   * Constructor.
   * 
   * @param fraction
   *          the fraction.
   * @param displayWhole
   *          boolean whether to display the whole part of the fraction.
   * @param style
   *          the set style.
   */
  public InputField(MixedFraction fraction, boolean displayWhole, int style)
  {
    super();

    this.style = style;

    HTMLEditorKit htmlEditorKit = new HTMLEditorKit();

    StyleSheet styleSheet = new StyleSheet();

    styleSheet.addRule("html {font-size: 12px; color: black}");
    styleSheet.addRule(".top {border-bottom: solid black 1px; display: inline-block; float: left}");
    styleSheet.addRule(".bottom {display: inline-block; clear: left; float: left}");
    styleSheet.addRule(".whole, .top {color: black; display: inline}");

    htmlEditorKit.setStyleSheet(styleSheet);

    HTMLDocument htmlDocument = (HTMLDocument) htmlEditorKit.createDefaultDocument();

    setEditorKit(htmlEditorKit);
    setDocument(htmlDocument);

    this.displayWhole = displayWhole;
    reset(fraction);
    setEditable(false);
  }

  /**
   * Adds the fraction to the Field.
   * 
   * @param frac
   *          the fraction being added to the field.
   */
  public void addFrac(MixedFraction frac)
  {
    this.fraction = frac;
    setText();
  }

  /**
   * Add text to input field.
   * 
   * @param text
   *          String to be added to input field
   */
  public void addText(String text)
  {
    this.setText(this.getText() + text);
  }

  /**
   * To add value to the fraction.
   * 
   * @param value
   *          the value to be added.
   * @param focus
   *          the part in focus.
   * @return whether the sign has been changed.
   */
  public boolean append(int value, int focus)
  {
    boolean changeSign = false;

    int w, n, d;

    w = fraction.getWhole();
    n = fraction.getNumerator();
    d = fraction.getDenominator();

    switch (focus)
    {
      case 0:
      case 3:
        w = w * 10 + value;
        if (value != 0 && n < 0)
        {
          w *= -1;
          n *= -1;
          changeSign = true;
        }
        break;
      case 1:
      case 4:
        n = n * 10 + value;
        break;
      case 2:
      case 5:
        d = d * 10 + value;
        break;
      default:
    }

    fraction = new MixedFraction(w, n, d, false);

    setText();
    return changeSign;
  }

  /**
   * Backspace the part in focus.
   * 
   * @param focus
   *          the current part of the fraction in focus.
   */
  public void backspace(int focus)
  {
    int w, n, d;

    w = fraction.getWhole();
    n = fraction.getNumerator();
    d = fraction.getDenominator();

    switch (focus)
    {
      case 0:
      case 3:
        w = w / 10;
        break;
      case 1:
      case 4:
        n = n / 10;
        break;
      case 2:
      case 5:
        d = d / 10;
        break;
      default:
    }

    fraction = new MixedFraction(w, n, d, false);

    setText();
  }

  /**
   * Calculates the result of the fractions and the operation.
   * 
   * @param left
   *          the left fraction.
   * @param right
   *          the right fraction.
   * @param historyField
   * 		  the history field.
   * @return the result.
   */
  public MixedFraction calculateResult(MixedFraction left, MixedFraction right,
      DisplayField historyField)
  {
    MixedFraction frac = null;
	
    if (operation == null)
    {
      return new MixedFraction(left.getWhole(), left.getNumerator(), left.getDenominator());
    }

    switch (operation)
    {
      case ADD:
    	  frac = MixedFractionOperations.add(left, right);
    	  break;
      case SUBTRACT:
    	  frac = MixedFractionOperations.subtract(left, right);
    	  break;
      case MULTIPLY:
    	  frac = MixedFractionOperations.multiply(left, right);
    	  break;
      case DIVIDE:
    	  frac = MixedFractionOperations.divide(left, right);
    	  break;
      case MEDIANT:
    	  frac = MixedFractionOperations.mediant(left, right);
    	  break;
      case POWER:
    	  frac = MixedFractionOperations.power(left, right.getWhole(), historyField);
    	  break;
      default:
    }
    
    return frac;
  }

  /**
   * Gets the fraction.
   * 
   * @return the MixedFraction object.
   */
  public MixedFraction getFraction()
  {
    return fraction;
  }

  /**
   * Returns a list of mixed fraction objects from the input field.
   * 
   * @return list of the mixed fraction objects parsed from input
   */
  public List<MixedFraction> getFractionList()
  {
    ArrayList<MixedFraction> retList = new ArrayList<MixedFraction>();
    String textString = this.getText();
    String[] tempList = textString.split("\\s");
    int whole = 0;
    int num = 0;
    int den = 1;
    boolean foundNum = false;
    int i;
    for ( i = 0; i < tempList.length; i++)
    {
      // Reset whole, num, and den
      whole = 0;
      num = 0;
      den = 1;

      if (!tempList[i].equals(" "))
      {
        try
        {
          if (!tempList[i].contains("/"))
          {
            whole = Integer.parseInt(tempList[i]);
            foundNum = true;
          }
          else if (!tempList[i].equals("/"))
          {
            i--; // Fixes issue where whole component is not given
            foundNum = true;
          }
        }
        catch (NumberFormatException e)
        {
          // System.out.println("Input is not an integer");
        }
        if (foundNum)
        {
          if (i + 1 < tempList.length)
          {
            if (tempList[i + 1].contains("/") && !tempList[i + 1].equals("/"))
            {
              String[] fracs = tempList[++i].split("/");

              num = Integer.parseInt(fracs[0]);
              den = Integer.parseInt(fracs[1]);
              if (den < 1)
              {
                den = 1;
              }
            }
          }
          else
          {
            num = 0;
            den = 1;
          }
          try
          {
            retList.add(new MixedFraction(whole, num, den));
          }
          catch (IllegalArgumentException e)
          {
            retList.add(new MixedFraction(0, 0, 1));
          }
        }
        foundNum = false;
      }
    }
    // System.out.println(retList);
    return retList;

  }

  /**
   * Gets the current operation.
   * 
   * @return the operation string.
   */
  public Operations getOperation()
  {
    return operation;
  }

  /**
   * Returns a list of operations from the input field.
   * 
   * @return String list of operations.
   */
  public List<String> getOperationList()
  {
    ArrayList<String> retList = new ArrayList<String>();
    String textString = this.getText();
    String[] tempList = textString.split("");
    for (int i = 0; i < tempList.length; i++)
    {
      if (tempList[i].equals("+") || tempList[i].equals("X"))
      {
        retList.add(tempList[i]);
      }
      else if (tempList[i].equals("/"))
      {
        if (tempList[i - 1].equals(" ") && tempList[i + 1].equals(" "))
        {
          retList.add(tempList[i]);
        }
      }
      else if (tempList[i].equals("-"))
      {
        if (tempList[i + 1].equals(" "))
        {
          retList.add(tempList[i]);
        }
      }
    }
    // System.out.println(retList.toString());
    return retList;
  }

  /**
   * Highlights the fraction in focus.
   * 
   * @param focus
   *          the fraction in focus.
   */
  public void highlight(int focus)
  {
    HTMLEditorKit htmlEditorKit = new HTMLEditorKit();
    StyleSheet styleSheet = new StyleSheet();
    HTMLDocument htmlDocument;

    switch (focus)
    {
      case 0:
      case 3:
        styleSheet.addRule("html {font-size: 12px; background-color: #FFFF00}");
        styleSheet
            .addRule(".top {border-bottom: solid black 1px; display: inline-block; float: left}");
        styleSheet.addRule(".bottom {display: inline-block; clear: left; float: left}");
        styleSheet.addRule(".whole, .top {color: black; display: inline}");

        htmlEditorKit.setStyleSheet(styleSheet);

        htmlDocument = (HTMLDocument) htmlEditorKit.createDefaultDocument();

        setEditorKit(htmlEditorKit);
        setDocument(htmlDocument);
        break;
      case 1:
      case 4:
        styleSheet.addRule("html {font-size: 12px}");
        styleSheet.addRule(
            ".top {background-color: #FFFF00; border-bottom: solid black 1px; display:" 
            		+ "inline-block; float: left}");
        styleSheet.addRule(".bottom {display: inline-block; clear: left; float: left}");
        styleSheet.addRule(".whole, .top {color: black; display: inline}");

        htmlEditorKit.setStyleSheet(styleSheet);

        htmlDocument = (HTMLDocument) htmlEditorKit.createDefaultDocument();

        setEditorKit(htmlEditorKit);
        setDocument(htmlDocument);
        break;
      case 2:
      case 5:
        styleSheet.addRule("html {font-size: 12px}");
        styleSheet
            .addRule(".top {border-bottom: solid black 1px; display: inline-block; float: left}");
        styleSheet.addRule(
            ".bottom {background-color: #FFFF00; display: inline-block; clear: left; float: left}");
        styleSheet.addRule(".whole, .top {color: black; display: inline}");

        htmlEditorKit.setStyleSheet(styleSheet);

        htmlDocument = (HTMLDocument) htmlEditorKit.createDefaultDocument();

        setEditorKit(htmlEditorKit);
        setDocument(htmlDocument);
        break;
      default:
    }

    setText();
  }

  @Override
  public void keyPressed(KeyEvent e)
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void keyReleased(KeyEvent e)
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void keyTyped(KeyEvent e)
  {
    // TODO Auto-generated method stub

  }

  /**
   * Resets fractions.
   */
  public void reset()
  {
    reset(null);
  }

  /**
   * Sets the text of the fraction.
   * 
   * @param result
   *          the new text.
   */
  public void reset(MixedFraction result)
  {
    fraction = result;
    setText();
  }

  /**
   * Sets the operation string.
   * 
   * @param op
   *          the new operation.
   */
  public void setOp(Operations op)
  {
    operation = op;

    setText();
  }

  /**
   * Sets the style of the field.
   * 
   * @param style - the style.
   */
  public void setStyle(int style)
  {
    this.style = style;
  }

  /**
   * Sets the text of the fraction.
   */
  public void setText()
  {
    if (fraction != null) // For displaying fraction
    {
      if (displayWhole) // For displaying whole part
      {
        setText(".");
        setText("\u00A0");
        setText("<span class=\"whole\">" + fraction.getWhole() + "</span>");
      }
      else // For displaying fraction part
      {
        switch (style)
        {
          case MixedFraction.BAR_STYLE:
            setText("<div class=\"top\">" + fraction.getNumerator() + "</div><div class=\"bottom\">"
                + fraction.getDenominator() + "</div>");
            break;
          case MixedFraction.SOLIDUS_STYLE:
            setText("<sup class=\"top\">" + fraction.getNumerator()
                + "</sup>/<sub class=\"bottom\">" + fraction.getDenominator() + "</sub>");
            break;
          case MixedFraction.SLASH_STYLE:
            setText("<span class=\"top\">" + fraction.getNumerator()
                + "</span>/<span class=\"bottom\">" + fraction.getDenominator() + "</span>");

            break;
          default:
        }
      }
    }
    else if (operation != null)
    {
      setText(".");
      setText("\u00A0");
      setText("<span class=\"op\">" + operation.toString() + "</span>");
    }
    else
    {
      setText("\u00A0");
    }
  }

  /**
   * Removes highlighting.
   */
  public void unhighlight()
  {
    HTMLEditorKit htmlEditorKit = new HTMLEditorKit();

    StyleSheet styleSheet = new StyleSheet();

    styleSheet.addRule("html {font-size: 12px;}");
    styleSheet.addRule(".top {border-bottom: solid black 1px; display: inline-block; float: left}");
    styleSheet.addRule(".bottom {display: inline-block; clear: left; float: left}");

    htmlEditorKit.setStyleSheet(styleSheet);

    HTMLDocument htmlDocument = (HTMLDocument) htmlEditorKit.createDefaultDocument();

    setEditorKit(htmlEditorKit);
    setDocument(htmlDocument);

    setText();
  }
}
