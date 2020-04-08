package gui;

import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

/**
 * A class for radio buttons as a menu item.
 * 
 * @author bradyimler
 *
 */
public class StyleMenuButtons extends JMenu
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private static final int BAR_STYLE = 0, SLASH_STYLE = 2, SOLIDUS_STYLE = 1;

  private ActionListener listener;
  private int style;

  /**
   * Constructor for menu buttons.
   * 
   * @param listener
   *          actionlistener for actions
   * @param style
   *          style, the current style
   */
  public StyleMenuButtons(ActionListener listener, int style)
  {
    super("Style");
    this.listener = listener;
    this.style = style;
    setupButtons();
  }

  /**
   * Setup the menu buttons, select the button that is the current style.
   */
  public void setupButtons()
  {
    ButtonGroup group = new ButtonGroup();

    JRadioButtonMenuItem menuItemBar = new JRadioButtonMenuItem("Bar Style");
    menuItemBar.addActionListener(listener);
    group.add(menuItemBar);
    this.add(menuItemBar);

    JRadioButtonMenuItem menuItemSlash = new JRadioButtonMenuItem("Slash Style");
    menuItemSlash.addActionListener(listener);
    group.add(menuItemSlash);
    this.add(menuItemSlash);

    JRadioButtonMenuItem menuItemSolidus = new JRadioButtonMenuItem("Solidus Style");
    menuItemSolidus.addActionListener(listener);
    group.add(menuItemSolidus);
    this.add(menuItemSolidus);

    if (style == BAR_STYLE)
    {
      group.clearSelection();
      menuItemBar.setSelected(true);
    }
    else if (style == SLASH_STYLE)
    {
      group.clearSelection();
      menuItemSlash.setSelected(true);
    }
    else if (style == SOLIDUS_STYLE)
    {
      group.clearSelection();
      menuItemSolidus.setSelected(true);
    }

  }

}
