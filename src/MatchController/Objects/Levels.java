package MatchController.Objects;

import java.util.HashMap;

/**
 * Created by vladislavs on 01.11.2016..
 */

// TODO Refactor

public class Levels
{
	public HashMap<Integer, Integer> mGroupCntOnLvls = new HashMap <> ();

	private int mLvlCnt     = 1;
	private int mEvenLvl    = 0;

	public Levels (int groupCnt)
	{
		addLevel (mLvlCnt, groupCnt);
		initialization (groupCnt);
	}


	private void initialization (int groupCnt)
	{
		int returnNumber = groupCnt;

		mLvlCnt++;

		if (isEvenNumber (groupCnt))
		{
			returnNumber = groupCnt / 2;
			addLevel (mLvlCnt, returnNumber);
		}
		else
		{
			if (returnNumber == 1)
			{
				if (mEvenLvl != 0)
				{
					addLevel (mLvlCnt, mEvenLvl);
					return;
				}

				return;
			}
			else
			{
				returnNumber = groupCnt / 2;

				if (mEvenLvl != 0)
					returnNumber += mEvenLvl;

				addLevel (mLvlCnt, returnNumber);

				returnNumber -= mEvenLvl;
				mEvenLvl = 0;

				mEvenLvl++;
			}
		}

		if (returnNumber != 0 & returnNumber > 0)
			initialization (returnNumber);
	}


	private void addLevel (int lvlNumber, int groupCnt)
	{
		mGroupCntOnLvls.put (lvlNumber, groupCnt);
	}


	private boolean isEvenNumber (int number) // Even - true, Odd - false
	{
		return (number & 1) == 0;
	}
}
