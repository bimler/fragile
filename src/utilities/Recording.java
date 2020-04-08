package utilities;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * For recording the history to a file.
 * 
 */
public class Recording
{

  private ArrayList<Calculation> history;

  /**
   * Constructor.
   */
  public Recording()
  {
    history = new ArrayList<Calculation>();
  }

  /**
   * Adds a calculation to the recording.
   * 
   * @param calculation
   *          the calcualtion to be recorded.
   */
  public void addCalculation(Calculation calculation)
  {
    history.add(calculation);
  }

  /**
   * Opens the recorded file.
   * 
   * @throws IOException
   *           if the file is not found
   */
  public void playback() throws IOException
  {

    File file = new File("calculation_history.txt");
    Desktop desk = Desktop.getDesktop();
    desk.open(file);
  }

  /**
   * Records to file.
   * 
   * @throws IOException
   * 			if the files is not found.
   */
  public void record() throws IOException
  {
    BufferedWriter writer = new BufferedWriter(new FileWriter("calculation_history.txt"));
    String output = "";

    for (int i = 0; i < history.size(); i++)
    {
      output += history.get(i).toString() + "     ";
    }

    writer.write(output);
    writer.close();
  }
}
