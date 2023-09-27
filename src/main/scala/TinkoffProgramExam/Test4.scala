package TinkoffProgramExam

object Test4 extends App {

  case class ValueTree(value: Int, acc: Int, nextValue: Option[ValueTree]) {
    def toList: List[Int] = {
      if (this.nextValue.isDefined) List(this.value) ++ this.nextValue.get.toList
      else List(this.value)
    }

    def add(value: Int, acc: Int): ValueTree = {
      if (this.nextValue.isDefined) this.copy(nextValue=Option(this.nextValue.get.add(value, acc)))
      else this.copy(nextValue = Option(ValueTree(value, acc, None)))
    }

      def getLast: ValueTree = {
        if (this.nextValue.isDefined) this.nextValue.get.getLast
        else this
      }

    def removeLast: ValueTree = {
      if (this.nextValue.isDefined)
        if (this.nextValue.get.nextValue.isDefined) this.copy(nextValue = Option(this.nextValue.get.removeLast))
        else this.copy(nextValue = None)
      else this
    }

    def sum: Int = {
      if (this.nextValue.isDefined) this.value + this.nextValue.get.sum
      else this.value
    }

    def length: Int = {
      if (this.nextValue.isDefined) 1 + this.nextValue.get.length
      else 1
    }
  }

  def solution(sum: Int, list: List[Int]): List[List[Int]] = {

      def count(valueTree: ValueTree, nList: List[Int], n: Int = 1): ValueTree = {
        if (n < nList.length) {
          val nValTree = valueTree.add(nList(n), n)
          println(nValTree, "CHECK", n, nValTree.sum, nList)
          sum - nValTree.sum match {
            case x if x > 0 => count(nValTree, nList, n + 1)
            case x if x < 0 => count(valueTree, nList, n+1)
            case x if x == 0 => nValTree
          }
        }
        else if (valueTree.length > 1) {
          count(valueTree.removeLast, nList, valueTree.getLast.acc + 1)
        }
        else ValueTree(-1,0, None)
      }

    list.map(x => count(ValueTree(x, list.indexOf(x), None), (list.drop(list.indexOf(x)) ++ list.drop(list.indexOf(x))).sorted).toList)
  }

  println(solution(30, List(1, 2, 3, 4, 5, 6, 7, 8, 9)))

}