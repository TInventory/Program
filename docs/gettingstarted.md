# Getting Started
To begin using the program, you must first acquire the binary file.

## Method 1: Downloading from GitHub
You can download the pre-compiled binaries for specific versions from the [releases
 section of our GitHub page][1]. You will likely want the `all` version of the
 binary, as that has all the dependencies shadowed into a single JAR file. If you
 will be providing your own copy of our dependencies and link them yourself, you
 may use the `bin` version of the binary.

After downloading the necessary file, run it like you would run any Java JAR file.
 TInventory requires Java 8 or later to run.

## Method 2: Building the Sources
Each release on our [GitHub releases page][1] also includes a link to the source
 used to build that respective binary, so you can download the source from there,
 or you can clone the [GitHub repo][2] and build the latest sources. Bear in mind
 that there is no guarantee that the latest sources will compile and run correctly.

TInventory utilizes the [Gradle Build System][3] to build the application. Besides
 being objectively faster than Maven, it doesn't require end users to have any
 other programs to be installed prior to building. As long as the JDK is installed
 on the system, than the user can take any project using Gradle, and build it without
 installing Gradle to their system. This is due to the [Gradle Wrapper][4].

To build the sources, after you have cloned or downloaded the sources, open a
 Terminal/Command Prompt window and execute the following command:

* Windows: `gradlew.bat build`
* macOS/Linux: `./gradlew build`

After completion of the build, the resulting JAR files will be in `/build/libs`.
 You will likely want the `all` version of the binary, as that has all the
 dependencies shadowed into a single JAR file. If you will be providing your own
 copy of our dependencies and link them yourself, you may use the `bin` version
 of the binary.

After building and locating the resulting JAR file, run it like you would run any
 Java JAR file. TInventory requires Java 8 or later to run.

[1]: https://github.com/TInventory/Program/releases
[2]: https://github.com/TInventory/Program
[3]: https://gradle.org/
[4]: https://docs.gradle.org/current/userguide/gradle_wrapper.html
