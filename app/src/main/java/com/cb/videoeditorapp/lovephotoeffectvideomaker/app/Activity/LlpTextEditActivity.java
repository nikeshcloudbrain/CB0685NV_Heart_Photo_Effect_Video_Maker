package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Adapter.BgColorAdapterAnimation;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Adapter.ColorLoveAdapterAnimation;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Adapter.FontAdapterAnimation;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Sticker.DrawableStickerPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Sticker.StickerPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.Constant;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.BackInterAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.ListBannerAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.databinding.ActivityTextEditPhotoBinding;
import com.preference.PowerPreference;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LlpTextEditActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.img_txt_edit_Back)
    ImageView img_txt_edit_Back;
    @BindView(R.id.crd_txt_done)
    LinearLayout crd_txt_done;
    @BindView(R.id.img_ET_font)
    ImageView img_ET_font;
    @BindView(R.id.txt_ET_font)
    TextView txt_ET_font;
    @BindView(R.id.lin_ET_Font)
    LinearLayout lin_ET_Font;
    @BindView(R.id.img_ET_Color)
    ImageView img_ET_Color;
    @BindView(R.id.txt_ET_Color)
    TextView txt_ET_Color;
    @BindView(R.id.lin_ET_Color)
    LinearLayout lin_ET_Color;
    @BindView(R.id.img_ET_Shadow)
    ImageView img_ET_Shadow;
    @BindView(R.id.txt_ET_Shadow)
    TextView txt_ET_Shadow;
    @BindView(R.id.textsettings)
    TextView textsettings;
    @BindView(R.id.lin_ET_Shadow)
    LinearLayout lin_ET_Shadow;
    @BindView(R.id.img_ET_Bg)
    ImageView img_ET_Bg;
    @BindView(R.id.txt_ET_Bg)
    TextView txt_ET_Bg;
    @BindView(R.id.lin_ET_Bg)
    LinearLayout lin_ET_Bg;
    @BindView(R.id.img_ET_Opacity)
    ImageView img_ET_Opacity;
    @BindView(R.id.txt_ET_Opacity)
    TextView txt_ET_Opacity;
    @BindView(R.id.lin_ET_Opacity)
    LinearLayout lin_ET_Opacity;
    @BindView(R.id.edt_filter_text)
    EditText edt_filter_text;
    @BindView(R.id.ll_screenshort)
    LinearLayout ll_screenshort;
    @BindView(R.id.gridFont)
    GridView gridFont;
    @BindView(R.id.lin_ET_GridFont)
    LinearLayout lin_ET_GridFont;
    @BindView(R.id.gridTextColor)
    GridView gridTextColor;
    @BindView(R.id.lin_ET_GridTextColor)
    LinearLayout lin_ET_GridTextColor;
    @BindView(R.id.gridTextBg)
    GridView gridTextBg;
    @BindView(R.id.lin_ET_GridTextBg)
    LinearLayout lin_ET_GridTextBg;
    @BindView(R.id.seekBarOpacity)
    SeekBar seekBarOpacity;
    @BindView(R.id.seekBarShadow)
    SeekBar seekBarShadow;
    @BindView(R.id.lin_ET_GridTextOpacity)
    LinearLayout lin_ET_GridTextOpacity;

ActivityTextEditPhotoBinding binding;
    public static Bitmap bmap_text_new = null;

    int alpha_bg = 255;
    float shadow_bg = 10;
    boolean flagTempShadow;

    String[] listfont;
    public static int[] fontColorArray;
    public static int[] fontBgColorArray;

    @Override
    protected void onResume() {
        super.onResume();

        if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        new ListBannerAds().showListBannerAds(this, binding.includedBanner.nativeAdMini, binding.includedBanner.adSpaceMini);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityTextEditPhotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ButterKnife.bind(this);

        getId();
    }

    private void getId() {

        seekBarOpacity.setProgress(255);
        seekBarOpacity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


                edt_filter_text.setAlpha((float) progress / (float) (seekBar.getMax()));
                alpha_bg = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        seekBarShadow.setProgress(10);
        seekBarShadow.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress==0){
                    flagTempShadow=false;
                }else {
                    flagTempShadow=true;

                }
                if (flagTempShadow) {
                    edt_filter_text.setShadowLayer(shadow_bg, shadow_bg, shadow_bg, -10);
                } else {
                    edt_filter_text.setShadowLayer(3.0f, -1.0f, 1.0f, R.color.MainColor);

                }

                shadow_bg = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        loadFilterColors();

        img_ET_font.setColorFilter(getResources().getColor(R.color.transparent));
        txt_ET_font.setTextColor(getResources().getColor(R.color.colorPrimary));
        hideAndShowLayout(lin_ET_GridFont);

        img_txt_edit_Back.setOnClickListener(this);
        crd_txt_done.setOnClickListener(this);

        lin_ET_Font.setOnClickListener(this);
        lin_ET_Color.setOnClickListener(this);
        lin_ET_Shadow.setOnClickListener(this);
        lin_ET_Bg.setOnClickListener(this);
        lin_ET_Opacity.setOnClickListener(this);


        loadTextFonts();
        loadTextColors();
        loadTextBgColors();

    }

    @Override
    public void onClick(View view) {

        closeKeyboard();
        int v = view.getId();
        boolean z = false;
        if (v != R.id.crd_txt_done) {
            switch (v) {

                case R.id.img_txt_edit_Back:
                    onBackPressed();
                    return;

                case R.id.lin_ET_Font:
                    edtText_method_color(img_ET_font, txt_ET_font);
                    hideAndShowLayout(lin_ET_GridFont);
                    return;

                case R.id.lin_ET_Color:
                    edtText_method_color(img_ET_Color, txt_ET_Color);
                    hideAndShowLayout(lin_ET_GridTextColor);
                    return;

                case R.id.lin_ET_Shadow:
                    edtText_method_color(img_ET_Shadow, txt_ET_Shadow);
                    lin_ET_GridFont.setVisibility(View.GONE);
                    lin_ET_GridTextColor.setVisibility(View.GONE);
                    lin_ET_GridTextBg.setVisibility(View.GONE);
                    lin_ET_GridTextOpacity.setVisibility(View.GONE);


                   // flagTempShadow = z;
                    textsettings.setText("Text Shadow");
                    seekBarOpacity.setVisibility(View.GONE);
                    seekBarShadow.setVisibility(View.VISIBLE);
                    hideAndShowLayout(lin_ET_GridTextOpacity);

                    return;
                case R.id.lin_ET_Bg:
                    edtText_method_color(img_ET_Bg, txt_ET_Bg);
                    hideAndShowLayout(lin_ET_GridTextBg);


                    return;

                case R.id.lin_ET_Opacity:
                    textsettings.setText("Text Opacity");

                    edtText_method_color(img_ET_Opacity, txt_ET_Opacity);
                    seekBarOpacity.setVisibility(View.VISIBLE);
                    seekBarShadow.setVisibility(View.GONE);
                    hideAndShowLayout(lin_ET_GridTextOpacity);


                    return;

                default:
                    return;
            }
        }

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocus = getCurrentFocus();
        if (currentFocus == null) {
            currentFocus = new View(this);
        }
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
        if (edt_filter_text.getText().toString().length() == 0) {
            Toast.makeText(this, "Please Enter Some Text", Toast.LENGTH_SHORT).show();
            return;
        }
        edt_filter_text.setCursorVisible(false);
        bmap_text_new = createBitmapFromLayout(ll_screenshort);

        Drawable d = new BitmapDrawable(getResources(), bmap_text_new);

        int number = getIntent().getIntExtra("number", 0);
        if (number == 0) {
            LlpSingleActivity.stickerView.addSticker(new DrawableStickerPhoto(d), StickerPhoto.Position.CENTER | StickerPhoto.Position.CENTER);
        } else if (number == 1) {
            LlpMultipleActivity.stickerView.addSticker(new DrawableStickerPhoto(d), StickerPhoto.Position.CENTER | StickerPhoto.Position.CENTER);
        } else if (number == 2) {
            LlpVideoActivity.stickerView.addSticker(new DrawableStickerPhoto(d), StickerPhoto.Position.CENTER | StickerPhoto.Position.CENTER);
        } else if (number == 3) {
            LlpImageEditActivity.stickerView.addSticker(new DrawableStickerPhoto(d), StickerPhoto.Position.CENTER | StickerPhoto.Position.CENTER);
        }

        finish();

    }

    private void edtText_method_color(ImageView clr_img, TextView clr_txt) {

        img_ET_font.setColorFilter(getResources().getColor(R.color.colorBlack));
        txt_ET_font.setTextColor(getResources().getColor(R.color.colorBlack));
        img_ET_Color.setColorFilter(getResources().getColor(R.color.colorBlack));
        txt_ET_Color.setTextColor(getResources().getColor(R.color.colorBlack));
        img_ET_Shadow.setColorFilter(getResources().getColor(R.color.colorBlack));
        txt_ET_Shadow.setTextColor(getResources().getColor(R.color.colorBlack));
        img_ET_Bg.setColorFilter(getResources().getColor(R.color.colorBlack));
        txt_ET_Bg.setTextColor(getResources().getColor(R.color.colorBlack));
        img_ET_Opacity.setColorFilter(getResources().getColor(R.color.colorBlack));
        txt_ET_Opacity.setTextColor(getResources().getColor(R.color.colorBlack));

        clr_img.setColorFilter(getResources().getColor(R.color.transparent));
        clr_txt.setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    private void hideAndShowLayout(LinearLayout linearLayout) {
        lin_ET_GridFont.setVisibility(View.GONE);
        lin_ET_GridTextColor.setVisibility(View.GONE);
        lin_ET_GridTextBg.setVisibility(View.GONE);
        lin_ET_GridTextOpacity.setVisibility(View.GONE);

        linearLayout.setVisibility(View.VISIBLE);
    }

    private void closeKeyboard() {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    public Bitmap createBitmapFromLayout(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocus = getCurrentFocus();
        if (currentFocus == null) {
            currentFocus = new View(this);
        }
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
        Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(createBitmap));
        return createBitmap;
    }

    private void loadTextFonts() {
        try {
            this.listfont = getAssets().list("fonts");
            if (this.listfont != null) {
                for (int i = 0; i < this.listfont.length; i++) {
                    String[] strArr = this.listfont;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("fonts/");
                    stringBuilder.append(this.listfont[i]);
                    strArr[i] = stringBuilder.toString();
                }
                this.gridFont.setAdapter(new FontAdapterAnimation(this.listfont, this));
                this.gridFont.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        edt_filter_text.setTypeface(Typeface.createFromAsset(getAssets(), listfont[position]));

                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFilterColors() {
        int i;
        TypedArray obtainTypedArray = getApplication().getResources().obtainTypedArray(R.array.textcolorarray);
        fontColorArray = new int[obtainTypedArray.length()];
        for (i = 0; i < obtainTypedArray.length(); i++) {
            fontColorArray[i] = obtainTypedArray.getColor(i, 0);
        }
        obtainTypedArray.recycle();
        obtainTypedArray = getApplication().getResources().obtainTypedArray(R.array.textcolorbgarray);
        fontBgColorArray = new int[obtainTypedArray.length()];
        for (i = 0; i < obtainTypedArray.length(); i++) {
            fontBgColorArray[i] = obtainTypedArray.getColor(i, 0);
        }
        obtainTypedArray.recycle();
    }

    private void loadTextBgColors() {
        int[] iArr = fontBgColorArray;
        if (iArr != null) {
            gridTextBg.setAdapter(new BgColorAdapterAnimation(iArr, this));
            gridTextBg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    edt_filter_text.setBackgroundColor(fontBgColorArray[position]);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new BackInterAds().showInterAds(this, new BackInterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {
                finish();
            }
        });

    }



    private void loadTextColors() {
        int[] iArr = fontColorArray;
        if (iArr != null) {
            this.gridTextColor.setAdapter(new ColorLoveAdapterAnimation(iArr, this));
            this.gridTextColor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    edt_filter_text.setTextColor(fontColorArray[position]);
                    edt_filter_text.setHintTextColor(fontColorArray[position]);
                }
            });
        }
    }
}
