package example;

import arc.*;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.world.blocks.logic.MessageBlock.*;

public class ExampleMod extends Mod {
    @Override
    public void init(){
        Events.on(ConfigEvent.class, event -> {
            if(event.tile instanceof MessageBuild message){
                Core.files.external("scrap_log.txt").appendString(message.message + "\n");
                Log.info("Записано в log: " + message.message);
            }
        });
    }
}
