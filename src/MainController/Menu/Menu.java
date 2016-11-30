package MainController.Menu;

import Constants.Constats;
import MainController.MainController;

public class Menu extends MenuGui
{
	@Override
	protected void tournamentButtonAction ()
	{
		MainController.startMatch (Constats.GameType.Tournament);
		destroy ();
	}


	@Override
	protected void groupTournamentButtonAction ()
	{
		MainController.startMatch (Constats.GameType.GroupTournament);
		destroy ();
	}
}
