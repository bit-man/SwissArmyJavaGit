---
layout: index
title: JavaGit Style Guide
---


All JavaGit code must conform to the following Java Style Guide. For items not
covered in this document, see [Sun's Code Conventions for the Java Programming
Language](http://java.sun.com/docs/codeconv/html/CodeConvTOC.doc.html).

An Ecplise formatter definintion file that (nearly) matches this policy can be
acquired at: [ https://subversive.cims.nyu.edu/osp/tools/trunk
/Eclipse_Formatter-OSP_Conventions.xml
](https://subversive.cims.nyu.edu/osp/tools/trunk/Eclipse_Formatter-
OSP_Conventions.xml). See below for how to install it.

### Indentation

  * Lines cannot exceed 100 characters 
  * NO TABS. Indentation must be 2 spaces except when continuing an expression or declaration from a previous line, in which case 4 spaces should be used. 

###  Comments

  * All files must include javadocs, including unit tests 
  * Multi-line comments should use /* ... */, while single line comments should use // 
  * Do not use trailing comments. To comment a line of code place the comment immediately above the line. 

###  Declarations

  * Use one declaration per line, and use the standard Java naming conventions. 
    * Link to the Sun [standards](http://java.sun.com/docs/codeconv)
  * Class level declarations belong at the top of the class definition. 

###  Exceptions

  * Never catch Exception or RuntimeException. Always catch the most specific type of exception thrown and handle the exception properly. 

###  Whitespace

  * Avoid ascii art. This includes lining up method arguments that exceed a single line, indenting the right side of declaration assignment operators to make them all line up, and any other use of whitespace that does not indicate a semantic change. Using indentation to indicate a that code is within a code block (and within a certain scope) is required, of course. 
  * Avoid extra blank lines unless it improves readability. 

###  Format Configs

Eclipse

  1. Right click on project in package explorer, select "Properties" 
  2. expand "Java Code Style" 
  3. select "Formatter"
  4. check "Enable project specific settings" 
  5. import [ eclipse format configuration file ](https://subversive.cims.nyu.edu/osp/tools/trunk/Eclipse_Formatter-OSP_Conventions.xml)

