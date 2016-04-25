package com.communicator;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import java.io.IOException;

public class JsonPostToFetchData extends
        AsyncTask<String, Void, JsonRequestData> {
    private static final String TAG = "FetchMyDataTask";
    ProgressDialog progressBar;
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private ProgressDialog mProgressDialog;
    ConnectionDetector connection;

    private JsonRequestData processData;
    String destinationFileName = null, coordinatorId, response,
            originalVoiceName;
    final static int CONN_WAIT_TIME = 40000;
    final static int CONN_DATA_WAIT_TIME = 40000;
    private Context context;
    private TaskCompleteListener listener;

    public JsonPostToFetchData(Context ctx, JsonRequestData createRequest) {

        this.context = ctx;
        this.processData = createRequest;
    }

    @Override
    protected JsonRequestData doInBackground(String... params) {
        JsonRequestData requestData = new JsonRequestData();

        try {
            requestData.setRequestdata(processData.getRequestdata());
            requestData.setUrl(processData.getUrl());
            requestData.setResult(mPostJsonRequest(requestData));
        } catch (Exception e) {

            e.printStackTrace();
        }

        return requestData;
    }

    /**
     * POST request data
     */
    public static String mPostJsonRequest(JsonRequestData requestDatas) {

        String result = "";
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, CONN_WAIT_TIME);
        HttpConnectionParams.setSoTimeout(httpParams, CONN_DATA_WAIT_TIME);
        HttpClient httpclient = new DefaultHttpClient(httpParams);


        try {
            // 1. create HttpClient

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(requestDatas.getUrl());

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(requestDatas.getRequestdata(),
                    "UTF-8");
            se.setContentType("application/json");

            // 6. set httpPost Entity
            httpPost.setEntity(se);
            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            // 7. Set some headers to inform server about the type of the
            // content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            BasicResponseHandler responseHandler = new BasicResponseHandler();
            if (httpResponse != null) {
                try {
                    result = responseHandler.handleResponse(httpResponse);
                } catch (HttpResponseException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                result = null;

            }

        } catch (Exception e) {

        } finally {

        }
        System.out.println("Response " + result.toString());
        return result;
    }

    @Override
    protected void onPostExecute(JsonRequestData responseData) {
        super.onPostExecute(responseData);
        if ((progressBar != null) && progressBar.isShowing()) {
            progressBar.dismiss();
        }
        System.out.println("response " + responseData.getResult());
        listener.onTaskComplete(responseData);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        Log.i(TAG, "Async task started");

        progressBar = new ProgressDialog(context);
        progressBar.setCancelable(true);
        progressBar.setProgressStyle(AlertDialog.THEME_HOLO_DARK);
        progressBar.setMessage("Please wait....");
        progressBar.setIndeterminate(false);
        progressBar.setCancelable(false);
        progressBar.show();

    }

    public void setFetchMyData(TaskCompleteListener listener) {
        this.listener = listener;

    }
}
