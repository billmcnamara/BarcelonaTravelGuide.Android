package fr.mcnamara.grenobleguide;
 
import android.app.Activity;
import android.content.Context; 
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.util.Log; 
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ClickedThis extends Activity {
	//private static final String TAG = "ClickedThis";
	final int NORTH = 0;
	final int SOUTH = 1;
	final int EAST  = 2;
	final int WEST  = 3; 
	double lon;
	double lat;
	String head;
	String link;
	 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        Bundle bun = getIntent().getExtras();
      //int imagenumber = bun.getInt("imagenumber"); 			//Log.v(TAG, "imagenumber  :" + imagenumber);  
      //************************************************************************************************************* 
        String clkTITLE 	= bun.getString("title");			//Log.v(TAG, "clkTITLE     :" + clkTITLE);
        head = clkTITLE;
        String clkCATEGORY 	= bun.getString("category");		//Log.v(TAG, "clkCATEGORY  :" 	+ clkCATEGORY);
        String clkIMAGE 	= bun.getString("image");   		//Log.v(TAG, "clkIMAGE     :" 	+ clkIMAGE);  
        String clkLATITUDE 	= bun.getString("latitude");		//Log.v(TAG, "clkLATITUDE :" 	+ clkLATITUDE);
        String clkLONGITUDE = bun.getString("longitude");		//Log.v(TAG, "clkLONGITUDE:" 	+ clkLONGITUDE);
        lat = convertStringToDouble(clkLATITUDE);
        lon = convertStringToDouble(clkLONGITUDE);  
        String clkLINK 		= bun.getString("link");  			//Log.v(TAG, "clkLINK     :" 	+ clkLINK);
        link = clkLINK;
        
        //***********************************************************************************************************
        // the following is not shown currently in this view
        //String clkNAME 		= bun.getString("name"); 			//Log.v(TAG, "clkNAME      :" 	+ clkNAME); 
        String clkDESCRIPTION1 = bun.getString("description1");	//Log.v(TAG, "clkDESCRIPTION1:" + clkDESCRIPTION1);
        String clkDESCRIPTION2 = bun.getString("description2");	//Log.v(TAG, "clkDESCRIPTION2:" + clkDESCRIPTION2);
        String clkEXTRA1 	= bun.getString("extra1");			//Log.v(TAG, "clkEXTRA1   :" 	+ clkEXTRA1);            
        //String clkADVICE 	= bun.getString("advice");			//Log.v(TAG, "clkADVICE   :" 	+ clkADVICE);
        //*************************************************************************************************************
        int nsew;
        nsew = bun.getInt("nsew"); 				  //Log.v(TAG, "nsew         :" + nsew);
        setContentView(R.layout.clickeditem);
              if (clkCATEGORY.equals("north"))  { nsew = NORTH;
		}else if (clkCATEGORY.equals("south"))  { nsew = SOUTH;
		}else if (clkCATEGORY.equals("east"))   { nsew = EAST;
		}else if(clkCATEGORY.equals("west"))    { nsew = WEST;
		}else if(clkCATEGORY.equals("midlands")){ nsew = WEST; clkCATEGORY = "west";  }
        //************************************************************************************************************* 
        String imagestr = clkIMAGE					  ; //Log.v(TAG, "imagestr :" + imagestr);
		String name0 = clkCATEGORY + "_" + imagestr	  ; //Log.v(TAG, "name0    :" + name0);
		String name1 = name0.replace(".jpg", "")      ; //Log.v(TAG, "name1    :" + name1);   
        String name2 = name0.replace(".jpg", "_2")    ; //Log.v(TAG, "name2    :" + name2);
        String namem = name0.replace(".jpg", "_map")  ; //Log.v(TAG, "namem    :" + namem);
        String namet = name0.replace(".jpg", "_t")    ; //Log.v(TAG, "namet    :" + namet);
 
        ImageView imageViewt = (ImageView)findViewById(R.id.imagetitle);
        ImageView imageView1 = (ImageView)findViewById(R.id.image1);
        ImageView imageView2 = (ImageView)findViewById(R.id.image2);
        ImageView imageViewm = (ImageView)findViewById(R.id.imagemap);

        imageView1.setImageResource(getImageId(this, name1));        
        imageViewt.setImageResource(getImageId(this, namet));   
        imageView2.setImageResource(getImageId(this, name2));  
        imageViewm.setImageResource(getImageId(this, namem)); 
        
        TextView descr1 = (TextView)findViewById(R.id.description1);
        TextView descr2 = (TextView)findViewById(R.id.description2);
        TextView extra1 = (TextView)findViewById(R.id.extra1);
        
        descr1.setText(clkDESCRIPTION1);
        descr2.setText(clkDESCRIPTION2);
        extra1.setText(clkEXTRA1);
        
}

private double convertStringToDouble(String aString) {
    double aDouble = Double.parseDouble(aString); 
    return aDouble;
}

public static int getImageId(Context context, String imageName) {
		//Log.v(TAG, "getImageId: input=" + imageName);
     	//alternative method at stackoverflow
		//int return_val = context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
		int return_val =  context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        //Log.v(TAG, "getImageId: return=" + return_val);
        return return_val;
}

public static String getImageName(Context context, int id) {
   		//Log.v(TAG, "getImageName: input=" + id);
	    String return_str = context.getResources().getString(id); 
        //Log.v(TAG, "getImageName: return=" + return_str);
        return return_str;
}
 
public void onGoogleMapClick(View view)  
{  
    //Toast.makeText(this, head + " @ " + lat +"," +lon, Toast.LENGTH_SHORT).show();
    Toast.makeText(this, head , Toast.LENGTH_LONG).show();
    //doesn't work 

    //String geoUriString="geo:"+lat+","+lon+"("+head+")"; //not ok 
    //String geoUriString="geo:"+lat+","+lon+"+("+head+")"; //not ok
    //String geoUriString="geo:"+lat+","+lon+" ("+head+")"; //not ok
    //String geoUriString="geo:"+lat+","+lon+"?q="+head; //ok but search
    String geoUriString="geo:"+lat+","+lon+"?q="+lat+","+lon+"("+head+")"; //ok 
    //"geo:0,0?q=Matterhorn&z=8?z=1"
    //String geoUriString="geo:"+lat+","+lon; // was working
    //String geoUriString="geo:"+lat+","+lon+"?z=15";
    
    //q="+slatitude+","+slongitude+"+("+title+")"
    //<string name="map_location">"google.streetview:cbll=46.813812,-71.207378&cbp=1,99.56,,1,-5.27&mz=21"</string>  1,yaw,,pitch,zoom    
    //String geoUriString = getResources().getString(map_location);
    //String geoUriString = getResources().getString(R.string.map_location);
    Uri geoUri = Uri.parse(geoUriString);
    //Log.v(TAG, "String: "+geoUriString);
    //Intent myIntent = new Intent(view.getContext(), NoDistanceListView.class);
    Intent mapCall  = new Intent(Intent.ACTION_VIEW, geoUri);
    startActivity(mapCall);
}

public void onLinkClick(View view)  
{  
    Toast.makeText(this, head , Toast.LENGTH_LONG).show();
    String linkUriString=link;
    Uri linkUri = Uri.parse(linkUriString);
    //Log.v(TAG, "String: "+linkUriString);
    Intent linkCall  = new Intent(Intent.ACTION_VIEW, linkUri);
    startActivity(linkCall);
}


}
