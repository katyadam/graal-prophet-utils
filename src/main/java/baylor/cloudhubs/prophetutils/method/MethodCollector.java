package baylor.cloudhubs.prophetutils.method;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.*;

public class MethodCollector {

    private static final JsonArray jsonArray = new JsonArray();

    public static void dump(File result) {
        try (var fw = new FileWriter(result)) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setPrettyPrinting();
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("methods", jsonArray);
            fw.write(gsonBuilder.create().toJson(jsonObject));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void parseMethodsCsv(File csvFile) {
        try (var br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] cols = line.split(",");
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("name", cols[0]);
                jsonObject.addProperty("bytecodeHash", cols[1]);
                jsonObject.add("parameters", parseToJsonArray(cols[2]));
                jsonObject.add("annotations", parseToJsonArray(cols[3]));

                jsonArray.add(jsonObject);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static JsonArray parseToJsonArray(String array) {
        String rawString = array.replace("[", "").replace("]", "");
        JsonArray jsonArray = new JsonArray();
        if (rawString.contains(",")) {
            for (String item : rawString.split(",")) {
                jsonArray.add(item.trim());
            }
        }
        return jsonArray;
    }
}
