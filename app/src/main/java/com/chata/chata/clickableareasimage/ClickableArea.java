package com.chata.chata.clickableareasimage;

/**
 * Created by Lukas on 10/22/2015.
 */
public class ClickableArea<T> {

    private int x;
    private int y;
    private int w;
    private int h;

    private T item;

    public ClickableArea(int x, int y, int w, int h, double scale, T item){
        this.x = (int) (x * scale);
        this.y = (int) (y * scale);
        this.w = (int) (w * scale);
        this.h = (int) (h * scale);
        this.item = item;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public T getItem() {
        return item;
    }

    public void setLabel(T item) {
        this.item = item;
    }
}
