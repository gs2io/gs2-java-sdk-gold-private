package io.gs2.gold.control;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.gs2.gold.model.Wallet;

/**
 * ウォレット一覧取得結果。
 * 
 * @author Game Server Services, Inc.
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class DescribeAllWalletResult {

	/** ウォレット一覧 */
	List<Wallet> items;
	/** 次のページを取得するためのトークン */
	String nextPageToken;
	
	/**
	 * 取得したウォレット数を取得。
	 * 
	 * @return 取得したウォレット数
	 */
	public Integer getCount() {
		return items == null ? null : items.size();
	}

	@Deprecated
	public void setCount(Integer count){ }
	
	/**
	 * 取得したウォレット一覧を取得。
	 * 
	 * @return ウォレット一覧
	 */
	public List<Wallet> getItems() {
		return items;
	}
	
	/**
	 * ウォレット一覧を設定。
	 * 
	 * @param items ウォレット一覧
	 */
	public void setItems(List<Wallet> items) {
		this.items = items;
	}
	
	/**
	 * 次のページを取得するためのトークンを取得。
	 * 
	 * {@link DescribeAllWalletRequest} に指定することで、次のページを取得できます。
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
