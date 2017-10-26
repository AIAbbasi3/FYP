package com.example.arham.fyp;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.PatternSyntaxException;

public class DisplayGraph extends AppCompatActivity {

    private static int batchSize = 128;
    private static int exampleLength = 44;
    private static double splitRatio = 0.75;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_graph);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        String shareSymbol = null;

        if(b!=null)
        {
            String j =(String) b.get("name");
            shareSymbol = j;

        }
        /**try {
            downloadLatestData("/storage/emulated/0/"+shareSymbol+".csv", shareSymbol);
        } catch (ParseException e) {
            e.printStackTrace();
        }**/

        makePrediction("/storage/emulated/0/"+shareSymbol+".csv",shareSymbol);
    }

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
    
    public void makePrediction(String filename,String shareSymbol) {

        MultiLayerNetwork loadedNetwork = null;
        File savedLocation = new File("/storage/emulated/0/NNModels/"+shareSymbol+"lstm.zip");
        try {
            loadedNetwork = ModelSerializer.restoreMultiLayerNetwork(savedLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Iterator iterator = new Iterator(filename, shareSymbol, batchSize, exampleLength, splitRatio);
        List<Pair<INDArray, Double>> test = iterator.getTestDataSet();


        double max = iterator.getMaxNum()[1];
        double min = iterator.getMinNum()[1];
        double[] predicts = new double[test.size()+1];
        double[] actuals = new double[test.size()];
        predicts[0] = 0;
        for (int i = 0; i < test.size(); i++) {

            predicts[i+1] = loadedNetwork.rnnTimeStep(test.get(i).getFirst()).getDouble(exampleLength - 1) * (max - min) + min;
            actuals[i] = test.get(i).getSecond();
        }

        final GraphView graph = (GraphView) findViewById(R.id.graph);

        int z;
        int y;
        if (test.size() < 128) {
            z = test.size();
            y = z + 1;
        } else {
            z = 128;
            y = z + 1;
        }

        DataPoint[] points = new DataPoint[z];
        for (int j = 0; j < points.length; j++) {
            points[j] = new DataPoint(j+1, predicts[j+1]);
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);
        DataPoint[] points2 = new DataPoint[z];
        for (int k = 0; k < points2.length; k++) {
            points2[k] = new DataPoint(k, actuals[k]);
        }
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(points2);
        series.setTitle("prediction");
        series2.setTitle("actual");


        TextView c0 = (TextView) findViewById(R.id.c0);
        TextView c1 = (TextView) findViewById(R.id.c1);

        TextView n0 = (TextView) findViewById(R.id.n0);
        TextView n1 = (TextView) findViewById(R.id.n1);

        c0.setBackgroundColor(Color.BLUE);
        c1.setBackgroundColor(Color.RED);

        n0.setText(" "+shareSymbol+" pred");
        n1.setText(" "+shareSymbol+" act");

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(20);
        graph.getViewport().setMaxX(y+2);

        graph.getGridLabelRenderer().setVerticalAxisTitle("Share Price");
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Each number represents a date");

        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);

        series.setColor(Color.BLUE);
        graph.addSeries(series);

        series2.setColor(Color.RED);
        graph.addSeries(series2);

    }

}
