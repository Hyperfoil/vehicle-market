import CONFIG from './config'
import { Feature } from './listingService';

const BASE_URL = (CONFIG ? CONFIG.discoveryUrl : "") + "/vehicle";

// Maps to io.hyperfoil.market.vehicle.model.VehicleDescription
export type VehicleDescription = {
    id: number,
    make: string,
    model: string,
    year: number,
    trany: string,
    drive: string,
    engine: string,
    vClass: string,
    fuel: string,
    seats: number,
    emissions: string;
}

export function fetchMakes(): Promise<string[]> {
    return fetch(BASE_URL + "/makes").then(res => res.json())
}

export function fetchModels(make: string): Promise<string[]> {
    return fetch(BASE_URL + "/byMake/" + make).then(res => res.json())
}
