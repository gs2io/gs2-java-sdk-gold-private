package io.gs2.gold;

import io.gs2.auth.control.LoginResult;
import io.gs2.gold.control.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import io.gs2.auth.Gs2AuthClient;
import io.gs2.auth.control.LoginRequest;
import io.gs2.exception.BadGatewayException;
import io.gs2.exception.BadRequestException;
import io.gs2.exception.NotFoundException;
import io.gs2.gold.control.CreateGoldRequest;
import io.gs2.gold.control.DescribeAllWalletRequest;
import io.gs2.gold.control.DescribeAllWalletResult;

import io.gs2.gold.model.Gold;
import io.gs2.gold.model.Wallet;
import io.gs2.model.BasicGs2Credential;
import junit.framework.TestCase;

import java.util.Iterator;
import java.util.List;

@RunWith(JUnit4.class)
public class WalletTest extends TestCase {

	protected static final String CLIENT_ID = "HovFAxYEvg0UMdUue740EQ==";
	protected static final String CLIENT_SECRET = "mHczQCWbukiCLd9ldCDRBw==";
	public static final String OWNER_ID = "7dWCSa0w";
	protected static final String GOLD_NAME1 = "gold-0001";
	protected static final String GOLD_NAME2 = "gold-0002";
    protected static final String GOLD_NAME3 = "gold-0003";
    protected static final String GOLD_NAME4 = "gold-0004";
    protected static String SERVICE_ID = "service-0001";
    protected static String USER_ID = "user-0001";
    protected static String USER_ID2 = "user-0002";

	protected static Gs2GoldClient client;
	protected static Gs2GoldPrivateClient pclient;
	protected static Gold gold;
	protected static Gold gold2;
	protected static Gold gold3;
    protected static Wallet wallet1;
    protected static Wallet wallet2;
	protected static String accessToken;

	@BeforeClass
	public static void startup() {
		String region = "ap-northeast-1";
		Gs2AuthClient.ENDPOINT = "auth-dev";
		Gs2GoldClient.ENDPOINT = "gold-dev";
		client = pclient = new Gs2GoldPrivateClient(new BasicGs2Credential(CLIENT_ID, CLIENT_SECRET));

		{
			CreateGoldRequest request = new CreateGoldRequest()
					.withName(GOLD_NAME1)
					.withServiceClass("gold1.small");
			try {
				client.createGold(request);
			} catch (BadRequestException e) {
				// ok_(emessage.startswith("BadRequest:"))
				assertEquals(e.getErrors().size(), 1);
				assertEquals(e.getErrors().get(0).getComponent(), "name");
				assertEquals(e.getErrors().get(0).getMessage(), "gold.name.error.duplicate");
			}
		}

		{
			// testSuccessfulNotifications 専用
			CreateGoldRequest request = new CreateGoldRequest()
					.withName(GOLD_NAME2)
					.withServiceClass("gold1.micro")
					.withNotificationUrl("https://gold-dev." + region + ".gs2.io/system/test/callback");
			try {
				client.createGold(request);
			} catch (BadRequestException e) {
				// ok_(emessage.startswith("BadRequest:"))
				assertEquals(e.getErrors().size(), 1);
				assertEquals(e.getErrors().get(0).getComponent(), "name");
				assertEquals(e.getErrors().get(0).getMessage(), "gold.name.error.duplicate");
			}
		}

		{
			// testFailedNotifications 専用
			CreateGoldRequest request = new CreateGoldRequest()
					.withName(GOLD_NAME3)
					.withServiceClass("gold1.micro")
					.withNotificationUrl("https://gold-dev." + region + ".gs2.io/system/test/callback2");
			try {
				client.createGold(request);
			} catch (BadRequestException e) {
				// ok_(emessage.startswith("BadRequest:"))
				assertEquals(e.getErrors().size(), 1);
				assertEquals(e.getErrors().get(0).getComponent(), "name");
				assertEquals(e.getErrors().get(0).getMessage(), "gold.name.error.duplicate");
			}
		}

		{
			GetGoldRequest request = new GetGoldRequest()
					.withGoldName(GOLD_NAME1);
			GetGoldResult result = client.getGold(request);
			gold = result.getItem();
		}

		{
			GetGoldStatusRequest request = new GetGoldStatusRequest()
					.withGoldName(GOLD_NAME1);
		}

		do {
			GetGoldStatusRequest request = new GetGoldStatusRequest()
					.withGoldName(GOLD_NAME1);
			GetGoldStatusResult result = client.getGoldStatus(request);
			String status = result.getStatus();
			if (status.equals("ACTIVE")) break;
			try {
				Thread.sleep(1000 * 1);
			} catch (InterruptedException e) { }
		} while(true);

		{
			GetGoldStatusRequest request = new GetGoldStatusRequest()
					.withGoldName(GOLD_NAME2);
		}

		do {
			GetGoldStatusRequest request = new GetGoldStatusRequest()
					.withGoldName(GOLD_NAME2);
			GetGoldStatusResult result = client.getGoldStatus(request);
			String status = result.getStatus();
			if (status.equals("ACTIVE")) break;
			try {
				Thread.sleep(1000 * 1);
			} catch (InterruptedException e) { }
		} while(true);

        {
            LoginRequest request = new LoginRequest()
                    .withServiceId(SERVICE_ID)
                    .withUserId(USER_ID);
            LoginResult result = new Gs2AuthClient(new BasicGs2Credential(CLIENT_ID, CLIENT_SECRET)).login(request);
            accessToken = result.getToken();
        }
	}

	@Test
	public void basic() {
        // あってもなくても Get できる
        {
            GetWalletRequest request = new GetWalletRequest()
                    .withGoldName(gold.getName())
                    .withUserId(USER_ID);
            GetWalletResult result = client.getWallet(request);
            assertNotNull(result);
            wallet1 = result.getItem();
            // assertEquals(wallet1.getGoldId(), gold.getGoldId());
            assertEquals(wallet1.getUserId(), USER_ID);
            assertEquals(wallet1.getBalance(), Integer.valueOf(0));
            assertEquals(wallet1.getLatestGain(), Integer.valueOf(0));
            assertNotNull(wallet1.getCreateAt());
            assertNotNull(wallet1.getUpdateAt());
        }
            
        // 残高を加算
        {
            AddToWalletRequest request = new AddToWalletRequest()
                    .withGoldName(gold.getName())
                    .withUserId(USER_ID)
                    .withValue(100);
            AddToWalletResult result = client.addToWallet(request);
            assertNotNull(result);
            wallet1 = result.getItem();
            assertEquals(wallet1.getUserId(), USER_ID);
            assertEquals(wallet1.getBalance(), Integer.valueOf(100));
            assertEquals(wallet1.getLatestGain(), Integer.valueOf(100));
            assertNotNull(wallet1.getCreateAt());
            assertNotNull(wallet1.getUpdateAt());
        }

        // 取得量をリセット
        {
            ResetLatestGainRequest request = new ResetLatestGainRequest()
                    .withGoldName(gold.getName())
                    .withUserId(USER_ID)
                    .withValue(70);
            ResetLatestGainResult result = client.resetLatestGain(request);
            assertNotNull(result);
            wallet1 = result.getItem();
            assertEquals(wallet1.getUserId(), USER_ID);
            assertEquals(wallet1.getBalance(), Integer.valueOf(100));
            assertEquals(wallet1.getLatestGain(), Integer.valueOf(70));
            assertNotNull(wallet1.getCreateAt());
            assertNotNull(wallet1.getUpdateAt());
        }

        {
            ResetLatestGainRequest request = new ResetLatestGainRequest()
                    .withGoldName(gold.getName())
                    .withUserId(USER_ID);
            // value を指定しなければ 0
            ResetLatestGainResult result = client.resetLatestGain(request);
            assertNotNull(result);
            wallet1 = result.getItem();
            assertEquals(wallet1.getUserId(), USER_ID);
            assertEquals(wallet1.getBalance(), Integer.valueOf(100));
            assertEquals(wallet1.getLatestGain(), Integer.valueOf(0));
            assertNotNull(wallet1.getCreateAt());
            assertNotNull(wallet1.getUpdateAt());
        }

        // 残高を減算
        {
            SubtractFromWalletRequest request = new SubtractFromWalletRequest()
                    .withGoldName(gold.getName())
                    .withUserId(USER_ID)
                    .withValue(60);
            SubtractFromWalletResult result = client.subtractFromWallet(request);
            assertNotNull(result);
            wallet1 = result.getItem();
            assertEquals(wallet1.getUserId(), USER_ID);
            assertEquals(wallet1.getBalance(), Integer.valueOf(40));
            assertEquals(wallet1.getLatestGain(), Integer.valueOf(0));
            assertNotNull(wallet1.getCreateAt());
            assertNotNull(wallet1.getUpdateAt());
        }

        // 残高がないところに残高を減算
        {
            SubtractFromWalletRequest request = new SubtractFromWalletRequest()
                    .withGoldName(gold.getName())
                    .withUserId(USER_ID2)
                    .withValue(60);
            try {
                client.subtractFromWallet(request);   // ウォレットがなければ残高も 0 なので、残高不足エラーになる
                assertTrue(false);
            } catch (BadRequestException e) {
                // ok_(emessage.startswith("BadRequest:"))
                assertEquals(e.getErrors().size(), 1);
                assertEquals(e.getErrors().get(0).getComponent(), "balance");
                assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.balance.error.insufficient");
            }
        }

        // 残高を加算して、ウォレットがあることを保証
        {
            AddToWalletRequest request = new AddToWalletRequest()
                    .withGoldName(gold.getName())
                    .withUserId(USER_ID2)
                    .withValue(50);
            AddToWalletResult result = client.addToWallet(request);
            assertNotNull(result);
            wallet2 = result.getItem();
            assertEquals(wallet2.getUserId(), USER_ID2);
            assertEquals(wallet2.getBalance(), Integer.valueOf(50));
            assertEquals(wallet2.getLatestGain(), Integer.valueOf(50));
            assertNotNull(wallet2.getCreateAt());
            assertNotNull(wallet2.getUpdateAt());
        }

        {
            GetWalletRequest request = new GetWalletRequest()
                    .withGoldName(gold.getName())
                    .withUserId(USER_ID2);
            GetWalletResult result = client.getWallet(request);
            assertNotNull(result);
            wallet2 = result.getItem();
            // assertEquals(wallet2.getGoldId(), gold.getGoldId());
            assertEquals(wallet2.getUserId(), USER_ID2);
            assertEquals(wallet2.getBalance(), Integer.valueOf(50));
            assertEquals(wallet2.getLatestGain(), Integer.valueOf(50));
            assertNotNull(wallet2.getCreateAt());
            assertNotNull(wallet2.getUpdateAt());
        }

        // gold 内のウォレットを列挙
        {
            DescribeAllWalletRequest request = new DescribeAllWalletRequest()
                    .withGoldId(gold.getGoldId());
            DescribeAllWalletResult result = pclient.describeAllWallet(request);
            assertNotNull(result.getItems());
            assertEquals(result.getItems().size(), 2);
            if (wallet2.getUserId().equals(result.getItems().get(0).getUserId())) {
                Wallet t = wallet1;
                wallet1 = wallet2;
                wallet2 = t;
            }
            // assertEquals(result.getItems().get(0).getGoldId(), wallet1.getGoldId());
            assertEquals(result.getItems().get(0).getUserId(), wallet1.getUserId());
            assertEquals(result.getItems().get(0).getBalance(), wallet1.getBalance());
            assertEquals(result.getItems().get(0).getLatestGain(), wallet1.getLatestGain());
            assertNotNull(result.getItems().get(0).getCreateAt());
            assertNotNull(result.getItems().get(0).getUpdateAt());
            // assertEquals(result.getItems().get(1).getGoldId(), wallet2.getGoldId());
            assertEquals(result.getItems().get(1).getUserId(), wallet2.getUserId());
            assertEquals(result.getItems().get(1).getBalance(), wallet2.getBalance());
            assertEquals(result.getItems().get(1).getLatestGain(), wallet2.getLatestGain());
            assertNotNull(result.getItems().get(1).getCreateAt());
            assertNotNull(result.getItems().get(1).getUpdateAt());
            assertNull(result.getNextPageToken());
        }

        // 最大数を指定してユーザーのウォレットを列挙
        {
            String nextPageToken;

            {
                DescribeAllWalletRequest request = new DescribeAllWalletRequest()
                        .withGoldId(gold.getGoldId())
                        .withLimit(1);
                DescribeAllWalletResult result = pclient.describeAllWallet(request);
                assertNotNull(result.getItems());
                assertEquals(result.getItems().size(), 1);
                // assertEquals(result.getItems().get(0).getGoldId(), wallet1.getGoldId());
                assertEquals(result.getItems().get(0).getUserId(), wallet1.getUserId());
                assertEquals(result.getItems().get(0).getBalance(), wallet1.getBalance());
                assertEquals(result.getItems().get(0).getLatestGain(), wallet1.getLatestGain());
                assertNotNull(result.getItems().get(0).getCreateAt());
                assertNotNull(result.getItems().get(0).getUpdateAt());
                assertNotNull(result.getNextPageToken());

                nextPageToken = result.getNextPageToken();
            }

            // ページトークンを指定して、前の列挙の続きを取得
            {
                DescribeAllWalletRequest request = new DescribeAllWalletRequest()
                        .withGoldId(gold.getGoldId())
                        .withPageToken(nextPageToken);
                DescribeAllWalletResult result = pclient.describeAllWallet(request);
                assertNotNull(result.getItems());
                assertEquals(result.getItems().size(), 1);
                // assertEquals(result.getItems().get(0).getGoldId(), wallet2.getGoldId());
                assertEquals(result.getItems().get(0).getUserId(), wallet2.getUserId());
                assertEquals(result.getItems().get(0).getBalance(), wallet2.getBalance());
                assertEquals(result.getItems().get(0).getLatestGain(), wallet2.getLatestGain());
                assertNotNull(result.getItems().get(0).getCreateAt());
                assertNotNull(result.getItems().get(0).getUpdateAt());
                assertNull(result.getNextPageToken());
            }
        }
	}

    @Test
    public void testAddToWalletUserIdNone() {
        {
            AddToWalletRequest request = new AddToWalletRequest()
                    .withGoldName(GOLD_NAME1)
                    //                 .withUserId(USER_ID)
                    .withValue(0);
            try {
                client.addToWallet(request);
                assertTrue(false);
            } catch (BadRequestException e) {
                // ok_(emessage.startswith("BadRequest:"))
                assertEquals(e.getErrors().size(), 1);
                assertEquals(e.getErrors().get(0).getComponent(), "userId");
                assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.userId.error.require");
            }
        }

        {
            AddToWalletRequest request = new AddToWalletRequest()
                    .withGoldName(GOLD_NAME1)
                    .withUserId("")
                    .withValue(0);
            try {
                client.addToWallet(request);
                assertTrue(false);
            } catch (BadRequestException e) {
                // ok_(emessage.startswith("BadRequest:"))
                assertEquals(e.getErrors().size(), 1);
                assertEquals(e.getErrors().get(0).getComponent(), "userId");
                assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.userId.error.require");
            }
        }
    }

    @Test
    public void testSubtractFromWalletUserIdNone() {
        {
            SubtractFromWalletRequest request = new SubtractFromWalletRequest()
                    .withGoldName(GOLD_NAME1)
                    //                 .withUserId(USER_ID)
                    .withValue(0);
            try {
                client.subtractFromWallet(request);
                assertTrue(false);
            } catch (BadRequestException e) {
                // ok_(emessage.startswith("BadRequest:"))
                assertEquals(e.getErrors().size(), 1);
                assertEquals(e.getErrors().get(0).getComponent(), "userId");
                assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.userId.error.require");
            }
        }

        {
            SubtractFromWalletRequest request = new SubtractFromWalletRequest()
                    .withGoldName(GOLD_NAME1)
                    .withUserId("")
                    .withValue(0);
            try {
                client.subtractFromWallet(request);
                assertTrue(false);
            } catch (BadRequestException e) {
                // ok_(emessage.startswith("BadRequest:"))
                assertEquals(e.getErrors().size(), 1);
                assertEquals(e.getErrors().get(0).getComponent(), "userId");
                assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.userId.error.require");
            }
        }
    }

    @Test
    public void testResetLatestGainUserIdNone() {
        {
            ResetLatestGainRequest request = new ResetLatestGainRequest()
                    .withGoldName(GOLD_NAME1)
                    //                 .withUserId(USER_ID)
                    .withValue(0);
            try {
                client.resetLatestGain(request);
                assertTrue(false);
            } catch (BadRequestException e) {
                // ok_(emessage.startswith("BadRequest:"))
                assertEquals(e.getErrors().size(), 1);
                assertEquals(e.getErrors().get(0).getComponent(), "userId");
                assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.userId.error.require");
            }
        }

        {
            ResetLatestGainRequest request = new ResetLatestGainRequest()
                    .withGoldName(GOLD_NAME1)
                    .withUserId("")
                    .withValue(0);
            try {
                client.resetLatestGain(request);
                assertTrue(false);
            } catch (BadRequestException e) {
                // ok_(emessage.startswith("BadRequest:"))
                assertEquals(e.getErrors().size(), 1);
                assertEquals(e.getErrors().get(0).getComponent(), "userId");
                assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.userId.error.require");
            }
        }
    }

    @Test
    public void testAddToWalletValueNone() {
        {
            AddToWalletRequest request = new AddToWalletRequest()
                    .withGoldName(GOLD_NAME1)
                    .withUserId(USER_ID);
            //         .withValue(0)
            try {
                client.addToWallet(request);
                assertTrue(false);
            } catch (BadRequestException e) {
                // ok_(emessage.startswith("BadRequest:"))
                assertEquals(e.getErrors().size(), 1);
                assertEquals(e.getErrors().get(0).getComponent(), "value");
                assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.value.error.require");
            }
        }

        {
            AddToWalletRequest request = new AddToWalletRequest()
                    .withGoldName(GOLD_NAME1)
                    .withUserId(USER_ID)
                    .withValue(null);
            try {
                client.addToWallet(request);
                assertTrue(false);
            } catch (BadRequestException e) {
                // ok_(emessage.startswith("BadRequest:"))
                assertEquals(e.getErrors().size(), 1);
                assertEquals(e.getErrors().get(0).getComponent(), "value");
                assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.value.error.require");
            }
        }
    }

    @Test
    public void testAddToMyWalletValueNone() {
        {
            AddToMyWalletRequest request = new AddToMyWalletRequest()
                    .withGoldName(GOLD_NAME1)
                    //         .withValue(0)
                    .withAccessToken(accessToken);
            try {
                client.addToMyWallet(request);
                assertTrue(false);
            } catch (BadRequestException e) {
                // ok_(emessage.startswith("BadRequest:"))
                assertEquals(e.getErrors().size(), 1);
                assertEquals(e.getErrors().get(0).getComponent(), "value");
                assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.value.error.require");
            }
        }

        {
            AddToMyWalletRequest request = new AddToMyWalletRequest()
                    .withGoldName(GOLD_NAME1)
                    .withValue(null)
                    .withAccessToken(accessToken);
            try {
                client.addToMyWallet(request);
                assertTrue(false);
            } catch (BadRequestException e) {
                // ok_(emessage.startswith("BadRequest:"))
                assertEquals(e.getErrors().size(), 1);
                assertEquals(e.getErrors().get(0).getComponent(), "value");
                assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.value.error.require");
            }
        }
    }

    @Test
    public void testSubtractFromWalletValueNone() {
        {
            SubtractFromWalletRequest request = new SubtractFromWalletRequest()
                    .withGoldName(GOLD_NAME1)
                    .withUserId(USER_ID);
            //         .withValue(0)
            try {
                client.subtractFromWallet(request);
                assertTrue(false);
            } catch (BadRequestException e) {
                // ok_(emessage.startswith("BadRequest:"))
                assertEquals(e.getErrors().size(), 1);
                assertEquals(e.getErrors().get(0).getComponent(), "value");
                assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.value.error.require");
            }
        }

        {
            SubtractFromWalletRequest request = new SubtractFromWalletRequest()
                    .withGoldName(GOLD_NAME1)
                    .withUserId(USER_ID)
                    .withValue(null);
            try {
                client.subtractFromWallet(request);
                assertTrue(false);
            } catch (BadRequestException e) {
                // ok_(emessage.startswith("BadRequest:"))
                assertEquals(e.getErrors().size(), 1);
                assertEquals(e.getErrors().get(0).getComponent(), "value");
                assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.value.error.require");
            }
        }
    }

    @Test
    public void testSubtractFromMyWalletValueNone() {
        {
            SubtractFromMyWalletRequest request = new SubtractFromMyWalletRequest()
                    .withGoldName(GOLD_NAME1)
                    //         .withValue(0)
                    .withAccessToken(accessToken);
            try {
                client.subtractFromMyWallet(request);
                assertTrue(false);
            } catch (BadRequestException e) {
                // ok_(emessage.startswith("BadRequest:"))
                assertEquals(e.getErrors().size(), 1);
                assertEquals(e.getErrors().get(0).getComponent(), "value");
                assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.value.error.require");
            }
        }

        {
            SubtractFromMyWalletRequest request = new SubtractFromMyWalletRequest()
                    .withGoldName(GOLD_NAME1)
                    .withValue(null)
                    .withAccessToken(accessToken);
            try {
                client.subtractFromMyWallet(request);
                assertTrue(false);
            } catch (BadRequestException e) {
                // ok_(emessage.startswith("BadRequest:"))
                assertEquals(e.getErrors().size(), 1);
                assertEquals(e.getErrors().get(0).getComponent(), "value");
                assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.value.error.require");
            }
        }
    }

    @Test
    public void testAddToWalletValueNegative() {
        AddToWalletRequest request = new AddToWalletRequest()
                .withGoldName(GOLD_NAME1)
                .withUserId(USER_ID)
                .withValue(-100);
        try {
            client.addToWallet(request);
            assertTrue(false);
        } catch (BadRequestException e) {
            // ok_(emessage.startswith("BadRequest:"))
            assertEquals(e.getErrors().size(), 1);
            assertEquals(e.getErrors().get(0).getComponent(), "value");
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.value.error.require");
        }
    }

    @Test
    public void testAddToMyWalletValueNegative() {
        AddToMyWalletRequest request = new AddToMyWalletRequest()
                .withGoldName(GOLD_NAME1)
                .withValue(-100)
                .withAccessToken(accessToken);
        try {
            client.addToMyWallet(request);
            assertTrue(false);
        } catch (BadRequestException e) {
            // ok_(emessage.startswith("BadRequest:"))
            assertEquals(e.getErrors().size(), 1);
            assertEquals(e.getErrors().get(0).getComponent(), "value");
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.value.error.require");
        }
    }

    @Test
    public void testSubtractFromWalletValueNegative() {
        SubtractFromWalletRequest request = new SubtractFromWalletRequest()
                .withGoldName(GOLD_NAME1)
                .withUserId(USER_ID)
                .withValue(-100);
        try {
            client.subtractFromWallet(request);
            assertTrue(false);
        } catch (BadRequestException e) {
            // ok_(emessage.startswith("BadRequest:"))
            assertEquals(e.getErrors().size(), 1);
            assertEquals(e.getErrors().get(0).getComponent(), "value");
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.value.error.require");
        }
    }

    @Test
    public void testSubtractFromMyWalletValueNegative() {
        SubtractFromMyWalletRequest request = new SubtractFromMyWalletRequest()
                .withGoldName(GOLD_NAME1)
                .withValue(-100)
                .withAccessToken(accessToken);
        try {
            client.subtractFromMyWallet(request);
            assertTrue(false);
        } catch (BadRequestException e) {
            // ok_(emessage.startswith("BadRequest:"))
            assertEquals(e.getErrors().size(), 1);
            assertEquals(e.getErrors().get(0).getComponent(), "value");
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.value.error.require");
        }
    }

    @Test
    public void testResetLatestGainValueNegative() {
        ResetLatestGainRequest request = new ResetLatestGainRequest()
                .withGoldName(GOLD_NAME1)
                .withUserId(USER_ID)
                .withValue(-100);
        try {
            client.resetLatestGain(request);
            assertTrue(false);
        } catch (BadRequestException e) {
            // ok_(emessage.startswith("BadRequest:"))
            assertEquals(e.getErrors().size(), 1);
            assertEquals(e.getErrors().get(0).getComponent(), "value");
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.value.error.require");
        }
    }

    @Test
    public void testAddToWalletGoldNotActive() {
        {
            CreateGoldRequest request = new CreateGoldRequest()
                    .withName(GOLD_NAME4)
                    .withServiceClass("gold1.nano");
            client.createGold(request);
        }

        {
            AddToWalletRequest request = new AddToWalletRequest()
                    .withGoldName(GOLD_NAME4)
                    .withUserId(USER_ID)
                    .withValue(0);
            try {
                client.addToWallet(request);
                assertTrue(false);
            } catch (NotFoundException e) {
                // ok_(emessage.startswith("BadRequest:"))
                assertEquals(e.getErrors().size(), 1);
                assertEquals(e.getErrors().get(0).getComponent(), "gold");
                assertEquals(e.getErrors().get(0).getMessage(), "gold.gold.error.notFound");
            }
        }

        do {
            GetGoldStatusRequest request = new GetGoldStatusRequest()
                    .withGoldName(GOLD_NAME4);
            GetGoldStatusResult result = client.getGoldStatus(request);
            String status = result.getStatus();
            if (status.equals("ACTIVE")) break;
            assertFalse(status.equals("DELETED"));
            try {
                Thread.sleep(1000 * 1);
            } catch (InterruptedException e) {
            }
        } while (true);

        {
            DeleteGoldRequest request = new DeleteGoldRequest()
                    .withGoldName(GOLD_NAME4);
            // DeleteGoldResult result = client.deleteGold(request);
            client.deleteGold(request);
        }
    }

    // 通知のテスト
    @Test
    public void testSuccessfulNotifications() {
        {
            AddToWalletRequest request = new AddToWalletRequest()
                    .withGoldName(GOLD_NAME2)
                    .withUserId(USER_ID)
                    .withValue(100);
            AddToWalletResult result = client.addToWallet(request);
            assertNotNull(result);
            wallet1 = result.getItem();
            assertEquals(wallet1.getUserId(), USER_ID);
            assertEquals(wallet1.getBalance(), Integer.valueOf(100));
            assertEquals(wallet1.getLatestGain(), Integer.valueOf(100));
            assertNotNull(wallet1.getCreateAt());
            assertNotNull(wallet1.getUpdateAt());
        }

        {
            SubtractFromWalletRequest request = new SubtractFromWalletRequest()
                    .withGoldName(GOLD_NAME2)
                    .withUserId(USER_ID)
                    .withValue(60);
            SubtractFromWalletResult result = client.subtractFromWallet(request);
            assertNotNull(result);
            wallet1 = result.getItem();
            assertEquals(wallet1.getUserId(), USER_ID);
            assertEquals(wallet1.getBalance(), Integer.valueOf(40));
            assertEquals(wallet1.getLatestGain(), Integer.valueOf(100));
            assertNotNull(wallet1.getCreateAt());
            assertNotNull(wallet1.getUpdateAt());
        }
    }

    @Test
    public void testFailedNotifications() {
        // 通知先から失敗応答を受ける加算
        {
            AddToWalletRequest request = new AddToWalletRequest()
                    .withGoldName(GOLD_NAME3)
                    .withUserId(USER_ID)
                    .withValue(100)
                    .withContext("failure");
            try {
                client.addToWallet(request);
                assertTrue(false);
            } catch (BadGatewayException e) {
                // ok_(emessage.startswith("BadGateway:"))
                assertEquals(e.getErrors().size(), 1);
                assertEquals(e.getErrors().get(0).getComponent(), "Wallet");
                assertEquals(e.getErrors().get(0).getMessage(), "Wallet.AddToWallet.error.invokeError");
            }
        }

        // 通知が失敗した場合、加算は行われない
        {
            GetWalletRequest request = new GetWalletRequest()
                    .withGoldName(GOLD_NAME3)
                    .withUserId(USER_ID);
            GetWalletResult result = client.getWallet(request);
            assertNotNull(result);
            wallet1 = result.getItem();
            assertEquals(wallet1.getUserId(), USER_ID);
            assertEquals(wallet1.getBalance(), Integer.valueOf(0));
            assertEquals(wallet1.getLatestGain(), Integer.valueOf(0));
            assertNotNull(wallet1.getCreateAt());
            assertNotNull(wallet1.getUpdateAt());
        }

        // 通知先から失敗応答を受ける加算
        {
            AddToMyWalletRequest request = new AddToMyWalletRequest()
                    .withGoldName(GOLD_NAME3)
                    .withValue(100)
                    .withContext("failure")
                    .withAccessToken(accessToken);
            try {
                client.addToMyWallet(request);
                assertTrue(false);
            } catch (BadGatewayException e) {
                // ok_(emessage.startswith("BadGateway:"))
                assertEquals(e.getErrors().size(), 1);
                assertEquals(e.getErrors().get(0).getComponent(), "Wallet");
                assertEquals(e.getErrors().get(0).getMessage(), "Wallet.AddToMyWallet.error.invokeError");
            }
        }

        // 通知が失敗した場合、加算は行われない
        {
            GetWalletRequest request = new GetWalletRequest()
                    .withGoldName(GOLD_NAME3)
                    .withUserId(USER_ID);
            GetWalletResult result = client.getWallet(request);
            assertNotNull(result);
            wallet1 = result.getItem();
            assertEquals(wallet1.getUserId(), USER_ID);
            assertEquals(wallet1.getBalance(), Integer.valueOf(0));
            assertEquals(wallet1.getLatestGain(), Integer.valueOf(0));
            assertNotNull(wallet1.getCreateAt());
            assertNotNull(wallet1.getUpdateAt());
        }

        // 次の減算のテストのために成功する加算
        {
            AddToWalletRequest request = new AddToWalletRequest()
                    .withGoldName(GOLD_NAME3)
                    .withUserId(USER_ID)
                    .withValue(100)
                    .withContext("success");   // "failure" 以外なら何でもよい
            AddToWalletResult result = client.addToWallet(request);
            assertNotNull(result);
            wallet1 = result.getItem();
            assertEquals(wallet1.getUserId(), USER_ID);
            assertEquals(wallet1.getBalance(), Integer.valueOf(100));
            assertEquals(wallet1.getLatestGain(), Integer.valueOf(100));
            assertNotNull(wallet1.getCreateAt());
            assertNotNull(wallet1.getUpdateAt());
        }

        // 通知先から失敗応答を受ける減算
        {
            SubtractFromWalletRequest request = new SubtractFromWalletRequest()
                    .withGoldName(GOLD_NAME3)
                    .withUserId(USER_ID)
                    .withValue(60)
                    .withContext("failure");
            try {
                SubtractFromWalletResult result = client.subtractFromWallet(request);
                assertNotNull(result);
                wallet1 = result.getItem();
            } catch (BadGatewayException e) {
                // ok_(emessage.startswith("BadGateway:"))
                assertEquals(e.getErrors().size(), 1);
                assertEquals(e.getErrors().get(0).getComponent(), "Wallet");
                assertEquals(e.getErrors().get(0).getMessage(), "Wallet.SubtractFromWallet.error.invokeError");
            }
        }
        // 通知が失敗した場合、減算は行われない
        {
            GetWalletRequest request = new GetWalletRequest()
                    .withGoldName(GOLD_NAME3)
                    .withUserId(USER_ID);
            GetWalletResult result = client.getWallet(request);
            assertNotNull(result);
            wallet1 = result.getItem();
            assertEquals(wallet1.getUserId(), USER_ID);
            assertEquals(wallet1.getBalance(), Integer.valueOf(100));
            assertEquals(wallet1.getLatestGain(), Integer.valueOf(100));
            assertNotNull(wallet1.getCreateAt());
            assertNotNull(wallet1.getUpdateAt());
        }

        // 通知先から失敗応答を受ける減算
        {
            SubtractFromMyWalletRequest request = new SubtractFromMyWalletRequest()
                    .withGoldName(GOLD_NAME3)
                    .withValue(60)
                    .withContext("failure")
                    .withAccessToken(accessToken);
            try {
                SubtractFromMyWalletResult result = client.subtractFromMyWallet(request);
                assertNotNull(result);
                wallet1 = result.getItem();
            } catch (BadGatewayException e) {
                // ok_(emessage.startswith("BadGateway:"))
                assertEquals(e.getErrors().size(), 1);
                assertEquals(e.getErrors().get(0).getComponent(), "Wallet");
                assertEquals(e.getErrors().get(0).getMessage(), "Wallet.SubtractFromMyWallet.error.invokeError");
            }
        }

        // 通知が失敗した場合、減算は行われない
        {
            GetWalletRequest request = new GetWalletRequest()
                    .withGoldName(GOLD_NAME3)
                    .withUserId(USER_ID);
            GetWalletResult result = client.getWallet(request);
            assertNotNull(result);
            wallet1 = result.getItem();
            assertEquals(wallet1.getUserId(), USER_ID);
            assertEquals(wallet1.getBalance(), Integer.valueOf(100));
            assertEquals(wallet1.getLatestGain(), Integer.valueOf(100));
            assertNotNull(wallet1.getCreateAt());
            assertNotNull(wallet1.getUpdateAt());
        }
    }

    /*
    @Test
    public void testExceedQuota() {
        try {
            DeleteWalletsRequest request = new DeleteWalletsRequest()
                    .withGoldName(gold.getName())
                    .withUserId(USER_ID)
            event[
                "walletIds"] = "0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9"
            client.deleteWallets(request);
            assertTrue(false);
        } catch (QuotaExceedException e) {
            emessage = str(e)
            assertNotNull(emessage.startswith("QuotaExceed:");)
            messages = json.loads(emessage[emessage.find(":") + 1:])
            eq_(len(messages), 1)
            eq_(messages[0]["component"], "quota")
            eq_(messages[0]["message"], "auth.quota.error.exceed")
    */

	@AfterClass
	public static void shutdown() {
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
