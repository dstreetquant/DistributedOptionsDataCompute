package com.ritesh.optionstrategies.data.datafetcher;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class BhavcopyToNiftyDataSplitter {
	//NIFTY Bhavcopy Directory
	private static final String bhavCopyDir = "D:\\RiteshComputer\\Trading\\OptionStrategies\\Historical_EOD_data\\NIFTY\\BhavCopyData";
	//Nifty EOD data file path
	private static final String niftyDailyDataFilePath = "D:\\RiteshComputer\\Trading\\OptionStrategies\\Historical_EOD_data\\NIFTY\\NIFTYEoDData\\NIFTY_EOD.csv";
	
	//Day-NiftyData Map for nifty for all dates
	private Map<Date, NiftyData> niftyDatesDataMap;
	
	/*
	 * Constructor
	 */
	public BhavcopyToNiftyDataSplitter(){
		niftyDatesDataMap = new TreeMap<Date, NiftyData>();
	}
	
	/*
	 * public routine to parse all the files in the Bhavcopy directory
	 */
	public void parseBhavCopyDir(){
		String dataLine;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
		SimpleDateFormat dayOfWkFormat = new SimpleDateFormat("EE");
		Date tradeDate = null;
		String tradeDayOfWk = null;
		
		//get list of files in BhavCopy directory
		File[] files = new File(bhavCopyDir).listFiles();
		System.out.println("BhavcopyToNiftyDataSplitter.parseBhavCopyDir(): num of files: " + files.length);
		
		//traverse through each file
		for(int fileCount = 0; fileCount < files.length; fileCount++){
			BufferedReader reader;
			
			try {
				//read the file
				reader = new BufferedReader(new FileReader(files[fileCount]));
				
				//first line is a header line, skip that line
				reader.readLine();
				
				//read each line from second line and parse
				while((dataLine = reader.readLine()) != null){
					//split the line on basis of ","
					String[] dataSplit = dataLine.split(",");
					
					//get the Nifty trade date
					String date = dataSplit[0].replace("\"", "").trim();
					System.out.println("BhavcopyToNiftyDataSplitter.parseBhavCopyDir(): date: " + date);
					tradeDate = dateFormat.parse(date);
					tradeDayOfWk = dayOfWkFormat.format(tradeDate);
					
					//create a new NiftyData object 
					//and add all the BhavCopy parameters one by one
					//Date[0] Open[1] High[2] Low[3] Close[4] Shares Traded[5] Turnover (Rs. Cr)[6]
					NiftyData niftyData = new NiftyData();
					niftyData.setTradeDate(date);
					niftyData.setTradeDayofWk(tradeDayOfWk);
					niftyData.setOpenVal(new BigDecimal(dataSplit[1].replace("\"", "").trim()));
					niftyData.setHighVal(new BigDecimal(dataSplit[2].replace("\"", "").trim()));
					niftyData.setLowVal(new BigDecimal(dataSplit[3].replace("\"", "").trim()));
					niftyData.setCloseVal(new BigDecimal(dataSplit[4].replace("\"", "").trim()));
					niftyData.setSharesTraded(Long.parseLong(dataSplit[5].replace("\"", "").trim()));
					niftyData.setTurnOverInCr(new BigDecimal(dataSplit[6].replace("\"", "").trim()));
					
					//add to the map
					niftyDatesDataMap.put(tradeDate, niftyData);
				}
				
				//after reading the whole file close reader
				reader.close();
			} catch (FileNotFoundException e) {
				System.out
						.println("BhavcopyToNiftyDataSplitter.parseBhavCopyDir(): FileNotFoundException on file: " + files[fileCount].getName());
			} catch (IOException e) {
				System.out
						.println("BhavcopyToNiftyDataSplitter.parseBhavCopyDir(): IOException on file: " + files[fileCount].getName());
			} catch (ParseException e) {
				System.out
						.println("BhavcopyToNiftyDataSplitter.parseBhavCopyDir(): ParseException for parsing date: " + tradeDate);
			}
		}
		
		//write the Nifty data to files
		writeNiftyMapToFiles();
	}
	
	/*
	 * private routine to write the map of Nifty to file
	 */
	private void writeNiftyMapToFiles(){
		int serialIdx = 0;
		BigDecimal lastClosePrice = new BigDecimal(0);
		
		try {
			//Nifty File Writer
			BufferedWriter writer = new BufferedWriter(new FileWriter(niftyDailyDataFilePath));
			
			//Update header
			String headerLine = "SlID, DATE, WKDAY, OPEN, HIGH, LOW, CLOSE, PREV_CLOSE, SHARES_TRADED, TURNOVER_IN_CR\n";
			writer.write(headerLine);
			
			//get the map of Nifty
			Set<Date> keySet = niftyDatesDataMap.keySet();
			Iterator<Date> keySetIt = keySet.iterator();
			while(keySetIt.hasNext()){
				Date tradeDate = keySetIt.next();
				serialIdx++;
				
				//get the day wise Nifty Data
				NiftyData niftyData = niftyDatesDataMap.get(tradeDate);
				
				//Update last close price
				niftyData.setLastCloseVal(lastClosePrice);
				
				//form the csv line
				//format: slIdx,date,dayOfWk,O,H,L,C,prevC,sharesTraded,turnoverInCr
				String csvStockDataLine = serialIdx + "," +
						niftyData.getTradeDate() + "," + 
						niftyData.getTradeDayofWk() + "," +
						niftyData.getOpenVal() + "," +
						niftyData.getHighVal() + "," +
						niftyData.getLowVal() + "," +
						niftyData.getCloseVal() + "," +
						niftyData.getLastCloseVal() + "," +
						niftyData.getSharesTraded() + "," + 
						niftyData.getTurnOverInCr() + "\n";
				writer.write(csvStockDataLine);
				
				//update last close price tracker
				lastClosePrice = niftyData.getCloseVal();
			}
			writer.close();
		} catch (IOException e) {
			System.out
				.println("BhavcopyToNiftyDataSplitter.writeNiftyMapToFiles(): IOException on file: " + niftyDailyDataFilePath);
		}
	}
	
	/*
	 * main routine
	 */
//	public static void main(String[] args){
//		BhavcopyToNiftyDataSplitter dataSplitter = new BhavcopyToNiftyDataSplitter();
//		dataSplitter.parseBhavCopyDir();
//	}
}
