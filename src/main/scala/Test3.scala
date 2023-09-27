object Test3 extends App{
  val value = scala.io.StdIn.readLine().toInt
  val list2 = scala.io.StdIn.readLine().split(" ").toList.map(_.toInt)
  val win = scala.io.StdIn.readLine().split(" ").toList.map(_.toInt)

  def sort(start: Int, end: Int, list: List[Int], win: List[Int]): String = {
    val newList = list.slice(0, start) ::: list.slice(start, end).sorted ::: list.drop(end)
        if (newList.zip(win).count(x => x._1 == x._2) == value) "Yes"
        else
          if (start != end) sort(start + 1, end, list, win)
          else
            if (end >= 1) sort(start = 0, end - 1, list, win)
            else "No"
  }

  println(sort(0, value, list2, win))
}
