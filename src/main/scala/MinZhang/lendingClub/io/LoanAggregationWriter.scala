package MinZhang.lendingClub.io

import org.apache.spark.internal.Logging
import org.apache.spark.sql.{DataFrame, SaveMode}

trait LoanAggregationWriter extends Logging{
  def writeLoanAggregateData (outputDataframe: DataFrame, outputPath: String): Unit = {
    logInfo (msg = "saving data to %s".format(outputPath))
    outputDataframe.repartition(1).write.mode(SaveMode.Overwrite).json(outputPath)
  }
}


