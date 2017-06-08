package com.ritesh.optionstrategies.spark.cassandraupdate.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TradingDateDataModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer dateSlId;
	private Date date;
	
	public TradingDateDataModel(){
		//Empty Constructor
	}

	/**
	 * Field Constructor
	 * @param dateSlId
	 * @param date
	 */
	public TradingDateDataModel(Integer dateSlId, Date date) {
		super();
		this.dateSlId = dateSlId;
		this.date = date;
	}

	//Getter-Setter
	public Integer getDateSlId() {
		return dateSlId;
	}

	public void setDateSlId(Integer dateSlId) {
		this.dateSlId = dateSlId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "TradingDateDataModel [dateSlId=" + dateSlId + ", date=" + date + "]";
	}
	
	/**
	 * Columns for Date table
	 * @return
	 */
	public static List<String> columns() {
        List<String> columns = new ArrayList<String>();
        columns.add("dateslid");
        columns.add("date");
        return columns;
    }
}
