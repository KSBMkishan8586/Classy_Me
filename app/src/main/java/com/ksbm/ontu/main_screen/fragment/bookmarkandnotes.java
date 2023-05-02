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
import com.ksbm.ontu.alerts_module.CalendarNotesListActivity;
import com.ksbm.ontu.databinding.FragmentBookmarkandnotesBinding;
import com.ksbm.ontu.foundation.fragment.Foundations_List_Fragment;
import com.ksbm.ontu.main_screen.DrawerActivity;
import com.ksbm.ontu.main_screen.MainActivity;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.MeraSharedPreferance;
import com.ksbm.ontu.utils.Utils;

public class bookmarkandnotes extends Fragment {

    FragmentBookmarkandnotesBinding binding;

    SessionManager sessionManager;


    MeraSharedPreferance meraSharedPreferance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bookmarkandnotes, container, false);
        View view = binding.getRoot();//using data binding
        sessionManager = new SessionManager(getActivity());
        meraSharedPreferance = MeraSharedPreferance.getInstance(getActivity());

        binding.ivSettingMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getContext(), DrawerActivity.class);
                startActivity(intent);

            }
        });

        binding.buttonBookmark.setOnClickListener(view1 -> {
            Fragment fragment_home = new Foundations_List_Fragment();
            Bundle bundle = new Bundle();
            bundle.putString("Title", "Book Mark");
            FragmentTransaction ft_home = getActivity().getSupportFragmentManager().beginTransaction();
            ft_home.replace(R.id.frame, fragment_home);
            ft_home.addToBackStack(null);
            ft_home.commit();
            fragment_home.setArguments(bundle);
        });

        binding.ivCoin.setOnClickListener(view12 -> {
            Utils.screenShot(binding.frameFrag, getView().getContext());
        });

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        binding.notes.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), CalendarNotesListActivity.class);
            startActivity(intent);
        });
        return view;


    }

    public void onBackPressed(){
        finish();
    }

    private void finish() {

    }

}