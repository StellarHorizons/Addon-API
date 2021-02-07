package com.hrznstudio.galacticraft.api.reaserch.criteria;

import com.hrznstudio.galacticraft.api.regisry.AddonRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.Registry;

public abstract class ResearchCriteria<C extends ResearchCriteriaConfig> {
   private final Codec<ConfiguredResearchCriteria<C, ResearchCriteria<C>>> codec;

   private static <C extends ResearchCriteriaConfig, F extends ResearchCriteria<C>> F register(String name, F reward) {
      return Registry.register(AddonRegistry.RESEARCH_CRITERIA, name, reward);
   }

   public ResearchCriteria(Codec<C> configCodec) {
      this.codec = configCodec.fieldOf("config").xmap((config) -> new ConfiguredResearchCriteria<>(this, config), (configuredResearchCriteria) -> configuredResearchCriteria.config).codec();
   }

   public Codec<ConfiguredResearchCriteria<C, ResearchCriteria<C>>> getCodec() {
      return this.codec;
   }

   public ConfiguredResearchCriteria<C, ResearchCriteria<C>> configure(C config) {
      return new ConfiguredResearchCriteria<>(this, config);
   }

   public abstract boolean test(ServerPlayerEntity player, C config);
}