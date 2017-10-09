# TInventory
[![Documentation Status](https://readthedocs.org/projects/tinventory/badge/?version=latest)](http://tinventory.readthedocs.io/en/latest/?badge=latest)

Our Inventory Management Program for Team Software Project

## Table of Contents
- [Building the Project](#building-the-project)
- [Setting up Development Environment](#setting-up-development-environment)
    - [Eclipse](#eclipse)
        - [GUI](#method-1-importing-the-gradle-project-into-eclipse)
        - [CLI](#method-2-using-the-cli-to-generate-the-project-files)
    - [IntelliJ IDEA](#intellij-idea)
        - [GUI](#method-1-importing-the-gradle-project-into-intellij)
        - [CLI](#method-2-using-the-cli-to-generate-the-project-files-1)

## Building the Project
We utilize the Gradle build system for our project.
The benefit of using Gradle is the only prerequisite needed is Java. No other tools need to be manually installed by the user before using Gradle.
Upon running Gradle for the first time, some files will need to be downloaded. This should be a one time only thing, or whenever the Gradle Wrapper version is updated.

1. Clone the repo and open a Command Prompt/Terminal window in the root directory
2. Run the following command
    - Windows: `gradlew.bat build`
    - macOS/Linux: `./gradlew build`
3. Upon completion, the JAR file should be in `/build/libs`.

## Setting up Development Environment
Gradle facilitates the creation of the project files for various IDEs, such as Eclipse and IntelliJ IDEA.
NetBeans is also supported, however we have not had experience with it, so Google will be your friend if you want to use NetBeans.

### Eclipse
#### Method 1: Importing the Gradle Project into Eclipse
Later versions of Eclipse come with Gradle support out of the box. If you do not see the Gradle options mentioned below, you can install the *Buildship Gradle Integration* plugin from the Eclipse Marketplace, or use the CLI method below.

**As a note:** Eclipse doesn't have support to import the project from Git, as it expects the project files to already exist. You will need to clone the repo externally first.

1. Clone the repo and open Eclipse (The location of the workspace should not matter).
2. Choose File -> Import...
3. Under Gradle, choose Gradle Project.
4. Choose the root folder of the project for the *Project root directory*.
5. Click Finish and let Eclipse set everything up.

The project should now be setup, and you should now have support to run Gradle tasks from Eclipse itself

#### Method 2: Using the CLI to generate the project files
If you're more versed in command-line, or your version of Eclipse doesn't have native support for Gradle, you can use Gradle to generate the project files and import the project into Eclipse.

1. Clone the repo and open a Command Prompt/Terminal window in the root directory
2. Run the following command
    - Windows: `gradlew.bat eclipse`
    - macOS/Linux: `./gradlew eclipse`
3. After Gradle is done, close the window and open Eclipse (The location of the workspace should not matter)
4. Choose File -> Import...
5. Under General, choose Existing Projects into Workspace
6. In *Select root directory*, browse to the root directory of the project
7. With the project selected in the *Projects* window, click Finish.

The project should now be setup. To run Gradle tasks, you will need to use the CLI.

### IntelliJ IDEA
#### Method 1: Importing the Gradle Project into IntelliJ
Most modern versions of IntelliJ should come with Gradle support out of the box. If you do not see the Gradle options mentioned below, you can use the CLI method below.

1. Open IntelliJ
2. From the home screen, choose Check out from Version Control -> Git
3. Paste in the URL to the repo and click Clone
4. After cloning, IntelliJ should automatically detect the `build.gradle` file. Choose Yes to import it.
5. The default settings should be sufficient, but change to your liking. Afterwards, choose OK.

After IntelliJ finishes, the project should be imported for use, and you should now have support to run Gradle tasks from IntelliJ itself.

#### Method 2: Using the CLI to generate the project files
If you're more versed in command-line, or your version of IntelliJ doesn't have native support for Gradle, you can use Gradle to generate the project files and open the project in IntelliJ.

1. Clone the repo and open a Command Prompt/Terminal window in the root directory
2. Run the following command
    - Windows: `gradlew.bat idea`
    - macOS/Linux: `./gradlew idea`
3. After Gradle is done, close the window and open IntelliJ
4. From the home screen, choose Open
5. Navigate to the root directory of the project, and choose the `.ipr` file.

After IntelliJ finishes, the project should be imported for use. To run Gradle tasks, you will need to use the CLI.
