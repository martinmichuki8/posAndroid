package com.michtech.pointofSale.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.fragments.Home;
import com.michtech.pointofSale.fragments.More;
import com.michtech.pointofSale.fragments.Store;
import com.michtech.pointofSale.fragments.employeeDevice.DeviceHome;

import java.util.ArrayList;

public class DashboardAdapter extends FragmentStatePagerAdapter {
    private int COUNT = 3;
    DatabaseManager db;
    Context context;
    ArrayList<String> pages = new ArrayList<>();
    public DashboardAdapter(@NonNull FragmentManager fragmentManager, Context context){
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        db = new DatabaseManager(context);
        Fragment fragment = null;
        switch (position){
            case 0:
                if(db.getAccountType().equals("Admin")){
                    fragment = new Home();
                }else{
                    fragment = new DeviceHome();
                }
                break;
            case 1:
                fragment = new Store();
                break;
            case 2:
                fragment = new More();
        }
        assert fragment != null;
        return fragment;
    }

    @Override
    public int getCount() {
        return COUNT;
    }
    public void add(){
        pages.add("Dashboard");
        pages.add("Store");
        pages.add("More");
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position){
        return pages.get(position);
    }
}
