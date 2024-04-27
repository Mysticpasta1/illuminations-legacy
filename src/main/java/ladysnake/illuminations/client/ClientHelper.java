package ladysnake.illuminations.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;

public class ClientHelper {
    public ClientHelper() {
    }

    public static void load() {
        if (ModList.get().isLoaded("cloth_config")) {
            ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> {
                return new ConfigScreenHandler.ConfigScreenFactory((mc, screen) -> {
                    return (new IlluminationsModMenuIntegration()).getModConfigScreenFactory(screen);
                });
            });
        }

        Minecraft.getInstance().getResourcePackRepository().addPackFinder(new IllumPacks());
    }
}
