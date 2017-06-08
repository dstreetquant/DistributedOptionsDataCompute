package com.ritesh.optionstrategies.data.datafetcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NSEFnOBhavcopyURLFetcher {

	//All FnO URL List File
	private static final String bhavCopyFile = "D:\\RiteshComputer\\Trading\\OptionStrategies\\URLlinks\\foNSEBhavCopyLink.txt";
	//private static final String bhavCopyFile = "D:\\RiteshComputer\\Trading\\OptionStrategies\\URLlinks\\stocksBhavCopyLinks_1.txt";
	//FnO Holiday URL List File
	private static final String bhavCopyHolidayFile = "D:\\RiteshComputer\\Trading\\OptionStrategies\\URLlinks\\foNSEBhavCopyHolidayLinks.txt";
	//private static final String bhavCopyHolidayFile = "D:\\RiteshComputer\\Trading\\OptionStrategies\\URLlinks\\stocksBhavCopyHolidayLinks.txt";
	//Output zip File Path
	private static final String zipFilePath = "D:\\RiteshComputer\\Trading\\OptionStrategies\\test\\";
	
	/**
	 * Public routine to download the zip file
	 */
	public void downloadZipFile() {
		URL url = null;		
		int countValidURL = 0;
		
		//List contains all URLs
		List<String> bhavCopyURLList = new ArrayList<String>();
		//List containing Holiday URLs
		List<String> bhavCopyHolidayURLList = new ArrayList<String>();
		
		//Read the complete list of URLs
		try {
			String urlLine = null;
			int countBhavCopyURL = 0;
			BufferedReader reader = new BufferedReader(new FileReader(new File(bhavCopyFile)));
			
			while((urlLine = reader.readLine()) != null){
				//add each URL in all URL list
				bhavCopyURLList.add(urlLine);
				countBhavCopyURL++;
			}
			
			reader.close();
		} catch (FileNotFoundException e1) {
			System.out.println("NSEFnOBhavcopyURLFetcher.downloadZipFile(): FileNotFoundException on file: " + bhavCopyFile);
		} catch (IOException e) {
			System.out.println("NSEFnOBhavcopyURLFetcher.downloadZipFile(): IOException on file: " + bhavCopyFile);
		}
		
		//Read the list of Holiday URLs
		try {
			String urlLine = null;
			int countHolidayURL = 0;
			BufferedReader reader = new BufferedReader(new FileReader(new File(bhavCopyHolidayFile)));
		
			while((urlLine = reader.readLine()) != null){
				//add the URL in holiday URL list
				bhavCopyHolidayURLList.add(urlLine);
				countHolidayURL++;
			}
			
			reader.close();
		} catch (FileNotFoundException e1) {
			System.out.println("NSEFnOBhavcopyURLFetcher.downloadZipFile(): FileNotFoundException on file: " + bhavCopyHolidayFile);
		} catch (IOException e) {
			System.out.println("NSEFnOBhavcopyURLFetcher.downloadZipFile(): IOException on file: " + bhavCopyHolidayFile);
		}		
			
		System.out.println("NSEFnOBhavcopyURLFetcher.downloadZipFile(): All URL list size: " + bhavCopyURLList.size());
		System.out.println("NSEFnOBhavcopyURLFetcher.downloadZipFile(): Holiday URL list size: " + bhavCopyHolidayURLList.size());
		
		Iterator<String> listIt = bhavCopyURLList.iterator();
		while(listIt.hasNext()){
			//Fetch one by one URL from all URL list
			String urlLine = listIt.next();
			
			//Validate if the day was a NSE holiday or not
			if(!bhavCopyHolidayURLList.contains(urlLine)){
				countValidURL++;
				InputStream in = null;
				
				System.out.println("NSEFnOBhavcopyURLFetcher.downloadZipFile(): urlLine: " + urlLine);			
				
				//Get the data from URL
				try {					
					url = new URL(urlLine);
					URLConnection conn = url.openConnection();
					in = conn.getInputStream();
				} catch (MalformedURLException e) {
					System.out.println("NSEFnOBhavcopyURLFetcher.downloadZipFile(): MalformedURLException on URL: " + urlLine);
				} catch (IOException e) {
					System.out.println("NSEFnOBhavcopyURLFetcher.downloadZipFile(): IOException on URL: " + urlLine);
				}
				
				//All files are named serially counted
				String fileName = zipFilePath + countValidURL + ".zip";
				FileOutputStream out = null;
				
				//Read each valid URL data and write it to corresponding file
				try {
					out = new FileOutputStream(fileName);
					
					if((out != null) && (in != null)){
						byte[] b = new byte[1024];
						int count;
						while ((count = in.read(b)) >= 0) {
							out.write(b, 0, count);
						}
					} else{
						System.out.println("NSEFnOBhavcopyURLFetcher.downloadZipFile(): out or in is null while read/write");
					}
				} catch (FileNotFoundException e) {
					System.out.println("NSEFnOBhavcopyURLFetcher.downloadZipFile(): FileNotFoundException on File: " + fileName);
				} catch (IOException e) {
					System.out.println("NSEFnOBhavcopyURLFetcher.downloadZipFile(): IOException while read/write");
				}
				
				//Flush and Close opened FileOutput and URL Stream handlers
				try {				
					if(out != null){
						out.flush();
						out.close();
					} else{
						System.out.println("NSEFnOBhavcopyURLFetcher.downloadZipFile(): out is null while flushing/closing");
					}
					
					if(in != null){
						in.close();
					} else{
						System.out.println("NSEFnOBhavcopyURLFetcher.downloadZipFile(): in is null while closing");
					}
				} catch (IOException e) {
					System.out.println("NSEFnOBhavcopyURLFetcher.downloadZipFile(): IOException while flushing/closing");
				}
			}
		}
	}
	
	/**
	 * Main Method
	 * @param args
	 */
//	public static void main(String[] args){
//		NSEFnOBhavcopyURLFetcher fnoBhavCopyURLFetcher = new NSEFnOBhavcopyURLFetcher();
//		fnoBhavCopyURLFetcher.downloadZipFile();
//	}
}
