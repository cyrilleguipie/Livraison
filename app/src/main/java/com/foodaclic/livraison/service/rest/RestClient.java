package com.foodaclic.livraison.service.rest;


import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by cyrilleguipie on 10/27/15.
 */
public class RestClient {

    private static RestService service = null;

    public RestClient()
    {
    }

    public static RestService getRestService()
    {
        if (service == null)
        {
            OkHttpClient client = new OkHttpClient.Builder().build();
            Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.177/localisation/")
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                    .build();

            service   = retrofit.create(RestService.class);

        }
        return service;
    }



    //public static Gson getGson() {
    //   // new GsonBuilder().registerTypeAdapterFactory(new ItemTypeAdapterFactory()).registerTypeAdapter(Date.class, jsonSerializer).registerTypeAdapter(Date.class, jsonDeserializer).create();
    //    return  new GsonBuilder().registerTypeAdapter(Date.class, ser).registerTypeAdapter(Date.class, deser).create();
    //}
    //
    //private static JsonDeserializer<Date> deser = new JsonDeserializer<Date>() {
    //    @Override
    //    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    //        return json == null ? null : new Date(json.getAsLong());
    //    }
    //};
    //
    //
    //
    //private static JsonSerializer<Date> ser = new JsonSerializer<Date>() {
    //    @Override
    //    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
    //        return src == null ? null : new JsonPrimitive(src.getTime());
    //    }
    //};




}
