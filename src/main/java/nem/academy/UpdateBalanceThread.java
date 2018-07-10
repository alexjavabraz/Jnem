package nem.academy;

import static java.lang.System.out;

import javax.swing.JLabel;

import org.nem.core.model.Account;

import io.nem.apps.api.AccountApi;
import io.nem.apps.builders.ConfigurationBuilder;

public class UpdateBalanceThread extends Thread {

	public UpdateBalanceThread(JLabel alabel, String theAccount) {
		label = alabel;
		account = theAccount;
	}

	public void run() {
		try {
			while (true) {
				out.println("Retrieving...");
				label.setText("<html><center><font color=green>Preparing to retrieve Balance</font>");
//				label.updateUI();

				if (null != account && !"".equals(account)) {
					label.setText("<html><center><font color=green>Retrieving Balance</font>");
					try {
						String privateKeyToAddress = new Account(
								new org.nem.core.crypto.KeyPair(org.nem.core.crypto.PrivateKey.fromHexString(account)))
										.getAddress().getEncoded();

						ConfigurationBuilder.nodeNetworkName("TestNet")
					    .nodeNetworkProtocol("http")
					    .nodeNetworkUri("50.3.87.123") //TESTNET NODE
					    .nodeNetworkPort("7890")
					    .setup();
						
						out.println(AccountApi.getAccountByAddress(privateKeyToAddress).getEntity().getBalance());

						label.setText("<html><center><font color=blue><b>0 XEM</b></font>");

					} catch (Exception e) {
						Thread.sleep(2000);
						label.setText("<html><center><font color=red>Could'nt retrieve <b><u>Balance</u></b></font>");
					}
				}

				Thread.sleep(5000);

			}
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		}
	}

	private JLabel label;
	private String account;
}