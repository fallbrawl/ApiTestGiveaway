package GiveAway;

import com.sun.security.ntlm.Client;
import okhttp3.*;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple App.
 */
public class TestAddItem

{
    @Test
    public void addItem() throws JSONException {

        TestClass randomItemName = new TestClass();

        final String localka = "http://192.168.0.102:3037";
        final String prod = "http://youmemeyou-api-prod.php-cd.attractgroup.com";

        final String APIUrlForLogin = prod + "/api/v1/login";

        String APIUrlForThing = prod + "/api/v1/thing?access_token="; //подставить токен

        final Map<String, Object> paramForItem = new HashMap<>();
        final Map<String, Object> paramForLogin = new HashMap<>();

        final String pathToImage = "/home/paul/testScreenShot.jpg";

        String[] logins = new String[3];

        logins[0] = "emailglol0@gmail.com";
        logins[1] = "emailglol1@gmail.com"; //PROD
        logins[2] = "emailglol2@gmail.com";

        //     logins[0] = "emaglol0@gmail.com";
        //     logins[1] = "emaglol1@gmail.com"; //LOCALKA
        //     logins[2] = "emaglol2@gmail.com";


        final String[] tokens = new String[3];

        tokens[0] = "jXvd7jrELZyo7JwY7EaRqjj9vLZzGF4Y+lPEODGNkgw=";
        tokens[1] = "s7QPDYGA6lgW10/Vg/2aKlIgyadV7PyzZn9rbpIpsbY=";
        tokens[2] = "BY+FX5jXvVCZPdH+5enKw2IR3up3LUQ3HfIaA0dP+eY=";

        String user_password = "attract";

        paramForLogin.put("user_password", user_password);


        //String name = "Test thing";
        String id = "56f01ac6cb219685122cee4e";
        String description = "WOW SUCH DELICIOUS!";
        boolean notMine = false;

        JSONArray imageArray = new JSONArray();
        imageArray.put(pathToImage);

//        JSONObject adress = new JSONObject();
//        adress.put("house", "34");
//        adress.put("street", "Chornomors'koho Kozatstva St");
//        adress.put("city", "Odessa");

        JSONArray geo = new JSONArray();
        geo.put("46.5042614");
        geo.put("30.7252648");


        paramForItem.put("thing_category", id);
        paramForItem.put("thing_description", description);
        paramForItem.put("thing_not_mine", notMine);
        //paramForItem.put("thing_address", adress);
        //paramForItem.put("thing_geo", geo);

        for (int j = 0; j < 1; j++) {

            paramForLogin.put("user_email", logins[j]);
            paramForLogin.put("access_token", tokens[j]);

            try {
                post(APIUrlForLogin, toJSON(paramForLogin));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            for (int i = 0; i < 2500; i++) {

                JSONObject adress = new JSONObject();
                adress.put("house", randomItemName.getTypeOfField("house"));
                adress.put("street", "street");
                adress.put("city", randomItemName.getTypeOfField("city"));

                paramForItem.put("thing_address", adress);

                paramForItem.put("thing_name", randomItemName.getTypeOfField("name"));

                Map<String, Object> data = new HashMap<>();
                data.put("data", toJSON(paramForItem));

                System.out.println("fsfr");

                postMultipart(APIUrlForThing + tokens[j], data, pathToImage);

                try {
                    Thread.sleep(10);
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

    public void postMultipart(String url, Map<String, Object> param, String file) {

        String boundary = String.format("---%d", System.currentTimeMillis());
        String dashes = "--";
        String separator = "\r\n";
        String content = "Content-Disposition: form-data;";

        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).readTimeout(15, TimeUnit.SECONDS).build();
        MediaType mediaType = MediaType.parse("multipart/form-data; boundary=" + boundary);

//        StringBuilder builder = new StringBuilder();
//
//        builder.append(dashes);
//        builder.append(boundary);
//        builder.append(separator);
//        builder.append(content);
//        builder.append("name=data");
//        builder.append(separator);
//        builder.append(param.get("data"));
//        builder.append(separator);
//        builder.append(dashes);
//        builder.append(boundary);
//
//        builder.append(separator);
//        builder.append(separator);
//        builder.append(content);
//        builder.append("name=thing_image");
//        builder.append(separator);
//        builder.append("filename=" + file);
//        builder.append(separator);
//        builder.append(dashes);
//        builder.append(boundary);
//        builder.append(dashes);
//
//


//        RequestBody body = RequestBody.create(mediaType, builder.toString());
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("data", String.valueOf(param.get("data")))
                .addFormDataPart("thing_image", "testScreenShot.jpg", RequestBody.create(MediaType.parse("image/jpeg"), new File(file))).build();


        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "multipart/form-data; boundary=" + boundary)
                .addHeader("cache-control", "no-cache")
                .build();
        okhttp3.Response response = null;
        try {
            response = client.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void registrationTest() {

        final String APIUrl = "http://192.168.0.102:3037/api/v1/register";
        final Map<String, Object> param = new HashMap<>();

        String user_password = "attract";
        String user_name = "Billy Talent";
        String access_token = "edA5la6qmRyRSi7kGwk4EFibKHy6ahms9cUVfhkcGHc=";

        param.put("user_password", user_password);
        param.put("user_name", user_name);
        param.put("access_token", access_token);

        for (int i = 0; i < 3; i++) {

            param.put("user_email", String.format("emaglol%d@gmail.com", i));

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