package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Service;

import android.graphics.Paint;
import android.graphics.Paint.Style;

import java.lang.reflect.Array;
import java.util.Random;

public class Service1Photo {

    public static float f6235a = 22.0f;

    public static int f6236b = ((int) (f6235a - 1.0f));

    public static int f6237c = 8;

    public static int f6238d = 30;

   public static final Paint f6239e = new Paint();

   public static int[][] f6240f = ((int[][]) Array.newInstance(Integer.TYPE, new int[]{20, 20}));

   public static Random f6241g = new Random();

    static {
        f6239e.setColor(-16777216);
        f6239e.setAntiAlias(true);
        f6239e.setStyle(Style.FILL_AND_STROKE);
    }

   public static float m8222a(int i, int i2) {
        return (float) Math.sqrt((double) (((i * i) + (i2 * i2)) / 4));
    }

    public static void m8223a() {
        f6240f = (int[][]) Array.newInstance(Integer.TYPE, new int[]{(int) f6235a, (int) f6235a});
        for (int i = 0; i < f6240f.length; i++) {
            for (int i2 = 0; i2 < f6240f[i].length; i2++) {
                f6240f[i][i2] = 0;
            }
        }
    }
}
