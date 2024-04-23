ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.13"

ThisBuild / resolvers += Resolver.ApacheMavenSnapshotsRepo

lazy val root = (project in file("."))
  .settings(
    name := "pekko-test",
    libraryDependencies ++= Seq(
      "org.apache.pekko" %% "pekko-actor" % "1.1.0-M0+420-08782cd2-SNAPSHOT",
      "commons-io" % "commons-io" % "2.16.1"
    )
  )

lazy val bench = project
  .dependsOn(root)
  .enablePlugins(JmhPlugin)
