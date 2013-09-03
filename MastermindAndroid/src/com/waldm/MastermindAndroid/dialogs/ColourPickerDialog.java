package com.waldm.MastermindAndroid.dialogs;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.waldm.MastermindAndroid.MainActivity;
import com.waldm.MastermindAndroid.R;

import java.util.List;
import java.util.Map;

public class ColourPickerDialog extends ListActivity {
    public static final int COLOUR_PICKED = 1989;
    public static final String IMAGE_KEY = "ImageKey";
    public static final String COLOUR_KEY = "ColourKey";

    private class PegAdapter extends ArrayAdapter<Map<String, Object>>{
        public PegAdapter(Context context, List<Map<String, Object>> array) {
            super(context, 0, array);
        }

        @Override
        public final View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_item_peg, parent, false);
            }

            TextView textView = (TextView)convertView.findViewById(R.id.text);
            String text = getItem(position).get(COLOUR_KEY).toString();
            textView.setText(String.valueOf(text.charAt(0)).toUpperCase() + text.substring(1).toLowerCase());

            ImageView image = (ImageView)convertView.findViewById(R.id.image);
            image.setImageResource((Integer) getItem(position).get(IMAGE_KEY));
            return convertView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final List<Map<String, Object>> array = Lists.newArrayList();
        for (MainActivity.Colour colour : MainActivity.colourDrawableIds.keySet()) {
            ImmutableMap<String, Object> map = new ImmutableMap.Builder<String, Object>()
                    .put(COLOUR_KEY, colour)
                    .put(IMAGE_KEY, MainActivity.colourDrawableIds.get(colour))
                    .build();
            array.add(map);
        }

        PegAdapter adapter = new PegAdapter(this, array);
        setListAdapter(adapter);

        setTitle(R.string.colour_picker_title);

        final ListView listView = getListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Map<String, Object> arrayItem = array.get(position);
                Intent intent = new Intent();
                intent.putExtra(COLOUR_KEY, arrayItem.get(COLOUR_KEY).toString());
                intent.putExtra(IMAGE_KEY, (Integer)arrayItem.get(IMAGE_KEY));
                setResult(COLOUR_PICKED, intent);
                finish();
            }
        });
    }
}
