package io.gs2.inbox.control;

import io.gs2.control.Gs2BasicRequest;
import io.gs2.inbox.Gs2Inbox;

/**
 * 受信ボックス内のメッセージ一覧の取得リクエスト。
 * 
 * @author Game Server Services, Inc.
 *
 */
@SuppressWarnings("serial")
public class DescribeAllMessageRequest extends Gs2BasicRequest<DescribeAllMessageRequest> {

	public static class Constant extends Gs2Inbox.Constant {
		public static final String FUNCTION = "DescribeMessage";
	}

	/** 受信ボックスID */
	String inboxId;
	/** 取得件数 */
	Integer limit;
	/** 取得開始位置トークン */
	String pageToken;

	/**
	 * 受信ボックスIDを取得。
	 * 
	 * @return 受信ボックスID
	 */
	public String getInboxId() {
		return inboxId;
	}
	
	/**
	 * 受信ボックスIDを設定。
	 * 
	 * @param inboxId 受信ボックスID
	 */
	public void setInboxId(String inboxId) {
		this.inboxId = inboxId;
	}
	
	/**
	 * 受信ボックスIDを設定。
	 * 
	 * @param inboxId 受信ボックスID
	 * @return this
	 */
	public DescribeAllMessageRequest withInboxId(String inboxId) {
		setInboxId(inboxId);
		return this;
	}

	/**
	 * 取得開始位置トークンを取得。
	 * 
	 * @return 取得開始位置トークン
	 */
	public String getPageToken() {
		return pageToken;
	}
	
	/**
	 * 取得開始位置トークンを設定。
	 * 
	 * @param pageToken 取得開始位置トークン
	 */
	public void setPageToken(String pageToken) {
		this.pageToken = pageToken;
	}
	
	/**
	 * 取得開始位置トークンを設定。
	 * 
	 * @param pageToken 取得開始位置トークン
	 * @return this
	 */
	public DescribeAllMessageRequest withPageToken(String pageToken) {
		setPageToken(pageToken);
		return this;
	}

	/**
	 * 取得件数を取得。
	 * 
	 * @return 取得件数
	 */
	public Integer getLimit() {
		return limit;
	}
	
	/**
	 * 取得件数を設定。
	 * 
	 * @param limit 取得件数
	 */
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	
	/**
	 * 取得件数を設定。
	 * 
	 * @param limit 取得件数
	 * @return this
	 */
	public DescribeAllMessageRequest withLimit(Integer limit) {
		setLimit(limit);
		return this;
	}
}
