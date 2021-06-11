name := "rpn"

version := "0.1"
scalaVersion := "2.13.6"

lazy val scalatest = "org.scalatest" %% "scalatest" % "3.2.9"

libraryDependencies ++= Seq(
  "org.mockito" %% "mockito-scala" % "1.16.37",
  "org.scalactic" %% "scalactic" % "3.2.9"
)

lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(
    Defaults.itSettings,
    libraryDependencies += scalatest % "it,test"
  )