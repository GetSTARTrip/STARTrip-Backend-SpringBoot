package com.startrip.core.util;

import lombok.extern.slf4j.Slf4j;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

@Slf4j
public class GoogleGeocodeApi {

    public static Map<String, String> getGeoDataByAddress(String completeAddress) {
        try {
            String API_KEY = "AIzaSyA0epGXSi7Dn0PMmhYr2_9zaWNxECbfup8"; // 개인 API_KEY, 30번 제한
            String surl = "https://maps.googleapis.com/maps/api/geocode/json?address="+ URLEncoder.encode(completeAddress, "UTF-8")+"&key="+API_KEY;
            URL url = new URL(surl);
            InputStream is = url.openConnection().getInputStream();


            BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            JSONObject jo = new JSONObject(responseStrBuilder.toString());
            JSONArray results = jo.getJSONArray("results");
            String lat = null;
            String lng = null;
            String region = null;
            String province = null;
            String zip = null;
            Map<String, String> ret = new HashMap<String, String>();
            if(results.length() > 0) {
                JSONObject jsonObject;
                jsonObject = results.getJSONObject(0);
                ret.put("lat", String.valueOf(jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lat")));
                ret.put("lng", String.valueOf(jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lng")));
                log.info("LAT:"+lat+";LNG:"+lng);
                JSONArray ja = jsonObject.getJSONArray("address_components");
                for(int l=0; l<ja.length(); l++) {
                    JSONObject curjo = ja.getJSONObject(l);
                    String type = curjo.getJSONArray("types").getString(0);
                    String short_name = curjo.getString("short_name");
                    if(type.equals("postal_code")) {
                        log.info("POSTAL_CODE: "+short_name);
                        ret.put("zip", short_name);
                    }
                    else if(type.equals("administrative_area_level_3")) {
                        log.info("CITY: "+short_name);
                        ret.put("city", short_name);
                    }
                    else if(type.equals("administrative_area_level_2")) {
                        log.info("PROVINCE: "+short_name);
                        ret.put("province", short_name);
                    }
                    else if(type.equals("administrative_area_level_1")) {
                        log.info("REGION: "+short_name);
                        ret.put("region", short_name);
                    }
                }
                return ret;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
