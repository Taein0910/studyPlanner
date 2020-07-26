package com.icecream.studyplanner;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<Todo> mList;


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView title;
        protected ImageButton item_done;
        protected ImageButton item_delete;

        public CustomViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.item_text);
            this.item_done = (ImageButton) view.findViewById(R.id.item_done);
            this.item_delete = (ImageButton) view.findViewById(R.id.item_delete);
        }
    }


    public CustomAdapter(ArrayList<Todo> list) {
        this.mList = list;
    }



    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_row, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);


        return viewHolder;


    }




    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder viewholder, final int position) {

        final Context context;
        viewholder.title.setText(mList.get(position).getTitle());
        /// 완료 버튼 이벤트
        viewholder.item_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewholder.title.getPaintFlags() == 0) {
                    viewholder.title.setPaintFlags(viewholder.title.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                    viewholder.item_done.setImageResource(R.drawable.ic_action_done_green);

                } else {
                    viewholder.title.setPaintFlags(0);
                    viewholder.item_done.setImageResource(R.drawable.ic_action_done);

                }

            }
        });
        ///////////\

        /// 삭제 버튼 이벤트
        viewholder.item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.remove(position);
                notifyItemRemoved(position);
            }
        });
        ///////////
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}