package za.co.crsnyders.neogps;

import android.graphics.Color;
import android.graphics.Point;

import java.util.Arrays;

/**
 * Created by csnyders on 2016/02/23.
 */
public class Dot {

    private Point point;
    private int[]rgb;

    public Dot(int[] rgb){
        this.rgb = rgb;
    }
    public Dot(int[] rgb, Point point){
    this.rgb = rgb;
        this.point = point;
    }

    public void setPoint(Point point ){
        this.point = point;
    }

    public int[] getRgb(){
        return this.rgb;
    }
    public Point getPoint(){
        return point;
    }
    public int getColour(){
        return Color.rgb(rgb[0],rgb[1],rgb[2]);
    }

    public String toString(){
        return Arrays.toString(rgb)+ " "+ point;
    }

}
