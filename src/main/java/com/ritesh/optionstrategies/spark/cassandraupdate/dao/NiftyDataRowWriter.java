package com.ritesh.optionstrategies.spark.cassandraupdate.dao;

import java.io.Serializable;

import com.datastax.spark.connector.ColumnRef;
import com.datastax.spark.connector.cql.TableDef;
import com.datastax.spark.connector.writer.RowWriter;
import com.datastax.spark.connector.writer.RowWriterFactory;
import com.ritesh.optionstrategies.spark.cassandraupdate.model.NiftyDataModel;

import scala.collection.IndexedSeq;
import scala.collection.Seq;

public class NiftyDataRowWriter implements RowWriter<NiftyDataModel>{
	private static final long serialVersionUID = 1L;
	
	private static RowWriter<NiftyDataModel> writer = new NiftyDataRowWriter();
	
	// Factory
    public static class NiftyDataRowWriterFactory implements RowWriterFactory<NiftyDataModel>, Serializable{
        private static final long serialVersionUID = 1L;
        
		public RowWriter<NiftyDataModel> rowWriter(TableDef arg0, IndexedSeq<ColumnRef> arg1) {
			return writer;
		}        
    }

	public Seq<String> columnNames() {
		return scala.collection.JavaConversions.asScalaBuffer(NiftyDataModel.columns()).toList();
	}

	public void readColumnValues(NiftyDataModel niftyDataModel, Object[] buffer) {
		buffer[0] = niftyDataModel.getSlId();	//slId
		buffer[1] = niftyDataModel.getTradeDate();	//tradeDate
		buffer[2] = niftyDataModel.getTradeDayofWk();	//tradeDayofWk
		buffer[3] = niftyDataModel.getOpenVal();	//openVal
		buffer[4] = niftyDataModel.getHighVal();	//highVal
		buffer[5] = niftyDataModel.getLowVal();	//lowVal
		buffer[6] = niftyDataModel.getCloseVal();	//closeVal
		buffer[7] = niftyDataModel.getPrevClose();	//prevClose
		buffer[8] = niftyDataModel.getSharesTraded();	//sharesTraded
		buffer[9] = niftyDataModel.getTurnOverInCr();	//turnOverinCr
		buffer[10] = niftyDataModel.getDerivedOCReturn();	//derivedOCReturn
		buffer[11] = niftyDataModel.getDerivedOCLogReturn();	//derivedOCLogReturn
		buffer[12] = niftyDataModel.getDerivedCOReturn();	//derivedCOReturn
		buffer[13] = niftyDataModel.getDerivedCOLogReturn();	//derivedCOLogReturn
		buffer[14] = niftyDataModel.getDerivedCCReturn();	//derivedCCReturn
		buffer[15] = niftyDataModel.getDerivedCCLogReturn();	//derivedCCLogReturn
	}
}
