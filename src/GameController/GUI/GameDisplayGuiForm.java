package GameController.GUI;

import GameController.Object.PlayerObject;
import MatchController.Constats;
import Tools.ImageLoader;

import javax.swing.*;

/**
 * Created by vladislavs on 07.09.2016..
 */
public class GameDisplayGuiForm
{
	private JFrame      mJFrame;
	private JPanel      mJPanel;

	private JTextField  mFirstPlayerName;
	private JTextField  mFirstPlayerLeg;
	private JTextField  mFirstPlayerScore;
	private JLabel      mFPlayerLabel;

	private JTextField  mSecondPlayerName;
	private JTextField  mSecondPlayerLeg;
	private JTextField  mSecondPlayerScore;
	private JLabel      mSPlayerLabel;
	private JLabel      mMainImage;
	private JLabel      fPlayerImage;
	private JLabel      sPlayerImage;


	public GameDisplayGuiForm (PlayerObject firstPlayerName, PlayerObject secondPlayerName)
	{
		mSPlayerLabel.setText       (firstPlayerName.mName);
		mFirstPlayerName.setText    (firstPlayerName.mName);
		mFirstPlayerLeg.setText     (String.valueOf (firstPlayerName.mLeg));
		mFirstPlayerScore.setText   (String.valueOf (firstPlayerName.mScore));

		mFPlayerLabel.setText       (secondPlayerName.mName);
		mSecondPlayerName.setText   (secondPlayerName.mName);
		mSecondPlayerLeg.setText    (String.valueOf (secondPlayerName.mLeg));
		mSecondPlayerScore.setText  (String.valueOf (secondPlayerName.mScore));

		formInitialization ();
		iconsInitialization ();
	}


	private void iconsInitialization ()
	{
		mMainImage.setIcon (new ImageIcon (ImageLoader.getImage (Constats.MAIN_BOARD_PIC)));
		sPlayerImage.setIcon (new ImageIcon (ImageLoader.getImage (Constats.DEFAULT_PLAYER_PIC_RIGHT)));
		fPlayerImage.setIcon (new ImageIcon (ImageLoader.getImage (Constats.DEFAULT_PLAYER_PIC_LEFT)));
	}


	private void formInitialization ()
	{
		mJFrame = new JFrame ("GameDisplayGuiForm");
		mJFrame.setContentPane (mJPanel);
		mJFrame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
		mJFrame.pack ();
		mJFrame.setVisible (true);
	}


	public void setPlayerScore (int playerNumber, int scores)
	{
		if (playerNumber == 1)
			mFirstPlayerScore.setText (String.valueOf (scores));

		if (playerNumber == 2)
			mSecondPlayerScore.setText (String.valueOf (scores));
	}


	public void updateGameLegData (PlayerObject fPlayer, PlayerObject sPlayer)
	{
		mFirstPlayerLeg.setText     (String.valueOf (fPlayer.mLeg));
		mFirstPlayerScore.setText   (String.valueOf (301));

		mSecondPlayerLeg.setText    (String.valueOf (sPlayer.mLeg));
		mSecondPlayerScore.setText  (String.valueOf (301));
	}


	public void destroy ()
	{
		mJFrame.setVisible (false);
		mJFrame.dispose ();
	}
}
