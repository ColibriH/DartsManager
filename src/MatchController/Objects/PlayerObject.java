package MatchController.Objects;

import MatchController.GUI.DisplayGroupPanel;

/**
 * Created by vladislavs on 07.09.2016..
 */
public class PlayerObject
{
	public String mName;
	public Integer mId;

	public Integer mLeg       = 0;
	public Integer mScore     = 301;
	public Integer mPrevScore = 301;
	public DisplayGroupPanel displayGroupPanel;

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
		return displayGroupPanel;
	}


	public void setDisplayGroupPanel (DisplayGroupPanel displayGroupPanel)
	{
		this.displayGroupPanel = displayGroupPanel;
	}
}
