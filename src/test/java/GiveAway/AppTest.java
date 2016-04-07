package GiveAway;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest

{
    @Test
    public void getRequestFindCapital() throws JSONException {

        //Initializing Rest API's URL
        final String localka = "http://192.168.0.102:3037";
        final String prod = "http://youmemeyou-api-prod.php-cd.attractgroup.com";
        final String APIUrl = prod + "/api/v1/token"; //Initializing payload or API body
//         String APIBody = "{\"grant_type\":\"client_credentials\",\"client_id\":\"vaspojhqwep9r58ghq3urvhpjpq3894ufg90843hrvpq3rihnvw\",\"client_secret\":\"client_secret\"}"; //e.g.- "{\"key1\":\"value1\",\"key2\":\"value2\"}" // Building request using requestSpecBuilder
//
//         RequestSpecBuilder builder = new RequestSpecBuilder();
//         builder.setBody(APIBody);
//         //Setting content type as application/json or application/xml
//         builder.setContentType("application/json; charset=UTF-8");
//         RequestSpecification requestSpec = builder.build();
//         //Making post request with authentication, leave blank in case there are no credentials
//         Response response = given().spec(requestSpec).when().post(APIUrl);
//         JSONObject JSONResponseBody = new JSONObject(response.body().asString()); //Fetching the desired value of a parameter
//
//         String result = JSONResponseBody.getString("status");
//         //Asserting that result of Norway is Oslo
//         Assert.assertEquals(result, "200");

        final Map<String, String> param = new HashMap<>();

        param.put("grant_type", "client_credentials");
        param.put("client_id", "vaspojhqwep9r58ghq3urvhpjpq3894ufg90843hrvpq3rihnvw");
        param.put("client_secret", "qwvpe2gu4p9t5bh8wiuerhgvqwljwthibp24835hgbqudhngvlw");

//        for(int i=0;i<100;i++)
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        post(APIUrl,toJSON(param));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
        try {
            post(APIUrl, toJSON(param));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toJSON(Map<String, String> map) {
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
}
