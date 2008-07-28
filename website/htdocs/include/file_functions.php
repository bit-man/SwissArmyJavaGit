<?php
  //include_once("/home/groups/g/gi/gitclipse/htdocs/include/node7.php");
?>
<?php
/**
 * Name: GetLinesFromFile
 * Description: Returns a string of a random line in $fileName
 * Parameters: fileName - full path and filename of file of a ascii file 
 */
function GetLinesFromFile($fileName)
{
        $lines = array();
        $lines = file($fileName);
        return $lines;
}
?>
<?php
/**
 * Name: PrintFile
 * Description: Prints out the contents of $fileName
 * Parameters: fileName - full path and filename of an ascii file 
 */
function PrintFile($fileName)
{
	$fh = fopen($fileName, 'r');
	$contents = fread($fh, filesize($fileName));
	fclose($fh);
	echo $contents;

}
?>
<?php
/**
 * Name: GetLineFromFile
 * Description: Returns a string of a random line in $fileName
 * Parameters: fileName - full path and filename of file of a ascii file 
 * Parameters: lineNumber - line you want to return fist line is zero 
 */
function GetLineFromFile($fileName,$lineNumber=-1)
{
        $lines = array();
        $lines = file($fileName);
        $numOfLines = count($lines);
	if (($lineNumber < $numOfLines) && ($lineNumber >= 0))
		$index = $lineNumber;
	else 
        	$index = rand(0,$numOfLines-1);
        return $lines[$index];
}
?>
<?
function GetFileType($filename) {
   ereg( ".*\.([a-zA-z0-9]{0,5})$", $filename, $regs );
   $f_ext = $regs[1];

   $types['image']    = array ('jpg','gif','png','jpeg','bmp');
   $types['document'] = array ('sxw','doc','rtf');
   $types['text']     = array ('txt','text','nfo');
   $types['archive']  = array ('zip','tgz','gz','tar','rar','gzip','arj');
   $types['app']      = array ('bin','sh','exe','out','elf');
	$types['music']    = array ('mp3','mpeg3','m3u','ogg');
   $types['video']    = array ('asf','mpg','mpeg','divx','dvx','avi','mov'); 
   $types['code']     = array ('html','htm','php','phps','c','cpp','c++',
                               'java','h','asm','sh','csh','tsh','ksh');

	foreach ($types as $k => $v) {
		if (in_array($f_ext, $v)) {
			return $k;
			break;
		}
	}
	return 'type-unknown';
}
?>
<?php
function GetFileImage($filename)
{
   global $PATH;

   $types = array('jpg','gif','png','jpeg','bmp','tiff');

   ereg(".*\.([a-zA-z0-9+]{0,5})$", $filename, $regs );
   $f_ext = $regs[1];
   $f_ext = strtolower($f_ext);

   // Miniature Image thumbnails for graphics
   if (in_array($f_ext, $types)) {
      return "<img src='$filename' width='48' height='48' alt='thumbnails' style='border:0;' />";
   } else if (is_file("$PATH/images/icons/".$f_ext.".png")) {
      return "<img src='/images/icons/".$f_ext.".png' width='48' height='48' alt='$f_ext' style='border:0;' />";
   } else {
      return "<img src='/images/icons/unknown.gif' width='48' height='48' alt='$f_ext' style='border:0;' />";
   }
}
?>
<?php
/**
 * Function: array getFilesArray(string path, char sortType)
 * Description: This Function will return an array of all the files in
 *              the directory path given and sort according to file or
 *              directory
 * Parameters:
 *    path - a string of for a full path name
 *    sortType - currently unused, only supports directory sorting right now
 * Returns:
 *    array - of all Files(files/directories) in a directory
 * Author: Andrew Case
 * Last Modified Date: 04.APR.03
 * Last Modified By: Andrew Case
 */
function getFilesArray($path, $sortType="", $filesToIgnore="") {
   $path = parsedPath($path);
   $handle = opendir($path);
   while (false !== ($file = readdir($handle))) {
      $fileArray[count($fileArray)] = $file;
   }
   closedir($handle);

   sort($fileArray);

   $myFileArray = array();

   if ($sortType == 'F') { //Sort by File

   } else { // Sort by Directory
      foreach ($fileArray as $fileName) {
         $file = $path.$fileName;
         if (is_dir($file)) {
            array_push($myFileArray, $fileName);
         }
      }
      foreach ($fileArray as $fileName) {
         $file = $path.$fileName;
         if (is_file($file)) {
            array_push($myFileArray, $fileName);
         }
      }
   }
   return $myFileArray;

} //end getFilesArray()
?>
<?php
/**
 * Function: printRemoteDirectory()                                    *
 * Description: This Function will return print out html with links to *
 *              files on a remote server, but directories on the       *
 *              current server (kinda twisted I know).  Mainly is used *
 *              for linking to a streaming media server right now.     *
 * Parameters: None                                                    *
 * Returns:                                                            *
 *    prints to request                                                *
 * Author: Andrew Case                                                 *
 * Last Modified Date: 10.09.05                                        *
 * Last Modified By: Andrew Case                                       *
 */
function PrintRemoteDirectory()
{
   $folderImage = "$PATH/images/icons/folder.gif";
   $currentFlag = true;
   $subdirFlag = true;
   $fileArray = getFilesArray("./");

   foreach($fileArray as $fileName)
   {
      $fileImage = GetFileImage($fileName);

      if (is_file($fileName)) // If a file
      {
         echo "<a href='./$fileName'>$fileImage</a>\n";
         echo "<a href='./$fileName'>$fileName</a><br/>\n";
      }
      else if (is_dir($fileName)) // If a directory
      {
         if (($fileName == ".") && ($currentFlag)) // If show current directory
         {
            echo "<a href=\"./\">\n";
            echo "<img src='$folderImage' width='48' height='48' alt='directory' style='border:0;' /></a>\n";
            echo "<a href=\"./\">\n";
            echo "$fileName</a><br/>";
         }
 	 elseif (($fileName == "..") && ($subdirFlag)) // If showing subdirectory
         {
            echo "<a href=\"../\">\n";
            echo "<img src='$folderImage' width='48' height='48' alt='directory' style='border:0;' /></a>\n";
            echo "<a href=\"../\">\n";
            echo "$fileName</a><br/>";
         }
         else  //If a standard directory
         {
            echo "<a href=\"./$fileName\">\n";
            echo "<img src='$folderImage' width='48' height='48' alt='directory' style='border:0;' /></a>\n";
            echo "<a href=\"./$fileName\">\n";
            echo "$fileName</a><br/>\n";
         }
      }
      else
         echo "not a file or dir<br/>";
   } // end foreach
} // end printDirectory
?>
<?php
function parsedPath($path)
{
	$path = ereg_replace("\.\.","",$path);
	return $path;
}
?>
<?php
   global $URL;
   global $PATH_TO_FILES;
   global $fileImage;
   global $folderImage;
   global $currentFlag;
   global $IMAGE_TEXT;
   global $IMAGE_MUSIC;
   global $IMAGE_IMAGE;
   global $IMAGE_ARCHIVE;
   global $IMAGE_CODE;
   global $IMAGE_APP;
   global $IMAGE_UNKNOWN;

   // Path to location of files
   $URL = "/nj-files-view/";

   // Path to location of files
   $PATH_TO_FILES = $_SERVER["DOCUMENT_ROOT"]."/nj-files-view/";

   //Show current/sub directory, aka: ./ ../
   $currentFlag = true;
   #$subdirFlag  = false;
   $subdirFlag  = true;

   //What image for files or folders
   $IMAGE_TEXT    = '/images/icons/gnome-gmush.gif';
   $IMAGE_MUSIC   = '/images/icons/music.gif';
   $IMAGE_IMAGE   = '/images/icons/image1.gif';
   $IMAGE_APP     = '/images/icons/application.gif';
   $IMAGE_CODE    = '/images/icons/gnome-gmush.gif';
   $IMAGE_ARCHIVE = '/images/icons/compressed2.gif';
   $IMAGE_UNKNOWN = '/images/icons/gnome-gmush.gif';
   $folderImage   = "/images/icons/gnome-folder.gif";
?>
<?
function GetFileImage2($fileType)
{
   include_once('conf.php');
 
   global $IMAGE_TEXT;
   global $IMAGE_MUSIC;
   global $IMAGE_IMAGE;
   global $IMAGE_CODE;
   global $IMAGE_APP;
   global $IMAGE_ARCHIVE;
   global $IMAGE_UNKNOWN;

   if ($fileType == 'music') {
      return $IMAGE_MUSIC;
   } elseif ($fileType == 'text') {
      return $IMAGE_TEXT;
   } elseif ($fileType == 'image') {
      return $IMAGE_IMAGE;
   } elseif ($fileType == 'archive') {
      return $IMAGE_ARCHIVE;
   } elseif ($fileType == 'code') {
      return $IMAGE_CODE;
   } elseif ($fileType == 'app') {
      return $IMAGE_APP;
   } else {
      return $IMAGE_UNKNOWN;
   }
}
?>
