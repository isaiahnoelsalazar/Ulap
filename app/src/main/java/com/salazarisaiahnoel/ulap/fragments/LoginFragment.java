package com.salazarisaiahnoel.ulap.fragments;

import static android.content.Context.MODE_PRIVATE;
import static com.salazarisaiahnoel.ulap.MainActivity.registerFragment;

import android.content.SharedPreferences;
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

import com.github.isaiahnoelsalazar.simplefunctions.EasySQL;
import com.salazarisaiahnoel.ulap.R;

import java.util.ArrayList;
import java.util.List;

public class LoginFragment extends Fragment {

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText email = view.findViewById(R.id.emaillogin);
        EditText password = view.findViewById(R.id.passwordlogin);
        Button loginbtn = view.findViewById(R.id.loginbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
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
                    List<String> fnames = new ArrayList<>();
                    List<String> lnames = new ArrayList<>();
                    for (String s : easySQL.getTableValues("account_creds_db", "account_creds_table")){
                        if (s.split(":")[0].equals("fname")){
                            fnames.add(s.split(":")[1].substring(1, s.split(":")[1].length() - 1));
                        }
                        if (s.split(":")[0].equals("lname")){
                            lnames.add(s.split(":")[1].substring(1, s.split(":")[1].length() - 1));
                        }
                    }
                    SharedPreferences prefs = requireContext().getSharedPreferences("monay_sharedpreferences", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("user_fname", fnames.get(emails.indexOf(email.getText().toString().toLowerCase())));
                    editor.putString("user_lname", lnames.get(emails.indexOf(email.getText().toString().toLowerCase())));
                    editor.putString("user_email", email.getText().toString().toLowerCase());
                    editor.putString("user_password", password.getText().toString());
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();
                    Toast.makeText(requireContext(), "Welcome back, " + fnames.get(emails.indexOf(email.getText().toString().toLowerCase())) + " " + lnames.get(emails.indexOf(email.getText().toString().toLowerCase())) + ".", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(requireContext(), MainPage.class));
                } else {
                    Toast.makeText(requireContext(), "This account does not exist.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView registerhint = view.findViewById(R.id.registerhint);
        SpannableString spannableString = new SpannableString("Register here");
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
        registerhint.setText(spannableString);
        registerhint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, registerFragment).commit();
            }
        });
    }
}