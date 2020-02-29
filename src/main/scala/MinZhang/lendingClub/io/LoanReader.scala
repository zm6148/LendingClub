package MinZhang.lendingClub.io
import MinZhang.lendingClub.types.LoanType
import org.apache.spark.internal.Logging
import org.apache.spark.sql.{Dataset, SparkSession}

trait LoanReader extends Logging {

  def readLoanData(inputPath: String, spark: SparkSession): Dataset[LoanType] = {

    



  }

}
