package com.sudolev.interiors.foundation.data;

import com.sudolev.interiors.CreateInteriors;
import com.tterrag.registrate.providers.RegistrateTagsProvider;

import net.minecraft.core.Registry;
import net.minecraft.data.tags.TagsProvider.TagAppender;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

import java.util.function.Consumer;

/**
 * A common tag is a trio of tags: one for common, one for fabric, and one for forge.
 * The common tag contains both loader tags and should only be used for querying.
 * Content is added to both loader tags separately.
 */
public class CommonTag<T> {
	public final TagKey<T> tag, fabric, forge;

	public CommonTag(TagKey<T> common, TagKey<T> fabric, TagKey<T> forge) {
		this.tag = common;
		this.fabric = fabric;
		this.forge = forge;
	}

	public CommonTag(ResourceKey<? extends Registry<T>> registry, ResourceLocation common, ResourceLocation fabric, ResourceLocation forge) {
		this(TagKey.create(registry, common), TagKey.create(registry, fabric), TagKey.create(registry, forge));
	}

	public static <T> CommonTag<T> conventional(ResourceKey<? extends Registry<T>> registry, String common, String fabric, String forge) {
		return new CommonTag<>(
			registry,
			CreateInteriors.asResource("internal/" + common),
			new ResourceLocation("c", fabric),
			new ResourceLocation("forge", forge)
		);
	}

	public CommonTag<T> generateBoth(RegistrateTagsProvider<T> tags, Consumer<TagAppender<T>> consumer) {
		#if PRE_CURRENT_MC_19_2
		consumer.accept(tags.tag(fabric));
		consumer.accept(tags.tag(forge));
		#else
		consumer.accept(tags.addTag(fabric));
		consumer.accept(tags.addTag(forge));
		#endif
		return this;
	}

	public CommonTag<T> generateCommon(RegistrateTagsProvider<T> tags) {
		#if PRE_CURRENT_MC_19_2
		tags.tag(tag)
		#else
		tags.addTag(tag)
		#endif
			.addOptionalTag(fabric.location())
			.addOptionalTag(forge.location());
		return this;
	}
}