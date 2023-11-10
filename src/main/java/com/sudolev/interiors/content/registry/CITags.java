package com.sudolev.interiors.content.registry;

import static com.simibubi.create.AllTags.NameSpace.MOD;

import com.simibubi.create.AllTags;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.data.TagGen;
import com.simibubi.create.foundation.utility.Lang;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.nullness.NonNullFunction;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

public class CITags {
	public static <T> TagKey<T> optionalTag(Registry<T> registry,
											ResourceLocation id) {
		return TagKey.create(registry.key(), id);
	}

	public static <T> TagKey<T> forgeTag(Registry<T> registry, String path) {
		return optionalTag(registry, new ResourceLocation("c", path));
	}

	public static TagKey<Block> forgeBlockTag(String path) {
		return forgeTag(Registry.BLOCK, path);
	}

	public static TagKey<Item> forgeItemTag(String path) {
		return forgeTag(Registry.ITEM, path);
	}

	public static TagKey<Fluid> forgeFluidTag(String path) {
		return forgeTag(Registry.FLUID, path);
	}

	@Deprecated(forRemoval = true)
	public static <T extends Block, P> NonNullFunction<BlockBuilder<T, P>, BlockBuilder<T, P>> axeOrPickaxe() {
		return TagGen.axeOrPickaxe();
	}

	@Deprecated(forRemoval = true)
	public static <T extends Block, P> NonNullFunction<BlockBuilder<T, P>, BlockBuilder<T, P>> axeOnly() {
		return TagGen.axeOnly();
	}

	@Deprecated(forRemoval = true)
	public static <T extends Block, P> NonNullFunction<BlockBuilder<T, P>, BlockBuilder<T, P>> pickaxeOnly() {
		return TagGen.pickaxeOnly();
	}

	@Deprecated(forRemoval = true)
	public static <T extends Block, P> NonNullFunction<BlockBuilder<T, P>, ItemBuilder<BlockItem, BlockBuilder<T, P>>> tagBlockAndItem(
			String... path) {
		return TagGen.tagBlockAndItem(path);
	}

	public static void init() {
		BlockTags.init();
		ItemTags.init();
	}

	public enum NameSpace {
		MOD(Create.ID, false, true),


		;

		public final String id;
		public final boolean optionalDefault;
		public final boolean alwaysDatagenDefault;

		NameSpace(String id) {
			this(id, true, false);
		}

		NameSpace(String id, boolean optionalDefault, boolean alwaysDatagenDefault) {
			this.id = id;
			this.optionalDefault = optionalDefault;
			this.alwaysDatagenDefault = alwaysDatagenDefault;
		}
	}

	public enum BlockTags {
		CHAIRS,
		FLOOR_CHAIRS,

		;

		public final TagKey<Block> tag;
		public final boolean alwaysDatagen;

		BlockTags() {
			this(MOD);
		}

		BlockTags(AllTags.NameSpace namespace) {
			this(namespace, namespace.optionalDefault, namespace.alwaysDatagenDefault);
		}

		BlockTags(AllTags.NameSpace namespace, String path) {
			this(namespace, path, namespace.optionalDefault, namespace.alwaysDatagenDefault);
		}

		BlockTags(AllTags.NameSpace namespace, boolean optional, boolean alwaysDatagen) {
			this(namespace, null, optional, alwaysDatagen);
		}

		BlockTags(AllTags.NameSpace namespace, String path, boolean optional, boolean alwaysDatagen) {
			ResourceLocation id = new ResourceLocation(namespace.id, path == null ? Lang.asId(name()) : path);
			tag = optionalTag(Registry.BLOCK, id);
			this.alwaysDatagen = alwaysDatagen;
		}

		private static void init() {
		}

		@SuppressWarnings("deprecation")
		public boolean matches(Block block) {
			return block.builtInRegistryHolder()
					.is(tag);
		}

		public boolean matches(BlockState state) {
			return state.is(tag);
		}
	}


	public enum ItemTags {
		CHAIRS,
		FLOOR_CHAIRS;

		public final TagKey<Item> tag;
		public final boolean alwaysDatagen;

		ItemTags() {
			this(MOD);
		}

		ItemTags(AllTags.NameSpace namespace) {
			this(namespace, namespace.optionalDefault, namespace.alwaysDatagenDefault);
		}

		ItemTags(AllTags.NameSpace namespace, String path) {
			this(namespace, path, namespace.optionalDefault, namespace.alwaysDatagenDefault);
		}

		ItemTags(AllTags.NameSpace namespace, boolean optional, boolean alwaysDatagen) {
			this(namespace, null, optional, alwaysDatagen);
		}

		ItemTags(AllTags.NameSpace namespace, String path, boolean optional, boolean alwaysDatagen) {
			ResourceLocation id = new ResourceLocation(namespace.id, path == null ? Lang.asId(name()) : path);
			tag = optionalTag(Registry.ITEM, id);

			this.alwaysDatagen = alwaysDatagen;
		}

		private static void init() {
		}

		@SuppressWarnings("deprecation")
		public boolean matches(Item item) {
			return item.builtInRegistryHolder()
					.is(tag);
		}

		public boolean matches(ItemStack stack) {
			return stack.is(tag);
		}
	}
}
