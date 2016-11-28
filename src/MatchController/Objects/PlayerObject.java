package MatchController.Objects;

import MatchController.Gui.Components.TournamentTableGroupPanel;

import javax.swing.*;

/**
 * Created by vladislavs on 07.09.2016..
 */
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
		mLooses  = playerObject.mLooses;
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


	public void setName (String mName)
	{
		this.mName = mName;
	}


	public Integer getId ()
	{
		return mId;
	}


	public void setId (Integer mId)
	{
		this.mId = mId;
	}


	public Integer getLeg ()
	{
		return mLeg;
	}


	public void setLeg (Integer mLeg)
	{
		this.mLeg = mLeg;
	}


	public Integer getScore ()
	{
		return mScore;
	}


	public void setScore (Integer mScore)
	{
		this.mScore = mScore;
	}


	public Integer getPrevScore ()
	{
		return mPrevScore;
	}


	public void setPrevScore (Integer mPrevScore)
	{
		this.mPrevScore = mPrevScore;
	}


	public Integer getmLooses ()
	{
		return mLooses;
	}


	public void setmLooses (Integer mLooses)
	{
		this.mLooses = mLooses;
	}
}
