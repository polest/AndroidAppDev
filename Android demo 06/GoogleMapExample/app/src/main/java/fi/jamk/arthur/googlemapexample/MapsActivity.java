package fi.jamk.arthur.googlemapexample;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private JSONArray jsonMarkers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        FetchDataTask task = new FetchDataTask();
        task.execute("http://ptm.fi/jamk/android/golf_courses.json");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // set map type
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // add one marker

        mMap.setInfoWindowAdapter(new PopupAdapter(getLayoutInflater()));

    }


    class FetchDataTask extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... urls) {
            HttpURLConnection urlConnection = null;
            JSONObject json = null;
            try {
                URL url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                json = new JSONObject(stringBuilder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) urlConnection.disconnect();
            }
            return json;
        }

        protected void onPostExecute(JSONObject json) {

            try {
                jsonMarkers = json.getJSONArray("kentat");
                for (int i=0;i < jsonMarkers.length();i++) {
                    JSONObject hs = jsonMarkers.getJSONObject(i);

                    BitmapDescriptor markerIcon;

                    switch (hs.get("Tyyppi").toString()) {
                        case "Kulta":  markerIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
                            break;
                        case "Kulta/Etu":  markerIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
                            break;
                        case "Etu":  markerIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                            break;
                        case "?":  markerIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
                            break;

                        default: markerIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE);
                            break;
                    }


                    LatLng makerPos = new LatLng(((Double) hs.get("lat")), (Double) hs.get("lng"));
                    // create one marker
                    final Marker ict2 = mMap.addMarker(new MarkerOptions()
                            .position(makerPos)
                            .title(hs.get("Kentta").toString())
                            .snippet(hs.get("Osoite").toString()+"\n"+hs.get("Puhelin").toString()+"\n"+hs.get("Sahkoposti").toString()+"\n"+hs.get("Webbi").toString())
                            .icon(markerIcon)
                    );

                    // point to jamk/ict and zoom a little
                    if(hs.get("Kentta").toString().equals("Laukaan Peurunkagolf")){
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(makerPos, 5));
                    }

                }
            } catch (JSONException e) {
                Log.e("JSON", "Error getting data.");
            }


        }
    }


}
