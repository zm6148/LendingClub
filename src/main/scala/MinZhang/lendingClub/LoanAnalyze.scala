package MinZhang.lendingClub

import MinZhang.lendingClub.aggregator.LoanInfoAggregator
import MinZhang.lendingClub.io.{LoanAggregationWriter, LoanReader, RejectionReader}
import org.apache.spark.internal.Logging
import org.apache.spark.sql.SparkSession

object LoanAnalyze extends Logging
  with LoanReader
  with RejectionReader
  with LoanInfoAggregator
  with LoanAggregationWriter {

  def main(args: Array[String]) :Unit ={

    if (args.length !=3){
      logError("3 inputs needed")
    }
    val spark = SparkSession.builder().appName("Loan-analyze").getOrCreate()

    val loanInputPath = args(0)
    val rejectionPath = args(1)
    val outputpPath = args(2)

    val loanDs = readLoanData(loanInputPath, spark)
    val rejectionDs = readRejectionData(rejectionPath, spark)
    val aggregatedDf = loanInfoAggregator(loanDs, rejectionDs, spark)

    writeLoanAggregateData(aggregatedDf, outputpPath)

  }

}
