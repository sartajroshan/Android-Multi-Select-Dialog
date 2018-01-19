package com.abdeveloper.sampleapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String TAG = "Cancel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //preselected Ids of Country List
        ArrayList<Integer> alreadySelectedCountries = new ArrayList<>();
        alreadySelectedCountries.add(1);
        alreadySelectedCountries.add(3);
        alreadySelectedCountries.add(4);
        alreadySelectedCountries.add(7);

        //List of Countries with Name and Id
        ArrayList<MultiSelectModel> listOfCountries= new ArrayList<>();
        listOfCountries.add(new MultiSelectModel(1,"INDIA"));
        listOfCountries.add(new MultiSelectModel(2,"USA"));
        listOfCountries.add(new MultiSelectModel(3,"UK"));
        listOfCountries.add(new MultiSelectModel(4,"UAE"));
        listOfCountries.add(new MultiSelectModel(5,"JAPAN"));
        listOfCountries.add(new MultiSelectModel(6,"SINGAPORE"));
        listOfCountries.add(new MultiSelectModel(7,"CHINA"));
        listOfCountries.add(new MultiSelectModel(8,"RUSSIA"));
        listOfCountries.add(new MultiSelectModel(9,"BANGLADESH"));
        listOfCountries.add(new MultiSelectModel(10,"BELGIUM"));
        listOfCountries.add(new MultiSelectModel(11,"DENMARK"));
        listOfCountries.add(new MultiSelectModel(12,"GERMANY"));
        listOfCountries.add(new MultiSelectModel(13,"HONG KONG"));
        listOfCountries.add(new MultiSelectModel(14,"INDONESIA"));
        listOfCountries.add(new MultiSelectModel(15,"NETHERLAND"));
        listOfCountries.add(new MultiSelectModel(16,"NEW ZEALAND"));
        listOfCountries.add(new MultiSelectModel(17,"PORTUGAL"));
        listOfCountries.add(new MultiSelectModel(18,"KUWAIT"));
        listOfCountries.add(new MultiSelectModel(19,"QATAR"));
        listOfCountries.add(new MultiSelectModel(20,"SAUDI ARABIA"));
        listOfCountries.add(new MultiSelectModel(21,"SRI LANKA"));

        //MultiSelectModel
        MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                .title(getResources().getString(R.string.multi_select_dialog_title)) //setting title for dialog
                .titleSize(25)
                .positiveText("Done")
                .negativeText("Cancel")
                .preSelectIDsList(alreadySelectedCountries) //List of ids that you need to be selected
                .multiSelectList(listOfCountries) // the multi select model list with ids and name
                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                        //will return list of selected IDS
                        for (int i = 0; i < selectedIds.size(); i++) {
                            Toast.makeText(MainActivity.this, "Selected Ids : " + selectedIds.get(i) + "\n" +
                                    "Selected Names : " + selectedNames.get(i) + "\n" +
                                    "DataString : " + dataString, Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG,"Dialog Dismissed without selection");
                    }
                });


        multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");

    }
}