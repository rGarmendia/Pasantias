package org.sevenlabs.modelo.utils;

public class CalcularNumeros {
  static final Double IVA = Double.valueOf(0.12D);
  
  public static Double calcularIVA(Double precio)
  {
    Double iva = Double.valueOf(precio.doubleValue() * IVA.doubleValue());
    double newNum = Math.round(iva.doubleValue() * 100.0D) / 100.0D;
    return new Double(newNum);
  }
  
  public static Double precioTotal(Double precio)
  {
    Double total = Double.valueOf(precio.doubleValue() * IVA.doubleValue() + precio.doubleValue());
    double newNum = Math.round(total.doubleValue() * 100.0D) / 100.0D;
    return new Double(newNum);
  }
}
