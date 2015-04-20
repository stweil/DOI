package de.mpg.mpdl.doi.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;

import de.mpg.mpdl.doi.controller.DataciteAPIController;
import de.mpg.mpdl.doi.controller.DoiControllerInterface;
import de.mpg.mpdl.doi.exception.DoiAlreadyExistsException;
import de.mpg.mpdl.doi.exception.DoxiException;
import de.mpg.mpdl.doi.exception.MetadataInvalidException;
import de.mpg.mpdl.doi.model.DOI;

@Path("doi")
public class DOIResource {

	private static Logger logger = LogManager.getLogger();

	private DoiControllerInterface doiController = DataciteAPIController.getInstance();
	

	
	@Path("{doi:10\\..+/.+}")
	@PUT
	@Produces("text/plain")
	@Consumes({"text/xml", "application/xml"})
	@PreAuthorize("hasAuthority('USER')")
	public Response create(@PathParam("doi") String doi, @QueryParam("url") String url, String metadataXml) throws Exception {

		String resultDoi = doiController.createDOI(doi, url, metadataXml).getDoi();
		Response r = Response.status(Status.CREATED).entity(resultDoi).build();
		return r;
	}
	
	@PUT
	@Produces("text/plain")
	@Consumes({"text/xml", "application/xml"})
	public Response createAutoOrSuffix(@QueryParam("url") String url, @QueryParam("suffix") String suffix, String metadataXml) throws DoxiException {
		
		String resultDoi = "";
		if(suffix == null)
		{
			resultDoi = doiController.createDOIAutoGenerated(url, metadataXml).getDoi();
		}
		else
		{
			resultDoi = doiController.createDOIKnownSuffix(suffix, url, metadataXml).getDoi();
		}
		
		Response r = Response.status(Status.CREATED).entity(resultDoi).build();
		return r;
	}

	@Path("{doi:10\\..+/.+}")
	@POST
	@Produces("text/plain")
	@Consumes({"text/xml", "application/xml"})
	public Response updateDOI(@PathParam("doi") String doi, @QueryParam("url") String url, String metadataXml) throws DoxiException {
		
		String resultDoi = doiController.updateDOI(doi, url, metadataXml).getDoi();
		Response r = Response.status(Status.CREATED).entity(resultDoi).build();
		return r;
	}

	
	@Path("{doi:10\\..+/.+}")
	@GET
	@Produces("text/plain")
	public String getDOI(@PathParam("doi") String doi) throws DoxiException {
		
		DOI resultDoi = doiController.getDOI(doi);
		return resultDoi.getDoi();
	}

	
	@Path("{doi:10\\..+/.+}")
	@DELETE
	@Produces("text/plain")
	public void inactivate(@PathParam("doi") String doi) throws DoxiException {
		
		doiController.inactivateDOI(doi);
		
	}
	
	/*
	@Path("test")
	@GET
	public Response test() throws DoxiException
	{
		if(1==1)
		{
			throw new DoiAlreadyExistsException();
		}
		
		
		return Response.ok().build();
	}
	
	@Path("test2")
	@GET
	public Response test2() throws DoxiException
	{
		if(1==1)
		{
			throw new MetadataInvalidException("blaa");
		}
		
		
		return Response.ok().build();
	}
	*/


}
