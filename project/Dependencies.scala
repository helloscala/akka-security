import sbt._

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-09-19 08:21:48
 */
object Dependencies {
  val versionScala212 = "2.12.12"
  val versionScala213 = "2.13.3"
  val versionScalaXml = "1.3.0"
  val versionScalaCollectionCompat = "2.2.0"
  val versionScalatest = "3.2.2"
  val versionAkka = "2.6.9"
  val versionAkkaManagement = "1.0.8"
  val versionAkkaHttp = "10.2.0"
  val versionAkkaHttpCors = "0.4.3"
  val versionJoseJwt = "9.0.1"
  val versionNacosScala = "1.3.2"
  val versionAlpnAgent = "2.0.10"

  val _scalaXml = ("org.scala-lang.modules" %% "scala-xml" % versionScalaXml).exclude("org.scala-lang", "scala-library")
  val _scalatest = "org.scalatest" %% "scalatest" % versionScalatest
  val _scalaCollectionCompat = "org.scala-lang.modules" %% "scala-collection-compat" % versionScalaCollectionCompat

  val _akkaActorTyped = "com.typesafe.akka" %% "akka-actor-typed" % versionAkka
  val _akkaStreamTyped = "com.typesafe.akka" %% "akka-stream-typed" % versionAkka
  val _akkaDiscovery = "com.typesafe.akka" %% "akka-discovery" % versionAkka
  val _akkaSerializationJackson = "com.typesafe.akka" %% "akka-serialization-jackson" % versionAkka
  val _akkaProtobufV3 = "com.typesafe.akka" %% "akka-protobuf-v3" % versionAkka
  val _akkaPersistenceTyped = "com.typesafe.akka" %% "akka-persistence-typed" % versionAkka
  val _akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % versionAkka
  val _akkaTypedTestkit = "com.typesafe.akka" %% "akka-actor-testkit-typed" % versionAkka
  val _akkaStreamTestkit = "com.typesafe.akka" %% "akka-stream-testkit" % versionAkka

  val _akkas =
    Seq(_akkaSlf4j, _akkaActorTyped, _akkaStreamTyped).map(
      _.exclude("org.scala-lang.modules", "scala-java8-compat").cross(CrossVersion.binary))
  val _akkaMultiNodeTestkit = "com.typesafe.akka" %% "akka-multi-node-testkit" % versionAkka

  val _akkaClusters = Seq(
    "com.typesafe.akka" %% "akka-cluster-typed" % versionAkka,
    "com.typesafe.akka" %% "akka-cluster-sharding-typed" % versionAkka)

  val _akkaHttp = ("com.typesafe.akka" %% "akka-http" % versionAkkaHttp)
    .exclude("com.typesafe.akka", "akka-stream")
    .cross(CrossVersion.binary)

  val _akkaHttpTestkit = ("com.typesafe.akka" %% "akka-http-testkit" % versionAkkaHttp)
    .exclude("com.typesafe.akka", "akka-stream-testkit")
    .cross(CrossVersion.binary)
    .exclude("com.typesafe.akka", "akka-testkit")
    .cross(CrossVersion.binary)

  val _akkaHttpCors = ("ch.megard" %% "akka-http-cors" % versionAkkaHttpCors)
    .excludeAll(ExclusionRule("com.typesafe.akka"))
    .cross(CrossVersion.binary)

  val _akkaHttp2 = ("com.typesafe.akka" %% "akka-http2-support" % versionAkkaHttp)
    .exclude("com.typesafe.akka", "akka-stream")
    .cross(CrossVersion.binary)

  val _akkaHttps = Seq(_akkaHttp, _akkaHttp2)

  val _nacosAkka = "me.yangbajing.nacos4s" %% "nacos-akka" % versionNacosScala

  val _joseJwt = "com.nimbusds" % "nimbus-jose-jwt" % versionJoseJwt
  val _alpnAgent = "org.mortbay.jetty.alpn" % "jetty-alpn-agent" % versionAlpnAgent

  val _logback = "ch.qos.logback" % "logback-classic" % "1.2.3"
}
