<?php
include_once("node7.php");
//include_once("color_theme.php");
include_once("theme.php");
include_once("file_functions.php");
include_once("database_functions.php");

class Content {
   var $fortune;
   var $theme;
   var $styleName;
   var $pageID;
   var $pageTitle;

/**
 * Content constructor
 * style - the css style sheet to use
 * theme - the theme to use
 * page_id - the name of the .txt file used for content
 * page_title - the title of the page
*/
function Content($style="default",$theme='default',$page_id='default',
                 $page_title='default')
{
   global $PATH;
   //echo("Style:".$style);
   $this->fortune = GetLineFromFile("$PATH/content/fortunes.txt");
   $this->styleName = $style;
   //$this->theme = new ColorTheme($theme);
   $this->theme = new Theme($theme);
   $this->pageID = $page_id;
   $this->pageTitle = $page_title;
}

// TODO (acase): Write a function that takes the content from a file
//               and will replace delimited text with specialized
//               content.  For example, if your text file contains
//               {{{pageName}}}, then we replace that with $this->pageName
//               and print that data.
function PrintContentFromFile() {
}

//Print the Header with current theme/style
function PrintHeader()
{
   global $SLOGAN;
   global $LOGO;
   echo('<?xml version="1.0" encoding="utf-8"?>'."\n");
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<!--XHTML_HEADER-->
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta http-equiv="Content-Language" content="en-us" />
  <meta http-equiv="Content-Script-Type" content="text/javascript" />
  <meta http-equiv="Content-Style-Type" content="text/css" />
  <link rel="stylesheet" type="text/css"
      href="/css/<?php echo($this->theme->themeType);?>/<?php echo($this->styleName);?>.css" title="Styles" />
  <meta name="description" content="JavaGit Project" />
  <meta name="keywords" content="Java Git library JavaGit" />

  <meta name="copyright" content="Copyleft 2008 JavaGit project." />
  <meta name="Author" content="JavaGit Developers" />
<!--
  <link rel="icon" href="/images/favicon.ico" type="image/ico" />
  <link rel="shortcut icon" href="/images/favicon.ico" />
-->
<!--
  <link rel="alternate" title="JavaGit RSS Feed" href="/rss.xml" type="application/rss+xml" />
-->
  <title>JavaGit - <?php echo($this->pageTitle);?></title>
</head>
<!--END XHTML_HEADER-->

<body>
<div id="document">

<!--DOCUMENT_HEADER-->
<div id="header">
   <div id="logo"><?php echo($LOGO);?></div>
   <div id="slogan"><?php echo($SLOGAN);?></div>
<!--
   <div id="themeselector"><?php //echo(GetColorBar()); ?></div>
-->
</div>
<!--END DOCUMENT_HEADER-->

<!--MENU-->
<div id="main-body">
<table cellpadding="0" cellspacing="0" border="0">
<tr><td>
<div id="menu-outer">
<div id="menu">
      <span class="bold">JavaGit</span></br>
      <a href="/">Welcome</a><br />
      <a href='http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html'>License (LGPL)</a><br />
      <a href='http://sourceforge.net/news/?group_id=233450'>News</a><br />
      <br />

      <span class="bold">Download</span><br />
      <a href='http://sourceforge.net/project/platformdownload.php?group_id=233450'>
        Releases
      </a><br />
      <br />

      <span class="bold">Documentation</span><br />
<!--
      <a href=''>Install</a><br />
-->
      <a href='cookbook.php'>CookBook</a><br />
	  <a href='javadoc.php'>JavaDocs</a><br />
	  <a href='faq.php'>FAQ</a><br />
      <br />

      <span class="bold">Contributing</span><br />
      <a href='http://sourceforge.net/mail/?group_id=233450'>Mailing Lists</a><br />
<!--
      <a href='http://sourceforge.net/svn/?group_id=233450'>Source Code</a><br />
-->
      <a href='http://www.cs.nyu.edu/~jhl388/javagit/cc-builds/'>Continuous Builds</a><br />
      <a href='http://sourceforge.net/tracker/?group_id=233450'>Bug Tracker</a><br />
      <br />

      <span class="bold">Project Management</span><br />
      <a href='contributors.php'>Contributors</a><br />
      <a href='devpolicies.php'>Dev Policies</a><br />
<!--
      <a href=''>Milestones</a><br />
      <a href=''>TODO list</a><br />
-->
</div>
</div>
</td>
<!--END MENU-->

<?php
}
function PrintLeft()
{
?>

<!--MAIN-->
<td>
<div id="main">
<?php
}


function PrintRight()
{
?>

</div> <!-- main -->
</td></tr>
</table>
</div> <!-- main-body -->
<!--END MAIN-->

<?php
}
function PrintStartContent()
{
?>
<?php
}

function PrintEndContent()
{
?>
<?php
}

//Print the content with current style/theme
function PrintContent()
{
   global $PATH;
   $lines = GetLinesFromFile($PATH."/content/".$this->pageID.".html");
   foreach($lines as $value) {
      echo("$value");
   }
}

function PrintFooter()
{
   global $N7_VERSION;
?>

<!--FOOTER-->
<div id="footer">
   <div id="footer_left"></div>
   <div id="footer_right"></div>
   <div id="footer_center">
      <div id="copyright">
Powered by N7 v<?php echo("$N7_VERSION")?> .:. Copyright &copy; 2008 <a href='mailto:webmaster@javagit.com'>JavaGit Developers</a>, <a href="http://creativecommons.org/licenses/by-nc-sa/2.5/">Some Rights Reserved.</a>
      </div>
   </div>
</div>
<!--END FOOTER-->

<!--SUBFOOTER-->
<div id="subfooter">
   <!--REFERENCES-->
   <div id="reference">
      <a href="http://sourceforge.net">
      <img src="http://sflogo.sourceforge.net/sflogo.php?group_id=233452&amp;type=1"
           style="border:0;width:88;height:31" alt="SourceForge.net" /></a>
      <a href="http://validator.w3.org/check/referer">
      <img style="border:0;width:88px;border:0;"
           src="/images/valid-xhtml10.png" alt="Valid XHTML 1.0!" /></a>
      <a href="http://jigsaw.w3.org/css-validator/">
      <img style="border:0;width:88px;height:31px;"
           src="/images/valid-css.png" alt="Valid CSS!" /></a>
   </div>
   <!--END REFERENCES-->
   <div id="fortune"><?php echo($this->fortune); ?></div>
</div>
<!--END SUBFOOTER-->

</div>
<!--END DOCUMENT-->
</body>
</html>

<?php
} //end function


} //end class
?>
