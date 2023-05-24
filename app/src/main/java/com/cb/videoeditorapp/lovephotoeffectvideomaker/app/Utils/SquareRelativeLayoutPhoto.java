package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class SquareRelativeLayoutPhoto extends RelativeLayout {
    public SquareRelativeLayoutPhoto(Context context) {
        super(context);
    }

    public SquareRelativeLayoutPhoto(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SquareRelativeLayoutPhoto(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    protected void onMeasure(int i, int i2) {
        if (i < i2) {
            super.onMeasure(i, i);
        } else {
            super.onMeasure(i2, i2);
        }
    }
}
