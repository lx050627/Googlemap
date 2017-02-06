import java.io.*;
import java.net.*;

import org.json.simple.*;
import java.util.regex.Matcher;  
import java.util.regex.Pattern;  

public class googlemap {

	public static void main(String[] args) {
		StringBuffer json=new StringBuffer();  
		String from="Sundsvall";
		String to="Stockholm";
        try {  
        	String url="https://maps.googleapis.com/maps/api/directions/json?origin="
        +from+"&destination="
       +to+"&key=AIzaSyArqcS_MwgL1kr97ZnMS2nI6Cyu3_Dpx0s";
            URL urlObject = new URL(url);  
            URLConnection uc = urlObject.openConnection();  
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));  
            String inputLine = null;  
            while ( (inputLine = in.readLine()) != null) {  
                json.append(inputLine);  
            }  
            in.close();  
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
         String jsonstring=json.toString();
         JSONObject obj = (JSONObject)JSONValue.parse(jsonstring);
         JSONArray routes=(JSONArray)obj.get("routes");
         JSONArray legs=(JSONArray)((JSONObject)routes.get(0)).get("legs");
         obj=(JSONObject)legs.get(0);
         JSONObject distance=(JSONObject)obj.get("distance");
         JSONObject duration=(JSONObject)obj.get("duration");
         String distance_text=(String)distance.get("text");
         String duration_text=(String)duration.get("text");
         System.out.println(from+"-->"+to);
         System.out.println("The total distance is "+distance_text);
         System.out.println("The estimated time is "+duration_text);
         
         JSONArray steps=(JSONArray)obj.get("steps");
         int n=steps.size();
         String ins=new String("");
         System.out.println("Driving Instructions:");
         for(int i=0;i<n;i++)
         {
         //ins=((JSONObject)steps.get(i)).get("html_instructions");
         ins=FilterHtml(((JSONObject)steps.get(i)).get("html_instructions"));
         System.out.println("["+(i+1)+"]"+ins);
         }
	}
	
	private static String FilterHtml(Object htmlStr)
	{
		String string=(String)htmlStr;
		final String regEx_b = "<b>|<\\/b>"; // 定义<b>..</b>的正则表达式 
		Pattern p_b = Pattern.compile(regEx_b, Pattern.CASE_INSENSITIVE);
		Matcher m_b = p_b.matcher(string); 
	    string=m_b.replaceAll(""); // 过滤b标签  
	    
	    final String regEx_div = "<div[^>]*?>[\\s\\S]*?<\\/div>";
	    Pattern p_div = Pattern.compile(regEx_div, Pattern.CASE_INSENSITIVE);
		Matcher m_div = p_div.matcher(string);
		string=m_div.replaceAll("");
		
		return string.trim();
	}

}
