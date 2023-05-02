package com.ksbm.ontu.utils;

import static com.ksbm.ontu.custom_class.Speach_Record_Activity.mTextToSpeech;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.main_screen.MainActivity;
import com.ksbm.ontu.main_screen.PaymentActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SweetAlt {

    public static void warningDialog(Context ctx, String title, String message, Boolean Cancellable, Boolean ShowCancelButton, String cancelBtnText, String confirmBtnText, SweetAlertDialog.OnSweetClickListener ButtonClicklistener) {

        SweetAlertDialog dialog = new SweetAlertDialog(ctx, SweetAlertDialog.WARNING_TYPE);

        dialog.setTitleText("\n" + title);
        if (message != null) {
            dialog.setContentText(message + "\n");
        }
        dialog.setConfirmText(confirmBtnText);
        dialog.setCancelable(Cancellable);

        if (ShowCancelButton) {

            dialog.setCancelButton(cancelBtnText, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });

        }

        if (ButtonClicklistener != null) {

            dialog.setConfirmClickListener(ButtonClicklistener);

        } else {

            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });
        }

        dialog.show();
    }

    public static void succesDialog(Context ctx, String title, String message, Boolean Cancellable, Boolean ShowCancelButton, String cancelBtnText, String confirmBtnText, SweetAlertDialog.OnSweetClickListener ButtonClicklistener) {

        SweetAlertDialog dialog = new SweetAlertDialog(ctx, SweetAlertDialog.SUCCESS_TYPE);

        dialog.setTitleText("\n" + title);
        if (message != null) {
            dialog.setContentText(message + "\n");
        }
        dialog.setConfirmText(confirmBtnText);
        dialog.setCancelable(Cancellable);

        if (ShowCancelButton) {

            dialog.setCancelButton(cancelBtnText, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });

        }

        if (ButtonClicklistener != null) {

            dialog.setConfirmClickListener(ButtonClicklistener);

        } else {

            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });
        }

        dialog.show();

    }

    public static void errorDialog(Context ctx, String title, String message, Boolean Cancellable, Boolean ShowCancelButton, String cancelBtnText, String confirmBtnText, SweetAlertDialog.OnSweetClickListener ButtonClicklistener) {

        SweetAlertDialog dialog = new SweetAlertDialog(ctx, SweetAlertDialog.ERROR_TYPE);

        dialog.setTitleText("\n" + title);
        if (message != null) {
            dialog.setContentText(message + "\n");
        }
        dialog.setConfirmText(confirmBtnText);
        dialog.setCancelable(Cancellable);

        if (ShowCancelButton) {

            dialog.setCancelButton(cancelBtnText, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });

        }

        if (ButtonClicklistener != null) {

            dialog.setConfirmClickListener(ButtonClicklistener);

        } else {

            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });
        }

        dialog.show();

    }

    public static void normalDialog(Context ctx, String title, String message, Boolean Cancellable, Boolean ShowCancelButton, String cancelBtnText, String confirmBtnText, SweetAlertDialog.OnSweetClickListener ButtonClicklistener) {

        SweetAlertDialog dialog = new SweetAlertDialog(ctx, SweetAlertDialog.NORMAL_TYPE);

        dialog.setTitleText("\n" + title);
        if (message != null) {
            dialog.setContentText(message + "\n\n");
        }

        //dialog.findViewById(R.id.confirm_button).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        dialog.setConfirmText(confirmBtnText);
        dialog.setCancelable(Cancellable);

        if (ShowCancelButton) {
            dialog.setCancelButton(cancelBtnText, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });

        }

        if (ButtonClicklistener != null) {
            dialog.setConfirmClickListener(ButtonClicklistener);

        } else {
            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });
        }

        dialog.show();

    }

    public static void normalDialogFinish(Context ctx, String title, String message, Boolean Cancellable, Boolean ShowCancelButton, String cancelBtnText, String confirmBtnText, SweetAlertDialog.OnSweetClickListener ButtonClicklistener) {

        SweetAlertDialog dialog = new SweetAlertDialog(ctx, SweetAlertDialog.CUSTOM_IMAGE_TYPE);

        dialog.setTitleText("\n" + title);
        if (message != null) {
            dialog.setContentText(message + "\n");
        }
        dialog.setConfirmText(confirmBtnText);
        dialog.setCancelable(Cancellable);

        if (ShowCancelButton) {

            dialog.setCancelButton(cancelBtnText, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                    ((YouTubeBaseActivity) ctx).finish();
                }
            });

        }

        if (ButtonClicklistener != null) {

            dialog.setConfirmClickListener(ButtonClicklistener);

        } else {

            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });
        }

        dialog.show();

    }

    public static void processDialog(Context ctx, String title, String message, Boolean Cancellable) {

        SweetAlertDialog dialog = new SweetAlertDialog(ctx, SweetAlertDialog.PROGRESS_TYPE);

        dialog.getProgressHelper().setBarColor(ctx.getResources().getColor(R.color.design_default_color_primary));
        dialog.setTitleText(title);
        if (message != null) {
            dialog.setContentText(message + "\n");
        }
        dialog.setCancelable(Cancellable);

        dialog.show();

    }

    public static void editTextDialog(Context ctx, EditText editText, String message, Boolean Cancellable, Boolean ShowCancelButton, String cancelBtnText, String confirmBtnText, SweetAlertDialog.OnSweetClickListener ButtonClicklistener) {

        SweetAlertDialog dialog = new SweetAlertDialog(ctx, SweetAlertDialog.NORMAL_TYPE);

        dialog.setTitleText("\n" + message + "\n");
        dialog.setCustomView(editText);
        dialog.setConfirmText(confirmBtnText);
        dialog.setCancelable(Cancellable);

        if (ShowCancelButton) {

            dialog.setCancelButton(cancelBtnText, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });

        }

        if (ButtonClicklistener != null) {

            dialog.setConfirmClickListener(ButtonClicklistener);

        } else {

            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });
        }

        dialog.show();

    }

    public static void customDialog(Context ctx, View customView, String message, Boolean Cancellable, Boolean ShowCancelButton, String cancelBtnText, String confirmBtnText, SweetAlertDialog.OnSweetClickListener ButtonClicklistener) {

        SweetAlertDialog dialog = new SweetAlertDialog(ctx, SweetAlertDialog.NORMAL_TYPE);

        dialog.setTitleText("\n" + message + "\n");
        dialog.setCustomView(customView);
        dialog.setConfirmText(confirmBtnText);
        dialog.setCancelable(Cancellable);

        if (ShowCancelButton) {

            dialog.setCancelButton(cancelBtnText, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });

        }

        if (ButtonClicklistener != null) {

            dialog.setConfirmClickListener(ButtonClicklistener);

        } else {

            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });
        }

        dialog.show();

    }

    public static void serverError(Context ctx, String btnText, SweetAlertDialog.OnSweetClickListener Buttonlistener) {

        SweetAlertDialog dialog = new SweetAlertDialog(ctx, SweetAlertDialog.WARNING_TYPE);

//        dialog.setTitleText("\n"+ctx.getResources().getString(R.string.title_server_problem));
//        dialog.setContentText(ctx.getResources().getString(R.string.msg_server_problem)+"\n");
        dialog.setConfirmText(btnText);
        dialog.setCancelable(true);

        if (Buttonlistener != null) {

            dialog.setConfirmClickListener(Buttonlistener);

        } else {

            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });
        }

        dialog.show();

    }

    public static void validationError(final Context ctx, final Integer Code) {

        SweetAlertDialog dialog = new SweetAlertDialog(ctx, SweetAlertDialog.WARNING_TYPE);

        if (Code == 699) {
            dialog.setTitleText("\n Demo WebPanel URL Detected");
            dialog.setContentText("Change the Server URL to yours or Please purchase a valid WebPanel License from : \n\n WWW.CODYHUB.COM \n");
            dialog.setConfirmText("OK");
        } else {
            dialog.setTitleText("\n License Validation Error");
            dialog.setContentText("Please Activate your License for App Source Code by opening a ticket in our Support Forum : \n\n WWW.DROIDOXY.COM/SUPPORT \n");
            dialog.setConfirmText("ACTIVATE");
            dialog.setCancelButton("PURCHASE", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    //    AppUtils.parse(ctx, App.getInstance().get("APP_LICENSE_URL","http://www.codyhub.com/item/video-rewards-android-app"));
                }
            });
        }

        dialog.setCancelable(false);

        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                if (Code == 699) {
                    //    AppUtils.parse(ctx, App.getInstance().get("WEB_LICENSE_URL","http://www.codyhub.com/item/video-rewards-webpanel"));
                } else {
                    //  AppUtils.parse(ctx,App.getInstance().get("VENDOR_SUPPORT_URL","http://www.droidoxy.com/support"));
                }
            }
        });

        dialog.show();

    }

    public static void showError(final Context context, String msg, String des) {
        SweetAlertDialog alertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        alertDialog.setTitleText(msg);
        alertDialog.setContentText(des);
        alertDialog.setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                ((Activity) context).finish();
            }
        });
        alertDialog.show();

    }

    public static void showErrorMessage(final Context context, String msg, String des) {
        final SweetAlertDialog alertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        alertDialog.setTitleText(msg);
        alertDialog.setContentText(des);
        alertDialog.setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                alertDialog.cancel();
            }
        });
        alertDialog.show();

    }

    public static void OpenCompleteLevelDialog(final Context context, String msg) {
        final Dialog dialog = new Dialog(context, R.style.CustomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_ans_dialog);
        //FragmentFreecoinBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.fragment_freecoin, null, false);
        // setContentView(binding.getRoot());

        ImageView Goback = (ImageView) dialog.findViewById(R.id.Goback);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);

        tv_msg.setText(msg);

        Goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }



    public static void OpenFreeCoinDialog(final Context context, String msg) {
        final Dialog dialog = new Dialog(context, R.style.CustomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_ans_dialog);
        //FragmentFreecoinBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.fragment_freecoin, null, false);
        // setContentView(binding.getRoot());

        ImageView Goback = (ImageView) dialog.findViewById(R.id.Goback);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);

        tv_msg.setText(msg);

        Goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTextToSpeech!=null){
                    mTextToSpeech.stop();
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    public static void OpenPaymentDialog(final Context context, String msg, ClickListionerss clickListionerss) {
        final Dialog dialog = new Dialog(context, R.style.CustomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.upgrade_courses_dialog);
        //FragmentFreecoinBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.fragment_freecoin, null, false);
        // setContentView(binding.getRoot());

        ImageView Goback = (ImageView) dialog.findViewById(R.id.Goback);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        CardView paynow = dialog.findViewById(R.id.pay_now);

        tv_msg.setText(msg);

        Goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListionerss.clickNo();
                dialog.dismiss();
            }
        });

        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListionerss.clickYes();
                dialog.dismiss();
            }
        });

        dialog.show();
    }



    public static void CountingTooltipDialog(final Context context, String msg) {
        final Dialog dialog = new Dialog(context, R.style.CustomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.counting_tooltip_dialog);
        //FragmentFreecoinBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.fragment_freecoin, null, false);
        // setContentView(binding.getRoot());

        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        TextView tv_counting_text = dialog.findViewById(R.id.tv_counting_text);

        tv_msg.setText(msg);

        String number_to_word = EnglishNumberToWords.convert(Long.parseLong(msg));
        tv_counting_text.setText(number_to_word);

        Speach_Record_Activity.speakOut(context, number_to_word, false);

        dialog.show();
    }
}
