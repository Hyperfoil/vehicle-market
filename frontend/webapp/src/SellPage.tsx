import React, { useEffect, useState } from 'react'
import {
    Bullseye,
    Button,
    Checkbox,
    Form,
    FormGroup,
    Tab,
    Tabs,
    TabTitleText,
    TextInput,
    Tooltip,
    Wizard,
} from '@patternfly/react-core'
import { useHistory } from 'react-router'
import MakeSelect from './components/MakeSelect'
import ModelSelect from './components/ModelSelect'
import NumberSelect from './components/NumberSelect'
import SimpleSelect from './components/SimpleSelect'
import { fetchAllFeatures, FeatureCategory, Feature, Offering } from './listingService'
import { VehicleDescription } from './discoveryService'
import './SellPage.css'

type PartialOffering = Partial<Omit<Offering, "model"> & { model: Partial<VehicleDescription> }>

type DescriptionProps = {
    offering: PartialOffering,
    onChange(o: PartialOffering): void,
}

function Description(props: DescriptionProps) {
    const o = props.offering
    const [valid, setValid] = useState<Partial<Record<keyof Offering, boolean>>>({ mileage: true })
    const [allFeatures, setAllFeatures] = useState<Feature[]>()
    useEffect(() => {
        fetchAllFeatures().then(fs => setAllFeatures(fs))
    }, [])
    const [activeFeatureTab, setActiveFeatureTab] = useState<FeatureCategory>('INTERIOR')
    return (
        <Form isHorizontal>
            {/* <FormGroup fieldId="make" label="Make" isRequired>
                <MakeSelect
                    make={ o.model?.make }
                    onChange={ make => props.onChange({ ...o, model: { ...o.model, make} }) }/>
            </FormGroup>
            <FormGroup fieldId="model" label="Model" isRequired>
                <ModelSelect
                    make={ o.model?.make}
                    model={ o.model?.model}
                    onChange={ model => props.onChange({ ...o, model: { ...o.model, model} }) }/>
            </FormGroup>
            <FormGroup fieldId="year" label="Year" isRequired>
                <NumberSelect
                    value={ o.year}
                    onChange={ year => props.onChange({ ...o, year}) }
                    from={ 1945 }
                    to={ new Date().getFullYear() }
                    order='desc' />
            </FormGroup>
            <FormGroup fieldId="trimLevel" label="Trim level">
                <TextInput
                    value={ o.trimLevel }
                    onChange={ trimLevel => props.onChange({ ...o, trimLevel }) }
                    />
            </FormGroup>
            <FormGroup fieldId="fuel" label="Fuel" isRequired>
                <SimpleSelect
                    value={ o.model?.fuel }
                    onChange={ fuel => props.onChange({ ...o, model: { ...o.model, fuel }})}
                    options={["Gasoline", "Diesel", "Electric"]} />
            </FormGroup>
            <FormGroup fieldId="color" label="Color" isRequired>
                <TextInput
                    value={ o.color }
                    onChange={ color => props.onChange({ ...o, color }) }
                    />
            </FormGroup>
            <FormGroup fieldId="mileage" label="Mileage" isRequired>
                <TextInput
                    id="mileage"
                    validated={ valid.mileage ? 'default' : 'error' }
                    value={ o.mileage }
                    onChange={ m => {
                        if (/^-?\d*$/.test(m)) {
                            props.onChange({ ...o, mileage: parseInt(m)})
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
            </FormGroup> */}
            <FormGroup fieldId="inspectionValidUntil" label="Inspection valid until" >
                <TextInput
                    id="inspectionValidUntil"
                    placeholder="yyyy-mm-dd"
                    value={ o.inspectionValidUntil?.toDateString() }
                    onChange={ date => props.onChange({ ...o, inspectionValidUntil: new Date(date) })} />
                {/* TODO use datepicker, but it crashes somehow */}
                {/* <DatePicker onChange={(str, date) => console.log('onChange', str, date)} /> */}
            </FormGroup>
            <FormGroup fieldId="history" label="History" >
                <TextInput
                    id="history"
                    value={ o.history }
                    onChange={ history => props.onChange({ ...o, history })} />
            </FormGroup>
            <FormGroup fieldId="features" label="Features">
                <Tabs activeKey={activeFeatureTab} onSelect={(_, key) => setActiveFeatureTab(key as FeatureCategory) } >
                    <Tab eventKey={'INTERIOR'} title={<TabTitleText>Interior</TabTitleText>} />
                    <Tab eventKey={'INFOTAINMENT'} title={<TabTitleText>Infotainment</TabTitleText>} />
                    <Tab eventKey={'EXTERIOR'} title={<TabTitleText>Exterior</TabTitleText>} />
                    <Tab eventKey={'SAFETY'} title={<TabTitleText>Safety</TabTitleText>} />
                    <Tab eventKey={'OTHER'} title={<TabTitleText>Extra</TabTitleText>} />
                </Tabs>
                <Button onClick={() => setActiveFeatureTab('EXTERIOR')}>touch me</Button>
                <div className="featureTabs">
                { /*
                    allFeatures?.filter(f => f.category === activeFeatureTab ).map(f => <Checkbox
                            isChecked={ o.features?.includes(f)}
                            id={ f.id }
                            onChange={ checked => {
                                var newFeatures = o.features || []
                                if (checked) {
                                    newFeatures = [ ...newFeatures, f]
                                } else {
                                    const index = newFeatures.indexOf(f)
                                    if (index >= 0) {
                                        newFeatures = newFeatures.splice(index, 1);
                                    }
                                }
                                props.onChange({ ...o, features: newFeatures })
                            }}
                            label={f.description ? <Tooltip content={f.description}><>{ f.name }</></Tooltip> : f.name}
                        />)
                        */ }
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

function Finished() {
    const history = useHistory();
    return (
        <Bullseye>
            Thank you!
            <Button onClick={() => history.push("/")}>Go to main page</Button>
        </Bullseye>
    )
}

function SellPage() {
    const [offering, setOffering] = useState<PartialOffering>({})
    return (<Description offering={offering} onChange={setOffering} />)
    // return (<Wizard
    //     navAriaLabel="Sell vehicle steps"
    //     mainAriaLabel="Sell vehicle content"
    //     steps={[ {
    //         id: 'description',
    //         name: "Vehicle description",
    //         component: <Description offering={offering} onChange={setOffering} />,
    //     }, {
    //         id: 'contact',
    //         name: "Contact",
    //         nextButtonText: "Post offering",
    //     }, {
    //         id: 'finished',
    //         name: "Finished",
    //         component: <Finished />,
    //     } ]}
    // />)
}

export default SellPage