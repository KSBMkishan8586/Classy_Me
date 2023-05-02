package com.ksbm.ontu.foundation.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ksbm.ontu.R;
import com.ksbm.ontu.foundation.adapter.BookMarkAdapter;
import com.ksbm.ontu.foundation.adapter.FoundationList_Adapter;
import com.ksbm.ontu.foundation.model.BookMarkModel;
import com.ksbm.ontu.foundation.model.FoundationTypeModel;
import com.ksbm.ontu.main_screen.DrawerActivity;
import com.ksbm.ontu.main_screen.MainActivity;
import com.ksbm.ontu.databinding.FragmentFoundationsListBinding;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Foundations_List_Fragment extends Fragment {
    FragmentFoundationsListBinding binding;
    SessionManager sessionManager;
    BookMarkAdapter friendsAdapter;
    public static String deleteid = "";
    public static List<BookMarkModel> personality=new ArrayList<BookMarkModel>();
    List<BookMarkModel> personalityModels=new ArrayList<BookMarkModel>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_home, null);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_foundations_list, container, false);
        View view = binding.getRoot();//using data binding
        sessionManager = new SessionManager(getActivity());
        String Title="";
        if (getArguments() != null) {
            Title = getArguments().getString("Title");
            if (Title.equalsIgnoreCase("Book Mark")){
                binding.tvTitle.setText("Bookmark");
            }else{
                binding.tvTitle.setText(Title);
            }
        }

        String finalTitle = Title;
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(finalTitle.equalsIgnoreCase("Book Mark"))
                {
                    getActivity().finish();
                }
                else
                {
                    ((MainActivity) getActivity()).onBackPressed();
                }
            }
        });

        String finalTitle1 = Title;
        binding.ivCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.screenShot(binding.rlScreen,getActivity());
                binding.ivCoin.setEnabled(false);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if(finalTitle1.equalsIgnoreCase("Book Mark"))
                        {
                            binding.ivCoin.setEnabled(true);
                            GetBookMark(getActivity());
                        }
                        else {
                            binding.ivCoin.setEnabled(true);
                        }

                    }
                }, 2000);



            }
        });





        if(Title.equalsIgnoreCase("Book Mark"))
        {
            GetBookMark(getActivity());
           // Toast.makeText(getActivity(), "Comming Soon...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            GetToodleCourse();
        }


        try {
            GridLayoutManager manager=new GridLayoutManager(getContext(),2);
            binding.recyclelist1.setLayoutManager(manager);
            binding.recyclelist1.setNestedScrollingEnabled(false);
            friendsAdapter = new BookMarkAdapter(getActivity(),personalityModels,binding.recyclelist1);
            binding.recyclelist1.setAdapter(friendsAdapter);
        }
        catch (Exception e)
        {

        }

        binding.deletebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string="";
                for (int i = 0;i<personality.size();i++)
                {
                    if(personality.get(i).getisselect())
                    {
                        string = string + personality.get(i).getid()+",";
                    }
                }



                if(string.isEmpty())
                {
                    Toast.makeText(getActivity(), "Nothing here to delete.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    string = string.substring(0, (string.length() - 1));
                    deleteBookMark(getActivity(),string);
                }


            }
        });

        binding.selectebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string="";
                for (int i = 0;i<personality.size();i++)
                {

                    BookMarkModel moviee = new BookMarkModel();
                    if(personality.get(i).getisselect())
                    {
                        moviee.setisselect(false);
                    }
                    else
                    {
                        moviee.setisselect(true);
                    }

                    moviee.setid(personality.get(i).getid());
                    moviee.setname(personality.get(i).getname());
                    moviee.setpassword(personality.get(i).getpassword());
                    personality.set(i,moviee);
                }
                friendsAdapter.notifyDataSetChanged();
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

    @SuppressLint("NotifyDataSetChanged")
    private void GetToodleCourse() {

        binding.recyclelist1.setVisibility(View.GONE);

        binding.recyclelist.setVisibility(View.VISIBLE);
        List<FoundationTypeModel> personalityModels=new ArrayList<FoundationTypeModel>();
        personalityModels.add(new FoundationTypeModel("Basics", R.drawable.basics_foundation));
        personalityModels.add(new FoundationTypeModel("Directions", R.drawable.direction_iocn));
        personalityModels.add(new FoundationTypeModel("Date", R.drawable.date));
        personalityModels.add(new FoundationTypeModel("Time", R.drawable.time));
        personalityModels.add(new FoundationTypeModel("Colours", R.drawable.colors_icon));
        personalityModels.add(new FoundationTypeModel("Body Parts", R.drawable.full_body_iconnn));

        FoundationList_Adapter friendsAdapter = new FoundationList_Adapter(personalityModels,getActivity(),getActivity());
        binding.setPersonalityAdapter(friendsAdapter);//set databinding adapter
        friendsAdapter.notifyDataSetChanged();

    }

//    static myDbAdapter helper;
//    @SuppressLint("NotifyDataSetChanged")
//    private void GetBookMark1() {
//
//        try {
//            helper = new myDbAdapter(getActivity());
//            List<BookMarkModel> personalityModels=new ArrayList<BookMarkModel>();
//            LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
//            binding.recyclelist1.setLayoutManager(manager);
//            binding.recyclelist1.setNestedScrollingEnabled(false);
//            personalityModels = helper.readCourses();
//            BookMarkAdapter friendsAdapter = new BookMarkAdapter(getActivity(),personalityModels,binding.recyclelist1);
//            binding.recyclelist1.setAdapter(friendsAdapter);
//        }
//        catch (Exception e)
//        {
//            Toast.makeText(getActivity(), e+"", Toast.LENGTH_SHORT).show();
//        }
//
//    }

    public void GetBookMark1() {







//        binding.recyclelist1.setVisibility(View.VISIBLE);
//        binding.recyclelist.setVisibility(View.GONE);
//
//        RequestQueue queue = Volley.newRequestQueue(getActivity());
//        StringRequest sr = new StringRequest(Request.Method.POST, "API_URL", new com.android.volley.Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                try {
//                    JSONObject object = new JSONObject(response);
//                    JSONObject obj = object.getJSONObject("data");
//                    String walletBallence = obj.getString("wallet");
//                    JSONArray array = obj.getJSONArray("list");
//                    try {
////                        helper = new myDbAdapter(getActivity());
//                        List<BookMarkModel> personalityModels=new ArrayList<BookMarkModel>();
//                        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
//                        binding.recyclelist1.setLayoutManager(manager);
//                        binding.recyclelist1.setNestedScrollingEnabled(false);
////                        personalityModels = helper.readCourses();
//                        BookMarkAdapter friendsAdapter = new BookMarkAdapter(getActivity(),personalityModels,binding.recyclelist1);
//                        binding.recyclelist1.setAdapter(friendsAdapter);
//                    }
//                    catch (Exception e)
//                    {
//                        Toast.makeText(getActivity(), e+"", Toast.LENGTH_SHORT).show();
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new com.android.volley.Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//
//        }){
//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("driver_id",securePreferences.GetPreference(SecurePreferences.userId)+"");
//                params.put("ok","done");
//                return params;
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("Content-Type","application/x-www-form-urlencoded");
//                return params;
//            }
//        };
//        queue.add(sr);
    }



    public void deleteBookMark(Context context,String idimage)
    {
        sessionManager = new SessionManager(context);
        final ProgressDialog progressDialog = new ProgressDialog(context, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.DEPRECATED_GET_OR_POST, "http://classyme.org/index.php/apis/bookmarks/bookmark_delete", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    progressDialog.dismiss();
                    JSONObject object = new JSONObject(response);
                    try {
                        String status = object.getString("status");
                        if(status.equals("1"))
                        {
                            Toast.makeText(context, "Screenshot deleted.", Toast.LENGTH_SHORT).show();
                            GetBookMark(getActivity());
                        }
                        else
                        {
                            Toast.makeText(context, "Screenshot not deleted.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(context, "Screenshot not deleted.", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Screenshot not deleted.", Toast.LENGTH_SHORT).show();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(context, "Screenshot not deleted.", Toast.LENGTH_SHORT).show();
            }

        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("userid",sessionManager.getUser().getUserid()+"");
                params.put("id",idimage);
//                params.put("image",image);
                params.put("API-KEY", "123456");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
//                params.put("API-KEY", "123456");
                return params;
            }
        };
        queue.add(sr);

    }






    public void GetBookMark(Context context)
    {
        final ProgressDialog progressDialog = new ProgressDialog(context, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.DEPRECATED_GET_OR_POST, "http://classyme.org/index.php/apis/bookmarks/bookmark_list", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    progressDialog.dismiss();
                    JSONObject object = new JSONObject(response);
                    try {
                        String status = object.getString("status");
                        if(status.equals("1"))
                        {

                         JSONArray array = object.getJSONArray("response");

                            personalityModels.clear();
                         personality.clear();
                         for (int i =0;i<array.length();i++)
                         {
                             JSONObject objecta = array.getJSONObject(i);
                             BookMarkModel bookMarkModel = new BookMarkModel();
                             bookMarkModel.setid(objecta.getString("id"));
                             bookMarkModel.setname(objecta.getString("name"));
                             bookMarkModel.setpassword(objecta.getString("image"));
                             bookMarkModel.setisselect(false);
                             personalityModels.add(bookMarkModel);
                         }


                         if(personalityModels.size()>0)
                         {
                             binding.bookll.setVisibility(View.VISIBLE);
                             personality.addAll(personalityModels);
                             //LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                             friendsAdapter.notifyDataSetChanged();
                         }

                        }
                        else
                        {   personality.clear();
                            personalityModels.clear();
                            Toast.makeText(context, "Screenshot not found.", Toast.LENGTH_SHORT).show();
                            friendsAdapter.notifyDataSetChanged();
                        }
                    }
                    catch (Exception e)
                    {
                    }

                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Screenshot not found.", Toast.LENGTH_SHORT).show();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(context, "Screenshot not saved, try again later.", Toast.LENGTH_SHORT).show();
            }

        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("userid",sessionManager.getUser().getUserid()+"");
//                params.put("name",name);
//                params.put("image",image);
                params.put("API-KEY", "123456");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
//                params.put("API-KEY", "123456");
                return params;
            }
        };
        queue.add(sr);

    }



}
