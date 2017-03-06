package com.lethalskillzz.andelaalcchallenge.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.lethalskillzz.andelaalcchallenge.R;


/**
 * Created by lethalskillzz on 7/18/2016.
 */
public class ProgressViewHolder extends RecyclerView.ViewHolder {
    public ProgressBar progressBar;

    public ProgressViewHolder(View v) {
        super(v);
        progressBar = (ProgressBar) v.findViewById(R.id.footer_progressbar);
    }
}
