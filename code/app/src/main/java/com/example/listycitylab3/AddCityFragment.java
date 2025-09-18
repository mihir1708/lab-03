package com.example.listycitylab3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {
    interface AddCityDialogListener {
        void addCity(City city);
        void editCity(City city, int index);
    }
    private AddCityDialogListener listener;

    static AddCityFragment newInstance(City city, int index) {
        Bundle args = new Bundle();
        args.putSerializable("city", city);
        args.putInt("index", index);
        AddCityFragment fragment = new AddCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        int index;
        City preexisting = null;
        Bundle args = getArguments();
        if (args != null) {
            index = args.getInt("index", -1);
            preexisting = (City) args.getSerializable("city");
        } else {
            index = -1;
        }
        if (preexisting != null && index >= 0) {
            editCityName.setText(preexisting.getName());
            editProvinceName.setText(preexisting.getProvince());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add/ edit city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    if (index >= 0) {
                        listener.editCity(new City(cityName, provinceName), index);
                    }
                    else listener.addCity(new City(cityName, provinceName));
                })
                .create();
    }
}