package com.example.arham.fyp;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import static com.example.arham.fyp.R.id.p0;

public class Market1 extends AppCompatActivity {

    private String[] shareList = new String[1639];
    public String[] selectedShares = new String[12];

    public TextView t0;
    public TextView t1;
    public TextView t2;
    public TextView t3;
    public TextView t4;
    public TextView t5;
    public TextView t6;
    public TextView t7;
    public TextView t8;
    public TextView t9;
    public TextView t10;
    public TextView t11;

    public ImageButton d0;
    public ImageButton d1;
    public ImageButton d2;
    public ImageButton d3;
    public ImageButton d4;
    public ImageButton d5;
    public ImageButton d6;
    public ImageButton d7;
    public ImageButton d8;
    public ImageButton d9;
    public ImageButton d10;
    public ImageButton d11;

    public ImageButton p0;
    public ImageButton p1;
    public ImageButton p2;
    public ImageButton p3;
    public ImageButton p4;
    public ImageButton p5;
    public ImageButton p6;
    public ImageButton p7;
    public ImageButton p8;
    public ImageButton p9;
    public ImageButton p10;
    public ImageButton p11;

    public void readRaw(Context ctx) {

        InputStream is = ctx.getResources().openRawResource(R.raw.nyse);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr, 8192);

        try {
            String line;
            String[] splitLine = new String[0];
            int x = 0;

            while (true) {
                line = br.readLine();

                try {
                    splitLine = line.split("\\;");
                } catch (PatternSyntaxException ex) {
                    //
                }

                shareList[x] = splitLine[0];

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
        BufferedReader br = new BufferedReader(isr, 8192);

        try {
            String line;
            String[] splitLine = new String[0];
            int y =0;

            while (true) {
                line = br.readLine();
                line = line.replace("\"","");
                try {
                    splitLine = line.split("\\,");
                } catch (PatternSyntaxException ex) {
                    //
                }

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market1);

        Context ctx = super.getBaseContext();

        Log.d("testing","started");

        t0 = (TextView) findViewById(R.id.t0);
        t1 = (TextView) findViewById(R.id.t1);
        t2 = (TextView) findViewById(R.id.t2);
        t3 = (TextView) findViewById(R.id.t3);
        t4 = (TextView) findViewById(R.id.t4);
        t5 = (TextView) findViewById(R.id.t5);
        t6 = (TextView) findViewById(R.id.t6);
        t7 = (TextView) findViewById(R.id.t7);
        t8 = (TextView) findViewById(R.id.t8);
        t9 = (TextView) findViewById(R.id.t9);
        t10 = (TextView) findViewById(R.id.t10);
        t11 = (TextView) findViewById(R.id.t11);

        d0 = (ImageButton) findViewById(R.id.d0);
        d1 = (ImageButton) findViewById(R.id.d1);
        d2 = (ImageButton) findViewById(R.id.d2);
        d3 = (ImageButton) findViewById(R.id.d3);
        d4 = (ImageButton) findViewById(R.id.d4);
        d5 = (ImageButton) findViewById(R.id.d5);
        d6 = (ImageButton) findViewById(R.id.d6);
        d7 = (ImageButton) findViewById(R.id.d7);
        d8 = (ImageButton) findViewById(R.id.d8);
        d9 = (ImageButton) findViewById(R.id.d9);
        d10 = (ImageButton) findViewById(R.id.d10);
        d11 = (ImageButton) findViewById(R.id.d11);

        p0 = (ImageButton) findViewById(R.id.p0);
        p1 = (ImageButton) findViewById(R.id.p1);
        p2 = (ImageButton) findViewById(R.id.p2);
        p3 = (ImageButton) findViewById(R.id.p3);
        p4 = (ImageButton) findViewById(R.id.p4);
        p5 = (ImageButton) findViewById(R.id.p5);
        p6 = (ImageButton) findViewById(R.id.p6);
        p7 = (ImageButton) findViewById(R.id.p7);
        p8 = (ImageButton) findViewById(R.id.p8);
        p9 = (ImageButton) findViewById(R.id.p9);
        p10 = (ImageButton) findViewById(R.id.p10);
        p11 = (ImageButton) findViewById(R.id.p11);

        /**if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }**/
        /**CSVReader reader = null;

        try {
            reader = new CSVReader(new FileReader("currentshares.csv"), '\t');
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String[] nextLine;
        int index = 0;
        try {
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                selectedShares[index] = nextLine[index];
                modifyList(index,selectedShares[index], 12);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }**/

        readRaw(ctx);
        readRaw2(ctx);
        initializeBtns();
        ddmenu();
        displayGraph();
    }

    public void initializeBtns() {

        p0.setVisibility(View.GONE);
        p1.setVisibility(View.GONE);
        p2.setVisibility(View.GONE);
        p3.setVisibility(View.GONE);
        p4.setVisibility(View.GONE);
        p5.setVisibility(View.GONE);
        p6.setVisibility(View.GONE);
        p7.setVisibility(View.GONE);
        p8.setVisibility(View.GONE);
        p9.setVisibility(View.GONE);
        p10.setVisibility(View.GONE);
        p11.setVisibility(View.GONE);

        d0.setVisibility(View.GONE);
        d1.setVisibility(View.GONE);
        d2.setVisibility(View.GONE);
        d3.setVisibility(View.GONE);
        d4.setVisibility(View.GONE);
        d5.setVisibility(View.GONE);
        d6.setVisibility(View.GONE);
        d7.setVisibility(View.GONE);
        d8.setVisibility(View.GONE);
        d9.setVisibility(View.GONE);
        d10.setVisibility(View.GONE);
        d11.setVisibility(View.GONE);
    }

    public void ddmenu() {
        Spinner dropdown = (Spinner) findViewById(R.id.dropdown);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, shareList);

        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("shareList", (String) parent.getItemAtPosition(position));

                String share = (String) parent.getItemAtPosition(position);
                String[] splitShare = share.split(" - ");
                share = splitShare[0];
                addShare(share);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }


    public int getIndex() {
        int y = 0;
        while (y<12) {
            if (selectedShares[y] == null) {
                break;
            }
            y += 1;
        }

        return y;
    }

    public void addShare(final String share) {


        final Button addBtn = (Button) findViewById(R.id.addBtn);

        addBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!(Arrays.asList(selectedShares).contains(share))) {
                    int i = getIndex();
                    selectedShares[i] = share;

                    modifyList(i,share, 12);

                    if (selectionFull()) {
                        addBtn.setEnabled(false);
                    }

                } else {
                    alreadySelectedDialog();
                }

            }

        });

        d0.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedShares[0] = null;
                modifyList(12, null, 0);
                addBtn.setEnabled(true);
            }
        });

        d1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedShares[1] = null;
                modifyList(12, null, 1);
                addBtn.setEnabled(true);
            }
        });

        d2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedShares[2] = null;
                modifyList(12, null, 2);
                addBtn.setEnabled(true);
            }
        });

        d3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedShares[3] = null;
                modifyList(12, null, 3);
                addBtn.setEnabled(true);
            }
        });

        d4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedShares[4] = null;
                modifyList(12, null, 4);
                addBtn.setEnabled(true);
            }
        });

        d5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedShares[5] = null;
                modifyList(12, null, 5);
                addBtn.setEnabled(true);
            }
        });

        d6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedShares[6] = null;
                modifyList(12, null, 6);
                addBtn.setEnabled(true);
            }
        });

        d7.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedShares[7] = null;
                modifyList(12, null, 7);
                addBtn.setEnabled(true);
            }
        });

        d8.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedShares[8] = null;
                modifyList(12, null, 8);
                addBtn.setEnabled(true);
            }
        });

        d9.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedShares[9] = null;
                modifyList(12, null, 9);
                addBtn.setEnabled(true);
            }
        });

        d10.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedShares[10] = null;
                modifyList(12, null, 10);
                addBtn.setEnabled(true);
            }
        });

        d11.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedShares[11] = null;
                modifyList(12, null, 11);
                addBtn.setEnabled(true);
            }
        });

    }

    public void modifyList(int addIndex,String share,int deleteIndex) {

        if (addIndex != 12) {

            switch (addIndex) {
                case 0:
                    t0.setText(share);
                    p0.setVisibility(View.VISIBLE);
                    d0.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    t1.setText(share);
                    p1.setVisibility(View.VISIBLE);
                    d1.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    t2.setText(share);
                    p2.setVisibility(View.VISIBLE);
                    d2.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    t3.setText(share);
                    p3.setVisibility(View.VISIBLE);
                    d3.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    t4.setText(share);
                    p4.setVisibility(View.VISIBLE);
                    d4.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    t5.setText(share);
                    p5.setVisibility(View.VISIBLE);
                    d5.setVisibility(View.VISIBLE);
                    break;
                case 6:
                    t6.setText(share);
                    p6.setVisibility(View.VISIBLE);
                    d6.setVisibility(View.VISIBLE);
                    break;
                case 7:
                    t7.setText(share);
                    p7.setVisibility(View.VISIBLE);
                    d7.setVisibility(View.VISIBLE);
                    break;
                case 8:
                    t8.setText(share);
                    p8.setVisibility(View.VISIBLE);
                    d8.setVisibility(View.VISIBLE);
                    break;
                case 9:
                    t9.setText(share);
                    p9.setVisibility(View.VISIBLE);
                    d9.setVisibility(View.VISIBLE);
                    break;
                case 10:
                    t10.setText(share);
                    p10.setVisibility(View.VISIBLE);
                    d10.setVisibility(View.VISIBLE);
                    break;
                case 11:
                    t11.setText(share);
                    p11.setVisibility(View.VISIBLE);
                    d11.setVisibility(View.VISIBLE);
                    break;
            }
        } else {
            rearrangeShares(deleteIndex,t0,t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11);
        }

    }

    public boolean selectionFull() {
        int a = 0;
        while (a<12) {
            if (selectedShares[a] == null) {
                return false;
            }
            a += 1;
        }
        return true;
    }

    public void alreadySelectedDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("Share already selected")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create().show();
    }

    public void rearrangeShares(int z,TextView t0,TextView t1,TextView t2,TextView t3,
                                TextView t4,TextView t5,TextView t6,TextView t7,
                                TextView t8,TextView t9,TextView t10,TextView t11) {

        while (z<11) {
            selectedShares[z] = selectedShares[z+1];
            z += 1;
        }
        selectedShares[11] = null;

        int j = 0;

        while (j<12) {
            switch (j) {
                case 0:
                    t0.setText(selectedShares[j]);
                    break;
                case 1:
                    t1.setText(selectedShares[j]);
                    break;
                case 2:
                    t2.setText(selectedShares[j]);
                    break;
                case 3:
                    t3.setText(selectedShares[j]);
                    break;
                case 4:
                    t4.setText(selectedShares[j]);
                    break;
                case 5:
                    t5.setText(selectedShares[j]);
                    break;
                case 6:
                    t6.setText(selectedShares[j]);
                    break;
                case 7:
                    t7.setText(selectedShares[j]);
                    break;
                case 8:
                    t8.setText(selectedShares[j]);
                    break;
                case 9:
                    t9.setText(selectedShares[j]);
                    break;
                case 10:
                    t10.setText(selectedShares[j]);
                    break;
                case 11:
                    t11.setText(selectedShares[j]);
                    break;
            }
            j += 1;
        }

        for (int k=0; k<12; k++) {
            if (selectedShares[0] == null) {
                p0.setVisibility(View.GONE);
                d0.setVisibility(View.GONE);
            }

            if (selectedShares[1] == null) {
                p1.setVisibility(View.GONE);
                d1.setVisibility(View.GONE);
            }

            if (selectedShares[2] == null) {
                p2.setVisibility(View.GONE);
                d2.setVisibility(View.GONE);
            }

            if (selectedShares[3] == null) {
                p3.setVisibility(View.GONE);
                d3.setVisibility(View.GONE);
            }

            if (selectedShares[4] == null) {
                p4.setVisibility(View.GONE);
                d4.setVisibility(View.GONE);
            }

            if (selectedShares[5] == null) {
                p5.setVisibility(View.GONE);
                d5.setVisibility(View.GONE);
            }

            if (selectedShares[6] == null) {
                p6.setVisibility(View.GONE);
                d6.setVisibility(View.GONE);
            }

            if (selectedShares[7] == null) {
                p7.setVisibility(View.GONE);
                d7.setVisibility(View.GONE);
            }

            if (selectedShares[8] == null) {
                p8.setVisibility(View.GONE);
                d8.setVisibility(View.GONE);
            }

            if (selectedShares[9] == null) {
                p9.setVisibility(View.GONE);
                d9.setVisibility(View.GONE);
            }

            if (selectedShares[10] == null) {
                p10.setVisibility(View.GONE);
                d10.setVisibility(View.GONE);
            }

            if (selectedShares[11] == null) {
                p11.setVisibility(View.GONE);
                d11.setVisibility(View.GONE);
            }
        }
    }

    public void displayGraph(){

        Button graphBtn = (Button) findViewById(R.id.graphBtn);
        graphBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent viewMkt = new Intent(Market1.this,DisplayAllGraph.class);

                TextView temp1 = (TextView) findViewById(R.id.t0);
                TextView temp2 = (TextView) findViewById(R.id.t1);
                TextView temp3 = (TextView) findViewById(R.id.t2);
                TextView temp4 = (TextView) findViewById(R.id.t3);
                TextView temp5 = (TextView) findViewById(R.id.t4);
                TextView temp6 = (TextView) findViewById(R.id.t5);
                TextView temp7 = (TextView) findViewById(R.id.t6);
                TextView temp8 = (TextView) findViewById(R.id.t7);
                TextView temp9 = (TextView) findViewById(R.id.t8);
                TextView temp10 = (TextView) findViewById(R.id.t9);
                TextView temp11 = (TextView) findViewById(R.id.t10);
                TextView temp12 = (TextView) findViewById(R.id.t11);

                int m = 0;

                if (!temp1.getText().equals("")) {
                    String shareCode1 = (String) temp1.getText();
                    viewMkt.putExtra("s1", shareCode1);
                    m += 1;
                }
                if (!temp2.getText().equals("")) {
                    String shareCode2 = (String) temp2.getText();
                    viewMkt.putExtra("s2", shareCode2);
                    m += 1;
                }
                if (!temp3.getText().equals("")) {
                    String shareCode3 = (String) temp3.getText();
                    viewMkt.putExtra("s3", shareCode3);
                    m += 1;
                }
                if (!temp4.getText().equals("")) {
                    String shareCode4 = (String) temp4.getText();
                    viewMkt.putExtra("s4", shareCode4);
                    m += 1;
                }
                if (!temp5.getText().equals("")) {
                    String shareCode5 = (String) temp5.getText();
                    viewMkt.putExtra("s5", shareCode5);
                    m += 1;
                }
                if (!temp6.getText().equals("")) {
                    String shareCode6 = (String) temp6.getText();
                    viewMkt.putExtra("s6", shareCode6);
                    m += 1;
                }
                if (!temp7.getText().equals("")) {
                    String shareCode7 = (String) temp7.getText();
                    viewMkt.putExtra("s7", shareCode7);
                    m += 1;
                }
                if (!temp8.getText().equals("")) {
                    String shareCode8 = (String) temp8.getText();
                    viewMkt.putExtra("s8", shareCode8);
                    m += 1;
                }
                if (!temp9.getText().equals("")) {
                    String shareCode9 = (String) temp9.getText();
                    viewMkt.putExtra("s9", shareCode9);
                    m += 1;
                }
                if (!temp10.getText().equals("")) {
                    String shareCode10 = (String) temp10.getText();
                    viewMkt.putExtra("s10", shareCode10);
                    m += 1;
                }
                if (!temp11.getText().equals("")) {
                    String shareCode11 = (String) temp11.getText();
                    viewMkt.putExtra("s11", shareCode11);
                    m += 1;
                }
                if (!temp12.getText().equals("")) {
                    String shareCode12 = (String) temp12.getText();
                    viewMkt.putExtra("s12", shareCode12);
                    m += 1;
                }

                viewMkt.putExtra("m", m);
                startActivity(viewMkt);

            }


        });

    }
    public void graph1(View view){
        TextView temp = (TextView) findViewById(R.id.t0);
        String shareCode = (String) temp.getText();
        Intent viewMkt = new Intent(Market1.this,DisplayGraph.class);
        viewMkt.putExtra("name", shareCode);
        startActivity(viewMkt);
    }
    public void graph2(View view){
        TextView temp = (TextView) findViewById(R.id.t1);
        String shareCode = (String) temp.getText();
        Intent viewMkt = new Intent(Market1.this,DisplayGraph.class);
        viewMkt.putExtra("name", shareCode);
        startActivity(viewMkt);
    }
    public void graph3(View view){
        TextView temp = (TextView) findViewById(R.id.t2);
        String shareCode = (String) temp.getText();
        Intent viewMkt = new Intent(Market1.this,DisplayGraph.class);
        viewMkt.putExtra("name", shareCode);
        startActivity(viewMkt);
    }
    public void graph4(View view){
        TextView temp = (TextView) findViewById(R.id.t3);
        String shareCode = (String) temp.getText();
        Intent viewMkt = new Intent(Market1.this,DisplayGraph.class);
        viewMkt.putExtra("name", shareCode);
        startActivity(viewMkt);
    }
    public void graph5(View view){
        TextView temp = (TextView) findViewById(R.id.t4);
        String shareCode = (String) temp.getText();
        Intent viewMkt = new Intent(Market1.this,DisplayGraph.class);
        viewMkt.putExtra("name", shareCode);
        startActivity(viewMkt);
    }
    public void graph6(View view){
        TextView temp = (TextView) findViewById(R.id.t5);
        String shareCode = (String) temp.getText();
        Intent viewMkt = new Intent(Market1.this,DisplayGraph.class);
        viewMkt.putExtra("name", shareCode);
        startActivity(viewMkt);
    }
    public void graph7(View view){
        TextView temp = (TextView) findViewById(R.id.t6);
        String shareCode = (String) temp.getText();
        Intent viewMkt = new Intent(Market1.this,DisplayGraph.class);
        viewMkt.putExtra("name", shareCode);
        startActivity(viewMkt);
    }
    public void graph8(View view){
        TextView temp = (TextView) findViewById(R.id.t7);
        String shareCode = (String) temp.getText();
        Intent viewMkt = new Intent(Market1.this,DisplayGraph.class);
        viewMkt.putExtra("name", shareCode);
        startActivity(viewMkt);
    }
    public void graph9(View view){
        TextView temp = (TextView) findViewById(R.id.t8);
        String shareCode = (String) temp.getText();
        Intent viewMkt = new Intent(Market1.this,DisplayGraph.class);
        viewMkt.putExtra("name", shareCode);
        startActivity(viewMkt);
    }
    public void graph10(View view){
        TextView temp = (TextView) findViewById(R.id.t9);
        String shareCode = (String) temp.getText();
        Intent viewMkt = new Intent(Market1.this,DisplayGraph.class);
        viewMkt.putExtra("name", shareCode);
        startActivity(viewMkt);
    }
    public void graph11(View view){
        TextView temp = (TextView) findViewById(R.id.t10);
        String shareCode = (String) temp.getText();
        Intent viewMkt = new Intent(Market1.this,DisplayGraph.class);
        viewMkt.putExtra("name", shareCode);
        startActivity(viewMkt);
    }
    public void graph12(View view){
        TextView temp = (TextView) findViewById(R.id.t11);
        String shareCode = (String) temp.getText();
        Intent viewMkt = new Intent(Market1.this,DisplayGraph.class);
        viewMkt.putExtra("name", shareCode);
        startActivity(viewMkt);
    }
}
