package com.yonosbi.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.yonosbi.bo.AccountBo;
import com.yonosbi.bo.AccountStatus;
import com.yonosbi.bo.DepositeBo;
import com.yonosbi.bo.DepositeReceipt;
import com.yonosbi.dao.AccountDao;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.StreamingOutput;

@Path("/account")
public class AccountResource {
	private AccountDao accountDao;

	public AccountResource() {
		accountDao = new AccountDao();
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public StreamingOutput openAccount(InputStream in)
			throws SQLException, ParserConfigurationException, SAXException, IOException, ParseException {
		int accountNo = 0;
		AccountBo accountBo = null;
		AccountStatus accountStatus = null;
		AccountStatusStreamingOutput statusStreamingOutput = null;

		accountBo = buildAccountBo(in);

		accountNo = accountDao.saveAccount(accountBo);

		accountStatus = new AccountStatus();
		accountStatus.setAccountNo(accountNo);
		accountStatus.setAccountHolderName(accountBo.getAccountHolderName());
		accountStatus.setOpenedDate(new Date());
		accountStatus.setBalance(accountBo.getBalance());
		accountStatus.setComments("Account will be activated within 2 working days!");
		accountStatus.setStatus("NEW");
		statusStreamingOutput = new AccountStatusStreamingOutput(accountStatus);

		return statusStreamingOutput;
	}

	@PUT
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public StreamingOutput depositeAmount(InputStream in) throws Exception {
		final DepositeReceipt depositeReceipt = new DepositeReceipt();
		DepositeBo depositeBo = null;
		AccountBo account = null;
		double balance = 0;
		double finalBalance = 0;

		depositeBo = buildDepositeBo(in);
		account = accountDao.getAccount(depositeBo.getAccountNo());
		balance = account.getBalance();
		finalBalance = balance + depositeBo.getAmount();

		accountDao.updateAccount(depositeBo.getAccountNo(), finalBalance);

		depositeReceipt.setAccountNo(depositeBo.getAccountNo());
		depositeReceipt.setAccountHolderName(depositeBo.getAccountHolderName());
		depositeReceipt.setBalance(finalBalance);
		depositeReceipt.setReferenceNo(UUID.randomUUID().toString());
		depositeReceipt.setStatus("Credited");

		return (output) -> {
			StringBuilder builder = null;
			builder = new StringBuilder();
			builder.append("<depositeReceipt><referenceNo>").append(depositeReceipt.getReferenceNo())
					.append("</referenceNo><accountNo>").append(depositeReceipt.getAccountNo())
					.append("</accountNo><accountHolderName>").append(depositeReceipt.getAccountHolderName())
					.append("</accountHolderName><balance>").append(depositeReceipt.getBalance())
					.append("</balance><status>").append(depositeReceipt.getStatus())
					.append("</status></depositeReceipt>");
			output.write(builder.toString().getBytes());
			output.close();
		};
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public StreamingOutput getAccount(@QueryParam("accountNo") int accountNo) throws Exception {

		final AccountBo bo = accountDao.getAccount(accountNo);

		return (output) -> {
			StringBuilder builder = null;
			builder = new StringBuilder();
			builder.append("<account><accountNo>").append(bo.getAccountNo()).append("</accountNo><accountHolderName>")
					.append(bo.getAccountHolderName()).append("</accountHolderName><dob>").append(bo.getDob())
					.append("</dob><gender>").append(bo.getGender()).append("</gender><mobileNo>")
					.append(bo.getMobileNo()).append("</mobileNo><emailAddress>").append(bo.getEmailAddress())
					.append("</emailAddress><balance>").append(bo.getBalance()).append("</balance><accountType>")
					.append(bo.getAccountType()).append("</accountType></account>");

			output.write(builder.toString().getBytes());
			output.close();
		};
	}

	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	public String closeAccount(@QueryParam("accountNo") int accountNo) throws SQLException {
		accountDao.deleteAccount(accountNo);

		return "Account No : " + accountNo + " is closed";
	}

	private class AccountStatusStreamingOutput implements StreamingOutput {
		private AccountStatus accountStatus;

		public AccountStatusStreamingOutput(AccountStatus accountStatus) {
			this.accountStatus = accountStatus;
		}

		@Override
		public void write(OutputStream output) throws IOException, WebApplicationException {
			StringBuilder builder = null;

			builder = new StringBuilder();
			builder.append("<accountStatus><accountNo>").append(accountStatus.getAccountNo())
					.append("</accountNo><accountHolderName>").append(accountStatus.getAccountHolderName())
					.append("</accountHolderName><openedDate>").append(accountStatus.getOpenedDate())
					.append("</openedDate><comments>").append(accountStatus.getComments())
					.append("</comments><balance>").append(accountStatus.getBalance()).append("</balance><status>")
					.append(accountStatus.getStatus()).append("</status></accountStatus>");
			output.write(builder.toString().getBytes());
			output.close();
		}
	}

	private AccountBo buildAccountBo(InputStream in)
			throws ParserConfigurationException, SAXException, IOException, ParseException {
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		NodeList children = null;
		Document doc = null;
		AccountBo bo = null;
		Node rootNode = null;
		Node child = null;
		String nodeValue = null;
		SimpleDateFormat sdf = null;

		factory = DocumentBuilderFactory.newInstance();
		builder = factory.newDocumentBuilder();
		doc = builder.parse(in);

		sdf = new SimpleDateFormat("dd/MM/yyyy");
		bo = new AccountBo();
		rootNode = doc.getFirstChild();
		children = rootNode.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				nodeValue = child.getFirstChild().getNodeValue();

				if (child.getNodeName().equals("accountHolderName")) {
					bo.setAccountHolderName(nodeValue);
				} else if (child.getNodeName().equals("dob")) {
					bo.setDob(sdf.parse(nodeValue));
				} else if (child.getNodeName().equals("gender")) {
					bo.setGender(nodeValue);
				} else if (child.getNodeName().equals("mobileNo")) {
					bo.setMobileNo(nodeValue);
				} else if (child.getNodeName().equals("emailAddress")) {
					bo.setEmailAddress(nodeValue);
				} else if (child.getNodeName().equals("accountType")) {
					bo.setAccountType(nodeValue);
				} else if (child.getNodeName().equals("balance")) {
					bo.setBalance(Double.parseDouble(nodeValue));
				}
			}
		}

		return bo;
	}

	private DepositeBo buildDepositeBo(InputStream in)
			throws ParseException, ParserConfigurationException, SAXException, IOException {
		DepositeBo bo = null;
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		NodeList children = null;
		Document doc = null;
		Node rootNode = null;
		Node child = null;
		String nodeValue = null;
		SimpleDateFormat sdf = null;

		factory = DocumentBuilderFactory.newInstance();
		builder = factory.newDocumentBuilder();
		doc = builder.parse(in);

		sdf = new SimpleDateFormat("dd/MM/yyyy");

		bo = new DepositeBo();
		rootNode = doc.getFirstChild();
		children = rootNode.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				nodeValue = child.getFirstChild().getNodeValue();
				if (child.getNodeName().equals("accountNo")) {
					bo.setAccountNo(Integer.parseInt(nodeValue));
				} else if (child.getNodeName().equals("accountHolderName")) {
					bo.setAccountHolderName(nodeValue);
				} else if (child.getNodeName().equals("depositedDate")) {
					bo.setDepositedDate(sdf.parse(nodeValue));
				} else if (child.getNodeName().equals("accountType")) {
					bo.setAccountType(nodeValue);
				} else if (child.getNodeName().equals("amount")) {
					bo.setAmount(Double.parseDouble(nodeValue));
				}
			}
		}

		return bo;
	}

}
