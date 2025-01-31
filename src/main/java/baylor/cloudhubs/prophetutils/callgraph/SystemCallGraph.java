package baylor.cloudhubs.prophetutils.callgraph;

import baylor.cloudhubs.prophetutils.visualizer.Link;
import baylor.cloudhubs.prophetutils.visualizer.Request;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor()
@Getter
public class SystemCallGraph {
    private final Collection<Method> methods = new ArrayList<>();
    private final Collection<Call> calls = new ArrayList<>();

    public void addCall(Call call) {
        this.calls.add(call);
    }

    public void addCalls(Collection<Call> calls) {
        this.calls.addAll(calls);
    }

    public void addMethods(Collection<Method> methods) {
        this.methods.addAll(methods);
    }

    public void addInterserviceLinks(Collection<Link> links) {
        for (Link link : links) {
            for (Request req : link.getRequests()) {
                var newInterserviceCall = new Call(
                        req.getRequestSignature(),
                        req.getEndpointFunction(),
                        true,
                        req.getType()
                );
                this.addCall(newInterserviceCall);
            }
        }
    }

    public void dump(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(filePath), this);
            System.out.println("SystemCallGraph dumped to " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing SystemCallGraph to file: " + e.getMessage());
        }
    }
}
