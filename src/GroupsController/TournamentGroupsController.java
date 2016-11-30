package GroupsController;

import MatchController.Gui.Components.TournamentTableGroupPanel;
import MatchController.Objects.GroupsTreeNode;
import MatchController.Objects.Stages;
import MatchController.Objects.PlayerObject;

import java.util.ArrayList;
import java.util.HashMap;

// TODO Create Level Links in nodes to rotate stage groups without find loop and rething tree structure to create more simpler walk in it
public class TournamentGroupsController
{
	private HashMap <Integer, ArrayList<GroupsTreeNode>>    mMatchGroups;
	private GroupsTreeNode                                  mCurrentPlayingGroup;


	public TournamentGroupsController (HashMap <Integer, ArrayList <PlayerObject>> generatedTournamentMathGroups)
	{
		mMatchGroups = new HashMap <> ();
		initializeGroups (generatedTournamentMathGroups);
		setCurrentPlayingGroup ();
	}

	private void setCurrentPlayingGroup ()
	{
		mCurrentPlayingGroup = mMatchGroups.get (0).get (0);
	}


	private void initializeGroups (HashMap <Integer, ArrayList <PlayerObject>> generatedTournamentMathGroups)
	{
		Stages stages = new Stages (generatedTournamentMathGroups.size ());

		int rowGap = 1, firstElementPosition;
		for (int i = 0; i < stages.getGroupCountOnStages ().size (); i++)
		{
			firstElementPosition = (i == 0) ? 0 : rowGap - 1;
			rowGap *= 2;
			addStageGroupToGroupHashMap (rowGap, firstElementPosition, i, stages.getGroupCountOnStages ().get (i), generatedTournamentMathGroups);
		}

		createGroupsTree ();
	}


	private void addStageGroupToGroupHashMap (int rowGap, int firstElementPosition, int stageSequenceNumber, int groupCount, HashMap <Integer, ArrayList <PlayerObject>> generatedTournamentMathGroups)
	{
		for (int j = 0; j < groupCount; j++)
		{
			int     row         = (j * rowGap) + firstElementPosition;
			double  weightX     = (j == 0) ? 1 : 0;
			double  weightY     = (j == generatedTournamentMathGroups.size () - 1) ? 1 : 0;

			final GroupsTreeNode groupNode;
			if (stageSequenceNumber == 0)
			{
				ArrayList <PlayerObject> playersInGroup = generatedTournamentMathGroups.get (j);      // Get (i) - Get players in group from generatedMathGroups
				groupNode = new GroupsTreeNode (new TournamentTableGroupPanel (playersInGroup, row, stageSequenceNumber, weightX, weightY), playersInGroup);
				linkPanelsWithPlayerObject (playersInGroup, groupNode.getDisplayGroupPanel ());
			}
			else
			{
				if (j != 0 && j != generatedTournamentMathGroups.size () - 1)
				{
					weightX = 0;
					weightY = 0.5;
				}

				groupNode = new GroupsTreeNode (new TournamentTableGroupPanel (row, stageSequenceNumber, weightX, weightY));
			}

			if (mMatchGroups.get (stageSequenceNumber) == null)
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
		for (PlayerObject playerInGroup : playersInGroup)
			playerInGroup.setTournamentTableGroupPanel(tournamentTableGroupPanel);
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

		if (node2 != null)      // In normal flow it can`t be null
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
		ArrayList <PlayerObject> players = node.getPlayer ();
		for (PlayerObject player : players)
			node.getDisplayGroupPanel ().setPlayerName (player.getName());
	}


	public ArrayList <TournamentTableGroupPanel> getAllTournamentMatchGroupsPanels ()
	{
		ArrayList <TournamentTableGroupPanel> tournamentTableGroupPanels = new ArrayList <> ();
		for (int i = 0; i < mMatchGroups.size (); i++)
		{
			ArrayList <GroupsTreeNode> nodes = mMatchGroups.get (i);
			for (GroupsTreeNode node : nodes)
				tournamentTableGroupPanels.add (node.getDisplayGroupPanel ());
		}

		return tournamentTableGroupPanels;
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


	public void promoteWinningPlayerToNextStage (PlayerObject winningPlayer)
	{
		ArrayList <PlayerObject> players = mCurrentPlayingGroup.getPlayer ();
		for (PlayerObject player : players)
		{
			if (winningPlayer.equals (player))
			{
				GroupsTreeNode node = mCurrentPlayingGroup.getParent ();
				if (node.getPlayer () == null)
				{
					mCurrentPlayingGroup.getParent ().setPlayerObjects (new ArrayList <PlayerObject> ()
					{
						{
							add (winningPlayer);
						}
					});
				}
				else
				{
					node.getPlayer ().add (winningPlayer);
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
