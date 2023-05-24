package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Adapter;

import static com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity.LlpMusicActivity.stringForTime;
import static com.google.android.gms.common.util.CollectionUtils.listOf;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity.LlpSaveActivity;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;


public class SaveFileListAdapterAnimation extends RecyclerView.Adapter<SaveFileListAdapterAnimation.MyViewHolder> {

    private File[] listFile;
    private Activity context;
    private String dirName;
    private onItemClick click;

    public interface onItemClick {
        public void onClick(String path);
    }

    public SaveFileListAdapterAnimation(Activity context, File[] file, String dir, onItemClick click) {
        this.context = context;
        this.listFile = file;
        this.dirName = dir;
        this.click = click;
    }

    public void refresh(File[] file, String dir) {

        this.listFile = file;
        this.dirName = dir;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_items_creation_photo, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final String name = listFile[position].getName();
        holder.txtName.setText(name);

        final String path = listFile[position].getAbsolutePath();

        File file1 = new File(path);
        final Uri uri = Uri.fromFile(file1);

        try {
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(this.listFile[position].getAbsolutePath());
            String stringForTime = stringForTime(Integer.parseInt(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)));
            File file = new File(this.listFile[position].getAbsolutePath());

            double bytes = file.length();
            double kilobytes = (bytes / 1024);
            double megabytes = (kilobytes / 1024);
            holder.txtduration.setText(stringForTime /*+ " | " + String.format("%.2f", megabytes) + " MB"*/);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Glide.with(context)
                .load(uri)
                .apply(new RequestOptions().transform(new RoundedCorners(10)).error(R.drawable.preview_photo).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(holder.imgThumb);

        //    FunctionsPhoto.LogW("pathsss", listFile[position].getAbsolutePath());

        holder.imgThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LlpSaveActivity.class);
                intent.putExtra("finlpath", path);
                intent.putExtra("onBack", false);
                context.startActivity(intent);
                context.finish();
            }
        });


        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onClick(listFile[holder.getAdapterPosition()].getAbsolutePath());
notifyDataSetChanged();
            }
        });

        holder.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File mFile = new File(listFile[position].getAbsolutePath());
                if (mFile.exists()) {
                    try {
                        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                        StrictMode.setVmPolicy(builder.build());

                        String shareMsg = "Download " + context.getString(R.string.app_name) + " App : " + Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName());

                        Uri uri = Uri.fromFile(mFile);
                        Intent videoshare = new Intent(Intent.ACTION_SEND);
                        videoshare.setType("video/*");
                        videoshare.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        videoshare.putExtra(Intent.EXTRA_STREAM, uri);
                        videoshare.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
                        videoshare.putExtra(Intent.EXTRA_TEXT, shareMsg);
                        context.startActivity(videoshare);
                    } catch (Exception e) {
                        Log.e("TAG", "Error", e);

                    }

                }

              /*  try {

                    String path = ""; //should be local path of downloaded video

                    ContentValues content = new ContentValues(4);
                    content.put(MediaStore.Video.VideoColumns.DATE_ADDED,
                            System.currentTimeMillis() / 1000);
                    content.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
                    content.put(MediaStore.Video.Media.DATA, path);

                    ContentResolver resolver = context.getContentResolver();
                    Uri uri = resolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, content);

                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("video/*");
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Hey this is the video subject");
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, "Hey this is the video text");
                    sharingIntent.putExtra(Intent.EXTRA_STREAM,uri);
                    context.startActivity(Intent.createChooser(sharingIntent,"Share Video"));
                } catch (Exception e) {
                }*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFile.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName, txtduration;
        private ImageView imgThumb, imgDelete, imgShare;


        public MyViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txt_video_name);
            txtduration = (TextView) itemView.findViewById(R.id.txt_video_duration);

            imgThumb = itemView.findViewById(R.id.img_creationpic);
            imgDelete = itemView.findViewById(R.id.img_video_delete);
            imgShare = itemView.findViewById(R.id.img_video_share);

        }
    }

}
