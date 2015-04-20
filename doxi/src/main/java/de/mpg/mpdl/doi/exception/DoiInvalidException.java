package de.mpg.mpdl.doi.exception;

import de.mpg.mpdl.doi.util.PropertyReader;

/**
 * Subclass of DoxiException signaling that the transfered DOI is not valid
 * @see de.mpg.mpdl.doi.exception.DoxiException
 * @author walter
 *
 */
public class DoiInvalidException extends DoxiException {
	
	
	public DoiInvalidException()
	{
		super(PropertyReader.getMessage("DOI_INVALID_EXCEPTION"));
	}
	
	public DoiInvalidException(String message)
	{
		super(message);
	}
	
	public DoiInvalidException(int statusCode)
	{
		super(statusCode);
	}
	
	public DoiInvalidException(int statusCode, String message)
	{
		super(statusCode, message);
	}
}