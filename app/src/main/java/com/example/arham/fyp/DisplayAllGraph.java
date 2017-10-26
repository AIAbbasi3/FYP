package com.example.arham.fyp;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.opencsv.CSVWriter;

import org.deeplearning4j.berkeley.Pair;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.PatternSyntaxException;

public class DisplayAllGraph extends AppCompatActivity {

    private static int batchSize = 128;
    private static int exampleLength = 44;
    private static double splitRatio = 0.75;

    ArrayList<LineGraphSeries<DataPoint>> allSeries = new ArrayList<LineGraphSeries<DataPoint>>();

    String decision;

    /**DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
     String[] strDates = new String[795];
     Date[] dates = new Date[795];
     Double[] closingPrices = new Double[795];**/
    //Market1 m = new Market1();
    //String[] selectedShares = m.selectedShares;



    //TextView  t = (TextView) findViewById(R.id.textView3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all_graph);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        String shareSymbol = null;

        TextView n0 = (TextView) findViewById(R.id.n0);
        TextView n1 = (TextView) findViewById(R.id.n1);
        TextView n2 = (TextView) findViewById(R.id.n2);
        TextView n3 = (TextView) findViewById(R.id.n3);
        TextView n4 = (TextView) findViewById(R.id.n4);
        TextView n5 = (TextView) findViewById(R.id.n5);
        TextView n6 = (TextView) findViewById(R.id.n6);
        TextView n7 = (TextView) findViewById(R.id.n7);
        TextView n8 = (TextView) findViewById(R.id.n8);
        TextView n9 = (TextView) findViewById(R.id.n9);
        TextView n10 = (TextView) findViewById(R.id.n10);
        TextView n11 = (TextView) findViewById(R.id.n11);
        TextView n12 = (TextView) findViewById(R.id.n12);
        TextView n13 = (TextView) findViewById(R.id.n13);
        TextView n14 = (TextView) findViewById(R.id.n14);
        TextView n15 = (TextView) findViewById(R.id.n15);
        TextView n16 = (TextView) findViewById(R.id.n16);
        TextView n17 = (TextView) findViewById(R.id.n17);
        TextView n18 = (TextView) findViewById(R.id.n18);
        TextView n19 = (TextView) findViewById(R.id.n19);
        TextView n20 = (TextView) findViewById(R.id.n20);
        TextView n21 = (TextView) findViewById(R.id.n21);
        TextView n22 = (TextView) findViewById(R.id.n22);
        TextView n23 = (TextView) findViewById(R.id.n23);

        n0.setVisibility(View.INVISIBLE);
        n1.setVisibility(View.INVISIBLE);
        n2.setVisibility(View.INVISIBLE);
        n3.setVisibility(View.INVISIBLE);
        n4.setVisibility(View.INVISIBLE);
        n5.setVisibility(View.INVISIBLE);
        n6.setVisibility(View.INVISIBLE);
        n7.setVisibility(View.INVISIBLE);
        n8.setVisibility(View.INVISIBLE);
        n9.setVisibility(View.INVISIBLE);
        n10.setVisibility(View.INVISIBLE);
        n11.setVisibility(View.INVISIBLE);
        n12.setVisibility(View.INVISIBLE);
        n13.setVisibility(View.INVISIBLE);
        n14.setVisibility(View.INVISIBLE);
        n15.setVisibility(View.INVISIBLE);
        n16.setVisibility(View.INVISIBLE);
        n17.setVisibility(View.INVISIBLE);
        n18.setVisibility(View.INVISIBLE);
        n19.setVisibility(View.INVISIBLE);
        n20.setVisibility(View.INVISIBLE);
        n21.setVisibility(View.INVISIBLE);
        n22.setVisibility(View.INVISIBLE);
        n23.setVisibility(View.INVISIBLE);


        if(b!=null)
        {
            int m = (int) b.get("m");

            int x = 0;
            if (x < m) {
                //String decision1;
                shareSymbol = (String) b.get("s1");
                makePrediction("/storage/emulated/0/" + shareSymbol + ".csv", shareSymbol);
                n0.setText(" "+shareSymbol+" pred");
                n1.setText(" "+shareSymbol+" act");
                n0.setVisibility(View.VISIBLE);
                n1.setVisibility(View.VISIBLE);
            }
            x += 1;
            if (x < m) {
                //String decision2;
                shareSymbol = (String) b.get("s2");
                makePrediction("/storage/emulated/0/"+shareSymbol+".csv",shareSymbol);
                //Log.d("testing", "dec: " + decision2);
                n2.setText(" "+shareSymbol+" pred");
                n3.setText(" "+shareSymbol+" act");
                n2.setVisibility(View.VISIBLE);
                n3.setVisibility(View.VISIBLE);
            }
            x += 1;
            if (x < m) {
                shareSymbol = (String) b.get("s3");
                makePrediction("/storage/emulated/0/"+shareSymbol+".csv",shareSymbol);
                n4.setText(" "+shareSymbol+" pred");
                n5.setText(" "+shareSymbol+" act");
                n4.setVisibility(View.VISIBLE);
                n5.setVisibility(View.VISIBLE);
            }
            x += 1;
            if (x < m) {
                shareSymbol = (String) b.get("s4");
                makePrediction("/storage/emulated/0/"+shareSymbol+".csv",shareSymbol);
                n6.setText(" "+shareSymbol+" pred");
                n7.setText(" "+shareSymbol+" act");
                n6.setVisibility(View.VISIBLE);
                n7.setVisibility(View.VISIBLE);
            }
            x += 1;
            if (x < m) {
                shareSymbol = (String) b.get("s5");
                makePrediction("/storage/emulated/0/"+shareSymbol+".csv",shareSymbol);
                n8.setText(" "+shareSymbol+" pred");
                n9.setText(" "+shareSymbol+" act");
                n8.setVisibility(View.VISIBLE);
                n9.setVisibility(View.VISIBLE);
            }
            x += 1;
            if (x < m) {
                shareSymbol = (String) b.get("s6");
                makePrediction("/storage/emulated/0/"+shareSymbol+".csv",shareSymbol);
                n10.setText(" "+shareSymbol+" pred");
                n11.setText(" "+shareSymbol+" act");
                n10.setVisibility(View.VISIBLE);
                n11.setVisibility(View.VISIBLE);
            }
            x += 1;
            if (x < m) {
                shareSymbol = (String) b.get("s7");
                makePrediction("/storage/emulated/0/"+shareSymbol+".csv",shareSymbol);
                n12.setText(" "+shareSymbol+" pred");
                n13.setText(" "+shareSymbol+" act");
                n12.setVisibility(View.VISIBLE);
                n13.setVisibility(View.VISIBLE);
            }
            x += 1;
            if (x < m) {
                shareSymbol = (String) b.get("s8");
                makePrediction("/storage/emulated/0/"+shareSymbol+".csv",shareSymbol);
                n14.setText(" "+shareSymbol+" pred");
                n15.setText(" "+shareSymbol+" act");
                n14.setVisibility(View.VISIBLE);
                n15.setVisibility(View.VISIBLE);
            }
            x += 1;
            if (x < m) {
                shareSymbol = (String) b.get("s9");
                makePrediction("/storage/emulated/0/"+shareSymbol+".csv",shareSymbol);
                n16.setText(" "+shareSymbol+" pred");
                n17.setText(" "+shareSymbol+" act");
                n16.setVisibility(View.VISIBLE);
                n17.setVisibility(View.VISIBLE);
            }
            x += 1;
            if (x < m) {
                shareSymbol = (String) b.get("s10");
                makePrediction("/storage/emulated/0/"+shareSymbol+".csv",shareSymbol);
                n18.setText(" "+shareSymbol+" pred");
                n19.setText(" "+shareSymbol+" act");
                n18.setVisibility(View.VISIBLE);
                n19.setVisibility(View.VISIBLE);
            }
            x += 1;
            if (x < m) {
                shareSymbol = (String) b.get("s11");
                makePrediction("/storage/emulated/0/"+shareSymbol+".csv",shareSymbol);
                n20.setText(" "+shareSymbol+" pred");
                n21.setText(" "+shareSymbol+" act");
                n20.setVisibility(View.VISIBLE);
                n21.setVisibility(View.VISIBLE);
            }
            x += 1;
            if (x < m) {
                shareSymbol = (String) b.get("s12");
                makePrediction("/storage/emulated/0/"+shareSymbol+".csv",shareSymbol);
                n22.setText(" "+shareSymbol+" pred");
                n23.setText(" "+shareSymbol+" act");
                n22.setVisibility(View.VISIBLE);
                n23.setVisibility(View.VISIBLE);
            }

            plotGraph();

        }
        //Log.d("testing", shareSymbol);

        //Log.d("testing", "chal shuru");
        /**try {
         downloadLatestData("/storage/emulated/0/"+shareSymbol+".csv", shareSymbol);
         registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
         } catch (ParseException e) {
         e.printStackTrace();
         }**/
        //Log.d("testing", "downloads done");

        //loadData();
        //showGraph();
    }

    BroadcastReceiver onComplete=new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            // your code
        }
    };

    public void downloadLatestData(String filename, String shareSymbol) throws ParseException {

        InputStream is = null;
        try {
            is = new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr, 8192);

        String line = null;
        String lastLine = "";

        try {
            while ((line = br.readLine()) != null)
            {
                lastLine = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        lastLine = lastLine.replace("\"","");
        String[] split = lastLine.split(",");
        String lastRecordedDateStr = split[1];

        Calendar c = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c2.add(Calendar.DATE,-1);

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String yestDateStr = String.valueOf(df.format(c2.getTime()));

        String curDateStr = String.valueOf(df.format(c.getTime()));

        Date downloadDate;
        String downloadDateStr = null;

        Date lastRecordedDate = df.parse(lastRecordedDateStr);
        Calendar c3 = Calendar.getInstance();
        c3.setTime(df.parse(lastRecordedDateStr));
        c3.add(Calendar.DATE, 1);
        downloadDateStr = df.format(c3.getTime());
        Date yestDate = df.parse(curDateStr);
        //Log.d("testing", "last: " + lastRecordedDateStr + " yest: " + yestDateStr + " cur: " + curDateStr);

        int abc = 0;
        if (lastRecordedDateStr != yestDateStr) {

            String day = null;
            String month = null;
            String year = null;

            boolean done = false;
            while (done == false) {
                year = downloadDateStr.substring(0, 4);
                month = downloadDateStr.substring(4, 6);
                day = downloadDateStr.substring(6, 8);
                Log.d("testing", "download: " + downloadDateStr);
                String url = "https://www.netfonds.no/quotes/exchange.php?exchange=N&at_day="+day+"&at_month="+month+"&at_year="+year+"&format=csv";
                //Log.d("testing", "Url hogayi");
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.setDescription("Market data");
                request.setTitle(year+month+day);
                Log.d("testing", "set hogaya");
                // in order for this if to run, you must use the android 3.2 to compile your app
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                }
                Log.d("testing", "yahan");
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, year+month+day+".csv");
                Log.d("testing", "directory hogayi");
                // get download service and enqueue file
                DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Log.d("testing", "service");
                manager.enqueue(request);

                if (downloadDateStr.equals(yestDateStr)) {
                    done = true;
                } else {
                    c3.setTime(df.parse(downloadDateStr));
                    c3.add(Calendar.DATE, 1);
                    downloadDateStr = df.format(c3.getTime());
                }
                abc += 1;
                if (abc == 20) {
                    break;
                }
            }
        }
    }

    public void makePrediction(String filename, String shareSymbol) {

        MultiLayerNetwork loadedNetwork = null;
        Log.d("testing", "loading data");
        File savedLocation = new File("/storage/emulated/0/NNModels/"+shareSymbol+"lstm.zip");
        try {
            Log.d("testing", "starting to load");
            loadedNetwork = ModelSerializer.restoreMultiLayerNetwork(savedLocation);
            Log.d("testing", "loaded");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("testing", "done");

        Iterator iterator = new Iterator(filename, shareSymbol, batchSize, exampleLength, splitRatio);
        List<Pair<INDArray, Double>> test = iterator.getTestDataSet();


        double max = iterator.getMaxNum()[1];
        double min = iterator.getMinNum()[1];
        double[] predicts = new double[test.size()+1];
        double[] actuals = new double[test.size()];
        Log.d("testing", "about to enter");
        predicts[0] = 0;
        for (int i = 0; i < test.size(); i++) {

            predicts[i+1] = loadedNetwork.rnnTimeStep(test.get(i).getFirst()).getDouble(exampleLength - 1) * (max - min) + min;
            actuals[i] = test.get(i).getSecond();
        }

        Log.d("testing", "Plotting graph");


        int z;
        if (test.size() < 128) {
            z = test.size();
        } else {
            z = 128;
        }

        DataPoint[] points = new DataPoint[z];
        Log.d("testing", "Adding points");
        for (int j = 0; j < points.length; j++) {
            points[j] = new DataPoint(j+1, predicts[j+1]);
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);
        DataPoint[] points2 = new DataPoint[z];
        for (int k = 0; k < points2.length; k++) {
            points2[k] = new DataPoint(k, actuals[k]);
        }
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(points2);

        series.setTitle(shareSymbol+" pred");
        series2.setTitle(shareSymbol+" act");

        allSeries.add(series);
        allSeries.add(series2);

        if (predicts[predicts.length-1] > predicts[predicts.length-2]) {
            decision = "Increase";
        } else if (predicts[predicts.length-1] < predicts[predicts.length-2]) {
            decision = "Decrease";
        } else {
            decision = "Same";
        }
        //return decision;

    }

    public void plotGraph() {

        TextView c0 = (TextView) findViewById(R.id.c0);
        TextView c1 = (TextView) findViewById(R.id.c1);
        TextView c2 = (TextView) findViewById(R.id.c2);
        TextView c3 = (TextView) findViewById(R.id.c3);
        TextView c4 = (TextView) findViewById(R.id.c4);
        TextView c5 = (TextView) findViewById(R.id.c5);
        TextView c6 = (TextView) findViewById(R.id.c6);
        TextView c7 = (TextView) findViewById(R.id.c7);
        TextView c8 = (TextView) findViewById(R.id.c8);
        TextView c9 = (TextView) findViewById(R.id.c9);
        TextView c10 = (TextView) findViewById(R.id.c10);
        TextView c11 = (TextView) findViewById(R.id.c11);
        TextView c12 = (TextView) findViewById(R.id.c12);
        TextView c13 = (TextView) findViewById(R.id.c13);
        TextView c14 = (TextView) findViewById(R.id.c14);
        TextView c15 = (TextView) findViewById(R.id.c15);
        TextView c16 = (TextView) findViewById(R.id.c16);
        TextView c17 = (TextView) findViewById(R.id.c17);
        TextView c18 = (TextView) findViewById(R.id.c18);
        TextView c19 = (TextView) findViewById(R.id.c19);
        TextView c20 = (TextView) findViewById(R.id.c20);
        TextView c21 = (TextView) findViewById(R.id.c21);
        TextView c22 = (TextView) findViewById(R.id.c22);
        TextView c23 = (TextView) findViewById(R.id.c23);

        c0.setVisibility(View.INVISIBLE);
        c1.setVisibility(View.INVISIBLE);
        c2.setVisibility(View.INVISIBLE);
        c3.setVisibility(View.INVISIBLE);
        c4.setVisibility(View.INVISIBLE);
        c5.setVisibility(View.INVISIBLE);
        c6.setVisibility(View.INVISIBLE);
        c7.setVisibility(View.INVISIBLE);
        c8.setVisibility(View.INVISIBLE);
        c9.setVisibility(View.INVISIBLE);
        c10.setVisibility(View.INVISIBLE);
        c11.setVisibility(View.INVISIBLE);
        c12.setVisibility(View.INVISIBLE);
        c13.setVisibility(View.INVISIBLE);
        c14.setVisibility(View.INVISIBLE);
        c15.setVisibility(View.INVISIBLE);
        c16.setVisibility(View.INVISIBLE);
        c17.setVisibility(View.INVISIBLE);
        c18.setVisibility(View.INVISIBLE);
        c19.setVisibility(View.INVISIBLE);
        c20.setVisibility(View.INVISIBLE);
        c21.setVisibility(View.INVISIBLE);
        c22.setVisibility(View.INVISIBLE);
        c23.setVisibility(View.INVISIBLE);

        final GraphView graph = (GraphView) findViewById(R.id.graph);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(20);
        graph.getViewport().setMaxX(130);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScrollableY(true);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);

        Context context = super.getApplicationContext();
        graph.getGridLabelRenderer().setVerticalAxisTitle("Share Price");
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Each number represents a date");

        for (int j=0; j<allSeries.size(); j++) {
            if (!allSeries.get(j).isEmpty()) {
                LineGraphSeries<DataPoint> series = allSeries.get(j);

                switch (j) {
                    case 0:
                        series.setColor(Color.rgb(209,54,54));
                        c0.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        series.setColor(Color.rgb(146,209,54));
                        c1.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        series.setColor(Color.rgb(255,205,29));
                        c2.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        series.setColor(Color.rgb(29,255,221));
                        c3.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        series.setColor(Color.rgb(255,29,229));
                        c4.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        series.setColor(Color.rgb(29,59,255));
                        c5.setVisibility(View.VISIBLE);
                        break;
                    case 6:
                        series.setColor(Color.rgb(171,59,255));
                        c6.setVisibility(View.VISIBLE);
                        break;
                    case 7:
                        series.setColor(Color.rgb(255,255,157));
                        c7.setVisibility(View.VISIBLE);
                        break;
                    case 8:
                        series.setColor(Color.rgb(60,24,105));
                        c8.setVisibility(View.VISIBLE);
                        break;
                    case 9:
                        series.setColor(Color.rgb(255,255,0));
                        c9.setVisibility(View.VISIBLE);
                        break;
                    case 10:
                        series.setColor(Color.rgb(71,135,122));
                        c10.setVisibility(View.VISIBLE);
                        break;
                    case 11:
                        series.setColor(Color.rgb(102,51,0));
                        c11.setVisibility(View.VISIBLE);
                        break;
                    case 12:
                        series.setColor(Color.rgb(135,71,78));
                        c12.setVisibility(View.VISIBLE);
                        break;
                    case 13:
                        series.setColor(Color.rgb(43,110,37));
                        c13.setVisibility(View.VISIBLE);
                        break;
                    case 14:
                        series.setColor(Color.rgb(255,205,219));
                        c14.setVisibility(View.VISIBLE);
                        break;
                    case 15:
                        series.setColor(Color.rgb(21,2,100));
                        c15.setVisibility(View.VISIBLE);
                        break;
                    case 16:
                        series.setColor(Color.rgb(0,0,0));
                        c16.setVisibility(View.VISIBLE);
                        break;
                    case 17:
                        series.setColor(Color.rgb(130,130,130));
                        c17.setVisibility(View.VISIBLE);
                        break;
                    case 18:
                        series.setColor(Color.rgb(153,76,0));
                        c18.setVisibility(View.VISIBLE);
                        break;
                    case 19:
                        series.setColor(Color.rgb(31,153,255));
                        c19.setVisibility(View.VISIBLE);
                        break;
                    case 20:
                        series.setColor(Color.rgb(153,0,0));
                        c20.setVisibility(View.VISIBLE);
                        break;
                    case 21:
                        series.setColor(Color.rgb(255,255,153));
                        c21.setVisibility(View.VISIBLE);
                        break;
                    case 22:
                        series.setColor(Color.rgb(0,102,102));
                        c22.setVisibility(View.VISIBLE);
                        break;
                    case 23:
                        series.setColor(Color.rgb(255,102,102));
                        c23.setVisibility(View.VISIBLE);
                        break;
                }

                graph.addSeries(series);
            }
        }

        c0.setBackgroundColor(Color.rgb(209,54,54));
        c1.setBackgroundColor(Color.rgb(146,209,54));
        c2.setBackgroundColor(Color.rgb(255,205,29));
        c3.setBackgroundColor(Color.rgb(29,255,221));
        c4.setBackgroundColor(Color.rgb(255,29,229));
        c5.setBackgroundColor(Color.rgb(29,59,255));
        c6.setBackgroundColor(Color.rgb(171,59,255));
        c7.setBackgroundColor(Color.rgb(255,255,157));
        c8.setBackgroundColor(Color.rgb(60,24,105));
        c9.setBackgroundColor(Color.rgb(255,255,0));
        c10.setBackgroundColor(Color.rgb(71,135,122));
        c11.setBackgroundColor(Color.rgb(102,51,0));
        c12.setBackgroundColor(Color.rgb(135,71,78));
        c13.setBackgroundColor(Color.rgb(43,110,37));
        c14.setBackgroundColor(Color.rgb(255,205,219));
        c15.setBackgroundColor(Color.rgb(21,2,100));
        c16.setBackgroundColor(Color.rgb(0,0,0));
        c17.setBackgroundColor(Color.rgb(130,130,130));
        c18.setBackgroundColor(Color.rgb(153,76,0));
        c19.setBackgroundColor(Color.rgb(31,153,255));
        c20.setBackgroundColor(Color.rgb(153,0,0));
        c21.setBackgroundColor(Color.rgb(255,255,153));
        c22.setBackgroundColor(Color.rgb(0,102,102));
        c23.setBackgroundColor(Color.rgb(255,102,102));
    }

    private Date[] getDates(String filename) {

        InputStream is = null;
        try {
            is = new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr, 8192);

        String line = null;
        try {
            line = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Log.d("testing",line);
        line = line.replace("\"","");
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        Date[] dates = new Date[1500];
        Log.d("testing", "adding dates");
        int n=0;
        while (line != null) {
            String[] split = line.split(",");
            Log.d("testing", "the date: " + split[1]);
            try {
                dates[n] = df.parse(split[1]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Log.d("testing", "date added");
            try {
                line = br.readLine();
                Log.d("testing", "next line read");
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("testing", "about to replace");
            if (line != null) {
                line = line.replace("\"", "");
                Log.d("testing", "replaced");
            }
            n += 1;
        }
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("testing", "actual dates done");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(df.parse(String.valueOf(dates[n-1])));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 1);
        try {
            dates[n] = df.parse(String.valueOf(c.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dates;
    }

}
