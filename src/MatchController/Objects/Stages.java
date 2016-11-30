package MatchController.Objects;

import java.util.HashMap;

public class Stages
{
	private HashMap <Integer, Integer>  mGroupCountOnStages  = new HashMap <> ();
	private int                         mStageCount          = 0;
	private int                         mEvenLvl             = 0;


	public Stages (int groupCount)
	{
		addStage (mStageCount, groupCount);
		initialization (groupCount);
	}


	// TODO Refactor
	private void initialization (int groupCnt)
	{
		int returnNumber = groupCnt;

		mStageCount++;

		if (isEvenNumber (groupCnt))
		{
			returnNumber = groupCnt / 2;
			addStage (mStageCount, returnNumber);
		}
		else
		{
			if (returnNumber == 1)
			{
				if (mEvenLvl != 0)
				{
					addStage (mStageCount, mEvenLvl);
					return;
				}

				return;
			}
			else
			{
				returnNumber = groupCnt / 2;

				if (mEvenLvl != 0)
					returnNumber += mEvenLvl;

				addStage (mStageCount, returnNumber);

				returnNumber -= mEvenLvl;
				mEvenLvl = 0;

				mEvenLvl++;
			}
		}

		if (returnNumber != 0 & returnNumber > 0)
			initialization (returnNumber);
	}


	private void addStage (int lvlNumber, int groupCnt)
	{
		mGroupCountOnStages.put (lvlNumber, groupCnt);
	}


	private boolean isEvenNumber (int number) // Even - true, Odd - false
	{
		return (number & 1) == 0;
	}


	public HashMap<Integer, Integer> getGroupCountOnStages ()
	{
		return mGroupCountOnStages;
	}
}
