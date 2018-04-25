package io.gs2.gold;

import io.gs2.exception.ServiceUnavailableException;
import io.gs2.gold.control.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import io.gs2.exception.BadRequestException;
import io.gs2.exception.NotFoundException;
import io.gs2.gold.model.Gold;
import io.gs2.gold.model.WalletSettings;
import io.gs2.model.BasicGs2Credential;
import junit.framework.TestCase;

import java.util.Iterator;
import java.util.List;

@RunWith(JUnit4.class)
public class GoldTest extends TestCase {

	protected static String CLIENT_ID = "HovFAxYEvg0UMdUue740EQ==";
	protected static String CLIENT_SECRET = "mHczQCWbukiCLd9ldCDRBw==";
	protected static String OWNER_ID = "7dWCSa0w";
	protected static String GOLD_NAME1 = "gold-0001";
	protected static String GOLD_NAME2 = "gold-0002";
	protected static String GOLD_NAME3 = "gold-0003";
	protected static String GOLD_NAME4 = "gold-0004";

	protected static Gs2GoldClient client;
	protected static Gs2GoldPrivateClient pclient;

	protected static void ensureGoldEmpty() {
		List<Gold> items;

		{
			DescribeGoldRequest request = new DescribeGoldRequest();
			DescribeGoldResult result = client.describeGold(request);

			items = result.getItems();
		}

		for (Iterator<Gold> itr = items.iterator(); itr.hasNext(); ) {
			Gold item = itr.next();

			do {
				GetGoldStatusRequest request = new GetGoldStatusRequest()
						.withGoldName(item.getName());
				GetGoldStatusResult result = client.getGoldStatus(request);
				String status = result.getStatus();
				if (status.equals("ACTIVE")) break;
				assertNotSame(status, "DELETED");
				try {
					Thread.sleep(1000 * 3);
				} catch (InterruptedException e) { }
			} while(true);

			{
				DeleteGoldRequest request = new DeleteGoldRequest()
						.withGoldName(item.getName());
				// DeleteGoldResult result = client.deleteGold(request);
				client.deleteGold(request);
			}

			do {
				GetGoldStatusRequest request = new GetGoldStatusRequest()
						.withGoldName(item.getName());
				GetGoldStatusResult result = client.getGoldStatus(request);
				String status = result.getStatus();
				if (status.equals("DELETED")) break;
				try {
					Thread.sleep(1000 * 3);
				} catch (InterruptedException e) { }
			} while(true);
		}
	}

	protected static void ensureGoldExists() {
		CreateGoldRequest request = new CreateGoldRequest()
				.withName(GOLD_NAME1)
				.withDescription("Gold 1")
				.withServiceClass("gold1.nano")
				.withBalanceMax(2000)
				.withResetType("weekly")
				.withResetDayOfMonth(0)
				.withResetDayOfWeek("tuesday")
				.withResetHour(17)
				.withLatestGainMax(450)
				.withNotificationUrl("http://example.com/");
		try {
			client.createGold(request);
		} catch (BadRequestException e) {
		}
	}

	@BeforeClass
	public static void startup() {
		Gs2GoldClient.ENDPOINT = "gold-dev";
		client = pclient = new Gs2GoldPrivateClient(new BasicGs2Credential(CLIENT_ID, CLIENT_SECRET));
	}
	
	@Test
	public void basic() {
		ensureGoldEmpty();

		Gold gold1, gold2;

		{
			CreateGoldRequest request = new CreateGoldRequest()
					.withName(GOLD_NAME1)
					.withDescription("Gold 1")
					.withServiceClass("gold1.nano")
					.withBalanceMax(2000)
					.withResetType("weekly")
					.withResetDayOfMonth(0)
					.withResetDayOfWeek("tuesday")
					.withResetHour(17)
					.withLatestGainMax(450)
					.withNotificationUrl("http://example.com/");
			CreateGoldResult result = client.createGold(request);
			assertNotNull(result);
			gold1 = result.getItem();
			assertNotNull(gold1);
			assertEquals(gold1.getOwnerId(), OWNER_ID);
			assertEquals(gold1.getName(), GOLD_NAME1);
			assertEquals(gold1.getDescription(), "Gold 1");
			assertEquals(gold1.getServiceClass(), "gold1.nano");
			assertEquals(gold1.getBalanceMax(), Integer.valueOf(2000));
			assertEquals(gold1.getResetType(), "weekly");
			assertEquals(gold1.getResetDayOfWeek(), "tuesday");
			assertEquals(gold1.getResetHour(), Integer.valueOf(17));
			assertEquals(gold1.getLatestGainMax(), Integer.valueOf(450));
			assertEquals(gold1.getNotificationUrl(), "http://example.com/");
			assertNotNull(gold1.getCreateAt());
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

		{
			CreateGoldRequest request = new CreateGoldRequest()
					.withName(GOLD_NAME2)
					.withServiceClass("gold1.nano");
			CreateGoldResult result = client.createGold(request);
			assertNotNull(result);
			gold2 = result.getItem();
			assertNotNull(gold2);
			assertEquals(gold2.getOwnerId(), OWNER_ID);
			assertEquals(gold2.getName(), GOLD_NAME2);
			assertNull(gold2.getDescription());
			assertEquals(gold2.getServiceClass(), "gold1.nano");
			assertEquals(gold2.getBalanceMax(), Integer.valueOf(99999999));
			assertEquals(gold2.getResetType(), "none");
			assertNull(gold2.getResetDayOfMonth());
			assertNull(gold2.getResetDayOfWeek());
			assertNull(gold2.getResetHour());
			assertEquals(gold2.getLatestGainMax(), Integer.valueOf(99999999));
			assertNull(gold2.getNotificationUrl());
			assertNotNull(gold2.getCreateAt());
		}

		do {
			GetGoldStatusRequest request = new GetGoldStatusRequest()
					.withGoldName(GOLD_NAME2);
			GetGoldStatusResult result = client.getGoldStatus(request);
			String status = result.getStatus();
			if (status.equals("ACTIVE")) break;
			assertNotSame(status, "DELETED");
			try {
				Thread.sleep(1000 * 3);
			} catch (InterruptedException e) { }
		} while(true);

		{
			GetGoldRequest request = new GetGoldRequest()
					.withGoldName(GOLD_NAME1);
			GetGoldResult result = client.getGold(request);
			assertNotNull(result);
			gold1 = result.getItem();
			assertNotNull(gold1);
			assertEquals(gold1.getOwnerId(), OWNER_ID);
			assertEquals(gold1.getName(), GOLD_NAME1);
			assertEquals(gold1.getDescription(), "Gold 1");
			assertEquals(gold1.getServiceClass(), "gold1.nano");
			assertEquals(gold1.getBalanceMax(), Integer.valueOf(2000));
			assertEquals(gold1.getResetType(), "weekly");
			assertEquals(gold1.getResetDayOfWeek(), "tuesday");
			assertEquals(gold1.getResetHour(), Integer.valueOf(17));
			assertEquals(gold1.getLatestGainMax(), Integer.valueOf(450));
			assertEquals(gold1.getNotificationUrl(), "http://example.com/");
			assertNotNull(gold1.getCreateAt());
		}

		{
			GetWalletSettingsRequest request = new GetWalletSettingsRequest()
					.withGoldName(GOLD_NAME1);
			GetWalletSettingsResult result = client.getWalletSettings(request);
			assertNotNull(result);
			WalletSettings walletSettings1 = result.getItem();
			assertNotNull(walletSettings1);
			assertEquals(walletSettings1.getBalanceMax(), Integer.valueOf(2000));
			assertEquals(walletSettings1.getResetType(), "weekly");
			assertEquals(walletSettings1.getResetDayOfWeek(), "tuesday");
			assertEquals(walletSettings1.getResetHour(), Integer.valueOf(17));
			assertEquals(walletSettings1.getLatestGainMax(), Integer.valueOf(450));
			assertNotNull(walletSettings1.getCreateAt());
		}

		{
			UpdateGoldRequest request = new UpdateGoldRequest()
					.withGoldName(GOLD_NAME1)
					.withServiceClass("gold1.micro")
					.withResetType("none");
			UpdateGoldResult result = client.updateGold(request);
			assertNotNull(result);
			gold1 = result.getItem();
			assertNotNull(gold1);
			assertEquals(gold1.getOwnerId(), OWNER_ID);
			assertEquals(gold1.getName(), GOLD_NAME1);
			assertNull(gold1.getDescription());
			assertEquals(gold1.getServiceClass(), "gold1.micro");
			assertEquals(gold1.getBalanceMax(), Integer.valueOf(99999999));
			assertEquals(gold1.getResetType(), "none");
			assertNull(gold1.getResetDayOfMonth());
			assertNull(gold1.getResetDayOfWeek());
			assertNull(gold1.getResetHour());
			assertEquals(gold1.getLatestGainMax(), Integer.valueOf(99999999));
			assertNull(gold1.getNotificationUrl());
			assertNotNull(gold1.getCreateAt());
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

		{
			DescribeGoldByOwnerIdRequest request = new DescribeGoldByOwnerIdRequest()
					.withOwnerId(OWNER_ID);
			DescribeGoldByOwnerIdResult result = pclient.describeGoldByOwnerId(request);
			assertNotNull(result.getItems());
			assertEquals(result.getItems().size(), 2);
			if (gold2.getGoldId().equals(result.getItems().get(0).getGoldId())) {
				Gold t = gold1;
				gold1 = gold2;
				gold2 = t;
			}
			assertEquals(result.getItems().get(0).getOwnerId(), gold1.getOwnerId());
			assertEquals(result.getItems().get(0).getName(), gold1.getName());
			assertEquals(result.getItems().get(0).getDescription(), gold1.getDescription());
			assertEquals(result.getItems().get(0).getServiceClass(), gold1.getServiceClass());
			assertEquals(result.getItems().get(0).getBalanceMax(), gold1.getBalanceMax());
			assertEquals(result.getItems().get(0).getResetType(), gold1.getResetType());
			assertEquals(result.getItems().get(0).getResetDayOfWeek(), gold1.getResetDayOfWeek());
			assertEquals(result.getItems().get(0).getResetHour(), gold1.getResetHour());
			assertEquals(result.getItems().get(0).getLatestGainMax(), gold1.getLatestGainMax());
			assertEquals(result.getItems().get(0).getNotificationUrl(), gold1.getNotificationUrl());
			assertNotNull(result.getItems().get(0).getCreateAt());
			assertEquals(result.getItems().get(1).getOwnerId(), gold2.getOwnerId());
			assertEquals(result.getItems().get(1).getName(), gold2.getName());
			assertEquals(result.getItems().get(1).getDescription(), gold2.getDescription());
			assertEquals(result.getItems().get(1).getServiceClass(), gold2.getServiceClass());
			assertEquals(result.getItems().get(1).getBalanceMax(), gold2.getBalanceMax());
			assertEquals(result.getItems().get(1).getResetType(), gold2.getResetType());
			assertEquals(result.getItems().get(1).getResetDayOfWeek(), gold2.getResetDayOfWeek());
			assertEquals(result.getItems().get(1).getResetHour(), gold2.getResetHour());
			assertEquals(result.getItems().get(1).getLatestGainMax(), gold2.getLatestGainMax());
			assertEquals(result.getItems().get(1).getNotificationUrl(), gold2.getNotificationUrl());
			assertNotNull(result.getItems().get(1).getCreateAt());
			assertNull(result.getNextPageToken());
		}

		{
			DescribeGoldRequest request = new DescribeGoldRequest();
			DescribeGoldResult result = client.describeGold(request);
			assertNotNull(result);
			if(gold2.getGoldId().equals(result.getItems().get(0).getGoldId())) {
				Gold t = gold1;
				gold1 = gold2;
				gold2 = t;
			}
			//assertEquals(result.getCount(), Integer.valueOf(2));
			assertEquals(result.getItems().size(), 2);
			assertEquals(result.getItems().get(0).getOwnerId(), gold1.getOwnerId());
			assertEquals(result.getItems().get(0).getName(), gold1.getName());
			assertEquals(result.getItems().get(0).getDescription(), gold1.getDescription());
			assertEquals(result.getItems().get(0).getServiceClass(), gold1.getServiceClass());
			assertEquals(result.getItems().get(0).getBalanceMax(), gold1.getBalanceMax());
			assertEquals(result.getItems().get(0).getResetType(), gold1.getResetType());
			assertEquals(result.getItems().get(0).getResetDayOfWeek(), gold1.getResetDayOfWeek());
			assertEquals(result.getItems().get(0).getResetHour(), gold1.getResetHour());
			assertEquals(result.getItems().get(0).getLatestGainMax(), gold1.getLatestGainMax());
			assertEquals(result.getItems().get(0).getNotificationUrl(), gold1.getNotificationUrl());
			assertNotNull(result.getItems().get(0).getCreateAt());
			assertEquals(result.getItems().get(1).getOwnerId(), gold2.getOwnerId());
			assertEquals(result.getItems().get(1).getName(), gold2.getName());
			assertEquals(result.getItems().get(1).getDescription(), gold2.getDescription());
			assertEquals(result.getItems().get(1).getServiceClass(), gold2.getServiceClass());
			assertEquals(result.getItems().get(1).getBalanceMax(), gold2.getBalanceMax());
			assertEquals(result.getItems().get(1).getResetType(), gold2.getResetType());
			assertEquals(result.getItems().get(1).getResetDayOfWeek(), gold2.getResetDayOfWeek());
			assertEquals(result.getItems().get(1).getResetHour(), gold2.getResetHour());
			assertEquals(result.getItems().get(1).getLatestGainMax(), gold2.getLatestGainMax());
			assertEquals(result.getItems().get(1).getNotificationUrl(), gold2.getNotificationUrl());
			assertNotNull(result.getItems().get(1).getCreateAt());
			assertNull(result.getNextPageToken());
		}

		{
			String nextPageToken;

			{
				DescribeGoldRequest request = new DescribeGoldRequest()
						.withLimit(1);
				DescribeGoldResult result = client.describeGold(request);
				assertNotNull(result);
				//assertEquals(result.getCount(), Integer.valueOf(1));
				assertEquals(result.getItems().size(), 1);
				assertEquals(result.getItems().get(0).getOwnerId(), gold1.getOwnerId());
				assertEquals(result.getItems().get(0).getName(), gold1.getName());
				assertEquals(result.getItems().get(0).getDescription(), gold1.getDescription());
				assertEquals(result.getItems().get(0).getServiceClass(), gold1.getServiceClass());
				assertEquals(result.getItems().get(0).getBalanceMax(), gold1.getBalanceMax());
				assertEquals(result.getItems().get(0).getResetType(), gold1.getResetType());
				assertEquals(result.getItems().get(0).getResetDayOfWeek(), gold1.getResetDayOfWeek());
				assertEquals(result.getItems().get(0).getResetHour(), gold1.getResetHour());
				assertEquals(result.getItems().get(0).getLatestGainMax(), gold1.getLatestGainMax());
				assertEquals(result.getItems().get(0).getNotificationUrl(), gold1.getNotificationUrl());
				assertNotNull(result.getItems().get(0).getCreateAt());
				assertNotNull(result.getNextPageToken());

				nextPageToken = result.getNextPageToken();
			}

			{
				DescribeGoldRequest request = new DescribeGoldRequest()
						.withPageToken(nextPageToken);
				DescribeGoldResult result = client.describeGold(request);
				assertNotNull(result);
				//assertEquals(result.getCount(), Integer.valueOf(1));
				assertEquals(result.getItems().size(), 1);
				assertEquals(result.getItems().get(0).getOwnerId(), gold2.getOwnerId());
				assertEquals(result.getItems().get(0).getName(), gold2.getName());
				assertEquals(result.getItems().get(0).getDescription(), gold2.getDescription());
				assertEquals(result.getItems().get(0).getServiceClass(), gold2.getServiceClass());
				assertEquals(result.getItems().get(0).getBalanceMax(), gold2.getBalanceMax());
				assertEquals(result.getItems().get(0).getResetType(), gold2.getResetType());
				assertEquals(result.getItems().get(0).getResetDayOfWeek(), gold2.getResetDayOfWeek());
				assertEquals(result.getItems().get(0).getResetHour(), gold2.getResetHour());
				assertEquals(result.getItems().get(0).getLatestGainMax(), gold2.getLatestGainMax());
				assertEquals(result.getItems().get(0).getNotificationUrl(), gold2.getNotificationUrl());
				assertNotNull(result.getItems().get(0).getCreateAt());
				assertNull(result.getNextPageToken());
			}
		}

		{
			List<Gold> items;

			{
				DescribeGoldRequest request = new DescribeGoldRequest();
				DescribeGoldResult result = client.describeGold(request);

				items = result.getItems();
			}

			for (Iterator<Gold> itr = items.iterator(); itr.hasNext(); ) {
				Gold item = itr.next();

				do {
					GetGoldStatusRequest request = new GetGoldStatusRequest()
							.withGoldName(item.getName());
					GetGoldStatusResult result = client.getGoldStatus(request);
					String status = result.getStatus();
					if (status.equals("ACTIVE")) break;
					assertNotSame(status, "DELETED");
					try {
						Thread.sleep(1000 * 3);
					} catch (InterruptedException e) { }
				} while(true);

				{
					DeleteGoldRequest request = new DeleteGoldRequest()
							.withGoldName(item.getName());
					// DeleteGoldResult result = client.deleteGold(request);
					client.deleteGold(request);
				}

				do {
					GetGoldStatusRequest request = new GetGoldStatusRequest()
							.withGoldName(item.getName());
					GetGoldStatusResult result = client.getGoldStatus(request);
					String status = result.getStatus();
					if (status.equals("DELETED")) break;
					try {
						Thread.sleep(1000 * 3);
					} catch (InterruptedException e) { }
				} while(true);
			}
		}
	}

	@Test
	public void testCreateGoldNameNone() {
		ensureGoldEmpty();

		{
			CreateGoldRequest request = new CreateGoldRequest()
					//         .withName(GOLD_NAME1)
					.withServiceClass("gold1.nano")
					.withResetType("none");
			try {
				client.createGold(request);
				assertTrue(false);
			} catch (BadRequestException e) {
				// ok_(emessage.startswith("BadRequest:"))
				assertEquals(e.getErrors().size(), 1);
				assertEquals(e.getErrors().get(0).getComponent(), "name");
				assertEquals(e.getErrors().get(0).getMessage(), "gold.gold.name.error.require");
			}
		}

		{
			CreateGoldRequest request = new CreateGoldRequest()
					.withName("")
					.withServiceClass("gold1.nano")
					.withResetType("none");
			try {
				client.createGold(request);
				assertTrue(false);
			} catch (BadRequestException e) {
				// ok_(emessage.startswith("BadRequest:"))
				assertEquals(e.getErrors().size(), 1);
				assertEquals(e.getErrors().get(0).getComponent(), "name");
				assertEquals(e.getErrors().get(0).getMessage(), "gold.gold.name.error.require");
			}
		}
	}

	@Test
	public void testCreateGoldNameInvalid() {
		ensureGoldEmpty();

		CreateGoldRequest request = new CreateGoldRequest()
				.withName("#")
				.withServiceClass("gold1.nano")
				.withResetType("none");
		try {
			client.createGold(request);
			assertTrue(false);
		} catch (BadRequestException e) {
			// ok_(emessage.startswith("BadRequest:"))
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "name");
			assertEquals(e.getErrors().get(0).getMessage(), "gold.gold.name.error.invalid");
		}
	}

	@Test
	public void testCreateGoldServiceClassNone() {
		ensureGoldEmpty();

		{
			CreateGoldRequest request = new CreateGoldRequest()
					.withName(GOLD_NAME1)
					//                 .withServiceClass("gold1.nano")
					.withResetType("none");
			try {
				client.createGold(request);
				assertTrue(false);
			} catch (BadRequestException e) {
				// ok_(emessage.startswith("BadRequest:"))
				assertEquals(e.getErrors().size(), 1);
				assertEquals(e.getErrors().get(0).getComponent(), "serviceClass");
				assertEquals(e.getErrors().get(0).getMessage(), "gold.gold.serviceClass.error.require");
			}
		}

		{
			CreateGoldRequest request = new CreateGoldRequest()
					.withName(GOLD_NAME1)
					.withServiceClass("")
					.withResetType("none");
			try {
				client.createGold(request);
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
	public void testCreateGoldServiceClassInvalid() {
		ensureGoldEmpty();

		CreateGoldRequest request = new CreateGoldRequest()
				.withName(GOLD_NAME1)
				.withServiceClass("invalid")
				.withResetType("none");
		try {
			client.createGold(request);
			assertTrue(false);
		} catch (BadRequestException e) {
			// ok_(emessage.startswith("BadRequest:"))
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "serviceClass");
			assertEquals(e.getErrors().get(0).getMessage(), "gold.gold.serviceClass.error.invalid");
		}
	}

	@Test
	public void testCreateGoldNotificationUrlInvalid() {
		ensureGoldEmpty();

		CreateGoldRequest request = new CreateGoldRequest()
				.withName(GOLD_NAME1)
				.withServiceClass("gold1.nano")
				.withResetType("none")
				.withNotificationUrl("invalid");
		try {
			client.createGold(request);
			assertTrue(false);
		} catch (BadRequestException e) {
			// ok_(emessage.startswith("BadRequest:"))
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "notificationUrl");
			assertEquals(e.getErrors().get(0).getMessage(), "gold.gold.notificationUrl.error.invalid");
		}
	}

	@Test
	public void testDeleteGoldNameNone() {
		ensureGoldEmpty();

		{
			DeleteGoldRequest request = new DeleteGoldRequest();
			//                 .withGoldName(GOLD_NAME1)
			try {
				client.deleteGold(request);
				assertTrue(false);
			} catch (BadRequestException e) {
				// ok_(emessage.startswith("BadRequest:"))
				assertEquals(e.getErrors().size(), 1);
				assertEquals(e.getErrors().get(0).getComponent(), "goldName");
				assertEquals(e.getErrors().get(0).getMessage(), "gold.gold.goldName.error.require");
			}
		}

		{
			DeleteGoldRequest request = new DeleteGoldRequest()
					.withGoldName("");
			try {
				client.deleteGold(request);
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
	public void testDeleteGoldNameInvalid() {
		ensureGoldEmpty();

		DeleteGoldRequest request = new DeleteGoldRequest()
				.withGoldName("invalid");
		try {
			client.deleteGold(request);
			assertTrue(false);
		} catch (NotFoundException e) {
			// ok_(emessage.startswith("NotFound:"))
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "gold");
			assertEquals(e.getErrors().get(0).getMessage(), "gold.gold.error.notFound");
		}
	}

	@Test
	public void testDeleteGoldGoldNotActive() {
		// 専用の GOLD_NAME3 しか操作しないので前提条件不要

		{
			CreateGoldRequest request = new CreateGoldRequest()
					.withName(GOLD_NAME3)
					.withServiceClass("gold1.nano")
					.withResetType("none");
			client.createGold(request);
		}

		try {
			DeleteGoldRequest request = new DeleteGoldRequest()
					.withGoldName(GOLD_NAME3);
			client.deleteGold(request);
			assertTrue(false);
		} catch (ServiceUnavailableException e) {
			// ServiceUnavailableException は中身なし
		}

		do {
			GetGoldStatusRequest request = new GetGoldStatusRequest()
					.withGoldName(GOLD_NAME3);
			GetGoldStatusResult result = client.getGoldStatus(request);
			String status = result.getStatus();
			if (status.equals("ACTIVE")) break;
			assertNotSame(status, "DELETED");
			try {
				Thread.sleep(1000 * 3);
			} catch (InterruptedException e) { }
		} while(true);

		DeleteGoldRequest request = new DeleteGoldRequest()
				.withGoldName(GOLD_NAME3);
		// DeleteGoldResult result = client.deleteGold(request);
		client.deleteGold(request);
	}

	@Test
	public void testDescribeGoldByOwnerIdOwnerIdNone() {
		DescribeGoldByOwnerIdRequest request = new DescribeGoldByOwnerIdRequest();
		//		.withOwnerId(OWNER_ID);
		DescribeGoldByOwnerIdResult result = pclient.describeGoldByOwnerId(request);
		List<Gold> items = result.getItems();
		String nextPageToken = result.getNextPageToken();

		assertNull(nextPageToken);
		assertEquals(items.size(), 0);
	}

	@Test
	public void testCreateGoldDuplicate() {
		ensureGoldExists();

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
		ensureGoldExists();

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
		ensureGoldExists();

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
		ensureGoldExists();

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
		ensureGoldExists();

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
		ensureGoldExists();

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
		ensureGoldExists();

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
		ensureGoldExists();

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
		ensureGoldExists();

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

	@Test
	public void testUpdateGoldGoldNotActive() {
		// 専用の GOLD_NAME4 しか操作しないので前提条件不要

		{
			CreateGoldRequest request = new CreateGoldRequest()
					.withName(GOLD_NAME4)
					.withServiceClass("gold1.nano");
			client.createGold(request);
		}

		try {
			UpdateGoldRequest request = new UpdateGoldRequest()
					.withGoldName(GOLD_NAME4)
					.withServiceClass("gold1.micro")
					.withNotificationUrl("");
			client.updateGold(request);
		} catch (ServiceUnavailableException e) {
			// ServiceUnavailableException は中身なし
		}

		do {
			GetGoldStatusRequest request = new GetGoldStatusRequest()
					.withGoldName(GOLD_NAME4);
			GetGoldStatusResult result = client.getGoldStatus(request);
			String status = result.getStatus();
			if (status.equals("ACTIVE")) break;
			try {
				Thread.sleep(1000 * 1);
			} catch (InterruptedException e) { }
		} while(true);

		{
			DeleteGoldRequest request = new DeleteGoldRequest()
					.withGoldName(GOLD_NAME4);
			// DeleteGoldResult result = client.deleteGold(request);
			client.deleteGold(request);
		}
	}

	@AfterClass
	public static void shutdown() {
		ensureGoldEmpty();
	}
}
