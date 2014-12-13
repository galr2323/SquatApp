//package com.sqvat.squat;
//
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.util.Log;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//
///**
// * Created by GAL on 9/13/2014.
// */
//public class GetExercisesTask extends AsyncTask<String, Void, String[]> {
//
//    private final String LOG_TAG = GetExercisesTask.class.getSimpleName();
//
//    @Override
//    protected String[] doInBackground(String... params) {
//
//
//        if (params.length == 0)
//            return null;
//
//        // These two need to be declared outside the try/catch
//// so that they can be closed in the finally block.
//        HttpURLConnection urlConnection = null;
//        BufferedReader reader = null;
//
//// Will contain the raw JSON response as a string.
//        String jsonStr = null;
//
//        String format = "json";
//        String units = "metric";
//        int numDays = 7;
//
//        try {
//            final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
//            final String Q_PARAM = "q";
//            final String FORMAT_PARAM = "mode";
//            final String UNITS_PARAM = "units";
//            final String DAYS_PARAM = "cnt";
//
//            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
//                    .appendQueryParameter(Q_PARAM, params[0])
//                    .appendQueryParameter(FORMAT_PARAM, format)
//                    .appendQueryParameter(UNITS_PARAM, units)
//                    .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
//                    .build();
//
//            URL url = new URL(builtUri.toString());
//
//            // Create the request to OpenWeatherMap, and open the connection
//            urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setRequestMethod("GET");
//            urlConnection.connect();
//
//            // Read the input stream into a String
//            InputStream inputStream = urlConnection.getInputStream();
//            StringBuffer buffer = new StringBuffer();
//            if (inputStream == null) {
//                // Nothing to do.
//                return null;
//            }
//            reader = new BufferedReader(new InputStreamReader(inputStream));
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
//                // But it does make debugging a *lot* easier if you print out the completed
//                // buffer for debugging.
//                buffer.append(line + "\n");
//            }
//
//            if (buffer.length() == 0) {
//                // Stream was empty.  No point in parsing.
//                return null;
//            }
//            jsonStr = buffer.toString();
//        } catch (IOException e) {
//            Log.e("PlaceholderFragment", "Error ", e);
//            // If the code didn't successfully get the weather data, there's no point in attemping
//            // to parse it.
//            return null;
//        } finally{
//            if (urlConnection != null) {
//                urlConnection.disconnect();
//            }
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (final IOException e) {
//                    Log.e("PlaceholderFragment", "Error closing stream", e);
//                }
//            }
//        }
//
//        try {
//            String[] weatherData = getWeatherDataFromJson(jsonStr, 7);
//            return weatherData;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }
//
//    private String[] getWeatherDataFromJson(String forecastJsonStr, int numDays)
//            throws JSONException {
//
//        // These are the names of the JSON objects that need to be extracted.
//        final String OWM_LIST = "list";
//        final String OWM_WEATHER = "weather";
//        final String OWM_TEMPERATURE = "temp";
//        final String OWM_MAX = "max";
//        final String OWM_MIN = "min";
//        final String OWM_DATETIME = "dt";
//        final String OWM_DESCRIPTION = "main";
//
//        JSONObject forecastJson = new JSONObject(forecastJsonStr);
//        JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);
//
//        String[] resultStrs = new String[numDays];
//        for(int i = 0; i < weatherArray.length(); i++) {
//            // For now, using the format "Day, description, hi/low"
//            String day;
//            String description;
//            String highAndLow;
//
//            // Get the JSON object representing the day
//            JSONObject dayForecast = weatherArray.getJSONObject(i);
//
//            // The time/time is returned as a long.  We need to convert that
//            // into something human-readable, since most people won't read "1400356800" as
//            // "this saturday".
//            long dateTime = dayForecast.getLong(OWM_DATETIME);
//            day = getReadableDateString(dateTime);
//
//            // description is in a child array called "weather", which is 1 element long.
//            JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
//            description = weatherObject.getString(OWM_DESCRIPTION);
//
//            // Temperatures are in a child object called "temp".  Try not to name variables
//            // "temp" when working with temperature.  It confuses everybody.
//            JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
//            double high = temperatureObject.getDouble(OWM_MAX);
//            double low = temperatureObject.getDouble(OWM_MIN);
//
//            highAndLow = formatHighLows(high, low);
//            resultStrs[i] = day + " - " + description + " - " + highAndLow;
//        }
//
//        return resultStrs;
//    }
//
//
//
//    @Override
//    protected void onPostExecute(String[] result) {
//        if(result != null){
//            forecastListAdapter.clear();
//            forecastListAdapter.addAll(result);
//        }
//    }
//}
