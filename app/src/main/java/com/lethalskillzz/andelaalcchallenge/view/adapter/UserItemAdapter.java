package com.lethalskillzz.andelaalcchallenge.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.lethalskillzz.andelaalcchallenge.R;
import com.lethalskillzz.andelaalcchallenge.model.UserItem;
import com.lethalskillzz.andelaalcchallenge.view.holder.ProgressViewHolder;
import com.lethalskillzz.andelaalcchallenge.view.holder.UserItemHolder;

import java.util.List;

import static com.lethalskillzz.andelaalcchallenge.manager.AppConfig.VIEW_ITEM_USER;
import static com.lethalskillzz.andelaalcchallenge.manager.AppConfig.VIEW_PROG;

/**
 * Created by lethalskillzz on 05/03/2017.
 */

public class UserItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UserItem> userItems;
    private Context context;

    public UserItemAdapter(Context context, List<UserItem> userItems) {
        this.userItems = userItems;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return userItems.get(position) != null ? VIEW_ITEM_USER : VIEW_PROG;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM_USER) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_user, parent, false);

            vh = new UserItemHolder(v,userItems);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_loading_footer, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof UserItemHolder) {
            final UserItem item = userItems.get(position);
            ((UserItemHolder) holder).login.setText(item.getLogin());


            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            // generate random color
            int color = generator.getRandomColor();

            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(item.getLogin().substring(0,1), color);


            Glide.with(context).load(item.getAvatarUrl())
                    .asBitmap().centerCrop().placeholder(drawable).into(new BitmapImageViewTarget(((UserItemHolder) holder).avatar) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    ((UserItemHolder) holder).avatar.setImageDrawable(circularBitmapDrawable);
                }
            });


        }
    }


    @Override
    public int getItemCount() {
        return this.userItems.size();
    }
}
