package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.newmap;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import org.json.JSONArray;

import java.util.Arrays;
import java.util.List;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;

public class ImageAdapter extends BaseAdapter {

    private Context mContext;

    private ContentZone contentZone;

    // Image Map
    private Integer[] mThumbIds = {
            R.drawable.p0, R.drawable.p1, R.drawable.p2, R.drawable.p3,
            R.drawable.p4, R.drawable.p5, R.drawable.p6, R.drawable.p7,
            R.drawable.p8, R.drawable.p9, R.drawable.p10, R.drawable.p11,
            R.drawable.p12, R.drawable.p13, R.drawable.p14, R.drawable.p15,
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

    int getThumbId(int position) {
        return mThumbIds[position];
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        /* ImageView to return */
        ImageView imageView;

        /* Check if a convertView exists */
        if (convertView == null) {

            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        } else {

            imageView = (ImageView) convertView;
        }

        /* Check if there are any contentZone active */
        if (contentZone != null) {

            if (contentZone.getIsComingIn()) {

                Log.i(
                        "*** ImageAdapter",
                        "Beacon " + contentZone.getTag() + " coming in"
                );

                List<String> aux = contentZone.getPos();

                for (String element : aux) {

                    List<String> elementParts = Arrays.asList(element.split("\\s*,\\s*"));

                    Integer pos = Integer.parseInt(elementParts.get(0));
                    String posName = elementParts.get(1);

                    /* Make de resourceName */
                    String resourceName = posName + "_" + contentZone.getCode();

                    /* Get the active image */
                    Resources resources = mContext.getResources();
                    final int resourceId = resources.getIdentifier(
                            resourceName,
                            "drawable",
                            mContext.getPackageName()
                    );

                    /* Set the active image */
                    mThumbIds[Integer.parseInt(String.valueOf(pos))] = resourceId;
                }

            } else {

                contentZone.setIsComingIn(false);

                List<String> aux = contentZone.getPos();

                for (String element : aux) {

                    List<String> elementParts = Arrays.asList(element.split("\\s*,\\s*"));

                    Integer pos = Integer.parseInt(elementParts.get(0));
                    String posName = elementParts.get(1);

                    /* Get the original image back */
                    Resources resources = mContext.getResources();
                    final int resourceId = resources.getIdentifier(
                            posName,
                            "drawable",
                            mContext.getPackageName()
                    );

                    /* Set the original image back */
                    Log.i(
                            "*** ImageAdapter",
                            "Beacon " + contentZone.getTag() + " coming out"
                    );
                    mThumbIds[Integer.parseInt(String.valueOf(pos))] = resourceId;
                }
            }
        }

        /* Set the rest of the image map */
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    /* Here we received the contentZone data */
    public void adjustMapWith(ContentZone contentZone) {
        this.contentZone = contentZone;
    }

    public void adjustMapWithDestination(Integer pos, JSONArray resp) {

        Log.i(
                "*** ImageAdapter",
                "adjustMapWithDestination - pos: " + pos + " resp: " + resp
        );
    }
}