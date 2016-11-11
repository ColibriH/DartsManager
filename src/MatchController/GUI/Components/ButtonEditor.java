package MatchController.GUI.Components;

import Constants.Constats;
import MatchController.GUI.PlayersRegistration.PlayersRegistration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by vladislavs on 12.09.2016..
 */

// TODO Refactor

public class ButtonEditor extends DefaultCellEditor
{
	private JButton     button;
	private String      label;
	private boolean     isPushed;

	private PlayersRegistration mPlayersRegistration;

	public ButtonEditor (PlayersRegistration playersRegistration, JCheckBox checkBox)
	{
		super (checkBox);

		mPlayersRegistration = playersRegistration;

		button = new JButton ();
		button.setOpaque (true);
		button.addActionListener (new ActionListener ()
		{
			public void actionPerformed (ActionEvent e)
			{
				fireEditingStopped ();
			}
		});
	}


	public Component getTableCellEditorComponent (JTable table, Object value,
	                                              boolean isSelected, int row, int column)
	{
		label = (value == null) ? "" : value.toString ();
		isPushed = true;

		return button;
	}


	public Object getCellEditorValue ()
	{
		if (isPushed)
		{
			if (label.equals (Constats.DELETE_BTN_ID))
				mPlayersRegistration.deleteNewPlayerFromTable ();
			else if (label.equals (Constats.EDIT_BTN_ID))
				mPlayersRegistration.editNewPlayerInTable ();
		}

		isPushed = false;

		return new String (label);
	}


	public boolean stopCellEditing ()
	{
		isPushed = false;
		return super.stopCellEditing ();
	}


	protected void fireEditingStopped ()
	{
		try
		{
			super.fireEditingStopped ();
		}
		catch (Exception e)
		{
			//SBE
		}
	}
}
