package com.ritesh.optionstrategies.spark.cassandraupdate.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NiftyDataModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//CSV format: SlID, DATE, WKDAY, OPEN, HIGH, LOW, CLOSE, PREV_CLOSE, SHARES_TRADED, TURNOVER_IN_CR
	private Integer slId;
	private Date tradeDate;
	private String tradeDayofWk;
	private BigDecimal openVal;
	private BigDecimal highVal;
	private BigDecimal lowVal;
	private BigDecimal closeVal;
	private BigDecimal prevClose;
	private Long sharesTraded;
	private BigDecimal turnOverInCr;
	//Derived Fields: OC_return, OC_logreturn, CO_return, CO_logreturn, CC_return, CC_logreturn
	private BigDecimal derivedOCReturn;
	private BigDecimal derivedOCLogReturn;
	private BigDecimal derivedCOReturn;
	private BigDecimal derivedCOLogReturn;
	private BigDecimal derivedCCReturn;
	private BigDecimal derivedCCLogReturn;
	
	public NiftyDataModel(){
		//Empty Constructor
	}

	/**
	 * Field Constructor	
	 * @param slId
	 * @param tradeDate
	 * @param tradeDayofWk
	 * @param openVal
	 * @param highVal
	 * @param lowVal
	 * @param closeVal
	 * @param prevClose
	 * @param sharesTraded
	 * @param turnOverInCr
	 * @param derivedOCReturn
	 * @param derivedOCLogReturn
	 * @param derivedCOReturn
	 * @param derivedCOLogReturn
	 * @param derivedCCReturn
	 * @param derivedCCLogReturn
	 */
	public NiftyDataModel(Integer slId, Date tradeDate, String tradeDayofWk, BigDecimal openVal, BigDecimal highVal,
			BigDecimal lowVal, BigDecimal closeVal, BigDecimal prevClose, Long sharesTraded, BigDecimal turnOverInCr,
			BigDecimal derivedOCReturn, BigDecimal derivedOCLogReturn, BigDecimal derivedCOReturn,
			BigDecimal derivedCOLogReturn, BigDecimal derivedCCReturn, BigDecimal derivedCCLogReturn) {
		super();
		this.slId = slId;
		this.tradeDate = tradeDate;
		this.tradeDayofWk = tradeDayofWk;
		this.openVal = openVal;
		this.highVal = highVal;
		this.lowVal = lowVal;
		this.closeVal = closeVal;
		this.prevClose = prevClose;
		this.sharesTraded = sharesTraded;
		this.turnOverInCr = turnOverInCr;
		this.derivedOCReturn = derivedOCReturn;
		this.derivedOCLogReturn = derivedOCLogReturn;
		this.derivedCOReturn = derivedCOReturn;
		this.derivedCOLogReturn = derivedCOLogReturn;
		this.derivedCCReturn = derivedCCReturn;
		this.derivedCCLogReturn = derivedCCLogReturn;
	}
	

	//Getter-Setter
	public Integer getSlId() {
		return slId;
	}


	public void setSlId(Integer slId) {
		this.slId = slId;
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


	public BigDecimal getPrevClose() {
		return prevClose;
	}


	public void setPrevClose(BigDecimal prevClose) {
		this.prevClose = prevClose;
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

	@Override
	public String toString() {
		return "NiftyDataModel [slId=" + slId + ", tradeDate=" + tradeDate + ", tradeDayofWk=" + tradeDayofWk
				+ ", openVal=" + openVal + ", highVal=" + highVal + ", lowVal=" + lowVal + ", closeVal=" + closeVal
				+ ", prevClose=" + prevClose + ", sharesTraded=" + sharesTraded + ", turnOverInCr=" + turnOverInCr
				+ ", derivedOCReturn=" + derivedOCReturn + ", derivedOCLogReturn=" + derivedOCLogReturn
				+ ", derivedCOReturn=" + derivedCOReturn + ", derivedCOLogReturn=" + derivedCOLogReturn
				+ ", derivedCCReturn=" + derivedCCReturn + ", derivedCCLogReturn=" + derivedCCLogReturn + "]";
	}

	/**
	 * Columns for Nifty table
	 * @return
	 */
	public static List<String> columns() {
        List<String> columns = new ArrayList<String>();
        columns.add("slid");
        columns.add("tradedate");
        columns.add("tradedayofwk");
        columns.add("openval");
        columns.add("highval");
        columns.add("lowval");
        columns.add("closeval");
        columns.add("prevclose");
        columns.add("sharestraded");
        columns.add("turnoverincr");
        columns.add("ocreturn");
        columns.add("oclogreturn");
        columns.add("coreturn");
        columns.add("cologreturn");
        columns.add("ccreturn");
        columns.add("cclogreturn");
        return columns;
    }
}
