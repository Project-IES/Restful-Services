package com.yonosbi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.yonosbi.bo.AccountBo;
import com.yonosbi.helper.ConnectionManager;

public class AccountDao {
	public int saveAccount(AccountBo bo) throws SQLException {
		int accountNo = 0;
		boolean flag = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet keyMap = null;

		try {
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(
					"insert into account(account_holder_nm, dob, gender, mobile_no, email_address, account_type, balance) values(?,?,?,?,?,?,?)",
					new String[] { "account_no" });
			pstmt.setString(1, bo.getAccountHolderName());
			pstmt.setDate(2, new java.sql.Date(bo.getDob().getTime()));
			pstmt.setString(3, bo.getGender());
			pstmt.setString(4, bo.getMobileNo());
			pstmt.setString(5, bo.getEmailAddress());
			pstmt.setString(6, bo.getAccountType());
			pstmt.setDouble(7, bo.getBalance());
			pstmt.executeUpdate();

			keyMap = pstmt.getGeneratedKeys();
			if (keyMap.next()) {
				accountNo = keyMap.getInt(1);
			}
			flag = true;
		} catch (SQLException e) {
			flag = false;
			throw e;
		} finally {
			if (con != null) {
				if (flag) {
					con.commit();
				} else {
					con.rollback();
				}
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
		return accountNo;
	}

	public void updateAccount(int accountNo, double amount) throws SQLException {
		boolean flag = false;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement("update account set balance = ? where account_no = ?");
			pstmt.setInt(2, accountNo);
			pstmt.setDouble(1, amount);
			pstmt.executeUpdate();

			flag = true;
		} catch (SQLException e) {
			flag = false;
			throw e;
		} finally {
			if (con != null) {
				if (flag) {
					con.commit();
				} else {
					con.rollback();
				}
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
	}

	public AccountBo getAccount(int accountNo) throws Exception {
		Connection con = null;
		AccountBo accountBo = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(
					"select account.account_no, account.account_holder_nm, account.gender, account.dob, account.mobile_no, account.email_address, account.account_type, account.balance from account account where account.account_no = ?");
			pstmt.setInt(1, accountNo);
			rs = pstmt.executeQuery();
			accountBo = new AccountBo();
			if (rs.next()) {
				accountBo.setAccountNo(rs.getInt(1));
				accountBo.setAccountHolderName(rs.getString(2));
				accountBo.setGender(rs.getString(3));
				accountBo.setDob(rs.getDate(4));
				accountBo.setMobileNo(rs.getString(5));
				accountBo.setEmailAddress(rs.getString(6));
				accountBo.setAccountType(rs.getString(7));
				accountBo.setBalance(rs.getDouble(8));
			} else {
				throw new Exception("account not found");
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
		return accountBo;
	}

	public void deleteAccount(int accountNo) throws SQLException {
		boolean flag = false;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement("delete from account where account_no = ?");
			pstmt.setInt(1, accountNo);
			pstmt.executeUpdate();

			flag = true;
		} catch (SQLException e) {
			flag = false;
			throw e;
		} finally {
			if (con != null) {
				if (flag) {
					con.commit();
				} else {
					con.rollback();
				}
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
	}

}
