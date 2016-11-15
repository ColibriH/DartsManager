package MainController.Menu;

import MainController.MainController;

/**
 * Created by vladislavs on 15.11.2016..
 */
public class Menu extends MenuGui
{
	@Override
	protected void tournamentButtonAction ()
	{
		MainController.startMatch ();
		destroy ();
	}
}
