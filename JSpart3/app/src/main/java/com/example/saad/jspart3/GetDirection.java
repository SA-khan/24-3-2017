package com.example.saad.jspart3;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class GetDirection extends AsyncTask<String, String, String> {

    private Context context;
    private GoogleMap mMap;
    private List<LatLng> pontos;
    private double userLatt, userLngg;
    private double searchLatt, searchLngg;

    public GetDirection(Context context, GoogleMap mMap,
                        double userLatt, double userLngg, double searchLatt, double searchLngg) {
        this.context = context;
        this.mMap = mMap;
        this.userLatt = userLatt;
        this.userLngg = userLngg;
        this.searchLatt = searchLatt;
        this.searchLngg = searchLngg;
    }

    String stringUrl;

    ProgressDialog dialog;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setMessage("Drawing the route, please wait!");
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);
        dialog.show();
        pontos = new ArrayList<>();
    }


    protected String doInBackground(String... args) {

        stringUrl = "http://maps.googleapis.com/maps/api/directions/json?origin=" + this.userLatt + "," + this.userLngg + "&destination=" + this.searchLatt + "," + this.searchLngg + "&region=uk&sensor=false";

        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(stringUrl);
            HttpURLConnection httpconn = (HttpURLConnection) url
                    .openConnection();
            if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader input = new BufferedReader(
                        new InputStreamReader(httpconn.getInputStream()),
                        8192);
                String strLine = null;

                while ((strLine = input.readLine()) != null) {
                    response.append(strLine);
                }
                input.close();
            }


            String jsonOutput = response.toString();

            JSONObject jsonObject = new JSONObject(jsonOutput);

            JSONArray routeArray = jsonObject.getJSONArray("routes");

            JSONObject routes = routeArray.getJSONObject(0);

            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");

            pontos = decodePoly(encodedString);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    protected void onPostExecute(String file_url) {
        try {
            for (int i = 0; i < pontos.size() - 1; i++) {
                LatLng src = pontos.get(i);
                LatLng dest = pontos.get(i + 1);
                try {
                    //here is where it will draw the polyline in your map
                    Polyline line = mMap.addPolyline(new PolylineOptions()
                            .add(new LatLng(src.latitude, src.longitude),
                                    new LatLng(dest.latitude, dest.longitude))
                            .width(5).color(Color.RED).geodesic(true));
                } catch (NullPointerException e) {
                    Log.e("Error", "NullPointerException onPostExecute: " + e.toString());
                } catch (Exception e2) {
                    Log.e("Error", "Exception onPostExecute: " + e2.toString());
                }

            }

        }catch(Exception e){Log.d("exception", "null pointer");}
        dialog.dismiss();

    }
    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng( (((double) lat / 1E5)),
                    (((double) lng / 1E5) ));
            poly.add(p);
        }

        return poly;
    }




}
