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
public class MasterTest extends TestCase {
    private static Random rand = new Random();

    private static String REGION = "ap-northeast-1";
    private static String export_data = null;
    private static List<GoldPool> goldPools = null;
    private static GoldPool goldPool1 = null;
    private static GoldMaster goldMaster1 = null;
    private static GoldMaster goldMaster2 = null;
    private static GoldMaster goldMaster3 = null;
    private static List<GoldMaster> goldMasters = null;
    private static String status = null;
    private static String nextPageToken = null;
    private static String CLIENT_ID = "gs2_gold_test_seeds.MasterTest-java";
    private static String CLIENT_SECRET = "Sxt8wMj8WIE0n44evX/wSN9MrWKTs1PPvZWJ/2rrHRA=";
    private static BasicGs2Credential credential = new BasicGs2Credential(CLIENT_ID, CLIENT_SECRET);
    private static Gs2GoldPrivateClient client = new Gs2GoldPrivateClient(credential);

    static {
        Gs2GoldPrivateClient.ENDPOINT = "gold-dev";
    }

	@BeforeClass
	public static void setUpClass() {
		shutdown();
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
            {
                io.gs2.gold.control.CreateGoldMasterResult result = client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withName("gold-0001")
                );
                goldMaster1 = result.getItem();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
            {
                io.gs2.gold.control.CreateGoldMasterResult result = client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withCreateWalletDoneTriggerScript("GS2Gold_create_wallet_done_trigger_script")
                        .withResetHour(Integer.valueOf(17))
                        .withMeta("meta")
                        .withResetType("monthly")
                        .withWithdrawFromWalletTriggerScript("GS2Gold_withdraw_from_wallet_trigger_script")
                        .withResetDayOfMonth(Integer.valueOf(1))
                        .withCreateWalletTriggerScript("GS2Gold_create_wallet_trigger_script")
                        .withDepositIntoWalletDoneTriggerScript("GS2Gold_deposit_into_wallet_done_trigger_script")
                        .withName("gold-0002")
                        .withLatestGainMax(Long.valueOf(500))
                        .withBalanceMax(Long.valueOf(1000))
                        .withWithdrawFromWalletDoneTriggerScript("GS2Gold_withdraw_from_wallet_done_trigger_script")
                        .withGoldPoolName("gold-pool-0001")
                        .withDepositIntoWalletTriggerScript("GS2Gold_deposit_into_wallet_trigger_script")
                );
                goldMaster2 = result.getItem();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
            {
                io.gs2.gold.control.CreateGoldMasterResult result = client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withMeta("meta3")
                        .withResetType("weekly")
                        .withName("gold-0003")
                        .withResetDayOfWeek("monday")
                        .withResetHour(Integer.valueOf(2))
                        .withLatestGainMax(Long.valueOf(1000))
                        .withBalanceMax(Long.valueOf(2000))
                );
                goldMaster3 = result.getItem();
            }
            // CloudDataStore に結果が反映されるのを待つ
            {
                goldMasters = null;
                goldMasters = null;
            
                int counter1 = 0;
                while ((goldMasters == null) || (goldMasters.size() != 3)) {
                    assertTrue(counter1 < 100000);
                    ++counter1;
                    try {
                        {
                            io.gs2.gold.control.DescribeGoldMasterResult result = client.describeGoldMaster(
                                new io.gs2.gold.control.DescribeGoldMasterRequest()
                                    .withGoldPoolName("gold-pool-0001")
                            );
                            goldMasters = result.getItems();
                        }
                    }catch (QuotaExceedException e) {
                    }
                }
            }
		} catch (BadRequestException e) {
			shutdown();
			throw e;
		}
	}

    
    @Test
    public void testExportMasterGoldPoolNameNone() {
        try {
            {
                client.exportMaster(
                    new io.gs2.gold.control.ExportMasterRequest()
                        .withGoldPoolName(null)
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.master.goldPoolName.error.require");
            
        }
    }
    
    
    @Test
    public void testExportMasterGoldPoolNameEmpty() {
        try {
            {
                client.exportMaster(
                    new io.gs2.gold.control.ExportMasterRequest()
                        .withGoldPoolName("")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.master.goldPoolName.error.require");
            
        }
    }
    
    
    @Test
    public void testExportMasterGoldPoolNameInvalid() {
        try {
            {
                client.exportMaster(
                    new io.gs2.gold.control.ExportMasterRequest()
                        .withGoldPoolName("@")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.master.goldPoolName.error.invalid");
            
        }
    }
    
    
    @Test
    public void testExportMasterGoldPoolNameTooLong() {
        try {
            {
                client.exportMaster(
                    new io.gs2.gold.control.ExportMasterRequest()
                        .withGoldPoolName("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.master.goldPoolName.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testExportMasterGoldPoolNameNotFound() {
        try {
            {
                client.exportMaster(
                    new io.gs2.gold.control.ExportMasterRequest()
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
    public void testBasic() {
        {
            io.gs2.gold.control.ExportMasterResult result = client.exportMaster(
                new io.gs2.gold.control.ExportMasterRequest()
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
                
                    int counter2 = 0;
                    while (!"DELETED".equals(status)) {
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
            }
            // DELETED になっていなければ削除する
            if (!"DELETED".equals(status)) {
                // ACTIVE になっていないかもしれないので、なるまで待つ
                {
                    status = null;
                
                    int counter3 = 0;
                    while (!"ACTIVE".equals(status)) {
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
                {
                    client.deleteGoldPool(
                        new io.gs2.gold.control.DeleteGoldPoolRequest()
                            .withGoldPoolName(goldPool1.getName())
                    );
                }
                {
                    status = null;
                
                    int counter4 = 0;
                    while (!"DELETED".equals(status)) {
                        assertTrue(counter4 < 100000);
                        ++counter4;
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