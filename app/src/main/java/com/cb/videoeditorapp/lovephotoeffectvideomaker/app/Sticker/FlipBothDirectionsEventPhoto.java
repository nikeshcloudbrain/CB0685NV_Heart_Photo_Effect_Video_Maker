package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Sticker;

/**
 * @author wupanjie
 */

public class FlipBothDirectionsEventPhoto extends AbstractFlipEventPhoto {

  @Override
  @StickerViewPhoto.Flip protected int getFlipDirection() {
    return StickerViewPhoto.FLIP_VERTICALLY | StickerViewPhoto.FLIP_HORIZONTALLY;
  }
}
