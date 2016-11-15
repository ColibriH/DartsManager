package MatchController.Objects;

import MatchController.Gui.Components.TournamentTableGroupPanel;

import javax.swing.*;

/**
 * Created by vladislavs on 07.09.2016..
 */

// TODO Create setters and getters

public class PlayerObject
{
	public String               mName;
	public Integer              mId;
	public TournamentTableGroupPanel mTournamentTableGroupPanel;
	public JLabel               mTurnArrow;

	public Integer              mLeg       = 0;
	public Integer              mScore     = 301;
	public Integer              mPrevScore = 301;


	public PlayerObject (String name, Integer id)
	{
		mName   = name;
		mId     = id;
	}


	public PlayerObject (PlayerObject playerObject)
	{
		mName   = playerObject.mName;
		mId     = playerObject.mId;
		mLeg    = playerObject.mLeg;
		mScore  = playerObject.mScore;
	}


	public TournamentTableGroupPanel getDisplayGroupPanel ()
	{
		return mTournamentTableGroupPanel;
	}


	public void setDisplayGroupPanel (TournamentTableGroupPanel tournamentTableGroupPanel)
	{
		mTournamentTableGroupPanel = tournamentTableGroupPanel;
	}


	public JLabel getTurnArrow ()
	{
		return mTurnArrow;
	}


	public void setTurnArrow (JLabel turnArrow)
	{
		mTurnArrow = turnArrow;
	}
}
