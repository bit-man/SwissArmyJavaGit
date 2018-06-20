## Project moved to https://gitlab.com/bit-man/SwissArmyJavaGit

JavaGit
=======

![Build status according to Travis](https://travis-ci.org/bit-man/SwissArmyJavaGit.svg)

TABLE OF CONTENTS
-----------------

   1. Welcome
   2. Building Swiss Army Java Git
   3. Using it in your project
   4. Developing with Eclipse
   5. Adding sources for dependencies


### Welcome

[Swiss Army JavaGit](http://bit-man.github.io/SwissArmyJavaGit/) is a
Java API that provides access to git repositories.
Our goal is to provide a library with an easy-to-use API that is
intuitive for developers new to git and developers who are veteran
git users.

This is a fork from [original JavaGit](http://javagit.sourceforge.net/)

### Building Swiss Army Java Git

JavaGit is built using Maven 2.  The steps to get started with building
the source is:

  1. Download Maven 2 from http://maven.apache.org and set it up.
  2. Navigate on the command line to the project's JavaGit directory and type:

    $ mvn package

The jar file for the project will be deposited in the target directory.


### Using it in your project

JavaGit is currently only available in source form.  To use JavaGit in
developing your application, you will need to check the source out of
the source repository and build the jar file.  Once you have the jar file,
there are many ways to use it; three possible methods to use it are:

1. Build the JavaGit jar file, copy it into your project tree and integrate the jar file into your build system and development environment.
2. Build the JavaGit jar file, install it in your local Maven 2 repository, and refer to that jar file in your IDE and build scripts.
3. Import the JavaGit project into your development environemnt and use the source directly.

Since JavaGit uses Maven 2 to build the project, here we describe how to
follow option 2.


**a**. Checkout the source code into a local sandbox.

**b**. Build JavaGit as described in section I.

**c**. While still in the javagit directory, type the command:

           $ mvn install

 The JavaGit jar file should now be installed in your local Maven 2
 repository.  On Unix systems this is:

           ~/.m2/repository/io/github/bit-man/javagit/

On Windows this is probably:

           C:\Documents and Settings\<YourUser>\.m2\repository\io\github\bit-man\javagit

**d**. If you are using Maven 2 as your project build tool, add JavaGit as a
 dependency:

               <dependency>
                    <groupId>io.github.bit-man</groupId>
                    <artifactId>javagit</artifactId>
                    <version>Java Git Version You like the most!</version>
                </dependency>


If you are using ant, add the JavaGit jar file to your build path.

**e**. If you are using Eclipse as your IDE and you are using Maven 2 as your
  build system, regenerate your Eclipse project files after adding the
  JavaGit dependency using the following two commands in your project
  sandbox:

            $ mvn eclipse:clean
            $ mvn eclipse:eclipse

If you are using Eclipse but are not using Maven 2 as your build
system, there are a few ways to use the installed JavaGit jar file
with your project.

1) Add the jar as a "Library" in your "Java Build Path":

 - Open your project's "Propeties" dialog
 - Click on "Java Build Path"
 - Click on "Add JARs..."
 - Find the installed JavaGit jar and select it for use

2) Add a variable to library variable for the M2 repository and then
 add the jar file to the Libraries in your "Java Build Path":

 - Open the workspace properties
 - Go to "Java -> Build Path -> Classpath Variables"
 - Click "New..."
 - Type in the name "M2_REPO"
 - Click "Folder..." and find the ~/.m2/repository directory
 - Click "Choose", then click "OK" and "OK"
 - Open your project's "Propeties" dialog
 - Click on "Java Build Path"
 - Click on "Add Variable..."
 - Select the "M2_REPO" variable and click "Extend..."
 - Find the JavavGit far file you want to use as the jar file
   to develop against.
 - Click "OK", then "OK", then "OK"

**f**. You are now ready to develop with JavaGit.


### Developing with Eclipse

After you have completed part I,

1. Navigate to the root of the project you want to do development on
 and type the command:

           $ mvn eclipse:eclipse

The necessary .project and .classfile files will be generated for all
sub-projects of that project.

2. Open the JavaGit sandbox directory as your Eclipse workspace.

3. Open the workspace's Preferences and:
 - Navigate to "Java -> Build Path -> Classpath Variables"
 - Click on "New..."
 - Enter the Name as "M2_REPO" (without the quotes)
 - Click on "Folder..."
 - Choose the directory ~/.m2/repository
 - Press OK
 - Press OK

4. Choose the menu File -> Import... and:

 - Choose "General -> Existing Projects into Workspace"
 - Press "Next >"
 - Make sure the "Select root directory:" radio button is selected and click "Browse..."
 - Choose the JavaGit directory and press "Choose"
 - Select the projects you want to import and press "Finish"

5. Start developing.


### Adding sources for dependencies

When developing in Eclipse, it is often desireable to see the sources for
the library dependencies of the project being worked on.  To download the
sources for the dependencies, run the following command:

          $ mvn dependency:sources

Once the sources are downloaded,

 1. Go to Eclipse and "Ctrl-Left Click" (windows/linux) or "Cmd-Click" (os x) a type from the dependency for which you want to view the  source.
 2. Click "Attach Source"
 3. Find the variable (M2_REPO) and the extension for the source zip/jar.
 4. Press OK.

The source for the dependency should now be loaded.

**Note:**  For JDK source, there is a src.zip file installed in the JAVA_HOME
     directory of all Sun JDK installations.  Attach this zip file for
     browsing JDK source.



