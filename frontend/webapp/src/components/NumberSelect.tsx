import React, { useState } from 'react'
import {
    Select, SelectOption
} from '@patternfly/react-core'

type NumberSelectProps = {
    value?: number,
    onChange(m: number): void,
    from: number,
    to: number,
    order?: 'asc' | 'desc',
}

function NumberSelect(props: NumberSelectProps) {
    const [open, setOpen] = useState(false)
    return (<Select
        menuAppendTo="parent"
        isOpen={open}
        placeholderText="Please select..."
        onToggle={setOpen}
        selections={props.value}
        onSelect={(_, v) => {
            props.onChange(v as number)
            setOpen(false)
        }}
    >
        { [...Array(props.to - props.from + 1).keys()].map((_, i) =>
            <SelectOption key={i} value={ String(props.order === 'desc' ? props.to - i : props.from + i) } />)
        }
    </Select>)
}

export default NumberSelect