import CONFIG from './config'

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

const DESCRIPTION_FIELDS = [ "id", "make", "model", "year", "trany", "drive", "engine",
    "vClass", "fuel", "seats", "emissions" ]

export function validateVehicleDescription(d: Partial<VehicleDescription>) {
    for (var f in DESCRIPTION_FIELDS) {
        if (d[f as (keyof VehicleDescription)] === undefined) return f
    }
    return undefined
}

export function fetchMakes(): Promise<string[]> {
    return fetch(BASE_URL + "/makes").then(res => res.json())
}

export function fetchModels(make: string): Promise<string[]> {
    return fetch(BASE_URL + "/models/" + make).then(res => res.json())
}
