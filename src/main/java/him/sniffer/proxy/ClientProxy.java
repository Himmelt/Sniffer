package him.sniffer.proxy;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import him.sniffer.client.command.CommandSniffer;
import him.sniffer.config.Config;
import him.sniffer.handler.EventBusHandler;
import him.sniffer.handler.FMLEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void loadConfig(File cfgDir) {
        config = new Config(cfgDir);
    }

    @Override
    public void registCommand() {
        ClientCommandHandler.instance.registerCommand(new CommandSniffer());
    }

    @Override
    public void registKeyBinding() {
        ClientRegistry.registerKeyBinding(keySwitch);
    }

    @Override
    public void registEventHandler() {
        MinecraftForge.EVENT_BUS.register(new EventBusHandler());
        FMLCommonHandler.instance().bus().register(new FMLEventHandler());
    }

    @Override
    public synchronized void addChatMessage(String key, Object... objects) {
        Minecraft client = FMLClientHandler.instance().getClient();
        if (client != null && client.thePlayer != null) {
            client.thePlayer.addChatMessage(new ChatComponentText(I18n.format("sf.chat.head") + ' ' + I18n.format(key, objects)));
        }
    }
}