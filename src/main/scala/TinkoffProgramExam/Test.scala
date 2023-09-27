package TinkoffProgramExam

object Test extends App{
  val value = scala.io.StdIn.readLine().split(" ").toList(1).toInt
  val price = scala.io.StdIn.readLine().split(" ").toList.map(_.toInt).sorted.reverse

  def buy1(value: Int, priceList: List[Int]): Int = {
    val newList = priceList.map(x => if (value >= x) x else 0)
    if (newList.max != 0) newList.max
    else 0
  }

  println(buy1(value, price))
}
