import React, { useEffect, useRef, useState } from 'react'
import {
    Select, SelectOption
} from '@patternfly/react-core'
import { fetchModels } from '../discoveryService'

type ModelSelectProps = {
    make?: string,
    model?: string,
    onChange(m: string): void,
}

function ModelSelect(props: ModelSelectProps) {
    const allModels = useRef(new Map<string, string[]>())
    const [models, setModels] = useState<string[]>()
    useEffect(() => {
        if (!props.make) {
            return;
        }
        const currentModels = allModels.current.get(props.make)
        if (currentModels) {
            setModels(currentModels)
        } else {
            setModels(undefined)
            fetchModels(props.make).then(res => {
                const ms = res.sort()
                allModels.current.set(props.make || "", ms);
                setModels(ms)
            }, () => setModels([]))
        }
    }, [props.make])
    const [open, setOpen] = useState(false)
    return (<Select
        menuAppendTo="parent"
        isOpen={open}
        isDisabled={!props.make}
        placeholderText="Please select..."
        onToggle={setOpen}
        selections={props.model}
        onSelect={(e, m) => {
            props.onChange(m as string)
            setOpen(false)
        }}
    >
        { models?.map((m, i) => <SelectOption key={i} value={m} />) || []}
    </Select>)
}

export default ModelSelect