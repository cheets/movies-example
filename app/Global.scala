import play.api._
import play.api.Play.current
import play.api.db.slick.Config.driver.simple._
import models._

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    InitialData.insert()
  }

  object InitialData {
    def insert() {
      val movies: TableQuery[Movies] = TableQuery[Movies]
      play.api.db.slick.DB.withSession{ implicit session =>
        movies.ddl.drop
        movies.ddl.create

        val moviesInsertResult: Option[Int] = movies ++= Seq (
          Movie(None, "How to Train Your Dragon 2", 2014, "tt1646971", "http://www.movieposterdb.com/posters/13_12/2014/1646971/l_1646971_91766e57.jpg"),
          Movie(None, "Star Trek: Into Darkness", 2013, "tt1408101", "http://www.movieposterdb.com/posters/12_12/2013/1408101/l_1408101_ce007376.jpg"),
          Movie(None, "The Hunger Games: Catching Fire", 2013, "tt1951264", "http://www.movieposterdb.com/posters/13_05/2013/1951264/l_1951264_61c1e330.jpg"),
          Movie(None, "The Great Gatsby", 2013, "tt1343092", "http://www.movieposterdb.com/posters/13_07/2013/1343092/l_1343092_be9926b0.jpg"),
          Movie(None, "Furious 6", 2013, "tt1905041", "http://www.movieposterdb.com/posters/13_04/2013/1905041/l_1905041_1f36429c.jpg"),
          Movie(None, "Pacific Rim", 2013, "tt1663662", "http://www.movieposterdb.com/posters/12_12/2013/1663662/l_1663662_8561742a.jpg"),
          Movie(None, "Epic", 2013, "tt0848537", "http://www.movieposterdb.com/posters/13_04/2013/848537/l_848537_82b6b634.jpg"),
          Movie(None, "The Lone Ranger", 2013, "tt1210819", "http://www.movieposterdb.com/posters/13_04/2013/1210819/l_1210819_99d20359.jpg"),
          Movie(None, "Rio 2", 2014, "tt2357291", "http://www.movieposterdb.com/posters/13_12/2014/2357291/l_2357291_b78f80a5.jpg"),
          Movie(None, "Captain America: The Winter Soldier", 2014, "tt1843866", "http://www.movieposterdb.com/posters/13_11/2014/1843866/l_1843866_af1c2669.jpg"),
          Movie(None, "RoboCop", 2014, "tt1234721", "http://www.movieposterdb.com/posters/13_11/2014/1234721/l_1234721_fb871b15.jpg"),
          Movie(None, "X-Men: Days of Future Past", 2014, "tt1877832", "http://www.movieposterdb.com/posters/14_03/2014/1877832/l_1877832_1b263c40.jpg"),
          Movie(None, "The Amazing Spider-Man 2", 2014, "tt1872181", "http://www.movieposterdb.com/posters/14_03/2014/1872181/l_1872181_54d671da.jpg"),
          Movie(None, "The Lego Movie", 2014, "tt1490017", "http://www.movieposterdb.com/posters/14_02/2014/1490017/l_1490017_efcfb332.jpg"),
          Movie(None, "Guardians of the Galaxy", 2014, "tt2015381", "http://www.movieposterdb.com/posters/14_05/2014/2015381/l_2015381_f93eadb3.jpg"),
          Movie(None, "Maleficent", 2014, "tt1587310", "http://www.movieposterdb.com/posters/14_03/2014/1587310/l_1587310_a7a1b290.jpg"),
          Movie(None, "Transformers: Age of Extinction", 2014, "tt2109248", "http://www.movieposterdb.com/posters/14_05/2014/2109248/l_2109248_c3b012cc.jpg"),
          Movie(None, "Django Unchained", 2012, "tt1853728", "http://www.movieposterdb.com/posters/13_03/2012/1853728/l_1853728_f77f8307.jpg"),
          Movie(None, "The Avengers", 2012, "tt0848228", "http://www.movieposterdb.com/posters/12_03/2012/848228/l_848228_6ed314dd.jpg"),
          Movie(None, "The Dark Knight Rises", 2012, "tt1345836", "http://www.movieposterdb.com/posters/12_07/2012/1345836/l_1345836_a7e751aa.jpg"),
          Movie(None, "Warm Bodies", 2012, "tt1588173", "http://www.movieposterdb.com/posters/12_11/2012/1588173/l_1588173_02b6dc94.jpg"),
          Movie(None, "007 Skyfall", 2012, "tt1074638", "http://www.movieposterdb.com/posters/13_09/2012/1074638/l_1074638_24489e64.jpg"),
          Movie(None, "Cloud Atlas", 2012, "tt1371111", "http://www.movieposterdb.com/posters/12_10/2012/1371111/l_1371111_e2619606.jpg"),
          Movie(None, "Jack Reacher", 2012, "tt0790724", "http://www.movieposterdb.com/posters/12_10/2012/790724/l_790724_f970a59e.jpg"),
          Movie(None, "The Hobbit: An Unexpected Journey", 2012, "tt0903624", "http://www.movieposterdb.com/posters/12_11/2012/903624/l_903624_f5449541.jpg"),
          Movie(None, "Les Misérables", 2012, "tt1707386", "http://www.movieposterdb.com/posters/13_02/2012/1707386/l_1707386_7e8023ff.jpg"),
          Movie(None, "Silver Linings Playbook", 2012, "tt1045658", "http://www.movieposterdb.com/posters/13_01/2012/1045658/l_1045658_e9cbc30c.jpg"),
          Movie(None, "Lincoln", 2012, "tt0443272", "http://www.movieposterdb.com/posters/12_10/2012/443272/l_443272_cf8d16cc.jpg"),
          Movie(None, "Rise of the Guardians", 2012, "tt1446192", "http://www.movieposterdb.com/posters/12_09/2012/1446192/l_1446192_305afde5.jpg"),
          Movie(None, "Zero Dark Thirty", 2012, "tt1790885", "http://www.movieposterdb.com/posters/12_11/2012/1790885/l_1790885_b050d034.jpg"),
          Movie(None, "Prometheus", 2012, "tt1446714", "http://www.movieposterdb.com/posters/12_01/2012/1446714/l_1446714_eb7defbd.jpg"),
          Movie(None, "The Expendables 2", 2012, "tt1764651", "http://www.movieposterdb.com/posters/12_06/2012/1764651/l_1764651_7fe81431.jpg"),
          Movie(None, "The Impossible", 2012, "tt1649419", "http://www.movieposterdb.com/posters/12_09/2011/1649419/l_1649419_47bba6b9.jpg"),
          Movie(None, "The Place Beyond the Pines", 2012, "tt1817273", "http://www.movieposterdb.com/posters/13_02/2012/1817273/l_1817273_6d13646f.jpg"),
          Movie(None, "Life of Pi", 2012, "tt0454876", "http://www.movieposterdb.com/posters/12_08/2012/454876/l_454876_61188492.jpg")
        )

        moviesInsertResult foreach { numRows =>
          println(s"Inserted $numRows rows into the Movies table")
        }
      }
    }
  }
}
