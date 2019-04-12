package com.chata.chata.clickableareasimage;

/**
 * Created by Lukas on 10/22/2015.
 */
public interface OnClickableAreaClickedListener<T> {
    void onClickableAreaTouched(T item, int x, int y, int width, int height);
    boolean onClickableAreaDragged(T item, Direction direction);
}

