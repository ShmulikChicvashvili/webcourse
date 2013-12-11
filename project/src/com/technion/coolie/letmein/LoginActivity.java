package com.technion.coolie.letmein;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.letmein.scapping.Scrapper;

public class LoginActivity extends CoolieActivity {

	private EditText usernameEditText;
	private EditText passwordEditText;
	private TextView getAPaswordTextView;
	private TextView privacyTextView;
	private TextView loginButton;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lmi_activity_login);

		usernameEditText = (EditText) findViewById(R.id.lmi_edit_username);
		usernameEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				updateEnabilityOfLoginButton();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		passwordEditText = (EditText) findViewById(R.id.lmi_edit_password);
		passwordEditText.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					startLogin();
				}

				return false;
			}
		});
		passwordEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				updateEnabilityOfLoginButton();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		getAPaswordTextView = (TextView) findViewById(R.id.lmi_get_a_password);
		getAPaswordTextView.setMovementMethod(LinkMovementMethod.getInstance());

		privacyTextView = (TextView) findViewById(R.id.lmi_privacy);
		privacyTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(LoginActivity.this).setTitle(R.string.lmi_privacy_title)
						.setMessage(R.string.lmi_privacy_message).show();
			}
		});

		loginButton = (Button) findViewById(R.id.lmi_login_exec_button);
		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startLogin();
			}
		});
		loginButton.setEnabled(false);
	}

	private void updateEnabilityOfLoginButton() {
		loginButton.setEnabled(!"".equals(usernameEditText.getText().toString())
				&& !"".equals(passwordEditText.getText().toString()));
	}

	private void startLogin() {
		String username = usernameEditText.getText().toString();
		String password = passwordEditText.getText().toString();

		if (isUserForgotAField(username, password)) {
			/*
			 * A toast is given to the user inside the method
			 * "isUserForgotAField".
			 */
			return;
		}

		// HACK for testing
		if (username.equals("asd") || password.equals("asd")) {
			endLogin(true);
		} else {
			(new AsyncTask<String, Void, Boolean>() {
				@Override
				protected Boolean doInBackground(String... params) {
					return Scrapper.CheckLogin(params[0], params[1]);
				}

				@Override
				protected void onPostExecute(Boolean result) {
					LoginActivity.this.endLogin(result);
				}

			}).execute(username, password);
		}

		Toast.makeText(getApplicationContext(), "Login with username " + username,
				Toast.LENGTH_SHORT).show();
	}

	private void endLogin(Boolean loginSuccess) {
		if (loginSuccess) {
			String username = usernameEditText.getText().toString();
			String password = passwordEditText.getText().toString();
			saveLoginData(username, password);

			Toast.makeText(getApplicationContext(), "Login successfull, user and password saved",
					Toast.LENGTH_SHORT).show();
			finish();
		} else {
			new AlertDialog.Builder(LoginActivity.this).setTitle(R.string.lmi_login_failed_title)
					.setMessage(R.string.lmi_login_failed_message).show();

		}
	}

	private void saveLoginData(String username, String password) {
		getSharedPreferences(Consts.PREF_FILE, Context.MODE_PRIVATE).edit()
				.putString(Consts.USERNAME, username).putString(Consts.PASSWORD, password)
				.putBoolean(Consts.IS_LOGGED_IN, true).apply();
	}

	private boolean isUserForgotAField(String username, String password) {
		if ("".equals(username)) {
			Toast.makeText(getApplicationContext(), R.string.lmi_enter_username, Toast.LENGTH_SHORT)
					.show();
		} else if ("".equals(password)) {
			Toast.makeText(getApplicationContext(), R.string.lmi_enter_password, Toast.LENGTH_SHORT)
					.show();
		} else {
			return false;
		}

		return true;
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);

		savedInstanceState.putCharSequence(String.valueOf(R.id.lmi_edit_username),
				usernameEditText.getText());
		savedInstanceState.putCharSequence(String.valueOf(R.id.lmi_edit_password),
				passwordEditText.getText());
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);

		usernameEditText.setText(savedInstanceState.getCharSequence(String
				.valueOf(R.id.lmi_edit_username)));
		passwordEditText.setText(savedInstanceState.getCharSequence(String
				.valueOf(R.id.lmi_edit_password)));
	}

}
