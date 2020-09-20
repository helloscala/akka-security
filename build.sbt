import Dependencies._

ThisBuild / scalaVersion := "2.12.12"

ThisBuild / crossScalaVersions := Seq("2.12.12", "2.13.3")

ThisBuild / scalafmtOnCompile := true

ThisBuild / version := "0.1"

lazy val parent =
  Project("akka-security", file("."))
    .aggregate(
      `example-akka-authorization-server`,
      `akka-authorization-server`,
      `akka-oauth-core`,
      `akka-security-josa`,
      `akka-security-core`)
    .settings(skip in publish := true)

lazy val `example-akka-authorization-server` =
  project
    .in(file("sample/example-authorization-server"))
    .dependsOn(`akka-authorization-server`)
    .settings(basicSettings: _*)
    .settings(libraryDependencies ++= Seq(_logback))

lazy val `akka-authorization-server` =
  project
    .in(file("akka-authorization-server"))
    .dependsOn(`akka-oauth-core`, `akka-security-josa`)
    .settings(basicSettings: _*)
    .settings(libraryDependencies ++= Seq(_akkaDiscovery, _akkaHttpTestkit % Test) ++ _akkaClusters)

lazy val `akka-security-josa` = project
  .in(file("akka-security-josa"))
  .dependsOn(`akka-security-core`)
  .settings(basicSettings)
  .settings(libraryDependencies ++= Seq(_joseJwt))

lazy val `akka-oauth-core` = project
  .in(file("akka-oauth-core"))
  .dependsOn(`akka-security-core`)
  .settings(basicSettings: _*)
  .settings(libraryDependencies ++= Seq(_akkaHttpTestkit % Test) ++ _akkaHttps)

lazy val `akka-security-core` =
  project
    .in(file("akka-security-core"))
    .settings(basicSettings: _*)
    .settings(libraryDependencies ++= Seq(_akkaSerializationJackson, _scalaCollectionCompat) ++ _akkas)

def basicSettings =
  Seq(
    organization := "com.helloscala",
    organizationName := "helloscala",
    organizationHomepage := Some(url("http://helloscala.com")),
    homepage := Some(url("https://yangbajing.github.cn/nacos-sdk-scala")),
    startYear := Some(2020),
    licenses += ("Apache-2.0", new URL("https://www.apache.org/licenses/LICENSE-2.0.txt")),
    headerLicense := Some(HeaderLicense.ALv2("2020", "com.helloscala")),
    scalacOptions ++= Seq(
      "-encoding",
      "UTF-8", // yes, this is 2 args
      "-feature",
      "-deprecation",
      "-unchecked",
      "-Xlint",
      "-opt:l:inline",
      "-opt-inline-from",
      "-Ywarn-dead-code"),
    javacOptions in Compile ++= Seq("-Xlint"),
    shellPrompt := { s =>
      Project.extract(s).currentProject.id + " > "
    },
    fork in run := true,
    fork in Test := true,
    parallelExecution in Test := false,
    libraryDependencies ++= Seq(_scalatest % Test)) ++ publishing

def publishing =
  Seq(
    bintrayOrganization := Some("helloscala"),
    bintrayRepository := "maven",
    developers := List(
      Developer(
        id = "yangbajing",
        name = "Yang Jing",
        email = "yang.xunjing@qq.com",
        url = url("https://github.com/yangbajing"))),
    scmInfo := Some(
      ScmInfo(
        url("https://github.com/yangbajing/nacos-sdk-scala"),
        "scm:git:git@github.com:yangbajing/nacos-sdk-scala.git")))
