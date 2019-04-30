package com.example.prog.bloodshare.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.prog.bloodshare.Activities.MyChatRooms;
import com.example.prog.bloodshare.R;
import com.example.prog.bloodshare.utils.Session;

import butterknife.Bind;
import butterknife.ButterKnife;


public class NavegationDrawerFragment extends Fragment {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    @Bind(R.id.LogOut)
    LinearLayout mLogout ;
    @Bind(R.id.ChatRooms)
    LinearLayout mChatRooms;
    public NavegationDrawerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_navegation_drawer , container);
        ButterKnife.bind(this, v);
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session.getInstance().logoutAndGoToLogin(getActivity());
            }
        });
        mChatRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , MyChatRooms.class);
                startActivity(intent);
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        return v;
    }

    public void setUp(DrawerLayout drawerLayout , Toolbar toolbar) {
        mDrawerLayout = drawerLayout ;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity() , drawerLayout , toolbar , R.string.drawer_open , R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                getActivity().findViewById(R.id.fab).setTranslationX(slideOffset*200);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }
}
