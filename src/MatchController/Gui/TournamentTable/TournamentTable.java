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
		setCurrentPlayingGroupText (getMatchController ().getCurrentTournamentPlayingGroupPanel (), true);
	}


	private void setCurrentPlayingGroupText (TournamentTableGroupPanel tournamentTableGroupPanel, boolean visibilityState)
	{
		tournamentTableGroupPanel.setCurrentPlayingGroup (visibilityState);
	}


	private void hideCurrentPlayingGroupPanelForAllGroups ()
	{
		ArrayList<TournamentTableGroupPanel> allTournamentMatchGroupsPanels = getMatchController ().getAllTournamentMatchGroupsPanels ();
		for (TournamentTableGroupPanel tournamentTableGroupPanel : allTournamentMatchGroupsPanels)
			setCurrentPlayingGroupText (tournamentTableGroupPanel, false);
	}


	public void displayCurrentPlayingGroupText ()
	{
		setCurrentPlayingGroupText ();
	}
}
