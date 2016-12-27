package org.sevenlabs.modelo.utils;

import java.awt.Color;
import org.apache.commons.lang.StringUtils;


public class ChartColors
{
  public static Color COLOR_1 = new Color(4080972);
  public static Color COLOR_2 = new Color(2196933);
  public static Color COLOR_3 = new Color(8310525);
  public static Color COLOR_4 = new Color(16774885);
  public static Color COLOR_5 = new Color(16744294);
  
  public static Color COLOR_6 = new Color(10017279);
  public static Color COLOR_7 = new Color(4622769);
  public static Color COLOR_8 = new Color(11631669);
  public static Color COLOR_9 = new Color(16631678);
  
  public static String toHtmlColor(Color color) {
    return "#" + toHexColor(color);
  }
  
  public static String toHexColor(Color color) {
    return StringUtils.leftPad(Integer.toHexString(color.getRGB() & 0xFFFFFF), 6, '0');
  }
}
