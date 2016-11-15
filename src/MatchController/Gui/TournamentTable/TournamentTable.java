package MatchController.Gui.TournamentTable;

import MatchController.Gui.Components.TournamentTableGroupPanel;
import MatchController.MatchController;

import java.util.ArrayList;

/**
 * Created by vladislavs on 11.11.2016..
 */

// TODO Refactor

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


	private void setCurrentPlayingGroupText (TournamentTableGroupPanel panel, boolean state)
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
		ArrayList<TournamentTableGroupPanel> allMatchGroupsPanels = getMatchController ().getAllMatchGroupsPanels ();
		for (TournamentTableGroupPanel allMatchGroupsPanel : allMatchGroupsPanels)
			setCurrentPlayingGroupText (allMatchGroupsPanel, false);
	}


	public void displayCurrentPlayingGroupText ()
	{
		setCurrentPlayingGroupText ();
	}
}
