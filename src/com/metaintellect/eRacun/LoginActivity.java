package com.metaintellect.eRacun;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        ActionBar actionBar = getActionBar();
        setVisible(false);
        // actionBar.setDisplayShowTitleEnabled(false);
        // actionBar.setDisplayUseLogoEnabled(false);
        // actionBar.setDisplayShowHomeEnabled(false);

        TextView moreInfoTextView = (TextView) findViewById(R.id.moreInfoTextView);
        moreInfoTextView.setText(Html.fromHtml("<a href=\"http://www.mali-zeleni.hr\">Get More Info</a>"));
        moreInfoTextView.setMovementMethod(LinkMovementMethod.getInstance());

        TextView customDomainURLEditText = (TextView) findViewById(R.id.customDomainURLEditText);
        customDomainURLEditText.setHorizontallyScrolling(true);
        customDomainURLEditText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((EditText) v).setText("http://");
                ((EditText) v).setSelection(7);
            }
        });
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
                if (customDomainURLEditText.getText() != null
                    || customDomainURLEditText.getText().length() == 0)  {
                    customDomainURLEditText.setText("http://");
                    customDomainURLEditText.append("current_this_edittext_string");
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });

 */