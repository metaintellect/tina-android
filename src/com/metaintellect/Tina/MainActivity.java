package com.metaintellect.tina;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.metaintellect.tina.models.Invoice;

public class MainActivity extends SherlockActivity {

    private Invoice invoice;

    public void onCreate(Bundle savedInstanceState) {

        int theme = R.style.Theme_Sherlock_Light;

        if (android.os.Build.VERSION.SDK_INT >= 15) {
            theme = R.style.Theme_Sherlock_Light_DarkActionBar;
        }

        setTheme(theme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.invoice = new Invoice();

        final TextView productCodeTextView = (TextView) findViewById(R.id.productCodeTextView);
        final EditText productCodeEditText = (EditText) findViewById(R.id.productCodeEditText);
        final EditText quantityEditText = (EditText) findViewById(R.id.quantityEditText);
        final TextView totalTextView = (TextView) findViewById(R.id.totalTextView);

        productCodeTextView.setVisibility(View.GONE);
        quantityEditText.setVisibility(View.GONE);

        totalTextView.setText(getString(R.string.Total) + this.invoice.getTotalPrice());

        productCodeEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    productCodeEditText.setVisibility(View.GONE);
                    productCodeTextView.setVisibility(View.VISIBLE);
                    productCodeTextView.setText("Medica 66.66");
                    quantityEditText.setVisibility(View.VISIBLE);
                    quantityEditText.requestFocus();
                    return true;
                }
                else {
                    return false;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(getString(R.string.Save))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        menu.add(getString(R.string.Cancel))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        menu.add(getString(R.string.SyncProducts));
        menu.add(getString(R.string.Logout));
        return super.onCreateOptionsMenu(menu);
    }
}