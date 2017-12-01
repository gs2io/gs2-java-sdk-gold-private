package io.gs2.gold;

import io.gs2.exception.ServiceUnavailableException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import io.gs2.exception.BadRequestException;
import io.gs2.exception.NotFoundException;
import io.gs2.gold.control.CreateGoldRequest;
import io.gs2.gold.control.CreateGoldResult;
import io.gs2.gold.control.GetGoldStatusRequest;
import io.gs2.gold.control.GetGoldStatusResult;
import io.gs2.gold.control.GetGoldRequest;
import io.gs2.gold.control.GetGoldResult;
import io.gs2.gold.control.UpdateGoldRequest;
import io.gs2.gold.control.UpdateGoldResult;
import io.gs2.gold.control.DescribeGoldRequest;
import io.gs2.gold.control.DescribeGoldResult;
import io.gs2.gold.control.DeleteGoldRequest;
//import io.gs2.gold.control.DeleteGoldResult;
import io.gs2.gold.model.Gold;
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
	
	protected static Gs2GoldClient client;
	// protected static Gs2GoldPrivateClient pclient;

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
				.withRestrictionType("weekly")
				.withResetDayOfMonth(0)
				.withResetDayOfWeek("1")
				.withResetHour(17)
				.withPeriodicalLimit(450)
				.withNotificationUrl("http://example.com/");
		try {
			client.createGold(request);
		} catch (BadRequestException e) {
		}
	}

	@BeforeClass
	public static void startup() {
		Gs2GoldClient.ENDPOINT = "gold-dev";
		//client = pclient = new Gs2GoldPrivateClient(new BasicGs2Credential(CLIENT_ID, CLIENT_SECRET));
		client = new Gs2GoldClient(new BasicGs2Credential(CLIENT_ID, CLIENT_SECRET));
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
					.withRestrictionType("weekly")
					.withResetDayOfMonth(0)
					.withResetDayOfWeek("1")
					.withResetHour(17)
					.withPeriodicalLimit(450)
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
			assertEquals(gold1.getRestrictionType(), "weekly");
			assertEquals(gold1.getResetDayOfWeek(), "1");		// TODO
			assertEquals(gold1.getResetHour(), Integer.valueOf(17));
			assertEquals(gold1.getPeriodicalLimit(), Integer.valueOf(450));
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
			assertEquals(gold2.getRestrictionType(), "none");
			assertNull(gold2.getResetDayOfMonth());
			assertNull(gold2.getResetDayOfWeek());
			assertNull(gold2.getResetHour());
			assertEquals(gold2.getPeriodicalLimit(), Integer.valueOf(99999999));
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
			assertEquals(gold1.getRestrictionType(), "weekly");
			assertEquals(gold1.getResetDayOfWeek(), "1");		// TODO
			assertEquals(gold1.getResetHour(), Integer.valueOf(17));
			assertEquals(gold1.getPeriodicalLimit(), Integer.valueOf(450));
			assertEquals(gold1.getNotificationUrl(), "http://example.com/");
			assertNotNull(gold1.getCreateAt());
		}

		{
			UpdateGoldRequest request = new UpdateGoldRequest()
					.withGoldName(GOLD_NAME1)
					.withServiceClass("gold1.micro")
					.withRestrictionType("none");
			UpdateGoldResult result = client.updateGold(request);
			assertNotNull(result);
			gold1 = result.getItem();
			assertNotNull(gold1);
			assertEquals(gold1.getOwnerId(), OWNER_ID);
			assertEquals(gold1.getName(), GOLD_NAME1);
			assertNull(gold1.getDescription());
			assertEquals(gold1.getServiceClass(), "gold1.micro");
			assertEquals(gold1.getBalanceMax(), Integer.valueOf(99999999));
			assertEquals(gold1.getRestrictionType(), "none");
			assertNull(gold1.getResetDayOfMonth());
			assertNull(gold1.getResetDayOfWeek());
			assertNull(gold1.getResetHour());
			assertEquals(gold1.getPeriodicalLimit(), Integer.valueOf(99999999));
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

		/*
		{
			DescribeGoldByOwnerIdRequest request = new DescribeGoldByOwnerIdRequest()
					.withOwnerId(OWNER_ID);
			DescribeGoldByOwnerIdResult result = client.describeGoldByOwnerId(request);
			items, next_page_token = result["items"], result["nextPageToken"]
			eq_(len(items), 2)

			if items[1]["name"] == gold1["name"]:
			gold1, gold2 = gold2, gold1

			assertEquals(result.getItems().get(0).getOwnerId(), gold1.getOwnerId());
			assertEquals(result.getItems().get(0).getName(), gold1.getName());
			assertEquals(result.getItems().get(0).getDescription(), gold1.getDescription());
			assertEquals(result.getItems().get(0).getServiceClass(), gold1.getServiceClass());
			assertEquals(result.getItems().get(0).getBalanceMax(), gold1.getBalanceMax());
			assertEquals(result.getItems().get(0).getRestrictionType(), gold1.getRestrictionType());
			assertEquals(result.getItems().get(0).getResetDayOfWeek(), gold1.getResetDayOfWeek());
			assertEquals(result.getItems().get(0).getResetHour(), gold1.getResetHour());
			assertEquals(result.getItems().get(0).getPeriodicalLimit(), gold1.getPeriodicalLimit());
			assertEquals(result.getItems().get(0).getNotificationUrl(), gold1.getNotificationUrl());
			ok_(items[0]["createAt"])
			assertEquals(result.getItems().get(1).getOwnerId(), gold2.getOwnerId());
			assertEquals(result.getItems().get(1).getName(), gold2.getName());
			assertEquals(result.getItems().get(1).getDescription(), gold2.getDescription());
			assertEquals(result.getItems().get(1).getServiceClass(), gold2.getServiceClass());
			assertEquals(result.getItems().get(1).getBalanceMax(), gold2.getBalanceMax());
			assertEquals(result.getItems().get(1).getRestrictionType(), gold2.getRestrictionType());
			assertEquals(result.getItems().get(1).getResetDayOfWeek(), gold2.getResetDayOfWeek());
			assertEquals(result.getItems().get(1).getResetHour(), gold2.getResetHour());
			assertEquals(result.getItems().get(1).getPeriodicalLimit(), gold2.getPeriodicalLimit());
			assertEquals(result.getItems().get(1).getNotificationUrl(), gold2.getNotificationUrl());
			ok_(items[1]["createAt"])
			assertNull(next_page_token);
		}
		*/

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
			assertEquals(result.getItems().get(0).getRestrictionType(), gold1.getRestrictionType());
			assertEquals(result.getItems().get(0).getResetDayOfWeek(), gold1.getResetDayOfWeek());
			assertEquals(result.getItems().get(0).getResetHour(), gold1.getResetHour());
			assertEquals(result.getItems().get(0).getPeriodicalLimit(), gold1.getPeriodicalLimit());
			assertEquals(result.getItems().get(0).getNotificationUrl(), gold1.getNotificationUrl());
			assertNotNull(result.getItems().get(0).getCreateAt());
			assertEquals(result.getItems().get(1).getOwnerId(), gold2.getOwnerId());
			assertEquals(result.getItems().get(1).getName(), gold2.getName());
			assertEquals(result.getItems().get(1).getDescription(), gold2.getDescription());
			assertEquals(result.getItems().get(1).getServiceClass(), gold2.getServiceClass());
			assertEquals(result.getItems().get(1).getBalanceMax(), gold2.getBalanceMax());
			assertEquals(result.getItems().get(1).getRestrictionType(), gold2.getRestrictionType());
			assertEquals(result.getItems().get(1).getResetDayOfWeek(), gold2.getResetDayOfWeek());
			assertEquals(result.getItems().get(1).getResetHour(), gold2.getResetHour());
			assertEquals(result.getItems().get(1).getPeriodicalLimit(), gold2.getPeriodicalLimit());
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
				assertEquals(result.getItems().get(0).getRestrictionType(), gold1.getRestrictionType());
				assertEquals(result.getItems().get(0).getResetDayOfWeek(), gold1.getResetDayOfWeek());
				assertEquals(result.getItems().get(0).getResetHour(), gold1.getResetHour());
				assertEquals(result.getItems().get(0).getPeriodicalLimit(), gold1.getPeriodicalLimit());
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
				assertEquals(result.getItems().get(0).getRestrictionType(), gold2.getRestrictionType());
				assertEquals(result.getItems().get(0).getResetDayOfWeek(), gold2.getResetDayOfWeek());
				assertEquals(result.getItems().get(0).getResetHour(), gold2.getResetHour());
				assertEquals(result.getItems().get(0).getPeriodicalLimit(), gold2.getPeriodicalLimit());
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
					.withRestrictionType("none");
			try {
				client.createGold(request);
				assertTrue(false);
			} catch (BadRequestException e) {
				// ok_(emessage.startswith("BadRequest:"))
				assertEquals(e.getErrors().size(), 1);
				assertEquals(e.getErrors().get(0).getComponent(), "name");
				assertEquals(e.getErrors().get(0).getMessage(), "gold.name.error.require");
			}
		}

		{
			CreateGoldRequest request = new CreateGoldRequest()
					.withName("")
					.withServiceClass("gold1.nano")
					.withRestrictionType("none");
			try {
				client.createGold(request);
				assertTrue(false);
			} catch (BadRequestException e) {
				// ok_(emessage.startswith("BadRequest:"))
				assertEquals(e.getErrors().size(), 1);
				assertEquals(e.getErrors().get(0).getComponent(), "name");
				assertEquals(e.getErrors().get(0).getMessage(), "gold.name.error.require");
			}
		}
	}

	@Test
    public void testCreateGoldNameInvalid() {
		ensureGoldEmpty();

		CreateGoldRequest request = new CreateGoldRequest()
                .withName("#")
                .withServiceClass("gold1.nano")
                .withRestrictionType("none");
        try {
            client.createGold(request);
            assertTrue(false);
        } catch (BadRequestException e) {
			// ok_(emessage.startswith("BadRequest:"))
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "name");
			assertEquals(e.getErrors().get(0).getMessage(), "gold.name.error.invalid");
		}
	}

	@Test
    public void testCreateGoldServiceClassNone() {
		ensureGoldEmpty();

		{
			CreateGoldRequest request = new CreateGoldRequest()
					.withName(GOLD_NAME1)
					//                 .withServiceClass("gold1.nano")
					.withRestrictionType("none");
			try {
				client.createGold(request);
				assertTrue(false);
			} catch (BadRequestException e) {
				// ok_(emessage.startswith("BadRequest:"))
				assertEquals(e.getErrors().size(), 1);
				assertEquals(e.getErrors().get(0).getComponent(), "serviceClass");
				assertEquals(e.getErrors().get(0).getMessage(), "gold.serviceClass.error.require");
			}
		}

		{
			CreateGoldRequest request = new CreateGoldRequest()
					.withName(GOLD_NAME1)
					.withServiceClass("")
					.withRestrictionType("none");
			try {
				client.createGold(request);
				assertTrue(false);
			} catch (BadRequestException e) {
				// ok_(emessage.startswith("BadRequest:"))
				assertEquals(e.getErrors().size(), 1);
				assertEquals(e.getErrors().get(0).getComponent(), "serviceClass");
				assertEquals(e.getErrors().get(0).getMessage(), "gold.serviceClass.error.require");
			}
		}
	}

	@Test
    public void testCreateGoldServiceClassInvalid() {
		ensureGoldEmpty();

		CreateGoldRequest request = new CreateGoldRequest()
                .withName(GOLD_NAME1)
                .withServiceClass("invalid")
                .withRestrictionType("none");
        try {
            client.createGold(request);
            assertTrue(false);
        } catch (BadRequestException e) {
			// ok_(emessage.startswith("BadRequest:"))
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "serviceClass");
			assertEquals(e.getErrors().get(0).getMessage(), "gold.serviceClass.error.require");
		}
	}

	@Test
    public void testCreateGoldNotificationUrlInvalid() {
		ensureGoldEmpty();

		CreateGoldRequest request = new CreateGoldRequest()
                .withName(GOLD_NAME1)
                .withServiceClass("gold1.nano")
                .withRestrictionType("none")
                .withNotificationUrl("invalid");
        try {
            client.createGold(request);
            assertTrue(false);
        } catch (BadRequestException e) {
			// ok_(emessage.startswith("BadRequest:"))
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "notificationUrl");
			assertEquals(e.getErrors().get(0).getMessage(), "gold.notificationUrl.error.invalid");
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
				assertEquals(e.getErrors().get(0).getComponent(), "goldId");
				assertEquals(e.getErrors().get(0).getMessage(), "gold.goldId.error.require");
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
				assertEquals(e.getErrors().get(0).getComponent(), "goldId");
				assertEquals(e.getErrors().get(0).getMessage(), "gold.goldId.error.require");
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
		{
			CreateGoldRequest request = new CreateGoldRequest()
					.withName(GOLD_NAME3)
					.withServiceClass("gold1.nano")
					.withRestrictionType("none");
			client.createGold(request);
		}

        try {
            DeleteGoldRequest request = new DeleteGoldRequest()
                    .withGoldName(GOLD_NAME3);
            client.deleteGold(request);
            assertTrue(false);
        } catch (ServiceUnavailableException e) {
			// ok_(emessage.startswith("ServiceUnavailable:"))
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "gold");
			assertEquals(e.getErrors().get(0).getMessage(), "gold.gold.error.notReady");
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
	}

	/*
	public void testDescribeGoldByOwnerIdOwnerIdNone() {
        DescribeGoldRequest request = new DescribeGoldRequest();
        //                 .withOwnerId(OWNER_ID)
        DescribeGoldByOwnerIdResult result = client.describeGoldByOwnerId(request);
	    List<Gold> items = result.getItems();
	    String nextPageToken = result.getNextPageToken();

	    assertNull(nextPageToken);
	    assertEquals(items.size(), 0);
	}
	*/

	@AfterClass
	public static void shutdown() {
		ensureGoldEmpty();
	}
}
