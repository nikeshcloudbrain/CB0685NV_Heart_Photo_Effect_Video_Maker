package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Sticker;

import android.view.MotionEvent;

/**
 * @author wupanjie
 */

public class ZoomIconEventPhoto implements StickerIconEventPhoto {
  @Override
  public void onActionDown(StickerViewPhoto stickerView, MotionEvent event) {

  }

  @Override
  public void onActionMove(StickerViewPhoto stickerView, MotionEvent event) {
    stickerView.zoomAndRotateCurrentSticker(event);
  }

  @Override
  public void onActionUp(StickerViewPhoto stickerView, MotionEvent event) {
    if (stickerView.getOnStickerOperationListener() != null) {
      stickerView.getOnStickerOperationListener()
          .onStickerZoomFinished(stickerView.getCurrentSticker());
    }
  }
}
