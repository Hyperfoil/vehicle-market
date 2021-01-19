import React, { useState } from 'react'
import { useHistory } from 'react-router'
import {
    Button,
    Form,
    FormGroup,
    Modal,
    TextInput,
} from '@patternfly/react-core'
import { login } from './userService'

type LoginModalProps = {
    isOpen: boolean,
    onClose(): void,
}

function LoginModal(props: LoginModalProps) {
    const [username, setUsername] = useState<string>()
    const [password, setPassword] = useState<string>()
    const history = useHistory()
    const close = () => {
        setUsername(undefined)
        setPassword(undefined)
        props.onClose()
    }
    const [failed, setFailed] = useState(false)
    const doLogin = () => login(username || '', password || '').then(close, e => {
        console.error(e)
        setUsername(undefined)
        setPassword(undefined)
        setFailed(true)
    })
    return (
        <Modal
            variant="small"
            title="Login to Vehicle Market"
            isOpen={props.isOpen}
            onClose={ props.onClose }
            showClose={ false }
            actions={[
                <Button
                    isDisabled={ !username || username === '' || !password || password === '' }
                    onClick={ doLogin }
                >Login</Button>,
                <Button variant="secondary" onClick={ close }>Cancel</Button>
            ] }
        >
            <Form isHorizontal>
                <FormGroup fieldId="username" label="User name">
                    <TextInput
                        id="username"
                        value={username}
                        onChange={value => {
                            setFailed(false)
                            setUsername(value)
                        }}
                    />
                </FormGroup>
                <FormGroup fieldId="username" label="Password">
                    <TextInput
                        id="password"
                        type="password"
                        value={password}
                        onChange={value => {
                            setFailed(false)
                            setPassword(value)
                        }}
                        onKeyPress={ e => {
                            if (e.key == 'Enter' && username && password) {
                                doLogin()
                            }
                        }}
                    />
                </FormGroup>
            </Form>
            { failed && <><br /><span style={{ color: "red"}}>Login failed!</span></> }
            <br />
            Lost username/password? Recover it <Button style={{ padding: "0" }} variant="link">here</Button>!<br />
            Not registered? <Button style={{ padding: "0" }} variant="link" onClick={ () => history.push("/register")}>Sign up now!</Button>
        </Modal>
    )
}

export default LoginModal;