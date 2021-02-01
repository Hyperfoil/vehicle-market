import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router'
import 'pure-react-carousel/dist/react-carousel.es.css';
import { FeatureCategory, fetchOfferingById, OfferingDetails } from './listingService';
import {
    Bullseye,
    Spinner,
    Tab,
    Tabs,
    TabTitleText,
    Tooltip,
} from '@patternfly/react-core';
import './OfferingPage.css'

function hasFeature(offering: OfferingDetails, category: FeatureCategory) {
    return offering.features.some(f => f.category === category);
}

function OfferingPage() {
    const params: any = useParams()
    const offerId: number = params.offerId
    const [offering, setOffering] = useState<OfferingDetails>()
    useEffect(() => {
        fetchOfferingById(offerId).then(setOffering)
    }, [offerId])
    const [activeFeatureTab, setActiveFeatureTab] = useState<FeatureCategory>('INTERIOR')
    const features = offering?.features?.filter(f => f.category === activeFeatureTab)
    if (!offering) {
        return <Bullseye><Spinner /></Bullseye>
    }
    console.log(features)
    return <div className="offering">
        { /* TODO: use proper carousel */ }
        <div className="carousel">
            { offering.gallery.map((img, i) => <img key={i} src={img.url} alt={img.title} />)}
        </div>
        <div>
            <h1>{ offering.overview.make } { offering.overview.model }, { offering.overview.year }</h1>
            <span>Buy now for just <span className="price total">$1234.00</span></span>
            <h3>Technical parameters</h3>
            <div className="techparams">
                <span className="label">Operating since</span>
                <span>{ offering.overview.year }</span>
                <span className="label">Class</span>
                <span>{ offering.overview.vClass }</span>
                <span className="label">Mileage</span>
                <span>{ offering.overview.mileage }</span>
                <span className="label">Fuel</span>
                <span>{ offering.overview.fuel }</span>
                <span className="label">Engine</span>
                <span>{ offering.overview.engine }</span>
                <span className="label">Seats</span>
                <span>{ offering.overview.seats }</span>
                <span className="label">Transmission</span>
                <span>{ offering.overview.trany }</span>
                <span className="label">Emissions</span>
                <span>{ offering.overview.emissions }</span>
                <span className="label">Color</span>
                <span>{ offering.overview.color }</span>
                <span className="label">History</span>
                <span>{ offering.history }</span>
                { offering.overview.trimLevel !== "" && <>
                <span className="label">Trim level</span>
                <span>{ offering.overview.trimLevel }</span>
                </>}
                { offering.inspectionValidUntil && <>
                <span className="label">Inspection valid until</span>
                <span>{ offering.inspectionValidUntil }</span>
                </>}
            </div>
            { features && features.length > 0 && <>
            <h3>Features</h3>
            <Tabs activeKey={activeFeatureTab} onSelect={(_, key) => setActiveFeatureTab(key as FeatureCategory) } >
                { hasFeature(offering, 'INTERIOR') &&
                <Tab eventKey={'INTERIOR'} title={<TabTitleText>Interior</TabTitleText>} />
                }
                { hasFeature(offering, 'INFOTAINMENT') &&
                <Tab eventKey={'INFOTAINMENT'} title={<TabTitleText>Infotainment</TabTitleText>} />
                }
                { hasFeature(offering, 'EXTERIOR') &&
                <Tab eventKey={'EXTERIOR'} title={<TabTitleText>Exterior</TabTitleText>} />
                }
                { hasFeature(offering, 'SAFETY') &&
                <Tab eventKey={'SAFETY'} title={<TabTitleText>Safety</TabTitleText>} />
                }
                { hasFeature(offering, 'OTHER') &&
                <Tab eventKey={'OTHER'} title={<TabTitleText>Extra</TabTitleText>} />
                }
            </Tabs>
            <div className="features">
                { features.map((f, i) => f.description ?
                    <Tooltip key={i} content={ f.description }>
                        <span style={{ textDecoration: "underline "}}>{ f.name }</span>
                    </Tooltip> : f.name) }
            </div>
            </> }
        </div>
    </div>
}

export default OfferingPage;