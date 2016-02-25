package za.co.crsnyders.neogps;
/**
 * Created by csnyders on 2016/02/23.
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class CircleView extends View {

    private List<Dot> dots = new ArrayList<>();
    public CircleView(Context context) {
        super(context);

    }

    public void addDot(Dot dot){
        this.dots.add(dot);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        for(Dot dot : dots) {
            Paint paint = new Paint();
            paint.setColor(dot.colour);
            canvas.drawCircle(dot.point.x, dot.point.y, 50, paint);
        }
    }

}
