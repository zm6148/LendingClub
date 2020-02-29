package MinZhang.lendingClub.aggregator

import MinZhang.lendingClub.types.LoanType
import org.apache.spark.internal.Logging
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

trait LoanInfoAggregator extends Logging {

  def loanInfoAggregator(rejectionDs: Dataset[LoanType],
                         loanDs: Dataset[LoanType],
                         spark: SparkSession): DataFrame = {
    import spark.implicits._

    logInfo (msg = "Aggregate data based on certain columns")

    val unionDs = rejectionDs.unionByName(loanDs)
    unionDs.groupBy("term", "home_ownership", "addr_state", "title", "emp_length")
      .agg(avg($"loan_amnt").as("avg_loan-amnt"),
        avg($"int_rate").as("avg_int_rate"),
        avg($"annual_inc").as("avg_annual_inc"),
        avg($"DTI").as("avg_DTI"),
        sum($"has_collection").as("sum_has_collection"),
        avg($"installment").as("avg_installment")
      )
  }
}
