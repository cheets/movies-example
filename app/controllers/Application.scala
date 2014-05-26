package controllers

import play.api.mvc._
import models.{Movie, Movies}
import play.api.libs.json.Json
import play.api.db.slick.DBAction

object Application extends Controller {

  def index = Action {
    Ok(views.html.index())
  }

  implicit val messageJsonWriter = Json.writes[Movie]

  def movies(year: Option[Int]) = DBAction { implicit rs =>
    implicit val s = rs.dbSession
    val movies = year match {
      case Some(y) => Movies.findByYear(y)
      case None => Movies.list
    }
    Ok(Json.toJson(Map("movies" -> movies)))
  }

  def movie(id: Long) = DBAction { implicit rs =>
    implicit val s = rs.dbSession
    val movie =  Movies.get(id)
    Ok(Json.toJson(movie))
  }

  def years = DBAction { implicit rs =>
    implicit val s = rs.dbSession
    val years = Movies.years
    Ok(Json.toJson(years))
  }
}
