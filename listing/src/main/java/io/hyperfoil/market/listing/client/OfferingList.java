package io.hyperfoil.market.listing.client;

import java.util.Collection;

public class OfferingList {
   public int page;
   public int perPage;
   public int total;
   public Collection<Offering> items;

   public OfferingList(int page, int perPage, int total, Collection<Offering> items) {
      this.page = page;
      this.perPage = perPage;
      this.total = total;
      this.items = items;
   }
}
