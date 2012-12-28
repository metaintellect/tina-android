package com.metaintellect.Tina;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import com.metaintellect.Tina.models.Invoice;

public class MainActivity extends Activity {

    private Invoice invoice;

    public void onCreate(Bundle savedInstanceState) {

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

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }
}