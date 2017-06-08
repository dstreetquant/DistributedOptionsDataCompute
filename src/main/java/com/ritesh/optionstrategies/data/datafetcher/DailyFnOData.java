package com.ritesh.optionstrategies.data.datafetcher;

import java.math.BigDecimal;

public class DailyFnOData {
	private String instrumentType;
	private String symbol;
	private String expiryDate;
	private BigDecimal strikePrice;
	private String optionType;
	private BigDecimal openVal;
	private BigDecimal highVal;
	private BigDecimal lowVal;
	private BigDecimal closeVal;
	private BigDecimal lastAdjustedVal;
	private BigDecimal contracts;
	private BigDecimal valInLakh;
	private Long openInterest;
	private Long changeOpenInterest;
	private String tradeDate;
	private String tradeDayofWk;
	
	public String getInstrumentType() {
		return instrumentType;
	}
	public void setInstrumentType(String instrumentType) {
		this.instrumentType = instrumentType;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public BigDecimal getStrikePrice() {
		return strikePrice;
	}
	public void setStrikePrice(BigDecimal strikePrice) {
		this.strikePrice = strikePrice;
	}
	public String getOptionType() {
		return optionType;
	}
	public void setOptionType(String optionType) {
		this.optionType = optionType;
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
	public BigDecimal getLastAdjustedVal() {
		return lastAdjustedVal;
	}
	public void setLastAdjustedVal(BigDecimal lastAdjustedVal) {
		this.lastAdjustedVal = lastAdjustedVal;
	}
	public BigDecimal getContracts() {
		return contracts;
	}
	public void setContracts(BigDecimal contracts) {
		this.contracts = contracts;
	}
	public BigDecimal getValInLakh() {
		return valInLakh;
	}
	public void setValInLakh(BigDecimal valInLakh) {
		this.valInLakh = valInLakh;
	}
	public Long getOpenInterest() {
		return openInterest;
	}
	public void setOpenInterest(Long openInterest) {
		this.openInterest = openInterest;
	}
	public Long getChangeOpenInterest() {
		return changeOpenInterest;
	}
	public void setChangeOpenInterest(Long changeOpenInterest) {
		this.changeOpenInterest = changeOpenInterest;
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
