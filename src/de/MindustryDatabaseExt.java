package de;

import arc.*;
import arc.util.*;
import mindustry.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.world.blocks.power.*;
import mindustry.world.meta.*;

public class MindustryDatabaseExt extends Mod{
    public MindustryDatabaseExt(){
        Events.on(ClientLoadEvent.class, e -> {
            Time.run(60f, this::add);
        });
    }

    public void add(){
        Vars.content.blocks().each(block -> {
            block.stats.add(solid, block.solid);
            block.stats.add(group, block.group.name());
            block.stats.add(canOverdrive, block.canOverdrive);
            block.stats.add(fogRadius, block.fogRadius);
            block.stats.add(sync, block.sync);

            block.stats.add(priority, block.priority);
            if(block.suppressable) block.stats.add(suppressable, block.suppressable);
            block.stats.add(attacks, block.attacks);
            if(!block.targetable) block.stats.add(targetable, block.targetable);
            block.stats.add(underBullets, block.underBullets);

            if(block instanceof ConsumeGenerator cg) block.stats.add(Stat.productionTime, 1f / (cg.itemDuration / 60f), StatUnit.perSecond);
        });

        Vars.content.units().each(unit -> {
            unit.stats.add(fogRadius, unit.fogRadius);
            unit.stats.add(targetAir, unit.targetAir);
            unit.stats.add(targetGround, unit.targetGround);
            if(unit.targetFlags != null){
                for(BlockFlag flag : unit.targetFlags){
                    if(flag != null) unit.stats.add(targetFlags, Core.bundle.get(flag.name()));
                }
            }
        });
    }

    public final Stat solid = new Stat("solid");
    public final Stat group = new Stat("group");
    public final Stat canOverdrive = new Stat("canOverdrive");
    public final Stat fogRadius = new Stat("fogRadius");
    public final Stat sync = new Stat("sync");

    public final StatCat atkdef = new StatCat("atkdef");
    public final Stat priority = new Stat("priority", atkdef);
    public final Stat suppressable = new Stat("suppressable", atkdef);
    public final Stat attacks = new Stat("attacks", atkdef);
    public final Stat targetable = new Stat("targetable", atkdef);
    public final Stat underBullets = new Stat("underBullets", atkdef);
    public final Stat targetAir = new Stat("targetAir", atkdef);
    public final Stat targetGround = new Stat("targetGround", atkdef);
    public final Stat targetFlags = new Stat("targetFlags", atkdef);
}
