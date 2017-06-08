package com.ritesh.optionstrategies.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NSEIndicesListParser {
	
	//Indices Data Array
	private static final String[][] indicesListArr = {
			{"CNX_100", "No"},
			{"CNX_NIFTY_50", "No"},
			{"CNX_NIFTY_JUNIOR", "No"},
			{"LIX_15_MIDCAP", "No"},
			{"LIX_15", "No"},
			{"NIFTY_MIDCAP_50", "No"},
			{"SECTOR_CNX_AUTO", "Yes"},
			{"SECTOR_CNX_BANK", "Yes"},
			{"SECTOR_CNX_ENERGY", "Yes"},
			{"SECTOR_CNX_FINANCE", "Yes"},
			{"SECTOR_CNX_FMCG", "Yes"},
			{"SECTOR_CNX_IT", "Yes"},
			{"SECTOR_CNX_MEDIA", "Yes"},
			{"SECTOR_CNX_METAL", "Yes"},
			{"SECTOR_CNX_PHARMA", "Yes"},
			{"SECTOR_CNX_PSU_BANKS", "Yes"},
			{"SECTOR_CNX_REALTY", "Yes"}
	};

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
	public NSEIndicesListParser() {		
		//Empty
	}
	
	/**
	 * Inner Class for Indices symbol Data
	 */
	private class IndicesSymbolData{
		private String indexName;
		private String isSector;
		
		public String getIndexName() {
			return indexName;
		}
		public void setIndexName(String indexName) {
			this.indexName = indexName;
		}
		public String getIsSector() {
			return isSector;
		}
		public void setIsSector(String isSector) {
			this.isSector = isSector;
		}
	}
	
	/**
	 * updateIndexSymData - Method to update Index List Data
	 */
	public void updateIndexSymData(){
		initializeJDBCConn();
		storeData();
		closeJDBCConn();
	}
	
	
	/**
	 * storeData - method to store data
	 */
	private void storeData() {
		List<IndicesSymbolData> indicesSymList = new ArrayList<IndicesSymbolData>();
		
		for(String[] sym : indicesListArr){
			IndicesSymbolData symData = new IndicesSymbolData();
			symData.setIndexName(sym[0]);
			symData.setIsSector(sym[1]);
			indicesSymList.add(symData);
		}

		// insert data into DB
		insertSQLData(indicesSymList);
	}
	
	/**
	 * insertSQLData - method to insert the Data to DB in batch
	 * @param indicesSymList
	 */
	private void insertSQLData(List<IndicesSymbolData> indicesSymList){
		try {
			//SQL query
			String sql = "INSERT INTO IndicesTable " + 
						"(IndexName, isSector) " + 
						"values(?,?)";
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			
			for(IndicesSymbolData sym : indicesSymList){
				//prepare statement
				prepStmt.setString(1, sym.getIndexName());
				prepStmt.setString(2, sym.getIsSector());
				
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
		NSEIndicesListParser symbolParser = new NSEIndicesListParser();
		symbolParser.updateIndexSymData();
	}
}
