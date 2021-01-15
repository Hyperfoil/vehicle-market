import React, { useState } from 'react'
import {
    Select, SelectOption
} from '@patternfly/react-core'

type SimpleSelectProps<T> = {
    value?: T,
    options: T[],
    onChange(m: T): void,
}

function SimpleSelect<T>(props: SimpleSelectProps<T>) {
    const [open, setOpen] = useState(false)
    return (<Select
        menuAppendTo="parent"
        isOpen={open}
        placeholderText="Please select..."
        onToggle={setOpen}
        selections={props.value}
        onSelect={(_, v) => {
            props.onChange(v as T)
            setOpen(false)
        }}
    >
        { props.options.map((v, i) => <SelectOption key={i} value={ String(v) } />) }
    </Select>)
}

export default SimpleSelect