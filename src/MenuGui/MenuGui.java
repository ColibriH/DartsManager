package MenuGui;

import MatchController.MatchController;
import MatchController.Constats;
import Tools.ImageLoader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by vladislavs on 23.09.2016..
 */

// TODO refactor

public class MenuGui
{
	private final int MAIN_WIDTH  = 600;
	private final int MAIN_HEIGHT = 800;

	private JFrame          mJFrame;
	private JPanel          mJPanel;
	private JPanel          mCtrBtnPanel;
	private JPanel          mBackGroundPanel;
	private JLayeredPane    mLayeredPane;

	private MenuOptionComponent         tournamentButton;
	private MenuOptionComponent         exitButton;
	private MenuOptionComponent         optionsButton;
	private MenuOptionComponent         eachVsEachButton;

	private JLabel          mBackGroundImg;


	public MenuGui ()
	{
		variableInitialization ();
		formComponentsModifications ();
		frameInitialization ();
	}


	private void variableInitialization ()
	{
		mJFrame             = new JFrame ();

		mJPanel             = new JPanel ();
		mLayeredPane        = new JLayeredPane ();
		mBackGroundPanel    = new JPanel ();
		mCtrBtnPanel        = new JPanel ();

		tournamentButton    = new MenuOptionComponent (250, 50, new JButton ());
		exitButton          = new MenuOptionComponent (250, 50, new JButton ());
		optionsButton       = new MenuOptionComponent (250, 50, new JButton ());
		eachVsEachButton    = new MenuOptionComponent (250, 50, new JButton ());

		mBackGroundImg      = new JLabel ();
	}


	private void formComponentsModifications ()
	{
		addComponentsOptions ();
		addComponentsListeners ();
	}


	private void addComponentsListeners ()
	{
		exitButton.getButton ().addActionListener (new ActionListener ()
		{
			@Override
			public void actionPerformed (ActionEvent e)
			{
				System.exit (1);
			}
		});

		tournamentButton.getButton ().addActionListener (new ActionListener ()
		{
			@Override
			public void actionPerformed (ActionEvent e)
			{
				new MatchController ();
				destroy ();
			}
		});
	}


	private void addComponentsOptions ()
	{
		mJPanel.setLayout (new BorderLayout ());
		mJPanel.setPreferredSize (new Dimension (MAIN_WIDTH - 10, MAIN_HEIGHT - 10));

		mLayeredPane.setPreferredSize (new Dimension (MAIN_WIDTH, MAIN_HEIGHT));
		mLayeredPane.setOpaque (true);
		mJPanel.add (mLayeredPane, BorderLayout.CENTER);

		mBackGroundPanel.setLayout (new BorderLayout ());
		mBackGroundPanel.setBounds (0, 0, MAIN_WIDTH, MAIN_HEIGHT);
		mBackGroundPanel.setBackground (Color.ORANGE);
		mBackGroundImg.setIcon (new ImageIcon (ImageLoader.getImage (Constats.MENU_BG_PIC)));
		mBackGroundPanel.add (mBackGroundImg);
		mLayeredPane.add (mBackGroundPanel, 1);

		mCtrBtnPanel.setLayout (new GridBagLayout ());

		GridBagConstraints gridBagConstraints = new GridBagConstraints ();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

		mCtrBtnPanel.setBackground (new Color (255, 255, 0, 0));

		Point mBackGroundPanelStartPoint = getBackGroundPanelStartPoint ();
		mCtrBtnPanel.setBounds (mBackGroundPanelStartPoint.x, mBackGroundPanelStartPoint.y, 270, 325);

		tournamentButton.setPreferredSize (new Dimension (250, 50));
	//	tournamentButton.setText ("tournamentButton");
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets  = new Insets (5, 0, 5, 0);
		mCtrBtnPanel.add (tournamentButton, gridBagConstraints);

		exitButton.setPreferredSize (new Dimension (250, 50));
	//	exitButton.setText ("exitButton");
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets  = new Insets (5, 0, 5, 0);
		mCtrBtnPanel.add (exitButton, gridBagConstraints);

		optionsButton.setPreferredSize (new Dimension (250, 50));
		//optionsButton.setText ("optionsButton");
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets  = new Insets (5, 0, 5, 0);
		mCtrBtnPanel.add (optionsButton, gridBagConstraints);

		eachVsEachButton.setPreferredSize (new Dimension (250, 50));
		//eachVsEachButton.setText ("eachVsEachButton");
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.insets  = new Insets (5, 0, 5, 0);
		mCtrBtnPanel.add (eachVsEachButton, gridBagConstraints);

		mLayeredPane.add (mCtrBtnPanel, 0);
	}


	private void frameInitialization ()
	{
		mJFrame.setContentPane (mJPanel);
		mJFrame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
		mJFrame.pack ();
		mJFrame.setResizable (false);
		mJFrame.setVisible (true);

		setMJFrameLocation ();
	}


	private void componentsInicialization ()
	{

	}


	private void setStyle ()
	{
		createLayerPane ();
	}


	private void createLayerPane ()
	{
		JLayeredPane layeredPane = new JLayeredPane ();
		layeredPane.setOpaque (true);
		layeredPane.setBackground (Color.RED);
		layeredPane.setPreferredSize(new Dimension (MAIN_WIDTH, MAIN_HEIGHT));
		layeredPane.setBorder(BorderFactory.createLineBorder(Color.green));


		mJPanel.add (layeredPane, BorderLayout.CENTER);
	}


	private void destroy ()
	{
		mJFrame.dispose ();
	}


	private void setMJFrameLocation ()
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mJFrame.setLocation (dim.width / 2 - mJFrame.getSize ().width / 2, 0);
	}


	public Point getBackGroundPanelStartPoint ()
	{
		return new Point (mBackGroundPanel.getWidth () / 2 - 130, mBackGroundPanel.getHeight () / 2 - 120);  // Magic numbers additional based on bg // TODO how to make auto?
	}
}
