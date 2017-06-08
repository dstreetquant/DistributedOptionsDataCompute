package com.ritesh.optionstrategies.data.datafetcher;

import java.math.BigDecimal;

public class DailyStockData {
	private BigDecimal openVal;
	private BigDecimal highVal;
	private BigDecimal lowVal;
	private BigDecimal closeVal;
	private BigDecimal lastAdjustedVal;
	private BigDecimal prevCloseVal;
	private int totTradeQty;
	private BigDecimal totTradeVal;
	private int totNumTrades;
	private String tradeDate;
	private String tradeDayofWk;
	
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
	public BigDecimal getLastAdjustedVal() {
		return lastAdjustedVal;
	}
	public void setLastAdjustedVal(BigDecimal lastAdjustedVal) {
		this.lastAdjustedVal = lastAdjustedVal;
	}
	public BigDecimal getPrevCloseVal() {
		return prevCloseVal;
	}
	public void setPrevCloseVal(BigDecimal prevCloseVal) {
		this.prevCloseVal = prevCloseVal;
	}
	public int getTotTradeQty() {
		return totTradeQty;
	}
	public void setTotTradeQty(int totTradeQty) {
		this.totTradeQty = totTradeQty;
	}
	public BigDecimal getTotTradeVal() {
		return totTradeVal;
	}
	public void setTotTradeVal(BigDecimal totTradeVal) {
		this.totTradeVal = totTradeVal;
	}
	public int getTotNumTrades() {
		return totNumTrades;
	}
	public void setTotNumTrades(int totNumTrades) {
		this.totNumTrades = totNumTrades;
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
}
