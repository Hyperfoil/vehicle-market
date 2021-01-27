import CONFIG from './config'
import { VehicleDescription } from './discoveryService'
import { ContactInfo, appendToken } from './userService';

const BASE_URL = CONFIG ? CONFIG.listingUrl : "";

// Maps to io.hyperfoil.market.listing.client.OfferingOverview
export type OfferingOverview = {
    id: number,
    make: string,
    model: string, 
    trimLevel: string,
    trany: string,
    vClass: string,
    fuel: string,
    seats: number
    emissions: string,
    engine: string,
    mileage: number,
    year: number,
    color: string,
    history: string,
    imageURL: string,
}

// Maps to io.hyperfoil.market.listing.client.OfferingDetails
export type OfferingDetails = {
    overview: OfferingOverview,
    prevOwners: number,
    inspectionValidUntil?: Date,
    history: string,
    features: Feature[],
    gallery: GalleryItem[],
}

export type PartialOffering = Partial<Omit<OfferingDetails, "overview">> & { overview: Partial<OfferingOverview> }

export type FeatureCategory = 'INTERIOR' | 'INFOTAINMENT' | 'EXTERIOR' | 'SAFETY' | 'OTHER';

export type Feature = {
    id: string,
    name: string,
    category: FeatureCategory,
    description?: string,
}

export type GalleryItem = {
    url: string,
    title: string,
}

// Maps to io.hyperfoil.market.vehicle.dto.OfferingList
export type OfferingList = {
    page: number,
    perPage: number,
    total: number,
    items: OfferingOverview[],
}

export const NO_OFFERINGS: OfferingList = { page: 1, perPage: 0, total: 0, items: []};

export function fetchOfferings(page: number, perPage: number): Promise<OfferingList> {
    return fetch(BASE_URL + "/list?page=" + page + "&perPage=" + perPage).then(res => res.json())
}

export function fetchOfferingById(id: number): Promise<OfferingDetails> {
    return fetch(BASE_URL + "/offering/" + id).then(res => res.json())
}

export function fetchAllFeatures(): Promise<Feature[]> {
    return fetch(BASE_URL + "/allfeatures").then(res => res.json())
}

export function publishOffering(offering: PartialOffering, contactInfo: Partial<ContactInfo>): Promise<Response> {
    return fetch(BASE_URL + "/offering", {
        method: 'POST',
        body: JSON.stringify({ offering, contactInfo }),
        headers: appendToken({
            "content-type" : "application/json",
        }),
    })
}