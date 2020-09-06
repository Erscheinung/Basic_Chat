package com.example.basic_chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    public void sendText(View view){
        EditText editText = findViewById(R.id.editText);
        mDatabase.push().setValue(editText.getText().toString());
        editText.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        final TextView chatText = findViewById(R.id.chatView);
        mDatabase = FirebaseDatabase.getInstance().getReference("chatMessage");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String[] chatMessages = snapshot.getValue().toString().split(",");

                chatText.setText("");

                for(int i =0; i<chatMessages.length;i++){
                    String[] Message = chatMessages[i].split("=");
                    chatText.append(Message[1]+'\n');
                }
                chatText.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                chatText.setText("cancelled");
            }
        });
    }

}