package io.gs2.gold;

import io.gs2.exception.BadRequestException;
import io.gs2.exception.NotFoundException;
import io.gs2.exception.ServiceUnavailableException;
import io.gs2.gold.control.*;
import io.gs2.gold.model.Gold;
import io.gs2.gold.model.WalletSettings;
import io.gs2.model.BasicGs2Credential;
import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Iterator;
import java.util.List;

@RunWith(JUnit4.class)
public class GoldTest2 extends TestCase {

	protected static String CLIENT_ID = "HovFAxYEvg0UMdUue740EQ==";
	protected static String CLIENT_SECRET = "mHczQCWbukiCLd9ldCDRBw==";
	protected static String OWNER_ID = "7dWCSa0w";
	protected static String GOLD_NAME1 = "gold-1001";

	protected static Gs2GoldClient client;
	protected static Gs2GoldPrivateClient pclient;

	@BeforeClass
	public static void startup() {
		Initializer.initialize();
		client = pclient = new Gs2GoldPrivateClient(new BasicGs2Credential(CLIENT_ID, CLIENT_SECRET));

		{
			CreateGoldRequest request = new CreateGoldRequest()
					.withName(GOLD_NAME1)
					.withDescription("Gold 1")
					.withServiceClass("gold1.nano")
					.withBalanceMax(2000L)
					.withResetType("weekly")
					.withResetDayOfMonth(1)                // 無視される
					.withResetDayOfWeek("tuesday")
					.withResetHour(17)
					.withLatestGainMax(450L)
					.withNotificationUrl("http://example.com/");
			try {
				client.createGold(request);
			} catch (BadRequestException e) {
				assertEquals(e.getErrors().size(), 1);
				assertEquals(e.getErrors().get(0).getComponent(), "name");
				assertEquals(e.getErrors().get(0).getMessage(), "gold.gold.name.error.duplicate");
			}
		}

		do {
			GetGoldStatusRequest request = new GetGoldStatusRequest()
					.withGoldName(GOLD_NAME1);
			GetGoldStatusResult result = client.getGoldStatus(request);
			String status = result.getStatus();
			if (status.equals("ACTIVE")) break;
			assertNotSame(status, "DELETED");
			try {
				Thread.sleep(1000 * 3);
			} catch (InterruptedException e) { }
		} while(true);
	}

	@Test
	public void testCreateGoldDuplicate() {
		CreateGoldRequest request = new CreateGoldRequest()
				.withName(GOLD_NAME1)
				.withServiceClass("gold1.nano");
		try {
			client.createGold(request);
			assertTrue(false);
		} catch (BadRequestException e) {
			// ok_(emessage.startswith("BadRequest:"))
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "name");
			assertEquals(e.getErrors().get(0).getMessage(), "gold.gold.name.error.duplicate");
		}
	}

	@Test
	public void testGetGoldGoldNameNone() {
		{
			GetGoldRequest request = new GetGoldRequest();
			//                 .withGoldName(GOLD_NAME1)
			try {
				client.getGold(request);
				assertTrue(false);
			} catch (BadRequestException e) {
				// ok_(emessage.startswith("BadRequest:"))
				assertEquals(e.getErrors().size(), 1);
				assertEquals(e.getErrors().get(0).getComponent(), "goldName");
				assertEquals(e.getErrors().get(0).getMessage(), "gold.gold.goldName.error.require");
			}
		}

		{
			GetGoldRequest request = new GetGoldRequest()
					.withGoldName("");
			try {
				client.getGold(request);
				assertTrue(false);
			} catch (BadRequestException e) {
				// ok_(emessage.startswith("BadRequest:"))
				assertEquals(e.getErrors().size(), 1);
				assertEquals(e.getErrors().get(0).getComponent(), "goldName");
				assertEquals(e.getErrors().get(0).getMessage(), "gold.gold.goldName.error.require");
			}
		}
	}

	@Test
	public void testGetGoldGoldNameInvalid() {
		GetGoldRequest request = new GetGoldRequest()
				.withGoldName("invalid");
		try {
			client.getGold(request);
			assertTrue(false);
		} catch (NotFoundException e) {
			// ok_(emessage.startswith("NotFound:"))
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "gold");
			assertEquals(e.getErrors().get(0).getMessage(), "gold.gold.error.notFound");
		}
	}

	@Test
	public void testGetWalletSettingsGoldNameNone() {
		{
			GetWalletSettingsRequest request = new GetWalletSettingsRequest();
			//                 .withGoldName(GOLD_NAME1)
			try {
				client.getWalletSettings(request);
				assertTrue(false);
			} catch (BadRequestException e) {
				// ok_(emessage.startswith("BadRequest:"))
				assertEquals(e.getErrors().size(), 1);
				assertEquals(e.getErrors().get(0).getComponent(), "goldName");
				assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.require");
			}
		}

		{
			GetWalletSettingsRequest request = new GetWalletSettingsRequest()
					.withGoldName("");
			try {
				client.getWalletSettings(request);
				assertTrue(false);
			} catch (BadRequestException e) {
				// ok_(emessage.startswith("BadRequest:"))
				assertEquals(e.getErrors().size(), 1);
				assertEquals(e.getErrors().get(0).getComponent(), "goldName");
				assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.require");
			}
		}
	}

	@Test
	public void testGetWalletSettingsGoldNameInvalid() {
		GetWalletSettingsRequest request = new GetWalletSettingsRequest()
				.withGoldName("invalid");
		try {
			client.getWalletSettings(request);
			assertTrue(false);
		} catch (NotFoundException e) {
			// ok_(emessage.startswith("NotFound:"))
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "gold");
			assertEquals(e.getErrors().get(0).getMessage(), "gold.gold.error.notFound");
		}
	}

	@Test
	public void testUpdateGoldGoldNameNone() {
		{
			UpdateGoldRequest request = new UpdateGoldRequest()
					//                 .withgoldName(GOLD_NAME1)
					.withServiceClass("gold1.micro")
					.withNotificationUrl("https://example.com/");
			try {
				client.updateGold(request);
				assertTrue(false);
			} catch (BadRequestException e) {
				// ok_(emessage.startswith("BadRequest:"))
				assertEquals(e.getErrors().size(), 1);
				assertEquals(e.getErrors().get(0).getComponent(), "goldName");
				assertEquals(e.getErrors().get(0).getMessage(), "gold.gold.goldName.error.require");
			}
		}

		{
			UpdateGoldRequest request = new UpdateGoldRequest()
					.withGoldName("")
					.withServiceClass("gold1.micro")
					.withNotificationUrl("https://example.com/");
			try {
				client.updateGold(request);
				assertTrue(false);
			} catch (BadRequestException e) {
				// ok_(emessage.startswith("BadRequest:"))
				assertEquals(e.getErrors().size(), 1);
				assertEquals(e.getErrors().get(0).getComponent(), "goldName");
				assertEquals(e.getErrors().get(0).getMessage(), "gold.gold.goldName.error.require");
			}
		}
	}

	@Test
	public void testUpdateGoldServiceClassNone() {
		{
			UpdateGoldRequest request = new UpdateGoldRequest()
					.withGoldName(GOLD_NAME1)
					//                 .withserviceClass("gold1.micro")
					.withNotificationUrl("https://example.com/");
			try {
				client.updateGold(request);
				assertTrue(false);
			} catch (BadRequestException e) {
				// ok_(emessage.startswith("BadRequest:"))
				assertEquals(e.getErrors().size(), 1);
				assertEquals(e.getErrors().get(0).getComponent(), "serviceClass");
				assertEquals(e.getErrors().get(0).getMessage(), "gold.gold.serviceClass.error.require");
			}
		}

		{
			UpdateGoldRequest request = new UpdateGoldRequest()
					.withGoldName(GOLD_NAME1)
					.withServiceClass("")
					.withNotificationUrl("https://example.com/");
			try {
				client.updateGold(request);
				assertTrue(false);
			} catch (BadRequestException e) {
				// ok_(emessage.startswith("BadRequest:"))
				assertEquals(e.getErrors().size(), 1);
				assertEquals(e.getErrors().get(0).getComponent(), "serviceClass");
				assertEquals(e.getErrors().get(0).getMessage(), "gold.gold.serviceClass.error.require");
			}
		}
	}

	@Test
	public void testUpdateGoldServiceClassInvalid() {
		UpdateGoldRequest request = new UpdateGoldRequest()
				.withGoldName(GOLD_NAME1)
				.withServiceClass("invalid")
				.withNotificationUrl("https://example.com/");
		try {
			client.updateGold(request);
			assertTrue(false);
		} catch (BadRequestException e) {
			// ok_(emessage.startswith("BadRequest:"))
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "serviceClass");
			assertEquals(e.getErrors().get(0).getMessage(), "gold.gold.serviceClass.error.invalid");
		}
	}

	@Test
	public void testUpdateGoldNotificationUrlInvalid() {
		UpdateGoldRequest request = new UpdateGoldRequest()
				.withGoldName(GOLD_NAME1)
				.withServiceClass("gold1.micro")
				.withNotificationUrl("invalid");
		try {
			client.updateGold(request);
			assertTrue(false);
		} catch (BadRequestException e) {
			// ok_(emessage.startswith("BadRequest:"))
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "notificationUrl");
			assertEquals(e.getErrors().get(0).getMessage(), "gold.gold.notificationUrl.error.invalid");
		}
	}

	@AfterClass
	public static void shutdown() {
	}
}
