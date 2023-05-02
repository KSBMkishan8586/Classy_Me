package com.ksbm.ontu.api_client;

import static com.ksbm.ontu.api_client.Base_Url.API_KEY_HEADER;
import static com.ksbm.ontu.api_client.Base_Url.add_calendar_notes;
import static com.ksbm.ontu.api_client.Base_Url.add_parent;
import static com.ksbm.ontu.api_client.Base_Url.all_calendar_dates;
import static com.ksbm.ontu.api_client.Base_Url.all_calendar_notes;
import static com.ksbm.ontu.api_client.Base_Url.all_category;
import static com.ksbm.ontu.api_client.Base_Url.all_certificate;
import static com.ksbm.ontu.api_client.Base_Url.all_cities_by_state;
import static com.ksbm.ontu.api_client.Base_Url.all_classes;
import static com.ksbm.ontu.api_client.Base_Url.all_competitions;
import static com.ksbm.ontu.api_client.Base_Url.all_currencies;
import static com.ksbm.ontu.api_client.Base_Url.all_drawing_images;
import static com.ksbm.ontu.api_client.Base_Url.all_foundation;
import static com.ksbm.ontu.api_client.Base_Url.all_foundation_learning;
import static com.ksbm.ontu.api_client.Base_Url.all_fundamentals;
import static com.ksbm.ontu.api_client.Base_Url.all_languages;
import static com.ksbm.ontu.api_client.Base_Url.all_levels;
import static com.ksbm.ontu.api_client.Base_Url.all_prize;
import static com.ksbm.ontu.api_client.Base_Url.all_shop;
import static com.ksbm.ontu.api_client.Base_Url.all_situations;
import static com.ksbm.ontu.api_client.Base_Url.all_stage;
import static com.ksbm.ontu.api_client.Base_Url.all_state;
import static com.ksbm.ontu.api_client.Base_Url.all_webinar;
import static com.ksbm.ontu.api_client.Base_Url.category_wise_video;
import static com.ksbm.ontu.api_client.Base_Url.coin_deduct;
import static com.ksbm.ontu.api_client.Base_Url.coin_earn;
import static com.ksbm.ontu.api_client.Base_Url.course_workbooks;
import static com.ksbm.ontu.api_client.Base_Url.daily_video;
import static com.ksbm.ontu.api_client.Base_Url.delete_calendar_note;
import static com.ksbm.ontu.api_client.Base_Url.fillData;
import static com.ksbm.ontu.api_client.Base_Url.forgot;
import static com.ksbm.ontu.api_client.Base_Url.foundation_questions;
import static com.ksbm.ontu.api_client.Base_Url.foundation_quiz_result;
import static com.ksbm.ontu.api_client.Base_Url.fundamental_toddles;
import static com.ksbm.ontu.api_client.Base_Url.get_alphabet;
import static com.ksbm.ontu.api_client.Base_Url.get_animal;
import static com.ksbm.ontu.api_client.Base_Url.get_banner;
import static com.ksbm.ontu.api_client.Base_Url.get_currency;
import static com.ksbm.ontu.api_client.Base_Url.get_fruit_vegetable;
import static com.ksbm.ontu.api_client.Base_Url.join_webinar;
import static com.ksbm.ontu.api_client.Base_Url.leaderboard;
import static com.ksbm.ontu.api_client.Base_Url.level_done;
import static com.ksbm.ontu.api_client.Base_Url.my_all_child;
import static com.ksbm.ontu.api_client.Base_Url.newupdates;
import static com.ksbm.ontu.api_client.Base_Url.noticeboard;
import static com.ksbm.ontu.api_client.Base_Url.offline_memory_map_quiz_result;
import static com.ksbm.ontu.api_client.Base_Url.offline_sink_coin;
import static com.ksbm.ontu.api_client.Base_Url.offline_stage_quiz_details;
import static com.ksbm.ontu.api_client.Base_Url.personality_quiz_details;
import static com.ksbm.ontu.api_client.Base_Url.personality_quiz_result;
import static com.ksbm.ontu.api_client.Base_Url.personality_video_bonus;
import static com.ksbm.ontu.api_client.Base_Url.personality_video_charge;
import static com.ksbm.ontu.api_client.Base_Url.post_body;
import static com.ksbm.ontu.api_client.Base_Url.proffession_list;
import static com.ksbm.ontu.api_client.Base_Url.progression_report;
import static com.ksbm.ontu.api_client.Base_Url.quiz_details;
import static com.ksbm.ontu.api_client.Base_Url.quiz_result;
import static com.ksbm.ontu.api_client.Base_Url.registration_by_social;
import static com.ksbm.ontu.api_client.Base_Url.report_curricular_report;
import static com.ksbm.ontu.api_client.Base_Url.report_details;
import static com.ksbm.ontu.api_client.Base_Url.report_internal_exam;
import static com.ksbm.ontu.api_client.Base_Url.resend_otp;
import static com.ksbm.ontu.api_client.Base_Url.save_purchase_details;
import static com.ksbm.ontu.api_client.Base_Url.selfcheck_done;
import static com.ksbm.ontu.api_client.Base_Url.sendOTP;
import static com.ksbm.ontu.api_client.Base_Url.set_bank_details;
import static com.ksbm.ontu.api_client.Base_Url.settings;
import static com.ksbm.ontu.api_client.Base_Url.situation_questions;
import static com.ksbm.ontu.api_client.Base_Url.situation_quiz_result;
import static com.ksbm.ontu.api_client.Base_Url.situation_sentence;
import static com.ksbm.ontu.api_client.Base_Url.state_list;
import static com.ksbm.ontu.api_client.Base_Url.upcoming_event;
import static com.ksbm.ontu.api_client.Base_Url.update_profile;
import static com.ksbm.ontu.api_client.Base_Url.update_setting_permissions;
import static com.ksbm.ontu.api_client.Base_Url.uploadProfile;
import static com.ksbm.ontu.api_client.Base_Url.user_plan_details;
import static com.ksbm.ontu.api_client.Base_Url.user_profile;
import static com.ksbm.ontu.api_client.Base_Url.user_registration;
import static com.ksbm.ontu.api_client.Base_Url.user_signin;
import static com.ksbm.ontu.api_client.Base_Url.verify_otp;

import com.google.gson.JsonObject;
import com.ksbm.ontu.alerts_module.model.CalendarNotesDateModel;
import com.ksbm.ontu.alerts_module.model.CalendarNotesModel;
import com.ksbm.ontu.alerts_module.model.NoticeBoardModel;
import com.ksbm.ontu.alerts_module.model.UpcomingEventModel;
import com.ksbm.ontu.alerts_module.model.WebinarModel;
import com.ksbm.ontu.foundation.model.AlphabetModelModel;
import com.ksbm.ontu.foundation.model.AnimalModel;
import com.ksbm.ontu.foundation.model.CurrencyModel;
import com.ksbm.ontu.foundation.model.DenominationModel;
import com.ksbm.ontu.foundation.model.Drawing_Model;
import com.ksbm.ontu.foundation.model.FoundationCourseModel;
import com.ksbm.ontu.foundation.model.FoundationQuizModel;
import com.ksbm.ontu.foundation.model.FoundationQuizSubmit;
import com.ksbm.ontu.free_coin.login_signup.add_parent.AddParentModel;
import com.ksbm.ontu.free_coin.login_signup.login_signup_model.LoginModel;
import com.ksbm.ontu.free_coin.login_signup.login_signup_model.SignupModel;
import com.ksbm.ontu.fundamental.model.fundamental_mcq_model.Fundamental_MCQ_Model;
import com.ksbm.ontu.fundamental.model.fundamental_model.Fundamental_Model;
import com.ksbm.ontu.fundamental.model.fundamental_model.Fundamental_Toodle_Model;
import com.ksbm.ontu.fundamental.model.fundamental_model.Fundamental_Workbook_Model;
import com.ksbm.ontu.fundamental.model.fundamental_quiz_model.Fundamental_Quiz_Model;
import com.ksbm.ontu.fundamental.model.fundamental_quiz_model.Fundamental_Quiz_Workbook_Resul;
import com.ksbm.ontu.fundamental.model.jumble_arrange_model.JumbleArrangeModel;
import com.ksbm.ontu.fundamental.model.match_following_model.Match_Following_Model;
import com.ksbm.ontu.fundamental.model.quiz_touch_fill.FundamentalTouch_Fill;
import com.ksbm.ontu.fundamental.model.touch_the_word.TouchWordModel;
import com.ksbm.ontu.main_screen.model.FreeCoinModel;
import com.ksbm.ontu.main_screen.model.MyShopCoin;
import com.ksbm.ontu.main_screen.model.OneTimeBannerModel;
import com.ksbm.ontu.main_screen.model.ResponseUpgradeCourse;
import com.ksbm.ontu.main_screen.model.ResponseUpgradeCourseStatus;
import com.ksbm.ontu.main_screen.model.SettingsModel;
import com.ksbm.ontu.main_screen.model.SuccesModel;
import com.ksbm.ontu.main_screen.model.class_model.ClassModel;
import com.ksbm.ontu.main_screen.model.language_model.LanguageModel;
import com.ksbm.ontu.main_screen.model.state_model.StateModel;
import com.ksbm.ontu.personality_dev.model.CategoryModel;
import com.ksbm.ontu.personality_dev.model.CategoryWiseModel;
import com.ksbm.ontu.personality_dev.model.PersonalityMCQTest;
import com.ksbm.ontu.practice_offline.model.MemoryMapQuizModel;
import com.ksbm.ontu.practice_offline.model.OfflineLevelList;
import com.ksbm.ontu.practice_offline.model.OfflineStageModel;
import com.ksbm.ontu.profile.model.CompetitionListModel;
import com.ksbm.ontu.profile.model.LeaderBoardModel;
import com.ksbm.ontu.profile.model.MyCertificate;
import com.ksbm.ontu.profile.model.MyChildModel;
import com.ksbm.ontu.profile.model.MyPrize;
import com.ksbm.ontu.profile.model.ProfileResponse;
import com.ksbm.ontu.progression_report.model.DetailedReport;
import com.ksbm.ontu.situation.model.SituationModel;
import com.ksbm.ontu.situation.model.SituationQuestionModel;
import com.ksbm.ontu.situation.model.SituationQuizSubmit;
import com.ksbm.ontu.situation.model.SituationSentenceModel;
import com.ksbm.ontu.translation.DeductCoinModel;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


/**
 * Created by Raghvendra Sahu on 20-Apr-21.
 */
public interface Api_Call {

    @GET(all_languages)
    @Headers(API_KEY_HEADER)
    Observable<LanguageModel> LanguageList();

    @POST(user_signin)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<LoginModel> LoginUser(
            @Field("username") String et_email,
            @Field("password") String et_pw,
            @Field("device_id") String device_id,
            @Field("device_token") String device_token);

    @POST(user_registration)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<SignupModel> SignupUser(
            @Field("fullname") String et_firstname,
            @Field("mobile") String et_mobile,
            @Field("email") String et_email,
            @Field("password") String et_pw);

    @POST(forgot)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<SuccesModel> ForgotUser(
            @Field("email") String et_email);

    @POST(all_fundamentals)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<Fundamental_Model> GetFundamentalCourse(
            @Field("language_id") String languageid,
            @Field("classid") String classid,
            @Field("userid") String userid);

    @POST(fundamental_toddles)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<Fundamental_Toodle_Model> GetFundamentalToodle(
            @Field("language_id") String languageid,
            @Field("fundamental_id") String fundamental_id,
            @Field("userid") String userid);


    @POST(course_workbooks)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<Fundamental_Workbook_Model> GetFundamentalWorkbook(
            @Field("language_id") String languageid,
            @Field("fundamental_id") String fundamental_id,
            @Field("toddle_id") String toodleId,
            @Field("userid") String userid);

    @POST(registration_by_social)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<LoginModel> LoginSocialUser(
            @Field("fullname") String full_name,
            @Field("email") String social_email,
            @Field("oauth_uid") String social_id,
            @Field("oauth_provider") String social_type,
            @Field("device_token") String tokenId,
            @Field("device_id") String deviceId,
            @Field("gender") String gender);

    @POST(add_parent)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<AddParentModel> UpdateProfile(
            @Field("fullname") String et_parent_name,
            @Field("mobile") String et_parent_mobile,
            @Field("student_id") String student_id,
            @Field("email") String et_email,
            @Field("school_code") String et_sch_code,
            @Field("school_name") String et_sch_name,
            @Field("dob") String et_std_dob,
            @Field("address") String et_address,
            @Field("cityname") String et_city,
            @Field("classid") String classId,
            @Field("state_id") String stateId,
            @Field("gender") String gender);

    @POST(verify_otp)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<LoginModel> VerifyOtp(
            @Field("otp") String otpValue,
            @Field("mobile") String mobile_no,
            @Field("device_id") String deviceId,
            @Field("device_token") String tokenId,
            @Field("student_id") String student_id);

    @POST(resend_otp)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<AddParentModel> ResendOtp(
            @Field("mobile") String mobile_no);

    @POST(quiz_details)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<Fundamental_Quiz_Model> GetFundamentalQuiz(
            @Field("language_id") String languageid,
            @Field("workbook_id") String workbook_id,
            @Field("fundamental_id") String fundamental_id,
            @Field("toddle_id") String toddle_id,
            @Field("quiz_type_id") String quiz_type_id);

    @POST(quiz_details)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<Fundamental_MCQ_Model> GetFundamentalQuizMCQ(
            @Field("language_id") String languageid,
            @Field("workbook_id") String workbook_id,
            @Field("fundamental_id") String fundamental_id,
            @Field("toddle_id") String toddle_id,
            @Field("quiz_type_id") String quiz_type_id);

    @POST(quiz_result)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<Fundamental_Quiz_Workbook_Resul> FundamentalQuiz_Result(
            @Field("userid") String userid,
            @Field("got_reward") String total_reward,
            @Field("is_right_answer") String right_ans,
            @Field("quiz_id") String quizId,
            @Field("quiz_question_id") String quiz_question_id);

    @GET(all_classes)
    @Headers(API_KEY_HEADER)
    Observable<ClassModel> GetAllClassName();

    @GET(all_state)
    @Headers(API_KEY_HEADER)
    Observable<StateModel> GetAllStateName();

    @POST(all_cities_by_state)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<StateModel> GetAllCityName(
            @Field("state_id") String stateId);

    @POST(all_situations)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<SituationModel> GetSituationList(
            @Field("language_id") String languageid,
            @Field("userid") String userid,
            @Field("classid") String classid);

    @POST(quiz_details)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<FundamentalTouch_Fill> GetFundamentalQuizFill(
            @Field("language_id") String languageid,
            @Field("workbook_id") String workbookId,
            @Field("fundamental_id") String fundamental_id,
            @Field("toddle_id") String toddle_id,
            @Field("quiz_type_id") String quiz_type_id);

    @POST(quiz_details)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<Match_Following_Model> GetFundamentalMatchFollowing(
            @Field("language_id") String languageid,
            @Field("workbook_id") String workbookId,
            @Field("fundamental_id") String fundamental_id,
            @Field("toddle_id") String toddle_id,
            @Field("quiz_type_id") String quiz_type_id);

    @POST(quiz_details)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<JumbleArrangeModel> GetFundamentalJumbleArrange(
            @Field("language_id") String languageid,
            @Field("workbook_id") String workbookId,
            @Field("fundamental_id") String fundamental_id,
            @Field("toddle_id") String toddle_id,
            @Field("quiz_type_id") String quiz_type_id);


    @POST(quiz_details)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<TouchWordModel> GetFundamentalTouchWord(
            @Field("language_id") String languageid,
            @Field("workbook_id") String workbookId,
            @Field("fundamental_id") String fundamental_id,
            @Field("toddle_id") String toddle_id,
            @Field("quiz_type_id") String quiz_type_id);

    @POST(situation_sentence)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<SituationSentenceModel> GetSituationSetences(
            @Field("language_id") String languageid,
            @Field("userid") String userid,
            @Field("situation_id") String situation_id);

    @POST(situation_questions)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<SituationQuestionModel> GetSituationQuestion(
            @Field("language_id") String languageid,
            @Field("situation_id") String situation_id,
            @Field("sentence_id") String sentence_id);

    @POST(all_category)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<CategoryModel> GetPersonalityList(
            @Field("language_id") String languageid,
            @Field("userid") String userid,
            @Field("classid") String classid);

    @POST(category_wise_video)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<CategoryWiseModel> GetCategoryWiseList(
            @Field("category_id") String categoryId,
            @Field("userid") String userid,
            @Field("language_id") String languageId);


    @POST(situation_quiz_result)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<SituationQuizSubmit> SituationQuiz_Result(
            @Field("userid") String userid,
            @Field("got_reward") String reward,
            @Field("is_right_answer") String isRight,
            @Field("situation_id") String situationId,
            @Field("sentence_id") String sentenceId,
            @Field("situation_question_id") String situationQuestionId);

    @POST(personality_video_charge)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<SuccesModel> PersonalityVideoCharge(
            @Field("userid") String userid,
            @Field("video_id") String videoid,
            @Field("category_id") String categoryId);

    @POST(personality_video_bonus)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<SuccesModel> GetPersonalityVideoEarning(
            @Field("userid") String userid,
            @Field("video_id") String videoid,
            @Field("category_id") String categoryId);

    @POST(personality_quiz_details)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<PersonalityMCQTest> GetMCQTest(
            @Field("language_id") String languageId,
            @Field("video_id") String video_id);

    @POST(personality_quiz_result)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<SuccesModel> SubmitPersonalityQuiz(
            @Field("userid") String userid,
            @Field("quiz_question_id") String ques_id,
            @Field("selected_ans") String selecteed_ans,
            @Field("video_id") String video_id,
            @Field("category_id") String category_id,
            @Field("got_reward") String got_reward);

    @POST(user_profile)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<LoginModel> GetProfileDetails(
            @Field("userid") String userid);

    @POST(all_certificate)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<MyCertificate> GetAllCerificate(
            @Field("userid") String userid);

    @POST(all_prize)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<MyPrize> GetAllPrize(
            @Field("type") String type);

    @POST(all_shop)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<MyShopCoin> GetAllShop(
            @Field("") String classid
    );

    @POST(all_stage)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<OfflineStageModel> GetOfflineStageList(
            @Field("classid") String classid,
            @Field("userid") String userid);

    @POST(coin_deduct)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<DeductCoinModel> DeductCoin(
            @Field("userid") String userid,
            @Field("coin_amount") String amount,
            @Field("reason") String reason);

    @POST(coin_earn)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<DeductCoinModel> EarnCoin(
            @Field("userid") String userid,
            @Field("coin_amount") String amount,
            @Field("reason") String reason,
            @Field("id") String id);

    @POST(daily_video)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<FreeCoinModel> GetFreeCoinVideo(
            @Field("userid") String userid);

    @POST(all_levels)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<OfflineLevelList> GetLevelList(
            @Field("stage_id") String stageId,
            @Field("userid") String userid);

    @POST(level_done)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<JsonObject> GetLeveldone(
            @Field("stage_id") String stageId,
            @Field("user_id") String userid,
            @Field("level_id") String lid);

    @POST(selfcheck_done)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<SuccesModel> GetSelfcheckdone(
            @Field("question_id") String stageId,
            @Field("user_id") String userid,
            @Field("level_id") String lid);

    @POST(all_foundation)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<FoundationCourseModel> GetFoundationList(
            @Field("language_id") String languageid,
            @Field("userid") String userid);

    @POST(offline_sink_coin)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<SuccesModel> UploadScoreData(
            @Field("userid") String userid,
            @Field("sink_data") String passArray);

    @POST(post_body)
    @FormUrlEncoded
    Observable<String> PostBody(@Field("userid") String userid);


    @POST(get_alphabet)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<AlphabetModelModel> GetAlphabetList(
            @Field("userid") String userid,
            @Field("language_id") String languageid,
            @Field("foundation_id") String foundation_id);

    @POST(all_foundation_learning)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<AlphabetModelModel> GetLearningList(
            @Field("userid") String userid,
            @Field("language_id") String languageid,
            @Field("foundation_id") String foundation_id);

    @POST(get_animal)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<AnimalModel> GetAnimalList(
            @Field("userid") String userid,
            @Field("language_id") String languageid,
            @Field("foundation_id") String foundation_id);

    @GET(all_currencies)
    @Headers(API_KEY_HEADER)
    Observable<CurrencyModel> GetCurrencyList();

    @POST(get_currency)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<DenominationModel> GetDenominationList(
            @Field("userid") String userid,
            @Field("language_id") String languageid);


    @POST(get_fruit_vegetable)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<AnimalModel> GetFruitTypeList(
            @Field("userid") String userid,
            @Field("language_id") String languageid,
            @Field("foundation_id") String foundation_id);

    @POST(offline_stage_quiz_details)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<MemoryMapQuizModel> GetMemoryMapQuiz(
            @Field("language_id") String languageid,
            @Field("stage_id") String stage_id,
            @Field("level_id") String levelid);

    @POST(offline_memory_map_quiz_result)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<SuccesModel> SubmitMemoryQuiz(
            @Field("userid") String userid,
            @Field("quiz_question_id") String ques_id,
            @Field("selected_ans") String selecteed_ans,
            @Field("stage_id") String stage_id,
            @Field("classid") String classid);

    @POST(foundation_questions)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<FoundationQuizModel> GetFoundationQuiz(
            @Field("language_id") String languageid,
            @Field("userid") String userid,
            @Field("foundation_id") String foundation_id);


    @POST(foundation_quiz_result)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<FoundationQuizSubmit> FoundationQuiz_Result(
            @Field("userid") String userid,
            @Field("got_reward") String reward,
            @Field("is_right_answer") String isRight,
            @Field("foundation_id") String foundation_id,
            @Field("question_id") String quiz_question_id);

    @GET(settings)
    @Headers(API_KEY_HEADER)
        // @FormUrlEncoded
    Observable<SettingsModel> GetAllSettings();

    @GET(all_drawing_images)
    @Headers(API_KEY_HEADER)
    Observable<Drawing_Model> GetDwawingImages();


    @POST(update_profile)
    @Headers(API_KEY_HEADER)
    @Multipart
    Observable<LoginModel> UpdateUserProfile(
            @Part("fullname") RequestBody et_parent_name,
            @Part("mobile") RequestBody et_parent_mobile,
            @Part("userid") RequestBody userid,
            @Part("email") RequestBody et_email,
            @Part("dob") RequestBody et_std_dob,
            @Part("address") RequestBody et_address,
            @Part("city_id") RequestBody cityId,
            @Part("state_id") RequestBody stateId,
            @Part("gender") RequestBody gender,
            @Part("eyesight") RequestBody eye_sight,
            @Part("why_learn_english") RequestBody whyEnglish,
            @Part("profession_id") RequestBody professional_id,
            @Part("pincode") RequestBody pincode,
            @Part MultipartBody.Part profile_pic
    );


    @POST(update_profile)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<LoginModel> UpdateUserProfileNoImage(
            @Field("fullname") String et_name,
            @Field("mobile") String et_mobile,
            @Field("userid") String userid,
            @Field("email") String et_email,
            @Field("dob") String et_std_dob,
            @Field("address") String et_address,
            @Field("city_id") String cityId,
            @Field("state_id") String stateId,
            @Field("gender") String gender,
            @Field("eyesight") String eye_sight,
            @Field("why_learn_english") String whyEnglish,
            @Field("profession_id") String s,
            @Field("pincode") String s1);

    @POST(noticeboard)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<NoticeBoardModel> GetNoticeboardListList(
            @Field("language_id") String languageid);

    @POST(newupdates)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<NoticeBoardModel> GetNewUpdateList(
            @Field("language_id") String languageid);

    @POST(all_webinar)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<WebinarModel> GetWebinarList(
            @Field("userid") String languageid);

    @POST(join_webinar)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<SuccesModel> Join_webinar(
            @Field("userid") String userid,
            @Field("webinar_id") String webinarId,
            @Field("join_amount") String joinAmount,
            @Field("w_join_id") String w_join_id);

    @POST(all_calendar_notes)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<CalendarNotesModel> GetallNotesList(
            @Field("userid") String userid,
            @Field("c_date") String selected_date);

    @POST(add_calendar_notes)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<SuccesModel> AddNotesList(
            @Field("userid") String userid,
            @Field("c_date") String selected_date,
            @Field("c_title") String et_title,
            @Field("c_description") String et_description);

    @POST(all_calendar_dates)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<CalendarNotesDateModel> GetallNotesMarkDate(
            @Field("userid") String userid);

    @POST(delete_calendar_note)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<SuccesModel> DeleteMyNote(
            // @Field("userid") String userid,
            @Field("c_note_id") String getcNoteId);

    @POST(leaderboard)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<LeaderBoardModel> GetLeaderBoard(
            @Field("userid") String userid,
            @Field("page_no") String page_no,
            @Field("list_type") String leaderboard_type);

    @GET(get_banner)
    @Headers(API_KEY_HEADER)
        //@FormUrlEncoded
    Observable<OneTimeBannerModel> GetAllBanner();


    @POST(my_all_child)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<MyChildModel> GetChildList(@Field("userid") String userid);

    @POST(update_setting_permissions)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<LoginModel> UpdateSettings(
            @Field("userid") String userid,
            @Field("allow_subtitle") String allow_subtitle,
            @Field("chat_message") String chat_message,
            @Field("show_online_status_to_friend") String show_online_status_to_friend,
            @Field("notification_while_learning") String noti_learning,
            @Field("allow_location") String allow_location);

    @GET(all_competitions)
    @Headers(API_KEY_HEADER)
        //@FormUrlEncoded
    Observable<CompetitionListModel> GetCompetitionList();

    @POST(proffession_list)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<JsonObject> GetProfession(
            @Field("API-KEY") String api_key);

    @POST(state_list)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<JsonObject> GetStates(
            @Field("API-KEY") String api_key, @Field("userid") String userid, @Field("language_id") String language_id);

    @POST(sendOTP)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<JsonObject> sendOTP(
            @Field("API-KEY") String api_key, @Field("mobile") String mobile, @Field("email") String email, @Field("type") String type);


    @POST(fillData)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<JsonObject> fillAllData(
            @Field("API-KEY") String api_key,
            @Field("state") String state,
            @Field("age") String age,
            @Field("mobile") String mobile,
            @Field("email") String email,
            @Field("gender") String gender,
            @Field("profession") String profession,
            @Field("english_proficiency") String english_proficiency,
            @Field("age_criteria") String age_criteria,
            @Field("user_id") String user_id);


    @Multipart
    @POST(uploadProfile)
    Call<JsonObject> apiUploadPhoto(@Part MultipartBody.Part file,
                                    @Part("API-KEY") RequestBody api_key,
                                    @Part("userid") RequestBody userid,
                                    @Part("profile_pic") RequestBody profile_pic);

    @POST(report_internal_exam)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<JsonObject> report_internal_exam(
            @Field("API-KEY") String api_key
            ,@Field("userid") String userid);

    @POST(report_curricular_report)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<JsonObject> report_co_curricular_exam(
            @Field("API-KEY") String api_key,
            @Field("userid") String userid);



    @POST(progression_report)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<JsonObject> report_co_progression(
            @Field("API-KEY") String api_key,
            @Field("userid") String userid);

    @POST(upcoming_event)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<UpcomingEventModel> upcoming_event(
            @Field("API-KEY") String api_key,
            @Field("userid") String userid);


    @POST(save_purchase_details)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<ResponseUpgradeCourse> SetUpgradeCourse(
            @Field("plan_amount") String plan_amount,
            @Field("userid") String userid,
            @Field("transaction_id") String transaction_id);


    @POST(user_plan_details)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<ResponseUpgradeCourseStatus> UpgradeCourseStatus(
            @Field("userid") String userid);

    @POST(report_details)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<DetailedReport> getDetailedReport(
            @Field("userid") String user_id,
            @Field("type") String type);

    @POST(set_bank_details)
    @Headers(API_KEY_HEADER)
    @FormUrlEncoded
    Observable<ProfileResponse> bankDetails(
            @Field("userid") String userid,
            @Field("account_no") String account_no,
            @Field("ac_holder_name") String ac_holder_name,
            @Field("ifsc_code") String ifsc_code,
            @Field("branch") String branch
    );

}
