package baylor.cloudhubs.prophetutils.callgraph;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

// Id,MethodId,BytecodeIndexes,TargetId,IsDirect

@AllArgsConstructor
@Getter
public class Invoke {
    private final Long methodId;
    private final String bytecodeIndexes;
    private final Long targetId;
    private final boolean isDirect;

    public static Map.Entry<Long, Invoke> parseLine(String line) {
        String[] items = line.split(",");
        Long id = Long.parseLong(items[0]);
        Invoke invoke = new Invoke(
                Long.parseLong(items[1]),
                items[2],
                Long.parseLong(items[3]),
                Boolean.parseBoolean(items[4])
        );

        return Map.entry(id, invoke);
    }
}
