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
import java.util.Random;
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
        final String APIUrlForThing = "/api/v1/thing";
        final String APIUrlForThingEdit = "/api/v1/thing/";

        final Map<String, Object> paramForItem = new HashMap<>();
        final Map<String, Object> paramForItemEdit = new HashMap<>();
        final Map<String, Object> paramForRegistration = new HashMap<>();

        String user_password = "attract";
        String user_name = "Billy Talent";
        String access_token = "VkCBKuibck4hRkNMvBVChrALZQm/THOJY7B9X3E6JgY=";

        paramForRegistration.put("user_password", user_password);
        paramForRegistration.put("user_name", user_name);
        paramForRegistration.put("access_token", access_token);

        String tokens = "?access_token=" + "VkCBKuibck4hRkNMvBVChrALZQm/THOJY7B9X3E6JgY=";

        String id = "56f01ac6cb219685122cee4e";
        String description = "WOW SUCH DELICIOUS!";

        JSONArray imageArray = new JSONArray();
        imageArray.put(randomItemName.getTypeOfField("pathToImage"));

        JSONArray geo = new JSONArray();
        geo.put("46.5042614");
        geo.put("30.7252648");

        paramForItem.put("thing_category", id);
        paramForItem.put("thing_description", description);

        paramForItemEdit.put("thing_category", id);
        paramForItemEdit.put("thing_description", description);


        for (int j = 0; j < 5; j++) {

            paramForRegistration.put("user_email", String.format("emfgrelo%d@gmail.com", j));

            synchronized (Thread.currentThread()) {
                try {
                    post(prod + APIUrlForRegistration, toJSON(paramForRegistration));
                    System.out.println(paramForRegistration);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < 500; i++) {

                Thread.sleep(500);
                JSONObject adress = new JSONObject();

                adress.put("house", randomItemName.getTypeOfField("house"));
                adress.put("street", "st");
                adress.put("city", randomItemName.getTypeOfField("city"));

                paramForItem.put("thing_address", adress);

                Random randomGenerator = new Random();
                int isItMine = randomGenerator.nextInt(2);
                boolean notMine = isItMine == 1;

                paramForItem.put("thing_not_mine", notMine);
                paramForItem.put("thing_name", randomItemName.getTypeOfField("name"));

                Map<String, Object> data = new HashMap<>();
                data.put("data", toJSON(paramForItem));

                System.out.println("fsfr");

                String[] paths = {
                        "/home/paul/testScreenShot.jpg",
                        "/home/paul/risovach.ru.jpg",
                        "/home/paul/large_ccf86f2ba2.jpg"
                };

                String path = paths[new Random().nextInt(paths.length - 1)];
                System.out.println(paths);

                String kakoitoobject = postMultipart(prod + APIUrlForThing + tokens, data, path, true);

                JSONObject kakhochu = new JSONObject(kakoitoobject);

                if (kakhochu.optInt("status") != 200)
                    return;

                String idURLThing = kakhochu.optJSONObject("data").optString("_id");
                JSONArray arrayForImages = kakhochu.optJSONObject("data").getJSONArray("thing_images");
                System.out.println("KAKOITOOBJECT " + idURLThing);

                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                paramForItemEdit.put("thing_category", randomItemName.getTypeOfField("category"));
                paramForItemEdit.put("thing_not_mine", notMine);
                paramForItemEdit.put("thing_description", randomItemName.getTypeOfField("description"));
                paramForItemEdit.put("thing_name", randomItemName.getTypeOfField("name"));
                paramForItemEdit.put("thing_images", arrayForImages);

                System.out.println(arrayForImages.toString());

                paramForItemEdit.put("thing_address", adress);

                Map<String, Object> data1 = new HashMap<>();
                data1.put("data", toJSON(paramForItemEdit));

                postMultipart(prod + APIUrlForThingEdit + idURLThing + tokens, data1, path, false);
                System.out.println(prod + APIUrlForThingEdit + idURLThing + tokens);

//        "thing_images":[
//              "uploads/things/56/14.jpg"], //Массив с путями к старым изображениям вещи, для удаления какого-то изображения нужно удалить его из массива
//        "thing_address":
//        {                               //Адрес расположения вещи - По адресу определяются гео координаты, если не отправлены
//            "city" : "Odessa",
//                "street":"b.Arnautskaya",                     //Улица
//                "house":43                                   //Номер дома

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

    public String postMultipart(String url, Map<String, Object> param, String file, boolean post) {

        String boundary = String.format("---%d", System.currentTimeMillis());

        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).readTimeout(15, TimeUnit.SECONDS).build();
        MediaType mediaType = MediaType.parse("multipart/form-data; boundary=" + boundary);

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("data", String.valueOf(param.get("data")))
                .addFormDataPart("thing_image", "testScreenShot.jpg", RequestBody.create(MediaType.parse("image/jpeg"), new File(file))).build();

        Request.Builder builder = new Request.Builder()
                .url(url).addHeader("content-type", "multipart/form-data; boundary=" + boundary)
                .addHeader("cache-control", "no-cache");
        if (post)
            builder.post(body);
        else
            builder.put(body);
        Request request = builder.build();

        okhttp3.Response response = null;
        String responseBody = null;

        try {
            response = client.newCall(request).execute();

            responseBody = response.body().string();
            System.out.println(responseBody);

        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return responseBody;
    }
}


//{"status":200,"data":[{"_id":"56f01ac6cb219685122cee4d",
//        "cat_pin":"uploads/categories/56f01ac6cb219685122cee4d/",
//        "cat_name":{"en":"Accessories","ru":"Аксессуары"}},{"_id":"56f01ac6cb219685122cee4f",
//        "cat_pin":"uploads/categories/56f01ac6cb219685122cee4f/",
//        "cat_name":{"en":"Clothing & shoes kids","ru":"Одежда и обувь (детская)"}},{"_id":"56f01ac6cb219685122cee51",
//        "cat_pin":"uploads/categories/56f01ac6cb219685122cee51/",
//        "cat_name":{"en":"Clothing & shoes women","ru":"Одежда и обувь (женская)"}},{"_id":"56f01ac6cb219685122cee52",
//        "cat_pin":"uploads/categories/56f01ac6cb219685122cee52/",
//        "cat_name":{"en":"Electronics","ru":"Электроника"}},{"_id":"56f01ac6cb219685122cee53",
//        "cat_pin":"uploads/categories/56f01ac6cb219685122cee53/",
//        "cat_name":{"en":"Furniture","ru":"Мебель"}},{"_id":"56f01ac6cb219685122cee54",
//        "cat_pin":"uploads/categories/56f01ac6cb219685122cee54/",
//        "cat_name":{"en":"Kitchen & dining","ru":"Кухонные принадлежности"}},{"_id":"56f01ac6cb219685122cee55",
//        "cat_pin":"uploads/categories/56f01ac6cb219685122cee55/",
//        "cat_name":{"en":"Pet supplies","ru":"Для животных"}},{"_id":"56f01ac6cb219685122cee56",
//        "cat_pin":"uploads/categories/56f01ac6cb219685122cee56/",
//        "cat_name":{"en":"Sport","ru":"Спорт"}},{"_id":"56f01ac6cb219685122cee57",
//        "cat_pin":"uploads/categories/56f01ac6cb219685122cee57/",
//        "cat_name":{"en":"Toys","ru":"Игрушки"}},{"_id":"56f01ac6cb219685122cee58",
//        "cat_pin":"uploads/categories/56f01ac6cb219685122cee58/",
//        "cat_name":{"en":"Transport","ru":"Транспорт"}},{"_id":"56f01ac6cb219685122cee4e",
//        "cat_pin":"uploads/categories/56f01ac6cb219685122cee4e/",
//        "cat_name":{"en":"Appliances","ru":"Бытовая техника"}},{"_id":"56f01ac6cb219685122cee50",
//        "cat_pin":"uploads/categories/56f01ac6cb219685122cee50/",
//        "cat_name":{"en":"Clothing & shoes men","ru":"Одежда и обувь (мужская)"}}]}