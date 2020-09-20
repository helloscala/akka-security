logLevel := Level.Info

resolvers += Resolver.bintrayIvyRepo("2m", "sbt-plugins")

addSbtPlugin("de.heikoseeberger" % "sbt-header" % "5.6.0")
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.0")
addSbtPlugin("org.foundweekends" % "sbt-bintray" % "0.5.5")
