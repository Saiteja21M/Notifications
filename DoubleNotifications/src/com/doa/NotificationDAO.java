package com.doa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dbcon.ConnectionHolder;
import com.dbcon.DBConnectionException;
import com.dbfw.DBFWException;
import com.dbfw.DBHelper;
import com.dbfw.ParamMapper;
import com.domain.Notification;

public class NotificationDAO {
	
	Logger log = null;
	
	@SuppressWarnings("unchecked")
	public List<Notification> getNotificationsByOrder(int nv_ws_order_id) {

		List<Notification> notificationList = new ArrayList<>();

		Connection con = null;
		ConnectionHolder ch = null;

		try {
			ch = ConnectionHolder.getInstance();
			con = ch.getConnection();
			con.setAutoCommit(false);
			final ParamMapper FETCHNOTIFICATION = new ParamMapper() {

				public void mapParam(PreparedStatement preStmt) throws SQLException {
					preStmt.setInt(1, nv_ws_order_id);
				}
			};
			notificationList = DBHelper.executeSelect(con, SQLMapper.GETNOTIFICATIONSBYORDER, SQLMapper.COUNTRYMAPPER,
					FETCHNOTIFICATION);

		} catch (DBConnectionException | SQLException e) {
			try {
				con.rollback();
				throw new DBConnectionException("Unable to connect to db" + e);
			} catch (DBConnectionException | SQLException e1) {
				e1.printStackTrace();
			}

		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return notificationList;
	}

	public int deleteNotificationByNotif(int nv_ws_order_id, String notif_number) {
		Connection con = null;
		ConnectionHolder ch = null;

		int deleted = 0;

		try {
			ch = ConnectionHolder.getInstance();
			con = ch.getConnection();
			con.setAutoCommit(false);
			final ParamMapper DELETENOTIFICATION = new ParamMapper() {

				public void mapParam(PreparedStatement preStmt) throws SQLException {
					preStmt.setInt(1, nv_ws_order_id);
					preStmt.setString(2, notif_number);
				}
			};
			deleted = DBHelper.executeUpdate(con, SQLMapper.DELETENOTIFICATIONBYNOTIF, DELETENOTIFICATION);
			con.commit();

		} catch (DBConnectionException | SQLException | DBFWException e) {
			try {
				con.rollback();
				throw new DBConnectionException("Unable to connect to db " + e);
			} catch (DBConnectionException | SQLException e1) {
				e1.printStackTrace();
			}

		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return deleted;
	}

	public int correctNotifications(int nv_ws_order_id, int notif_item_number) {
		Connection con = null;
		ConnectionHolder ch = null;
		int corrected = 0;

		try {
			ch = ConnectionHolder.getInstance();
			con = ch.getConnection();
			con.setAutoCommit(false);
			final ParamMapper UPDATENOTIFICATION = new ParamMapper() {

				public void mapParam(PreparedStatement preStmt) throws SQLException {
					preStmt.setInt(1, nv_ws_order_id);
					preStmt.setInt(2, notif_item_number-1);
				}
			};
			corrected = DBHelper.executeUpdate(con, SQLMapper.UPDATENOTIFICAIONSBYNOTIF, UPDATENOTIFICATION);
			con.commit();

		} catch (DBConnectionException | SQLException | DBFWException e) {
			try {
				con.rollback();
				throw new DBConnectionException("Unable to connect to db" + e);
			} catch (DBConnectionException | SQLException e1) {
				e1.printStackTrace();
			}

		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return corrected;
	}

}
