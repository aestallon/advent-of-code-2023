/*
 * Copyright 2023 Szabolcs Bazil Papp
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.aestallon.adventofcode.year2023.day05.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.toMap;

public final class Almanac {

  private static final Logger log = LoggerFactory.getLogger(Almanac.class);


  private record MapRange(long dest, long src, long length) {

    private long get(long key) {
      if (key >= src && key < src + length) {
        return dest + (key - src);
      }
      return -1;
    }

    private long keyOf(long val) {
      if (val >= dest && val < dest + length) {
        return src + (val - dest);
      }
      return -1;
    }
  }

  public static Almanac parse(List<String> lines) {
    // first line is seeds:
    String seedLine = lines.get(0);
    int headerDelimiter = seedLine.indexOf(':');
    long[] seeds = Arrays.stream(seedLine.substring(headerDelimiter + 1).trim().split("\\s"))
        .map(String::trim)
        .mapToLong(Long::parseLong)
        .toArray();

    int ptr = 2;
    MapRange[][] ranges = new MapRange[7][];
    int rangePtr = 0;
    List<MapRange> currentRanges = new ArrayList<>();
    while (ptr < lines.size()) {
      final String s = lines.get(ptr++);
      if (s.contains("map:")) {
        continue;
      }

      if (s.isBlank()) {
        MapRange[] range = currentRanges.toArray(MapRange[]::new);
        ranges[rangePtr++] = range;
        currentRanges.clear();
      } else {
        final long[] lineNums = Arrays.stream(s.trim().split("\\s"))
            .mapToLong(Long::parseLong)
            .toArray();
        currentRanges.add(new MapRange(lineNums[0], lineNums[1], lineNums[2]));
      }
    }
    MapRange[] range = currentRanges.toArray(MapRange[]::new);
    ranges[rangePtr++] = range;
    currentRanges.clear();

    log.debug("Range pointer at {}", rangePtr);
    return new Almanac(
        seeds,
        ranges[0],
        ranges[1],
        ranges[2],
        ranges[3],
        ranges[4],
        ranges[5],
        ranges[6]);
  }

  private record SeedRange(long start, long length) {

    boolean hasSeed(long seed) {
      return seed >= start && seed < start + length;
    }
  }

  private final long[] seeds;
  private final SeedRange[] seedRanges;
  private final MapRange[] seedToSoil;
  private final MapRange[] soilToFertilizer;
  private final MapRange[] fertilizerToWater;
  private final MapRange[] waterToLight;
  private final MapRange[] lightToTemperature;
  private final MapRange[] temperatureToHumidity;
  private final MapRange[] humidityToLocation;

  private Almanac(long[] seeds, MapRange[] seedToSoil, MapRange[] soilToFertilizer,
                  MapRange[] fertilizerToWater, MapRange[] waterToLight,
                  MapRange[] lightToTemperature, MapRange[] temperatureToHumidity,
                  MapRange[] humidityToLocation) {
    this.seeds = seeds;
    this.seedToSoil = seedToSoil;
    this.soilToFertilizer = soilToFertilizer;
    this.fertilizerToWater = fertilizerToWater;
    this.waterToLight = waterToLight;
    this.lightToTemperature = lightToTemperature;
    this.temperatureToHumidity =
        temperatureToHumidity;
    this.humidityToLocation = humidityToLocation;
    this.seedRanges = initSeedRanges(seeds);
  }
  
  private SeedRange[] initSeedRanges(long[] seeds) {
    return IntStream.range(0, seeds.length / 2)
        .mapToObj(i -> new SeedRange(seeds[2 * i], seeds[2 * i + 1]))
        .toArray(SeedRange[]::new);
  }

  private long get(long key, MapRange[] from) {
    final long val = Arrays.stream(from).mapToLong(it -> it.get(key)).max().orElse(-1L);
    return (val == -1L)
        ? key
        : val;
  }

  private long keyOf(long val, MapRange[] from) {
    final long key = Arrays.stream(from).mapToLong(it -> it.keyOf(val)).max().orElse(-1L);
    return (key == -1L)
        ? val
        : key;
  }

  private boolean hasSeed(long seed) {
    return Arrays.stream(seedRanges).anyMatch(it->it.hasSeed(seed));
  }

  private long getLocationOfSeed(long seed) {
    return get(get(get(
        get(get(get(get(seed, seedToSoil), soilToFertilizer), fertilizerToWater), waterToLight),
        lightToTemperature), temperatureToHumidity), humidityToLocation);
  }

  private OptionalLong getSeedOfLocation(long location) {
    final long seed = keyOf(keyOf(keyOf(keyOf(keyOf(keyOf(keyOf(location,
        humidityToLocation), temperatureToHumidity), lightToTemperature), waterToLight),
        fertilizerToWater), soilToFertilizer), seedToSoil);
    return hasSeed(seed) ? OptionalLong.of(seed) : OptionalLong.empty();

  }

  public Map<Long, Long> seedsByLocation() {
    return LongStream.of(seeds)
        .boxed()
        .collect(toMap(
            Function.identity(),
            this::getLocationOfSeed
        ));
  }

  public long lowestLocation() {
    for (long i = 0L; i < Long.MAX_VALUE; i++) {
      if (getSeedOfLocation(i).isPresent()) {
        return i;
      }
    }
    return -1L;
  }
}
