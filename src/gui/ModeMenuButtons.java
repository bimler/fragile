package gui;

import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

/**
 * A class for menu buttons as a menu item.
 * 
 * @author bradyimler
 *
 */
public class ModeMenuButtons extends JMenu
{
  public static final int MIXED = 0, IMPROPER = 1, PROPER = 2;
  private static final long serialVersionUID = 1L;

  private int mode;
  private ActionListener listener;

  /**
   * Constructor.
   * 
   * @param listener
   *          actionlistener for actions
   * @param mode
   *          the current mode to be selected
   */
  public ModeMenuButtons(ActionListener listener, int mode)
  {
    super("Mode");
    this.listener = listener;
    this.mode = mode;
    setupButtons();
  }

  /**
   * Sets up the menu buttons.
   */
  public void setupButtons()
  {
    ButtonGroup group = new ButtonGroup();

    JRadioButtonMenuItem menuItemM = new JRadioButtonMenuItem("Mixed");
    menuItemM.addActionListener(listener);
    group.add(menuItemM);
    this.add(menuItemM);

    JRadioButtonMenuItem menuItemI = new JRadioButtonMenuItem("Improper");
    menuItemI.addActionListener(listener);
    group.add(menuItemI);
    this.add(menuItemI);

    JRadioButtonMenuItem menuItemP = new JRadioButtonMenuItem("Proper");
    menuItemP.addActionListener(listener);
    group.add(menuItemP);
    this.add(menuItemP);

    if (mode == MIXED)
    {
      group.clearSelection();
      menuItemM.setSelected(true);
    }
    else if (mode == IMPROPER)
    {
      group.clearSelection();
      menuItemI.setSelected(true);
    }
    else if (mode == PROPER)
    {
      group.clearSelection();
      menuItemP.setSelected(true);
    }

  }

}
