package com.example.viteck.viteckchallenge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import eu.amirs.JSON;

/**
 * Created by Eric on 12/2/2017.
 */

public class CardFragment extends Fragment implements ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = "CardFragment";
    double bronzePrem;
    double silverPrem;
    double platinumPrem;
    double goldPrem;
    int cls;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private  ScrollView scrollView;
    private CardView card1;
    boolean card1DidShow = false;
    boolean card2DidShow = false;
    boolean card3DidShow = false;
    boolean card4DidShow = false;
    ViewTreeObserver s;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        final View rootview = inflater.inflate(R.layout.cardlist, container, false);
         card1DidShow = false;
         card2DidShow = false;
         card3DidShow = false;
         card4DidShow = false;

        ImageView platStar = rootview.findViewById(R.id.platStar);
        TextView platPredictedPrice = rootview.findViewById(R.id.platPredictedPrice);
        ImageView platArrow = rootview.findViewById(R.id.platArrow);
        TextView platComparisonText = rootview.findViewById(R.id.platComparisonText);
        ImageView goldStar = rootview.findViewById(R.id.goldStar);
        TextView goldPredictedPrice = rootview.findViewById(R.id.goldPredictedPrice);
        ImageView goldArrow = rootview.findViewById(R.id.goldArrow);
        TextView goldComparisonText = rootview.findViewById(R.id.goldComparisonText);
        ImageView silverStar = rootview.findViewById(R.id.silverStar);
        TextView silverPredictedPrice = rootview.findViewById(R.id.silverPredictedPrice);
        ImageView silverArrow = rootview.findViewById(R.id.silverArrow);
        TextView silverComparisonText = rootview.findViewById(R.id.silverComparisonText);
        ImageView bronzeStar = rootview.findViewById(R.id.bronzeStar);
        TextView bronzePredictedPrice = rootview.findViewById(R.id.bronzePredictedPrice);
        ImageView bronzeArrow = rootview.findViewById(R.id.bronzeArrow);
        TextView bronzeComparisonText = rootview.findViewById(R.id.bronzeComparisonText);

        card1 = rootview.findViewById(R.id.card1);
        final CardView card2 = rootview.findViewById(R.id.card2);
        final CardView card3 = rootview.findViewById(R.id.card3);
        final CardView card4 = rootview.findViewById(R.id.card4);
        scrollView = rootview.findViewById(R.id.scrollView);


        Bundle args = getArguments();
        bronzePrem = args.getDouble("bronzePremium");
        silverPrem = args.getDouble("silverPremium");
        goldPrem = args.getDouble("goldPremium");
        platinumPrem = args.getDouble("platinumPremium");
        cls = args.getInt("class");

        double predictedPrice;

        if(cls == 1){
            predictedPrice = bronzePrem;
            bronzeStar.setVisibility(View.VISIBLE);
        }else if(cls == 2){
            predictedPrice = silverPrem;
            silverStar.setVisibility(View.VISIBLE);
        }else if(cls == 3){
            predictedPrice = goldPrem;
            goldStar.setVisibility(View.VISIBLE);
        }else{
            predictedPrice = platinumPrem;
            platStar.setVisibility(View.VISIBLE);
        }

        platPredictedPrice.setText(Double.toString((int)platinumPrem));
        goldPredictedPrice.setText(Double.toString((int)goldPrem));
        silverPredictedPrice.setText(Double.toString((int)silverPrem));
        bronzePredictedPrice.setText(Double.toString((int)bronzePrem));

        double platPercent = (platinumPrem / predictedPrice) * 100;
        double goldPercent = (goldPrem / predictedPrice) * 100;
        double silverPercent = (silverPrem / predictedPrice) * 100;
        double bronzePercent = (bronzePrem / predictedPrice) * 100;

        platComparisonText.setText(Double.toString((int)platPercent));
        goldComparisonText.setText(Double.toString((int)goldPercent));
        silverComparisonText.setText(Double.toString((int)silverPercent));
        bronzeComparisonText.setText(Double.toString((int)bronzePercent));

        double platDifference = predictedPrice - platinumPrem;
        double goldDifference = predictedPrice - goldPrem;
        double silverDifference = predictedPrice - silverPrem;
        double bronzeDifference = predictedPrice - bronzePrem;

        if(platDifference > 0){
            //Current plan costs less
            platArrow.setImageResource(R.drawable.up_arrow);
            platComparisonText.setTextColor(Color.GREEN);
        }else if(platDifference < 0){
            //Current plan costs more
            platArrow.setImageResource(R.drawable.down_arrow);
            platComparisonText.setTextColor(Color.RED);
        }else{
            platArrow.setVisibility(View.INVISIBLE);
        }
        if(goldDifference > 0){
            //Current plan costs less
            goldArrow.setImageResource(R.drawable.up_arrow);
            goldComparisonText.setTextColor(Color.GREEN);
        }else if(goldDifference < 0){
            //Current plan costs more
            goldArrow.setImageResource(R.drawable.down_arrow);
            goldComparisonText.setTextColor(Color.RED);
        }
        else{
            goldArrow.setVisibility(View.INVISIBLE);
        }
        if(silverDifference > 0){
            //Current plan costs less
            silverArrow.setImageResource(R.drawable.up_arrow);
            silverComparisonText.setTextColor(Color.GREEN);
        }else if(silverDifference < 0){
            //Current plan costs more
            silverArrow.setImageResource(R.drawable.down_arrow);
            silverComparisonText.setTextColor(Color.RED);
        }
        else{
            silverArrow.setVisibility(View.INVISIBLE);
        }
        if(bronzeDifference > 0){
            //Current plan costs less
            bronzeArrow.setImageResource(R.drawable.up_arrow);
            bronzeComparisonText.setTextColor(Color.GREEN);
        }else if(bronzeDifference < 0){
            //Current plan costs more
            bronzeArrow.setImageResource(R.drawable.down_arrow);
            bronzeComparisonText.setTextColor(Color.RED);
        }
        else{
            bronzeArrow.setVisibility(View.INVISIBLE);
        }


        /*
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int size = displayMetrics.heightPixels / 10;
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) card1.getLayoutParams();
        params.topMargin = size;
        ViewGroup.MarginLayoutParams params1 = (ViewGroup.MarginLayoutParams) card2.getLayoutParams();
        params1.topMargin = size;
        ViewGroup.MarginLayoutParams params2 = (ViewGroup.MarginLayoutParams) card3.getLayoutParams();
        params2.topMargin = size;
        ViewGroup.MarginLayoutParams params3 = (ViewGroup.MarginLayoutParams) card4.getLayoutParams();
        params3.topMargin = size;
        */
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                System.out.println("oldX: " + oldScrollX);
                System.out.println("oldY: " + oldScrollY);
                System.out.println("newX: " + scrollX);
                System.out.println("newY: " + scrollY);

//                Rect rect = new Rect();
//                Rect scrollBounds = new Rect();
//                scrollView.getHitRect(scrollBounds);
                Rect rect = new Rect();


                if(card1.getGlobalVisibleRect(rect)
                        && card1.getHeight() == rect.height()
                        && card1.getWidth() == rect.width() && !card1DidShow)  {
                    // imageView is within the visible window

                    card1DidShow = true;
                    card2DidShow = false;
                    card3DidShow = false;
                    card4DidShow = false;
//                    card1.setVisibility(View.VISIBLE);
//                    card1.setAlpha(0.0f);
//
//// Start the animation
//                    card1.animate()
//                            .translationY(card1.getHeight()/4)
//                            .alpha(1.0f)
//                            .setListener(null);
                }
                if(card2.getGlobalVisibleRect(rect)
                        && card2.getHeight() == rect.height()
                        && card2.getWidth() == rect.width() && !card2DidShow)  {
                    // imageView is within the visible window
                    card1DidShow = false;
                    card2DidShow = true;
                    card3DidShow = false;
                    card4DidShow = false;
//                    card2.setVisibility(View.VISIBLE);
//                    card2.setAlpha(0.0f);
//
//
//// Start the animation
//                    card2.animate()
//                            .translationYBy(card3.getHeight()/4)
//                            .alpha(1.0f)
//                            .setListener(null);

                }
                if(card3.getGlobalVisibleRect(rect)
                        && card3.getHeight() == rect.height()
                        && card3.getWidth() == rect.width() && !card3DidShow)  {
                    // imageView is within the visible window
                    card3DidShow = true;
                    card2DidShow = false;
                    card1DidShow = false;
                    card4DidShow = false;
//                    card3.setVisibility(View.VISIBLE);
//                    card3.setAlpha(0.0f);
//
//
//// Start the animation
//                    card3.animate()
//                            .translationYBy(card3.getHeight()/4)
//                            .alpha(1.0f)
//                            .setListener(null);
                }
                if(card4.getGlobalVisibleRect(rect)
                        && card4.getHeight() == rect.height()
                        && card4.getWidth() == rect.width() && !card4DidShow)  {
                    // imageView is within the visible window
                    card4DidShow = true;
                    card3DidShow = false;
                    card2DidShow = false;
                    card1DidShow = false;
//                    card4.setAlpha(0.0f);
//
//                    card4.setVisibility(View.VISIBLE);
//
//// Start the animation
//                    card4.animate()
//                            .translationY(card4.getHeight()/4)
//                            .alpha(1.0f)
//                            .setListener(null);
                }




//                if(card2.getGlobalVisibleRect(rect)
//                        && card2.getHeight() == rect.height()
//                        && card2.getWidth() == rect.width() ) {
//                    Toast.makeText(getContext(), "card2", Toast.LENGTH_SHORT).show();
//                    // view is fully visible on screen
//                }
//
//                else if (card3.getGlobalVisibleRect(rect)
//                        && card3.getHeight() == rect.height()
//                        && card3.getWidth() == rect.width() ) {
//                    Toast.makeText(getContext(), "card3", Toast.LENGTH_SHORT).show();
//                    // view is fully visible on screen
//                }
//                else if (card4.getGlobalVisibleRect(rect)
//                        && card4.getHeight() == rect.height()
//                        && card4.getWidth() == rect.width() ) {
//                    Toast.makeText(getContext(), "card4", Toast.LENGTH_SHORT).show();
//                    // view is fully visible on screen
//                }



            }
        });




        return rootview;
    }

    private boolean isViewVisible(View view) {
        Rect scrollBounds = new Rect();
        scrollView.getDrawingRect(scrollBounds);
        float top = view.getY();
        float bottom = top + view.getHeight();
        if (scrollBounds.top < top && scrollBounds.bottom > bottom) {
            return true; //View is visible.
        } else {
            return false; //View is NOT visible.
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onAttach(Context ctx) {
        //This method is only called on API >= 23
        super.onAttach(ctx);
    }

    @Override
    public void onAttach(Activity ctx) {
        //This method is only called on API < 23. THIS IS NECESSARY.
        super.onAttach(ctx);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onGlobalLayout() {
        final int i[] = new int[2];
        final Rect scrollBounds = new Rect();

        scrollView.getHitRect(scrollBounds);
        card1.getLocationOnScreen(i);

        if (i[1] >= scrollBounds.bottom) {
//            Toast.makeText(getContext(), "TESTING", Toast.LENGTH_SHORT).show();
//            scrollView.post(new Runnable() {
//                @Override
//                public void run() {
//                    sView.smoothScrollTo(0, sView.getScrollY() + (i[1] - scrollBounds.bottom));
//                }
//            });
        }

//        s.removeOnGlobalLayoutListener(this);
    }
}
