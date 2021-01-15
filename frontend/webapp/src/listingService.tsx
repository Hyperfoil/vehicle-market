import CONFIG from './config'
import { VehicleDescription } from './discoveryService'

const BASE_URL = CONFIG ? CONFIG.listingUrl : "";

// Maps to io.hyperfoil.market.listing.client.Offering
export type Offering = {
    id: number,
    model: VehicleDescription,
    trimLevel: string,
    mileage: number,
    year: number,
    color: string,
    prevOwners: number,
    inspectionValidUntil: Date,
    history: string,
    features: Feature[],
    gallery: GalleryItem[],
}

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

export function fetchOfferings(page: number, perPage: number): Promise<Offering[]> {
    return fetch(BASE_URL + "/list?page=" + page + "&perPage=" + perPage).then(res => res.json())
}

export function fetchOfferingById(id: number): Promise<Offering> {
    return fetch(BASE_URL + "/offering/" + id).then(res => res.json())
}

export function fetchAllFeatures(): Promise<Feature[]> {
    return fetch(BASE_URL + "/allfeatures").then(res => res.json())
}