package com.ri.resources;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/account")
public class AccountService {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{accountNo}/balance")
	public String getAccountBalance(@PathParam("accountNo") String accountNo, @CookieParam("appId") String appId) {
		return "accountNo : " + accountNo + " appId : " + appId;
	}

}
