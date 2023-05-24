package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Service;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.util.Log;


import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.FastblurPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.FunctionsPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.GlobPhoto;

import java.io.IOException;
import java.lang.reflect.Array;
public class Service2Photo {

    public static Bitmap m6618a(Bitmap bitmap, Bitmap bitmap2, int i, int i2, float f, float f2) {
        Canvas canvas = null;
        float f3;
        int[] iArr;
        Canvas canvas2;
        int i3 = i;
        int i4 = i2;
        Bitmap copy = bitmap2.copy(bitmap2.getConfig(), true);
        int width = copy.getWidth();
        int height = copy.getHeight();
        int i5 = width * height;
        int[] iArr2 = new int[i5];
        int[] iArr3 = iArr2;
        copy.getPixels(iArr2, 0, width, 0, 0, width, height);
        int i6 = width - 1;
        int i7 = height - 1;
        int[] iArr4 = new int[i5];
        int[] iArr5 = new int[i5];
        int[] iArr6 = new int[i5];
        int[] iArr7 = new int[Math.max(width, height)];
        int[] iArr8 = new int[173056];
        int i8 = 0;
        for (int i9 = 173056; i8 < i9; i9 = 173056) {
            iArr8[i8] = i8 / 676;
            i8++;
        }
        int[][] iArr9 = (int[][]) Array.newInstance(Integer.TYPE, new int[]{51, 3});
        int i10 = 0;
        int i11 = 0;
        int i12 = 0;
        while (true) {
            if (i10 >= height) {
                break;
            }
            int i13 = -25;
            int i14 = 0;
            int i15 = 0;
            int i16 = 0;
            int i17 = 0;
            int i18 = 0;
            int i19 = 0;
            int i20 = 0;
            int i21 = 0;
            int i22 = 0;
            for (int i23 = 25; i13 <= i23; i23 = 25) {
                int i24 = iArr3[Math.min(i6, Math.max(i13, 0)) + i11];
                int[] iArr10 = iArr9[i13 + 25];
                iArr10[0] = (i24 & 16711680) >> 16;
                iArr10[1] = (i24 & 65280) >> 8;
                iArr10[2] = i24 & 255;
                int abs = 26 - Math.abs(i13);
                i14 = (iArr10[0] * abs) + i14;
                i15 = (iArr10[1] * abs) + i15;
                i16 = (iArr10[2] * abs) + i16;
                if (i13 > 0) {
                    i20 += iArr10[0];
                    i21 += iArr10[1];
                    i22 += iArr10[2];
                } else {
                    i17 += iArr10[0];
                    i18 += iArr10[1];
                    i19 += iArr10[2];
                }
                i13++;
                int i25 = i2;
            }
            int i26 = 0;
            int i27 = 25;
            while (i26 < width) {
                iArr4[i11] = iArr8[i14];
                iArr5[i11] = iArr8[i15];
                iArr6[i11] = iArr8[i16];
                int i28 = i14 - i17;
                int i29 = i15 - i18;
                int i30 = i16 - i19;
                int[] iArr11 = iArr9[((i27 - 25) + 51) % 51];
                int i31 = i17 - iArr11[0];
                int i32 = i18 - iArr11[1];
                int i33 = i19 - iArr11[2];
                if (i10 == 0) {
                    canvas2 = canvas;
                    iArr7[i26] = Math.min(i26 + 25 + 1, i6);
                } else {
                    canvas2 = canvas;
                }
                int i34 = iArr3[iArr7[i26] + i12];
                iArr11[0] = (i34 & 16711680) >> 16;
                iArr11[1] = (i34 & 65280) >> 8;
                iArr11[2] = i34 & 255;
                int i35 = i20 + iArr11[0];
                int i36 = i21 + iArr11[1];
                int i37 = i22 + iArr11[2];
                i14 = i28 + i35;
                i15 = i29 + i36;
                i16 = i30 + i37;
                i27 = (i27 + 1) % 51;
                int[] iArr12 = iArr9[i27 % 51];
                i17 = i31 + iArr12[0];
                i18 = i32 + iArr12[1];
                i19 = i33 + iArr12[2];
                i20 = i35 - iArr12[0];
                i21 = i36 - iArr12[1];
                i22 = i37 - iArr12[2];
                i11++;
                i26++;
                canvas = canvas2;
            }
            Canvas canvas3 = canvas;
            i12 += width;
            i10++;
            int i38 = i2;
        }
        Canvas canvas4 = canvas;
        int i39 = 0;
        while (i39 < width) {
            Bitmap bitmap3 = copy;
            int i40 = -25 * width;
            int[] iArr13 = iArr7;
            int i41 = -25;
            int i42 = 0;
            int i43 = 0;
            int i44 = 0;
            int i45 = 0;
            int i46 = 0;
            int i47 = 0;
            int i48 = 0;
            int i49 = 0;
            int i50 = 0;
            for (int i51 = 25; i41 <= i51; i51 = 25) {
                int max = Math.max(0, i40) + i39;
                int[] iArr14 = iArr9[i41 + 25];
                iArr14[0] = iArr4[max];
                iArr14[1] = iArr5[max];
                iArr14[2] = iArr6[max];
                int abs2 = 26 - Math.abs(i41);
                i49 = (iArr4[max] * abs2) + i49;
                i48 = (iArr5[max] * abs2) + i48;
                i47 = (iArr6[max] * abs2) + i47;
                if (i41 > 0) {
                    i42 += iArr14[0];
                    i43 += iArr14[1];
                    i50 += iArr14[2];
                } else {
                    i46 += iArr14[0];
                    i45 += iArr14[1];
                    i44 += iArr14[2];
                }
                if (i41 < i7) {
                    i40 += width;
                }
                i41++;
            }
            int i52 = i39;
            int i53 = i42;
            int i54 = 0;
            int i55 = 25;
            while (i54 < height) {
                iArr3[i52] = (iArr3[i52] & -16777216) | (iArr8[i49] << 16) | (iArr8[i48] << 8) | iArr8[i47];
                int i56 = i49 - i46;
                int i57 = i48 - i45;
                int i58 = i47 - i44;
                int[] iArr15 = iArr9[((i55 - 25) + 51) % 51];
                int i59 = i46 - iArr15[0];
                int i60 = i45 - iArr15[1];
                int i61 = i44 - iArr15[2];
                if (i39 == 0) {
                    iArr = iArr8;
                    iArr13[i54] = Math.min(i54 + 26, i7) * width;
                } else {
                    iArr = iArr8;
                }
                int i62 = iArr13[i54] + i39;
                iArr15[0] = iArr4[i62];
                iArr15[1] = iArr5[i62];
                iArr15[2] = iArr6[i62];
                int i63 = i53 + iArr15[0];
                int i64 = i43 + iArr15[1];
                int i65 = i50 + iArr15[2];
                i49 = i56 + i63;
                i48 = i57 + i64;
                i47 = i58 + i65;
                i55 = (i55 + 1) % 51;
                int[] iArr16 = iArr9[i55];
                i46 = i59 + iArr16[0];
                i45 = i60 + iArr16[1];
                i44 = i61 + iArr16[2];
                i53 = i63 - iArr16[0];
                i43 = i64 - iArr16[1];
                i50 = i65 - iArr16[2];
                i52 += width;
                i54++;
                iArr8 = iArr;
            }
            i39++;
            copy = bitmap3;
            iArr7 = iArr13;
            iArr8 = iArr8;
        }
        Bitmap bitmap4 = copy;
        bitmap4.setPixels(iArr3, 0, width, 0, 0, width, height);
        Bitmap bitmap5 = bitmap4;
        Canvas canvas5 = new Canvas(bitmap5);
        Paint paint = new Paint();
        int i66 = i;
        int i67 = i2;
        new Rect(0, 0, i66, i67);
        Bitmap createBitmap = Bitmap.createBitmap(i66, i67, Config.ARGB_8888);
        float width2 = (float) bitmap.getWidth();
        float height2 = (float) bitmap.getHeight();
        Canvas canvas6 = new Canvas(createBitmap);
        float f4 = (float) i66;
        float f5 = f4 / width2;
        float f6 = (float) i67;
        float f7 = f6 / height2;
        float f8 = (f6 - (height2 * f5)) / 2.0f;
        if (f8 < 0.0f) {
            f3 = (f4 - (width2 * f7)) / 2.0f;
            f8 = 0.0f;
        } else {
            f7 = f5;
            f3 = 0.0f;
        }
        Matrix matrix = new Matrix();
        matrix.postTranslate(f * f3, f2 + f8);
        StringBuilder sb = new StringBuilder();
        sb.append("xTranslation :");
        sb.append(f3);
        sb.append(" yTranslation :");
        sb.append(f8);
        FunctionsPhoto.LogD("translation", sb.toString());
        matrix.preScale(f7, f7);
        Paint paint2 = new Paint();
        paint2.setFilterBitmap(true);
        canvas6.drawBitmap(bitmap, matrix, paint2);
        canvas5.drawBitmap(createBitmap, 0.0f, 0.0f, paint);
        paint.setColor(-1996488705);
        paint.setStrokeWidth(120.0f);
        paint.setTextSize(120.0f);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_OVER));
        return bitmap5;
    }

    public static Bitmap ConvetrSameSize(Bitmap bitmap, Bitmap bitmap2, int i, int i2, float f, float f2) {
        Bitmap copy = bitmap2.copy(bitmap2.getConfig(), true);
        Canvas canvas = new Canvas(FastblurPhoto.doBlur(copy, GlobPhoto.blurRadius, true));
        Paint paint = new Paint();
        new Rect(0, 0, i, i2);
        canvas.drawBitmap(newscaleBitmap(bitmap, i, i2, f, f2), 0.0f, 0.0f, paint);
        paint.setColor(-1996488705);
        paint.setStrokeWidth(120.0f);
        paint.setTextSize(120.0f);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_OVER));
        return copy;
    }

    private static Bitmap newscaleBitmap(Bitmap bitmap, int i, int i2, float f, float f2) {
        float f3;
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
        float width = (float) bitmap.getWidth();
        float height = (float) bitmap.getHeight();
        Canvas canvas = new Canvas(createBitmap);
        float f4 = (float) i;
        float f5 = f4 / width;
        float f6 = (float) i2;
        float f7 = f6 / height;
        float f8 = (f6 - (height * f5)) / 2.0f;
        if (f8 < 0.0f) {
            f3 = (f4 - (width * f7)) / 2.0f;
            f5 = f7;
            f8 = 0.0f;
        } else {
            f3 = 0.0f;
        }
        Matrix matrix = new Matrix();
        matrix.postTranslate(f * f3, f2 + f8);
        StringBuilder sb = new StringBuilder();
        sb.append("xTranslation :");
        sb.append(f3);
        sb.append(" yTranslation :");
        sb.append(f8);
        Log.d("translation", sb.toString());
        matrix.preScale(f5, f5);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.drawBitmap(bitmap, matrix, paint);
        return createBitmap;
    }

    public static Bitmap scaleCenterCrop(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width == i && height == i2) {
            return bitmap;
        }
        float f = (float) i;
        float f2 = (float) width;
        float f3 = (float) i2;
        float f4 = (float) height;
        float max = Math.max(f / f2, f3 / f4);
        float f5 = f2 * max;
        float f6 = max * f4;
        float f7 = (f - f5) / 2.0f;
        float f8 = (f3 - f6) / 2.0f;
        RectF rectF = new RectF(f7, f8, f5 + f7, f6 + f8);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, bitmap.getConfig());
        new Canvas(createBitmap).drawBitmap(bitmap, null, rectF, null);
        return createBitmap;
    }

    public static Bitmap checkBitmap(String str) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        int i = 1;
        options.inJustDecodeBounds = true;
        // options.inSampleSize = 1;
        BitmapFactory.decodeFile(str, options);
        Bitmap decodeFile = BitmapFactory.decodeFile(str, new BitmapFactory.Options());
        try {
            String attribute = new ExifInterface(str).getAttribute("Orientation");
            if (attribute != null) {
                i = Integer.parseInt(attribute);
            }
            int i2 = 0;
            if (i == 6) {
                i2 = 90;
            }
            if (i == 3) {
                i2 = 180;
            }
            if (i == 8) {
                i2 = 270;
            }
            Matrix matrix = new Matrix();
            matrix.setRotate((float) i2, ((float) decodeFile.getWidth()) / 2.0f, ((float) decodeFile.getHeight()) / 2.0f);
            return Bitmap.createBitmap(decodeFile, 0, 0, options.outWidth, options.outHeight, matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
