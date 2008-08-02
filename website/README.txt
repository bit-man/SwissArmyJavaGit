                        JavaGit Website

      TABLE OF CONTENTS

   I. SUMMARY
  II. MAKING CHANGES
 III. EDITING CONTENT
  IV. EDITING THE MENU
   V. ADDING CONTENT
  VI. EDITING THE STYLE SHEET

I.    SUMMARY

      This directory contains the javagit website.  The contents of the site
      are currently in a .php template system.  This document describes how 
      to modify the contents of the website.

      The code for the template system is released under the GPL v2.0 license.

II.   MAKING CHANGES

      To make changes to the website:

      1. Check the website code out from the repository to a local sandbox
      2. Make your changes in your local sandbox
      3. Test locally if you have apache and php set up
      4. sftp or scp your changed files up to the website.  For example:

            ~/sandbox/javagit/website/htdocs$ scp faq.php USERNAME@shell.sf.net:/home/groups/j/ja/javagit/htdocs

      5. Make sure the uploaded files have the following settings:

            $ chmod -R ugo+r /home/groups/j/ja/javagit/htdocs
            $ chmod -R ug+w /home/groups/j/ja/javagit/htdocs
            $ find /home/groups/j/ja/javagit/htdocs -type d -exec chmod ugo+rx {} \;
            $ find /home/groups/j/ja/javagit/htdocs -type d -exec chmod ug+w {} \;
            $ chgrp -R javagit /home/groups/j/ja/javagit/htdocs
            

III.  EDITING CONTENT

      Content for each page is located in 

        htdocs/content/PAGE_ID.html

      Just edit it like standard [x]html.


IV.   EDITING THE MENU

      Menu hasn't been pulled into it's seperate content section, so you need 
      to edit "htdocs/include/content.php" and search for the "--MENU--" 
      section in the document.  Just edit the html.


V.    ADDING CONTENT

      1. Copy the 'htdocs/index.php' file
      2. Change the '$page_id' to a unique name with no special characters or
         spaces.
      3. Change the '$page_title' to whatever you want
      4. [Optional] Add the page to the menu by following the instructions of 
         "Editing the menu" above.
      5. Add content as plain [x]html to 'htdocs/content/PAGE_ID.html' where 
         PAGE_ID is the string you used for $page_id.


VI.   EDITING THE STYLE SHEET

      Unless new themes are added, just edit 'htdocs/css/default/default.css'


