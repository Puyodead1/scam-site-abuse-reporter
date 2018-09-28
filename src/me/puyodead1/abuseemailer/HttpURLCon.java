package me.puyodead1.abuseemailer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

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

		email = response.substring(firstIndex + 16, lastIndex - 5);
		System.out.println("Found Email: " + email + ", Use?: ");
		if (sc.nextLine() == "n") {
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
			send(link + ".json");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("-----------------END WHO.IS-----------------");
	}

	public static void send(String fileName) {
		String SFTPHOST = "54.244.61.152";
		int SFTPPORT = 22;
		String SFTPUSER = "root";
		String SFTPPASS = "JuRi2003";
		String SFTPWORKINGDIR = "/var/www/html";

		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		System.out.println("-----------------SFTP-----------------");
		System.out.println("preparing the host information for sftp.");
		try {
			JSch jsch = new JSch();
			session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
			session.setPassword(SFTPPASS);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			System.out.println("Host connected.");
			channel = session.openChannel("sftp");
			channel.connect();
			System.out.println("sftp channel opened and connected.");
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(SFTPWORKINGDIR);
			File f = new File(fileName);
			channelSftp.put(new FileInputStream(f), f.getName());
			System.out.println("File transfered successfully to host.");
		} catch (Exception ex) {
			System.out.println("Exception found while tranfer the response.");
			System.out.println(ex.getMessage());
		} finally {

			channelSftp.exit();
			System.out.println("sftp Channel exited.");
			channel.disconnect();
			System.out.println("Channel disconnected.");
			session.disconnect();
			System.out.println("Host Session disconnected.");
			System.out.println("-----------------SFTP END-----------------");
		}
	}
}