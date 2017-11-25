package io.gs2.inbox;

import java.util.Arrays;

import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import io.gs2.auth.Gs2AuthClient;
import io.gs2.auth.control.LoginRequest;
import io.gs2.auth.control.LoginResult;
import io.gs2.exception.BadGatewayException;
import io.gs2.exception.BadRequestException;
import io.gs2.exception.NotFoundException;
import io.gs2.inbox.control.CreateInboxRequest;
import io.gs2.inbox.control.CreateInboxResult;
import io.gs2.inbox.control.DeleteInboxRequest;
import io.gs2.inbox.control.DeleteMessageRequest;
import io.gs2.inbox.control.DeleteMessagesRequest;
import io.gs2.inbox.control.DescribeMessageRequest;
import io.gs2.inbox.control.DescribeMessageResult;
import io.gs2.inbox.control.GetInboxRequest;
import io.gs2.inbox.control.GetInboxStatusRequest;
import io.gs2.inbox.control.GetInboxStatusResult;
import io.gs2.inbox.control.GetMessageRequest;
import io.gs2.inbox.control.GetMessageResult;
import io.gs2.inbox.control.ReadMessageRequest;
import io.gs2.inbox.control.ReadMessagesRequest;
import io.gs2.inbox.control.SendMessageRequest;
import io.gs2.inbox.control.SendMessageResult;
import io.gs2.inbox.control.UpdateInboxRequest;
import io.gs2.inbox.model.Inbox;
import io.gs2.inbox.model.Message;
import io.gs2.model.BasicGs2Credential;
import junit.framework.TestCase;

@RunWith(JUnit4.class)
public class MessageTest extends TestCase {

	protected static final String CLIENT_ID = "HovFAxYEvg0UMdUue740EQ==";
	protected static final String CLIENT_SECRET = "mHczQCWbukiCLd9ldCDRBw==";
	public static final String OWNER_ID = "7dWCSa0w";
	protected static final String INBOX_NAME = "inbox-0001";
	protected static final String INBOX_NAME2 = "inbox-0002";
	protected static final String INBOX_NAME3 = "inbox-0003";

	protected static Gs2InboxClient client;
	protected static Gs2InboxPrivateClient pclient;
	protected static Inbox inbox;
	protected static Inbox inbox2;
	protected static Inbox inbox3;
	protected static String accessToken;
	
	@BeforeClass
	public static void startup() {
		String region = "ap-northeast-1";
		Gs2AuthClient.ENDPOINT = "auth-dev";
		Gs2InboxClient.ENDPOINT = "inbox-dev";
		client = new Gs2InboxClient(new BasicGs2Credential(CLIENT_ID, CLIENT_SECRET));
		pclient = new Gs2InboxPrivateClient(new BasicGs2Credential(CLIENT_ID, CLIENT_SECRET));

		{
			CreateInboxRequest request = new CreateInboxRequest()
					.withName(INBOX_NAME)
					.withServiceClass("inbox1.micro")
					.withAutoDelete(false)
					.withCooperationUrl("http://example.com/");
			CreateInboxResult result = client.createInbox(request);
			assertNotNull(result);
			inbox = result.getItem();
		}
		{
			CreateInboxRequest request = new CreateInboxRequest()
					.withName(INBOX_NAME2)
					.withServiceClass("inbox1.micro")
					.withAutoDelete(true);
			CreateInboxResult result = client.createInbox(request);
			assertNotNull(result);
			inbox2 = result.getItem();
		}
		{
			CreateInboxRequest request = new CreateInboxRequest()
					.withName(INBOX_NAME3)
					.withServiceClass("inbox1.micro")
					.withAutoDelete(false)
					.withCooperationUrl("https://inbox-dev." + region + ".gs2.io/notfound");
			CreateInboxResult result = client.createInbox(request);
			assertNotNull(result);
			inbox3 = result.getItem();
		}
		{
			do {
				GetInboxStatusRequest request = new GetInboxStatusRequest()
						.withInboxName(inbox.getName());
				GetInboxStatusResult result = client.getInboxStatus(request);
				if(result.getStatus().equals("ACTIVE")) break;
				assertNotSame(result.getStatus(), "DELETED");
				try {
					Thread.sleep(1000 * 5);
				} catch (InterruptedException e) { }
			} while(true);
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
			do {
				GetInboxStatusRequest request = new GetInboxStatusRequest()
						.withInboxName(inbox3.getName());
				GetInboxStatusResult result = client.getInboxStatus(request);
				if(result.getStatus().equals("ACTIVE")) break;
				assertNotSame(result.getStatus(), "DELETED");
				try {
					Thread.sleep(1000 * 5);
				} catch (InterruptedException e) { }
			} while(true);
		}
		{
			LoginRequest request = new LoginRequest()
					.withServiceId("service-0001")
					.withUserId("user-0001");
			LoginResult result = new Gs2AuthClient(new BasicGs2Credential(CLIENT_ID, CLIENT_SECRET)).login(request);
			accessToken = result.getToken();
		}
	}

	@Test
	public void basic() {
		Message message1 = null;
		{
			SendMessageRequest request = new SendMessageRequest()
					.withInboxName(inbox.getName())
					.withUserId("user-0001")
					.withMessage("message")
					.withCooperation(false);
			message1 = client.sendMessage(request).getItem();
			System.out.println(message1);
			assertEquals(message1.getInboxId(), inbox.getInboxId());
			assertEquals(message1.getUserId(), "user-0001");
			assertEquals(message1.getMessage(), "message");
			assertFalse(message1.getCooperation());
			assertFalse(message1.getRead());
		}
		Message message2 = null;
		{
			SendMessageRequest request = new SendMessageRequest()
					.withInboxName(inbox.getName())
					.withUserId("user-0001")
					.withMessage("message2")
					.withCooperation(true);
			message2 = client.sendMessage(request).getItem();
			assertEquals(message2.getInboxId(), inbox.getInboxId());
			assertEquals(message2.getUserId(), "user-0001");
			assertEquals(message2.getMessage(), "message2");
			assertTrue(message2.getCooperation());
			assertFalse(message2.getRead());
		}
		{
			ReadMessageRequest request = new ReadMessageRequest()
					.withInboxName(inbox.getName())
					.withMessageId(message1.getMessageId())
					.withAccessToken(accessToken);
			client.readMessage(request);
			message1.setRead(true);
		}
		String message1Id = message1.getMessageId();
		String message2Id = message2.getMessageId();
		{
			DescribeMessageRequest request = new DescribeMessageRequest()
					.withInboxName(inbox.getName())
					.withAccessToken(accessToken);
			DescribeMessageResult result = client.describeMessage(request);
			assertEquals(result.getCount(), Integer.valueOf(2));
			assertEquals(result.getItems().size(), 2);
			if(message2.getMessageId().equals(result.getItems().get(0).getMessageId())) {
				Message t = message1;
				message1 = message2;
				message2 = t;
			}
			assertEquals(result.getItems().get(0).getInboxId(), message1.getInboxId());
			assertEquals(result.getItems().get(0).getUserId(), message1.getUserId());
			assertEquals(result.getItems().get(0).getMessage(), message1.getMessage());
			assertEquals(result.getItems().get(0).getCooperation(), message1.getCooperation());
			assertEquals(result.getItems().get(0).getRead(), message1.getRead());
			assertEquals(result.getItems().get(1).getInboxId(), message2.getInboxId());
			assertEquals(result.getItems().get(1).getUserId(), message2.getUserId());
			assertEquals(result.getItems().get(1).getMessage(), message2.getMessage());
			assertEquals(result.getItems().get(1).getCooperation(), message2.getCooperation());
			assertEquals(result.getItems().get(1).getRead(), message2.getRead());
			assertNull(result.getNextPageToken());
		}
		{
			DescribeMessageRequest request = new DescribeMessageRequest()
					.withInboxName(inbox.getName())
					.withAccessToken(accessToken)
					.withLimit(1);
			DescribeMessageResult result = client.describeMessage(request);
			assertEquals(result.getCount(), Integer.valueOf(1));
			assertEquals(result.getItems().size(), 1);
			assertEquals(result.getItems().get(0).getInboxId(), message1.getInboxId());
			assertEquals(result.getItems().get(0).getUserId(), message1.getUserId());
			assertEquals(result.getItems().get(0).getMessage(), message1.getMessage());
			assertEquals(result.getItems().get(0).getCooperation(), message1.getCooperation());
			assertEquals(result.getItems().get(0).getRead(), message1.getRead());
			assertNotNull(result.getNextPageToken());

			request = new DescribeMessageRequest()
					.withInboxName(inbox.getName())
					.withAccessToken(accessToken)
					.withPageToken(result.getNextPageToken())
					.withLimit(-1);
			result = client.describeMessage(request);
			assertEquals(result.getItems().get(0).getInboxId(), message2.getInboxId());
			assertEquals(result.getItems().get(0).getUserId(), message2.getUserId());
			assertEquals(result.getItems().get(0).getMessage(), message2.getMessage());
			assertEquals(result.getItems().get(0).getCooperation(), message2.getCooperation());
			assertEquals(result.getItems().get(0).getRead(), message2.getRead());
			assertNull(result.getNextPageToken());
		}
		{
			DeleteMessageRequest request = new DeleteMessageRequest()
					.withInboxName(inbox.getName())
					.withMessageId(message1Id)
					.withAccessToken(accessToken);
			client.deleteMessage(request);
		}
		try {
			ReadMessageRequest request = new ReadMessageRequest()
					.withInboxName(inbox.getName())
					.withMessageId(message1Id)
					.withAccessToken(accessToken);
			client.readMessage(request);
			assertTrue(false);
		} catch(NotFoundException e) {}
		{
			ReadMessageRequest request = new ReadMessageRequest()
					.withInboxName(inbox.getName())
					.withMessageId(message2Id)
					.withAccessToken(accessToken);
			client.readMessage(request);
			message2.setRead(true);
		}
		{
			DeleteMessageRequest request = new DeleteMessageRequest()
					.withInboxName(inbox.getName())
					.withMessageId(message2Id)
					.withAccessToken(accessToken);
			client.deleteMessage(request);
		}
		try {
			ReadMessageRequest request = new ReadMessageRequest()
					.withInboxName(inbox.getName())
					.withMessageId(message2Id)
					.withAccessToken(accessToken);
			client.readMessage(request);
			assertTrue(false);
		} catch(NotFoundException e) {}
		Message message3 = null;
		{
			SendMessageRequest request = new SendMessageRequest()
					.withInboxName(inbox2.getName())
					.withUserId("user-0001")
					.withMessage("message")
					.withCooperation(false);
			message3 = client.sendMessage(request).getItem();
			assertEquals(message3.getInboxId(), inbox2.getInboxId());
			assertEquals(message3.getUserId(), "user-0001");
			assertEquals(message3.getMessage(), "message");
			assertFalse(message3.getCooperation());
			assertFalse(message3.getRead());
		}
		{
			SendMessageRequest request = new SendMessageRequest()
					.withInboxName(inbox2.getName())
					.withUserId("user-0002")
					.withMessage("message2")
					.withCooperation(false);
			Message message4 = client.sendMessage(request).getItem();
			assertEquals(message4.getInboxId(), inbox2.getInboxId());
			assertEquals(message4.getUserId(), "user-0002");
			assertEquals(message4.getMessage(), "message2");
			assertFalse(message4.getCooperation());
			assertFalse(message4.getRead());
		}
		{
			DescribeMessageRequest request = new DescribeMessageRequest()
					.withInboxName(inbox2.getName())
					.withAccessToken(accessToken);
			DescribeMessageResult result = client.describeMessage(request);
			assertEquals(result.getCount(), Integer.valueOf(1));
			assertEquals(result.getItems().size(), 1);
		}
		{
			GetMessageRequest request = new GetMessageRequest()
					.withInboxName(inbox2.getName())
					.withMessageId(message3.getMessageId());
			GetMessageResult result = client.getMessage(request);
			assertNotNull(result);
			message3 = result.getItem();
			assertEquals(message3.getInboxId(), inbox2.getInboxId());
			assertEquals(message3.getUserId(), "user-0001");
			assertEquals(message3.getMessage(), "message");
			assertFalse(message3.getCooperation());
			assertFalse(message3.getRead());
		}
		{
			ReadMessageRequest request = new ReadMessageRequest()
					.withInboxName(inbox2.getName())
					.withMessageId(message3.getMessageId())
					.withAccessToken(accessToken);
			client.readMessage(request);
		}
		{
			DescribeMessageRequest request = new DescribeMessageRequest()
					.withInboxName(inbox2.getName())
					.withAccessToken(accessToken);
			DescribeMessageResult result = client.describeMessage(request);
			assertEquals(result.getCount(), Integer.valueOf(0));
			assertEquals(result.getItems().size(), 0);
		}
		{
			SendMessageRequest request = new SendMessageRequest()
					.withInboxName(inbox.getName())
					.withUserId("user-0001")
					.withMessage("message")
					.withCooperation(false);
			message1 = client.sendMessage(request).getItem();
			assertEquals(message1.getInboxId(), inbox.getInboxId());
			assertEquals(message1.getUserId(), "user-0001");
			assertEquals(message1.getMessage(), "message");
			assertFalse(message1.getCooperation());
			assertFalse(message1.getRead());
		}
		{
			SendMessageRequest request = new SendMessageRequest()
					.withInboxName(inbox.getName())
					.withUserId("user-0001")
					.withMessage("message2")
					.withCooperation(true);
			message2 = client.sendMessage(request).getItem();
			assertEquals(message2.getInboxId(), inbox.getInboxId());
			assertEquals(message2.getUserId(), "user-0001");
			assertEquals(message2.getMessage(), "message2");
			assertTrue(message2.getCooperation());
			assertFalse(message2.getRead());
		}
		{
			ReadMessagesRequest request = new ReadMessagesRequest()
					.withInboxName(inbox.getName())
					.withMessageIds(Arrays.asList(message1.getMessageId(), message2.getMessageId()))
					.withAccessToken(accessToken);
			client.readMessages(request);
		}
		{
			DeleteMessagesRequest request = new DeleteMessagesRequest()
					.withInboxName(inbox.getName())
					.withMessageIds(Arrays.asList(message1.getMessageId(), message2.getMessageId()))
					.withAccessToken(accessToken);
			client.deleteMessages(request);
		}
	}

	@Test(expected=NullPointerException.class)
	public void getInboxNull() {
		client.getInbox(null);
	}

	@Test
	public void getInboxNameNull() {
		try {
			GetInboxRequest request = new GetInboxRequest();
//					.withInboxName(INBOX_NAME);
			client.getInbox(request);
			assertTrue(false);
		} catch (NotFoundException e) {
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "inbox");
			assertEquals(e.getErrors().get(0).getMessage(), "inbox.inbox.error.notFound");
		}
	}

	@Test
	public void getInboxNameInvalid() {
		try {
			GetInboxRequest request = new GetInboxRequest()
					.withInboxName("invalid");
			client.getInbox(request);
			assertTrue(false);
		} catch (NotFoundException e) {
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "inbox");
			assertEquals(e.getErrors().get(0).getMessage(), "inbox.inbox.error.notFound");
		}
	}

	@Test(expected=NullPointerException.class)
	public void getInboxStatusNull() {
		client.getInboxStatus(null);
	}

	@Test
	public void getInboxStatusNameNull() {
		try {
			GetInboxStatusRequest request = new GetInboxStatusRequest();
//					.withInboxName(INBOX_NAME);
			String status = client.getInboxStatus(request).getStatus();
			assertEquals(status, "DELETED");
		} catch (NotFoundException e) {
			assertTrue(false);
		}
	}

	@Test
	public void getInboxStatusNameInvalid() {
		try {
			GetInboxStatusRequest request = new GetInboxStatusRequest()
					.withInboxName("invalid");
			String status = client.getInboxStatus(request).getStatus();
			assertEquals(status, "DELETED");
		} catch (NotFoundException e) {
			assertTrue(false);
		}
	}

	@Test(expected=NullPointerException.class)
	public void describeInboxNull() {
		client.describeInbox(null);
	}

	@Test(expected=NullPointerException.class)
	public void updateInboxNull() {
		client.updateInbox(null);
	}

	@Test
	public void updateInboxServiceClassNull() {
		try {
			UpdateInboxRequest request = new UpdateInboxRequest()
					.withInboxName(INBOX_NAME)
//					.withServiceClass(ServiceClass.I1_NANO.getName())
					.withCooperationUrl("http://example.com/");
			client.updateInbox(request);
			assertTrue(false);
		} catch (BadRequestException e) {
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "serviceClass");
			assertEquals(e.getErrors().get(0).getMessage(), "inbox.serviceClass.error.require");
		}
	}

	@Test
	public void updateInboxCooperationUrlInvalid() {
		try {
			UpdateInboxRequest request = new UpdateInboxRequest()
					.withInboxName(INBOX_NAME)
					.withServiceClass("inbox1.nano")
					.withCooperationUrl("invalid");
			client.updateInbox(request);
			assertTrue(false);
		} catch (BadRequestException e) {
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "cooperationUrl");
			assertEquals(e.getErrors().get(0).getMessage(), "inbox.cooperationUrl.error.invalid");
		}
	}
	
	@Test(expected=NullPointerException.class)
	public void sendMessageNull() {
		client.sendMessage(null);
	}
	
	@Test
	public void sendMessageInboxNameNull() {
		try {
			SendMessageRequest request = new SendMessageRequest()
//					.withInboxName(inbox.getName())
					.withUserId("user-0001")
					.withMessage("message")
					.withCooperation(false);
			client.sendMessage(request);
			assertTrue(false);
		} catch (NotFoundException e) {
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "inbox");
			assertEquals(e.getErrors().get(0).getMessage(), "inbox.inbox.error.notFound");
		}
	}
	
	@Test
	public void sendMessageInboxNameInvalid() {
		try {
			SendMessageRequest request = new SendMessageRequest()
					.withInboxName("invalid")
					.withUserId("user-0001")
					.withMessage("message")
					.withCooperation(false);
			client.sendMessage(request);
			assertTrue(false);
		} catch (NotFoundException e) {
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "inbox");
			assertEquals(e.getErrors().get(0).getMessage(), "inbox.inbox.error.notFound");
		}
	}

	@Test
	public void sendMessageUserIdNull() {
		try {
			SendMessageRequest request = new SendMessageRequest()
					.withInboxName(inbox.getName())
//					.withUserId("user-0001")
					.withMessage("message")
					.withCooperation(false);
			client.sendMessage(request);
			assertTrue(false);
		} catch (BadRequestException e) {
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "userId");
			assertEquals(e.getErrors().get(0).getMessage(), "inbox.message.userId.error.require");
		}
	}

	@Test
	public void sendMessageMessageNull() {
		try {
			SendMessageRequest request = new SendMessageRequest()
					.withInboxName(inbox.getName())
					.withUserId("user-0001")
//					.withMessage("message")
					.withCooperation(false);
			client.sendMessage(request);
			assertTrue(false);
		} catch (BadRequestException e) {
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "message");
			assertEquals(e.getErrors().get(0).getMessage(), "inbox.message.message.error.require");
		}
	}

	@Test
	public void sendMessageMessageTooLong() {
		try {
			String message = "";
			for(int i=0; i<1025; i++) {
				message += "a";
			}
			SendMessageRequest request = new SendMessageRequest()
					.withInboxName(inbox.getName())
					.withUserId("user-0001")
					.withMessage(message)
					.withCooperation(false);
			client.sendMessage(request);
			assertTrue(false);
		} catch (BadRequestException e) {
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "message");
			assertEquals(e.getErrors().get(0).getMessage(), "inbox.message.message.error.tooLong");
		}
	}

	@Test
	public void readMessageCooperationFailed() {
		try {
			String messageId = null;
			{
				SendMessageRequest request = new SendMessageRequest()
						.withInboxName(inbox3.getName())
						.withUserId("user-0001")
						.withMessage("co-op message")
						.withCooperation(true);
				SendMessageResult result = client.sendMessage(request);
				messageId = result.getItem().getMessageId();
			}
			ReadMessageRequest request = new ReadMessageRequest()
					.withInboxName(inbox3.getName())
					.withMessageId(messageId)
					.withAccessToken(accessToken);
			client.readMessage(request);
			assertTrue(false);
		} catch (BadGatewayException e) {
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "cooperation");
			JSONObject json = new JSONObject(e.getErrors().get(0).getMessage());
			assertEquals(json.getString("message"), "Missing Authentication Token");
		}
	}
	
	@Test(expected=NullPointerException.class)
	public void describeMessageNull() {
		client.describeMessage(null);
	}

	@Test
	public void describeMessageInboxNameNull() {
		try {
			DescribeMessageRequest request = new DescribeMessageRequest()
//					.withInboxName(inbox.getName())
					.withAccessToken(accessToken);
			client.describeMessage(request);
			assertTrue(false);
		} catch (NotFoundException e) {
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "inbox");
			assertEquals(e.getErrors().get(0).getMessage(), "inbox.inbox.error.notFound");
		}
	}
	
	@Test
	public void describeMessageInboxNameInvalid() {
		try {
			DescribeMessageRequest request = new DescribeMessageRequest()
					.withInboxName("invalid")
					.withAccessToken(accessToken);
			client.describeMessage(request);
			assertTrue(false);
		} catch (NotFoundException e) {
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "inbox");
			assertEquals(e.getErrors().get(0).getMessage(), "inbox.inbox.error.notFound");
		}
	}

	@Test(expected=NullPointerException.class)
	public void readMessageNull() {
		client.readMessage(null);
	}

	@Test
	public void readMessageInboxNameNull() {
		try {
			ReadMessageRequest request = new ReadMessageRequest()
//					.withInboxName(inbox.getName())
					.withMessageId("message-id")
					.withAccessToken(accessToken);
			client.readMessage(request);
			assertTrue(false);
		} catch (NotFoundException e) {
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "inbox");
			assertEquals(e.getErrors().get(0).getMessage(), "inbox.inbox.error.notFound");
		}
	}
	
	@Test
	public void readMessageInboxNameInvalid() {
		try {
			ReadMessageRequest request = new ReadMessageRequest()
					.withInboxName("invalid")
					.withMessageId("message-id")
					.withAccessToken(accessToken);
			client.readMessage(request);
			assertTrue(false);
		} catch (NotFoundException e) {
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "inbox");
			assertEquals(e.getErrors().get(0).getMessage(), "inbox.inbox.error.notFound");
		}
	}

	@Test
	public void readMessageMessageIdNull() {
		try {
			ReadMessageRequest request = new ReadMessageRequest()
					.withInboxName(inbox.getName())
//					.withMessageId("message-id")
					.withAccessToken(accessToken);
			client.readMessage(request);
			assertTrue(false);
		} catch (NotFoundException e) {
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "message");
			assertEquals(e.getErrors().get(0).getMessage(), "inbox.message.message.error.notFound");
		}
	}
	
	@Test
	public void readMessageMessageIdInvalid() {
		try {
			ReadMessageRequest request = new ReadMessageRequest()
					.withInboxName(inbox.getName())
					.withMessageId("invalid")
					.withAccessToken(accessToken);
			client.readMessage(request);
		} catch (NotFoundException e) {
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "message");
			assertEquals(e.getErrors().get(0).getMessage(), "inbox.message.message.error.notFound");
		}
	}

	@Test(expected=NullPointerException.class)
	public void deleteMessageNull() {
		client.deleteMessage(null);
	}

	@Test
	public void deleteMessageInboxNameNull() {
		try {
			DeleteMessageRequest request = new DeleteMessageRequest()
//					.withInboxName(inbox.getName())
					.withMessageId("message-id")
					.withAccessToken(accessToken);
			client.deleteMessage(request);
			assertTrue(false);
		} catch (NotFoundException e) {
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "inbox");
			assertEquals(e.getErrors().get(0).getMessage(), "inbox.inbox.error.notFound");
		}
	}
	
	@Test
	public void deleteMessageInboxNameInvalid() {
		try {
			DeleteMessageRequest request = new DeleteMessageRequest()
					.withInboxName("invalid")
					.withMessageId("message-id")
					.withAccessToken(accessToken);
			client.deleteMessage(request);
			assertTrue(false);
		} catch (NotFoundException e) {
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "inbox");
			assertEquals(e.getErrors().get(0).getMessage(), "inbox.inbox.error.notFound");
		}
	}

	@Test
	public void deleteMessageMessageIdNull() {
		try {
			DeleteMessageRequest request = new DeleteMessageRequest()
					.withInboxName(inbox.getName())
//					.withMessageId("message-id")
					.withAccessToken(accessToken);
			client.deleteMessage(request);
			assertTrue(false);
		} catch (NotFoundException e) {
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "message");
			assertEquals(e.getErrors().get(0).getMessage(), "inbox.message.message.error.notFound");
		}
	}
	
	@Test
	public void deleteMessageMessageIdInvalid() {
		try {
			DeleteMessageRequest request = new DeleteMessageRequest()
					.withInboxName(inbox.getName())
					.withMessageId("invalid")
					.withAccessToken(accessToken);
			client.deleteMessage(request);
			assertTrue(false);
		} catch (NotFoundException e) {
			assertEquals(e.getErrors().size(), 1);
			assertEquals(e.getErrors().get(0).getComponent(), "message");
			assertEquals(e.getErrors().get(0).getMessage(), "inbox.message.message.error.notFound");
		}
	}

	@AfterClass
	public static void shutdown() {
		if(accessToken != null) {
			{
				DescribeMessageRequest request = new DescribeMessageRequest()
						.withInboxName(INBOX_NAME)
						.withAccessToken(accessToken);
				DescribeMessageResult result = client.describeMessage(request);
				String _accessToken = accessToken;
				result.getItems().parallelStream().forEach(message -> {
					DeleteMessageRequest request2 = new DeleteMessageRequest()
							.withInboxName(INBOX_NAME)
							.withMessageId(message.getMessageId())
							.withAccessToken(_accessToken);
					client.deleteMessage(request2);
				});
			}
			{
				DescribeMessageRequest request = new DescribeMessageRequest()
						.withInboxName(INBOX_NAME2)
						.withAccessToken(accessToken);
				DescribeMessageResult result = client.describeMessage(request);
				String _accessToken = accessToken;
				result.getItems().parallelStream().forEach(message -> {
					DeleteMessageRequest request2 = new DeleteMessageRequest()
							.withInboxName(INBOX_NAME2)
							.withMessageId(message.getMessageId())
							.withAccessToken(_accessToken);
					client.deleteMessage(request2);
				});
			}
		}
		{
			try {
				DeleteInboxRequest request = new DeleteInboxRequest()
						.withInboxName(INBOX_NAME);
				client.deleteInbox(request);
			} catch (NotFoundException e) {}
		}
		{
			try {
				DeleteInboxRequest request = new DeleteInboxRequest()
						.withInboxName(INBOX_NAME2);
				client.deleteInbox(request);
			} catch (NotFoundException e) {}
		}
		{
			try {
				DeleteInboxRequest request = new DeleteInboxRequest()
						.withInboxName(INBOX_NAME3);
				client.deleteInbox(request);
			} catch (NotFoundException e) {}
		}
	}
}
