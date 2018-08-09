package io.gs2.gold;

import io.gs2.Gs2Constant;
import io.gs2.gold.control.DescribeGoldPoolByOwnerIdRequest;
import io.gs2.gold.control.DescribeGoldPoolByOwnerIdResult;
import io.gs2.model.IGs2Credential;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * GS2 Wallet API クライアント
 * 
 * @author Game Server Services, Inc.
 *
 */
public class Gs2GoldPrivateClient extends Gs2GoldClient {
	
	/**
	 * コンストラクタ。
	 * 
	 * @param credential 認証情報
	 */
	public Gs2GoldPrivateClient(IGs2Credential credential) {
		super(credential);
	}

	/**
	 * 特定アカウントのゴールド一覧を取得
	 *
	 * @param request リクエストパラメータ
	 * @return ゴールド一覧
	 */
	public DescribeGoldPoolByOwnerIdResult describeGoldPoolByOwnerId(DescribeGoldPoolByOwnerIdRequest request) {
		String url = Gs2Constant.ENDPOINT_HOST + "/system/" + request.getOwnerId() + "/goldPool";
		List<NameValuePair> queryString = new ArrayList<>();
		if(request.getLimit() != null) queryString.add(new BasicNameValuePair("limit", String.valueOf(request.getLimit())));
		if(request.getPageToken() != null) queryString.add(new BasicNameValuePair("pageToken", request.getPageToken()));
		if(queryString.size() > 0) {
			url += "?" + URLEncodedUtils.format(queryString, "UTF-8");
		}
		HttpGet get = createHttpGet(
				url,
				credential,
				ENDPOINT,
				DescribeGoldPoolByOwnerIdRequest.Constant.MODULE,
				DescribeGoldPoolByOwnerIdRequest.Constant.FUNCTION);
		return doRequest(get, DescribeGoldPoolByOwnerIdResult.class);
	}

}
