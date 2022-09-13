package com.michtech.pointofSale.Ui.more.devices.fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.esotericsoftware.kryonet.Server;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.michtech.pointofSale.R;
import com.michtech.pointofSale.database.DatabaseManager;

import java.util.Objects;

public class CreateDeviceAccount extends Fragment {
    TextView Create;
    TextInputLayout UserName, Password, ConfirmPassword;

    private String account;

    DatabaseManager db;
    public CreateDeviceAccount(String account){
        this.account = account;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_device_account, container, false);

        db = new DatabaseManager(getContext());

        Create = view.findViewById(R.id.create);
        UserName = view.findViewById(R.id.UserName);
        Password = view.findViewById(R.id.Password);
        ConfirmPassword = view.findViewById(R.id.ConfrimPassword);

        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkForm()){
                    if (checkPassword()){
                        if(!db.CheckDevice(UserName.getEditText().getText().toString())){
                            //Save goto next
                            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, new CreateAccountQrCode(account, Objects.requireNonNull(UserName.getEditText()).getText().toString(),
                                    Objects.requireNonNull(Password.getEditText()).getText().toString())).commit();
                        }else{
                            Snackbar.make(view, "Device with the same UserName already exists", Snackbar.LENGTH_LONG).setAction(null, null).show();
                        }
                    }else{
                        Snackbar.make(view, "Password does not match", Snackbar.LENGTH_LONG).setAction(null, null).show();
                    }
                }else{
                    Snackbar.make(view, "Fill all the fields", Snackbar.LENGTH_LONG).setAction(null, null).show();
                }
            }
        });

        return view;
    }
    private boolean checkForm(){
        return !Objects.requireNonNull(UserName.getEditText()).getText().toString().isEmpty() && !Objects.requireNonNull(Password.getEditText()).getText().toString().isEmpty() &&
                !Objects.requireNonNull(ConfirmPassword.getEditText()).getText().toString().isEmpty();
    }
    private boolean checkPassword(){
        return Objects.requireNonNull(Password.getEditText()).getText().toString().equals(Objects.requireNonNull(ConfirmPassword.getEditText()).getText().toString());
    }

}
