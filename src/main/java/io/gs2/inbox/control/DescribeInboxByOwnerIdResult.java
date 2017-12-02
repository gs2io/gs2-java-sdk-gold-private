package io.gs2.inbox.control;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.gs2.inbox.model.Inbox;

/**
 * 受信ボックス一覧取得結果。
 * 
 * @author Game Server Services, Inc.
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class DescribeInboxByOwnerIdResult {

	/** 受信ボックス一覧 */
	List<Inbox> items;
	/** 次のページを取得するためのトークン */
	String nextPageToken;
	
	/**
	 * 取得した受信ボックス数を取得。
	 * 
	 * @return 取得した受信ボックス数
	 */
	public Integer getCount() {
		return items == null ? null : items.size();
	}
	
	@Deprecated
	public void setCount(Integer count){ }
	
	/**
	 * 取得した受信ボックス一覧を取得。
	 * 
	 * @return 受信ボックス一覧
	 */
	public List<Inbox> getItems() {
		return items;
	}
	
	/**
	 * 受信ボックス一覧を設定。
	 * 
	 * @param items 受信ボックス一覧
	 */
	public void setItems(List<Inbox> items) {
		this.items = items;
	}
	
	/**
	 * 次のページを取得するためのトークンを取得。
	 * 
	 * {@link DescribeInboxByOwnerIdRequest} に指定することで、次のページを取得できます。
	 * 
	 * @return トークン
	 */
	public String getNextPageToken() {
		return nextPageToken;
	}
	
	public void setNextPageToken(String nextPageToken) {
		this.nextPageToken = nextPageToken;
	}
}
