package MatchController.GUI;

import MatchController.Constats;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by vladislavs on 12.09.2016..
 */
public class ButtonEditor extends DefaultCellEditor
{
	private JButton     button;
	private String      label;
	private boolean     isPushed;

	private GameManagerGuiForm mGameManagerGuiForm;

	public ButtonEditor (GameManagerGuiForm gameManagerGuiForm, JCheckBox checkBox)
	{
		super (checkBox);

		mGameManagerGuiForm = gameManagerGuiForm;

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
				mGameManagerGuiForm.deleteNewPlayerFromTable ();
			else if (label.equals (Constats.EDIT_BTN_ID))
				mGameManagerGuiForm.editNewPlayerInTable ();
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
