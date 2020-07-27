package Project.covid.hack;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Arrays;
import java.util.List;

public class Mapa extends FragmentActivity implements OnMapReadyCallback ,
        GoogleMap.OnPolylineClickListener,
        GoogleMap.OnPolygonClickListener  {


    private GoogleMap map;
    private LocationRequest mLocationRequest;
    public static final int LOCATION_REQUEST_CODE = 1;
    public static final int SETTINGS_REQUEST_CODE = 2;
    private GoogleMap.OnCameraIdleListener mCameraListener;
    private FusedLocationProviderClient mFusedLocation;
    private LatLng origen;
    private Marker marker;

    private static final int POLYGON_STROKE_WIDTH_PX = 8;
    private static final int PATTERN_DASH_LENGTH_PX = 20;
    private static final int PATTERN_GAP_LENGTH_PX = 20;
    private static final PatternItem DOT = new Dot();
    private static final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    private static final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH);
    private static final List<PatternItem> PATTERN_POLYLINE_DOTTED = Arrays.asList(GAP, DOT);
    private static final List<PatternItem> PATTERN_POLYGON_BETA =
            Arrays.asList(DOT, GAP, DASH, GAP);

    PolygonOptions polygonOptions = new PolygonOptions();

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
                origen = new LatLng(location.getLatitude(),location.getLongitude());
                if (getApplicationContext() != null) {
                    //obtener locatizacion en tiempo real
                    map.moveCamera(CameraUpdateFactory.newCameraPosition(
                            new CameraPosition.Builder()
                                    .target(new LatLng(location.getLatitude(), location.getLongitude()))
                                    .zoom(17f)
                                    .build()
                    ));
                    marker = map.addMarker(new MarkerOptions().position(
                            new LatLng(location.getLatitude(), location.getLongitude()))
                            .title("Tu poscición")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.mascara))
                    );
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        mFusedLocation = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(googleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.setOnCameraIdleListener(mCameraListener);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(5);


        Polygon polygon1 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(-12.5860861,-69.1896241),
                        new LatLng(-12.5854844,-69.1904487),
                        new LatLng(-12.5861313,-69.1911644),
                        new LatLng(-12.5868895,-69.1903572),
                        new LatLng(-12.5862513,-69.1898655),
                        new LatLng(-12.5860861,-69.1896241)
                        ));
        polygon1.setTag("A");

        polygon1.setFillColor(Color.rgb(241, 16, 16));

        Polygon polygon2 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(-12.585609,-69.1877074),
                        new LatLng(-12.585188,-69.1872851),
                        new LatLng(-12.5845279,-69.1880584),
                        new LatLng(-12.5849299,-69.1885604),
                        new LatLng(-12.585609,-69.1877074)
                ));
        polygon2.setTag("B");
        polygon2.setFillColor(Color.rgb(23, 167, 0));

        Polygon polygon3 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(-12.5886289,-69.1891353),
                        new LatLng(-12.5878923,-69.1899969),
                        new LatLng(-12.5869149,-69.188842),
                        new LatLng(-12.5876009,-69.1880743),
                        new LatLng(-12.5886289,-69.1891353)
                ));
        polygon3.setTag("C");
        polygon3.setFillColor(Color.rgb(46, 134, 193 ));

        startLocacion();

        googleMap.setOnPolylineClickListener(this);
        googleMap.setOnPolygonClickListener(this);
    }

    private boolean gpsActive(){
        boolean isActive = false;
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            isActive = true;
        }

        return isActive;
    }
    public void startLocacion(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                if(gpsActive())
                {
                    mFusedLocation.requestLocationUpdates(mLocationRequest,mLocationCallback, Looper.myLooper());
                    map.setMyLocationEnabled(true);
                }
                else{
                    showAlertDialog();
                }

            }
            else {
                checkLocationPermision();
            }
        }
        else {
            if(gpsActive()){
                mFusedLocation.requestLocationUpdates(mLocationRequest,mLocationCallback, Looper.myLooper());
                map.setMyLocationEnabled(true);

            }
            else {
                showAlertDialog();
            }

        }
    }
    private void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Por favor activa tu ubicación para continuar")
                .setPositiveButton("Configuraciones", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),SETTINGS_REQUEST_CODE);
                    }
                }).create().show();
    }

    public void checkLocationPermision(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            mFusedLocation.requestLocationUpdates(mLocationRequest,mLocationCallback, Looper.myLooper());

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
                new AlertDialog.Builder(this)
                        .setTitle("Proporciona los permisos para continuar")
                        .setMessage("Esta aplicación requiere los permisos para continuar")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(Mapa.this , new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);
                            }
                        })
                        .create()
                        .show();
            }
            else {
                ActivityCompat.requestPermissions(Mapa.this , new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onPolygonClick(Polygon polygon) {
        Toast.makeText(this, "Zona "+polygon.getTag().toString()+" de infección", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPolylineClick(Polyline polyline) {
        if ((polyline.getPattern() == null) || (!polyline.getPattern().contains(DOT))) {
            polyline.setPattern(PATTERN_POLYLINE_DOTTED);
        } else {
            polyline.setPattern(null);
        }
        Toast.makeText(this, "Zona " + polyline.getTag().toString()+" de infección",
                Toast.LENGTH_SHORT).show();
    }
}