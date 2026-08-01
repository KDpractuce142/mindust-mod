package example;

import arc.*;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.world.blocks.logic.MessageBlock.*;

public class ExampleJavaMod extends Mod {
    @Override
    public void init(){
        Events.on(ConfigEvent.class, event -> {
            if(event.tile instanceof MessageBuild message){
                // В Arc метод записи с дозаписью выглядит так: writeString(текст, append)
                Core.files.external("scrap_log.txt").writeString(message.message + "\n", true);
                Log.info("Записано в log: " + message.message);
            }
        });
    }
}
