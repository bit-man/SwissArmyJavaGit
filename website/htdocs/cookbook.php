<?php
$page_id = "cookbook";
$page_title = "JavaGit Cookbook";
$style = "default";
$theme = "default";

include_once("./include/node7.php");

//Content in user preferred style and theme
include_once("$PATH/include/content.php");

$content = new Content($style,$theme,$page_id,$page_title);

$content->PrintHeader();
$content->PrintLeft();
$content->PrintStartContent();
$content->PrintContent();
$content->PrintEndContent();
$content->PrintRight();
$content->PrintFooter();
?>
