package MainController;

import MatchController.MatchController;
import MenuGui.MenuGui;

/**
 * Created by vladislavs on 30.09.2016..
 */
public class MainController
{
	public static MatchController matchController;

	public static void openMenuGui ()
	{
		new MenuGui ();
	}


	public static void startMatch ()
	{
		if (matchController == null)
			matchController = new MatchController ();
		else
			matchController.initializeNewMatch ();
	}
}
