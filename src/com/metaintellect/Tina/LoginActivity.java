package com.metaintellect.Tina;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.preference.PreferenceManager;
import com.metaintellect.Tina.utils.Tina;

public class LoginActivity extends Activity {

    String CUSTOM_DOMAIN_URL = "customDomainURL";
    SharedPreferences settings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        settings = PreferenceManager.getDefaultSharedPreferences(Tina.getAppContext());

        ActionBar actionBar = getActionBar();
        actionBar.hide();
        // actionBar.setDisplayShowTitleEnabled(false);
        // actionBar.setDisplayUseLogoEnabled(false);
        // actionBar.setDisplayShowHomeEnabled(false);

        final Button loginButton = (Button) findViewById(R.id.loginButton);
        final EditText customDomainURLEditText = (EditText) findViewById(R.id.customDomainURLEditText);

        this.setCustomDomainURLFromSharedPreferences(customDomainURLEditText);

        setGetMoreInfoText();

        setOnCustomDomainURLClick(customDomainURLEditText);

        customDomainURLEditText.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                String customDomainURL = customDomainURLEditText.getText().toString();

                if (!hasFocus && !URLUtil.isValidUrl(customDomainURL)) {
                        showInvalidURLAlertDialog(customDomainURLEditText);
                } else {

                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString(CUSTOM_DOMAIN_URL, customDomainURL);
                    editor.commit();
                }
            }
        });
    }

    private void setCustomDomainURLFromSharedPreferences(EditText customDomainURLEditText) {

        String customDomainURL = settings.getString(CUSTOM_DOMAIN_URL, null);

        if (customDomainURL != null && customDomainURL.length() > 0) {
            customDomainURLEditText.setText(customDomainURL);
        } else {
            customDomainURLEditText.requestFocus();
        }
    }


    public void onLoginButtonClick(View view) {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        LoginActivity.this.finish();
    }

    private void setOnCustomDomainURLClick(final EditText customDomainURLEditText) {
        customDomainURLEditText.setHorizontallyScrolling(true);
        customDomainURLEditText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((EditText) v).setText("http://");
                ((EditText) v).setSelection(7);
            }
        });
    }

    private void showInvalidURLAlertDialog(final EditText customDomainURLEditText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.InvalidURLMessage))
                .setTitle(R.string.InvalidURLTitle)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        customDomainURLEditText.requestFocus();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void setGetMoreInfoText() {
        final TextView moreInfoTextView = (TextView) findViewById(R.id.moreInfoTextView);
        moreInfoTextView.setText(Html.fromHtml("<a href=\"http://www.mali-zeleni.hr\">Get More Info</a>"));
        moreInfoTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }


}

/*
        customDomainURLEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

 */