package nem.academy;

import static java.lang.System.out;

import org.nem.core.model.Account;
import org.nem.core.model.primitive.Amount;

import com.github.rosklyar.client.DefaultNemClientFactory;
import com.github.rosklyar.client.account.AccountClient;
import com.github.rosklyar.client.account.domain.KeyPair;
import com.github.rosklyar.client.node.NodeClient;
import com.github.rosklyar.client.transaction.TransactionClient;
import com.github.rosklyar.client.transaction.domain.NemAnnounceResult;
import com.netflix.config.ConfigurationManager;

import io.nem.apps.api.AccountApi;
import io.nem.apps.builders.ConfigurationBuilder;
import io.nem.apps.builders.TransferTransactionBuilder;
import io.nem.apps.model.GeneratedAccount;


/**
 * Alex Simas Braz
 * @since 09/07/2018
 * @author asimas
 *
 */
public class CreateAccount {
	
	private static final String ACCOUNT_="TDOQ2JXHF64QREQRM3RIMRSDVO7NYKD2ARJDQJN5"; //MAIN ACCOUNT
	private static final String ACCOUNT_TRANSFER_TO = "TDLUE6PJPQPTYEVBOSS4GNZKW4VRM7H76DRSRGON"; //TO ACCOUNT
	private static final int totalSecondsTransactionTimeout = 600;
	private static final String myPrivateKey = "6dc7873ca6d3d2e7be2baa22573e9963b1ada5a54778705e286fd54499a3f6fd"; //MAIN ACCOUNT PRIVATE KEY
	
	/**
	 * TO ACCOUNT PUBLIC KEY
	 * If you dont know what is the public key, and you have the account, you can see the public key here:
	 * http://50.3.87.123:7890/account/get?address=<put the account testnet address here>
	 * 
	 * If you're using NanoWallet you can obtain the public key, clicking in the label account
	 * As can you see in public_key_nanowallet_screen.png
	 */
	private static final String ACCOUNT_TRANSFER_TO_PUBLIC_KEY = "a779dc736d699bf5279b41b21dc1dbcde73cc89a89a1ec31c2256d7d410ef461"; 
			
	
	public static void main(String[] args) {
		configure();
//		createAccount();
//		nodeInfo();
//		simpleTransfer2();
		
		loadBalanceAccount();
		
		System.exit(0);
	}
	
	/**
	 * This method is necessary to setup the environment and set the primary node who you will communicate
	 */
	public static void configure() {
		ConfigurationBuilder.nodeNetworkName("TestNet")
	    .nodeNetworkProtocol("http")
	    .nodeNetworkUri("50.3.87.123") //TESTNET NODE
	    .nodeNetworkPort("7890")
	    .setup();
	}
	
	/**
	 * Simple form to create neww account
	 */
	public static void createAccount() {
		GeneratedAccount ga = AccountApi.generateAccount();
		out.println(ga.getEncodedAddress());
		out.println(ga.getEncodedPublicKey());
		out.println(ga.getEncodedAddress());
		out.print(ga.getAccount().getAddress());
		out.print(ga.getAccount().getAddress().getPublicKey());
		out.print(ga.getAccount().getAddress().getEncoded());
	}
	
	/**
	 * Obtain the desctiption of node, that description are in config.properties file, and will show information like 
	 * nis.bootName = xxxxxx
	 */
	public static void nodeInfo() {
		ConfigurationManager.getConfigInstance().setProperty("nodeApi.ribbon.listOfServers", "50.3.87.123:7890");
		NodeClient nodeClient = new DefaultNemClientFactory().createNodeClient("nodeApi");
		out.println(nodeClient.info().identity.name);
		
		AccountClient accountClient = new DefaultNemClientFactory().simpleAccountClient("http://50.3.87.123:7890");
		KeyPair newAccount = accountClient.generate();
		out.println(newAccount.address);
		out.println(newAccount.privateKey);
		out.println(newAccount.publicKey);
	}
	
	public static void simpleTransfer() {
		TransactionClient simpleTransactionClient = new DefaultNemClientFactory().simpleTransactionClient("http://50.3.87.123:7890", DefaultNemClientFactory.TEST);
		NemAnnounceResult result = simpleTransactionClient.transferNem(myPrivateKey, ACCOUNT_TRANSFER_TO, 050000, null, totalSecondsTransactionTimeout);
		out.println(result.code);
		out.println(result.message);
		out.println(result.type);
		out.println(result.innerTransactionHash.data);
	}
	
	/**
	 * This method uses a NEMPH library
	 */
	public static void simpleTransfer2() {

		org.nem.core.crypto.KeyPair senderPrivateKeyPair,recipientPublicKeyPair;
		
		senderPrivateKeyPair = new org.nem.core.crypto.KeyPair(org.nem.core.crypto.PrivateKey.fromHexString(myPrivateKey));
		recipientPublicKeyPair = new org.nem.core.crypto.KeyPair( org.nem.core.crypto.PublicKey.fromHexString(ACCOUNT_TRANSFER_TO_PUBLIC_KEY));
		
		org.nem.core.model.ncc.NemAnnounceResult result = TransferTransactionBuilder
	    .sender(new Account(senderPrivateKeyPair))
	    .recipient(new Account(recipientPublicKeyPair))
	    .amount(new Amount(1000000))
	    .fee(Amount.fromMicroNem(50000)) // fee
//	    .message("Teste", org.nem.core.model.MessageTypes.PLAIN) //IF YOU SEND A MESSAGE, INCREASE THE FEE FIELD
	    .buildAndSendTransaction();
		
		System.out.println(result.getCode());
		System.out.println(result.getTransactionHash());
		System.out.println(result.getMessage());
		System.out.println(result.getTransactionHash().getRaw());
		
	}
	
	/**
	 * 
	 */
	public static void loadBalanceAccount() {
		try {
			
			/**
			 * Getting by address
			 */
			out.println(AccountApi.getAccountByAddress(ACCOUNT_TRANSFER_TO).getEntity().getBalance());
			
			
			/**
			 * Getting by private key, to address
			 */
			String privateKeyToAddress = new Account(new org.nem.core.crypto.KeyPair(
					org.nem.core.crypto.PrivateKey.fromHexString(myPrivateKey))).getAddress().getEncoded();
			
			out.println(AccountApi.getAccountByAddress(privateKeyToAddress).getEntity().getBalance());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
