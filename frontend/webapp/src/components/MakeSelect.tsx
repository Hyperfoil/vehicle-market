import React, { useEffect, useState } from 'react'
import {
    Select, SelectOption
} from '@patternfly/react-core'
import { fetchMakes } from '../discoveryService'

type MakeSelectProps = {
    make?: string,
    onChange(m: string): void,
}

function MakeSelect(props: MakeSelectProps) {
    const [makes, setMakes] = useState<string[]>()
    useEffect(() => {
        fetchMakes().then(res => setMakes(res.sort()), () => setMakes([]))
    }, [])
    const [open, setOpen] = useState(false)
    return (<Select
        menuAppendTo="parent"
        isOpen={open}
        placeholderText="Please select..."
        onToggle={setOpen}
        selections={props.make}
        onSelect={(e, m) => {
            props.onChange(m as string)
            setOpen(false)
        }}
    >
        { makes?.map((m, i) => <SelectOption key={i} value={m} />) || []}
    </Select>)
}

export default MakeSelect