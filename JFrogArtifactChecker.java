import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Arrays;
import java.util.List;

public class JFrogArtifactChecker {

    private static final String JFROG_BASE_URL = "https://your_jfrog_instance.jfrog.io/artifactory";
    private static final String USERNAME = "your_username";
    private static final String TOKEN = "your_token";

    // List of artifacts to check
    private static final List<String> artifacts = Arrays.asList(
        "libs-release-local/com/example/projectA/1.0.0/projectA-1.0.0.jar",
        "libs-release-local/com/example/projectB/2.1.0/projectB-2.1.0.war",
        "generic-local/assets/images/logo.png"
        // Add all 200 artifacts here
    );

    public static void main(String[] args) {
        for (String artifact : artifacts) {
            checkArtifactExistence(artifact);
        }
    }

    private static void checkArtifactExistence(String artifactPath) {
        String url = JFROG_BASE_URL + "/" + artifactPath;
        
        try {
            HttpURLConnection connection = createConnection(url);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Available: " + artifactPath);
            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                System.out.println("Not available: " + artifactPath);
            } else {
                System.out.println("Error checking " + artifactPath + ": HTTP " + responseCode);
            }

            connection.disconnect();

        } catch (IOException e) {
            System.out.println("Failed to check " + artifactPath + ": " + e.getMessage());
        }
    }

    // Creates a connection with pre-set authentication headers
    private static HttpURLConnection createConnection(String url) throws IOException {
        URL artifactUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) artifactUrl.openConnection();
        connection.setRequestMethod("HEAD");

        // Set up authentication once
        String auth = USERNAME + ":" + TOKEN;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        connection.setRequestProperty("Authorization", "Basic " + encodedAuth);

        return connection;
    }
}
