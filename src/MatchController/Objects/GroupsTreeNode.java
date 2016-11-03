package MatchController.Objects;

import MatchController.GUI.Components.DisplayGroupPanel;

import java.util.ArrayList;

/**
 * Created by vladislavs on 03.11.2016..
 */
public class GroupsTreeNode
{
	private ArrayList <GroupsTreeNode> mChildrens;
	private DisplayGroupPanel mDisplayGroupPanel;
	private GroupsTreeNode mParent;

	public GroupsTreeNode (DisplayGroupPanel group)
	{
		mChildrens = new ArrayList <> ();
		mDisplayGroupPanel = group;
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
}
