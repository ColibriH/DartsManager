package MatchController.Objects;

import MatchController.Gui.Components.TournamentTableGroupPanel;

import javax.swing.*;

public class PlayerObject
{
	private String               		mName;
	private Integer              		mId;
	private TournamentTableGroupPanel 	mTournamentTableGroupPanel;
	private JLabel               		mTurnArrow;
	private Integer             		mLeg       = 0;
	private Integer             		mScore     = 301;
	private Integer             		mPrevScore = 301;
	private Integer                     mLooses    = 0;
	private Integer                     mWinPoints = 0;


	public PlayerObject (String name, Integer id)
	{
		mName   = name;
		mId     = id;
	}


	public PlayerObject (PlayerObject playerObject)
	{
		mName       = playerObject.mName;
		mId         = playerObject.mId;
		mLeg        = playerObject.mLeg;
		mScore      = playerObject.mScore;
		mLooses     = playerObject.mLooses;
		mWinPoints  = playerObject.mWinPoints;
	}


	public TournamentTableGroupPanel getTournamentTableGroupPanel()
	{
		return mTournamentTableGroupPanel;
	}


	public void setTournamentTableGroupPanel(TournamentTableGroupPanel tournamentTableGroupPanel)
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


	public String getName ()
	{
		return mName;
	}


	public void setName (String name)
	{
		mName = name;
	}


	public Integer getId ()
	{
		return mId;
	}


	public void setId (Integer id)
	{
		mId = id;
	}


	public Integer getLeg ()
	{
		return mLeg;
	}


	public void setLeg (Integer leg)
	{
		mLeg = leg;
	}


	public Integer getScore ()
	{
		return mScore;
	}


	public void setScore (Integer score)
	{
		mScore = score;
	}


	public Integer getPrevScore ()
	{
		return mPrevScore;
	}


	public void setPrevScore (Integer prevScore)
	{
		mPrevScore = prevScore;
	}


	public Integer getLooses ()
	{
		return mLooses;
	}


	public void setLooses (Integer looses)
	{
		mLooses = looses;
	}


	public Integer getWinPoints ()
	{
		return mWinPoints;
	}


	public void setWinPoints (Integer winPoints)
	{
		mWinPoints = winPoints;
	}
}
