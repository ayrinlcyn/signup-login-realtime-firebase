package com.example.signuploginrealtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileActivity extends AppCompatActivity {

    EditText edit_name, edit_email, edit_username, edit_password;
    Button save_button;
    String nameUser, emailUser, usernameUser, passwordUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        reference = FirebaseDatabase.getInstance().getReference("users");

        edit_name = findViewById(R.id.edit_name);
        edit_email = findViewById(R.id.edit_email);
        edit_username = findViewById(R.id.edit_username);
        edit_password = findViewById(R.id.edit_password);
        save_button = findViewById(R.id.save_button);

        showData();

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    public void saveData() {
        String newName = edit_name.getText().toString();
        String newEmail = edit_email.getText().toString();
        String newUsername = edit_username.getText().toString();
        String newPassword = edit_password.getText().toString();

        HelperClass helperClass = new HelperClass(newName, newEmail, newUsername, newPassword);

        if (!usernameUser.equals(newUsername)) {
            reference.child(usernameUser).removeValue();
        }

        reference.child(newUsername).setValue(helperClass).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(EditProfileActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                intent.putExtra("name", newName);
                intent.putExtra("email", newEmail);
                intent.putExtra("username", newUsername);
                intent.putExtra("password", newPassword);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(EditProfileActivity.this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showData() {
        Intent intent = getIntent();

        nameUser = intent.getStringExtra("name");
        emailUser = intent.getStringExtra("email");
        usernameUser = intent.getStringExtra("username");
        passwordUser = intent.getStringExtra("password");

        edit_name.setText(nameUser);
        edit_email.setText(emailUser);
        edit_username.setText(usernameUser);
        edit_password.setText(passwordUser);
    }
}
