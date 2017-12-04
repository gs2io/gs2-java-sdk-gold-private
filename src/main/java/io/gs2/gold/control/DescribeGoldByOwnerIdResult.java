package io.gs2.gold.control;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.gs2.gold.model.Gold;

/**
 * ゴールド一覧取得結果。
 * 
 * @author Game Server Services, Inc.
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class DescribeGoldByOwnerIdResult {

	/** ゴールド一覧 */
	List<Gold> items;
	/** 次のページを取得するためのトークン */
	String nextPageToken;
	
	/**
	 * 取得したゴールド数を取得。
	 * 
	 * @return 取得したゴールド数
	 */
	public Integer getCount() {
		return items == null ? null : items.size();
	}
	
	@Deprecated
	public void setCount(Integer count){ }
	
	/**
	 * 取得したゴールド一覧を取得。
	 * 
	 * @return ゴールド一覧
	 */
	public List<Gold> getItems() {
		return items;
	}
	
	/**
	 * ゴールド一覧を設定。
	 * 
	 * @param items ゴールド一覧
	 */
	public void setItems(List<Gold> items) {
		this.items = items;
	}
	
	/**
	 * 次のページを取得するためのトークンを取得。
	 * 
	 * {@link DescribeGoldByOwnerIdRequest} に指定することで、次のページを取得できます。
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
