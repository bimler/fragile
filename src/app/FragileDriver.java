package app;

import gui.*;

import java.awt.Color;
import java.lang.reflect.*;
import javax.swing.*;

/**
 * The main class for the Fragile application.
 */
public class FragileDriver implements Runnable
{
  /**
   * The entry point of the application.
   * 
   * @param args
   *          The command line arguments (which are ignored)
   */
  public static void main(String[] args)
  {
    try
    {
      SwingUtilities.invokeAndWait(new FragileDriver());
    }
    catch (InterruptedException | InvocationTargetException e)
    {
      e.printStackTrace();
      System.out.println("Unable to start the GUI.");
    }
  }

  /**
   * The code to execute in the event dispatch thread.
   */
  public void run()
  {
    int theme = 1;
    FragileWindow window;

    if (theme == 1)
    {
      window = new FragileWindow(new Color(0, 128, 97), new Color(237, 27, 45),
          new Color(245, 130, 30), new Color(0, 128, 97), new Color(255, 255, 255),
          new Color(255, 255, 255), new Color(0, 128, 97), "7eleven.png");
    }
    else
    {
      window = new FragileWindow(new Color(0, 0, 0), new Color(0, 191, 255), new Color(255, 215, 0),
          null, new Color(255, 255, 255), new Color(255, 255, 255), new Color(255, 255, 255),
          "fragileLogo.gif");
    }

    window.setVisible(true);
  }

}
