package MenuGui;

import MatchController.Constats;
import Tools.ImageLoader;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// TODO change to mel bg anf white mel border for btn

/**
 * Created by vladislavs on 26.09.2016..
 */
public class MenuOptionComponent extends JPanel
{
	private JLayeredPane mLayeredPane;
	private JPanel mBGPanel;
	private JPanel mBtnPanel;
	private JButton mBtn;

	public MenuOptionComponent (int width, int height, JButton btn)
	{
		mBtn = btn;

		modifyButton ();

		setLayout (new BorderLayout ());

	//	JLabel mCornerIcon = new JLabel (new ImageIcon (ImageLoader.getImage (Constats.CORNER_BG_PIC)));
		JLabel mBtnBg      = new JLabel (new ImageIcon (ImageLoader.getImage (Constats.BTN_TEXTURE_PIC)));

		JLayeredPane mJLayeredPane = new JLayeredPane ();
		mJLayeredPane.setOpaque (true);
		mJLayeredPane.setBackground (new Color (255, 255, 0 ,0));
		mJLayeredPane.setPreferredSize(new Dimension(width, height));

		//JPanel mCornerIconsPanel = new JPanel (new BorderLayout ());
		//mCornerIconsPanel.setBackground (new Color (255, 255, 0, 0));
		//mCornerIconsPanel.setBounds (0, 0, 20, 20);
		//mCornerIconsPanel.add (mCornerIcon);


		JPanel mBtnBgPanel = new JPanel (new BorderLayout ());
		mBtnBgPanel.setBackground (new Color (255, 255, 0, 0));
		mBtnBgPanel.setBounds (10, 10, width - 10, height - 10);
		mBtnBgPanel.add (mBtnBg);

		JPanel mBtnPanel = new JPanel ();
		mBtnPanel.setLayout (new BorderLayout ());
		mBtnPanel.setOpaque (false);
		mJLayeredPane.setBackground (new Color (255, 255, 0 ,0));
		mBtnPanel.setBounds (10, 10, width - 10, height - 10);


		mBtn.setBounds (10, 10, width - 10, height - 10);


		//mJLayeredPane.add (mCornerIconsPanel, 0);
		mJLayeredPane.add (btn, 1);
		mJLayeredPane.add (mBtnPanel, 2);
		mJLayeredPane.add (mBtnBgPanel, 3);


		add (mJLayeredPane, BorderLayout.CENTER);
	}


	private void modifyButton ()
	{
		mBtn.setOpaque (false);
		mBtn.setBackground (new Color (255, 255, 0 ,0));
		mBtn.setBorder (new EmptyBorder (0, 0, 0, 0));
		//mBtn.set
	}


	public JButton getButton ()
	{
		return mBtn;
	}
}
