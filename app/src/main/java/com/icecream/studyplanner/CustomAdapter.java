package com.icecream.studyplanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.JsonReader;
import android.util.Log;
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
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.icecream.studyplanner.MainActivity.MyPREFERENCES;
import static com.icecream.studyplanner.MainActivity.roadJSONTodo;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<Todo> mList;
    SharedPreferences sharedpreferences;
    private String roadJson;
    private Context context;



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
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_row, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        roadJson = roadJSONTodo;



        return viewHolder;


    }




    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder viewholder, final int position) {

        viewholder.title.setText(mList.get(position).getTitle());
        /// 완료 버튼 이벤트
        viewholder.item_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //json삭제

                ArrayList<String> list = new ArrayList<String>();
                list.clear();
                int len = 0;
                try {
                    JSONArray jsonArray = new JSONArray(roadJson);
                    len = jsonArray.length();
                    if (jsonArray != null) {
                        for (int i=0;i<len;i++){
                            list.add(jsonArray.get(i).toString());
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                list.remove((len-position)-1);
                roadJson = list.toString();

                SharedPreferences sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("todoJson", roadJson);
                editor.commit();

                Log.e("json", roadJson);

                mList.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();

            }
        });
        ///////////\

        viewholder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TextView 클릭
                Context context = v.getContext();
                Intent intent = new Intent(context, TodoDetail.class);

                intent.putExtra("todoDetailTitle", mList.get(position).getTitle());

                intent.putExtra("todoDetailDescription", mList.get(position).getDescription());
                Log.e("detail",  mList.get(position).getTitle()+", "+mList.get(position).getDescription());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}