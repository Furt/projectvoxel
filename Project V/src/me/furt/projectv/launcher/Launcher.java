package me.furt.projectv.launcher;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Toolkit;

public class Launcher extends JFrame {
	private static final long serialVersionUID = 1L;
	public static String version = "0.0.1";
	private JPanel contentPane;

	public static void main(String[] args) {
		System.setProperty("http.agent", "");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Launcher frame = new Launcher();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Launcher() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Launcher.class.getResource("/me/furt/projectv/client/assets/images/magex16.png")));
		setTitle("Project V [Launcher v" + version + "]");
		setDefaultCloseOperation(3);
		setBounds(100, 100, 849, 551);
	    
	    contentPane = new JPanel();
	    contentPane.setForeground(new Color(0, 0, 0));
	    setContentPane(contentPane);
	    GridBagLayout gbl_contentPane = new GridBagLayout();
	    gbl_contentPane.columnWidths = new int[] { 0, 0 };
	    gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0 };
	    gbl_contentPane.columnWeights = new double[] { 1.0D, 4.9E-324D };
	    gbl_contentPane.rowWeights = new double[] { 1.0D, 1.0D, 1.0D, 4.9E-324D };
	    contentPane.setLayout(gbl_contentPane);

	    final JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setPreferredSize(new Dimension(400, 300));
	    scrollPane.setMinimumSize(new Dimension(23, 30));
	    GridBagConstraints gbc_scrollPane = new GridBagConstraints();
	    gbc_scrollPane.fill = 1;
	    gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
	    gbc_scrollPane.gridx = 0;
	    gbc_scrollPane.gridy = 0;
	    contentPane.add(scrollPane, gbc_scrollPane);
	    
	    //
	    JTextPane txtpnIfTheDownload = new JTextPane();
	    txtpnIfTheDownload.setPreferredSize(new Dimension(300, 30));
	    scrollPane.setViewportView(txtpnIfTheDownload);
	    txtpnIfTheDownload.setText("Sur casernes eut pic criaient couvrent defoncat heureuse. Bon oeil aux mats tuer chez poil peur. Saut poil il fils un nous je eu idee. Si mais haut oh ah quoi loin. Crepitent demeurent perimetre sa xv cartouche convertir he culbutent. Cercle qu valoir ca bruits le ca. Oeufs feu dit sorte rente trois ecole mur moins.\r\n\r\nHebetude joyeuses assister nul ton prochain les commence massacre. Tout ni elle pris il au ma vaut sent hein. Ils pleine net enleve tenter maison centre blancs. Ils voeux que aimer bas linge des verre. Instrument maintenant en miserables au defilaient he. Se torture enlever en dessein. Peur moi age sang deja fort etat fin. Ronfle car car mon ces pareil reunir humain metres peuple. Corbeille sacrifice convertir des ses militaire ans.\r\n\r\nVictoire oh respirer fusiller repartit flottent ah tu. Militaire le printemps sinistres un resterait sentiment. Aimer sa joies du le porta. Art mes vecut peu menue monte. La situation polygones souvenirs desespoir ah. Revendre flottent des hataient ils coupoles.\r\n\r\nCoupoles me crispent posseder philippe susciter ou ma. Laidement ca universel repousser me uniformes annoncait. Femme au laque au va levee. Net age berce iii idees actes. Oh rappelles citadelle boulevard chambrees un. Apercoit relevent ans ignorant foi fillette.\r\n\r\nSurprit pic attenua encourt ecarter seconde mur sur lui. Promenade en au jugements direction ou. Vif porta selon grand bas par. Sur nez employees prenaient ete demeurent fut firmament indicible. Humaines six ici gravures ces arrivera. Victoire sifflent ignorant habitent nouvelle ont six ici dissiper. Un legerement etonnement diplomates me consentiez.\r\n\r\nBout ifs avez ere main oui oui fait bois. Ah devant qu va lazzis suivit bourse depuis ordure. Republique paraissent evidemment tristement il grouillent oh pressentit. Courir cet les yeuses nouent pleurs roc fut ouvert forges. Frequentes survivants instrument dit paraissait des executeurs souhaitait. Miserable flamboyer illumines repousser xv on mystiques ah effrayant. Remarquait estaminets ordonnance ras ils compassion par. Sur coups ils entre art connu votre essor. Et sans as feux ii fait il pans.\r\n\r\nLaissant eau peu prochain repondit son nid. Un files connu arbre ii autre. Lumieres vit aux soudaine derniere. Les prisonnier bon pic bas historique comprendre. Pompons nos menions peloton pourrai dur. Craie jeune sacre comme non coups feu son. Route nid homme eut coeur ils finit. Pic louis noces signe foi fer.\r\n\r\n");
	    SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMinimum());
			}
	    	
	    });
	    //
	    UpdatePanel updatePanel = new UpdatePanel();
	    updatePanel.setBorder(null);
	    GridBagConstraints gbc_updatePanel = new GridBagConstraints();
	    gbc_updatePanel.weighty = 1.0D;
	    gbc_updatePanel.fill = 1;
	    gbc_updatePanel.weightx = 1.0D;
	    gbc_updatePanel.gridx = 0;
	    gbc_updatePanel.gridy = 2;
	    contentPane.add(updatePanel, gbc_updatePanel);
	}
}
