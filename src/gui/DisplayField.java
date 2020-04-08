package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyListener;
import java.awt.print.PrinterException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.Scrollable;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import calculator.MixedFraction;
import calculator.Operations;

/**
 * 
 * DisplayField for outputting text.
 *
 */
public class DisplayField extends JFrame implements Runnable, Scrollable
{
  private static final long serialVersionUID = 1L;
  private static final int MARGIN_ADJUSTMENT = 7;
  private static final int ADORNMENT_SIZE = 65;
  private static final double ANIMATION_SPEED = .03;
  private static final int WIDTH = 300;
  private static final int MIN_HEIGHT = 300;

  private double maxX, minX;
  private Font font;
  private JTextPane display;
  private FragileWindow parentFrame;
  private boolean hidden;
  private boolean moving;

  private String text;

  /**
   * Field to output the history of calculations.
   * 
   * @param parentFrame
   *          the frame which this one belongs to.
   */
  public DisplayField(FragileWindow parentFrame)
  {
    super();

    text = "";

    hidden = true;
    moving = false;
    this.parentFrame = parentFrame;
    setUndecorated(true);
    font = new Font("Helvetica", Font.PLAIN, 20);
    JPanel panel = new JPanel();
    panel.setBackground(Color.WHITE);
    panel.setLayout(new BorderLayout());
    display = new JTextPane();
    display.addKeyListener((KeyListener) parentFrame);
    display.setText("");
    display.setEditable(false);
    display.setFont(font);
    panel.add(display, BorderLayout.CENTER);

    JScrollPane scrollPane = new JScrollPane(display);
    scrollPane.addKeyListener((KeyListener) parentFrame);

    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    resize();

    move();

    setTitle("Calculation History");
    getContentPane().add(scrollPane, BorderLayout.CENTER);
  }

  /**
   * Adds a calculation to the field.
   * 
   * @param left
   *          the left fraction.
   * @param right
   *          the right fraction.
   * @param result
   *          the result of the calculation.
   * @param op
   *          the operation.
   */
  public void addCalculation(MixedFraction left, MixedFraction right, MixedFraction result,
      Operations op)
  {
    String calculation;

    MixedFraction zero = new MixedFraction(0, 0, 1);

    if (result == null || op == null)
    {
      MixedFraction operand = null;

      if (zero.compareTo(left) == 0)
      {
        operand = right;
      }
      else if (zero.compareTo(right) == 0)
      {
        operand = left;
      }

      if (operand != null)
      {
        calculation = String.format("%s<br>", operand.toStringHTML());
      }
      else
      {
        calculation = "Check input\n";
      }

    }
    else
    {
      String opString = "";

      switch (op)
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
          opString = "\u00f7";
          break;
        default:
          break;
      }

      calculation = left.toStringHTML() + " " + opString + " " + right.toStringHTML() + " = "
          + result.toStringHTML() + "<br>";
    }

    text += calculation;

    display.setText("<html>" + text + "</html>");

  }

  /**
   * Adds a calculation for the power operation.
   * 
   * @param result
   *          the result of the operation.
   * @param factor
   *          the fraction.
   * @param power
   *          the power which the fraction is raised to.
   */
  public void addPowerCalculation(MixedFraction result, MixedFraction factor, int power)
  {
    text += String.format("(%s) ^ %d = %s<br>", factor.toStringHTML(), power,
        result.toStringHTML());

    display.setText("<html>" + text + "</html>");
  }

  /**
   * Starts the moving animation.
   */
  public void beginAnimation()
  {
    if (!moving)
    {
      moving = true;
      Thread t = new Thread(this);
      t.start();
    }
  }

  @Override
  public Dimension getPreferredScrollableViewportSize()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction)
  {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public boolean getScrollableTracksViewportHeight()
  {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean getScrollableTracksViewportWidth()
  {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction)
  {
    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * Returns the text from the TextPane.
   * 
   * @return String of the text.
   */
  public String getText()
  {
    return display.getText();
  }

  /**
   * Checks if the frame is moving.
   * 
   * @return moving boolean value if the field is moving.
   */
  public boolean isMoving()
  {
    return moving;
  }

  /**
   * Moves the DisplayField with the parent frame.
   */
  public void move()
  {
    setLocationRelativeTo(parentFrame); // Recenter
    Point location = getLocation(); // get Point object
    maxX = location.x - MARGIN_ADJUSTMENT + parentFrame.getWidth() / 2 
        + getWidth() / 2; // Calculate
                                                                                         // maxX
    minX = location.getX(); // Record minX

    if (!hidden) // if already out
    {
      setLocation((int) maxX, location.y); // move to out position
    }
    else // otherwise
    {
      setLocation((int) minX, location.y); // move to in position
    }
  }

  /**
   * Creates an empty display.
   */
  public void newDisplay()
  {
    display.setText("");
    text = "";
    setStyle(parentFrame.getStyle());
  }

  /**
   * Prints the contents of the JTextPane.
   */
  public void printDisplayContents()
  {
    try
    {
      display.print();
    }
    catch (PrinterException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * Resiszes the displayfield with the parent frame.
   */
  public void resize()
  {
    move(); // adjust for width change
    setSize(WIDTH, Math.max(MIN_HEIGHT, parentFrame.getHeight()) - ADORNMENT_SIZE); // reset
                                                                                    // dimensions
    if (isVisible())
    {
      setVisible(false);
      setVisible(true);
    }
  }

  @Override
  public synchronized void run()
  {
    // TODO Auto-generated method stub
    // Determine whether to go out or in
    if (hidden)
    {
      hidden = false; // flip switch
      setVisible(true); // make visible
      while (getLocation().getX() < maxX) // Until we reach maxX
      {
        int newX;
        // Increment current X by ANIMATION_SPEED
        newX = (int) (getLocation().getX() + ((double) getWidth() * ANIMATION_SPEED));
        setLocation(newX, (int) getLocation().getY());
        try
        {
          // pause thread for 1ms
          this.wait(1L);
        }
        catch (InterruptedException e)
        {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

      }
    }
    else
    {
      hidden = true; // flip switch
      while (getLocation().getX() > minX) // Until we reach minX
      {
        int newX;
        // Decrement current x by ANIMATION_SPEED
        newX = (int) (getLocation().getX() - ((double) getWidth() * ANIMATION_SPEED));
        setLocation(newX, (int) getLocation().getY());
        try
        {
          // pause thread for 1ms
          this.wait(1L);
        }
        catch (InterruptedException e)
        {
          e.printStackTrace();
        }
      }
      setVisible(false); // make invisible
    }

    moving = false;
  }

  /**
   * Sets the style of the displayfield.
   * 
   * @param style
   *          the style which the field will be set to.
   */
  public void setStyle(int style)
  {
    HTMLEditorKit htmlEditorKit;
    StyleSheet styleSheet;
    HTMLDocument htmlDocument;

    switch (style)
    {
      case MixedFraction.BAR_STYLE:
      case MixedFraction.SOLIDUS_STYLE:
        htmlEditorKit = new HTMLEditorKit();

        styleSheet = new StyleSheet();

        styleSheet.addRule("html {font-size: 12px; color: black}");
        styleSheet.addRule(".top {vertical-align: super}");
        styleSheet.addRule(".bottom {vertical-align: sub}");
        styleSheet.addRule(".whole, .top {color: black; display: inline}");

        htmlEditorKit.setStyleSheet(styleSheet);

        htmlDocument = (HTMLDocument) htmlEditorKit.createDefaultDocument();

        display.setEditorKit(htmlEditorKit);
        display.setDocument(htmlDocument);
        break;
      case MixedFraction.SLASH_STYLE:
        htmlEditorKit = new HTMLEditorKit();

        styleSheet = new StyleSheet();

        styleSheet.addRule("html {font-size: 12px; color: black}");

        htmlEditorKit.setStyleSheet(styleSheet);

        htmlDocument = (HTMLDocument) htmlEditorKit.createDefaultDocument();

        display.setEditorKit(htmlEditorKit);
        display.setDocument(htmlDocument);
        break;
      default:
        break;
    }

    if (!text.equals(""))
    {
      display.setText("<html>" + text + "</html>");
    }
  }

}
