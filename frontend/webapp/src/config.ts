export {}

type Config = {
    discoveryUrl: string
    listingUrl: string
}

declare global {
    var vehicleMarketConfig: Config;
}

export default window.vehicleMarketConfig