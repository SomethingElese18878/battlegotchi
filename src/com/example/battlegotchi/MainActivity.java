package com.example.battlegotchi;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	ImageButton btnFeed;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        addListenerOnButton();
    }
    
    public void sendInfo(View view) {
    	Toast.makeText(MainActivity.this, "Info!", Toast.LENGTH_SHORT).show();
    }
    public void sendFeed(View view) {
    	Toast.makeText(MainActivity.this, "feed feed!", Toast.LENGTH_SHORT).show();
    }
    public void sendMessage(View view) {
    	Toast.makeText(MainActivity.this, "Send message!", Toast.LENGTH_SHORT).show();
    }
    public void sendFight(View view) {
    	Toast.makeText(MainActivity.this, "Send fight!", Toast.LENGTH_SHORT).show();
    }
    
    //Listener for buttons when they created at runtime    
//    public void addListenerOnButton() {
//		
//    	ImageButton btnInfo = (ImageButton) findViewById(R.id.btnInfo);
//    	ImageButton btnFeed = (ImageButton) findViewById(R.id.btnFeed);
//    	
//		btnFeed.setOnClickListener(new View.OnClickListener() {
//				
//			public void onClick(View v) {
//				Toast.makeText(MainActivity.this, "Feed feed!", Toast.LENGTH_SHORT).show();
//			}
//		});
//			
//	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}