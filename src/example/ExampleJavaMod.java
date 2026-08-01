package example;

import arc.*;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.world.blocks.logic.MessageBlock.*;
import mindustry.Vars;

public class ExampleJavaMod extends Mod {
    @Override
    public void init(){
        Events.on(ConfigEvent.class, event -> {
            if(event.tile instanceof MessageBuild message){
                // Берем файл в папочке данных самой игры (гарантированный путь)
                var file = Vars.dataDirectory.child("scrap_log.txt");
                
                file.writeString(message.message + "\n", true);
                
                // Выводим ТОЧНЫЙ абсолютный путь прямо в игровой чат
                if(Vars.ui != null && Vars.ui.chatfrag != null){
                    Vars.ui.chatfrag.addMessage("[green]Сохранено в:[white] " + file.absolutePath());
                }
            }
        });
    }
}
