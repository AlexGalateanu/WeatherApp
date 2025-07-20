import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;
import org.json.JSONArray;

public class WeatherApp {
    public static void main(String[] args) {
        try {
            Scanner input = new Scanner(System.in);
            System.out.print("Introdu numele orașului: ");
            String city = input.nextLine();

            String apiKey = "";
            String urlString = String.format(
                "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric&lang=ro",
                city, apiKey);

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                System.out.println("Eroare: " + responseCode + " – orașul nu a fost găsit.");
                return;
            }

            Scanner sc = new Scanner(url.openStream());
            StringBuilder inline = new StringBuilder();
            while (sc.hasNext()) {
                inline.append(sc.nextLine());
            }
            sc.close();

            JSONObject obj = new JSONObject(inline.toString());
            JSONObject main = obj.getJSONObject("main");
            JSONArray weatherArray = obj.getJSONArray("weather");
            JSONObject weather = weatherArray.getJSONObject(0);
            JSONObject wind = obj.getJSONObject("wind");

            System.out.println("Vreme în " + obj.getString("name") + ":");
            System.out.println("  Temperatură: " + main.getDouble("temp") + "°C");
            System.out.println("  Descriere: " + weather.getString("description"));
            System.out.println("  Umiditate: " + main.getInt("humidity") + "%");
            System.out.println("  Vânt: " + wind.getDouble("speed") + " m/s");

        } catch (Exception e) {
            System.out.println("Eroare: " + e.getMessage());
        }
    }
}
