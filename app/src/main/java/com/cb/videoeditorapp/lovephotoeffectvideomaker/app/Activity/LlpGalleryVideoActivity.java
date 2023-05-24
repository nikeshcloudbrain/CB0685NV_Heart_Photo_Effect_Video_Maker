package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.UtilsFoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model.FolderModelPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model.ImageModelPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model.SelectionModelPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.Constant;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.BackInterAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.InterAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.ListBannerAds;
import com.bumptech.glide.Glide;
import com.preference.PowerPreference;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.databinding.ActivityGalleryVideoPhotoBinding;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LlpGalleryVideoActivity extends AppCompatActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.grid_imgfolder)
    GridView grid_imgfolder;
    @BindView(R.id.grid_subimage)
    GridView grid_subimage;
    @BindView(R.id.linGrid)
    LinearLayout linGrid;
    @BindView(R.id.image_nodata)
    TextView image_nodata;
  ActivityGalleryVideoPhotoBinding binding;

    public static ArrayList<FolderModelPhoto> folderData = new ArrayList<>();
    public static ArrayList<ImageModelPhoto> drag_imageData = new ArrayList<>();
    boolean boolean_folder;
    CustomListAdapter customListAdapter;
    int selectedPosition = 0;
    CustomImageAdepter customImageAdepter;
    static ArrayList<SelectionModelPhoto> selectionData = new ArrayList();

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
        binding=ActivityGalleryVideoPhotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ButterKnife.bind(this);

        selectionData = new ArrayList();

        getId();

        if (folderData.size() <= 0) {
            UtilsFoto.dilogShow(this);
            fn_imagespath();
        } else {
            loadData();
        }

    }

    private void getId() {
        txtTitle.setText("Gallery");

        ivBack.setOnClickListener(v -> {
            if (grid_imgfolder.getVisibility() == View.VISIBLE) {
                onBackPressed();
                return;
            }
            try {
                txtTitle.setText("Gallery");
                grid_subimage.setVisibility(View.GONE);
                grid_imgfolder.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    public void loadData() {
        UtilsFoto.dilogHide(this);


        customListAdapter = new CustomListAdapter();
        grid_imgfolder.setHorizontalSpacing(5);
        grid_imgfolder.setVerticalSpacing(5);
        txtTitle.setText("Gallery");
        grid_imgfolder.setAdapter(this.customListAdapter);

        if (folderData.size() > 0) {
            linGrid.setVisibility(View.VISIBLE);
            image_nodata.setVisibility(View.GONE);
            final File file = new File((folderData.get(0)).getFolderName());
            customImageAdepter = new CustomImageAdepter(0);
            grid_subimage.setAdapter(customImageAdepter);
            txtTitle.setText(file.getName());
        } else {
            linGrid.setVisibility(View.GONE);
            image_nodata.setVisibility(View.VISIBLE);
        }

    }

    public ArrayList<FolderModelPhoto> fn_imagespath() {

        if (grid_imgfolder.getVisibility() == View.VISIBLE) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    folderData.clear();
                    folderData = new ArrayList();

                    int int_position = 0;
                    Uri uri;
                    Cursor cursor;
                    int column_index_data, column_index_folder_name;

                    String absolutePathOfImage = null;
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

                    String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Video.Media.BUCKET_DISPLAY_NAME};

                    final String orderBy = MediaStore.Video.Media.DATE_TAKEN;
                    cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

                    column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                    column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);

                    while (cursor.moveToNext()) {
                        try {
                            absolutePathOfImage = cursor.getString(column_index_data);
//                    FunctionsPhoto.LogE("Column", absolutePathOfImage);
//                    FunctionsPhoto.LogE("Folder", cursor.getString(column_index_folder_name));
                            for (int i2 = 0; i2 < folderData.size(); i2++) {
                                if ((folderData.get(i2)).getFolderName().equals(cursor.getString(column_index_folder_name))) {
                                    boolean_folder = true;
                                    int_position = i2;
                                    break;
                                }
                                boolean_folder = false;
                            }
                            if (boolean_folder) {
                                ArrayList arrayList = new ArrayList();
                                ImageModelPhoto imageData = new ImageModelPhoto();
                                imageData.setImagePath(absolutePathOfImage);
                                imageData.setSelected(Boolean.valueOf(false));
                                imageData.setCount(0);
                                arrayList.addAll(((FolderModelPhoto) folderData.get(int_position)).getImageData());
                                arrayList.add(imageData);
                                (folderData.get(int_position)).setImageData(arrayList);
                            } else {
                                ArrayList arrayList2 = new ArrayList();
                                ImageModelPhoto imageData2 = new ImageModelPhoto();
                                imageData2.setImagePath(absolutePathOfImage);
                                imageData2.setSelected(Boolean.valueOf(false));
                                imageData2.setCount(0);
                                arrayList2.add(imageData2);
                                FolderModelPhoto folderData1 = new FolderModelPhoto();
                                folderData1.setFolderName(cursor.getString(column_index_folder_name));
                                folderData1.setImageData(arrayList2);
                                folderData.add(folderData1);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadData();
                        }
                    });
                }
            }).start();

        }
        return folderData;
    }

    @Override
    public void onBackPressed() {
        new BackInterAds().showInterAds(this, new BackInterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {
                if (grid_imgfolder.getVisibility() == View.VISIBLE) {
                    Intent i = new Intent(LlpGalleryVideoActivity.this, LlpHomeActivity.class);
                    startActivity(i);
                    finish();
                    return;
                }
                try {
                    txtTitle.setText("Gallery");
                    grid_subimage.setVisibility(View.GONE);
                    grid_imgfolder.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    class CustomListAdapter extends BaseAdapter {
        LayoutInflater layoutInflater;
        ViewHolder viewGroup;

        class ViewHolder {
            ImageView img_galleryfolder;
            TextView txt_folder_imgCount;
            TextView txt_foldername;

            ViewHolder() {
            }
        }

        public long getItemId(int i) {
            return (long) i;
        }

        CustomListAdapter() {
            this.layoutInflater = LayoutInflater.from(LlpGalleryVideoActivity.this.getApplicationContext());
        }

        public int getCount() {
            return LlpGalleryVideoActivity.folderData.size();
        }

        public Object getItem(int i) {
            return Integer.valueOf(i);
        }

        public View getView(final int i, View view, ViewGroup viewGrgoup) {
            if (view == null) {
                view = this.layoutInflater.inflate(R.layout.custom_items_folder_photo, viewGrgoup, false);

                viewGroup = new ViewHolder();
                viewGroup.img_galleryfolder = (ImageView) view.findViewById(R.id.img_galleryfolder);
                viewGroup.txt_foldername = (TextView) view.findViewById(R.id.txt_foldername);
                viewGroup.txt_folder_imgCount = (TextView) view.findViewById(R.id.txt_folder_imgCount);
                view.setTag(viewGroup);
            } else {
                viewGroup = (ViewHolder) view.getTag();
            }
            Glide.with(LlpGalleryVideoActivity.this.getApplicationContext()).load(LlpGalleryVideoActivity.folderData.get(i).getImageData().get(0).getImagePath()).into(viewGroup.img_galleryfolder);
            //  FunctionsPhoto.LogE("getView", ((ImageModelPhoto) ((FolderModelPhoto) GalleryActivityPhoto.folderData.get(i)).getImageData().get(0)).getImagePath());
            try {
                final File file = new File(LlpGalleryVideoActivity.folderData.get(i).getFolderName());
                viewGroup.txt_foldername.setText(file.getName());
                viewGroup.txt_folder_imgCount.setText("" + LlpGalleryVideoActivity.folderData.get(i).getImageData().size() + "");

                view.setOnClickListener(view1 -> {
                    try {

                        selectedPosition = i;
                        customImageAdepter = new CustomImageAdepter(i);
                        grid_subimage.setAdapter(customImageAdepter);
                            grid_imgfolder.setVisibility(View.GONE);
                            grid_subimage.setVisibility(View.VISIBLE);
                        txtTitle.setText(file.getName());


                    } catch (Exception view2) {
                        view2.printStackTrace();
                    }
                });
            } catch (Exception i2) {
                i2.printStackTrace();
            }
            return view;
        }
    }

    class CustomImageAdepter extends BaseAdapter {
        private LayoutInflater mInflater;
        int rootPosition;
        ViewHolder viewGroup;

        class ViewHolder {
            ImageView img_subPhotos, img_subPhotos_select;
            RelativeLayout rel_unchek, rel_chek;
            ViewHolder() {
            }
        }

        public long getItemId(int i) {
            return (long) i;
        }

        int getRootPosition() {
            return this.rootPosition;
        }

        CustomImageAdepter(int i) {
            this.rootPosition = i;
            this.mInflater = LayoutInflater.from(LlpGalleryVideoActivity.this);
        }

        public int getCount() {
            return LlpGalleryVideoActivity.folderData.get(this.rootPosition).getImageData().size();
        }

        public Object getItem(int i) {
            return LlpGalleryVideoActivity.folderData.get(this.rootPosition).getImageData().get(i);
        }

        public View getView(final int i, View view, ViewGroup viewGroupg) {
            if (view == null) {
                view = this.mInflater.inflate(R.layout.custom_items_folder_sub_photo, viewGroupg, false);
                viewGroup = new ViewHolder();
                viewGroup.img_subPhotos = (ImageView) view.findViewById(R.id.img_subPhotos);
                viewGroup.img_subPhotos_select = (ImageView) view.findViewById(R.id.img_subPhotos_select);
                viewGroup.rel_unchek = (RelativeLayout) view.findViewById(R.id.rel_unchek);
                viewGroup.rel_chek = (RelativeLayout) view.findViewById(R.id.rel_chek);

                view.setTag(viewGroup);
            } else {
                viewGroup = (ViewHolder) view.getTag();
            }
            try {
                Glide.with(LlpGalleryVideoActivity.this).load(LlpGalleryVideoActivity.folderData.get(this.rootPosition).getImageData().get(i).getImagePath()).into(viewGroup.img_subPhotos);
                Glide.with(LlpGalleryVideoActivity.this).load(LlpGalleryVideoActivity.folderData.get(this.rootPosition).getImageData().get(i).getImagePath()).into(viewGroup.img_subPhotos_select);

                if (LlpGalleryVideoActivity.folderData.get(this.rootPosition).getImageData().get(i).getCount() == 0) {

                    viewGroup.rel_unchek.setVisibility(View.VISIBLE);
                    viewGroup.rel_chek.setVisibility(View.GONE);

                } else {

                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(LlpGalleryVideoActivity.folderData.get(this.rootPosition).getImageData().get(i).getCount());

                    viewGroup.rel_unchek.setVisibility(View.GONE);
                    viewGroup.rel_chek.setVisibility(View.VISIBLE);

                }
                view.setOnClickListener(view1 -> {
                    try {
                        if (!LlpGalleryVideoActivity.folderData.get(rootPosition).getImageData().get(i).getImagePath().endsWith(".gif") || LlpGalleryVideoActivity.folderData.get(rootPosition).getImageData().get(i).getImagePath().endsWith(".GIF")) {
                            final AlertDialog.Builder alert = new AlertDialog.Builder(LlpGalleryVideoActivity.this);
                            new InterAds().showInterAds(LlpGalleryVideoActivity.this, new InterAds.OnAdClosedListener() {
                                @Override
                                public void onAdClosed() {
                                    startActivity(new Intent(getApplicationContext(), LlpVideoActivity.class));
                                    LlpGalleryVideoActivity.selectionData.add(new SelectionModelPhoto(CustomImageAdepter.this.rootPosition, i, LlpGalleryVideoActivity.folderData.get(CustomImageAdepter.this.rootPosition).getImageData().get(i).getImagePath()));

                                    File file2 = new File(selectionData.get(0).getFilepath());

                                    Intent intent = new Intent(getApplicationContext(), LlpVideoActivity.class);
                                    intent.putExtra("videoPath", file2.getAbsolutePath());
                                    startActivity(intent);
                                }
                            });




                        } else {
                            Toast.makeText(LlpGalleryVideoActivity.this, "Please Select only Images", Toast.LENGTH_SHORT).show();
                        }

                    } catch (
                            Exception view2) {
                        view2.printStackTrace();
                    }
                });


            } catch (
                    Exception i2) {
                i2.printStackTrace();
            }
            return view;
        }
    }




}
