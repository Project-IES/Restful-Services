package com.ri.resources;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import com.ri.dto.ReferenceNo;

@Path("/postal")
public class PostalService {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/track/{referenceNo}")
	public String track(@PathParam("referenceNo") ReferenceNo referenceNo) {
		return referenceNo.toString();
	}

}
