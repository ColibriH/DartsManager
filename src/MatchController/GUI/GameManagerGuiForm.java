package MatchController.GUI;

import MatchController.MatchController;
import MatchController.Constats;
import MatchController.Objects.PlayerObject;
import Tools.ImageLoader;
import Tools.ImageViewport;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

// TODO Set BackGround of mPanel as dartBoard with two side windows
// TODO set Board at the middle
// TODO set font + underline
// TODO change font of table
// TODO change button style
// TODO thing on input style
// TODO Borders of table hide
// TODO Create check on name twin (name should be UNIQUE)

/**
 * Created by vladislavs on 06.09.2016..
 */
public class GameManagerGuiForm
{
	private final String            COLUMN_ID      = "Id";
	private final String            COLUMN_NAME    = "Name";

	private final MatchController   mMatchController;

	private JFrame                  mJFrame;
	private JTable                  mNewPlayerTable;
	private JTextField              mNewPlayerNameTxtField;
	private JButton                 mNewPlayerAddBtn;
	private JButton                 mMatchStartBtn;
	private JLabel                  mNameLabel;
	private JPanel                  mJPanel;
	private JPanel                  mInnerJPanel;
	private JPanel                  mTablePanel;
	private JScrollPane             mNewPlayerJScrollPane;
	private JTextField mPlayersInGroupTxtField;
	private JButton backButton;
	private Image                   mBackGroundImage;

	private String                  mNewPlayerNameTxtFieldDefaultValue;
	private String []               mNewPlayerTableHeaders;
	private Object [][]             mNewPlayerTableData;
	private DefaultTableModel       mDefaultTableModel;


	public GameManagerGuiForm (MatchController matchController)
	{
		mMatchController = matchController;

		frameInitialization ();
		variableInitialization ();
		formComponentsModifications ();
		newPlayerTableInitialization ();
	}


	private void frameInitialization ()
	{
		mJFrame = new JFrame ("GameManagerGuiForm");
		mJFrame.setContentPane (mJPanel);
		mJFrame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
		mJFrame.pack ();
		mJFrame.setVisible (true);

		setMJFrameLocation ();
		setEntryComponentFocus ();
	}


	private void setMJFrameLocation ()
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mJFrame.setLocation (dim.width / 2 - mJFrame.getSize ().width / 2, 0);
	}


	private void setEntryComponentFocus ()
	{
		mJPanel.requestFocus ();
	}


	private void variableInitialization ()
	{
		mNewPlayerTableHeaders = new String[] {COLUMN_ID, COLUMN_NAME, Constats.DELETE_BTN_ID, Constats.EDIT_BTN_ID};
		mNewPlayerNameTxtFieldDefaultValue = mNewPlayerNameTxtField.getText ();   // Start value described in .form file
	}


	private void formComponentsModifications ()
	{
		componentsStyleModifications    ();
		addComponentsListeners          ();
	}


	private void componentsStyleModifications ()
	{
		mJFrame.setResizable (false);
		setTableStyle ();
	}


	private ArrayList <PlayerObject> getPlayersFromTable ()
	{
		ArrayList <PlayerObject> returnPlayerList = new ArrayList <> ();
		Vector tableData = mDefaultTableModel.getDataVector ();

		try
		{
			for (Object rowData : tableData)
				returnPlayerList.add (getPlayerObjectNewInstance ((Vector) rowData));
		}
		catch (Exception e)
		{
			e.printStackTrace ();
		}

		return returnPlayerList;
	}


	private PlayerObject getPlayerObjectNewInstance (Vector rowData)
	{
		try
		{
			String playerName = (String) rowData.get (getColumnNumberByName (COLUMN_NAME));
			String playerIdString = (String) rowData.get (getColumnNumberByName (COLUMN_ID));
			Integer playerId = Integer.parseInt (playerIdString);

			return new PlayerObject (playerName, playerId);
		}
		catch (Exception e)
		{
			e.printStackTrace ();
		}

		return null;
	}


	private int getColumnNumberByName (String columnName)
	{
		int columnNumber = 0;
		for (String cName : mNewPlayerTableHeaders)
		{
			if (cName.equals (columnName))
				return columnNumber;

			columnNumber++;
		}

		return -1;
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


	private boolean isTableInnerButton (int column, boolean hackFlag)
	{
		int hackInt = 0;
		if (hackFlag)
			hackInt = 1;

		//TODO resolve issue based on hidden column
		return mNewPlayerTable.getColumnName (column - hackInt).equals (Constats.DELETE_BTN_ID) ||
		       mNewPlayerTable.getColumnName (column - hackInt).equals (Constats.EDIT_BTN_ID);
	}


	private String getEditedText (String oldTxt)
	{
		JFrame frame = new JFrame();
		String result = JOptionPane.showInputDialog (frame, "Enter new name:");

		return  (result.isEmpty ()) ? oldTxt : result;
	}


	private void addNewPlayer (String Name)
	{
		// Players Id`s are connected with data model size (to get last id we need to get data model size)
		int lastInsertedId = mDefaultTableModel.getRowCount ();

		mDefaultTableModel.addRow (new String [] {String.valueOf (lastInsertedId), Name,
				Constats.DELETE_BTN_ID, Constats.EDIT_BTN_ID});
	}


	private void addNewPlayer ()
	{
		String playerName = mNewPlayerNameTxtField.getText ();
		if (mNewPlayerNameTxtFieldDefaultValue.equals (playerName) || playerName.isEmpty ())
			return;

		addNewPlayer (playerName);
		mNewPlayerNameTxtField.setText (mNewPlayerNameTxtFieldDefaultValue);
	}


	protected void editNewPlayerInTable ()
	{
		int selectedRow     = mNewPlayerTable.getSelectedRow ();
		int selectedColumn  = mNewPlayerTable.getColumn (COLUMN_NAME).getModelIndex ();

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


	//TODO create separate model class
	//TODO same to table
	private void newPlayerTableInitialization ()
	{
		mDefaultTableModel = new DefaultTableModel ()
		{
			public boolean isCellEditable (int row, int column)
			{
				return isTableInnerButton (column, true);
			}
		};

		mDefaultTableModel.setDataVector (mNewPlayerTableData, mNewPlayerTableHeaders);

		if (mMatchController.getPlayerList () != null)
			populateTableModelWithOldPlayers (mMatchController.getPlayerList ());

		mNewPlayerTable.setModel (mDefaultTableModel);

		mNewPlayerTable.getColumn (Constats.DELETE_BTN_ID).setCellRenderer (new ButtonRenderer ());
		mNewPlayerTable.getColumn (Constats.EDIT_BTN_ID  ).setCellRenderer (new ButtonRenderer ());

		mNewPlayerTable.getColumn (Constats.DELETE_BTN_ID).setCellEditor (new ButtonEditor (this, new JCheckBox ()));
		mNewPlayerTable.getColumn (Constats.EDIT_BTN_ID  ).setCellEditor (new ButtonEditor (this, new JCheckBox ()));

		mNewPlayerTable.removeColumn (mNewPlayerTable.getColumn ("Id"));    // To hide Id Column

		mNewPlayerTable.getColumnModel ().getColumn (0).setPreferredWidth (mNewPlayerJScrollPane.getWidth () - 103);
		mNewPlayerTable.getColumnModel ().getColumn (1).setPreferredWidth (50);
		mNewPlayerTable.getColumnModel ().getColumn (2).setPreferredWidth (50);

		mNewPlayerTable.getTableHeader ().setReorderingAllowed (false);
		mNewPlayerTable.getTableHeader ().setResizingAllowed (false);
	}


	private void populateTableModelWithOldPlayers (ArrayList <PlayerObject> playerObjectArrayList)
	{
		for (PlayerObject playerObject : playerObjectArrayList)
			mDefaultTableModel.addRow (new String [] {String.valueOf (playerObject.mId), playerObject.mName, Constats.DELETE_BTN_ID, Constats.EDIT_BTN_ID});
	}


	private boolean isPlayersInGroupNumberCorrect ()
	{
		String digitRegEx = "[0-9]+";
		String playersInGroup = mPlayersInGroupTxtField.getText ();

		return playersInGroup.matches (digitRegEx);
	}


	private void tryToRegisterPlayers ()
	{
		ArrayList <PlayerObject> createdPlayers = getPlayersFromTable ();

		if (! isPlayersInGroupNumberCorrect ())
		{
			JOptionPane.showMessageDialog (null, "Number of players in group is incorrect.");
			return;
		}

		Integer playersNumberInGroup = Integer.parseInt (mPlayersInGroupTxtField.getText ());

		if (createdPlayers.size () < playersNumberInGroup)
		{
			JOptionPane.showMessageDialog (null, "Number of players have to be at least " + playersNumberInGroup + ".");
			return;
		}

		mMatchController.runActionsAfterPlayerRegistration (playersNumberInGroup, createdPlayers);
	}


	private void addComponentsListeners ()
	{
		mNewPlayerNameTxtField.addFocusListener (new FocusAdapter ()
		{
			@Override
			public void focusGained (FocusEvent e)
			{
				String currentTxtFieldValue = mNewPlayerNameTxtField.getText ();
				if(currentTxtFieldValue.equals (mNewPlayerNameTxtFieldDefaultValue))
					mNewPlayerNameTxtField.setText ("");
			}


			@Override
			public void focusLost (FocusEvent e)
			{
				if (mNewPlayerNameTxtField.getText ().length () == 0)
					mNewPlayerNameTxtField.setText (mNewPlayerNameTxtFieldDefaultValue);
			}
		});

		backButton.addActionListener (new ActionListener ()
		{
			@Override
			public void actionPerformed (ActionEvent e)
			{
				mMatchController.openMenuGuiForm ();
			}
		});

		mMatchStartBtn.addActionListener (new ActionListener ()
		{
			@Override
			public void actionPerformed (ActionEvent e)
			{
				tryToRegisterPlayers ();
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

	public void destroy ()
	{
		mJFrame.dispose ();
	}
}
