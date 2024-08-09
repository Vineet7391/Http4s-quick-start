import com.github.sbt.jacoco.report.JacocoReportSettings
import com.github.sbt.jacoco.JacocoPlugin.autoImport._

val Http4sVersion = "0.23.27"
val CirceVersion = "0.14.7"
val MunitVersion = "0.7.29"
val LogbackVersion = "1.5.6"
val MunitCatsEffectVersion = "1.0.7"
lazy val doobieVersion = "1.0.0-RC4"
val Fs2Version = "3.10.2"
lazy val excludes = Test / jacocoExcludes := Seq(
  "example.jacoco.main.Main*"
)

lazy val jacoco = test / jacocoReportSettings := JacocoReportSettings(
  "Jacoco_Test_Report",
  None,
  JacocoThresholds(branch = 0),
  Seq(JacocoReportFormats.ScalaHTML, JacocoReportFormats.XML),
  "utf-8"
)

val jacocoSettings = Seq(jacoco, excludes)
lazy val root = (project in file("."))
  .settings(
    organization := "com.example",
    name := "quickstart",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.13",
    jacocoSettings,
    libraryDependencies ++= Seq(
      "org.http4s"      %% "http4s-ember-server" % Http4sVersion,
      "org.http4s"      %% "http4s-ember-client" % Http4sVersion,
      "org.http4s"      %% "http4s-circe"        % Http4sVersion,
      "org.http4s"      %% "http4s-dsl"          % Http4sVersion,
      "io.circe"        %% "circe-generic"       % CirceVersion,
      "org.scalameta"   %% "munit"               % MunitVersion           % Test,
      "ch.qos.logback"  %  "logback-classic"     % LogbackVersion         % Runtime,
      "org.scalameta"   %  "svm-subs"            % "101.0.0",
      "org.tpolecat" %% "doobie-core"     % doobieVersion,
      "org.tpolecat" %% "doobie-postgres" % doobieVersion,
      "org.tpolecat" %% "doobie-specs2"   % doobieVersion,
      "co.fs2" %% "fs2-core" % Fs2Version,
      "org.typelevel" %% "munit-cats-effect" % "2.0.0" % "test"
    ),
    sonarProperties := Map(
      "sonar.projectName" -> "sbt-sonarqube-test",
      "sonar.projectKey" -> "sbt-sonarqube-test",
      "sonar.java.coveragePlugin" -> "jacoco",
      "sonar.host.url" -> "http://localhost:9000"
    ),
    addCompilerPlugin("org.typelevel" %% "kind-projector"     % "0.13.3" cross CrossVersion.full),
    addCompilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.1"),

    assembly / assemblyMergeStrategy := {
      case "module-info.class" => MergeStrategy.discard
      case x => (assembly / assemblyMergeStrategy).value.apply(x)
    }
  )
