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
public class GoldMasterTest extends TestCase {
    private static Random rand = new Random();

    private static String REGION = "ap-northeast-1";
    private static String status = null;
    private static String nextPageToken = null;
    private static String CLIENT_ID = "gs2_gold_test_seeds.GoldMasterTest-java";
    private static String CLIENT_SECRET = "XbAoGcL+4t9AjF1XYKCTUjPcGMf2uYihBxq6BWhW0Gc=";
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
		} catch (BadRequestException e) {
			shutdown();
			throw e;
		}
	}

    
    @Test
    public void testCreateGoldMasterGoldPoolNameNone() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName(null)
                        .withName("gold-0001")
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
    public void testCreateGoldMasterGoldPoolNameEmpty() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("")
                        .withName("gold-0001")
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
    public void testCreateGoldMasterGoldPoolNameInvalid() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("@")
                        .withName("gold-0001")
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
    public void testCreateGoldMasterGoldPoolNameTooLong() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withName("gold-0001")
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
    public void testCreateGoldMasterGoldPoolNameNotFound() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("not_found")
                        .withName("gold-0001")
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
    public void testCreateGoldMasterNameNone() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withName(null)
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "name");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.name.error.require");
            
        }
    }
    
    
    @Test
    public void testCreateGoldMasterNameEmpty() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withName("")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "name");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.name.error.require");
            
        }
    }
    
    
    @Test
    public void testCreateGoldMasterNameInvalid() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withName("@")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "name");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.name.error.invalid");
            
        }
    }
    
    
    @Test
    public void testCreateGoldMasterNameTooLong() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withName("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "name");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.name.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testCreateGoldMasterMetaTooLong() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withMeta("257aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withName("gold-0001")
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
    public void testCreateGoldMasterBalanceMaxInvalid() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withBalanceMax(Long.valueOf(-1))
                        .withName("gold-0001")
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
    public void testCreateGoldMasterResetTypeInvalid() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withResetType("invalid")
                        .withName("gold-0001")
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
    public void testCreateGoldMasterResetDayOfMonthMonthlyNone() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withResetHour(Integer.valueOf(17))
                        .withResetType("monthly")
                        .withName("gold-0001")
                        .withResetDayOfMonth(null)
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
    public void testCreateGoldMasterResetDayOfMonthMonthlyInvalid() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withResetHour(Integer.valueOf(17))
                        .withResetType("monthly")
                        .withName("gold-0001")
                        .withResetDayOfMonth(Integer.valueOf(-1))
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
    public void testCreateGoldMasterResetDayOfWeekWeeklyNone() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withResetDayOfWeek(null)
                        .withGoldPoolName("gold-pool-0001")
                        .withResetHour(Integer.valueOf(17))
                        .withResetType("weekly")
                        .withName("gold-0001")
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
    public void testCreateGoldMasterResetDayOfWeekWeeklyEmpty() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withResetDayOfWeek("")
                        .withGoldPoolName("gold-pool-0001")
                        .withResetHour(Integer.valueOf(17))
                        .withResetType("weekly")
                        .withName("gold-0001")
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
    public void testCreateGoldMasterResetDayOfWeekWeeklyInvalid() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withResetDayOfWeek("invalid")
                        .withGoldPoolName("gold-pool-0001")
                        .withResetHour(Integer.valueOf(17))
                        .withResetType("weekly")
                        .withName("gold-0001")
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
    public void testCreateGoldMasterResetHourMonthlyNone() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withResetHour(null)
                        .withResetType("monthly")
                        .withName("gold-0001")
                        .withResetDayOfMonth(Integer.valueOf(1))
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
    public void testCreateGoldMasterResetHourWeeklyNone() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withResetDayOfWeek("monday")
                        .withGoldPoolName("gold-pool-0001")
                        .withResetHour(null)
                        .withResetType("weekly")
                        .withName("gold-0001")
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
    public void testCreateGoldMasterResetHourDailyNone() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withResetHour(null)
                        .withResetType("daily")
                        .withName("gold-0001")
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
    public void testCreateGoldMasterResetHourMonthlyInvalid() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withResetHour(Integer.valueOf(-1))
                        .withResetType("monthly")
                        .withName("gold-0001")
                        .withResetDayOfMonth(Integer.valueOf(1))
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
    public void testCreateGoldMasterResetHourWeeklyInvalid() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withResetDayOfWeek("monday")
                        .withGoldPoolName("gold-pool-0001")
                        .withResetHour(Integer.valueOf(-1))
                        .withResetType("weekly")
                        .withName("gold-0001")
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
    public void testCreateGoldMasterResetHourDailyInvalid() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withResetHour(Integer.valueOf(-1))
                        .withResetType("daily")
                        .withName("gold-0001")
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
    public void testCreateGoldMasterResetHourMonthlyInvalid2() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withResetHour(Integer.valueOf(24))
                        .withResetType("monthly")
                        .withName("gold-0001")
                        .withResetDayOfMonth(Integer.valueOf(1))
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
    public void testCreateGoldMasterResetHourWeeklyInvalid2() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withResetDayOfWeek("monday")
                        .withGoldPoolName("gold-pool-0001")
                        .withResetHour(Integer.valueOf(24))
                        .withResetType("weekly")
                        .withName("gold-0001")
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
    public void testCreateGoldMasterResetHourDailyInvalid2() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withResetHour(Integer.valueOf(24))
                        .withResetType("daily")
                        .withName("gold-0001")
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
    public void testCreateGoldMasterLatestGainMaxMonthlyInvalid() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withName("gold-0001")
                        .withResetHour(Integer.valueOf(17))
                        .withLatestGainMax(Long.valueOf(-1))
                        .withGoldPoolName("gold-pool-0001")
                        .withResetType("monthly")
                        .withResetDayOfMonth(Integer.valueOf(1))
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
    public void testCreateGoldMasterLatestGainMaxWeeklyInvalid() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withName("gold-0001")
                        .withResetHour(Integer.valueOf(17))
                        .withLatestGainMax(Long.valueOf(-1))
                        .withGoldPoolName("gold-pool-0001")
                        .withResetType("weekly")
                        .withResetDayOfWeek("monday")
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
    public void testCreateGoldMasterLatestGainMaxDailyInvalid() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withResetHour(Integer.valueOf(17))
                        .withLatestGainMax(Long.valueOf(-1))
                        .withResetType("daily")
                        .withName("gold-0001")
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
    public void testCreateGoldMasterCreateWalletTriggerScriptInvalid() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withName("gold-0001")
                        .withCreateWalletTriggerScript("@")
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
    public void testCreateGoldMasterCreateWalletTriggerScriptTooLong() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withName("gold-0001")
                        .withCreateWalletTriggerScript("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
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
    public void testCreateGoldMasterCreateWalletDoneTriggerScriptInvalid() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withName("gold-0001")
                        .withCreateWalletDoneTriggerScript("@")
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
    public void testCreateGoldMasterCreateWalletDoneTriggerScriptTooLong() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withName("gold-0001")
                        .withCreateWalletDoneTriggerScript("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
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
    public void testCreateGoldMasterDepositIntoWalletTriggerScriptInvalid() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withDepositIntoWalletTriggerScript("@")
                        .withName("gold-0001")
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
    public void testCreateGoldMasterDepositIntoWalletTriggerScriptTooLong() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withDepositIntoWalletTriggerScript("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withName("gold-0001")
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
    public void testCreateGoldMasterDepositIntoWalletDoneTriggerScriptInvalid() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withDepositIntoWalletDoneTriggerScript("@")
                        .withName("gold-0001")
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
    public void testCreateGoldMasterDepositIntoWalletDoneTriggerScriptTooLong() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withDepositIntoWalletDoneTriggerScript("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withName("gold-0001")
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
    public void testCreateGoldMasterWithdrawFromWalletTriggerScriptInvalid() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withName("gold-0001")
                        .withWithdrawFromWalletTriggerScript("@")
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
    public void testCreateGoldMasterWithdrawFromWalletTriggerScriptTooLong() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withName("gold-0001")
                        .withWithdrawFromWalletTriggerScript("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
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
    public void testCreateGoldMasterWithdrawFromWalletDoneTriggerScriptInvalid() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withName("gold-0001")
                        .withWithdrawFromWalletDoneTriggerScript("@")
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
    public void testCreateGoldMasterWithdrawFromWalletDoneTriggerScriptTooLong() {
        try {
            {
                client.createGoldMaster(
                    new io.gs2.gold.control.CreateGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withName("gold-0001")
                        .withWithdrawFromWalletDoneTriggerScript("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "withdrawFromWalletDoneTriggerScript");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.withdrawFromWalletDoneTriggerScript.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testGetGoldMasterGoldPoolNameNone() {
        try {
            {
                client.getGoldMaster(
                    new io.gs2.gold.control.GetGoldMasterRequest()
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
    public void testGetGoldMasterGoldPoolNameEmpty() {
        try {
            {
                client.getGoldMaster(
                    new io.gs2.gold.control.GetGoldMasterRequest()
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
    public void testGetGoldMasterGoldPoolNameInvalid() {
        try {
            {
                client.getGoldMaster(
                    new io.gs2.gold.control.GetGoldMasterRequest()
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
    public void testGetGoldMasterGoldPoolNameTooLong() {
        try {
            {
                client.getGoldMaster(
                    new io.gs2.gold.control.GetGoldMasterRequest()
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
    public void testGetGoldMasterGoldPoolNameNotFound() {
        try {
            {
                client.getGoldMaster(
                    new io.gs2.gold.control.GetGoldMasterRequest()
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
    public void testGetGoldMasterGoldNameNone() {
        try {
            {
                client.getGoldMaster(
                    new io.gs2.gold.control.GetGoldMasterRequest()
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
    public void testGetGoldMasterGoldNameEmpty() {
        try {
            {
                client.getGoldMaster(
                    new io.gs2.gold.control.GetGoldMasterRequest()
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
    public void testGetGoldMasterGoldNameInvalid() {
        try {
            {
                client.getGoldMaster(
                    new io.gs2.gold.control.GetGoldMasterRequest()
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
    public void testGetGoldMasterGoldNameTooLong() {
        try {
            {
                client.getGoldMaster(
                    new io.gs2.gold.control.GetGoldMasterRequest()
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
    public void testGetGoldMasterGoldNameNotFound() {
        try {
            {
                client.getGoldMaster(
                    new io.gs2.gold.control.GetGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withGoldName("not_found")
                );
            }
            assertTrue(false);
        }catch (NotFoundException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldMaster");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.goldMaster.error.notFound");
            
        }
    }
    
    
    @Test
    public void testDeleteGoldMasterGoldPoolNameNone() {
        try {
            {
                client.deleteGoldMaster(
                    new io.gs2.gold.control.DeleteGoldMasterRequest()
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
    public void testDeleteGoldMasterGoldPoolNameEmpty() {
        try {
            {
                client.deleteGoldMaster(
                    new io.gs2.gold.control.DeleteGoldMasterRequest()
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
    public void testDeleteGoldMasterGoldPoolNameInvalid() {
        try {
            {
                client.deleteGoldMaster(
                    new io.gs2.gold.control.DeleteGoldMasterRequest()
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
    public void testDeleteGoldMasterGoldPoolNameTooLong() {
        try {
            {
                client.deleteGoldMaster(
                    new io.gs2.gold.control.DeleteGoldMasterRequest()
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
    public void testDeleteGoldMasterGoldPoolNameNotFound() {
        try {
            {
                client.deleteGoldMaster(
                    new io.gs2.gold.control.DeleteGoldMasterRequest()
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
    public void testDeleteGoldMasterGoldNameNone() {
        try {
            {
                client.deleteGoldMaster(
                    new io.gs2.gold.control.DeleteGoldMasterRequest()
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
    public void testDeleteGoldMasterGoldNameEmpty() {
        try {
            {
                client.deleteGoldMaster(
                    new io.gs2.gold.control.DeleteGoldMasterRequest()
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
    public void testDeleteGoldMasterGoldNameInvalid() {
        try {
            {
                client.deleteGoldMaster(
                    new io.gs2.gold.control.DeleteGoldMasterRequest()
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
    public void testDeleteGoldMasterGoldNameTooLong() {
        try {
            {
                client.deleteGoldMaster(
                    new io.gs2.gold.control.DeleteGoldMasterRequest()
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
    public void testDeleteGoldMasterGoldNameNotFound() {
        try {
            {
                client.deleteGoldMaster(
                    new io.gs2.gold.control.DeleteGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withGoldName("not_found")
                );
            }
            assertTrue(false);
        }catch (NotFoundException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldMaster");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.goldMaster.error.notFound");
            
        }
    }
    
    
    @Test
    public void testDescribeGoldMasterGoldPoolNameNone() {
        try {
            {
                client.describeGoldMaster(
                    new io.gs2.gold.control.DescribeGoldMasterRequest()
                        .withGoldPoolName(null)
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
    public void testDescribeGoldMasterGoldPoolNameEmpty() {
        try {
            {
                client.describeGoldMaster(
                    new io.gs2.gold.control.DescribeGoldMasterRequest()
                        .withGoldPoolName("")
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
    public void testDescribeGoldMasterGoldPoolNameInvalid() {
        try {
            {
                client.describeGoldMaster(
                    new io.gs2.gold.control.DescribeGoldMasterRequest()
                        .withGoldPoolName("@")
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
    public void testDescribeGoldMasterGoldPoolNameTooLong() {
        try {
            {
                client.describeGoldMaster(
                    new io.gs2.gold.control.DescribeGoldMasterRequest()
                        .withGoldPoolName("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
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
    public void testDescribeGoldMasterGoldPoolNameNotFound() {
        try {
            {
                client.describeGoldMaster(
                    new io.gs2.gold.control.DescribeGoldMasterRequest()
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
    public void testDescribeGoldMasterLimitInvalid() {
        try {
            {
                client.describeGoldMaster(
                    new io.gs2.gold.control.DescribeGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withLimit(Integer.valueOf(-1))
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "limit");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldMaster.limit.error.invalid");
            
        }
    }
    
    
    @Test
    public void testBasic() {
        // GoldMaster を生成
        {
            io.gs2.gold.control.CreateGoldMasterResult result = client.createGoldMaster(
                new io.gs2.gold.control.CreateGoldMasterRequest()
                    .withGoldPoolName("gold-pool-0001")
                    .withName("gold-0001")
            );
            assertNotNull(result);
            assertNotNull(result.getItem());
            assertEquals(result.getItem().getName(), "gold-0001");
            assertEquals(result.getItem().getBalanceMax(), Long.valueOf(99999999));
            assertEquals(result.getItem().getResetType(), "none");
            assertNull(result.getItem().getResetDayOfMonth());
            assertNull(result.getItem().getResetDayOfWeek());
            assertNull(result.getItem().getResetHour());
            assertEquals(result.getItem().getLatestGainMax(), Long.valueOf(99999999));
            assertNotNull(result.getItem().getCreateAt());
            goldMaster1 = result.getItem();
        }
        // GoldMaster をもう一つ生成
        {
            io.gs2.gold.control.CreateGoldMasterResult result = client.createGoldMaster(
                new io.gs2.gold.control.CreateGoldMasterRequest()
                    .withCreateWalletDoneTriggerScript("createWalletDoneTriggerScript")
                    .withResetHour(Integer.valueOf(17))
                    .withResetType("weekly")
                    .withWithdrawFromWalletTriggerScript("withdrawFromWalletTriggerScript")
                    .withResetDayOfMonth(Integer.valueOf(1))
                    .withResetDayOfWeek("tuesday")
                    .withCreateWalletTriggerScript("createWalletTriggerScript")
                    .withDepositIntoWalletDoneTriggerScript("depositIntoWalletDoneTriggerScript")
                    .withName("gold-0002")
                    .withLatestGainMax(Long.valueOf(450))
                    .withBalanceMax(Long.valueOf(2000))
                    .withWithdrawFromWalletDoneTriggerScript("withdrawFromWalletDoneTriggerScript")
                    .withGoldPoolName("gold-pool-0001")
                    .withDepositIntoWalletTriggerScript("depositIntoWalletTriggerScript")
            );
            assertNotNull(result);
            assertNotNull(result.getItem());
            assertEquals(result.getItem().getName(), "gold-0002");
            assertEquals(result.getItem().getBalanceMax(), Long.valueOf(2000));
            assertEquals(result.getItem().getResetType(), "weekly");
            assertEquals(result.getItem().getResetDayOfWeek(), "tuesday");
            assertEquals(result.getItem().getResetHour(), Integer.valueOf(17));
            assertEquals(result.getItem().getLatestGainMax(), Long.valueOf(450));
            assertNotNull(result.getItem().getCreateAt());
            goldMaster2 = result.getItem();
        }
        // GoldMaster を取得して内容確認
        {
            io.gs2.gold.control.GetGoldMasterResult result = client.getGoldMaster(
                new io.gs2.gold.control.GetGoldMasterRequest()
                    .withGoldPoolName("gold-pool-0001")
                    .withGoldName("gold-0001")
            );
            assertNotNull(result);
            assertNotNull(result.getItem());
            assertEquals(result.getItem().getName(), "gold-0001");
            assertEquals(result.getItem().getBalanceMax(), Long.valueOf(99999999));
            assertEquals(result.getItem().getResetType(), "none");
            assertNull(result.getItem().getResetDayOfMonth());
            assertNull(result.getItem().getResetDayOfWeek());
            assertNull(result.getItem().getResetHour());
            assertEquals(result.getItem().getLatestGainMax(), Long.valueOf(99999999));
            assertNotNull(result.getItem().getCreateAt());
        }
        // GoldMaster の内容を更新
        {
            io.gs2.gold.control.UpdateGoldMasterResult result = client.updateGoldMaster(
                new io.gs2.gold.control.UpdateGoldMasterRequest()
                    .withCreateWalletDoneTriggerScript("createWalletDoneTriggerScript1")
                    .withResetHour(Integer.valueOf(17))
                    .withResetType("monthly")
                    .withWithdrawFromWalletTriggerScript("withdrawFromWalletTriggerScript1")
                    .withResetDayOfMonth(Integer.valueOf(1))
                    .withResetDayOfWeek("tuesday")
                    .withCreateWalletTriggerScript("createWalletTriggerScript1")
                    .withDepositIntoWalletDoneTriggerScript("depositIntoWalletDoneTriggerScript1")
                    .withLatestGainMax(Long.valueOf(300))
                    .withBalanceMax(Long.valueOf(3000))
                    .withWithdrawFromWalletDoneTriggerScript("withdrawFromWalletDoneTriggerScript1")
                    .withGoldPoolName("gold-pool-0001")
                    .withDepositIntoWalletTriggerScript("depositIntoWalletTriggerScript1")
                    .withGoldName("gold-0001")
            );
            assertNotNull(result);
            assertNotNull(result.getItem());
            assertEquals(result.getItem().getName(), "gold-0001");
            assertEquals(result.getItem().getBalanceMax(), Long.valueOf(3000));
            assertEquals(result.getItem().getResetDayOfMonth(), Integer.valueOf(1));
            assertEquals(result.getItem().getResetHour(), Integer.valueOf(17));
            assertEquals(result.getItem().getLatestGainMax(), Long.valueOf(300));
            assertNotNull(result.getItem().getCreateAt());
            goldMaster1 = result.getItem();
        }
        // GoldMaster を取得して更新確認
        {
            io.gs2.gold.control.GetGoldMasterResult result = client.getGoldMaster(
                new io.gs2.gold.control.GetGoldMasterRequest()
                    .withGoldPoolName("gold-pool-0001")
                    .withGoldName("gold-0001")
            );
            assertNotNull(result);
            assertNotNull(result.getItem());
            assertEquals(result.getItem().getName(), "gold-0001");
            assertEquals(result.getItem().getBalanceMax(), Long.valueOf(3000));
            assertEquals(result.getItem().getResetDayOfMonth(), Integer.valueOf(1));
            assertEquals(result.getItem().getResetHour(), Integer.valueOf(17));
            assertEquals(result.getItem().getLatestGainMax(), Long.valueOf(300));
            assertNotNull(result.getItem().getCreateAt());
            goldMaster1 = result.getItem();
        }
        {
            io.gs2.gold.control.DescribeGoldMasterResult result = client.describeGoldMaster(
                new io.gs2.gold.control.DescribeGoldMasterRequest()
                    .withGoldPoolName("gold-pool-0001")
            );
            assertNotNull(result);
            assertNotNull(result.getItems());
            assertEquals(2, result.getItems().size());
            assertNull(result.getNextPageToken());
            goldMasters = result.getItems();
        }
        if (goldMaster1.getName().equals(goldMasters.get(1).getName())) {
            GoldMaster temp = goldMaster1;
            goldMaster1 = goldMaster2;
            goldMaster2 = temp;
        }
        assertEquals(goldMasters.get(0).getName(), goldMaster1.getName());
        assertEquals(goldMasters.get(0).getBalanceMax(), goldMaster1.getBalanceMax());
        assertEquals(goldMasters.get(0).getResetType(), goldMaster1.getResetType());
        assertEquals(goldMasters.get(0).getResetDayOfMonth(), goldMaster1.getResetDayOfMonth());
        assertEquals(goldMasters.get(0).getResetDayOfWeek(), goldMaster1.getResetDayOfWeek());
        assertEquals(goldMasters.get(0).getResetHour(), goldMaster1.getResetHour());
        assertEquals(goldMasters.get(0).getLatestGainMax(), goldMaster1.getLatestGainMax());
        assertEquals(goldMasters.get(0).getCreateAt(), goldMaster1.getCreateAt());
        assertEquals(goldMasters.get(0).getUpdateAt(), goldMaster1.getUpdateAt());
        
        assertEquals(goldMasters.get(1).getName(), goldMaster2.getName());
        assertEquals(goldMasters.get(1).getBalanceMax(), goldMaster2.getBalanceMax());
        assertEquals(goldMasters.get(1).getResetType(), goldMaster2.getResetType());
        assertEquals(goldMasters.get(1).getResetDayOfMonth(), goldMaster2.getResetDayOfMonth());
        assertEquals(goldMasters.get(1).getResetDayOfWeek(), goldMaster2.getResetDayOfWeek());
        assertEquals(goldMasters.get(1).getResetHour(), goldMaster2.getResetHour());
        assertEquals(goldMasters.get(1).getLatestGainMax(), goldMaster2.getLatestGainMax());
        assertEquals(goldMasters.get(1).getCreateAt(), goldMaster2.getCreateAt());
        assertEquals(goldMasters.get(1).getUpdateAt(), goldMaster2.getUpdateAt());
        
        // 1件ずつ列挙
        {
            io.gs2.gold.control.DescribeGoldMasterResult result = client.describeGoldMaster(
                new io.gs2.gold.control.DescribeGoldMasterRequest()
                    .withGoldPoolName("gold-pool-0001")
                    .withLimit(Integer.valueOf(1))
            );
            assertNotNull(result.getItems());
            assertEquals(1, result.getItems().size());
            assertNotNull(result.getNextPageToken());
            goldMasters = result.getItems();
            nextPageToken = result.getNextPageToken();
        }
        assertEquals(goldMasters.get(0).getName(), goldMaster1.getName());
        assertEquals(goldMasters.get(0).getBalanceMax(), goldMaster1.getBalanceMax());
        assertEquals(goldMasters.get(0).getResetType(), goldMaster1.getResetType());
        assertEquals(goldMasters.get(0).getResetDayOfMonth(), goldMaster1.getResetDayOfMonth());
        assertEquals(goldMasters.get(0).getResetDayOfWeek(), goldMaster1.getResetDayOfWeek());
        assertEquals(goldMasters.get(0).getResetHour(), goldMaster1.getResetHour());
        assertEquals(goldMasters.get(0).getLatestGainMax(), goldMaster1.getLatestGainMax());
        assertEquals(goldMasters.get(0).getCreateAt(), goldMaster1.getCreateAt());
        assertEquals(goldMasters.get(0).getUpdateAt(), goldMaster1.getUpdateAt());
        
        {
            io.gs2.gold.control.DescribeGoldMasterResult result = client.describeGoldMaster(
                new io.gs2.gold.control.DescribeGoldMasterRequest()
                    .withGoldPoolName("gold-pool-0001")
                    .withLimit(Integer.valueOf(10))
                    .withPageToken(nextPageToken)
            );
            assertNotNull(result.getItems());
            assertEquals(1, result.getItems().size());
            assertNull(result.getNextPageToken());
            goldMasters = result.getItems();
        }
        assertEquals(goldMasters.get(0).getName(), goldMaster2.getName());
        assertEquals(goldMasters.get(0).getBalanceMax(), goldMaster2.getBalanceMax());
        assertEquals(goldMasters.get(0).getResetType(), goldMaster2.getResetType());
        assertEquals(goldMasters.get(0).getResetDayOfMonth(), goldMaster2.getResetDayOfMonth());
        assertEquals(goldMasters.get(0).getResetDayOfWeek(), goldMaster2.getResetDayOfWeek());
        assertEquals(goldMasters.get(0).getResetHour(), goldMaster2.getResetHour());
        assertEquals(goldMasters.get(0).getLatestGainMax(), goldMaster2.getLatestGainMax());
        assertEquals(goldMasters.get(0).getCreateAt(), goldMaster2.getCreateAt());
        assertEquals(goldMasters.get(0).getUpdateAt(), goldMaster2.getUpdateAt());
        
        // GoldMaster を1件ずつ削除（まとめて待つことで合計の待ち時間を短縮する）
        {
            client.deleteGoldMaster(
                new io.gs2.gold.control.DeleteGoldMasterRequest()
                    .withGoldPoolName("gold-pool-0001")
                    .withGoldName(goldMaster1.getName())
            );
        }
        {
            client.deleteGoldMaster(
                new io.gs2.gold.control.DeleteGoldMasterRequest()
                    .withGoldPoolName("gold-pool-0001")
                    .withGoldName(goldMaster2.getName())
            );
        }
        try {
            {
                client.getGoldMaster(
                    new io.gs2.gold.control.GetGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withGoldName(goldMaster1.getName())
                );
            }
            assertTrue(false);
        }catch (NotFoundException e) {
        }
        try {
            {
                client.getGoldMaster(
                    new io.gs2.gold.control.GetGoldMasterRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withGoldName(goldMaster2.getName())
                );
            }
            assertTrue(false);
        }catch (NotFoundException e) {
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