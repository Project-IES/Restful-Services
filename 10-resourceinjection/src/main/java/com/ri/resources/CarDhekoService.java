package com.ri.resources;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriInfo;

import com.ri.dto.CarDto;

@Path("/cardhekho")
public class CarDhekoService {
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{city}/{carType}")
	public StreamingOutput search(@Context UriInfo uriInfo, @Context HttpHeaders headers ) {
		return (os) -> {
			StringBuilder builder = new StringBuilder();
			List<PathSegment> pathSegments = null;

			pathSegments = uriInfo.getPathSegments();
			for (PathSegment pathSegment : pathSegments) {
				builder.append("path segment: ").append(pathSegment.getPath()).append(" matrix parameters :")
						.append(getMultivaluedMapAsString(pathSegment.getMatrixParameters()));
			}
			builder.append(" query parameters").append(getMultivaluedMapAsString(uriInfo.getQueryParameters()));
			builder.append("headers :").append(getMultivaluedMapAsString(headers.getRequestHeaders()));

			os.write(builder.toString().getBytes());
			os.close();
		};
	}
    
	// BeanParam - instead of declaring all parameter
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/sell/{city}")
	public String sellCar(@BeanParam CarDto carDto) {
		return carDto.toString();
	}

	private String getMultivaluedMapAsString(MultivaluedMap<String, String> multiValuedMap) {
		StringBuilder builder = null;

		builder = new StringBuilder();
		for (String paramName : multiValuedMap.keySet()) {
			builder.append(paramName).append("=");
			for (Object paramValue : multiValuedMap.get(paramName)) {
				builder.append(paramValue).append(",");
			}
		}
		builder.append(";");
		return builder.toString();
	}
}
