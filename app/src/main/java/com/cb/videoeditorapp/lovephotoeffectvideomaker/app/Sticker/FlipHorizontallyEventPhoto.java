package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Sticker;

/**
 * @author wupanjie
 */

public class FlipHorizontallyEventPhoto extends AbstractFlipEventPhoto {

  @Override
  @StickerViewPhoto.Flip protected int getFlipDirection() {
    return StickerViewPhoto.FLIP_HORIZONTALLY;
  }
}
