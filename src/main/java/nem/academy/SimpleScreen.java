package nem.academy;

import static java.lang.System.out;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.nem.core.model.Account;
import org.nem.core.model.primitive.Amount;

import ch.randelshofer.quaqua.QuaquaManager;
import io.nem.apps.api.AccountApi;
import io.nem.apps.builders.ConfigurationBuilder;
import io.nem.apps.builders.TransferTransactionBuilder;

/**
 * 
 * @author asimas
 *
 */
public class SimpleScreen extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private JLabel privateKeyAccount;
	private JTextField privateKeyAccountField;
	private JLabel amountTransfer;
	private JTextField amountTextField;
	private JLabel accountDestiny;
	private JTextField accountDestinyTextField;
	
	private JLabel fee;
	private JLabel feeTextField;
	
	private JLabel balance;
	private JLabel balanceTextField;	
	
	private JButton sendButton;

	private JMenuBar menuBar;
	private JMenu menu, submenu;
	private JMenuItem menuItem;
	private JRadioButtonMenuItem rbMenuItem;
	private JCheckBoxMenuItem cbMenuItem;
    private ImageIcon iconSuccess;	
    private ImageIcon iconError;

	/**
	 * 
	 */
	public SimpleScreen() {

		System.setProperty("Quaqua.tabLayoutPolicy", "wrap"

		);
		try {
			UIManager.setLookAndFeel(ch.randelshofer.quaqua.QuaquaManager.getLookAndFeel());

			// Explicitly turn on font antialiasing.
			try {
				System.setProperty("swing.aatext", "true");
			} catch (AccessControlException e) {
				// can't do anything about this
			}

			// Use screen menu bar, if not switched off explicitly
			try {
				if (System.getProperty("apple.laf.useScreenMenuBar") == null
						&& System.getProperty("com.apple.macos.useScreenMenuBar") == null) {
					System.setProperty("apple.laf.useScreenMenuBar", "true");
					System.setProperty("com.apple.macos.useScreenMenuBar", "true");
				}
			} catch (AccessControlException e) {
				// can't do anything about this
			}

			// Add Quaqua to the lafs
			ArrayList<LookAndFeelInfo> infos = new ArrayList<LookAndFeelInfo>(
					Arrays.asList(UIManager.getInstalledLookAndFeels()));
			infos.add(new LookAndFeelInfo("Quaqua", QuaquaManager.getLookAndFeelClassName()));
			UIManager.setInstalledLookAndFeels(infos.toArray(new LookAndFeelInfo[infos.size()]));

			// Turn on look and feel decoration when not running on Mac OS X or Darwin.
			// This will still not look pretty, because we haven't got cast shadows
			// for the frame on other operating systems.

			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);

			String lafName = QuaquaManager.getLookAndFeelClassName();

			try {
				// UIManager.setLookAndFeel(lafName);
				System.out.println("   CREATING LAF   " + lafName);

				LookAndFeel laf = (LookAndFeel) Class.forName(lafName).newInstance();
				System.out.println("   LAF CREATED   ");
				System.out.println("   SETTING LAF  ");
				UIManager.setLookAndFeel(laf);
				System.out.println("   LAF SET   ");
			} catch (Exception e) {
				System.err.println("Error setting " + lafName + " in UIManager.");
				e.printStackTrace();
				// can't do anything about this
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		initComponents();
		menuBar();
		centerFrame();

		setDefaultLookAndFeelDecorated(true);

	}

	/**
	 * 
	 */
	private void initComponents() {
		privateKeyAccount = new javax.swing.JLabel("Private Key Account Origin:");
		privateKeyAccountField = new JTextField("6dc7873ca6d3d2e7be2baa22573e9963b1ada5a54778705e286fd54499a3f6fd");

		accountDestiny = new JLabel("To: ");
		accountDestinyTextField = new JTextField("TDLUE6PJPQPTYEVBOSS4GNZKW4VRM7H76DRSRGON");

		amountTransfer = new javax.swing.JLabel("Amount to transfer:");
		amountTextField = new javax.swing.JTextField();

		fee = new JLabel("Transaction Fee:");
		feeTextField = new JLabel("0 XEM");

		balance = new JLabel("Balance:");
		balanceTextField = new JLabel("0 XEM");

		sendButton = new javax.swing.JButton("Send Xem");

		privateKeyAccountField.putClientProperty("Quaqua.TextField.style", "search");
		accountDestinyTextField.putClientProperty("Quaqua.TextField.style", "search");
		amountTextField.putClientProperty("Quaqua.TextField.style", "search");

		privateKeyAccountField.setBackground(new Color(0x00000000, true));
		accountDestinyTextField.setBackground(new Color(0x00000000, true));
		amountTextField.setBackground(new Color(0x00000000, true));

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Nem Java Wallet Implementation");

		sendButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				convertButtonActionPerformed(evt);
			}
		});

		setIconImage(getImage("logo_nem_brasil.png"));

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(650, 250));

		JPanel panelNorth = new JPanel();
		JPanel panelSouth = new JPanel(new GridLayout(1, 2));
		JPanel panelCenter = new JPanel(new GridLayout(4, 2));

		panelCenter.add(privateKeyAccount);
		panelCenter.add(privateKeyAccountField);

		panelCenter.add(accountDestiny);
		panelCenter.add(accountDestinyTextField);

		panelCenter.add(amountTransfer);
		panelCenter.add(amountTextField);

		panelCenter.add(fee);
		panelCenter.add(feeTextField);

		panelNorth.add(new JLabel("Testnet - Transfer XEM"));
		panelSouth.add(sendButton);

		panelSouth.add(balance);
		panelSouth.add(balanceTextField);

		panel.setLayout(new BorderLayout());
		panel.add(panelNorth, BorderLayout.NORTH);
		panel.add(panelSouth, BorderLayout.SOUTH);
		panel.add(panelCenter, BorderLayout.CENTER);

		setContentPane(panel);
		updateBalance();
		pack();

	}

	/**
	 * 
	 */
	private void updateBalance() {
//		new Thread(new Runnable() {
//
//			public void run() {
//				
//				}
//		}).start();
		
		new UpdateBalanceThread(balanceTextField, privateKeyAccountField.getText()).start();
		
	}

	/**
	 * 
	 */
	private static void configure() {
		ConfigurationBuilder.nodeNetworkName("TestNet").nodeNetworkProtocol("http").nodeNetworkUri("50.3.87.123") // TESTNET
																													// NODE
				.nodeNetworkPort("7890").setup();
	}

	/**
	 * 
	 * @param evt
	 */
	private void convertButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_convertButtonActionPerformed
		configure();

		try {
			if(Util.isBlankOrNull(privateKeyAccountField.getText()) && Util.isBlankOrNull(accountDestinyTextField.getText()) && Util.isBlankOrNull(amountTextField.getText())) {
				simpleTransfer();
			}else {
				
				if (null == iconError) {
					iconError = new ImageIcon(getImage("error.png"));
				}
				
				JOptionPane.showMessageDialog(this, "'Private Key','To' and 'Amount' fields need to be filled", "Error",
						JOptionPane.PLAIN_MESSAGE, iconError);
			}
		} catch (Exception e) {
			int ta = optionPane(e.getMessage());
			if (ta == 0) {
				try {
					simpleTransfer();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, "ex.getMessage()", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		final long start = System.currentTimeMillis();

		// Explicitly turn on font antialiasing.
		try {
			System.setProperty("swing.aatext", "true");
		} catch (AccessControlException e) {
			// can't do anything about this
		}

		// Use screen menu bar, if not switched off explicitly
		try {
			if (System.getProperty("apple.laf.useScreenMenuBar") == null
					&& System.getProperty("com.apple.macos.useScreenMenuBar") == null) {
				System.setProperty("apple.laf.useScreenMenuBar", "true");
				System.setProperty("com.apple.macos.useScreenMenuBar", "true");
			}
		} catch (AccessControlException e) {
			// can't do anything about this
		}

		// Add Quaqua to the lafs
		ArrayList<LookAndFeelInfo> infos = new ArrayList<LookAndFeelInfo>(
				Arrays.asList(UIManager.getInstalledLookAndFeels()));
		infos.add(new LookAndFeelInfo("Quaqua", QuaquaManager.getLookAndFeelClassName()));
		UIManager.setInstalledLookAndFeels(infos.toArray(new LookAndFeelInfo[infos.size()]));

		// Turn on look and feel decoration when not running on Mac OS X or Darwin.
		// This will still not look pretty, because we haven't got cast shadows
		// for the frame on other operating systems.
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);

		// Launch the test program
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				try {
					UIManager.setLookAndFeel(QuaquaManager.getLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}
				new SimpleScreen().setVisible(true);
				long end = System.currentTimeMillis();
				System.out.println("QuaquaTest EDT latency=" + (end - start));
			}
		});
	}

	/**
	 * 
	 */
	private void centerFrame() {

		Dimension windowSize = getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();

		int dx = centerPoint.x - windowSize.width / 2;
		int dy = centerPoint.y - windowSize.height / 2;
		setLocation(dx, dy);
	}

	public void menuBar() {
		// Create the menu bar.
		menuBar = new JMenuBar();

		// Build the first menu.
		menu = new JMenu("");
		// menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
		menu.setIcon(new ImageIcon(getImage("menu.png")));
		menuBar.add(menu);

		// a submenu
		menu.addSeparator();
		submenu = new JMenu("A submenu");
		submenu.setMnemonic(KeyEvent.VK_S);

		// a group of JMenuItems
		menuItem = new JMenuItem("A text-only menu item", KeyEvent.VK_T);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
		submenu.add(menuItem);

		menuItem = new JMenuItem("Both text and icon", new ImageIcon("images/middle.gif"));
		menuItem.setMnemonic(KeyEvent.VK_B);
		submenu.add(menuItem);

		menuItem = new JMenuItem(new ImageIcon("images/middle.gif"));
		menuItem.setMnemonic(KeyEvent.VK_D);
		submenu.add(menuItem);

		// a group of radio button menu items
		submenu.addSeparator();
		ButtonGroup group = new ButtonGroup();
		rbMenuItem = new JRadioButtonMenuItem("A radio button menu item");
		rbMenuItem.setSelected(true);
		rbMenuItem.setMnemonic(KeyEvent.VK_R);
		group.add(rbMenuItem);
		submenu.add(rbMenuItem);

		rbMenuItem = new JRadioButtonMenuItem("Another one");
		rbMenuItem.setMnemonic(KeyEvent.VK_O);
		group.add(rbMenuItem);
		submenu.add(rbMenuItem);

		// a group of check box menu items
		submenu.addSeparator();
		cbMenuItem = new JCheckBoxMenuItem("A check box menu item");
		cbMenuItem.setMnemonic(KeyEvent.VK_C);
		submenu.add(cbMenuItem);

		cbMenuItem = new JCheckBoxMenuItem("Another one");
		cbMenuItem.setMnemonic(KeyEvent.VK_H);
		submenu.add(cbMenuItem);

		menuItem = new JMenuItem("An item in the submenu");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		submenu.add(menuItem);

		menuItem = new JMenuItem("Another item");
		submenu.add(menuItem);

		menuBar.add(submenu);
		this.setJMenuBar(menuBar);
	}

	/**
	 * 
	 * @param text
	 * @return
	 */
	public int optionPane(String text) {
		Object[] options = { "Yes", "No" };
		int n = JOptionPane.showOptionDialog(this, "text", "Try again?", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, // do not use a custom Icon
				options, // the titles of buttons
				options[0]); // default button title

		return n;
	}

	/**
	 * 
	 * @param locationInResources
	 * @return
	 */
	public Image getImage(String locationInResources) {
		try {
			Image image = ImageIO.read(getClass().getClassLoader().getResource(locationInResources));
			return image;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This method uses a NEMPH library
	 */
	private void simpleTransfer() throws Exception {

		org.nem.core.crypto.KeyPair senderPrivateKeyPair, recipientPublicKeyPair;

		senderPrivateKeyPair = new org.nem.core.crypto.KeyPair(
				org.nem.core.crypto.PrivateKey.fromHexString(privateKeyAccountField.getText()));
		recipientPublicKeyPair = AccountApi.getAccountByAddress(accountDestinyTextField.getText()).getEntity()
				.getKeyPair();

		org.nem.core.model.ncc.NemAnnounceResult result = TransferTransactionBuilder
				.sender(new Account(senderPrivateKeyPair)).recipient(new Account(recipientPublicKeyPair))
				.amount(new Amount(1000000)).fee(Amount.fromMicroNem(50000)) // fee
				// .message("Teste", org.nem.core.model.MessageTypes.PLAIN) //IF YOU SEND A
				// MESSAGE, INCREASE THE FEE FIELD
				.buildAndSendTransaction();

		System.out.println(result.getCode());
		System.out.println(result.getTransactionHash());
		System.out.println(result.getMessage());
		System.out.println(result.getTransactionHash().getRaw());

		if (null == iconSuccess) {
			iconSuccess = new ImageIcon(getImage("success.png"));
		}
		
		JOptionPane.showInputDialog(this, "Transfer success! \n Transaction Hash: ", "Success",
				JOptionPane.PLAIN_MESSAGE, iconSuccess, null, result.getTransactionHash());

	}

}
