package com.ri.resources;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;

@Path("/myntracatalog")
public class MyntraCatalogService {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{category}/products")
	public StreamingOutput browseProducts(@PathParam("category") String category,
			@QueryParam("brand") @DefaultValue("any") String brand,
			@QueryParam("minPrice") @DefaultValue("0") double minPrice,
			@QueryParam("maxPrice") @DefaultValue("9999999") double maxPrice) {
		return (os) -> {
			StringBuilder builder = new StringBuilder();
			builder.append("category : ").append(category).append(" brand :").append(brand).append(" minPrice: ")
					.append(minPrice).append(" maxPrice: ").append(maxPrice);
			os.write(builder.toString().getBytes());
			os.close();
		};
	}
}
