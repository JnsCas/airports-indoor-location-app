package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.newmap;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;

public class ImageAdapter extends BaseAdapter {

    private Context mContext;

    private ContentZone contentZone;

    // Image Map
    private Integer[] mThumbIds = {
            R.drawable.pos01,R.drawable.pos02, R.drawable.pos03,
            R.drawable.pos04,R.drawable.pos05, R.drawable.pos06,
            R.drawable.pos07,R.drawable.pos08, R.drawable.pos09,
    };

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    int getThumbId(int position){
        return mThumbIds[position];
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        // ImageView to return
        ImageView imageView;

        if (convertView == null) {
            /*
            Crear un nuevo Image View de 90x90
            y con recorte alrededor del centro
             */
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(90,90));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        } else {

            imageView = (ImageView) convertView;
        }



        /* Check if there are any contentZone active */
        if (contentZone != null) {

            if (contentZone.getIsComingIn()) {

                Log.i("*** ImageAdapter","Beacon "+contentZone.getTag()+" coming in");
                mThumbIds[Integer.parseInt(String.valueOf(contentZone.getPos()))] = R.drawable.beacon;

            } else {

                contentZone.setIsComingIn(false);

                Resources resources = mContext.getResources();
                final int resourceId = resources.getIdentifier(contentZone.getPosName(),
                        "drawable", mContext.getPackageName());

                Log.i("*** ImageAdapter","Beacon "+contentZone.getTag()+" coming out");
                mThumbIds[Integer.parseInt(String.valueOf(contentZone.getPos()))] = resourceId;
            }
        }

        //Setear la imagen desde el recurso drawable
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    void adjustMapWith(ContentZone contentZone) {
        this.contentZone = contentZone;
    }

}