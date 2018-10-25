package io.gs2.gold;

import io.gs2.exception.*;
import io.gs2.model.*;
import io.gs2.gold.model.*;
import io.gs2.gold.control.*;
import io.gs2.gold.Gs2GoldPrivateClient;
import io.gs2.model.BasicGs2Credential;
import io.gs2.auth.Gs2AuthClient;
import io.gs2.auth.control.*;
import io.gs2.model.BasicGs2Credential;
import junit.framework.TestCase;
import org.json.*;
import net.javacrumbs.jsonunit.JsonAssert;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RunWith(JUnit4.class)
public class WalletTest extends TestCase {
    private static Random rand = new Random();

    private static String REGION = "ap-northeast-1";
    private static String status = null;
    private static String accessToken1 = null;
    private static String nextPageToken = null;
    private static String CLIENT_ID = "gs2_gold_test_seeds.WalletTest-java";
    private static String CLIENT_SECRET = "rC56jmchfY32VYavhqvRKMiNdeFcCFIXuFTMwXE4+Zc=";
    private static BasicGs2Credential credential = new BasicGs2Credential(CLIENT_ID, CLIENT_SECRET);
    private static Gs2AuthClient authClient = new Gs2AuthClient(credential, REGION);
    private static List<GoldPool> goldPools = null;
    private static GoldPool goldPool1 = null;
    private static Wallet wallet1 = null;
    private static Wallet wallet2 = null;
    private static List<Wallet> wallets = null;
    private static Gs2GoldPrivateClient client = new Gs2GoldPrivateClient(credential);

    static {
        Gs2GoldPrivateClient.ENDPOINT = "gold-dev";
        Gs2AuthClient.ENDPOINT = "auth-dev";
    }

	@BeforeClass
	public static void setUpClass() {
		shutdown();
		try {

            {
                io.gs2.gold.control.CreateGoldPoolResult result = client.createGoldPool(
                    new io.gs2.gold.control.CreateGoldPoolRequest()
                        .withServiceClass("gold1.micro")
                        .withName("gold-pool-0001")
                );
                goldPool1 = result.getItem();
            }
            {
                status = null;
            
                int counter0 = 0;
                while (!"ACTIVE".equals(status)) {
                    assertTrue(counter0 < 100000);
                    ++counter0;
                    {
                        io.gs2.gold.control.GetGoldPoolStatusResult result = client.getGoldPoolStatus(
                            new io.gs2.gold.control.GetGoldPoolStatusRequest()
                                .withGoldPoolName(goldPool1.getName())
                        );
                        status = result.getStatus();
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {}
                }
            }
            {
                client.updateCurrentGoldMaster(
                    new io.gs2.gold.control.UpdateCurrentGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withSettings("{\n  \"golds\": [\n    {\n      \"balanceMax\": 99999999,\n      \"createWalletDoneTriggerScript\": null,\n      \"createWalletTriggerScript\": null,\n      \"depositIntoWalletDoneTriggerScript\": null,\n      \"depositIntoWalletTriggerScript\": null,\n      \"latestGainMax\": 99999999,\n      \"meta\": null,\n      \"name\": \"gold-0001\",\n      \"resetDayOfMonth\": null,\n      \"resetDayOfWeek\": null,\n      \"resetHour\": null,\n      \"resetType\": \"none\",\n      \"withdrawFromWalletDoneTriggerScript\": null,\n      \"withdrawFromWalletTriggerScript\": null\n    },\n    {\n      \"balanceMax\": 1000,\n      \"createWalletDoneTriggerScript\": \"GS2Gold_create_wallet_done_trigger_script\",\n      \"createWalletTriggerScript\": \"GS2Gold_create_wallet_trigger_script\",\n      \"depositIntoWalletDoneTriggerScript\": \"GS2Gold_deposit_into_wallet_done_trigger_script\",\n      \"depositIntoWalletTriggerScript\": \"GS2Gold_deposit_into_wallet_trigger_script\",\n      \"latestGainMax\": 500,\n      \"meta\": \"meta\",\n      \"name\": \"gold-0002\",\n      \"resetDayOfMonth\": 1,\n      \"resetDayOfWeek\": null,\n      \"resetHour\": 17,\n      \"resetType\": \"monthly\",\n      \"withdrawFromWalletDoneTriggerScript\": \"GS2Gold_withdraw_from_wallet_done_trigger_script\",\n      \"withdrawFromWalletTriggerScript\": \"GS2Gold_withdraw_from_wallet_trigger_script\"\n    },\n    {\n      \"balanceMax\": 2000,\n      \"createWalletDoneTriggerScript\": null,\n      \"createWalletTriggerScript\": null,\n      \"depositIntoWalletDoneTriggerScript\": null,\n      \"depositIntoWalletTriggerScript\": null,\n      \"latestGainMax\": 1000,\n      \"meta\": \"meta3\",\n      \"name\": \"gold-0003\",\n      \"resetDayOfMonth\": null,\n      \"resetDayOfWeek\": \"monday\",\n      \"resetHour\": 2,\n      \"resetType\": \"weekly\",\n      \"withdrawFromWalletDoneTriggerScript\": null,\n      \"withdrawFromWalletTriggerScript\": null\n    }\n  ],\n  \"version\": \"2018-08-03\"\n}")
                );
            }
            {
                io.gs2.auth.control.LoginResult result = authClient.login(
                    new io.gs2.auth.control.LoginRequest()
                        .withServiceId("service-0001")
                        .withUserId("user-0001")
                );
                assertNotNull(result);
                accessToken1 = result.getToken();
            }
		} catch (BadRequestException e) {
			shutdown();
			throw e;
		}
	}

    
    @Test
    public void testGetWalletSettingsGoldPoolNameNone() {
        try {
            {
                client.getWalletSettings(
                    new io.gs2.gold.control.GetWalletSettingsRequest()
                        .withGoldPoolName(null)
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.require");
            
        }
    }
    
    
    @Test
    public void testGetWalletSettingsGoldPoolNameEmpty() {
        try {
            {
                client.getWalletSettings(
                    new io.gs2.gold.control.GetWalletSettingsRequest()
                        .withGoldPoolName("")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.require");
            
        }
    }
    
    
    @Test
    public void testGetWalletSettingsGoldPoolNameInvalid() {
        try {
            {
                client.getWalletSettings(
                    new io.gs2.gold.control.GetWalletSettingsRequest()
                        .withGoldPoolName("@")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.invalid");
            
        }
    }
    
    
    @Test
    public void testGetWalletSettingsGoldPoolNameTooLong() {
        try {
            {
                client.getWalletSettings(
                    new io.gs2.gold.control.GetWalletSettingsRequest()
                        .withGoldPoolName("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testGetWalletSettingsGoldPoolNameNotFound() {
        try {
            {
                client.getWalletSettings(
                    new io.gs2.gold.control.GetWalletSettingsRequest()
                        .withGoldPoolName("not_found")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (NotFoundException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPool");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.goldPool.error.notFound");
            
        }
    }
    
    
    @Test
    public void testGetWalletSettingsGoldNameNone() {
        try {
            {
                client.getWalletSettings(
                    new io.gs2.gold.control.GetWalletSettingsRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withGoldName(null)
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.require");
            
        }
    }
    
    
    @Test
    public void testGetWalletSettingsGoldNameEmpty() {
        try {
            {
                client.getWalletSettings(
                    new io.gs2.gold.control.GetWalletSettingsRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withGoldName("")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.require");
            
        }
    }
    
    
    @Test
    public void testGetWalletSettingsGoldNameInvalid() {
        try {
            {
                client.getWalletSettings(
                    new io.gs2.gold.control.GetWalletSettingsRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withGoldName("@")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.invalid");
            
        }
    }
    
    
    @Test
    public void testGetWalletSettingsGoldNameTooLong() {
        try {
            {
                client.getWalletSettings(
                    new io.gs2.gold.control.GetWalletSettingsRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withGoldName("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testGetWalletSettingsGoldNameNotFound() {
        try {
            {
                client.getWalletSettings(
                    new io.gs2.gold.control.GetWalletSettingsRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withGoldName("not_found")
                );
            }
            assertTrue(false);
        }catch (NotFoundException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "gold");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.gold.gold.error.notFound");
            
        }
    }
    
    
    @Test
    public void testDepositIntoWalletByUserIdGoldPoolNameNone() {
        try {
            {
                client.depositIntoWalletByUserId(
                    new io.gs2.gold.control.DepositIntoWalletByUserIdRequest()
                        .withGoldPoolName(null)
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(40))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.require");
            
        }
    }
    
    
    @Test
    public void testDepositIntoWalletByUserIdGoldPoolNameEmpty() {
        try {
            {
                client.depositIntoWalletByUserId(
                    new io.gs2.gold.control.DepositIntoWalletByUserIdRequest()
                        .withGoldPoolName("")
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(40))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.require");
            
        }
    }
    
    
    @Test
    public void testDepositIntoWalletByUserIdGoldPoolNameInvalid() {
        try {
            {
                client.depositIntoWalletByUserId(
                    new io.gs2.gold.control.DepositIntoWalletByUserIdRequest()
                        .withGoldPoolName("@")
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(40))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.invalid");
            
        }
    }
    
    
    @Test
    public void testDepositIntoWalletByUserIdGoldPoolNameTooLong() {
        try {
            {
                client.depositIntoWalletByUserId(
                    new io.gs2.gold.control.DepositIntoWalletByUserIdRequest()
                        .withGoldPoolName("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(40))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testDepositIntoWalletByUserIdGoldPoolNameNotFound() {
        try {
            {
                client.depositIntoWalletByUserId(
                    new io.gs2.gold.control.DepositIntoWalletByUserIdRequest()
                        .withGoldPoolName("not_found")
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(40))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (NotFoundException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPool");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.goldPool.error.notFound");
            
        }
    }
    
    
    @Test
    public void testDepositIntoWalletByUserIdGoldNameNone() {
        try {
            {
                client.depositIntoWalletByUserId(
                    new io.gs2.gold.control.DepositIntoWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(40))
                        .withGoldName(null)
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.require");
            
        }
    }
    
    
    @Test
    public void testDepositIntoWalletByUserIdGoldNameEmpty() {
        try {
            {
                client.depositIntoWalletByUserId(
                    new io.gs2.gold.control.DepositIntoWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(40))
                        .withGoldName("")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.require");
            
        }
    }
    
    
    @Test
    public void testDepositIntoWalletByUserIdGoldNameInvalid() {
        try {
            {
                client.depositIntoWalletByUserId(
                    new io.gs2.gold.control.DepositIntoWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(40))
                        .withGoldName("@")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.invalid");
            
        }
    }
    
    
    @Test
    public void testDepositIntoWalletByUserIdGoldNameTooLong() {
        try {
            {
                client.depositIntoWalletByUserId(
                    new io.gs2.gold.control.DepositIntoWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(40))
                        .withGoldName("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testDepositIntoWalletByUserIdGoldNameNotFound() {
        try {
            {
                client.depositIntoWalletByUserId(
                    new io.gs2.gold.control.DepositIntoWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(40))
                        .withGoldName("not_found")
                );
            }
            assertTrue(false);
        }catch (NotFoundException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "gold");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.gold.gold.error.notFound");
            
        }
    }
    
    
    @Test
    public void testDepositIntoWalletByUserIdUserIdNone() {
        try {
            {
                client.depositIntoWalletByUserId(
                    new io.gs2.gold.control.DepositIntoWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId(null)
                        .withValue(Long.valueOf(40))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "userId");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.userId.error.require");
            
        }
    }
    
    
    @Test
    public void testDepositIntoWalletByUserIdUserIdEmpty() {
        try {
            {
                client.depositIntoWalletByUserId(
                    new io.gs2.gold.control.DepositIntoWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("")
                        .withValue(Long.valueOf(40))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "userId");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.userId.error.require");
            
        }
    }
    
    
    @Test
    public void testDepositIntoWalletByUserIdUserIdInvalid() {
        try {
            {
                client.depositIntoWalletByUserId(
                    new io.gs2.gold.control.DepositIntoWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("@")
                        .withValue(Long.valueOf(40))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "userId");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.userId.error.invalid");
            
        }
    }
    
    
    @Test
    public void testDepositIntoWalletByUserIdUserIdTooLong() {
        try {
            {
                client.depositIntoWalletByUserId(
                    new io.gs2.gold.control.DepositIntoWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withValue(Long.valueOf(40))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "userId");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.userId.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testDepositIntoWalletByUserIdValueNone() {
        try {
            {
                client.depositIntoWalletByUserId(
                    new io.gs2.gold.control.DepositIntoWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("user-0001")
                        .withValue(null)
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "value");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.value.error.require");
            
        }
    }
    
    
    @Test
    public void testDepositIntoWalletByUserIdValueInvalid() {
        try {
            {
                client.depositIntoWalletByUserId(
                    new io.gs2.gold.control.DepositIntoWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(-1))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "value");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.value.error.invalid");
            
        }
    }
    
    
    @Test
    public void testDescribeWalletByUserIdGoldPoolNameNone() {
        try {
            {
                client.describeWalletByUserId(
                    new io.gs2.gold.control.DescribeWalletByUserIdRequest()
                        .withGoldPoolName(null)
                        .withUserId("user-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.require");
            
        }
    }
    
    
    @Test
    public void testDescribeWalletByUserIdGoldPoolNameEmpty() {
        try {
            {
                client.describeWalletByUserId(
                    new io.gs2.gold.control.DescribeWalletByUserIdRequest()
                        .withGoldPoolName("")
                        .withUserId("user-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.require");
            
        }
    }
    
    
    @Test
    public void testDescribeWalletByUserIdGoldPoolNameInvalid() {
        try {
            {
                client.describeWalletByUserId(
                    new io.gs2.gold.control.DescribeWalletByUserIdRequest()
                        .withGoldPoolName("@")
                        .withUserId("user-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.invalid");
            
        }
    }
    
    
    @Test
    public void testDescribeWalletByUserIdGoldPoolNameTooLong() {
        try {
            {
                client.describeWalletByUserId(
                    new io.gs2.gold.control.DescribeWalletByUserIdRequest()
                        .withGoldPoolName("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withUserId("user-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testDescribeWalletByUserIdGoldPoolNameNotFound() {
        try {
            {
                client.describeWalletByUserId(
                    new io.gs2.gold.control.DescribeWalletByUserIdRequest()
                        .withGoldPoolName("not_found")
                        .withUserId("user-0001")
                );
            }
            assertTrue(false);
        }catch (NotFoundException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPool");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.goldPool.error.notFound");
            
        }
    }
    
    
    @Test
    public void testDescribeWalletByUserIdUserIdNone() {
        try {
            {
                client.describeWalletByUserId(
                    new io.gs2.gold.control.DescribeWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId(null)
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "userId");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.userId.error.require");
            
        }
    }
    
    
    @Test
    public void testDescribeWalletByUserIdUserIdEmpty() {
        try {
            {
                client.describeWalletByUserId(
                    new io.gs2.gold.control.DescribeWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "userId");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.userId.error.require");
            
        }
    }
    
    
    @Test
    public void testDescribeWalletByUserIdUserIdTooLong() {
        try {
            {
                client.describeWalletByUserId(
                    new io.gs2.gold.control.DescribeWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "userId");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.userId.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testDescribeWalletByUserIdUserIdInvalid() {
        try {
            {
                client.describeWalletByUserId(
                    new io.gs2.gold.control.DescribeWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("@")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "userId");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.userId.error.invalid");
            
        }
    }
    
    
    @Test
    public void testDescribeWalletByUserIdLimitInvalid() {
        try {
            {
                client.describeWalletByUserId(
                    new io.gs2.gold.control.DescribeWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("user-0001")
                        .withLimit(Integer.valueOf(-1))
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "limit");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.limit.error.invalid");
            
        }
    }
    
    
    @Test
    public void testWithdrawFromWalletByUserIdGoldPoolNameNone() {
        try {
            {
                client.withdrawFromWalletByUserId(
                    new io.gs2.gold.control.WithdrawFromWalletByUserIdRequest()
                        .withGoldPoolName(null)
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(40))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.require");
            
        }
    }
    
    
    @Test
    public void testWithdrawFromWalletByUserIdGoldPoolNameEmpty() {
        try {
            {
                client.withdrawFromWalletByUserId(
                    new io.gs2.gold.control.WithdrawFromWalletByUserIdRequest()
                        .withGoldPoolName("")
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(40))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.require");
            
        }
    }
    
    
    @Test
    public void testWithdrawFromWalletByUserIdGoldPoolNameInvalid() {
        try {
            {
                client.withdrawFromWalletByUserId(
                    new io.gs2.gold.control.WithdrawFromWalletByUserIdRequest()
                        .withGoldPoolName("@")
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(40))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.invalid");
            
        }
    }
    
    
    @Test
    public void testWithdrawFromWalletByUserIdGoldPoolNameTooLong() {
        try {
            {
                client.withdrawFromWalletByUserId(
                    new io.gs2.gold.control.WithdrawFromWalletByUserIdRequest()
                        .withGoldPoolName("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(40))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testWithdrawFromWalletByUserIdGoldPoolNameNotFound() {
        try {
            {
                client.withdrawFromWalletByUserId(
                    new io.gs2.gold.control.WithdrawFromWalletByUserIdRequest()
                        .withGoldPoolName("not_found")
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(40))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (NotFoundException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPool");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.goldPool.error.notFound");
            
        }
    }
    
    
    @Test
    public void testWithdrawFromWalletByUserIdGoldNameNone() {
        try {
            {
                client.withdrawFromWalletByUserId(
                    new io.gs2.gold.control.WithdrawFromWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(40))
                        .withGoldName(null)
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.require");
            
        }
    }
    
    
    @Test
    public void testWithdrawFromWalletByUserIdGoldNameEmpty() {
        try {
            {
                client.withdrawFromWalletByUserId(
                    new io.gs2.gold.control.WithdrawFromWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(40))
                        .withGoldName("")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.require");
            
        }
    }
    
    
    @Test
    public void testWithdrawFromWalletByUserIdGoldNameInvalid() {
        try {
            {
                client.withdrawFromWalletByUserId(
                    new io.gs2.gold.control.WithdrawFromWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(40))
                        .withGoldName("@")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.invalid");
            
        }
    }
    
    
    @Test
    public void testWithdrawFromWalletByUserIdGoldNameTooLong() {
        try {
            {
                client.withdrawFromWalletByUserId(
                    new io.gs2.gold.control.WithdrawFromWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(40))
                        .withGoldName("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testWithdrawFromWalletByUserIdGoldNameNotFound() {
        try {
            {
                client.withdrawFromWalletByUserId(
                    new io.gs2.gold.control.WithdrawFromWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(40))
                        .withGoldName("not_found")
                );
            }
            assertTrue(false);
        }catch (NotFoundException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "gold");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.gold.gold.error.notFound");
            
        }
    }
    
    
    @Test
    public void testWithdrawFromWalletByUserIdUserIdNone() {
        try {
            {
                client.withdrawFromWalletByUserId(
                    new io.gs2.gold.control.WithdrawFromWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId(null)
                        .withValue(Long.valueOf(40))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "userId");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.userId.error.require");
            
        }
    }
    
    
    @Test
    public void testWithdrawFromWalletByUserIdUserIdEmpty() {
        try {
            {
                client.withdrawFromWalletByUserId(
                    new io.gs2.gold.control.WithdrawFromWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("")
                        .withValue(Long.valueOf(40))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "userId");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.userId.error.require");
            
        }
    }
    
    
    @Test
    public void testWithdrawFromWalletByUserIdUserIdInvalid() {
        try {
            {
                client.withdrawFromWalletByUserId(
                    new io.gs2.gold.control.WithdrawFromWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("@")
                        .withValue(Long.valueOf(40))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "userId");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.userId.error.invalid");
            
        }
    }
    
    
    @Test
    public void testWithdrawFromWalletByUserIdUserIdTooLong() {
        try {
            {
                client.withdrawFromWalletByUserId(
                    new io.gs2.gold.control.WithdrawFromWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withValue(Long.valueOf(40))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "userId");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.userId.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testWithdrawFromWalletByUserIdValueNone() {
        try {
            {
                client.withdrawFromWalletByUserId(
                    new io.gs2.gold.control.WithdrawFromWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("user-0001")
                        .withValue(null)
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "value");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.value.error.require");
            
        }
    }
    
    
    @Test
    public void testWithdrawFromWalletByUserIdValueInvalid() {
        try {
            {
                client.withdrawFromWalletByUserId(
                    new io.gs2.gold.control.WithdrawFromWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(-1))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "value");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.value.error.invalid");
            
        }
    }
    
    
    @Test
    public void testDescribeWalletGoldPoolNameNone() {
        try {
            {
                client.describeWallet(
                    new io.gs2.gold.control.DescribeWalletRequest()
                        .withGoldPoolName(null)
                        .withAccessToken(accessToken1)
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.require");
            
        }
    }
    
    
    @Test
    public void testDescribeWalletGoldPoolNameEmpty() {
        try {
            {
                client.describeWallet(
                    new io.gs2.gold.control.DescribeWalletRequest()
                        .withGoldPoolName("")
                        .withAccessToken(accessToken1)
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.require");
            
        }
    }
    
    
    @Test
    public void testDescribeWalletGoldPoolNameInvalid() {
        try {
            {
                client.describeWallet(
                    new io.gs2.gold.control.DescribeWalletRequest()
                        .withGoldPoolName("@")
                        .withAccessToken(accessToken1)
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.invalid");
            
        }
    }
    
    
    @Test
    public void testDescribeWalletGoldPoolNameTooLong() {
        try {
            {
                client.describeWallet(
                    new io.gs2.gold.control.DescribeWalletRequest()
                        .withGoldPoolName("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withAccessToken(accessToken1)
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testDescribeWalletGoldPoolNameNotFound() {
        try {
            {
                client.describeWallet(
                    new io.gs2.gold.control.DescribeWalletRequest()
                        .withGoldPoolName("not_found")
                        .withAccessToken(accessToken1)
                );
            }
            assertTrue(false);
        }catch (NotFoundException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPool");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.goldPool.error.notFound");
            
        }
    }
    
    
    @Test
    public void testDescribeWalletLimitInvalid() {
        try {
            {
                client.describeWallet(
                    new io.gs2.gold.control.DescribeWalletRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withLimit(Integer.valueOf(-1))
                        .withAccessToken(accessToken1)
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "limit");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.limit.error.invalid");
            
        }
    }
    
    
    @Test
    public void testDepositIntoWalletGoldPoolNameNone() {
        try {
            {
                client.depositIntoWallet(
                    new io.gs2.gold.control.DepositIntoWalletRequest()
                        .withGoldPoolName(null)
                        .withValue(Long.valueOf(40))
                        .withAccessToken(accessToken1)
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.require");
            
        }
    }
    
    
    @Test
    public void testDepositIntoWalletGoldPoolNameEmpty() {
        try {
            {
                client.depositIntoWallet(
                    new io.gs2.gold.control.DepositIntoWalletRequest()
                        .withGoldPoolName("")
                        .withValue(Long.valueOf(40))
                        .withAccessToken(accessToken1)
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.require");
            
        }
    }
    
    
    @Test
    public void testDepositIntoWalletGoldPoolNameInvalid() {
        try {
            {
                client.depositIntoWallet(
                    new io.gs2.gold.control.DepositIntoWalletRequest()
                        .withGoldPoolName("@")
                        .withValue(Long.valueOf(40))
                        .withAccessToken(accessToken1)
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.invalid");
            
        }
    }
    
    
    @Test
    public void testDepositIntoWalletGoldPoolNameTooLong() {
        try {
            {
                client.depositIntoWallet(
                    new io.gs2.gold.control.DepositIntoWalletRequest()
                        .withGoldPoolName("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withValue(Long.valueOf(40))
                        .withAccessToken(accessToken1)
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testDepositIntoWalletGoldPoolNameNotFound() {
        try {
            {
                client.depositIntoWallet(
                    new io.gs2.gold.control.DepositIntoWalletRequest()
                        .withGoldPoolName("not_found")
                        .withValue(Long.valueOf(40))
                        .withAccessToken(accessToken1)
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (NotFoundException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPool");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.goldPool.error.notFound");
            
        }
    }
    
    
    @Test
    public void testDepositIntoWalletGoldNameNone() {
        try {
            {
                client.depositIntoWallet(
                    new io.gs2.gold.control.DepositIntoWalletRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withValue(Long.valueOf(40))
                        .withAccessToken(accessToken1)
                        .withGoldName(null)
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.require");
            
        }
    }
    
    
    @Test
    public void testDepositIntoWalletGoldNameEmpty() {
        try {
            {
                client.depositIntoWallet(
                    new io.gs2.gold.control.DepositIntoWalletRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withValue(Long.valueOf(40))
                        .withAccessToken(accessToken1)
                        .withGoldName("")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.require");
            
        }
    }
    
    
    @Test
    public void testDepositIntoWalletGoldNameInvalid() {
        try {
            {
                client.depositIntoWallet(
                    new io.gs2.gold.control.DepositIntoWalletRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withValue(Long.valueOf(40))
                        .withAccessToken(accessToken1)
                        .withGoldName("@")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.invalid");
            
        }
    }
    
    
    @Test
    public void testDepositIntoWalletGoldNameTooLong() {
        try {
            {
                client.depositIntoWallet(
                    new io.gs2.gold.control.DepositIntoWalletRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withValue(Long.valueOf(40))
                        .withAccessToken(accessToken1)
                        .withGoldName("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testDepositIntoWalletGoldNameNotFound() {
        try {
            {
                client.depositIntoWallet(
                    new io.gs2.gold.control.DepositIntoWalletRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withValue(Long.valueOf(40))
                        .withAccessToken(accessToken1)
                        .withGoldName("not_found")
                );
            }
            assertTrue(false);
        }catch (NotFoundException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "gold");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.gold.gold.error.notFound");
            
        }
    }
    
    
    @Test
    public void testDepositIntoWalletValueNone() {
        try {
            {
                client.depositIntoWallet(
                    new io.gs2.gold.control.DepositIntoWalletRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withValue(null)
                        .withAccessToken(accessToken1)
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "value");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.value.error.require");
            
        }
    }
    
    
    @Test
    public void testDepositIntoWalletValueInvalid() {
        try {
            {
                client.depositIntoWallet(
                    new io.gs2.gold.control.DepositIntoWalletRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withValue(Long.valueOf(-1))
                        .withAccessToken(accessToken1)
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "value");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.value.error.invalid");
            
        }
    }
    
    
    @Test
    public void testDepositIntoWalletAccessTokenNone() {
        try {
            {
                client.depositIntoWallet(
                    new io.gs2.gold.control.DepositIntoWalletRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withValue(Long.valueOf(40))
                        .withAccessToken(null)
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "accessToken");
            
            assertEquals(e.getErrors().get(0).getMessage(), "auth.accessToken.error.require");
            
        }
    }
    
    
    @Test
    public void testDepositIntoWalletAccessTokenEmpty() {
        try {
            {
                client.depositIntoWallet(
                    new io.gs2.gold.control.DepositIntoWalletRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withValue(Long.valueOf(40))
                        .withAccessToken("")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "accessToken");
            
            assertEquals(e.getErrors().get(0).getMessage(), "auth.accessToken.error.require");
            
        }
    }
    
    
    @Test
    public void testDepositIntoWalletAccessTokenInvalid() {
        try {
            {
                client.depositIntoWallet(
                    new io.gs2.gold.control.DepositIntoWalletRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withValue(Long.valueOf(40))
                        .withAccessToken("invalid")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (UnauthorizedException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "accessToken");
            
            assertEquals(e.getErrors().get(0).getMessage(), "auth.accessToken.error.expired");
            
        }
    }
    
    
    @Test
    public void testGetWalletByUserIdGoldPoolNameNone() {
        try {
            {
                client.getWalletByUserId(
                    new io.gs2.gold.control.GetWalletByUserIdRequest()
                        .withGoldPoolName(null)
                        .withUserId("user-0001")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.require");
            
        }
    }
    
    
    @Test
    public void testGetWalletByUserIdGoldPoolNameEmpty() {
        try {
            {
                client.getWalletByUserId(
                    new io.gs2.gold.control.GetWalletByUserIdRequest()
                        .withGoldPoolName("")
                        .withUserId("user-0001")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.require");
            
        }
    }
    
    
    @Test
    public void testGetWalletByUserIdGoldPoolNameInvalid() {
        try {
            {
                client.getWalletByUserId(
                    new io.gs2.gold.control.GetWalletByUserIdRequest()
                        .withGoldPoolName("@")
                        .withUserId("user-0001")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.invalid");
            
        }
    }
    
    
    @Test
    public void testGetWalletByUserIdGoldPoolNameTooLong() {
        try {
            {
                client.getWalletByUserId(
                    new io.gs2.gold.control.GetWalletByUserIdRequest()
                        .withGoldPoolName("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withUserId("user-0001")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testGetWalletByUserIdGoldPoolNameNotFound() {
        try {
            {
                client.getWalletByUserId(
                    new io.gs2.gold.control.GetWalletByUserIdRequest()
                        .withGoldPoolName("not_found")
                        .withUserId("user-0001")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (NotFoundException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPool");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.goldPool.error.notFound");
            
        }
    }
    
    
    @Test
    public void testGetWalletByUserIdGoldNameNone() {
        try {
            {
                client.getWalletByUserId(
                    new io.gs2.gold.control.GetWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("user-0001")
                        .withGoldName(null)
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.require");
            
        }
    }
    
    
    @Test
    public void testGetWalletByUserIdGoldNameEmpty() {
        try {
            {
                client.getWalletByUserId(
                    new io.gs2.gold.control.GetWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("user-0001")
                        .withGoldName("")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.require");
            
        }
    }
    
    
    @Test
    public void testGetWalletByUserIdGoldNameInvalid() {
        try {
            {
                client.getWalletByUserId(
                    new io.gs2.gold.control.GetWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("user-0001")
                        .withGoldName("@")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.invalid");
            
        }
    }
    
    
    @Test
    public void testGetWalletByUserIdGoldNameTooLong() {
        try {
            {
                client.getWalletByUserId(
                    new io.gs2.gold.control.GetWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("user-0001")
                        .withGoldName("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testGetWalletByUserIdGoldNameNotFound() {
        try {
            {
                client.getWalletByUserId(
                    new io.gs2.gold.control.GetWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("user-0001")
                        .withGoldName("not_found")
                );
            }
            assertTrue(false);
        }catch (NotFoundException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "gold");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.gold.gold.error.notFound");
            
        }
    }
    
    
    @Test
    public void testGetWalletByUserIdUserIdNone() {
        try {
            {
                client.getWalletByUserId(
                    new io.gs2.gold.control.GetWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId(null)
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "userId");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.userId.error.require");
            
        }
    }
    
    
    @Test
    public void testGetWalletByUserIdUserIdEmpty() {
        try {
            {
                client.getWalletByUserId(
                    new io.gs2.gold.control.GetWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "userId");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.userId.error.require");
            
        }
    }
    
    
    @Test
    public void testGetWalletByUserIdUserIdInvalid() {
        try {
            {
                client.getWalletByUserId(
                    new io.gs2.gold.control.GetWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("@")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "userId");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.userId.error.invalid");
            
        }
    }
    
    
    @Test
    public void testGetWalletByUserIdUserIdTooLong() {
        try {
            {
                client.getWalletByUserId(
                    new io.gs2.gold.control.GetWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "userId");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.userId.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testGetWalletGoldPoolNameNone() {
        try {
            {
                client.getWallet(
                    new io.gs2.gold.control.GetWalletRequest()
                        .withGoldPoolName(null)
                        .withAccessToken(accessToken1)
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.require");
            
        }
    }
    
    
    @Test
    public void testGetWalletGoldPoolNameEmpty() {
        try {
            {
                client.getWallet(
                    new io.gs2.gold.control.GetWalletRequest()
                        .withGoldPoolName("")
                        .withAccessToken(accessToken1)
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.require");
            
        }
    }
    
    
    @Test
    public void testGetWalletGoldPoolNameInvalid() {
        try {
            {
                client.getWallet(
                    new io.gs2.gold.control.GetWalletRequest()
                        .withGoldPoolName("@")
                        .withAccessToken(accessToken1)
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.invalid");
            
        }
    }
    
    
    @Test
    public void testGetWalletGoldPoolNameTooLong() {
        try {
            {
                client.getWallet(
                    new io.gs2.gold.control.GetWalletRequest()
                        .withGoldPoolName("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withAccessToken(accessToken1)
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testGetWalletGoldPoolNameNotFound() {
        try {
            {
                client.getWallet(
                    new io.gs2.gold.control.GetWalletRequest()
                        .withGoldPoolName("not_found")
                        .withAccessToken(accessToken1)
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (NotFoundException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPool");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.goldPool.error.notFound");
            
        }
    }
    
    
    @Test
    public void testGetWalletGoldNameNone() {
        try {
            {
                client.getWallet(
                    new io.gs2.gold.control.GetWalletRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withAccessToken(accessToken1)
                        .withGoldName(null)
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.require");
            
        }
    }
    
    
    @Test
    public void testGetWalletGoldNameEmpty() {
        try {
            {
                client.getWallet(
                    new io.gs2.gold.control.GetWalletRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withAccessToken(accessToken1)
                        .withGoldName("")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.require");
            
        }
    }
    
    
    @Test
    public void testGetWalletGoldNameInvalid() {
        try {
            {
                client.getWallet(
                    new io.gs2.gold.control.GetWalletRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withAccessToken(accessToken1)
                        .withGoldName("@")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.invalid");
            
        }
    }
    
    
    @Test
    public void testGetWalletGoldNameTooLong() {
        try {
            {
                client.getWallet(
                    new io.gs2.gold.control.GetWalletRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withAccessToken(accessToken1)
                        .withGoldName("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testGetWalletGoldNameNotFound() {
        try {
            {
                client.getWallet(
                    new io.gs2.gold.control.GetWalletRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withAccessToken(accessToken1)
                        .withGoldName("not_found")
                );
            }
            assertTrue(false);
        }catch (NotFoundException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "gold");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.gold.gold.error.notFound");
            
        }
    }
    
    
    @Test
    public void testGetWalletAccessTokenNone() {
        try {
            {
                client.getWallet(
                    new io.gs2.gold.control.GetWalletRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withAccessToken(null)
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "accessToken");
            
            assertEquals(e.getErrors().get(0).getMessage(), "auth.accessToken.error.require");
            
        }
    }
    
    
    @Test
    public void testGetWalletAccessTokenEmpty() {
        try {
            {
                client.getWallet(
                    new io.gs2.gold.control.GetWalletRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withAccessToken("")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "accessToken");
            
            assertEquals(e.getErrors().get(0).getMessage(), "auth.accessToken.error.require");
            
        }
    }
    
    
    @Test
    public void testGetWalletAccessTokenInvalid() {
        try {
            {
                client.getWallet(
                    new io.gs2.gold.control.GetWalletRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withAccessToken("invalid")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (UnauthorizedException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "accessToken");
            
            assertEquals(e.getErrors().get(0).getMessage(), "auth.accessToken.error.expired");
            
        }
    }
    
    
    @Test
    public void testSetLatestGainGoldPoolNameNone() {
        try {
            {
                client.setLatestGain(
                    new io.gs2.gold.control.SetLatestGainRequest()
                        .withGoldPoolName(null)
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(40))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.require");
            
        }
    }
    
    
    @Test
    public void testSetLatestGainGoldPoolNameEmpty() {
        try {
            {
                client.setLatestGain(
                    new io.gs2.gold.control.SetLatestGainRequest()
                        .withGoldPoolName("")
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(40))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.require");
            
        }
    }
    
    
    @Test
    public void testSetLatestGainGoldPoolNameInvalid() {
        try {
            {
                client.setLatestGain(
                    new io.gs2.gold.control.SetLatestGainRequest()
                        .withGoldPoolName("@")
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(40))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.invalid");
            
        }
    }
    
    
    @Test
    public void testSetLatestGainGoldPoolNameTooLong() {
        try {
            {
                client.setLatestGain(
                    new io.gs2.gold.control.SetLatestGainRequest()
                        .withGoldPoolName("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(40))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testSetLatestGainGoldPoolNameNotFound() {
        try {
            {
                client.setLatestGain(
                    new io.gs2.gold.control.SetLatestGainRequest()
                        .withGoldPoolName("not_found")
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(40))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (NotFoundException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPool");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.goldPool.error.notFound");
            
        }
    }
    
    
    @Test
    public void testSetLatestGainGoldNameNone() {
        try {
            {
                client.setLatestGain(
                    new io.gs2.gold.control.SetLatestGainRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(40))
                        .withGoldName(null)
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.require");
            
        }
    }
    
    
    @Test
    public void testSetLatestGainGoldNameEmpty() {
        try {
            {
                client.setLatestGain(
                    new io.gs2.gold.control.SetLatestGainRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(40))
                        .withGoldName("")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.require");
            
        }
    }
    
    
    @Test
    public void testSetLatestGainGoldNameInvalid() {
        try {
            {
                client.setLatestGain(
                    new io.gs2.gold.control.SetLatestGainRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(40))
                        .withGoldName("@")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.invalid");
            
        }
    }
    
    
    @Test
    public void testSetLatestGainGoldNameTooLong() {
        try {
            {
                client.setLatestGain(
                    new io.gs2.gold.control.SetLatestGainRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(40))
                        .withGoldName("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testSetLatestGainGoldNameNotFound() {
        try {
            {
                client.setLatestGain(
                    new io.gs2.gold.control.SetLatestGainRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(40))
                        .withGoldName("not_found")
                );
            }
            assertTrue(false);
        }catch (NotFoundException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "gold");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.gold.gold.error.notFound");
            
        }
    }
    
    
    @Test
    public void testSetLatestGainUserIdNone() {
        try {
            {
                client.setLatestGain(
                    new io.gs2.gold.control.SetLatestGainRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId(null)
                        .withValue(Long.valueOf(40))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "userId");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.userId.error.require");
            
        }
    }
    
    
    @Test
    public void testSetLatestGainUserIdEmpty() {
        try {
            {
                client.setLatestGain(
                    new io.gs2.gold.control.SetLatestGainRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("")
                        .withValue(Long.valueOf(40))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "userId");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.userId.error.require");
            
        }
    }
    
    
    @Test
    public void testSetLatestGainUserIdInvalid() {
        try {
            {
                client.setLatestGain(
                    new io.gs2.gold.control.SetLatestGainRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("@")
                        .withValue(Long.valueOf(40))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "userId");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.userId.error.invalid");
            
        }
    }
    
    
    @Test
    public void testSetLatestGainUserIdTooLong() {
        try {
            {
                client.setLatestGain(
                    new io.gs2.gold.control.SetLatestGainRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withValue(Long.valueOf(40))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "userId");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.userId.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testSetLatestGainValueInvalid() {
        try {
            {
                client.setLatestGain(
                    new io.gs2.gold.control.SetLatestGainRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(-1))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "value");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.value.error.invalid");
            
        }
    }
    
    
    @Test
    public void testWithdrawFromWalletGoldPoolNameNone() {
        try {
            {
                client.withdrawFromWallet(
                    new io.gs2.gold.control.WithdrawFromWalletRequest()
                        .withGoldPoolName(null)
                        .withValue(Long.valueOf(40))
                        .withAccessToken(accessToken1)
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.require");
            
        }
    }
    
    
    @Test
    public void testWithdrawFromWalletGoldPoolNameEmpty() {
        try {
            {
                client.withdrawFromWallet(
                    new io.gs2.gold.control.WithdrawFromWalletRequest()
                        .withGoldPoolName("")
                        .withValue(Long.valueOf(40))
                        .withAccessToken(accessToken1)
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.require");
            
        }
    }
    
    
    @Test
    public void testWithdrawFromWalletGoldPoolNameInvalid() {
        try {
            {
                client.withdrawFromWallet(
                    new io.gs2.gold.control.WithdrawFromWalletRequest()
                        .withGoldPoolName("@")
                        .withValue(Long.valueOf(40))
                        .withAccessToken(accessToken1)
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.invalid");
            
        }
    }
    
    
    @Test
    public void testWithdrawFromWalletGoldPoolNameTooLong() {
        try {
            {
                client.withdrawFromWallet(
                    new io.gs2.gold.control.WithdrawFromWalletRequest()
                        .withGoldPoolName("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withValue(Long.valueOf(40))
                        .withAccessToken(accessToken1)
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldPoolName.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testWithdrawFromWalletGoldPoolNameNotFound() {
        try {
            {
                client.withdrawFromWallet(
                    new io.gs2.gold.control.WithdrawFromWalletRequest()
                        .withGoldPoolName("not_found")
                        .withValue(Long.valueOf(40))
                        .withAccessToken(accessToken1)
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (NotFoundException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPool");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.goldPool.error.notFound");
            
        }
    }
    
    
    @Test
    public void testWithdrawFromWalletGoldNameNone() {
        try {
            {
                client.withdrawFromWallet(
                    new io.gs2.gold.control.WithdrawFromWalletRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withValue(Long.valueOf(40))
                        .withAccessToken(accessToken1)
                        .withGoldName(null)
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.require");
            
        }
    }
    
    
    @Test
    public void testWithdrawFromWalletGoldNameEmpty() {
        try {
            {
                client.withdrawFromWallet(
                    new io.gs2.gold.control.WithdrawFromWalletRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withValue(Long.valueOf(40))
                        .withAccessToken(accessToken1)
                        .withGoldName("")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.require");
            
        }
    }
    
    
    @Test
    public void testWithdrawFromWalletGoldNameInvalid() {
        try {
            {
                client.withdrawFromWallet(
                    new io.gs2.gold.control.WithdrawFromWalletRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withValue(Long.valueOf(40))
                        .withAccessToken(accessToken1)
                        .withGoldName("@")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.invalid");
            
        }
    }
    
    
    @Test
    public void testWithdrawFromWalletGoldNameTooLong() {
        try {
            {
                client.withdrawFromWallet(
                    new io.gs2.gold.control.WithdrawFromWalletRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withValue(Long.valueOf(40))
                        .withAccessToken(accessToken1)
                        .withGoldName("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.goldName.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testWithdrawFromWalletGoldNameNotFound() {
        try {
            {
                client.withdrawFromWallet(
                    new io.gs2.gold.control.WithdrawFromWalletRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withValue(Long.valueOf(40))
                        .withAccessToken(accessToken1)
                        .withGoldName("not_found")
                );
            }
            assertTrue(false);
        }catch (NotFoundException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "gold");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.gold.gold.error.notFound");
            
        }
    }
    
    
    @Test
    public void testWithdrawFromWalletValueNone() {
        try {
            {
                client.withdrawFromWallet(
                    new io.gs2.gold.control.WithdrawFromWalletRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withValue(null)
                        .withAccessToken(accessToken1)
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "value");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.value.error.require");
            
        }
    }
    
    
    @Test
    public void testWithdrawFromWalletValueInvalid() {
        try {
            {
                client.withdrawFromWallet(
                    new io.gs2.gold.control.WithdrawFromWalletRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withValue(Long.valueOf(-1))
                        .withAccessToken(accessToken1)
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "value");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.value.error.invalid");
            
        }
    }
    
    
    @Test
    public void testWithdrawFromWalletAccessTokenNone() {
        try {
            {
                client.withdrawFromWallet(
                    new io.gs2.gold.control.WithdrawFromWalletRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withValue(Long.valueOf(40))
                        .withAccessToken(null)
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "accessToken");
            
            assertEquals(e.getErrors().get(0).getMessage(), "auth.accessToken.error.require");
            
        }
    }
    
    
    @Test
    public void testWithdrawFromWalletAccessTokenEmpty() {
        try {
            {
                client.withdrawFromWallet(
                    new io.gs2.gold.control.WithdrawFromWalletRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withValue(Long.valueOf(40))
                        .withAccessToken("")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "accessToken");
            
            assertEquals(e.getErrors().get(0).getMessage(), "auth.accessToken.error.require");
            
        }
    }
    
    
    @Test
    public void testWithdrawFromWalletAccessTokenInvalid() {
        try {
            {
                client.withdrawFromWallet(
                    new io.gs2.gold.control.WithdrawFromWalletRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withValue(Long.valueOf(40))
                        .withAccessToken("invalid")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (UnauthorizedException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "accessToken");
            
            assertEquals(e.getErrors().get(0).getMessage(), "auth.accessToken.error.expired");
            
        }
    }
    
    
    @Test
    public void testBasic() {
        // あってもなくても Get できる
        {
            io.gs2.gold.control.GetWalletByUserIdResult result = client.getWalletByUserId(
                new io.gs2.gold.control.GetWalletByUserIdRequest()
                    .withGoldPoolName("gold-pool-0001")
                    .withUserId("user-0001")
                    .withGoldName("gold-0001")
            );
            assertNotNull(result);
            assertNotNull(result.getItem());
            assertEquals(result.getItem().getGoldName(), "gold-0001");
            assertEquals(result.getItem().getUserId(), "user-0001");
            assertEquals(result.getItem().getBalance(), Long.valueOf(0));
            assertEquals(result.getItem().getLatestGain(), Long.valueOf(0));
            assertNotNull(result.getItem().getCreateAt());
            assertNotNull(result.getItem().getUpdateAt());
            wallet1 = result.getItem();
        }
        {
            io.gs2.gold.control.GetWalletResult result = client.getWallet(
                new io.gs2.gold.control.GetWalletRequest()
                    .withGoldPoolName("gold-pool-0001")
                    .withAccessToken(accessToken1)
                    .withGoldName("gold-0001")
            );
            assertNotNull(result);
            assertNotNull(result.getItem());
            assertEquals(result.getItem().getGoldName(), "gold-0001");
            assertEquals(result.getItem().getUserId(), "user-0001");
            assertEquals(result.getItem().getBalance(), Long.valueOf(0));
            assertEquals(result.getItem().getLatestGain(), Long.valueOf(0));
            assertNotNull(result.getItem().getCreateAt());
            assertNotNull(result.getItem().getUpdateAt());
            wallet1 = result.getItem();
        }
        // 残高を加算
        {
            io.gs2.gold.control.DepositIntoWalletByUserIdResult result = client.depositIntoWalletByUserId(
                new io.gs2.gold.control.DepositIntoWalletByUserIdRequest()
                    .withGoldPoolName("gold-pool-0001")
                    .withUserId("user-0001")
                    .withValue(Long.valueOf(40))
                    .withGoldName("gold-0001")
            );
            assertNotNull(result);
            assertNotNull(result.getItem());
            assertEquals(result.getItem().getGoldName(), "gold-0001");
            assertEquals(result.getItem().getUserId(), "user-0001");
            assertEquals(result.getItem().getBalance(), Long.valueOf(40));
            assertEquals(result.getItem().getLatestGain(), Long.valueOf(40));
            assertNotNull(result.getItem().getCreateAt());
            assertNotNull(result.getItem().getUpdateAt());
            wallet1 = result.getItem();
        }
        {
            io.gs2.gold.control.DepositIntoWalletResult result = client.depositIntoWallet(
                new io.gs2.gold.control.DepositIntoWalletRequest()
                    .withGoldPoolName("gold-pool-0001")
                    .withAccessToken(accessToken1)
                    .withValue(Long.valueOf(60))
                    .withGoldName("gold-0001")
            );
            assertNotNull(result);
            assertNotNull(result.getItem());
            assertEquals(result.getItem().getGoldName(), "gold-0001");
            assertEquals(result.getItem().getUserId(), "user-0001");
            assertEquals(result.getItem().getBalance(), Long.valueOf(100));
            assertEquals(result.getItem().getLatestGain(), Long.valueOf(100));
            assertNotNull(result.getItem().getCreateAt());
            assertNotNull(result.getItem().getUpdateAt());
            wallet1 = result.getItem();
        }
        // 取得量をリセット
        {
            io.gs2.gold.control.SetLatestGainResult result = client.setLatestGain(
                new io.gs2.gold.control.SetLatestGainRequest()
                    .withGoldPoolName("gold-pool-0001")
                    .withUserId("user-0001")
                    .withValue(Long.valueOf(70))
                    .withGoldName("gold-0001")
            );
            assertNotNull(result);
            assertNotNull(result.getItem());
            assertEquals(result.getItem().getGoldName(), "gold-0001");
            assertEquals(result.getItem().getUserId(), "user-0001");
            assertEquals(result.getItem().getBalance(), Long.valueOf(100));
            assertEquals(result.getItem().getLatestGain(), Long.valueOf(70));
            assertNotNull(result.getItem().getCreateAt());
            assertNotNull(result.getItem().getUpdateAt());
            wallet1 = result.getItem();
        }
        {
            io.gs2.gold.control.SetLatestGainResult result = client.setLatestGain(
                new io.gs2.gold.control.SetLatestGainRequest()
                    .withGoldPoolName("gold-pool-0001")
                    .withUserId("user-0001")
                    .withGoldName("gold-0001")
            );
            assertNotNull(result);
            assertNotNull(result.getItem());
            assertEquals(result.getItem().getGoldName(), "gold-0001");
            assertEquals(result.getItem().getUserId(), "user-0001");
            assertEquals(result.getItem().getBalance(), Long.valueOf(100));
            assertEquals(result.getItem().getLatestGain(), Long.valueOf(0));
            assertNotNull(result.getItem().getCreateAt());
            assertNotNull(result.getItem().getUpdateAt());
            wallet1 = result.getItem();
        }
        // 残高を減算
        {
            io.gs2.gold.control.WithdrawFromWalletByUserIdResult result = client.withdrawFromWalletByUserId(
                new io.gs2.gold.control.WithdrawFromWalletByUserIdRequest()
                    .withGoldPoolName("gold-pool-0001")
                    .withUserId("user-0001")
                    .withValue(Long.valueOf(40))
                    .withGoldName("gold-0001")
            );
            assertNotNull(result);
            assertNotNull(result.getItem());
            assertEquals(result.getItem().getGoldName(), "gold-0001");
            assertEquals(result.getItem().getUserId(), "user-0001");
            assertEquals(result.getItem().getBalance(), Long.valueOf(60));
            assertEquals(result.getItem().getLatestGain(), Long.valueOf(0));
            assertNotNull(result.getItem().getCreateAt());
            assertNotNull(result.getItem().getUpdateAt());
            wallet1 = result.getItem();
        }
        {
            io.gs2.gold.control.WithdrawFromWalletResult result = client.withdrawFromWallet(
                new io.gs2.gold.control.WithdrawFromWalletRequest()
                    .withGoldPoolName("gold-pool-0001")
                    .withAccessToken(accessToken1)
                    .withValue(Long.valueOf(20))
                    .withGoldName("gold-0001")
            );
            assertNotNull(result);
            assertNotNull(result.getItem());
            assertEquals(result.getItem().getGoldName(), "gold-0001");
            assertEquals(result.getItem().getUserId(), "user-0001");
            assertEquals(result.getItem().getBalance(), Long.valueOf(40));
            assertEquals(result.getItem().getLatestGain(), Long.valueOf(0));
            assertNotNull(result.getItem().getCreateAt());
            assertNotNull(result.getItem().getUpdateAt());
            wallet1 = result.getItem();
        }
        // 残高のないところに残高を減算
        try {
            {
                client.withdrawFromWalletByUserId(
                    new io.gs2.gold.control.WithdrawFromWalletByUserIdRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withUserId("user-0001")
                        .withValue(Long.valueOf(60))
                        .withGoldName("gold-0003")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(e.getErrors().get(0).getComponent(), "balance");
            assertEquals(e.getErrors().get(0).getMessage(), "gold.wallet.balance.error.insufficient");
        }
        // 残高を加算してウォレットがあることを保証
        {
            io.gs2.gold.control.DepositIntoWalletByUserIdResult result = client.depositIntoWalletByUserId(
                new io.gs2.gold.control.DepositIntoWalletByUserIdRequest()
                    .withGoldPoolName("gold-pool-0001")
                    .withUserId("user-0001")
                    .withValue(Long.valueOf(50))
                    .withGoldName("gold-0003")
            );
            assertNotNull(result);
            assertNotNull(result.getItem());
            assertEquals(result.getItem().getGoldName(), "gold-0003");
            assertEquals(result.getItem().getUserId(), "user-0001");
            assertEquals(result.getItem().getBalance(), Long.valueOf(50));
            assertEquals(result.getItem().getLatestGain(), Long.valueOf(50));
            assertNotNull(result.getItem().getCreateAt());
            assertNotNull(result.getItem().getUpdateAt());
            wallet2 = result.getItem();
        }
        {
            io.gs2.gold.control.GetWalletByUserIdResult result = client.getWalletByUserId(
                new io.gs2.gold.control.GetWalletByUserIdRequest()
                    .withGoldPoolName("gold-pool-0001")
                    .withUserId("user-0001")
                    .withGoldName("gold-0003")
            );
            assertNotNull(result);
            assertNotNull(result.getItem());
            assertEquals(result.getItem().getGoldName(), "gold-0003");
            assertEquals(result.getItem().getUserId(), "user-0001");
            assertEquals(result.getItem().getBalance(), Long.valueOf(50));
            assertEquals(result.getItem().getLatestGain(), Long.valueOf(50));
            assertNotNull(result.getItem().getCreateAt());
            assertNotNull(result.getItem().getUpdateAt());
            wallet2 = result.getItem();
        }
        // ウォレットの列挙
        {
            io.gs2.gold.control.DescribeWalletResult result = client.describeWallet(
                new io.gs2.gold.control.DescribeWalletRequest()
                    .withGoldPoolName("gold-pool-0001")
                    .withAccessToken(accessToken1)
            );
            assertNotNull(result.getItems());
            assertEquals(2, result.getItems().size());
            assertNull(result.getNextPageToken());
            wallets = result.getItems();
        }
        if (wallet1.getGoldName().equals(wallets.get(1).getGoldName())) {
            Wallet temp = wallet1;
            wallet1 = wallet2;
            wallet2 = temp;
        }
        assertEquals(wallets.get(0).getUserId(), wallet1.getUserId());
        assertEquals(wallets.get(0).getBalance(), wallet1.getBalance());
        assertEquals(wallets.get(0).getLatestGain(), wallet1.getLatestGain());
        assertEquals(wallets.get(0).getCreateAt(), wallet1.getCreateAt());
        assertEquals(wallets.get(0).getUpdateAt(), wallet1.getUpdateAt());
        
        assertEquals(wallets.get(1).getUserId(), wallet2.getUserId());
        assertEquals(wallets.get(1).getBalance(), wallet2.getBalance());
        assertEquals(wallets.get(1).getLatestGain(), wallet2.getLatestGain());
        assertEquals(wallets.get(1).getCreateAt(), wallet2.getCreateAt());
        assertEquals(wallets.get(1).getUpdateAt(), wallet2.getUpdateAt());
        
        {
            io.gs2.gold.control.DescribeWalletResult result = client.describeWallet(
                new io.gs2.gold.control.DescribeWalletRequest()
                    .withGoldPoolName("gold-pool-0001")
                    .withLimit(Integer.valueOf(1))
                    .withAccessToken(accessToken1)
            );
            assertNotNull(result.getItems());
            assertEquals(1, result.getItems().size());
            assertNotNull(result.getNextPageToken());
            wallets = result.getItems();
            nextPageToken = result.getNextPageToken();
        }
        assertEquals(wallets.get(0).getUserId(), wallet1.getUserId());
        assertEquals(wallets.get(0).getBalance(), wallet1.getBalance());
        assertEquals(wallets.get(0).getLatestGain(), wallet1.getLatestGain());
        assertEquals(wallets.get(0).getCreateAt(), wallet1.getCreateAt());
        assertEquals(wallets.get(0).getUpdateAt(), wallet1.getUpdateAt());
        
        {
            io.gs2.gold.control.DescribeWalletResult result = client.describeWallet(
                new io.gs2.gold.control.DescribeWalletRequest()
                    .withGoldPoolName("gold-pool-0001")
                    .withAccessToken(accessToken1)
                    .withPageToken(nextPageToken)
            );
            assertNotNull(result.getItems());
            assertEquals(1, result.getItems().size());
            assertNull(result.getNextPageToken());
            wallets = result.getItems();
        }
        assertEquals(wallets.get(0).getUserId(), wallet2.getUserId());
        assertEquals(wallets.get(0).getBalance(), wallet2.getBalance());
        assertEquals(wallets.get(0).getLatestGain(), wallet2.getLatestGain());
        assertEquals(wallets.get(0).getCreateAt(), wallet2.getCreateAt());
        assertEquals(wallets.get(0).getUpdateAt(), wallet2.getUpdateAt());
        
        // ウォレットの列挙
        {
            io.gs2.gold.control.DescribeWalletByUserIdResult result = client.describeWalletByUserId(
                new io.gs2.gold.control.DescribeWalletByUserIdRequest()
                    .withGoldPoolName("gold-pool-0001")
                    .withUserId("user-0001")
            );
            assertNotNull(result.getItems());
            assertEquals(2, result.getItems().size());
            assertNull(result.getNextPageToken());
            wallets = result.getItems();
        }
        if (wallet1.getGoldName().equals(wallets.get(1).getGoldName())) {
            Wallet temp = wallet1;
            wallet1 = wallet2;
            wallet2 = temp;
        }
        assertEquals(wallets.get(0).getUserId(), wallet1.getUserId());
        assertEquals(wallets.get(0).getBalance(), wallet1.getBalance());
        assertEquals(wallets.get(0).getLatestGain(), wallet1.getLatestGain());
        assertEquals(wallets.get(0).getCreateAt(), wallet1.getCreateAt());
        assertEquals(wallets.get(0).getUpdateAt(), wallet1.getUpdateAt());
        
        assertEquals(wallets.get(1).getUserId(), wallet2.getUserId());
        assertEquals(wallets.get(1).getBalance(), wallet2.getBalance());
        assertEquals(wallets.get(1).getLatestGain(), wallet2.getLatestGain());
        assertEquals(wallets.get(1).getCreateAt(), wallet2.getCreateAt());
        assertEquals(wallets.get(1).getUpdateAt(), wallet2.getUpdateAt());
        
        {
            io.gs2.gold.control.DescribeWalletByUserIdResult result = client.describeWalletByUserId(
                new io.gs2.gold.control.DescribeWalletByUserIdRequest()
                    .withGoldPoolName("gold-pool-0001")
                    .withUserId("user-0001")
                    .withLimit(Integer.valueOf(1))
            );
            assertNotNull(result.getItems());
            assertEquals(1, result.getItems().size());
            assertNotNull(result.getNextPageToken());
            wallets = result.getItems();
            nextPageToken = result.getNextPageToken();
        }
        assertEquals(wallets.get(0).getUserId(), wallet1.getUserId());
        assertEquals(wallets.get(0).getBalance(), wallet1.getBalance());
        assertEquals(wallets.get(0).getLatestGain(), wallet1.getLatestGain());
        assertEquals(wallets.get(0).getCreateAt(), wallet1.getCreateAt());
        assertEquals(wallets.get(0).getUpdateAt(), wallet1.getUpdateAt());
        
        {
            io.gs2.gold.control.DescribeWalletByUserIdResult result = client.describeWalletByUserId(
                new io.gs2.gold.control.DescribeWalletByUserIdRequest()
                    .withGoldPoolName("gold-pool-0001")
                    .withUserId("user-0001")
                    .withPageToken(nextPageToken)
            );
            assertNotNull(result.getItems());
            assertEquals(1, result.getItems().size());
            assertNull(result.getNextPageToken());
            wallets = result.getItems();
        }
        assertEquals(wallets.get(0).getUserId(), wallet2.getUserId());
        assertEquals(wallets.get(0).getBalance(), wallet2.getBalance());
        assertEquals(wallets.get(0).getLatestGain(), wallet2.getLatestGain());
        assertEquals(wallets.get(0).getCreateAt(), wallet2.getCreateAt());
        assertEquals(wallets.get(0).getUpdateAt(), wallet2.getUpdateAt());
        
        // ウォレット設定を取得
        {
            io.gs2.gold.control.GetWalletSettingsResult result = client.getWalletSettings(
                new io.gs2.gold.control.GetWalletSettingsRequest()
                    .withGoldPoolName("gold-pool-0001")
                    .withGoldName("gold-0002")
            );
            assertNotNull(result);
            assertNotNull(result.getItem());
            assertEquals(result.getItem().getGoldName(), "gold-0002");
            assertEquals(result.getItem().getMeta(), "meta");
            assertEquals(result.getItem().getBalanceMax(), Long.valueOf(1000));
            assertEquals(result.getItem().getResetType(), "monthly");
            assertEquals(result.getItem().getResetDayOfMonth(), Integer.valueOf(1));
            assertEquals(result.getItem().getLatestGainMax(), Long.valueOf(500));
            assertEquals(result.getItem().getResetHour(), Integer.valueOf(17));
        }
        // ウォレットを削除
        {
            client.deleteWalletByUserId(
                new io.gs2.gold.control.DeleteWalletByUserIdRequest()
                    .withGoldPoolName("gold-pool-0001")
                    .withUserId("user-0001")
                    .withGoldName("gold-0003")
            );
        }
        {
            io.gs2.gold.control.GetWalletByUserIdResult result = client.getWalletByUserId(
                new io.gs2.gold.control.GetWalletByUserIdRequest()
                    .withGoldPoolName("gold-pool-0001")
                    .withUserId("user-0001")
                    .withGoldName("gold-0003")
            );
            assertNotNull(result);
            assertNotNull(result.getItem());
            assertEquals(result.getItem().getGoldName(), "gold-0003");
            assertEquals(result.getItem().getUserId(), "user-0001");
            assertEquals(result.getItem().getBalance(), Long.valueOf(0));
            assertEquals(result.getItem().getLatestGain(), Long.valueOf(0));
            assertNotNull(result.getItem().getCreateAt());
            assertNotNull(result.getItem().getUpdateAt());
        }
    }
    
	@AfterClass
	public static void shutdown() {

        {
            io.gs2.gold.control.DescribeGoldPoolResult result = client.describeGoldPool(
                new io.gs2.gold.control.DescribeGoldPoolRequest()
            );
            goldPools = result.getItems();
        }
        for(GoldPool goldPool1 : goldPools) {
            // GoldPool を1件ずつ削除
            {
                io.gs2.gold.control.GetGoldPoolStatusResult result = client.getGoldPoolStatus(
                    new io.gs2.gold.control.GetGoldPoolStatusRequest()
                        .withGoldPoolName(goldPool1.getName())
                );
                status = result.getStatus();
            }
            // 削除中であれば削除完了まで待つ
            if ("DELETING".equals(status)) {
                // ACTIVE になっていないかもしれないので、なるまで待つ
                {
                    status = null;
                
                    int counter1 = 0;
                    while (!"DELETED".equals(status)) {
                        assertTrue(counter1 < 100000);
                        ++counter1;
                        {
                            io.gs2.gold.control.GetGoldPoolStatusResult result = client.getGoldPoolStatus(
                                new io.gs2.gold.control.GetGoldPoolStatusRequest()
                                    .withGoldPoolName(goldPool1.getName())
                            );
                            status = result.getStatus();
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {}
                    }
                }
            }
            // DELETED になっていなければ削除する
            if (!"DELETED".equals(status)) {
                // ACTIVE になっていないかもしれないので、なるまで待つ
                {
                    status = null;
                
                    int counter2 = 0;
                    while (!"ACTIVE".equals(status)) {
                        assertTrue(counter2 < 100000);
                        ++counter2;
                        {
                            io.gs2.gold.control.GetGoldPoolStatusResult result = client.getGoldPoolStatus(
                                new io.gs2.gold.control.GetGoldPoolStatusRequest()
                                    .withGoldPoolName(goldPool1.getName())
                            );
                            status = result.getStatus();
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {}
                    }
                }
                {
                    client.deleteGoldPool(
                        new io.gs2.gold.control.DeleteGoldPoolRequest()
                            .withGoldPoolName(goldPool1.getName())
                    );
                }
                {
                    status = null;
                
                    int counter3 = 0;
                    while (!"DELETED".equals(status)) {
                        assertTrue(counter3 < 100000);
                        ++counter3;
                        {
                            io.gs2.gold.control.GetGoldPoolStatusResult result = client.getGoldPoolStatus(
                                new io.gs2.gold.control.GetGoldPoolStatusRequest()
                                    .withGoldPoolName(goldPool1.getName())
                            );
                            status = result.getStatus();
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {}
                    }
                }
            }
        }
	}
}