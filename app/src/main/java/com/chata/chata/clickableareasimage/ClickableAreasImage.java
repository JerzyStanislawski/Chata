package com.chata.chata.clickableareasimage;

import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Lukas on 10/22/2015.
 */
public class ClickableAreasImage {

    private ImageView imageView;
    private OnClickableAreaClickedListener listener;

    private List<ClickableArea> clickableAreas;
    private List<ClickableArea> dragableAreas;

    private int imageWidthInPx;
    private int imageHeightInPx;

    public ClickableAreasImage(ImageView imageView, OnClickableAreaClickedListener listener){
        this.imageView = imageView;
        init(listener);
    }

    private void init(OnClickableAreaClickedListener listener) {
        this.listener = listener;
        getImageDimensions(imageView);
    }


    private void getImageDimensions(ImageView imageView){
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        imageWidthInPx = (int) (drawable.getBitmap().getWidth() / Resources.getSystem().getDisplayMetrics().density);
        imageHeightInPx = (int) (drawable.getBitmap().getHeight() / Resources.getSystem().getDisplayMetrics().density);
    }

    public void onPhotoTap(int x, int y) {
        //PixelPosition pixel = ImageUtils.getPixelPosition(x, y, imageWidthInPx, imageHeightInPx);
        List<ClickableArea> clickableAreas = getClickAbleAreas(x, y, getClickableAreas());
        for(ClickableArea ca : clickableAreas){
            listener.onClickableAreaTouched(ca.getItem(), ca.getX(), ca.getY(), ca.getW(), ca.getH());
        }
    }

    public void onDrag(int x, int y, int startX, int startY) {
        int xLeft = Math.min(x, startX);
        int xRight = Math.max(x, startX);
        int yUp = Math.min(y, startY);
        int yDown = Math.max(y, startY);

        for (int ix = (int) xLeft; ix <= xRight; ix++) {
            for (int iy = (int) yUp; iy <= yDown; iy++) {
                List<ClickableArea> clickableAreas = getClickAbleAreas(ix, iy, getDragableAreas());
                for (ClickableArea area : clickableAreas) {
                    if (listener.onClickableAreaDragged(area.getItem(), y < startY ? Direction.UP : Direction.DOWN)) {
                        return;
                    }
                }
            }
        }
    }


    private List<ClickableArea> getClickAbleAreas(int x, int y, List<ClickableArea> areas){
        List<ClickableArea> clickableAreas= new ArrayList<>();
        for(ClickableArea ca : areas){
            if(isBetween(ca.getX(),(ca.getX()+ca.getW()),x)){
                if(isBetween(ca.getY(),(ca.getY()+ca.getH()),y)){
                    clickableAreas.add(ca);
                }
            }
        }
        return clickableAreas;
    }

    private boolean isBetween(int start, int end, int actual){
        return (start <= actual && actual <= end);
    }

    public void setClickableAreas(List<ClickableArea> clickableAreas) {
        this.clickableAreas = clickableAreas;
    }

    public List<ClickableArea> getClickableAreas() {
        return clickableAreas;
    }

    public void setDragableAreas(List<ClickableArea> dragableAreas) {
        this.dragableAreas = dragableAreas;
    }

    public List<ClickableArea> getDragableAreas() {
        return dragableAreas;
    }
}
