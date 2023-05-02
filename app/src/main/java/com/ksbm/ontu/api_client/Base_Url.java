package com.ksbm.ontu.api_client;

/**
 * Created by Raghvendra Sahu on 06-Jan-20.
 */
public interface Base_Url {

    //String BaseUrl="http://15.207.216.147/index.php/apis/";
    String BaseUrl = "http://classyme.org/index.php/apis/";

    String API_KEY_HEADER = "API-KEY: 123456";
    String API_KEY = "123456";


    String user_registration = "auth/user_registration";
    String user_signin = "auth/user_signin";
    String proffession_list = "openmarket/proffession_list";
    String state_list = "openmarket/state_list";
    String sendOTP = "openmarket/sentOtp";
    String fillData = "openmarket/user_profile";
    String uploadProfile = "openmarket/profile_pic";
    String registration_by_social = "auth/registration_by_social";
    String forgot = "auth/forgot_password";
    String add_parent = "auth/add_parent";
    String update_profile = "auth/update_profile";
    String verify_otp = "auth/verify_otp";
    String resend_otp = "auth/resend_otp";
    String user_logout = "auth/user_logout";
    String user_profile = "auth/user_profile";
    String update_setting_permissions = "auth/update_setting_permissions";

    String all_languages = "home/all_languages";
    String all_classes = "home/all_classes";
    String all_state = "home/all_state";
    String all_cities_by_state = "home/all_cities_by_state";
    String all_certificate = "home/all_certificate";
    String all_prize = "home/prize";
    String all_shop = "home/shop";
    String coin_deduct = "home/coin_deduct";
    String daily_video = "home/daily_video";
    String coin_earn = "home/coin_earn";
    String settings = "home/settings";
    String noticeboard = "home/noticeboard";
    String newupdates = "home/newupdates";
    String all_webinar = "home/all_webinar";
    String join_webinar = "home/join_webinar";
    String all_calendar_notes = "home/all_calendar_notes";
    String add_calendar_notes = "home/add_calendar_note";
    String all_calendar_dates = "home/all_calendar_dates";
    String delete_calendar_note = "home/delete_calendar_note";
    String leaderboard = "home/leaderboard";
    String get_banner = "home/get_banner";
    String all_competitions = "home/all_competitions";
    String my_all_child = "home/my_all_child";

    String all_fundamentals = "fundamentals/all_fundamentals";
    String fundamental_toddles = "fundamentals/fundamental_toddles";
    String course_workbooks = "fundamentals/toddle_workbooks";

    String quiz_details = "quiz/quiz_details";
    String quiz_result = "quiz/quiz_result";

    String all_situations = "situations/all_situations";
    String situation_sentence = "situations/situation_sentence";
    String situation_questions = "situations/situation_questions";
    String situation_quiz_result = "situations/situation_quiz_result";

    String all_category = "personality/all_category";
    String category_wise_video = "personality/category_wise_video";
    String personality_video_charge = "personality/personality_video_charge";
    String personality_video_bonus = "personality/personality_video_bonus";
    String personality_quiz_details = "personality/quiz_details";
    String personality_quiz_result = "personality/personality_quiz_result";

    String all_stage = "OfflineMode/all_stage";
    String all_levels = "OfflineMode/levels";
    String level_done = "OfflineMode/play";
    String selfcheck_done = "OfflineMode/self_checked";
    String offline_sink_coin = "OfflineMode/offline_sink_coin";
    String offline_stage_quiz_details = "OfflineMode/offline_stage_quiz_details";
    String offline_memory_map_quiz_result = "OfflineMode/offline_memory_map_quiz_result";

    String all_foundation = "foundations/all_foundation";
    String get_alphabet = "foundations/get_alphabet";
    String post_body = "auth/update_coins";
    String get_animal = "foundations/get_animal";
    String all_currencies = "foundations/all_currencies";
    String get_currency = "foundations/get_currency";
    String get_fruit_vegetable = "foundations/get_fruit_vegetable";
    String foundation_questions = "foundations/foundation_questions";
    String foundation_quiz_result = "foundations/foundation_quiz_result";
    String all_drawing_images = "foundations/all_drawing_images";
    String all_foundation_learning = "foundations/all_foundation_learning";

    // Report
    String report_internal_exam = "Vishal/internal_exam";
    String report_curricular_report="Vishal/co_curricular_activity";
    String progression_report ="report/progression_report";
    String upcoming_event ="home/upcoming_event";

    String save_purchase_details = "vishal/save_purchase_details";
    String user_plan_details = "vishal/user_plan_details";
    String report_details = "Vishal/report_details";
    String set_bank_details = "Vishal/set_bank_details";

}
