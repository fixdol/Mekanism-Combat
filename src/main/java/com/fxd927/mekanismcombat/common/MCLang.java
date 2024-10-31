package com.fxd927.mekanismcombat.common;

import com.fxd927.mekanismscience.common.MekanismScience;
import mekanism.api.text.ILangEntry;
import net.minecraft.Util;

public enum MCLang implements ILangEntry {
    MEKANISM_COMBAT("constants","mod_name");

    private final String key;

    MCLang(String type,String path){
        this(Util.makeDescriptionId(type, MekanismCombat.rl(path)));
    }

    MCLang(String key){
        this.key = key;
    }

    @Override
    public String getTranslationKey(){
        return key;
    }
}
