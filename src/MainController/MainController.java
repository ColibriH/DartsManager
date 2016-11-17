package MainController;

import Constants.Constats;
import MainController.Menu.Menu;
import MatchController.MatchController;
import MainController.Menu.MenuGui;

/**
 * Created by vladislavs on 30.09.2016..
 */
public class MainController
{
	public static boolean DEBUG_MODE;
    private static MatchController matchController;


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
