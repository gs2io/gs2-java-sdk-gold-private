package io.gs2.gold.control;

import io.gs2.control.Gs2BasicRequest;
import io.gs2.gold.Gs2Gold;

/**
 * ゴールド一覧の取得リクエスト。
 * 
 * @author Game Server Services, Inc.
 *
 */
@SuppressWarnings("serial")
public class DescribeGoldPoolByOwnerIdRequest extends Gs2BasicRequest<DescribeGoldPoolByOwnerIdRequest> {

	public static class Constant extends Gs2Gold.Constant {
		public static final String FUNCTION = "DescribeGold";
	}

	/** オーナーID */
	String ownerId;
	/** 取得開始位置トークン */
	String pageToken;
	/** 取得件数 */
	Integer limit;

	/**
	 * オーナーIDを取得。
	 * 
	 * @return オーナーID
	 */
	public String getOwnerId() {
		return ownerId;
	}	
	
	/**
	 * オーナーIDを設定。
	 * 
	 * @param ownerId オーナーID
	 */
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
	/**
	 * オーナーIDを設定。
	 * 
	 * @param ownerId オーナーID
	 * @return this
	 */
	public DescribeGoldPoolByOwnerIdRequest withOwnerId(String ownerId) {
		setOwnerId(ownerId);
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
	public DescribeGoldPoolByOwnerIdRequest withPageToken(String pageToken) {
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
	 * @param count 取得件数
	 */
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	
	/**
	 * 取得件数を設定。
	 * 
	 * @param count 取得件数
	 * @return this
	 */
	public DescribeGoldPoolByOwnerIdRequest withLimit(Integer limit) {
		setLimit(limit);
		return this;
	}
}
