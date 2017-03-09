package com.lethalskillzz.andelaalcchallenge.view.widget;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;

import com.lethalskillzz.andelaalcchallenge.util.ToolbarUtil;

/**
 * Created by Ibrahim on 09/12/2015.
 */
public class ScrollingFABBehaviour extends CoordinatorLayout.Behavior<FloatingActionButton> {

    private int toolbarHeight;

    public ScrollingFABBehaviour(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.toolbarHeight = ToolbarUtil.getToolbarHeight(context);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton fab, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton fab, View dependency) {
        if (dependency instanceof AppBarLayout) {
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
            int fabBottomMargin = lp.bottomMargin;
            int distanceToScroll = fab.getHeight() + fabBottomMargin;
            float ratio = (float)dependency.getY()/(float)toolbarHeight;
            fab.setTranslationY(-distanceToScroll * ratio);
        }
        return true;
    }
}
