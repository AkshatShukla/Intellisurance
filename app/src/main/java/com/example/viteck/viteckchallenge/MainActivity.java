package com.example.viteck.viteckchallenge;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, NavigationView.OnNavigationItemSelectedListener {
    private ViewPager viewPager;
    private FirebaseAuth mAuth;
    private DrawerLayout di;
    private JSONObject MLResponse = null;
    private CardFragment cardFrag;

    Bundle bundle;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        setContentView(R.layout.nav_drawer);
        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }

        mAuth = FirebaseAuth.getInstance();


        Intent i = getIntent();
        cardFrag = new CardFragment();
        if(i.hasExtra("MLResponse")){

            String rawResponse = i.getStringExtra("MLResponse");
            try {
                JSONObject someObj = new JSONObject(rawResponse);
                String s = someObj.get("cust_data").toString();
                JSONObject j = new JSONObject(s);
                double bronzePrem = j.getDouble("BRONZE_PREMIUM");
                double silverPrem = j.getDouble("SILVER_PREMIUM");
                double goldPrem = j.getDouble("GOLD_PREMIUM");
                double platinumPrem = j.getDouble("PLATINUM_PREMIUM");
                int cls = j.getInt("CLASS");

                bundle = new Bundle();
                bundle.putDouble("bronzePremium", bronzePrem);
                bundle.putDouble("silverPremium", silverPrem);
                bundle.putDouble("goldPremium", goldPrem);
                bundle.putDouble("platinumPremium", platinumPrem);
                bundle.putInt("class", cls);
                cardFrag.setArguments(bundle);


            }catch(JSONException e){
                e.printStackTrace();
            }
        }else{

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference mDatabase = database.getReference();

            String user = mAuth.getCurrentUser().getUid();
            final DatabaseReference dbResponse = mDatabase.child("Users").child(user).child("predicted");
            dbResponse.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //String arrayAsString = dataSnapshot.child("preexisting").getValue().toString();
                    //conditionsList = (ArrayList<String>) Arrays.asList(arrayAsString.replace("[", "").replace("]", "").split(","));
                    /*
                    FirebaseDatabase.getInstance().getReference().child("User").child(thisUser).child("predicted").child("Bronze").setValue(bronzePrem);
            FirebaseDatabase.getInstance().getReference().child("User").child(thisUser).child("predicted").child("Silver").setValue(silverPrem);
            FirebaseDatabase.getInstance().getReference().child("User").child(thisUser).child("predicted").child("Gold").setValue(goldPrem);
            FirebaseDatabase.getInstance().getReference().child("User").child(thisUser).child("predicted").child("Platinum").setValue(platinumPrem);
            FirebaseDatabase.getInstance().getReference().child("User").child(thisUser).child("predicted").child("Class").setValue(cls);
                     */
                    double bronze_val = (double) dataSnapshot.child("Bronze").getValue();
                    double silver_val = (double) dataSnapshot.child("Silver").getValue();
                    double gold_val = (double) dataSnapshot.child("Gold").getValue();
                    double plat_val = (double) dataSnapshot.child("Platinum").getValue();
                    int classifier = (int) dataSnapshot.child("Class").getValue();

                    bundle = new Bundle();
                    bundle.putDouble("bronzePremium", bronze_val);
                    bundle.putDouble("silverPremium", silver_val);
                    bundle.putDouble("goldPremium", gold_val);
                    bundle.putDouble("platinumPremium", plat_val);
                    bundle.putInt("class", classifier);

                    cardFrag.setArguments(bundle);
                    TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

                    tabLayout.addTab(tabLayout.newTab().setText("Map View"));
                    tabLayout.addTab(tabLayout.newTab().setText("Recommended Plans"));
                    tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

                    viewPager = (ViewPager) findViewById(R.id.pager);
                    final PageAdapter adapter = new PageAdapter
                            (getSupportFragmentManager(), tabLayout.getTabCount(), cardFrag);
                    viewPager.setAdapter(adapter);
                    tabLayout.setOnTabSelectedListener(MainActivity.this);

                    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        }

        di = (DrawerLayout) findViewById(R.id.drawer_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("Map View"));
        tabLayout.addTab(tabLayout.newTab().setText("Recommended Plans"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);
        final PageAdapter adapter = new PageAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount(), cardFrag);
        viewPager.setAdapter(adapter);
        tabLayout.setOnTabSelectedListener(this);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent intent;
        if (id == R.id.nav_profile) {
            intent = new Intent(this, UserQuestions.class);
            intent.putExtra("fromMain", "true");
            startActivity(intent);

        } else if (id == R.id.nav_logout) {
            mAuth.signOut();
            Snackbar snackbar = Snackbar
                    .make(di, R.string.sign_out, Snackbar.LENGTH_LONG);
            snackbar.show();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
