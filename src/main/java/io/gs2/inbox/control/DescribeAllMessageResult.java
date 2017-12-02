package io.gs2.inbox.control;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.gs2.inbox.model.Message;

/**
 * メッセージ一覧取得結果。
 * 
 * @author Game Server Services, Inc.
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class DescribeAllMessageResult {

	/** メッセージ一覧 */
	List<Message> items;
	/** 次のページを取得するためのトークン */
	String nextPageToken;
	
	/**
	 * 取得したメッセージ数を取得。
	 * 
	 * @return 取得したメッセージ数
	 */
	public Integer getCount() {
		return items == null ? null : items.size();
	}

	@Deprecated
	public void setCount(Integer count){ }
	
	/**
	 * 取得したメッセージ一覧を取得。
	 * 
	 * @return メッセージ一覧
	 */
	public List<Message> getItems() {
		return items;
	}
	
	/**
	 * メッセージ一覧を設定。
	 * 
	 * @param items メッセージ一覧
	 */
	public void setItems(List<Message> items) {
		this.items = items;
	}
	
	/**
	 * 次のページを取得するためのトークンを取得。
	 * 
	 * {@link DescribeAllMessageRequest} に指定することで、次のページを取得できます。
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
