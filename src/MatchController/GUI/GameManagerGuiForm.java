package MatchController.GUI;

import MatchController.MatchController;
import MatchController.Constats;
import Tools.ImageLoader;
import Tools.ImageViewport;
import com.sun.prism.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

// TODO Refactor: check all syntax
// TODO Refactor: replace all not unappropriated logic from this class

// TODO Create check on name twin (name should be UNIQUE)

/**
 * Created by vladislavs on 06.09.2016..
 */
public class GameManagerGuiForm
{
	private final MatchController mMatchController;

	private JFrame            mJFrame;
	private JTable            mNewPlayerTable;
	private JTextField        mNewPlayerNameTxtField;
	private JButton           mNewPlayerAddBtn;
	private JButton           mMatchStartBtn;
	private JLabel            mNameLabel;
	private JPanel            mJPanel;
	private JPanel            mInnerJPanel;
	private JPanel            mTablePanel;
	private JScrollPane       mNewPlayerJScrollPane;

	private Image             mBackGroundImage;

	private Object []         mNewPlayerTableHeaders  = {"Id", "Name", Constats.DELETE_BTN_ID, Constats.EDIT_BTN_ID};
	private Object [][]       mNewPlayerTableData;
	private DefaultTableModel mDefaultTableModel;

	private final String      mNewPlayerNameTxtFieldDefaultValue = mNewPlayerNameTxtField.getText ();   // Start value
																										// described in
																										// .form file

	public GameManagerGuiForm (MatchController matchController)
	{
		mMatchController = matchController;

		mJFrame = new JFrame ("GameManagerGuiForm");
		mJFrame.setContentPane (mJPanel);
		mJFrame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
		mJFrame.pack ();
		mJFrame.setVisible (true);

		formComponentsModifications ();
		newPlayerTableInitialization ();

		mJPanel.requestFocus ();        // Needed!
		mNewPlayerTable.addMouseMotionListener (new MouseMotionAdapter ()
		{
			@Override
			public void mouseMoved (MouseEvent e)
			{
				int columnIndex = mNewPlayerTable.columnAtPoint (e.getPoint());
				if (isTableInnerButton (columnIndex, false))
					mNewPlayerTable.setCursor (new Cursor (Cursor.HAND_CURSOR));
				else
					mNewPlayerTable.setCursor (new Cursor (Cursor.DEFAULT_CURSOR));
			}
		});
	}


	private void addNewPlayer (String Name)
	{
		// Players Id`s are connected with data model size (to get last id we need to get data model size)
		int lastInsertedId = mDefaultTableModel.getRowCount ();

		mDefaultTableModel.addRow (new String [] {String.valueOf (lastInsertedId), Name,
				Constats.DELETE_BTN_ID, Constats.EDIT_BTN_ID});
	}


	private void formComponentsModifications ()
	{
		// Styling of components
		// Components options modifications
		// =====================================================================
		mJFrame.setResizable (false);
		setTableStyle ();


		// Components listener initialization
		// =====================================================================
		mNewPlayerNameTxtField.addFocusListener (new FocusAdapter ()
		{
			@Override
			public void focusGained (FocusEvent e)
			{
				// TODO realization to separate function

				String currentTxtFieldValue = mNewPlayerNameTxtField.getText ();

				if(currentTxtFieldValue.equals (mNewPlayerNameTxtFieldDefaultValue))
					mNewPlayerNameTxtField.setText ("");
			}


			@Override
			public void focusLost (FocusEvent e)
			{
				// TODO realization to separate function

				if (mNewPlayerNameTxtField.getText ().length () == 0)
					mNewPlayerNameTxtField.setText (mNewPlayerNameTxtFieldDefaultValue);
			}
		});

		mMatchStartBtn.addActionListener (new ActionListener ()
		{
			@Override
			public void actionPerformed (ActionEvent e)
			{
				// TODO realization to separate function

				ArrayList <Vector> newPlayerList = new ArrayList <Vector> (mDefaultTableModel.getDataVector ());
				mMatchController.setPlayerList (newPlayerList);
				mMatchController.runActionsAfterPlayerRegistration ();
			}
		});

		mNewPlayerAddBtn.addActionListener (new ActionListener ()
		{
			@Override
			public void actionPerformed (ActionEvent e)
			{
				addNewPlayer ();
			}
		});

		mNewPlayerNameTxtField.addKeyListener (new KeyAdapter ()
		{
			@Override
			public void keyPressed (KeyEvent e)
			{
				if (e.getKeyCode () == KeyEvent.VK_ENTER)
				{
					addNewPlayer ();
					mNewPlayerNameTxtField.setText ("");
				}
			}
		});
	}


	private void setTableStyle ()
	{
		mNewPlayerTable.setForeground(Color.BLACK);
		mNewPlayerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		mNewPlayerTable.setOpaque(false);
		mNewPlayerTable.setBackground(new Color(255, 255, 255, 158));

		mBackGroundImage = ImageLoader.getImage (Constats.TABLE_PIC);

		mNewPlayerJScrollPane.setViewport(new ImageViewport (mBackGroundImage));
		mNewPlayerJScrollPane.setViewportView(mNewPlayerTable);
	}


	private void addNewPlayer ()
	{
		String playerName = mNewPlayerNameTxtField.getText ();
		if (mNewPlayerNameTxtFieldDefaultValue.equals (playerName) || playerName.isEmpty ())
			return;

		addNewPlayer (playerName);
		mNewPlayerNameTxtField.setText (mNewPlayerNameTxtFieldDefaultValue);
	}


	private boolean isTableInnerButton (int column, boolean hackFlag)
	{
		int hackInt = 0;
		if (hackFlag)
			hackInt = 1;

		//TODO resolve issue based on hidden column
		return mNewPlayerTable.getColumnName (column - hackInt).equals (Constats.DELETE_BTN_ID) ||
		       mNewPlayerTable.getColumnName (column - hackInt).equals (Constats.EDIT_BTN_ID);
	}


	private void newPlayerTableInitialization ()
	{
		// Data initialization
		// =====================================================================
		mDefaultTableModel = new DefaultTableModel ()
		{
			public boolean isCellEditable (int row, int column)
			{
				if (isTableInnerButton (column, true))
					return true;

				return false;
			}
		};

		mDefaultTableModel.setDataVector(mNewPlayerTableData, mNewPlayerTableHeaders);
		mNewPlayerTable.setModel (mDefaultTableModel);

		mNewPlayerTable.getColumn (Constats.DELETE_BTN_ID).setCellRenderer (new ButtonRenderer ());
		mNewPlayerTable.getColumn (Constats.EDIT_BTN_ID  ).setCellRenderer (new ButtonRenderer ());

		mNewPlayerTable.getColumn (Constats.DELETE_BTN_ID).setCellEditor (new ButtonEditor (this, new JCheckBox()));
		mNewPlayerTable.getColumn (Constats.EDIT_BTN_ID  ).setCellEditor (new ButtonEditor (this, new JCheckBox()));


		mNewPlayerTable.removeColumn (mNewPlayerTable.getColumn ("Id"));    // To hide Id Column

		mNewPlayerTable.getColumnModel().getColumn(0).setPreferredWidth(mNewPlayerJScrollPane.getWidth () - 103);
		mNewPlayerTable.getColumnModel().getColumn(1).setPreferredWidth(50);
		mNewPlayerTable.getColumnModel().getColumn(2).setPreferredWidth(50);

		mNewPlayerTable.getTableHeader().setReorderingAllowed(false);
		mNewPlayerTable.getTableHeader().setResizingAllowed (false);

		// Listener and event`s initialization
		// =====================================================================
		mNewPlayerTable.addKeyListener (new KeyAdapter ()
		{
			@Override
			public void keyPressed (KeyEvent e)
			{
				if (e.getKeyCode () == KeyEvent.VK_DELETE)
					deleteNewPlayerFromTable ();
			}
		});
	}


	private String getEditedText (String oldTxt)
	{
		JFrame frame = new JFrame();
		String result = JOptionPane.showInputDialog(frame, "Enter new name:");

		return  (result.isEmpty ()) ? oldTxt : result;
	}


	protected void editNewPlayerInTable ()
	{
		int selectedRow     = mNewPlayerTable.getSelectedRow ();
		int selectedColumn  = mNewPlayerTable.getColumn ("Name").getModelIndex ();

		if (mDefaultTableModel.getRowCount () == 0)
			return;

		if (selectedRow == -1)
		{
			JOptionPane.showMessageDialog (null, "Please select player in the table to Edit.");
			return;
		}

		String currentCellValue = (String) mDefaultTableModel.getValueAt (selectedRow, selectedColumn);
		mDefaultTableModel.setValueAt (getEditedText (currentCellValue), selectedRow, selectedColumn);
	}


	protected void deleteNewPlayerFromTable ()
	{
		int selectedRow = mNewPlayerTable.getSelectedRow ();

		if (mDefaultTableModel.getRowCount () == 0)
			return;

		if (selectedRow == -1)
		{
			JOptionPane.showMessageDialog (null, "Please select player in the table to delete.");
			return;
		}

		if (isDeleteConfirmed ())
			mDefaultTableModel.removeRow (selectedRow);
	}


	private boolean isDeleteConfirmed ()
	{
		int result = JOptionPane.showConfirmDialog (null, "Are you sure, you want to delete player?",
		                              "alert", JOptionPane.OK_CANCEL_OPTION);
		return result == 0;
	}


	public void setVisibility (boolean visibilityFlag)
	{
		mJFrame.setVisible (visibilityFlag);
	}
}
