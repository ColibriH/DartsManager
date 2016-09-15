package MatchController.GUI;

import MatchController.Constats;
import MatchController.Objects.NewPlayerObject;
import Tools.ImageLoader;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by vladislavs on 13.09.2016..
 */

//TODO REFACTOR CODE
public class DisplayGroupPanel extends JPanel
{
	private JPanel mFirstPlayerPanel   = new JPanel ();
	private JPanel mSecondPlayerPanel  = new JPanel ();
	private JPanel mWinnerPanel        = new JPanel ();
	private JPanel mVersusPanel        = new JPanel ();

	private JLabel mNameLabel          = new JLabel ();
	private JLabel mSNameLabel         = new JLabel ();
	private JLabel mWinnerName         = new JLabel ("Name3");

	private JLabel vs                  = new JLabel ("VS");
	private JLabel lArrow;
	private JLabel rArrow;


	public DisplayGroupPanel (String[] playersIds, ArrayList<NewPlayerObject> mPlayerList)
	{
		initialization (mPlayerList.get(Integer.parseInt (playersIds[0])).mName, mPlayerList.get(Integer.parseInt (playersIds[1])).mName);
	}


	private void initialization (String fPlayer, String sPlayer)
	{

		// TODO refactor multiple players

		panelStyling ();

		mNameLabel.setText (fPlayer);
		mSNameLabel.setText (sPlayer);

		labelStyling ();

		mVersusPanel.setLayout (new GridLayout (1,3, -1, -1));
		mFirstPlayerPanel.setLayout (new GridLayout (1,2, -1, -1));
		mSecondPlayerPanel.setLayout (new GridLayout (1,2, -1, -1));
		mWinnerPanel.setLayout (new GridLayout (1,2, -1, -1));

		mVersusPanel.add (lArrow);
		mVersusPanel.add (vs);
		mVersusPanel.add (rArrow);

		mFirstPlayerPanel.add (mNameLabel);

		mSecondPlayerPanel.add (mSNameLabel);

		mWinnerPanel.add (new Label ("Winner:"));
		mWinnerPanel.add (mWinnerName);

		mWinnerPanel.setVisible (false);

		GroupLayout groupLayout = new GroupLayout (this);
		this.setLayout (groupLayout);

		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);

		groupLayout.setHorizontalGroup(
				groupLayout.createSequentialGroup()
						.addComponent(mFirstPlayerPanel)
						.addGroup (groupLayout.createParallelGroup (GroupLayout.Alignment.LEADING)
								           .addComponent(mVersusPanel)
								           .addComponent(mWinnerPanel))
						.addComponent(mSecondPlayerPanel)

		);
		groupLayout.setVerticalGroup(
				groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								          .addComponent(mFirstPlayerPanel)
								          .addComponent(mVersusPanel)
								          .addComponent(mSecondPlayerPanel))

						.addComponent(mWinnerPanel)
		);
	}


	private void labelStyling ()
	{
		vs.setHorizontalAlignment (SwingConstants.CENTER);

		mNameLabel.setOpaque (true);
		mSNameLabel.setOpaque (false);
		mWinnerName.setOpaque (false);


		mNameLabel.setBackground (new Color(255, 255, 255, 0));
		mSNameLabel.setBackground (new Color(255, 255, 255, 0));
		mWinnerName.setBackground (new Color(255, 255, 255, 0));

		Image leftImage = ImageLoader.getImage(Constats.LEFT_DART_PIC);
		Icon leftIcon = new ImageIcon(leftImage);
		lArrow = new JLabel(leftIcon);

		Image rightImage = ImageLoader.getImage(Constats.RIGHT_DART_PIC);
		Icon rightIcon = new ImageIcon(rightImage);
		rArrow = new JLabel(rightIcon);
	}


	private static Color hex2Rgb(String colorStr)
	{
		// TODO check pattern
		return new Color(
				Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
				Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
				Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
	}


	@Override
	protected void paintComponent (Graphics g)
	{
		super.paintComponent (g);
		Graphics2D g2d = (Graphics2D) g;

		int width = getWidth ();
		int height = getHeight ();

		Color lowerColor = hex2Rgb ("#e5ffd4");
		Color upperColor = hex2Rgb ("#d4ffda");

		GradientPaint gp = new GradientPaint (3, 4, lowerColor, 3, height, upperColor);

		g2d.setRenderingHint (RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setPaint (gp);
		g2d.fillRect (0, 0, width, height);
	}

	private void panelStyling ()
	{
		this.setBorder(new EtchedBorder (EtchedBorder.LOWERED));

		mFirstPlayerPanel.setOpaque (false);
		mSecondPlayerPanel.setOpaque (false);
		mWinnerPanel.setOpaque (false);
		mVersusPanel.setOpaque (false);

		mFirstPlayerPanel.setBackground (new Color(255, 255, 255, 0));
		mSecondPlayerPanel.setBackground (new Color(255, 255, 255, 0));
		mWinnerPanel.setBackground (new Color(255, 255, 255, 0));
		mVersusPanel.setBackground (new Color(255, 255, 255, 0));
	}
}
