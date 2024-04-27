package ladysnake.illuminations.client;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.SharedConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.AbstractPackResources;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.server.packs.repository.Pack.Position;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.Nullable;

public class IllumPacks implements RepositorySource {
    public IllumPacks() {
    }

    public void loadPacks(Consumer<net.minecraft.server.packs.repository.Pack> infoConsumer, net.minecraft.server.packs.repository.Pack.PackConstructor factory) {
        infoConsumer.accept(net.minecraft.server.packs.repository.Pack.create("lowerres", false, () -> {
            return new Pack("lowerres");
        }, factory, Position.TOP, PackSource.BUILT_IN));
        infoConsumer.accept(net.minecraft.server.packs.repository.Pack.create("pixelaccurate", false, () -> {
            return new Pack("pixelaccurate");
        }, factory, Position.TOP, PackSource.BUILT_IN));
    }

    public static class Pack implements PackResources {
        private final String name;

        public Pack(String name) {
            this.name = name;
        }

        public @Nullable InputStream getRootResource(String fileName) {
            return IllumPacks.class.getResourceAsStream("/resourcepacks/" + this.name + "/" + fileName);
        }

        public InputStream getResource(PackType type, ResourceLocation id) throws IOException {
            String var10001 = id.getNamespace();
            return this.getRootResource("assets/" + var10001 + "/" + id.getPath());
        }

        public Collection<ResourceLocation> getResources(PackType type, String namespace, String prefix, Predicate<ResourceLocation> allowedPathPredicate) {
            System.out.println("3");
            System.out.println(namespace);
            System.out.println(prefix);
            return (Collection)(namespace.equals(this.name) && "textures/particle/firefly.png".contains(prefix) ? Collections.singleton(new ResourceLocation(this.name, "textures/particle/firefly.png")) : Collections.emptyList());
        }

        public boolean hasResource(PackType type, ResourceLocation id) {
            try {
                return this.getResource(type, id) != null;
            } catch (IOException var4) {
                return false;
            }
        }

        public Set<String> getNamespaces(PackType type) {
            return Collections.singleton(this.name);
        }

        public <T> T getMetadataSection(MetadataSectionSerializer<T> metaReader) {
            int var10000 = SharedConstants.getCurrentVersion().getPackVersion(com.mojang.bridge.game.PackType.RESOURCE);
            String pack = "{\"pack\":{\"pack_format\":" + var10000 + ",\"description\":\"" + this.name + "\"}}";
            return AbstractPackResources.getMetadataFromStream(metaReader, IOUtils.toInputStream(pack, StandardCharsets.UTF_8));
        }

        public String getName() {
            return this.name;
        }

        public void close() {
        }
    }
}
