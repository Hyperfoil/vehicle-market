import React, { useEffect, useState } from 'react'
import {
    Bullseye,
    Button,
    Checkbox,
    DatePicker,
    Form,
    FormGroup,
    Spinner,
    Tab,
    Tabs,
    TabTitleText,
    TextInput,
    Tooltip,
    Wizard,
    WizardStep,
} from '@patternfly/react-core'
import { useHistory } from 'react-router'
import MakeSelect from './components/MakeSelect'
import ModelSelect from './components/ModelSelect'
import NumberSelect from './components/NumberSelect'
import SimpleSelect from './components/SimpleSelect'
import { fetchAllFeatures, publishOffering, FeatureCategory, Feature, OfferingOverview, OfferingDetails, PartialOffering } from './listingService'
import './SellPage.css'
import { ContactInfo, getUserToken } from './userService'

type DescriptionProps = {
    offering: PartialOffering,
    onChange(o: PartialOffering): void,
}

function pad(x: number): string {
    return x >= 10 ? x.toString() : "0" + x;
}

function formatDate(d: Date): string {
    console.log(d)
    return d.getFullYear() + "-" + pad(d.getMonth() + 1) + "-" + pad(d.getDate());
}

function parseDate(value: string): Date {
    const parts = value.split("-")
    return new Date(parseInt(parts[0]), parseInt(parts[1]) - 1, parseInt(parts[2]))
}

function Description(props: DescriptionProps) {
    const o = props.offering
    // TODO: more validations than just mileage
    const [valid, setValid] = useState<Partial<Record<keyof OfferingOverview, boolean>>>({ mileage: true })
    const [allFeatures, setAllFeatures] = useState<Feature[]>()
    useEffect(() => {
        fetchAllFeatures().then(fs => setAllFeatures(fs))
    }, [])
    const [activeFeatureTab, setActiveFeatureTab] = useState<FeatureCategory>('INTERIOR')
    const [inspectionValid, setInspectionValid] = useState<boolean>(!!o.inspectionValidUntil)
    return (
        <Form isHorizontal >
            <FormGroup fieldId="make" label="Make" isRequired>
                <MakeSelect
                    make={ o.overview.make }
                    onChange={ make => props.onChange({ ...o, overview: { ...o.overview, make }}) }/>
            </FormGroup>
            <FormGroup fieldId="model" label="Model" isRequired>
                <ModelSelect
                    make={ o.overview.make}
                    model={ o.overview.model}
                    onChange={ model => props.onChange({ ...o, overview: { ...o.overview, model }}) }/>
            </FormGroup>
            <FormGroup fieldId="year" label="Year" isRequired>
                <NumberSelect
                    value={ o.overview.year }
                    onChange={ year => props.onChange({ ...o, overview: { ...o.overview, year }}) }
                    from={ 1945 }
                    to={ new Date().getFullYear() }
                    order='desc' />
            </FormGroup>
            <FormGroup fieldId="trimLevel" label="Trim level">
                <TextInput
                    id="trimLevel"
                    value={ o.overview.trimLevel }
                    onChange={ trimLevel => props.onChange({ ...o, overview: { ...o.overview, trimLevel }}) }
                    />
            </FormGroup>
            <FormGroup fieldId="fuel" label="Fuel" isRequired>
                <SimpleSelect
                    value={ o.overview.fuel }
                    onChange={ fuel => props.onChange({ ...o, overview: { ...o.overview, fuel }}) }
                    options={["Gasoline", "Diesel", "Electric"]} />
            </FormGroup>
            <FormGroup fieldId="color" label="Color" isRequired>
                <TextInput
                    id="color"
                    value={ o.overview.color }
                    onChange={ color => props.onChange({ ...o, overview: { ...o.overview, color }}) }
                    />
            </FormGroup>
            <FormGroup fieldId="mileage" label="Mileage" isRequired>
                <TextInput
                    id="mileage"
                    validated={ valid.mileage ? 'default' : 'error' }
                    value={ o.overview?.mileage }
                    onChange={ m => {
                        if (/^-?\d*$/.test(m)) {
                            props.onChange({ ...o, overview: { ...o.overview, mileage: parseInt(m) }}) 
                            setValid({ ...valid, mileage: true });
                        } else {
                            setValid({ ...valid, mileage: false });
                        }
                    }}
                    />
            </FormGroup>
            <FormGroup fieldId="prevOwners" label="Number of previous owners" >
                <SimpleSelect
                    value={ o.prevOwners !== undefined ? prevOwnersNumberToString(o.prevOwners) : undefined }
                    onChange={ prev => props.onChange({ ...o, prevOwners: prevOwnersToNumber(prev.toString()) })}
                    options={["unknown", "0", "1", "2", "3", "more"]} />
            </FormGroup>
            <FormGroup fieldId="inspectionValidUntil" label="Inspection valid until" >
                <Checkbox
                    id="inspectionValid"
                    label="valid"
                    isChecked={ inspectionValid }
                    onChange={ valid => {
                        setInspectionValid(valid)
                        if (!valid) props.onChange({ ...o, inspectionValidUntil: undefined })
                    }} />
                <DatePicker
                    id="inspectionValidUntil"
                    placeholder="yyyy-mm-dd"
                    isDisabled={!inspectionValid}
                    value={ o.inspectionValidUntil ? formatDate(o.inspectionValidUntil) : undefined }
                    dateParse={ parseDate }
                    onClick={ e => e.preventDefault() }
                    onChange={ (text, date) => {
                        if (/\d\d\d\d-\d\d-\d\d/.test(text)) {
                            props.onChange({ ...o, inspectionValidUntil: date })
                        }
                    }} />
            </FormGroup>
            <FormGroup fieldId="history" label="History" >
                <TextInput
                    id="history"
                    value={ o.history }
                    onChange={ history => props.onChange({ ...o, history })} />
            </FormGroup>
            <FormGroup fieldId="features" label="Features">
                <Tabs activeKey={activeFeatureTab} onSelect={(e, key) => {
                    e.preventDefault()
                    setActiveFeatureTab(key as FeatureCategory)
                 }} >
                    <Tab key={0} eventKey={'INTERIOR'} title={<TabTitleText>Interior</TabTitleText>} />
                    <Tab key={1} eventKey={'INFOTAINMENT'} title={<TabTitleText>Infotainment</TabTitleText>} />
                    <Tab key={2} eventKey={'EXTERIOR'} title={<TabTitleText>Exterior</TabTitleText>} />
                    <Tab key={3} eventKey={'SAFETY'} title={<TabTitleText>Safety</TabTitleText>} />
                    <Tab key={4} eventKey={'OTHER'} title={<TabTitleText>Extra</TabTitleText>} />
                </Tabs>
                <div className="featureTabs">
                {
                    allFeatures?.filter(f => f.category === activeFeatureTab ).map(f => <Checkbox
                        key={ f.id }
                        isChecked={ o.features?.includes(f)}
                        id={ f.id }
                        onChange={ checked => {
                            var newFeatures = o.features || []
                            if (checked) {
                                newFeatures = [ ...newFeatures, f]
                            } else {
                                const index = newFeatures.indexOf(f)
                                if (index >= 0) {
                                    newFeatures.splice(index, 1);
                                }
                            }
                            props.onChange({ ...o, features: newFeatures })
                        }}
                        label={f.description ? <Tooltip content={f.description}><>{ f.name }</></Tooltip> : f.name}
                    />)
                }
                </div>
            </FormGroup>
        </Form>
    )
}

function prevOwnersNumberToString(prev: number): string {
    if (prev < 0) {
        return "unknown"
    } else if (prev > 3) {
        return "more";
    } else {
        return prev.toString()
    }
}

function prevOwnersToNumber(prev: string): number {
    if (prev === "unknown") {
        return -1;
    } else if (prev === "more") {
        return 100;
    } else {
        return parseInt(prev)
    }
}

type ContactProps = {
    contactInfo: Partial<ContactInfo>,
    onChange(contactInfo: Partial<ContactInfo>): void,
}

function Contact(props: ContactProps) {
    const c = props.contactInfo
    return (
        <Form isHorizontal>
            <FormGroup fieldId="firstName" label="First name" isRequired>
                <TextInput
                    id="firstName"
                    value={ c.firstName }
                    onChange={ firstName => props.onChange({ ...c, firstName })}
                />
            </FormGroup>
            <FormGroup fieldId="lastName" label="Last name" isRequired>
                <TextInput
                    id="lastName"
                    value={ c.lastName }
                    onChange={ lastName => props.onChange({ ...c, lastName })}
                />
            </FormGroup>
            <FormGroup fieldId="email" label="E-mail" isRequired>
                { /* TODO validation */ }
                <TextInput
                    id="email"
                    value={ c.email }
                    onChange={ email => props.onChange({ ...c, email })}
                />
            </FormGroup>
            <FormGroup fieldId="phone" label="Phone number" isRequired>
                <TextInput
                    id="phone"
                    value={ c.phone }
                    onChange={ phone => props.onChange({ ...c, phone })}
                />
            </FormGroup>
        </Form>
    )
}

type FinishedProps = {
    completed: boolean,
    failed: boolean,
}

function Finished(props: FinishedProps) {
    const history = useHistory();
    return (
        <Bullseye style={{ minHeight: "400px"}}>
            { props.completed &&
            <div style={{ textAlign: "center"}}>
                { props.failed ? "Sorry, there was an error :-/" : "Thank you!" }<br />
                <Button onClick={() => history.push("/")}>Go to main page</Button>
            </div>
            }
            { !props.completed &&
                <Spinner />
            }
        </Bullseye>
    )
}

function SellPage() {
    const [offering, setOffering] = useState<PartialOffering>({ overview: {} })
    const [contactInfo, setContactInfo] = useState<Partial<ContactInfo>>({})
    const [completed, setCompleted] = useState(false)
    const [failed, setFailed] = useState(false)
    const token = getUserToken();
    const steps: WizardStep[] = [{
        id: 'description',
        name: "Vehicle description",
        component: <Description offering={offering} onChange={setOffering} />,
        nextButtonText: token ? <>Publish your offering</> : undefined,
    }];
    if (!token) {
        steps.push({
            id: 'contact',
            name: "Contact",
            component: <Contact contactInfo={contactInfo} onChange={setContactInfo} />,
            nextButtonText: <>Publish your offering</>,
        })
    }
    steps.push({
        id: 'finished',
        name: "Finished",
        isFinishedStep: true,
        component: <Finished completed={ completed} failed={failed} />,
    })
    return (<Wizard
        navAriaLabel="Sell vehicle steps"
        mainAriaLabel="Sell vehicle content"
        steps={steps}
        onNext={({ id }) => {
            if (id === 'finished') {
                publishOffering(offering, contactInfo).then(res => {
                    if (res.status >= 200 && res.status < 300) {
                        setCompleted(true)
                    } else {
                        setFailed(true)
                        setCompleted(true)
                    }
                }, _ => setFailed(true))
            }
        }}
    />)
}

export default SellPage