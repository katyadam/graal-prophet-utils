package baylor.cloudhubs.prophetutils.callgraph;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class CallGraphParser {

    public static Map<Long, Method> parseMethods(Path filePath, String basePackage) throws IOException {
        Map<Long, Method> methods = new HashMap<>();

        if (Files.exists(filePath)) {
            System.out.println("Parsing: " + filePath);
            try (BufferedReader reader = Files.newBufferedReader(filePath)) {
                String line;
                boolean skipFirst = true;
                while ((line = reader.readLine()) != null) {
                    if (skipFirst) {
                        skipFirst = false;
                        continue;
                    }
                    Map.Entry<Long, Method> parsedMethod = Method.parseLine(line);
                    if (parsedMethod.getValue().getType().startsWith(basePackage)) {
                        methods.put(parsedMethod.getKey(), parsedMethod.getValue());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File not found: " + filePath);
        }

        return methods;
    }

    public static Map<Long, Invoke> parseInvokes(Path filePath, Map<Long, Method> parsedMethods) throws IOException {
        Map<Long, Invoke> invokes = new HashMap<>();

        if (Files.exists(filePath)) {
            System.out.println("Parsing: " + filePath);
            try (BufferedReader reader = Files.newBufferedReader(filePath)) {
                String line;
                boolean skipFirst = true;
                while ((line = reader.readLine()) != null) {
                    if (skipFirst) {
                        skipFirst = false;
                        continue;
                    }
                    Map.Entry<Long, Invoke> parsedInvoke = Invoke.parseLine(line);
                    // If invoke source method and its target are within the basePackage, as defined in parseMethods,
                    // then we can add this method into collected invokes.
                    // This ensures that call graph will consist only methods defined by user.
                    if (parsedMethods.containsKey(parsedInvoke.getValue().getMethodId())
                            && parsedMethods.containsKey(parsedInvoke.getValue().getTargetId())) {
                        invokes.put(parsedInvoke.getKey(), parsedInvoke.getValue());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            System.out.println("File not found: " + filePath);
        }

        return invokes;
    }
}