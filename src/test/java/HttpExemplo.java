/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;

public class HttpExemplo {

    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {
       
     //Recebe a lista em formato json atrave's da URL abaixo	
      HttpExemplo http = new HttpExemplo();
      String json = http.sendGet("http://api.adsim.co/refrigerante/listar","GET"); 
      
      Gson gson =  new Gson();
   
      //cria uma lista do tipo Refri
      ArrayList<Refri> refris = new ArrayList<Refri>();
      
      JsonParser parser = new JsonParser();
      // Fazendo o parse do JSON para um JsonArray
      JsonArray array = parser.parse(json).getAsJsonArray();
      for (int i = 0; i < array.size(); i++) {
          // Adicionando na lista de refri o mesmo refri da lista que veio pela URL 
          refris.add(gson.fromJson(array.get(i), Refri.class));
      }
      
      System.out.println("Marcac | Sabor | Valor | Quantidade");
      for (int i = 0; i < refris.size(); i++) {
    	  System.out.println(refris.get(i).getMarca() +" "+ refris.get(i).getSabor()+" "+refris.get(i).getValor()+" "+ refris.get(i).getQuantidade());
	}
      
    }

    // HTTP GET request
    private String sendGet(String url, String method) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod(method);

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();

    }

    // HTTP POST request
    private void sendPost(String url, String urlParameters, String method) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        //add reuqest header
        con.setRequestMethod(method);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		//String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }

    

}
