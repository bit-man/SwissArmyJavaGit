<?php
function connectMysql($program) {
   include("$program.php");  //include database variables
   $dbcon = mysql_connect($db_host,$db_user,$db_pass)
        or die ("Could not connect");
   mysql_select_db ($db_name,$dbcon)
        or die ("Could not select database");
}
?>
