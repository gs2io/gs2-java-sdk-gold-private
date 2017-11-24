package io.gs2.inbox;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import io.gs2.exception.BadRequestException;
import io.gs2.exception.NotFoundException;
import io.gs2.inbox.control.CreateInboxRequest;
import io.gs2.inbox.control.CreateInboxResult;
import io.gs2.inbox.control.DeleteInboxRequest;
import io.gs2.inbox.control.DescribeInboxByOwnerIdRequest;
import io.gs2.inbox.control.DescribeInboxByOwnerIdResult;
import io.gs2.inbox.control.DescribeInboxRequest;
import io.gs2.inbox.control.DescribeInboxResult;
import io.gs2.inbox.control.DescribeServiceClassRequest;
import io.gs2.inbox.control.DescribeServiceClassResult;
import io.gs2.inbox.control.GetInboxRequest;
import io.gs2.inbox.control.GetInboxStatusRequest;
import io.gs2.inbox.control.GetInboxStatusResult;
import io.gs2.inbox.control.UpdateInboxRequest;
import io.gs2.inbox.control.UpdateInboxResult;
import io.gs2.inbox.model.Inbox;
import io.gs2.model.BasicGs2Credential;
import junit.framework.TestCase;

@RunWith(JUnit4.class)
public class InboxTest extends TestCase {

	protected static String CLIENT_ID = "HovFAxYEvg0UMdUue740EQ==";
	protected static String CLIENT_SECRET = "mHczQCWbukiCLd9ldCDRBw==";
	protected static final String OWNER_ID = "7dWCSa0w";
	protected static final String INBOX_NAME = "inbox-0001";
	protected static final String INBOX_NAME2 = "inbox-0002";

	protected static Gs2InboxClient client;
	protected static Gs2InboxPrivateClient pclient;
	
	@BeforeClass
	public static void startup() {
		Gs2InboxClient.ENDPOINT = "inbox-dev";
		client = pclient = new Gs2InboxPrivateClient(new BasicGs2Credential(CLIENT_ID, CLIENT_SECRET));
	}
	
	@Test
	public void basic() {
		Inbox inbox1 = null;
		{
			DescribeServiceClassRequest request = new DescribeServiceClassRequest();
			DescribeServiceClassResult result = client.describeServiceClass(request);
			assertNotNull(result);
			assertNotSame(result.getCount(), 0);
			assertTrue(result.getItems().contains("inbox1.nano"));
		}
		{
			CreateInboxRequest request = new CreateInboxRequest()
					.withName(INBOX_NAME)
					.withServiceClass("inbox1.nano")
					.withAutoDelete(true)
					.withCooperationUrl("http://example.com/");
			CreateInboxResult result = client.createInbox(request);
			assertNotNull(result);
			inbox1 = result.getItem();
			assertNotNull(inbox1);
			assertEquals(inbox1.getOwnerId(), OWNER_ID);
			assertEquals(inbox1.getName(), INBOX_NAME);
			assertEquals(inbox1.getServiceClass(), "inbox1.nano");
			assertTrue(inbox1.getAutoDelete());
			assertEquals(inbox1.getCooperationUrl(), "http://example.com/");
		}
		{
			do {
				GetInboxStatusRequest request = new GetInboxStatusRequest()
						.withInboxName(inbox1.getName());
				GetInboxStatusResult result = client.getInboxStatus(request);
				if(result.getStatus().equals("ACTIVE")) break;
				assertNotSame(result.getStatus(), "DELETED");
				try {
					Thread.sleep(1000 * 5);
				} catch (InterruptedException e) { }
			} while(true);
		}
		Inbox inbox2 = null;
		{
			CreateInboxRequest request = new CreateInboxRequest()
					.withName(INBOX_NAME2)
					.withServiceClass("inbox1.nano")
					.withAutoDelete(false);
			CreateInboxResult result = client.createInbox(request);
			assertNotNull(result);
			inbox2 = result.getItem();
			assertNotNull(inbox2);
			assertEquals(inbox2.getOwnerId(), OWNER_ID);
			assertEquals(inbox2.getName(), INBOX_NAME2);
			assertEquals(inbox2.getServiceClass(), "inbox1.nano");
			assertFalse(inbox2.getAutoDelete());
			assertNull(inbox2.getCooperationUrl());
		}
		try {
			Thread.sleep(1000 * 5);
		} catch (InterruptedException e) { }
		{
			do {
				GetInboxStatusRequest request = new GetInboxStatusRequest()
						.withInboxName(inbox2.getName());
				GetInboxStatusResult result = client.getInboxStatus(request);
				if(result.getStatus().equals("ACTIVE")) break;
				assertNotSame(result.getStatus(), "DELETED");
				try {
					Thread.sleep(1000 * 5);
				} catch (InterruptedException e) { }
			} while(true);
		}
		{
			GetInboxRequest request = new GetInboxRequest()
					.withInboxName(inbox1.getName());
			Inbox inbox = client.getInbox(request).getItem();
			assertNotNull(inbox);
			assertEquals(inbox.getOwnerId(), OWNER_ID);
			assertEquals(inbox.getName(), INBOX_NAME);
			assertEquals(inbox.getServiceClass(), "inbox1.nano");
			assertTrue(inbox.getAutoDelete());
			assertEquals(inbox.getCooperationUrl(), "http://example.com/");
		}
		{
			UpdateInboxRequest request = new UpdateInboxRequest()
					.withInboxName(inbox1.getName())
					.withServiceClass("inbox1.micro")
					.withCooperationUrl("https://example.com/");
			UpdateInboxResult result = client.updateInbox(request);
			assertNotNull(result);
			inbox1 = result.getItem();
			assertNotNull(inbox1);
			assertEquals(inbox1.getOwnerId(), OWNER_ID);
			assertEquals(inbox1.getName(), INBOX_NAME);
			assertEquals(inbox1.getServiceClass(), "inbox1.micro");
			assertTrue(inbox1.getAutoDelete());
			assertEquals(inbox1.getCooperationUrl(), "https://example.com/");
		}
		{
			do {
				GetInboxStatusRequest request = new GetInboxStatusRequest()
						.withInboxName(inbox1.getName());
				GetInboxStatusResult result = client.getInboxStatus(request);
				if(result.getStatus().equals("ACTIVE")) break;
				assertNotSame(result.getStatus(), "DELETED");
				try {
					Thread.sleep(1000 * 5);
				} catch (InterruptedException e) { }
			} while(true);
		}
		{
			DescribeInboxByOwnerIdRequest request = new DescribeInboxByOwnerIdRequest()
					.withOwnerId(OWNER_ID);
			DescribeInboxByOwnerIdResult result = pclient.describeInboxByOwnerId(request);
			assertNotNull(result);
			if(inbox2.getInboxId().equals(result.getItems().get(0).getInboxId())) {
				Inbox t = inbox1;
				inbox1 = inbox2;
				inbox2 = t;
			}
			assertEquals(result.getCount(), Integer.valueOf(2));
			assertEquals(result.getItems().size(), 2);
			assertEquals(result.getItems().get(0).getOwnerId(), inbox1.getOwnerId());
			assertEquals(result.getItems().get(0).getName(), inbox1.getName());
			assertEquals(result.getItems().get(0).getServiceClass(), inbox1.getServiceClass());
			assertEquals(result.getItems().get(0).getAutoDelete(), inbox1.getAutoDelete());
			assertEquals(result.getItems().get(0).getCooperationUrl(), inbox1.getCooperationUrl());
			assertEquals(result.getItems().get(1).getOwnerId(), inbox2.getOwnerId());
			assertEquals(result.getItems().get(1).getName(), inbox2.getName());
			assertEquals(result.getItems().get(1).getServiceClass(), inbox2.getServiceClass());
			assertEquals(result.getItems().get(1).getAutoDelete(), inbox2.getAutoDelete());
			assertEquals(result.getItems().get(1).getCooperationUrl(), inbox2.getCooperationUrl());
			assertNull(result.getNextPageToken());
		}
		{
			DescribeInboxRequest request = new DescribeInboxRequest();
			DescribeInboxResult result = client.describeInbox(request);
			assertNotNull(result);
			if(inbox2.getInboxId().equals(result.getItems().get(0).getInboxId())) {
				Inbox t = inbox1;
				inbox1 = inbox2;
				inbox2 = t;
			}
			assertEquals(result.getCount(), Integer.valueOf(2));
			assertEquals(result.getItems().size(), 2);
			assertEquals(result.getItems().get(0).getOwnerId(), inbox1.getOwnerId());
			assertEquals(result.getItems().get(0).getName(), inbox1.getName());
			assertEquals(result.getItems().get(0).getServiceClass(), inbox1.getServiceClass());
			assertEquals(result.getItems().get(0).getAutoDelete(), inbox1.getAutoDelete());
			assertEquals(result.getItems().get(0).getCooperationUrl(), inbox1.getCooperationUrl());
			assertEquals(result.getItems().get(1).getOwnerId(), inbox2.getOwnerId());
			assertEquals(result.getItems().get(1).getName(), inbox2.getName());
			assertEquals(result.getItems().get(1).getServiceClass(), inbox2.getServiceClass());
			assertEquals(result.getItems().get(1).getAutoDelete(), inbox2.getAutoDelete());
			assertEquals(result.getItems().get(1).getCooperationUrl(), inbox2.getCooperationUrl());
			assertNull(result.getNextPageToken());
		}
		{
			DescribeInboxRequest request = new DescribeInboxRequest()
					.withLimit(1);
			DescribeInboxResult result = client.describeInbox(request);
			assertNotNull(result);
			assertEquals(result.getItems().size(), 1);
			assertEquals(result.getItems().get(0).getOwnerId(), inbox1.getOwnerId());
			assertEquals(result.getItems().get(0).getName(), inbox1.getName());
			assertEquals(result.getItems().get(0).getServiceClass(), inbox1.getServiceClass());
			assertEquals(result.getItems().get(0).getAutoDelete(), inbox1.getAutoDelete());
			assertEquals(result.getItems().get(0).getCooperationUrl(), inbox1.getCooperationUrl());
			assertNotNull(result.getNextPageToken());

			request = new DescribeInboxRequest()
					.withPageToken(result.getNextPageToken())
					.withLimit(10);
			result = client.describeInbox(request);
			assertNotNull(result);
			assertEquals(result.getItems().size(), 1);
			assertEquals(result.getItems().get(0).getOwnerId(), inbox2.getOwnerId());
			assertEquals(result.getItems().get(0).getName(), inbox2.getName());
			assertEquals(result.getItems().get(0).getServiceClass(), inbox2.getServiceClass());
			assertEquals(result.getItems().get(0).getAutoDelete(), inbox2.getAutoDelete());
			assertEquals(result.getItems().get(0).getCooperationUrl(), inbox2.getCooperationUrl());
			assertNull(result.getNextPageToken());
		}
		{
			DeleteInboxRequest request = new DeleteInboxRequest()
					.withInboxName(inbox1.getName());
			client.deleteInbox(request);
		}
		try {
			GetInboxRequest request = new GetInboxRequest()
					.withInboxName(inbox1.getName());
			client.getInbox(request).getItem();
			assertTrue(false);
		} catch(NotFoundException e) {}
		{
			DeleteInboxRequest request = new DeleteInboxRequest()
					.withInboxName(inbox2.getName());
			client.deleteInbox(request);
		}
		try {
			GetInboxRequest request = new GetInboxRequest()
					.withInboxName(inbox2.getName());
			client.getInbox(request).getItem();
			assertTrue(false);
		} catch(NotFoundException e) {}
	}

	@Test(expected=NullPointerException.class)
	public void createInboxNull() {
		client.createInbox(null);
	}

	@Test
	public void createInboxNameNull() {
		try {
			CreateInboxRequest request = new CreateInboxRequest()
//					.withName(INBOX_NAME)
					.withServiceClass("inbox1.nano")
					.withAutoDelete(true)
					.withCooperationUrl("http://example.com/");
			client.createInbox(request);
			assertTrue(false);
		} catch (BadRequestException e) {
			assertEquals(e.getErrors().get(0).getComponent(), "name");
			assertEquals(e.getErrors().get(0).getMessage(), "inbox.name.error.require");
		}
	}

	@Test
	public void createInboxServiceClassNull() {
		try {
			CreateInboxRequest request = new CreateInboxRequest()
					.withName(INBOX_NAME)
//					.withServiceClass(ServiceClass.I1_NANO.getName())
					.withAutoDelete(true)
					.withCooperationUrl("http://example.com/");
			client.createInbox(request);
			assertTrue(false);
		} catch (BadRequestException e) {
			assertEquals(e.getErrors().get(0).getComponent(), "serviceClass");
			assertEquals(e.getErrors().get(0).getMessage(), "inbox.serviceClass.error.require");
		}
	}

	@Test(expected=NullPointerException.class)
	public void deleteInboxNull() {
		client.deleteInbox(null);
	}

	@Test
	public void deleteInboxNameNull() {
		try {
			DeleteInboxRequest request = new DeleteInboxRequest();
//					.withInboxName(INBOX_NAME);
			client.deleteInbox(request);
			assertTrue(false);
		} catch (NotFoundException e) {
			assertEquals(e.getErrors().get(0).getComponent(), "inbox");
			assertEquals(e.getErrors().get(0).getMessage(), "inbox.inbox.error.notFound");
		}
	}

	@Test
	public void deleteInboxNameInvalid() {
		try {
			DeleteInboxRequest request = new DeleteInboxRequest()
					.withInboxName("invalid");
			client.deleteInbox(request);
			assertTrue(false);
		} catch (NotFoundException e) {
			assertEquals(e.getErrors().get(0).getComponent(), "inbox");
			assertEquals(e.getErrors().get(0).getMessage(), "inbox.inbox.error.notFound");
		}
	}

	@AfterClass
	public static void shutdown() {
		try {
			DeleteInboxRequest request = new DeleteInboxRequest()
					.withInboxName(INBOX_NAME);
			client.deleteInbox(request);
		} catch (NotFoundException e) {}
		try {
			DeleteInboxRequest request = new DeleteInboxRequest()
					.withInboxName(INBOX_NAME2);
			client.deleteInbox(request);
		} catch (NotFoundException e) {}
	}
}
