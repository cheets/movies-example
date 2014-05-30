name := "movies-example"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  "com.typesafe.play" %% "play-slick" % "0.6.0.1",
  "com.typesafe.slick" %% "slick" % "2.0.2",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.h2database" % "h2" % "1.3.175",
  "org.webjars" %% "webjars-play" % "2.2.1-2",
  "org.webjars" % "angularjs" % "1.2.16-2",
  "org.webjars" % "masonry" % "3.1.5",
  "org.webjars" % "foundation" % "5.2.2",
  "org.webjars" % "foundation-icon-fonts" % "d596a3cfb3"
)

play.Project.playScalaSettings
