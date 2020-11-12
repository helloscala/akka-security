import Dependencies._

ThisBuild / scalaVersion := "2.12.12"

ThisBuild / crossScalaVersions := Seq("2.12.12", "2.13.3")

ThisBuild / scalafmtOnCompile := true

ThisBuild / version := "0.1"

lazy val parent =
  Project("akka-security", file("."))
    .aggregate(
      `example-oauth2-server-in-slick`,
      `example-oauth2-server-in-memory`,
      `example-spring-oauth2-security-resource`,
      `example-spring-oauth2-client`,
      `akka-authorization-server`,
      `akka-security-oauth-client`,
      `akka-security-oauth-core`,
      `akka-security-oauth-jose`,
      `akka-security-core`)
    .settings(skip in publish := true)

lazy val `example-oauth2-server-in-slick` =
  project
    .in(file("samples/oauth2-server-in-slick"))
    .dependsOn(`akka-authorization-server`)
    .settings(basicSettings: _*)
    .settings(
      skip in publish := true,
      fork in run := false,
      libraryDependencies ++= Seq(_slickHikaricp, _postgresql, _logback))

lazy val `example-oauth2-server-in-memory` =
  project
    .in(file("samples/oauth2-server-in-memory"))
    .dependsOn(`akka-authorization-server`)
    .settings(basicSettings: _*)
    .settings(skip in publish := true, fork in run := false, libraryDependencies ++= Seq(_logback))

lazy val `example-spring-oauth2-security-resource` = project
  .in(file("samples/spring-oauth2-security-resource"))
  .settings(basicSettings: _*)
  .settings(
    skip in publish := true,
    fork in run := false,
    libraryDependencies ++= Seq(_springOAuth2ResourceServer, _logback) ++ _springSecurities)

lazy val `example-spring-oauth2-client` = project
  .in(file("samples/spring-oauth2-client"))
  .settings(basicSettings: _*)
  .settings(
    skip in publish := true,
    fork in run := false,
    libraryDependencies ++= Seq(_springOAuth2Client, _logback) ++ _springSecurities ++ _springThymeleafs ++ _webjars)

lazy val `akka-authorization-server` =
  project
    .in(file("akka-authorization-server"))
    .dependsOn(`akka-security-oauth-core`, `akka-security-oauth-jose`)
    .settings(basicSettings: _*)
    .settings(libraryDependencies ++= Seq(_akkaDiscovery, _akkaHttpTestkit % Test) ++ _akkaClusters)

lazy val `akka-security-oauth-client` = project
  .in(file("akka-security-oauth-client"))
  .dependsOn(`akka-security-oauth-core`, `akka-security-core`)
  .settings(basicSettings)
  .settings(libraryDependencies ++= Seq())

lazy val `akka-security-oauth-jose` = project
  .in(file("akka-security-oauth-jose"))
  .dependsOn(`akka-security-oauth-core`)
  .settings(basicSettings)
  .settings(libraryDependencies ++= Seq(_joseJwt))

lazy val `akka-security-oauth-core` = project
  .in(file("akka-security-oauth-core"))
  .dependsOn(`akka-security-core`)
  .settings(basicSettings: _*)
  .settings(libraryDependencies ++= Seq(_akkaHttpTestkit % Test) ++ _akkaHttps)

lazy val `akka-security-core` =
  project
    .in(file("akka-security-core"))
    .settings(basicSettings: _*)
    .settings(libraryDependencies ++= Seq(_akkaSerializationJackson, _scalaCollectionCompat, _bouncycastle) ++ _akkas)

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
