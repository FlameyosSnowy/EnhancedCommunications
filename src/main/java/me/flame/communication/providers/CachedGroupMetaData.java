package me.flame.communication.providers;

import net.luckperms.api.cacheddata.CachedMetaData;

import java.util.Collection;
import java.util.function.Function;

public record CachedGroupMetaData(Collection<String> prefixes, Collection<String> suffixes) {
}
