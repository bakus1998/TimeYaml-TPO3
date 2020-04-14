/**
 * @author Baka Krzysztof S16696
 */

package zad3;


import org.yaml.snakeyaml.Yaml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Tools {
    public static Options createOptionsFromYaml(String fileName) throws Exception {
        FileInputStream fis = new FileInputStream(new File(fileName));
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String syaml = "";
        String tmp = "";

        while ((tmp = br.readLine()) != null) {
            syaml += tmp + "\n";
        }


        Yaml yaml = new Yaml();
        Object obj = yaml.load(syaml);
        LinkedHashMap data = (LinkedHashMap) obj;

        Map<String, List<String>> clientsMap = new LinkedHashMap<>();
        String mapData = data.get("clientsMap").toString();
        String[] split = mapData.split("], ");
        for (int i = 0; i < split.length; i++) {
            String s = split[i].replace("=[", ", ").replace("{", "").replace("]}", "");
            String[] splitToMap = s.split(", ");
            List<String> toMap = new ArrayList<>();

            for (int j = 1; j < splitToMap.length; j++) {
                toMap.add(splitToMap[j]);
            }
            clientsMap.put(splitToMap[0], toMap);
        }

        Options options = new Options(
                data.get("host").toString(),
                Integer.parseInt(data.get("port").toString()),
                data.get("concurMode").toString().equals("true"),
                data.get("showSendRes").toString().equals("true"),
                clientsMap
        );

        return options;
    }

}
