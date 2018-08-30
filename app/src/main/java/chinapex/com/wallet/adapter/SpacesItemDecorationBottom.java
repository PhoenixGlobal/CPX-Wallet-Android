package chinapex.com.wallet.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpacesItemDecorationBottom extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecorationBottom(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        outRect.left = space;
//        outRect.right = space;

        if (parent.getChildPosition(view) == 0) return;
        outRect.bottom = space;

        // Add top margin only for the first item to avoid double space between items

    }

}
