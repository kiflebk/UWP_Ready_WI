package u.ready_wisc.BePrepared;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import u.ready_wisc.Config;
import u.ready_wisc.R;

public class PrepWebView extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prep_web_view);
        WebView myWebView = (WebView) findViewById(R.id.webview);
        Intent i = getIntent();
        String webview = i.getStringExtra("webview");
        switch (webview) {
            case "plan":
                myWebView.loadUrl(Config.MAKE_PLAN_URL);
                break;
            case "kit":
                myWebView.loadUrl(Config.GET_KIT_URL);
                break;
        }
//        myWebView.loadUrl("http://www.example.com");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_prep_web_view, menu);
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
}
