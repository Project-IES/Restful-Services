package com.ri.resources;
import java.util.Calendar;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/loan")
public class LoanService {
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/enquire")
	public String loanEnquiry(@FormParam("applicantName") String applicantName,
			@FormParam("principleAmount") double principleAmount, @FormParam("tenure") int tenure,
			@FormParam("loanType") String loanType, @FormParam("contactNo") String contactNo) {
		StringBuilder builder = new StringBuilder();
		builder.append("applicantName :").append(applicantName).append(" principleAmount :").append(principleAmount)
				.append(" tenure :").append(tenure).append(" loanType :").append(loanType).append(" contactNo :")
				.append(contactNo);
		return builder.toString();
	}
	
	
	//-----------------------------------------------------------------------------------------------------------------------------------------
	//*****************************for Rest Client**************************************
	@GET
	@Produces (MediaType.TEXT_PLAIN)
	@Path("/{applicationNo}/status")
	public String loanStatus (@PathParam("applicationNo") String applicationNo) throws InterruptedException {
		Thread.sleep(1000L);
		System.out.println("dispatching status response: " +Calendar.getInstance().getTimeInMillis()); //what time, dispatching the response
	return applicationNo + " pending";
	}
	
	@GET
	@Produces (MediaType.TEXT_PLAIN)
	@Path("/{income}/{loanType}/elgibility")
	public String getLoanEligibilityAmount (@QueryParam("applicantName") String applicantName, @QueryParam("mobileNo") String mobileNo, 
			@PathParam("income") double grossIncome, @PathParam("loanType") String loanType) throws InterruptedException {
		Thread.sleep(1000L);
		System.out.println("received request at: " +Calendar.getInstance().getTimeInMillis()); 
	return "ApplicationName:" + applicantName + " mobileNo: " + mobileNo + " grossIncome: " + grossIncome + " loanType :" + loanType;
	}
	
	@GET
	@Produces (MediaType.TEXT_PLAIN)
	@Path("/{city}/{area}/nearestbranch")
	public String nearestBranch(@PathParam("city") String city, @PathParam("area") String area,
			@MatrixParam("landMark") String landmark, @QueryParam("loanType") @DefaultValue("any") String loanType) {
		
	return "city: " + city + " area: " + area + " landmark: " + landmark + " loanType: " + loanType;
	}

	@GET
	@Produces (MediaType.TEXT_PLAIN)
	@Path("/{pancard}/cibilscore")
	public String getCibilScore(@PathParam("pancard") String pancardno, @CookieParam("appcode") String appCode,
			@HeaderParam("agentType") String agentType) {
		
	return "pancard: " + pancardno + " appcode: " + appCode + "agent Type: " + agentType;
	}
}
