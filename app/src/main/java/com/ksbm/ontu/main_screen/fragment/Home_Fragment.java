package com.ksbm.ontu.main_screen.fragment;

import static com.ksbm.ontu.utils.Constant.Fundamental_current_item;
import static com.ksbm.ontu.utils.Utils.isNumeric;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ksbm.ontu.R;
import com.ksbm.ontu.databinding.FragmentHomeBinding;
import com.ksbm.ontu.foundation.fragment.Foundations_List_Fragment;
import com.ksbm.ontu.fundamental.fragment.Fundamental_Fragment;
import com.ksbm.ontu.main_screen.DrawerActivity;
import com.ksbm.ontu.main_screen.MainActivity;
import com.ksbm.ontu.main_screen.YoutubeVideoA;
import com.ksbm.ontu.main_screen.fragment.RelateToFragment_OnBack.RootFragment;
import com.ksbm.ontu.personality_dev.Personality_Fragment;
import com.ksbm.ontu.practice_offline.fragment.Fragment_Offline_Stage;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.situation.fragment.Situation_Fragment;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.MeraSharedPreferance;
import com.ksbm.ontu.utils.Utils;

import java.util.Locale;

public class Home_Fragment extends RootFragment implements
        TextToSpeech.OnInitListener {
    FragmentHomeBinding binding;
    SessionManager sessionManager;
    TextToSpeech tts;

    MeraSharedPreferance meraSharedPreferance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.fragment_home, null);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        tts = new TextToSpeech((Activity)getActivity(), this);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View view = binding.getRoot();//using data binding
        sessionManager = new SessionManager(getActivity());
        meraSharedPreferance = MeraSharedPreferance.getInstance(getActivity());

        try {
            ((MainActivity) getActivity()).removeBottomNavigationLabels(0);
            ((MainActivity) getActivity()).CheckBottom(0);
        } catch (Exception e) {
        }


        try {
            Log.d("onNextonNext",""+sessionManager.getUser().getRoleid());
            if (sessionManager.getUser().getRoleid().equalsIgnoreCase(Constant.studend_role_id)) {
                if (sessionManager.getUser().getClassname() != null
                        && !sessionManager.getUser().getClassname().equalsIgnoreCase("")) {
                    if (isNumeric(sessionManager.getUser().getClassname())) {
                        //fundamental course
                        binding.cardCrashCourse.setVisibility(View.GONE);
                        binding.cardFoundation.setVisibility(View.GONE);
                        binding.cardHavefun.setVisibility(View.GONE);
                        binding.cardFundamental.setVisibility(View.VISIBLE);
                        binding.cardSituation.setVisibility(View.VISIBLE);
                    } else {
                        //kids learning course
                        binding.cardCrashCourse.setVisibility(View.GONE);
                        binding.cardFundamental.setVisibility(View.GONE);
                        binding.cardSituation.setVisibility(View.GONE);
                        binding.cardFoundation.setVisibility(View.VISIBLE);
                        binding.cardHavefun.setVisibility(View.VISIBLE);
                    }

                } else {
                    //crash course
                    binding.cardCrashCourse.setVisibility(View.VISIBLE);
                    binding.cardFundamental.setVisibility(View.VISIBLE);
                    binding.cardFoundation.setVisibility(View.GONE);
                    binding.cardSituation.setVisibility(View.GONE);
                    binding.cardHavefun.setVisibility(View.GONE);
                }
            } else if (sessionManager.getUser().getRoleid().equalsIgnoreCase(Constant.individual_role_id)){
                //kids learning
                if (meraSharedPreferance.isOpenMarketGet()){
                    if (meraSharedPreferance.professionGet().equalsIgnoreCase("School Students")){
                        if (meraSharedPreferance.ageGet().equalsIgnoreCase("Below 13")){
                            binding.cardCrashCourse.setVisibility(View.GONE);
                            binding.cardFundamental.setVisibility(View.GONE);
                            binding.cardSituation.setVisibility(View.GONE);
                            binding.cardFoundation.setVisibility(View.VISIBLE);
                            binding.cardHavefun.setVisibility(View.VISIBLE);

                        }else if (meraSharedPreferance.ageGet().equalsIgnoreCase("Above 13")){

                            binding.cardCrashCourse.setVisibility(View.VISIBLE);
                            binding.cardFundamental.setVisibility(View.VISIBLE);
                            binding.cardSituation.setVisibility(View.GONE);
                            binding.cardFoundation.setVisibility(View.GONE);
                            binding.cardHavefun.setVisibility(View.GONE);
                        }

                    }else {
                        binding.cardCrashCourse.setVisibility(View.VISIBLE);
                        binding.cardFundamental.setVisibility(View.VISIBLE);
                        binding.cardSituation.setVisibility(View.GONE);
                        binding.cardFoundation.setVisibility(View.GONE);
                        binding.cardHavefun.setVisibility(View.GONE);
                    }
                }

            }else {
                //crash course
                binding.cardCrashCourse.setVisibility(View.VISIBLE);
                binding.cardFundamental.setVisibility(View.VISIBLE);
                binding.cardFoundation.setVisibility(View.GONE);
                binding.cardSituation.setVisibility(View.GONE);
                binding.cardHavefun.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            //crash course
            binding.cardCrashCourse.setVisibility(View.VISIBLE);
            binding.cardFundamental.setVisibility(View.VISIBLE);
            binding.cardFoundation.setVisibility(View.GONE);
            binding.cardSituation.setVisibility(View.GONE);
            binding.cardHavefun.setVisibility(View.GONE);
        }




        binding.ivCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), YoutubeVideoA.class));
//                Utils.screenShot(binding.frameFrag, getActivity());
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("https://www.youtube.com/"));
//                intent.setPackage("com.google.android.youtube");
//                PackageManager manager = getActivity().getPackageManager();
//                List<ResolveInfo> info = manager.queryIntentActivities(intent, 0);
//                if (info.size() > 0) {
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(getActivity(), "Youtube not installed", Toast.LENGTH_SHORT).show();
//                    //No Application can handle your intent
//                }
            }
        });


        binding.cardFundamental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fundamental_current_item = 0;
                Fragment fragment_home = new Fundamental_Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("Title", binding.tvFundamentalTitle.getText().toString());
                FragmentTransaction ft_home = getActivity().getSupportFragmentManager().beginTransaction();
                ft_home.replace(R.id.frame, fragment_home);
                ft_home.addToBackStack(null);
                ft_home.commit();
                fragment_home.setArguments(bundle);
                speakOut(binding.tvFundamentalTitle.getText().toString());

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
                speakOut(binding.tvFoundationTitle.getText().toString());
            }
        });

        binding.cardCrashCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment_home = new CrashCourse_Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("Title", binding.tvCrashcourseTitle.getText().toString());
                FragmentTransaction ft_home = getActivity().getSupportFragmentManager().beginTransaction();
                ft_home.replace(R.id.frame, fragment_home);
                ft_home.addToBackStack(null);
                ft_home.commit();
                fragment_home.setArguments(bundle);
                speakOut(binding.tvCrashcourseTitle.getText().toString());
            }
        });

        binding.cardSituation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.havefun = false;
                Fragment fragment_home = new Situation_Fragment();
                FragmentTransaction ft_home = getActivity().getSupportFragmentManager().beginTransaction();
                ft_home.replace(R.id.frame, fragment_home);
                ft_home.addToBackStack(null);
                ft_home.commit();
                speakOut("Situation");
            }
        });

        binding.cardPersonality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment_home = new Personality_Fragment();
                FragmentTransaction ft_home = getActivity().getSupportFragmentManager().beginTransaction();
                ft_home.replace(R.id.frame, fragment_home);
                ft_home.addToBackStack(null);
                ft_home.commit();
                speakOut("Personality Development");
            }
        });

        binding.cardPracticeOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment_home = new Fragment_Offline_Stage();
                FragmentTransaction ft_home = getActivity().getSupportFragmentManager().beginTransaction();
                ft_home.replace(R.id.frame, fragment_home);
                ft_home.addToBackStack(null);
                ft_home.commit();
                speakOut("Practice Speaking");
            }
        });

        binding.cardHavefun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Toast.makeText(getActivity(), "Coming soon...", Toast.LENGTH_SHORT).show();
                Constant.havefun = true;
                Fragment fragment_home = new Situation_Fragment();
                FragmentTransaction ft_home = getActivity().getSupportFragmentManager().beginTransaction();
                ft_home.replace(R.id.frame, fragment_home);
                ft_home.addToBackStack(null);
                ft_home.commit();
                speakOut("Have Fun");
            }
        });

        binding.ivSettingMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SideBarFragment sideBarFragment = new SideBarFragment();
//                sideBarFragment.show(getChildFragmentManager(), "");

                Intent intent = new Intent(getActivity(), DrawerActivity.class);
                startActivity(intent);


//                Fragment fragment_home = new SideBarFragment();
//                Bundle bundle = new Bundle();
////                bundle.putString("Title", binding.tvFundamentalTitle.getText().toString());
//                FragmentTransaction ft_home = getActivity().getSupportFragmentManager().beginTransaction();
//                ft_home.replace(R.id.frame, fragment_home);
//                ft_home.addToBackStack(null);
//                ft_home.commit();
//                fragment_home.setArguments(bundle);

            }
        });


        return view;
    }

    private void speakOut(String txt) {
        tts.speak(txt, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(new Locale("en", "IN"));

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {

            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }
}
