package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/**
 * About window for displaying information about the product.
 * 
 * @author bradyimler
 *
 */
public class AboutDialog extends JDialog implements ActionListener
{

  private static final long serialVersionUID = 1L;

  /**
   * Constructor.
   * 
   * @param window
   *          the window which the dialog belongs to.
   */
  public AboutDialog(FragileWindow window)
  {
    super(window, "About");
    setupDialog();
  }

  /**
   * Override for ActionListener implementation.
   * 
   * @param e
   * 	 from actionlistener.
   */
  public void actionPerformed(ActionEvent e)
  {
    String command = e.getActionCommand();

    if (command.contentEquals("OK"))
    {
      this.dispose();
    }
  }

  /**
   * Sets up the dialog layout/positioning.
   */
  public void setupDialog()
  {
    Container contentPane;

    setSize(400, 250);

    setLocationRelativeTo(null);

    contentPane = this.getContentPane();
    contentPane.setLayout(new BorderLayout());

    JTextPane about = new JTextPane();
    about.setContentType("type/html");
    about.setEditable(false);

    // add about text from file
    File file = new File("resources/about.html");
    try
    {
      about.setPage(file.toURI().toURL());
    }
    catch (MalformedURLException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    JScrollPane scrollPane = new JScrollPane(about);
    // always show scroll bar
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    contentPane.add(scrollPane, BorderLayout.CENTER);

    // create a panel for the buttons
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
    // create okay and cancel buttons
    JButton okButton = new JButton("OK");
    // add listeners and add to the button panel
    okButton.addActionListener(this);
    buttonPanel.add(okButton);

    // add panel to contentPane
    contentPane.add(buttonPanel, BorderLayout.SOUTH);
  }

}
