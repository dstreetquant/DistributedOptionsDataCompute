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
 * INFO:
 * Every Bhavcopy contains 1474 EQ stocks
 * Valid symbols 158 stocks
 * Total trading dates 787 (including 1 day for Gold ETF trading)
*/
public class BhavcopyToPerStockDataSplitter {
	//Bhavcopy Directory
	private static final String bhavCopyDir = "D:\\RiteshComputer\\Trading\\OptionStrategies\\Historical_EOD_data\\Equity\\BhavCopyData";
	//NSE symbol list file
	private static final String symbolListFile = "D:\\RiteshComputer\\Trading\\OptionStrategies\\Listings\\stockNSESymbolList.csv";
	//Stock wise daily data file path
	private static final String stockWiseDailyDataFilePath = "D:\\RiteshComputer\\Trading\\OptionStrategies\\test\\";
	
	//StockCode-[Day-StockData] Map for all the stocks for all dates
	private Map<String, Map<Date, DailyStockData>> perStockAllDatesDataMap;
	
	//stock symbol list
	private List<String> stockSymList;
	
	/*
	 * Constructor
	 */
	public BhavcopyToPerStockDataSplitter(){
		stockSymList = new ArrayList<String>();
		perStockAllDatesDataMap = new HashMap<String, Map<Date, DailyStockData>>();
	}
	
	/*
	 * parse the stock symbol list file and create a list
	 */
	private void createValidStocksSymbolList(){
		String symLine;
		
		//read all the stock symbols in each line
		try {
			BufferedReader reader = new BufferedReader(new FileReader(symbolListFile));
			
			//add each symbol in symList
			while((symLine = reader.readLine()) != null){
				if(!stockSymList.contains(symLine)){
					stockSymList.add(symLine);
				}
			}
			
			reader.close();
		} catch (FileNotFoundException e) {
			System.out
					.println("BhavcopyToPerStockDataSplitter.createValidStocksSymbolList(): FileNotFoundException on file: " + symbolListFile);
		} catch (IOException e) {
			System.out
					.println("BhavcopyToPerStockDataSplitter.createValidStocksSymbolList(): IOException on file: " + symbolListFile);
		}
	}
	
	/*
	 * public routine to parse all the files in the Bhavcopy directory
	 */
	public void parseBhavCopyDir(){
		String dataLine;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
		Date tradeDate = null;
		String tradeDayOfWk = null;
		
		//Create the required valid list of stock symbols
		createValidStocksSymbolList();
		
		//get list of files in BhavCopy directory
		File[] files = new File(bhavCopyDir).listFiles();
		System.out.println("BhavcopyToPerStockDataSplitter.parseBhavCopyDir(): num of files: " + files.length + ", num of valid symbols: " + stockSymList.size());
		
		//traverse through each file
		for(int fileCount = 0; fileCount < files.length; fileCount++){			
			//System.out.println("BhavcopyToPerStockDataSplitter.parseBhavCopyDir(): file[]" + files[fileCount]);
			
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
					
					//get the stock symbol (first String)
					String sym = dataSplit[0];
					
					//get the series
					String series = dataSplit[1];
					
					//perform processing only if stock is in valid symbol list and is of series EQ
					if(series.equals("EQ") && (stockSymList.contains(sym))){
						//get the stock trade date
						String date = dataSplit[10];
						tradeDate = dateFormat.parse(date);
						tradeDayOfWk = new SimpleDateFormat("EE").format(tradeDate);
						
						//create a new DailyStockData object 
						//and add all the BhavCopy parameters one by one
						DailyStockData stockData = new DailyStockData();
						stockData.setOpenVal(new BigDecimal(dataSplit[2]));
						stockData.setHighVal(new BigDecimal(dataSplit[3]));
						stockData.setLowVal(new BigDecimal(dataSplit[4]));
						stockData.setCloseVal(new BigDecimal(dataSplit[5]));
						stockData.setLastAdjustedVal(new BigDecimal(dataSplit[6]));
						stockData.setPrevCloseVal(new BigDecimal(dataSplit[7]));
						stockData.setTotTradeQty(Integer.parseInt(dataSplit[8]));
						stockData.setTotTradeVal(new BigDecimal(dataSplit[9]));
						stockData.setTradeDate(dataSplit[10]);
						stockData.setTotNumTrades(Integer.parseInt(dataSplit[11]));
						
						//add externally derived parameters
						//trading day of week
						stockData.setTradeDayofWk(tradeDayOfWk);						
						
						//Get the symbol sets
						Set<String>symSet = perStockAllDatesDataMap.keySet();
						if(!symSet.contains(sym)){
							//create Day-StockData Map for each Stock
							Map<Date, DailyStockData> dayWiseDataMap = new TreeMap<Date, DailyStockData>(new Comparator<Date>() {
							    public int compare(Date date1, Date date2) {
							        return date1.compareTo(date2);							    	
							    }
							});
							//add the processed StockData for this date key in the map of this stock
							dayWiseDataMap.put(tradeDate, stockData);
							//add into symbol list
							perStockAllDatesDataMap.put(sym, dayWiseDataMap);
						} else{
							//All Dates Data Map already contains this stock symbol, get it
							Map<Date, DailyStockData> dayWiseDataMap = perStockAllDatesDataMap.get(sym);
							//add the processed StockData for this date key in the map of this stock
							dayWiseDataMap.put(tradeDate, stockData);
						}
					}
				}
				
				//after reading the whole file close reader
				reader.close();
			} catch (FileNotFoundException e) {
				System.out
						.println("BhavcopyToPerStockDataSplitter.parseBhavCopyDir(): FileNotFoundException on file: " + files[fileCount].getName());
			} catch (IOException e) {
				System.out
						.println("BhavcopyToPerStockDataSplitter.parseBhavCopyDir(): IOException on file: " + files[fileCount].getName());
			} catch (ParseException e) {
				System.out
						.println("BhavcopyToPerStockDataSplitter.parseBhavCopyDir(): ParseException for parsing date: " + tradeDate);
			}			
		}
		
		//write the stock map data to files
		writeStockMapToFiles();
	}
	
	/*
	 * private routine to write the map of each stock to file
	 */
	private void writeStockMapToFiles(){
		String fileName = null;
		
		try {
			//get the list of stocks
			Set<String> keySet = perStockAllDatesDataMap.keySet();
			Iterator<String> keySetIt = keySet.iterator();
			while(keySetIt.hasNext()){
				String stock = keySetIt.next();
				
				//fileName is in the name of stock.csv
				fileName = stockWiseDailyDataFilePath + stock + ".csv";
				BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
				
				//Update header
				String headerLine = "SlID, STOCK, DATE, WKDAY, OPEN, HIGH, LOW, CLOSE, PREV_CLOSE, TOTAL_NUM_TRADES, TOTAL_TRADE_QNTY, TOTA_TRADE_VAL\n";
				writer.write(headerLine);
				
				//get the day wise data map for this stock
				Map<Date, DailyStockData> dayWiseDataMap = perStockAllDatesDataMap.get(stock);
				Set<Date> daySet = dayWiseDataMap.keySet();
				Iterator<Date> dateSetIt = daySet.iterator();
				int serialIdx = 0;
				while(dateSetIt.hasNext()){
					serialIdx++;
					//get the data for this date
					Date date = dateSetIt.next();
					DailyStockData stockData = dayWiseDataMap.get(date);
						
					//form the csv line
					//format: slIdx,stock,date,dayOfWk,O,H,L,C,prevC,totNumTrades,totTradeQty,totTradeVal
					String csvStockDataLine = serialIdx + "," +
												stock + "," +
												stockData.getTradeDate() + "," + 
												stockData.getTradeDayofWk() + "," +
												stockData.getOpenVal() + "," +
												stockData.getHighVal() + "," +
												stockData.getLowVal() + "," +
												stockData.getCloseVal() + "," +
												stockData.getPrevCloseVal() + "," +
												stockData.getTotNumTrades() + "," +
												stockData.getTotTradeQty() + "," +
												stockData.getTotTradeVal() + "\n";
					writer.write(csvStockDataLine);
				}
				writer.close();
			}			
		} catch (IOException e) {
			System.out
					.println("BhavcopyToPerStockDataSplitter.writeStockMapToFiles(): IOException on file: " + fileName);
		}
	}
	
	/*
	 * main routine
	 */
//	public static void main(String[] args){
//		BhavcopyToPerStockDataSplitter dataSplitter = new BhavcopyToPerStockDataSplitter();
//		dataSplitter.parseBhavCopyDir();
//	}

}
