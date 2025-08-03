ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.16"

//ThisBuild / resolvers += Resolver.ApacheMavenSnapshotsRepo

lazy val root = (project in file("."))
  .settings(
    name := "pekko-test",
    libraryDependencies ++= Seq(
      "org.apache.pekko" %% "pekko-actor" % "1.1.5",
      "org.apache.pekko" %% "pekko-stream" % "1.1.5",
      "commons-io" % "commons-io" % "2.18.0",
      "org.scalatest" %% "scalatest" % "3.2.19" % Test
    )
  )

lazy val bench = project
  .dependsOn(root)
  .enablePlugins(JmhPlugin)
