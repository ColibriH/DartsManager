package GroupsController;

import MatchController.GUI.Components.DisplayGroupPanel;
import MatchController.Objects.GroupsTreeNode;
import MatchController.Objects.Stages;
import MatchController.Objects.PlayerObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vladislavs on 03.11.2016..
 */

//TODO Refactor

public class GroupsController
{
	private HashMap<Integer, ArrayList<GroupsTreeNode>> mMatchGroups;
	private int mCurrentStage;
	private int mCurrentPlayingGroup;

	public GroupsController (HashMap <Integer, ArrayList <PlayerObject>> generatedMathGroups)
	{
		mMatchGroups = new HashMap <> ();
		initializeGroups (generatedMathGroups);
	}


	private void initializeGroups (HashMap <Integer, ArrayList <PlayerObject>> generatedMathGroups)
	{
		Stages stages = new Stages (generatedMathGroups.size ());

		int rowGap = 1, firstElementPosition = 0;
		for (int i = 0; i < stages.mGroupCountOnStages.size (); i++)
		{
			firstElementPosition = (i == 0) ? 0 : rowGap - 1;
			rowGap *= 2;

			addStageGroupToGroupHashMap (rowGap, firstElementPosition, i, stages.mGroupCountOnStages.get (i), generatedMathGroups);
		}

		createGroupsTree ();
	}


	private void addStageGroupToGroupHashMap (int rowGap, int firstElementPosition, int stageSequenceNumber, int groupCount, HashMap <Integer, ArrayList <PlayerObject>> generatedMathGroups)
	{
		for (int j = 0; j < groupCount; j++)      // Fill 1st lvl groups
		{
			int     row         = (j * rowGap) + firstElementPosition;
			double  weightX     = (j == 0) ? 1 : 0;
			double  weightY     = (j == generatedMathGroups.size () - 1) ? 1 : 0;

			GroupsTreeNode groupNode;
			if (stageSequenceNumber == 0)
			{
				ArrayList <PlayerObject> playersInGroup = generatedMathGroups.get (j);      // Get (i) - Get players in group from generatedMathGroups
				groupNode = new GroupsTreeNode (new DisplayGroupPanel (playersInGroup, row, stageSequenceNumber, weightX, weightY), playersInGroup);
				linkPanelsWithPlayerObject (playersInGroup, groupNode.getDisplayGroupPanel ());
			}
			else
			{
				weightX = 0;
				weightY = 0.5;
				groupNode = new GroupsTreeNode (new DisplayGroupPanel (row, stageSequenceNumber, weightX, weightY));
			}

			if (mMatchGroups.get (stageSequenceNumber) == null)       // TODO get (stageSequenceNumber) as sep. variable?
				mMatchGroups.put (stageSequenceNumber, new ArrayList <GroupsTreeNode> ()
				{{
					add (groupNode);
				}});
			else
				mMatchGroups.get (stageSequenceNumber).add (groupNode);
		}
	}


	private void linkPanelsWithPlayerObject (ArrayList <PlayerObject> playersInGroup, DisplayGroupPanel displayGroupPanel)
	{
		for (PlayerObject player : playersInGroup)
			player.mDisplayGroupPanel = displayGroupPanel;
	}


	private void createGroupsTree ()
	{
		for (Integer i = mMatchGroups.size (); i > 1; i--)
		{
			ArrayList groups = mMatchGroups.get (i);
			for (int j = 0; j < groups.size (); j++)
				addChildren (mMatchGroups.get (i-1), j * 2, (GroupsTreeNode) groups.get (j), i);
		}
	}


	private void addChildren (ArrayList groups, int spos, GroupsTreeNode parent, int key)
	{
		GroupsTreeNode node = (GroupsTreeNode) groups.get (spos);
		GroupsTreeNode node2;

		if (groups.size () > spos + 1)
			node2 = (GroupsTreeNode) groups.get (spos + 1);
		else
			node2 = findNode (key);

		parent.addChildren (node);
		parent.addChildren (node2);

		node.setParent (parent);
		node2.setParent (parent);
	}


	private GroupsTreeNode findNode (Integer key)
	{
		for (Integer i = mMatchGroups.size (); i > 0; i--)
		{
			if (i < key - 1)
			{
				ArrayList valueArr = mMatchGroups.get (i);
				if (valueArr.size () % 2 != 0)
					return (GroupsTreeNode)valueArr.get (valueArr.size () - 1);
			}
		}

		return null;
	}


	public HashMap <Integer, ArrayList <GroupsTreeNode>> getMatchGroups ()
	{
		return mMatchGroups;
	}
}
