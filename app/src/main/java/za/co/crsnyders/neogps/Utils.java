package za.co.crsnyders.neogps;

/**
 * Created by csnyders on 2016/02/23.
 */
public class Utils {

    public static int[] hcl2rgb(double h, double c, double l, double gamma) {
        int[] rgb = new int[3];

        h -= Math.floor(h);

        double q = Math.exp((1.0-3.0*c/4.0/l)*gamma/100.0);
        double min = (4.0*l-3.0*c)/(4.0*q-2.0);
        double max = min+1.5*c/q;

        double hdeg = Math.PI*2.0*h;
        if (hdeg > Math.PI)
            hdeg -= 2*Math.PI;

        if(0<=h && h<=1.0/6.0) {
            rgb[0] = (int)Math.round(max);
            rgb[1] = (int)Math.round((max*Math.tan(1.5*hdeg)+min)/(1.0+Math.tan(1.5*hdeg)));
            rgb[2] = (int)Math.round(min);
        } else if (h<=2.0/6.0) {
            rgb[0] = (int)Math.round((max*(1+Math.tan(0.75*(hdeg-Math.PI)))-min)/(1+Math.tan(0.75*(hdeg-Math.PI))));
            rgb[1] = (int)Math.round(max);
            rgb[2] = (int)Math.round(min);
        } else if (h<=3.0/6.0) {
            rgb[0] = (int)Math.round(min);
            rgb[1] = (int)Math.round(max);
            rgb[2] = (int)Math.round( max*(1+Math.tan(0.75*(hdeg-Math.PI)))-min*Math.tan(0.75*(hdeg-Math.PI)));
        } else if (h<=4.0/6.0) {
            rgb[0] = (int)Math.round(min);
            rgb[1] = (int)Math.round((min*Math.tan(1.5*(hdeg+Math.PI))+max)/(1+Math.tan(1.5*(hdeg+Math.PI))));
            rgb[2] = (int)Math.round(max);
        } else if (h<=5.0/6.0) {
            rgb[0] = (int)Math.round((min*(1+Math.tan(0.75*hdeg))-max)/(Math.tan(0.75*hdeg)));
            rgb[1] = (int)Math.round(min);
            rgb[2] = (int)Math.round(max);
        } else {
            rgb[0] = (int)Math.round(max);
            rgb[1] = (int)Math.round(min);
            rgb[2] = (int)Math.round(min*(1.0+Math.tan(0.75*hdeg))-max*Math.tan(0.75*hdeg));
        }

        rgb[0] = Math.min(Math.max(rgb[0],0), 255);
        rgb[1] = Math.min(Math.max(rgb[1],0), 255);
        rgb[2] = Math.min(Math.max(rgb[2],0), 255);

        return rgb;
    }
}
