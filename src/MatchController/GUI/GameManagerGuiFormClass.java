package MatchController.GUI;

import MatchController.MatchController;
import MatchController.Constats;
import MatchController.Objects.PlayerObject;
import MenuGui.ImagedPanel;
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
public class GameManagerGuiFormClass
{
	private final String            COLUMN_ID      = "Id";
	private final String            COLUMN_NAME    = "Name";

	private final MatchController mMatchController;

	private JFrame mJFrame;

	private ImagedPanel             mJPanel;
	private JPanel                  mTableJPanel;
	private JPanel                  mControlJPanel;

	private JTable                  mPlayerTable;
	private JTextField              mPlayerNameTxtField;
	private JButton                 mPlayerAddBtn;
	private JButton                 mMatchStartBtn;
	private JLabel                  mNameLabel;

	private JScrollPane             mTableJScrollPane;
	private JTextField              mPlayersInGroupTxtField;
	private JButton                 mBackButton;
	private Image                   mBackGroundImage;

	private String                  mPlayerNameTxtFieldDefaultValue;
	private String []               mPlayerTableHeaders;
	private Object [][]             mPlayerTableData;
	private DefaultTableModel       mDefaultTableModel;


	public GameManagerGuiFormClass (MatchController matchController)
	{
		mMatchController = matchController;

		componentInitialization ();
		componentStyling ();

		initialization ();
		variableInitialization ();
		formComponentsModifications ();
		newPlayerTableInitialization ();
	}


	private void componentStyling ()
	{
		mJFrame.setPreferredSize ();
	}


	private void componentInitialization ()
	{
		// TODO add image
		//mBackGroundImage

		mJFrame                 = new JFrame ();

		mJPanel                 = new ImagedPanel (mBackGroundImage);
		mControlJPanel          = new JPanel ();
		mTableJPanel            = new JPanel ();

		mPlayerTable            = new JTable ();
		mTableJScrollPane       = new JScrollPane (mPlayerTable);

		mPlayerAddBtn           = new JButton ();
		mMatchStartBtn          = new JButton ();
		mBackButton             = new JButton ();

		mNameLabel              = new JLabel ();

		mPlayersInGroupTxtField = new JTextField ();
		mPlayerNameTxtField     = new JTextField ();
	}


	private void initialization ()
	{
		componentCreation ();

		mJFrame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
		mJFrame.pack ();
		mJFrame.setVisible (true);

		setMJFrameLocation ();
		setEntryComponentFocus ();
	}


	private void componentCreation ()
	{
		GridBagConstraints mainPanelGbc = new GridBagConstraints ();

		addMainPanel ();
		addControlPanel (mainPanelGbc);
		addTablePanel (mainPanelGbc);
	}


	//TODO refactor
	private void addTablePanel (GridBagConstraints mainPanelGbc)
	{
		mainPanelGbc.fill = GridBagConstraints.HORIZONTAL;
		mainPanelGbc.gridx = 1;
		mainPanelGbc.gridy = 0;
		mainPanelGbc.insets  = new Insets (5, 0, 5, 0);

		mTableJPanel = new JPanel ();
		mTableJPanel.setLayout (new BorderLayout ());
		mTableJScrollPane = new JScrollPane (mPlayerTable);
	}


	private void addComponentToPanel (JPanel parent, Component child, int xPos, int yPos, Insets insets, GridBagConstraints gbc)
	{
		gbc.fill    = GridBagConstraints.HORIZONTAL;
		gbc.gridx   = xPos;
		gbc.gridy   = yPos;
		gbc.insets  = insets;

		parent.add (child, gbc);
	}


	private void addControlPanel (GridBagConstraints mainPanelGbc)
	{

	}


	private void addMainPanel ()
	{
		mJPanel = new ImagedPanel ();
		mJPanel.setLayout (new GridBagLayout ());
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
		mPlayerTableHeaders = new String[] {COLUMN_ID, COLUMN_NAME, Constats.DELETE_BTN_ID, Constats.EDIT_BTN_ID};
		mPlayerNameTxtFieldDefaultValue = mPlayerNameTxtField.getText ();   // Start value described in .form file
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


	private ArrayList<PlayerObject> getPlayersFromTable ()
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
		for (String cName : mPlayerTableHeaders)
		{
			if (cName.equals (columnName))
				return columnNumber;

			columnNumber++;
		}

		return -1;
	}


	private void setTableStyle ()
	{
		mPlayerTable.setForeground(Color.BLACK);
		mPlayerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		mPlayerTable.setOpaque(false);
		mPlayerTable.setBackground(new Color(255, 255, 255, 158));

		mBackGroundImage = ImageLoader.getImage (Constats.TABLE_PIC);

		mTableJScrollPane.setViewport(new ImageViewport (mBackGroundImage));
		mTableJScrollPane.setViewportView(mPlayerTable);
	}


	private boolean isTableInnerButton (int column, boolean hackFlag)
	{
		int hackInt = 0;
		if (hackFlag)
			hackInt = 1;

		//TODO resolve issue based on hidden column
		return mPlayerTable.getColumnName (column - hackInt).equals (Constats.DELETE_BTN_ID) ||
				mPlayerTable.getColumnName (column - hackInt).equals (Constats.EDIT_BTN_ID);
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
		String playerName = mPlayerNameTxtField.getText ();
		if (mPlayerNameTxtFieldDefaultValue.equals (playerName) || playerName.isEmpty ())
			return;

		addNewPlayer (playerName);
		mPlayerNameTxtField.setText (mPlayerNameTxtFieldDefaultValue);
	}


	protected void editNewPlayerInTable ()
	{
		int selectedRow     = mPlayerTable.getSelectedRow ();
		int selectedColumn  = mPlayerTable.getColumn (COLUMN_NAME).getModelIndex ();

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
		int selectedRow = mPlayerTable.getSelectedRow ();

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

		mDefaultTableModel.setDataVector (mPlayerTableData, mPlayerTableHeaders);

		if (mMatchController.getPlayerList () != null)
			populateTableModelWithOldPlayers (mMatchController.getPlayerList ());

		mPlayerTable.setModel (mDefaultTableModel);

		mPlayerTable.getColumn (Constats.DELETE_BTN_ID).setCellRenderer (new ButtonRenderer ());
		mPlayerTable.getColumn (Constats.EDIT_BTN_ID  ).setCellRenderer (new ButtonRenderer ());

		mPlayerTable.getColumn (Constats.DELETE_BTN_ID).setCellEditor (new ButtonEditor (this, new JCheckBox ()));
		mPlayerTable.getColumn (Constats.EDIT_BTN_ID  ).setCellEditor (new ButtonEditor (this, new JCheckBox ()));

		mPlayerTable.removeColumn (mPlayerTable.getColumn ("Id"));    // To hide Id Column

		mPlayerTable.getColumnModel ().getColumn (0).setPreferredWidth (mTableJScrollPane.getWidth () - 103);
		mPlayerTable.getColumnModel ().getColumn (1).setPreferredWidth (50);
		mPlayerTable.getColumnModel ().getColumn (2).setPreferredWidth (50);

		mPlayerTable.getTableHeader ().setReorderingAllowed (false);
		mPlayerTable.getTableHeader ().setResizingAllowed (false);
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
		mPlayerNameTxtField.addFocusListener (new FocusAdapter ()
		{
			@Override
			public void focusGained (FocusEvent e)
			{
				String currentTxtFieldValue = mPlayerNameTxtField.getText ();
				if(currentTxtFieldValue.equals (mPlayerNameTxtFieldDefaultValue))
					mPlayerNameTxtField.setText ("");
			}


			@Override
			public void focusLost (FocusEvent e)
			{
				if (mPlayerNameTxtField.getText ().length () == 0)
					mPlayerNameTxtField.setText (mPlayerNameTxtFieldDefaultValue);
			}
		});

		mBackButton.addActionListener (new ActionListener ()
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

		mPlayerAddBtn.addActionListener (new ActionListener ()
		{
			@Override
			public void actionPerformed (ActionEvent e)
			{
				addNewPlayer ();
			}
		});

		mPlayerNameTxtField.addKeyListener (new KeyAdapter ()
		{
			@Override
			public void keyPressed (KeyEvent e)
			{
				if (e.getKeyCode () == KeyEvent.VK_ENTER)
				{
					addNewPlayer ();
					mPlayerNameTxtField.setText ("");
				}
			}
		});

		mPlayerTable.addMouseMotionListener (new MouseMotionAdapter ()
		{
			@Override
			public void mouseMoved (MouseEvent e)
			{
				int columnIndex = mPlayerTable.columnAtPoint (e.getPoint());
				if (isTableInnerButton (columnIndex, false))
					mPlayerTable.setCursor (new Cursor (Cursor.HAND_CURSOR));
				else
					mPlayerTable.setCursor (new Cursor (Cursor.DEFAULT_CURSOR));
			}
		});


		mPlayerTable.addKeyListener (new KeyAdapter ()
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
