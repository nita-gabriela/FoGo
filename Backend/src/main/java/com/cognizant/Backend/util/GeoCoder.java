package com.cognizant.Backend.util;

import com.cognizant.Backend.exception.InvalidAddressException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

public class GeoCoder {
    private static final String NOMINATIM_URL = "https://nominatim.openstreetmap.org/search?format=json&q=";

    public static double[] getCoordinates(String address) {
        RestTemplate restTemplate = new RestTemplate();
        String url = NOMINATIM_URL + address;
        String response = restTemplate.getForObject(url, String.class);

        try {
            JSONArray jsonArray = new JSONArray(response);
            JSONObject firstResult = jsonArray.getJSONObject(0);
            double latitude = firstResult.getDouble("lat");
            double longitude = firstResult.getDouble("lon");

            return new double[]{latitude, longitude};
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidAddressException("Invalid address!");
        }
    }
}
