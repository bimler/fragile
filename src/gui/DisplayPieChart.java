package gui;

import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JFrame;

/**
 * Frame which displays the pie chart of the leftmost fraction/solution.
 *
 */
public class DisplayPieChart extends JFrame
{
  private static final long serialVersionUID = 1L;
  private double num;
  private double den;

  /**
   * Constructor.
   * 
   * @param numerator
   *          numerator as int.
   * @param denominator
   *          denominator as int.
   */
  public DisplayPieChart(int numerator, int denominator)
  {
    num = numerator;
    den = denominator;
    setTitle("Pie Display");
    setSize(500, 500);
    setVisible(true);
    setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
  }

  /**
   * Makes the pie chart invisible.
   */
  public void close()
  {
    setVisible(false);
  }

  /**
   * Paints the pie chart.
   * 
   * @param g
   *          graphics
   */
  public void paint(Graphics g)
  {
    double angle = 0.0;
    if (num != 0 && den != 0 && num < den)
    {
      angle = (double) ((num / den) * 360);
    }
    Rectangle bound = getBounds();
    g.fillArc(bound.x + 20, bound.y + 40, bound.width - 50, bound.height - 50, 180, (int) angle);
    g.drawArc(bound.x + 20, bound.y + 40, bound.width - 50, bound.height - 50, 0, 360);
  }

  /**
   * Updates the value of the pie chart.
   * 
   * @param x
   *          numerator
   * @param y
   *          denominator
   */
  public void update(int x, int y)
  {
    this.removeAll();
    updateChart(x, y);
  }

  /**
   * Updates the value of the pie chart.
   * 
   * @param x
   *          numerator
   * @param y
   *          denominator
   */
  private void updateChart(int x, int y)
  {
    this.setVisible(true);
    num = x;
    den = y;
    repaint();
    revalidate();
  }
}
