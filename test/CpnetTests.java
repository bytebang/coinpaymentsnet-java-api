import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.coinpayments.CoinPaymentsAPI;
import net.coinpayments.CoinPaymentsAPI.CoinpaymentsApiCallException;

/**
 * Testcases for the coinpayment api. Please ensure that your 
 * cinfiguration allows the call of all APIs
 * @author gue
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CpnetTests
{

	CoinPaymentsAPI api = null;
	private static String statusUrl = "";
	private static String transactionId = "";
	
	@Before
	public void onStartup()
	{

		// Your keys go in here 
		String public_key = "YOUR_PUBLIC_API_KEY";
		String private_key = "YOUR_PRIVATE_API_KEY";
		
		
		if(public_key.equals("YOUR_PUBLIC_API_KEY") || private_key.equals("YOUR_PRIVATE_API_KEY"))		
		{
			throw new IllegalStateException("Please enter your public/private API keys into the unittest");
		}
		this.api = new CoinPaymentsAPI(public_key, private_key);
	}
	
	@Test(expected=CoinpaymentsApiCallException.class)
	public void test1_NotExistingFunction()
	{
		// Be aware: If you do this too often then you may be blocked for a few minutes
		api.call("nonExistingFunction");
	}

	
	@Test
	public void test2_GetBitcoinName()
	{
		JsonObject j = api.set("accepted", 1).call("rates");
		String coinname = j.get("BTC").getAsJsonObject().get("name").getAsString();
		assertTrue(coinname.equals("Bitcoin"));
	}
	
	@Test
	public void test3_GetAcceptedCoins()
	{
		JsonObject j = api.set("accepted", 1).call("rates");
		List<String> supportedCoins = new ArrayList<>();
		
		for(Entry<String, JsonElement> e : j.entrySet())
		{
			JsonObject coin = e.getValue().getAsJsonObject();
			supportedCoins.add(coin.get("name").getAsString());
		}

		System.out.println("The following coins are supported " + supportedCoins.toString());
		assertTrue(supportedCoins.size() > 0);
	}
	
	@Test
	public void test4_CreateTransaction()
	{
		// Create the transaction
		JsonObject tx = api.set("amount",  314.15)
							.set("currency1", "EUR")
							.set("currency2", "BTC")
							.set("item_name", "Sample Item")
							.set("item_number", "007-4711-0815")
							.call("create_transaction");
		
		CpnetTests.statusUrl = tx.get("status_url").getAsString();
		assertTrue(CpnetTests.statusUrl !=  null);
		assertTrue(CpnetTests.statusUrl.length() > 0);
		System.out.println("Status URL = " + statusUrl);
		
		CpnetTests.transactionId = tx.get("txn_id").getAsString();
		assertTrue(CpnetTests.transactionId !=  null);
		assertTrue(CpnetTests.transactionId.length() > 0);
		System.out.println("Transaction id = " + transactionId);
	}
	
	@Test
	public void test5_CreateTransactionDetails()
	{
		// Create the transaction
		JsonObject txinfo = api.set("txid", CpnetTests.transactionId).call("get_tx_info");
		assertTrue(txinfo != null);
		
		// Look if something has been paid already
		float amount = txinfo.get("amountf").getAsFloat();
		float received = txinfo.get("receivedf").getAsFloat();
		float remeaning = amount - received;
		
		String info = "We have received " + received + " of " + amount + " Coins. Please transfer the reameaning " + remeaning + " coins within the next few minutes to the address " + txinfo.get("payment_address").getAsString();
		System.out.println(info);
	}
}
