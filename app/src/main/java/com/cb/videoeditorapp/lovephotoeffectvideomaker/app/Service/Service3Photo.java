package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Service;

import static com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity.LlpMultipleActivity.f9024a;
import static com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity.LlpMultipleActivity.f9025b;

import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;

import java.lang.reflect.Array;
import java.util.Random;



public class Service3Photo {

    public static final Paint f12401a = new Paint();

    public static float f12402b = 22.0f;

    public static int f12403c = ((int) (f12402b - 1.0f));

    public static int f12404d = 0;

    public static C4393a f12405e;

    public static int[][] f12406f;

    public static int f12407g;

    public static int f12408h;

    public static float f12409i;

    public static float f12410j;

    public static Bitmap[][] f12411k;

    public static Camera f12412l = new Camera();

    public static Matrix f12413m = new Matrix();

    public static int f12414n = 8;

    public static float f12415o;

    public static float f12416p;

    public enum C4393a {


        Jalousie_BT("Jalousie_BT") {
            public Bitmap mo10083a(Bitmap bitmap, Bitmap bitmap2, int i) {
                return bitmap;
            }

            @Override
            public void mo10084a(Bitmap bitmap, Bitmap bitmap2) {

            }
        },

        Jalousie_LR("Jalousie_LR") {
            public Bitmap mo10083a(Bitmap bitmap, Bitmap bitmap2, int i) {
                return bitmap;
            }

            @Override
            public void mo10084a(Bitmap bitmap, Bitmap bitmap2) {

            }
        }, RollInTurn_BT("RollInTurn_BT") {
            public Bitmap mo10083a(Bitmap bitmap, Bitmap bitmap2, int i) {
                return bitmap;
            }

            @Override
            public void mo10084a(Bitmap bitmap, Bitmap bitmap2) {

            }
        }, RollInTurn_LR("RollInTurn_LR") {
            public Bitmap mo10083a(Bitmap bitmap, Bitmap bitmap2, int i) {
                return bitmap;
            }

            @Override
            public void mo10084a(Bitmap bitmap, Bitmap bitmap2) {

            }
        }, RollInTurn_RL("RollInTurn_RL") {
            public Bitmap mo10083a(Bitmap bitmap, Bitmap bitmap2, int i) {
                return bitmap;
            }

            @Override
            public void mo10084a(Bitmap bitmap, Bitmap bitmap2) {

            }
        }, RollInTurn_TB("RollInTurn_TB") {
            public Bitmap mo10083a(Bitmap bitmap, Bitmap bitmap2, int i) {
                return bitmap;
            }

            @Override
            public void mo10084a(Bitmap bitmap, Bitmap bitmap2) {

            }
        }, SepartConbine_BT("SepartConbine_BT") {
            public Bitmap mo10083a(Bitmap bitmap, Bitmap bitmap2, int i) {
                return bitmap;
            }

            @Override
            public void mo10084a(Bitmap bitmap, Bitmap bitmap2) {

            }
        }, SepartConbine_LR("SepartConbine_LR") {
            public Bitmap mo10083a(Bitmap bitmap, Bitmap bitmap2, int i) {
                return bitmap;
            }

            @Override
            public void mo10084a(Bitmap bitmap, Bitmap bitmap2) {

            }
        }, SepartConbine_RL("SepartConbine_RL") {
            public Bitmap mo10083a(Bitmap bitmap, Bitmap bitmap2, int i) {
                return bitmap;
            }

            @Override
            public void mo10084a(Bitmap bitmap, Bitmap bitmap2) {

            }
        },
        SepartConbine_TB("SepartConbine_TB") {
            public Bitmap mo10083a(Bitmap bitmap, Bitmap bitmap2, int i) {
                return bitmap;
            }

            @Override
            public void mo10084a(Bitmap bitmap, Bitmap bitmap2) {

            }
        };

        String effetcName;

        C4393a(String str) {
            this.effetcName = str;
        }

        /* renamed from: a */
        public abstract Bitmap mo10083a(Bitmap bitmap, Bitmap bitmap2, int i);

        /* renamed from: a */
        public abstract void mo10084a(Bitmap bitmap, Bitmap bitmap2);
    }

    static {
        new Random();
        f12401a.setColor(-16777216);
        f12401a.setAntiAlias(true);
        f12401a.setStyle(Style.FILL_AND_STROKE);
    }

    public static void m12636a() {
        Class cls = Integer.TYPE;
        float f = f12402b;
        f12406f = (int[][]) Array.newInstance(cls, new int[]{(int) f, (int) f});
        for (int i = 0; i < f12406f.length; i++) {
            int i2 = 0;
            while (true) {
                int[][] iArr = f12406f;
                if (i2 >= iArr[i].length) {
                    break;
                }
                iArr[i][i2] = 0;
                i2++;
            }
        }
    }

    public static void m12637a(int i) {
        if (f12405e == C4393a.RollInTurn_BT || f12405e == C4393a.RollInTurn_LR || f12405e == C4393a.RollInTurn_RL || f12405e == C4393a.RollInTurn_TB) {
            f12415o = (((((float) (f12414n - 1)) * 30.0f) + 90.0f) * ((float) i)) / ((float) f12403c);
        } else {
            f12415o = ((f12405e == C4393a.Jalousie_BT || f12405e == C4393a.Jalousie_LR) ? ((float) i) * 180.0f : ((float) i) * 90.0f) / ((float) f12403c);
        }
        int i2 = 180;
        if (f12404d == 1) {
            f12416p = f12415o;
            if (!(f12405e == C4393a.Jalousie_BT || f12405e == C4393a.Jalousie_LR)) {
                i2 = 90;
            }
            f12410j = (f12416p / ((float) i2)) * ((float) f9024a);
            return;
        }
        f12416p = f12415o;
        if (!(f12405e == C4393a.Jalousie_BT || f12405e == C4393a.Jalousie_LR)) {
            i2 = 90;
        }
        f12409i = (f12416p / ((float) i2)) * ((float) f9025b);
    }

    public static void m12637a_11_12_13_14(int i) {

        f12415o = (((((float) (f12414n - 1)) * 30.0f) + 90.0f) * ((float) i)) / ((float) f12403c);

        int i2 = 180;
        if (f12404d == 1) {
            f12416p = f12415o;

            i2 = 90;
            f12410j = (f12416p / ((float) i2)) * ((float) f9024a);
            return;
        }
        f12416p = f12415o;
        i2 = 90;
        f12409i = (f12416p / ((float) i2)) * ((float) f9025b);
    }

    public static void m12637a910(int i) {

        f12415o = ((f12404d == 1 || f12404d == 0) ? ((float) i) * 180.0f : ((float) i) * 90.0f) / ((float) f12403c);

        int i2 = 180;
        if (f12404d == 1) {
            f12416p = f12415o;
            f12410j = (f12416p / ((float) i2)) * ((float) f9024a);
            return;
        }
        f12416p = f12415o;
        f12409i = (f12416p / ((float) i2)) * ((float) f9025b);
    }

    public static void m12638a(Bitmap bitmap, Bitmap bitmap2, Canvas canvas, boolean z) {
        float f;
        Matrix matrix;
        float f2;
        canvas.save();
        if (f12404d == 1) {
            f12412l.save();
            if (z) {
                f12412l.rotateX(0.0f);
            } else {
                f12412l.rotateX(-f12415o);
            }
            f12412l.getMatrix(f12413m);
            f12412l.restore();
            f12413m.preTranslate((float) ((-f9025b) / 2), 0.0f);
            f12413m.postTranslate((float) (f9025b / 2), f12410j);
            canvas.drawBitmap(bitmap, f12413m, f12401a);
            f12412l.save();
            if (z) {
                f12412l.rotateX(0.0f);
            } else {
                f12412l.rotateX(90.0f - f12415o);
            }
            f12412l.getMatrix(f12413m);
            f12412l.restore();
            f12413m.preTranslate((float) ((-f9025b) / 2), (float) (-f9024a));
            matrix = f12413m;
            f = (float) (f9025b / 2);
            f2 = f12410j;
        } else {
            f12412l.save();
            if (z) {
                f12412l.rotateY(0.0f);
            } else {
                f12412l.rotateY(f12415o);
            }
            f12412l.getMatrix(f12413m);
            f12412l.restore();
            f12413m.preTranslate(0.0f, (float) ((-f9024a) / 2));
            f12413m.postTranslate(f12409i, (float) (f9024a / 2));
            canvas.drawBitmap(bitmap, f12413m, f12401a);
            f12412l.save();
            if (z) {
                f12412l.rotateY(0.0f);
            } else {
                f12412l.rotateY(f12415o - 90.0f);
            }
            f12412l.getMatrix(f12413m);
            f12412l.restore();
            f12413m.preTranslate((float) (-f9025b), (float) ((-f9024a) / 2));
            matrix = f12413m;
            f = f12409i;
            f2 = (float) (f9024a / 2);
        }
        matrix.postTranslate(f, f2);
        canvas.drawBitmap(bitmap2, f12413m, f12401a);
        canvas.restore();
    }

    public static void m12641b(Canvas canvas) {
        for (int i = 0; i < f12414n; i++) {
            Bitmap[][] bitmapArr = f12411k;
            Bitmap bitmap = bitmapArr[0][i];
            Bitmap bitmap2 = bitmapArr[1][i];
            float f = f12415o - ((float) (i * 30));
            if (f < 0.0f) {
                f = 0.0f;
            }
            if (f > 90.0f) {
                f = 90.0f;
            }
            canvas.save();
            if (f12404d == 1) {
                float f2 = (float) f9024a;
                float f3 = (f / 90.0f) * f2;
                if (f3 > f2) {
                    f3 = f2;
                }
                if (f3 < 0.0f) {
                    f3 = 0.0f;
                }
                f12412l.save();
                f12412l.rotateX(-f);
                f12412l.getMatrix(f12413m);
                f12412l.restore();
                f12413m.preTranslate((float) (-bitmap.getWidth()), 0.0f);
                f12413m.postTranslate((float) ((f12408h * i) + bitmap.getWidth()), f3);
                canvas.drawBitmap(bitmap, f12413m, f12401a);
                f12412l.save();
                f12412l.rotateX(90.0f - f);
                f12412l.getMatrix(f12413m);
                f12412l.restore();
                f12413m.preTranslate((float) (-bitmap2.getWidth()), (float) (-bitmap2.getHeight()));
                f12413m.postTranslate((float) ((f12408h * i) + bitmap2.getWidth()), f3);
            } else {
                float f4 = (float) f9025b;
                float f5 = (f / 90.0f) * f4;
                if (f5 > f4) {
                    f5 = f4;
                }
                if (f5 < 0.0f) {
                    f5 = 0.0f;
                }
                f12412l.save();
                f12412l.rotateY(f);
                f12412l.getMatrix(f12413m);
                f12412l.restore();
                f12413m.preTranslate(0.0f, (float) ((-bitmap.getHeight()) / 2));
                f12413m.postTranslate(f5, (float) ((f12407g * i) + (bitmap.getHeight() / 2)));
                canvas.drawBitmap(bitmap, f12413m, f12401a);
                f12412l.save();
                f12412l.rotateY(f - 90.0f);
                f12412l.getMatrix(f12413m);
                f12412l.restore();
                f12413m.preTranslate((float) (-bitmap2.getWidth()), (float) ((-bitmap2.getHeight()) / 2));
                f12413m.postTranslate(f5, (float) ((f12407g * i) + (bitmap2.getHeight() / 2)));
            }
            canvas.drawBitmap(bitmap2, f12413m, f12401a);
            canvas.restore();
        }
    }

    public static void m12642c(Canvas canvas) {
        float f;
        float f2;
        Matrix matrix = null;
        float f3 = 0;
        float f4 = 0;
        for (int i = 0; i < f12414n; i++) {
            Bitmap[][] bitmapArr = f12411k;
            Bitmap bitmap = bitmapArr[0][i];
            Bitmap bitmap2 = bitmapArr[1][i];
            canvas.save();
            if (f12404d == 1) {
                if (f12415o < 90.0f) {
                    f12412l.save();
                    f12412l.rotateX(f12415o);
                    f12412l.getMatrix(f12413m);
                    f12412l.restore();
                    f12413m.preTranslate((float) ((-bitmap.getWidth()) / 2), (float) ((-bitmap.getHeight()) / 2));
                    matrix = f12413m;
                    f4 = (float) (bitmap.getWidth() / 2);
                    f3 = (float) ((f12407g * i) + (bitmap.getHeight() / 2));
                    matrix.postTranslate(f4, f3);
                    canvas.drawBitmap(bitmap, f12413m, f12401a);
                    canvas.restore();

                } else {
                    f12412l.save();
                    f12412l.rotateX(180.0f - f12415o);
                    f12412l.getMatrix(f12413m);
                    f12412l.restore();
                    f12413m.preTranslate((float) ((-bitmap2.getWidth()) / 2), (float) ((-bitmap2.getHeight()) / 2));
                    matrix = f12413m;
                    f2 = (float) (bitmap2.getWidth() / 2);
                    f = (float) ((f12407g * i) + (bitmap2.getHeight() / 2));
                    matrix.postTranslate(f2, f);
                    canvas.drawBitmap(bitmap2, f12413m, f12401a);
                    canvas.restore();

                }
            } else if (f12415o < 90.0f) {
                f12412l.save();
                f12412l.rotateY(f12415o);
                f12412l.getMatrix(f12413m);
                f12412l.restore();
                f12413m.preTranslate((float) ((-bitmap.getWidth()) / 2), (float) ((-bitmap.getHeight()) / 2));
                matrix = f12413m;
                f4 = (float) ((f12408h * i) + (bitmap.getWidth() / 2));
                f3 = (float) (bitmap.getHeight() / 2);
                matrix.postTranslate(f4, f3);
                canvas.drawBitmap(bitmap, f12413m, f12401a);
                canvas.restore();

            } else {
                f12412l.save();
                f12412l.rotateY(180.0f - f12415o);
                f12412l.getMatrix(f12413m);
                f12412l.restore();
                f12413m.preTranslate((float) ((-bitmap2.getWidth()) / 2), (float) ((-bitmap2.getHeight()) / 2));
                matrix = f12413m;
                f2 = (float) ((f12408h * i) + (bitmap2.getWidth() / 2));
                f = (float) (bitmap2.getHeight() / 2);
                matrix.postTranslate(f2, f);
                canvas.drawBitmap(bitmap2, f12413m, f12401a);
                canvas.restore();

            }

//            matrix.postTranslate(f4, f3);
//            canvas.drawBitmap(bitmap, f12413m, f12401a);
//            canvas.restore();
        }
    }

    public static void m12640a(Canvas canvas) {
        float f;
        float f2;
        Matrix matrix;
        for (int i = 0; i < f12414n; i++) {
            Bitmap[][] bitmapArr = f12411k;
            Bitmap bitmap = bitmapArr[0][i];
            Bitmap bitmap2 = bitmapArr[1][i];
            canvas.save();
            if (f12404d == 1) {
                f12412l.save();
                f12412l.rotateX(-f12415o);
                f12412l.getMatrix(f12413m);
                f12412l.restore();
                f12413m.preTranslate((float) ((-bitmap.getWidth()) / 2), 0.0f);
                f12413m.postTranslate((float) ((f12408h * i) + (bitmap.getWidth() / 2)), f12410j);
                canvas.drawBitmap(bitmap, f12413m, f12401a);
                f12412l.save();
                f12412l.rotateX(90.0f - f12415o);
                f12412l.getMatrix(f12413m);
                f12412l.restore();
                f12413m.preTranslate((float) ((-bitmap2.getWidth()) / 2), (float) (-bitmap2.getHeight()));
                matrix = f12413m;
                f2 = (float) ((f12408h * i) + (bitmap2.getWidth() / 2));
                f = f12410j;

            } else {
                f12412l.save();
                f12412l.rotateY(f12415o);
                f12412l.getMatrix(f12413m);
                f12412l.restore();
                f12413m.preTranslate(0.0f, (float) ((-bitmap.getHeight()) / 2));
                f12413m.postTranslate(f12409i, (float) ((f12407g * i) + (bitmap.getHeight() / 2)));
                canvas.drawBitmap(bitmap, f12413m, f12401a);
                f12412l.save();
                f12412l.rotateY(f12415o - 90.0f);
                f12412l.getMatrix(f12413m);
                f12412l.restore();
                f12413m.preTranslate((float) (-bitmap2.getWidth()), (float) ((-bitmap2.getHeight()) / 2));
                matrix = f12413m;
                f2 = f12409i;
                f = (float) ((f12407g * i) + (bitmap2.getHeight() / 2));

            }
            matrix.postTranslate(f2, f);
            canvas.drawBitmap(bitmap2, f12413m, f12401a);
            canvas.restore();
        }
    }

    public static void m12639a(Bitmap bitmap, Bitmap bitmap2) {

        Bitmap bitmap3;
        Rect rect = null;
        Bitmap bitmap4 = null;
        if (f9024a > 0 || f9025b > 0) {
            f12411k = (Bitmap[][]) Array.newInstance(Bitmap.class, new int[]{2, f12414n});
            f12408h = f9025b / f12414n;
            f12407g = f9024a / f12414n;
            int i = 0;
            while (i < 2) {
                for (int i2 = 0; i2 < f12414n; i2++) {
                    if (f12404d == 1) {
                        int i4 = f12408h;
                        rect = new Rect(i4 * i2, 0, (i2 + 1) * i4, f9024a);
                        bitmap4 = i == 0 ? bitmap : bitmap2;
                        bitmap3 = Bitmap.createBitmap(bitmap4, f12408h * i2, 0, rect.width(), rect.height());
                        f12411k[i][i2] = bitmap3;

                    } else {
                        Bitmap bitmap5 = i == 0 ? bitmap : bitmap2;
                        int i5 = f12407g;
                        int i6 = i5 * i2;
                        Rect rect3 = new Rect(0, i5 * i2, f9025b, (i2 + 1) * f12407g);
                        bitmap3 = Bitmap.createBitmap(bitmap5, 0, i6, rect3.width(), rect3.height());
                        f12411k[i][i2] = bitmap3;

                    }
                }
                i++;
            }
        }
    }

    public static void m12639a910(Bitmap bitmap, Bitmap bitmap2) {
        Bitmap bitmap3;
        Rect rect = null;
        Bitmap bitmap4 = null;
        if (f9024a > 0 || f9025b > 0) {
            f12411k = (Bitmap[][]) Array.newInstance(Bitmap.class, new int[]{2, f12414n});
            f12408h = f9025b / f12414n;
            f12407g = f9024a / f12414n;
            int i = 0;
            while (i < 2) {
                for (int i2 = 0; i2 < f12414n; i2++) {

                    if (f12404d == 1) {
                        Rect rect2 = new Rect(0, f12407g * i2, f9025b, (i2 + 1) * f12407g);
                        bitmap3 = Bitmap.createBitmap(i == 0 ? bitmap : bitmap2, 0, f12407g * i2, rect2.width(), rect2.height());
                        f12411k[i][i2] = bitmap3;

                    } else {
                        int i3 = f12408h;
                        rect = new Rect(i3 * i2, 0, (i2 + 1) * i3, f9024a);
                        bitmap4 = i == 0 ? bitmap : bitmap2;
                        bitmap3 = Bitmap.createBitmap(bitmap4, f12408h * i2, 0, rect.width(), rect.height());
                        f12411k[i][i2] = bitmap3;

                    }
//                    bitmap3 = Bitmap.createBitmap(bitmap4, f12408h * i2, 0, rect.width(), rect.height());
//                    f12411k[i][i2] = bitmap3;
                }
                i++;
            }
        }
    }
}
