package io.hyperfoil.market.vehicle.dto;

import io.hyperfoil.market.vehicle.model.VehicleOffer;

import java.util.List;
import java.util.stream.Collectors;

public class OfferingList {

    public int page;

    public int perPage;

    public int total;

    public List<Offering> items;

    public OfferingList(int page, int perPage, int total, List<? extends VehicleOffer> items) {
        this.page = page;
        this.perPage = perPage;
        this.total = total;
        this.items = items.stream().map(Offering::new).collect(Collectors.toList());
    }
}
