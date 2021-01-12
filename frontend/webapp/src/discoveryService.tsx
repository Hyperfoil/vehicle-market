import CONFIG from './config'

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
