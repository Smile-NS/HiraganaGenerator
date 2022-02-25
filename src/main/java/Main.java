import io.github.smile_ns.simplejson.SimpleJson;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        output("hiragana/general");
        output("hiragana/other");
    }

    static void output(String path) throws IOException {
        File[] folder = new File("dev/textures/" + path).listFiles();
        assert folder != null;

        SimpleJson paper = new SimpleJson(new File("dev/models/item/paper.json"));

        for (File file : folder) {

            String fileName = file.getName().substring(0, file.getName().indexOf('.'));
            String filePath = path + "/" + fileName;

            List<Object> list = paper.getList("overrides");
            SimpleJson custom = new SimpleJson();
            custom.put("predicate.custom_model_data", list.size() + 1);
            custom.put("model", filePath);
            list.add(custom.toJsonNode());
            paper.put("overrides", list);

            SimpleJson json = new SimpleJson("{\n" +
                    "  \"parent\": \"minecraft:item/generated\",\n" +
                    "  \"textures\": {\n" +
                    "    \"layer0\": \"\"\n" +
                    "  }\n" +
                    "}");

            json.setFile(new File("dev/models/" + filePath + ".json"));
            json.put("textures.layer0", "minecraft:" + filePath);
            json.save();
        }

        paper.save();
    }
}
