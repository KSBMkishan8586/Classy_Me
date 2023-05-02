package com.ksbm.ontu.custom_class;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.foundation.model.FoundationQuizSubmit;
import com.ksbm.ontu.fundamental.activity.Fundamental_Quiz_Drag_Drop;
import com.ksbm.ontu.fundamental.model.fundamental_quiz_model.Fundamental_Quiz_Workbook_Resul;
import com.ksbm.ontu.main_screen.model.SuccesModel;
import com.ksbm.ontu.situation.model.SituationQuizSubmit;
import com.ksbm.ontu.translation.DeductCoinModel;
import com.ksbm.ontu.utils.SweetAlt;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class CommonUtil {


    //submit quiz and get coin
    @SuppressLint("CheckResult")
    public static void SubmitQuiz(String userid, String quiz_id, String reward, String isRight, String quiz_question_id) {

//        final ProgressDialog progressDialog = new ProgressDialog(Fundamental_Quiz_Drag_Drop.this, R.style.MyGravity);
//        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.FundamentalQuiz_Result(userid, reward, isRight, quiz_id, quiz_question_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Fundamental_Quiz_Workbook_Resul>() {
                    @Override
                    public void onNext(Fundamental_Quiz_Workbook_Resul response) {
                        //Handle logic
                        try {
                            //  progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {



                            } else {
                               // SweetAlt.showErrorMessage(Fundamental_Quiz_Drag_Drop.this, "Sorry", response.getMessage());

                            }

                        } catch (Exception e) {
                            // progressDialog.dismiss();
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        //Handle error
                        //  progressDialog.dismiss();
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
                        // progressDialog.dismiss();
                    }
                });


    }

    @SuppressLint("CheckResult")
    public static void SubmitSituationQuiz(String userid, String situationId, String sentenceId, String situationQuestionId,
                                           String reward, String isRight) {
        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.SituationQuiz_Result(userid, reward, isRight, situationId, sentenceId, situationQuestionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SituationQuizSubmit>() {
                    @Override
                    public void onNext(SituationQuizSubmit response) {
                        //Handle logic
                        try {
                            //  progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {



                            } else {
                                // SweetAlt.showErrorMessage(Fundamental_Quiz_Drag_Drop.this, "Sorry", response.getMessage());

                            }

                        } catch (Exception e) {
                            // progressDialog.dismiss();
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        //Handle error
                        //  progressDialog.dismiss();
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
                        // progressDialog.dismiss();
                    }
                });

    }

    @SuppressLint("CheckResult")
    public static void DeductCoin(String userid, String amount, String reason) {
        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.DeductCoin(userid, amount, reason)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<DeductCoinModel>() {
                    @Override
                    public void onNext(DeductCoinModel response) {
                        //Handle logic
                        try {
                            //  progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {



                            } else {
                                // SweetAlt.showErrorMessage(Fundamental_Quiz_Drag_Drop.this, "Sorry", response.getMessage());

                            }

                        } catch (Exception e) {
                            // progressDialog.dismiss();
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        //Handle error
                        //  progressDialog.dismiss();
                        Log.e("mr_product_error", e.toString());

                    }

                    @Override
                    public void onComplete() {
                        // progressDialog.dismiss();
                    }
                });

    }

    @SuppressLint("CheckResult")
    public static void EarnCoin(String userid, String amount, String reason, String id) {
        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.EarnCoin(userid, amount, reason, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<DeductCoinModel>() {
                    @Override
                    public void onNext(DeductCoinModel response) {
                        //Handle logic
                        try {
                            //  progressDialog.dismiss();
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {


                            } else {
                                // SweetAlt.showErrorMessage(Fundamental_Quiz_Drag_Drop.this, "Sorry", response.getMessage());
                            }

                        } catch (Exception e) {
                            // progressDialog.dismiss();
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        //Handle error
                        //  progressDialog.dismiss();
                        Log.e("mr_product_error", e.toString());

                    }

                    @Override
                    public void onComplete() {
                        // progressDialog.dismiss();
                    }
                });

    }

    @SuppressLint("CheckResult")
    public static void SubmitMemoryQuiz(String userid, String quizid, String selecteed_ans, String classid, String stageid) {
//        final ProgressDialog progressDialog = new ProgressDialog(PersonalityVideoPlayer.this, R.style.MyGravity);
//        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.SubmitMemoryQuiz(userid, quizid, selecteed_ans, stageid, classid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SuccesModel>() {
                    @Override
                    public void onNext(SuccesModel response) {
                        //Handle logic
                        try {
                            //  progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {


                            } else {
                                // SweetAlt.showErrorMessage(getActivity(), "Sorry", response.getMessage());

                            }
                            //  binding.swipeToRefresh.setRefreshing(false);

                        } catch (Exception e) {
                            // progressDialog.dismiss();
                        }//

                        //  binding.swipeToRefresh.setRefreshing(false);

                    }

                    @Override
                    public void onError(Throwable e) {
                        //Handle error
                        //  progressDialog.dismiss();
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
                        // progressDialog.dismiss();
                    }
                });

    }

    @SuppressLint("CheckResult")
    public static void SubmitFoundationActivityQuiz(String userid, String foundation_id, String reward, String isRight, String quiz_question_id) {

//        final ProgressDialog progressDialog = new ProgressDialog(Fundamental_Quiz_Drag_Drop.this, R.style.MyGravity);
//        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.FoundationQuiz_Result(userid, reward, isRight, foundation_id, quiz_question_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<FoundationQuizSubmit>() {
                    @Override
                    public void onNext(FoundationQuizSubmit response) {
                        //Handle logic
                        try {
                            //  progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {



                            } else {
                                // SweetAlt.showErrorMessage(Fundamental_Quiz_Drag_Drop.this, "Sorry", response.getMessage());

                            }

                        } catch (Exception e) {
                            // progressDialog.dismiss();
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        //Handle error
                        //  progressDialog.dismiss();
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
                        // progressDialog.dismiss();
                    }
                });


    }

}
