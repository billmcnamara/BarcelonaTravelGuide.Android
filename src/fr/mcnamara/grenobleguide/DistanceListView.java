package fr.mcnamara.grenobleguide;

import java.io.InputStream;
import java.util.ArrayList; 
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;  

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity; 
import android.content.Context;
import android.content.Intent;   
import android.location.Location;   
import android.os.Bundle;
import android.util.Log;
import android.view.View; 
 
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView;
import android.widget.Toast;
 
public class DistanceListView extends ListActivity {
    //private static final String TAG = "DistanceListView ============>";
    private int NSEW = 0;
    // content array index
	final int NORTH = 0; final int SOUTH = 1; final int EAST = 2; final int WEST = 3; 
	// cardinal points
	final int NN = 0; final int NE = 1; final int EE = 2; final int SE = 3; 
	final int SS = 4;	final int SW = 5; final int WW = 6; final int NW = 7; 
	double lat, lon = 0.0;
	Location coord;
    float accuracyd;
    private int adviceDrawable,imageaid,bearingDrawable,imageid, imageDrawable, imagetDrawable, imagetid, iimage;
    String clkNAME, clkTITLE, clkCATEGORY, clkIMAGE, clkDESCRIPTION1, clkDESCRIPTION2, clkEXTRA1, clkLATITUDE, clkLONGITUDE, clkADVICE, clkLINK;
    ArrayList<HashMap<String, Object>> clicklist = new ArrayList<HashMap<String, Object>>();
	
	
public void onCreate(Bundle InstanceState) 
{	
	super.onCreate(InstanceState);  
	Bundle bundle = getIntent().getExtras(); 
	int nsew = bundle.getInt("nsew");  
	NSEW = nsew;  
	lat = bundle.getDouble("lat");  
	lon = bundle.getDouble("lon");  

    coord = getCoord(lat,lon);
   
	
	//Log.d(TAG, "gps  : mylongitude,mylatitude: (d)  " + coord.getLongitude() +","+ coord.getLatitude());   
	buildObjectFromFile(coord);
}
	
public Location getCoord(double lat2, double lon2)
{
	if ((lat2 == 0.0) && (lon2 == 0.0)) {
		 String Text = "location is unknown, trying to locate..";
         Toast.makeText( getApplicationContext(), Text, Toast.LENGTH_LONG).show();
	}  else {
		 String Text = "location is:"+ lon2 +","+lat2;
		 Toast.makeText( getApplicationContext(), Text, Toast.LENGTH_LONG).show();
	}	
    coord = new Location("myapp");
	coord.setLatitude(lat);
	coord.setLongitude(lon);

	return coord;
}

@Override
protected void onListItemClick(ListView l, View v, int position, long id) {
	super.onListItemClick(l, v, position, id);
	HashMap<String, Object> clickeditem = clicklist.get(position) ;
	Intent i = new Intent(this, ClickedThis.class);

	Bundle j = new Bundle();
	j.putInt("imagenumber", position); 
	j.putInt("nsew", NSEW);
	//************************************************************** 
	clkNAME        = (String) clickeditem.get("name"); 
	clkTITLE       = (String) clickeditem.get("title"); 
	clkCATEGORY    = (String) clickeditem.get("category");
	clkIMAGE       = (String) clickeditem.get("image"); 
	clkDESCRIPTION1= (String) clickeditem.get("description1");
	clkDESCRIPTION2= (String) clickeditem.get("description2");
	clkEXTRA1      = (String) clickeditem.get("extra1");
	clkLATITUDE    = (String) clickeditem.get("latitude");
	clkLONGITUDE   = (String) clickeditem.get("longitude");

	
	
	//
	String imageastr = getAdviceDrawable(clkADVICE);
	clkADVICE      = imageastr.replace(".png", "").replace("res/drawable/", "")     ;
	//Log.e(TAG, "clkADVICE :" + clkADVICE);
	//Log.e(TAG, "imageastr :" + imageastr);
    //
	
	
	
	clkLINK        = (String) clickeditem.get("link");
	j.putString("name", clkNAME); 
	j.putString("title", clkTITLE);
	j.putString("category", clkCATEGORY);
	j.putString("image", clkIMAGE); 
	j.putString("description1", clkDESCRIPTION1);
	j.putString("description2", clkDESCRIPTION2);
	j.putString("extra1", clkEXTRA1); 
	j.putString("latitude", clkLATITUDE);
	j.putString("longitude", clkLONGITUDE);
	j.putString("advice", clkADVICE);
	j.putString("link", clkLINK);		            
	i.putExtras(j);       
	startActivity( i ); 
}

private void buildObjectFromFile(Location inputCoord) {
/*
    telnet localhost 5554
    geo fix -82.411629 28.054553
 */
//double mylatitude  = inputCoord.getLatitude()  ;
//double mylongitude = inputCoord.getLongitude() ;
//Log.e(TAG, "gps  : mylongitude,mylatitude: (d)  " + mylongitude +","+ mylatitude);  

try
 {
   InputStream no, so, ea, we;
   no = this.getResources().openRawResource(R.raw.knorth);
   so = this.getResources().openRawResource(R.raw.ksouth);
   ea = this.getResources().openRawResource(R.raw.keast);
   we = this.getResources().openRawResource(R.raw.kwest);
			
   byte [] buffer_no = new byte[no.available()];
   while (no.read(buffer_no) != -1);
     String json_no = new String(buffer_no);

   byte [] buffer_so = new byte[so.available()];
   while (so.read(buffer_so) != -1);
     String json_so = new String(buffer_so);

   byte [] buffer_ea = new byte[ea.available()];
   while (ea.read(buffer_ea) != -1);
     String json_ea = new String(buffer_ea);
		  
   byte [] buffer_we = new byte[we.available()];
   while (we.read(buffer_we) != -1);
     String json_we = new String(buffer_we);
			
   JSONObject obj_no = new JSONObject(json_no);
   JSONObject obj_so = new JSONObject(json_so);
   JSONObject obj_ea = new JSONObject(json_ea);
   JSONObject obj_we = new JSONObject(json_we);

   JSONArray items_no;
   JSONArray items_so;
   JSONArray items_ea;
   JSONArray items_we;
			
   items_no = obj_no.getJSONArray("knorth");
   items_so = obj_so.getJSONArray("ksouth"); 
   items_ea = obj_ea.getJSONArray("keast"); 
   items_we = obj_we.getJSONArray("kwest");

   ArrayList<HashMap<String, Object>> mylist = new ArrayList<HashMap<String, Object>>();

// ** north ****************************************************************************************************************************************
   addPoints(items_no, inputCoord, mylist);
// ** south ****************************************************************************************************************************************
   addPoints(items_so, inputCoord, mylist);
// ** east *****************************************************************************************************************************************
   addPoints(items_ea, inputCoord, mylist);
// ** west *****************************************************************************************************************************************
   addPoints(items_we, inputCoord, mylist);
// *************************************************************************************************************************************************
			
  Collections.sort(mylist, new Comparator<HashMap<String, Object>>() {
   public int compare(HashMap<String, Object> o1, HashMap<String, Object> o2) { 
	     return ((Integer) o1.get("distance")).compareTo( (Integer) o2.get("distance"));
   }
  });

// ** copying the array to be able to get the clicked item ***************************************************************************************** 
 clicklist = mylist ;
 ListAdapter adapter = new SimpleAdapter(this, mylist , R.layout.distancelistview,
 new String[] { "advice",	  "description1",    "description2",    "extra1",    "distance",    "bearingDrawable"   , "imagetDrawable", "imageDrawable"   }, 
 new int[]    {  R.id.advice, R.id.description1, R.id.description2, R.id.extra1, R.id.distance, R.id.bearing ,        R.id.imaget,      R.id.image });
 setListAdapter(adapter);
 }
 catch (Exception je) {
	setStatus("Error : " + je.getMessage());
 }
}


private void addPoints(JSONArray items, Location inputCoord, ArrayList<HashMap<String, Object>> mylist) {
	int i;
	for (i=0;i<items.length();i++)
	{
		//Log.e(TAG, "west  :"+i + "/"+ items.length());  
		JSONObject qa4;
		try {
			qa4 = items.getJSONObject(i);
		HashMap<String,Object> element_we = new HashMap<String, Object>();
		element_we.put("id",  		String.valueOf(i));
		clkNAME         = qa4.getString("name");        element_we.put("name",  	  clkNAME);
		clkTITLE        = qa4.getString("title");       element_we.put("title", 	  clkTITLE);
		clkIMAGE        = qa4.getString("image");       element_we.put("image", 	  clkIMAGE);
		clkCATEGORY     = qa4.getString("category");    element_we.put("category",    clkCATEGORY);
		clkDESCRIPTION1 = qa4.getString("description1");element_we.put("description1",clkDESCRIPTION1);
		clkDESCRIPTION2 = qa4.getString("description2");element_we.put("description2",clkDESCRIPTION2);
		clkEXTRA1       = qa4.getString("extra1");      element_we.put("extra1",      clkEXTRA1);
		clkLATITUDE     = qa4.getString("latitude");    String slatitude  = clkLATITUDE;  element_we.put("latitude", 	clkLATITUDE ); 
		clkLONGITUDE    = qa4.getString("longitude");   String slongitude = clkLONGITUDE; element_we.put("longitude", 	clkLONGITUDE ); 	 			
 		clkADVICE       = qa4.getString("advice");
  /*
   * 
   * 
   * 
   */
        String imageastr = getAdviceDrawable(clkADVICE);
        String namea = imageastr.replace(".png", "").replace("res/drawable/", "")     ;
        // element_we.put("bearing", 	imageastr );
        imageaid = getImageId(this, namea );        
        element_we.put("advice", 	adviceDrawable );
     	// element_we.put("advice", 	    clkADVICE );
        // Log.e(TAG, "imageastr      :" + imageastr);
        // Log.e(TAG, "imageaid       :" + imageaid);
    	// Log.e(TAG, "adviceDrawable :" + adviceDrawable);
        
   /* 
   * 
   * 
   * 
   * 
   * 
   */ 

 		
 		clkLINK         = qa4.getString("link");        element_we.put("link", 	    	clkLINK );
        double xlatitude = convertStringToDouble(slatitude);
        double xlongitude = convertStringToDouble(slongitude);
        Location xlocation = new Location("xprovider");
        xlocation.setLatitude(xlatitude);
        xlocation.setLongitude(xlongitude);	            
        double distance = inputCoord.distanceTo(xlocation);
        double distancekm = distance/1000;
        double dd = Math.round(distancekm*100)/100; 
		//String ddist = convertDoubleToString(dd);
		int idist = convertObjectToInt(dd);
        element_we.put("distance", 	idist); 
        float bearing = inputCoord.bearingTo(xlocation);
        
        String imagestr = getBearingDrawable(bearing);
        String name1 = imagestr.replace(".png", "").replace("res/drawable/", "")     ;
        element_we.put("bearing", 	imagestr );
        imageid = getImageId(this, name1 );
        //String bearing_str = convertFloatToString(bearing);
        element_we.put("bearingDrawable", 	bearingDrawable );
        
        /*********************************************************************************************************/        
        String image = clkIMAGE;
        String nsew_image = clkCATEGORY + "_" + image	  ; // west_eiffel.jpg 
		String name = nsew_image.replace(".jpg", "")      ; // west_eiffel
		String namet = nsew_image.replace(".jpg", "_t")      ; // west_eiffel_t
		//String imagestring = getImageDrawable(name)		  ; // res/drawable/west_eiffel.jpg
		//String imagetstring = getImagetDrawable(namet)		  ; // res/drawable/west_eiffel_t.png
		//String image1 = imagestring.replace(".jpg", "").replace("res/drawable/", "");//west_eiffel
		//String imaget = imagetstring.replace(".png", "").replace("res/drawable/", "");	//west_eiffel_t
		imageid = getImageId(this, name)				  ; // 2130837639 
		imagetid = getImageId(this, namet)				  ; // 2130837630 
		element_we.put("imageDrawable", 	imageid );
		element_we.put("imagetDrawable", 	imagetid );
		/*      
		Log.v(TAG, "***** name                    :" + name); 
        Log.v(TAG, "***** imagestring             :" + imagestring);
        Log.v(TAG, "***** imageid                 :" + imageid );
        Log.v(TAG, "***** imaget                  :" + imaget );
        Log.v(TAG, "***** imagetid                :" + imagetid );
        Log.v(TAG, "***** image1                  :" + image1 ); 
        Log.v(TAG, "***** res/drawable/{name}.jpg :" + "res/drawable/"+name+".jpg");
        */
		/*********************************************************************************************************/
        
        mylist.add(element_we);
		} catch (JSONException e) {
			setStatus(e.toString());
		}	 			
	} 
}

private String getAdviceDrawable(String advice) {
	/*Log.e(TAG, "advice string in stars:" + advice);*/
    if        (advice.contentEquals("*"))     { iimage = 1 ;/*Log.e(TAG, "advice 1:" + advice)*/;
    } else if (advice.contentEquals("**"))    { iimage = 2 ;/*Log.e(TAG, "advice 2:" + advice)*/;
    } else if (advice.contentEquals("***"))   { iimage = 3 ;/*Log.e(TAG, "advice 3:" + advice)*/;
    } else if (advice.contentEquals("****"))  { iimage = 4 ;/*Log.e(TAG, "advice 4:" + advice)*/;
    } else if (advice.contentEquals("*****")) { iimage = 5 ;/*Log.e(TAG, "advice 5:" + advice)*/;
    } else if (advice.contentEquals(""))      { iimage = 0 ;/*Log.e(TAG, "advice 0:" + advice)*/;
    }
    adviceDrawable = R.drawable.advice_0;
	switch (iimage) {
	   case 0: adviceDrawable = R.drawable.advice_0		;	/*Log.e(TAG, "advice_0 :" + adviceDrawable)*/; break;
	   case 1: adviceDrawable = R.drawable.advice_1 	;	/*Log.e(TAG, "advice_1 :" + adviceDrawable)*/; break;
	   case 2: adviceDrawable = R.drawable.advice_2		;	/*Log.e(TAG, "advice_2 :" + adviceDrawable)*/; break;
	   case 3: adviceDrawable = R.drawable.advice_3		;	/*Log.e(TAG, "advice_3 :" + adviceDrawable)*/; break;
	   case 4: adviceDrawable = R.drawable.advice_4		;	/*Log.e(TAG, "advice_4 :" + adviceDrawable)*/; break;
	   case 5: adviceDrawable = R.drawable.advice_5		;	/*Log.e(TAG, "advice_5 :" + adviceDrawable)*/; break;
	  default: adviceDrawable = R.drawable.advice_0     ;	/*Log.e(TAG, "advice_0 :" + adviceDrawable)*/; break;
	} 
  	String imagestr = getImageName(this, adviceDrawable );
  	/*Log.e(TAG, "returning imagestr :" + imagestr);*/
  	return imagestr;
}
/*
private String getImageDrawable(String imagename) { 
	   int iimage = NN;
	   //Log.e(TAG, "***** imagename               :" + imagename);
       if        (imagename == "south_naturalhistory.jpg")   { iimage = NN;
       } else if (imagename == "east_zoo.jpg")   			 { iimage = NE;
       } else if (imagename == "east_perelachaise.jpg")   	 { iimage = EE; 
       }
    imageDrawable = R.drawable.south_naturalhistory;
  	switch (iimage) {
   	  case NN: imageDrawable = R.drawable.south_naturalhistory	; break;
   	  case NE: imageDrawable = R.drawable.east_zoo 				; break;
   	  case EE: imageDrawable = R.drawable.east_perelachaise		; break;
	  default: imageDrawable = R.drawable.south_naturalhistory  ; break;
  	} 
  	String imagestr = getImageName(this, imageDrawable ); 
  	return imagestr;
}

private String getImagetDrawable(String imagetname) { 
	   int timage = NN;
	  // Log.e(TAG, "***** imagetname               :" + imagetname);
    if        (imagetname == "south_naturalhistory_t.png")   { timage = NN;
    } else if (imagetname == "east_zoo_t.png")   			 { timage = NE;
    } else if (imagetname == "east_perelachaise_t.png")   	 { timage = EE; 
    }
 imageDrawable = R.drawable.south_naturalhistory;
	switch (timage) {
	  case NN: imagetDrawable = R.drawable.south_naturalhistory_t	; break;
	  case NE: imagetDrawable = R.drawable.east_zoo_t 				; break;
	  case EE: imagetDrawable = R.drawable.east_perelachaise_t		; break;
	  default: imagetDrawable = R.drawable.south_naturalhistory_t  ; break;
	} 
	String imagetstr = getImageName(this, imagetDrawable ); 
	return imagetstr;
}
*/
private String getBearingDrawable(float bearing) {
    //float pbearing = Math.abs(bearing); 
	   int bimage = NN;
       if        (bearing > -22   && bearing <  22)   { bimage = NN;
       } else if (bearing >  22   && bearing <  68)   { bimage = NE;
       } else if (bearing >  68   && bearing < 113)   { bimage = EE;
       } else if (bearing > 113   && bearing < 158)   { bimage = SE;
       } else if (bearing > 158   && bearing <= 180)  { bimage = SS;
       } else if (bearing >= -180 && bearing <= -158) { bimage = SS;
       } else if (bearing > -158  && bearing < -113)  { bimage = SW;
       } else if (bearing > -113  && bearing < -68)   { bimage = WW;
       } else if (bearing > -68   && bearing < -22)   { bimage = NW;
       }
    bearingDrawable = R.drawable.bearing_north;
  	switch (bimage) {
   	  case NN: bearingDrawable = R.drawable.bearing_north     ; break;
   	  case NE: bearingDrawable = R.drawable.bearing_northeast ; break;
   	  case EE: bearingDrawable = R.drawable.bearing_east      ; break;
   	  case SE: bearingDrawable = R.drawable.bearing_southeast ; break;
   	  case SS: bearingDrawable = R.drawable.bearing_south     ; break;
   	  case SW: bearingDrawable = R.drawable.bearing_southwest ; break;
   	  case WW: bearingDrawable = R.drawable.bearing_west      ; break;
   	  case NW: bearingDrawable = R.drawable.bearing_northwest ; break;
	  default: bearingDrawable = R.drawable.bearing_north     ; break;
  	} 
  	String imagestr = getImageName(this, bearingDrawable ); 
  	return imagestr;
}

//String handling/conversion
 private int convertObjectToInt(Object aObject){ 
	  String str = aObject.toString();
	  // Log.v(TAG, "string of object is: " + str);
	  int i = (int)Float.parseFloat(str);
	  // Log.v(TAG, "integer of object is: " + i);
	  return i;  
}

private double convertStringToDouble(String aString) {
    double aDouble = Double.parseDouble(aString); 
    return aDouble;
}

public static int    getImageId(Context context, String imageName) {
        int return_val = context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
        return return_val;
} 
public static String getImageName(Context context, int id) {
        String return_str = context.getResources().getString(id); 
        return return_str;
} 

void setStatus(String x)
{
		Toast.makeText(this, x, Toast.LENGTH_LONG).show();
}

 
}