package io.gs2.gold;

import io.gs2.exception.*;
import io.gs2.model.*;
import io.gs2.gold.model.*;
import io.gs2.gold.control.*;
import io.gs2.gold.Gs2GoldPrivateClient;
import io.gs2.model.BasicGs2Credential;
import io.gs2.auth.Gs2AuthClient;
import io.gs2.auth.control.*;
import io.gs2.variable.Gs2VariableClient;
import io.gs2.variable.control.*;
import io.gs2.variable.model.*;
import io.gs2.script.Gs2ScriptClient;
import io.gs2.script.control.*;
import io.gs2.script.model.*;
import io.gs2.key.Gs2KeyClient;
import io.gs2.key.control.*;
import io.gs2.key.model.*;
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
public class StampSheetTest extends TestCase {
    private static Random rand = new Random();

    private static String REGION = "ap-northeast-1";
    private static String accessToken = null;
    private static String accessToken2 = null;
    private static String CLIENT_ID = "gs2_gold_test_seeds.StampSheetTest-java";
    private static String CLIENT_SECRET = "4EiS1ZTaevLhhaBD2Q2Z1EY/0oVrUIyVgGzzNLhlhQY=";
    private static BasicGs2Credential credential = new BasicGs2Credential(CLIENT_ID, CLIENT_SECRET);
    private static Gs2AuthClient authClient = new Gs2AuthClient(credential, REGION);
    private static Gs2ScriptClient scriptClient = new Gs2ScriptClient(credential, REGION);
    private static Gs2VariableClient variableClient = new Gs2VariableClient(credential, REGION);
    private static Gs2KeyClient keyClient = new Gs2KeyClient(credential, REGION);
    private static List<GoldPool> goldPools = null;
    private static GoldPool goldPool1 = null;
    private static GoldPool goldPool2 = null;
    private static String status = null;
    private static String master = null;
    private static JSONObject sheet = null;
    private static JSONObject task = null;
    private static JSONObject value = null;
    private static Gs2GoldPrivateClient client = new Gs2GoldPrivateClient(credential);

    static {
        Gs2GoldPrivateClient.ENDPOINT = "gold-dev";
        Gs2AuthClient.ENDPOINT = "auth-dev";
        Gs2VariableClient.ENDPOINT = "variable-dev";
        Gs2ScriptClient.ENDPOINT = "script-dev";
        Gs2KeyClient.ENDPOINT = "key-dev";
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




            try {
                {
                    scriptClient.deleteScript(
                        new io.gs2.script.control.DeleteScriptRequest()
                            .withScriptName("gold-consume-stamp-sheet")
                    );
                }
                assertTrue(false);
            }catch (NotFoundException e) {
            }
            try {
                {
                    scriptClient.deleteScript(
                        new io.gs2.script.control.DeleteScriptRequest()
                            .withScriptName("gold-done-task-stamp-sheet")
                    );
                }
                assertTrue(false);
            }catch (NotFoundException e) {
            }


		} catch (BadRequestException e) {
			shutdown();
			throw e;
		}
	}

    
    @Test
    public void testWithdrawFromWalletByStampTaskTaskNone() {
    
    }
    
    
    @Test
    public void testWithdrawFromWalletByStampTaskTaskEmpty() {
    
    }
    
    
    @Test
    public void testWithdrawFromWalletByStampTaskTaskInvalid() {
    
    }
    
    
    @Test
    public void testWithdrawFromWalletByStampTaskKeyNameNone() {
    
    }
    
    
    @Test
    public void testWithdrawFromWalletByStampTaskKeyNameEmpty() {
    
    }
    
    
    @Test
    public void testWithdrawFromWalletByStampTaskKeyNameInvalid() {
    
    }
    
    
    @Test
    public void testWithdrawFromWalletByStampTaskKeyNameNotFound() {
    
    }
    
    
    @Test
    public void testWithdrawFromWalletByStampTaskTransactionIdNone() {
    
    }
    
    
    @Test
    public void testWithdrawFromWalletByStampTaskTransactionIdEmpty() {
    
    }
    
    
    @Test
    public void testWithdrawFromWalletByStampTaskAccessTokenNone() {
    
    }
    
    
    @Test
    public void testWithdrawFromWalletByStampTaskAccessTokenEmpty() {
    
    }
    
    
    @Test
    public void testWithdrawFromWalletByStampTaskAccessTokenInvalid() {
    
    }
    
    
    @Test
    public void testDepositIntoWalletByStampSheetSheetNone() {
    
    }
    
    
    @Test
    public void testDepositIntoWalletByStampSheetSheetEmpty() {
    
    }
    
    
    @Test
    public void testDepositIntoWalletByStampSheetSheetInvalid() {
    
    }
    
    
    @Test
    public void testDepositIntoWalletByStampSheetKeyNameNone() {
    
    }
    
    
    @Test
    public void testDepositIntoWalletByStampSheetKeyNameEmpty() {
    
    }
    
    
    @Test
    public void testDepositIntoWalletByStampSheetKeyNameInvalid() {
    
    }
    
    
    @Test
    public void testDepositIntoWalletByStampSheetKeyNameNotFound() {
    
    }
    
    
    @Test
    public void testDepositIntoWalletByStampSheetAccessTokenNone() {
    
    }
    
    
    @Test
    public void testDepositIntoWalletByStampSheetAccessTokenEmpty() {
    
    }
    
    
    @Test
    public void testDepositIntoWalletByStampSheetAccessTokenInvalid() {
    
    }
    
    
    @Test
    public void testGoldDepositStampSheet() {
    
    
    
    }
    
    
    @Test
    public void testGoldWithdrawStampTask() {
    
    
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