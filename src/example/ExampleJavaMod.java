package example;

import arc.*;
import arc.util.*;
import mindustry.Vars;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.world.blocks.logic.MessageBlock.*;
import mindustry.gen.*;
import java.util.HashMap;

public class ExampleJavaMod extends Mod {
    private final HashMap<Integer, String> lastMessages = new HashMap<>();

    @Override
    public void init(){
        Events.run(Trigger.update, () -> {
            // Проверяем, что игра активна
            if(Vars.state == null || !Vars.state.isGame()) return;

            Groups.build.each(b -> {
                if(b instanceof MessageBuild message){
                    // Вытягиваем текст с процессора/игрока через toString()
                    String currentText = message.message == null ? "" : message.message.toString();
                    int id = message.id;

                    String lastText = lastMessages.getOrDefault(id, "");

                    // Если текст изменился
                    if(!currentText.equals(lastText) && !currentText.isEmpty()){
                        lastMessages.put(id, currentText);

                        // Пишем в файл
                        var file = Core.files.external("scrap_log.txt");
                        file.writeString(currentText + "\n", true);

                        Log.info("Сообщение обновилось: " + currentText);
                    }
                }
            });
        });

        Events.on(WorldLoadEvent.class, e -> lastMessages.clear());
    }
}
