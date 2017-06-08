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

public class IndexSymbolsParsers {

	// Index csv files
	private static final String INDEX_CSV_FOLDER = "D:\\RiteshComputer\\Trading\\NSESectorsCode\\sectorLists";

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
	public IndexSymbolsParsers() {		
		//Empty
	}
	
	/**
	 * Inner Class for Stock symbol Data
	 */
	private class StockSymbolData{
		private String companyName;
		private String industry;
		private String nSEStockCode;
		private String series;
		private String ISINCode;

		public String getCompanyName() {
			return companyName;
		}
		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}
		public String getIndustry() {
			return industry;
		}
		public void setIndustry(String industry) {
			this.industry = industry;
		}
		public String getnSEStockCode() {
			return nSEStockCode;
		}
		public void setnSEStockCode(String nSEStockCode) {
			this.nSEStockCode = nSEStockCode;
		}
		public String getSeries() {
			return series;
		}
		public void setSeries(String series) {
			this.series = series;
		}
		public String getISINCode() {
			return ISINCode;
		}
		public void setISINCode(String iSINCode) {
			ISINCode = iSINCode;
		}
	}
	
	/**
	 * updateIndexData - Method to update complete Index Data
	 */
	public void updateIndexData(){
		initializeJDBCConn();
		fetchStoreFolderData();
		closeJDBCConn();
	}
	
	/**
	 * getFolderData - Parsing folder data content
	 */
	private void fetchStoreFolderData() {
		// get list of files in BhavCopy directory
		File[] files = new File(INDEX_CSV_FOLDER).listFiles();
		System.out.println("IndexSymbolsParsers.getFolderData(): Num of files: " + files.length);

		// traverse through each file
		for (int fileCount = 0; fileCount < files.length; fileCount++) {
			String fileName = files[fileCount].getName();
			System.out.println("IndexSymbolsParsers.getFolderData(): fileName: " + fileName);

			// perform operation for each index
			switch (fileName) {
			case "CNX_100_stocks.csv":
				storeData(files[fileCount], "CNX_100");
				break;
			case "CNX_NIFTY_50_stocks.csv":
				storeData(files[fileCount], "CNX_NIFTY_50");
				break;
			case "CNX_NIFTY_JUNIOR_stocks.csv":
				storeData(files[fileCount], "CNX_NIFTY_JUNIOR");
				break;
			case "LIX_15_MIDCAP_stocks.csv":
				storeData(files[fileCount], "LIX_15_MIDCAP");
				break;
			case "LIX_15_stocks.csv":
				storeData(files[fileCount], "LIX_15");
				break;
			case "NIFTY_MIDCAP_50_stocks.csv":
				storeData(files[fileCount], "NIFTY_MIDCAP_50");
				break;
			case "SECTOR_CNX_AUTO_stocks.csv":
				storeData(files[fileCount], "SECTOR_CNX_AUTO");
				break;
			case "SECTOR_CNX_BANK_stocks.csv":
				storeData(files[fileCount], "SECTOR_CNX_BANK");
				break;
			case "SECTOR_CNX_ENERGY_stocks.csv":
				storeData(files[fileCount], "SECTOR_CNX_ENERGY");
				break;
			case "SECTOR_CNX_FINANCE_stocks.csv":
				storeData(files[fileCount], "SECTOR_CNX_FINANCE");
				break;
			case "SECTOR_CNX_FMCG_stocks.csv":
				storeData(files[fileCount], "SECTOR_CNX_FMCG");
				break;
			case "SECTOR_CNX_IT_stocks.csv":
				storeData(files[fileCount], "SECTOR_CNX_IT");
				break;
			case "SECTOR_CNX_MEDIA_stocks.csv":
				storeData(files[fileCount], "SECTOR_CNX_MEDIA");
				break;
			case "SECTOR_CNX_METAL_stocks.csv":
				storeData(files[fileCount], "SECTOR_CNX_METAL");
				break;
			case "SECTOR_CNX_PHARMA_stocks.csv":
				storeData(files[fileCount], "SECTOR_CNX_PHARMA");
				break;
			case "SECTOR_CNX_PSU_BANKS_stocks.csv":
				storeData(files[fileCount], "SECTOR_CNX_PSU_BANKS");
				break;
			case "SECTOR_CNX_REALTY_stocks.csv":
				storeData(files[fileCount], "SECTOR_CNX_REALTY");
				break;
			default:
				System.out.println("IndexSymbolsParsers.fetchStoreFolderData(): ERROR: Wrong File Name: " + fileName);
				break;
			}
		}
	}

	/**
	 * storeData - method to store data
	 * @param file
	 * @param indexName
	 */
	private void storeData(File file, String indexName) {
		String dataLine;
		BufferedReader reader;
		
		List<StockSymbolData> stockSymList = new ArrayList<StockSymbolData>();

		try {
			// read the file
			reader = new BufferedReader(new FileReader(file));
			// first line is a header line, skip that line
			reader.readLine();

			// read each line from second line and parse
			while ((dataLine = reader.readLine()) != null) {
				// split the line on basis of ","
				String[] dataSplit = dataLine.split(",");
				//Prepare the StockSymbolData object
				StockSymbolData symData = new StockSymbolData();
				symData.setCompanyName(dataSplit[0]);
				symData.setIndustry(dataSplit[1]);
				symData.setnSEStockCode(dataSplit[2]);
				symData.setSeries(dataSplit[3]);
				symData.setISINCode(dataSplit[4]);
				//Add to symbol list
				stockSymList.add(symData);
			}
		} catch (FileNotFoundException e) {
			System.out
					.println("IndexSymbolsParsers.storeData(): FileNotFoundException for file: " + file.getName());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IndexSymbolsParsers.storeData(): IOException for file read: " + file.getName());
			e.printStackTrace();
		}
		
		// insert data into DB
		insertSQLData(stockSymList, indexName);
	}
	
	/**
	 * insertSQLData - method to insert the Data to DB in batch
	 * @param stockSymList
	 */
	private void insertSQLData(List<StockSymbolData> stockSymList, String indexName){
		try {
			//SQL query
			String sql = "INSERT INTO StockSymbolTable " + 
						"(CompanyName, Industry, NSEStockCode, Series, ISINCode, IndexName) " + 
						"values(?,?,?,?,?,?)";
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			
			for(StockSymbolData sym : stockSymList){
				//prepare statement
				prepStmt.setString(1, sym.getCompanyName());
				prepStmt.setString(2, sym.getIndustry());
				prepStmt.setString(3, sym.getnSEStockCode());
				prepStmt.setString(4, sym.getSeries());
				prepStmt.setString(5, sym.getISINCode());
				prepStmt.setString(6, indexName);
				
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
		IndexSymbolsParsers symbolParser = new IndexSymbolsParsers();
		symbolParser.updateIndexData();
	}
}
