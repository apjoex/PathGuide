package adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pathguide.R;

import java.util.ArrayList;

import models.Lab;

/**
 * Created by AKINDE-PETERS on 6/9/2016.
 */
public class LabListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Lab> labs;

    public LabListAdapter(Context context, ArrayList<Lab> labs){
        this.context = context;
        this.labs = labs;
    }

    @Override
    public int getCount() {
        return labs.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.class_list_item, null);

            viewHolder.class_name = (TextView)convertView.findViewById(R.id.class_name);
            Typeface customFont = Typeface.createFromAsset(context.getAssets(),"fonts/Montserrat.otf");
            viewHolder.class_name.setTypeface(customFont, Typeface.BOLD);

            convertView.setTag(viewHolder);
        }else{ viewHolder = (ViewHolder)convertView.getTag(); }

        viewHolder.class_name.setText(labs.get(i).getName());

        return convertView;
    }

    class ViewHolder{
        TextView class_name;
    }

}
