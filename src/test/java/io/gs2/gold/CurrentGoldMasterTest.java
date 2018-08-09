package io.gs2.gold;

import io.gs2.exception.*;
import io.gs2.model.*;
import io.gs2.gold.model.*;
import io.gs2.gold.control.*;
import io.gs2.gold.Gs2GoldPrivateClient;
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
public class CurrentGoldMasterTest extends TestCase {
    private static Random rand = new Random();

    private static String REGION = "ap-northeast-1";
    private static String export_data = null;
    private static List<GoldPool> goldPools = null;
    private static GoldPool goldPool1 = null;
    private static GoldPool goldPool2 = null;
    private static List<GoldMaster> golds = null;
    private static String status = null;
    private static String nextPageToken = null;
    private static String CLIENT_ID = "gs2_gold_test_seeds.CurrentGoldMasterTest-java";
    private static String CLIENT_SECRET = "gw7ptA/sjkFpvlgfkjFyxy6ALJZpUvwrgH8LJ01OWYU=";
    private static BasicGs2Credential credential = new BasicGs2Credential(CLIENT_ID, CLIENT_SECRET);
    private static Gs2GoldPrivateClient client = new Gs2GoldPrivateClient(credential);

    static {
        Gs2GoldPrivateClient.ENDPOINT = "gold-dev";
    }

	@BeforeClass
	public static void setUpClass() {
		try {

            {
                io.gs2.gold.control.CreateGoldPoolResult result = client.createGoldPool(
                    new io.gs2.gold.control.CreateGoldPoolRequest()
                        .withServiceClass("gold1.nano")
                        .withName("gold-pool-0001")
                );
                assertNotNull(result);
                assertNotNull(result.getItem());
                assertEquals(result.getItem().getOwnerId(), CLIENT_ID);
                assertEquals(result.getItem().getName(), "gold-pool-0001");
                assertNull(result.getItem().getDescription());
                assertEquals(result.getItem().getServiceClass(), "gold1.nano");
                assertNull(result.getItem().getCreateWalletTriggerScript());
                assertNull(result.getItem().getCreateWalletDoneTriggerScript());
                assertNull(result.getItem().getDepositIntoWalletTriggerScript());
                assertNull(result.getItem().getDepositIntoWalletDoneTriggerScript());
                assertNull(result.getItem().getWithdrawFromWalletTriggerScript());
                assertNull(result.getItem().getWithdrawFromWalletDoneTriggerScript());
                assertNotNull(result.getItem().getCreateAt());
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
		} catch (BadRequestException e) {
			shutdown();
			throw e;
		}
	}

    
    @Test
    public void testGetCurrentGoldMasterGoldPoolNameNone() {
        try {
            {
                client.getCurrentGoldMaster(
                    new io.gs2.gold.control.GetCurrentGoldMasterRequest()
                        .withGoldPoolName(null)
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.currentGoldMaster.goldPoolName.error.require");
            
        }
    }
    
    
    @Test
    public void testGetCurrentGoldMasterGoldPoolNameEmpty() {
        try {
            {
                client.getCurrentGoldMaster(
                    new io.gs2.gold.control.GetCurrentGoldMasterRequest()
                        .withGoldPoolName("")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.currentGoldMaster.goldPoolName.error.require");
            
        }
    }
    
    
    @Test
    public void testGetCurrentGoldMasterGoldPoolNameInvalid() {
        try {
            {
                client.getCurrentGoldMaster(
                    new io.gs2.gold.control.GetCurrentGoldMasterRequest()
                        .withGoldPoolName("@")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.currentGoldMaster.goldPoolName.error.invalid");
            
        }
    }
    
    
    @Test
    public void testGetCurrentGoldMasterGoldPoolNameTooLong() {
        try {
            {
                client.getCurrentGoldMaster(
                    new io.gs2.gold.control.GetCurrentGoldMasterRequest()
                        .withGoldPoolName("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.currentGoldMaster.goldPoolName.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testGetCurrentGoldMasterGoldPoolNameNotFound() {
        try {
            {
                client.getCurrentGoldMaster(
                    new io.gs2.gold.control.GetCurrentGoldMasterRequest()
                        .withGoldPoolName("not_found")
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
    public void testUpdateCurrentGoldMasterGoldPoolNameNone() {
        try {
            {
                client.updateCurrentGoldMaster(
                    new io.gs2.gold.control.UpdateCurrentGoldMasterRequest()
                        .withGoldPoolName(null)
                        .withSettings("{\n  \"golds\": [\n    {\n      \"balanceMax\": 99999999,\n      \"createWalletDoneTriggerScript\": null,\n      \"createWalletTriggerScript\": null,\n      \"depositIntoWalletDoneTriggerScript\": null,\n      \"depositIntoWalletTriggerScript\": null,\n      \"latestGainMax\": 99999999,\n      \"meta\": null,\n      \"name\": \"gold-0001\",\n      \"resetDayOfMonth\": null,\n      \"resetDayOfWeek\": null,\n      \"resetHour\": null,\n      \"resetType\": \"none\",\n      \"withdrawFromWalletDoneTriggerScript\": null,\n      \"withdrawFromWalletTriggerScript\": null\n    },\n    {\n      \"balanceMax\": 1000,\n      \"createWalletDoneTriggerScript\": \"GS2Gold_create_wallet_done_trigger_script\",\n      \"createWalletTriggerScript\": \"GS2Gold_create_wallet_trigger_script\",\n      \"depositIntoWalletDoneTriggerScript\": \"GS2Gold_deposit_into_wallet_done_trigger_script\",\n      \"depositIntoWalletTriggerScript\": \"GS2Gold_deposit_into_wallet_trigger_script\",\n      \"latestGainMax\": 500,\n      \"meta\": \"meta\",\n      \"name\": \"gold-0002\",\n      \"resetDayOfMonth\": 1,\n      \"resetDayOfWeek\": null,\n      \"resetHour\": 17,\n      \"resetType\": \"monthly\",\n      \"withdrawFromWalletDoneTriggerScript\": \"GS2Gold_withdraw_from_wallet_done_trigger_script\",\n      \"withdrawFromWalletTriggerScript\": \"GS2Gold_withdraw_from_wallet_trigger_script\"\n    },\n    {\n      \"balanceMax\": 2000,\n      \"createWalletDoneTriggerScript\": null,\n      \"createWalletTriggerScript\": null,\n      \"depositIntoWalletDoneTriggerScript\": null,\n      \"depositIntoWalletTriggerScript\": null,\n      \"latestGainMax\": 1000,\n      \"meta\": \"meta3\",\n      \"name\": \"gold-0003\",\n      \"resetDayOfMonth\": null,\n      \"resetDayOfWeek\": \"monday\",\n      \"resetHour\": 2,\n      \"resetType\": \"weekly\",\n      \"withdrawFromWalletDoneTriggerScript\": null,\n      \"withdrawFromWalletTriggerScript\": null\n    }\n  ],\n  \"version\": \"2018-08-03\"\n}")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.currentGoldMaster.goldPoolName.error.require");
            
        }
    }
    
    
    @Test
    public void testUpdateCurrentGoldMasterGoldPoolNameEmpty() {
        try {
            {
                client.updateCurrentGoldMaster(
                    new io.gs2.gold.control.UpdateCurrentGoldMasterRequest()
                        .withGoldPoolName("")
                        .withSettings("{\n  \"golds\": [\n    {\n      \"balanceMax\": 99999999,\n      \"createWalletDoneTriggerScript\": null,\n      \"createWalletTriggerScript\": null,\n      \"depositIntoWalletDoneTriggerScript\": null,\n      \"depositIntoWalletTriggerScript\": null,\n      \"latestGainMax\": 99999999,\n      \"meta\": null,\n      \"name\": \"gold-0001\",\n      \"resetDayOfMonth\": null,\n      \"resetDayOfWeek\": null,\n      \"resetHour\": null,\n      \"resetType\": \"none\",\n      \"withdrawFromWalletDoneTriggerScript\": null,\n      \"withdrawFromWalletTriggerScript\": null\n    },\n    {\n      \"balanceMax\": 1000,\n      \"createWalletDoneTriggerScript\": \"GS2Gold_create_wallet_done_trigger_script\",\n      \"createWalletTriggerScript\": \"GS2Gold_create_wallet_trigger_script\",\n      \"depositIntoWalletDoneTriggerScript\": \"GS2Gold_deposit_into_wallet_done_trigger_script\",\n      \"depositIntoWalletTriggerScript\": \"GS2Gold_deposit_into_wallet_trigger_script\",\n      \"latestGainMax\": 500,\n      \"meta\": \"meta\",\n      \"name\": \"gold-0002\",\n      \"resetDayOfMonth\": 1,\n      \"resetDayOfWeek\": null,\n      \"resetHour\": 17,\n      \"resetType\": \"monthly\",\n      \"withdrawFromWalletDoneTriggerScript\": \"GS2Gold_withdraw_from_wallet_done_trigger_script\",\n      \"withdrawFromWalletTriggerScript\": \"GS2Gold_withdraw_from_wallet_trigger_script\"\n    },\n    {\n      \"balanceMax\": 2000,\n      \"createWalletDoneTriggerScript\": null,\n      \"createWalletTriggerScript\": null,\n      \"depositIntoWalletDoneTriggerScript\": null,\n      \"depositIntoWalletTriggerScript\": null,\n      \"latestGainMax\": 1000,\n      \"meta\": \"meta3\",\n      \"name\": \"gold-0003\",\n      \"resetDayOfMonth\": null,\n      \"resetDayOfWeek\": \"monday\",\n      \"resetHour\": 2,\n      \"resetType\": \"weekly\",\n      \"withdrawFromWalletDoneTriggerScript\": null,\n      \"withdrawFromWalletTriggerScript\": null\n    }\n  ],\n  \"version\": \"2018-08-03\"\n}")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.currentGoldMaster.goldPoolName.error.require");
            
        }
    }
    
    
    @Test
    public void testUpdateCurrentGoldMasterGoldPoolNameInvalid() {
        try {
            {
                client.updateCurrentGoldMaster(
                    new io.gs2.gold.control.UpdateCurrentGoldMasterRequest()
                        .withGoldPoolName("@")
                        .withSettings("{\n  \"golds\": [\n    {\n      \"balanceMax\": 99999999,\n      \"createWalletDoneTriggerScript\": null,\n      \"createWalletTriggerScript\": null,\n      \"depositIntoWalletDoneTriggerScript\": null,\n      \"depositIntoWalletTriggerScript\": null,\n      \"latestGainMax\": 99999999,\n      \"meta\": null,\n      \"name\": \"gold-0001\",\n      \"resetDayOfMonth\": null,\n      \"resetDayOfWeek\": null,\n      \"resetHour\": null,\n      \"resetType\": \"none\",\n      \"withdrawFromWalletDoneTriggerScript\": null,\n      \"withdrawFromWalletTriggerScript\": null\n    },\n    {\n      \"balanceMax\": 1000,\n      \"createWalletDoneTriggerScript\": \"GS2Gold_create_wallet_done_trigger_script\",\n      \"createWalletTriggerScript\": \"GS2Gold_create_wallet_trigger_script\",\n      \"depositIntoWalletDoneTriggerScript\": \"GS2Gold_deposit_into_wallet_done_trigger_script\",\n      \"depositIntoWalletTriggerScript\": \"GS2Gold_deposit_into_wallet_trigger_script\",\n      \"latestGainMax\": 500,\n      \"meta\": \"meta\",\n      \"name\": \"gold-0002\",\n      \"resetDayOfMonth\": 1,\n      \"resetDayOfWeek\": null,\n      \"resetHour\": 17,\n      \"resetType\": \"monthly\",\n      \"withdrawFromWalletDoneTriggerScript\": \"GS2Gold_withdraw_from_wallet_done_trigger_script\",\n      \"withdrawFromWalletTriggerScript\": \"GS2Gold_withdraw_from_wallet_trigger_script\"\n    },\n    {\n      \"balanceMax\": 2000,\n      \"createWalletDoneTriggerScript\": null,\n      \"createWalletTriggerScript\": null,\n      \"depositIntoWalletDoneTriggerScript\": null,\n      \"depositIntoWalletTriggerScript\": null,\n      \"latestGainMax\": 1000,\n      \"meta\": \"meta3\",\n      \"name\": \"gold-0003\",\n      \"resetDayOfMonth\": null,\n      \"resetDayOfWeek\": \"monday\",\n      \"resetHour\": 2,\n      \"resetType\": \"weekly\",\n      \"withdrawFromWalletDoneTriggerScript\": null,\n      \"withdrawFromWalletTriggerScript\": null\n    }\n  ],\n  \"version\": \"2018-08-03\"\n}")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.currentGoldMaster.goldPoolName.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateCurrentGoldMasterGoldPoolNameTooLong() {
        try {
            {
                client.updateCurrentGoldMaster(
                    new io.gs2.gold.control.UpdateCurrentGoldMasterRequest()
                        .withGoldPoolName("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withSettings("{\n  \"golds\": [\n    {\n      \"balanceMax\": 99999999,\n      \"createWalletDoneTriggerScript\": null,\n      \"createWalletTriggerScript\": null,\n      \"depositIntoWalletDoneTriggerScript\": null,\n      \"depositIntoWalletTriggerScript\": null,\n      \"latestGainMax\": 99999999,\n      \"meta\": null,\n      \"name\": \"gold-0001\",\n      \"resetDayOfMonth\": null,\n      \"resetDayOfWeek\": null,\n      \"resetHour\": null,\n      \"resetType\": \"none\",\n      \"withdrawFromWalletDoneTriggerScript\": null,\n      \"withdrawFromWalletTriggerScript\": null\n    },\n    {\n      \"balanceMax\": 1000,\n      \"createWalletDoneTriggerScript\": \"GS2Gold_create_wallet_done_trigger_script\",\n      \"createWalletTriggerScript\": \"GS2Gold_create_wallet_trigger_script\",\n      \"depositIntoWalletDoneTriggerScript\": \"GS2Gold_deposit_into_wallet_done_trigger_script\",\n      \"depositIntoWalletTriggerScript\": \"GS2Gold_deposit_into_wallet_trigger_script\",\n      \"latestGainMax\": 500,\n      \"meta\": \"meta\",\n      \"name\": \"gold-0002\",\n      \"resetDayOfMonth\": 1,\n      \"resetDayOfWeek\": null,\n      \"resetHour\": 17,\n      \"resetType\": \"monthly\",\n      \"withdrawFromWalletDoneTriggerScript\": \"GS2Gold_withdraw_from_wallet_done_trigger_script\",\n      \"withdrawFromWalletTriggerScript\": \"GS2Gold_withdraw_from_wallet_trigger_script\"\n    },\n    {\n      \"balanceMax\": 2000,\n      \"createWalletDoneTriggerScript\": null,\n      \"createWalletTriggerScript\": null,\n      \"depositIntoWalletDoneTriggerScript\": null,\n      \"depositIntoWalletTriggerScript\": null,\n      \"latestGainMax\": 1000,\n      \"meta\": \"meta3\",\n      \"name\": \"gold-0003\",\n      \"resetDayOfMonth\": null,\n      \"resetDayOfWeek\": \"monday\",\n      \"resetHour\": 2,\n      \"resetType\": \"weekly\",\n      \"withdrawFromWalletDoneTriggerScript\": null,\n      \"withdrawFromWalletTriggerScript\": null\n    }\n  ],\n  \"version\": \"2018-08-03\"\n}")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.currentGoldMaster.goldPoolName.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testUpdateCurrentGoldMasterGoldPoolNameNotFound() {
        try {
            {
                client.updateCurrentGoldMaster(
                    new io.gs2.gold.control.UpdateCurrentGoldMasterRequest()
                        .withGoldPoolName("not_found")
                        .withSettings("{\n  \"golds\": [\n    {\n      \"balanceMax\": 99999999,\n      \"createWalletDoneTriggerScript\": null,\n      \"createWalletTriggerScript\": null,\n      \"depositIntoWalletDoneTriggerScript\": null,\n      \"depositIntoWalletTriggerScript\": null,\n      \"latestGainMax\": 99999999,\n      \"meta\": null,\n      \"name\": \"gold-0001\",\n      \"resetDayOfMonth\": null,\n      \"resetDayOfWeek\": null,\n      \"resetHour\": null,\n      \"resetType\": \"none\",\n      \"withdrawFromWalletDoneTriggerScript\": null,\n      \"withdrawFromWalletTriggerScript\": null\n    },\n    {\n      \"balanceMax\": 1000,\n      \"createWalletDoneTriggerScript\": \"GS2Gold_create_wallet_done_trigger_script\",\n      \"createWalletTriggerScript\": \"GS2Gold_create_wallet_trigger_script\",\n      \"depositIntoWalletDoneTriggerScript\": \"GS2Gold_deposit_into_wallet_done_trigger_script\",\n      \"depositIntoWalletTriggerScript\": \"GS2Gold_deposit_into_wallet_trigger_script\",\n      \"latestGainMax\": 500,\n      \"meta\": \"meta\",\n      \"name\": \"gold-0002\",\n      \"resetDayOfMonth\": 1,\n      \"resetDayOfWeek\": null,\n      \"resetHour\": 17,\n      \"resetType\": \"monthly\",\n      \"withdrawFromWalletDoneTriggerScript\": \"GS2Gold_withdraw_from_wallet_done_trigger_script\",\n      \"withdrawFromWalletTriggerScript\": \"GS2Gold_withdraw_from_wallet_trigger_script\"\n    },\n    {\n      \"balanceMax\": 2000,\n      \"createWalletDoneTriggerScript\": null,\n      \"createWalletTriggerScript\": null,\n      \"depositIntoWalletDoneTriggerScript\": null,\n      \"depositIntoWalletTriggerScript\": null,\n      \"latestGainMax\": 1000,\n      \"meta\": \"meta3\",\n      \"name\": \"gold-0003\",\n      \"resetDayOfMonth\": null,\n      \"resetDayOfWeek\": \"monday\",\n      \"resetHour\": 2,\n      \"resetType\": \"weekly\",\n      \"withdrawFromWalletDoneTriggerScript\": null,\n      \"withdrawFromWalletTriggerScript\": null\n    }\n  ],\n  \"version\": \"2018-08-03\"\n}")
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
    public void testUpdateCurrentGoldMasterSettingsNone() {
        try {
            {
                client.updateCurrentGoldMaster(
                    new io.gs2.gold.control.UpdateCurrentGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withSettings(null)
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "settings");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.currentGoldMaster.settings.error.require");
            
        }
    }
    
    
    @Test
    public void testUpdateCurrentGoldMasterSettingsEmpty() {
        try {
            {
                client.updateCurrentGoldMaster(
                    new io.gs2.gold.control.UpdateCurrentGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withSettings("")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "settings");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.currentGoldMaster.settings.error.require");
            
        }
    }
    
    
    @Test
    public void testUpdateCurrentGoldMasterSettingsInvalid() {
        try {
            {
                client.updateCurrentGoldMaster(
                    new io.gs2.gold.control.UpdateCurrentGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withSettings("invalid")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "settings");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.currentGoldMaster.settings.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateCurrentGoldMasterSettingsVersionSettingsNotDefined() {
        try {
            {
                client.updateCurrentGoldMaster(
                    new io.gs2.gold.control.UpdateCurrentGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withSettings("{\"golds\": [{\"name\": \"gold-0001\", \"createWalletDoneTriggerScript\": null, \"depositIntoWalletTriggerScript\": null, \"latestGainMax\": 99999999, \"balanceMax\": 99999999, \"withdrawFromWalletDoneTriggerScript\": null, \"meta\": null, \"resetType\": \"none\", \"withdrawFromWalletTriggerScript\": null, \"resetDayOfMonth\": null, \"resetDayOfWeek\": null, \"createWalletTriggerScript\": null, \"depositIntoWalletDoneTriggerScript\": null, \"resetHour\": null}, {\"name\": \"gold-0002\", \"createWalletDoneTriggerScript\": \"GS2Gold_create_wallet_done_trigger_script\", \"depositIntoWalletTriggerScript\": \"GS2Gold_deposit_into_wallet_trigger_script\", \"latestGainMax\": 500, \"balanceMax\": 1000, \"withdrawFromWalletDoneTriggerScript\": \"GS2Gold_withdraw_from_wallet_done_trigger_script\", \"meta\": \"meta\", \"resetType\": \"monthly\", \"withdrawFromWalletTriggerScript\": \"GS2Gold_withdraw_from_wallet_trigger_script\", \"resetDayOfMonth\": 1, \"resetDayOfWeek\": null, \"createWalletTriggerScript\": \"GS2Gold_create_wallet_trigger_script\", \"depositIntoWalletDoneTriggerScript\": \"GS2Gold_deposit_into_wallet_done_trigger_script\", \"resetHour\": 17}, {\"name\": \"gold-0003\", \"createWalletDoneTriggerScript\": null, \"depositIntoWalletTriggerScript\": null, \"latestGainMax\": 1000, \"balanceMax\": 2000, \"withdrawFromWalletDoneTriggerScript\": null, \"meta\": \"meta3\", \"resetType\": \"weekly\", \"withdrawFromWalletTriggerScript\": null, \"resetDayOfMonth\": null, \"resetDayOfWeek\": \"monday\", \"createWalletTriggerScript\": null, \"depositIntoWalletDoneTriggerScript\": null, \"resetHour\": 2}]}")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "version");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.currentGoldMaster.version.error.require");
            
        }
    }
    
    
    @Test
    public void testUpdateCurrentGoldMasterSettingsVersionSettingsNone() {
        try {
            {
                client.updateCurrentGoldMaster(
                    new io.gs2.gold.control.UpdateCurrentGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withSettings("{\"golds\": [{\"name\": \"gold-0001\", \"createWalletDoneTriggerScript\": null, \"depositIntoWalletTriggerScript\": null, \"latestGainMax\": 99999999, \"balanceMax\": 99999999, \"withdrawFromWalletDoneTriggerScript\": null, \"meta\": null, \"resetType\": \"none\", \"withdrawFromWalletTriggerScript\": null, \"resetDayOfMonth\": null, \"resetDayOfWeek\": null, \"createWalletTriggerScript\": null, \"depositIntoWalletDoneTriggerScript\": null, \"resetHour\": null}, {\"name\": \"gold-0002\", \"createWalletDoneTriggerScript\": \"GS2Gold_create_wallet_done_trigger_script\", \"depositIntoWalletTriggerScript\": \"GS2Gold_deposit_into_wallet_trigger_script\", \"latestGainMax\": 500, \"balanceMax\": 1000, \"withdrawFromWalletDoneTriggerScript\": \"GS2Gold_withdraw_from_wallet_done_trigger_script\", \"meta\": \"meta\", \"resetType\": \"monthly\", \"withdrawFromWalletTriggerScript\": \"GS2Gold_withdraw_from_wallet_trigger_script\", \"resetDayOfMonth\": 1, \"resetDayOfWeek\": null, \"createWalletTriggerScript\": \"GS2Gold_create_wallet_trigger_script\", \"depositIntoWalletDoneTriggerScript\": \"GS2Gold_deposit_into_wallet_done_trigger_script\", \"resetHour\": 17}, {\"name\": \"gold-0003\", \"createWalletDoneTriggerScript\": null, \"depositIntoWalletTriggerScript\": null, \"latestGainMax\": 1000, \"balanceMax\": 2000, \"withdrawFromWalletDoneTriggerScript\": null, \"meta\": \"meta3\", \"resetType\": \"weekly\", \"withdrawFromWalletTriggerScript\": null, \"resetDayOfMonth\": null, \"resetDayOfWeek\": \"monday\", \"createWalletTriggerScript\": null, \"depositIntoWalletDoneTriggerScript\": null, \"resetHour\": 2}], \"version\": null}")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "version");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.currentGoldMaster.version.error.require");
            
        }
    }
    
    
    @Test
    public void testUpdateCurrentGoldMasterSettingsVersionSettingsNull() {
        try {
            {
                client.updateCurrentGoldMaster(
                    new io.gs2.gold.control.UpdateCurrentGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withSettings("{\"golds\": [{\"name\": \"gold-0001\", \"createWalletDoneTriggerScript\": null, \"depositIntoWalletTriggerScript\": null, \"latestGainMax\": 99999999, \"balanceMax\": 99999999, \"withdrawFromWalletDoneTriggerScript\": null, \"meta\": null, \"resetType\": \"none\", \"withdrawFromWalletTriggerScript\": null, \"resetDayOfMonth\": null, \"resetDayOfWeek\": null, \"createWalletTriggerScript\": null, \"depositIntoWalletDoneTriggerScript\": null, \"resetHour\": null}, {\"name\": \"gold-0002\", \"createWalletDoneTriggerScript\": \"GS2Gold_create_wallet_done_trigger_script\", \"depositIntoWalletTriggerScript\": \"GS2Gold_deposit_into_wallet_trigger_script\", \"latestGainMax\": 500, \"balanceMax\": 1000, \"withdrawFromWalletDoneTriggerScript\": \"GS2Gold_withdraw_from_wallet_done_trigger_script\", \"meta\": \"meta\", \"resetType\": \"monthly\", \"withdrawFromWalletTriggerScript\": \"GS2Gold_withdraw_from_wallet_trigger_script\", \"resetDayOfMonth\": 1, \"resetDayOfWeek\": null, \"createWalletTriggerScript\": \"GS2Gold_create_wallet_trigger_script\", \"depositIntoWalletDoneTriggerScript\": \"GS2Gold_deposit_into_wallet_done_trigger_script\", \"resetHour\": 17}, {\"name\": \"gold-0003\", \"createWalletDoneTriggerScript\": null, \"depositIntoWalletTriggerScript\": null, \"latestGainMax\": 1000, \"balanceMax\": 2000, \"withdrawFromWalletDoneTriggerScript\": null, \"meta\": \"meta3\", \"resetType\": \"weekly\", \"withdrawFromWalletTriggerScript\": null, \"resetDayOfMonth\": null, \"resetDayOfWeek\": \"monday\", \"createWalletTriggerScript\": null, \"depositIntoWalletDoneTriggerScript\": null, \"resetHour\": 2}], \"version\": \"\"}")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "version");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.currentGoldMaster.version.error.require");
            
        }
    }
    
    
    @Test
    public void testUpdateCurrentGoldMasterSettingsVersionSettingsInvalid() {
        try {
            {
                client.updateCurrentGoldMaster(
                    new io.gs2.gold.control.UpdateCurrentGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withSettings("{\"golds\": [{\"name\": \"gold-0001\", \"createWalletDoneTriggerScript\": null, \"depositIntoWalletTriggerScript\": null, \"latestGainMax\": 99999999, \"balanceMax\": 99999999, \"withdrawFromWalletDoneTriggerScript\": null, \"meta\": null, \"resetType\": \"none\", \"withdrawFromWalletTriggerScript\": null, \"resetDayOfMonth\": null, \"resetDayOfWeek\": null, \"createWalletTriggerScript\": null, \"depositIntoWalletDoneTriggerScript\": null, \"resetHour\": null}, {\"name\": \"gold-0002\", \"createWalletDoneTriggerScript\": \"GS2Gold_create_wallet_done_trigger_script\", \"depositIntoWalletTriggerScript\": \"GS2Gold_deposit_into_wallet_trigger_script\", \"latestGainMax\": 500, \"balanceMax\": 1000, \"withdrawFromWalletDoneTriggerScript\": \"GS2Gold_withdraw_from_wallet_done_trigger_script\", \"meta\": \"meta\", \"resetType\": \"monthly\", \"withdrawFromWalletTriggerScript\": \"GS2Gold_withdraw_from_wallet_trigger_script\", \"resetDayOfMonth\": 1, \"resetDayOfWeek\": null, \"createWalletTriggerScript\": \"GS2Gold_create_wallet_trigger_script\", \"depositIntoWalletDoneTriggerScript\": \"GS2Gold_deposit_into_wallet_done_trigger_script\", \"resetHour\": 17}, {\"name\": \"gold-0003\", \"createWalletDoneTriggerScript\": null, \"depositIntoWalletTriggerScript\": null, \"latestGainMax\": 1000, \"balanceMax\": 2000, \"withdrawFromWalletDoneTriggerScript\": null, \"meta\": \"meta3\", \"resetType\": \"weekly\", \"withdrawFromWalletTriggerScript\": null, \"resetDayOfMonth\": null, \"resetDayOfWeek\": \"monday\", \"createWalletTriggerScript\": null, \"depositIntoWalletDoneTriggerScript\": null, \"resetHour\": 2}], \"version\": \"2018-01-01\"}")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "version");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.currentGoldMaster.version.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateCurrentGoldMasterSettingsGoldsSettingsTooMany() {
        try {
            {
                client.updateCurrentGoldMaster(
                    new io.gs2.gold.control.UpdateCurrentGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withSettings("{\"golds\": [\"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\", \"a\"], \"version\": \"2018-08-03\"}")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "golds");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.currentGoldMaster.golds.error.tooMany");
            
        }
    }
    
    
    @Test
    public void testUpdateCurrentGoldMasterSettingsGoldsSettingsInvalid() {
        try {
            {
                client.updateCurrentGoldMaster(
                    new io.gs2.gold.control.UpdateCurrentGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withSettings("{\"golds\": [\"invalid\", {\"name\": \"gold-0002\", \"createWalletDoneTriggerScript\": \"GS2Gold_create_wallet_done_trigger_script\", \"depositIntoWalletTriggerScript\": \"GS2Gold_deposit_into_wallet_trigger_script\", \"latestGainMax\": 500, \"balanceMax\": 1000, \"withdrawFromWalletDoneTriggerScript\": \"GS2Gold_withdraw_from_wallet_done_trigger_script\", \"meta\": \"meta\", \"resetType\": \"monthly\", \"withdrawFromWalletTriggerScript\": \"GS2Gold_withdraw_from_wallet_trigger_script\", \"resetDayOfMonth\": 1, \"resetDayOfWeek\": null, \"createWalletTriggerScript\": \"GS2Gold_create_wallet_trigger_script\", \"depositIntoWalletDoneTriggerScript\": \"GS2Gold_deposit_into_wallet_done_trigger_script\", \"resetHour\": 17}, {\"name\": \"gold-0003\", \"createWalletDoneTriggerScript\": null, \"depositIntoWalletTriggerScript\": null, \"latestGainMax\": 1000, \"balanceMax\": 2000, \"withdrawFromWalletDoneTriggerScript\": null, \"meta\": \"meta3\", \"resetType\": \"weekly\", \"withdrawFromWalletTriggerScript\": null, \"resetDayOfMonth\": null, \"resetDayOfWeek\": \"monday\", \"createWalletTriggerScript\": null, \"depositIntoWalletDoneTriggerScript\": null, \"resetHour\": 2}], \"version\": \"2018-08-03\"}")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "settings");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.currentGoldMaster.settings.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateCurrentGoldMasterSettingsGoldNameSettingsInvalid() {
        try {
            {
                client.updateCurrentGoldMaster(
                    new io.gs2.gold.control.UpdateCurrentGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withSettings("{\"golds\": [{\"name\": \"@\", \"createWalletDoneTriggerScript\": null, \"depositIntoWalletTriggerScript\": null, \"latestGainMax\": 99999999, \"balanceMax\": 99999999, \"withdrawFromWalletDoneTriggerScript\": null, \"meta\": null, \"resetType\": \"none\", \"withdrawFromWalletTriggerScript\": null, \"resetDayOfMonth\": null, \"resetDayOfWeek\": null, \"createWalletTriggerScript\": null, \"depositIntoWalletDoneTriggerScript\": null, \"resetHour\": null}, {\"name\": \"gold-0002\", \"createWalletDoneTriggerScript\": \"GS2Gold_create_wallet_done_trigger_script\", \"depositIntoWalletTriggerScript\": \"GS2Gold_deposit_into_wallet_trigger_script\", \"latestGainMax\": 500, \"balanceMax\": 1000, \"withdrawFromWalletDoneTriggerScript\": \"GS2Gold_withdraw_from_wallet_done_trigger_script\", \"meta\": \"meta\", \"resetType\": \"monthly\", \"withdrawFromWalletTriggerScript\": \"GS2Gold_withdraw_from_wallet_trigger_script\", \"resetDayOfMonth\": 1, \"resetDayOfWeek\": null, \"createWalletTriggerScript\": \"GS2Gold_create_wallet_trigger_script\", \"depositIntoWalletDoneTriggerScript\": \"GS2Gold_deposit_into_wallet_done_trigger_script\", \"resetHour\": 17}, {\"name\": \"gold-0003\", \"createWalletDoneTriggerScript\": null, \"depositIntoWalletTriggerScript\": null, \"latestGainMax\": 1000, \"balanceMax\": 2000, \"withdrawFromWalletDoneTriggerScript\": null, \"meta\": \"meta3\", \"resetType\": \"weekly\", \"withdrawFromWalletTriggerScript\": null, \"resetDayOfMonth\": null, \"resetDayOfWeek\": \"monday\", \"createWalletTriggerScript\": null, \"depositIntoWalletDoneTriggerScript\": null, \"resetHour\": 2}], \"version\": \"2018-08-03\"}")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "name");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.currentGoldMaster.golds[0].name.error.invalid");
            
        }
    }
    
    
    @Test
    public void testBasic() {
        try {
            {
                client.getCurrentGoldMaster(
                    new io.gs2.gold.control.GetCurrentGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                );
            }
            assertTrue(false);
        }catch (NotFoundException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "gold");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.gold.gold.error.notFound");
            
        }
        {
            io.gs2.gold.control.UpdateCurrentGoldMasterResult result = client.updateCurrentGoldMaster(
                new io.gs2.gold.control.UpdateCurrentGoldMasterRequest()
                    .withGoldPoolName("gold-pool-0001")
                    .withSettings("{\n  \"golds\": [\n    {\n      \"balanceMax\": 99999999,\n      \"createWalletDoneTriggerScript\": null,\n      \"createWalletTriggerScript\": null,\n      \"depositIntoWalletDoneTriggerScript\": null,\n      \"depositIntoWalletTriggerScript\": null,\n      \"latestGainMax\": 99999999,\n      \"meta\": null,\n      \"name\": \"gold-0001\",\n      \"resetDayOfMonth\": null,\n      \"resetDayOfWeek\": null,\n      \"resetHour\": null,\n      \"resetType\": \"none\",\n      \"withdrawFromWalletDoneTriggerScript\": null,\n      \"withdrawFromWalletTriggerScript\": null\n    },\n    {\n      \"balanceMax\": 1000,\n      \"createWalletDoneTriggerScript\": \"GS2Gold_create_wallet_done_trigger_script\",\n      \"createWalletTriggerScript\": \"GS2Gold_create_wallet_trigger_script\",\n      \"depositIntoWalletDoneTriggerScript\": \"GS2Gold_deposit_into_wallet_done_trigger_script\",\n      \"depositIntoWalletTriggerScript\": \"GS2Gold_deposit_into_wallet_trigger_script\",\n      \"latestGainMax\": 500,\n      \"meta\": \"meta\",\n      \"name\": \"gold-0002\",\n      \"resetDayOfMonth\": 1,\n      \"resetDayOfWeek\": null,\n      \"resetHour\": 17,\n      \"resetType\": \"monthly\",\n      \"withdrawFromWalletDoneTriggerScript\": \"GS2Gold_withdraw_from_wallet_done_trigger_script\",\n      \"withdrawFromWalletTriggerScript\": \"GS2Gold_withdraw_from_wallet_trigger_script\"\n    },\n    {\n      \"balanceMax\": 2000,\n      \"createWalletDoneTriggerScript\": null,\n      \"createWalletTriggerScript\": null,\n      \"depositIntoWalletDoneTriggerScript\": null,\n      \"depositIntoWalletTriggerScript\": null,\n      \"latestGainMax\": 1000,\n      \"meta\": \"meta3\",\n      \"name\": \"gold-0003\",\n      \"resetDayOfMonth\": null,\n      \"resetDayOfWeek\": \"monday\",\n      \"resetHour\": 2,\n      \"resetType\": \"weekly\",\n      \"withdrawFromWalletDoneTriggerScript\": null,\n      \"withdrawFromWalletTriggerScript\": null\n    }\n  ],\n  \"version\": \"2018-08-03\"\n}")
            );
            try {
                String json1 = null;
                {
                    JSONObject item = new JSONObject(result.getItem());
                    json1 = item.toString();
                }
            
                String json2 = null;
                {
                    JSONObject item = new JSONObject("{\n  \"golds\": [\n    {\n      \"balanceMax\": 99999999,\n      \"createWalletDoneTriggerScript\": null,\n      \"createWalletTriggerScript\": null,\n      \"depositIntoWalletDoneTriggerScript\": null,\n      \"depositIntoWalletTriggerScript\": null,\n      \"latestGainMax\": 99999999,\n      \"meta\": null,\n      \"name\": \"gold-0001\",\n      \"resetDayOfMonth\": null,\n      \"resetDayOfWeek\": null,\n      \"resetHour\": null,\n      \"resetType\": \"none\",\n      \"withdrawFromWalletDoneTriggerScript\": null,\n      \"withdrawFromWalletTriggerScript\": null\n    },\n    {\n      \"balanceMax\": 1000,\n      \"createWalletDoneTriggerScript\": \"GS2Gold_create_wallet_done_trigger_script\",\n      \"createWalletTriggerScript\": \"GS2Gold_create_wallet_trigger_script\",\n      \"depositIntoWalletDoneTriggerScript\": \"GS2Gold_deposit_into_wallet_done_trigger_script\",\n      \"depositIntoWalletTriggerScript\": \"GS2Gold_deposit_into_wallet_trigger_script\",\n      \"latestGainMax\": 500,\n      \"meta\": \"meta\",\n      \"name\": \"gold-0002\",\n      \"resetDayOfMonth\": 1,\n      \"resetDayOfWeek\": null,\n      \"resetHour\": 17,\n      \"resetType\": \"monthly\",\n      \"withdrawFromWalletDoneTriggerScript\": \"GS2Gold_withdraw_from_wallet_done_trigger_script\",\n      \"withdrawFromWalletTriggerScript\": \"GS2Gold_withdraw_from_wallet_trigger_script\"\n    },\n    {\n      \"balanceMax\": 2000,\n      \"createWalletDoneTriggerScript\": null,\n      \"createWalletTriggerScript\": null,\n      \"depositIntoWalletDoneTriggerScript\": null,\n      \"depositIntoWalletTriggerScript\": null,\n      \"latestGainMax\": 1000,\n      \"meta\": \"meta3\",\n      \"name\": \"gold-0003\",\n      \"resetDayOfMonth\": null,\n      \"resetDayOfWeek\": \"monday\",\n      \"resetHour\": 2,\n      \"resetType\": \"weekly\",\n      \"withdrawFromWalletDoneTriggerScript\": null,\n      \"withdrawFromWalletTriggerScript\": null\n    }\n  ],\n  \"version\": \"2018-08-03\"\n}");
                    json2 = item.toString();
                }
            
                JsonAssert.assertJsonEquals(
                    json1,
                    json2,
                    JsonAssert.when(Option.IGNORING_ARRAY_ORDER)
                );
            } catch (JSONException jsonException) {
                assertTrue(false);
            }
        }
        {
            io.gs2.gold.control.GetCurrentGoldMasterResult result = client.getCurrentGoldMaster(
                new io.gs2.gold.control.GetCurrentGoldMasterRequest()
                    .withGoldPoolName("gold-pool-0001")
            );
            try {
                String json1 = null;
                {
                    JSONObject item = new JSONObject(result.getItem());
                    json1 = item.toString();
                }
            
                String json2 = null;
                {
                    JSONObject item = new JSONObject("{\n  \"golds\": [\n    {\n      \"balanceMax\": 99999999,\n      \"createWalletDoneTriggerScript\": null,\n      \"createWalletTriggerScript\": null,\n      \"depositIntoWalletDoneTriggerScript\": null,\n      \"depositIntoWalletTriggerScript\": null,\n      \"latestGainMax\": 99999999,\n      \"meta\": null,\n      \"name\": \"gold-0001\",\n      \"resetDayOfMonth\": null,\n      \"resetDayOfWeek\": null,\n      \"resetHour\": null,\n      \"resetType\": \"none\",\n      \"withdrawFromWalletDoneTriggerScript\": null,\n      \"withdrawFromWalletTriggerScript\": null\n    },\n    {\n      \"balanceMax\": 1000,\n      \"createWalletDoneTriggerScript\": \"GS2Gold_create_wallet_done_trigger_script\",\n      \"createWalletTriggerScript\": \"GS2Gold_create_wallet_trigger_script\",\n      \"depositIntoWalletDoneTriggerScript\": \"GS2Gold_deposit_into_wallet_done_trigger_script\",\n      \"depositIntoWalletTriggerScript\": \"GS2Gold_deposit_into_wallet_trigger_script\",\n      \"latestGainMax\": 500,\n      \"meta\": \"meta\",\n      \"name\": \"gold-0002\",\n      \"resetDayOfMonth\": 1,\n      \"resetDayOfWeek\": null,\n      \"resetHour\": 17,\n      \"resetType\": \"monthly\",\n      \"withdrawFromWalletDoneTriggerScript\": \"GS2Gold_withdraw_from_wallet_done_trigger_script\",\n      \"withdrawFromWalletTriggerScript\": \"GS2Gold_withdraw_from_wallet_trigger_script\"\n    },\n    {\n      \"balanceMax\": 2000,\n      \"createWalletDoneTriggerScript\": null,\n      \"createWalletTriggerScript\": null,\n      \"depositIntoWalletDoneTriggerScript\": null,\n      \"depositIntoWalletTriggerScript\": null,\n      \"latestGainMax\": 1000,\n      \"meta\": \"meta3\",\n      \"name\": \"gold-0003\",\n      \"resetDayOfMonth\": null,\n      \"resetDayOfWeek\": \"monday\",\n      \"resetHour\": 2,\n      \"resetType\": \"weekly\",\n      \"withdrawFromWalletDoneTriggerScript\": null,\n      \"withdrawFromWalletTriggerScript\": null\n    }\n  ],\n  \"version\": \"2018-08-03\"\n}");
                    json2 = item.toString();
                }
            
                JsonAssert.assertJsonEquals(
                    json1,
                    json2,
                    JsonAssert.when(Option.IGNORING_ARRAY_ORDER)
                );
            } catch (JSONException jsonException) {
                assertTrue(false);
            }
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