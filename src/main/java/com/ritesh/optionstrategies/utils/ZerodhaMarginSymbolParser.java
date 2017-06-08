package com.ritesh.optionstrategies.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ZerodhaMarginSymbolParser {

	// Index csv files
	private static final String MARGIN_CSV_FILE = "D:\\RiteshComputer\\Trading\\Zerodha\\EquityMargin.csv";

	// JDBC driver name and database URL
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost/StocksDB";

	// Database credentials
	private static final String USER = "root";
	private static final String PASS = "";
	
	// DB connection
	private Connection conn = null;

	/**
	 * Constructor
	 */
	public ZerodhaMarginSymbolParser() {		
		//Empty
	}
	
	/**
	 * Inner Class for Stock symbol Data
	 */
	private class StockSymbolData{
		private String marginEquity;
		private int CNC;
		private int MIS;
		private String nSEStockCode;
		private String zerodhaCode;
		private String googleCode;
		private String yahooCode;
		private String isLiquid;
		private String remarks;
		
		public String getMarginEquity() {
			return marginEquity;
		}
		public void setMarginEquity(String marginEquity) {
			this.marginEquity = marginEquity;
		}
		public int getCNC() {
			return CNC;
		}
		public void setCNC(int cNC) {
			CNC = cNC;
		}
		public int getMIS() {
			return MIS;
		}
		public void setMIS(int mIS) {
			MIS = mIS;
		}
		public String getnSEStockCode() {
			return nSEStockCode;
		}
		public void setnSEStockCode(String nSEStockCode) {
			this.nSEStockCode = nSEStockCode;
		}
		public String getZerodhaCode() {
			return zerodhaCode;
		}
		public void setZerodhaCode(String zerodhaCode) {
			this.zerodhaCode = zerodhaCode;
		}
		public String getGoogleCode() {
			return googleCode;
		}
		public void setGoogleCode(String googleCode) {
			this.googleCode = googleCode;
		}
		public String getYahooCode() {
			return yahooCode;
		}
		public void setYahooCode(String yahooCode) {
			this.yahooCode = yahooCode;
		}
		public String getIsLiquid() {
			return isLiquid;
		}
		public void setIsLiquid(String isLiquid) {
			this.isLiquid = isLiquid;
		}
		public String getRemarks() {
			return remarks;
		}
		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}
	}
	
	/**
	 * updateMarginData - Method to update complete Zerodha Margin Data
	 */
	public void updateMarginData(){
		initializeJDBCConn();
		storeData();
		closeJDBCConn();
	}
	
	
	/**
	 * storeData - method to store data
	 * @param file
	 * @param indexName
	 */
	private void storeData() {
		String dataLine;
		BufferedReader reader;
		
		List<StockSymbolData> stockSymList = new ArrayList<StockSymbolData>();

		try {
			// read the file
			reader = new BufferedReader(new FileReader(new File(MARGIN_CSV_FILE)));
			// first line is a header line, skip that line
			reader.readLine();

			// read each line from second line and parse
			while ((dataLine = reader.readLine()) != null) {
				// split the line on basis of ","
				String[] dataSplit = dataLine.split(",");
				//Prepare the StockSymbolData object
				StockSymbolData symData = new StockSymbolData();
				symData.setMarginEquity(dataSplit[1]);
				symData.setCNC(Integer.parseInt(dataSplit[2].replace("x", "")));
				symData.setMIS(Integer.parseInt(dataSplit[3].replace("x", "")));
				symData.setnSEStockCode(dataSplit[4]);
				symData.setZerodhaCode(dataSplit[5]);
				symData.setGoogleCode(dataSplit[6]);
				symData.setYahooCode(dataSplit[7]);
				symData.setIsLiquid(dataSplit[8]);
				symData.setRemarks(dataSplit[9]);
				//Add to symbol list
				stockSymList.add(symData);
			}
		} catch (FileNotFoundException e) {
			System.out
					.println("IndexSymbolsParsers.storeData(): FileNotFoundException for file: " + MARGIN_CSV_FILE);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IndexSymbolsParsers.storeData(): IOException for file read: " + MARGIN_CSV_FILE);
			e.printStackTrace();
		}
		
		// insert data into DB
		insertSQLData(stockSymList);
	}
	
	/**
	 * insertSQLData - method to insert the Data to DB in batch
	 * @param stockSymList
	 */
	private void insertSQLData(List<StockSymbolData> stockSymList){
		try {
			//SQL query
			String sql = "INSERT INTO MarginTable " + 
						"(MarginEquity, CNC, MIS, NSEStockCode, ZerodhaCode, GoogleCode, YahooCode, isLiquid, Remarks) " + 
						"values(?,?,?,?,?,?,?,?,?)";
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			
			for(StockSymbolData sym : stockSymList){
				//prepare statement
				prepStmt.setString(1, sym.getMarginEquity());
				prepStmt.setInt(2, sym.getCNC());
				prepStmt.setInt(3, sym.getMIS());
				prepStmt.setString(4, sym.getnSEStockCode());
				prepStmt.setString(5, sym.getZerodhaCode());
				prepStmt.setString(6, sym.getGoogleCode());
				prepStmt.setString(7, sym.getYahooCode());
				prepStmt.setString(8, sym.getIsLiquid());
				prepStmt.setString(9, sym.getRemarks());
				
				//add to batch
				prepStmt.addBatch();
			}
			
			//fire batch SQL execute
			prepStmt.executeBatch();
			prepStmt.close();
		} catch (SQLException e) {
			System.out.println("IndexSymbolsParsers.insertSQLData(): SQLException");
			e.printStackTrace();
		}
	}
	
	/**
	 * initializeJDBCConn - method to create and initialize JDBC connection
	 */
	private void initializeJDBCConn(){
		try {
			// Register JDBC driver
			Class.forName(JDBC_DRIVER);
			// Open the connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (ClassNotFoundException e) {
			System.out.println("IndexSymbolsParsers.initializeJDBCConn(): ClassNotFoundException: " + JDBC_DRIVER);
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("IndexSymbolsParsers.initializeJDBCConn(): SQLException on getConnection");
			e.printStackTrace();
		}
	}
	
	/**
	 * closeJDBCConn - method to close JDBC connection
	 */
	private void closeJDBCConn(){
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("IndexSymbolsParsers.closeJDBCConn(): SQLException on conn close");
				e.printStackTrace();
			}
		}
	}

	/**
	 * main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ZerodhaMarginSymbolParser symbolParser = new ZerodhaMarginSymbolParser();
		symbolParser.updateMarginData();
	}
}
