package com.pathguide;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapters.ExpandableListAdapter;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FAQs extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.faq_list);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }

    private void prepareListData() {
            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<String>>();

            // Adding child data

            listDataHeader.add("Where can I eat?");
            listDataHeader.add("Where can I relax?");
            listDataHeader.add("Where can I make photocopies?");
            listDataHeader.add("Where can I submit my docket?");
            listDataHeader.add("Where can I pay my faculty dues?");


            // Adding child data
            List<String> eatAnswer = new ArrayList<String>();
            eatAnswer.add("The Engineering canteen can be found blah blah blah");

            List<String> relaxAnswer = new ArrayList<String>();
            relaxAnswer.add("There are various relaxation centres in the faculty.\n\nCivil Shed\n\nDubbing centre sef dey");

            List<String> photocopyAnswer = new ArrayList<String>();
            photocopyAnswer.add("Photocopies or printing can done at the any of Business centres besides the new System Engineering building");

            List<String> docketAnswer = new ArrayList<String>();
            docketAnswer.add("Dockets are to be submitted to your course adviser. At least that's who I've been submitting to since");

            List<String> duesAnswer = new ArrayList<String>();
            duesAnswer.add("Well, if you choose to pay, payment of faculty dues can be made at the faculty office. And if you choose not to, you'll still be forced to anyway!");


            listDataChild.put(listDataHeader.get(0), eatAnswer); // Header, Child data
            listDataChild.put(listDataHeader.get(1), relaxAnswer);
            listDataChild.put(listDataHeader.get(2), photocopyAnswer);
            listDataChild.put(listDataHeader.get(3), docketAnswer);
            listDataChild.put(listDataHeader.get(4), duesAnswer);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
