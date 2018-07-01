<h1 align="center">A Zero-Dependency Mapping Library</h1>
<h4 align="center">Simple Java Library to include scripting languages into your Java application.</h4>
<h5 align="center">API Module</h5>

<p align="center">
  <a href="https://travis-ci.com/dmeiners88/mapping-api">
    <img src="https://travis-ci.com/dmeiners88/mapping-api.svg?branch=develop"
         alt="Build Status">
  </a>
  <a href="https://sonarcloud.io/dashboard?id=de.dmeiners.mapping%3Amapping-api">
    <img src="https://sonarcloud.io/api/project_badges/measure?project=de.dmeiners.mapping%3Amapping-api&metric=alert_status" alt="SonarCloud Analysis">
  </a>
  <a href="https://bintray.com/dmeiners/mapping/mapping-api/_latestVersion">
    <img src="https://api.bintray.com/packages/dmeiners/mapping/mapping-api/images/download.svg" alt="Download">
  </a>
  <a href="https://github.com/dmeiners88/mapping-api/blob/develop/LICENSE">
    <img src="https://img.shields.io/github/license/dmeiners88/mapping-api.svg" alt="License">
  </a>
  <a href="https://semver.org/spec/v2.0.0.html">
    <img src="https://img.shields.io/badge/semver-2.0.0-brightgreen.svg" alt="Semantic Versioning">
  </a>
</p>

## Features
* Zero dependencies
* Alternative to _Scripting for the Java Platform_ [JSR-223](https://jcp.org/en/jsr/detail?id=223)
* Different scripting language implementations
* Compile scripts inline, load from the classpath or roll your own `ScriptNameResolver`
* Expose your utility classes into the scripting language

## Limitations
* No handling of script engine/language mismatch
* Only one script engine implementation from the classpath is picked up

## Installation

### Add the API Module
Add [JCenter](https://bintray.com/bintray/jcenter) as a repository to your Maven POM:
```xml
<repositories>
    <repository>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
        <id>jcenter</id>
        <name>JCenter</name>
        <url>https://jcenter.bintray.com</url>
    </repository>
</repositories>
```

Add the following dependency:
```xml
<dependency>
    <groupId>de.dmeiners.mapping</groupId>
    <artifactId>mapping-api</artifactId>
    <version>1.0.0</version>
</dependency>
```

> **Important**: Adding the Mapping API dependency alone will result in a no-op fallback implementation. See the next section for a list of implementation modules.

### Add an Implementation Module
* [Apache Commons JEXL](https://github.com/dmeiners88/mapping-impl-jexl)
* [MVFLEX Expression Language](https://github.com/dmeiners88/mapping-impl-mvel) (experimental)
* [Java-on-Java](https://github.com/dmeiners88/mapping-impl-java) (experimental)

## Usage

### Simple Usage

```java
PostProcessor processor = PostProcessorFactory.builder().build(); // <1>

String someObject = "Hello"; // <2>

Script script = processor.compileInline("target += ' World!'"); // <3>

String result = script.execute(someObject, Collections.emptyMap()); // <4>

assertThat(result).isEqualTo("Hello World!");
```
1. Create a new post processor
2. Create a very simple target object
3. Compile a simple script (scripting language dependent on the implementation available on the classpath, here it is Apache Commons JEXL)
4. Execute the script, result type is guaranteed to the original type

### Context Usage

```java
PostProcessor processor = PostProcessorFactory.builder().build();

Map<String, Object> user = new HashMap<>(); // <1>
user.put("firstName", "John");
user.put("lastName", "Doe");

Map<String, Object> context = new HashMap<>(); // <2>
context.put("user", user);

String someObject = "Hello";

String scriptText = "target += ` ${user.firstName} ${user.lastName}`"; // <3>

Script script = processor.compileInline(scriptText);

String result = script.execute(someObject, context);

assertThat(result).isEqualTo("Hello John Doe");
```
1. Target object is a Map this time
2. We create another map to use as script context
3. Beware that the script text is dependent on the available implementation on the classpath, here it is Apache Commons JEXL

### Expose Utility Class

```java
PostProcessor processor = PostProcessorFactory.builder()
    .extension("StringUtils", StringUtils.class) // <1>
    .extension("tools", new Tools()) // <2>
    .build();

Map<String, Object> user = new HashMap<>();
user.put("firstName", "John");
user.put("lastName", "Doe");

Map<String, Object> context = new HashMap<>();
context.put("user", user);

String someObject = "Hello";

String scriptText = "if (StringUtils.isNotBlank(user.firstName)) { target = 'First name is not blank and reversed ' + tools.reverse(user.firstName); }"; // <2>

Script script = processor.compileInline(scriptText);

String result = script.execute(someObject, context);

assertThat(result).isEqualTo("First name is not blank and reversed nhoJ");
```
1. We can register classes to expose static methods to the scripting language
2. We can register objects to expose instance methods to the scripting language