package com.ritesh.optionstrategies.spark.cassandraupdate.dao;

import java.io.Serializable;

import com.datastax.spark.connector.ColumnRef;
import com.datastax.spark.connector.cql.TableDef;
import com.datastax.spark.connector.writer.RowWriter;
import com.datastax.spark.connector.writer.RowWriterFactory;
import com.ritesh.optionstrategies.spark.cassandraupdate.model.EquityDataModel;

import scala.collection.IndexedSeq;
import scala.collection.Seq;

public class EquityDataRowWriter implements RowWriter<EquityDataModel>{
	private static final long serialVersionUID = 1L;
	
	private static RowWriter<EquityDataModel> writer = new EquityDataRowWriter();
	
	// Factory
    public static class EquityDataRowWriterFactory implements RowWriterFactory<EquityDataModel>, Serializable{
        private static final long serialVersionUID = 1L;
        
		public RowWriter<EquityDataModel> rowWriter(TableDef arg0, IndexedSeq<ColumnRef> arg1) {
			return writer;
		}        
    }

	public Seq<String> columnNames() {
		return scala.collection.JavaConversions.asScalaBuffer(EquityDataModel.columns()).toList();
	}

	public void readColumnValues(EquityDataModel equityDataModel, Object[] buffer) {
		buffer[0] = equityDataModel.getSlIdx();	//slIdx
		buffer[1] = equityDataModel.getStock();	//stock
		buffer[2] = equityDataModel.getTradeDate();	//tradeDate
		buffer[3] = equityDataModel.getTradeDayofWk();	//tradeDayofWk
		buffer[4] = equityDataModel.getOpenVal();	//openVal
		buffer[5] = equityDataModel.getHighVal();	//highVal
		buffer[6] = equityDataModel.getLowVal();	//lowVal
		buffer[7] = equityDataModel.getCloseVal();	//closeVal
		buffer[8] = equityDataModel.getPrevCloseVal();	//prevCloseVal
		buffer[9] = equityDataModel.getTotNumTrades();	//totNumTrades
		buffer[10] = equityDataModel.getTotTradeQty();	//totTradeQty
		buffer[11] = equityDataModel.getTotTradeVal();	//totTradeVal
		buffer[12] = equityDataModel.getDerivedOCReturn();	//derivedOCReturn
		buffer[13] = equityDataModel.getDerivedOCLogReturn();	//derivedOCLogReturn
		buffer[14] = equityDataModel.getDerivedCOReturn();	//derivedCOReturn
		buffer[15] = equityDataModel.getDerivedCOLogReturn();	//derivedCOLogReturn
		buffer[16] = equityDataModel.getDerivedCCReturn();	//derivedCCReturn
		buffer[17] = equityDataModel.getDerivedCCLogReturn();	//derivedCCLogReturn
	}
}
