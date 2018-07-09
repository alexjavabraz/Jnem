package nem.academy;

import static java.lang.System.out;

import javax.xml.bind.JAXBElement.GlobalScope;

import org.nem.core.model.Account;
import org.nem.core.model.primitive.Amount;
import org.nem.core.node.NodeEndpoint;

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

public class CreateAccount {
	
	private static final String ACCOUNT_="TDOQ2JXHF64QREQRM3RIMRSDVO7NYKD2ARJDQJN5";
	private static final String ACCOUNT_TRANSFER_TO = "TDLUE6PJPQPTYEVBOSS4GNZKW4VRM7H76DRSRGON";
	private static final int totalSecondsTransactionTimeout = 600;
	private static final String myPrivateKey = "6dc7873ca6d3d2e7be2baa22573e9963b1ada5a54778705e286fd54499a3f6fd";
	private static final String ACCOUNT_TRANSFER_TO_PUBLIC_KEY = "a779dc736d699bf5279b41b21dc1dbcde73cc89a89a1ec31c2256d7d410ef461";
			
	
	public static void main(String[] args) {
		configure();
//		createAccount();
//		nodeInfo();
		simpleTransfer2();
		System.exit(0);
	}
	
	public static void configure() {
		ConfigurationBuilder.nodeNetworkName("TestNet")
	    .nodeNetworkProtocol("http")
	    .nodeNetworkUri("50.3.87.123")
	    .nodeNetworkPort("7890")
	    .setup();
	}
	
	public static void createAccount() {
		GeneratedAccount ga = AccountApi.generateAccount();
		out.println(ga.getEncodedAddress());
		out.println(ga.getEncodedPublicKey());
		out.println(ga.getEncodedAddress());
		out.print(ga.getAccount().getAddress());
		out.print(ga.getAccount().getAddress().getPublicKey());
		out.print(ga.getAccount().getAddress().getEncoded());
	}
	
	public static void nodeInfo() {
		ConfigurationManager.getConfigInstance().setProperty("nodeApi.ribbon.listOfServers", "192.168.0.21:7890");
		NodeClient nodeClient = new DefaultNemClientFactory().createNodeClient("nodeApi");
		out.println(nodeClient.info().identity.name);
		
		AccountClient accountClient = new DefaultNemClientFactory().simpleAccountClient("http://192.168.0.21:7890");
		KeyPair newAccount = accountClient.generate();
		out.println(newAccount.address);
		out.println(newAccount.privateKey);
		out.println(newAccount.publicKey);
	}
	
	public static void simpleTransfer() {
		TransactionClient simpleTransactionClient = new DefaultNemClientFactory().simpleTransactionClient("http://192.168.0.21:7890", DefaultNemClientFactory.TEST);
		NemAnnounceResult result = simpleTransactionClient.transferNem(myPrivateKey, ACCOUNT_TRANSFER_TO, 050000, null, totalSecondsTransactionTimeout);
		out.println(result.code);
		out.println(result.message);
		out.println(result.type);
		out.println(result.innerTransactionHash.data);
	}
	
	public static void simpleTransfer2() {

		org.nem.core.crypto.KeyPair senderPrivateKeyPair,recipientPublicKeyPair;
		
		senderPrivateKeyPair = new org.nem.core.crypto.KeyPair(org.nem.core.crypto.PrivateKey.fromHexString(myPrivateKey));
		recipientPublicKeyPair = new org.nem.core.crypto.KeyPair( org.nem.core.crypto.PublicKey.fromHexString(ACCOUNT_TRANSFER_TO_PUBLIC_KEY));
		
		org.nem.core.model.ncc.NemAnnounceResult result = TransferTransactionBuilder
	    .sender(new Account(senderPrivateKeyPair))
	    .recipient(new Account(recipientPublicKeyPair))
	    .amount(new Amount(1000000))
	    .fee(Amount.fromMicroNem(50000)) // fee
//	    .message("Teste", org.nem.core.model.MessageTypes.PLAIN)
	    .buildAndSendTransaction();
		
		System.out.println(result.getCode());
		System.out.println(result.getTransactionHash());
		System.out.println(result.getMessage());
		System.out.println(result.getTransactionHash().getRaw());
		
	}	

}
