package com.ksbm.ontu.practice_offline.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.FragmentOfflineLevelBinding;
import com.ksbm.ontu.foundation.activity.FoundationWinnerActivity;
import com.ksbm.ontu.main_screen.MainActivity;
import com.ksbm.ontu.practice_offline.activity.OfflineQuizActivity;
import com.ksbm.ontu.practice_offline.model.OfflineLevelList;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class Fragment_Offline_Level extends Fragment {
    FragmentOfflineLevelBinding binding;
    SessionManager sessionManager;
    private String StageId, StageName, StageImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.fragment_home, null);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_offline_level, container, false);
        View view = binding.getRoot();//using data binding
        sessionManager = new SessionManager(getActivity());




        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).onBackPressed();
            }
        });

        if (getArguments() != null) {
            StageId = getArguments().getString("StageId");
            StageName = getArguments().getString("StageName");
            StageImage = getArguments().getString("StageImage");

            binding.tvStageName.setText("Stage "+ StageName);

            if (StageImage!=null && !StageImage.isEmpty()){
                new LoadBackground(StageImage, "androidfigure").execute();
            }

        }

        if (Connectivity.isConnected(getActivity())) {
            AllLevelList(StageId);
        } else {
            //SweetAlt.showErrorMessage(getActivity(), "Sorry", "You have no internet!");
        }

        return view;
    }

    @SuppressLint("CheckResult")
    private void AllLevelList(String StageId) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetLevelList(StageId, sessionManager.getUser().getUserid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<OfflineLevelList>() {
                    @Override
                    public void onNext(OfflineLevelList response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getStage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                ClickLevel_open_quiz(response.getStage().get(0).getLevelData());

                            } else {
                                SweetAlt.showErrorMessage(getActivity(), "Sorry", response.getMessage());

                            }
                            //  binding.swipeToRefresh.setRefreshing(false);

                        } catch (Exception e) {
                            progressDialog.dismiss();
                        }

                        //  binding.swipeToRefresh.setRefreshing(false);

                    }

                    @Override
                    public void onError(Throwable e) {
                        //Handle error
                        progressDialog.dismiss();
                        Log.e("mr_product_error", e.toString());

                        if (e instanceof HttpException) {
                            int code = ((HttpException) e).code();
                            switch (code) {
                                case 403:
                                    break;
                                case 404:
                                    //Toast.makeText(EmailSignupActivity.this, R.string.email_already_use, Toast.LENGTH_SHORT).show();
                                    break;
                                case 409:
                                    break;
                                default:
                                    // Toast.makeText(EmailSignupActivity.this, R.string.network_failure, Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } else {
                            if (TextUtils.isEmpty(e.getMessage())) {
                                // Toast.makeText(EmailSignupActivity.this, R.string.network_failure, Toast.LENGTH_SHORT).show();
                            } else {
                                //Toast.makeText(EmailSignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        progressDialog.dismiss();
                    }
                });

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void ClickLevel_open_quiz(List<OfflineLevelList.LevelData> levelData) {



        if (levelData != null) {

            if (levelData.get(0).getLevel_play().equalsIgnoreCase("false")){
                if (sessionManager.getUser().getProfilePic() != null && !sessionManager.getUser().getProfilePic().isEmpty()) {
                    Glide.with(this)
                            .load(sessionManager.getUser().getProfilePic())
                            .error(R.drawable.man)
                            .placeholder(R.drawable.man)
                            .into(binding.lp1);
                }
                binding.lp1.setVisibility(View.VISIBLE);
                binding.lp2.setVisibility(View.GONE);
                binding.lp3.setVisibility(View.GONE);
                binding.lp4.setVisibility(View.GONE);
                binding.lp5.setVisibility(View.GONE);
                binding.lp6.setVisibility(View.GONE);
                binding.lpideal.setVisibility(View.GONE);
            }else {
                if (levelData.get(1).getLevel_play().equalsIgnoreCase("false")){
                    if (sessionManager.getUser().getProfilePic() != null && !sessionManager.getUser().getProfilePic().isEmpty()) {
                        Glide.with(this)
                                .load(sessionManager.getUser().getProfilePic())
                                .error(R.drawable.man)
                                .placeholder(R.drawable.man)
                                .into(binding.lp2);
                    }
                    binding.lp1.setVisibility(View.GONE);
                    binding.lp2.setVisibility(View.VISIBLE);
                    binding.lp3.setVisibility(View.GONE);
                    binding.lp4.setVisibility(View.GONE);
                    binding.lp5.setVisibility(View.GONE);
                    binding.lp6.setVisibility(View.GONE);
                    binding.lpideal.setVisibility(View.GONE);
                } else {
                    if (levelData.get(2).getLevel_play().equalsIgnoreCase("false")){
                        if (sessionManager.getUser().getProfilePic() != null && !sessionManager.getUser().getProfilePic().isEmpty()) {
                            Glide.with(this)
                                    .load(sessionManager.getUser().getProfilePic())
                                    .error(R.drawable.man)
                                    .placeholder(R.drawable.man)
                                    .into(binding.lp3);
                        }
                        binding.lp1.setVisibility(View.GONE);
                        binding.lp2.setVisibility(View.GONE);
                        binding.lp3.setVisibility(View.VISIBLE);
                        binding.lp4.setVisibility(View.GONE);
                        binding.lp5.setVisibility(View.GONE);
                        binding.lp6.setVisibility(View.GONE);
                        binding.lpideal.setVisibility(View.GONE);
                    }else {
                        if (levelData.get(3).getLevel_play().equalsIgnoreCase("false")){
                            if (sessionManager.getUser().getProfilePic() != null && !sessionManager.getUser().getProfilePic().isEmpty()) {
                                Glide.with(this)
                                        .load(sessionManager.getUser().getProfilePic())
                                        .error(R.drawable.man)
                                        .placeholder(R.drawable.man)
                                        .into(binding.lp4);
                            }
                            binding.lp1.setVisibility(View.GONE);
                            binding.lp2.setVisibility(View.GONE);
                            binding.lp3.setVisibility(View.GONE);
                            binding.lp4.setVisibility(View.VISIBLE);
                            binding.lp5.setVisibility(View.GONE);
                            binding.lp6.setVisibility(View.GONE);
                            binding.lpideal.setVisibility(View.GONE);
                        }else {
                            if (levelData.get(4).getLevel_play().equalsIgnoreCase("false")){
                                if (sessionManager.getUser().getProfilePic() != null && !sessionManager.getUser().getProfilePic().isEmpty()) {
                                    Glide.with(this)
                                            .load(sessionManager.getUser().getProfilePic())
                                            .error(R.drawable.man)
                                            .placeholder(R.drawable.man)
                                            .into(binding.lp5);
                                }
                                binding.lp1.setVisibility(View.GONE);
                                binding.lp2.setVisibility(View.GONE);
                                binding.lp3.setVisibility(View.GONE);
                                binding.lp4.setVisibility(View.GONE);
                                binding.lp5.setVisibility(View.VISIBLE);
                                binding.lp6.setVisibility(View.GONE);
                                binding.lpideal.setVisibility(View.GONE);
                            }else {
                                if (levelData.get(5).getLevel_play().equalsIgnoreCase("false")){
                                    if (sessionManager.getUser().getProfilePic() != null && !sessionManager.getUser().getProfilePic().isEmpty()) {
                                        Glide.with(this)
                                                .load(sessionManager.getUser().getProfilePic())
                                                .error(R.drawable.man)
                                                .placeholder(R.drawable.man)
                                                .into(binding.lp6);
                                    }
                                    binding.lp1.setVisibility(View.GONE);
                                    binding.lp2.setVisibility(View.GONE);
                                    binding.lp3.setVisibility(View.GONE);
                                    binding.lp4.setVisibility(View.GONE);
                                    binding.lp5.setVisibility(View.GONE);
                                    binding.lp6.setVisibility(View.VISIBLE);
                                    binding.lpideal.setVisibility(View.GONE);
                                }else {
                                    binding.lp1.setVisibility(View.GONE);
                                    binding.lp2.setVisibility(View.GONE);
                                    binding.lp3.setVisibility(View.GONE);
                                    binding.lp4.setVisibility(View.GONE);
                                    binding.lp5.setVisibility(View.GONE);
                                    binding.lp6.setVisibility(View.GONE);
                                    binding.lpideal.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                }
            }



//            if (levelData.get(0).getTotal_attempt_question()!=null && !levelData.get(0).getTotal_attempt_question().equalsIgnoreCase("0")){
//                binding.tvLevel1.setText(levelData.get(0).getLevelQuiz().size()+"");
//                binding.ivLevel1.setImageDrawable(getResources().getDrawable(R.drawable.level_complete_icon));
//
//            } if (levelData.get(1).getTotal_attempt_question()!=null && !levelData.get(1).getTotal_attempt_question().equalsIgnoreCase("0")){
//                binding.tvLevel2.setText(levelData.get(1).getLevelQuiz().size()+"");
//                binding.ivLevel2.setImageDrawable(getResources().getDrawable(R.drawable.level_complete_icon));
//
//            } if (levelData.get(2).getTotal_attempt_question()!=null && !levelData.get(2).getTotal_attempt_question().equalsIgnoreCase("0")){
//                binding.tvLevel3.setText(levelData.get(2).getLevelQuiz().size()+"");
//                binding.ivLevel3.setImageDrawable(getResources().getDrawable(R.drawable.level_complete_icon));
//
//            } if (levelData.get(3).getTotal_attempt_question()!=null && !levelData.get(3).getTotal_attempt_question().equalsIgnoreCase("0")){
//                binding.tvLevel4.setText(levelData.get(3).getLevelQuiz().size()+"");
//                binding.ivLevel4.setImageDrawable(getResources().getDrawable(R.drawable.level_complete_icon));
//
//            } if (levelData.get(4).getTotal_attempt_question()!=null && !levelData.get(4).getTotal_attempt_question().equalsIgnoreCase("0")){
//                binding.tvLevel5.setText(levelData.get(4).getLevelQuiz().size()+"");
//                binding.ivLevel5.setImageDrawable(getResources().getDrawable(R.drawable.level_complete_icon));
//
//            } if (levelData.get(5).getTotal_attempt_question()!=null && !levelData.get(5).getTotal_attempt_question().equalsIgnoreCase("0")){
//                binding.tvLevel6.setText(levelData.get(5).getLevelQuiz().size()+"");
//                binding.ivLevel6.setImageDrawable(getResources().getDrawable(R.drawable.level_complete_icon));
//
//            }


              if (levelData.get(0).getLevelQuiz() != null && levelData.get(0).getLevelQuiz().size() > 0) {
                binding.tvLevel1.setText(levelData.get(0).getSelf_checked()+"");
                binding.ivLevel1.setImageDrawable(getResources().getDrawable(R.drawable.level_complete_icon));

            }if (levelData.get(0).getLevel_play().equalsIgnoreCase("true")){
                  binding.tvLevel2.setText(levelData.get(1).getSelf_checked()+"");
                  binding.ivLevel2.setImageDrawable(getResources().getDrawable(R.drawable.level_complete_icon));

              }
                  if (levelData.get(1).getLevel_play().equalsIgnoreCase("true")){
                binding.tvLevel3.setText(levelData.get(1).getSelf_checked()+"");
                binding.ivLevel3.setImageDrawable(getResources().getDrawable(R.drawable.level_complete_icon));

            } if (levelData.get(2).getLevel_play().equalsIgnoreCase("true")){
                binding.tvLevel4.setText(levelData.get(2).getSelf_checked()+"");
                binding.ivLevel4.setImageDrawable(getResources().getDrawable(R.drawable.level_complete_icon));

            } if (levelData.get(3).getLevel_play().equalsIgnoreCase("true")){
                binding.tvLevel5.setText(levelData.get(3).getSelf_checked()+"");
                binding.ivLevel5.setImageDrawable(getResources().getDrawable(R.drawable.level_complete_icon));

            } if (levelData.get(4).getLevel_play().equalsIgnoreCase("true")){
                binding.tvLevel6.setText(levelData.get(4).getSelf_checked()+"");
                binding.ivLevel6.setImageDrawable(getResources().getDrawable(R.drawable.level_complete_icon));

            }
//
//                  if (levelData.get(5).getTotal_attempt_question()!=null && !levelData.get(5).getTotal_attempt_question().equalsIgnoreCase("0")){
//                binding.tvLevel6.setText(levelData.get(5).getLevelQuiz().size()+"");
//                binding.ivLevel6.setImageDrawable(getResources().getDrawable(R.drawable.level_complete_icon));

         //   }

            binding.relLevel1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (levelData.get(0).getLevelQuiz() != null && levelData.get(0).getLevelQuiz().size() > 0) {
                        Utils.playMusic(R.raw.practice_offline_snake, getActivity());
                        Intent intent= new Intent(getActivity(), OfflineQuizActivity.class);
                        intent.putParcelableArrayListExtra("LevelQuizData", (ArrayList<? extends Parcelable>) levelData.get(0).getLevelQuiz());
                        intent.putExtra("level_name", levelData.get(0).getLevelName());
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getActivity(), "Level Locked please Complete Previous level for unlock", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            binding.relLevel2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (levelData.get(1).getLevelQuiz() != null && levelData.get(1).getLevelQuiz().size() > 0) {
                        if (levelData.get(0).getLevel_play().equalsIgnoreCase("true")){
                            Utils.playMusic(R.raw.practice_offline_snake, getActivity());
                            Intent intent= new Intent(getActivity(), OfflineQuizActivity.class);
                            intent.putParcelableArrayListExtra("LevelQuizData", (ArrayList<? extends Parcelable>) levelData.get(1).getLevelQuiz());
                            intent.putExtra("level_name", levelData.get(1).getLevelName());
                            startActivity(intent);
                        }else {
                            Toast.makeText(getActivity(), "Level Locked please Complete Previous level for unlock", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), "Level Locked please Complete Previous level for unlock", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            binding.relLevel3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (levelData.get(2).getLevelQuiz() != null && levelData.get(2).getLevelQuiz().size() > 0) {
                        if (levelData.get(1).getLevel_play().equalsIgnoreCase("true")) {
                            Utils.playMusic(R.raw.practice_offline_snake, getActivity());
                            Intent intent = new Intent(getActivity(), OfflineQuizActivity.class);
                            intent.putParcelableArrayListExtra("LevelQuizData", (ArrayList<? extends Parcelable>) levelData.get(2).getLevelQuiz());
                            intent.putExtra("level_name", levelData.get(2).getLevelName());
                            startActivity(intent);
                        }else {
                            Toast.makeText(getActivity(), "Level Locked please Complete Previous level for unlock", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Level Locked please Complete Previous level for unlock", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            binding.relLevel4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (levelData.get(3).getLevelQuiz() != null && levelData.get(3).getLevelQuiz().size() > 0) {
                        if (levelData.get(2).getLevel_play().equalsIgnoreCase("true")) {
                            Utils.playMusic(R.raw.practice_offline_snake, getActivity());
                            Intent intent= new Intent(getActivity(), OfflineQuizActivity.class);
                            intent.putParcelableArrayListExtra("LevelQuizData", (ArrayList<? extends Parcelable>) levelData.get(3).getLevelQuiz());
                            intent.putExtra("level_name", levelData.get(3).getLevelName());
                            startActivity(intent);
                        }else {
                            Toast.makeText(getActivity(), "Level Locked please Complete Previous level for unlock", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), "Level Locked please Complete Previous level for unlock", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            binding.relLevel5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (levelData.get(4).getLevelQuiz() != null && levelData.get(4).getLevelQuiz().size() > 0) {
                        if (levelData.get(3).getLevel_play().equalsIgnoreCase("true")) {
                            Utils.playMusic(R.raw.practice_offline_snake, getActivity());
                            Intent intent= new Intent(getActivity(), OfflineQuizActivity.class);
                            intent.putParcelableArrayListExtra("LevelQuizData", (ArrayList<? extends Parcelable>) levelData.get(4).getLevelQuiz());
                            intent.putExtra("level_name", levelData.get(4).getLevelName());
                            startActivity(intent);
                        }else {
                            Toast.makeText(getActivity(), "Level Locked please Complete Previous level for unlock", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), "Level Locked please Complete Previous level for unlock", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            binding.relLevel6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (levelData.get(5).getLevelQuiz() != null && levelData.get(5).getLevelQuiz().size() > 0) {
                        if (levelData.get(4).getLevel_play().equalsIgnoreCase("true")) {
                            Utils.playMusic(R.raw.practice_offline_snake, getActivity());
                            Intent intent= new Intent(getActivity(), OfflineQuizActivity.class);
                            intent.putParcelableArrayListExtra("LevelQuizData", (ArrayList<? extends Parcelable>) levelData.get(5).getLevelQuiz());
                            intent.putExtra("level_name", levelData.get(5).getLevelName());
                            startActivity(intent);
                        }else {
                            Toast.makeText(getActivity(), "Level Locked please Complete Previous level for unlock", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), "Level Locked please Complete Previous level for unlock", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }else {
            if (sessionManager.getUser().getProfilePic() != null && !sessionManager.getUser().getProfilePic().isEmpty()) {
                Glide.with(this)
                        .load(sessionManager.getUser().getProfilePic())
                        .error(R.drawable.man)
                        .placeholder(R.drawable.man)
                        .into(binding.lpideal);
            }
            binding.lpideal.setVisibility(View.VISIBLE);
        }


    }

    private class LoadBackground extends AsyncTask<String, Void, Drawable> {

        private String imageUrl , imageName;

        public LoadBackground(String url, String file_name) {
            this.imageUrl = url;
            this.imageName = file_name;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Drawable doInBackground(String... urls) {

            try {
                InputStream is = (InputStream) this.fetch(this.imageUrl);
                Drawable d = Drawable.createFromStream(is, this.imageName);
                return d;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        private Object fetch(String address) throws MalformedURLException,IOException {
            URL url = new URL(address);
            Object content = url.getContent();
            return content;
        }

        @Override
        protected void onPostExecute(Drawable result) {
            super.onPostExecute(result);
            binding.llBg.setBackgroundDrawable(result);
        }
    }

    @Override
    public void onResume() {
      if (Connectivity.isConnected(getActivity())) {
        AllLevelList(StageId);
      } else {
        //SweetAlt.showErrorMessage(getActivity(), "Sorry", "You have no internet!");
      }


        super.onResume();
    }
}
