package org.androidtown.hansungclass.Adapter;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by hscom006 on 2017-11-30.
 */

public class MarginitemDecoration extends RecyclerView.ItemDecoration{

    private final int mVerticalSpaceHeight;

    public MarginitemDecoration(int mMarginSize) {
        this.mVerticalSpaceHeight = mMarginSize;
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = mVerticalSpaceHeight;
        outRect.right = mVerticalSpaceHeight;
        outRect.left = mVerticalSpaceHeight;
        outRect.top = mVerticalSpaceHeight;
    }
}
