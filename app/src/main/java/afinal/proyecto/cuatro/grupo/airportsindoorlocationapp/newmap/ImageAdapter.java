package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.newmap;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /* Any time that something change in the view, the map cleans */
    private ImageView cleanMap(ImageView imageView){

        /* Get the active image */
        Resources resources = mContext.getResources();

        for(int i=0; i<16; i++){

            final int resourceId = resources.getIdentifier(
                    "p"+i,
                    "drawable",
                    mContext.getPackageName()
            );

            /* Set the active image */
            mThumbIds[i] = resourceId;
        }
        return imageView;
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

        /* Clean map */

        imageView = cleanMap(imageView);

        /* Create a hashMap to check to illuminate when I move in the map */

        Map<String, String> hashMap = new HashMap<>();

        /* Draw the road if exist */
        if (way != null) {

            for (String node : way) {

                /* Orientation */

                String orientation = getOrientation(way, node);

                /* Get Positions */

                String positionString = getPos(node);
                String[] aux = positionString.split("\\s*;\\s*");

                /* for each position in node */

                for (String element : aux) {

                    List<String> elementParts = Arrays.asList(element.split("\\s*,\\s*"));

                    Integer pos = Integer.parseInt(elementParts.get(0));
                    String posName = elementParts.get(1);

                    /* Make de resourceName */
                    String resourceName = posName + "_wf_" + orientation;


                    /* Get the active image */
                    Resources resources = mContext.getResources();
                    final int resourceId = resources.getIdentifier(
                            resourceName,
                            "drawable",
                            mContext.getPackageName()
                    );

                    /* Adding resource into the hashMap*/
                    hashMap.put(posName, resourceName);

                    System.out.println("--->  node: " + node + " resourceName: " + resourceName);

                    /* Set the active image */
                    mThumbIds[Integer.parseInt(String.valueOf(pos))] = resourceId;
                }
            }

            System.out.println("---> final hashMap: " + hashMap);
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


                    /* Check in hashMap if is part of the road */

                    String value = hashMap.get(posName);

                    if (value != null) {

                        resourceName = value + "_ilu_" + contentZone.getCode();

                        System.out.println("---> resourceName: " + resourceName);
                    }

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

                    /* Check in hashMap if is part of the road */

                    String value = hashMap.get(posName);

                    if (value != null) {

                        posName = value;

                        System.out.println("---> resourceName: " + posName);
                    }

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

    /* A PARTIR DE ACÁ CACA */

    /* BORRAR ESTO POR DIOS (Si llegamos con el tiempo) */

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

    private Integer getNumber(String code) {

        Integer num;

        switch (code) {
            case "le2":
                num = 1;
                break;
            case "br2":
                num = 2;
                break;
            case "le1":
                num = 3;
                break;
            case "ca2":
                num = 4;
                break;
            case "br1":
                num = 4;
                break;
            case "co1":
                num = 5;
                break;
            case "co2":
                num = 6;
                break;
            case "ca1":
                num = 7;
                break;
            default:
                num = 0;
        }
        return num;
    }

    /* Define the direction of the road */
    private String getOrientation(List<String> way, String node) {

        // I get current node order number
        Integer number = getNumber(node);

        // I get previous node order number
        Integer anterior = 0;

        if (way.indexOf(node) != 0) {

            try {
                anterior = getNumber(way.get(way.indexOf(node) - 1));

            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        // Set postfijo order

        String postfijo;

        if (number > anterior) {

            postfijo = "i";

        } else if (number < anterior) {

            postfijo = "d";

        } else {

            postfijo = "c";

            // si no es nodo inicial y origen es menor que actual viene de abajo

            if (way.indexOf(node) != 0 && getNumber(way.get(0)) < number) {

                postfijo = "c_down";

            } else if (way.indexOf(node) != 0 && getNumber(way.get(0)) > number) {

                postfijo = "c_up";
            }
        }

        String orientation;

        // Set orientation

        if (way.indexOf(node) == 0) {

            orientation = "o" + postfijo;

            switch (node) {
                case "ca1":
                    orientation = "o" + postfijo + "_ca1";
                    break;
                case "ca2":
                    orientation = "o" + postfijo + "_ca2";
                    break;
                case "co1":
                    orientation = "o" + postfijo + "_co1";
                    break;
                case "co2":
                    orientation = "o" + postfijo + "_co2";
                    break;
            }

        } else if ((way.indexOf(node) + 1) == way.size()) {

            orientation = "f" + postfijo;

            switch (node) {
                case "ca1":
                    orientation = "f" + postfijo + "_ca1";
                    break;
                case "ca2":
                    orientation = "f" + postfijo + "_ca2";
                    break;
                case "co1":
                    orientation = "f" + postfijo + "_co1";
                    break;
                case "co2":
                    orientation = "f" + postfijo + "_co2";
                    break;
                case "le1":
                    orientation = "f" + postfijo + "_le1";
                    break;
            }

        } else {

            orientation = "d" + postfijo;

            if ( way.get(0).equals("ca1") && node.equals("ca2") ) {

                orientation = "d" + postfijo + "_ca1";

            }
        }

        return orientation;
    }

}