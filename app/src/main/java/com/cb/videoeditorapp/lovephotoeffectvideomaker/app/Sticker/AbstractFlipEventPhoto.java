package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Sticker;

import android.view.MotionEvent;

public abstract class AbstractFlipEventPhoto implements StickerIconEventPhoto {

  @Override
  public void onActionDown(StickerViewPhoto stickerView, MotionEvent event) {

  }

  @Override
  public void onActionMove(StickerViewPhoto stickerView, MotionEvent event) {

  }

  @Override
  public void onActionUp(StickerViewPhoto stickerView, MotionEvent event) {
    stickerView.flipCurrentSticker(getFlipDirection());
  }

  @StickerViewPhoto.Flip protected abstract int getFlipDirection();
}
