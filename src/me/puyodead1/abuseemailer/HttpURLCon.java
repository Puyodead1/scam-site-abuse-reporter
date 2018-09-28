package me.puyodead1.abuseemailer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class HttpURLCon {

	private final String USER_AGENT = "Mozilla/5.0";
	public static String email = null;
	public static Scanner sc = new Scanner(System.in);

	public void sendGet(String link, int mode) throws Exception {
		String url = null;
		String _ip = null;
		if (mode == 1) {
			url = "https://www.whoisxmlapi.com/whoisserver/WhoisService?apiKey=at_2URBwkWirMUD6GvxwGyEy5FyW3Twc&domainName="
					+ link + "&outputFormat=json";
		} else if (mode == 2) {

			url = "https://reverse-ip-api.whoisxmlapi.com/api/v1?apiKey=at_2URBwkWirMUD6GvxwGyEy5FyW3Twc&ip=" + _ip;
		}

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// Request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		System.out.println(response);

		int firstIndex = response.indexOf("contactEmail");
		int lastIndex = response.lastIndexOf("contactEmail");

		email = response.substring(firstIndex + 16, lastIndex + 33);
		System.out.println("Found Email: " + email + ", Use?: ");
		if(sc.nextLine() == "n") {
			System.out.println("Enter email to use: ");
			email = sc.nextLine();
		}
		
	}
}