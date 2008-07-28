                        JavaGit Website

      TABLE OF CONTENTS

   I. SUMMARY
  II. EDITING CONTENT
 III. EDITING THE MENU
  IV. ADDING CONTENT
   V. EDITING THE STYLE SHEET

I.    SUMMARY

      This directory contains the javagit website.  The contents of the site
      are currently in a .php template system.  This document describes how 
      to modify the contents of the website.

      The code for the template system is released under the GPL v2.0 license.


II.   EDITING CONTENT

      Content for each page is located in 

        htdocs/content/PAGE_ID.html

      Just edit it like standard [x]html.


III.  EDITING THE MENU

      Menu hasn't been pulled into it's seperate content section, so you need 
      to edit "htdocs/include/content.php" and search for the "--MENU--" 
      section in the document.  Just edit the html.


IV.   ADDING CONTENT

      1. Copy the 'htdocs/index.php' file
      2. Change the '$page_id' to a unique name with no special characters or
         spaces.
      3. Change the '$page_title' to whatever you want
      4. [Optional] Add the page to the menu by following the instructions of 
         "Editing the menu" above.
      5. Add content as plain [x]html to 'htdocs/content/PAGE_ID.html' where 
         PAGE_ID is the string you used for $page_id.


V.    EDITING THE STYLE SHEET

      Unless new themes are added, just edit 'htdocs/css/default/default.css'


