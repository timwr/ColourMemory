
package tv.accedo.colourmemory;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GridAdapter extends BaseAdapter {

    private Context context;
    private Puzzle[] gridValues;

    public GridAdapter(Context context, Puzzle[] gridValues) {
        this.context = context;
        this.gridValues = gridValues;
    }

    @Override
    public int getCount() {
        return gridValues.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Puzzle current = gridValues[position];
        ImageView gridView;
        if (convertView == null) {
            gridView = new ImageView(context);
        } else {
            gridView = (ImageView) convertView;
        }

        if (current.removed) {
            gridView.setVisibility(View.GONE);
        } else {
            gridView.setVisibility(View.VISIBLE);
        }
        gridView.setBackgroundResource(R.drawable.card_bg);
        if (current.revealed) {
            if (current.type == 1) {
                gridView.setImageResource(R.drawable.colour1);
            } else if (current.type == 2) {
                gridView.setImageResource(R.drawable.colour2);
            } else if (current.type == 3) {
                gridView.setImageResource(R.drawable.colour3);
            } else if (current.type == 4) {
                gridView.setImageResource(R.drawable.colour4);
            } else if (current.type == 5) {
                gridView.setImageResource(R.drawable.colour5);
            } else if (current.type == 6) {
                gridView.setImageResource(R.drawable.colour6);
            } else if (current.type == 7) {
                gridView.setImageResource(R.drawable.colour7);
            } else if (current.type == 8) {
                gridView.setImageResource(R.drawable.colour8);
            }
        } else {
            gridView.setImageResource(0);
        }

        return gridView;
    }
}
