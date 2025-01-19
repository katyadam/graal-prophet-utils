package baylor.cloudhubs.prophetutils.callgraph;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

// InvokeId,TargetId

public class CallGraphLinker {

    public static void linkCallGraph(
            CallGraph callGraph,
            Map<Long, Invoke> invokes,
            Path pathToTargets
    ) throws IOException {
        if (Files.exists(pathToTargets)) {
            System.out.println("Linking with: " + pathToTargets);
            try (BufferedReader reader = Files.newBufferedReader(pathToTargets)) {
                String line;
                boolean skipFirst = true;
                while ((line = reader.readLine()) != null) {
                    if (skipFirst) {
                        skipFirst = false;
                        continue;
                    }
                    String[] ids = line.split(",");
                    Invoke invoke = invokes.getOrDefault(Long.parseLong(ids[0]), null);
                    if (invoke == null) {
                        continue;
                    }
                    callGraph.addCall(new Call(invoke.getMethodId(), Long.parseLong(ids[1])));
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        }
    }

}
