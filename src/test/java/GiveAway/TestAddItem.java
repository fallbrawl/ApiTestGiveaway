package GiveAway;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class TestAddItem

{
    @Test
    public void addItem() throws JSONException {

        final String APIUrlForLogin = "http://youmemeyou-api-prod.php-cd.attractgroup.com/api/v1/login";
        final String APIUrlForThing = "http://youmemeyou-api-prod.php-cd.attractgroup.com/api/v1/thing?access_token=ogQh2/dfngOlxFLsLBDpr6JA8Dm79x0yViFxuFiiOwo="; //подставить токен

        final Map<String, Object> paramForItem = new HashMap<>();
        final Map<String, Object> paramForLogin = new HashMap<>();

        final String pathToImage = "/home/paul/testScreenShot.jpg";

        String[] logins = new String[3];

        logins[0] = "emaillol977@gmail.com";
        logins[1] = "emaillol978@gmail.com";
        logins[2] = "emaillol979@gmail.com";

        String[] tokens = new String[3];

        tokens[0] = "ogQh2/dfngOlxFLsLBDpr6JA8Dm79x0yViFxuFiiOwo=";
        tokens[1] = "";
        tokens[2] = "";

        String user_password = "attract";

        paramForLogin.put("user_password", user_password);


        String name = "Test thing";
        String id = "56f01ac6cb219685122cee4e";
        String description = "WOW SUCH DELICIOUS!";
        boolean notMine = false;

        JSONArray imageArray = new JSONArray();
        imageArray.put(pathToImage);

        JSONObject adress = new JSONObject();
        adress.put("house", "34");
        adress.put("street", "Chornomors'koho Kozatstva St");
        adress.put("city", "Odessa");

        JSONArray geo = new JSONArray();
        geo.put("46.5042614");
        geo.put("30.7252648");

        paramForItem.put("thing_name", name);
        paramForItem.put("thing_category", id);
        paramForItem.put("thing_description", description);
        paramForItem.put("thing_not_mine", notMine);
        paramForItem.put("thing_images", imageArray);
        paramForItem.put("thing_address", adress);
        paramForItem.put("thing_geo", geo);

        for (int j = 0; j < 1; j++) {

            paramForLogin.put("user_email", logins[j]);
            paramForLogin.put("access_token", tokens[j]);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        post(APIUrlForLogin, toJSON(paramForLogin));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < 10; i++) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            post(APIUrlForThing, toJSON(paramForItem));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public String toJSON(Map<String, Object> map) {
        JSONObject ob = new JSONObject();
        for (String key : map.keySet())
            try {
                ob.put(key, map.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return ob.toString();
    }

    public void post(String url, String json) throws IOException {

        MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        okhttp3.Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }

    @Test
    public void registrationTest() {

        final String APIUrl = "http://youmemeyou-api-prod.php-cd.attractgroup.com/api/v1/register";
        final Map<String, Object> param = new HashMap<>();

        String user_password = "attract";
        String user_name = "Billy Talent";
        String access_token = "SYuIvp+gxO9BLSN4cXe7wewegBGTz55UGYUHhsPE0uY=";

        param.put("user_password", user_password);
        param.put("user_name", user_name);
        param.put("access_token", access_token);

        for (int i = 0; i < 1000; i++) {

            param.put("user_email", String.format("emaillol%d@gmail.com", i));

            synchronized (Thread.currentThread()) {
                try {
                    post1(APIUrl, toJSON1(param));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

//                try {
//                    Thread.sleep(5);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

        }
    }

    public String toJSON1(Map<String, Object> map) {
        JSONObject ob = new JSONObject();
        for (String key : map.keySet())
            try {
                ob.put(key, map.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return ob.toString();
    }

    public void post1(String url, String json) throws IOException {

        MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        okhttp3.Response response = client.newCall(request).execute();
        System.out.println(response.body().string());

    }
}