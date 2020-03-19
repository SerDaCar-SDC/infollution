package com.serdacar.infollution;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.serdacar.infollution.database.EstacionDataSource;
import com.serdacar.infollution.model.Estacion;
import com.serdacar.infollution.retrofit.RetrofitClient;
import com.serdacar.infollution.retrofit.model.APIEstaciones;
import com.serdacar.infollution.retrofit.model.DatoHorario;
import com.serdacar.infollution.retrofit.model.Datos;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FirstActivity extends AppCompatActivity {
    static final String CLAVE_EMAIL = "EMAIL";
    FirebaseAuth fbAuth;

    // TextView tv;
    ImageView ivLogo;
    String email;

    // GRAFICAS

    String da;
    String le;
    String mc;
    String dn;


    private LocationManager miLocalizacion;
    private Location loc;

    private double miLatitud;
    private double miLongitud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        getSupportActionBar().hide();
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(getResources().getColor(R.color.colorAzulOscuro)));

        // tv = findViewById(R.id.tvBienvenida);
        ivLogo = findViewById(R.id.ivMenuLogo);
        ivLogo.setEnabled(false);


        email = getIntent().getStringExtra(RegisterActivity.CLAVE_EMAIL);
        // tv.setText(String.format(getString(R.string.tv_bienvenida_first), email));

        fbAuth = FirebaseAuth.getInstance();


        /* * * *  ENCONTRAR UBICACION * * * * * * * * * * * * * * * * * */

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "Es necesario que active su GPS", Toast.LENGTH_LONG).show();
            return;
        } else {

            miLocalizacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            loc = miLocalizacion.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }


        miLatitud = loc.getLatitude();
        miLongitud = loc.getLongitude();

        Toast.makeText(this, miLatitud + " " + miLongitud, Toast.LENGTH_LONG).show();


        EstacionDataSource eds = new EstacionDataSource(this);

        ArrayList<Estacion> listaEstaciones = eds.leerEstacionLista();
        Estacion estacionCerca = encontrarEstMasCerca(listaEstaciones, miLatitud, miLongitud);

        estacionCerca.getLatitud();
        estacionCerca.getLongitud();
        Toast.makeText(this, estacionCerca.getNombre(), Toast.LENGTH_LONG).show();

        /* * * * * * * * * * * * * * * * * * * * * */


        Retrofit retrofit = RetrofitClient.getClient(APIEstaciones.BASE_URL);
        APIEstaciones apiEstaciones = retrofit.create(APIEstaciones.class);
        Call<Datos> call  = apiEstaciones.obtenerDatos();

        call.enqueue(new Callback<Datos>() {
            @Override
            public void onResponse(Call<Datos> call, Response<Datos> response) {
                if(response.isSuccessful()) {
                    Datos d = response.body();
                    List<DatoHorario> listaEstaciones = d.getDatoHorario();



                    // for(int i = 0; i < listaEstaciones.size(); i++) {

                        da = listaEstaciones.get(0).getH01();
                        le = listaEstaciones.get(0).getH02();
                        mc = listaEstaciones.get(0).getH03();
                        dn = listaEstaciones.get(0).getH04();

                    // }


                } else {
                    Log.e("ERROR ON RESPONSE", "ERROR: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Datos> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                Log.e("ERROR ON FAILURE", "ERROR: " + t.getMessage());
            }
        });

        // TODO: Toast.makeText(this, "DA: " + da + ", LE: " + le + ", MC: " + mc + ", DN: " + dn, Toast.LENGTH_LONG).show();


       /* * * * * * * * * * * * * * * * ** * * * * * * * * * ** * * * * * * * * * **

        AnyChartView anyChartView = findViewById(R.id.acGrafica);

        CircularGauge circularGauge = AnyChart.circular();
        circularGauge.data(new SingleValueDataSet(new String[] { "7", "7", "7", "7", "100", "100"}));
        circularGauge.fill("#fff").stroke(null).padding(0d, 0d, 0d, 0d).margin(100d, 100d, 100d, 100d);
        circularGauge.startAngle(0d); // 0d
        circularGauge.sweepAngle(270d); // 270d

        Circular xAxis = circularGauge.axis(0).radius(100d).width(1d).fill((Fill) null);
        xAxis.scale().minimum(0d).maximum(100d);
        xAxis.ticks("{ interval: 5 }").minorTicks("{ interval: 5 }");
        xAxis.labels().enabled(false);
        xAxis.ticks().enabled(true);
        xAxis.minorTicks().enabled(true);





        circularGauge.label(0d).text("Dioxido de Azufre").useHtml(true).hAlign(String.valueOf(HAlign.CENTER)).vAlign(String.valueOf(HAlign.CENTER));
        circularGauge.label(0d).anchor(Anchor.RIGHT_CENTER).padding(0d, 10d, 0d, 0d).height(17d / 2d + "%").offsetY(100d + "%").offsetX(0d);

        Bar bar0 = circularGauge.bar(0d);
        bar0.dataIndex(0d);
        bar0.radius(100d);
        bar0.width(17d);
        bar0.fill(new SolidFill("#64b5f6", 1d));
        bar0.stroke(null);
        bar0.zIndex(5d);
        Bar bar100 = circularGauge.bar(100d);
        bar100.dataIndex(5d);
        bar100.radius(100d);
        bar100.width(17d);
        bar100.fill(new SolidFill("#F5F4F4", 1d));
        bar100.stroke("1 #e5e4e4");
        bar100.zIndex(4d);

        circularGauge.label(1d).text("Monóxido de Carbono").useHtml(true).hAlign(String.valueOf(HAlign.CENTER)).vAlign(String.valueOf(HAlign.CENTER));
        circularGauge.label(1d).anchor(Anchor.RIGHT_CENTER).padding(0d, 10d, 0d, 0d).height(17d / 2d + "%").offsetY(80d + "%").offsetX(0d);
        Bar bar1 = circularGauge.bar(1d);
        bar1.dataIndex(1d);
        bar1.radius(80d);
        bar1.width(17d);
        bar1.fill(new SolidFill("#1976d2", 1d));
        bar1.stroke(null);
        bar1.zIndex(5d);
        Bar bar101 = circularGauge.bar(101d);
        bar101.dataIndex(5d);
        bar101.radius(80d);
        bar101.width(17d);
        bar101.fill(new SolidFill("#F5F4F4", 1d));
        bar101.stroke("1 #e5e4e4");
        bar101.zIndex(4d);

        circularGauge.label(2d).text("Dioxido de Nitrogeno").useHtml(true).hAlign(String.valueOf(HAlign.CENTER)).vAlign(String.valueOf(HAlign.CENTER));
        circularGauge.label(2d).anchor(Anchor.RIGHT_CENTER).padding(0d, 10d, 0d, 0d).height(17d / 2d + "%").offsetY(60d + "%").offsetX(0d);
        Bar bar2 = circularGauge.bar(2d);
        bar2.dataIndex(2d);
        bar2.radius(60d);
        bar2.width(17d);
        bar2.fill(new SolidFill("#ef6c00", 1d));
        bar2.stroke(null);
        bar2.zIndex(5d);
        Bar bar102 = circularGauge.bar(102d);
        bar102.dataIndex(5d);
        bar102.radius(60d);
        bar102.width(17d);
        bar102.fill(new SolidFill("#F5F4F4", 1d));
        bar102.stroke("1 #e5e4e4");
        bar102.zIndex(4d);

        circularGauge.label(3d).text("Dióxido de Carbono").useHtml(true).hAlign(String.valueOf(HAlign.CENTER)).vAlign(String.valueOf(HAlign.CENTER));
        circularGauge.label(3d).anchor(Anchor.RIGHT_CENTER).padding(0d, 10d, 0d, 0d).height(17d / 2d + "%").offsetY(40d + "%").offsetX(0d);
        Bar bar3 = circularGauge.bar(3d);
        bar3.dataIndex(3d);
        bar3.radius(40d);
        bar3.width(17d);
        bar3.fill(new SolidFill("#ffd54f", 1d));
        bar3.stroke(null);
        bar3.zIndex(5d);
        Bar bar103 = circularGauge.bar(103d);
        bar103.dataIndex(5d);
        bar103.radius(40d);
        bar103.width(17d);
        bar103.fill(new SolidFill("#F5F4F4", 1d));
        bar103.stroke("1 #e5e4e4");
        bar103.zIndex(4d);


        circularGauge.margin(50d, 50d, 50d, 50d);
        circularGauge.title().text("MEDIDOR DE CONTAMINACIÓN' +\n" + "    '<br/><span style=\"color:#929292; font-size: 12px;\">(ESTACIONES DE LA COMUNIDAD DE MADRID").useHtml(true);
        circularGauge.title().enabled(true);
        circularGauge.title().hAlign(String.valueOf(HAlign.CENTER));
        circularGauge.title().padding(0d, 0d, 0d, 0d).margin(0d, 0d, 20d, 0d);

        anyChartView.setChart(circularGauge);

        * * * * * * * * * * * * * * * ** * * * * * * * * * ** * * * * * * * * * **/




       // EJEMPLO OFICIAL DE LA PAGINA
        AnyChartView anyChartView = findViewById(R.id.acGrafica);

        Pie pie = AnyChart.pie();

        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(FirstActivity.this, event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });

        /*int dioxidoAzufre = Integer.parseInt(da);*/
        /*int monoxidoNitrogeno = Integer.parseInt(le);*/
        /*int monoxidoCarbono = Integer.parseInt(mc);*/
        /*int dioxidoNitrogeno = Integer.parseInt(dn);*/

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Dioxido de Azufre", 10/*dioxidoAzufre*/));
        data.add(new ValueDataEntry("Monoxido de Nitrogeno", 20/*monoxidoNitrogeno*/));
        data.add(new ValueDataEntry("Monoxido de Carbono", 12/*monoxidoCarbono*/));
        data.add(new ValueDataEntry("Dioxido de Nitrogeno", 18/*dioxidoNitrogeno*/));

        pie.data(data);

        pie.title("Estacion mas cerca" + estacionCerca.getNombre()).margin(50d, 50d, 50d, 50d);

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title().text("Leyenda").padding(0d, 0d, 10d, 0d);

        pie.legend().position("center-bottom").itemsLayout(LegendLayout.HORIZONTAL).align(Align.CENTER);
        anyChartView.setChart(pie);










        /*
        pie1 = AnyChart.pie();
        // pie2 = CircularGauge.instantiate()

        List<DataEntry> data = new ArrayList<>();

        data.add(new ValueDataEntry("Dioxido de Azufre", Integer.parseInt(da)));
        data.add(new ValueDataEntry("Monoxido de Carbono", Integer.parseInt(mc)));
        data.add(new ValueDataEntry("Dioxido de Nitrogeno", Integer.parseInt(le)));
        data.add(new ValueDataEntry("Dioxido de Nitrogeno", Integer.parseInt(dn)));

        pie1.data(data);
        graficaTarta = (AnyChartView) findViewById(R.id.acGrafica);
        graficaTarta.setChart(pie1);
        */


/*        Retrofit retrofit = RetrofitClient.getClient(APIEstaciones.BASE_URL);
        APIEstaciones apiEstaciones = retrofit.create(APIEstaciones.class);
        Call<Datos> call  = apiEstaciones.obtenerDatos();

        String da;
        String mc;
        String no;

        call.enqueue(new Callback<Datos>() {
            @Override
            public void onResponse(Call<Datos> call, Response<Datos> response) {
                if(response.isSuccessful()) {
                    Datos d = response.body();
                    List<DatoHorario> listaEstaciones = d.getDatoHorario();
                    List<DataEntry> data = new ArrayList<>();

                    for(int i = 0; i < listaEstaciones.size(); i++) {

                        listaEstaciones.get(i).getEstacion();
                        listaEstaciones.get(i).getH02();
                        listaEstaciones.get(i).getH03();
                    }

                    pie1.data(data);
                    graficaTarta.setChart(pie1);


                } else {
                    Log.e("ERROR ON RESPONSE", "ERROR: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Datos> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                Log.e("ERROR ON FAILURE", "ERROR: " + t.getMessage());
            }
        });*/

        /*graficaTarta = (AnyChartView) findViewById(R.id.acGrafica);
        graficaTarta.setChart(pie1);*/



    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * ** * */

    // lat = x \\
    // long = y   \\
    public Estacion encontrarEstMasCerca(ArrayList<Estacion> lista, double posiX, double posiY) {
        Estacion resMenosdistante = null;

        double distancia;
        double distanciaMenor = 9999999.99;

        for (Estacion estacion : lista) {
            distancia = Math.sqrt(Math.pow(posiX - estacion.getLatitud(), 2) + Math.pow(posiY - estacion.getLongitud(), 2));
            if (distancia < distanciaMenor) {
                distanciaMenor = distancia;
                resMenosdistante = estacion;
            }
        }
        return resMenosdistante;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * ** * */

    public void accederNoticias(View view) {
        startActivity(new Intent(this, NewsActivity.class));
    }

    public void accesoMapa(View view) {
        startActivity(new Intent(this, MapActivity.class));
        //overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    public void accederChat(View view) {
        startActivity(new Intent(this, ChatActivity.class));
        //overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    public void desconectar() {
        fbAuth.signOut();
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        i.putExtra(CLAVE_EMAIL, email);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    public void createSimpleDialog(View v) {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Infollution")
                .setMessage("¿Desea salir de la aplicación?")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                desconectar();
                            }
                        })
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               dialog.dismiss();
                            }
                        });

        dialog = builder.create();
        dialog.show();
    }


}
