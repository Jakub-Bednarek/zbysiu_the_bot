package bot.commands.object;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Joke {
    private static final String jokesURL = "https://official-joke-api.appspot.com/jokes/random";
    private String category;
    private String setup;
    private String punchline;

    public Joke(){
        try{
            URL url = new URL(jokesURL);
            URLConnection connection = url.openConnection();
            String JSONString = getJSONString(connection);

            writeJokeFields(JSONString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCategory() {
        return this.category;
    }

    public String getSetup(){
        return this.setup;
    }

    public String getPunchline(){
        return this.punchline;
    }

    private String getJSONString(URLConnection connection){
        String jsonString = "";

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
            String line;

            while((line = reader.readLine()) != null){
                jsonString += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

    private void writeJokeFields(String JSONString){
        JSONObject jObject = new JSONObject(JSONString);

        this.category = jObject.get("type").toString();
        this.setup = jObject.get("setup").toString();
        this.punchline = jObject.get("punchline").toString();
    }
}
