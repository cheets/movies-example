package models

import play.api.db.slick.Config.driver.simple._

abstract class BaseTable[T](tag: Tag, name: String) extends Table[T](tag, name) {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
}

case class Movie(id: Option[Long], name: String, year: Int, imdbID: String, posterURL: String)

class Movies(tag: Tag) extends BaseTable[Movie](tag, "movies") {
  def name: Column[String] = column[String]("name", O.NotNull)
  def year: Column[Int] = column[Int]("year", O.NotNull)
  def imdbID: Column[String] = column[String]("imdb_id", O.NotNull)
  def posterURL: Column[String] = column[String]("poster_url", O.NotNull)

  def * = {
    (id.?, name, year, imdbID, posterURL) <> (Movie.tupled, Movie.unapply)
  }
}

object Movies {
  val movies = TableQuery[Movies]
  def get(id: Long)(implicit s: Session): Option[Movie] = movies.where(_.id === id).firstOption
  def list()(implicit s: Session): List[Movie] = movies.list
  def findByYear(year: Int)(implicit s: Session): List[Movie] = {
    val query = for (m <- movies if m.year is year) yield m
    query.list
  }
  def years()(implicit s: Session): Seq[Int] = movies.groupBy(_.year).map(_._1).sorted.run
}