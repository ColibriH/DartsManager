package MatchController.Gui.WinnerFrame;

import MatchController.MatchController;
import MatchController.Objects.PlayerObject;

/**
 * Created by vladislavs on 14.11.2016..
 */
public class WinnerFrame extends WinnerFrameGui
{
	public WinnerFrame (MatchController matchController, PlayerObject winner)
	{
		super (matchController, winner);
	}


	@Override
	protected void menuButtonAction ()
	{
		getMatchController ().newMatch ();
		getMainJFrame ().setVisible (false);
		getMainJFrame ().dispose ();
	}


	protected void exitButtonAction ()
	{
		menuButtonAction ();
		getMatchController ().exitFromApplication ();
	}
}
