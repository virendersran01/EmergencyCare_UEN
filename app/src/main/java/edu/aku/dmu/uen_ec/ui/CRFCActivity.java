package edu.aku.dmu.uen_ec.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import edu.aku.dmu.uen_ec.JSONModels.JSONModelCRFA;
import edu.aku.dmu.uen_ec.JsonUtils.JSONUtils;
import edu.aku.dmu.uen_ec.R;
import edu.aku.dmu.uen_ec.contracts.FormsContract;
import edu.aku.dmu.uen_ec.core.DatabaseHelper;
import edu.aku.dmu.uen_ec.core.MainApp;
import edu.aku.dmu.uen_ec.databinding.ActivityBBinding;
import edu.aku.dmu.uen_ec.databinding.ActivityCBinding;
import edu.aku.dmu.uen_ec.util.Util;

import static android.content.Context.MODE_PRIVATE;
import static edu.aku.dmu.uen_ec.core.MainApp.fc;

public class CRFCActivity extends AppCompatActivity {

    private static final String TAG = "CRFCActivity";
    public static ActivityCBinding bi;
    DatabaseHelper db;

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    public static boolean days_21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_c);
        bi.setCallback(this);
        db = new DatabaseHelper(this);

        days_21 = true;

        get_data_recylceview("0");


    }

    public void day21() {

        get_data_recylceview("0");
        days_21 = true;

    }

    public void day28() {
        get_data_recylceview("1");

        days_21 = false;

    }

    public void get_data_recylceview(String crfstatus) {
        // list here
        List<String> list = getdata(crfstatus);

        if (crfstatus == "0") {
            bi.btn21.setText("21 Days Follow-Up" + " (" + list.size() + ")");
        } else {
            bi.btn48.setText("28 Days Follow-Up" + " (" + list.size() + ")");
        }
        if (list == null)
            return;

        Collections.sort(list);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_survey_completed);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new SurveyCompletedCustomAdapter(this, list);
        mRecyclerView.setAdapter(mAdapter);

    }


    public List<String> getdata(String crfstatus) {
        List<String> lst_string = new ArrayList<String>();
        List<FormsContract> lst = db.getsFormContractCRFC(crfstatus);
        if (lst != null) {
            for (FormsContract fc : lst) {
                JSONModelCRFA crfa = JSONUtils.getModelFromJSON(fc.getCRFA(), JSONModelCRFA.class);
                String stringg = "";
                if(crfa.getCra12().equals("1") || crfa.getCra12().equals("2") || crfa.getCra12().equals("3") ) {
                    stringg = crfa.getCra01() + "-" + crfa.getCra02() + "-" + crfa.getCra04() + "-" + crfa.getCra05() + "-" +
                            crfa.getCra03a() + "/" + crfa.getCra03b() + "/" + crfa.getCra03c();
                    lst_string.add(stringg);
                }
            }
        }
        return lst_string;
    }










/*
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back.", Toast.LENGTH_SHORT).show();
    }


 */

}

