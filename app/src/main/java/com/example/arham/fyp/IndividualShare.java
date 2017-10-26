package com.example.arham.fyp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.deeplearning4j.berkeley.Pair;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.regex.PatternSyntaxException;

public class IndividualShare extends AppCompatActivity {

    private String[] shareList = new String[1639];

    private static int batchSize = 128;
    private static int exampleLength = 44;
    private static double splitRatio = 0.75;

    Button predictBtn;
    String share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_share);
        Log.d("testing", "started");
        Context ctx = super.getBaseContext();
        readRaw(ctx);
        readRaw2(ctx);
        Log.d("testing", "read the raw");
        ddmenu();
    }

    public void ddmenu() {
        Spinner dropdown = (Spinner) findViewById(R.id.dropdown2);

        //String[] items = new String[] {"Chai Latte", "Green Tea", "Black Tea"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, shareList);

        dropdown.setAdapter(adapter);
        predictBtn = (Button) findViewById(R.id.predictBtn);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("shareList", (String) parent.getItemAtPosition(position));

                share = (String) parent.getItemAtPosition(position);
                String[] splitShare = share.split(" - ");
                share = splitShare[0];


                //predict(share);
                predictBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        predict(share);
                    }

                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void readRaw(Context ctx) {

        InputStream is = ctx.getResources().openRawResource(R.raw.nyse);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr, 8192); // 2nd arg is buffer
        // size

        //TextView t = (TextView) findViewById(R.id.textView2);
        // More efficient (less readable) implementation of above is the
        // composite expression
    /*
     * BufferedReader br = new BufferedReader(new InputStreamReader(
     * this.getResources().openRawResource(R.raw.textfile)), 8192);
     */

        try {
            String line;
            String[] splitLine = new String[0];
            int x = 0;

            while (true) {
                line = br.readLine();
                // readLine() returns null if no more lines in the file

                try {
                    splitLine = line.split("\\;");
                } catch (PatternSyntaxException ex) {
                    //
                }

                shareList[x] = splitLine[0];

                //t.setText(splitLine[3]);
                if (x==1638)
                    break;
                x += 1;
            }
            isr.close();
            is.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void readRaw2(Context ctx) {
        InputStream is = ctx.getResources().openRawResource(R.raw.companylist1);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr, 8192); // 2nd arg is buffer
        // size

        //TextView t = (TextView) findViewById(R.id.textView2);
        // More efficient (less readable) implementation of above is the
        // composite expression
    /*
     * BufferedReader br = new BufferedReader(new InputStreamReader(
     * this.getResources().openRawResource(R.raw.textfile)), 8192);
     */

        try {
            String line;
            String[] splitLine = new String[0];
            int y =0;

            while (true) {
                line = br.readLine();
                // readLine() returns null if no more lines in the file
                line = line.replace("\"","");
                try {
                    splitLine = line.split("\\,");
                } catch (PatternSyntaxException ex) {
                    //
                }

                //shareList[x] = splitLine[0];
                for (int x = 0; x<shareList.length;x++) {
                    if (shareList[x].equals(splitLine[0])) {
                        shareList[x] = splitLine[0] + " - " + splitLine[1];
                        break;
                    }
                    if (x == 1638) {
                        break;
                    }
                }
                if (y == 3136) {
                    break;
                }
                y += 1;
            }
            isr.close();
            is.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void predict(final String share) {



        String filename = "/storage/emulated/0/"+share+".csv";

        MultiLayerNetwork loadedNetwork = null;
        File savedLocation = new File("/storage/emulated/0/NNModels/"+share+"lstm.zip");
        try {
            loadedNetwork = ModelSerializer.restoreMultiLayerNetwork(savedLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Iterator iterator = new Iterator(filename, share, batchSize, exampleLength, splitRatio);
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
        TextView lastClosing = (TextView) findViewById(R.id.lc);
        lastClosing.setText(String.valueOf(actuals[actuals.length-1]));

        TextView todayPred = (TextView) findViewById(R.id.tp);
        if (predicts[predicts.length-1] > predicts[predicts.length-2]) {
            todayPred.setText("Increase");
        } else if (predicts[predicts.length-1] < predicts[predicts.length-2]) {
            todayPred.setText("Derease");
        } else {
            todayPred.setText("Same");
        }

    }
}
