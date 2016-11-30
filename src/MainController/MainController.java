package MainController;

import Constants.Constats;
import MainController.Menu.Menu;
import MatchController.MatchController;

public class MainController
{
    private static  MatchController  matchController;
	public static   boolean           DEBUG_MODE;

	public static void openMenuGui ()
	{
		new Menu ();
	}


	public static void startMatch (Constats.GameType gameType)
	{
		if (matchController == null)
			matchController = new MatchController (gameType);
		else
			matchController.initializeNewMatch (gameType);
	}


	public static MatchController getMatchController ()
	{
		return matchController;
	}


	public static void setMatchController (MatchController matchController)
	{
		MainController.matchController = matchController;
	}
}
