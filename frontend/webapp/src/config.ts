export {}

type Config = {
    discoveryUrl: string,
    listingUrl: string,
    userUrl: string,
}

declare global {
    var vehicleMarketConfig: Config;
}

export default window.vehicleMarketConfig