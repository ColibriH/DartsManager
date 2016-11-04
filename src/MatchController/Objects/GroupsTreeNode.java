package MatchController.Objects;

import MatchController.GUI.Components.DisplayGroupPanel;

import java.util.ArrayList;

/**
 * Created by vladislavs on 03.11.2016..
 */
public class GroupsTreeNode
{
	private ArrayList <GroupsTreeNode> mChildrens;
	private ArrayList <PlayerObject> mPlayerObjects;
	private DisplayGroupPanel mDisplayGroupPanel;
	private GroupsTreeNode mParent;

	public GroupsTreeNode (DisplayGroupPanel group)
	{
		mPlayerObjects  = new ArrayList <> ();
		mChildrens      = new ArrayList <> ();
		mDisplayGroupPanel = group;
	}


	public GroupsTreeNode (DisplayGroupPanel group, ArrayList <PlayerObject> playersInGroup)
	{
		this (group);
		mPlayerObjects = new ArrayList <> (playersInGroup);
	}


	public void addChildren (GroupsTreeNode children)
	{
		mChildrens.add (children);
	}


	public void setParent (GroupsTreeNode parent)
	{
		mParent = parent;
	}


	public DisplayGroupPanel getDisplayGroupPanel ()
	{
		return mDisplayGroupPanel;
	}


	public GroupsTreeNode getParent ()
	{
		return mParent;
	}


	public ArrayList <GroupsTreeNode> getChildrens ()
	{
		return mChildrens;
	}


	public ArrayList <PlayerObject> getPlayerObjects ()
	{
		return mPlayerObjects;
	}


	public void setPlayerObjects (ArrayList <PlayerObject> mPlayerObjects)
	{
		this.mPlayerObjects = mPlayerObjects;
	}
}
