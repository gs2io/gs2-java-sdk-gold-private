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
public class GoldPoolTest extends TestCase {
    private static Random rand = new Random();

    private static String REGION = "ap-northeast-1";
    private static String status = null;
    private static String nextPageToken = null;
    private static String CLIENT_ID = "gs2_gold_test_seeds.GoldPoolTest-java";
    private static String CLIENT_SECRET = "v4y0gbGBEE1aaYXeKFHICqVHbgDYmi6C54QiN7qDJQg=";
    private static BasicGs2Credential credential = new BasicGs2Credential(CLIENT_ID, CLIENT_SECRET);
    private static GoldPool goldPool1 = null;
    private static GoldPool goldPool2 = null;
    private static List<GoldPool> goldPools = null;
    private static Gs2GoldPrivateClient client = new Gs2GoldPrivateClient(credential);

    static {
        Gs2GoldPrivateClient.ENDPOINT = "gold-dev";
    }

	@BeforeClass
	public static void setUpClass() {
		shutdown();
		try {

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
                        .withDescription("GoldPool 1")
                        .withCreateWalletDoneTriggerScript("createWalletDoneTriggerScript1")
                        .withDepositIntoWalletTriggerScript("depositIntoWalletTriggerScript1")
                        .withServiceClass("gold1.micro")
                        .withWithdrawFromWalletDoneTriggerScript("withdrawFromWalletDoneTriggerScript1")
                        .withGoldPoolName(null)
                        .withWithdrawFromWalletTriggerScript("withdrawFromWalletTriggerScript1")
                        .withCreateWalletTriggerScript("createWalletTriggerScript1")
                        .withDepositIntoWalletDoneTriggerScript("depositIntoWalletDoneTriggerScript1")
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
                        .withDescription("GoldPool 1")
                        .withCreateWalletDoneTriggerScript("createWalletDoneTriggerScript1")
                        .withDepositIntoWalletTriggerScript("depositIntoWalletTriggerScript1")
                        .withServiceClass("gold1.micro")
                        .withWithdrawFromWalletDoneTriggerScript("withdrawFromWalletDoneTriggerScript1")
                        .withGoldPoolName("")
                        .withWithdrawFromWalletTriggerScript("withdrawFromWalletTriggerScript1")
                        .withCreateWalletTriggerScript("createWalletTriggerScript1")
                        .withDepositIntoWalletDoneTriggerScript("depositIntoWalletDoneTriggerScript1")
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
                        .withDescription("GoldPool 1")
                        .withCreateWalletDoneTriggerScript("createWalletDoneTriggerScript1")
                        .withDepositIntoWalletTriggerScript("depositIntoWalletTriggerScript1")
                        .withServiceClass("gold1.micro")
                        .withWithdrawFromWalletDoneTriggerScript("withdrawFromWalletDoneTriggerScript1")
                        .withGoldPoolName("@")
                        .withWithdrawFromWalletTriggerScript("withdrawFromWalletTriggerScript1")
                        .withCreateWalletTriggerScript("createWalletTriggerScript1")
                        .withDepositIntoWalletDoneTriggerScript("depositIntoWalletDoneTriggerScript1")
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
    public void testUpdateGoldPoolGoldPoolNameTooLong() {
        try {
            {
                client.updateGoldPool(
                    new io.gs2.gold.control.UpdateGoldPoolRequest()
                        .withDescription("GoldPool 1")
                        .withCreateWalletDoneTriggerScript("createWalletDoneTriggerScript1")
                        .withDepositIntoWalletTriggerScript("depositIntoWalletTriggerScript1")
                        .withServiceClass("gold1.micro")
                        .withWithdrawFromWalletDoneTriggerScript("withdrawFromWalletDoneTriggerScript1")
                        .withGoldPoolName("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withWithdrawFromWalletTriggerScript("withdrawFromWalletTriggerScript1")
                        .withCreateWalletTriggerScript("createWalletTriggerScript1")
                        .withDepositIntoWalletDoneTriggerScript("depositIntoWalletDoneTriggerScript1")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.goldPoolName.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testUpdateGoldPoolDescriptionTooLong() {
        try {
            {
                client.updateGoldPool(
                    new io.gs2.gold.control.UpdateGoldPoolRequest()
                        .withDescription("1025aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withCreateWalletDoneTriggerScript("createWalletDoneTriggerScript1")
                        .withDepositIntoWalletTriggerScript("depositIntoWalletTriggerScript1")
                        .withServiceClass("gold1.micro")
                        .withWithdrawFromWalletDoneTriggerScript("withdrawFromWalletDoneTriggerScript1")
                        .withGoldPoolName("gold-pool-0001")
                        .withWithdrawFromWalletTriggerScript("withdrawFromWalletTriggerScript1")
                        .withCreateWalletTriggerScript("createWalletTriggerScript1")
                        .withDepositIntoWalletDoneTriggerScript("depositIntoWalletDoneTriggerScript1")
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
                        .withDescription("GoldPool 1")
                        .withCreateWalletDoneTriggerScript("createWalletDoneTriggerScript1")
                        .withDepositIntoWalletTriggerScript("depositIntoWalletTriggerScript1")
                        .withServiceClass(null)
                        .withWithdrawFromWalletDoneTriggerScript("withdrawFromWalletDoneTriggerScript1")
                        .withGoldPoolName("gold-pool-0001")
                        .withWithdrawFromWalletTriggerScript("withdrawFromWalletTriggerScript1")
                        .withCreateWalletTriggerScript("createWalletTriggerScript1")
                        .withDepositIntoWalletDoneTriggerScript("depositIntoWalletDoneTriggerScript1")
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
                        .withDescription("GoldPool 1")
                        .withCreateWalletDoneTriggerScript("createWalletDoneTriggerScript1")
                        .withDepositIntoWalletTriggerScript("depositIntoWalletTriggerScript1")
                        .withServiceClass("")
                        .withWithdrawFromWalletDoneTriggerScript("withdrawFromWalletDoneTriggerScript1")
                        .withGoldPoolName("gold-pool-0001")
                        .withWithdrawFromWalletTriggerScript("withdrawFromWalletTriggerScript1")
                        .withCreateWalletTriggerScript("createWalletTriggerScript1")
                        .withDepositIntoWalletDoneTriggerScript("depositIntoWalletDoneTriggerScript1")
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
                        .withDescription("GoldPool 1")
                        .withCreateWalletDoneTriggerScript("createWalletDoneTriggerScript1")
                        .withDepositIntoWalletTriggerScript("depositIntoWalletTriggerScript1")
                        .withServiceClass("invalid")
                        .withWithdrawFromWalletDoneTriggerScript("withdrawFromWalletDoneTriggerScript1")
                        .withGoldPoolName("gold-pool-0001")
                        .withWithdrawFromWalletTriggerScript("withdrawFromWalletTriggerScript1")
                        .withCreateWalletTriggerScript("createWalletTriggerScript1")
                        .withDepositIntoWalletDoneTriggerScript("depositIntoWalletDoneTriggerScript1")
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
                        .withDescription("GoldPool 1")
                        .withCreateWalletDoneTriggerScript("createWalletDoneTriggerScript1")
                        .withDepositIntoWalletTriggerScript("depositIntoWalletTriggerScript1")
                        .withServiceClass("gold1.micro")
                        .withWithdrawFromWalletDoneTriggerScript("withdrawFromWalletDoneTriggerScript1")
                        .withGoldPoolName("gold-pool-0001")
                        .withWithdrawFromWalletTriggerScript("withdrawFromWalletTriggerScript1")
                        .withCreateWalletTriggerScript("@")
                        .withDepositIntoWalletDoneTriggerScript("depositIntoWalletDoneTriggerScript1")
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
                        .withDescription("GoldPool 1")
                        .withCreateWalletDoneTriggerScript("createWalletDoneTriggerScript1")
                        .withDepositIntoWalletTriggerScript("depositIntoWalletTriggerScript1")
                        .withServiceClass("gold1.micro")
                        .withWithdrawFromWalletDoneTriggerScript("withdrawFromWalletDoneTriggerScript1")
                        .withGoldPoolName("gold-pool-0001")
                        .withWithdrawFromWalletTriggerScript("withdrawFromWalletTriggerScript1")
                        .withCreateWalletTriggerScript("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withDepositIntoWalletDoneTriggerScript("depositIntoWalletDoneTriggerScript1")
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
                        .withDescription("GoldPool 1")
                        .withCreateWalletDoneTriggerScript("@")
                        .withDepositIntoWalletTriggerScript("depositIntoWalletTriggerScript1")
                        .withServiceClass("gold1.micro")
                        .withWithdrawFromWalletDoneTriggerScript("withdrawFromWalletDoneTriggerScript1")
                        .withGoldPoolName("gold-pool-0001")
                        .withWithdrawFromWalletTriggerScript("withdrawFromWalletTriggerScript1")
                        .withCreateWalletTriggerScript("createWalletTriggerScript1")
                        .withDepositIntoWalletDoneTriggerScript("depositIntoWalletDoneTriggerScript1")
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
                        .withDescription("GoldPool 1")
                        .withCreateWalletDoneTriggerScript("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withDepositIntoWalletTriggerScript("depositIntoWalletTriggerScript1")
                        .withServiceClass("gold1.micro")
                        .withWithdrawFromWalletDoneTriggerScript("withdrawFromWalletDoneTriggerScript1")
                        .withGoldPoolName("gold-pool-0001")
                        .withWithdrawFromWalletTriggerScript("withdrawFromWalletTriggerScript1")
                        .withCreateWalletTriggerScript("createWalletTriggerScript1")
                        .withDepositIntoWalletDoneTriggerScript("depositIntoWalletDoneTriggerScript1")
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
                        .withDescription("GoldPool 1")
                        .withCreateWalletDoneTriggerScript("createWalletDoneTriggerScript1")
                        .withDepositIntoWalletTriggerScript("@")
                        .withServiceClass("gold1.micro")
                        .withWithdrawFromWalletDoneTriggerScript("withdrawFromWalletDoneTriggerScript1")
                        .withGoldPoolName("gold-pool-0001")
                        .withWithdrawFromWalletTriggerScript("withdrawFromWalletTriggerScript1")
                        .withCreateWalletTriggerScript("createWalletTriggerScript1")
                        .withDepositIntoWalletDoneTriggerScript("depositIntoWalletDoneTriggerScript1")
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
                        .withDescription("GoldPool 1")
                        .withCreateWalletDoneTriggerScript("createWalletDoneTriggerScript1")
                        .withDepositIntoWalletTriggerScript("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withServiceClass("gold1.micro")
                        .withWithdrawFromWalletDoneTriggerScript("withdrawFromWalletDoneTriggerScript1")
                        .withGoldPoolName("gold-pool-0001")
                        .withWithdrawFromWalletTriggerScript("withdrawFromWalletTriggerScript1")
                        .withCreateWalletTriggerScript("createWalletTriggerScript1")
                        .withDepositIntoWalletDoneTriggerScript("depositIntoWalletDoneTriggerScript1")
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
                        .withDescription("GoldPool 1")
                        .withCreateWalletDoneTriggerScript("createWalletDoneTriggerScript1")
                        .withDepositIntoWalletTriggerScript("depositIntoWalletTriggerScript1")
                        .withServiceClass("gold1.micro")
                        .withWithdrawFromWalletDoneTriggerScript("withdrawFromWalletDoneTriggerScript1")
                        .withGoldPoolName("gold-pool-0001")
                        .withWithdrawFromWalletTriggerScript("withdrawFromWalletTriggerScript1")
                        .withCreateWalletTriggerScript("createWalletTriggerScript1")
                        .withDepositIntoWalletDoneTriggerScript("@")
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
                        .withDescription("GoldPool 1")
                        .withCreateWalletDoneTriggerScript("createWalletDoneTriggerScript1")
                        .withDepositIntoWalletTriggerScript("depositIntoWalletTriggerScript1")
                        .withServiceClass("gold1.micro")
                        .withWithdrawFromWalletDoneTriggerScript("withdrawFromWalletDoneTriggerScript1")
                        .withGoldPoolName("gold-pool-0001")
                        .withWithdrawFromWalletTriggerScript("withdrawFromWalletTriggerScript1")
                        .withCreateWalletTriggerScript("createWalletTriggerScript1")
                        .withDepositIntoWalletDoneTriggerScript("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
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
                        .withDescription("GoldPool 1")
                        .withCreateWalletDoneTriggerScript("createWalletDoneTriggerScript1")
                        .withDepositIntoWalletTriggerScript("depositIntoWalletTriggerScript1")
                        .withServiceClass("gold1.micro")
                        .withWithdrawFromWalletDoneTriggerScript("withdrawFromWalletDoneTriggerScript1")
                        .withGoldPoolName("gold-pool-0001")
                        .withWithdrawFromWalletTriggerScript("@")
                        .withCreateWalletTriggerScript("createWalletTriggerScript1")
                        .withDepositIntoWalletDoneTriggerScript("depositIntoWalletDoneTriggerScript1")
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
                        .withDescription("GoldPool 1")
                        .withCreateWalletDoneTriggerScript("createWalletDoneTriggerScript1")
                        .withDepositIntoWalletTriggerScript("depositIntoWalletTriggerScript1")
                        .withServiceClass("gold1.micro")
                        .withWithdrawFromWalletDoneTriggerScript("withdrawFromWalletDoneTriggerScript1")
                        .withGoldPoolName("gold-pool-0001")
                        .withWithdrawFromWalletTriggerScript("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withCreateWalletTriggerScript("createWalletTriggerScript1")
                        .withDepositIntoWalletDoneTriggerScript("depositIntoWalletDoneTriggerScript1")
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
                        .withDescription("GoldPool 1")
                        .withCreateWalletDoneTriggerScript("createWalletDoneTriggerScript1")
                        .withDepositIntoWalletTriggerScript("depositIntoWalletTriggerScript1")
                        .withServiceClass("gold1.micro")
                        .withWithdrawFromWalletDoneTriggerScript("@")
                        .withGoldPoolName("gold-pool-0001")
                        .withWithdrawFromWalletTriggerScript("withdrawFromWalletTriggerScript1")
                        .withCreateWalletTriggerScript("createWalletTriggerScript1")
                        .withDepositIntoWalletDoneTriggerScript("depositIntoWalletDoneTriggerScript1")
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
                        .withDescription("GoldPool 1")
                        .withCreateWalletDoneTriggerScript("createWalletDoneTriggerScript1")
                        .withDepositIntoWalletTriggerScript("depositIntoWalletTriggerScript1")
                        .withServiceClass("gold1.micro")
                        .withWithdrawFromWalletDoneTriggerScript("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withGoldPoolName("gold-pool-0001")
                        .withWithdrawFromWalletTriggerScript("withdrawFromWalletTriggerScript1")
                        .withCreateWalletTriggerScript("createWalletTriggerScript1")
                        .withDepositIntoWalletDoneTriggerScript("depositIntoWalletDoneTriggerScript1")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "withdrawFromWalletDoneTriggerScript");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.withdrawFromWalletDoneTriggerScript.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testDescribeGoldPoolLimitInvalid() {
        try {
            {
                client.describeGoldPool(
                    new io.gs2.gold.control.DescribeGoldPoolRequest()
                        .withLimit(Integer.valueOf(-1))
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "limit");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.limit.error.invalid");
            
        }
    }
    
    
    @Test
    public void testDeleteGoldPoolGoldPoolNameNone() {
        try {
            {
                client.deleteGoldPool(
                    new io.gs2.gold.control.DeleteGoldPoolRequest()
                        .withGoldPoolName(null)
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
    public void testDeleteGoldPoolGoldPoolNameEmpty() {
        try {
            {
                client.deleteGoldPool(
                    new io.gs2.gold.control.DeleteGoldPoolRequest()
                        .withGoldPoolName("")
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
    public void testGetGoldPoolStatusGoldPoolNameNone() {
        try {
            {
                client.getGoldPoolStatus(
                    new io.gs2.gold.control.GetGoldPoolStatusRequest()
                        .withGoldPoolName(null)
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
    public void testGetGoldPoolStatusGoldPoolNameEmpty() {
        try {
            {
                client.getGoldPoolStatus(
                    new io.gs2.gold.control.GetGoldPoolStatusRequest()
                        .withGoldPoolName("")
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
    public void testGetGoldPoolStatusGoldPoolNameInvalid() {
        try {
            {
                client.getGoldPoolStatus(
                    new io.gs2.gold.control.GetGoldPoolStatusRequest()
                        .withGoldPoolName("@")
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
    public void testGetGoldPoolStatusGoldPoolNameTooLong() {
        try {
            {
                client.getGoldPoolStatus(
                    new io.gs2.gold.control.GetGoldPoolStatusRequest()
                        .withGoldPoolName("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.goldPoolName.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testCreateGoldPoolNameNone() {
        try {
            {
                client.createGoldPool(
                    new io.gs2.gold.control.CreateGoldPoolRequest()
                        .withServiceClass("gold1.nano")
                        .withName(null)
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "name");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.name.error.require");
            
        }
    }
    
    
    @Test
    public void testCreateGoldPoolNameEmpty() {
        try {
            {
                client.createGoldPool(
                    new io.gs2.gold.control.CreateGoldPoolRequest()
                        .withServiceClass("gold1.nano")
                        .withName("")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "name");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.name.error.require");
            
        }
    }
    
    
    @Test
    public void testCreateGoldPoolNameInvalid() {
        try {
            {
                client.createGoldPool(
                    new io.gs2.gold.control.CreateGoldPoolRequest()
                        .withServiceClass("gold1.nano")
                        .withName("@")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "name");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.name.error.invalid");
            
        }
    }
    
    
    @Test
    public void testCreateGoldPoolDescriptionTooLong() {
        try {
            {
                client.createGoldPool(
                    new io.gs2.gold.control.CreateGoldPoolRequest()
                        .withServiceClass("gold1.nano")
                        .withName("gold-pool-0001")
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
    public void testCreateGoldPoolServiceClassNone() {
        try {
            {
                client.createGoldPool(
                    new io.gs2.gold.control.CreateGoldPoolRequest()
                        .withServiceClass(null)
                        .withName("gold-pool-0001")
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
    public void testCreateGoldPoolServiceClassEmpty() {
        try {
            {
                client.createGoldPool(
                    new io.gs2.gold.control.CreateGoldPoolRequest()
                        .withServiceClass("")
                        .withName("gold-pool-0001")
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
    public void testCreateGoldPoolServiceClassInvalid() {
        try {
            {
                client.createGoldPool(
                    new io.gs2.gold.control.CreateGoldPoolRequest()
                        .withServiceClass("invalid")
                        .withName("gold-pool-0001")
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
    public void testCreateGoldPoolCreateWalletTriggerScriptInvalid() {
        try {
            {
                client.createGoldPool(
                    new io.gs2.gold.control.CreateGoldPoolRequest()
                        .withCreateWalletTriggerScript("@")
                        .withServiceClass("gold1.nano")
                        .withName("gold-pool-0001")
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
    public void testCreateGoldPoolCreateWalletTriggerScriptTooLong() {
        try {
            {
                client.createGoldPool(
                    new io.gs2.gold.control.CreateGoldPoolRequest()
                        .withCreateWalletTriggerScript("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withServiceClass("gold1.nano")
                        .withName("gold-pool-0001")
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
    public void testCreateGoldPoolCreateWalletDoneTriggerScriptInvalid() {
        try {
            {
                client.createGoldPool(
                    new io.gs2.gold.control.CreateGoldPoolRequest()
                        .withCreateWalletDoneTriggerScript("@")
                        .withServiceClass("gold1.nano")
                        .withName("gold-pool-0001")
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
    public void testCreateGoldPoolCreateWalletDoneTriggerScriptTooLong() {
        try {
            {
                client.createGoldPool(
                    new io.gs2.gold.control.CreateGoldPoolRequest()
                        .withCreateWalletDoneTriggerScript("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withServiceClass("gold1.nano")
                        .withName("gold-pool-0001")
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
    public void testCreateGoldPoolDepositIntoWalletTriggerScriptInvalid() {
        try {
            {
                client.createGoldPool(
                    new io.gs2.gold.control.CreateGoldPoolRequest()
                        .withDepositIntoWalletTriggerScript("@")
                        .withServiceClass("gold1.nano")
                        .withName("gold-pool-0001")
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
    public void testCreateGoldPoolDepositIntoWalletTriggerScriptTooLong() {
        try {
            {
                client.createGoldPool(
                    new io.gs2.gold.control.CreateGoldPoolRequest()
                        .withDepositIntoWalletTriggerScript("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withServiceClass("gold1.nano")
                        .withName("gold-pool-0001")
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
    public void testCreateGoldPoolDepositIntoWalletDoneTriggerScriptInvalid() {
        try {
            {
                client.createGoldPool(
                    new io.gs2.gold.control.CreateGoldPoolRequest()
                        .withDepositIntoWalletDoneTriggerScript("@")
                        .withServiceClass("gold1.nano")
                        .withName("gold-pool-0001")
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
    public void testCreateGoldPoolDepositIntoWalletDoneTriggerScriptTooLong() {
        try {
            {
                client.createGoldPool(
                    new io.gs2.gold.control.CreateGoldPoolRequest()
                        .withDepositIntoWalletDoneTriggerScript("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .withServiceClass("gold1.nano")
                        .withName("gold-pool-0001")
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
    public void testCreateGoldPoolWithdrawFromWalletTriggerScriptInvalid() {
        try {
            {
                client.createGoldPool(
                    new io.gs2.gold.control.CreateGoldPoolRequest()
                        .withServiceClass("gold1.nano")
                        .withName("gold-pool-0001")
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
    public void testCreateGoldPoolWithdrawFromWalletTriggerScriptTooLong() {
        try {
            {
                client.createGoldPool(
                    new io.gs2.gold.control.CreateGoldPoolRequest()
                        .withServiceClass("gold1.nano")
                        .withName("gold-pool-0001")
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
    public void testCreateGoldPoolWithdrawFromWalletDoneTriggerScriptInvalid() {
        try {
            {
                client.createGoldPool(
                    new io.gs2.gold.control.CreateGoldPoolRequest()
                        .withServiceClass("gold1.nano")
                        .withName("gold-pool-0001")
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
    public void testCreateGoldPoolWithdrawFromWalletDoneTriggerScriptTooLong() {
        try {
            {
                client.createGoldPool(
                    new io.gs2.gold.control.CreateGoldPoolRequest()
                        .withServiceClass("gold1.nano")
                        .withName("gold-pool-0001")
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
    
    
    @Test
    public void testDescribeGoldPoolByOwnerIdOwnerIdNone() {
        try {
            {
                client.describeGoldPoolByOwnerId(
                    new io.gs2.gold.control.DescribeGoldPoolByOwnerIdRequest()
                        .withOwnerId(null)
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "ownerId");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.ownerId.error.require");
            
        }
    }
    
    
    @Test
    public void testDescribeGoldPoolByOwnerIdOwnerIdEmpty() {
        try {
            {
                client.describeGoldPoolByOwnerId(
                    new io.gs2.gold.control.DescribeGoldPoolByOwnerIdRequest()
                        .withOwnerId("")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "ownerId");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.ownerId.error.require");
            
        }
    }
    
    
    @Test
    public void testDescribeGoldPoolByOwnerIdOwnerIdInvalid() {
        try {
            {
                client.describeGoldPoolByOwnerId(
                    new io.gs2.gold.control.DescribeGoldPoolByOwnerIdRequest()
                        .withOwnerId("@")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "ownerId");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.ownerId.error.invalid");
            
        }
    }
    
    
    @Test
    public void testDescribeGoldPoolByOwnerIdOwnerIdTooLong() {
        try {
            {
                client.describeGoldPoolByOwnerId(
                    new io.gs2.gold.control.DescribeGoldPoolByOwnerIdRequest()
                        .withOwnerId("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "ownerId");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.ownerId.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testDescribeGoldPoolByOwnerIdLimitInvalid() {
        try {
            {
                client.describeGoldPoolByOwnerId(
                    new io.gs2.gold.control.DescribeGoldPoolByOwnerIdRequest()
                        .withOwnerId(CLIENT_ID)
                        .withLimit(Integer.valueOf(-1))
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "limit");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.limit.error.invalid");
            
        }
    }
    
    
    @Test
    public void testGetGoldPoolGoldPoolNameNone() {
        try {
            {
                client.getGoldPool(
                    new io.gs2.gold.control.GetGoldPoolRequest()
                        .withGoldPoolName(null)
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
    public void testGetGoldPoolGoldPoolNameEmpty() {
        try {
            {
                client.getGoldPool(
                    new io.gs2.gold.control.GetGoldPoolRequest()
                        .withGoldPoolName("")
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
    public void testGetGoldPoolGoldPoolNameInvalid() {
        try {
            {
                client.getGoldPool(
                    new io.gs2.gold.control.GetGoldPoolRequest()
                        .withGoldPoolName("@")
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
    public void testGetGoldPoolGoldPoolNameTooLong() {
        try {
            {
                client.getGoldPool(
                    new io.gs2.gold.control.GetGoldPoolRequest()
                        .withGoldPoolName("129aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                );
            }
            assertTrue(false);
        }catch (BadRequestException e) {
            assertEquals(1, e.getErrors().size());
            
            assertEquals(e.getErrors().get(0).getComponent(), "goldPoolName");
            
            assertEquals(e.getErrors().get(0).getMessage(), "gold.goldPool.goldPoolName.error.tooLong");
            
        }
    }
    
    
    @Test
    public void testBasic() {
        // GoldPool を生成
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
        // GoldPool をもう一つ生成
        {
            io.gs2.gold.control.CreateGoldPoolResult result = client.createGoldPool(
                new io.gs2.gold.control.CreateGoldPoolRequest()
                    .withWithdrawFromWalletDoneTriggerScript("withdrawFromWalletDoneTriggerScript")
                    .withWithdrawFromWalletTriggerScript("withdrawFromWalletTriggerScript")
                    .withName("gold-pool-0002")
                    .withCreateWalletDoneTriggerScript("createWalletDoneTriggerScript")
                    .withCreateWalletTriggerScript("createWalletTriggerScript")
                    .withDepositIntoWalletTriggerScript("depositIntoWalletTriggerScript")
                    .withServiceClass("gold1.nano")
                    .withDepositIntoWalletDoneTriggerScript("depositIntoWalletDoneTriggerScript")
                    .withDescription("GoldPool 2")
            );
            assertNotNull(result);
            assertNotNull(result.getItem());
            assertEquals(result.getItem().getOwnerId(), CLIENT_ID);
            assertEquals(result.getItem().getName(), "gold-pool-0002");
            assertEquals(result.getItem().getDescription(), "GoldPool 2");
            assertEquals(result.getItem().getServiceClass(), "gold1.nano");
            assertEquals(result.getItem().getCreateWalletTriggerScript(), "createWalletTriggerScript");
            assertEquals(result.getItem().getCreateWalletDoneTriggerScript(), "createWalletDoneTriggerScript");
            assertEquals(result.getItem().getDepositIntoWalletTriggerScript(), "depositIntoWalletTriggerScript");
            assertEquals(result.getItem().getDepositIntoWalletDoneTriggerScript(), "depositIntoWalletDoneTriggerScript");
            assertEquals(result.getItem().getWithdrawFromWalletTriggerScript(), "withdrawFromWalletTriggerScript");
            assertEquals(result.getItem().getWithdrawFromWalletDoneTriggerScript(), "withdrawFromWalletDoneTriggerScript");
            assertNotNull(result.getItem().getCreateAt());
            goldPool2 = result.getItem();
        }
        // 生成した GoldPool の ACTIVE への遷移待ち（まとめて待つことで合計の待ち時間を短縮する）
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
            status = null;
        
            int counter1 = 0;
            while (!"ACTIVE".equals(status)) {
                assertTrue(counter1 < 100000);
                ++counter1;
                {
                    io.gs2.gold.control.GetGoldPoolStatusResult result = client.getGoldPoolStatus(
                        new io.gs2.gold.control.GetGoldPoolStatusRequest()
                            .withGoldPoolName(goldPool2.getName())
                    );
                    status = result.getStatus();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {}
            }
        }
        // GoldPool を取得して内容確認
        {
            io.gs2.gold.control.GetGoldPoolResult result = client.getGoldPool(
                new io.gs2.gold.control.GetGoldPoolRequest()
                    .withGoldPoolName("gold-pool-0001")
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
        }
        // GoldPool の内容を更新
        {
            io.gs2.gold.control.UpdateGoldPoolResult result = client.updateGoldPool(
                new io.gs2.gold.control.UpdateGoldPoolRequest()
                    .withGoldPoolName("gold-pool-0001")
                    .withWithdrawFromWalletDoneTriggerScript("withdrawFromWalletDoneTriggerScript1")
                    .withDescription("GoldPool 1")
                    .withCreateWalletDoneTriggerScript("createWalletDoneTriggerScript1")
                    .withCreateWalletTriggerScript("createWalletTriggerScript1")
                    .withDepositIntoWalletTriggerScript("depositIntoWalletTriggerScript1")
                    .withServiceClass("gold1.micro")
                    .withDepositIntoWalletDoneTriggerScript("depositIntoWalletDoneTriggerScript1")
                    .withWithdrawFromWalletTriggerScript("withdrawFromWalletTriggerScript1")
            );
            assertNotNull(result);
            assertNotNull(result.getItem());
            assertEquals(result.getItem().getOwnerId(), CLIENT_ID);
            assertEquals(result.getItem().getName(), "gold-pool-0001");
            assertEquals(result.getItem().getDescription(), "GoldPool 1");
            assertEquals(result.getItem().getServiceClass(), "gold1.micro");
            assertEquals(result.getItem().getCreateWalletTriggerScript(), "createWalletTriggerScript1");
            assertEquals(result.getItem().getCreateWalletDoneTriggerScript(), "createWalletDoneTriggerScript1");
            assertEquals(result.getItem().getDepositIntoWalletTriggerScript(), "depositIntoWalletTriggerScript1");
            assertEquals(result.getItem().getDepositIntoWalletDoneTriggerScript(), "depositIntoWalletDoneTriggerScript1");
            assertEquals(result.getItem().getWithdrawFromWalletTriggerScript(), "withdrawFromWalletTriggerScript1");
            assertEquals(result.getItem().getWithdrawFromWalletDoneTriggerScript(), "withdrawFromWalletDoneTriggerScript1");
            assertNotNull(result.getItem().getCreateAt());
            goldPool1 = result.getItem();
        }
        // サービスクラスの変更は UPDATING ステータスに遷移する
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
        // GoldPool を取得して更新確認
        {
            io.gs2.gold.control.GetGoldPoolResult result = client.getGoldPool(
                new io.gs2.gold.control.GetGoldPoolRequest()
                    .withGoldPoolName("gold-pool-0001")
            );
            assertNotNull(result);
            assertNotNull(result.getItem());
            assertEquals(result.getItem().getOwnerId(), CLIENT_ID);
            assertEquals(result.getItem().getName(), "gold-pool-0001");
            assertEquals(result.getItem().getDescription(), "GoldPool 1");
            assertEquals(result.getItem().getServiceClass(), "gold1.micro");
            assertEquals(result.getItem().getCreateWalletTriggerScript(), "createWalletTriggerScript1");
            assertEquals(result.getItem().getCreateWalletDoneTriggerScript(), "createWalletDoneTriggerScript1");
            assertEquals(result.getItem().getDepositIntoWalletTriggerScript(), "depositIntoWalletTriggerScript1");
            assertEquals(result.getItem().getDepositIntoWalletDoneTriggerScript(), "depositIntoWalletDoneTriggerScript1");
            assertEquals(result.getItem().getWithdrawFromWalletTriggerScript(), "withdrawFromWalletTriggerScript1");
            assertEquals(result.getItem().getWithdrawFromWalletDoneTriggerScript(), "withdrawFromWalletDoneTriggerScript1");
            assertNotNull(result.getItem().getCreateAt());
            goldPool1 = result.getItem();
        }
        {
            io.gs2.gold.control.DescribeGoldPoolByOwnerIdResult result = client.describeGoldPoolByOwnerId(
                new io.gs2.gold.control.DescribeGoldPoolByOwnerIdRequest()
                    .withOwnerId(CLIENT_ID)
            );
            assertNotNull(result);
            assertNotNull(result.getItems());
            assertEquals(2, result.getItems().size());
            assertNull(result.getNextPageToken());
            goldPools = result.getItems();
        }
        if (goldPool1.getName().equals(goldPools.get(1).getName())) {
            GoldPool temp = goldPool1;
            goldPool1 = goldPool2;
            goldPool2 = temp;
        }
        assertEquals(goldPools.get(0).getName(), goldPool1.getName());
        assertEquals(goldPools.get(0).getDescription(), goldPool1.getDescription());
        assertEquals(goldPools.get(0).getServiceClass(), goldPool1.getServiceClass());
        assertEquals(goldPools.get(0).getCreateAt(), goldPool1.getCreateAt());
        assertEquals(goldPools.get(0).getUpdateAt(), goldPool1.getUpdateAt());
        
        assertEquals(goldPools.get(1).getName(), goldPool2.getName());
        assertEquals(goldPools.get(1).getDescription(), goldPool2.getDescription());
        assertEquals(goldPools.get(1).getServiceClass(), goldPool2.getServiceClass());
        assertEquals(goldPools.get(1).getCreateAt(), goldPool2.getCreateAt());
        assertEquals(goldPools.get(1).getUpdateAt(), goldPool2.getUpdateAt());
        
        {
            io.gs2.gold.control.DescribeGoldPoolResult result = client.describeGoldPool(
                new io.gs2.gold.control.DescribeGoldPoolRequest()
            );
            assertNotNull(result);
            assertNotNull(result.getItems());
            assertEquals(2, result.getItems().size());
            assertNull(result.getNextPageToken());
            goldPools = result.getItems();
        }
        if (goldPool1.getName().equals(goldPools.get(1).getName())) {
            GoldPool temp = goldPool1;
            goldPool1 = goldPool2;
            goldPool2 = temp;
        }
        assertEquals(goldPools.get(0).getName(), goldPool1.getName());
        assertEquals(goldPools.get(0).getDescription(), goldPool1.getDescription());
        assertEquals(goldPools.get(0).getServiceClass(), goldPool1.getServiceClass());
        assertEquals(goldPools.get(0).getCreateAt(), goldPool1.getCreateAt());
        assertEquals(goldPools.get(0).getUpdateAt(), goldPool1.getUpdateAt());
        
        assertEquals(goldPools.get(1).getName(), goldPool2.getName());
        assertEquals(goldPools.get(1).getDescription(), goldPool2.getDescription());
        assertEquals(goldPools.get(1).getServiceClass(), goldPool2.getServiceClass());
        assertEquals(goldPools.get(1).getCreateAt(), goldPool2.getCreateAt());
        assertEquals(goldPools.get(1).getUpdateAt(), goldPool2.getUpdateAt());
        
        // 1件ずつ列挙
        {
            io.gs2.gold.control.DescribeGoldPoolResult result = client.describeGoldPool(
                new io.gs2.gold.control.DescribeGoldPoolRequest()
                    .withLimit(Integer.valueOf(1))
            );
            assertNotNull(result.getItems());
            assertEquals(1, result.getItems().size());
            assertNotNull(result.getNextPageToken());
            goldPools = result.getItems();
            nextPageToken = result.getNextPageToken();
        }
        assertEquals(goldPools.get(0).getName(), goldPool1.getName());
        assertEquals(goldPools.get(0).getDescription(), goldPool1.getDescription());
        assertEquals(goldPools.get(0).getServiceClass(), goldPool1.getServiceClass());
        assertEquals(goldPools.get(0).getCreateAt(), goldPool1.getCreateAt());
        assertEquals(goldPools.get(0).getUpdateAt(), goldPool1.getUpdateAt());
        
        {
            io.gs2.gold.control.DescribeGoldPoolResult result = client.describeGoldPool(
                new io.gs2.gold.control.DescribeGoldPoolRequest()
                    .withPageToken(nextPageToken)
                    .withLimit(Integer.valueOf(10))
            );
            assertNotNull(result.getItems());
            assertEquals(1, result.getItems().size());
            assertNull(result.getNextPageToken());
            goldPools = result.getItems();
        }
        assertEquals(goldPools.get(0).getName(), goldPool2.getName());
        assertEquals(goldPools.get(0).getDescription(), goldPool2.getDescription());
        assertEquals(goldPools.get(0).getServiceClass(), goldPool2.getServiceClass());
        assertEquals(goldPools.get(0).getCreateAt(), goldPool2.getCreateAt());
        assertEquals(goldPools.get(0).getUpdateAt(), goldPool2.getUpdateAt());
        
        // GoldPool を1件ずつ削除（まとめて待つことで合計の待ち時間を短縮する）
        {
            client.deleteGoldPool(
                new io.gs2.gold.control.DeleteGoldPoolRequest()
                    .withGoldPoolName(goldPool1.getName())
            );
        }
        {
            client.deleteGoldPool(
                new io.gs2.gold.control.DeleteGoldPoolRequest()
                    .withGoldPoolName(goldPool2.getName())
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
        try {
            {
                client.getGoldPool(
                    new io.gs2.gold.control.GetGoldPoolRequest()
                        .withGoldPoolName(goldPool1.getName())
                );
            }
            assertTrue(false);
        }catch (NotFoundException e) {
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
                            .withGoldPoolName(goldPool2.getName())
                    );
                    status = result.getStatus();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {}
            }
        }
        try {
            {
                client.getGoldPool(
                    new io.gs2.gold.control.GetGoldPoolRequest()
                        .withGoldPoolName(goldPool2.getName())
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
                
                    int counter5 = 0;
                    while (!"DELETED".equals(status)) {
                        assertTrue(counter5 < 100000);
                        ++counter5;
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
                
                    int counter6 = 0;
                    while (!"ACTIVE".equals(status)) {
                        assertTrue(counter6 < 100000);
                        ++counter6;
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
                
                    int counter7 = 0;
                    while (!"DELETED".equals(status)) {
                        assertTrue(counter7 < 100000);
                        ++counter7;
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