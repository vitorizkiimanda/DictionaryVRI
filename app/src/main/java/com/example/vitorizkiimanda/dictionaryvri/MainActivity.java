package com.example.vitorizkiimanda.dictionaryvri;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Integer, Void> {
        final String TAG = LoadData.class.getSimpleName();
        DictionaryHelper dictionaryHelper;
        AppPreference appPreference;
        double progress;
        double maxprogress = 100;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dictionaryHelper = new DictionaryHelper(MainActivity.this);
            appPreference = new AppPreference(MainActivity.this);
        }

        @Override
        protected Void doInBackground(Void... params) {

            Boolean firstRun = appPreference.getFirstRun();
            dictionaryHelper.open();

            if (firstRun) {

                ArrayList<DictionaryModel> dictionaryModelArrayListIndonesia = preLoadRaw(R.raw.english_indonesia);
                ArrayList<DictionaryModel> dictionaryModelArrayListEnglish = preLoadRaw(R.raw.indonesia_english);


                progress = 30;
                publishProgress((int) progress);
                Double progressMaxInsert = 80.0;
                Double progressDiff = (progressMaxInsert - progress) / (dictionaryModelArrayListEnglish.size() + dictionaryModelArrayListIndonesia.size());

//                Log.d("apakah dapat data", String.valueOf(dictionaryModelArrayListIndonesia));
                dictionaryHelper.insertTransaction(dictionaryModelArrayListEnglish, true);
                progress+=progressDiff;
                publishProgress((int) progress);

                dictionaryHelper.insertTransaction(dictionaryModelArrayListIndonesia, false);
                progress+=progressDiff;
                publishProgress((int) progress);

                dictionaryHelper.close();
                appPreference.setFirstRun(false);
            }else{
                try {
                    synchronized (this) {
                        this.wait(300);

                        publishProgress(50);

                        this.wait(300);
                        publishProgress((int) maxprogress);
                    }
                } catch (Exception e) {
                }
            }

            return null;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent i = new Intent(MainActivity.this, DictionaryActivity.class);
            startActivity(i);
            finish();
        }
    }

    public ArrayList<DictionaryModel> preLoadRaw(int data) {
        ArrayList<DictionaryModel> words = new ArrayList<>();
        String line = null;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_res = res.openRawResource(data);

            reader = new BufferedReader(new InputStreamReader(raw_res));
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                DictionaryModel w;

                w = new DictionaryModel(splitstr[0], splitstr[1]);

                words.add(w);
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return words;


    }
}