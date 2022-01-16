package bot.music;

import bot.exceptions.NoVideoFoundException;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import javax.annotation.Nullable;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class YouTubeDataFetcher {
    private static final String APP_NAME = "ZbysiuTheBot";
    private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String WATCHURL = "https://www.youtube.com/watch?v=";
    private static YouTube YOUTUBE;

    static{
        try{
            YOUTUBE = getService();
        } catch (GeneralSecurityException e){
            System.out.println("GeneralSecurityException while creating YouTube service");
        } catch(IOException e){
            System.out.println("Failed to open httpTransport while creating YouTube service");
        }
    }

    public static String getYouTubeVideoLink(String videoName) throws IOException, NoVideoFoundException {
        String videoId = requestVideoId(videoName);

        return WATCHURL + videoId;
    }

    @Nullable
    private static String requestVideoId(String videoName) throws IOException, NoVideoFoundException {
        YouTube.Search.List request = YOUTUBE.search().list("id");

        SearchListResponse response = request.setKey("AIzaSyAZ43dsOKCmU0Nxl91gYbjqln05xNyxJks")
                .setMaxResults(1L)
                .setQ(videoName)
                .execute();

        List<SearchResult> result = response.getItems();

        if(result.size() == 0){
            throw new NoVideoFoundException();
        }

        return result.get(0).getId().getVideoId();
    }

    private static YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName(APP_NAME)
                .build();
    }
}
