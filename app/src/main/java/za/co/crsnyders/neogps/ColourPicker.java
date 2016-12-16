package za.co.crsnyders.neogps;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import za.co.crsnyders.neogps.layout.CircleLayout;

import static za.co.crsnyders.neogps.Utils.hcl2rgb;
import android.content.*;
import android.os.*;
import android.support.v4.content.*;
import android.widget.*;
import java.util.*;

public class ColourPicker extends AppCompatActivity implements IntentHandler
{

	@Override
	public void handleIntent(Intent intent)
	{
		String direction =  intent.getExtras().getString("directions");
		DirectionLogFragment.getInstance().add(direction);
		
	}
	

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colour_picker);
		
		

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
						DirectionLogFragment.getInstance().add("test");
						
            }
        });
		
		System.out.println("starting service");
		startService(new Intent(this, MapsNotificationListener.class));
		
		IntentFilter statusIntentFilter = new IntentFilter();
		statusIntentFilter.addAction("za.co.crsnyders.neopixel.direction_update");
		LocalBroadcastManager.getInstance(this).registerReceiver(new IntentManager(this),statusIntentFilter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_colour_picker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

	public static class DirectionLogFragment extends Fragment{
		private static final String ARG_SECTION_NUMBER = "section_number";
		private View rootView;
		private ListView listview;
		private ArrayAdapter<String> adapter;
		private static DirectionLogFragment fragment;
		
		private DirectionLogFragment(){
			
		};
		public static DirectionLogFragment newInstance(int sectionNumber) {
            if(fragment == null){
			fragment = new DirectionLogFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
			}
			
            return fragment;
        }
		public static DirectionLogFragment getInstance(){
			return fragment;
		}
		
		public void add(String text){
			this.adapter.add(text);
			this.adapter.notifyDataSetChanged();
		}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            this.rootView = inflater.inflate(R.layout.fragment_direction_log, container, false);
            this.listview = (ListView)this.rootView.findViewById(R.id.textLog);
			this.adapter = new TextAdapter<>(this.rootView.getContext());
			this.listview.setAdapter(adapter);
			
            return this.rootView;
        }
		}
		/**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener{
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        private View rootView;
        private CircleLayout circleLayout;
        private EditText ledNumber;
        private List<Dot> dots;
        private SeekBar seekBar;
        private int width;
        private int height;
        private int ledSize;
        private int radius;

        private PostService postService;
        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }
		
		

        @Override
        public void onClick(View v) {
            this.dots = makeRaintbowDots();
            drawDots(this.dots);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            this.rootView = inflater.inflate(R.layout.fragment_colour_picker, container, false);
            this.circleLayout = (CircleLayout)this.rootView.findViewById(R.id.circleLayout);
            this.ledNumber = (EditText) this.rootView.findViewById(R.id.ledNumber);
            this.seekBar = (SeekBar)this.rootView.findViewById(R.id.seekBar);
            DisplayMetrics metrics = this.rootView.getContext().getResources().getDisplayMetrics();
            this.width = metrics.widthPixels;
            this.height = metrics.heightPixels;

            this.ledSize = 20;
            this.radius = 550;
            Button go  = (Button)this.rootView.findViewById(R.id.go);
            go.setOnClickListener(this);
            postService = new PostService();
            this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    drawDots(dots);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            dots = makeRaintbowDots();
            this.drawDots(dots);

            return this.rootView;
        }

        private List<Dot> makeRaintbowDots(){

            List<Dot> dots  = new ArrayList<>();
            for(int i =0; i< getLedCount();i++){

                double degrees = Math.toDegrees(i * getStep());
                int[] rgb = hcl2rgb(degrees / 360, 100, 100, 0);
                dots.add(new Dot(rgb));

            }

            return dots;
        }

        private void drawDots(List<Dot> dots){

            circleLayout.removeAllViews();

            CircleView circleView = new CircleView(rootView.getContext());
            int offset = (int)Math.round((getRotation() / 360.0) * getLedCount());
            /*for(int i =0;i<dots.size();i++){
                int index = (i+offset)% getLedCount();
                long x = Math.round(width/2 + radius * Math.cos(i * getStep()) - ledSize*3);
                long y = Math.round(height * 0.30 + radius * Math.sin(i * getStep()) - ledSize / 2);

                Point p = new Point();
                p.set((int) x, (int) y);
                Dot dot  = dots.get(index);
                dot.setPoint(p);
                circleView.addDot(dot);
            }*/
            //postService.writeString(arrayString(circleView.getDots()));
            //circleLayout.addView(circleView);
            for(int i =0;i<dots.size();i++) {
                Dot dot  = dots.get((i+offset)%getLedCount());
                GradientDrawable d = (GradientDrawable) ContextCompat.getDrawable(this.getContext(), R.drawable.dot_bg);
                d.setColor(dot.getColour());


                ImageView image = new ImageView(this.getContext());
                image.setImageDrawable(d);

                image.setOnTouchListener(new View.OnTouchListener() {
                   @Override
                   public boolean onTouch(View v, MotionEvent event) {
                       switch (event.getAction()) {
                           case MotionEvent.ACTION_MOVE:
                               System.out.println("MOVE");
                               float newX = event.getX();
                               float newY = event.getY();
                               float oldY = event.getHistoricalY(event.getPointerCount()-1,1);
                               float oldX = event.getHistoricalX(event.getPointerCount()-1,1);
                               return true;
                       }
                       return false;
                   }
               });

                circleLayout.addView(image);
            }
           /* LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(100,100);

            image.setLayoutParams(parms);
            image.requestLayout();

           image.setOnDragListener(new View.OnDragListener() {
               @Override
               public boolean onDrag(View v, DragEvent event) {
                   System.out.println("Drag Event: "+event.getAction());
                   return false;
               }
           });*/
        }

        public String arrayString(List<Dot> dots){
            char[] characters =  new char[dots.size()*3];
            int counter =0;
            for(int i=0;i<dots.size();i++) {
                characters[counter] = ((char) dots.get(i).getRgb()[0]);
                characters[counter++] = ((char) dots.get(i).getRgb()[1]);
                characters[counter++] = ((char) dots.get(i).getRgb()[2]);
                counter++;
            }
            return new String(characters);
        }

        public double getStep(){
            return (2*Math.PI)/getLedCount();
        }
        private int getRotation(){
            return this.seekBar.getProgress();
        }

        private int getLedCount(){
            return Integer.parseInt(ledNumber.getText().toString());
        }
		
	
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
			if(position ==0){
				return PlaceholderFragment.newInstance(position + 1);
			}else{
				DirectionLogFragment f = DirectionLogFragment.newInstance(position + 1);
				return f;
			}
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
				case 1:
					return "SECTION 2";
            }
            return null;
        }
    }
}

class IntentManager extends BroadcastReceiver {

	private IntentHandler handler;

	public IntentManager(IntentHandler handler){
		this.handler = handler;

	}

	@Override
	public void onReceive(Context p1, Intent p2) {
		handler.handleIntent(p2);
	}


}

class StableArrayAdapter extends ArrayAdapter<String> {

		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId) {
			super(context, textViewResourceId);
		}
		
		@Override
		public void add(String item){
		 super.add(item);
		}

		@Override
		public long getItemId(int position) {
			String item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

	}

