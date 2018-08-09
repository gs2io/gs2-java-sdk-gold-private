package io.gs2.gold.control;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.gs2.gold.model.GoldPool;

import java.util.List;

/**
 * ゴールド一覧取得結果。
 * 
 * @author Game Server Services, Inc.
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class DescribeGoldPoolByOwnerIdResult {

	/** ゴールド一覧 */
	List<GoldPool> items;
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
	public List<GoldPool> getItems() {
		return items;
	}
	
	/**
	 * ゴールド一覧を設定。
	 * 
	 * @param items ゴールド一覧
	 */
	public void setItems(List<GoldPool> items) {
		this.items = items;
	}
	
	/**
	 * 次のページを取得するためのトークンを取得。
	 * 
	 * {@link DescribeGoldPoolByOwnerIdRequest} に指定することで、次のページを取得できます。
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
