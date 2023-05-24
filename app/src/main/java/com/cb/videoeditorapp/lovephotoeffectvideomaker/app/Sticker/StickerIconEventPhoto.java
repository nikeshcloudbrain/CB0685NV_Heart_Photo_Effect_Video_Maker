package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Sticker;

import android.view.MotionEvent;

/**
 * @author wupanjie
 */

public interface StickerIconEventPhoto {
  void onActionDown(StickerViewPhoto stickerView, MotionEvent event);

  void onActionMove(StickerViewPhoto stickerView, MotionEvent event);

  void onActionUp(StickerViewPhoto stickerView, MotionEvent event);
}
