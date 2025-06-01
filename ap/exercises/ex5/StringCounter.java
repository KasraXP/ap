package exercises.ex5;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StringCounter <String> {

    private Map<String,Integer> counterMap;

    public StringCounter() {
        this.counterMap=new HashMap<>();
    }

    public void add(String item){
        if (this.counterMap.containsKey(item)){
            this.counterMap.put(item, this.counterMap.get(item)+1);
        }
        else{
            this.counterMap.put(item,1);
        }
    }

    public List<Map.Entry<String, Integer>> getTop(int k){
        return this.counterMap.entrySet().stream()
                .sorted((a,b) -> -a.getValue().compareTo(b.getValue()))
                .limit(k)
                .collect(Collectors.toList());
    }
}