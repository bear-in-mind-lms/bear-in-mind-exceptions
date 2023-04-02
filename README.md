# Bear in Mind | Exceptions

The Bear in Mind is a free open-source LMS (learning management system). This is the library that provides and handles
common exceptions. Bear in mind that the library automatically registers `RestControllerAdvice` bean for controller exception
handling.

## Setup

### Maven

`settings.xml > profiles > profile > repositories`
```xml
<repository>
    <id>bear-in-mind-exceptions</id>
    <releases>
        <enabled>true</enabled>
    </releases>
    <snapshots>
        <enabled>true</enabled>
    </snapshots>
    <url>https://github.com/bear-in-mind-lms/bear-in-mind-exceptions/raw/mvn-artifact</url>
</repository>
```

`pom.xml`
```xml
<project>
    <properties>
        <bear-in-mind-exceptions.version>0.0.1</bear-in-mind-exceptions.version>
    </properties>
    
    <dependencies>
        <dependency>
            <groupId>com.kwezal.bearinmind</groupId>
            <artifactId>bear-in-mind-exceptions</artifactId>
            <version>${bear-in-mind-exceptions.version}</version>
        </dependency>
    </dependencies>
</project>
```

## Contribution

Your contribution is welcome and we appreciate it. 💝 Before you start, please make sure you have read
the [information for contributors][contributing].

## Code of Conduct

This project is governed by the [Bear in Mind Code of Conduct][conduct]. By participating, you are expected to uphold
this code of conduct.

## License

Bear in Mind Exceptions is released under the [Apache 2.0 License][license].

[contributing]: https://github.com/bear-in-mind-lms/bear-in-mind-core/blob/main/CONTRIBUTING.md

[conduct]: https://github.com/bear-in-mind-lms/bear-in-mind-core/blob/main/CODE_OF_CONDUCT.md

[license]: https://www.apache.org/licenses/LICENSE-2.0