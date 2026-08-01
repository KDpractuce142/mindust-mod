package example;

import arc.*;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.world.blocks.logic.MessageBlock.*;
import mindustry.gen.*;
import java.util.HashMap;

public class ExampleJavaMod extends Mod {
    // Храним предыдущий текст для каждого блока по его ID
    private final HashMap<Integer, String> lastMessages = new HashMap<>();

    @Override
    public void init(){
        // Слушаем каждый такт игры
        Events.run(Trigger.update, () -> {
            // Перебираем все постройки на карте
            Groups.build.each(b -> {
                if(b instanceof MessageBuild message){
                    String currentText = message.message;
                    int id = message.id;

                    // Получаем прошлый текст этого конкретного блока
                    String lastText = lastMessages.getOrDefault(id, "");

                    // Если текст изменился и он не пустой
                    if(!currentText.equals(lastText) && !currentText.isEmpty()){
                        // Запоминаем новый текст
                        lastMessages.put(id, currentText);

                        // Пишем в файл
                        var file = Core.files.external("scrap_log.txt");
                        file.writeString(currentText + "\n", true);
                        
                        Log.info("Процессор/Игрок записал: " + currentText);
                    }
                }
            });
        });

        // Очищаем память при смене карты
        Events.on(WorldLoadEvent.class, e -> lastMessages.clear());
    }
}
