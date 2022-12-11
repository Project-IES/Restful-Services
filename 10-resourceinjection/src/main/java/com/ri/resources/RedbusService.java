package com.ri.resources;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;

@Path("/redbus")
public class RedbusService {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{source}/{destination}/buses")
	public StreamingOutput getAvailableBuses(@PathParam("source") String source,
			@PathParam("destination") String destination, @QueryParam("minPrice") double minPrice,
			@QueryParam("maxPrice") double maxPrice, @HeaderParam("platform") String appPlatformType) {
		return (os) -> {
			StringBuilder builder = new StringBuilder();
			builder.append("source :").append(source).append("destination :").append(destination).append("minPrice :")
					.append(minPrice).append(" maxPrice :").append(maxPrice).append(" platform :")
					.append(appPlatformType);
			os.write(builder.toString().getBytes());
			os.close();
		};
	}

}
