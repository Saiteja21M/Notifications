package com.driver;

import java.util.Scanner;

import com.helpers.HelpingMethods;

public class MainDriver {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		System.out.print("Please enter nv_ws_order_id: ");

		int nv_ws_order_id = sc.nextInt();

		HelpingMethods hm = new HelpingMethods();

		hm.showNotificationByOrder(nv_ws_order_id);

		sc.close();
	}

}
