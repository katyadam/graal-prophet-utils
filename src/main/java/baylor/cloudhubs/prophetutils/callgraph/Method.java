package baylor.cloudhubs.prophetutils.callgraph;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Id,Name,Type,Parameters,Return,Display,Flags,IsEntryPoint

@AllArgsConstructor
@Getter
public class Method {
    private final String name;
    private final String type;
    private final List<String> parameters;
    private final String returnType;
    private final String display;
    private final String flags;
    private final boolean isEntryPoint;

    public static Map.Entry<Long, Method> parseLine(String line) {
        String[] items = line.split(",");
        Long id = Long.parseLong(items[0]);
        List<String> params = Arrays.stream(items[3].split(" ")).collect(Collectors.toList());
        Method method = new Method(
                items[1],  // name
                items[2],  // type
                params,    // parameters
                items[4],  // returnType
                items[5],  // display
                items[6],  // flags
                Boolean.parseBoolean(items[7]) // isEntryPoint
        );

        return Map.entry(id, method);
    }

}
