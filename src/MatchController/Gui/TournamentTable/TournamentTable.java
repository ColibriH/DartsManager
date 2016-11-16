package MatchController.Gui.TournamentTable;

import MatchController.Gui.Components.TournamentTableGroupPanel;
import MatchController.MatchController;

import java.util.ArrayList;

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
		panel.setCurrentPlayingGroup (state);
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
