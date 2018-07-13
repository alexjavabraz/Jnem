package nem.academy;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.security.AccessControlException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
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
	
	private JLabel message;
	private JTextArea messageTextArea;	
	
	private JLabel balance;
	private JLabel balanceTextField;
	private JLabel statusLabel;
	
	private static final String VERSION = "V 0.0.1 - ";	
	
	private JButton sendButton;

	private JMenuBar menuBar;
	private JMenu menu, submenu;
	private JMenuItem menuItem;
	private JRadioButtonMenuItem rbMenuItem;
	private JCheckBoxMenuItem cbMenuItem;
    private ImageIcon iconSuccess;	
    private ImageIcon iconError;
    private JToolBar statusBar;
    private Timer timer;

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
		setDefaultLookAndFeelDecorated(true);

		initComponents();
		menuBar();
		centerFrame();
		setupStatusBar();


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

		sendButton = new javax.swing.JButton("Send Xem", new ImageIcon(getImage("icos/transfer.png").getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH)));

		privateKeyAccountField.putClientProperty("Quaqua.TextField.style", "search");
		accountDestinyTextField.putClientProperty("Quaqua.TextField.style", "search");
		amountTextField.putClientProperty("Quaqua.TextField.style", "search");

		privateKeyAccountField.setBackground(new Color(0x00000000, true));
		accountDestinyTextField.setBackground(new Color(0x00000000, true));
		amountTextField.setBackground(new Color(0x00000000, true));
		
		message = new JLabel("Transaction Message:");
		messageTextArea = new JTextArea(100, 100);	

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Nem Java Wallet Implementation");

		sendButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				convertButtonActionPerformed(evt);
			}
		});

		setIconImage(getImage("logo_nem_brasil.png"));

		JPanel mainPanel       = new JPanel();
		JPanel secondMainPanel = new JPanel(new BorderLayout());

		JPanel southPanel = new JPanel(new java.awt.GridBagLayout());
		setPreferredSize(new Dimension(850, 600));

		JPanel panelNorth = new JPanel();
		JPanel panelSouth = new JPanel(new GridBagLayout());
		JPanel panelCenter = new JPanel(new GridLayout(5, 2));
		
		panelNorth.add(new JLabel("Testnet - Transfer XEM"));

		panelCenter.add(privateKeyAccount);
		panelCenter.add(privateKeyAccountField);

		panelCenter.add(accountDestiny);
		panelCenter.add(accountDestinyTextField);

		panelCenter.add(amountTransfer);
		panelCenter.add(amountTextField);
		
		panelCenter.add(balance);
		panelCenter.add(balanceTextField);
		
		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setViewportView(messageTextArea);
		
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
		
		panelCenter.add(fee);
		panelCenter.add(feeTextField);		

		southPanel.add(scrollPane1, gridBagConstraints);
		
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        southPanel.add(message, gridBagConstraints);

		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(panelNorth, BorderLayout.NORTH);
		mainPanel.add(panelCenter, BorderLayout.CENTER);
		mainPanel.add(panelSouth, BorderLayout.SOUTH);
		
		secondMainPanel.add(southPanel, BorderLayout.CENTER);
		secondMainPanel.add(sendButton, BorderLayout.SOUTH);

		setLayout(new BorderLayout());
		add(mainPanel, BorderLayout.NORTH);
		add(secondMainPanel, BorderLayout.CENTER);
		updateBalance();
		systemTray();
		pack();
	}
	
	/**
	 * 
	 */
	private void setupStatusBar() {
		statusBar = new JToolBar();
		statusLabel = new JLabel(VERSION);
//		statusBar.add(new JLabel("V 0.0.1", new ImageIcon(getImage("jnem.png").getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH)), SwingConstants.CENTER));
		statusBar.add(statusLabel);

		statusBar.putClientProperty("Quaqua.ToolBar.style", "gradient");
		statusBar.setFloatable(false);
		add(statusBar, BorderLayout.SOUTH);
		startTimer();
	}
	
	/**
	 * 
	 */
    private void startTimer() {
        if (timer==null) {
            timer=new Timer(500,new ActionListener(){

                public void actionPerformed(ActionEvent e) {
                    updateStatusLabel();
                }
            });
            timer.setRepeats(true);
            timer.start();
        }
    }

    private void stopTimer() {
        
        if (timer != null) {
            timer.stop();
            timer = null;
        }
    }

    private void updateStatusLabel() {
       DateFormat tf=new SimpleDateFormat("HH:mm:ss");
        statusLabel.setText(VERSION + tf.format(new Date()));
    }

	/**
	 * 
	 */
	private void updateBalance() {
		new UpdateBalanceThread(balanceTextField, privateKeyAccountField.getText()).start();
	}

	/**
	 * 
	 */
	private static void configure() {
		ConfigurationBuilder.nodeNetworkName("TestNet").nodeNetworkProtocol("http").nodeNetworkUri("50.3.87.123") // TESTNET
				.nodeNetworkPort("7890").setup();
	}

	/**
	 * 
	 * @param evt
	 */
	private void convertButtonActionPerformed(java.awt.event.ActionEvent evt) {
		configure();

		try {
			if(!Util.isBlankOrNull(privateKeyAccountField.getText()) && !Util.isBlankOrNull(accountDestinyTextField.getText()) && !Util.isBlankOrNull(amountTextField.getText())) {
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
		menu.setIcon(new ImageIcon(getImage("menu.png").getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH)));
		menuBar.add(menu);

		// a submenu
		menu.addSeparator();
		submenu = new JMenu("Accounts");
		submenu.setMnemonic(KeyEvent.VK_A);

		// a group of JMenuItems
		menuItem = new JMenuItem("Create new account", KeyEvent.VK_N);
		menuItem.setIcon(new ImageIcon(getImage("icon-register.png").getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH)));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Creates a new Nem Account on Testnet");
		submenu.add(menuItem);

		menuItem = new JMenuItem("View Transactions By Account", new ImageIcon(getImage("transactions.png").getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH)));
		menuItem.setMnemonic(KeyEvent.VK_V);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.ALT_MASK));
		submenu.add(menuItem);

		menuItem = new JMenuItem("Import Primary Key", new ImageIcon(getImage("private_key.png").getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH)));
		menuItem.setMnemonic(KeyEvent.VK_D);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.ALT_MASK));
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
				org.nem.core.crypto.PrivateKey.fromHexString(Util.sanityzer(privateKeyAccountField.getText())));
		recipientPublicKeyPair = AccountApi.getAccountByAddress(Util.sanityzer(accountDestinyTextField.getText())).getEntity()
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
	
	/**
	 * 
	 */
	private void systemTray() {
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        final PopupMenu popup   = new PopupMenu();
        final TrayIcon trayIcon = new TrayIcon(getImage("jnem.png").getScaledInstance(22, 22, java.awt.Image.SCALE_SMOOTH), "JNem Wallet TestNet");
        final SystemTray tray   = SystemTray.getSystemTray();
       
        Menu displayMenu = new Menu("JNem");
        MenuItem reopen = new MenuItem("Dashboard");
        MenuItem about = new MenuItem("About");
        MenuItem exitItem = new MenuItem("Exit");
       
        //Add components to pop-up menu
        popup.add(displayMenu);
        displayMenu.add(reopen);
        popup.addSeparator();
        displayMenu.add(about);
        popup.add(exitItem);
       
        trayIcon.setPopupMenu(popup);
       
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
        
        ActionListener listener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				MenuItem item = (MenuItem)e.getSource();
                System.out.println(item.getLabel());
                if ("Dashboard".equals(item.getLabel())) {
                    SimpleScreen.this.setVisible(true);
                    SimpleScreen.this.setState(Frame.NORMAL);
                } else if ("About".equals(item.getLabel())) {
                    trayIcon.displayMessage("JNem Wallet for TestNet",
                            "V 0.0.1", TrayIcon.MessageType.INFO);
                }
                
			}
        	
        };
        
        about.addActionListener(listener);
        reopen.addActionListener(listener);
        
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                System.exit(0);
            }
        });
	}

}
