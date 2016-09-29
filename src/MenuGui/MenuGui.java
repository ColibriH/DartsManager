package MenuGui;

import MatchController.MatchController;
import MatchController.Constats;
import Tools.FontLoader;
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

	private JButton         tournamentButton;
	private JButton         exitButton;
	private JButton         optionsButton;
	private JButton         eachVsEachButton;

	private JLabel          mBackGroundImg;


	public MenuGui ()
	{
		FontLoader.loadFont ();

		variableInitialization ();
		frameInitialization ();
	}


	private void variableInitialization ()
	{
		mJFrame             = new JFrame ();
		mJPanel             = new JPanel ()
		{
			Image bg = ImageLoader.getImage (Constats.MENU_BG_PIC);

			@Override
			protected void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.drawImage(bg, 0, 0, bg.getWidth(null), bg.getHeight(null), this);
			}
		};

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
		GridBagConstraints gridBagConstraints = new GridBagConstraints ();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.insets = new Insets (90, 10, 0 ,0);

		mJPanel.setLayout (new GridBagLayout ());
		mJPanel.setPreferredSize (new Dimension (MAIN_WIDTH - 10, MAIN_HEIGHT - 10));
		mJPanel.add (mCtrBtnPanel, gridBagConstraints);

		mCtrBtnPanel.setBackground (new Color (255, 255, 0, 0));
		mCtrBtnPanel.setLayout (new GridBagLayout ());
		mCtrBtnPanel.setOpaque (false);
		mCtrBtnPanel.setPreferredSize (new Dimension (270, 325));

		tournamentButton.setPreferredSize (new Dimension (250, 50));
		tournamentButton.setText ("Tournament");
		//tournamentButton.setFont (FontLoader.ge.getAllFonts ()[1]);
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets  = new Insets (5, 0, 5, 0);

		tournamentButton.setBackground (new Color (255, 255, 0, 0));
		tournamentButton.setHorizontalTextPosition (SwingConstants.CENTER);
		tournamentButton.setOpaque (false);
		tournamentButton.setBorder (new EmptyBorder (0, 0, 0, 0));
		tournamentButton.setBorderPainted (false);
		tournamentButton.setContentAreaFilled (false);
		tournamentButton.setFocusPainted (false);
		tournamentButton.setIcon (new ImageIcon (ImageLoader.getImage (Constats.BTN_TEXTURE_PIC)));
		tournamentButton.setRolloverIcon (new ImageIcon (ImageLoader.getImage (Constats.BTN_FILLER_PIC)));

		mCtrBtnPanel.add (tournamentButton, gridBagConstraints);

		exitButton.setPreferredSize (new Dimension (250, 50));
		exitButton.setText ("Exit");
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets  = new Insets (5, 0, 5, 0);

		exitButton.setBackground (new Color (255, 255, 0, 0));
		exitButton.setHorizontalTextPosition (SwingConstants.CENTER);
		exitButton.setOpaque (false);
		exitButton.setBorder (new EmptyBorder (0, 0, 0, 0));
		exitButton.setBorderPainted (false);
		exitButton.setContentAreaFilled (false);
		exitButton.setIcon (new ImageIcon (ImageLoader.getImage (Constats.BTN_TEXTURE_PIC)));
		exitButton.setRolloverIcon (new ImageIcon (ImageLoader.getImage (Constats.BTN_FILLER_PIC)));

		mCtrBtnPanel.add (exitButton, gridBagConstraints);

		optionsButton.setPreferredSize (new Dimension (250, 50));
		optionsButton.setText ("Settings");
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets  = new Insets (5, 0, 5, 0);

		optionsButton.setBackground (new Color (255, 255, 0, 0));
		optionsButton.setHorizontalTextPosition (SwingConstants.CENTER);
		optionsButton.setOpaque (true);
		optionsButton.setBorder (new EmptyBorder (0, 0, 0, 0));
		optionsButton.setBorderPainted (false);
		optionsButton.setContentAreaFilled (false);
		optionsButton.setIcon (new ImageIcon (ImageLoader.getImage (Constats.BTN_TEXTURE_PIC)));
		optionsButton.setRolloverIcon (new ImageIcon (ImageLoader.getImage (Constats.BTN_FILLER_PIC)));

		mCtrBtnPanel.add (optionsButton, gridBagConstraints);

		eachVsEachButton.setPreferredSize (new Dimension (250, 50));
		eachVsEachButton.setHorizontalTextPosition (SwingConstants.CENTER);
		eachVsEachButton.setText ("Each Vs Each");
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.insets  = new Insets (5, 0, 5, 0);

		eachVsEachButton.setBackground (new Color (255, 255, 0, 0));
		eachVsEachButton.setOpaque (false);
		eachVsEachButton.setBorder (new EmptyBorder (0, 0, 0, 0));
		eachVsEachButton.setBorderPainted (false);
		eachVsEachButton.setContentAreaFilled (false);
		eachVsEachButton.setIcon (new ImageIcon (ImageLoader.getImage (Constats.BTN_TEXTURE_PIC)));
		eachVsEachButton.setRolloverIcon (new ImageIcon (ImageLoader.getImage (Constats.BTN_FILLER_PIC)));

		mCtrBtnPanel.add (eachVsEachButton, gridBagConstraints);
	}


	private void frameInitialization ()
	{
		mJFrame.setContentPane (mJPanel);
		mJFrame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);

		formComponentsModifications ();

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
		return new Point (MAIN_WIDTH / 2 - 135, MAIN_HEIGHT / 2 - 120);  // Magic numbers additional based on bg // TODO how to make auto?
	}
}
