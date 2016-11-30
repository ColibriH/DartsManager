package MatchController.Gui.Components;

import MatchController.Objects.GroupPlayerObject;

public interface GroupTournamentMethod
{
	void execute ();
	void execute (GroupPlayerObject loserGroup, GroupPlayerObject winnerGroup);
}
