package me.puyodead1.abuseemailer;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		ArrayList<String> info = new ArrayList<String>();
		Scanner sc = new Scanner(System.in);

		System.out.println("Please paste the ADAROSS post: ");

		while (sc.hasNext()) {
			String i = sc.nextLine();
			if (i.equals("e")) {
				break;
			} else {
				info.add(i);
			}
		}
		System.out.println("Broke");
		String _domain = GetDomain(info.get(3));
		String _link = info.get(3);

		System.out.println("Other Links: ");
		String _otherLinks = sc.nextLine();
		/*if(_otherLinks == null || _otherLinks == "" || _otherLinks == " ") {
			_otherLinks = "N/A";
		}*/
		
		System.out.println("Screen Shots: ");
		String _sc = sc.nextLine();
		/*if(_sc == null || _sc == "" || _sc == " ") {
			_sc = "N/A";
		}*/

		System.out.println("Comments: ");
		String _comments = sc.nextLine();
		/*if(_comments == null || _comments == "" || _comments == " ") {
			_comments = "N/A";
		}*/

		System.out.println("Please wait....");

		HttpURLCon http = new HttpURLCon();

		http.sendGet(_domain, 1);

		SendEmail.SendMail(HttpURLCon.email, _domain, _link, _otherLinks, _sc, _comments);
	}

	public static String OtherLinks() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter any other links if any: ");
		String _otherLinks = null;

		if (sc.nextLine() == null || sc.nextLine() == "" || sc.nextLine() == " ") {
			_otherLinks = "N/A";
			sc.close();
		} else {
			_otherLinks = sc.nextLine();
		}
		return _otherLinks;
	}

	public static String GetDomain(String _link) {
		String _domain = null;
		if (_link == null || _link == "" || _link == " ") {
			System.out.println("No link found!");
			System.exit(1);
		} else {
			String a[] = _link.split("/");
			_domain = a[0] + "//" + a[2];
		}
		return _domain;
	}
}
