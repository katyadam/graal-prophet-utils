package baylor.cloudhubs.prophetutils.callgraph;

import baylor.cloudhubs.prophetutils.microservice.Microservice;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CallGraphCollector {

    private static final List<CallGraph> callGraphs = new ArrayList<>();

    public static void saveCallGraph(String callGraphOutputDir, Microservice ms) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            CallGraph callGraph = CallGraph.create(
                    new File(callGraphOutputDir + "/reports").toPath(),
                    ms.getMicroserviceName(),
                    ms.getBasePackage()
            );
            callGraphs.add(callGraph);
            objectMapper.writeValue(new File(callGraphOutputDir + "/reports/callGraph.json"), callGraph);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<CallGraph> getCollectedCallGraphs() {
        return Collections.unmodifiableList(callGraphs);
    }
}
