package baylor.cloudhubs.prophetutils.callgraph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CallGraphParser {

    public static Map<Long, Method> parseMethods(Path filePath) throws IOException {
        Map<Long, Method> methods = new HashMap<>();
        if (Files.exists(filePath)) {
            System.out.println("Parsing: " + filePath);
            List<String> lines = Files.readAllLines(filePath);
            methods.putAll(
                    lines.stream()
                            .map(Method::parseLine)
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
            );
        } else {
            System.out.println("File not found: " + filePath);
        }

        return methods;
    }

    public static Map<Long, Invoke> parseInvokes(Path filePath) throws IOException {
        Map<Long, Invoke> invokes = new HashMap<>();
        if (Files.exists(filePath)) {
            System.out.println("Parsing: " + filePath);
            List<String> lines = Files.readAllLines(filePath);
            invokes.putAll(
                    lines.stream()
                            .map(Invoke::parseLine)
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
            );
        } else {
            System.out.println("File not found: " + filePath);
        }
        return invokes;
    }
}