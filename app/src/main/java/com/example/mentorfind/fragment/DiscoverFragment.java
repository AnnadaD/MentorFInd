package com.example.mentorfind.fragment;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.mentorfind.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DiscoverFragment extends Fragment {

    private EditText editTextLang;
    private Button buttonFetchData;
    private TextView textViewResult;

    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        editTextLang = view.findViewById(R.id.editTextLang);
        buttonFetchData = view.findViewById(R.id.buttonFetchData);
        textViewResult = view.findViewById(R.id.textViewResult);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        buttonFetchData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchAllUsers();
            }
        });
    }

    private void fetchAllUsers() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    StringBuilder resultText = new StringBuilder();
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String name = userSnapshot.child("name").getValue(String.class);
                        String desc = userSnapshot.child("desc").getValue(String.class);
                        String hobby = userSnapshot.child("hobby").child("0").getValue(String.class);
                        // Modify as needed based on your actual data structure

                        resultText.append("Name: ").append(name).append("\n");
                        resultText.append("Description: ").append(desc).append("\n");
                        resultText.append("Interests: ").append(hobby).append("\n\n");
                    }
                    textViewResult.setText(resultText.toString());
                } else {
                    textViewResult.setText("No users in the database");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                textViewResult.setText("Error fetching data");
            }
        });
    }
}
