package com.ksbm.ontu.main_screen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ksbm.ontu.R;
import com.ksbm.ontu.databinding.FragmentCrashcourseBinding;
import com.ksbm.ontu.foundation.fragment.Foundations_List_Fragment;
import com.ksbm.ontu.main_screen.DrawerActivity;
import com.ksbm.ontu.main_screen.MainActivity;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.situation.fragment.Situation_Fragment;
import com.ksbm.ontu.utils.Utils;

public class CrashCourse_Fragment extends Fragment {
    FragmentCrashcourseBinding binding;
    SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.fragment_home, null);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_crashcourse, container, false);
        View view = binding.getRoot();//using data binding
        sessionManager = new SessionManager(getActivity());

        if (getArguments() != null) {
            String Title = getArguments().getString("Title");
            binding.tvTitle.setText(Title);

        }
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).onBackPressed();
            }
        });

        binding.cardFoundation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment_home = new Foundations_List_Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("Title", binding.tvFoundationTitle.getText().toString());
                FragmentTransaction ft_home = getActivity().getSupportFragmentManager().beginTransaction();
                ft_home.replace(R.id.frame, fragment_home);
                ft_home.addToBackStack(null);
                ft_home.commit();
                fragment_home.setArguments(bundle);
            }
        });

        binding.cardSituation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment_home = new Situation_Fragment();
                FragmentTransaction ft_home = getActivity().getSupportFragmentManager().beginTransaction();
                ft_home.replace(R.id.frame, fragment_home);
                ft_home.addToBackStack(null);
                ft_home.commit();

            }
        });

        binding.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SideBarFragment blankFragment = new SideBarFragment();
//                blankFragment.show(getActivity().getSupportFragmentManager(),blankFragment.getTag());

                Intent intent = new Intent(getActivity(), DrawerActivity.class);
                startActivity(intent);

            }
        });

        return view;

    }
}
