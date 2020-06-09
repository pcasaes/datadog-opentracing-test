package org.acme;

import io.opentracing.References;
import io.opentracing.Scope;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;

public class Main {

    public static void main(String[] args) {
        Tracer tracer = GlobalTracer.get();

        System.out.println("Will create a new context");
        try (Scope scope1 = tracer.buildSpan("test-span")
                .ignoreActiveSpan()
                .startActive(true)) {

            System.out.println("Will create a a context that follows from previous");
            try (Scope scope2 = tracer.buildSpan("test-span")
                    .ignoreActiveSpan()
                    .addReference(References.FOLLOWS_FROM, scope1.span().context()) //will break here
                    .startActive(true)) {

                System.out.println("done");

            }
        }

    }
}
