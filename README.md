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

- Get `access_token`
    ```
    curl -i -XPOST 'http://localhost:9999/oauth2/token' \
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
      "access_token":"eyJraWQiOiJyc2Eta2V5IiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJtZXNzYWdpbmctY2xpZW50Iiwic2NvcGUiOiJtZXNzYWdlLnJlYWQiLCJpc3MiOiJodHRwczpcL1wvYWtrYS1zZWN1cml0eS5oZWxsb3NjYWxhLmNvbSIsImV4cCI6MTYwMTI1ODkzNCwiaWF0IjoxNjAwNjUzODM0LCJqdGkiOiJyc2Eta2V5In0.SjUQQW0lcc3y4VmR-nIVn2obq9InBs5n02B5DFiH3Uytixnctfktnxmucc42ab2mM8AHSZMg3y__rBq7XIufZJT1-IbIyiRG3YSPCP_rCss6QU0R7YqbUOQXRRonp8_0Tu7EDa8jxhFhBxRVcRyF0GLgEwaMqDpuOtBrGbEc5Nq-2zkBGNRaOAsp_dgouFL43vghGeRHVdss0peCE6ZI-apzxXvzJMh3i0SR26-OiPco76qa5Uo3Dr_qbYmWJh5Fkl6MH2keM_Lvb-iHmnAt5vPOBk23XAliQrfgzGTdv02SgOeE5pUpp82zQ2M6MAXEuiclPox9nZml4rRGi3UEaA",
      "scope":"message.read",
      "token_type":"Bearer",
      "expires_in":"605100"
    }
    ```
- Access to `messages`
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
