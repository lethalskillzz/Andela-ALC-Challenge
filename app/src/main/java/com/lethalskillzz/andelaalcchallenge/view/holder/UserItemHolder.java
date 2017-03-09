package com.lethalskillzz.andelaalcchallenge.view.holder;

import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lethalskillzz.andelaalcchallenge.R;
import com.lethalskillzz.andelaalcchallenge.model.UserItem;
import com.lethalskillzz.andelaalcchallenge.view.activity.UserListActivity;

import java.util.List;

import static com.lethalskillzz.andelaalcchallenge.manager.AppConfig.handlerClickUser;

/**
 * Created by lethalskillzz on 05/03/2017.
 */

public class UserItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView avatar;
    public TextView login;
    private List<UserItem> userItems;
    private View itemLayout;


    public UserItemHolder(View convertView, List<UserItem> userItems) {
        super(convertView);
        this.userItems = userItems;

        itemView.setOnClickListener(this);

        avatar = (ImageView) convertView.findViewById(R.id.item_user_avatar);
        login = (TextView) convertView.findViewById(R.id.item_user_login);

        itemLayout = (View) convertView.findViewById(R.id.item_user_layout);
        itemLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.item_user_layout: {
                clickUser(view);
            }
            break;

            default: {
            }
            break;
        }
    }


    private void clickUser(View v) {
        final int position = getPosition();
        UserItem item = userItems.get(position);

        Message msg = new Message();
        msg.what = handlerClickUser;

        msg.obj = item.getId()+"<>"+item.getLogin()+"<>"+item.getAvatarUrl()+"<>"+item.getUrl();

        if(UserListActivity.mUiHandler != null)
            UserListActivity.mUiHandler.sendMessage(msg);

    }

}
