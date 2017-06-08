package com.ritesh.optionstrategies.spark.cassandraupdate.dao;

import java.io.Serializable;

import com.datastax.spark.connector.ColumnRef;
import com.datastax.spark.connector.cql.TableDef;
import com.datastax.spark.connector.writer.RowWriter;
import com.datastax.spark.connector.writer.RowWriterFactory;
import com.ritesh.optionstrategies.spark.cassandraupdate.model.FnODataModel;

import scala.collection.IndexedSeq;
import scala.collection.Seq;

public class FnODataRowWriter implements RowWriter<FnODataModel>{
	private static final long serialVersionUID = 1L;
	
	private static RowWriter<FnODataModel> writer = new FnODataRowWriter();
	
	// Factory
    public static class FnODataRowWriterFactory implements RowWriterFactory<FnODataModel>, Serializable{
        private static final long serialVersionUID = 1L;
        
		public RowWriter<FnODataModel> rowWriter(TableDef arg0, IndexedSeq<ColumnRef> arg1) {
			return writer;
		}        
    }

	public Seq<String> columnNames() {
		return scala.collection.JavaConversions.asScalaBuffer(FnODataModel.columns()).toList();
	}

	public void readColumnValues(FnODataModel fnODataModel, Object[] buffer) {
		buffer[0] = fnODataModel.getInstrumentType();	//instrumentType
		buffer[1] = fnODataModel.getSymbol();	//symbol;
		buffer[2] = fnODataModel.getExpiryDate();	//expiryDate;
		buffer[3] = fnODataModel.getStrikePrice();	//strikePrice;
		buffer[4] = fnODataModel.getOptionType();	//optionType;
		buffer[5] = fnODataModel.getOpenVal();	//openVal;
		buffer[6] = fnODataModel.getHighVal();	//highVal;
		buffer[7] = fnODataModel.getLowVal();	//lowVal;
		buffer[8] = fnODataModel.getCloseVal();	//closeVal;
		buffer[9] = fnODataModel.getLastAdjustedVal();	//lastAdjustedVal;
		buffer[10] = fnODataModel.getContracts();	//contracts;
		buffer[11] = fnODataModel.getValInLakh();	//valInLakh;
		buffer[12] = fnODataModel.getOpenInterest();	//openInterest;
		buffer[13] = fnODataModel.getChangeOpenInterest();	//changeOpenInterest;
		buffer[14] = fnODataModel.getTradeDate();	//tradeDate;
		buffer[15] = fnODataModel.getTradeDayofWk();	//tradeDayofWk;
		buffer[16] = fnODataModel.getDerivedReturn();	//derivedReturn;
		buffer[17] = fnODataModel.getDerivedLogReturn();	//derivedLogReturn;
	}
}
