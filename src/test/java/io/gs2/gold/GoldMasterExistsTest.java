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
public class GoldMasterExistsTest extends TestCase {
    private static Random rand = new Random();

    private static String REGION = "ap-northeast-1";
    private static String status = null;
    private static String nextPageToken = null;
    private static String CLIENT_ID = "gs2_gold_test_seeds.GoldMasterExistsTest-java";
    private static String CLIENT_SECRET = "2IiH26fY5Me7O14NhmHxO+CoWMCS64KH046JHuyFqDE=";
    private static BasicGs2Credential credential = new BasicGs2Credential(CLIENT_ID, CLIENT_SECRET);
    private static List<GoldPool> goldPools = null;
    private static GoldPool goldPool1 = null;
    private static GoldMaster goldMaster1 = null;
    private static GoldMaster goldMaster2 = null;
    private static List<GoldMaster> goldMasters = null;
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
		} catch (BadRequestException e) {
			shutdown();
			throw e;
		}
	}

    
    @Test
    public void testUpdateGoldMasterGoldPoolNameNone() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withGoldPoolName(null)
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.goldPoolName.error.require");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterGoldPoolNameEmpty() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withGoldPoolName("")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.goldPoolName.error.require");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterGoldPoolNameInvalid() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withGoldPoolName("@")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.goldPoolName.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterGoldPoolNameTooLong() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withGoldPoolName("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.goldPoolName.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterGoldPoolNameNotFound() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
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
    public void testUpdateGoldMasterGoldNameNone() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withGoldName(null)
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.goldName.error.require");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterGoldNameEmpty() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withGoldName("")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.goldName.error.require");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterGoldNameInvalid() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withGoldName("@")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.goldName.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterGoldNameTooLong() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withGoldName("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.goldName.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterMetaTooLong() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withMeta("257aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "meta");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.meta.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterBalanceMaxInvalid() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withBalanceMax(Long.valueOf(-1))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "balanceMax");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.balanceMax.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterResetTypeInvalid() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withResetType("invalid")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "resetType");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.resetType.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterResetDayOfMonthMonthlyNone() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withResetHour(Integer.valueOf(17))
                        .withResetDayOfMonth(null)
                        .withResetType("monthly")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "resetDayOfMonth");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.resetDayOfMonth.error.require");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterResetDayOfMonthMonthlyInvalid() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withResetHour(Integer.valueOf(17))
                        .withResetDayOfMonth(Integer.valueOf(-1))
                        .withResetType("monthly")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "resetDayOfMonth");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.resetDayOfMonth.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterResetDayOfWeekWeeklyNone() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withResetDayOfWeek(null)
                        .withGoldPoolName("gold-pool-0001")
                        .withResetHour(Integer.valueOf(17))
                        .withResetType("weekly")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "resetDayOfWeek");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.resetDayOfWeek.error.require");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterResetDayOfWeekWeeklyEmpty() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withResetDayOfWeek("")
                        .withGoldPoolName("gold-pool-0001")
                        .withResetHour(Integer.valueOf(17))
                        .withResetType("weekly")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "resetDayOfWeek");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.resetDayOfWeek.error.require");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterResetDayOfWeekWeeklyInvalid() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withResetDayOfWeek("invalid")
                        .withGoldPoolName("gold-pool-0001")
                        .withResetHour(Integer.valueOf(17))
                        .withResetType("weekly")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "resetDayOfWeek");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.resetDayOfWeek.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterResetHourMonthlyNone() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withResetHour(null)
                        .withResetDayOfMonth(Integer.valueOf(1))
                        .withResetType("monthly")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "resetHour");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.resetHour.error.require");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterResetHourWeeklyNone() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withResetDayOfWeek("monday")
                        .withGoldPoolName("gold-pool-0001")
                        .withResetHour(null)
                        .withResetType("weekly")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "resetHour");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.resetHour.error.require");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterResetHourDailyNone() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withResetHour(null)
                        .withResetType("daily")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "resetHour");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.resetHour.error.require");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterResetHourMonthlyInvalid() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withResetHour(Integer.valueOf(-1))
                        .withResetDayOfMonth(Integer.valueOf(1))
                        .withResetType("monthly")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "resetHour");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.resetHour.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterResetHourWeeklyInvalid() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withResetDayOfWeek("monday")
                        .withGoldPoolName("gold-pool-0001")
                        .withResetHour(Integer.valueOf(-1))
                        .withResetType("weekly")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "resetHour");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.resetHour.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterResetHourDailyInvalid() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withResetHour(Integer.valueOf(-1))
                        .withResetType("daily")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "resetHour");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.resetHour.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterResetHourMonthlyInvalid2() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withResetHour(Integer.valueOf(24))
                        .withResetDayOfMonth(Integer.valueOf(1))
                        .withResetType("monthly")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "resetHour");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.resetHour.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterResetHourWeeklyInvalid2() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withResetDayOfWeek("monday")
                        .withGoldPoolName("gold-pool-0001")
                        .withResetHour(Integer.valueOf(24))
                        .withResetType("weekly")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "resetHour");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.resetHour.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterResetHourDailyInvalid2() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withResetHour(Integer.valueOf(24))
                        .withResetType("daily")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "resetHour");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.resetHour.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterLatestGainMaxMonthlyInvalid() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withResetHour(Integer.valueOf(17))
                        .withLatestGainMax(Long.valueOf(-1))
                        .withGoldPoolName("gold-pool-0001")
                        .withResetType("monthly")
                        .withResetDayOfMonth(Integer.valueOf(1))
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "latestGainMax");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.latestGainMax.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterLatestGainMaxWeeklyInvalid() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withResetHour(Integer.valueOf(17))
                        .withLatestGainMax(Long.valueOf(-1))
                        .withGoldPoolName("gold-pool-0001")
                        .withResetType("weekly")
                        .withResetDayOfWeek("monday")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "latestGainMax");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.latestGainMax.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterLatestGainMaxDailyInvalid() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withResetHour(Integer.valueOf(17))
                        .withLatestGainMax(Long.valueOf(-1))
                        .withResetType("daily")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "latestGainMax");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.latestGainMax.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterCreateWalletTriggerScriptInvalid() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withCreateWalletTriggerScript("@")
                        .withGoldPoolName("gold-pool-0001")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "createWalletTriggerScript");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.createWalletTriggerScript.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterCreateWalletTriggerScriptTooLong() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withCreateWalletTriggerScript("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withGoldPoolName("gold-pool-0001")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "createWalletTriggerScript");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.createWalletTriggerScript.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterCreateWalletDoneTriggerScriptInvalid() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withCreateWalletDoneTriggerScript("@")
                        .withGoldPoolName("gold-pool-0001")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "createWalletDoneTriggerScript");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.createWalletDoneTriggerScript.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterCreateWalletDoneTriggerScriptTooLong() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withCreateWalletDoneTriggerScript("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withGoldPoolName("gold-pool-0001")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "createWalletDoneTriggerScript");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.createWalletDoneTriggerScript.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterDepositIntoWalletTriggerScriptInvalid() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withDepositIntoWalletTriggerScript("@")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "depositIntoWalletTriggerScript");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.depositIntoWalletTriggerScript.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterDepositIntoWalletTriggerScriptTooLong() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withDepositIntoWalletTriggerScript("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "depositIntoWalletTriggerScript");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.depositIntoWalletTriggerScript.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterDepositIntoWalletDoneTriggerScriptInvalid() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withDepositIntoWalletDoneTriggerScript("@")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "depositIntoWalletDoneTriggerScript");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.depositIntoWalletDoneTriggerScript.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterDepositIntoWalletDoneTriggerScriptTooLong() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withDepositIntoWalletDoneTriggerScript("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "depositIntoWalletDoneTriggerScript");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.depositIntoWalletDoneTriggerScript.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterWithdrawFromWalletTriggerScriptInvalid() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withWithdrawFromWalletTriggerScript("@")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "withdrawFromWalletTriggerScript");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.withdrawFromWalletTriggerScript.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterWithdrawFromWalletTriggerScriptTooLong() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withWithdrawFromWalletTriggerScript("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "withdrawFromWalletTriggerScript");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.withdrawFromWalletTriggerScript.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterWithdrawFromWalletDoneTriggerScriptInvalid() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withWithdrawFromWalletDoneTriggerScript("@")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "withdrawFromWalletDoneTriggerScript");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.withdrawFromWalletDoneTriggerScript.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldMasterWithdrawFromWalletDoneTriggerScriptTooLong() {
        try {
            {
                client.updateGoldMaster(
                    new io.gs2.gold.control.UpdateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withWithdrawFromWalletDoneTriggerScript("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withGoldName("gold-0001")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "withdrawFromWalletDoneTriggerScript");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.withdrawFromWalletDoneTriggerScript.error.tooLong");
            
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