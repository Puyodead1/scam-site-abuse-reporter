package me.puyodead1.abuseemailer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class HttpURLCon {

	private final String USER_AGENT = "Mozilla/5.0";
	public static String email = null;
	public static Scanner sc = new Scanner(System.in);

	public void sendGet(String link, int mode) throws Exception {
		
		JsonParser parser = new JsonParser();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
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
		int lastIndex = response.lastIndexOf("domainNameExt");

		email = response.substring(firstIndex + 16, lastIndex-5);
		System.out.println("Found Email: " + email + ", Use?: ");
		if(sc.nextLine() == "n") {
			System.out.println("Enter email to use: ");
			email = sc.nextLine();
		}
		String a[] = link.split("//");
		link = a[1];
		try { 
		PrintWriter out = new PrintWriter(link + ".json");
		JsonElement el = parser.parse(response.toString());
		out.println(gson.toJson(el));
		
		out.close();
		System.out.println("Who.is file saved as: " + link + ".json");
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
}