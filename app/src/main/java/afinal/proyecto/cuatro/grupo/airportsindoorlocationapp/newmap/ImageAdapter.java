package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.newmap;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.R;

public class ImageAdapter extends BaseAdapter {

    private Context mContext;

    private ContentZone contentZone;

    private List<String> way;

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

    /* Here we received a contentZone data when are interacting with a beacon */
    public void adjustMapWith(ContentZone contentZone) {
        this.contentZone = contentZone;
    }

    /* Here we received a list of node when someone select a destination */
    public void adjustMapWithDestination(ArrayList<String> way) {
        this.way = way;
    }


    /* CACA hardcodeada fea - BORRAR ESTO POR DIOS (Si llegamos con el tiempo) */
    private String getPos(String code) {

        String pos;

        switch (code) {
            case "le2":
                pos = "8,p8;9,p9";
                break;
            case "br2":
                pos = "13,p13;14,p14";
                break;
            case "le1":
                pos = "9,p9;10,p10";
                break;
            case "ca2":
                pos = "5,p5;6,p6;9,p9;10,p10";
                break;
            case "br1":
                pos = "11,p11;10,p10";
                break;
            case "co1":
                pos = "1,p1;2,p2;5,p5;6,p6";
                break;
            case "co2":
                pos = "2,p2;3,p3;6,p6;7,p7";
                break;
            case "ca1":
                pos = "6,p6;7,p7";
                break;
            default:
                pos = null;
        }

        return pos;
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

        /* Draw the road if exist */
        if (way != null) {

            for (String node : way) {

                String positionString = getPos(node);
                List<String> aux = Arrays.asList(positionString.split("\\s*;\\s*"));

                if (way.indexOf(node) == 0) {
                    System.out.println("-------->  first element");
                }
                if ((way.indexOf(node) + 1) == way.size()) {
                    System.out.println("-------->  last element");
                }

                for (String element : aux) {

                    List<String> elementParts = Arrays.asList(element.split("\\s*,\\s*"));

                    Integer pos = Integer.parseInt(elementParts.get(0));
                    String posName = elementParts.get(1);

                    System.out.println("-------->  code: " + node + " pos: " + pos + " posName: " + posName);

                    /* Make de resourceName */
                    String resourceName = posName + "_" + node;

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
            }
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

}