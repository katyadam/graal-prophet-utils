package baylor.cloudhubs.prophetutils.callgraph;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class CallGraphParser {

    public static Map<Long, Method> parseMethods(Path filePath) throws IOException {
        Map<Long, Method> methods = new HashMap<>();

        if (Files.exists(filePath)) {
            System.out.println("Parsing: " + filePath);
            try (BufferedReader reader = Files.newBufferedReader(filePath)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Map.Entry<Long, Method> parsedMethod = Method.parseLine(line);
                    methods.put(parsedMethod.getKey(), parsedMethod.getValue());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File not found: " + filePath);
        }

        return methods;
    }

    public static Map<Long, Invoke> parseInvokes(Path filePath) throws IOException {
        Map<Long, Invoke> invokes = new HashMap<>();

        if (Files.exists(filePath)) {
            System.out.println("Parsing: " + filePath);
            try (BufferedReader reader = Files.newBufferedReader(filePath)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Map.Entry<Long, Invoke> parsedInvoke = Invoke.parseLine(line);
                    invokes.put(parsedInvoke.getKey(), parsedInvoke.getValue());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File not found: " + filePath);
        }

        return invokes;
    }
}