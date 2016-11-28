package MatchController.Objects;

import MatchController.Gui.Components.GroupTournamentTableGroupPanel;
import MatchController.Gui.Components.TournamentTableGroupPanel;

public class GroupPlayerObject
{
	private PlayerObject                    mFirstPlayer;
	private PlayerObject                    mSecondPlayer;
	private GroupTournamentTableGroupPanel  mGroupTournamentGroupPanel;


	public GroupPlayerObject (PlayerObject firstPlayer, PlayerObject secondPlayer)
	{
		mFirstPlayer    = firstPlayer;
		mSecondPlayer   = secondPlayer;
	}


	public GroupPlayerObject (GroupPlayerObject groupPlayerObject)
	{
		mFirstPlayer            = groupPlayerObject.mFirstPlayer;
		mSecondPlayer           = groupPlayerObject.mSecondPlayer;
		mGroupTournamentGroupPanel = groupPlayerObject.mGroupTournamentGroupPanel;
	}


	public PlayerObject getFirstPlayer ()
	{
		return mFirstPlayer;
	}


	public void setFirstPlayer (PlayerObject firstPlayer)
	{
		mFirstPlayer = firstPlayer;
	}


	public PlayerObject getSecondPlayer ()
	{
		return mSecondPlayer;
	}


	public void setSecondPlayer (PlayerObject secondPlayer)
	{
		mSecondPlayer = secondPlayer;
	}


	public void setGroupTournamentGroupPanel (GroupTournamentTableGroupPanel tournamentGroupPanel)
	{
		mGroupTournamentGroupPanel = tournamentGroupPanel;
	}


	public GroupTournamentTableGroupPanel getGroupTournamentGroupPanel ()
	{
		return mGroupTournamentGroupPanel;
	}
}
