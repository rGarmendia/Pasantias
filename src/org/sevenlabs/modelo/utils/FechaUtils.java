package org.sevenlabs.modelo.utils;

import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;




public class FechaUtils
{
  public static final String MES = "mes";
  public static final String DIA = "dia";
  
  public static Integer retornaValorEntreFechas(Date fechaInicio, Date fechaFin, int tipo)
  {
    Calendar calendarInicio = Calendar.getInstance();
    calendarInicio.setTime(fechaInicio);
    int diaInicio = calendarInicio.get(5);
    int mesInicio = calendarInicio.get(2) + 1;
    int anioInicio = calendarInicio.get(1);
    

    Calendar calendarFin = Calendar.getInstance();
    calendarFin.setTime(fechaFin);
    int diaFin = calendarFin.get(5);
    int mesFin = calendarFin.get(2) + 1;
    int anioFin = calendarFin.get(1);
    
    int anios = 0;
    int mesesPorAnio = 0;
    int diasPorMes = 0;
    int diasTipoMes = 0;
    



    if (mesInicio == 2)
    {
      if ((anioFin % 4 == 0) && ((anioFin % 100 != 0) || (anioFin % 400 == 0)))
      {
        diasTipoMes = 29;
      }
      else {
        diasTipoMes = 28;
      }
    } else if (mesInicio <= 7)
    {
      if (mesInicio % 2 == 0) {
        diasTipoMes = 30;
      } else {
        diasTipoMes = 31;
      }
    } else if (mesInicio > 7)
    {
      if (mesInicio % 2 == 0) {
        diasTipoMes = 31;
      } else {
        diasTipoMes = 30;
      }
    }
    




    if ((anioInicio > anioFin) || ((anioInicio == anioFin) && (mesInicio > mesFin)) || (
      (anioInicio == anioFin) && (mesInicio == mesFin) && (diaInicio > diaFin)))
    {

      return Integer.valueOf(-1);
    }
    if (mesInicio <= mesFin) {
      anios = anioFin - anioInicio;
      if (diaInicio <= diaFin) {
        mesesPorAnio = mesFin - mesInicio;
        diasPorMes = diaFin - diaInicio;
      } else {
        if (mesFin == mesInicio) {
          anios--;
        }
        mesesPorAnio = (mesFin - mesInicio - 1 + 12) % 12;
        diasPorMes = diasTipoMes - (diaInicio - diaFin);
      }
    } else {
      anios = anioFin - anioInicio - 1;
      System.out.println(anios);
      if (diaInicio > diaFin) {
        mesesPorAnio = mesFin - mesInicio - 1 + 12;
        diasPorMes = diasTipoMes - (diaInicio - diaFin);
      } else {
        mesesPorAnio = mesFin - mesInicio + 12;
        diasPorMes = diaFin - diaInicio;
      }
    }
    





    long returnValue = -1L;
    
    switch (tipo)
    {
    case 0: 
      returnValue = anios;
      
      break;
    

    case 1: 
      returnValue = anios * 12 + mesesPorAnio;
      
      break;
    

    case 2: 
      long millsecsPerDay = 86400000L;
      returnValue = (fechaFin.getTime() - fechaInicio.getTime()) / millsecsPerDay;
      
      break;
    

    case 3: 
      returnValue = mesesPorAnio;
      
      break;
    

    case 4: 
      returnValue = diasPorMes;
      
      break;
    }
    
    


    return new Integer(new Float((float)returnValue).intValue());
  }
}
