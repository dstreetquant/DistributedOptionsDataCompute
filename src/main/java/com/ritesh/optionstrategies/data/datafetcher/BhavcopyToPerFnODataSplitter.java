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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/*
 * THIS CLASS MAP OF MAPS OF MAPS => Giving OOM problem in Str split()
*/
public class BhavcopyToPerFnODataSplitter {
	//FnO Bhavcopy Directory
	private static final String bhavCopyDir = "D:\\RiteshComputer\\Trading\\OptionStrategies\\Historical_EOD_data\\FnO\\BhavCopyData";
	//NSE FnO symbol list file
	private static final String symbolListFile = "D:\\RiteshComputer\\Trading\\OptionStrategies\\Listings\\foNSESymbolList.csv";
	//FnO wise daily data file path
	private static final String fnoWiseDailyDataFilePath = "D:\\RiteshComputer\\Trading\\OptionStrategies\\test\\";
	
	//fnoCode-[Day-[StrikePrice-DailyFnOData]] Map for all the FnO for all dates
	private Map<String, Map<Date, Map<String, DailyFnOData>>> perFnOAllDatesDataMap;
	
	//fno symbol list
	private List<String> fnoSymList;
	
	/*
	 * Constructor
	 */
	public BhavcopyToPerFnODataSplitter(){
		fnoSymList = new ArrayList<String>();
		perFnOAllDatesDataMap = new HashMap<String, Map<Date, Map<String, DailyFnOData>>>();
	}
	
	/*
	 * parse the FnO symbol list file and create a list
	 */
	private void createValidFnOSymbolList(){
		String symLine;
		
		//read all the FnO symbols in each line
		try {
			BufferedReader reader = new BufferedReader(new FileReader(symbolListFile));
			
			//add each symbol in symList
			while((symLine = reader.readLine()) != null){
				if(!fnoSymList.contains(symLine)){
					fnoSymList.add(symLine);
				}
			}
			
			reader.close();
		} catch (FileNotFoundException e) {
			System.out
					.println("BhavcopyToPerFnODataSplitter.createValidFnOSymbolList(): FileNotFoundException on file: " + symbolListFile);
		} catch (IOException e) {
			System.out
					.println("BhavcopyToPerFnODataSplitter.createValidFnOSymbolList(): IOException on file: " + symbolListFile);
		}
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
		
		//Create the required valid list of FnO symbols
		createValidFnOSymbolList();
		
		//get list of files in BhavCopy directory
		File[] files = new File(bhavCopyDir).listFiles();
		System.out.println("BhavcopyToPerFnODataSplitter.parseBhavCopyDir(): num of files: " + files.length + ", num of valid symbols: " + fnoSymList.size());
		
		//traverse through each file
		for(int fileCount = 0; fileCount < files.length; fileCount++){			
			//System.out.println("BhavcopyToPerFnODataSplitter.parseBhavCopyDir(): file[]" + files[fileCount]);
			
			BufferedReader reader;
			try {
				//read the file
				reader = new BufferedReader(new FileReader(files[fileCount]));
				
				//first line is a header line, skip that line
				reader.readLine();
				//read each line from second line and parse
				while((dataLine = reader.readLine()) != null){
					//System.out.println("BhavcopyToPerFnODataSplitter.parseBhavCopyDir(): filename: " + files[fileCount].getName() + "line: " + dataLine);
					//split the line on basis of ","
					String[] dataSplit = dataLine.split(",");
					
					//get the instrument type (first String - FUTIDX/FUTIVX/FUTSTK/OPTIDX/OPTSTK etc)
					String instrumentType = dataSplit[0];
					
					//get the FnO symbol (second String)
					String sym = dataSplit[1];
										
					//perform processing only if FnO is in valid symbol list and is of type Option Index or Option Stock
					if((instrumentType.equals("OPTIDX") || instrumentType.equals("OPTSTK")) && (fnoSymList.contains(sym))){
						//get the stock trade date
						String date = dataSplit[14];
						tradeDate = dateFormat.parse(date);
						tradeDayOfWk = dayOfWkFormat.format(tradeDate);
						
						//get the strike price
						String strikePrice = dataSplit[3];
						
						//create a new DailyFnOData object 
						//and add all the BhavCopy parameters one by one
						//System.out.println("BhavcopyToPerFnODataSplitter.parseBhavCopyDir(): sym: " + sym + ", Date: " + date);
						DailyFnOData fnoData = new DailyFnOData();
						
						fnoData.setInstrumentType(instrumentType);	//Instrument Type
						fnoData.setSymbol(sym);		//Symbol
						fnoData.setExpiryDate(dataSplit[2]);	//Expiry Date
						fnoData.setStrikePrice(new BigDecimal(dataSplit[3]));	//Strike Price
						fnoData.setOptionType(dataSplit[4]);	//option type
						fnoData.setOpenVal(new BigDecimal(dataSplit[5]));	//open
						fnoData.setHighVal(new BigDecimal(dataSplit[6]));	//High
						fnoData.setLowVal(new BigDecimal(dataSplit[7]));	//Low
						fnoData.setCloseVal(new BigDecimal(dataSplit[8]));	//Close
						fnoData.setLastAdjustedVal(new BigDecimal(dataSplit[9]));	//Settle Price
						fnoData.setContracts(new BigDecimal(dataSplit[10]));	//Contracts
						fnoData.setValInLakh(new BigDecimal(dataSplit[11]));	//Value in Lakhs
						fnoData.setOpenInterest(Long.parseLong(dataSplit[12]));	//Open Interest
						fnoData.setChangeOpenInterest(Long.parseLong(dataSplit[13]));		//Change In Open Interest
						fnoData.setTradeDate(date);		//TimeStamp						
						
						//add externally derived parameters
						//trading day of week
						fnoData.setTradeDayofWk(tradeDayOfWk);		//Trading Day					
						
						//Get the fnoCode symbol sets in perFnOAllDatesDataMap
						Set<String>symSet = perFnOAllDatesDataMap.keySet();
						if(!symSet.contains(sym)){
							//create StrikePrice-FnOData Map for this date
							Map<String, DailyFnOData> strikePriceDataMap = new TreeMap<String, DailyFnOData>(new Comparator<String>() {
							    public int compare(String str1, String str2) {
							        return str1.compareTo(str2);							    	
							    }
							});
							//add this FnOData in this strike price
							strikePriceDataMap.put(strikePrice, fnoData);
							
							//create Date-[StrikePrice-FnOData] Map for each FnO
							Map<Date, Map<String, DailyFnOData>> dayWiseDataMap = new TreeMap<Date, Map<String, DailyFnOData>>(new Comparator<Date>() {
							    public int compare(Date date1, Date date2) {
							        return date1.compareTo(date2);							    	
							    }
							});
							//add the StrikePrice-FnOData for this date key in the map of this fno
							dayWiseDataMap.put(tradeDate, strikePriceDataMap);
							//add into symbol map
							perFnOAllDatesDataMap.put(sym, dayWiseDataMap);
						} else{
							//All Dates Data Map already contains this fno symbol, get it
							Map<Date, Map<String, DailyFnOData>> dayWiseDataMap = perFnOAllDatesDataMap.get(sym);
							Set<Date>dateSet = dayWiseDataMap.keySet();
							if(!dateSet.contains(tradeDate)){
								//Does not contain this date, create StrikePrice-FnOData Map for this date
								Map<String, DailyFnOData> strikePriceDataMap = new TreeMap<String, DailyFnOData>(new Comparator<String>() {
								    public int compare(String str1, String str2) {
								        return str1.compareTo(str2);							    	
								    }
								});
								//add this FnOData in this strike price
								strikePriceDataMap.put(strikePrice, fnoData);
								//add the StrikePrice-fnoData Map for this date key in the map of this fno
								dayWiseDataMap.put(tradeDate, strikePriceDataMap);
							} else{
								//StrikePrice-FnoData Map already exists for this date, get it
								Map<String, DailyFnOData> strikePriceDataMap = dayWiseDataMap.get(tradeDate);
								//add the processed fnoData for this Strike Price key for this date for this fno
								strikePriceDataMap.put(strikePrice, fnoData);
							}							
						}
					}
				}
				
				//after reading the whole file close reader
				reader.close();
			} catch (FileNotFoundException e) {
				System.out
						.println("BhavcopyToPerFnODataSplitter.parseBhavCopyDir(): FileNotFoundException on file: " + files[fileCount].getName());
			} catch (IOException e) {
				System.out
						.println("BhavcopyToPerFnODataSplitter.parseBhavCopyDir(): IOException on file: " + files[fileCount].getName());
			} catch (ParseException e) {
				System.out
						.println("BhavcopyToPerFnODataSplitter.parseBhavCopyDir(): ParseException for parsing date: " + tradeDate);
			}			
		}
		
		//write the fno map data to files
		writeFnOMapToFiles();
	}
	
	/*
	 * private routine to write the map of each FnO to file
	 */
	private void writeFnOMapToFiles(){
		String fileName = null;
		
		try {
			//get the list of fnos
			Set<String> fnoSet = perFnOAllDatesDataMap.keySet();
			Iterator<String> fnoSetIt = fnoSet.iterator();
			while(fnoSetIt.hasNext()){
				String fno = fnoSetIt.next();
				
				//fileName is in the name of <fno>.csv
				fileName = fnoWiseDailyDataFilePath + fno + ".csv";
				BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
				
				//Update header
				String headerLine = "INSTRUMENT, SYMBOL, EXPIRY_DT, STRIKE_PR, OPTION_TYP, OPEN, HIGH, LOW, CLOSE, SETTLE_PR, CONTRACTS, VAL_INLAKH, OPEN_INT, CHG_IN_OI, TIMESTAMP\n";
				writer.write(headerLine);
				
				//get the day wise data map for this fno
				Map<Date, Map<String, DailyFnOData>> dayWiseDataMap = perFnOAllDatesDataMap.get(fno);
				Set<Date> daySet = dayWiseDataMap.keySet();
				Iterator<Date> dateSetIt = daySet.iterator();
				while(dateSetIt.hasNext()){
					//get the data for this date
					Date date = dateSetIt.next();
					
					//get the strikePrice-fnoData Map for this date
					Map<String, DailyFnOData> strikePriceDataMap = dayWiseDataMap.get(date);
					Set<String> strikePriceSet = strikePriceDataMap.keySet();
					Iterator<String> strikePriceSetIt = strikePriceSet.iterator();
					while(strikePriceSetIt.hasNext()){
						//get the strike price
						String strikePrice = strikePriceSetIt.next();
						//get the corresponding fnoData for this strike price for this date for this fno
						DailyFnOData fnoData = strikePriceDataMap.get(strikePrice);
						
						//form the csv line
						//format: INSTRUMENT, SYMBOL, EXPIRY_DT, STRIKE_PR, OPTION_TYP, OPEN, HIGH, LOW, CLOSE, SETTLE_PR, CONTRACTS, VAL_INLAKH, OPEN_INT, CHG_IN_OI, TIMESTAMP
						String csvStockDataLine = fnoData.getInstrumentType()  + "," + 	//Instrument Type
								fnoData.getSymbol()  + "," + 	//Symbol
								fnoData.getExpiryDate()  + "," + 	//Expiry Date
								fnoData.getStrikePrice()  + "," + 	//Strike Price
								fnoData.getOptionType()  + "," + 	//option type
								fnoData.getOpenVal()  + "," + 	//open
								fnoData.getHighVal()  + "," + 	//High
								fnoData.getLowVal()  + "," + 	//Low
								fnoData.getCloseVal()  + "," + 	//Close
								fnoData.getLastAdjustedVal()  + "," + 	//Settle Price
								fnoData.getContracts()  + "," + 	//Contracts
								fnoData.getValInLakh()  + "," + 	//Value in Lakhs
								fnoData.getOpenInterest()  + "," + 	//Open Interest
								fnoData.getChangeOpenInterest()  + "," + 		//Change In Open Interest
								fnoData.getTradeDate()  + "," +		//TimeStamp	
								fnoData.getTradeDayofWk()  + "\n";	//Trade Day of Wk
						
						writer.write(csvStockDataLine);
					}
				}
				writer.close();
			}			
		} catch (IOException e) {
			System.out
					.println("BhavcopyToPerFnODataSplitter.writeFnOMapToFiles(): IOException on file: " + fileName);
		}
	}
	
	/*
	 * main routine
	 */
//	public static void main(String[] args){
//		BhavcopyToPerFnODataSplitter dataSplitter = new BhavcopyToPerFnODataSplitter();
//		dataSplitter.parseBhavCopyDir();
//	}
}
