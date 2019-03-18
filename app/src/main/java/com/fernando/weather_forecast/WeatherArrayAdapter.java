package com.fernando.weather_forecast;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class WeatherArrayAdapter extends ArrayAdapter<Weather> {
    WeatherArrayAdapter(Context context, List<Weather> previsoes) {
        super(context, -1, previsoes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View covnertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View caixa = inflater.inflate(R.layout.list_item, parent, false);
        Weather caraDaVez = getItem(position);

        TextView dayTextView = caixa.findViewById(R.id.dayTextView);

        TextView lowTextView = caixa.findViewById(R.id.lowTextView);

        TextView highTextView = caixa.findViewById(R.id.highTextView);

        TextView humidityTextView = caixa.findViewById(R.id.humidityTextView);

        dayTextView.setText(getContext().getString(R.string.day_description, caraDaVez.dayOfWeek, caraDaVez.description));

        lowTextView.setText(getContext().getString(R.string.low_temp, caraDaVez.minTemp));

        highTextView.setText(getContext().getString(R.string.high_temp, caraDaVez.maxTemp));

        return caixa;
    }
}
