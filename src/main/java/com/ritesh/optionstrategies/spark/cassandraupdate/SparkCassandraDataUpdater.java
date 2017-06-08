package com.ritesh.optionstrategies.spark.cassandraupdate;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import org.eclipse.jetty.util.log.Log;

import com.ritesh.optionstrategies.spark.cassandraupdate.dao.EquityDataRowWriter.EquityDataRowWriterFactory;
import com.ritesh.optionstrategies.spark.cassandraupdate.dao.FnODataRowWriter.FnODataRowWriterFactory;
import com.ritesh.optionstrategies.spark.cassandraupdate.dao.NiftyDataRowWriter.NiftyDataRowWriterFactory;
import com.ritesh.optionstrategies.spark.cassandraupdate.dao.TradingDateDataRowWriter.TradingDateDataRowWriterFactory;
import com.ritesh.optionstrategies.spark.cassandraupdate.dao.VIXDataRowWriter.VIXDataRowWriterFactory;
import com.ritesh.optionstrategies.spark.cassandraupdate.model.EquityDataModel;
import com.ritesh.optionstrategies.spark.cassandraupdate.model.FnODataModel;
import com.ritesh.optionstrategies.spark.cassandraupdate.model.NiftyDataModel;
import com.ritesh.optionstrategies.spark.cassandraupdate.model.TradingDateDataModel;
import com.ritesh.optionstrategies.spark.cassandraupdate.model.VIXDataModel;

import scala.Tuple2;

public class SparkCassandraDataUpdater implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//FnO Data Path
	private static final String FNO_DATA_DIR_PATH = "/media/sf_UbuntuVirtualBox_SharedPath/Trading/OptionStrategies/Historical_EOD_data/FnO/BhavCopyData";
	//Equity Data Path
	private static final String EQUITY_DATA_DIR_PATH = "/media/sf_UbuntuVirtualBox_SharedPath/Trading/OptionStrategies/Historical_EOD_data/Equity/EquityEoDData";
	//NIFTY Data Path
	private static final String NIFTY_DATA_FILE_PATH = "/media/sf_UbuntuVirtualBox_SharedPath/Trading/OptionStrategies/Historical_EOD_data/NIFTY/NIFTYEoDData/NIFTY.csv";
	//VIX Data Path
	private static final String VIX_DATA_FILE_PATH = "/media/sf_UbuntuVirtualBox_SharedPath/Trading/OptionStrategies/Historical_EOD_data/VIX/VIXEoDData/VIX.csv";
	
	//Master IP for the Spark Cluster
	private static final String SPARK_MASTER_IP = "spark://127.0.0.1:7077";
	private static final String CASSANDRA_RING_IP = "127.0.0.1";
	
	//Cassandra keyspace properties
	private static final String CASSANDRA_OPTIONS_MASTER_DATA_KEYSPACE = "optionsmasterdb";
	//Cassandra Tables properties
	private static final String CASSANDRA_OPTIONS_MASTER_KEYSPACE_FNO_TABLE = "fnotable";
	private static final String CASSANDRA_OPTIONS_MASTER_KEYSPACE_EQUITY_TABLE = "equitytable";
	private static final String CASSANDRA_OPTIONS_MASTER_KEYSPACE_NIFTY_TABLE = "niftytable";
	private static final String CASSANDRA_OPTIONS_MASTER_KEYSPACE_VIX_TABLE = "vixtable";
	private static final String CASSANDRA_OPTIONS_MASTER_KEYSPACE_DATE_TABLE = "tradingdatetable";	
	
	//Spark-cassandra-connector objects
	private EquityDataRowWriterFactory equityDataRowWriter = null;
	private FnODataRowWriterFactory fnODataRowWriter = null;
	private NiftyDataRowWriterFactory niftyDataRowWriter = null;
	private VIXDataRowWriterFactory vixDataRowWriter = null;
	private TradingDateDataRowWriterFactory tradingDateDataRowWriter = null;
	
	//Logger
	private static Logger log = Logger.getLogger(SparkCassandraDataUpdater.class.getName());
	
	//Spark configurations
    private transient SparkConf conf;
    
	//Trading Days List
  	List<TradingDateDataModel> sortedTradingDateList = new ArrayList<TradingDateDataModel>();
  	
  	/**
     * Constructor
     * @param conf
     */
    private SparkCassandraDataUpdater(SparkConf conf) {
        this.conf = conf;
        this.equityDataRowWriter = new EquityDataRowWriterFactory();		
		this.fnODataRowWriter = new FnODataRowWriterFactory();
		this.niftyDataRowWriter = new NiftyDataRowWriterFactory();
		this.vixDataRowWriter = new VIXDataRowWriterFactory();
		this.tradingDateDataRowWriter = new TradingDateDataRowWriterFactory();
    }
    
    /**
     * Run method - for running the job
     */
    private void run() {
    	System.out.println("SparkCassandraDataUpdater: run: method called");
    	log.info("SparkCassandraDataUpdater: run: starting spark analytics");
                
        //Prepare Trading dates list
        //prepareDateList();
        
        JavaSparkContext sc = new JavaSparkContext(conf);
        
        //Spark Jobs from here
        //Perform Operations for Cassandra Date Data Updating
        //updateDateTable(sc);
        updateFnOTable(sc);
        //updateEquityTable(sc);
        //updateNIFTYTable(sc);
        //updateVIXTable(sc);
        
        sc.stop();
    }
    
    /**
	 * Routine to Update Date Table
	 * @param sc
	 */
	@SuppressWarnings("serial")
	private void updateDateTable(JavaSparkContext sc){
		System.out.println("SparkCassandraDataUpdater: updateDateTable: method called");
		
		JavaRDD<TradingDateDataModel> tradingDateDataRDD = sc.parallelize(sortedTradingDateList);
		
		//DEBUG
//		tradingDateDataRDD.foreach(new VoidFunction<TradingDateDataModel>() {			
//			public void call(TradingDateDataModel dateModel) throws Exception {
//				log.info("SparkCassandraDataUpdater.updateDateTable.call(): date: " + dateModel.getDate());
//			}
//		});
		//
		
		System.out.println("SparkCassandraDataUpdater: updateDateTable: prepared tradingDateDataRDD");
		
		//save the Date Table
		javaFunctions(tradingDateDataRDD).writerBuilder(CASSANDRA_OPTIONS_MASTER_DATA_KEYSPACE, CASSANDRA_OPTIONS_MASTER_KEYSPACE_DATE_TABLE, tradingDateDataRowWriter)
								.saveToCassandra();
		System.out.println("SparkCassandraDataUpdater: updateDateTable: saved tradingDateDataRDD");
	}
	
	/**
	 * Routine to update FnO Master Table
	 * @param sc
	 */
	@SuppressWarnings("serial")
	private void updateFnOTable(JavaSparkContext sc){
		System.out.println("SparkCassandraDataUpdater: updateFnOTable: method called");
		
		//Pair RDD - <filename, filecontent>
		JavaPairRDD<String, String> csvFileDataRDD = sc.wholeTextFiles(FNO_DATA_DIR_PATH);
		
		JavaRDD<FnODataModel> fnODataRDD = csvFileDataRDD.flatMap(new FlatMapFunction<Tuple2<String, String>, FnODataModel>(){
			public Iterable<FnODataModel> call(Tuple2<String, String> csvFileData) throws Exception {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
				SimpleDateFormat dayOfWkFormat = new SimpleDateFormat("EE");
				//Read the data for each CSV file
				String[] csvLines = csvFileData._2().split("\n");
				String[][] csvData = new String[csvLines.length][];
				for(int i = 1; i < csvLines.length; i++){
					csvData[i] = csvLines[i].split(",");
				}
				
				List<FnODataModel> fnoDataList = new ArrayList<FnODataModel>();
				
				//prepare the FnODataModel, leave the first header line
				for(int i = 1; i < csvData.length; i++){
					
					//DEBUG
//					if(!csvData[i][0].equals("INSTRUMENT")){
//						log.info(
//							"SparkCassandraDataUpdater.updateFnOTable.call(): derivedReturn: " + new BigDecimal(csvData[i][8]).subtract(new BigDecimal(csvData[i][5])));
//						//log.info(
//							//"SparkCassandraDataUpdater.updateFnOTable.call(): derivedLogreturn: " + new BigDecimal(Math.log(Math.abs(new BigDecimal(csvData[i][8]).subtract(new BigDecimal(csvData[i][5])).doubleValue()))));
//					}
					//
					
					FnODataModel fnoDataModel = null;
					
					
					if(!csvData[i][0].equals("INSTRUMENT")){
						
						//precisely take care of log operations
						Double derivedLogReturn = 0.00;
						if(new BigDecimal(csvData[i][5]).compareTo(BigDecimal.ZERO) != 0){
							derivedLogReturn = Math.log(Math.abs(new BigDecimal(csvData[i][8]).divide(new BigDecimal(csvData[i][5]), 6, RoundingMode.HALF_UP).doubleValue()));
							if(Double.isInfinite(derivedLogReturn) || Double.isNaN(derivedLogReturn)){
								derivedLogReturn = 0.00;
							}
						}
						
						fnoDataModel = new FnODataModel(
								csvData[i][0],	//instrumenttype
								csvData[i][1],	//symbol
								dateFormat.parse(csvData[i][2]),	//expirydate
								new BigDecimal(csvData[i][3]),	//strikeprice
								csvData[i][4],	//optiontype
								new BigDecimal(csvData[i][5]),	//openval
								new BigDecimal(csvData[i][6]),	//highval
								new BigDecimal(csvData[i][7]),	//lowval
								new BigDecimal(csvData[i][8]),	//closeval
								new BigDecimal(csvData[i][9]),	//lastadjustedval
								new BigDecimal(csvData[i][10]),	//contracts
								new BigDecimal(csvData[i][11]),	//valinlakh        
								Long.parseLong(csvData[i][12]),	//openinterest
								Long.parseLong(csvData[i][13]),	//changeopeninterest
								dateFormat.parse(csvData[i][14]),	//tradedate
								//Derived Data Fields
								dayOfWkFormat.format(dateFormat.parse(csvData[i][14])),	//tradedayofwk
								new BigDecimal(csvData[i][8]).subtract(new BigDecimal(csvData[i][5])),	//derivedReturn
								new BigDecimal(derivedLogReturn).multiply(new BigDecimal("100")).setScale(4, RoundingMode.HALF_EVEN)	//derivedLogReturn
								);
					} else{
						//Default Values
						fnoDataModel = new FnODataModel(
								"UNDEFINED",	//instrumenttype
								"UNDEFINED",	//symbol
								dateFormat.parse("01-Jan-1970"),	//expirydate
								new BigDecimal("0"),	//strikeprice
								"UNDEFINED",	//optiontype
								new BigDecimal("0"),	//openval
								new BigDecimal("0"),	//highval
								new BigDecimal("0"),	//lowval
								new BigDecimal("0"),	//closeval
								new BigDecimal("0"),	//lastadjustedval
								new BigDecimal("0"),	//contracts
								new BigDecimal("0"),	//valinlakh        
								Long.parseLong("0"),	//openinterest
								Long.parseLong("0"),	//changeopeninterest
								dateFormat.parse("01-Jan-1970"),	//tradedate
								//Derived Data Fields
								dayOfWkFormat.format("Sun"),	//tradedayofwk
								new BigDecimal("0"),	//derivedReturn
								new BigDecimal("0")	//derivedLogReturn
								);
					}
					fnoDataList.add(fnoDataModel);
				}
				
				return fnoDataList;
			}			
		});
		
		System.out.println("SparkCassandraDataUpdater: updateFnOTable: prepared fnODataRDD");
		
		//save the FnO Table
		javaFunctions(fnODataRDD).writerBuilder(CASSANDRA_OPTIONS_MASTER_DATA_KEYSPACE, CASSANDRA_OPTIONS_MASTER_KEYSPACE_FNO_TABLE, fnODataRowWriter)
										.saveToCassandra();
		System.out.println("SparkCassandraDataUpdater: updateFnOTable: saved fnODataRDD");
	}
	
	/**
	 * Routine to update Equity Master Table
	 * @param sc
	 */
	@SuppressWarnings("serial")
	private void updateEquityTable(JavaSparkContext sc){
		System.out.println("SparkCassandraDataUpdater: updateEquityTable: method called");
		
		//Pair RDD - <filename, filecontent>
		JavaPairRDD<String, String> csvFileDataRDD = sc.wholeTextFiles(EQUITY_DATA_DIR_PATH);
		
		JavaRDD<EquityDataModel> equityDataRDD = csvFileDataRDD.flatMap(new FlatMapFunction<Tuple2<String, String>, EquityDataModel>(){
			public Iterable<EquityDataModel> call(Tuple2<String, String> csvFileData) throws Exception {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
				//Read the data for each CSV file
				String[] csvLines = csvFileData._2().split("\n");
				String[][] csvData = new String[csvLines.length][];
				for(int i = 1; i < csvLines.length; i++){
					csvData[i] = csvLines[i].split(",");
				}
				
				List<EquityDataModel> equityDataList = new ArrayList<EquityDataModel>();
				
				//prepare the equityDataModel, leave the first header line
				for(int i = 1; i < csvData.length; i++){
					
					//DEBUG
//					if(!csvData[i][0].equals("SlID")){
//						log.info(
//							"SparkCassandraDataUpdater.updateEquityTable.call(): ocreturn: " + new BigDecimal(csvData[i][7]).subtract(new BigDecimal(csvData[i][4])) + ", tottradeqty: " + csvData[i][10]);
//						//log.info(
//							//"SparkCassandraDataUpdater.updateEquityTable.call(): oclogreturn: " + new BigDecimal(Math.log(Math.abs(new BigDecimal(csvData[i][7]).subtract(new BigDecimal(csvData[i][4])).doubleValue()))));
//					}
//					
//					log.info("HERE");
					//
					
					EquityDataModel equityDataModel = null;
					
					if(!csvData[i][0].equals("SlID")){
						//Precisely take care of log operations
						Double oclogreturn = 0.00;
						if(new BigDecimal(csvData[i][4]).compareTo(BigDecimal.ZERO) != 0){
							oclogreturn = Math.log(Math.abs(new BigDecimal(csvData[i][7]).divide(new BigDecimal(csvData[i][4]), 6, RoundingMode.HALF_UP).doubleValue()));
							if(Double.isInfinite(oclogreturn) || Double.isNaN(oclogreturn)){
								oclogreturn = 0.00;
							}
						}
						
						Double cologreturn = 0.00;
						if(new BigDecimal(csvData[i][8]).compareTo(BigDecimal.ZERO) != 0){
							cologreturn = Math.log(Math.abs(new BigDecimal(csvData[i][4]).divide(new BigDecimal(csvData[i][8]), 6, RoundingMode.HALF_UP).doubleValue()));
							if(Double.isInfinite(cologreturn) || Double.isNaN(cologreturn)){
								cologreturn = 0.00;
							}
						}
						
						Double cclogreturn = 0.00;
						if(new BigDecimal(csvData[i][8]).compareTo(BigDecimal.ZERO) != 0){
							cclogreturn = Math.log(Math.abs(new BigDecimal(csvData[i][7]).divide(new BigDecimal(csvData[i][8]), 6, RoundingMode.HALF_UP).doubleValue()));
							if(Double.isInfinite(cclogreturn) || Double.isNaN(cclogreturn)){
								cclogreturn = 0.00;
							}
						}
						
						equityDataModel = new EquityDataModel(							
								Integer.parseInt(csvData[i][0]),	//slidx
								csvData[i][1],	//stock
								dateFormat.parse(csvData[i][2]),	//tradedate
								csvData[i][3],	//tradedayofwk
								new BigDecimal(csvData[i][4]),	//openval
								new BigDecimal(csvData[i][5]),	//highval
								new BigDecimal(csvData[i][6]),	//lowval
								new BigDecimal(csvData[i][7]),	//closeval
								new BigDecimal(csvData[i][8]),	//prevcloseval
								Integer.parseInt(csvData[i][9]),	//totnumtrades
								Integer.parseInt(csvData[i][10]),	//tottradeqty
								new BigDecimal(csvData[i][11]),	//tottradeval
								//Derived Data Fields
								new BigDecimal(csvData[i][7]).subtract(new BigDecimal(csvData[i][4])),	//ocreturn
								new BigDecimal(oclogreturn).multiply(new BigDecimal("100")).setScale(4, RoundingMode.HALF_EVEN),	//oclogreturn
								new BigDecimal(csvData[i][4]).subtract(new BigDecimal(csvData[i][8])),	//coreturn
								new BigDecimal(cologreturn).multiply(new BigDecimal("100")).setScale(4, RoundingMode.HALF_EVEN),	//cologreturn
								new BigDecimal(csvData[i][7]).subtract(new BigDecimal(csvData[i][8])),	//ccreturn
								new BigDecimal(cclogreturn).multiply(new BigDecimal("100")).setScale(4, RoundingMode.HALF_EVEN)	//cclogreturn
								);
					} else{
						//Default Value
						equityDataModel = new EquityDataModel(							
								Integer.parseInt("0"),	//slidx
								"UNDEFINED",	//stock
								dateFormat.parse("01-Jan-70"),	//tradedate
								"Sun",	//tradedayofwk
								new BigDecimal("0"),	//openval
								new BigDecimal("0"),	//highval
								new BigDecimal("0"),	//lowval
								new BigDecimal("0"),	//closeval
								new BigDecimal("0"),	//prevcloseval
								Integer.parseInt("0"),	//totnumtrades
								Integer.parseInt("0"),	//tottradeqty
								new BigDecimal("0"),	//tottradeval
								//Derived Data Fields
								new BigDecimal("0"),	//ocreturn
								new BigDecimal("0"),	//oclogreturn
								new BigDecimal("0"),	//coreturn
								new BigDecimal("0"),	//cologreturn
								new BigDecimal("0"),	//ccreturn
								new BigDecimal("0")		//cclogreturn
								);
					}
					equityDataList.add(equityDataModel);
				}
				
				return equityDataList;
			}			
		});
		
		System.out.println("SparkCassandraDataUpdater: updateEquityTable: prepared equityDataRDD");
		
		//save the Equity Table
		javaFunctions(equityDataRDD).writerBuilder(CASSANDRA_OPTIONS_MASTER_DATA_KEYSPACE, CASSANDRA_OPTIONS_MASTER_KEYSPACE_EQUITY_TABLE, equityDataRowWriter)
										.saveToCassandra();
		System.out.println("SparkCassandraDataUpdater: updateEquityTable: saved equityDataRDD");
	}
	
	/**
	 * Routine to update NIFTY Master Table
	 * @param sc
	 */
	@SuppressWarnings("serial")
	private void updateNIFTYTable(JavaSparkContext sc){
		System.out.println("SparkCassandraDataUpdater: updateNIFTYTable: method called");
		
		//RDD - <Line>, since NIFTY.csv is a single file
		JavaRDD<String> csvFileDataRDD = sc.textFile(NIFTY_DATA_FILE_PATH);
		
		JavaRDD<NiftyDataModel> niftyDataRDD = csvFileDataRDD.map(new Function<String, NiftyDataModel>(){
			public NiftyDataModel call(String csvLine) throws Exception {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
				//Read the data for each Line in the CSV File
				String[] csvWords = csvLine.split(",");
				
				//DEBUG
//				if(!csvWords[0].equals("SlID")){
//					log.info("SparkCassandraDataUpdater.updateNIFTYTable.call(): ocreturn: " + new BigDecimal(csvWords[6]).subtract(new BigDecimal(csvWords[3])));
//					//log.info("SparkCassandraDataUpdater.updateNIFTYTable.call(): oclogreturn: " + new BigDecimal(Math.log(Math.abs(new BigDecimal(csvWords[6]).subtract(new BigDecimal(csvWords[3])).doubleValue()))));
//				}
				//
				
				//prepare the niftyDataModel, leave the first header line
				if(!csvWords[0].equals("SlID")){
					//Precisely take care of log operations
					Double oclogreturn = 0.00;
					if(new BigDecimal(csvWords[3]).compareTo(BigDecimal.ZERO) != 0){
						oclogreturn = Math.log(Math.abs(new BigDecimal(csvWords[6]).divide(new BigDecimal(csvWords[3]), 6, RoundingMode.HALF_UP).doubleValue()));
						if(Double.isInfinite(oclogreturn) || Double.isNaN(oclogreturn)){
							oclogreturn = 0.00;
						}
					}
					
					Double cologreturn = 0.00;
					if(new BigDecimal(csvWords[7]).compareTo(BigDecimal.ZERO) != 0){
						cologreturn = Math.log(Math.abs(new BigDecimal(csvWords[3]).divide(new BigDecimal(csvWords[7]), 6, RoundingMode.HALF_UP).doubleValue()));
						if(Double.isInfinite(cologreturn) || Double.isNaN(cologreturn)){
							cologreturn = 0.00;
						}
					}
					
					Double cclogreturn = 0.00;
					if(new BigDecimal(csvWords[7]).compareTo(BigDecimal.ZERO) != 0){
						cclogreturn = Math.log(Math.abs(new BigDecimal(csvWords[6]).divide(new BigDecimal(csvWords[7]), 6, RoundingMode.HALF_UP).doubleValue()));
						if(Double.isInfinite(cclogreturn) || Double.isNaN(cclogreturn)){
							cclogreturn = 0.00;
						}
					}
					
					NiftyDataModel niftyDataModel = new NiftyDataModel(							
								Integer.parseInt(csvWords[0]),	//slid
								dateFormat.parse(csvWords[1]),	//tradedate
								csvWords[2],	//tradedayofwk
								new BigDecimal(csvWords[3]),	//openval
								new BigDecimal(csvWords[4]),	//highval
								new BigDecimal(csvWords[5]),	//lowval
								new BigDecimal(csvWords[6]),	//closeval
								new BigDecimal(csvWords[7]),	//prevclose
								Long.parseLong(csvWords[8]),	//sharestraded
								new BigDecimal(csvWords[9]),	//turnoverincr
								//Derived Data Fields
								new BigDecimal(csvWords[6]).subtract(new BigDecimal(csvWords[3])),	//ocreturn
								new BigDecimal(oclogreturn).multiply(new BigDecimal("100")).setScale(4, RoundingMode.HALF_EVEN),	//oclogreturn
								new BigDecimal(csvWords[3]).subtract(new BigDecimal(csvWords[7])),	//coreturn
								new BigDecimal(cologreturn).multiply(new BigDecimal("100")).setScale(4, RoundingMode.HALF_EVEN),	//cologreturn
								new BigDecimal(csvWords[6]).subtract(new BigDecimal(csvWords[7])),	//ccreturn
								new BigDecimal(cclogreturn).multiply(new BigDecimal("100")).setScale(4, RoundingMode.HALF_EVEN)	//cclogreturn
								);
					
					return niftyDataModel;
				} else{
					//Case of header Line
					//return null;
					return new NiftyDataModel(
							//Default Values
							Integer.parseInt("0"),	//slid
							dateFormat.parse("01-Jan-1970"),	//tradedate
							"Sun",	//tradedayofwk
							new BigDecimal("0"),	//openval
							new BigDecimal("0"),	//highval
							new BigDecimal("0"),	//lowval
							new BigDecimal("0"),	//closeval
							new BigDecimal("0"),	//prevclose
							Long.parseLong("0"),	//sharestraded
							new BigDecimal("0"),	//turnoverincr
							//Derived Data Fields
							new BigDecimal("0"),	//ocreturn
							new BigDecimal("0"),	//oclogreturn
							new BigDecimal("0"),	//coreturn
							new BigDecimal("0"),	//cologreturn
							new BigDecimal("0"),	//ccreturn
							new BigDecimal("0")	//cclogreturn
							);
				}
			}			
		});
		
		System.out.println("SparkCassandraDataUpdater: updateNIFTYTable: prepared niftyDataRDD");
		
		//save the NIFTY Table
		javaFunctions(niftyDataRDD).writerBuilder(CASSANDRA_OPTIONS_MASTER_DATA_KEYSPACE, CASSANDRA_OPTIONS_MASTER_KEYSPACE_NIFTY_TABLE, niftyDataRowWriter)
										.saveToCassandra();
		System.out.println("SparkCassandraDataUpdater: updateNIFTYTable: saved niftyDataRDD");
	}
	
	/**
	 * Routine to update VIX Master Table
	 * @param sc
	 */
	@SuppressWarnings("serial")
	private void updateVIXTable(JavaSparkContext sc){
		System.out.println("SparkCassandraDataUpdater: updateVIXTable: method called");
		
		//RDD - <Line>, since VIX.csv is a single file
		JavaRDD<String> csvFileDataRDD = sc.textFile(VIX_DATA_FILE_PATH);
		
		JavaRDD<VIXDataModel> vixDataRDD = csvFileDataRDD.map(new Function<String, VIXDataModel>(){
			public VIXDataModel call(String csvLine) throws Exception {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
				//Read the data for each Line in the CSV File
				String[] csvWords = csvLine.split(",");
				
				//DEBUG
//				if(!csvWords[0].equals("SlID")){
//					log.info("SparkCassandraDataUpdater.updateVIXTable.call(): ocreturn: " + new BigDecimal(csvWords[6]).subtract(new BigDecimal(csvWords[3])));
//					//log.info("SparkCassandraDataUpdater.updateVIXTable.call(): oclogreturn: " + new BigDecimal(Math.log(Math.abs(new BigDecimal(csvWords[6]).subtract(new BigDecimal(csvWords[3])).doubleValue()))));
//					log.info("SparkCassandraDataUpdater.updateVIXTable.call(): coreturn: " + new BigDecimal(csvWords[3]).subtract(new BigDecimal(csvWords[7])));
//					//log.info("SparkCassandraDataUpdater.updateVIXTable.call(): cologreturn: " + new BigDecimal(Math.log(Math.abs(new BigDecimal(csvWords[3]).subtract(new BigDecimal(csvWords[7])).doubleValue()))));
//				}
				//
				
				//prepare the VIXDataModel, leave the first header line
				if(!csvWords[0].equals("SlID")){
					//Precisely take care of log operations
					Double oclogreturn = 0.00;
					if(new BigDecimal(csvWords[3]).compareTo(BigDecimal.ZERO) != 0){
						oclogreturn = Math.log(Math.abs(new BigDecimal(csvWords[6]).divide(new BigDecimal(csvWords[3]), 6, RoundingMode.HALF_UP).doubleValue()));
						if(Double.isInfinite(oclogreturn) || Double.isNaN(oclogreturn)){
							oclogreturn = 0.00;
						}
					}
					
					Double cologreturn = 0.00;
					if(new BigDecimal(csvWords[7]).compareTo(BigDecimal.ZERO) != 0){
						cologreturn = Math.log(Math.abs(new BigDecimal(csvWords[3]).divide(new BigDecimal(csvWords[7]), 6, RoundingMode.HALF_UP).doubleValue()));
						if(Double.isInfinite(cologreturn) || Double.isNaN(cologreturn)){
							cologreturn = 0.00;
						}
					}
					
					Double cclogreturn = 0.00;
					if(new BigDecimal(csvWords[7]).compareTo(BigDecimal.ZERO) != 0){
						cclogreturn = Math.log(Math.abs(new BigDecimal(csvWords[6]).divide(new BigDecimal(csvWords[7]), 6, RoundingMode.HALF_UP).doubleValue()));
						if(Double.isInfinite(cclogreturn) || Double.isNaN(cclogreturn)){
							cclogreturn = 0.00;
						}
					}
					
					VIXDataModel vixDataModel = new VIXDataModel(							
								Integer.parseInt(csvWords[0]),	//slid
								dateFormat.parse(csvWords[1]),	//tradedate
								csvWords[2],	//tradedayofwk
								new BigDecimal(csvWords[3]),	//openval
								new BigDecimal(csvWords[4]),	//highval
								new BigDecimal(csvWords[5]),	//lowval
								new BigDecimal(csvWords[6]),	//closeval
								new BigDecimal(csvWords[7]),	//prevclose
								new BigDecimal(csvWords[8]),	//change
								new BigDecimal(csvWords[9]),	//percntgChange
								//Derived Data Fields
								new BigDecimal(csvWords[6]).subtract(new BigDecimal(csvWords[3])),	//ocreturn
								new BigDecimal(oclogreturn).multiply(new BigDecimal("100")).setScale(4, RoundingMode.HALF_EVEN),	//oclogreturn
								new BigDecimal(csvWords[3]).subtract(new BigDecimal(csvWords[7])),	//coreturn
								new BigDecimal(cologreturn).multiply(new BigDecimal("100")).setScale(4, RoundingMode.HALF_EVEN),	//cologreturn
								new BigDecimal(csvWords[6]).subtract(new BigDecimal(csvWords[7])),	//ccreturn
								new BigDecimal(cclogreturn).multiply(new BigDecimal("100")).setScale(4, RoundingMode.HALF_EVEN)	//cclogreturn
								);
					
					return vixDataModel;
				} else{
					//Case of header Line
					//return null;
					return new VIXDataModel(							
							Integer.parseInt("0"),	//slid
							dateFormat.parse("01-Jan-1970"),	//tradedate
							"Sun",	//tradedayofwk
							new BigDecimal("0"),	//openval
							new BigDecimal("0"),	//highval
							new BigDecimal("0"),	//lowval
							new BigDecimal("0"),	//closeval
							new BigDecimal("0"),	//prevclose
							new BigDecimal("0"),	//change
							new BigDecimal("0"),	//percntgChange
							//Derived Data Fields
							new BigDecimal("0"),	//ocreturn
							new BigDecimal("0"),	//oclogreturn
							new BigDecimal("0"),	//coreturn
							new BigDecimal("0"),	//cologreturn
							new BigDecimal("0"),	//ccreturn
							new BigDecimal("0")	//cclogreturn
							);
				}
			}			
		});
		
		System.out.println("SparkCassandraDataUpdater: updateVIXTable: prepared vixDataRDD");
		
		//save the VIX Table
		javaFunctions(vixDataRDD).writerBuilder(CASSANDRA_OPTIONS_MASTER_DATA_KEYSPACE, CASSANDRA_OPTIONS_MASTER_KEYSPACE_VIX_TABLE, vixDataRowWriter)
										.saveToCassandra();
		System.out.println("SparkCassandraDataUpdater: updateVIXTable: saved vixDataRDD");
	}
    
    /**
     * Routine to Prepare the Sorted Trading Days List
     */
    private void prepareDateList(){
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
    	Date tradeDate = null;
    	//get list of files in BhavCopy directory
    	File[] files = new File(FNO_DATA_DIR_PATH).listFiles();
    	List<Date> sortedDateList = new ArrayList<Date>();
    	
    	System.out.println("SparkCassandraDataUpdater.prepareDateList(): fileNum: " + files.length);
    	
    	//traverse through each file
    	for(int fileCount = 0; fileCount < files.length; fileCount++){
    		BufferedReader reader;
    		try {
				//read the file
				reader = new BufferedReader(new FileReader(files[fileCount]));
				//first line is a header line, skip that line
				reader.readLine();
				//read the second line and parse
				String dataLine = reader.readLine();
				//split the line on basis of ","
				String[] dataSplit = dataLine.split(",");
				//get the stock trade date
				String date = dataSplit[14];
				tradeDate = dateFormat.parse(date);
				sortedDateList.add(tradeDate);
				//after reading the whole file close reader
				reader.close();
			} catch (FileNotFoundException e) {
				System.out
						.println("SparkCassandraDataUpdater.prepareDateList(): FileNotFoundException on file: " + files[fileCount].getName());
			} catch (IOException e) {
				System.out
						.println("SparkCassandraDataUpdater.prepareDateList(): IOException on file: " + files[fileCount].getName());
			} catch (ParseException e) {
				System.out
						.println("SparkCassandraDataUpdater.prepareDateList(): ParseException for parsing date: " + tradeDate);
			}
    	}
    	
    	//Sort the Date List
    	Collections.sort(sortedDateList, new Comparator<Date>(){
    		public int compare(Date date1, Date date2){
    			return date1.compareTo(date2);
    		}
    	});
    	
    	//Update the global Date List
    	Iterator<Date> listIt = sortedDateList.iterator();
    	int dateCount = 0;
    	while(listIt.hasNext()){
    		dateCount++;
    		Date date = listIt.next();
    		TradingDateDataModel tradingDateDataModel = new TradingDateDataModel(dateCount, date);
    		sortedTradingDateList.add(tradingDateDataModel);
    	}
    }
    
    /**
     * Main Method
     * @param args
     */
    public static void main(String[] args) {
    	System.out.println("SparkCassandraDataUpdater: main: method called");
    	System.out.println("SparkCassandraDataUpdater: main: Spark IP: " + SPARK_MASTER_IP + 
    										", cassandra IP: " + CASSANDRA_RING_IP);
    	
    	//Spark Configuration
        SparkConf conf = new SparkConf();
        conf.setAppName("Option Strategies Spark Analytics");
        conf.setMaster(SPARK_MASTER_IP);
        conf.set("spark.cassandra.connection.host", CASSANDRA_RING_IP);

        //Instantiate and run the job spark job application
        SparkCassandraDataUpdater sparkCassandraDataUpdater = new SparkCassandraDataUpdater(conf);
        sparkCassandraDataUpdater.run();
    }
    
//    /**
//     * TEST main method
//     * @param args
//     */
//    public static void main(String[] args){
//    	SparkCassandraDataUpdater sparkCassandraDataUpdater = new SparkCassandraDataUpdater();
//    	sparkCassandraDataUpdater.prepareDateList();
//    }
}
