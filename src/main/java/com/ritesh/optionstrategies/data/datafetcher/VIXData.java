package com.ritesh.optionstrategies.data.datafetcher;

import java.math.BigDecimal;

public class VIXData {
	private Integer slIdx;
	private String tradeDate;
	private String tradeDayofWk;
	private BigDecimal openVal;
	private BigDecimal highVal;
	private BigDecimal lowVal;
	private BigDecimal closeVal;
	private BigDecimal prevCloseVal;
	private BigDecimal change;
	private BigDecimal percntgChange;
	
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
	public BigDecimal getPrevCloseVal() {
		return prevCloseVal;
	}
	public void setPrevCloseVal(BigDecimal prevCloseVal) {
		this.prevCloseVal = prevCloseVal;
	}
	public BigDecimal getChange() {
		return change;
	}
	public void setChange(BigDecimal change) {
		this.change = change;
	}
	public BigDecimal getPercntgChange() {
		return percntgChange;
	}
	public void setPercntgChange(BigDecimal percntgChange) {
		this.percntgChange = percntgChange;
	}
}
