package pojo;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Region {
    public Set<Restaurant> restaurants;
    public RegionNum regionNum;

    public Region(RegionNum regionNum) {
        this.regionNum = regionNum;
        restaurants = new HashSet<>();
    }

    public Region() {

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Region region = (Region) o;
        return regionNum == region.regionNum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(regionNum);
    }
}
