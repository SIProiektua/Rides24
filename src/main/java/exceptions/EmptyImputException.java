package exceptions;

public class EmptyImputException extends Exception {
	public EmptyImputException()
	  {
	    super();
	  }
	
	/**This exception is triggered if the question already exists 
	  *@param s String of the exception
	  */
	  public EmptyImputException(String s)
	  {
	    	s="Parametro hutsak daude";
	  }
}
