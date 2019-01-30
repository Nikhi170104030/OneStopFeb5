package com.swc.onestop.Activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.swc.onestop.R;

public class Timetable extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener, View.OnTouchListener {

    int roll, division = 0, labGroup = 0, tutGroup = 0;
    EditText et;
    SharedPreferences sp;
    Spinner SLab, Sbranch, STut;
    String[] tut = {"Select tut group", "T1", "T2", "T3", "T4", "T5", "T6", "T7", "T8", "T9", "T10", "T11", "T12", "T13", "T14"};
    String[] lab = {"Select lab group", "L1", "L2", "L3", "L4", "L5", "L6", "L7", "L8", "L9", "L10"};
    String[] branch = {"Select branch", "ME", "CE", "ECE", "EEE", "BT", "CSE", "EPH", "M&C", "CL", "CST", "DD"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);


        et = (EditText) findViewById(R.id.etRoll);
        et.setOnTouchListener(this);

        sp = getSharedPreferences("rollcode", 0);
        if (sp.getString("roll", null) != null)
            et.setText(sp.getString("roll", null), TextView.BufferType.EDITABLE);

        Sbranch = (Spinner) findViewById(R.id.spinTTbranch);
        ArrayAdapter<String> adapterDiv = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, branch);
        Sbranch.setAdapter(adapterDiv);
        Sbranch.setOnItemSelectedListener(this);

        SLab = (Spinner) findViewById(R.id.spinTTLab);
        ArrayAdapter<String> adapterLab = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, lab);
        SLab.setAdapter(adapterLab);
        SLab.setOnItemSelectedListener(this);

        STut = (Spinner) findViewById(R.id.spinTTtut);
        ArrayAdapter<String> adapterTut = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tut);
        STut.setAdapter(adapterTut);
        STut.setOnItemSelectedListener(this);

        Button submit = (Button) findViewById(R.id.bTTSubmit);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {


        String s = et.getText().toString();

        //Input handling
        if (!s.equals("")) {
            try {
                roll = Integer.parseInt(s);
                if ((roll > 179999999) | (roll < 170000000)) {
                    Toast t = Toast.makeText(this, "Enter a valid FIRST year roll number", Toast.LENGTH_SHORT);
                    t.show();
                } else {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("roll", s);
                    editor.commit();

                    Intent i = new Intent("com.helloiitg.ttdata");
                    i.putExtra("Lab", LabGroup(roll));
                    i.putExtra("Tut", TutGroup(roll));
                    i.putExtra("Division", Division(roll));
                    i.putExtra("roll", roll);
                    startActivity(i);
                }
            } catch (NumberFormatException nfe) {
                Toast t = Toast.makeText(this, "Error", Toast.LENGTH_SHORT);
                t.show();
            }
        } else {
            if ((division != 0) & (labGroup != 0) & (tutGroup != 0)) {
                Intent intent = new Intent("com.helloiitg.ttdata");
                intent.putExtra("Tut", tutGroup);
                intent.putExtra("Division", division);
                intent.putExtra("Lab", labGroup);
                startActivity(intent);
            } else {
                Toast t = Toast.makeText(this, "Fill the form completely", Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }

    private int Division(int roll) {
        if (((roll >= 170103001) & (roll <= 170103081)) | ((roll >= 170104001) & (roll <= 170104080)))
            return 1;
        else if (((roll >= 170106001) & (roll <= 170106067)) | ((roll >= 170108001) & (roll <= 170108043)) | ((roll >= 170102001) & (roll <= 170102073)))
            return 2;
        else if (((roll >= 170101001) & (roll <= 170101080)) | ((roll >= 170121001) & (roll <= 170121051)) | ((roll >= 170123001) & (roll <= 170123055)))
            return 3;
        else if (((roll >= 170107001) & (roll <= 170107072)) | ((roll >= 170122001) & (roll <= 170122050)) | ((roll >= 170205001) & (roll <= 170205045)))
            return 4;
        else
            return 0;
    }

    private int TutGroup(int roll) {
        if ((roll >= 170103001) & (roll <= 150103050))
            return 1;
        else if (((roll >= 170103051) & (roll <= 170103081)) | ((roll >= 170104001) & (roll <= 170104019)))
            return 2;
        else if ((roll >= 170104020) & (roll <= 170104069))
            return 3;
        else if (((roll >= 170102001) & (roll <= 170102039)) | ((roll >= 170104070) & (roll <= 170104080)))
            return 4;
        else if (((roll >= 170102040) & (roll <= 170102073)) | ((roll >= 170106001) & (roll <= 170106016)))
            return 5;
        else if ((roll >= 170106017) & (roll <= 170106066))
            return 6;
        else if (((roll >= 170101001) & (roll <= 170101006)) | ((roll >= 170108001) & (roll <= 170108043)) | (roll == 170106067))
            return 7;
        else if ((roll >= 170101007) & (roll <= 170101056))
            return 8;
        else if (((roll >= 170101057) & (roll <= 170101080)) | ((roll >= 170121001) & (roll <= 170121026)))
            return 9;
        else if (((roll >= 170123001) & (roll <= 170123025)) | ((roll >= 170121027) & (roll <= 170121051)))
            return 10;
        else if (((roll >= 170123026) & (roll <= 170123055)) | ((roll >= 170107001) & (roll <= 170107020)))
            return 11;
        else if ((roll >= 170107021) & (roll <= 170107070))
            return 12;
        else if (((roll >= 170122001) & (roll<= 170122048)) |(roll== 170107071) | (roll == 170107072))
            return 13;
        else if ((roll== 170122049) | (roll== 170122050)| ((roll>=170205001) & (roll <= 170205045)))
            return 14;
        else
            return 0;
    }

    private int LabGroup(int roll) {
        if ((roll >= 170103001) & (roll <= 170103070))
            return 1;
        else if (((roll >= 170103071) & (roll <= 170103081)) | ((roll >= 170104001) & (roll <= 170104059)))
            return 2;
        else if (((roll >= 170102001) & (roll <= 170102049)) | ((roll >= 170104060) & (roll <= 170104080)))
            return 3;
        else if (((roll >= 170102050) & (roll <= 170102073)) | ((roll >= 170106001) & (roll <= 170106046)))
            return 4;
        else if (((roll >= 170106047) & (roll <= 170106067)) | ((roll >= 170108001) & (roll <= 170108043)))
            return 5;
        else if (((roll >= 170101007) & (roll <= 170101076)))
            return 6;
        else if (((roll >= 170121001) & (roll <= 170121051)) | ((roll >= 170123001) & (roll <= 170123015)) |((roll>= 170101077)&(roll<= 170101080)))
            return 7;
        else if (((roll >= 170107001) & (roll <= 170107030)) | ((roll >= 170123016) & (roll <= 170123055)))
            return 8;
        else if (((roll >= 170107031) & (roll <= 170107072)) | ((roll >= 170122001) & (roll <= 170122028)))
            return 9;
        else if (((roll >= 170122029) & (roll <= 170122050)) | ((roll >= 170205001) & (roll <= 170205045)))
            return 10;
        else
            return 0;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinner spinner = (Spinner) adapterView;
        switch (spinner.getId()) {
            case R.id.spinTTbranch:
                if ((i == 1) | (i == 2))
                    division = 1;
                else if ((i >= 3) & (i <= 5))
                    division = 2;
                else if ((i >= 6) & (i <= 8))
                    division = 3;
                else if ((i >= 9) & (i <= 11))
                    division = 4;
                if (i != 0)
                    et.setText("", TextView.BufferType.EDITABLE);
                break;
            case R.id.spinTTLab:
                labGroup = i;
                if (i != 0)
                    et.setText("", TextView.BufferType.EDITABLE);
                break;
            case R.id.spinTTtut:
                tutGroup = i;
                if (i != 0)
                    et.setText("", TextView.BufferType.EDITABLE);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Sbranch.setSelection(0);
        SLab.setSelection(0);
        STut.setSelection(0);
        return false;
    }


}
