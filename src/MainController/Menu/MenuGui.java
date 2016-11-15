package MainController.Menu;

import BaseAbstractClasses.DartsGuiFormBase;
import GuiComponents.ImagedPanel;
import Constants.Constats;
import GuiComponents.MenuButton;
import Tools.FontLoader;
import Tools.ImageLoader;

import javax.swing.*;
import java.awt.*;

/**
 * Created by vladislavs on 23.09.2016..
 */
public abstract class MenuGui extends DartsGuiFormBase
{
	protected abstract void tournamentButtonAction ();

	private final int   MENU_BUTTON_WIDTH     = 250;
	private final int   MENU_BUTTON_HEIGHT    = 50;
	private final int   CTR_PANEL_WIDTH       = 270;
	private final int   CTR_PANEL_HEIGHT      = 325;

	private JPanel      mControlPanel;

	private MenuButton  mTournamentButton;
	private MenuButton  mExitButton;
	private MenuButton  mOptionsButton;
	private MenuButton  mEachVsEachButton;


	@Override
	protected void  initializeComponents ()
	{
		setMainJPanel (new ImagedPanel (ImageLoader.getImage (Constats.MENU_BG_PIC)));

		mControlPanel           = new JPanel ();

		mTournamentButton       = new MenuButton ("Tournament",   MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
		mExitButton             = new MenuButton ("Exit",         MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
		mOptionsButton          = new MenuButton ("Settings",     MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
		mEachVsEachButton       = new MenuButton ("Each Vs Each", MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
	}


	@Override
	protected void  addComponentsListener ()
	{
		mTournamentButton.  addActionListener (e -> tournamentButtonAction ());
		mExitButton.        addActionListener (e -> System.exit (1));
	}


	@Override
	protected void  buildMainPanel ()
	{
		GridBagConstraints gbc = new GridBagConstraints ();

		FontLoader.loadFont ();
		getMainJPanel ().setLayout (new GridBagLayout ());
		getMainJPanel ().setPreferredSize (new Dimension (Constats.MAIN_WIDTH - 10, Constats.MAIN_HEIGHT - 10));

		buildControlPanel ();

		addComponentToPanel (getMainJPanel (), mControlPanel, 0, 0, new Insets (90, 20, 0 ,0), 0, 0, 0, 1, null, gbc, GridBagConstraints.HORIZONTAL);
	}


	private void    buildControlPanel ()
	{
		GridBagConstraints gbc = new GridBagConstraints ();

		mControlPanel.setBackground (new Color (255, 255, 0, 0));
		mControlPanel.setLayout (new GridBagLayout ());
		mControlPanel.setOpaque (false);
		mControlPanel.setPreferredSize (new Dimension (CTR_PANEL_WIDTH, CTR_PANEL_HEIGHT));

		addComponentToPanel (mControlPanel, mTournamentButton,  0, 0, new Insets (5, 0, 5, 0), 0, 0, 0, 1, null, gbc, GridBagConstraints.HORIZONTAL);
		addComponentToPanel (mControlPanel, mEachVsEachButton,  0, 1, new Insets (5, 0, 5, 0), 0, 0, 0, 1, null, gbc, GridBagConstraints.HORIZONTAL);
		addComponentToPanel (mControlPanel, mOptionsButton,     0, 2, new Insets (5, 0, 5, 0), 0, 0, 0, 1, null, gbc, GridBagConstraints.HORIZONTAL);
		addComponentToPanel (mControlPanel, mExitButton,        0, 3, new Insets (5, 0, 5, 0), 0, 0, 0, 1, null, gbc, GridBagConstraints.HORIZONTAL);
	}


	public JPanel       getControlPanel ()
	{
		return mControlPanel;
	}


	public void         setControlPanel (JPanel mControlPanel)
	{
		this.mControlPanel = mControlPanel;
	}


	public MenuButton   getTournamentButton ()
	{
		return mTournamentButton;
	}


	public void         setTournamentButton (MenuButton mTournamentButton)
	{
		this.mTournamentButton = mTournamentButton;
	}


	public MenuButton   getExitButton ()
	{
		return mExitButton;
	}


	public void         setExitButton (MenuButton mExitButton)
	{
		this.mExitButton = mExitButton;
	}


	public MenuButton   getOptionsButton ()
	{
		return mOptionsButton;
	}


	public void         setOptionsButton (MenuButton mOptionsButton)
	{
		this.mOptionsButton = mOptionsButton;
	}


	public MenuButton   getEachVsEachButton ()
	{
		return mEachVsEachButton;
	}


	public void         setEachVsEachButton (MenuButton mEachVsEachButton)
	{
		this.mEachVsEachButton = mEachVsEachButton;
	}
}
