import React, {useState, useEffect} from 'react'
import { fetchUserInfo, UserInfo as UserInfoData } from './userService'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faUser } from '@fortawesome/free-solid-svg-icons'


type UserInfoProps = {
    token: string,
}

function UserInfo(props: UserInfoProps) {
    const [userInfo, setUserInfo] = useState<UserInfoData>()
    useEffect(() => {
        if (props.token) {
            fetchUserInfo(props.token).then(setUserInfo)
        }
    }, [props.token])
    if (!userInfo) return null;
    return (
        <span style={{ color: "white"}}>
            <FontAwesomeIcon icon={ faUser } />{'\u00A0'}
            { userInfo.firstName } { userInfo.lastName }
        </span>
    )
}

export default UserInfo;