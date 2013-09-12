package com.waldm.MastermindAndroid.dialogs;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.waldm.MastermindAndroid.Colour;
import com.waldm.MastermindAndroid.ColourRepository;
import com.waldm.MastermindAndroid.MainActivity;
import com.waldm.MastermindAndroid.R;

import java.util.List;

public class ColourPickerDialog extends ListActivity {
    public static final String COLOUR_KEY = "ColourKey";

    private class PegAdapter extends ArrayAdapter<Colour>{
        public PegAdapter(Context context, List<Colour> array) {
            super(context, 0, array);
        }

        @Override
        public final View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_item_peg, parent, false);
            }

            TextView textView = (TextView)convertView.findViewById(R.id.text);
            textView.setText(getItem(position).name);

            ImageView image = (ImageView)convertView.findViewById(R.id.image);
            image.setImageResource(getItem(position).drawableResource);
            return convertView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int numberOfAvailableColours = getIntent().getIntExtra(MainActivity.NUMBER_OF_AVAILABLE_COLOURS, -1);
        final ColourRepository colourRepository = new ColourRepository(numberOfAvailableColours);

        PegAdapter adapter = new PegAdapter(this, colourRepository.getAvailableColours());
        setListAdapter(adapter);

        setTitle(R.string.colour_picker_title);

        final ListView listView = getListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Colour arrayItem = colourRepository.getAvailableColours().get(position);
                Intent intent = new Intent();
                intent.putExtra(COLOUR_KEY, arrayItem.name);
                setResult(MainActivity.RESULT_COLOUR_PICKED, intent);
                finish();
            }
        });
    }
}
