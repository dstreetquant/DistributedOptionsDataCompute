package com.ritesh.optionstrategies.spark.cassandraupdate.dao;

import java.io.Serializable;

import com.datastax.spark.connector.ColumnRef;
import com.datastax.spark.connector.cql.TableDef;
import com.datastax.spark.connector.writer.RowWriter;
import com.datastax.spark.connector.writer.RowWriterFactory;
import com.ritesh.optionstrategies.spark.cassandraupdate.model.NiftyDataModel;
import com.ritesh.optionstrategies.spark.cassandraupdate.model.VIXDataModel;

import scala.collection.IndexedSeq;
import scala.collection.Seq;

public class VIXDataRowWriter implements RowWriter<VIXDataModel>{
	private static final long serialVersionUID = 1L;
	
	private static RowWriter<VIXDataModel> writer = new VIXDataRowWriter();
	
	// Factory
    public static class VIXDataRowWriterFactory implements RowWriterFactory<VIXDataModel>, Serializable{
        private static final long serialVersionUID = 1L;
        
		public RowWriter<VIXDataModel> rowWriter(TableDef arg0, IndexedSeq<ColumnRef> arg1) {
			return writer;
		}        
    }

	public Seq<String> columnNames() {
		return scala.collection.JavaConversions.asScalaBuffer(VIXDataModel.columns()).toList();
	}

	public void readColumnValues(VIXDataModel vixDataModel, Object[] buffer) {
		buffer[0] = vixDataModel.getSlId();	//slId
		buffer[1] = vixDataModel.getTradeDate();	//tradeDate
		buffer[2] = vixDataModel.getTradeDayofWk();	//tradeDayofWk
		buffer[3] = vixDataModel.getOpenVal();	//openVal
		buffer[4] = vixDataModel.getHighVal();	//highVal
		buffer[5] = vixDataModel.getLowVal();	//lowVal
		buffer[6] = vixDataModel.getCloseVal();	//closeVal
		buffer[7] = vixDataModel.getPrevClose();	//prevClose
		buffer[8] = vixDataModel.getChange();	//change
		buffer[9] = vixDataModel.getPercntgChange();	//percntgChange
		buffer[10] = vixDataModel.getDerivedOCReturn();	//derivedOCReturn
		buffer[11] = vixDataModel.getDerivedOCLogReturn();	//derivedOCLogReturn
		buffer[12] = vixDataModel.getDerivedCOReturn();	//derivedCOReturn
		buffer[13] = vixDataModel.getDerivedCOLogReturn();	//derivedCOLogReturn
		buffer[14] = vixDataModel.getDerivedCCReturn();	//derivedCCReturn
		buffer[15] = vixDataModel.getDerivedCCLogReturn();	//derivedCCLogReturn
	}
}
