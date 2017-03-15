package alc.javadevslagos.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import alc.javadevslagos.R;
import alc.javadevslagos.models.JavaDeveloper;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dannytee on 10/03/2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder>  {

    private ArrayList<JavaDeveloper> javaDeveloperList;

    private Context mContext;

    public ListAdapter(ArrayList<JavaDeveloper> javaDeveloperList, Context mContext) {
        this.javaDeveloperList = javaDeveloperList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        JavaDeveloper javaDeveloper = javaDeveloperList.get(position);
        holder.textView_username.setText(javaDeveloper.getLogin());

        Picasso.with(mContext)
                .load(javaDeveloper.getAvatarUrl())
                .placeholder(R.mipmap.placeholder)
                .resize(50, 50)
                .centerCrop()
                .into(holder.imageView_avatar);
    }

    @Override
    public int getItemCount() {
        return javaDeveloperList.size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, JavaDeveloper javaDeveloper) {
        javaDeveloperList.add(position, javaDeveloper);
        notifyItemInserted(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView_username;
        public CircleImageView imageView_avatar;

        public MyViewHolder(View view) {
            super(view);
            textView_username = (TextView) view.findViewById(R.id.textview_username);
            imageView_avatar = (CircleImageView) view.findViewById(R.id.imageview_avatar);
        }
    }
}
