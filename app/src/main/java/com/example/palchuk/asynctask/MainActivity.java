package com.example.palchuk.asynctask;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class MainActivity extends ActionBarActivity {
    MyTask mt;
    TextView tv;
    final String LOG_TAG = "MyLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);
    }

    class MyTask extends AsyncTask<Void, Void, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tv.setText("Begin");
            Log.d(LOG_TAG,"Begin.");
        }

        @Override
        protected Integer doInBackground(Void... params) {
            try {
//                int cnt = 0;
//                for (String url:urls) {
//                    downloadFile(url);
//                    publishProgress(++cnt);
//                }
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return 100500;
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            tv.setText("Downloaded " + values[0] + " files");
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            tv.setText("End. Result = " +result );
            Log.d(LOG_TAG,"End. Result = " +result);
        }

        private void downloadFile(String url) throws InterruptedException {
            TimeUnit.SECONDS.sleep(2);
        }
    }

    public void onclick(View view) {
//        mt = new MyTask();
//        mt.execute("file_path_1", "file_path_2", "file_path_3", "file_path_4");
        switch (view.getId()) {
            case R.id.button:
                mt = new MyTask();
                mt.execute();
                break;
            case R.id.button2:
                showResult();
                break;
            default:
                break;
        }
    }

    private void showResult() {
        if (mt == null) return;
        int result = -1;
        try {
            Log.d(LOG_TAG, "Try to get result");
            result = mt.get(1, TimeUnit.SECONDS);
            Log.d(LOG_TAG, "get returns " + result);
            Toast.makeText(this, "get returns " + result, Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            Log.d(LOG_TAG,"TIMEOUT. Result = " + result);
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

 }
