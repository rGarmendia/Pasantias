package org.sevenlabs.exception;

public class SevenLabsException extends RuntimeException
{
  private int numError = 0;
  
  public SevenLabsException(int numError)
  {
    this.numError = numError;
  }
  
  public SevenLabsException(String message) {
    super(message);
    this.numError = 0;
  }
  
  public int getNumError() {
    return this.numError;
  }
  
  public void setNumError(int numError) {
    this.numError = numError;
  }
}
