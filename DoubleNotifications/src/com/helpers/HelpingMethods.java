package com.helpers;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.doa.NotificationDAO;
import com.domain.Notification;

public class HelpingMethods {

	public Scanner sc = new Scanner(System.in);

	NotificationDAO nd = new NotificationDAO();

	public void showNotificationByOrder(int nv_ws_order_id) {

		List<Notification> notificationList = nd.getNotificationsByOrder(nv_ws_order_id);

		for (Notification notifs : notificationList) {
			System.out.println(notifs.getNv_ws_order_id() + "\t" + notifs.getTs_modification() + "\t"
					+ notifs.getNotification_type() + "\t" + notifs.getNotif_action() + "\t"
					+ notifs.getNotif_item_number());
		}

		System.out.print("Enter 1 to view duplicates or 2 to delete directly from here: ");

		int choice = sc.nextInt();

		switch (choice) {
		case 1:
			viewDuplicates();
			break;
		case 2:
			deletenotifications(nv_ws_order_id);
			break;
		}

	}

	private void deletenotifications(int nv_ws_order_id) {
		System.out.print(
				"Please enter the notif item number of the notification which you want delete seperated by a comma: ");

		String notif_number = sc.next();

		int deleted = 0;

		if (!notif_number.contains(",")) {
			deleted += nd.deleteNotificationByNotif(nv_ws_order_id, notif_number);
		} else {
			String[] notif_numbers = notif_number.split(",");
			Arrays.sort(notif_numbers);
			for (String number : notif_numbers) {
				deleted += nd.deleteNotificationByNotif(nv_ws_order_id, number);
			}
			notif_number = notif_numbers[0];
		}
		System.out.println(deleted + " notification(s) deleted");
		//showNotificationByOrder(nv_ws_order_id);
		
		if(nd.correctNotifications(nv_ws_order_id, Integer.parseInt(notif_number))>0)
			System.out.println("notif_item_numbers have been corrected and C05 has been updated....");
	}

	private void viewDuplicates() {
		// TODO Auto-generated method stub

	}

}
