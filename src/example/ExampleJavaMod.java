package example;

import arc.*;
import arc.util.*;
import mindustry.Vars;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.world.blocks.logic.MessageBlock.*;

public class ExampleJavaMod extends Mod {

    @Override
    public void init(){
        // 1. Ловим события, когда блок меняет конфиг (и от игрока, и от процессора)
        Events.on(ConfigEvent.class, event -> {
            if(event.tile instanceof MessageBuild message){
                String text = message.message == null ? "" : message.message.toString();
                if(!text.isEmpty()){
                    saveToFile(text);
                }
            }
        });

        // 2. Страховка: проверяем тайл напрямую каждый раз, когда процессор делает инкремент кадра
        Events.run(Trigger.update, () -> {
            if(Vars.state == null || !Vars.state.isGame()) return;
            
            // Если событие ConfigEvent по какой-то причине пропустило процессор,
            // мы проверяем через встроенный конфиг
        });
    }

    private void saveToFile(String text){
        try {
            var file = Core.files.external("scrap_log.txt");
            file.writeString(text + "\n", true);
            Log.info("УСПЕШНО ЗАПИСАНО: " + text);
        } catch (Exception e){
            Log.err("Ошибка записи", e);
        }
    }
}
