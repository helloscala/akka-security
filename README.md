# Akka Security

[![Gitter](https://badges.gitter.im/helloscala/akka-security.svg)](https://gitter.im/helloscala/akka-security?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

Out-of-the-box OAuth 2 client-server library based on Akka.

## Building from Source

Akka Security uses a [SBT](https://www.scala-sbt.org/)-based build system.

### Prerequisites

[Git](https://git-scm.com/downloads) and the [JDK 11](https://adoptopenjdk.net/?variant=openjdk11&jvmVariant=hotspot) build.

We recommend using [SDKMAN](https://sdkman.io/) to configure the development environment.

### Check out sources

```
git clone https://github.com/helloscala/akka-security.git
```

### Compile

```
sbt compile
```

## OAuth 2 Integration Sample

This sample integrates ~~spring-security-oauth2-client and~~ spring-security-oauth2-resource-server with Akka Authorization Server.

### Run the Sample

- Run Authorization Server -> `sbt "project example-oauth2-server-in-memory" run`
    - **IMPORTANT**: Make sure to modify your `/etc/hosts` file to avoid problems with session cookie overwrites between `spring-security-oauth2-client` and `example-oauth2-server-in-memory`. Simply add the entry `127.0.0.1 auth-server`
- Rune Resource Server -> `sbt "project example-spring-oauth2-security-resource" "runMain sample.OAuth2ResourceServerApplication"`
- ~~Run Client -> `sbt "project example-spring-oauth2-client" "runMain sample.OAuth2ClientApplication"`~~

### Beginning test

#### Get `access_token`

```
curl -i -XPOST 'http://localhost:9000/oauth2/token' \
  -u 'messaging-client:secret' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'grant_type=client_credentials&scope=message.read'
```
Example of response:
```
HTTP/1.1 200 OK
Server: akka-http/10.2.0
Date: Mon, 21 Sep 2020 02:03:54 GMT
Content-Type: application/json
Content-Length: 667

{
  "access_token":"eyJraWQiOiJlYy1rZXkiLCJhbGciOiJFUzI1NiJ9.eyJzdWIiOiJlYy1jbGllbnQiLCJzY29wZSI6Im1lc3NhZ2UucmVhZCIsImlzcyI6Imh0dHBzOlwvXC9ha2thLXNlY3VyaXR5LmhlbGxvc2NhbGEuY29tIiwiZXhwIjoxNjA1Nzk5ODgxLCJpYXQiOjE2MDUxOTQ3ODEsImp0aSI6ImVjLWtleSJ9.ebtN29ey5lkp2wtH9NeABqpcDswLZHBWgVhof2qMvD-QgKeu5rJHArxnnh_4hRDlUCH9bo6H_UKu5BYtq-E-Kw",
  "scope":"message.read",
  "token_type":"Bearer",
  "expires_in":"605100"
}
```

JWS uses the `ES256` algorithm by default, and you can change it by using parameter such as `algorithm=RS256`. 
```
curl -i -XPOST 'http://localhost:9000/oauth2/token' \
  -u 'messaging-client:secret' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'grant_type=client_credentials&scope=message.read&algorithm=RS256'
```
  
#### Access to `messages`

```
curl -i 'http://localhost:8090/messages' \
  -H 'Authorization: Bearer <access_token>'
```
*Please replace the `<access_token>` punctuation character with the correct `access_token`, which can be found in the previous example.* Example of response:
```
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 37
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1 ; mode=block
Referrer-Policy: no-referrer

["Message 1","Message 2","Message 3"]
```
