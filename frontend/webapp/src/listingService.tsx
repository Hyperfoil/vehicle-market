import CONFIG from './config'

type VehicleListing = {
    id: number,
    descriptionId: number,

}

function fetchCars(page: number, perPage: number) {
    fetch(CONFIG.listingUrl + "/list?page=" + page + "&perPage=" + perPage )
}