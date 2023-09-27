import scala.annotation.tailrec

object TEST7 extends App {

  def tictactoe(moves: Array[Array[Int]]): Unit = {

    def checkDiagonal(diag: List[Char]) = {
      if (diag.count(_ == diag.head) == 3) "Yes"
      else "No"
    }
//
    def checkColumn(column: List[Char]) = {
      if (column.count(_ == column.head) == 3) "Yes"
      else "No"
    }

    def checkRow(row: List[Char]) = {
      if (row.count(_ == row.head) == 3) "Yes"
      else "No"
    }

    def fullCheck : List[String] = {
      checkRow() ++ checkColumn() ++ checkDiagonal()
    }

    screen.map(x =>)

  }
  val mov = Array(Array(0,0), Array(1, 0))
  tictactoe(mov)
}
