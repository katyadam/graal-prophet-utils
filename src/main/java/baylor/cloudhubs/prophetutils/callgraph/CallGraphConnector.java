package baylor.cloudhubs.prophetutils.callgraph;

import baylor.cloudhubs.prophetutils.visualizer.Endpoint;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CallGraphConnector {

    public static SystemCallGraph connect(List<CallGraph> callGraphs, Map<String, Endpoint> endpointsMap) {
        SystemCallGraph systemCallGraph = new SystemCallGraph();
        for (CallGraph callGraph : callGraphs) {
            List<Method> updatedMethods = callGraph.getMethods().stream()
                    .map(method -> {
                        var endpoint = endpointsMap.get(createEndpointKey(method));
                        if (endpoint != null) {
                            method.setMethodEndpointInfo(true, endpoint.getPath(), endpoint.getHttpMethod());
                        }
                        return method;
                    })
                    .collect(Collectors.toList());
            systemCallGraph.addMethods(updatedMethods);
            systemCallGraph.addCalls(callGraph.getCalls());
        }
        return systemCallGraph;
    }

    private static String createEndpointKey(Method method) {
        return method.getMicroservice() + "/" + method.getType() + "." + method.getName();
    }

}
