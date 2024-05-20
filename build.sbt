ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.14"

//ThisBuild / resolvers += Resolver.ApacheMavenSnapshotsRepo

lazy val root = (project in file("."))
  .settings(
    name := "pekko-test",
    libraryDependencies ++= Seq(
      "org.apache.pekko" %% "pekko-actor" % "1.1.0-M1",
      "commons-io" % "commons-io" % "2.16.1"
    )
  )

lazy val bench = project
  .dependsOn(root)
  .enablePlugins(JmhPlugin)
