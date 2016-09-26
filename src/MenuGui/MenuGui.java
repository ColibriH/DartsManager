package MenuGui;

import MatchController.MatchController;
import MatchController.Constats;
import Tools.ImageLoader;

import javax.swing.*;
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

	private JButton         tournamentButton;
	private JButton         exitButton;
	private JButton         optionsButton;
	private JButton         eachVsEachButton;

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

		tournamentButton    = new JButton ();
		exitButton          = new JButton ();
		optionsButton       = new JButton ();
		eachVsEachButton    = new JButton ();

		mBackGroundImg      = new JLabel ();
	}


	private void formComponentsModifications ()
	{
		addComponentsOptions ();
		addComponentsListeners ();
	}


	private void addComponentsListeners ()
	{
		exitButton.addActionListener (new ActionListener ()
		{
			@Override
			public void actionPerformed (ActionEvent e)
			{
				System.exit (1);
			}
		});

		tournamentButton.addActionListener (new ActionListener ()
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
		mBackGroundPanel.setBounds (0, 0, MAIN_WIDTH, MAIN_HEIGHT);   // 10 - is additional size for layered pane
		mBackGroundPanel.setBackground (Color.ORANGE);
		mBackGroundImg.setIcon (new ImageIcon (ImageLoader.getImage (Constats.TEST)));
		mBackGroundPanel.add (mBackGroundImg);
		mLayeredPane.add (mBackGroundPanel, 1);

		mCtrBtnPanel.setLayout (new FlowLayout ());
		mCtrBtnPanel.setBackground (Color.green);
		Point mBackGroundPanelStartPoint = getBackGroundPanelStartPoint ();
		mCtrBtnPanel.setBounds (mBackGroundPanelStartPoint.x, mBackGroundPanelStartPoint.y, 270, 410);
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
		return new Point (mBackGroundPanel.getWidth () / 2 - 130, mBackGroundPanel.getHeight () / 2 - 205);  // Magic numbers additional based on bg // TODO how to make auto?
	}
}
