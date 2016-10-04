package MenuGui;

import MainController.MainController;
import MatchController.Constats;
import Tools.FontLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by vladislavs on 23.09.2016..
 */
public class MenuGui
{
	private final int MENU_BUTTON_WIDTH     = 250;
	private final int MENU_BUTTON_HEIGHT    = 50;
	private final int CTR_PANEL_WIDTH       = 270;
	private final int CTR_PANEL_HEIGHT      = 325;

	private JFrame                  mJFrame;
	private JPanel                  mCtrBtnPanel;
	private ImagedPanel             mJPanel;

	private MenuButton              tournamentButton;
	private MenuButton              exitButton;
	private MenuButton              optionsButton;
	private MenuButton              eachVsEachButton;


	public MenuGui ()
	{
		FontLoader.loadFont ();
		variableInitialization ();
		frameInitialization ();
	}


	private void variableInitialization ()
	{
		mJFrame             = new JFrame ();
		mJPanel             = new ImagedPanel ();
		mCtrBtnPanel        = new JPanel ();
		tournamentButton    = new MenuButton ("Tournament",     MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
		exitButton          = new MenuButton ("Exit",           MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
		optionsButton       = new MenuButton ("Settings",       MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
		eachVsEachButton    = new MenuButton ("Each Vs Each",   MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
	}


	private void frameInitialization ()
	{
		mJFrame.setContentPane (mJPanel);
		mJFrame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
		formComponentsModifications ();
		mJFrame.pack ();
		mJFrame.setResizable (false);
		mJFrame.setVisible (true);
		setMJFrameLocation ();
	}


	private void formComponentsModifications ()
	{
		setComponentOptions ();
		addComponents ();
		addComponentsListeners ();
	}


	private void setComponentOptions ()
	{
		mJPanel.setLayout (new GridBagLayout ());
		mJPanel.setPreferredSize (new Dimension (Constats.MAIN_WIDTH - 10, Constats.MAIN_HEIGHT - 10));

		mCtrBtnPanel.setBackground (new Color (255, 255, 0, 0));
		mCtrBtnPanel.setLayout (new GridBagLayout ());
		mCtrBtnPanel.setOpaque (false);
		mCtrBtnPanel.setPreferredSize (new Dimension (CTR_PANEL_WIDTH, CTR_PANEL_HEIGHT));
	}


	private void addComponents ()
	{
		GridBagConstraints gridBagConstraints = new GridBagConstraints ();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.insets = new Insets (90, 10, 0 ,0);

		mJPanel.add (mCtrBtnPanel, gridBagConstraints);
		addButton (tournamentButton, gridBagConstraints, 0);
		addButton (eachVsEachButton, gridBagConstraints, 1);
		addButton (optionsButton, gridBagConstraints, 2);
		addButton (exitButton, gridBagConstraints, 3);
	}


	private void addButton (MenuButton menuButton, GridBagConstraints gbc, int pos)
	{
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = pos;
		gbc.insets  = new Insets (5, 0, 5, 0);
		mCtrBtnPanel.add (menuButton, gbc);
	}


	private void destroy ()
	{
		mJFrame.dispose ();
	}


	private void setMJFrameLocation ()
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mJFrame.setLocation (dim.width / 2 - mJFrame.getSize ().width / 2, 0);
	}


	private void addComponentsListeners ()
	{
		tournamentButton.addActionListener (new ActionListener ()
		{
			@Override
			public void actionPerformed (ActionEvent e)
			{
				MainController.startMatch ();
				destroy ();
			}
		});

		exitButton.addActionListener (new ActionListener ()
		{
			@Override
			public void actionPerformed (ActionEvent e)
			{
				System.exit (1);
			}
		});
	}
}
