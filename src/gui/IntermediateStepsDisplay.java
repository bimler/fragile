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
import utilities.Calculation;

/**
 * Displays intermediate steps.
 *
 */
public class IntermediateStepsDisplay extends JFrame implements Runnable, Scrollable
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
  private JFrame parentFrame;
  private boolean hidden;
  private boolean moving;

  private String text;

  /**
   * Constructor.
   * 
   * @param parentFrame
   *          the frame which the display belongs.
   */
  public IntermediateStepsDisplay(JFrame parentFrame)
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

    setTitle("Intermediate Steps");
    getContentPane().add(scrollPane, BorderLayout.CENTER);
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
   * Creates an empty display.
   */
  private void newDisplay()
  {
    display.setText("");
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
  public synchronized void run()
  {
    // TODO Auto-generated method stub
    // Determine whether to go out or in
    if (hidden)
    {
      hidden = false; // flip switch
      setVisible(true); // make visible
      while (getLocation().getX() > minX) // Until we reach maxX
      {
        int newX;
        // Increment current X by ANIMATION_SPEED
        newX = (int) (getLocation().getX() - ((double) getWidth() * ANIMATION_SPEED));
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
      while (getLocation().getX() < maxX) // Until we reach minX
      {
        int newX;
        // Decrement current x by ANIMATION_SPEED
        newX = (int) (getLocation().getX() + ((double) getWidth() * ANIMATION_SPEED));
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
   * Moves the DisplayField with the parent frame.
   */
  public void move()
  {
    setLocationRelativeTo(parentFrame); // Recenter
    Point location = getLocation(); // get Point object
    maxX = location.getX(); // Calculate
                            // maxX
    minX = location.x - MARGIN_ADJUSTMENT - parentFrame.getWidth() / 2 - getWidth() / 2; // Record
                                                                                         // minX

    if (hidden) // if already out
    {
      setLocation((int) maxX, location.y); // move to out position
    }
    else // otherwise
    {
      setLocation((int) minX, location.y); // move to in position
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
   * Returns the text from the TextPane.
   * 
   * @return String of the text.
   */
  public String getText()
  {
    return display.getText();
  }

  @Override
  public Dimension getPreferredScrollableViewportSize()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction)
  {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction)
  {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public boolean getScrollableTracksViewportWidth()
  {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean getScrollableTracksViewportHeight()
  {
    // TODO Auto-generated method stub
    return false;
  }

  /**
   * Returns the steps of the most recent calculation.
   * 
   * @param recentCalc
   *          the most recent calculation.
   */
  public void printSteps(Calculation recentCalc)
  {
    if (recentCalc != null)
    {
      text += "--------------------------<br>";

      text += recentCalc.showSteps();

      text += "<br>--------------------------<br>";
      if (!text.equals(""))
      {
        display.setText("<html>" + text + "</html>");
      }
    }
  }

  /**
   * sets the style of the display.
   * 
   * @param style
   *          style which will be set to.
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
    }

    setText();
  }

  /**
   * Updates the text for the display.
   */
  private void setText()
  {
    display.setText("<html>" + text + "</html>");
  }

}
