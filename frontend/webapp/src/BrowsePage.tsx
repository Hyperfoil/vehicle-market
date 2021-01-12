import React, { useEffect, useState } from 'react'
import {
    Bullseye,
    Spinner,
    Tooltip,
} from '@patternfly/react-core'
import { useHistory } from 'react-router'
import { fetchOfferings, Offering } from './listingService'
import './BrowsePage.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faCar, faCogs, faGasPump, faTachometerAlt } from '@fortawesome/free-solid-svg-icons'

function addComma(element: any, add: boolean) {
    return add ? <>{ element }, </> : element;
}

function BrowsePage() {
    const [page, setPage] = useState(0)
    const [perPage, setPerPage] = useState(10)
    const [offerings, setOfferings] = useState<Offering[]>()
    useEffect(() => {
        fetchOfferings(page, perPage).then(setOfferings).catch(_ => setOfferings([]))
    }, [])
    const history = useHistory()
    if (!offerings) {
        return <Bullseye><Spinner /></Bullseye>
    }
    return <div id="offerings">
        { offerings.map((o, i) => <>
            <div
                className="offeringshadow"
                style={{ gridRow: i + 1}}
            />
            <img
                className="offeringimage"
                style={{ gridRow: i + 1}}
                src={ o.gallery.length > 0 ? o.gallery[0].url : "/nocar.svg" }
                onClick={() => history.push("/offering/" + o.id)}
            />
            <div
                className="offeringdesc"
                style={{ gridRow: i + 1}}
                onClick={() => history.push("/offering/" + o.id)}
            >
                <h3>{ o.model.make } { o.model.model }, { o.year }</h3>
                { o.history && <>{ o.history }<br /></> }
                { o.features.slice(0, 8).map((f, j) => addComma(f.description ?
                    <Tooltip content={ f.description }><span style={{ textDecoration: "underline "}}>{ f.name }</span></Tooltip>
                    : f.name, j < Math.min(o.features.length, 8))) }
                <div className="offeringdetails">
                    <div><FontAwesomeIcon icon={ faTachometerAlt } />{o.mileage}</div>
                    <div><FontAwesomeIcon icon={ faCogs } />{o.model.trany}</div>
                    <div><FontAwesomeIcon icon={ faGasPump } />{o.model.fuel} { o.model.emissions }</div>
                    <div><FontAwesomeIcon icon={ faCar } />{ o.model.engine }</div>
                </div>
            </div>
            <div
                className="offeringprices"
                style={{ gridRow: i + 1}}
                onClick={() => history.push("/offering/" + o.id)}
            >
                Price:<br />
                <span className="price total">$123,456</span><br />
                Monthly installment:<br />
                <span className="price installment">from $123</span>
            </div>
        </>)}
    </div>
}

export default BrowsePage;