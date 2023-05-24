package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.UtilsFoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.LargeNativeAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model.FolderModelPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model.ImageModelPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model.SelectionModelPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.ApplicationPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.Constant;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.FunctionsPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.BackInterAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.InterAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.ListBannerAds;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.preference.PowerPreference;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.databinding.ActivityGalleryPhotoBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LlpGalleryActivity extends AppCompatActivity {

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
    @BindView(R.id.tv_select_count)
    TextView tv_select_count;
    @BindView(R.id.lin_next)
    LinearLayout lin_next;
    @BindView(R.id.selectimg_recyclerView)
    RecyclerView selectimg_recyclerView;
    @BindView(R.id.linBottom)
    LinearLayout linBottom;


    public static ArrayList<FolderModelPhoto> folderData = new ArrayList<>();
    public static ArrayList<ImageModelPhoto> drag_imageData = new ArrayList<>();
    boolean boolean_folder;
    CustomListAdapter customListAdapter;
    int selectedPosition = 0;
    CustomImageAdepter customImageAdepter;
    ActivityGalleryPhotoBinding binding;
    static ArrayList<SelectionModelPhoto> selectionData = new ArrayList();
    static MyRecycleviewAdepter myRecycleviewAdepter;
    boolean isSingle;
    private Dialog dialog;

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
        binding = ActivityGalleryPhotoBinding.inflate(getLayoutInflater());
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

        Intent i = getIntent();
        isSingle = i.getBooleanExtra("isSingle", false);

        if (isSingle) {
            linBottom.setVisibility(View.GONE);
            lin_next.setVisibility(View.GONE);

        } else {
            linBottom.setVisibility(View.VISIBLE);
            lin_next.setVisibility(View.VISIBLE);

        }

        selectimg_recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        myRecycleviewAdepter = new MyRecycleviewAdepter();
        selectimg_recyclerView.setAdapter(myRecycleviewAdepter);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

        lin_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                method_next_screeen_move();
            }
        });
    }


    public void fn_imagespath() {
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
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

                    String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

                    final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
                    cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

                    column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                    column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

                    while (cursor.moveToNext()) {
                        try {
                            absolutePathOfImage = cursor.getString(column_index_data);
                            for (int i2 = 0; i2 < folderData.size(); i2++) {
                                if (folderData.get(i2).getFolderName().equals(cursor.getString(column_index_folder_name))) {
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
                                imageData.setSelected(Boolean.FALSE);
                                imageData.setCount(0);
                                arrayList.addAll(folderData.get(int_position).getImageData());
                                arrayList.add(imageData);
                                folderData.get(int_position).setImageData(arrayList);
                            } else {
                                ArrayList arrayList2 = new ArrayList();
                                ImageModelPhoto imageData2 = new ImageModelPhoto();
                                imageData2.setImagePath(absolutePathOfImage);
                                imageData2.setSelected(Boolean.FALSE);
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
    }

    public void loadData() {
        UtilsFoto.dilogHide(this);

        customListAdapter = new CustomListAdapter();
        grid_imgfolder.setHorizontalSpacing(5);
        grid_imgfolder.setVerticalSpacing(5);
        txtTitle.setText("Gallery");
        grid_imgfolder.setAdapter(customListAdapter);

        if (folderData.size() > 0) {
            linGrid.setVisibility(View.VISIBLE);
            image_nodata.setVisibility(View.GONE);

            final File file = new File(folderData.get(0).getFolderName());
            customImageAdepter = new CustomImageAdepter(0);
            grid_subimage.setAdapter(customImageAdepter);
            txtTitle.setText(file.getName());
        } else {
            linGrid.setVisibility(View.GONE);
            image_nodata.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onBackPressed() {
        new BackInterAds().showInterAds(this, new BackInterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {
                if (grid_imgfolder.getVisibility() == View.VISIBLE) {
                    Intent i = new Intent(LlpGalleryActivity.this, LlpHomeActivity.class);
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
            layoutInflater = LayoutInflater.from(getApplicationContext());
        }

        public int getCount() {
            return folderData.size();
        }

        public Object getItem(int i) {
            return Integer.valueOf(i);
        }

        public View getView(final int i, View view, ViewGroup viewGrgoup) {
            if (view == null) {
                view = layoutInflater.inflate(R.layout.custom_items_folder_photo, viewGrgoup, false);

                viewGroup = new ViewHolder();
                viewGroup.img_galleryfolder = (ImageView) view.findViewById(R.id.img_galleryfolder);
                viewGroup.txt_foldername = (TextView) view.findViewById(R.id.txt_foldername);
                viewGroup.txt_folder_imgCount = (TextView) view.findViewById(R.id.txt_folder_imgCount);
                view.setTag(viewGroup);
            } else {
                viewGroup = (ViewHolder) view.getTag();
            }
            Glide.with(getApplicationContext()).load(folderData.get(i).getImageData().get(0).getImagePath()).diskCacheStrategy(DiskCacheStrategy.NONE).into(viewGroup.img_galleryfolder);
//            Glide.with(getApplicationContext()).load(folderData.get(i).getImageData().get(0).getImagePath()).into(viewGroup.img_galleryfolder);
            //  FunctionsPhoto.LogE("getView", ((ImageModelPhoto) ((FolderModelPhoto) folderData.get(i)).getImageData().get(0)).getImagePath());
            try {
                final File file = new File(folderData.get(i).getFolderName());
                viewGroup.txt_foldername.setText(file.getName());
                viewGroup.txt_folder_imgCount.setText("" + (folderData.get(i)).getImageData().size() + "");

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
            return rootPosition;
        }

        CustomImageAdepter(int i) {
            rootPosition = i;
            mInflater = LayoutInflater.from(LlpGalleryActivity.this);
        }

        public int getCount() {
            return folderData.get(rootPosition).getImageData().size();
        }

        public Object getItem(int i) {
            return folderData.get(rootPosition).getImageData().get(i);
        }

        public View getView(final int i, View view, ViewGroup viewGroupg) {
            if (view == null) {
                view = mInflater.inflate(R.layout.custom_items_folder_sub_photo, viewGroupg, false);
                viewGroup = new ViewHolder();
                viewGroup.img_subPhotos = view.findViewById(R.id.img_subPhotos);
                viewGroup.img_subPhotos_select = view.findViewById(R.id.img_subPhotos_select);
                viewGroup.rel_unchek = view.findViewById(R.id.rel_unchek);
                viewGroup.rel_chek = view.findViewById(R.id.rel_chek);

                view.setTag(viewGroup);
            } else {
                viewGroup = (ViewHolder) view.getTag();
            }
            try {
                Glide.with(getApplicationContext()).load(folderData.get(rootPosition).getImageData().get(i).getImagePath()).diskCacheStrategy(DiskCacheStrategy.NONE).into(viewGroup.img_subPhotos);
                Glide.with(getApplicationContext()).load(folderData.get(rootPosition).getImageData().get(i).getImagePath()).diskCacheStrategy(DiskCacheStrategy.NONE).into(viewGroup.img_subPhotos_select);

//                Glide.with(GalleryActivityPhoto.this).load(folderData.get(rootPosition).getImageData().get(i).getImagePath()).into(viewGroup.img_subPhotos);
//                Glide.with(GalleryActivityPhoto.this).load(folderData.get(rootPosition).getImageData().get(i).getImagePath()).into(viewGroup.img_subPhotos_select);

                if (folderData.get(rootPosition).getImageData().get(i).getCount() == 0) {

                    viewGroup.rel_unchek.setVisibility(View.VISIBLE);
                    viewGroup.rel_chek.setVisibility(View.GONE);

                } else {

                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(folderData.get(rootPosition).getImageData().get(i).getCount());

                    viewGroup.rel_unchek.setVisibility(View.GONE);
                    viewGroup.rel_chek.setVisibility(View.VISIBLE);

                }
                view.setOnClickListener(view1 -> {
                    try {
                        //   FunctionsPhoto.LogE("hhhh", "---" + ((ImageModelPhoto) ((FolderModelPhoto) folderData.get(rootPosition)).getImageData().get(i)).getImagePath());

                        if (!folderData.get(rootPosition).getImageData().get(i).getImagePath().endsWith(".gif_photo") || folderData.get(rootPosition).getImageData().get(i).getImagePath().endsWith(".GIF")) {

                            if (isSingle) {
                                selectionData.add(new SelectionModelPhoto(rootPosition, i, folderData.get(rootPosition).getImageData().get(i).getImagePath()));

                                File file2 = new File(selectionData.get(0).getFilepath());

                                Intent intent = new Intent(getApplicationContext(), LlpCropSingleActivity.class);
                                intent.putExtra("ImagePath", file2.getAbsolutePath());
                                startActivity(intent);
                            } else {
                                folderData.get(rootPosition).getImageData().get(i).countadd();
                                Log.e("TAG", folderData.get(rootPosition).getImageData().get(i).getImagePath());
                                selectionData.add(new SelectionModelPhoto(rootPosition, i, folderData.get(rootPosition).getImageData().get(i).getImagePath()));
//                                    tv_select_count.setText("(" + selectionData.size() + ")");
                                tv_select_count.setText("(" + selectionData.size() + ")");
                                viewGroup.rel_unchek.setVisibility(View.GONE);
                                viewGroup.rel_chek.setVisibility(View.VISIBLE);
                                myRecycleviewAdepter.notifyDataSetChanged();
                                customImageAdepter.notifyDataSetChanged();
                                selectimg_recyclerView.smoothScrollToPosition(selectionData.size());
                            }

                        } else {
                            Toast.makeText(LlpGalleryActivity.this, "Please Select only Images", Toast.LENGTH_SHORT).show();
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

    public class MyRecycleviewAdepter extends RecyclerView.Adapter<MyRecycleviewAdepter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            ViewHolder(View view) {
                super(view);
                imageView = view.findViewById(R.id.imageview);
            }
        }

        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_item_selected_images_photo, viewGroup, false));
        }

        public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
            try {

                Glide.with(getApplicationContext())
                        .load(selectionData.get(i).getFilepath())
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(viewHolder.imageView);



//                Glide.with(GalleryActivityPhoto.this)
//                        .load(selectionData.get(i).getFilepath())
//                        .into(viewHolder.imageView);

                viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        try {
                            folderData.get(selectionData.get(i).getFolderIndex()).getImageData().get(selectionData.get(i).getFileIndex()).countremove();
                            if (customImageAdepter.getRootPosition() == selectionData.get(i).getFolderIndex()) {
                                customImageAdepter.notifyDataSetChanged();
                            }
                            selectionData.remove(i);
                            tv_select_count.setText("(" + selectionData.size() + ")");
                            notifyDataSetChanged();
                        } catch (Exception view2) {
                            view2.printStackTrace();
                        }
                    }
                });


            } catch (Exception viewHolder2) {
                viewHolder2.printStackTrace();
            }
        }

        public int getItemCount() {
            return selectionData.size();
        }

    }

    private void method_next_screeen_move() {
        if (selectionData.size() < 2) {
            Toast.makeText(this, "Please Add atleast 2 Images", Toast.LENGTH_SHORT).show();
        } else if (selectionData.size() > 50) {
            Toast.makeText(this, "Max 50 Images", Toast.LENGTH_SHORT).show();

        } else {
//            new saveSelectedImages().execute();
            new saveSelectedImages().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

       /* GalleryActivityPhoto.drag_imageData.clear();
        //     ArrayList arrayList = new ArrayList();
        int i = 0;
        for (int i2 = 0; i2 < selectionData.size(); i2++) {
            ImageModelPhoto photo = new ImageModelPhoto();
            photo.setImagePath(((SelectionModelPhoto) selectionData.get(i)).getFilepath());
            drag_imageData.add(photo);
            i++;
        }

        new InterAds().showInterAds(GalleryActivityPhoto.this, new InterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {
                startActivity(new Intent(GalleryActivityPhoto.this, SwipeActivityPhoto.class));
            }
        });*/
    }

    @SuppressLint("StaticFieldLeak")
    private class saveSelectedImages extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dilog_show();
            FunctionsPhoto.LogE("saveSelectedImages", " 1 ");
        }

        @Override
        protected Void doInBackground(Void... voids) {

            FunctionsPhoto.LogE("saveSelectedImages", " 2 ");


            String mainFolder = getString(R.string.mainFolder);
            String selectFolder = getString(R.string.select_image_folder);

            File mainFile = new File(Constant.getFolderPath(ApplicationPhoto.getContext()) + File.separator + mainFolder);
            File selectFile = new File(mainFile, selectFolder);


            if (selectFile.exists())
                deleteDir(selectFile);

            if (!selectFile.exists())
                selectFile.mkdirs();


            LlpGalleryActivity.drag_imageData.clear();


            int i = 0;
            for (int i2 = 0; i2 < selectionData.size(); i2++) {
//                File file2 = new File(selectionData.get(i).getFilepath());

                File newFile = new File(selectFile, "selected_" + i2 + ".jpg");

                FunctionsPhoto.LogE("imageDragPath", newFile.getAbsolutePath());
                FunctionsPhoto.LogE("imageDragPath", selectionData.get(i).getFilepath());

                FileOutputStream fileOutputStream = null;
                try {

                    Bitmap bitmap = BitmapFactory.decodeFile(selectionData.get(i).getFilepath());
                    //  Bitmap bitmap = Service2Photo.checkBitmap(selectionData.get(i).getFilepath());
                    fileOutputStream = new FileOutputStream(newFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);

                } catch (Exception e) {
                    Log.e("TAG", e.toString());
                    e.printStackTrace();
                }

                ImageModelPhoto imageData = new ImageModelPhoto();
                imageData.setImagePath(newFile.getAbsolutePath());
                drag_imageData.add(imageData);
                i++;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dilogHide();
            new InterAds().showInterAds(LlpGalleryActivity.this, new InterAds.OnAdClosedListener() {
                @Override
                public void onAdClosed() {
                    Intent i = new Intent(LlpGalleryActivity.this, LlpSwipeActivity.class);
                    startActivity(i);
                }

            });
        }
    }

    public boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    boolean success = deleteDir(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }

    public void dilog_show() {
        try {

            if (dialog != null && dialog.isShowing())
                dialog.dismiss();


            dialog = new Dialog(LlpGalleryActivity.this);
            dialog.setContentView(R.layout.custom_dialog_save_video_photo);
            TextView progress = (TextView) dialog.findViewById(R.id.txtvlu);
            progress.setVisibility(View.GONE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);


            if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {

                int ui_flags =
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

                dialog.getWindow().
                        setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                dialog.getWindow().getDecorView().setSystemUiVisibility(ui_flags);
            }
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            FrameLayout nativeframe=dialog.findViewById(R.id.nativeAd);
            TextView adspace=dialog.findViewById(R.id.ad_space);

            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    if(PowerPreference.getDefaultFile().getBoolean(Constant.LoaderNativeOnOff,false)){
                        new LargeNativeAds().showNativeAds(LlpGalleryActivity.this, dialog);
                    }else {
                        nativeframe.setVisibility(View.GONE);
                        adspace.setVisibility(View.GONE);
                    }
                }
            });

            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dilogHide() {
        dialog.dismiss();
        FunctionsPhoto.loadNativeAd(LlpGalleryActivity.this);
    }
}
