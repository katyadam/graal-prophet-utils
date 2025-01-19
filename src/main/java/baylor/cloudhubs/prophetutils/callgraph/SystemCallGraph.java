package baylor.cloudhubs.prophetutils.callgraph;

import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
