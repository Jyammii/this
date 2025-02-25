package com.example.closetifiy_finalproject;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class CanvasItem implements Parcelable {
    private Uri uri;
    private float left;
    private float top;
    private int width;
    private int height;

    public CanvasItem(Uri uri, float left, float top, int width, int height) {
        this.uri = uri;
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
    }

    protected CanvasItem(Parcel in) {
        uri = in.readParcelable(Uri.class.getClassLoader());
        left = in.readFloat();
        top = in.readFloat();
        width = in.readInt();
        height = in.readInt();
    }

    public static final Creator<CanvasItem> CREATOR = new Creator<CanvasItem>() {
        @Override
        public CanvasItem createFromParcel(Parcel in) {
            return new CanvasItem(in);
        }

        @Override
        public CanvasItem[] newArray(int size) {
            return new CanvasItem[size];
        }
    };

    public Uri getUri() {
        return uri;
    }

    public float getLeft() {
        return left;
    }

    public float getTop() {
        return top;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(uri, flags);
        dest.writeFloat(left);
        dest.writeFloat(top);
        dest.writeInt(width);
        dest.writeInt(height);
    }
}