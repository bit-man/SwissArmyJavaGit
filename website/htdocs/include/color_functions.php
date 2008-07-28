<?php
if (!isset($_COOKIE['myColor']))
{
   $colorName = GetDefaultColorName();
}
else
{
   $colorName = $_COOKIE['myColor'];
}
$colorNumber = GetColorNumber($colorName);
?>
<?php
function GetColorNumber($colorName)
{
   if ($colorName == 'black') {
      return "#000000";
   } elseif ($colorName == 'darkblue') {
      return "#002277";
   } elseif ($colorName == 'green') {
      return "#034B17";
   } elseif ($colorName == 'lightblue') {
      return "#0077AA";
   } elseif ($colorName == 'midblue') {
      return "#2805D7";
   } elseif ($colorName == 'olive') {
      return "#66672F";
   } elseif ($colorName == 'orange') {
      return "#A15E0F";
   } elseif ($colorName == 'purple') {
      return "#770077";
   } elseif ($colorName == 'red') {
      return "#810416";
   } elseif ($colorName == 'teal') {
      return "#0099AA";
   } elseif ($colorName == 'violet') {
      return "#770055";
   } else {
      return "#002277";
   }
}
?>
<?php
function GetColorBar()
{
   $returnValue = '
<form action="/colorSelected.php" method="post">
<p>
<input type="image" src="/images/transparent.gif" alt="green" name="submit" value="green" class="green" />
<input type="image" src="/images/transparent.gif" alt="darkblue" name="submit" value="darkblue" class="darkblue" />
<input type="image" src="/images/transparent.gif" alt="midblue" name="submit" value="midblue" class="midblue" />
<input type="image" src="/images/transparent.gif" alt="lightblue" name="submit" value="lightblue" class="lightblue" />
<input type="image" src="/images/transparent.gif" alt="teal" name="submit" value="teal" class="teal" />
<input type="image" src="/images/transparent.gif" alt="olive" name="submit" value="olive" class="olive" />
<input type="image" src="/images/transparent.gif" alt="orange" name="submit" value="orange" class="orange" />
<input type="image" src="/images/transparent.gif" alt="red" name="submit" value="red" class="red" />
<input type="image" src="/images/transparent.gif" alt="purple" name="submit" value="purple" class="purple" />
<input type="image" src="/images/transparent.gif" alt="violet" name="submit" value="violet" class="violet" />
</p>
</form>
   ';

   return $returnValue;
}
?>
<?php
function GetDefaultColor()
{
   $color = array();
   $color['colorName'] = 'green';
   $color['colorNumber'] = '#03417B';
   return $color;
}
?>
<?php
function GetDefaultColorName()
{
   return 'green';
}
?>
<?php
function GetBoardColor1($colorNumber)
{
$colorNumber = ltrim($colorNumber,"#");
$colorNumber = chunk_split($colorNumber, 2, ':');
$color = explode(":",$colorNumber);
$colorRed = hexdec($color[0]);
$colorGreen = hexdec($color[1]);
$colorBlue = hexdec($color[2]);

$colorRed = AddValue($colorRed,"0");
$colorGreen = AddValue($colorGreen,"0");
$colorBlue = AddValue($colorBlue,"0");

$colorRed = dechex($colorRed);
$colorGreen = dechex($colorGreen);
$colorBlue = dechex($colorBlue);

if (strlen($colorRed) < 2) {
   $colorRed = "0$colorRed";
}
if (strlen($colorGreen) < 2) {
   $colorGreen = "0$colorGreeen";
}
if (strlen($colorBlue) < 2) {
   $colorBlue = "0$colorBlue";
}

$colorNumber = "#$colorRed$colorGreen$colorBlue";
//echo "GetBoardColors.php colorNumber: $colorNumber";
return $colorNumber;
}
?>
<?php
function GetBoardColor2($colorNumber)
{
$colorNumber = ltrim($colorNumber,"#");
$colorNumber = chunk_split($colorNumber, 2, ':');
$color = explode(":",$colorNumber);

$colorRed = hexdec($color[0]);
$colorGreen = hexdec($color[1]);
$colorBlue = hexdec($color[2]);

$colorRed = AddValue($colorRed,"24");
$colorGreen = AddValue($colorGreen,"24");
$colorBlue = AddValue($colorBlue,"24");

$colorRed = dechex($colorRed);
$colorGreen = dechex($colorGreen);
$colorBlue = dechex($colorBlue);

if (strlen($colorRed) < 2) {
   $colorRed = "0$colorRed";
}
if (strlen($colorGreen) < 2) {
   $colorGreen = "0$colorGreeen";
}
if (strlen($colorBlue) < 2) {
   $colorBlue = "0$colorBlue";
}

$colorNumber = "#$colorRed$colorGreen$colorBlue";
//echo "GetBoardColors.php colorNumber: $colorNumber";
return $colorNumber;
}
?>
<?php
function AddValue($colorNumber,$changeValue)
{
   $colorNumber = $colorNumber + $changeValue;

   if ($colorNumber > 255)
   {
      $colorNumber = 255;
   }

   return $colorNumber;
}
?>
