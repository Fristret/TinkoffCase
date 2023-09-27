import scala.annotation.tailrec
import scala.util.Random

object Test5 extends App{

  case class Town(number: Int)
  case class Road(town1: Town, town2: Town, roadLength: Int)


  val nTown = Random.between(6, 20)
  val nRoads = Random.between(6, 20)
  val listOfRoads = lineGen(nRoads)

  def genTown = {
    Town(Random.between(1, nTown))
  }

  def lineGen(n: Int): List[Road] = {
    if (n > 1) List(Road(genTown, genTown, Random.between(1, 10))) ++ lineGen(n-1)
    else List(Road(genTown, genTown, Random.between(1, 10)))
  }
//
  def stateCount(list: List[Road]) = {

    def listToSortedList = {
      list.map(x =>
          if (x.town1.number < x.town2.number) (x.town1, x.town2, x.roadLength)
          else (x.town2, x.town1, x.roadLength))
        .sortBy(a =>
          (a._1.number, a._2.number, a._3)
        )
    }

    def removeSelfroad(): List[(Town, Town, Int)] = {
      listToSortedList
        .filter(x =>
          x._1.number != x._2.number
        )
    }

    def removeDublicate(): List[(Town, Town, Int)] = {
      val newList = removeSelfroad()
      newList
        .map(x => {
          val check = !newList.exists(y =>
            newList.indexOf(x) != newList.indexOf(y) &&
              x._1.number == y._1.number &&
              x._2.number == y._2.number &&
              x._3 < y._3
          )
          if (check) Some(x) else None
        }
        ).filter(x => x.isDefined).map(x => x.get)
    }

    def removeCircle(blist: List[(Town, Town, Int)]): List[(Town, Town, Int)] = {

      @tailrec
      def get(int1: Int, lastT: Int, length: List[(Town, Town, Int)], nList: List[(Town, Town, Int)]): List[(Town, Town, Int)] = {
        val newLastT = nList.find(x => x._1.number == lastT)
        if (newLastT.isDefined) {
          val int2 = newLastT.get._2.number
          if (int1 != int2) get(int1, int2, length.appended(newLastT.get), nList.filter(x => x != newLastT.get))
          else List(length.minBy(x => x._3))
        }
        else {
          val newLastT = nList.find(x => x._2.number == lastT)
          if (newLastT.isDefined) {
            val int2 = newLastT.get._1.number
            if (int1 == int2) List(length.minBy(x => x._3))
            else get(int1, int2, length.appended(newLastT.get), nList.filter(x => x != newLastT.get))
          }
          else List()
        }
      }

      blist
        .map(x =>
          get(x._1.number, x._2.number, List(x), blist.filter(a => a != x))
        ).distinct.flatten
    }

    def getAnswer = {
      val list = removeDublicate()
      val removeRoad = removeCircle(list).minBy(_._3)
      val newList = list.filter(x => x != removeRoad).minBy(x => x._3)._3

      if (newList <= removeRoad._3) newList - 1
      else removeRoad._3
    }
  }

  println(listOfRoads)

  println(stateCount(listOfRoads))


}