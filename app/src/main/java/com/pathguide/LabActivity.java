package com.pathguide;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import adapters.LabListAdapter;
import connectors.Database;
import models.Lab;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LabActivity extends AppCompatActivity {
    Context context;
    ArrayList<Lab> labs = new ArrayList<>();
    Database local_db;
    LinearLayout result_body;
    TextView result_header;
    EditText searchbox;
    ListView lab_list;
    LabListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab);
        context = this;
        local_db = new Database(context);
        lab_list = (ListView)findViewById(R.id.lab_list);
        searchbox = (EditText)findViewById(R.id.searchbox);
        result_body = (LinearLayout) findViewById(R.id.result_body);
        if (result_body != null) {
            result_body.setVisibility(View.INVISIBLE);
        }

        clickEvents();

        result_header = (TextView)findViewById(R.id.result_header);
        result_header.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Montserrat.otf"), Typeface.ITALIC);

        searchbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().length() != 0){
                    result_body.setVisibility(View.VISIBLE);
                    labs = local_db.getSearchLabs(charSequence.toString());
                    local_db.close();
                    showResults(labs);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 0){
                    result_body.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void clickEvents() {

        lab_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                final CharSequence[] options = {"Engineering car park","Engineering-Science link up bridge"};
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Where are you coming from?")
                        .setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(context, Directions.class);
                                intent.putExtra("lab", labs.get(position));
                                intent.putExtra("location",options[i]);
                                startActivity(intent);
                            }
                        })
                        .create().show();

            }
        });
    }

    private void showResults(ArrayList<Lab> labs) {
        adapter = new LabListAdapter(context, labs);
        lab_list.setAdapter(adapter);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
