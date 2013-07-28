package me.furt.projectv.launcher;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class UpdatePanel extends JPanel implements Observer {
	private static final long serialVersionUID = -6294893132089375005L;
	public static int maxMemory = 1024;
	public static int minMemory = 512;
	public static int earlyGenMemory = 128;

	public static int maxMemory32 = 512;
	public static int minMemory32 = 256;
	public static int earlyGenMemory32 = 64;

	public static int serverMaxMemory = 1024;
	public static int serverMinMemory = 1024;
	public static int serverEarlyGenMemory = 256;

	public static int port = 4242;
	private Updater updater;
	private JButton btnUpdateToNewest;
	private JProgressBar progressBar;
	private JButton btnRefresh;
	private JButton btnStartGame;
	private JLabel label;
	private JLabel lblNewLabel;
	private JLabel label_1;

	private static String getJavaExec() {
		if ((System.getProperty("os.name").equals("Mac OS X"))
				|| (System.getProperty("os.name").contains("Linux"))) {
			return "java";
		}
		return "javaw";
	}

	public static boolean is64Bit() {
		return System.getProperty("os.arch").contains("64");
	}

	public UpdatePanel() {
		setBorder(null);
		updater = new Updater();
		updater.addObserver(this);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 250, 0, 156, 87, 0 };
		gridBagLayout.rowHeights = new int[] { 23, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 6.0D, 0.0D, 0.0D, 0.0D,
				4.9E-324D };

		gridBagLayout.rowWeights = new double[] { 0.0D, 0.0D, 0.0D, 0.0D,
				4.9E-324D };
		setLayout(gridBagLayout);

		progressBar = new JProgressBar();
		progressBar.setPreferredSize(new Dimension(250, 20));
		progressBar.setMinimumSize(new Dimension(200, 14));
		progressBar.setStringPainted(true);
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.weightx = 10.0D;
		gbc_progressBar.fill = 1;
		gbc_progressBar.insets = new Insets(0, 0, 5, 5);
		gbc_progressBar.gridx = 0;
		gbc_progressBar.gridy = 0;
		add(progressBar, gbc_progressBar);

		btnRefresh = new JButton("refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updater.reloadVersion();
				updater.startLoadVersionList();
				btnStartGame.setEnabled(updater.lookForGame());
			}
		});
		GridBagConstraints gbc_btnRefresh = new GridBagConstraints();
		gbc_btnRefresh.anchor = 12;
		gbc_btnRefresh.weightx = 3.0D;
		gbc_btnRefresh.insets = new Insets(0, 0, 5, 5);
		gbc_btnRefresh.gridx = 1;
		gbc_btnRefresh.gridy = 0;
		add(btnRefresh, gbc_btnRefresh);

		btnUpdateToNewest = new JButton("Update");
		btnUpdateToNewest.setFont(new Font("Tahoma", 1, 11));
		btnUpdateToNewest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updater.startUpdate();
			}
		});
		btnUpdateToNewest.setAlignmentX(1.0F);
		btnUpdateToNewest.setEnabled(updater.isNewerVersionAvailable());
		GridBagConstraints gbc_btnUpdateToNewest = new GridBagConstraints();
		gbc_btnUpdateToNewest.weightx = 3.0D;
		gbc_btnUpdateToNewest.anchor = 13;
		gbc_btnUpdateToNewest.insets = new Insets(0, 0, 5, 5);
		gbc_btnUpdateToNewest.gridx = 2;
		gbc_btnUpdateToNewest.gridy = 0;
		add(btnUpdateToNewest, gbc_btnUpdateToNewest);

		btnStartGame = new JButton("Start");
		btnStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdatePanel.this.startInstance(new String[0]);
			}
		});
		btnStartGame.setAlignmentX(1.0F);
		GridBagConstraints gbc_btnStartGame = new GridBagConstraints();
		gbc_btnStartGame.insets = new Insets(0, 0, 5, 0);
		gbc_btnStartGame.weightx = 1.0D;
		gbc_btnStartGame.anchor = 13;
		gbc_btnStartGame.gridx = 3;
		gbc_btnStartGame.gridy = 0;
		btnStartGame.setEnabled(updater.lookForGame());
		add(btnStartGame, gbc_btnStartGame);
		label_1 = new JLabel("");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 1;
		add(label_1, gbc_label_1);

		label = new JLabel("");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.gridwidth = 3;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 2;
		gbc_label.anchor = 17;
		add(label, gbc_label);

		lblNewLabel = new JLabel("");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 3;
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 3;
		gbc_lblNewLabel.anchor = 17;
		add(lblNewLabel, gbc_lblNewLabel);

		updater.startLoadVersionList();
	}

	private void startInstance(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					String a = "";
					for (int i = 0; i < args.length; i++) {
						a = a + " " + args[i];
					}
					String projectvPath = updater.getProjectVStartPath();
					File projectvFile = new File(projectvPath);
					String[] command = null;

					if (UpdatePanel.is64Bit()) {
						command = new String[] { UpdatePanel.getJavaExec(),
								"-Xmn" + UpdatePanel.earlyGenMemory + "M",
								"-Xms" + UpdatePanel.minMemory + "M",
								"-Xmx" + UpdatePanel.maxMemory + "M",
								"-Xincgc", "-jar",
								projectvFile.getAbsolutePath(), "-force",
								"-port:" + UpdatePanel.port, a };
					} else {
						command = new String[] { UpdatePanel.getJavaExec(),
								"-Xmn" + UpdatePanel.earlyGenMemory32 + "M",
								"-Xms" + UpdatePanel.minMemory32 + "M",
								"-Xmx" + UpdatePanel.maxMemory32 + "M",
								"-Xincgc", "-jar",
								projectvFile.getAbsolutePath(), "-force",
								"-port:" + UpdatePanel.port, a };
					}

					System.err.println("RUNNING COMMAND: " + command);
					ProcessBuilder pb = new ProcessBuilder(command);
					pb.environment();
					File file = new File("./ProjectV/");
					if (file.exists()) {
						pb.directory(file.getAbsoluteFile());
						pb.start();
						System.err
								.println("Exiting because updater staring game");
						System.exit(0);
					} else {
						throw new FileNotFoundException(
								"Cannot find the Install Directory: ./ProjectV/");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void update(Observable arg0, Object o) {
		if (o != null) {
			if (o.equals("versions loaded")) {
				updater.reloadVersion();
				btnUpdateToNewest.setEnabled(updater.isNewerVersionAvailable());

				progressBar.setString("");
			} else if (o.equals("updating")) {
				btnUpdateToNewest.setEnabled(false);
				btnStartGame.setEnabled(false);

				btnRefresh.setEnabled(false);
			} else if (o.equals("finished")) {
				btnUpdateToNewest.setEnabled(false);
				btnRefresh.setEnabled(true);
				updater.reloadVersion();
				updater.startLoadVersionList();
				btnStartGame.setEnabled(updater.lookForGame());
			} else if (o.equals("reset")) {
				progressBar.setString("");
				progressBar.setValue(0);
			} else {
				if ((o instanceof long[])) {
					long[] p = (long[]) (long[]) o;
					int val = (int) ((float) p[0] / (float) p[1] * 100.0F);
					progressBar.setString("downloading... " + val + "%");

					progressBar.setValue(val);
					return;
				}
				if ((o instanceof String)) {
					progressBar.setString(o.toString());
				}
			}
		}

		boolean gameFound = updater.lookForGame();
		File f = new File("./ProjectV/");
		String version = "CURRENT VERSION: v" + Version.VERSION + "; build "
				+ Version.build + "; LauncherVersion: " + Launcher.version;
		if (updater.isNewerVersionAvailable()) {
			version = version + ". A new Version is Available!";
			label.setForeground(Color.GREEN.darker());
		} else {
			version = version + ". You already have the latest version";
			label.setForeground(Color.BLACK);
		}
		label.setText(version);
		String v = "No installation found in " + f.getAbsolutePath();
		v = v.replace("/./", "/");
		v = v.replace("\\.\\", "\\");
		lblNewLabel.setText(v);
		if (gameFound)
			lblNewLabel.setForeground(Color.GREEN.darker());
		else
			lblNewLabel.setForeground(Color.RED.darker());
	}
}
