package MainController.Menu;

import Constants.Constats;
import MainController.MainController;

/**
 * Created by vladislavs on 15.11.2016..
 */
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
