package io.gs2.gold.control;

import io.gs2.control.Gs2BasicRequest;
import io.gs2.gold.Gs2Gold;

/**
 * ゴールド内のウォレット一覧の取得リクエスト。
 * 
 * @author Game Server Services, Inc.
 *
 */
@SuppressWarnings("serial")
public class DescribeAllWalletRequest extends Gs2BasicRequest<DescribeAllWalletRequest> {

	public static class Constant extends Gs2Gold.Constant {
		public static final String FUNCTION = "DescribeWallet";
	}

	/** ゴールドID */
	String goldId;
	/** 取得件数 */
	Integer limit;
	/** 取得開始位置トークン */
	String pageToken;

	/**
	 * ゴールドIDを取得。
	 * 
	 * @return ゴールドID
	 */
	public String getGoldId() {
		return goldId;
	}
	
	/**
	 * ゴールドIDを設定。
	 * 
	 * @param goldId ゴールドID
	 */
	public void setGoldId(String goldId) {
		this.goldId = goldId;
	}
	
	/**
	 * ゴールドIDを設定。
	 * 
	 * @param goldId ゴールドID
	 * @return this
	 */
	public DescribeAllWalletRequest withGoldId(String goldId) {
		setGoldId(goldId);
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
	public DescribeAllWalletRequest withPageToken(String pageToken) {
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
	public DescribeAllWalletRequest withLimit(Integer limit) {
		setLimit(limit);
		return this;
	}
}
