package com.example.lecturecode;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lecturecode.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

	private static final String SHARED_PREFERENCES_NAME = "SHARED_PREFERENCES_NAME";
	private static final String SAVED_TEXT_KEY = "SAVED_TEXT_KEY";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		setSupportActionBar(binding.toolbar);

		// Show a swipe-able message
		binding.fab.setOnClickListener(view -> Snackbar.make(view, getString(R.string.fab_message), Snackbar.LENGTH_LONG)
				.setAnchorView(R.id.fab)
				.setAction("Action", null).show());

		SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();

		EditText editTextSaved = findViewById(R.id.edit_text_saved);
		// Load text
		editTextSaved.setText(sharedPreferences.getString(SAVED_TEXT_KEY, getString(R.string.edit1)));
		// Save Text on edit
		editTextSaved.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				editor.putString(SAVED_TEXT_KEY, s.toString());
				editor.apply(); // Not blocking
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		ProgressBar progressBar = findViewById(R.id.progress_bar);
		// Clear the data stored in shared preferences
		findViewById(R.id.clear).setOnClickListener(v -> {
			progressBar.setVisibility(View.VISIBLE);
			editor.clear();
			editor.commit(); // Blocking
			progressBar.postDelayed(() -> {
				progressBar.setVisibility(View.GONE);
				Toast.makeText(this, getString(R.string.cleared), Toast.LENGTH_SHORT).show();
			}, 1500);
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if (item.getItemId() != R.id.settings_menu) return super.onOptionsItemSelected(item);

		// Show a message when the settings menu item is selected
		Toast.makeText(this, getString(R.string.settings), Toast.LENGTH_SHORT).show();
		return true;
	}
}