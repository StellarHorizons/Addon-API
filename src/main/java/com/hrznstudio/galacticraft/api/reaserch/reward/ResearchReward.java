/*
 * Copyright (c) 2019-2021 HRZN LTD
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.hrznstudio.galacticraft.api.reaserch.reward;

import com.hrznstudio.galacticraft.api.regisry.AddonRegistry;
import com.mojang.serialization.Codec;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.Registry;

public abstract class ResearchReward<C extends ResearchRewardConfig> {
   private final Codec<ConfiguredResearchReward<C, ResearchReward<C>>> codec;

   private static <C extends ResearchRewardConfig, F extends ResearchReward<C>> F register(String name, F reward) {
      return Registry.register(AddonRegistry.RESEARCH_REWARDS, name, reward);
   }

   public ResearchReward(Codec<C> configCodec) {
      this.codec = configCodec.fieldOf("config").xmap((config) -> new ConfiguredResearchReward<>(this, config), (configuredResearchReward) -> configuredResearchReward.config).codec();
   }

   public Codec<ConfiguredResearchReward<C, ResearchReward<C>>> getCodec() {
      return this.codec;
   }

   public ConfiguredResearchReward<C, ?> configure(C config) {
      return new ConfiguredResearchReward<>(this, config);
   }

   public abstract void apply(ServerPlayerEntity player, C config);
}
