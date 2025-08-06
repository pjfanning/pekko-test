ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.16"

//ThisBuild / resolvers += Resolver.ApacheMavenSnapshotsRepo

val pekkoVersion = "1.1.5"
val aeronVersion = "1.45.1"
val agronaVersion = "1.22.0"

lazy val root = (project in file("."))
  .settings(
    name := "pekko-test",
    libraryDependencies ++= Seq(
      "org.apache.pekko" %% "pekko-actor" % pekkoVersion,
      "org.apache.pekko" %% "pekko-stream" % pekkoVersion,
      "commons-io" % "commons-io" % "2.19.0",
      "org.apache.pekko" %% "pekko-remote" % pekkoVersion % Test,
      "org.apache.pekko" %% "pekko-testkit" % pekkoVersion % Test,
      "io.aeron" % "aeron-driver" % aeronVersion % Test,
      "io.aeron" % "aeron-client" % aeronVersion % Test,
      "org.agrona" % "agrona" % agronaVersion % Test,
      "org.scalatest" %% "scalatest" % "3.2.19" % Test
    )
  )

lazy val bench = project
  .dependsOn(root)
  .enablePlugins(JmhPlugin)
