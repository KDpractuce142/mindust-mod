package example;

import arc.*;
import arc.util.*;
import mindustry.Vars;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.world.blocks.logic.MemoryBlock.*;
import mindustry.gen.*;
import java.util.HashMap;

public class ExampleJavaMod extends Mod {
    // Храним последнее прочитанное значение для каждой ячейки памяти по её ID
    private final HashMap<Integer, Double> lastValues = new HashMap<>();

    @Override
    public void init(){
        Events.run(Trigger.update, () -> {
            if(Vars.state == null || !Vars.state.isGame()) return;

            Groups.build.each(b -> {
                // Проверяем, что постройка — это MemoryCell или MemoryBank
                if(b instanceof MemoryBuild memory){
                    // Читаем значение из ячейки [0]
                    double currentValue = memory.memory[0];
                    int id = memory.id;

                    double lastValue = lastValues.getOrDefault(id, -999999.0);

                    // Если число в ячейке [0] изменилось
                    if(currentValue != lastValue){
                        lastValues.put(id, currentValue);

                        // Пишем значение в файл
                        var file = Core.files.external("scrap_log.txt");
                        file.writeString(currentValue + "\n", true);

                        Log.info("Память cell1[0] изменилась: " + currentValue);
                    }
                }
            });
        });

        // Очищаем кэш при перезапуске карты
        Events.on(WorldLoadEvent.class, e -> lastValues.clear());
    }
}
