package u.ready_wisc;

/**
 * Created by kiflebk on 3/9/15.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class CountyPicker extends Activity implements AdapterViewCompat.OnItemSelectedListener {


    Spinner counties;
    Button pick;
    public static String countyIdCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_county_picker);


        counties = (Spinner) findViewById(R.id.spinner);
        pick = (Button) findViewById(R.id.button);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.county, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        counties.setAdapter(adapter);


        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int countyInt = counties.getSelectedItemPosition();

                // County codes are located in config file for
                // easy updating and additions
                if (countyInt == 0)
                    countyIdCode = Config.RSS_DANE;
                else if (countyInt == 1)
                    countyIdCode = Config.RSS_JEFFERSON;
                else if (countyInt == 2)
                    countyIdCode = Config.RSS_KENOSHA;
                else if (countyInt == 3)
                    countyIdCode = Config.RSS_MILWAUKEE;
                else if (countyInt == 4)
                    countyIdCode = Config.RSS_OZAUKEE;
                else if (countyInt == 5)
                    countyIdCode = Config.RSS_RACINE;
                else if (countyInt == 6)
                    countyIdCode = Config.RSS_ROCK;
                else if (countyInt == 7)
                    countyIdCode = Config.RSS_SAUK;
                else if (countyInt == 8)
                    countyIdCode = Config.RSS_WALWORTH;
                else if (countyInt == 9)
                    countyIdCode = Config.RSS_WASHINGTON;
                else if (countyInt == 10)
                    countyIdCode = Config.RSS_WAUKESHA;
                startUI(getCurrentFocus());
            }
        });

    }


    public void startUI(View v){

        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        intent.putExtra("county",counties.getSelectedItem().toString());

        startActivity(intent);
        finish();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_county_picker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterViewCompat<?> adapterViewCompat, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterViewCompat<?> adapterViewCompat) {

    }
}
