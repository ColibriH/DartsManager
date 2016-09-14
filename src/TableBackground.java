import MatchController.Constats;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TableBackground {

	private BufferedImage background;

	public static void main(String[] args) {
		new TableBackground();
	}

	public TableBackground() {
		try {
			background = ImageIO.read(new File (System.getProperty("user.dir") + File.separator +
					                                    Constats.PIC_FOLDER_PATH +
					                                    File.separator +
					                                    Constats.TABLE_PIC));
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				System.out.println (System.getProperty("user.dir") + File.separator +
						                    Constats.PIC_FOLDER_PATH +
						                    File.separator +
						                    Constats.TABLE_PIC);
				JPanel mJPanel = new JPanel ();

				Object[][] data = new Object[50][4];
				for (int row = 0; row < 50; row++) {
					for (int col = 0; col < 4; col++) {
						data[row][col] = col + "." + row;
					}
				}

				JTable table = new JTable();
				table.setForeground(Color.WHITE);
				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				table.setModel(new javax.swing.table.DefaultTableModel(
						data,
						new String[]{
								"Title 1", "Title 2", "Title 3", "Title 4"
						}));

				JScrollPane scrollPane = new JScrollPane();
				table.setOpaque(false);
				table.setBackground(new Color(255, 255, 255, 0));
				scrollPane.setViewport(new ImageViewport());
				scrollPane.setViewportView(table);
				mJPanel.setLayout(new BorderLayout());
				mJPanel.add(scrollPane);

				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				frame.setLayout(new BorderLayout());
				frame.add(mJPanel);
				frame.setSize(200, 200);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

	public class ImageViewport extends JViewport {

		public ImageViewport() {
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (background != null) {
				Rectangle bounds = getViewRect();
				int x = Math.max(0, (bounds.width - background.getWidth()) / 2);
				int y = Math.max(0, (bounds.height - background.getHeight()) / 2);
				g.drawImage(background, x, y, this);
			}
		}
	}
}
