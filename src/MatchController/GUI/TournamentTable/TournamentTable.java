package MatchController.GUI.TournamentTable;

import MatchController.GUI.Components.DisplayGroupPanel;
import MatchController.MatchController;

import java.util.ArrayList;

/**
 * Created by vladislavs on 11.11.2016..
 */
public class TournamentTable extends TournamentTableGui
{
	public TournamentTable (MatchController matchController)
	{
		super (matchController);
	}


	@Override
	protected void setCurrentPlayingGroupText ()
	{
		hideCurrentPlayingGroupPanelForAllGroups ();
		setCurrentPlayingGroupText (getMatchController ().getCurrentPlayingGroupPanel (), true);
	}


	private void setCurrentPlayingGroupText (DisplayGroupPanel panel, boolean state)
	{
		try
		{
			panel.setCurrentPlayingGroup (state);
		}
		catch (Exception e)
		{
			e.printStackTrace ();
		}
	}


	private void hideCurrentPlayingGroupPanelForAllGroups ()
	{
		ArrayList<DisplayGroupPanel> allMatchGroupsPanels = getMatchController ().getAllMatchGroupsPanels ();
		for (DisplayGroupPanel allMatchGroupsPanel : allMatchGroupsPanels)
			setCurrentPlayingGroupText (allMatchGroupsPanel, false);
	}


	public void displayCurrentPlayingGroupText ()
	{
		setCurrentPlayingGroupText ();
	}
}
