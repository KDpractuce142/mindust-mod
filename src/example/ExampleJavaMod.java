package example;

import arc.*;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.world.blocks.logic.MessageBlock.*;
import mindustry.gen.*;
import java.util.HashMap;

public class ExampleJavaMod extends Mod {
    private final HashMap<Integer, String> lastMessages = new HashMap<>();

    @Override
    public void init(){
        // Слушаем обновление каждого кадра
        Events.run(Trigger.update, () -> {
            // Если мы не в игре или карта не загружена — ничего не делаем
            if(Vars.state == null || !Vars.state.isGame()) return;

            Groups.build.each(b -> {
                // Проверяем, что постройка — это MessageBlock
                if(b instanceof MessageBuild message){
                    // В Mindustry нативный метод получения текущего текста сообщения — это .config() или message.message
                    Object config = message.config();
                    String currentText = config != null ? config.toString() : "";

                    int id = message.id;
                    String lastText = lastMessages.getOrDefault(id, "");

                    // Если текст изменился
                    if(!currentText.equals(lastText) && !currentText.isEmpty()){
                        lastMessages.put(id, currentText);

                        // Пишем в файл
                        var file = Core.files.external("scrap_log.txt");
                        file.writeString(currentText + "\n", true);

                        Log.info("Message update: " + currentText);
                    }
                }
            });
        });

        // Сбрасываем кэш при загрузке новой карты
        Events.on(WorldLoadEvent.class, e -> lastMessages.clear());
    }
}
