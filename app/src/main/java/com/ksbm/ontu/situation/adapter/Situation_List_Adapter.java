package com.ksbm.ontu.situation.adapter;

import static android.content.Context.MODE_PRIVATE;
import static com.ksbm.ontu.utils.Constant.upgradePackage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ksbm.ontu.BR;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.SituationList1Binding;
import com.ksbm.ontu.databinding.SituationListBinding;
import com.ksbm.ontu.main_screen.PaymentActivity;
import com.ksbm.ontu.personality_dev.PersonalityVideoPlayer;
import com.ksbm.ontu.situation.SituationQuizActivity;
import com.ksbm.ontu.situation.SituationQuizPatternActivity;
import com.ksbm.ontu.situation.model.SituationModelResponse;
import com.ksbm.ontu.utils.ClickListionerss;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.MeraSharedPreferance;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;
import com.skydoves.elasticviews.ElasticLayout;

import java.util.List;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Situation_List_Adapter extends RecyclerView.Adapter<Situation_List_Adapter.BaseViewHolder> {
    private List<SituationModelResponse> dataModelList;
    Context context;
    MeraSharedPreferance meraSharedPreferance;
    int colordode;

    public Situation_List_Adapter(List<SituationModelResponse> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;
        meraSharedPreferance = MeraSharedPreferance.getInstance(context);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // ToodleListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
        //       R.layout.situation_list, parent, false);

        //  return new ViewHolder(binding);

        if (viewType == 0) {
            SituationListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.situation_list, parent, false);
            return new ViewHolder(binding);
        } else {
            SituationList1Binding binding1 = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.situation_list1, parent, false);
            return new ViewHolder1(binding1);
        }

    }


    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @SuppressLint({"CheckResult", "SetTextI18n"})
    @Override
    public void onBindViewHolder(BaseViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SituationModelResponse dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        // holder.itemRowBinding.setModel(dataModel);
        // holder.itemRowBinding.setItemClickListener(this);


        holder.tv_title.setText(dataModel.getSituationName());

        Utils.setRainbowColor(holder.tv_title);

        if (dataModel.getReward() != null) {
            holder.tv_prize.setVisibility(View.VISIBLE);
            holder.tv_prize.setText("Reward: " + dataModel.getReward());
        } else {
            holder.tv_prize.setVisibility(View.GONE);
        }
        Utils.setRandomBgImage(holder.rel_item, holder.ll_bottom, context);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(8));

       /* Glide.with(context)
                .load(Situation_Fragment.bg_image).placeholder(R.drawable.whitebackground).apply(requestOptions)
                .into(holder.rel_img);*/

//        if (String.valueOf(position).length()==1){
//            holder.tv_number.setText("0" + (position+1));
//        }else {
//            holder.tv_number.setText("" + (position+1));
//        }


        if (dataModel.getIconImage() != null && !dataModel.getIconImage().equalsIgnoreCase("")) {
            Glide.with(context)
                    .load(dataModel.getIconImage())
                    .into(holder.iv_icon);
        } else {
            holder.iv_icon.setVisibility(View.INVISIBLE);
        }

//        if(position==0)
//        {
//            holder.card_bg_colorhide.setVisibility(View.VISIBLE);
//        }
//
//        if(dataModelList.size()-1==position)
//        {
//            holder.card_bg_color.setVisibility(View.GONE);
//        }


        try {
            if (!dataModel.getiText().isEmpty() && dataModel.getiText() != null) {
                holder.information.setVisibility(View.VISIBLE);

                holder.information.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//
//                        Intent intent = new Intent(context, PersonalityVideoPlayer.class);
//                        intent.putExtra("FilePath", dataModel.getiText());
//                        intent.putExtra("Video_type", "Youtube");
//                        intent.putExtra("Player_type", "situation");
//                        intent.putExtra("Drive_Link", dataModel.getOtherLink());
//                        intent.putExtra("Video_Id", dataModel.getSituationId());
//                        intent.putExtra("Category_Id", "");
//                        intent.putExtra("ComeFrom", "Sit");
//                        intent.putExtra("first_open", "");
//                        intent.putExtra("Duration", "");
//                        context.startActivity(intent);
//
//                        final Dialog dialog = new Dialog(context);
//                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                        dialog.setCancelable(false);
//                        dialog.setCanceledOnTouchOutside(true);
//                        dialog.setContentView(R.layout.discription_layout);
//                        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//                       TextView  msg = dialog.findViewById(R.id.txtdescription);
//                        ImageView close = dialog.findViewById(R.id.closemasg);
//                        msg.setText(dataModel.getiText());
//                        close.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog.dismiss();
//                            }
//                        });
//                        dialog.show();


                        SweetAlt.OpenFreeCoinDialog(context, dataModel.getiText());
//                        Intent i = new Intent(Intent.ACTION_VIEW);
//                        i.setData(Uri.parse(dataModel.getiText()));
//                        context.startActivity(i);

                    }
                });

            } else {

                holder.information.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            holder.information.setVisibility(View.GONE);
        }


        if (!dataModel.getOtherLink().isEmpty() && dataModel.getOtherLink() != null) {
            holder.videolink.setVisibility(View.VISIBLE);

            holder.videolink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (position<=3){
                        String URL = dataModel.getOtherLink().replace("http:", "https:");

                        Intent intent = new Intent(context, PersonalityVideoPlayer.class);
                        intent.putExtra("FilePath", URL);
                        intent.putExtra("Video_type", "Youtube");
                        intent.putExtra("Player_type", "youtube");
                        intent.putExtra("Drive_Link", URL);
                        intent.putExtra("Coin", dataModel.getReward());
                        intent.putExtra("Video_Id", dataModel.getSituationId());
                        intent.putExtra("Category_Id", "");
                        intent.putExtra("ComeFrom", "Sit");
                        intent.putExtra("first_open", "");
                        intent.putExtra("Duration", "");
                        context.startActivity(intent);
                    }else{
                        if (meraSharedPreferance.isOpenMarketGet()&& !meraSharedPreferance.isPackageUpgraded()){
                            SweetAlt.OpenPaymentDialog(context, Constant.upgrade_course, new ClickListionerss() {
                                @Override
                                public void clickYes() {
                                    context.startActivity(new Intent(context, PaymentActivity.class).putExtra("price",upgradePackage));
                                }
                                @Override
                                public void clickNo() {

                                }
                            });
                        }else{
                            String URL = dataModel.getOtherLink().replace("http:", "https:");

                            Intent intent = new Intent(context, PersonalityVideoPlayer.class);
                            intent.putExtra("FilePath", URL);
                            intent.putExtra("Video_type", "Youtube");
                            intent.putExtra("Player_type", "youtube");
                            intent.putExtra("Drive_Link", URL);
                            intent.putExtra("Coin", dataModel.getReward());
                            intent.putExtra("Video_Id", dataModel.getSituationId());
                            intent.putExtra("Category_Id", "");
                            intent.putExtra("ComeFrom", "Sit");
                            intent.putExtra("first_open", "");
                            intent.putExtra("Duration", "");
                            context.startActivity(intent);
                        }
                    }





//                    String URL = dataModel.getOtherLink().replace("http:", "https:");
//
//                    Intent intent = new Intent(context, PersonalityVideoPlayer.class);
//                    intent.putExtra("FilePath", URL);
//                    intent.putExtra("Video_type", "Youtube");
//                    intent.putExtra("Player_type", "youtube");
//                    intent.putExtra("Drive_Link", URL);
//                    intent.putExtra("Coin", dataModel.getReward());
//                    intent.putExtra("Video_Id", dataModel.getSituationId());
//                    intent.putExtra("Category_Id", "");
//                    intent.putExtra("ComeFrom", "Sit");
//                    intent.putExtra("first_open", "");
//                    intent.putExtra("Duration", "");
//                    context.startActivity(intent);
//
//                    Intent intent = new Intent(context, WebViewActivity.class);
//                    intent.putExtra("Drive_Link", URL);
//                    intent.putExtra("ComeFrom", "Sit");
//                    intent.putExtra("name", dataModel.getSituationName());
//                    context.startActivity(intent);

                }
            });

        } else {
            holder.videolink.setVisibility(View.GONE);
        }


        Random rnd = new Random();
        int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
//        holder.card_bg_color1.setBackgroundColor(currentColor);
//       // Utils.setRandomColorWord(holder.card_bg_color, context);
//       // Utils.setRandomColorWord(holder.card_bg_color1, context);
//        colordode = currentColor;
//        holder.card_bg_color.setBackgroundColor(currentColor);
        holder.rel_item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (position<=3){
                    Speach_Record_Activity.speakOut(context, dataModel.getSituationName(), false);
                    ResumeQuize(dataModel.getSituationId(), dataModel.getSituationName(), dataModel.getSituationId(), dataModel.getSituationName());
                }else{

                    if (meraSharedPreferance.isOpenMarketGet()&& !meraSharedPreferance.isPackageUpgraded()){
                        SweetAlt.OpenPaymentDialog(context, Constant.upgrade_course, new ClickListionerss() {
                            @Override
                            public void clickYes() {
                                context.startActivity(new Intent(context, PaymentActivity.class).putExtra("price",upgradePackage));
                            }
                            @Override
                            public void clickNo() {

                            }
                        });
                    }else{
                        Speach_Record_Activity.speakOut(context, dataModel.getSituationName(), false);
                        ResumeQuize(dataModel.getSituationId(), dataModel.getSituationName(), dataModel.getSituationId(), dataModel.getSituationName());
                    }

                }


            }
        });

    }


    public void ResumeQuize(String Id, String Name, String RId, String RName) {

        SharedPreferences prefs = context.getSharedPreferences("ResumeQuize", MODE_PRIVATE);
        boolean value = prefs.getBoolean("value", false);
        String situation_id1 = prefs.getString("situation_id", "0");

        if (value && situation_id1.equals(Id)) {
            new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText(context.getResources().getString(R.string.app_name) + "")
                    .setContentText("Are you want to resume or play quize from bigining?")
                    .setConfirmText("Resume")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {

                            sDialog.dismissWithAnimation();

                            Intent intent = new Intent(context, SituationQuizPatternActivity.class);
                            intent.putExtra("situation_id", Id);
                            intent.putExtra("situation_name", Name);
                            context.startActivity(intent);

                        }
                    })
                    .setCancelButton("Start", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();

                            SharedPreferences.Editor editor = context.getSharedPreferences("ResumeQuize", MODE_PRIVATE).edit();
                            editor.clear();
                            editor.apply();
                            Intent intent = new Intent(context, SituationQuizActivity.class);
                            intent.putExtra("situation_id", Id);
                            intent.putExtra("situation_name", Name);
                            context.startActivity(intent);
                        }
                    })
                    .show();
        } else {
            Intent intent = new Intent(context, SituationQuizActivity.class);
            intent.putExtra("situation_id", Id);
            intent.putExtra("situation_name", Name);
            context.startActivity(intent);
        }

    }


    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends BaseViewHolder {
        public SituationListBinding itemRowBinding;

        public ViewHolder(SituationListBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }

    public class ViewHolder1 extends BaseViewHolder {
        public SituationList1Binding itemRowBinding;

        public ViewHolder1(SituationList1Binding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }


    public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_number, tv_prize;
        ImageView iv_icon, videolink, information;
        //CardView card_bg_color1;
        ElasticLayout rel_item1;
        RelativeLayout rel_item;
        ImageView rel_img;
        LinearLayout ll_bottom;
        //RelativeLayout card_bg_color,rl;
        //RelativeLayout rel_situation_item,card_bg_colorhide;

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);

            rel_item1 = itemView.findViewById(R.id.rel_item1);
            ll_bottom = itemView.findViewById(R.id.ll_bottom);
            rel_img = itemView.findViewById(R.id.rel_img);
            rel_item = itemView.findViewById(R.id.rel_item);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_prize = itemView.findViewById(R.id.tv_prize);
            //tv_number= itemView.findViewById(R.id.tv_number);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            videolink = itemView.findViewById(R.id.videolink);
            information = itemView.findViewById(R.id.information);
//            card_bg_color1= itemView.findViewById(R.id.card_bg_color1);
//            card_bg_color= itemView.findViewById(R.id.card_bg_color);
//            rel_situation_item= itemView.findViewById(R.id.rel_situation_item);
//            rl= itemView.findViewById(R.id.rl);
//            card_bg_colorhide= itemView.findViewById(R.id.card_bg_colorhide);
        }

        // delegate binding to child class
        protected abstract void bind(Object obj);
    }

    // method for filtering our recyclerview items.
    public void filterList(List<SituationModelResponse> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        dataModelList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }
}
