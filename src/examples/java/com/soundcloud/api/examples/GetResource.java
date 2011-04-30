package com.soundcloud.api.examples;

import static com.soundcloud.api.examples.CreateWrapper.WRAPPER_SER;

import com.soundcloud.api.ApiWrapper;
import com.soundcloud.api.Http;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

/**
 * Access a protected resource from the server. This needs a prepared (serialized) API
 * wrapper instance (create one with CreateWrapper).
 * @see CreateWrapper
 */
public final class GetResource {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.err.println(GetResource.class.getSimpleName() + " resource");
            System.exit(1);
        } else if (!WRAPPER_SER.exists()) {
            System.err.println("\nThe serialised wrapper ("+ WRAPPER_SER +") does not exist.\n"+
                "Run CreateWrapper first to create it.");
            System.exit(1);
        } else {
            final String resource = args[0];
            final ApiWrapper wrapper = ApiWrapper.fromFile(WRAPPER_SER);
            System.err.println("GET " + resource);
            try {
                HttpResponse resp = wrapper.getContent(resource);
                if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    System.out.println("\n"+Http.getString(resp)+"\n");
                } else {
                    System.err.println("Invalid status received: "+resp.getStatusLine());
                }
            } finally {
                // serialise wrapper state again (token might have changed)
                wrapper.toFile(WRAPPER_SER);
            }
        }
    }
}