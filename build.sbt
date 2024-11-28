ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.15"

//ThisBuild / resolvers += Resolver.ApacheMavenSnapshotsRepo

lazy val root = (project in file("."))
  .settings(
    name := "pekko-test",
    libraryDependencies ++= Seq(
      "org.apache.pekko" %% "pekko-actor" % "1.1.2",
      "commons-io" % "commons-io" % "2.18.0"
    )
  )

lazy val bench = project
  .dependsOn(root)
  .enablePlugins(JmhPlugin)
