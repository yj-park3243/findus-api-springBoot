package com.findus.findus.common.util;

import com.findus.findus.model.location.LocationVO;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

@Component
public class GeoPointUtil {

    private static String apiURl;
    private static String key;

    @Value("${googleAPI.url}")
    public void setApiURl(String value) {
        apiURl = value;
    }



    @Value("${googleAPI.key}")
    public void setKey(String value) {
        key = value;
    }

    public static Double[] findGeoPoint(String location) throws IOException ,ParseException{

        /* firebase 메세지 전송 프로토콜 */
        URL url = null;
        HttpURLConnection conn = null;
        OutputStream os= null;
        int responseCode= 0;
        BufferedReader in= null;
        String inputType = "data";
        if (location == null)
            return null;

        url = new URL(apiURl + "?address="+location.replace(" ","%20")+"&key=" + key);

        conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        os = conn.getOutputStream();

        os.flush();
        os.close();

        responseCode = conn.getResponseCode();
        in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        // print result
        System.out.println("———————————push result: " + response.toString());

        JSONParser parser = new JSONParser();
        Object obj =  parser.parse( response.toString() );
        JSONObject jsonObj = (JSONObject) obj;
        ArrayList arr = (ArrayList) jsonObj.get("results");
        HashMap map = (HashMap) arr.get(0);
        map = (HashMap) map.get("geometry");
        map = (HashMap) map.get("location");
        Double[] coords = new Double[2];
        coords[0] = (Double) map.get("lat");
        coords[1] = (Double) map.get("lng");
        return coords;
    }


    public static String findGeoAddr(LocationVO vo, String language) throws IOException ,ParseException{
        /* firebase 메세지 전송 프로토콜 */
        URL url = null;
        HttpURLConnection conn = null;
        OutputStream os= null;
        BufferedReader in= null;
        if (vo == null) return null;

        url = new URL(apiURl + "?latlng="+vo.getLatitude()+","+vo.getLongitude()+"&key=" + key+ "&language=" + language) ;

        conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        os = conn.getOutputStream();
        os.flush();
        os.close();

        in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        // print result
        System.out.println("———————————push result: " + response.toString());
        JSONObject jsonObj = (JSONObject) new JSONParser().parse( response.toString() );
        ArrayList arr = (ArrayList) jsonObj.get("results");
        HashMap map = (HashMap) arr.get(0);
        return (String) map.get("formatted_address") ;
    }
}
