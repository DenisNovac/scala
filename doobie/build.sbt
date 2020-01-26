name := "doobie"

version := "0.1"

scalaVersion := "2.13.1"


lazy val doobieVersion = "0.8.8"

libraryDependencies ++= Seq(
  "org.tpolecat" %% "doobie-core"     % doobieVersion,
  "org.tpolecat" %% "doobie-postgres" % doobieVersion,
  "org.tpolecat" %% "doobie-specs2"   % doobieVersion
)

//scalacOptions += "-Ypartial-unification" // 2.11.9+
