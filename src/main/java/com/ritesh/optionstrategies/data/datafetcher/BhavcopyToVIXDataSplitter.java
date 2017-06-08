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

public class BhavcopyToVIXDataSplitter {
	//VIX Bhavcopy Directory
	private static final String bhavCopyDir = "D:\\RiteshComputer\\Trading\\OptionStrategies\\Historical_EOD_data\\VIX\\BhavCopyData";
	//VIX EOD data file path
	private static final String vixDailyDataFilePath = "D:\\RiteshComputer\\Trading\\OptionStrategies\\Historical_EOD_data\\VIX\\VIXEoDData\\VIX_EOD.csv";
		
	//Day-NiftyData Map for nifty for all dates
	private Map<Date, VIXData> vixDatesDataMap;
	
	/*
	 * Constructor
	 */
	public BhavcopyToVIXDataSplitter(){
		vixDatesDataMap = new TreeMap<Date, VIXData>();
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
		System.out.println("BhavcopyToVIXDataSplitter.parseBhavCopyDir(): num of files: " + files.length);
		
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
					
					//get the VIX trade date
					String date = dataSplit[0].trim();
					System.out.println("BhavcopyToVIXDataSplitter.parseBhavCopyDir(): date: " + date);
					tradeDate = dateFormat.parse(date);
					tradeDayOfWk = dayOfWkFormat.format(tradeDate);
					
					//create a new VIXData object 
					//and add all the BhavCopy parameters one by one
					//Date[0] Open[1] High[2] Low[3] Close[4] Prev. Close[5] Change[6]	Percntg_Change[7]
					VIXData vixData = new VIXData();
					vixData.setTradeDate(date);
					vixData.setTradeDayofWk(tradeDayOfWk);
					vixData.setOpenVal(new BigDecimal(dataSplit[1].trim()));
					vixData.setHighVal(new BigDecimal(dataSplit[2].trim()));
					vixData.setLowVal(new BigDecimal(dataSplit[3].trim()));
					vixData.setCloseVal(new BigDecimal(dataSplit[4].trim()));
					vixData.setPrevCloseVal(new BigDecimal(dataSplit[5].trim()));
					vixData.setChange(new BigDecimal(dataSplit[6].trim()));
					vixData.setPercntgChange(new BigDecimal(dataSplit[7].trim()));
					
					//add to the map
					vixDatesDataMap.put(tradeDate, vixData);
				}
				
				//after reading the whole file close reader
				reader.close();
			} catch (FileNotFoundException e) {
				System.out
						.println("BhavcopyToVIXDataSplitter.parseBhavCopyDir(): FileNotFoundException on file: " + files[fileCount].getName());
			} catch (IOException e) {
				System.out
						.println("BhavcopyToVIXDataSplitter.parseBhavCopyDir(): IOException on file: " + files[fileCount].getName());
			} catch (ParseException e) {
				System.out
						.println("BhavcopyToVIXDataSplitter.parseBhavCopyDir(): ParseException for parsing date: " + tradeDate);
			}
		}
		
		//write the VIX data to files
		writeVixMapToFiles();
	}
	
	/*
	 * private routine to write the map of VIX to file
	 */
	private void writeVixMapToFiles(){
		int serialIdx = 0;
		
		try {
			//VIX File Writer
			BufferedWriter writer = new BufferedWriter(new FileWriter(vixDailyDataFilePath));
			
			//Update header
			String headerLine = "SlID, DATE, WKDAY, OPEN, HIGH, LOW, CLOSE, PREV_CLOSE, CHANGE, PERCNTG_CHANGE\n";
			writer.write(headerLine);
			
			//get the map of VIX
			Set<Date> keySet = vixDatesDataMap.keySet();
			Iterator<Date> keySetIt = keySet.iterator();
			while(keySetIt.hasNext()){
				Date tradeDate = keySetIt.next();
				serialIdx++;
				
				//get the day wise VIX Data
				VIXData vixData = vixDatesDataMap.get(tradeDate);
				
				//form the csv line
				//format: slIdx,date,dayOfWk,O,H,L,C,prevC,change,percntgChange
				String csvStockDataLine = serialIdx + "," +
						vixData.getTradeDate() + "," + 
						vixData.getTradeDayofWk() + "," +
						vixData.getOpenVal() + "," +
						vixData.getHighVal() + "," +
						vixData.getLowVal() + "," +
						vixData.getCloseVal() + "," +
						vixData.getPrevCloseVal() + "," +
						vixData.getChange() + "," + 
						vixData.getPercntgChange() + "\n";
				writer.write(csvStockDataLine);
			}
			writer.close();
		} catch (IOException e) {
			System.out
				.println("BhavcopyToVIXDataSplitter.writeVixMapToFiles(): IOException on file: " + vixDailyDataFilePath);
		}
	}
	
	/*
	 * main routine
	 */
//	public static void main(String[] args){
//		BhavcopyToVIXDataSplitter dataSplitter = new BhavcopyToVIXDataSplitter();
//		dataSplitter.parseBhavCopyDir();
//	}
}
