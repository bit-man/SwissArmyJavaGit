                                  JavaGit 

      TABLE OF CONTENTS

   I. BUILDING JAVAGIT
  II. DEVELOPING WITH ECLIPSE
 III. ADDING SOURCES FOR DEPENDENCIES


I.    BUILDING JAVAGIT

      JavaGit is built using Maven 2.  The steps to get started with building 
      the source is:

      1. Download Maven 2 from http://maven.apache.org and set it up.

      2. Navigate on the command line to the project's javagit directory and 
         type:

           $ mvn package

         The jar file for the project will be deposited in the target 
         directory.


II.   DEVELOPING WITH ECLIPSE

      After you have completed part I, 

      1. Navigate to the root of the project you want to do development on 
         and type the command:

           $ mvn eclipse:eclipse

         The necessary .project and .classfile files will be generated for all
         sub-projects of that project.

      2. Open the javagit sandbox directory as your Eclipse workspace.

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
         d. Choose the javagit directory and press "Choose"
         e. Select the projects you want to import and press "Finish"

      5. Start developing.


III.  ADDING SOURCES FOR DEPENDENCIES

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


