package com;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

public class Test {
    public static void main(String[] args) {
        App app = new App();
        app.setName("榛子IT教育");
        app.setType("教育培训");
        Server server = new Server();
        server.setHost("127.0.0.1");
        server.setPort(80);
        app.setServer(server);

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setIndent(5);
//        options.setCanonical(true);
//        options.setExplicitStart(true);
//        options.setExplicitEnd(true);
        //options.setDefaultScalarStyle(DumperOptions.ScalarStyle.LITERAL);
        Yaml yaml = new Yaml(options);
        String s = yaml.dump(app);
        System.out.println(s);

    }
}
