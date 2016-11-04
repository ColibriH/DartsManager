package MatchController.Objects;

import MatchController.GUI.Components.DisplayGroupPanel;

import javax.swing.*;

/**
 * Created by vladislavs on 07.09.2016..
 */

// TODO Create setters and getters

public class PlayerObject
{
	public String               mName;
	public Integer              mId;
	public DisplayGroupPanel    mDisplayGroupPanel;
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


	public DisplayGroupPanel getDisplayGroupPanel ()
	{
		return mDisplayGroupPanel;
	}


	public void setDisplayGroupPanel (DisplayGroupPanel displayGroupPanel)
	{
		mDisplayGroupPanel = displayGroupPanel;
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
