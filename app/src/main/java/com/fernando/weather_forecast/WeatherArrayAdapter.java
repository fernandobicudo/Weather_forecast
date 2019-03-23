package com.fernando.weather_forecast;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class WeatherArrayAdapter extends ArrayAdapter<Weather> {

    private class ViewHolder{

        public ImageView conditionImageView;
        public TextView dayTextView;
        public TextView lowTextView;
        public TextView highTextView;
        public TextView humidityTextView;
    }


    private Map<String, Bitmap> figuras = new HashMap<>();

    WeatherArrayAdapter(Context context, List<Weather> previsoes) {
        super(context, -1, previsoes);
    }

    private class LoadImageTask extends AsyncTask<String, Void, Bitmap> {

        private ImageView conditionImageView;String iconURL;

        public LoadImageTask (String iconURL, ImageView conditionImageView) {
            this.conditionImageView = conditionImageView;
            this.iconURL = iconURL;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap){
            conditionImageView.setImageBitmap(bitmap);
            figuras.put(this.iconURL, bitmap);
        }

        @Override
        protected Bitmap doInBackground(String... strings){
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream is = conn.getInputStream();
                Bitmap figura = BitmapFactory.decodeStream(is);
                return figura;
            }
                catch  (IOException e) {
                    e.printStackTrace();
                }
                return null;
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder vh = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);

            vh = new ViewHolder();
            vh.dayTextView = convertView.findViewById(R.id.dayTextView);
            vh.lowTextView = convertView.findViewById(R.id.lowTextView);
            vh.highTextView = convertView.findViewById(R.id.highTextView);
            vh.humidityTextView = convertView.findViewById(R.id.humidityTextView);
            vh.conditionImageView = convertView.findViewById(R.id.conditionImageView);

            convertView.setTag(vh);


        }
        else
            //downcasting
            vh = (ViewHolder) convertView.getTag();


        Weather caraDaVez = getItem(position);

//        TextView dayTextView = convertView.findViewById(R.id.dayTextView);
//
//        TextView lowTextView = convertView.findViewById(R.id.lowTextView);
//
//        TextView highTextView = convertView.findViewById(R.id.highTextView);
//
//        TextView humidityTextView = convertView.findViewById(R.id.humidityTextView);
//        ImageView conditionImageView = convertView.findViewById(R.id.conditionImageView);

        if (figuras.containsKey(caraDaVez.iconURL)) {

            vh.conditionImageView.setImageBitmap(figuras.get(caraDaVez.iconURL));

        }
        else {

            new LoadImageTask(caraDaVez.iconURL, vh.conditionImageView).execute(caraDaVez.iconURL);
        }

        vh.dayTextView.setText(getContext().getString(R.string.day_description, caraDaVez.dayOfWeek, caraDaVez.description));

        vh.lowTextView.setText(getContext().getString(R.string.low_temp, caraDaVez.minTemp));

        vh.highTextView.setText(getContext().getString(R.string.high_temp, caraDaVez.maxTemp));

        vh.humidityTextView.setText(getContext().getString(R.string.humidity, caraDaVez.humidity));

        return convertView;
    }
}
