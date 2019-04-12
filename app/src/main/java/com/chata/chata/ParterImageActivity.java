package com.chata.chata;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.chata.chata.clickableareasimage.ClickableArea;
import com.chata.chata.clickableareasimage.ClickableAreasImage;
import com.chata.chata.clickableareasimage.Direction;
import com.chata.chata.clickableareasimage.OnClickableAreaClickedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParterImageActivity extends BasePageActivity  implements OnClickableAreaClickedListener {

    final int OSW_GARAZ = 24;
    final int OSW_KOTLOWNIA = 25;
    final int OSW_WIATROLAP = 26;
    final int OSW_KORYTARZ = 28;
    final int OSW_SALON_LED = 30;
    final int OSW_JADALNIA = 31;
    final int OSW_KUCHNIA = 32;
    final int OSW_BAREK = 33;
    final int OSW_SPIZARNIA = 34;
    final int OSW_GABINET = 35;
    final int OSW_LAZIENKA = 36;
    final int OSW_WEJSCIE_GLOWNE = 37;
    final int OSW_WEJSCIE_KOLUMNA = 38;
    final int OSW_FRONT = 39;
    final int OSW_SALON_KOMINEK = 40;
    final int OSW_LAZIENKA_LUSTRO = 41;
    final int OSW_SALON = 42;
    final int OSW_KUCHNIA_SZAFKI = 43;
    final int OSW_SCHODY = 29;

    //PhotoViewAttacher attacher;
    private ImageView image;
    private int xDown;
    private int yDown;
    private ViewGroup mainLayout;
    private LinearLayout.LayoutParams lParams;
    private ClickableAreasImage clickableAreasImage;

    List<ClickableArea> clickableAreas;
    List<ClickableArea> dragableAreas;

    private Rect imageRectangle;
    private double scale;

    private boolean isMoving = false;
    private Bitmap bitmap;
    private Canvas canvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oswietlenie_parter_image);

        //mainLayout = (RelativeLayout) findViewById(R.id.)
        image = findViewById(R.id.parterImage);
        image.setImageResource(R.drawable.ina_r1);

        image.setOnTouchListener(onTouchListener());

        clickableAreasImage = new ClickableAreasImage(image, this);

        final View rootView = getWindow().getDecorView().getRootView();
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        if (imageRectangle == null) {
                            LinearLayout layout = findViewById(R.id.parterLayout);
                            lParams = (LinearLayout.LayoutParams) image.getLayoutParams();

                            int width = layout.getWidth() - lParams.leftMargin - lParams.rightMargin;
                            int height = layout.getHeight() - lParams.topMargin - lParams.bottomMargin;
                            imageRectangle = calculateFitCenterObjectRect(width, height, 1359, 1651);

                            clickableAreas = getClickableAreas();
                            clickableAreasImage.setClickableAreas(clickableAreas);

                            dragableAreas = getDragableAreas();
                            clickableAreasImage.setDragableAreas(dragableAreas);

                            //image.setColorFilter(Color.rgb(123, 123, 123), PorterDuff.Mode.LIGHTEN);
                            //bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
                            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ina_r1);
                            Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                            canvas = new Canvas(mutableBitmap);

                            UpdateState();
                        }
                    }
                });
    }

    public Rect calculateFitCenterObjectRect (float containerWidth, float containerHeight, float objectWidth, float objectHeight) {

        scale = Math.min( (double)containerWidth/(double)objectWidth, (double)containerHeight/(double)objectHeight );

        int h = (int) ((double)scale * objectHeight); // new height of the object
        int w = (int) ((double)scale * objectWidth); // new width of the object

        int x = (int) ((double)(containerWidth - w)*0.5f); // new x location of the object relative to the container
        int y = (int) ((double)(containerHeight - h)*0.5f); // new y  location of the object relative to the container

        // calculate the empty space between the object and the container after fit center
        int marginRight = (int) ((double)(containerWidth - w) * 0.5f);
        int marginLeft = (int) ((double)(containerWidth - w) * 0.5f);
        int marginTop = (int) ((double)(containerHeight - h) * 0.5f);
        int marginBottom = (int) ((double)(containerHeight - h) * 0.5f);

        return new Rect( x, y, x + w, y + h );
    }

    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                final int x = (int) event.getX() - imageRectangle.left;
                final int y = (int) event.getY() - imageRectangle.top;

                final int top = view.getTop();
                final int actionType = event.getAction() & MotionEvent.ACTION_MASK;

                Log.d("myTag", "onTouch() - ".concat(String.valueOf(actionType)).concat(" ").concat(String.valueOf(x)).concat(" ").concat(String.valueOf(y)));

                switch (actionType) {

                    case MotionEvent.ACTION_DOWN:
                        xDown = x - lParams.leftMargin;
                        yDown = y - lParams.topMargin;
                        isMoving = false;
                        break;

                    case MotionEvent.ACTION_UP:
                        if (isMoving)
                            clickableAreasImage.onDrag(x, y, xDown, yDown);
                        else
                            clickableAreasImage.onPhotoTap(xDown, yDown);

                        isMoving = false;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        int xMove = x - lParams.leftMargin;
                        int yMove = y - lParams.topMargin;

                        if (xMove != xDown || yMove != yDown)
                            isMoving = true;
                        break;
                }

                //mainLayout.invalidate();
                return true;
            }
        };
    }

    private List<ClickableArea> getDragableAreas() {
        List<ClickableArea> dragableAreas = new ArrayList<>();

        dragableAreas.add(new ClickableArea(440 , 146 , 281 , 37 , scale, new Roleta("salon2")));
        dragableAreas.add(new ClickableArea(726 , 187 , 17 , 200 , scale, new Roleta("salon1")));
        dragableAreas.add(new ClickableArea(841 , 407 , 156 , 28 , scale, new Roleta("gabinet")));
        dragableAreas.add(new ClickableArea(155 , 630 , 41 , 200 , scale, new Roleta("jadalnia")));
        dragableAreas.add(new ClickableArea(193 , 1308 , 215 , 48 , scale, new Roleta("kuchnia")));
        dragableAreas.add(new ClickableArea(522 , 1501 , 63 , 30 , scale, new Roleta("garderoba")));
        dragableAreas.add(new ClickableArea(744 , 1493 , 59 , 40 , scale, new Roleta("kotlownia")));

        return dragableAreas;
    }

    private List<ClickableArea> getClickableAreas() {
        List<ClickableArea> clickableAreas = new ArrayList<>();

        clickableAreas.add(new ClickableArea(936 , 786 , 340 , 740 , scale, new Light("garaz", OSW_GARAZ)));
        clickableAreas.add(new ClickableArea(155 , 151 , 290 , 143 , scale, new Light("salon_kominek", OSW_SALON_KOMINEK)));
        clickableAreas.add(new ClickableArea(158 , 298 , 545 , 280 , scale, new Light("salon", OSW_SALON)));
        clickableAreas.add(new ClickableArea(451 , 157 , 280 , 215 , scale, new Light("salon", OSW_SALON)));
        clickableAreas.add(new ClickableArea(518 , 578 , 185 , 260 , scale, new Light("salon_led", OSW_SALON_LED)));
        clickableAreas.add(new ClickableArea(159 , 580 , 345 , 252 , scale, new Light("jadalnia", OSW_JADALNIA)));
        clickableAreas.add(new ClickableArea(151 , 833 , 365 , 124 , scale, new Light("barek", OSW_BAREK)));
        clickableAreas.add(new ClickableArea(247 , 958 , 163 , 280 , scale, new Light("kuchnia", OSW_KUCHNIA)));
        clickableAreas.add(new ClickableArea(154 , 959 , 90 , 400 , scale, new Light("kuchnia_szafki", OSW_KUCHNIA_SZAFKI)));
        clickableAreas.add(new ClickableArea(244 , 1238 , 155 , 115 , scale, new Light("kuchnia_szafki", OSW_KUCHNIA_SZAFKI)));
        clickableAreas.add(new ClickableArea(414 , 1135 , 111 , 205 , scale, new Light("spizarnia", OSW_SPIZARNIA)));
        clickableAreas.add(new ClickableArea(517 , 836 , 190 , 288 , scale, new Light("hall", OSW_KORYTARZ)));
        clickableAreas.add(new ClickableArea(536 , 1132 , 171 , 364 , scale, new Light("wiatrolap", OSW_WIATROLAP)));
        clickableAreas.add(new ClickableArea(729 , 1148 , 184 , 348 , scale, new Light("kotlownia", OSW_KOTLOWNIA)));
        clickableAreas.add(new ClickableArea(729 , 824 , 184 , 180 , scale, new Light("lazienka", OSW_LAZIENKA)));
        clickableAreas.add(new ClickableArea(735 , 762 , 173 , 60 , scale, new Light("lazienka_lustro", OSW_LAZIENKA_LUSTRO)));
        clickableAreas.add(new ClickableArea(728 , 421 , 347 , 337 , scale, new Light("gabinet", OSW_GABINET)));
        clickableAreas.add(new ClickableArea(709 , 1019 , 204 , 101 , scale, new Light("schody", OSW_SCHODY)));

        return clickableAreas;
    }


    public void UpdateState() {
        try {
            utility.Get("http://" + getLightHost() + "/getStatus", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response != null && response.length() > 0) {
                        Map<Integer, Boolean> state = ParseStatus(response);
                        UpdateAreas(state);
                    }
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void UpdateAreas(Map<Integer, Boolean> state) {
        for (ClickableArea<Light> area : clickableAreas) {
            boolean isOn = state.get(area.getItem().Output);
            area.getItem().State = isOn;
        }
    }

    protected String getLightHost() {
        return "192.168.1.25";
    }

    protected String getRoletyHost() {
        return "192.168.1.26";
    }

    public Map<Integer, Boolean> ParseStatus(String response) {
        Map<Integer, Boolean> statusMap = new HashMap<Integer, Boolean>();
        String[] lines = response.split("\n");
        for (String line : lines) {
            String[] lights = line.split("=");
            statusMap.put(Integer.parseInt(lights[0]), lights[1].trim().equals("1"));
        }
        return statusMap;
    }

    //@Override
    public void onClickableAreaTouched(Object item, int x, int y, int width, int height) {
        if (item instanceof Light) {
            Light light = (Light) item;
            light.State = !light.State;

            utility.Post("http://" + getLightHost() + "/impulsOswietlenie", light.Name + "=" + Boolean.toString(light.State));

            Paint paint = new Paint();
            paint.setColor(Color.GREEN);

            Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

            for (int i = 0; i < 50; i++) {
                for (int j = 0; j < 50; j++) {

                    mutableBitmap.setPixel(x + i, y + j, Color.GREEN);

                }
            }

            image.setImageBitmap(mutableBitmap);
            image.setImageDrawable(new BitmapDrawable(getResources(), mutableBitmap));
            image.postInvalidate();
        }
    }

   // @Override
    public boolean onClickableAreaDragged(Object item, Direction direction) {
        if (item instanceof Roleta) {
            String roleta = ((Roleta)item).getName();
            utility.Post("http://" + getRoletyHost() + "/impulsRolety", roleta.concat("_").concat(direction.toString().toLowerCase()));
            Toast.makeText(image.getContext(), roleta.concat("_").concat(direction.toString().toLowerCase()), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
