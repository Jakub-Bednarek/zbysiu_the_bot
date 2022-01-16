package bot.commands.object;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Meme {
    private static final String pageURL = "https://meme-api.herokuapp.com/gimme";
    private String title;
    private String imageURL;
    private String author;

    public Meme(){
        try{
            URL url = new URL(pageURL);
            URLConnection connection = url.openConnection();
            String JSONString = fetchURLData(connection);

            getMemeFields(JSONString);
        } catch(MalformedURLException e){
            System.out.println("Incorrect page URL");
        } catch(IOException e){
            System.out.println("Failed to create connection to page");
        }
    }

    public String getTitle(){
        return this.title;
    }

    public String getURL(){
        return this.imageURL;
    }

    public String getAuthor(){
        return this.author;
    }

    private String fetchURLData(URLConnection connection){
        String urlData = "";

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
            String line;

            while((line = reader.readLine()) != null){
                urlData += line;
            }
        } catch(IOException e){
            System.out.println("Failed to create data stream from connection");
        }

        return urlData;
    }

    private void getMemeFields(String JSONString){
        JSONObject jObject = new JSONObject(JSONString);

        title = jObject.get("title").toString();
        imageURL = jObject.get("url").toString();
        author = jObject.get("author").toString();
    }
}

