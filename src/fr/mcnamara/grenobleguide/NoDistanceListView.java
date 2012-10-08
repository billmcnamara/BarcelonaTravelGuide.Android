package fr.mcnamara.grenobleguide;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap; 

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent; 
import android.os.Bundle;  
import android.util.Log;
import android.view.View; 
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
 
public class NoDistanceListView extends ListActivity {
    //private static final String TAG = "NoDistanceListView:";
    private int NSEW = 0;
	final int NORTH = 0;
	final int SOUTH = 1;
	final int EAST = 2;
	final int WEST = 3; 
	
    String clkNAME, clkTITLE, clkCATEGORY, clkIMAGE, clkDESCRIPTION1, clkDESCRIPTION2, clkEXTRA1, clkLATITUDE, clkLONGITUDE, clkADVICE, clkLINK;
    private int imgSizeId, imageid, imageDrawable, imagetDrawable, imagetid, adviceDrawable, iimage, imageaid;
    ArrayList<HashMap<String, Object>> clicklist = new ArrayList<HashMap<String, Object>>();
    
	public void onCreate(Bundle InstanceState) {
		super.onCreate(InstanceState);  
		 Bundle bundle = getIntent().getExtras(); 
	     int nsew = bundle.getInt("nsew");  
	     NSEW = nsew;
	     //Log.e(TAG, "building  :"+ NSEW); 
		 buildObjectFromFile(NSEW);
	}

 	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		HashMap<String, Object> clickeditem = clicklist.get(position) ;
        Intent i = new Intent(this, ClickedItem.class); 
        Bundle j = new Bundle();
                j.putInt("imagenumber", position); 
	            j.putInt("nsew", NSEW); 
	            clkNAME        = (String) clickeditem.get("name"); 
	        	clkTITLE       = (String) clickeditem.get("title"); 
	        	clkCATEGORY    = (String) clickeditem.get("category");
	          	clkIMAGE       = (String) clickeditem.get("image"); 
	        	clkDESCRIPTION1= (String) clickeditem.get("description1");
	         	clkDESCRIPTION2= (String) clickeditem.get("description2");
	         	clkEXTRA1      = (String) clickeditem.get("extra1");
	         	clkLATITUDE    = (String) clickeditem.get("latitude");
	          	clkLONGITUDE   = (String) clickeditem.get("longitude");
	     
	      		String imageastr = getAdviceDrawable(clkADVICE);
	  			clkADVICE      = imageastr.replace(".png", "").replace("res/drawable/", "")     ;
	  	
	  			//clkADVICE      = (String) clickeditem.get("advice");
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
	        	String image = clkIMAGE;
	            String nsew_image = clkCATEGORY + "_" + image	  ; // west_eiffel.jpg 
	    		String name = nsew_image.replace(".jpg", "")      ; // west_eiffel
	    		String namet = nsew_image.replace(".jpg", "_t")      ; // west_eiffel_t
	    		j.putString("imageDrawable", 	name );
	    		j.putString("imagetDrawable", 	namet );
	        	i.putExtras(j);       
	    i.putExtras(j);       
        startActivity( i ); 
        }
 
	void buildObjectFromFile(int nsew)
	{
		try
		{
			InputStream is;
			switch (nsew) {
			case NORTH:	is = this.getResources().openRawResource(R.raw.knorth);
			    break;
			case  SOUTH:is = this.getResources().openRawResource(R.raw.ksouth);
			    break;
			case  EAST:	is = this.getResources().openRawResource(R.raw.keast);
			    break;
			case  WEST:	is = this.getResources().openRawResource(R.raw.kwest);
			    break; 
			default:	is = this.getResources().openRawResource(R.raw.kwest);
			    break;
			}
				 
			byte [] buffer = new byte[is.available()];
			while (is.read(buffer) != -1);
				String json = new String(buffer);
			
				ArrayList<HashMap<String, Object>> mylist = new ArrayList<HashMap<String, Object>>();
				
			JSONObject obj = new JSONObject(json);
			//obj = new JSONObject(json); 
 
			JSONArray items;
			switch (nsew) {
			case NORTH:	items = obj.getJSONArray("knorth");
			    break;
			case  SOUTH:items = obj.getJSONArray("ksouth");
			    break;
			case  EAST:	items = obj.getJSONArray("keast");
			    break;
			case  WEST:	items = obj.getJSONArray("kwest");
			    break; 
			default:	items = obj.getJSONArray("kwest");
			    break;
			}

			int i;
			for (i=0;i<items.length();i++)
			{
				//Log.e(TAG, i+"/"+ items.length()); 
				JSONObject qa = items.getJSONObject(i);
	 			HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("id",  			String.valueOf(i));
	            map.put("name",  		qa.getString("name"));
	            map.put("title", 		qa.getString("title"));
	            map.put("image", 		qa.getString("image"));
	            map.put("category", 	qa.getString("category"));
	            map.put("description1", qa.getString("description1"));
	            map.put("description2", qa.getString("description2"));
	            map.put("extra1", 		qa.getString("extra1"));
	            //map.put("advice", 		qa.getString("advice"));
	            
	            map.put("longitude", 	qa.getString("longitude"));
	            map.put("latitude", 	qa.getString("latitude"));
	            map.put("link", 		qa.getString("link")); 
	            
	            String nsew_image = qa.getString("category") + "_" + qa.getString("image")	  ; // west_eiffel.jpg 
	    		String name = nsew_image.replace(".jpg", "")      ; // west_eiffel
	    		String namet = nsew_image.replace(".jpg", "_t")   ; // west_eiffel_t
	    		imageid = getImageId(this, name)				  ; // 2130837639 
	    		imagetid = getImageId(this, namet)			      ; // 2130837630 
	    		map.put("imageDrawable", 	imageid );
	    		map.put("imagetDrawable", 	imagetid );	    		
	    		

	    		clkADVICE       = qa.getString("advice");
	            String imageastr = getAdviceDrawable(clkADVICE);
	            String namea = imageastr.replace(".png", "").replace("res/drawable/", "")     ;
	            // element_we.put("bearing", 	imageastr );
	            imageaid = getImageId(this, namea );        
	            map.put("advice", 	adviceDrawable );
	         	// element_we.put("advice", 	    clkADVICE );
	            //Log.e(TAG, "imageastr      :" + imageastr);
	            //Log.e(TAG, "imageaid       :" + imageaid);
	        	//Log.e(TAG, "adviceDrawable :" + adviceDrawable);
	            // Log.e(TAG, "imageastr      :" + imageastr);
	            // Log.e(TAG, "namea          :" + namea);
	            // Log.e(TAG, "imageaid       :" + imageaid);
	        	// Log.e(TAG, "adviceDrawable :" + adviceDrawable);

	    		
	            mylist.add(map);
			}

			clicklist = mylist ;
			ListAdapter adapter = new SimpleAdapter( this, mylist,  R.layout.nodistancelistview,
					new String[] {  "description1",    "description2",    "extra1",    "advice"  ,  "imagetDrawable",  "imageDrawable"  }, 
					new int[] {      R.id.description1, R.id.description2, R.id.extra1, R.id.advice, R.id.imaget,       R.id.image  }
			);
			setListAdapter(adapter);
 
		}
		catch (Exception je)
		{
			setStatus("Error w/file: " + je.getMessage());
		}
	}
	private String getAdviceDrawable(String advice) {
		 
		//Log.e(TAG, "advice string in stars:" + advice);
	    if        (advice.contentEquals("*"))     { iimage = 1 ;/*Log.e(TAG, "advice 1:" + advice)*/;
	    } else if (advice.contentEquals("**"))    { iimage = 2 ;/*Log.e(TAG, "advice 2:" + advice)*/;
	    } else if (advice.contentEquals("***"))   { iimage = 3 ;/*Log.e(TAG, "advice 3:" + advice)*/;
	    } else if (advice.contentEquals("****"))  { iimage = 4 ;/*Log.e(TAG, "advice 4:" + advice)*/;
	    } else if (advice.contentEquals("*****")) { iimage = 5 ;/*Log.e(TAG, "advice 5:" + advice)*/;
	    } else if (advice.contentEquals(""))      { iimage = 0 ;/*Log.e(TAG, "advice 0:" + advice)*/;
	    }
	    adviceDrawable = R.drawable.advice_0;
		switch (iimage) {
		   case 0: adviceDrawable = R.drawable.advice_0		;/*Log.e(TAG, "advice_0 :" + adviceDrawable)*/; break;
		   case 1: adviceDrawable = R.drawable.advice_1 	;/*Log.e(TAG, "advice_1 :" + adviceDrawable)*/; break;
		   case 2: adviceDrawable = R.drawable.advice_2		;/*Log.e(TAG, "advice_2 :" + adviceDrawable)*/; break;
		   case 3: adviceDrawable = R.drawable.advice_3		;/*Log.e(TAG, "advice_3 :" + adviceDrawable)*/; break;
		   case 4: adviceDrawable = R.drawable.advice_4		;/*Log.e(TAG, "advice_4 :" + adviceDrawable)*/; break;
		   case 5: adviceDrawable = R.drawable.advice_5		;/*Log.e(TAG, "advice_5 :" + adviceDrawable)*/; break;
		  default: adviceDrawable = R.drawable.advice_0     ;/*Log.e(TAG, "advice_0 :" + adviceDrawable)*/; break;
		} 
	  	String imagestr = getImageName(this, adviceDrawable );
	  	//Log.e(TAG, "returning imagestr :" + imagestr);
	  	return imagestr;
	}

public static String getImageName(Context context, int id) {
        String return_str = context.getResources().getString(id); 
        return return_str;
} 

public static int    getImageId(Context context, String imageName) {
        int return_val = context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
        return return_val;
} 
 
	void setStatus(String x)
	{
		Toast.makeText(this, x, Toast.LENGTH_SHORT).show();
	}

}