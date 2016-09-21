package GameController.GUI;

import MatchController.Objects.PlayerObject;
import MatchController.Constats;
import Tools.ImageLoader;

import javax.swing.*;
import java.awt.*;

/**
 * Created by vladislavs on 07.09.2016..
 */
public class GameDisplayGuiForm
{
	private JFrame          mJFrame;
	private JPanel          mJPanel;

	private JTextField      mFirstPlayerName;
	private JTextField      mFirstPlayerLeg;
	private JTextField      mFirstPlayerScore;
	private JLabel          mFPlayerLabel;

	private JTextField      mSecondPlayerName;
	private JTextField      mSecondPlayerLeg;
	private JTextField      mSecondPlayerScore;
	private JLabel          mSPlayerLabel;
	private JLabel          mMainImage;
	private JLabel          fPlayerImage;
	private JLabel          sPlayerImage;

	private PlayerObject    mPlayer;
	private PlayerObject    mPlayerOpponent;


	public GameDisplayGuiForm (PlayerObject firstPlayer, PlayerObject secondPlayer)
	{
		mPlayer         = firstPlayer;
		mPlayerOpponent = secondPlayer;

		formInitialization ();
		iconsInitialization ();
		playerTableInitialization ();
	}


	private void formInitialization ()
	{
		mJFrame = new JFrame ("GameDisplayGuiForm");
		mJFrame.setContentPane (mJPanel);
		mJFrame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
		mJFrame.setUndecorated(true);
		mJFrame.getRootPane().setWindowDecorationStyle(JRootPane.COLOR_CHOOSER_DIALOG);
		mJFrame.pack ();
		mJFrame.setVisible (true);

		setMJFrameLocation ();
	}


	private void setMJFrameLocation ()
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		mJFrame.setLocation (dim.width / 2 - mJFrame.getSize ().width / 2, 0);
	}


	private void iconsInitialization ()
	{
		mMainImage.setIcon      (new ImageIcon (ImageLoader.getImage   (Constats.MAIN_BOARD_PIC)));
		sPlayerImage.setIcon    (new ImageIcon (ImageLoader.getImage   (Constats.DEFAULT_PLAYER_PIC_RIGHT)));
		fPlayerImage.setIcon    (new ImageIcon (ImageLoader.getImage   (Constats.DEFAULT_PLAYER_PIC_LEFT)));
	}


	private void playerTableInitialization ()
	{
		mSPlayerLabel.setText       (mPlayer.mName);
		mFirstPlayerName.setText    (mPlayer.mName);
		mFirstPlayerLeg.setText     (String.valueOf (mPlayer.mLeg));
		mFirstPlayerScore.setText   (String.valueOf (mPlayer.mScore));

		mFPlayerLabel.setText       (mPlayerOpponent.mName);
		mSecondPlayerName.setText   (mPlayerOpponent.mName);
		mSecondPlayerLeg.setText    (String.valueOf (mPlayerOpponent.mLeg));
		mSecondPlayerScore.setText  (String.valueOf (mPlayerOpponent.mScore));
	}


	public void updatePlayerData ()
	{
		mFirstPlayerScore.setText (String.valueOf (mPlayer.mScore));
		mSecondPlayerScore.setText (String.valueOf (mPlayerOpponent.mScore));
	}


	public void updateGameLegData ()
	{
		mFirstPlayerLeg.setText     (String.valueOf (mPlayer.mLeg));
		mFirstPlayerScore.setText   (String.valueOf (mPlayer.mScore));

		mSecondPlayerLeg.setText    (String.valueOf (mPlayerOpponent.mLeg));
		mSecondPlayerScore.setText  (String.valueOf (mPlayerOpponent.mScore));
	}


	public Dimension getSize ()
	{
		return mJFrame.getSize ();
	}


	public Point getLocation ()
	{
		return mJFrame.getLocation ();
	}


	public void destroy ()
	{
		mJFrame.setVisible (false);
		mJFrame.dispose ();
	}
}
