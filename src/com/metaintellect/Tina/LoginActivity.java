package com.metaintellect.tina;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import com.metaintellect.tina.utils.ConnectionDetector;
import com.metaintellect.tina.utils.JSONParser;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends SherlockActivity {

    public static final String CUSTOM_DOMAIN_URL = "customDomainURL";
    public static final String AUTH_TOKEN = "token";
    public static final String USER_ID = "userId";
    // public static final String ACCOUNT_FULLNAME = "accountFullName";
    // public static final String CASH_REGISTER = "cashRegister";
    public static final String CASH_REGISTER_ID = "cashRegisterId";
    SharedPreferences _settings;
    private String _loginURL;
    private EditText _usernameEditText;
    private EditText _passwordEditText;
    private EditText _customDomainURLEditText;

    private JSONParser _jsonParser = new JSONParser();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        int theme = R.style.Theme_Sherlock_Light;

        if (android.os.Build.VERSION.SDK_INT >= 15) {
            theme = R.style.Theme_Sherlock_Light_DarkActionBar;
        }

        setTheme(theme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        this._settings = PreferenceManager.getDefaultSharedPreferences(Tina.getAppContext());
        getSupportActionBar().hide();
        // actionBar.setDisplayShowTitleEnabled(false);
        // actionBar.setDisplayUseLogoEnabled(false);
        // actionBar.setDisplayShowHomeEnabled(false);

        this.checkIfInternetIsPresent();

        this.redirectIfTokenIsPersisted();

        this._usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        this._passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        this._customDomainURLEditText = (EditText) findViewById(R.id.customDomainURLEditText);

        this.setCustomDomainURLFromSharedPreferences();

        this.setGetMoreInfoText();

        this.setOnCustomDomainURLClick();

        this._customDomainURLEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            String customDomainURL = _customDomainURLEditText.getText().toString();

            if (!hasFocus && !URLUtil.isValidUrl(customDomainURL)) {
                showInvalidURLAlertDialog();
            } else {

                SharedPreferences.Editor editor = _settings.edit();
                editor.putString(CUSTOM_DOMAIN_URL, customDomainURL);
                editor.commit();
            }
            }
        });
    }

    private void redirectIfTokenIsPersisted() {

        String token = this._settings.getString(AUTH_TOKEN, null);

        if (token != null && token.length() > 0) {
            this.redirectToMainActivity();
        }
    }

    private void setCustomDomainURLFromSharedPreferences() {

        String customDomainURL = this._settings.getString(CUSTOM_DOMAIN_URL, null);

        if (customDomainURL != null && customDomainURL.length() > 0) {
            this._customDomainURLEditText.setText(customDomainURL);
            this._loginURL = customDomainURL + "/api/login";
            Log.d("API Login URL: ", this._loginURL);
        } else {
            this._customDomainURLEditText.requestFocus();
        }
    }

    public void onLoginButtonClick(View view) {
        String username = this._usernameEditText.getText().toString();
        String password = this._passwordEditText.getText().toString();

        if (this.isAccountValid(username, password)) {
            this.redirectToMainActivity();
        }
    }

    private boolean isAccountValid(String username, String password) {
        if (!this.usernameAndPasswordAreSet(username, password)) return false;
        if (this._loginURL == null || this._loginURL.length() == 0) return false;

        JSONObject credentials = this.CreteCredentialsJSONObject(username, password);

        if (credentials != null) {
            Log.d("Credentials JSON: ", credentials.toString());
            String responseJSON = _jsonParser.makeHttpRequest(this._loginURL, "POST", credentials.toString());

            try {

                JSONObject user = new JSONObject(responseJSON);
                SharedPreferences.Editor editor = _settings.edit();
                editor.putString(USER_ID, user.get(USER_ID).toString());
                editor.putString(AUTH_TOKEN, user.get(AUTH_TOKEN).toString());
                editor.putString(CASH_REGISTER_ID, user.get(CASH_REGISTER_ID).toString());
                editor.commit();

                return true;

            } catch (JSONException e) {
                Log.d("JSONObject exception: ", e.getMessage());
                return false;
            }
        }

        return false;
    }

    private boolean usernameAndPasswordAreSet(String username, String password) {
        return (username != null && username.length() > 0) &&
                (password != null && password.length() > 0);

    }

    private void setOnCustomDomainURLClick() {
        this._customDomainURLEditText.setHorizontallyScrolling(true);
        this._customDomainURLEditText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((EditText) v).setText("http://");
                ((EditText) v).setSelection(7);
            }
        });
    }

    private void redirectToMainActivity() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        LoginActivity.this.finish();
    }

    private JSONObject CreteCredentialsJSONObject(String username, String password) {

        JSONObject result = new JSONObject();

        try {
            result.put("username", username);
            result.put("password", password);

        }  catch (JSONException e) {
            //e.printStackTrace();
            Log.d("JSONObject exception: ", e.getMessage());
            return null;
        }

        return result;
    }

    private void showInvalidURLAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.InvalidURLMessage))
                .setTitle(R.string.InvalidURLTitle)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    _customDomainURLEditText.requestFocus();
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

    private void checkIfInternetIsPresent() {

        ConnectionDetector connectionDetector = new ConnectionDetector(Tina.getAppContext());

        if (!connectionDetector.isConnectingToInternet()) {
            // Internet Connection is not present
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.NoInternetMessage))
                    .setTitle(R.string.NoInternetTitle)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
            // stop executing code by return
            return;
        }
    }
}

/*
        _customDomainURLEditText.addTextChangedListener(new TextWatcher()
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