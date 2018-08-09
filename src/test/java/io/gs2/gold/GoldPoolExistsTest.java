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
public class GoldPoolExistsTest extends TestCase {
    private static Random rand = new Random();

    private static String REGION = "ap-northeast-1";
    private static String status = null;
    private static String nextPageToken = null;
    private static String CLIENT_ID = "gs2_gold_test_seeds.GoldPoolExistsTest-java";
    private static String CLIENT_SECRET = "a9KO0tfx5kq5Mt1cNG7Ie8xEpyh6SIRgsh3ZwDm/hCs=";
    private static BasicGs2Credential credential = new BasicGs2Credential("gs2_gold_test_seeds.GoldPoolExistsTest-java", "a9KO0tfx5kq5Mt1cNG7Ie8xEpyh6SIRgsh3ZwDm/hCs=");
    private static GoldPool goldPool1 = null;
    private static GoldPool goldPool2 = null;
    private static List<GoldPool> goldPools = null;
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
    public void testUpdateGoldPoolGoldPoolNameNone() {
        try {
            {
                client.updateGoldPool(
                    new io.gs2.gold.control.UpdateGoldPoolRequest()
                        .withGoldPoolName(null)
                        .withServiceClass("gold1.nano")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.goldPoolName.error.require");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldPoolGoldPoolNameEmpty() {
        try {
            {
                client.updateGoldPool(
                    new io.gs2.gold.control.UpdateGoldPoolRequest()
                        .withGoldPoolName("")
                        .withServiceClass("gold1.nano")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.goldPoolName.error.require");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldPoolGoldPoolNameInvalid() {
        try {
            {
                client.updateGoldPool(
                    new io.gs2.gold.control.UpdateGoldPoolRequest()
                        .withGoldPoolName("@")
                        .withServiceClass("gold1.nano")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.goldPoolName.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldPoolDescriptionTooLong() {
        try {
            {
                client.updateGoldPool(
                    new io.gs2.gold.control.UpdateGoldPoolRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withServiceClass("gold1.nano")
                        .withDescription("1025aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "description");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.description.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldPoolServiceClassNone() {
        try {
            {
                client.updateGoldPool(
                    new io.gs2.gold.control.UpdateGoldPoolRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withServiceClass(null)
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "serviceClass");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.serviceClass.error.require");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldPoolServiceClassEmpty() {
        try {
            {
                client.updateGoldPool(
                    new io.gs2.gold.control.UpdateGoldPoolRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withServiceClass("")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "serviceClass");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.serviceClass.error.require");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldPoolServiceClassInvalid() {
        try {
            {
                client.updateGoldPool(
                    new io.gs2.gold.control.UpdateGoldPoolRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withServiceClass("invalid")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "serviceClass");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.serviceClass.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldPoolCreateWalletTriggerScriptInvalid() {
        try {
            {
                client.updateGoldPool(
                    new io.gs2.gold.control.UpdateGoldPoolRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withServiceClass("gold1.nano")
                        .withCreateWalletTriggerScript("@")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "createWalletTriggerScript");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.createWalletTriggerScript.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldPoolCreateWalletTriggerScriptTooLong() {
        try {
            {
                client.updateGoldPool(
                    new io.gs2.gold.control.UpdateGoldPoolRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withServiceClass("gold1.nano")
                        .withCreateWalletTriggerScript("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "createWalletTriggerScript");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.createWalletTriggerScript.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldPoolCreateWalletDoneTriggerScriptInvalid() {
        try {
            {
                client.updateGoldPool(
                    new io.gs2.gold.control.UpdateGoldPoolRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withServiceClass("gold1.nano")
                        .withCreateWalletDoneTriggerScript("@")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "createWalletDoneTriggerScript");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.createWalletDoneTriggerScript.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldPoolCreateWalletDoneTriggerScriptTooLong() {
        try {
            {
                client.updateGoldPool(
                    new io.gs2.gold.control.UpdateGoldPoolRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withServiceClass("gold1.nano")
                        .withCreateWalletDoneTriggerScript("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "createWalletDoneTriggerScript");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.createWalletDoneTriggerScript.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldPoolDepositIntoWalletTriggerScriptInvalid() {
        try {
            {
                client.updateGoldPool(
                    new io.gs2.gold.control.UpdateGoldPoolRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withDepositIntoWalletTriggerScript("@")
                        .withServiceClass("gold1.nano")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "depositIntoWalletTriggerScript");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.depositIntoWalletTriggerScript.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldPoolDepositIntoWalletTriggerScriptTooLong() {
        try {
            {
                client.updateGoldPool(
                    new io.gs2.gold.control.UpdateGoldPoolRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withDepositIntoWalletTriggerScript("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withServiceClass("gold1.nano")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "depositIntoWalletTriggerScript");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.depositIntoWalletTriggerScript.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldPoolDepositIntoWalletDoneTriggerScriptInvalid() {
        try {
            {
                client.updateGoldPool(
                    new io.gs2.gold.control.UpdateGoldPoolRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withDepositIntoWalletDoneTriggerScript("@")
                        .withServiceClass("gold1.nano")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "depositIntoWalletDoneTriggerScript");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.depositIntoWalletDoneTriggerScript.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldPoolDepositIntoWalletDoneTriggerScriptTooLong() {
        try {
            {
                client.updateGoldPool(
                    new io.gs2.gold.control.UpdateGoldPoolRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withDepositIntoWalletDoneTriggerScript("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withServiceClass("gold1.nano")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "depositIntoWalletDoneTriggerScript");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.depositIntoWalletDoneTriggerScript.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldPoolWithdrawFromWalletTriggerScriptInvalid() {
        try {
            {
                client.updateGoldPool(
                    new io.gs2.gold.control.UpdateGoldPoolRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withServiceClass("gold1.nano")
                        .withWithdrawFromWalletTriggerScript("@")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "withdrawFromWalletTriggerScript");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.withdrawFromWalletTriggerScript.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldPoolWithdrawFromWalletTriggerScriptTooLong() {
        try {
            {
                client.updateGoldPool(
                    new io.gs2.gold.control.UpdateGoldPoolRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withServiceClass("gold1.nano")
                        .withWithdrawFromWalletTriggerScript("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "withdrawFromWalletTriggerScript");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.withdrawFromWalletTriggerScript.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldPoolWithdrawFromWalletDoneTriggerScriptInvalid() {
        try {
            {
                client.updateGoldPool(
                    new io.gs2.gold.control.UpdateGoldPoolRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withServiceClass("gold1.nano")
                        .withWithdrawFromWalletDoneTriggerScript("@")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "withdrawFromWalletDoneTriggerScript");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.withdrawFromWalletDoneTriggerScript.error.invalid");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldPoolWithdrawFromWalletDoneTriggerScriptTooLong() {
        try {
            {
                client.updateGoldPool(
                    new io.gs2.gold.control.UpdateGoldPoolRequest()
                        .withGoldPoolName("gold-pool-0001")
                        .withServiceClass("gold1.nano")
                        .withWithdrawFromWalletDoneTriggerScript("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "withdrawFromWalletDoneTriggerScript");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.withdrawFromWalletDoneTriggerScript.error.tooLong");
            
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