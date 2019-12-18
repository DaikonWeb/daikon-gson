# Daikon Json

![Daikon](./logo.svg)

Daikon Json  is a library that add to Daikon the ability to handle json requests and responses.

The main goals are:
* Help in rendering a json in the response
* Help in using a json request

## How to add Daikon Json to your project
[![](https://jitpack.io/v/DaikonWeb/daikon-json.svg)](https://jitpack.io/#DaikonWeb/daikon-json)

### Gradle
- Add JitPack in your root build.gradle at the end of repositories:
```
repositories {
    ...
    maven { url 'https://jitpack.io' }
}
```
- Add the dependency
```
implementation 'com.github.DaikonWeb:daikon-json:0.0.1'
```

### Maven
- Add the JitPack repository to your build file 
```
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
- Add the dependency
```
<dependency>
    <groupId>com.github.DaikonWeb</groupId>
    <artifactId>daikon-json</artifactId>
    <version>0.0.1</version>
</dependency>
```

## How to use
```
data class Result(val message: String, val code: String)

HttpServer()
    .post("/") { req, res -> res.json(req.json<Result>()) }
    .start().use {
        val response = post(url = "http://localhost:4545/", json = mapOf("message" to "ciao", "code" to "HELLO_ITA"))
        assertThat(response.text).isEqualTo("""{"message":"ciao","code":"HELLO_ITA"}""")
        assertThat(response.headers["Content-Type"]).isEqualTo(APPLICATION_JSON_UTF_8.asString())
    }
```

## Resources
* Documentation: https://daikonweb.github.io
* Examples: https://github.com/DaikonWeb/daikon-examples

## Authors

* **[Alessio Coser](https://github.com/alessiocoser)**

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details