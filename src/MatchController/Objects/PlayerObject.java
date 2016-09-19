package MatchController.Objects;

/**
 * Created by vladislavs on 07.09.2016..
 */
public class PlayerObject
{
	public String mName;
	public Integer mId;

	public int mLeg       = 0;
	public int mScore     = 301;
	public int mPrevScore = 301;

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
}
