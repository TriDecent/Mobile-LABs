package com.example.lab02_2;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListViewManager {

    private final ListView lvPerson;
    private final TextView tvSelection;
    private final ArrayAdapter<String> adapter;
    private final List<String> names;

    public ListViewManager(AppCompatActivity activity) {
        lvPerson = activity.findViewById(R.id.lv_person);
        tvSelection = activity.findViewById(R.id.tv_selection);
        names = new ArrayList<>(List.of("Teo", "Ty", "Bin", "Bo"));
        adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, names);
    }

    public void setupListView() {
        lvPerson.setAdapter(adapter);

        // Listen for clicking item on the list view
        // And update the text view with the selected item
        lvPerson.setOnItemClickListener(
                (parent, view, position, id) ->
                        tvSelection.setText(lvPerson.getContext().getString(R.string.selection_text, position, names.get(position)))
        );

        // Listen for long clicking item on the list view
        // And remove the selected item from the list
        lvPerson.setOnItemLongClickListener(
                (parent, view, position, id) -> {
                    names.remove(position);
                    adapter.notifyDataSetChanged();
                    return true;
                }
        );
    }

    public void addName(String name) {
        names.add(name);
        adapter.notifyDataSetChanged();
    }
}
