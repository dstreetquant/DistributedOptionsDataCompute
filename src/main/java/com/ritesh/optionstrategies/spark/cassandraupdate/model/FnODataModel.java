package com.ritesh.optionstrategies.spark.cassandraupdate.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FnODataModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//format: INSTRUMENT, SYMBOL, EXPIRY_DT, STRIKE_PR, OPTION_TYP, OPEN, HIGH, LOW, CLOSE, SETTLE_PR, CONTRACTS, VAL_INLAKH, OPEN_INT, CHG_IN_OI, TIMESTAMP
	private String instrumentType;
	private String symbol;
	private Date expiryDate;
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
	private Date tradeDate;
	//Derived Fields: tradeDayofWk, return, logreturn
	private String tradeDayofWk;
	private BigDecimal derivedReturn;
	private BigDecimal derivedLogReturn;
	
	public FnODataModel(){
		//Empty Constructor
	}

	/**
	 * Field Constructor
	 * @param instrumentType
	 * @param symbol
	 * @param expiryDate
	 * @param strikePrice
	 * @param optionType
	 * @param openVal
	 * @param highVal
	 * @param lowVal
	 * @param closeVal
	 * @param lastAdjustedVal
	 * @param contracts
	 * @param valInLakh
	 * @param openInterest
	 * @param changeOpenInterest
	 * @param tradeDate
	 * @param tradeDayofWk
	 * @param derivedReturn
	 * @param derivedLogReturn
	 */
	public FnODataModel(String instrumentType, String symbol, Date expiryDate, BigDecimal strikePrice,
			String optionType, BigDecimal openVal, BigDecimal highVal, BigDecimal lowVal, BigDecimal closeVal,
			BigDecimal lastAdjustedVal, BigDecimal contracts, BigDecimal valInLakh, Long openInterest,
			Long changeOpenInterest, Date tradeDate, String tradeDayofWk, BigDecimal derivedReturn,
			BigDecimal derivedLogReturn) {
		super();
		this.instrumentType = instrumentType;
		this.symbol = symbol;
		this.expiryDate = expiryDate;
		this.strikePrice = strikePrice;
		this.optionType = optionType;
		this.openVal = openVal;
		this.highVal = highVal;
		this.lowVal = lowVal;
		this.closeVal = closeVal;
		this.lastAdjustedVal = lastAdjustedVal;
		this.contracts = contracts;
		this.valInLakh = valInLakh;
		this.openInterest = openInterest;
		this.changeOpenInterest = changeOpenInterest;
		this.tradeDate = tradeDate;
		this.tradeDayofWk = tradeDayofWk;
		this.derivedReturn = derivedReturn;
		this.derivedLogReturn = derivedLogReturn;
	}

	//Getter-Setter
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

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
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

	public Date getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getTradeDayofWk() {
		return tradeDayofWk;
	}

	public void setTradeDayofWk(String tradeDayofWk) {
		this.tradeDayofWk = tradeDayofWk;
	}

	public BigDecimal getDerivedReturn() {
		return derivedReturn;
	}

	public void setDerivedReturn(BigDecimal derivedReturn) {
		this.derivedReturn = derivedReturn;
	}

	public BigDecimal getDerivedLogReturn() {
		return derivedLogReturn;
	}

	public void setDerivedLogReturn(BigDecimal derivedLogReturn) {
		this.derivedLogReturn = derivedLogReturn;
	}

	@Override
	public String toString() {
		return "FnODataModel [instrumentType=" + instrumentType + ", symbol=" + symbol + ", expiryDate=" + expiryDate
				+ ", strikePrice=" + strikePrice + ", optionType=" + optionType + ", openVal=" + openVal + ", highVal="
				+ highVal + ", lowVal=" + lowVal + ", closeVal=" + closeVal + ", lastAdjustedVal=" + lastAdjustedVal
				+ ", contracts=" + contracts + ", valInLakh=" + valInLakh + ", openInterest=" + openInterest
				+ ", changeOpenInterest=" + changeOpenInterest + ", tradeDate=" + tradeDate + ", tradeDayofWk="
				+ tradeDayofWk + ", derivedReturn=" + derivedReturn + ", derivedLogReturn=" + derivedLogReturn + "]";
	}
	
	/**
	 * Columns for FnO table
	 * @return
	 */
	public static List<String> columns() {
        List<String> columns = new ArrayList<String>();
        columns.add("instrumenttype");
        columns.add("symbol");
        columns.add("expirydate");
        columns.add("strikeprice");
        columns.add("optiontype");
        columns.add("openval");
        columns.add("highval");
        columns.add("lowval");
        columns.add("closeval");
        columns.add("lastadjustedval");
        columns.add("contracts");
        columns.add("valinlakh");        
        columns.add("openinterest");
        columns.add("changeopeninterest");
        columns.add("tradedate");
        columns.add("tradedayofwk");
        columns.add("return");
        columns.add("logreturn");
        return columns;
    }
}
