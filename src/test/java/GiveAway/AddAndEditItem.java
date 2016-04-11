package GiveAway;

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
 * Created by paul on 11.04.16.
 */
public class AddAndEditItem {

    @Test

    public void addAndEditItem() throws JSONException, InterruptedException {
        TestClass randomItemName = new TestClass();

        final String prod = "http://youmemeyou-api-prod.php-cd.attractgroup.com";

        final String APIUrlForRegistration = "/api/v1/register";
        final String APIUrlForThing = "/api/v1/thing?access_token=";
        final String APIUrlForThingEdit = "/thing/thing_id/";

        final Map<String, Object> paramForItem = new HashMap<>();
        final Map<String, Object> paramForRegistration = new HashMap<>();

        final String pathToImage = "/home/paul/testScreenShot.jpg";

        String user_password = "attract";
        String user_name = "Billy Talent";
        String access_token = "ZfolMie5zi1y8UYfHTmVziRSzgNQTgeE0VQqP4Ib+Pg=";

        paramForRegistration.put("user_password", user_password);
        paramForRegistration.put("user_name", user_name);
        paramForRegistration.put("access_token", access_token);

        String tokens = "ZfolMie5zi1y8UYfHTmVziRSzgNQTgeE0VQqP4Ib+Pg=";

        String id = "56f01ac6cb219685122cee4e";
        String description = "WOW SUCH DELICIOUS!";
        boolean notMine = false;

        JSONArray imageArray = new JSONArray();
        imageArray.put(pathToImage);

        JSONArray geo = new JSONArray();
        geo.put("46.5042614");
        geo.put("30.7252648");

        paramForItem.put("thing_category", id);
        paramForItem.put("thing_description", description);
        paramForItem.put("thing_not_mine", notMine);
        //paramForItem.put("thing_address", adress);
        //paramForItem.put("thing_geo", geo);

        for (int j = 0; j < 5; j++) {

            paramForRegistration.put("user_email", String.format("embdglo%d@gmail.com", j));

            synchronized (Thread.currentThread()) {
                try {
                    post(prod + APIUrlForRegistration, toJSON(paramForRegistration));
                    System.out.println(paramForRegistration);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < 5; i++) {

                Thread.sleep(500);
                JSONObject adress = new JSONObject();
                adress.put("house", randomItemName.getTypeOfField("house"));
                adress.put("street", "street");
                adress.put("city", randomItemName.getTypeOfField("city"));

                paramForItem.put("thing_address", adress);

                paramForItem.put("thing_name", randomItemName.getTypeOfField("name"));

                Map<String, Object> data = new HashMap<>();
                data.put("data", toJSON(paramForItem));

                System.out.println("fsfr");

                String kakoitoobject = postMultipart(prod + APIUrlForThing + tokens, data, pathToImage);

                //TODO: ВОТ ТУТ ВСЕ ПИСАТЬ ДАЛЬШЕ

                JSONObject kakhochu = new JSONObject(kakoitoobject);
                String idThing = kakhochu.optJSONObject("data").optString("_id");



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

    public String post(String url, String json) throws IOException {

        MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        okhttp3.Response response = client.newCall(request).execute();

        String responseBody = response.body().string();
        System.out.println(responseBody);

        return responseBody;
    }

    public String postMultipart(String url, Map<String, Object> param, String file) {

        String boundary = String.format("---%d", System.currentTimeMillis());
        String dashes = "--";
        String separator = "\r\n";
        String content = "Content-Disposition: form-data;";

        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).readTimeout(15, TimeUnit.SECONDS).build();
        MediaType mediaType = MediaType.parse("multipart/form-data; boundary=" + boundary);

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
        String responseBody = null;
        try {
            response = client.newCall(request).execute();

            responseBody = response.body().string();
            System.out.println(responseBody);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseBody;
    }
}

