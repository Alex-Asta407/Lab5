package com.example.lab5;

import android.content.Context;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetrofitDataLoader {
    ArrayList<String> forecastList;

    public void getForecastData(Context ctx) {
        forecastList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.meteo.lt/v1/")
                .build();

        IForecastService service = retrofit.create(IForecastService.class); // Use the new interface
        Call<ResponseBody> stringCall = service.getForecastResponse("places/vilnius/forecasts/long-term");
        stringCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String jsonFromApi = response.body().string();
                        JSONObject jObj = new JSONObject(jsonFromApi);
                        JSONArray forecastsArray = jObj.getJSONArray("forecastTimestamps");

                        for (int i = 0; i < forecastsArray.length(); i++) {
                            JSONObject forecastObject = forecastsArray.getJSONObject(i);
                            String date = forecastObject.getString("forecastTimeUtc");
                            String temperature = forecastObject.getString("airTemperature");
                            // Add other forecast details as needed

                            forecastList.add(date + " - Temperature: " + temperature);
                            // Add other forecast details to the list
                        }

                        // Update the UI with the forecast data
                        MainActivity.adapter = new ArrayAdapter<>(ctx, R.layout.listview_items_style, forecastList);
                        MainActivity.ratesListView.setAdapter(MainActivity.adapter);

                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
