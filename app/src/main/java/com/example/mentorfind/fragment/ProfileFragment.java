package com.example.mentorfind.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.mentorfind.MainActivity;
import com.example.mentorfind.R;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    CircleImageView mImg;
    private static final int RESULT_LOAD_IMG = 123;
    EditText mHobby;
    EditText mLang;
    EditText mDesc;
    ChipGroup mChipGroup;
    Button mSaveProfile;
    Uri url = null;
    EditText mName;
    private FirebaseAuth mAuth;
    private DatabaseReference mStore;
    List<String> mChipList;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mImg = view.findViewById(R.id.pro_image);
        mLang = view.findViewById(R.id.pro_lang);
        mHobby = view.findViewById(R.id.pro_hobby);
        mDesc = view.findViewById(R.id.pro_desc);
        mName = view.findViewById(R.id.pro_name);
        mChipGroup = view.findViewById(R.id.chip_c);
        mSaveProfile = view.findViewById(R.id.save_pro);
        mAuth = FirebaseAuth.getInstance();
        mChipList = new ArrayList<>();

        displayChipData(mChipList);

        mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });

        mHobby.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    String enteredHobby = mHobby.getText().toString().trim().toLowerCase();
                    if (isValidHobby(enteredHobby)) {
                        mChipList.add(enteredHobby);
                        displayChipData(mChipList);
                        mHobby.setText("");
                        return true;
                    } else {
                        // Show an alert message
                        showAlertMessage("Choose between ML, DSA, and Android");
                        return false;
                    }
                }
                return false;
            }
        });

        // Logout button
        Button btn = view.findViewById(R.id.logout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
            }
        });

        // Save profile button
        mSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = mAuth.getCurrentUser().getUid();
                String name = mName.getText().toString();
                String description = mDesc.getText().toString();
                String lang = mLang.getText().toString();
                List<String> hobbyList = mChipList;
                String imageUrl = (url != null) ? url.toString() : "";

                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

                Map<String, Object> userData = new HashMap<>();
                userData.put("name", name);
                userData.put("desc", description);
                userData.put("lang", lang);
                userData.put("hobby", hobbyList);
                userData.put("img_url", imageUrl);

                // Proceed with updating the user's profile
                usersRef.child(userId).setValue(userData, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Failed to update profile", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        return view;
    }
    private void displayChipData(List<String> mChipList) {
        mChipGroup.removeAllViews();
        for (String s : mChipList) {
            Chip chip = (Chip) this.getLayoutInflater().inflate(R.layout.single_chip_item, null, false);
            chip.setText(s);
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mChipGroup.removeView(v);
                    Chip c = (Chip) v;
                    mChipList.remove(c.getText().toString());
                }
            });
            mChipGroup.addView(chip);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMG && resultCode == getActivity().RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            url = selectedImage;
            Glide.with(this).load(selectedImage).into(mImg);
        }
    }

    private boolean isValidHobby(String hobby) {
        List<String> validHobbies = Arrays.asList("ml", "dsa", "android");
        return validHobbies.contains(hobby);
    }

    private void showAlertMessage(String message) {
        // You can implement an alert dialog to show the message to the user.
        // Here, we'll just use a toast for demonstration.
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Invalid Hobby");
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the "OK" button click (if needed)
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}