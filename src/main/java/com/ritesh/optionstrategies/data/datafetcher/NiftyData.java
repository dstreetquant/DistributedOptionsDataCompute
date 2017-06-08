package com.ritesh.optionstrategies.data.datafetcher;

import java.math.BigDecimal;

public class NiftyData {
	private Integer slIdx;
	private String tradeDate;
	private String tradeDayofWk;
	private BigDecimal openVal;
	private BigDecimal highVal;
	private BigDecimal lowVal;
	private BigDecimal closeVal;
	private BigDecimal lastCloseVal;
	private Long sharesTraded;
	private BigDecimal turnOverInCr;
	
	public Integer getSlIdx() {
		return slIdx;
	}
	public void setSlIdx(Integer slIdx) {
		this.slIdx = slIdx;
	}
	public String getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}
	public String getTradeDayofWk() {
		return tradeDayofWk;
	}
	public void setTradeDayofWk(String tradeDayofWk) {
		this.tradeDayofWk = tradeDayofWk;
	}
	public BigDecimal getOpenVal() {
		return openVal;
	}
	public void setOpenVal(BigDecimal openVal) {
		this.openVal = openVal;
	}
	public BigDecimal getHighVal() {
		return highVal;
	}
	public void setHighVal(BigDecimal highVal) {
		this.highVal = highVal;
	}
	public BigDecimal getLowVal() {
		return lowVal;
	}
	public void setLowVal(BigDecimal lowVal) {
		this.lowVal = lowVal;
	}
	public BigDecimal getCloseVal() {
		return closeVal;
	}
	public void setCloseVal(BigDecimal closeVal) {
		this.closeVal = closeVal;
	}
	public BigDecimal getLastCloseVal() {
		return lastCloseVal;
	}
	public void setLastCloseVal(BigDecimal lastCloseVal) {
		this.lastCloseVal = lastCloseVal;
	}
	public Long getSharesTraded() {
		return sharesTraded;
	}
	public void setSharesTraded(Long sharesTraded) {
		this.sharesTraded = sharesTraded;
	}
	public BigDecimal getTurnOverInCr() {
		return turnOverInCr;
	}
	public void setTurnOverInCr(BigDecimal turnOverInCr) {
		this.turnOverInCr = turnOverInCr;
	}
}
