object Test2 extends App{
  val value = scala.io.StdIn.readLine()
  val string1 = "sheriff"

  def count(x: String, string: String): Int = {
    val map = x.foldLeft(Map.empty[Char, Int])((acc, x) => {
      if (string.contains(x))
        if (acc.contains(x)) acc.updated(x, acc(x) + 1)
        else acc.updated(x, 1)
      else acc
    })
    if (map.values.size + 1 == string.length) {
      val newMap = map.updated('f', map.get('f').map(_ / 2).get)
      newMap.values.toList.min
    }
    else 0
  }

  println(count(value, string1))
}
