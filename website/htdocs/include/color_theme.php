<?php
include_once("color_functions.php");
include_once("theme.php");

class ColorTheme extends Theme {
   var $colorName;
   var $colorNumber;

function ColorTheme($themeType='')
{
   parent::Theme($themeType);
   $this->colorName = $themeType;
   $this->colorNumber = GetColorNumber($this->colorName);
}

}
?>
