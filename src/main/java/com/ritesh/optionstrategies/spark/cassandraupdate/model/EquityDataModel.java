package com.ritesh.optionstrategies.spark.cassandraupdate.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EquityDataModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//CSV format: slIdx,stock,date,dayOfWk,O,H,L,C,prevC,totNumTrades,totTradeQty,totTradeVal
	private Integer slIdx;
	private String stock;
	private Date tradeDate;
	private String tradeDayofWk;
	private BigDecimal openVal;
	private BigDecimal highVal;
	private BigDecimal lowVal;
	private BigDecimal closeVal;
	private BigDecimal prevCloseVal;
	private Integer totNumTrades;
	private Integer totTradeQty;
	private BigDecimal totTradeVal;
	//Derived Fields: OC_return, OC_logreturn, CO_return, CO_logreturn, CC_return, CC_logreturn
	private BigDecimal derivedOCReturn;
	private BigDecimal derivedOCLogReturn;
	private BigDecimal derivedCOReturn;
	private BigDecimal derivedCOLogReturn;
	private BigDecimal derivedCCReturn;
	private BigDecimal derivedCCLogReturn;
	
	
	public EquityDataModel(){
		//Empty Constructor
	}

	/**
	 * Field Constructor
	 * @param slIdx
	 * @param stock
	 * @param tradeDate
	 * @param tradeDayofWk
	 * @param openVal
	 * @param highVal
	 * @param lowVal
	 * @param closeVal
	 * @param prevCloseVal
	 * @param totNumTrades
	 * @param totTradeQty
	 * @param totTradeVal
	 * @param derivedOCReturn
	 * @param derivedOCLogReturn
	 * @param derivedCOReturn
	 * @param derivedCOLogReturn
	 * @param derivedCCReturn
	 * @param derivedCCLogReturn
	 */
	public EquityDataModel(Integer slIdx, String stock, Date tradeDate, String tradeDayofWk, BigDecimal openVal,
			BigDecimal highVal, BigDecimal lowVal, BigDecimal closeVal, BigDecimal prevCloseVal, Integer totNumTrades,
			Integer totTradeQty, BigDecimal totTradeVal, BigDecimal derivedOCReturn, BigDecimal derivedOCLogReturn,
			BigDecimal derivedCOReturn, BigDecimal derivedCOLogReturn, BigDecimal derivedCCReturn,
			BigDecimal derivedCCLogReturn) {
		super();
		this.slIdx = slIdx;
		this.stock = stock;
		this.tradeDate = tradeDate;
		this.tradeDayofWk = tradeDayofWk;
		this.openVal = openVal;
		this.highVal = highVal;
		this.lowVal = lowVal;
		this.closeVal = closeVal;
		this.prevCloseVal = prevCloseVal;
		this.totNumTrades = totNumTrades;
		this.totTradeQty = totTradeQty;
		this.totTradeVal = totTradeVal;
		this.derivedOCReturn = derivedOCReturn;
		this.derivedOCLogReturn = derivedOCLogReturn;
		this.derivedCOReturn = derivedCOReturn;
		this.derivedCOLogReturn = derivedCOLogReturn;
		this.derivedCCReturn = derivedCCReturn;
		this.derivedCCLogReturn = derivedCCLogReturn;
	}

	//Generate getter setter
	public Integer getSlIdx() {
		return slIdx;
	}

	public void setSlIdx(Integer slIdx) {
		this.slIdx = slIdx;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
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

	public Integer getTotNumTrades() {
		return totNumTrades;
	}

	public void setTotNumTrades(Integer totNumTrades) {
		this.totNumTrades = totNumTrades;
	}

	public Integer getTotTradeQty() {
		return totTradeQty;
	}

	public void setTotTradeQty(Integer totTradeQty) {
		this.totTradeQty = totTradeQty;
	}

	public BigDecimal getTotTradeVal() {
		return totTradeVal;
	}

	public void setTotTradeVal(BigDecimal totTradeVal) {
		this.totTradeVal = totTradeVal;
	}

	public BigDecimal getDerivedOCReturn() {
		return derivedOCReturn;
	}

	public void setDerivedOCReturn(BigDecimal derivedOCReturn) {
		this.derivedOCReturn = derivedOCReturn;
	}

	public BigDecimal getDerivedOCLogReturn() {
		return derivedOCLogReturn;
	}

	public void setDerivedOCLogReturn(BigDecimal derivedOCLogReturn) {
		this.derivedOCLogReturn = derivedOCLogReturn;
	}

	public BigDecimal getDerivedCOReturn() {
		return derivedCOReturn;
	}

	public void setDerivedCOReturn(BigDecimal derivedCOReturn) {
		this.derivedCOReturn = derivedCOReturn;
	}

	public BigDecimal getDerivedCOLogReturn() {
		return derivedCOLogReturn;
	}

	public void setDerivedCOLogReturn(BigDecimal derivedCOLogReturn) {
		this.derivedCOLogReturn = derivedCOLogReturn;
	}

	public BigDecimal getDerivedCCReturn() {
		return derivedCCReturn;
	}

	public void setDerivedCCReturn(BigDecimal derivedCCReturn) {
		this.derivedCCReturn = derivedCCReturn;
	}

	public BigDecimal getDerivedCCLogReturn() {
		return derivedCCLogReturn;
	}

	public void setDerivedCCLogReturn(BigDecimal derivedCCLogReturn) {
		this.derivedCCLogReturn = derivedCCLogReturn;
	}
	
	/**
	 * ToString method
	 */
	@Override
	public String toString() {
		return "EquityDataModel [slIdx=" + slIdx + ", stock=" + stock + ", tradeDate=" + tradeDate + ", tradeDayofWk="
				+ tradeDayofWk + ", openVal=" + openVal + ", highVal=" + highVal + ", lowVal=" + lowVal + ", closeVal="
				+ closeVal + ", prevCloseVal=" + prevCloseVal + ", totNumTrades=" + totNumTrades + ", totTradeQty="
				+ totTradeQty + ", totTradeVal=" + totTradeVal + ", derivedOCReturn=" + derivedOCReturn
				+ ", derivedOCLogReturn=" + derivedOCLogReturn + ", derivedCOReturn=" + derivedCOReturn
				+ ", derivedCOLogReturn=" + derivedCOLogReturn + ", derivedCCReturn=" + derivedCCReturn
				+ ", derivedCCLogReturn=" + derivedCCLogReturn + "]";
	}

	/**
	 * Columns for Equity table
	 * @return
	 */
	public static List<String> columns() {
        List<String> columns = new ArrayList<String>();
        columns.add("slidx");
        columns.add("stock");
        columns.add("tradedate");
        columns.add("tradedayofwk");
        columns.add("openval");
        columns.add("highval");
        columns.add("lowval");
        columns.add("closeval");
        columns.add("prevcloseval");
        columns.add("totnumtrades");
        columns.add("tottradeqty");
        columns.add("tottradeval");
        columns.add("ocreturn");
        columns.add("oclogreturn");
        columns.add("coreturn");
        columns.add("cologreturn");
        columns.add("ccreturn");
        columns.add("cclogreturn");        
        return columns;
    }
}
