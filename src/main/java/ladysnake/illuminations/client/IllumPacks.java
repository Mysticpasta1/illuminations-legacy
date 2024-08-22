package ladysnake.illuminations.client;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.AbstractPackResources;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.server.packs.repository.Pack.Position;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.world.flag.FeatureFlagSet;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.Nullable;

public class IllumPacks implements RepositorySource {
    public IllumPacks() {
    }

    @Override
    public void loadPacks(Consumer<net.minecraft.server.packs.repository.Pack> infoConsumer) {
        infoConsumer.accept(net.minecraft.server.packs.repository.Pack.create("lowerres", Component.literal("lowerres"),
                false, string -> new Pack("lowerres"),
                new net.minecraft.server.packs.repository.Pack.Info(Component.literal("lowerres"), 15, FeatureFlagSet.of()), PackType.CLIENT_RESOURCES, Position.TOP, false, PackSource.BUILT_IN));
        infoConsumer.accept(net.minecraft.server.packs.repository.Pack.create("pixelaccurate", Component.literal("pixelaccurate"),
                false, string -> new Pack("pixelaccurate"),
                new net.minecraft.server.packs.repository.Pack.Info(Component.literal("pixelaccurate"), 15, FeatureFlagSet.of()), PackType.CLIENT_RESOURCES, Position.TOP, false, PackSource.BUILT_IN));
    }

    public static class Pack implements PackResources {
        private final String name;

        public Pack(String name) {
            this.name = name;
        }

        public @Nullable InputStream getRootResource(String fileName) {
            return IllumPacks.class.getResourceAsStream("/resourcepacks/" + this.name + "/" + fileName);
        }

        @Override
        public @Nullable IoSupplier<InputStream> getRootResource(String... strings) {
            return null;
        }

        public IoSupplier<InputStream> getResource(PackType type, ResourceLocation id) {
            String var10001 = id.getNamespace();
            return () -> Objects.requireNonNull(getRootResource("assets/" + var10001 + "/" + id.getPath()));
        }

        @Override
        public void listResources(PackType arg, String string, String string2, ResourceOutput arg2) {}

        public Collection<ResourceLocation> getResources(PackType type, String namespace, String prefix, Predicate<ResourceLocation> allowedPathPredicate) {
            System.out.println("3");
            System.out.println(namespace);
            System.out.println(prefix);
            return (Collection)(namespace.equals(this.name) && "textures/particle/firefly.png".contains(prefix) ? Collections.singleton(new ResourceLocation(this.name, "textures/particle/firefly.png")) : Collections.emptyList());
        }

        public boolean hasResource(PackType type, ResourceLocation id) {
            return this.getResource(type, id) != null;
        }

        public Set<String> getNamespaces(PackType type) {
            return Collections.singleton(this.name);
        }

        public <T> T getMetadataSection(MetadataSectionSerializer<T> metaReader) {
            int var10000 = SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES);
            String pack = "{\"pack\":{\"pack_format\":" + var10000 + ",\"description\":\"" + this.name + "\"}}";
            return AbstractPackResources.getMetadataFromStream(metaReader, IOUtils.toInputStream(pack, StandardCharsets.UTF_8));
        }

        @Override
        public String packId() {
            return "";
        }

        public String getName() {
            return this.name;
        }

        public void close() {
        }
    }
}
