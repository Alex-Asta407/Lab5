package com.example.lab5;

import android.content.Context;
import android.widget.ArrayAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class RetrofitDataLoader {
    ArrayList ratesList;

    public String getData(Context ctx) {
        ratesList = new ArrayList<String>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://your.base.url/")
                .build();

        IRatesService service = retrofit.create(IRatesService.class);
        Call<ResponseBody> stringCall = service.getRatesResponse("https://api.exchangerate.host/latest");
        stringCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    try {
                        String jsonFromApi = response.body().string();
                        JSONObject jObj = new JSONObject(jsonFromApi);
                        JSONObject ratesObject = jObj.getJSONObject("rates");
                        for (Iterator<String> it = ratesObject.keys(); it.hasNext(); ) {
                            String key = it.next();
                            String value = ratesObject.get(key).toString();
                            ratesList.add(key + " - " + value);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    MainActivity.adapter = new ArrayAdapter<>(ctx, R.layout.listview_items_style, ratesList);
                    MainActivity.ratesListView.setAdapter(MainActivity.adapter);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return "";
    }
}