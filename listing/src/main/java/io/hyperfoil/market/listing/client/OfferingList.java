package io.hyperfoil.market.listing.client;

import java.util.List;

public class OfferingList {

    public int page;

    public int perPage;

    public int total;

    public List<OfferingOverview> items;

    public OfferingList(int page, int perPage, int total, List<OfferingOverview> items) {
        this.page = page;
        this.perPage = perPage;
        this.total = total;
        this.items = items;
    }
}
