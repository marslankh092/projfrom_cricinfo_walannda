package com.virmana.status_app_all.Adapters;

import android.app.Activity;
import android.content.Context;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.virmana.status_app_all.R;
import com.virmana.status_app_all.model.Comment;
import com.squareup.picasso.Picasso;
import com.virmana.status_app_all.ui.Activities.SupportActivity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Tamim on 16/01/2018.
 */


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {
    private List<Comment> commentList= new ArrayList<>();
    private Activity context;
    public CommentAdapter(List<Comment> commentList, Activity context){
        this.context=context;
        this.commentList=commentList;
    }
    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewHolder= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, null, false);
        viewHolder.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new CommentAdapter.CommentHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(CommentHolder holder,final int xposition) {
        int position=holder.getAdapterPosition();
        holder.text_view_time_item_comment.setText(commentList.get(position).getCreated());

        byte[] data = Base64.decode(commentList.get(position).getContent(), Base64.DEFAULT);
        String Comment_text = "";
        try {
            Comment_text = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Comment_text = commentList.get(position).getContent();
        }

        holder.text_view_name_item_comment.setText(commentList.get(position).getUser());
        Picasso.get().load(commentList.get(position).getImage()).placeholder(R.drawable.profile).into(holder.image_view_comment_iten);
        if (!commentList.get(position).getEnabled()){
            holder.text_view_content_item_comment.setText(context.getResources().getString(R.string.comment_hidden));
            holder.text_view_content_item_comment.setTextColor(context.getResources().getColor(R.color.gray_color));
        }else{
            holder.text_view_content_item_comment.setText(Comment_text);
        }
        if (commentList.get(position).getTrusted().equals("true")){
            holder.image_view_comment_item_verified.setVisibility(View.VISIBLE);
        }else{
            holder.image_view_comment_item_verified.setVisibility(View.GONE);
        }
        holder.image_view_report.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(context,  holder.image_view_report);
            popup.getMenuInflater().inflate(R.menu.menu_report, popup.getMenu());

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.report:
                                Intent intent = new Intent(context, SupportActivity.class);
                                intent.putExtra("message","Hi Admin, Please check this comment i think should be removed comment id : "+commentList.get(position).getId() );
                                context.startActivity(intent);
                                break;
                            case R.id.report_user:
                                Intent intent_user = new Intent(context, SupportActivity.class);
                                intent_user.putExtra("message","Hi Admin, Please check this user i think should be removed user name : "+commentList.get(position).getUser() );
                                context.startActivity(intent_user);
                                break;
                        }
                        return false;
                }
            });

            popup.show();
        });
    }
    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class CommentHolder extends RecyclerView.ViewHolder {
        private final TextView text_view_name_item_comment;
        private final TextView text_view_time_item_comment;
        private final TextView text_view_content_item_comment;
        private final CircleImageView image_view_comment_iten;
        private final ImageView image_view_comment_item_verified;
        private final AppCompatImageView image_view_report;

        public CommentHolder(View itemView) {
            super(itemView);
            this.image_view_report=(AppCompatImageView) itemView.findViewById(R.id.image_view_report);

            this.image_view_comment_iten=(CircleImageView) itemView.findViewById(R.id.image_view_comment_iten);
            this.image_view_comment_item_verified=(ImageView) itemView.findViewById(R.id.image_view_comment_item_verified);
            this.text_view_name_item_comment=(TextView) itemView.findViewById(R.id.text_view_name_item_comment);
            this.text_view_time_item_comment=(TextView) itemView.findViewById(R.id.text_view_time_item_comment);
            this.text_view_content_item_comment=(TextView) itemView.findViewById(R.id.text_view_content_item_comment);
        }
    }
}
