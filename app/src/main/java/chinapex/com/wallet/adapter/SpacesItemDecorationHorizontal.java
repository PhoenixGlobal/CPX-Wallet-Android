package chinapex.com.wallet.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpacesItemDecorationHorizontal extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecorationHorizontal(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State
            state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        // Add top margin only for the first item to avoid double space between items
        // 横向排列无需控制
//        if (parent.getChildPosition(view) == 0)
        outRect.top = space;
    }

}
