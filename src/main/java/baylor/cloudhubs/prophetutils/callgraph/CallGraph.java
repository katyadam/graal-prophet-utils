package baylor.cloudhubs.prophetutils.callgraph;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static baylor.cloudhubs.prophetutils.callgraph.CallGraphParser.parseInvokes;
import static baylor.cloudhubs.prophetutils.callgraph.CallGraphParser.parseMethods;

@AllArgsConstructor
@Getter
public class CallGraph {

    private final String msName;
    private final Collection<Method> methods;
    private final Collection<Call> calls;

    public void addCall(Call call) {
        this.calls.add(call);
    }

    public static CallGraph create(Path dir, String msName) throws IOException {
        try {
            Map<Long, Method> methods = parseMethods(dir.resolve("call_tree_methods.csv"));
            Map<Long, Invoke> invokes = parseInvokes(dir.resolve("call_tree_invokes.csv"));
            CallGraph callGraph = new CallGraph(msName, methods.values(), new ArrayList<>());
            CallGraphLinker.linkCallGraph(callGraph, methods, invokes, dir.resolve("call_tree_targets.csv")); // adding calls for each method
            return callGraph;
        } catch (IOException e) {
            System.err.println("An error occurred while reading files: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("An error occurred while reading files: " + e.getMessage());
        }
    }

}
