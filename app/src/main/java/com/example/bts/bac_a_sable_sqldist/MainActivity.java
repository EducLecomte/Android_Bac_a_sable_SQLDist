package com.example.bts.bac_a_sable_sqldist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    // attribut
    private AccesDist accesDist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        ((EditText)findViewById(R.id.et_isbn)).setText("9782409015267");
        //((EditText)findViewById(R.id.et_isbn)).setText("9782409025273"); // flutter
        //((EditText)findViewById(R.id.et_isbn)).setText("9782709109093"); // z80
        //findViewById(R.id.btn_recup).performClick();
    }

    private void init() {
        findViewById(R.id.card).setVisibility(View.INVISIBLE);
        // initialisation du singleton pour l'acces distant
        accesDist = new AccesDist(MainActivity.this);
        accesDist.getInstance(MainActivity.this);
        accesDist.getRequestQueue();

        ecouteBtn();

    }

    private void ecouteBtn() {
        findViewById(R.id.btn_recup).setOnClickListener(new TextView.OnClickListener() {
            public void onClick(View v) {
                String isbn = ((EditText)findViewById(R.id.et_isbn)).getText().toString();
                if(isbn.length()==13){
                    accesDist.addQueue(accesDist.doJSONObjectRequest("https://openlibrary.org/isbn/"+isbn+".json"));
                    hideKeyboard(MainActivity.this);
                }else {
                    Toast.makeText(MainActivity.this, "ISBN inccorect", Toast.LENGTH_SHORT).show();
                }
                onResume();
            }
        });
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
