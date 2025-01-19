package baylor.cloudhubs.prophetutils.callgraph;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Call {
    private final String source;
    private final String target;
    private final Boolean isConnectedViaEndpoint;
}
