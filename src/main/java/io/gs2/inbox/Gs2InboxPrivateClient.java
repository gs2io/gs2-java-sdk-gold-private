package io.gs2.inbox;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import io.gs2.Gs2Constant;
import io.gs2.inbox.control.DescribeAllMessageRequest;
import io.gs2.inbox.control.DescribeAllMessageResult;
import io.gs2.inbox.control.DescribeInboxByOwnerIdRequest;
import io.gs2.inbox.control.DescribeInboxByOwnerIdResult;
import io.gs2.model.IGs2Credential;

/**
 * GS2 Message API クライアント
 * 
 * @author Game Server Services, Inc.
 *
 */
public class Gs2InboxPrivateClient extends Gs2InboxClient {
	
	/**
	 * コンストラクタ。
	 * 
	 * @param credential 認証情報
	 */
	public Gs2InboxPrivateClient(IGs2Credential credential) {
		super(credential);
	}

	/**
	 * 特定アカウントの受信ボックス一覧を取得
	 * 
	 * @param request リクエストパラメータ
	 * @return 受信ボックス一覧
	 */
	public DescribeInboxByOwnerIdResult describeInboxByOwnerId(DescribeInboxByOwnerIdRequest request) {
		String url = Gs2Constant.ENDPOINT_HOST + "/system/" + request.getOwnerId() + "/inbox";
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
				DescribeInboxByOwnerIdRequest.Constant.MODULE, 
				DescribeInboxByOwnerIdRequest.Constant.FUNCTION);
		return doRequest(get, DescribeInboxByOwnerIdResult.class);
	}

	/**
	 * 受信ボックス内のメッセージ一覧を取得
	 * 
	 * @param request リクエストパラメータ
	 * @return メッセージ一覧
	 */
	public DescribeAllMessageResult describeAllMessage(DescribeAllMessageRequest request) {
		String url = Gs2Constant.ENDPOINT_HOST + "/system/inbox/" + request.getInboxId() + "/message";
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
				DescribeAllMessageRequest.Constant.MODULE, 
				DescribeAllMessageRequest.Constant.FUNCTION);
		return doRequest(get, DescribeAllMessageResult.class);
	}

}
