                                  JavaGit 

      TABLE OF CONTENTS

   I. BUILDING JAVAGIT
  II. USING JAVAGIT IN YOUR PROJECT
 III. DEVELOPING JAVAGIT WITH ECLIPSE
  IV. ADDING SOURCES FOR DEPENDENCIES


I.    BUILDING JAVAGIT

      JavaGit is built using Maven 2.  The steps to get started with building 
      the source is:

      1. Download Maven 2 from http://maven.apache.org and set it up.

      2. Navigate on the command line to the project's JavaGit directory and 
         type:

           $ mvn package

         The jar file for the project will be deposited in the target 
         directory.


II.   USING JAVAGIT IN YOUR PROJECT

      JavaGit is currently only available in source form.  To use JavaGit in
      developing your application, you will need to check the source out of 
      the source repository and build the jar file.  Once you have the jar file,
      there are many ways to use it; three possible methods to use it are:

      1. Build the JavaGit jar file, copy it into your project tree and integrate
         the jar file into your build system and development environment.

      2. Build the JavaGit jar file, install it in your local Maven 2 repository,
         and refer to that jar file in your IDE and build scripts.

      3. Import the JavaGit project into your development environemnt and use the
         source directly.

      Since JavaGit uses Maven 2 to build the project, here we describe how to
      follow option 2.

      a. Checkout the source code into a local sandbox.

      b. Build JavaGit as described in section I.

      c. While still in the javagit directory, type the command:

           $ mvn install

         The JavaGit jar file should now be installed in your local Maven 2 
         repository.  On Unix systems this is:

           ~/.m2/repository/javagit/javagit

         On Windows this is probably:

           C:\Documents and Settings\<YourUser>\.m2\repository\javagit\javagit

      d. If you are using Maven 2 as your project build tool, add JavaGit as a
         dependency:

           <dependency>
             <groupId>javagit</groupId>
             <artifactId>javagit</artifactId>
             <version>0.01-SNAPSHOT</version>
           </dependency>

         If you are using ant, add the JavaGit jar file to your build path.

       e. If you are using Eclipse as your IDE and you are using Maven 2 as your
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

       f. You are now ready to develop with JavaGit.


III.  DEVELOPING JAVAGIT WITH ECLIPSE

      After you have completed part I, 

      1. Navigate to the root of the project you want to do development on 
         and type the command:

           $ mvn eclipse:eclipse

         The necessary .project and .classfile files will be generated for all
         sub-projects of that project.

      2. Open the JavaGit sandbox directory as your Eclipse workspace.

      3. Open the workspace's Preferences and:

         a. Navigate to "Java -> Build Path -> Classpath Variables"
         b. Click on "New..."
         c. Enter the Name as "M2_REPO" (without the quotes)
         d. Click on "Folder..."
         e. Choose the directory ~/.m2/repository
         f. Press OK
         g. Press OK

      4. Choose the menu File -> Import... and:

         a. Choose "General -> Existing Projects into Workspace"
         b. Press "Next >"
         c. Make sure the "Select root directory:" radio button is selected and
            click "Browse..."
         d. Choose the JavaGit directory and press "Choose"
         e. Select the projects you want to import and press "Finish"

      5. Start developing.


IV.   ADDING SOURCES FOR DEPENDENCIES

      When developing in Eclipse, it is often desireable to see the sources for
      the library dependencies of the project being worked on.  To download the
      sources for the dependencies, run the following command:

          $ mvn dependency:sources

      Once the sources are downloaded, 

      1. Go to Eclipse and "Ctrl-Left Click" (windows/linux) or "Cmd-Click" 
         (os x) a type from the dependency for which you want to view the 
         source.
      2. Click "Attach Source"
      3. Find the variable (M2_REPO) and the extension for the source zip/jar.
      4. Press OK.

      The source for the dependency should now be loaded.

      Note:  For JDK source, there is a src.zip file installed in the JAVA_HOME
             directory of all Sun JDK installations.  Attach this zip file for
             browsing JDK source.



