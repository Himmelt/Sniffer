package org.soraworld.sniffer.config;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.soraworld.sniffer.config.property.IProperty.PropertyD;
import org.soraworld.sniffer.constant.Constants;

import java.io.File;

import static org.soraworld.sniffer.Sniffer.proxy;

@SideOnly(Side.CLIENT)
public class Config {

    public final PropertyD hudX = new PropertyD(Constants.MODID, "hudX", 0.05D);
    public final PropertyD hudY = new PropertyD(Constants.MODID, "hudY", 0.05D);

    private final Configuration config;
    private final File jsonFile;

    public Config(File configDir) {
        config = new Configuration(new File(configDir, Constants.MODID + ".cfg"), Constants.VERSION);
        jsonFile = new File(new File(configDir, Constants.MODID), "target.json");
        config.load();
        bind();
        comments();
        config.save();
        Constants.LOGGER.info("config reloaded!");
    }

    public void reload() {
        config.load();
        bind();
        proxy.sniffer.reload(jsonFile);
        proxy.setGamma(1);
        Constants.LOGGER.info("config reloaded!");
    }

    public void comments() {
        hudX.setComment(I18n.format("sf.cfg.hudX"));
        hudY.setComment(I18n.format("sf.cfg.hudY"));
    }

    public void save() {
        config.save();
        proxy.sniffer.save(jsonFile);
        Constants.LOGGER.info("config saved!");
    }

    private void bind() {
        hudX.bind(config);
        hudY.bind(config);
    }

}