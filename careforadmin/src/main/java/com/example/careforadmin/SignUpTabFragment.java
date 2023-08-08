package com.example.careforadmin;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class SignUpTabFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://carefor-3f2dc-default-rtdb.firebaseio.com");
    TextView dateformat;
    int year, month, day;
    int getYear;

//    ProgressBar progressBar;
    ProgressBar progressBar2;
    int counter = 0;
    String savegender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancesState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_register, container,false);

        dateformat = root.findViewById(R.id.dob);
        final Calendar calendar = Calendar.getInstance();
        dateformat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendar.get(Calendar.YEAR);
                getYear = year;
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayofMonth) {
                        dateformat.setText(dayofMonth + "/" + (month + 1) + "/" + year);
                    }
                },year, month, day);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()-6570* 24 * 60 * 60 * 1000l);
                datePickerDialog.show();

            }
        });

        Spinner spin = root.findViewById(R.id.gdspinner);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter ad
                = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, gender);

        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        ad.setDropDownViewResource(R.layout.list_spinner);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spin.setAdapter(ad);



        final EditText fullname = root.findViewById(R.id.fullname);
        final EditText username = root.findViewById(R.id.username);
        final EditText email = root.findViewById(R.id.email);
        final EditText phone = root.findViewById(R.id.phone);
        final EditText password = root.findViewById(R.id.password);
        final EditText confirm_password = root.findViewById(R.id.confirm_password);
        final Spinner genspinner = root.findViewById(R.id.gdspinner);

        final Button registeration = root.findViewById(R.id.signup);
//        progressBar = root.findViewById(R.id.progressBar);
        progressBar2 = root.findViewById(R.id.progressBar2);

        registeration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected()) {
                    //get data from EditTexts into String variables
                    final String name = fullname.getText().toString();
                    final String usern = username.getText().toString();
                    final String emailaddress = email.getText().toString();
                    final String phone_number = phone.getText().toString();
                    final String dateofBirth = dateformat.getText().toString();
                    final String pass = password.getText().toString();
                    final String confirm_pass = confirm_password.getText().toString();


//                progressBar.setVisibility(View.VISIBLE);
//                progressBar2.setVisibility(View.VISIBLE);
//
//                Timer timer = new Timer();
//                TimerTask timerTask = new TimerTask() {
//                    @Override
//                    public void run() {
//                        counter++;
//                        progressBar.setProgress(counter);
//                        progressBar2.setProgress(counter);
//
//                        if (counter == 100)
//                        {
//                            timer.cancel();
//
//                        }
//                    }
//                };

                    //check if user fill al the fields correctly before sending data to firebase database
                    if (name.isEmpty()) {
                        fullname.setError("Kindly Enter FullName");
                    } else if (usern.isEmpty()) {
                        username.setError("Kindly Enter Username");
                    } else if (emailaddress.isEmpty()) {
                        email.setError("Kindly Enter Email Address");
                    } else if (phone_number.isEmpty()) {
                        phone.setError("Kindly Enter Phone Number");
                    } else if (dateofBirth.isEmpty()) {
                        Toast.makeText(getActivity(), "Select Date", Toast.LENGTH_SHORT).show();
                    }else if(savegender.equals("Please Select Gender")){
                        Toast.makeText(getActivity(),"Please Select Gender",Toast.LENGTH_SHORT);
                    } else if (pass.isEmpty()) {
                        password.setError("Kindly Enter Password");
                    } else if (confirm_pass.isEmpty()) {
                        confirm_password.setError("Kindly Re-enter Password");
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(emailaddress).matches()) {
                        email.setError("Please provide valid email address");
                        email.requestFocus();
                    } else if (!name.matches("^[a-zA-Z]{3,}( {1,2}[a-zA-Z]{3,}){0,}$")) {
                        fullname.setError("Full Name can only contain lower and uppercase letter and spaces");
                    } else if (!usern.matches("^[a-zA-Z0-9]([_-](?![_-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$")) {
                        username.setError("Username should contain 5-20 characters, including characters, numbers, underscore (_) or hyphen(-)");
                        username.requestFocus();
                    } else if (!phone_number.matches("^03[0-9]{2}-[0-9]{7}$")) {
                        phone.setError("Please enter valid Contact Number");
                    } else if (password.length() < 6) {
                        password.setError("Password length should be greater than 6 characters!");
                        password.requestFocus();
//                }else if (!pass.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$")){
//                    password.setError("Password should contain at least eight characters, including at least one number and includes both lower and uppercase letters and special characters");
//                    password.requestFocus();
                    } else if (!pass.equals(confirm_pass)) {
                        confirm_password.setError("Passwords do not match");
                        confirm_password.requestFocus();
                    } else {

                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                        final String encryptpass = md5(pass);
                            if (!(getYear >= 2004)) {
                                System.out.println("The Current year is: "+getYear);
                                Toast.makeText(getActivity(), "Caregiver has to be of Age 18 or above to register for CareFor", Toast.LENGTH_SHORT).show();
                            } else {
//                            progressBar.setVisibility(View.VISIBLE);
                                progressBar2.setVisibility(View.VISIBLE);

                                Timer timer = new Timer();
                                TimerTask timerTask = new TimerTask() {
                                    @Override
                                    public void run() {
                                        counter++;
//                                    progressBar.setProgress(counter);
                                        progressBar2.setProgress(counter);

                                        if (counter == 100) {
                                            timer.cancel();

                                            databaseReference.child("Caregiver").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                                                    //if the username is not registered before
                                                    if (snapshot.hasChild(usern)) {
                                                        Toast.makeText(getActivity(), "Username already exists", Toast.LENGTH_SHORT).show();
                                                        username.requestFocus();
//                                                    progressBar.setProgress(0);
//                                                    progressBar.setVisibility(View.INVISIBLE);
                                                        progressBar2.setProgress(0);
                                                        progressBar2.setVisibility(View.INVISIBLE);
                                                    } else {
                                                        firebaseAuth.createUserWithEmailAndPassword(emailaddress, pass).addOnCompleteListener((task) -> {
                                                            if (task.isSuccessful()) {
                                                                firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            //sending data to realtime database
                                                                            //we are using username as unique identity of every user
                                                                            databaseReference.child("Caregiver").child(usern).child("Full_Name").setValue(name);
                                                                            databaseReference.child("Caregiver").child(usern).child("Email_Address").setValue(emailaddress);
                                                                            databaseReference.child("Caregiver").child(usern).child("User_Name").setValue(usern);
                                                                            databaseReference.child("Caregiver").child(usern).child("Phone_Number").setValue(phone_number);
                                                                            databaseReference.child("Caregiver").child(usern).child("Date_OF_Birth").setValue(dateofBirth);
                                                                            databaseReference.child("Caregiver").child(usern).child("Gender").setValue(savegender);
                                                                            //databaseReference.child("Caregiver").child(usern).child("Status").setValue("Nothing");

//                                                                        databaseReference.child("Caregiver").child(usern).child("password").setValue(encryptpass);
                                                                            Toast.makeText(getActivity(), "User Successfully Registered. Please Verify Email", Toast.LENGTH_SHORT).show();
                                                                            Intent i = new Intent(getActivity(), MainActivity.class);
                                                                            startActivity(i);

                                                                        } else {
                                                                            progressBar2.setProgress(0);
                                                                            progressBar2.setVisibility(View.INVISIBLE);
                                                                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });
                                                            } else {
                                                                progressBar2.setProgress(0);
                                                                progressBar2.setVisibility(View.INVISIBLE);
                                                                Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }
                                    }
                                };
                                timer.schedule(timerTask, 100, 100);


//                            databaseReference.child("Caregiver").addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                    //if the username is not registered before
//                                    if (snapshot.hasChild(usern)) {
//                                        Toast.makeText(RegistrationActvity.this, "Email Address already exists", Toast.LENGTH_SHORT).show();
//                                    } else {
//                                        //sending data to realtime database
//                                        //we are using email address as unique identity of every user
//                                        databaseReference.child("Caregiver").child(usern).child("Full_Name").setValue(name);
//                                        databaseReference.child("Caregiver").child(usern).child("Email_Address").setValue(emailaddress);
//                                        databaseReference.child("Caregiver").child(usern).child("User_Name").setValue(usern);
//                                        databaseReference.child("Caregiver").child(usern).child("Phone_Number").setValue(phone_number);
//                                        databaseReference.child("Caregiver").child(usern).child("Date_OF_Birth").setValue(dateofBirth);
//                                        databaseReference.child("Caregiver").child(usern).child("password").setValue(encryptpass);
//
//                                        //Finish Activity after successful registration.
//                                        Toast.makeText(RegistrationActvity.this, "Account created Successfully", Toast.LENGTH_SHORT).show();
//                                        Intent i = new Intent(RegistrationActvity.this, LoginActivity.class);
//                                        startActivity(i);
//                                        finish();
//                                    }
//
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                }
//                            });

                            }

                    }
                }else{
                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }


            }
        });

        return root;
    }

    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }

    // create array of Strings
    // and store name of courses
    String[] gender = { "Please Select Gender","Female", "Male"};

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        savegender = gender[i];

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(getActivity(),
                "Please select gender",
                Toast.LENGTH_SHORT)
                .show();

    }


//    public String md5(String s) {
//        try {
//            // Create MD5 Hash
//            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
//            digest.update(s.getBytes());
//            byte messageDigest[] = digest.digest();
//            // Create Hex String
//            StringBuffer hexString = new StringBuffer();
//            for (int i=0; i<messageDigest.length; i++)
//                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
//            return hexString.toString();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }


}
