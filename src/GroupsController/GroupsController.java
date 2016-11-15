package GroupsController;

import MatchController.Gui.Components.TournamentTableGroupPanel;
import MatchController.Objects.GroupsTreeNode;
import MatchController.Objects.Stages;
import MatchController.Objects.PlayerObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vladislavs on 03.11.2016..
 */

// TODO Refactor
// TODO Create Level Links in nodes to rotate stage groups without find alg.

public class GroupsController
{
	private HashMap<Integer, ArrayList<GroupsTreeNode>> mMatchGroups;
	private GroupsTreeNode mCurrentPlayingGroup;

	public GroupsController (HashMap <Integer, ArrayList <PlayerObject>> generatedMathGroups)
	{
		mMatchGroups = new HashMap <> ();
		initializeGroups (generatedMathGroups);
		setCurrentPlayingGroup ();
	}

	private void setCurrentPlayingGroup ()
	{
		mCurrentPlayingGroup = mMatchGroups.get (0).get (0);
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
		for (int j = 0; j < groupCount; j++)
		{
			int     row         = (j * rowGap) + firstElementPosition;
			double  weightX     = (j == 0) ? 1 : 0;
			double  weightY     = (j == generatedMathGroups.size () - 1) ? 1 : 0;

			final GroupsTreeNode groupNode;
			if (stageSequenceNumber == 0)
			{
				ArrayList <PlayerObject> playersInGroup = generatedMathGroups.get (j);      // Get (i) - Get players in group from generatedMathGroups
				groupNode = new GroupsTreeNode (new TournamentTableGroupPanel (playersInGroup, row, stageSequenceNumber, weightX, weightY), playersInGroup);
				linkPanelsWithPlayerObject (playersInGroup, groupNode.getDisplayGroupPanel ());
			}
			else
			{
				if (j != 0 && j != generatedMathGroups.size () - 1)
				{
					weightX = 0;
					weightY = 0.5;
				}

				groupNode = new GroupsTreeNode (new TournamentTableGroupPanel (row, stageSequenceNumber, weightX, weightY));
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


	private void linkPanelsWithPlayerObject (ArrayList <PlayerObject> playersInGroup, TournamentTableGroupPanel tournamentTableGroupPanel)
	{
		for (PlayerObject player : playersInGroup)
			player.mTournamentTableGroupPanel = tournamentTableGroupPanel;
	}


	private void createGroupsTree ()
	{
		for (Integer i = mMatchGroups.size () - 1; i > 0; i--)
		{
			ArrayList <GroupsTreeNode>  groups = mMatchGroups.get (i);
			for (int j = 0; j < groups.size (); j++)
				addChildren (mMatchGroups.get (i-1), j * 2, groups.get (j), i);
		}
	}


	private void addChildren (ArrayList <GroupsTreeNode> groups, int spos, GroupsTreeNode parent, int key)
	{
		GroupsTreeNode node = groups.get (spos);
		GroupsTreeNode node2;

		if (groups.size () > spos + 1)
			node2 = groups.get (spos + 1);
		else
			node2 = findNode (key);

		parent.addChildren (node);
		parent.addChildren (node2);

		node.setParent (parent);
		node2.setParent (parent);
	}


	private GroupsTreeNode findNode (Integer key)
	{
		for (int i = mMatchGroups.size () - 1; i >= 0; i--)
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


	private void updateDisplayPanelData (GroupsTreeNode node)
	{
		ArrayList <PlayerObject> players = node.getPlayerObjects ();
		for (PlayerObject player : players)
			node.getDisplayGroupPanel ().setPlayerName (player.mName);
	}


	public ArrayList <TournamentTableGroupPanel> getAllMatchGroupsPanels ()
	{
		ArrayList <TournamentTableGroupPanel> allMatchGroupsPanels = new ArrayList <> ();
		for (int i = 0; i < mMatchGroups.size (); i++)
		{
			ArrayList <GroupsTreeNode> nodes = mMatchGroups.get (i);
			for (int j = 0; j < nodes.size (); j++)
			{
				allMatchGroupsPanels.add (nodes.get (j).getDisplayGroupPanel ());
			}
		}

		return allMatchGroupsPanels;
	}


	public TournamentTableGroupPanel getCurrentPlayingGroupPanel ()
	{
		return mCurrentPlayingGroup.getDisplayGroupPanel ();
	}


	public GroupsTreeNode getCurrentPlayingGroup ()
	{
		return mCurrentPlayingGroup;
	}


	public HashMap <Integer, ArrayList <GroupsTreeNode>> getMatchGroups ()
	{
		return mMatchGroups;
	}


	public boolean isLastGroupPlayed ()
	{
		return mCurrentPlayingGroup.getParent () == null;
	}


	public void promoteWinningPlayerToNextStage (PlayerObject winningPlayerObject)
	{
		ArrayList <PlayerObject> players = mCurrentPlayingGroup.getPlayerObjects ();
		for (PlayerObject player : players)
		{
			if (winningPlayerObject.equals (player))
			{
				GroupsTreeNode node = mCurrentPlayingGroup.getParent ();
				if (node.getPlayerObjects () == null)
				{
					mCurrentPlayingGroup.getParent ().setPlayerObjects (new ArrayList <PlayerObject> ()
					{
						{
							add (winningPlayerObject);
						}
					});
				}
				else
				{
					node.getPlayerObjects ().add (winningPlayerObject);
				}

				updateDisplayPanelData (node);
				return;
			}
		}
	}


	public void rotateToNextGroup ()
	{
		for (int i = 0; i < mMatchGroups.size (); i++)
		{
			ArrayList <GroupsTreeNode> nodes = mMatchGroups.get (i);
			for (int j = 0; j < nodes.size (); j++)
			{
				if (nodes.get (j).equals (mCurrentPlayingGroup))
				{
					if (j + 1 > nodes.size () - 1)
						mCurrentPlayingGroup = nodes.get (0).getParent ();
					else
						mCurrentPlayingGroup = nodes.get (j + 1);

					return;
				}
			}
		}

	}
}
