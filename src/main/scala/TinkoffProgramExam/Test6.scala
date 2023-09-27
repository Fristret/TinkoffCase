package TinkoffProgramExam

import scala.util.Random

case class Ghost(pos: Int, group: Int, prev: Int = 1)

case class Group(ghosts: List[Ghost]) {
  def concat(list: Group): Group = Group(ghosts ++ list.ghosts)
}

sealed trait Question
case class Question1(x: Int, y:Int) extends Question
case class Question2(x: Int, y:Int) extends Question
case class Question3(x: Int) extends Question

object Question {
  def createQues(num: Int, n: Int, genY: Int => Int): Question = {
    num match
    {
      case x if x == 1 =>
        val x = Random.between(1, n + 1)
        val y = genY(x)
        Question1(x, y)
      case x if x == 2 =>
        val x = Random.between(1, n + 1)
        val y = genY(x)
        Question2(x, y)
      case x if x == 3 =>
        val x = Random.between(1, n + 1)
        Question3(x)
    }
  }
}

object Test6 extends App{

  def createGroup(list: List[Ghost]): Map[Int, Group] = list
    .map(x => (x.group, Group(List(x)))).toMap

  def createN(n: Int): List[Ghost] =
    if (n <= 1) List(Ghost(n, n))
    else List(Ghost(n, n)) ++ createN(n - 1)

  val (n, m): (Int, Int) = (7, 10)
  val second: List[Question] = generateList(m)
  val ghosts = createN(n).reverse
  val groups: Map[Int, Group] = createGroup(ghosts)

  def generateList(count: Int): List[Question] = {

    def genY(comp: Int): Int = {
      val buf = Random.between(1, n + 1)
      if (buf == comp) genY(comp)
      else buf
    }
    if (count <= 0) List(Question.createQues(Random.between(1, 4), n, genY))
    else List(Question.createQues(Random.between(1, 4), n, genY)) ++ generateList(count - 1)
  }

  def moveGhosts(x: Int, y: Int, groups: Map[Int, Group]): Map[Int, Group] = {
    val firstGroup = groups.find(gr => gr._2.ghosts.exists(a => a.pos == x)).get
    val secondGroup = groups.find(gr => gr._2.ghosts.exists(a => a.pos == y)).get
    if (firstGroup._1 == secondGroup._1) groups
    else {
      val updatedFG = firstGroup.copy(_2 = Group(firstGroup._2.ghosts.map(x => x.copy(prev = x.prev + 1))))
      val updatedSG = secondGroup.copy(_2 = Group(secondGroup._2.ghosts.map(x => x.copy(group = firstGroup._1, prev = x.prev + 1))))
      groups.updated(updatedFG._1, updatedFG._2.concat(updatedSG._2)).removed(updatedSG._1)
    }
  }

  def answerSecond(x: Int, y: Int, groups: Map[Int, Group]) = {
    groups.find(gr => gr._2.ghosts.exists(a => a.pos == x) && gr._2.ghosts.exists(a => a.pos == y)) match {
      case Some(x) => "YES"
      case None => "NO"
    }
  }
  def answerThird(x: Int, groups: Map[Int, Group]) = {
    groups.find(gr => gr._2.ghosts.exists(a => a.pos == x)).get._2.ghosts.find(a => a.pos == x).get.prev.toString
  }



  def dealWithList(list: List[Question], groups: Map[Int, Group]): List[String] = {

     if (list.nonEmpty)
      list
        .head match {
        case Question1(x, y) =>
          val newMap = moveGhosts(x, y, groups)
          List(" ") ++ dealWithList(list.drop(1), newMap)
        case Question2(x, y) => List(answerSecond(x, y, groups)) ++ dealWithList(list.drop(1), groups)
        case Question3(x) => List(answerThird(x, groups)) ++ dealWithList(list.drop(1), groups)
      }
      else List(" ")
  }.filterNot(_ == " ")

  println(second)
  println(dealWithList(second, groups))
}
