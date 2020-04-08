package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import calculator.*;
import utilities.Calculation;
import utilities.Recording;

/**
 * FragileWindow.
 *
 */
public class FragileWindow extends JFrame
    implements ActionListener, KeyListener, FocusListener, ComponentListener
{
  public static final int MIXED = 0, IMPROPER = 1, PROPER = 2;

  private static final long serialVersionUID = 1L;
  private static final int BAR_STYLE = 0;
  private static final int SLASH_STYLE = 2;
  private static final int SOLIDUS_STYLE = 1;

  private int style;
  private int mode;

  private Color backgroundColor;
  private Color mainColor;
  private Color secondColor;
  private Color thirdColor;
  private Color inputColor;
  private Color buttonColor;
  private Color logoBack;
  private ImageIcon logo;
  private Font font;
  // private InputField input;
  private InputField leftWhole, leftFrac, opPane, rightWhole, rightFrac;
  private MixedFraction left, right;
  private Calculation mostRecentCalc;
  private Recording recording;
  private int focus;
  private DisplayField historyField;
  private IntermediateStepsDisplay stepsField;
  private Operations operation;
  private boolean displayed;
  private DisplayPieChart pie;
  private boolean pieActive;
  private JButton histButton;
  private JPanel p1;
  private JPanel p0;
  private String logoString;

  /**
   * Constructor.
   * 
   * @param mainColor
   *          a color for themeing.
   * @param secondColor
   *          a color for themeing.
   * @param thirdColor
   *          a color for themeing.
   * @param backgroundColor
   *          a color for themeing.
   * @param inputColor
   *          a color for themeing.
   * @param buttonColor
   *          a color for themeing.
   * @param logoBack
   *          the image icon of the logo, for themeing.
   * @param str
   *          location of image.
   */
  public FragileWindow(Color mainColor, Color secondColor, Color thirdColor, Color backgroundColor,
      Color inputColor, Color buttonColor, Color logoBack, String str)
  {
    super();

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    setAlwaysOnTop(true);
    setBounds(300, 50, 400, 400);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mode = MIXED;
    font = new Font("Helvetica", Font.PLAIN, 20);
    p0 = new JPanel();

    this.mainColor = mainColor;
    this.secondColor = secondColor;
    this.thirdColor = thirdColor;
    this.backgroundColor = backgroundColor;
    this.inputColor = inputColor;
    this.buttonColor = buttonColor;
    this.logoBack = logoBack;
    this.logoString = str;

    addComponentListener(this);
    addKeyListener(this);
    getContentPane().addKeyListener(this);
    displayed = false;
    style = SOLIDUS_STYLE;
    operation = null;

    left = new MixedFraction(0, 0, 1, false);
    right = new MixedFraction(0, 0, 1, false);

    leftWhole = new InputField(left, true, style);
    leftFrac = new InputField(left, false, style);
    opPane = new InputField(false, style);
    rightWhole = new InputField(true, style);
    rightFrac = new InputField(false, style);

    leftWhole.setBackground(inputColor);
    leftFrac.setBackground(inputColor);
    opPane.setBackground(inputColor);
    rightWhole.setBackground(inputColor);
    rightFrac.setBackground(inputColor);

    leftWhole.addKeyListener(this);
    leftFrac.addKeyListener(this);
    opPane.addKeyListener(this);
    rightWhole.addKeyListener(this);
    rightFrac.addKeyListener(this);

    setupLayout();

    historyField = new DisplayField(this);
    historyField.addKeyListener(this);
    stepsField = new IntermediateStepsDisplay(this);
    stepsField.addKeyListener(this);

    leftWhole.highlight(focus);
    recording = new Recording();

    pieActive = false;

    historyField.setStyle(style);
    stepsField.setStyle(style);

    setLocation(screenSize.width / 4, screenSize.height / 4);
  }

  /**
   * actionPerformed override method.
   * 
   * @param e
   *          action event e
   */
  public void actionPerformed(ActionEvent e)
  {
    String cmd = e.getActionCommand();

    if (displayed)
    {
      p1.removeAll();
    }

    switch (cmd)
    {
      case "Exit":
        System.exit(0);
        break;
      case "Open in Browser":
        File htmlFile = new File("resources/help.html");
        try
        {
          Desktop.getDesktop().browse(htmlFile.toURI());
          this.setAlwaysOnTop(false);
        }
        catch (IOException e2)
        {
          e2.printStackTrace();
        }
        break;
      case "Dark Theme":
        setVisible(false);
        FragileWindow window = new FragileWindow(new Color(255, 255, 255), new Color(0, 191, 255),
            new Color(255, 215, 0), new Color(75, 75, 75), new Color(128, 128, 128),
            new Color(128, 128, 128), new Color(128, 128, 128), this.logoString);
        window.setVisible(true);
        window.getContentPane().setBackground(new Color(75, 75, 75));
        break;
      case "Fragile Theme":
        setVisible(false);
        window = new FragileWindow(new Color(220, 20, 60), new Color(220, 20, 60),
            new Color(220, 20, 60), new Color(169, 169, 169), new Color(255, 255, 255),
            new Color(255, 255, 255), new Color(255, 255, 255), this.logoString);
        window.setVisible(true);
        window.getContentPane().setBackground(new Color(169, 169, 169));
        break;
      case "Light Theme":
        setVisible(false);
        window = new FragileWindow(new Color(0, 0, 0), new Color(0, 191, 255),
            new Color(255, 215, 0), null, new Color(255, 255, 255), new Color(255, 255, 255),
            new Color(255, 255, 255), this.logoString);
        window.setVisible(true);
        break;
      case "R":
        historyField.newDisplay();
        clearAll();
        break;
      case "C":
        clearCurrent();
        break;
      case "+":
        setOp(Operations.ADD);
        break;
      case "\u00f7":
        setOp(Operations.DIVIDE);
        break;
      case "-":
        setOp(Operations.SUBTRACT);
        break;
      case "x":
        setOp(Operations.MULTIPLY);
        break;
      case "Med.":
        if (mode != MIXED)
        {
          setOp(Operations.MEDIANT);
        }
        break;
      case "\u232B":
        backspaceCurrent();
        break;
      case "=":
        MixedFraction lef = getLeftFraction();
        int num = lef.getNumerator();
        int den = lef.getDenominator();

        if (pieActive)
        {
          pie.close();
          pie = new DisplayPieChart(num, den);
        }
        calculate();
        break;
      case "+/-":
        changeSign();
        break;
      case "1":
      case "2":
      case "3":
      case "4":
      case "5":
      case "6":
      case "7":
      case "8":
      case "9":
      case "0":
        appendCurrent(Integer.parseInt(cmd));
        break;
      case ">":
        cycleFocus();
        break;
      case "Inv":
        invertCurrent();
        break;
      case "Mixed":
        mode = MIXED;
        setMode();
        break;
      case "Proper":
        mode = PROPER;
        setMode();
        break;
      case "Improper":
        mode = IMPROPER;
        setMode();
        break;
      case "^":
        setOp(Operations.POWER);
        break;
      case "<":
        decrementFocus();
        break;
      case "Hist>":
        this.setAlwaysOnTop(true);
        triggerAnimation();
        break;
      case "Bar Style":
        style = BAR_STYLE;
        leftFrac.setStyle(style);
        rightFrac.setStyle(style);
        historyField.setStyle(style);
        stepsField.setStyle(style);
        updateAll();
        break;
      case "Slash Style":
        style = SLASH_STYLE;
        leftFrac.setStyle(style);
        rightFrac.setStyle(style);
        historyField.setStyle(style);
        stepsField.setStyle(style);
        updateAll();
        break;
      case "Solidus Style":
        style = SOLIDUS_STYLE;
        leftFrac.setStyle(style);
        rightFrac.setStyle(style);
        historyField.setStyle(style);
        stepsField.setStyle(style);
        updateAll();
        break;
      case "About":
        cycleFocus();
        AboutDialog aboutDialog = new AboutDialog(this);
        aboutDialog.setVisible(true);
        decrementFocus();
        break;
      case "Show Steps":
        this.setAlwaysOnTop(true);
        triggerStepsAnimation();
        updateAll();
        break;
      case "Print":
        historyField.printDisplayContents();
        break;
      case "Record":
        try
        {
          recording.record();
        }
        catch (IOException e1)
        {
          e1.printStackTrace();
        }
        break;
      case "Playback":
        try
        {
          recording.playback();
        }
        catch (IOException e1)
        {
          e1.printStackTrace();
        }
        break;
      case "\u03b3":
        if (mode == PROPER)
        {
          FareySequence farey = new FareySequence(getLeftFraction().getDenominator());
          Fraction frac = farey.getNext(new Fraction(getLeftFraction().getNumerator(),
              getLeftFraction().getDenominator(), true));
          leftFrac.addFrac(new MixedFraction(0, frac.getNumerator(), frac.getDenominator(), true));

          updateAll();
        }
        break;
      case "Pie Chart":
        MixedFraction frac = getLeftFraction();
        int nume = frac.getNumerator();
        int deno = frac.getDenominator();
        pie = new DisplayPieChart(nume, deno);
        pieActive = true;
        break;
      default:
        System.out.println("Cannot read case");
        break;
    }

    updateAll();
    leftFrac.requestFocusInWindow();
  }

  @Override
  public void keyTyped(KeyEvent e)
  {
    switch (e.getKeyChar())
    {
      case '0':
      case '1':
      case '2':
      case '3':
      case '4':
      case '5':
      case '6':
      case '7':
      case '8':
      case '9':
        appendCurrent((int) e.getKeyChar() - 48);
        break;
      case '+':
        setOp(Operations.ADD);
        break;
      case '-':
        setOp(Operations.SUBTRACT);
        break;
      case 'x':
      case 'X':
        setOp(Operations.MULTIPLY);
        break;
      case '%':
      case '/':
        setOp(Operations.DIVIDE);
        break;
      case '.':
        cycleFocus();
        break;
      case ',':
        decrementFocus();
        break;
      case 'h':
      case 'H':
        triggerAnimation();
        break;
      case 'c':
      case 'C':
        clearCurrent();
        break;
      case 'r':
      case 'R':
        clearAll();
        historyField.newDisplay();
        break;
      case 'i':
      case 'I':
        invertCurrent();
        break;
      case '^':
        setOp(Operations.POWER);
        break;
      default:
    }
    updateAll();
    leftFrac.requestFocusInWindow();
  }

  @Override
  public void keyReleased(KeyEvent e)
  {
    switch (e.getKeyCode())
    {
      case KeyEvent.VK_ENTER:
        calculate();
        break;
      case KeyEvent.VK_BACK_SPACE:
        backspaceCurrent();
        break;
      default:
    }
    updateAll();
    leftFrac.requestFocusInWindow();
  }

  @Override
  public void keyPressed(KeyEvent e)
  {
    // Nothing
  }

  /**
   * Sets up the layout.
   */
  private void setupLayout()
  {
    setSize(600, 450);

    setTitle("Fragile");

    getContentPane().removeAll();

    p0 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    p0.setBackground(backgroundColor);
    ImageIcon log = new ImageIcon("resources/" + logoString);
    JLabel logoLabel = new JLabel(log, JLabel.LEFT);
    p0.setBackground(logoBack);
    p0.add(logoLabel);

    p1 = display();
    JPanel p2 = buttonRow1();
    JPanel p3 = buttonRow2();
    JPanel p4 = buttonRow3();
    JPanel p5 = buttonRow4();
    JPanel p6 = buttonRow5();

    p2.addKeyListener(this);
    p3.addKeyListener(this);
    p4.addKeyListener(this);
    p5.addKeyListener(this);
    p6.addKeyListener(this);

    this.getContentPane().setBackground(this.getBackgroundColor());

    p1.setBackground(inputColor);

    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    add(Box.createVerticalStrut(3));
    add(p0);
    add(Box.createVerticalStrut(3));
    add(p1);
    add(Box.createVerticalStrut(10));
    add(p2);
    add(Box.createVerticalStrut(10));
    add(p3);
    add(Box.createVerticalStrut(10));
    add(p4);
    add(Box.createVerticalStrut(10));
    add(p5);
    add(Box.createVerticalStrut(10));
    add(p6);
    add(Box.createVerticalStrut(10));

    setupMenu();

    rightFrac.setVisible(operation != Operations.POWER);
  }

  /**
   * Sets up the input field.
   * 
   * @return the panel containing the input field.
   */
  private JPanel display()
  {
    JPanel panel1 = new JPanel();

    panel1.setLayout(new FlowLayout());

    panel1.add(leftWhole);
    panel1.add(leftFrac);
    panel1.add(opPane);
    panel1.add(rightWhole);
    panel1.add(rightFrac);

    panel1.addKeyListener(this);

    return panel1;
  }

  /**
   * Sets up the first row of buttons.
   * 
   * @return the panel containing the first row of buttons.
   */
  private JPanel buttonRow1()
  {
    JPanel p2 = new JPanel(new GridLayout(1, 5, 10, 0));

    JButton button = new JButton("+/-");
    button.setFont(font);
    button.addActionListener(this);
    button.addKeyListener(this);
    button.setForeground(thirdColor);
    button.setBackground(buttonColor);
    button.setOpaque(true);
    button.setBorderPainted(false);
    p2.add(button);

    button = new JButton("C");
    button.setFont(font);
    button.addActionListener(this);
    button.addKeyListener(this);
    button.setForeground(thirdColor);
    button.setBackground(buttonColor);
    button.setOpaque(true);
    button.setBorderPainted(false);
    p2.add(button);

    button = new JButton("\u232B");
    button.setFont(font);
    button.addActionListener(this);
    button.addKeyListener(this);
    button.setForeground(thirdColor);
    button.setBackground(buttonColor);
    button.setOpaque(true);
    button.setBorderPainted(false);
    p2.add(button);

    button = new JButton("+");
    button.setFont(font);
    button.addActionListener(this);
    button.addKeyListener(this);
    button.setForeground(secondColor);
    button.setBackground(buttonColor);
    button.setOpaque(true);
    button.setBorderPainted(false);
    p2.add(button);

    button = new JButton("R");
    button.setFont(font);
    button.addActionListener(this);
    button.addKeyListener(this);
    button.setForeground(secondColor);
    button.setBackground(buttonColor);
    button.setOpaque(true);
    button.setBorderPainted(false);
    p2.add(button);

    p2.setBackground(backgroundColor);

    return p2;
  }

  /**
   * Sets up the second row of buttons.
   * 
   * @return the panel containing the second row of buttons.
   */
  private JPanel buttonRow2()
  {
    JPanel p3 = new JPanel(new GridLayout(1, 5, 10, 0));

    JButton button = new JButton("1");
    button.setFont(font);
    button.addActionListener(this);
    button.addKeyListener(this);
    button.setForeground(mainColor);
    button.setBackground(buttonColor);
    button.setOpaque(true);
    button.setBorderPainted(false);
    p3.add(button);

    button = new JButton("2");
    button.setFont(font);
    button.addActionListener(this);
    button.addKeyListener(this);
    button.setForeground(mainColor);
    button.setBackground(buttonColor);
    button.setOpaque(true);
    button.setBorderPainted(false);
    p3.add(button);

    button = new JButton("3");
    button.setFont(font);
    button.addActionListener(this);
    button.addKeyListener(this);
    button.setForeground(mainColor);
    button.setBackground(buttonColor);
    button.setOpaque(true);
    button.setBorderPainted(false);
    p3.add(button);

    button = new JButton("-");
    button.setFont(font);
    button.addActionListener(this);
    button.addKeyListener(this);
    button.setForeground(secondColor);
    button.setBackground(buttonColor);
    button.setOpaque(true);
    button.setBorderPainted(false);
    p3.add(button);

    button = new JButton("Inv");
    button.setFont(font);
    button.addActionListener(this);
    button.addKeyListener(this);
    button.setForeground(secondColor);
    button.setBackground(buttonColor);
    button.setOpaque(true);
    button.setBorderPainted(false);
    p3.add(button);

    p3.setBackground(backgroundColor);

    return p3;
  }

  /**
   * Sets up the third row of buttons.
   * 
   * @return the panel containing the third row of buttons.
   */
  private JPanel buttonRow3()
  {
    JPanel p4 = new JPanel(new GridLayout(1, 5, 10, 0));

    JButton button = new JButton("4");
    button.setFont(font);
    button.addActionListener(this);
    button.addKeyListener(this);
    button.setForeground(mainColor);
    button.setBackground(buttonColor);
    button.setOpaque(true);
    button.setBorderPainted(false);
    p4.add(button);

    button = new JButton("5");
    button.setFont(font);
    button.addActionListener(this);
    button.addKeyListener(this);
    button.setForeground(mainColor);
    button.setBackground(buttonColor);
    button.setOpaque(true);
    button.setBorderPainted(false);
    p4.add(button);

    button = new JButton("6");
    button.setFont(font);
    button.addActionListener(this);
    button.addKeyListener(this);
    button.setForeground(mainColor);
    button.setBackground(buttonColor);
    button.setOpaque(true);
    button.setBorderPainted(false);
    p4.add(button);

    button = new JButton("x");
    button.setFont(font);
    button.addActionListener(this);
    button.addKeyListener(this);
    button.setForeground(secondColor);
    button.setBackground(buttonColor);
    button.setOpaque(true);
    button.setBorderPainted(false);
    p4.add(button);

    button = new JButton("Hist>");
    histButton = button;
    button.setFont(font);
    button.addActionListener(this);
    button.addKeyListener(this);
    button.setForeground(secondColor);
    button.setBackground(buttonColor);
    button.setOpaque(true);
    button.setBorderPainted(false);
    p4.add(button);

    p4.setBackground(backgroundColor);

    return p4;
  }

  /**
   * Sets up the fourth row of buttons.
   * 
   * @return the panel containing the fourth row of buttons.
   */
  private JPanel buttonRow4()
  {
    JPanel p5 = new JPanel(new GridLayout(1, 5, 10, 0));

    JButton button = new JButton("7");
    button.setFont(font);
    button.addActionListener(this);
    button.addKeyListener(this);
    button.setForeground(mainColor);
    button.setBackground(buttonColor);
    button.setOpaque(true);
    button.setBorderPainted(false);
    p5.add(button);

    button = new JButton("8");
    button.setFont(font);
    button.addActionListener(this);
    button.addKeyListener(this);
    button.setForeground(mainColor);
    button.setBackground(buttonColor);
    button.setOpaque(true);
    button.setBorderPainted(false);
    p5.add(button);

    button = new JButton("9");
    button.setFont(font);
    button.addActionListener(this);
    button.addKeyListener(this);
    button.setForeground(mainColor);
    button.setBackground(buttonColor);
    button.setOpaque(true);
    button.setBorderPainted(false);
    p5.add(button);

    button = new JButton("\u00f7");
    button.setFont(font);
    button.addActionListener(this);
    button.addKeyListener(this);
    button.setForeground(secondColor);
    button.setBackground(buttonColor);
    button.setOpaque(true);
    button.setBorderPainted(false);
    p5.add(button);

    button = new JButton("^");
    button.setFont(font);
    button.setEnabled(true);
    button.setVisible(true);
    button.addActionListener(this);
    button.addKeyListener(this);
    button.setForeground(secondColor);
    button.setBackground(buttonColor);
    button.setOpaque(true);
    button.setBorderPainted(false);
    p5.add(button);

    p5.setBackground(backgroundColor);

    return p5;
  }

  /**
   * Sets up the fifth row of buttons.
   * 
   * @return the panel containing the fifth row of buttons.
   */
  private JPanel buttonRow5()
  {
    JPanel p6 = new JPanel(new GridLayout(1, 5, 10, 0));

    JButton button = new JButton("0");
    button.setFont(font);
    button.addActionListener(this);
    button.addKeyListener(this);
    button.setForeground(mainColor);
    button.setBackground(buttonColor);
    button.setOpaque(true);
    button.setBorderPainted(false);
    p6.add(button);

    button = new JButton(">");
    button.setEnabled(true);
    button.setFont(font);
    button.addActionListener(this);
    button.addKeyListener(this);
    button.setForeground(mainColor);
    button.setBackground(buttonColor);
    button.setOpaque(true);
    button.setBorderPainted(false);
    p6.add(button);

    button = new JButton("\u03b3");
    button.setFont(font);
    button.addActionListener(this);
    button.addKeyListener(this);
    button.setForeground(secondColor);
    button.setBackground(buttonColor);
    button.setOpaque(true);
    button.setBorderPainted(false);
    p6.add(button);

    button = new JButton("=");
    button.setFont(font);
    button.addActionListener(this);
    button.addKeyListener(this);
    button.setForeground(secondColor);
    button.setBackground(buttonColor);
    button.setOpaque(true);
    button.setBorderPainted(false);
    p6.add(button);

    button = new JButton("Med.");
    button.setFont(font);
    button.addActionListener(this);
    button.addKeyListener(this);
    button.setForeground(secondColor);
    button.setBackground(buttonColor);
    button.setOpaque(true);
    button.setBorderPainted(false);
    p6.add(button);

    p6.setBackground(backgroundColor);

    return p6;
  }

  /**
   * Sets up the menu at top of window.
   */
  private void setupMenu()
  {
    JMenuBar menuBar = new JMenuBar();

    menuBar.addKeyListener(this);

    // Menus
    JMenu fileMenu = new JMenu("File");
    JMenu displayMenu = new JMenu("Display");
    JMenu helpMenu = new JMenu("Help");
    JMenu themeMenu = new JMenu("Theme");

    fileMenu.addKeyListener(this);
    displayMenu.addKeyListener(this);
    helpMenu.addKeyListener(this);
    themeMenu.addKeyListener(this);

    // About
    JMenuItem item = new JMenuItem("About");
    item.addActionListener(this);
    item.addKeyListener(this);
    helpMenu.add(item);

    // Show Steps
    item = new JMenuItem("Show Steps");
    item.addActionListener(this);
    item.addKeyListener(this);
    fileMenu.add(item);

    // Print
    item = new JMenuItem("Print");
    item.addActionListener(this);
    item.addKeyListener(this);
    fileMenu.add(item);

    // record
    item = new JMenuItem("Record");
    item.addActionListener(this);
    item.addKeyListener(this);
    fileMenu.add(item);

    // record
    item = new JMenuItem("Playback");
    item.addActionListener(this);
    item.addKeyListener(this);
    fileMenu.add(item);

    // Put in Exit Option
    item = new JMenuItem("Exit");
    item.addActionListener(this);
    item.addKeyListener(this);
    fileMenu.add(item);

    // Style Menu
    displayMenu.add(new StyleMenuButtons(this, style));
    displayMenu.add(new ModeMenuButtons(this, mode));

    // pie chart option
    item = new JMenuItem("Pie Chart");
    item.addActionListener(this);
    item.addKeyListener(this);
    displayMenu.add(item);

    // Help Menu
    item = new JMenuItem("Open in Browser");
    item.addActionListener(this);
    item.addKeyListener(this);
    helpMenu.add(item);

    menuBar.add(fileMenu);
    menuBar.add(displayMenu);
    menuBar.add(helpMenu);

    setJMenuBar(menuBar);
  }

  /**
   * Calculates the input from the input field to the output field.
   */
  private void calculate()
  {
    if (rightFrac.getFraction() == null)
    {
      rightWhole.addFrac(right);
      rightFrac.addFrac(right);
    }

    left = getLeftFraction();
    right = getRightFraction();

    if (left.getDenominator() == 0)
    {
      left = new MixedFraction(left.getWhole(), left.getNumerator(), 1, false);
    }
    if (right.getDenominator() == 0)
    {
      right = new MixedFraction(right.getWhole(), right.getNumerator(), 1, false);
    }

    MixedFraction result = opPane.calculateResult(left, right, historyField);
    mostRecentCalc = new Calculation(left, right, result, operation);
    if (mostRecentCalc != null)
    {
      stepsField.printSteps(mostRecentCalc);
    }
    recording.addCalculation(mostRecentCalc);
    if (operation != Operations.POWER)
    {
      historyField.addCalculation(left, right, result, operation);
    }
    if (result != null)
    {
      if (mode == IMPROPER)
      {
        int w, n, d;

        w = result.getWhole();
        n = result.getNumerator();
        d = result.getDenominator();

        n = (w * d) + n;

        result = new MixedFraction(0, n, d, false);
      }
      left = result;
      right = new MixedFraction(0, 0, 0, false);
      leftWhole.reset(left);
      leftFrac.reset(left);
      rightWhole.reset(null);
      rightFrac.reset(null);
      operation = null;
      opPane.setOp(null);
      focus = 0;

      if (pieActive)
      {
        pie.update(result.getNumerator(), result.getDenominator());
      }

      unhighlight();
      highlightFocus();
    }
    setupLayout();
    updateAll();
  }

  @Override
  public void focusGained(FocusEvent e)
  {

  }

  @Override
  public void focusLost(FocusEvent e)
  {

  }

  @Override
  public void componentResized(ComponentEvent e)
  {
    historyField.resize();
    stepsField.resize();
  }

  @Override
  public void componentMoved(ComponentEvent e)
  {
    historyField.move();
    stepsField.move();
  }

  @Override
  public void componentShown(ComponentEvent e)
  {

  }

  @Override
  public void componentHidden(ComponentEvent e)
  {

  }

  /**
   * Triggers animation for the movement of the historyField.
   */
  private void triggerAnimation()
  {
    if (!historyField.isMoving())
    {
      historyField.beginAnimation();
      if (histButton.getText().contentEquals("Hist>"))
      {
        histButton.setText("Hist<");
      }
      else
      {
        histButton.setText("Hist>");
      }
      setupLayout();
    }
  }

  /**
   * Triggers animation for the movement of the stepsField.
   */
  private void triggerStepsAnimation()
  {
    if (!stepsField.isMoving())
    {
      stepsField.beginAnimation();
      setupLayout();
    }
  }

  /**
   * Appends the current fraction in focus.
   * 
   * @param value
   *          int value to add to the fraction.
   */
  private void appendCurrent(int value)
  {
    InputField field = null;

    switch (focus)
    {
      case 0:
        field = leftWhole;
        break;
      case 1:
      case 2:
        field = leftFrac;
        break;
      case 3:
        field = rightWhole;
        break;
      case 4:
      case 5:
        field = rightFrac;
        break;
      default:
    }

    if (field.append(value, focus))
    {
      if (focus <= 2)
      {
        leftFrac.addFrac(leftWhole.getFraction());
      }
      else
      {
        rightFrac.addFrac(rightWhole.getFraction());
      }
    }
    setupLayout();
  }

  /**
   * Removes last digit from the fraction in focus.
   */
  private void backspaceCurrent()
  {
    InputField field = null;

    switch (focus)
    {
      case 0:
        field = leftWhole;
        break;
      case 1:
      case 2:
        field = leftFrac;
        break;
      case 3:
        field = rightWhole;
        break;
      case 4:
      case 5:
        field = rightFrac;
        break;
      default:
    }

    field.backspace(focus);
    setupLayout();
  }

  /**
   * Cycles the focus to the next part of the fraction.
   */
  private void cycleFocus()
  {
    unhighlight();

    if (operation == null)
    {
      focus = (focus + 1) % 3;
    }
    else
    {
      focus = (focus + 1) % 6;
    }

    if ((mode != MIXED) && (focus == 0 || focus == 3))
    {
      focus++;
    }

    if (operation == Operations.POWER && focus > 3)
    {
      focus = 0;
    }
    unhighlight();
    highlightFocus();
    setupLayout();
  }

  /**
   * Sets the operation string.
   * 
   * @param op
   *          the operation which will be performed.
   */
  private void setOp(Operations op)
  {
    boolean jump = false;

    if (operation == null)
    {
      rightWhole.addFrac(right);
      rightFrac.addFrac(right);
      jump = true;
      if (op == Operations.POWER)
      {
        rightFrac.setText("");
      }
    }
    operation = op;

    if (jump)
    {
      focus = 2;
      cycleFocus();
      leftWhole.unhighlight();
      leftFrac.unhighlight();
    }

    opPane.setOp(op);
    setupLayout();
  }

  /**
   * Highlights the fraction in focus.
   */
  private void highlightFocus()
  {
    InputField field = null;

    switch (focus)
    {
      case 0:
        field = leftWhole;
        break;
      case 1:
      case 2:
        field = leftFrac;
        break;
      case 3:
        field = rightWhole;
        break;
      case 4:
      case 5:
        field = rightFrac;
        break;
      default:
    }

    field.highlight(focus);
    setupLayout();
  }

  /**
   * Changes focus to the last part in focus.
   */
  private void decrementFocus()
  {
    unhighlight();

    if (operation == null)
    {
      if (focus == 0)
      {
        focus = 3;
      }
      else if (focus == 1 && mode != MIXED)
      {
        focus = 3;
      }
    }
    else
    {
      if (focus == 0)
      {
        focus = 6;
      }
      else if (focus == 4 && mode != MIXED)
      {
        focus = 3;
      }
      else if (focus == 1 && mode != MIXED)
      {
        focus = 6;
      }
    }

    focus--;

    if (operation == Operations.POWER && focus > 3)
    {
      focus = 3;
    }

    highlightFocus();
    setupLayout();
  }

  /**
   * Removes highlight.
   */
  private void unhighlight()
  {
    leftWhole.unhighlight();
    leftFrac.unhighlight();
    opPane.unhighlight();
    rightWhole.unhighlight();
    rightFrac.unhighlight();

    setupLayout();
  }

  /**
   * Updates all fields to the text set in FragileWindow.
   */
  private void updateAll()
  {
    leftFrac.setText();
    leftWhole.setText();
    opPane.setText();
    rightWhole.setText();
    rightFrac.setText();
    // stepsField.setText();
    unhighlight();
    highlightFocus();
    setupLayout();
  }

  /**
   * Clears all fields.
   */
  private void clearAll()
  {
    focus = 0;
    operation = null;

    left = new MixedFraction(0, 0, 1, false);
    right = new MixedFraction(0, 0, 1, false);

    leftWhole = new InputField(left, true, style);
    leftFrac = new InputField(left, false, style);
    opPane = new InputField(false, style);
    rightWhole = new InputField(true, style);
    rightFrac = new InputField(false, style);

    leftWhole.addKeyListener(this);
    leftFrac.addKeyListener(this);
    opPane.addKeyListener(this);
    rightWhole.addKeyListener(this);
    rightFrac.addKeyListener(this);

    mode = MIXED;

    // cycleMode();

    setupLayout();

    leftWhole.highlight(focus);
  }

  /**
   * Clears the field in focus.
   */
  private void clearCurrent()
  {
    if (focus <= 2)
    {
      left = new MixedFraction(0, 0, 1, false);
      leftWhole.addFrac(left);
      leftFrac.addFrac(left);
    }
    else
    {
      right = new MixedFraction(0, 0, 1, false);
      rightWhole.addFrac(right);
      rightFrac.addFrac(right);
    }
    setupLayout();
  }

  /**
   * Changes sign of the fraction in focus.
   */
  private void changeSign()
  {
    int w, n, d;

    InputField wholeField, fracField;

    if (focus <= 2)
    {
      wholeField = leftWhole;
      fracField = leftFrac;
    }
    else
    {
      wholeField = rightWhole;
      fracField = rightFrac;
    }

    w = wholeField.getFraction().getWhole();
    n = fracField.getFraction().getNumerator();
    d = fracField.getFraction().getDenominator();

    if (w == 0)
    {
      n *= -1;
    }
    else
    {
      w *= -1;
    }

    wholeField.addFrac(new MixedFraction(w, n, d, false));
    fracField.addFrac(new MixedFraction(w, n, d, false));

    setupLayout();
  }

  /**
   * Inverts the fraction in focus.
   */
  private void invertCurrent()
  {
    int w, n, d;

    InputField wholeField, fracField;

    if (focus <= 2)
    {
      wholeField = leftWhole;
      fracField = leftFrac;
    }
    else
    {
      wholeField = rightWhole;
      fracField = rightFrac;
    }

    w = wholeField.getFraction().getWhole();
    n = fracField.getFraction().getNumerator();
    d = fracField.getFraction().getDenominator();

    MixedFraction inverted = MixedFractionOperations.divide(new MixedFraction(1, 0, 1),
        new MixedFraction(w, n, d));
    wholeField.addFrac(inverted);
    fracField.addFrac(inverted);

    setupLayout();
  }

  /**
   * Returns main color.
   * 
   * @return color the color object
   */
  public Color getMainColor()
  {
    return this.mainColor;
  }

  /**
   * Returns secondary color.
   * 
   * @return color the color object
   */
  public Color getSecondColor()
  {
    return this.secondColor;
  }

  /**
   * Returns third color.
   * 
   * @return color the color object
   */
  public Color getThirdColor()
  {
    return this.thirdColor;
  }

  /**
   * Returns background color.
   * 
   * @return color the color object
   */
  public Color getBackgroundColor()
  {
    return this.backgroundColor;
  }

  /**
   * Returns input color.
   * 
   * @return color the color object
   */
  public Color getInputColor()
  {
    return this.inputColor;
  }

  /**
   * Returns button color.
   * 
   * @return color the color object
   */
  public Color getButtonColor()
  {
    return this.buttonColor;
  }

  /**
   * Returns the image icon logo.
   * 
   * @return logo
   */
  public ImageIcon getLogo()
  {
    return this.logo;
  }

  /**
   * Gets the left fraction.
   * 
   * @return leftfraction the mixedfraction object of the left fraction.
   */
  private MixedFraction getLeftFraction()
  {
    return new MixedFraction(leftWhole.getFraction().getWhole(),
        leftFrac.getFraction().getNumerator(), leftFrac.getFraction().getDenominator());
  }

  /**
   * Gets the right fraction.
   * 
   * @return rightfraction the mixedfraction object of the right fraction.
   */
  private MixedFraction getRightFraction()
  {
    if (rightFrac.getFraction() == null)
    {
      return new MixedFraction(0, 0, 1, true);
    }

    return new MixedFraction(rightWhole.getFraction().getWhole(),
        rightFrac.getFraction().getNumerator(), rightFrac.getFraction().getDenominator());
  }

  /**
   * Sets the mode.
   */
  private void setMode()
  {
    if (mode == MIXED)
    {
      leftWhole.setVisible(true);
      rightWhole.setVisible(true);

      int w, n, d;

      MixedFraction lef = getLeftFraction();

      w = lef.getWhole();
      n = lef.getNumerator();
      d = lef.getDenominator();

      lef = new MixedFraction(w, n, d, true);

      MixedFraction righ = getRightFraction();

      w = righ.getWhole();
      n = righ.getNumerator();
      d = righ.getDenominator();

      righ = new MixedFraction(w, n, d, true);

      leftWhole.addFrac(lef);
      leftFrac.addFrac(lef);
      if (rightFrac.getFraction() != null)
      {
        rightWhole.addFrac(righ);
        rightFrac.addFrac(righ);
      }
    }
    else if (mode == IMPROPER)
    {
      int w, n, d;

      MixedFraction lef = getLeftFraction();

      w = lef.getWhole();
      n = lef.getNumerator();
      d = lef.getDenominator();

      n = (w * d) + n;

      lef = new MixedFraction(0, n, d, false);

      MixedFraction righ = getRightFraction();

      w = righ.getWhole();
      n = righ.getNumerator();
      d = righ.getDenominator();

      n = (w * d) + n;

      righ = new MixedFraction(0, n, d, false);

      leftWhole.addFrac(lef);
      leftFrac.addFrac(lef);

      if (rightFrac.getFraction() != null)
      {
        rightWhole.addFrac(righ);
        rightFrac.addFrac(righ);
      }

      if (focus == 0 || focus == 3)
      {
        focus++;
      }
      leftWhole.setVisible(false);
      rightWhole.setVisible(false);
    }
    else
    {
      int w, n, d;

      MixedFraction lef = getLeftFraction();

      w = lef.getWhole();
      n = lef.getNumerator();
      d = lef.getDenominator();

      lef = new MixedFraction(w, n, d, true);

      MixedFraction righ = getRightFraction();

      w = righ.getWhole();
      n = righ.getNumerator();
      d = righ.getDenominator();

      righ = new MixedFraction(w, n, d, true);

      leftWhole.addFrac(lef);
      leftFrac.addFrac(lef);

      if (rightFrac.getFraction() != null)
      {
        rightWhole.addFrac(righ);
        rightFrac.addFrac(righ);
      }

      if (focus == 0 || focus == 3)
      {
        focus++;
      }
      leftWhole.setVisible(false);
      rightWhole.setVisible(false);
    }

    updateAll();
  }

  /**
   * returns the style of the fractions.
   * 
   * @return int style of the fractions.
   */
  public int getStyle()
  {
    return style;
  }

}
