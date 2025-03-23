package com.salazarisaiahnoel.ulap.fragments;

import static com.salazarisaiahnoel.ulap.MainActivity.loginFragment;
import static com.salazarisaiahnoel.ulap.MainActivity.registerFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.saiaaaaaaa.cod.normal.EasySQL;
import com.salazarisaiahnoel.ulap.R;

import java.util.ArrayList;
import java.util.List;

public class RegisterFragment extends Fragment {

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText firstname = view.findViewById(R.id.firstnameregister);
        EditText lastname = view.findViewById(R.id.lastnameregister);
        EditText email = view.findViewById(R.id.emailregister);
        EditText password = view.findViewById(R.id.passwordregister);
        Button registerbtn = view.findViewById(R.id.registerbtn);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasySQL easySQL = new EasySQL(requireContext());
                if (!easySQL.doesTableExist("account_creds_db", "account_creds_table")){
                    easySQL.createTable("account_creds_db", "account_creds_table", new String[]{"fname:text", "lname:text", "user_email:text", "user_password:text"});
                }
                List<String> emails = new ArrayList<>();
                for (String s : easySQL.getTableValues("account_creds_db", "account_creds_table")){
                    if (s.split(":")[0].equals("user_email")){
                        emails.add(s.split(":")[1].substring(1, s.split(":")[1].length() - 1));
                    }
                }
                if (emails.contains(email.getText().toString().toLowerCase())){
                    Toast.makeText(requireContext(), "This email address is already in use.", Toast.LENGTH_SHORT).show();
                } else {
                    easySQL.insertToTable("account_creds_db", "account_creds_table", new String[]{"fname:" + firstname.getText().toString(), "lname:" + lastname.getText().toString(), "user_email:" + email.getText().toString().toLowerCase(), "user_password:" + password.getText().toString()});
                    Toast.makeText(requireContext(), "Account created successfully.", Toast.LENGTH_SHORT).show();
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, loginFragment).commit();
                }
            }
        });

        TextView loginhint = view.findViewById(R.id.loginhint);
        SpannableString spannableString = new SpannableString("Log in here");
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
        loginhint.setText(spannableString);
        loginhint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, loginFragment).commit();
            }
        });
    }
}