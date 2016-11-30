package MatchController.Gui.TournamentWinnerFrame;

import MatchController.MatchController;
import MatchController.Objects.PlayerObject;

public class TournamentWinnerFrame extends TournamentWinnerFrameGui
{
	public TournamentWinnerFrame (MatchController matchController, PlayerObject winner)
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
