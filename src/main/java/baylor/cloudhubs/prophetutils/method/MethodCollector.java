package baylor.cloudhubs.prophetutils.method;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.*;

public class MethodCollector {

    private static final JsonArray msArray = new JsonArray();

    public static void dump(File result) {
        try (var fw = new FileWriter(result)) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setPrettyPrinting();
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("methods", msArray);
            fw.write(gsonBuilder.create().toJson(jsonObject));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void parseMethodsCsv(File csvFile) {
        try (var br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            String msName = csvFile.getName().substring(0, csvFile.getName().indexOf("_methods.csv"));
            JsonObject ms = new JsonObject();
            JsonArray methods = new JsonArray();
            ms.addProperty("name", msName);

            while ((line = br.readLine()) != null) {
                String[] cols = line.split(",");
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("name", cols[0]);
                jsonObject.addProperty("bytecodeHash", cols[1]);
                methods.add(jsonObject);
            }
            ms.add("methods", methods);
            msArray.add(ms);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
