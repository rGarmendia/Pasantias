package org.zkforge.converters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.zkoss.util.Locales;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

public class DateCustom implements TypeConverter
{
  String serverDefaultFormat;
  
  public DateCustom()
  {
    this.serverDefaultFormat = getDefaultFormat();
  }
  
  public Object coerceToBean(Object arg0, Component arg1)
  {
    return null;
  }
  





  public Object coerceToUi(Object arg0, Component arg1)
  {
    if (arg0 == null) {
      return null;
    }
    String format = (String)arg1.getAttribute("format");
    if (format == null) {
      format = this.serverDefaultFormat;
    }
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    Date date = (Date)arg0;
    return sdf.format(date);
  }
  









  protected String getDefaultFormat()
  {
    DateFormat df = DateFormat.getDateInstance(2, 
      Locales.getCurrent());
    if ((df instanceof SimpleDateFormat)) {
      String fmt = ((SimpleDateFormat)df).toPattern();
      if ((fmt != null) && (!"M/d/yy h:mm a".equals(fmt)))
        return fmt;
    }
    return "yyyy/MM/dd";
  }
}
