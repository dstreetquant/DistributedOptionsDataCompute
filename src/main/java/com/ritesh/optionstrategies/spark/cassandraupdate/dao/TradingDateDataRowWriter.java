package com.ritesh.optionstrategies.spark.cassandraupdate.dao;

import java.io.Serializable;

import com.datastax.spark.connector.ColumnRef;
import com.datastax.spark.connector.cql.TableDef;
import com.datastax.spark.connector.writer.RowWriter;
import com.datastax.spark.connector.writer.RowWriterFactory;
import com.ritesh.optionstrategies.spark.cassandraupdate.model.TradingDateDataModel;

import scala.collection.IndexedSeq;
import scala.collection.Seq;

public class TradingDateDataRowWriter implements RowWriter<TradingDateDataModel>{
	private static final long serialVersionUID = 1L;
	
	private static RowWriter<TradingDateDataModel> writer = new TradingDateDataRowWriter();
	
	// Factory
    public static class TradingDateDataRowWriterFactory implements RowWriterFactory<TradingDateDataModel>, Serializable{
        private static final long serialVersionUID = 1L;
        
		public RowWriter<TradingDateDataModel> rowWriter(TableDef arg0, IndexedSeq<ColumnRef> arg1) {
			return writer;
		}        
    }

	public Seq<String> columnNames() {
		return scala.collection.JavaConversions.asScalaBuffer(TradingDateDataModel.columns()).toList();
	}

	public void readColumnValues(TradingDateDataModel tradingDateDataModel, Object[] buffer) {
		buffer[0] = tradingDateDataModel.getDateSlId();	//Date Sl ID
		buffer[1] = tradingDateDataModel.getDate();	//Date;
	}
}

