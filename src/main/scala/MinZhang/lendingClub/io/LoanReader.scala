package MinZhang.lendingClub.io
import MinZhang.lendingClub.types.LoanType
import org.apache.spark.internal.Logging
import org.apache.spark.sql.{Dataset, SparkSession}
import org.apache.spark.sql.functions._
trait LoanReader extends Logging {

  def readLoanData(inputPath: String, spark: SparkSession): Dataset[LoanType] = {
    import spark.implicits._

    // read data from
    val rawData = spark.read.option("header", "true").csv(inputPath)
    logInfo (msg = "reading data from %s".format(inputPath))

    // $ means column name
    // not fully paid (still has collection pending)
    val filterRawDf = rawData.filter($"loan_status" =!= "Fully Paid")// not fully paid

    // load which columns
    val fields = List("Loan_amnt", "term", "int_rate", "installment", "home_ownership", "annual_inc", "emp_length",
    "title", "addr_state", "loan_status", "tot_coll_amt").map(col)

    // filterRawDf.columns :-
    // add two coloumns
    filterRawDf.select(fields:_*)
      .withColumn("has_collection", when($"tot_coll_amt" =!= "0", value = 1).otherwise(0).as("has_collection"))
      .withColumn("DTI", $"installment"/($"annual_inc"/12))
      .drop("loan_status")
      // same as dataset
      .as[LoanType]
  }
}
